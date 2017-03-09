/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.security.*;

/**
 * This class controls the use of dynamic proxies.
 * A DynamicAccessPermission contains a name (also referred to as a "target name") but
 * no actions list; you either have the named permission
 * or you don't.
 *
 * <p>
 *  这个类控制动态代理的使用。 DynamicAccessPermission包含一个名称(也称为"目标名称"),但没有动作列表;你有命名的权限或你不。
 * 
 */

public final class DynamicAccessPermission extends BasicPermission {
    //private static final long serialVersionUID = -8343910153355041693L;

    /**
     * Creates a new DynamicAccessPermission with the specified name.
     * <p>
     *  创建具有指定名称的新DynamicAccessPermission。
     * 
     * 
     * @param name the name of the DynamicAccessPermission.
     */
    public DynamicAccessPermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new DynamicAccessPermission object with the specified name.
     * The name is the symbolic name of the DynamicAccessPermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     *  创建具有指定名称的新DynamicAccessPermission对象。名称是DynamicAccessPermission的符号名称,操作String目前未使用,应为null。
     * 
     * @param name the name of the DynamicAccessPermission.
     * @param actions should be null.
     */
    public DynamicAccessPermission(String name, String actions)
    {
        super(name, actions);
    }
}
