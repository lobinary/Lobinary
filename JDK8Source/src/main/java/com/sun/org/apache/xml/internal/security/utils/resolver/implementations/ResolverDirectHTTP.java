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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;

/**
 * A simple ResourceResolver for HTTP requests. This class handles only 'pure'
 * HTTP URIs which means without a fragment. The Fragment handling is done by the
 * {@link ResolverFragment} class.
 * <BR>
 * If the user has a corporate HTTP proxy which is to be used, the usage can be
 * switched on by setting properties for the resolver:
 * <PRE>
 * resourceResolver.setProperty("http.proxy.host", "proxy.company.com");
 * resourceResolver.setProperty("http.proxy.port", "8080");
 *
 * // if we need a password for the proxy
 * resourceResolver.setProperty("http.proxy.username", "proxyuser3");
 * resourceResolver.setProperty("http.proxy.password", "secretca");
 * </PRE>
 *
 * <p>
 *  一个用于HTTP请求的简单ResourceResolver。此类仅处理"纯"HTTP URI,这意味着没有片段。 Fragment处理由{@link ResolverFragment}类完成。
 * <BR>
 *  如果用户具有要使用的公司HTTP代理,则可以通过设置解析器的属性来打开用法：
 * <PRE>
 *  resourceResolver.setProperty("http.proxy.host","proxy.company.com"); resourceResolver.setProperty("h
 * ttp.proxy.port","8080");。
 * 
 * //如果我们需要代理的密码resourceResolver.setProperty("http.proxy.username","proxyuser3"); resourceResolver.setPr
 * operty("http.proxy.password","secretca");。
 * </PRE>
 * 
 * @see <A HREF="http://www.javaworld.com/javaworld/javatips/jw-javatip42_p.html">Java Tip 42: Write Java apps that work with proxy-based firewalls</A>
 * @see <A HREF="https://docs.oracle.com/javase/1.4.2/docs/guide/net/properties.html">SUN J2SE docs for network properties</A>
 * @see <A HREF="http://metalab.unc.edu/javafaq/javafaq.html#proxy">The JAVA FAQ Question 9.5: How do I make Java work with a proxy server?</A>
 */
public class ResolverDirectHTTP extends ResourceResolverSpi {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ResolverDirectHTTP.class.getName());

    /** Field properties[] */
    private static final String properties[] = {
                                                 "http.proxy.host", "http.proxy.port",
                                                 "http.proxy.username", "http.proxy.password",
                                                 "http.basic.username", "http.basic.password"
                                               };

    /** Field HttpProxyHost */
    private static final int HttpProxyHost = 0;

    /** Field HttpProxyPort */
    private static final int HttpProxyPort = 1;

    /** Field HttpProxyUser */
    private static final int HttpProxyUser = 2;

    /** Field HttpProxyPass */
    private static final int HttpProxyPass = 3;

    /** Field HttpProxyUser */
    private static final int HttpBasicUser = 4;

    /** Field HttpProxyPass */
    private static final int HttpBasicPass = 5;

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    /**
     * Method resolve
     *
     * <p>
     * 
     * 
     * @param uri
     * @param baseURI
     *
     * @throws ResourceResolverException
     * @return
     * $todo$ calculate the correct URI from the attribute and the baseURI
     */
    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {
        try {

            // calculate new URI
            URI uriNew = getNewURI(context.uriToResolve, context.baseUri);
            URL url = uriNew.toURL();
            URLConnection urlConnection;
            urlConnection = openConnection(url);

            // check if Basic authentication is required
            String auth = urlConnection.getHeaderField("WWW-Authenticate");

            if (auth != null && auth.startsWith("Basic")) {
                // do http basic authentication
                String user =
                    engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpBasicUser]);
                String pass =
                    engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpBasicPass]);

                if ((user != null) && (pass != null)) {
                    urlConnection = openConnection(url);

                    String password = user + ":" + pass;
                    String encodedPassword = Base64.encode(password.getBytes("ISO-8859-1"));

                    // set authentication property in the http header
                    urlConnection.setRequestProperty("Authorization",
                                                     "Basic " + encodedPassword);
                }
            }

            String mimeType = urlConnection.getHeaderField("Content-Type");
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte buf[] = new byte[4096];
            int read = 0;
            int summarized = 0;

            while ((read = inputStream.read(buf)) >= 0) {
                baos.write(buf, 0, read);
                summarized += read;
            }

            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Fetched " + summarized + " bytes from URI " + uriNew.toString());
            }

            XMLSignatureInput result = new XMLSignatureInput(baos.toByteArray());

            result.setSourceURI(uriNew.toString());
            result.setMIMEType(mimeType);

            return result;
        } catch (URISyntaxException ex) {
            throw new ResourceResolverException("generic.EmptyMessage", ex, context.attr, context.baseUri);
        } catch (MalformedURLException ex) {
            throw new ResourceResolverException("generic.EmptyMessage", ex, context.attr, context.baseUri);
        } catch (IOException ex) {
            throw new ResourceResolverException("generic.EmptyMessage", ex, context.attr, context.baseUri);
        } catch (IllegalArgumentException e) {
            throw new ResourceResolverException("generic.EmptyMessage", e, context.attr, context.baseUri);
        }
    }

    private URLConnection openConnection(URL url) throws IOException {

        String proxyHostProp =
                engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpProxyHost]);
        String proxyPortProp =
                engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpProxyPort]);
        String proxyUser =
                engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpProxyUser]);
        String proxyPass =
                engineGetProperty(ResolverDirectHTTP.properties[ResolverDirectHTTP.HttpProxyPass]);

        Proxy proxy = null;
        if ((proxyHostProp != null) && (proxyPortProp != null)) {
            int port = Integer.parseInt(proxyPortProp);
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHostProp, port));
        }

        URLConnection urlConnection;
        if (proxy != null) {
            urlConnection = url.openConnection(proxy);

            if ((proxyUser != null) && (proxyPass != null)) {
                String password = proxyUser + ":" + proxyPass;
                String authString = "Basic " + Base64.encode(password.getBytes("ISO-8859-1"));

                urlConnection.setRequestProperty("Proxy-Authorization", authString);
            }
        } else {
            urlConnection = url.openConnection();
        }

        return urlConnection;
    }

    /**
     * We resolve http URIs <I>without</I> fragment...
     *
     * <p>
     *  方法解析
     * 
     * 
     * @param uri
     * @param baseURI
     * @return true if can be resolved
     */
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "quick fail, uri == null");
            }
            return false;
        }

        if (context.uriToResolve.equals("") || (context.uriToResolve.charAt(0)=='#')) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "quick fail for empty URIs and local ones");
            }
            return false;
        }

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I was asked whether I can resolve " + context.uriToResolve);
        }

        if (context.uriToResolve.startsWith("http:") ||
            (context.baseUri != null && context.baseUri.startsWith("http:") )) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I state that I can resolve " + context.uriToResolve);
            }
            return true;
        }

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I state that I can't resolve " + context.uriToResolve);
        }

        return false;
    }

    /**
     * @inheritDoc
     * <p>
     *  我们解析http URIs <I>,而没有</I>片段...
     * 
     */
    public String[] engineGetPropertyKeys() {
        return ResolverDirectHTTP.properties.clone();
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
