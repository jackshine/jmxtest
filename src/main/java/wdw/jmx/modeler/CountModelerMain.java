package wdw.jmx.modeler;

import java.io.InputStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;

import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class CountModelerMain {

    public static void main(String[] args) throws Exception {
        // ����Registry
        Registry registry = Registry.getRegistry(null, null);
        InputStream stream = CountModelerMain.class.getResourceAsStream("mbeans.xml");
        registry.loadMetadata(stream);
        stream.close();
        // ��Registry�õ�һ��MBeanServer
        MBeanServer server = registry.getMBeanServer();
        // �õ�Count�������ļ��е���Ϣ�࣬��Ӧ��xml�ļ�<mbean>��ǩ��name���ԡ�
        ManagedBean managed = registry.findManagedBean("Count");
        // ����ObjectName
        ObjectName objectName = new ObjectName(managed.getDomain() + ":name=" + managed.getGroup());
        // �õ�ModelMBean
        Count count = new Count();
        ModelMBean model = managed.createMBean(count);
        // ע��MBean
        server.registerMBean(model, objectName);

        // ��HtmlAdaptor����,��Ҫ�����jmxtools.jar
        ObjectName adapterName = new ObjectName("wdw:name=htmladapter");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(8080);// Ĭ�϶˿�8082
        server.registerMBean(adapter, adapterName);
        adapter.start();
        System.out.println("htmladapter start at 8080.....");

        int i = 1;
        while (!count.stop) {
            Thread.sleep(1000 * 5);
            count.setCount(i);
            i++;
        }
    }

}
