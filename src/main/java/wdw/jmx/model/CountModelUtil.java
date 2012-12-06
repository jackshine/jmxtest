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
        // ����count������Ϣ
        ModelMBeanAttributeInfo count = new ModelMBeanAttributeInfo(//
                                                                    "count", // ������
                                                                    "java.lang.Long", // ��������
                                                                    "count", // ����
                                                                    true, true, false, // ��д
                                                                    null);
        // ����stop������Ϣ
        ModelMBeanAttributeInfo stop = new ModelMBeanAttributeInfo(//
                                                                   "stop", // ������
                                                                   "java.lang.Boolean", // ��������
                                                                   "stop", // ����
                                                                   true, true, false, // ��д
                                                                   null);
        // ���� stop()��������Ϣ
        ModelMBeanOperationInfo stopAction = new ModelMBeanOperationInfo("stop", null, null, "void",
                                                                         MBeanOperationInfo.INFO, null);
        // ��װ ModelMBeanInfo
        ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport(RequiredModelMBean.class.getName(), null,
                                                             new ModelMBeanAttributeInfo[] { count, stop }, null,
                                                             new ModelMBeanOperationInfo[] { stopAction }, null, null);
        return mbeanInfo;
    }
}
