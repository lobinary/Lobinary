/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.security;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import javax.security.auth.Subject;
import javax.security.auth.SubjectDomainCombiner;

/**
 * <p>This class represents an extension to the {@link SubjectDomainCombiner}
 * and is used to add a new {@link ProtectionDomain}, comprised of a null
 * codesource/signers and an empty permission set, to the access control
 * context with which this combiner is combined.</p>
 *
 * <p>When the {@link #combine} method is called the {@link ProtectionDomain}
 * is augmented with the permissions granted to the set of principals present
 * in the supplied {@link Subject}.</p>
 * <p>
 *  <p>此类表示对{@link SubjectDomainCombiner}的扩展,用于向访问控制上下文添加一个新的{@link ProtectionDomain},包括空的代码源/签名者和空权限集,该
 * 组合器</p>。
 * 
 *  <p>当调用{@link #combine}方法时,{@link ProtectionDomain}会增加授予给提供的{@link Subject}中的一组主体的权限。</p>
 * 
 */
public class JMXSubjectDomainCombiner extends SubjectDomainCombiner {

    public JMXSubjectDomainCombiner(Subject s) {
        super(s);
    }

    public ProtectionDomain[] combine(ProtectionDomain[] current,
                                      ProtectionDomain[] assigned) {
        // Add a new ProtectionDomain with the null codesource/signers, and
        // the empty permission set, to the end of the array containing the
        // 'current' protections domains, i.e. the ones that will be augmented
        // with the permissions granted to the set of principals present in
        // the supplied subject.
        //
        ProtectionDomain[] newCurrent;
        if (current == null || current.length == 0) {
            newCurrent = new ProtectionDomain[1];
            newCurrent[0] = pdNoPerms;
        } else {
            newCurrent = new ProtectionDomain[current.length + 1];
            for (int i = 0; i < current.length; i++) {
                newCurrent[i] = current[i];
            }
            newCurrent[current.length] = pdNoPerms;
        }
        return super.combine(newCurrent, assigned);
    }

    /**
     * A null CodeSource.
     * <p>
     *  一个null CodeSource。
     * 
     */
    private static final CodeSource nullCodeSource =
        new CodeSource(null, (java.security.cert.Certificate[]) null);

    /**
     * A ProtectionDomain with a null CodeSource and an empty permission set.
     * <p>
     *  一个ProtectionDomain,具有一个空CodeSource和一个空的权限集。
     * 
     */
    private static final ProtectionDomain pdNoPerms =
        new ProtectionDomain(nullCodeSource, new Permissions());

    /**
     * Get the current AccessControlContext combined with the supplied subject.
     * <p>
     *  获取当前AccessControlContext结合提供的主题。
     * 
     */
    public static AccessControlContext getContext(Subject subject) {
        return new AccessControlContext(AccessController.getContext(),
                                        new JMXSubjectDomainCombiner(subject));
    }

    /**
     * Get the AccessControlContext of the domain combiner created with
     * the supplied subject, i.e. an AccessControlContext with the domain
     * combiner created with the supplied subject and where the caller's
     * context has been removed.
     * <p>
     *  获取使用提供的主题创建的域组合器的AccessControlContext,即具有使用提供的主题创建的域组合器的AccessControlContext,并且已删除了调用者的上下文。
     */
    public static AccessControlContext
        getDomainCombinerContext(Subject subject) {
        return new AccessControlContext(
            new AccessControlContext(new ProtectionDomain[0]),
            new JMXSubjectDomainCombiner(subject));
    }
}
