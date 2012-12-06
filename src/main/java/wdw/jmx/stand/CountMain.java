package wdw.jmx.stand;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class CountMain {

    public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException,
                                          MBeanRegistrationException, NotCompliantMBeanException, IOException,
                                          InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("wdw:name=Count");
        Count count = new Count();
        server.registerMBean(count, objectName);
        // ��HtmlAdaptor����,��Ҫ�����jmxtools.jar
        ObjectName adapterName = new ObjectName("wdw:name=htmladapter");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(8080);// Ĭ�϶˿�8082
        server.registerMBean(adapter, adapterName);
        adapter.start();
        System.out.println("htmladapter start at 8080.....");
        // ����ֻ�ǲ����ã�����ȫ������ʹ��RMI��Server,ͨ��jconsole����
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8888/server");
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
        cs.start();
        System.out.println("rmiadapter register at 8888.....");
        int i = 1;
        while (!count.stop) {
            Thread.sleep(1000 * 5);
            count.setCount(i);
            i++;
        }
    }
}
