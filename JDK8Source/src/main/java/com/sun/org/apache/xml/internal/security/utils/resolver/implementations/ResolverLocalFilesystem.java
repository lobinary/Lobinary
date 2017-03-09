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
package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;

/**
 * A simple ResourceResolver for requests into the local filesystem.
 * <p>
 *  一个简单的ResourceResolver,用于请求进入本地文件系统。
 * 
 */
public class ResolverLocalFilesystem extends ResourceResolverSpi {

    private static final int FILE_URI_LENGTH = "file:/".length();

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ResolverLocalFilesystem.class.getName());

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     * 
     */
    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {
        try {
            // calculate new URI
            URI uriNew = getNewURI(context.uriToResolve, context.baseUri);

            String fileName =
                ResolverLocalFilesystem.translateUriToFilename(uriNew.toString());
            FileInputStream inputStream = new FileInputStream(fileName);
            XMLSignatureInput result = new XMLSignatureInput(inputStream);

            result.setSourceURI(uriNew.toString());

            return result;
        } catch (Exception e) {
            throw new ResourceResolverException("generic.EmptyMessage", e, context.attr, context.baseUri);
        }
    }

    /**
     * Method translateUriToFilename
     *
     * <p>
     *  方法translateUriToFilename
     * 
     * 
     * @param uri
     * @return the string of the filename
     */
    private static String translateUriToFilename(String uri) {

        String subStr = uri.substring(FILE_URI_LENGTH);

        if (subStr.indexOf("%20") > -1) {
            int offset = 0;
            int index = 0;
            StringBuilder temp = new StringBuilder(subStr.length());
            do {
                index = subStr.indexOf("%20",offset);
                if (index == -1) {
                    temp.append(subStr.substring(offset));
                } else {
                    temp.append(subStr.substring(offset, index));
                    temp.append(' ');
                    offset = index + 3;
                }
            } while(index != -1);
            subStr = temp.toString();
        }

        if (subStr.charAt(1) == ':') {
            // we're running M$ Windows, so this works fine
            return subStr;
        }
        // we're running some UNIX, so we have to prepend a slash
        return "/" + subStr;
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     */
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            return false;
        }

        if (context.uriToResolve.equals("") || (context.uriToResolve.charAt(0)=='#') ||
            context.uriToResolve.startsWith("http:")) {
            return false;
        }

        try {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I was asked whether I can resolve " + context.uriToResolve);
            }

            if (context.uriToResolve.startsWith("file:") || context.baseUri.startsWith("file:")) {
                if (log.isLoggable(java.util.logging.Level.FINE)) {
                    log.log(java.util.logging.Level.FINE, "I state that I can resolve " + context.uriToResolve);
                }
                return true;
            }
        } catch (Exception e) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, e.getMessage(), e);
            }
        }

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "But I can't");
        }

        return false;
    }

    private static URI getNewURI(String uri, String baseURI) throws URISyntaxException {
        URI newUri = null;
        if (baseURI == null || "".equals(baseURI)) {
            newUri = new URI(uri);
        } else {
            newUri = new URI(baseURI).resolve(uri);
        }

        // if the URI contains a fragment, ignore it
        if (newUri.getFragment() != null) {
            URI uriNewNoFrag =
                new URI(newUri.getScheme(), newUri.getSchemeSpecificPart(), null);
            return uriNewNoFrag;
        }
        return newUri;
    }
}
