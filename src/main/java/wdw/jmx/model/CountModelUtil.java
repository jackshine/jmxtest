package wdw.jmx.model;

import javax.management.MBeanOperationInfo;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class CountModelUtil {

    public static RequiredModelMBean createModlerMBean(Count count) {
        RequiredModelMBean model = null;
        try {
            model = new RequiredModelMBean();
            model.setManagedResource(count, "ObjectReference");
            ModelMBeanInfo info = createModelMBeanInfo();
            model.setModelMBeanInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    private static ModelMBeanInfo createModelMBeanInfo() {
        // 构造count属性信息
        ModelMBeanAttributeInfo count = new ModelMBeanAttributeInfo(//
                                                                    "count", // 属性名
                                                                    "java.lang.Long", // 属性类型
                                                                    "count", // 描述
                                                                    true, true, false, // 读写
                                                                    null);
        // 构造stop属性信息
        ModelMBeanAttributeInfo stop = new ModelMBeanAttributeInfo(//
                                                                   "stop", // 属性名
                                                                   "java.lang.Boolean", // 属性类型
                                                                   "stop", // 描述
                                                                   true, true, false, // 读写
                                                                   null);
        // 构造 stop()操作的信息
        ModelMBeanOperationInfo stopAction = new ModelMBeanOperationInfo("stop", null, null, "void",
                                                                         MBeanOperationInfo.INFO, null);
        // 组装 ModelMBeanInfo
        ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport(RequiredModelMBean.class.getName(), null,
                                                             new ModelMBeanAttributeInfo[] { count, stop }, null,
                                                             new ModelMBeanOperationInfo[] { stopAction }, null, null);
        return mbeanInfo;
    }
}
