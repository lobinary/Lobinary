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
 */
package com.sun.org.apache.xml.internal.security.encryption;

/**
 * Constants
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
public interface XMLCipherParameters {

    String AES_128 =
        "http://www.w3.org/2001/04/xmlenc#aes128-cbc";

    String AES_256 =
        "http://www.w3.org/2001/04/xmlenc#aes256-cbc";

    String AES_192 =
        "http://www.w3.org/2001/04/xmlenc#aes192-cbc";

    String RSA_1_5 =
        "http://www.w3.org/2001/04/xmlenc#rsa-1_5";

    String RSA_OAEP =
        "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";

    String DIFFIE_HELLMAN =
        "http://www.w3.org/2001/04/xmlenc#dh";

    String TRIPLEDES_KEYWRAP =
        "http://www.w3.org/2001/04/xmlenc#kw-tripledes";

    String AES_128_KEYWRAP =
        "http://www.w3.org/2001/04/xmlenc#kw-aes128";

    String AES_256_KEYWRAP =
        "http://www.w3.org/2001/04/xmlenc#kw-aes256";

    String AES_192_KEYWRAP =
        "http://www.w3.org/2001/04/xmlenc#kw-aes192";

    String SHA1 =
        "http://www.w3.org/2000/09/xmldsig#sha1";

    String SHA256 =
        "http://www.w3.org/2001/04/xmlenc#sha256";

    String SHA512 =
        "http://www.w3.org/2001/04/xmlenc#sha512";

    String RIPEMD_160 =
        "http://www.w3.org/2001/04/xmlenc#ripemd160";

    String XML_DSIG =
        "http://www.w3.org/2000/09/xmldsig#";

    String N14C_XML =
        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

    String N14C_XML_CMMNTS =
        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";

    String EXCL_XML_N14C =
        "http://www.w3.org/2001/10/xml-exc-c14n#";

    String EXCL_XML_N14C_CMMNTS =
        "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
}
