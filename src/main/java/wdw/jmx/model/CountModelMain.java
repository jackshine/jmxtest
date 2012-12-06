package wdw.jmx.model;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.modelmbean.RequiredModelMBean;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class CountModelMain {

    public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException,
                                          MBeanRegistrationException, NotCompliantMBeanException, InterruptedException {
        MBeanServer server = MBeanServerFactory.createMBeanServer();
        ObjectName objectName = new ObjectName("wdw:name=CountModel");
        Count count = new Count();
        RequiredModelMBean model = CountModelUtil.createModlerMBean(count);
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
            System.out.println("******" + i);
            i++;
        }
    }

}
