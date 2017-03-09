/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

import java.security.BasicPermission;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

/**
 * This permission represents "trust" in a signer or codebase.
 * <p>
 * MBeanTrustPermission contains a target name but no actions list.
 * A single target name, "register", is defined for this permission.
 * The target "*" is also allowed, permitting "register" and any future
 * targets that may be defined.
 * Only the null value or the empty string are allowed for the action
 * to allow the policy object to create the permissions specified in
 * the policy file.
 * <p>
 * If a signer, or codesource is granted this permission, then it is
 * considered a trusted source for MBeans. Only MBeans from trusted
 * sources may be registered in the MBeanServer.
 *
 * <p>
 *  此权限表示签名者或代码库中的"信任"。
 * <p>
 *  MBeanTrustPermission包含目标名称,但没有操作列表。为此权限定义了单个目标名称"register"。目标"*"也是允许的,允许"注册"以及可以定义的任何未来目标。
 * 对于操作,只允许空值或空字符串,以允许策略对象创建策略文件中指定的权限。
 * <p>
 *  如果签署者或codesource被授予此权限,那么它被认为是MBeans的可信源。只有来自受信任来源的MBean可能会在MBeanServer中注册。
 * 
 * @since 1.5
 */
public class MBeanTrustPermission extends BasicPermission {

    private static final long serialVersionUID = -2952178077029018140L;

    /** <p>Create a new MBeanTrustPermission with the given name.</p>
        <p>This constructor is equivalent to
        <code>MBeanTrustPermission(name,null)</code>.</p>
    /* <p>
    /* 
    /* 
        @param name the name of the permission. It must be
        "register" or "*" for this permission.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is neither
     * "register" nor "*".
     */
    public MBeanTrustPermission(String name) {
        this(name, null);
    }

    /** <p>Create a new MBeanTrustPermission with the given name.</p>
    /* <p>
    /*  <p>此构造函数等效于<code> MBeanTrustPermission(name,null)</code>。</p>
    /* 
    /* 
        @param name the name of the permission. It must be
        "register" or "*" for this permission.
        @param actions the actions for the permission.  It must be
        null or <code>""</code>.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is neither
     * "register" nor "*"; or if <code>actions</code> is a non-null
     * non-empty string.
     */
    public MBeanTrustPermission(String name, String actions) {
        super(name, actions);
        validate(name,actions);
    }

    private static void validate(String name, String actions) {
        /* Check that actions is a null empty string */
        if (actions != null && actions.length() > 0) {
            throw new IllegalArgumentException("MBeanTrustPermission actions must be null: " +
                                               actions);
        }

        if (!name.equals("register") && !name.equals("*")) {
            throw new IllegalArgumentException("MBeanTrustPermission: Unknown target name " +
                                               "[" + name + "]");
        }
    }

    private void readObject(ObjectInputStream in)
         throws IOException, ClassNotFoundException {

        // Reading private fields of base class
        in.defaultReadObject();
        try {
            validate(super.getName(),super.getActions());
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }
}
