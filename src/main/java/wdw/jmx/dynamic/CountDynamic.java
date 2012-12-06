package wdw.jmx.dynamic;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;

/**
 * 类CountDynamic.java的实现描述：需要实现DynamicMBean的接口，描述此类的一些构造函数，属性，方法等元数据信息
 * 
 * @author wdw 2012-11-16 上午12:25:11
 */
public class CountDynamic implements DynamicMBean {

    private long                   count;
    public boolean                 stop;
    private MBeanInfo              mBeanInfo = null;          // 元数据
    private String                 className;                 // 类信息
    private String                 description;               // 描述
    private MBeanAttributeInfo[]   attributes;                // 属性信息
    private MBeanConstructorInfo[] constructors;              // 构造函数信息
    private MBeanOperationInfo[]   operations;                // 方法调用
    MBeanNotificationInfo[]        mBeanNotificationInfoArray; // 消息机制

    @SuppressWarnings("rawtypes")
	public CountDynamic() {
        className = this.getClass().getName();
        description = "CountDynamic is a dynamic MBean.";
        attributes = new MBeanAttributeInfo[2];
        constructors = new MBeanConstructorInfo[1];
        operations = new MBeanOperationInfo[1];// 这次只暴露stop方法
        mBeanNotificationInfoArray = new MBeanNotificationInfo[0];
        // 设定构造函数
        Constructor[] thisconstructors = this.getClass().getConstructors();
        constructors[0] = new MBeanConstructorInfo("CountDynamic(): Constructs a CountDynamic object",
                                                   thisconstructors[0]);
        // 设定Count属性
        attributes[0] = new MBeanAttributeInfo("Count", "java.lang.Long", "Count: count long.", true, true, false);
        attributes[1] = new MBeanAttributeInfo("Stop", "java.lang.Boolean", "Stop: stop boolean.", true, true, false);
        // operate method 我们的操作方法是print
        MBeanParameterInfo[] params = null;// 无参数
        operations[0] = new MBeanOperationInfo("stop", "stop(): stop count++", params, "void", MBeanOperationInfo.INFO);
        mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations,
                                  mBeanNotificationInfoArray);
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (attribute == null) return null;
        if (attribute.equals("Count")) return count;
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException,
                                                 MBeanException, ReflectionException {
        if (attribute == null) return;
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name.equals("Count")) {
            if (value == null) {
                count = 0;
            } else if (value instanceof Long) {
                count = (long) value;
            }
        }
        if (name.equals("Stop")) {
            if (value == null) {
                stop = false;
            } else if (value instanceof Boolean) {
                stop = (boolean) value;
            }
        }

    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        if (attributes == null) return null;
        AttributeList resultList = new AttributeList();
        // if attributeNames is empty, return an empty result list
        if (attributes.length == 0) return resultList;
        for (int i = 0; i < attributes.length; i++) {
            Object value;
            try {
                value = getAttribute(attributes[i]);
                resultList.add(new Attribute(attributes[i], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public AttributeList setAttributes(AttributeList attributes) {
        if (attributes == null) return null;
        AttributeList resultList = new AttributeList();
        if (attributes.isEmpty()) return resultList;
        for (Iterator i = attributes.iterator(); i.hasNext();) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException,
                                                                                ReflectionException {
        if (actionName.equals("stop")) {
            // 具体实现我们的操作方法stop
            stop = true;
            operations = new MBeanOperationInfo[2];// 设定数组为两个
            operations[0] = new MBeanOperationInfo("stop", "stop(): stop count++", null, "void",
                                                   MBeanOperationInfo.INFO);
            operations[1] = new MBeanOperationInfo("add", "add(): make count++", null, "void", MBeanOperationInfo.INFO);
            mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations,
                                      mBeanNotificationInfoArray);
            return null;
        } else if (actionName.equals("add")) {
            count++;
            return null;
        } else {
            // unrecognized operation name:
            throw new ReflectionException(new NoSuchMethodException(actionName), "Cannot find the operation "
                                                                                 + actionName + " in " + className);
        }
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return mBeanInfo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
