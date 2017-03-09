/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.presentation.rmi;

import com.sun.corba.se.spi.presentation.rmi.PresentationManager;

import com.sun.corba.se.impl.util.Utility ;

public abstract class StubFactoryFactoryBase implements
    PresentationManager.StubFactoryFactory
{
    /**
     * Returns the stub classname for the given interface name.
     *
     * <p>
     *  返回给定接口名称的存根类名。
     * 
     * @param fullName fully qualified name remote class
     */
    public String getStubName(String fullName)
    {
        return Utility.stubName( fullName ) ;
    }
}
