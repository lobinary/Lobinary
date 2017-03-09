/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.PortableServer.ServantLocatorPackage;

/**
 * The native type PortableServer::ServantLocator::Cookie is mapped
 * to java.lang.Object. A CookieHolder class is provided for passing
 * the Cookie type as an out parameter. The CookieHolder class
 * follows exactly the same pattern as the other holder classes
 * for basic types.
 * <p>
 *  本机类型PortableServer :: ServantLocator :: Cookie映射到java.lang.Object。
 * 提供了CookieHolder类,用于将Cookie类型作为输出参数传递。 CookieHolder类遵循与基本类型的其他holder类完全相同的模式。
 */

final public class CookieHolder implements org.omg.CORBA.portable.Streamable
{
    public java.lang.Object value;

    public CookieHolder() { }

    public CookieHolder(java.lang.Object initial) {
        value = initial;
    }

    public void _read( org.omg.CORBA.portable.InputStream is) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void _write( org.omg.CORBA.portable.OutputStream os) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.TypeCode _type() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
