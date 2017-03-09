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
package com.sun.org.apache.xml.internal.security.utils.resolver;

import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import org.w3c.dom.Attr;

/**
 * During reference validation, we have to retrieve resources from somewhere.
 *
 * <p>
 *  在参考验证期间,我们必须从某处检索资源。
 * 
 * 
 * @author $Author: coheigea $
 */
public abstract class ResourceResolverSpi {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ResourceResolverSpi.class.getName());

    /** Field properties */
    protected java.util.Map<String, String> properties = null;

    /**
     * Deprecated - used to carry state about whether resolution was being done in a secure fashion,
     * but was not thread safe, so the resolution information is now passed as parameters to methods.
     *
     * <p>
     *  已弃用 - 用于承载有关是否以安全方式完成解析,但不是线程安全的状态,因此解析度信息现在作为参数传递给方法。
     * 
     * 
     * @deprecated Secure validation flag is now passed to methods.
     */
    @Deprecated
    protected final boolean secureValidation = true;

    /**
     * This is the workhorse method used to resolve resources.
     *
     * <p>
     *  这是用于解决资源的主力方法。
     * 
     * 
     * @param uri
     * @param BaseURI
     * @return the resource wrapped around a XMLSignatureInput
     *
     * @throws ResourceResolverException
     *
     * @deprecated New clients should override {@link #engineResolveURI(ResourceResolverContext)}
     */
    @Deprecated
    public XMLSignatureInput engineResolve(Attr uri, String BaseURI)
        throws ResourceResolverException {
        throw new UnsupportedOperationException();
    }

    /**
     * This is the workhorse method used to resolve resources.
     * <p>
     *  这是用于解决资源的主力方法。
     * 
     * 
     * @param context Context to use to resolve resources.
     *
     * @return the resource wrapped around a XMLSignatureInput
     *
     * @throws ResourceResolverException
     */
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {
        // The default implementation, to preserve backwards compatibility in the
        // test cases, calls the old resolver API.
        return engineResolve(context.attr, context.baseUri);
    }

    /**
     * Method engineSetProperty
     *
     * <p>
     *  方法engineSetProperty
     * 
     * 
     * @param key
     * @param value
     */
    public void engineSetProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(key, value);
    }

    /**
     * Method engineGetProperty
     *
     * <p>
     *  方法engineGetProperty
     * 
     * 
     * @param key
     * @return the value of the property
     */
    public String engineGetProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

    /**
     *
     * <p>
     * 
     * @param newProperties
     */
    public void engineAddProperies(Map<String, String> newProperties) {
        if (newProperties != null && !newProperties.isEmpty()) {
            if (properties == null) {
                properties = new HashMap<String, String>();
            }
            properties.putAll(newProperties);
        }
    }

    /**
     * Tells if the implementation does can be reused by several threads safely.
     * It normally means that the implementation does not have any member, or there is
     * member change between engineCanResolve & engineResolve invocations. Or it maintains all
     * member info in ThreadLocal methods.
     * <p>
     * 告诉这个实现是否可以被多个线程安全地重用。它通常意味着实现没有任何成员,或者engineCanResolve&engineResolve调用之间有成员更改。
     * 或者它维护所有成员信息在ThreadLocal方法。
     * 
     */
    public boolean engineIsThreadSafe() {
        return false;
    }

    /**
     * This method helps the {@link ResourceResolver} to decide whether a
     * {@link ResourceResolverSpi} is able to perform the requested action.
     *
     * <p>
     *  此方法有助于{@link ResourceResolver}决定{@link ResourceResolverSpi}是否能够执行请求的操作。
     * 
     * 
     * @param uri
     * @param BaseURI
     * @return true if the engine can resolve the uri
     *
     * @deprecated See {@link #engineCanResolveURI(ResourceResolverContext)}
     */
    @Deprecated
    public boolean engineCanResolve(Attr uri, String BaseURI) {
        // This method used to be abstract, so any calls to "super" are bogus.
        throw new UnsupportedOperationException();
    }

    /**
     * This method helps the {@link ResourceResolver} to decide whether a
     * {@link ResourceResolverSpi} is able to perform the requested action.
     *
     * <p>New clients should override this method, and not override {@link #engineCanResolve(Attr, String)}
     * </p>
     * <p>
     *  此方法有助于{@link ResourceResolver}决定{@link ResourceResolverSpi}是否能够执行请求的操作。
     * 
     *  <p>新客户端应覆盖此方法,而不覆盖{@link #engineCanResolve(Attr,String)}
     * </p>
     * 
     * @param context Context in which to do resolution.
     * @return true if the engine can resolve the uri
     */
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        // To preserve backward compatibility with existing resolvers that might override the old method,
        // call the old deprecated API.
        return engineCanResolve( context.attr, context.baseUri );
    }

    /**
     * Method engineGetPropertyKeys
     *
     * <p>
     *  方法engineGetPropertyKeys
     * 
     * 
     * @return the property keys
     */
    public String[] engineGetPropertyKeys() {
        return new String[0];
    }

    /**
     * Method understandsProperty
     *
     * <p>
     *  方法understandsProperty
     * 
     * 
     * @param propertyToTest
     * @return true if understands the property
     */
    public boolean understandsProperty(String propertyToTest) {
        String[] understood = this.engineGetPropertyKeys();

        if (understood != null) {
            for (int i = 0; i < understood.length; i++) {
                if (understood[i].equals(propertyToTest)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Fixes a platform dependent filename to standard URI form.
     *
     * <p>
     *  将平台相关的文件名修复为标准URI形式。
     * 
     * @param str The string to fix.
     *
     * @return Returns the fixed URI string.
     */
    public static String fixURI(String str) {

        // handle platform dependent strings
        str = str.replace(java.io.File.separatorChar, '/');

        if (str.length() >= 4) {

            // str =~ /^\W:\/([^/])/ # to speak perl ;-))
            char ch0 = Character.toUpperCase(str.charAt(0));
            char ch1 = str.charAt(1);
            char ch2 = str.charAt(2);
            char ch3 = str.charAt(3);
            boolean isDosFilename = ((('A' <= ch0) && (ch0 <= 'Z'))
                && (ch1 == ':') && (ch2 == '/')
                && (ch3 != '/'));

            if (isDosFilename && log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Found DOS filename: " + str);
            }
        }

        // Windows fix
        if (str.length() >= 2) {
            char ch1 = str.charAt(1);

            if (ch1 == ':') {
                char ch0 = Character.toUpperCase(str.charAt(0));

                if (('A' <= ch0) && (ch0 <= 'Z')) {
                    str = "/" + str;
                }
            }
        }

        // done
        return str;
    }
}
