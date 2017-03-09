/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

// java import
import java.io.Serializable;

// RI import
import javax.management.ObjectName;


/**
 * Used to represent the object name of an MBean and its class name.
 * If the MBean is a Dynamic MBean the class name should be retrieved from
 * the <CODE>MBeanInfo</CODE> it provides.
 *
 * <p>
 *  用于表示MBean的对象名称及其类名称。如果MBean是动态MBean,则应该从它提供的<CODE> MBeanInfo </CODE>检索类名。
 * 
 * 
 * @since 1.5
 */
public class ObjectInstance implements Serializable   {


    /* Serial version */
    private static final long serialVersionUID = -4099952623687795850L;

    /**
    /* <p>
    /* 
     * @serial Object name.
     */
    private ObjectName name;

    /**
    /* <p>
    /* 
     * @serial Class name.
     */
    private String className;

    /**
     * Allows an object instance to be created given a string representation of
     * an object name and the full class name, including the package name.
     *
     * <p>
     *  允许在给定对象名称和完整类名(包括程序包名称)的字符串表示形式的情况下创建对象实例。
     * 
     * 
     * @param objectName  A string representation of the object name.
     * @param className The full class name, including the package
     * name, of the object instance.  If the MBean is a Dynamic MBean
     * the class name corresponds to its {@link
     * DynamicMBean#getMBeanInfo()
     * getMBeanInfo()}<code>.getClassName()</code>.
     *
     * @exception MalformedObjectNameException The string passed as a
     * parameter does not have the right format.
     *
     */
    public ObjectInstance(String objectName, String className)
            throws MalformedObjectNameException {
        this(new ObjectName(objectName), className);
    }

    /**
     * Allows an object instance to be created given an object name and
     * the full class name, including the package name.
     *
     * <p>
     *  允许在给定对象名称和完整类名(包括程序包名称)的情况下创建对象实例。
     * 
     * 
     * @param objectName  The object name.
     * @param className  The full class name, including the package
     * name, of the object instance.  If the MBean is a Dynamic MBean
     * the class name corresponds to its {@link
     * DynamicMBean#getMBeanInfo()
     * getMBeanInfo()}<code>.getClassName()</code>.
     * If the MBean is a Dynamic MBean the class name should be retrieved
     * from the <CODE>MBeanInfo</CODE> it provides.
     *
     */
    public ObjectInstance(ObjectName objectName, String className) {
        if (objectName.isPattern()) {
            final IllegalArgumentException iae =
                new IllegalArgumentException("Invalid name->"+
                                             objectName.toString());
            throw new RuntimeOperationsException(iae);
        }
        this.name= objectName;
        this.className= className;
    }


    /**
     * Compares the current object instance with another object instance.
     *
     * <p>
     *  将当前对象实例与另一个对象实例进行比较。
     * 
     * 
     * @param object  The object instance that the current object instance is
     *     to be compared with.
     *
     * @return  True if the two object instances are equal, otherwise false.
     */
    public boolean equals(Object object)  {
        if (!(object instanceof ObjectInstance)) {
            return false;
        }
        ObjectInstance val = (ObjectInstance) object;
        if (! name.equals(val.getObjectName())) return false;
        if (className == null)
            return (val.getClassName() == null);
        return className.equals(val.getClassName());
    }

    public int hashCode() {
        final int classHash = ((className==null)?0:className.hashCode());
        return name.hashCode() ^ classHash;
    }

    /**
     * Returns the object name part.
     *
     * <p>
     *  返回对象名称部分。
     * 
     * 
     * @return the object name.
     */
    public ObjectName getObjectName()  {
        return name;
    }

    /**
     * Returns the class part.
     *
     * <p>
     *  返回类部件。
     * 
     * 
     * @return the class name.
     */
    public String getClassName()  {
        return className;
    }

    /**
     * Returns a string representing this ObjectInstance object. The format of this string
     * is not specified, but users can expect that two ObjectInstances return the same
     * string if and only if they are equal.
     * <p>
     *  返回表示此ObjectInstance对象的字符串。此字符串的格式未指定,但用户可以预期,两个ObjectInstances返回相同的字符串,当且仅当它们相等。
     */
    public String toString() {
        return getClassName() + "[" + getObjectName() + "]";
    }
 }
