/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性Copyright IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.CORBA_2_3.portable;

import java.io.SerializablePermission;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * InputStream provides for the reading of all of the mapped IDL types
 * from the stream. It extends org.omg.CORBA.portable.InputStream.  This
 * class defines new methods that were added for CORBA 2.3.
 *
 * <p>
 *  InputStream提供从流中读取所有映射的IDL类型。它扩展org.omg.CORBA.portable.InputStream。这个类定义了为CORBA 2.3添加的新方法。
 * 
 * 
 * @see org.omg.CORBA.portable.InputStream
 * @author  OMG
 * @since   JDK1.2
 */

public abstract class InputStream extends org.omg.CORBA.portable.InputStream {


    private static final String ALLOW_SUBCLASS_PROP = "jdk.corba.allowInputStreamSubclass";

    private static final boolean allowSubclass = AccessController.doPrivileged(
        new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
            String prop = System.getProperty(ALLOW_SUBCLASS_PROP);
                return prop == null ? false :
                           (prop.equalsIgnoreCase("false") ? false : true);
            }
        });

    private static Void checkPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            if (!allowSubclass)
                sm.checkPermission(new
                    SerializablePermission("enableSubclassImplementation"));
        }
        return null;
    }

    private InputStream(Void ignore) { }

    /**
     * Create a new instance of this class.
     *
     * throw SecurityException if SecurityManager is installed and
     * enableSubclassImplementation SerializablePermission
     * is not granted or jdk.corba.allowOutputStreamSubclass system
     * property is either not set or is set to 'false'
     * <p>
     *  创建此类的新实例。
     * 
     *  如果安装了SecurityManager,则抛出SecurityException,并且未授予serialSubclassImplementation SerializablePermission或未
     * 设置jdk.corba.allowOutputStreamSubclass系统属性或将其设置为"false"。
     * 
     */
    public InputStream() {
        this(checkPermission());
    }

    /**
     * Unmarshalls a value type from the input stream.
     * <p>
     *  从输入流中取消编组值类型。
     * 
     * 
     * @return the value type unmarshalled from the input stream
     */
    public java.io.Serializable read_value() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshalls a value type from the input stream.
     * <p>
     *  从输入流中取消编组值类型。
     * 
     * 
     * @param clz is the declared type of the value to be unmarshalled
     * @return the value unmarshalled from the input stream
     */
    public java.io.Serializable read_value(java.lang.Class clz) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshalls a value type from the input stream.
     * <p>
     *  从输入流中取消编组值类型。
     * 
     * 
     * @param factory is the instance fo the helper to be used for
     * unmarshalling the value type
     * @return the value unmarshalled from the input stream
     */
    public java.io.Serializable read_value(org.omg.CORBA.portable.BoxedValueHelper factory) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshalls a value type from the input stream.
     * <p>
     *  从输入流中取消编组值类型。
     * 
     * 
     * @param rep_id identifies the type of the value to be unmarshalled
     * @return value type unmarshalled from the input stream
     */
    public java.io.Serializable read_value(java.lang.String rep_id) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshalls a value type from the input stream.
     * <p>
     *  从输入流中取消编组值类型。
     * 
     * 
     * @param value is an uninitialized value which is added to the orb's
     * indirection table before calling Streamable._read() or
     * CustomMarshal.unmarshal() to unmarshal the value.
     * @return value type unmarshalled from the input stream
     */
    public java.io.Serializable read_value(java.io.Serializable value) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshal the value object or a suitable stub object.
     * <p>
     *  解组值对象或合适的存根对象。
     * 
     * 
     * @return ORB runtime returns the value object or a suitable stub object.
     */
    public java.lang.Object read_abstract_interface() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Unmarshal the class object or the stub class corresponding to the passed type.
     * <p>
     *  解组类对象或与传递的类型对应的存根类。
     * 
     * @param clz is the Class object for the stub class which corresponds to
     * the type that is statically expected.
     * @return ORB runtime returns the value object or a suitable stub object.
     */
    public java.lang.Object read_abstract_interface(java.lang.Class clz) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

}
