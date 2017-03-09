/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.signature;

/**
 * Thrown by {@link com.sun.org.apache.xml.internal.security.signature.SignedInfo#verify()} when
 * testing the signature fails because of uninitialized
 * {@link com.sun.org.apache.xml.internal.security.signature.Reference}s.
 *
 * <p>
 *  由于未初始化的{@link com.sun.org.apache.xml.internal)测试签名失败时,由{@link com.sun.org.apache.xml.internal.security.signature.SignedInfo#verify()}
 * 引发。
 *  security.signature.Reference}。
 * 
 * 
 * @author Christian Geuer-Pollmann
 * @see ReferenceNotInitializedException
 */
public class MissingResourceFailureException extends XMLSignatureException {

    /**
     *
     * <p>
     */
    private static final long serialVersionUID = 1L;

    /** Field uninitializedReference */
    private Reference uninitializedReference = null;

    /**
     * MissingKeyResourceFailureException constructor.
     * <p>
     *  MissingKeyResourceFailureException构造函数。
     * 
     * 
     * @param msgID
     * @param reference
     * @see #getReference
     */
    public MissingResourceFailureException(String msgID, Reference reference) {
        super(msgID);

        this.uninitializedReference = reference;
    }

    /**
     * Constructor MissingResourceFailureException
     *
     * <p>
     *  构造函数MissingResourceFailureException
     * 
     * 
     * @param msgID
     * @param exArgs
     * @param reference
     * @see #getReference
     */
    public MissingResourceFailureException(String msgID, Object exArgs[], Reference reference) {
        super(msgID, exArgs);

        this.uninitializedReference = reference;
    }

    /**
     * Constructor MissingResourceFailureException
     *
     * <p>
     *  构造函数MissingResourceFailureException
     * 
     * 
     * @param msgID
     * @param originalException
     * @param reference
     * @see #getReference
     */
    public MissingResourceFailureException(
        String msgID, Exception originalException, Reference reference
    ) {
        super(msgID, originalException);

        this.uninitializedReference = reference;
    }

    /**
     * Constructor MissingResourceFailureException
     *
     * <p>
     *  构造函数MissingResourceFailureException
     * 
     * 
     * @param msgID
     * @param exArgs
     * @param originalException
     * @param reference
     * @see #getReference
     */
    public MissingResourceFailureException(
        String msgID, Object exArgs[], Exception originalException, Reference reference
    ) {
        super(msgID, exArgs, originalException);

        this.uninitializedReference = reference;
    }

    /**
     * used to set the uninitialized {@link com.sun.org.apache.xml.internal.security.signature.Reference}
     *
     * <p>
     *  用于设置未初始化的{@link com.sun.org.apache.xml.internal.security.signature.Reference}
     * 
     * 
     * @param reference the Reference object
     * @see #getReference
     */
    public void setReference(Reference reference) {
        this.uninitializedReference = reference;
    }

    /**
     * used to get the uninitialized {@link com.sun.org.apache.xml.internal.security.signature.Reference}
     *
     * This allows to supply the correct {@link com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput}
     * to the {@link com.sun.org.apache.xml.internal.security.signature.Reference} to try again verification.
     *
     * <p>
     *  用于获取未初始化的{@link com.sun.org.apache.xml.internal.security.signature.Reference}
     * 
     * 这允许将正确的{@link com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput}提供给{@link com.sun.org.apache.xml.internal.security.signature.Reference}
     * 
     * @return the Reference object
     * @see #setReference
     */
    public Reference getReference() {
        return this.uninitializedReference;
    }
}
