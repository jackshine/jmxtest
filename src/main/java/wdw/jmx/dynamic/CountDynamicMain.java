package wdw.jmx.dynamic;

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

public class CountDynamicMain {

    public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException,
                                          MBeanRegistrationException, NotCompliantMBeanException, IOException,
                                          InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("wdw:name=CountDynamic");
        CountDynamic count = new CountDynamic();
        server.registerMBean(count, objectName);
        // 以HtmlAdaptor管理,需要额外的jmxtools.jar
        ObjectName adapterName = new ObjectName("wdw:name=htmladapter");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(8080);// 默认端口8082
        server.registerMBean(adapter, adapterName);
        adapter.start();
        System.out.println("htmladapter start at 8080.....");
        // 以上只是测试用，不安全，可以使用RMI的Server,通过jconsole连接
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
