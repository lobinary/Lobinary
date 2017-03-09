/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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
package org.omg.PortableServer.portable;

import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POA;

/**
 * The portability package contains interfaces and classes
 * that are designed for and intended to be used by ORB
 * implementor. It exposes the publicly defined APIs that
 * are used to connect stubs and skeletons to the ORB.
 * The Delegate interface provides the ORB vendor specific
 * implementation of PortableServer::Servant.
 * Conformant to spec CORBA V2.3.1, ptc/00-01-08.pdf
 * <p>
 *  可移植包包含为ORB实现者设计并打算供其使用的接口和类。它公开了用于将存根和骨架连接到ORB的公开定义的API。
 * 代理接口提供了ORB供应商特定的PortableServer :: Servant实现。符合规格CORBA V2.3.1,ptc / 00-01-08.pdf。
 * 
 */
public interface Delegate {
/**
 * Convenience method that returns the instance of the ORB
 * currently associated with the Servant.
 * <p>
 *  方便方法返回当前与服务方相关联的ORB的实例。
 * 
 * 
 * @param Self the servant.
 * @return ORB associated with the Servant.
 */
    org.omg.CORBA.ORB orb(Servant Self);

/**
 * This allows the servant to obtain the object reference for
 * the target CORBA Object it is incarnating for that request.
 * <p>
 *  这允许服务方获得其为该请求所体现的目标CORBA对象的对象引用。
 * 
 * 
 * @param Self the servant.
 * @return Object reference associated with the request.
 */
    org.omg.CORBA.Object this_object(Servant Self);

/**
 * The method _poa() is equivalent to
 * calling PortableServer::Current:get_POA.
 * <p>
 *  方法_poa()等效于调用PortableServer :: Current：get_POA。
 * 
 * 
 * @param Self the servant.
 * @return POA associated with the servant.
 */
    POA poa(Servant Self);

/**
 * The method _object_id() is equivalent
 * to calling PortableServer::Current::get_object_id.
 * <p>
 *  方法_object_id()等效于调用PortableServer :: Current :: get_object_id。
 * 
 * 
 * @param Self the servant.
 * @return ObjectId associated with this servant.
 */
    byte[] object_id(Servant Self);

/**
 * The default behavior of this function is to return the
 * root POA from the ORB instance associated with the servant.
 * <p>
 *  此函数的默认行为是从与服务方关联的ORB实例返回根POA。
 * 
 * 
 * @param Self the servant.
 * @return POA associated with the servant class.
 */
    POA default_POA(Servant Self);

/**
 * This method checks to see if the specified repid is present
 * on the list returned by _all_interfaces() or is the
 * repository id for the generic CORBA Object.
 * <p>
 *  此方法检查指定的repid是否存在于_all_interfaces()返回的列表中,或者是通用CORBA对象的存储库ID。
 * 
 * 
 * @param Self the servant.
 * @param Repository_Id the repository_id to be checked in the
 *            repository list or against the id of generic CORBA
 *            object.
 * @return boolean indicating whether the specified repid is
 *         in the list or is same as that got generic CORBA
 *         object.
 */
    boolean is_a(Servant Self, String Repository_Id);

/**
 * This operation is used to check for the existence of the
 * Object.
 * <p>
 *  此操作用于检查对象的存在。
 * 
 * 
 * @param Self the servant.
 * @return boolean true to indicate that object does not exist,
 *                 and false otherwise.
 */
    boolean non_existent(Servant Self);
    //Simon And Ken Will Ask About Editorial Changes
    //In Idl To Java For The Following Signature.

/**
 * This operation returns an object in the Interface Repository
 * which provides type information that may be useful to a program.
 * <p>
 *  此操作在Interface Repository中返回一个对象,该对象提供可能对程序有用的类型信息。
 * 
 * @param self the servant.
 * @return type information corresponding to the object.
 */
    // The get_interface() method has been replaced by get_interface_def()
    //org.omg.CORBA.Object get_interface(Servant Self);

    org.omg.CORBA.Object get_interface_def(Servant self);
}
