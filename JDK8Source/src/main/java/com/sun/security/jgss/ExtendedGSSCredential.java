/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.jgss;

import org.ietf.jgss.*;

/**
 * The extended GSSCredential interface for supporting additional
 * functionalities not defined by {@code org.ietf.jgss.GSSCredential}.
 * <p>
 *  扩展的GSSCredential接口,用于支持未由{@code org.ietf.jgss.GSSCredential}定义的其他功能。
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public interface ExtendedGSSCredential extends GSSCredential {
    /**
     * Impersonates a principal. In Kerberos, this can be implemented
     * using the Microsoft S4U2self extension.
     * <p>
     * A {@link GSSException#NO_CRED GSSException.NO_CRED} will be thrown if the
     * impersonation fails. A {@link GSSException#FAILURE GSSException.FAILURE}
     * will be  thrown if the impersonation method is not available to this
     * credential object.
     * <p>
     *  假冒校长。在Kerberos中,这可以使用Microsoft S4U2self扩展实现。
     * <p>
     *  如果模拟失败,将抛出{@link GSSException#NO_CRED GSSException.NO_CRED}。
     * 
     * @param name the name of the principal to impersonate
     * @return a credential for that principal
     * @throws GSSException  containing the following
     * major error codes:
     *   {@link GSSException#NO_CRED GSSException.NO_CRED}
     *   {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public GSSCredential impersonate(GSSName name) throws GSSException;
}
