/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.copyobject ;

/** Manager of ObjectCopier implementations used to support javax.rmi.CORBA.Util.copyObject(s).
 * This provides simple methods for registering all supported ObjectCopier factories.
 * A default copier is also supported, for use in contexts where no specific copier id
 * is available.
 * <p>
 *  这提供了注册所有支持的ObjectCopier工厂的简单方法。还支持默认复印机,用于没有特定复印机ID的上下文。
 * 
 */
public interface CopierManager
{
    /** Set the Id of the copier to use if no other copier has been set.
    /* <p>
     */
    void setDefaultId( int id ) ;

    /** Return the copier for the default copier id.  Throws a BAD_PARAM exception
     * if no default copier id has been set.
     * <p>
     *  如果没有设置默认复印机ID。
     * 
     */
    int getDefaultId() ;

    ObjectCopierFactory getObjectCopierFactory( int id ) ;

    ObjectCopierFactory getDefaultObjectCopierFactory() ;

    /** Register an ObjectCopierFactory under a particular id.  This can be retrieved
     * later by getObjectCopierFactory.
     * <p>
     *  后来被getObjectCopierFactory。
     */
    void registerObjectCopierFactory( ObjectCopierFactory factory, int id ) ;
}
