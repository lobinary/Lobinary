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

package javax.rmi;

import java.lang.reflect.Method ;

import org.omg.CORBA.INITIALIZE;
import javax.rmi.CORBA.Util;

import java.rmi.RemoteException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.net.MalformedURLException ;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.rmi.server.RMIClassLoader;

import com.sun.corba.se.impl.orbutil.GetPropertyAction;

/**
 * Server implementation objects may either inherit from
 * javax.rmi.PortableRemoteObject or they may implement a remote interface
 * and then use the exportObject method to register themselves as a server object.
 * The toStub method takes a server implementation and returns a stub that
 * can be used to access that server object.
 * The connect method makes a Remote object ready for remote communication.
 * The unexportObject method is used to deregister a server object, allowing it to become
 * available for garbage collection.
 * The narrow method takes an object reference or abstract interface type and
 * attempts to narrow it to conform to
 * the given interface. If the operation is successful the result will be an
 * object of the specified type, otherwise an exception will be thrown.
 * <p>
 *  服务器实现对象可以从javax.rmi.PortableRemoteObject继承,也可以实现远程接口,然后使用exportObject方法将自身注册为服务器对象。
 *  toStub方法接受服务器实现并返回可用于访问该服务器对象的存根。 connect方法使远程对象准备好进行远程通信。 unexportObject方法用于注销服务器对象,允许它可用于垃圾回收。
 *  narrow方法采用对象引用或抽象接口类型,并尝试将其缩小以符合给定的接口。如果操作成功,结果将是指定类型的对象,否则将抛出异常。
 * 
 */
public class PortableRemoteObject {

    private static final javax.rmi.CORBA.PortableRemoteObjectDelegate proDelegate;

    private static final String PortableRemoteObjectClassKey =
            "javax.rmi.CORBA.PortableRemoteObjectClass";

    static {
        proDelegate = (javax.rmi.CORBA.PortableRemoteObjectDelegate)
            createDelegate(PortableRemoteObjectClassKey);
    }

    /**
     * Initializes the object by calling <code>exportObject(this)</code>.
     * <p>
     *  通过调用<code> exportObject(this)</code>来初始化对象。
     * 
     * 
     * @exception RemoteException if export fails.
     */
    protected PortableRemoteObject() throws RemoteException {
        if (proDelegate != null) {
            PortableRemoteObject.exportObject((Remote)this);
        }
    }

    /**
     * Makes a server object ready to receive remote calls. Note
     * that subclasses of PortableRemoteObject do not need to call this
     * method, as it is called by the constructor.
     * <p>
     *  使服务器对象准备好接收远程调用。注意,PortableRemoteObject的子类不需要调用此方法,因为它是由构造函数调用的。
     * 
     * 
     * @param obj the server object to export.
     * @exception RemoteException if export fails.
     */
    public static void exportObject(Remote obj)
        throws RemoteException {

        // Let the delegate do everything, including error handling.
        if (proDelegate != null) {
            proDelegate.exportObject(obj);
        }
    }

    /**
     * Returns a stub for the given server object.
     * <p>
     *  返回给定服务器对象的存根。
     * 
     * 
     * @param obj the server object for which a stub is required. Must either be a subclass
     * of PortableRemoteObject or have been previously the target of a call to
     * {@link #exportObject}.
     * @return the most derived stub for the object.
     * @exception NoSuchObjectException if a stub cannot be located for the given server object.
     */
    public static Remote toStub (Remote obj)
        throws NoSuchObjectException {

        if (proDelegate != null) {
            return proDelegate.toStub(obj);
        }
        return null;
    }

    /**
     * Deregisters a server object from the runtime, allowing the object to become
     * available for garbage collection.
     * <p>
     *  从运行时注销一个服务器对象,允许该对象变为可用于垃圾回收。
     * 
     * 
     * @param obj the object to unexport.
     * @exception NoSuchObjectException if the remote object is not
     * currently exported.
     */
    public static void unexportObject(Remote obj)
        throws NoSuchObjectException {

        if (proDelegate != null) {
            proDelegate.unexportObject(obj);
        }

    }

    /**
     * Checks to ensure that an object of a remote or abstract interface type
     * can be cast to a desired type.
     * <p>
     * 检查以确保远程或抽象接口类型的对象可以转换为所需类型。
     * 
     * 
     * @param narrowFrom the object to check.
     * @param narrowTo the desired type.
     * @return an object which can be cast to the desired type.
     * @throws ClassCastException if narrowFrom cannot be cast to narrowTo.
     */
    public static java.lang.Object narrow ( java.lang.Object narrowFrom,
                                            java.lang.Class narrowTo)
        throws ClassCastException {

        if (proDelegate != null) {
            return proDelegate.narrow(narrowFrom, narrowTo);
        }
        return null;

    }

    /**
     * Makes a Remote object ready for remote communication. This normally
     * happens implicitly when the object is sent or received as an argument
     * on a remote method call, but in some circumstances it is useful to
     * perform this action by making an explicit call.  See the
     * {@link javax.rmi.CORBA.Stub#connect} method for more information.
     * <p>
     *  使远程对象准备好进行远程通信。这通常在将对象作为远程方法调用的参数发送或接收时隐式发生,但在某些情况下,通过显式调用来执行此操作很有用。
     * 有关详细信息,请参阅{@link javax.rmi.CORBA.Stub#connect}方法。
     * 
     * 
     * @param target the object to connect.
     * @param source a previously connected object.
     * @throws RemoteException if <code>source</code> is not connected
     * or if <code>target</code> is already connected to a different ORB than
     * <code>source</code>.
     */
    public static void connect (Remote target, Remote source)
        throws RemoteException {

        if (proDelegate != null) {
            proDelegate.connect(target, source);
        }

    }

    // Same code as in javax.rmi.CORBA.Util. Can not be shared because they
    // are in different packages and the visibility needs to be package for
    // security reasons. If you know a better solution how to share this code
    // then remove it from here.
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
            return new com.sun.corba.se.impl.javax.rmi.PortableRemoteObject();
        }

        try {
            return (Object) loadDelegateClass(className).newInstance();
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
     */
    private static Properties getORBPropertiesFile () {
        return (Properties) AccessController.doPrivileged(new GetORBPropertiesFileAction());
    }
}

class GetORBPropertiesFileAction implements PrivilegedAction {
    private boolean debug = false ;

    public GetORBPropertiesFileAction () {
    }

    private String getSystemProperty(final String name) {
        // This will not throw a SecurityException because this
        // class was loaded from rt.jar using the bootstrap classloader.
        String propValue = (String) AccessController.doPrivileged(
            new PrivilegedAction() {
                public java.lang.Object run() {
                    return System.getProperty(name);
                }
            }
        );

        return propValue;
    }

    private void getPropertiesFromFile( Properties props, String fileName )
    {
        try {
            File file = new File( fileName ) ;
            if (!file.exists())
                return ;

            FileInputStream in = new FileInputStream( file ) ;

            try {
                props.load( in ) ;
            } finally {
                in.close() ;
            }
        } catch (Exception exc) {
            if (debug)
                System.out.println( "ORB properties file " + fileName +
                    " not found: " + exc) ;
        }
    }

    public Object run()
    {
        Properties defaults = new Properties() ;

        String javaHome = getSystemProperty( "java.home" ) ;
        String fileName = javaHome + File.separator + "lib" + File.separator +
            "orb.properties" ;

        getPropertiesFromFile( defaults, fileName ) ;

        Properties results = new Properties( defaults ) ;

        String userHome = getSystemProperty( "user.home" ) ;
        fileName = userHome + File.separator + "orb.properties" ;

        getPropertiesFromFile( results, fileName ) ;
        return results ;
    }
}
