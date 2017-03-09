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

package javax.rmi.CORBA;

import org.omg.CORBA.ORB;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA_2_3.portable.ObjectImpl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException ;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import java.rmi.server.RMIClassLoader;

import com.sun.corba.se.impl.orbutil.GetPropertyAction;


/**
 * Base class from which all RMI-IIOP stubs must inherit.
 * <p>
 *  所有RMI-IIOP存根必须从其继承的基类。
 * 
 */
public abstract class Stub extends ObjectImpl
    implements java.io.Serializable {

    private static final long serialVersionUID = 1087775603798577179L;

    // This can only be set at object construction time (no sync necessary).
    private transient StubDelegate stubDelegate = null;
    private static Class stubDelegateClass = null;
    private static final String StubClassKey = "javax.rmi.CORBA.StubClass";

    static {
        Object stubDelegateInstance = createDelegate(StubClassKey);
        if (stubDelegateInstance != null)
            stubDelegateClass = stubDelegateInstance.getClass();
    }


    /**
     * Returns a hash code value for the object which is the same for all stubs
     * that represent the same remote object.
     * <p>
     *  返回对于表示同一远程对象的所有存根都相同的对象的哈希代码值。
     * 
     * 
     * @return the hash code value.
     */
    public int hashCode() {

        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        if (stubDelegate != null) {
            return stubDelegate.hashCode(this);
        }

        return 0;
    }

    /**
     * Compares two stubs for equality. Returns <code>true</code> when used to compare stubs
     * that represent the same remote object, and <code>false</code> otherwise.
     * <p>
     *  比较两个存根以实现相等。当用于比较表示相同远程对象的存根时,返回<code> true </code>,否则返回<code> false </code>。
     * 
     * 
     * @param obj the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the <code>obj</code>
     *          argument; <code>false</code> otherwise.
     */
    public boolean equals(java.lang.Object obj) {

        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        if (stubDelegate != null) {
            return stubDelegate.equals(this, obj);
        }

        return false;
    }

    /**
     * Returns a string representation of this stub. Returns the same string
     * for all stubs that represent the same remote object.
     * <p>
     *  返回此存根的字符串表示形式。为表示同一远程对象的所有存根返回相同的字符串。
     * 
     * 
     * @return a string representation of this stub.
     */
    public String toString() {


        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        String ior;
        if (stubDelegate != null) {
            ior = stubDelegate.toString(this);
            if (ior == null) {
                return super.toString();
            } else {
                return ior;
            }
        }
        return super.toString();
    }

    /**
     * Connects this stub to an ORB. Required after the stub is deserialized
     * but not after it is demarshalled by an ORB stream. If an unconnected
     * stub is passed to an ORB stream for marshalling, it is implicitly
     * connected to that ORB. Application code should not call this method
     * directly, but should call the portable wrapper method
     * {@link javax.rmi.PortableRemoteObject#connect}.
     * <p>
     *  将此存根连接到ORB。在存根被反序列化之后但不是在被ORB流分解之后,是必需的。如果未连接的存根传递到ORB流进行编组,则它将隐式连接到该ORB。
     * 应用程序代码不应该直接调用此方法,而应调用便携式换行器方法{@link javax.rmi.PortableRemoteObject#connect}。
     * 
     * 
     * @param orb the ORB to connect to.
     * @exception RemoteException if the stub is already connected to a different
     * ORB, or if the stub does not represent an exported remote or local object.
     */
    public void connect(ORB orb) throws RemoteException {

        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        if (stubDelegate != null) {
            stubDelegate.connect(this, orb);
        }

    }

    /**
     * Serialization method to restore the IOR state.
     * <p>
     *  序列化方法来恢复IOR状态。
     * 
     */
    private void readObject(java.io.ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        if (stubDelegate != null) {
            stubDelegate.readObject(this, stream);
        }

    }

    /**
     * Serialization method to save the IOR state.
     * <p>
     *  序列化方法来保存IOR状态。
     * 
     * 
     * @serialData The length of the IOR type ID (int), followed by the IOR type ID
     * (byte array encoded using ISO8859-1), followed by the number of IOR profiles
     * (int), followed by the IOR profiles.  Each IOR profile is written as a
     * profile tag (int), followed by the length of the profile data (int), followed
     * by the profile data (byte array).
     */
    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {

        if (stubDelegate == null) {
            setDefaultDelegate();
        }

        if (stubDelegate != null) {
            stubDelegate.writeObject(this, stream);
        }
    }

    private void setDefaultDelegate() {
        if (stubDelegateClass != null) {
            try {
                 stubDelegate = (javax.rmi.CORBA.StubDelegate) stubDelegateClass.newInstance();
            } catch (Exception ex) {
            // what kind of exception to throw
            // delegate not set therefore it is null and will return default
            // values
            }
        }
    }

    // Same code as in PortableRemoteObject. Can not be shared because they
    // are in different packages and the visibility needs to be package for
    // security reasons. If you know a better solution how to share this code
    // then remove it from PortableRemoteObject. Also in Util.java
    private static Object createDelegate(String classKey) {
        String className = (String)
            AccessController.doPrivileged(new GetPropertyAction(classKey));
        if (className == null) {
            Properties props = getORBPropertiesFile();
            if (props != null) {
                className = props.getProperty(classKey);
            }
        }

        if (className == null) {
            return new com.sun.corba.se.impl.javax.rmi.CORBA.StubDelegateImpl();
        }

        try {
            return loadDelegateClass(className).newInstance();
        } catch (ClassNotFoundException ex) {
            INITIALIZE exc = new INITIALIZE( "Cannot instantiate " + className);
            exc.initCause( ex ) ;
            throw exc ;
        } catch (Exception ex) {
            INITIALIZE exc = new INITIALIZE( "Error while instantiating" + className);
            exc.initCause( ex ) ;
            throw exc ;
        }

    }

    private static Class loadDelegateClass( String className )  throws ClassNotFoundException
    {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            return Class.forName(className, false, loader);
        } catch (ClassNotFoundException e) {
            // ignore, then try RMIClassLoader
        }

        try {
            return RMIClassLoader.loadClass(className);
        } catch (MalformedURLException e) {
            String msg = "Could not load " + className + ": " + e.toString();
            ClassNotFoundException exc = new ClassNotFoundException( msg ) ;
            throw exc ;
        }
    }

    /**
     * Load the orb.properties file.
     * <p>
     *  加载orb.properties文件。
     */
    private static Properties getORBPropertiesFile () {
        return (Properties) AccessController.doPrivileged(new GetORBPropertiesFileAction());
    }

}
