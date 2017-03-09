/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil;

import com.sun.corba.se.spi.orb.ORBVersion;
import com.sun.corba.se.spi.orb.ORB;

public abstract class RepositoryIdFactory
{
    private static final RepIdDelegator currentDelegator
        = new RepIdDelegator();

    /**
     * Returns the latest version RepositoryIdStrings instance
     * <p>
     *  返回最新版本的RepositoryIdStrings实例
     * 
     */
    public static RepositoryIdStrings getRepIdStringsFactory()
    {
        return currentDelegator;
    }

    /**
     * Returns the latest version RepositoryIdUtility instance
     * <p>
     *  返回最新版本的RepositoryIdUtility实例
     */
    public static RepositoryIdUtility getRepIdUtility()
    {
        return currentDelegator;
    }

}
