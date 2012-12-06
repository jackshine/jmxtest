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
        // 构建Registry ss
        Registry registry = Registry.getRegistry(null, null);
        InputStream stream = CountModelerMain.class.getResourceAsStream("mbeans.xml");
        registry.loadMetadata(stream);
        stream.close();
        // 由Registry得到一个MBeanServer
        MBeanServer server = registry.getMBeanServer();
        // 得到Count在描述文件中的信息类，对应于xml文件<mbean>标签的name属性。
        ManagedBean managed = registry.findManagedBean("Count");
        // 创建ObjectName
        ObjectName objectName = new ObjectName(managed.getDomain() + ":name=" + managed.getGroup());
        // 得到ModelMBean
        Count count = new Count();
        ModelMBean model = managed.createMBean(count);
        // 注册MBean
        server.registerMBean(model, objectName);

        // 以HtmlAdaptor管理,需要额外的jmxtools.jar
        ObjectName adapterName = new ObjectName("wdw:name=htmladapter");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(8080);// 默认端口8082
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
