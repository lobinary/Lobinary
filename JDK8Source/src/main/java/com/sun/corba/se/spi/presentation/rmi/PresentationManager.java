/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.presentation.rmi ;

import java.util.Map ;

import java.lang.reflect.Method ;
import java.lang.reflect.InvocationHandler ;

import javax.rmi.CORBA.Tie ;

import com.sun.corba.se.spi.orb.ORB ;
import com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory ;


/** Provides access to RMI-IIOP stubs and ties.
 * Any style of stub and tie generation may be used.
 * This includes compiler generated stubs and runtime generated stubs
 * as well as compiled and reflective ties.  There is normally
 * only one instance of this interface per VM.  The instance
 * is obtained from the static method
 * com.sun.corba.se.spi.orb.ORB.getPresentationManager.
 * <p>
 * Note that
 * the getClassData and getDynamicMethodMarshaller methods
 * maintain caches to avoid redundant computation.
 * <p>
 *  可以使用任何形式的存根和连线生成。这包括编译器生成的存根和运行时生成的存根以及编译和反射关系。每个VM通常只有一个此接口的实例。
 * 该实例从静态方法com.sun.corba.se.spi.orb.ORB.getPresentationManager获取。
 * <p>
 *  注意,getClassData和getDynamicMethodMarshaller方法维护缓存以避免冗余计算。
 * 
 */
public interface PresentationManager
{
    /** Creates StubFactory and Tie instances.
    /* <p>
     */
    public interface StubFactoryFactory
    {
        /** Return the standard name of a stub (according to the RMI-IIOP specification
         * and rmic).  This is needed so that the name of a stub is known for
         * standalone clients of the app server.
         * <p>
         *  和rmic)。这是必需的,以便为应用服务器的独立客户端知道存根的名称。
         * 
         */
        String getStubName( String className ) ;

        /** Create a stub factory for stubs for the interface whose type is given by
         * className.  className may identify either an IDL interface or an RMI-IIOP
         * interface.
         * <p>
         *  班级名称。 className可以标识IDL接口或RMI-IIOP接口。
         * 
         * 
         * @param className The name of the remote interface as a Java class name.
         * @param isIDLStub True if className identifies an IDL stub, else false.
         * @param remoteCodeBase The CodeBase to use for loading Stub classes, if
         * necessary (may be null or unused).
         * @param expectedClass The expected stub type (may be null or unused).
         * @param classLoader The classLoader to use (may be null).
         */
        PresentationManager.StubFactory createStubFactory( String className,
            boolean isIDLStub, String remoteCodeBase, Class expectedClass,
            ClassLoader classLoader);

        /** Return a Tie for the given class.
        /* <p>
         */
        Tie getTie( Class cls ) ;

        /** Return whether or not this StubFactoryFactory creates StubFactory
         * instances that create dynamic stubs and ties.  At the top level,
         * true indicates that rmic -iiop is not needed for generating stubs
         * or ties.
         * <p>
         *  创建动态存根和关系的实例。在顶层,true表示不需要rmic -iiop来生成存根或关系。
         * 
         */
        boolean createsDynamicStubs() ;
    }

    /** Creates the actual stub needed for RMI-IIOP remote
     * references.
     * <p>
     *  参考。
     * 
     */
    public interface StubFactory
    {
        /** Create a new dynamic stub.  It has the type that was
         * used to create this factory.
         * <p>
         *  用于创建这个工厂。
         * 
         */
        org.omg.CORBA.Object makeStub() ;

        /** Return the repository ID information for all Stubs
         * created by this stub factory.
         * <p>
         *  由这个存根工厂创建。
         * 
         */
        String[] getTypeIds() ;
    }

    public interface ClassData
    {
        /** Get the class used to create this ClassData instance
        /* <p>
         */
        Class getMyClass() ;

        /** Get the IDLNameTranslator for the class used to create
         * this ClassData instance.
         * <p>
         *  这个ClassData实例。
         * 
         */
        IDLNameTranslator getIDLNameTranslator() ;

        /** Return the array of repository IDs for all of the remote
         * interfaces implemented by this class.
         * <p>
         *  这个类实现的接口。
         * 
         */
        String[] getTypeIds() ;

        /** Get the InvocationHandlerFactory that is used to create
         * an InvocationHandler for dynamic stubs of the type of the
         * ClassData.
         * <p>
         *  用于类数据的动态存根的InvocationHandler。
         * 
         */
        InvocationHandlerFactory getInvocationHandlerFactory() ;

        /** Get the dictionary for this ClassData instance.
         * This is used to hold class-specific information for a Class
         * in the class data.  This avoids the need to create other
         * caches for accessing the information.
         * <p>
         *  这用于保存类数据中类的特定于类的信息。这避免了创建用于访问信息的其它高速缓存的需要。
         * 
         */
        Map getDictionary() ;
    }

    /** Get the ClassData for a particular class.
     * This class may be an implementation class, in which
     * case the IDLNameTranslator handles all Remote interfaces implemented by
     * the class.  If the class implements more than one remote interface, and not
     * all of the remote interfaces are related by inheritance, then the type
     * IDs have the implementation class as element 0.
     * <p>
     * 这个类可能是一个实现类,在这种情况下,IDLNameTranslator处理由类实现的所有远程接口。如果类实现了多个远程接口,而不是所有的远程接口都通过继承关联,那么类型ID的实现类为元素0。
     * 
     */
    ClassData getClassData( Class cls ) ;

    /** Given a particular method, return a DynamicMethodMarshaller
     * for that method.  This is used for dynamic stubs and ties.
     * <p>
     *  为该方法。这用于动态存根和关系。
     * 
     */
    DynamicMethodMarshaller getDynamicMethodMarshaller( Method method ) ;

    /** Return the registered StubFactoryFactory.
    /* <p>
     */
    StubFactoryFactory getStubFactoryFactory( boolean isDynamic ) ;

    /** Register the StubFactoryFactory.  Note that
     * a static StubFactoryFactory is always required for IDL.  The
     * dynamic stubFactoryFactory is optional.
     * <p>
     *  IDL总是需要一个静态StubFactoryFactory。动态stubFactoryFactory是可选的。
     * 
     */
    void setStubFactoryFactory( boolean isDynamic, StubFactoryFactory sff ) ;

    /** Equivalent to getStubFactoryFactory( true ).getTie( null ).
     * Provided for compatibility with earlier versions of PresentationManager
     * as used in the app server.  The class argument is ignored in
     * the dynamic case, so this is safe.
     * <p>
     *  用于与应用程序服务器中使用的PresentationManager早期版本兼容。在动态情况下,类参数被忽略,因此这是安全的。
     * 
     */
    Tie getTie() ;

    /** Returns the value of the com.sun.CORBA.ORBUseDynamicStub
     * property.
     * <p>
     *  属性。
     */
    boolean useDynamicStubs() ;
}
