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
package com.sun.org.apache.xml.internal.security.keys;

import java.io.PrintStream;
import java.security.PublicKey;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;

/**
 * Utility class for for <CODE>com.sun.org.apache.xml.internal.security.keys</CODE> package.
 *
 * <p>
 * 
 * @author $Author: coheigea $
 */
public class KeyUtils {

    private KeyUtils() {
        // no instantiation
    }

    /**
     * Method prinoutKeyInfo
     *
     * <p>
     *  用于<CODE> com.sun.org.apache.xml.internal.security.keys </CODE>包的实用程序类。
     * 
     * 
     * @param ki
     * @param os
     * @throws XMLSecurityException
     */
    public static void prinoutKeyInfo(KeyInfo ki, PrintStream os)
        throws XMLSecurityException {

        for (int i = 0; i < ki.lengthKeyName(); i++) {
            KeyName x = ki.itemKeyName(i);

            os.println("KeyName(" + i + ")=\"" + x.getKeyName() + "\"");
        }

        for (int i = 0; i < ki.lengthKeyValue(); i++) {
            KeyValue x = ki.itemKeyValue(i);
            PublicKey pk = x.getPublicKey();

            os.println("KeyValue Nr. " + i);
            os.println(pk);
        }

        for (int i = 0; i < ki.lengthMgmtData(); i++) {
            MgmtData x = ki.itemMgmtData(i);

            os.println("MgmtData(" + i + ")=\"" + x.getMgmtData() + "\"");
        }

        for (int i = 0; i < ki.lengthX509Data(); i++) {
            X509Data x = ki.itemX509Data(i);

            os.println("X509Data(" + i + ")=\"" + (x.containsCertificate()
                ? "Certificate " : "") + (x.containsIssuerSerial()
                ? "IssuerSerial " : "") + "\"");
        }
    }
}
