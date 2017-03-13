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
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)有关版权所有权的其他信息,请参阅随本作品分发的NOTICE文件。
 * ASF根据Apache许可证第20版("许可证")向您授予此文件;您不得使用此文件,除非符合许可证您可以在获取许可证的副本。
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 * 除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
package com.sun.org.apache.xml.internal.security.utils.resolver;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Attr;

/**
 * This Exception is thrown if something related to the
 * {@link com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver} goes wrong.
 *
 * <p>
 *  如果与{@link comsunorgapachexmlinternalsecurityutilsresolverResourceResolver}相关的问题出错,则会抛出此异常
 * 
 * 
 * @author $Author: coheigea $
 */
public class ResourceResolverException extends XMLSecurityException {

    private static final long serialVersionUID = 1L;

    private Attr uri = null;

    private String baseURI = null;

    /**
     * Constructor ResourceResolverException
     *
     * <p>
     *  构造方法ResourceResolverException
     * 
     * 
     * @param msgID
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, Attr uri, String baseURI) {
        super(msgID);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     * Constructor ResourceResolverException
     *
     * <p>
     *  构造方法ResourceResolverException
     * 
     * 
     * @param msgID
     * @param exArgs
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, Object exArgs[], Attr uri,
                                     String baseURI) {
        super(msgID, exArgs);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     * Constructor ResourceResolverException
     *
     * <p>
     *  构造方法ResourceResolverException
     * 
     * 
     * @param msgID
     * @param originalException
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, Exception originalException,
                                     Attr uri, String baseURI) {
        super(msgID, originalException);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     * Constructor ResourceResolverException
     *
     * <p>
     *  构造方法ResourceResolverException
     * 
     * 
     * @param msgID
     * @param exArgs
     * @param originalException
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, Object exArgs[],
                                     Exception originalException, Attr uri,
                                     String baseURI) {
        super(msgID, exArgs, originalException);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     *
     * <p>
     * 
     * @param uri
     */
    public void setURI(Attr uri) {
        this.uri = uri;
    }

    /**
     *
     * <p>
     * 
     * @return the uri
     */
    public Attr getURI() {
        return this.uri;
    }

    /**
     *
     * <p>
     * 
     * @param baseURI
     */
    public void setbaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     *
     * <p>
     * 
     * @return the baseURI
     */
    public String getbaseURI() {
        return this.baseURI;
    }

}
