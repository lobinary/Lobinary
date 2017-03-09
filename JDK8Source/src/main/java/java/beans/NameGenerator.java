/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package java.beans;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * A utility class which generates unique names for object instances.
 * The name will be a concatenation of the unqualified class name
 * and an instance number.
 * <p>
 * For example, if the first object instance javax.swing.JButton
 * is passed into <code>instanceName</code> then the returned
 * string identifier will be &quot;JButton0&quot;.
 *
 * <p>
 *  为对象实例生成唯一名称的实用程序类。名称将是非限定类名和实例号的连接。
 * <p>
 *  例如,如果第一对象实例javax.swing.JButton被传递到<code> instanceName </code>,则返回的字符串标识符将是"JButton0"。
 * 
 * 
 * @author Philip Milne
 */
class NameGenerator {

    private Map<Object, String> valueToName;
    private Map<String, Integer> nameToCount;

    public NameGenerator() {
        valueToName = new IdentityHashMap<>();
        nameToCount = new HashMap<>();
    }

    /**
     * Clears the name cache. Should be called to near the end of
     * the encoding cycle.
     * <p>
     *  清除名称缓存。应该调用接近编码周期的结束。
     * 
     */
    public void clear() {
        valueToName.clear();
        nameToCount.clear();
    }

    /**
     * Returns the root name of the class.
     * <p>
     *  返回类的根名称。
     * 
     */
    @SuppressWarnings("rawtypes")
    public static String unqualifiedClassName(Class type) {
        if (type.isArray()) {
            return unqualifiedClassName(type.getComponentType())+"Array";
        }
        String name = type.getName();
        return name.substring(name.lastIndexOf('.')+1);
    }

    /**
     * Returns a String which capitalizes the first letter of the string.
     * <p>
     *  返回一个字符串,它大写字符串的第一个字母。
     * 
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    /**
     * Returns a unique string which identifies the object instance.
     * Invocations are cached so that if an object has been previously
     * passed into this method then the same identifier is returned.
     *
     * <p>
     *  返回标识对象实例的唯一字符串。调用缓存,以便如果先前已将对象传递到此方法,则返回相同的标识符。
     * 
     * @param instance object used to generate string
     * @return a unique string representing the object
     */
    public String instanceName(Object instance) {
        if (instance == null) {
            return "null";
        }
        if (instance instanceof Class) {
            return unqualifiedClassName((Class)instance);
        }
        else {
            String result = valueToName.get(instance);
            if (result != null) {
                return result;
            }
            Class<?> type = instance.getClass();
            String className = unqualifiedClassName(type);

            Integer size = nameToCount.get(className);
            int instanceNumber = (size == null) ? 0 : (size).intValue() + 1;
            nameToCount.put(className, new Integer(instanceNumber));

            result = className + instanceNumber;
            valueToName.put(instance, result);
            return result;
        }
    }
}
