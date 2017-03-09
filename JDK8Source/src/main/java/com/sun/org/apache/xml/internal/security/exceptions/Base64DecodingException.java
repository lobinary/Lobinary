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
package com.sun.org.apache.xml.internal.security.exceptions;

/**
 * This Exception is thrown if decoding of Base64 data fails.
 *
 * <p>
 *  如果Base64数据的解码失败,则抛出此异常。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class Base64DecodingException extends XMLSecurityException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor Base64DecodingException
     *
     * <p>
     *  构造函数Base64DecodingException
     * 
     */
    public Base64DecodingException() {
        super();
    }

    /**
     * Constructor Base64DecodingException
     *
     * <p>
     *  构造函数Base64DecodingException
     * 
     * 
     * @param msgID
     */
    public Base64DecodingException(String msgID) {
        super(msgID);
    }

    /**
     * Constructor Base64DecodingException
     *
     * <p>
     *  构造函数Base64DecodingException
     * 
     * 
     * @param msgID
     * @param exArgs
     */
    public Base64DecodingException(String msgID, Object exArgs[]) {
        super(msgID, exArgs);
    }

    /**
     * Constructor Base64DecodingException
     *
     * <p>
     *  构造函数Base64DecodingException
     * 
     * 
     * @param msgID
     * @param originalException
     */
    public Base64DecodingException(String msgID, Exception originalException) {
        super(msgID, originalException);
    }

    /**
     * Constructor Base64DecodingException
     *
     * <p>
     *  构造函数Base64DecodingException
     * 
     * @param msgID
     * @param exArgs
     * @param originalException
     */
    public Base64DecodingException(String msgID, Object exArgs[], Exception originalException) {
        super(msgID, exArgs, originalException);
    }

}
