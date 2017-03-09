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
package com.sun.org.apache.xml.internal.security.keys.storage;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.sun.org.apache.xml.internal.security.keys.storage.implementations.KeyStoreResolver;
import com.sun.org.apache.xml.internal.security.keys.storage.implementations.SingleCertificateResolver;

/**
 * This class collects customized resolvers for Certificates.
 * <p>
 *  此类收集证书的自定义解析器。
 * 
 */
public class StorageResolver {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(StorageResolver.class.getName());

    /** Field storageResolvers */
    private List<StorageResolverSpi> storageResolvers = null;

    /**
     * Constructor StorageResolver
     *
     * <p>
     *  构造函数StorageResolver
     * 
     */
    public StorageResolver() {}

    /**
     * Constructor StorageResolver
     *
     * <p>
     *  构造函数StorageResolver
     * 
     * 
     * @param resolver
     */
    public StorageResolver(StorageResolverSpi resolver) {
        this.add(resolver);
    }

    /**
     * Method addResolver
     *
     * <p>
     *  方法addResolver
     * 
     * 
     * @param resolver
     */
    public void add(StorageResolverSpi resolver) {
        if (storageResolvers == null) {
            storageResolvers = new ArrayList<StorageResolverSpi>();
        }
        this.storageResolvers.add(resolver);
    }

    /**
     * Constructor StorageResolver
     *
     * <p>
     *  构造函数StorageResolver
     * 
     * 
     * @param keyStore
     */
    public StorageResolver(KeyStore keyStore) {
        this.add(keyStore);
    }

    /**
     * Method addKeyStore
     *
     * <p>
     *  方法addKeyStore
     * 
     * 
     * @param keyStore
     */
    public void add(KeyStore keyStore) {
        try {
            this.add(new KeyStoreResolver(keyStore));
        } catch (StorageResolverException ex) {
            log.log(java.util.logging.Level.SEVERE, "Could not add KeyStore because of: ", ex);
        }
    }

    /**
     * Constructor StorageResolver
     *
     * <p>
     *  构造函数StorageResolver
     * 
     * 
     * @param x509certificate
     */
    public StorageResolver(X509Certificate x509certificate) {
        this.add(x509certificate);
    }

    /**
     * Method addCertificate
     *
     * <p>
     *  方法addCertificate
     * 
     * 
     * @param x509certificate
     */
    public void add(X509Certificate x509certificate) {
        this.add(new SingleCertificateResolver(x509certificate));
    }

    /**
     * Method getIterator
     * <p>
     *  方法getIterator
     * 
     * 
     * @return the iterator for the resolvers.
     */
    public Iterator<Certificate> getIterator() {
        return new StorageResolverIterator(this.storageResolvers.iterator());
    }

    /**
     * Class StorageResolverIterator
     * This iterates over all the Certificates found in all the resolvers.
     * <p>
     *  StorageResolverIterator类迭代所有解析器中找到的所有证书。
     * 
     */
    static class StorageResolverIterator implements Iterator<Certificate> {

        /** Field resolvers */
        Iterator<StorageResolverSpi> resolvers = null;

        /** Field currentResolver */
        Iterator<Certificate> currentResolver = null;

        /**
         * Constructor StorageResolverIterator
         *
         * <p>
         *  构造函数StorageResolverIterator
         * 
         * 
         * @param resolvers
         */
        public StorageResolverIterator(Iterator<StorageResolverSpi> resolvers) {
            this.resolvers = resolvers;
            currentResolver = findNextResolver();
        }

        /** @inheritDoc */
        public boolean hasNext() {
            if (currentResolver == null) {
                return false;
            }

            if (currentResolver.hasNext()) {
                return true;
            }

            currentResolver = findNextResolver();
            return (currentResolver != null);
        }

        /** @inheritDoc */
        public Certificate next() {
            if (hasNext()) {
                return currentResolver.next();
            }

            throw new NoSuchElementException();
        }

        /**
         * Method remove
         * <p>
         *  方法删除
         */
        public void remove() {
            throw new UnsupportedOperationException("Can't remove keys from KeyStore");
        }

        // Find the next storage with at least one element and return its Iterator
        private Iterator<Certificate> findNextResolver() {
            while (resolvers.hasNext()) {
                StorageResolverSpi resolverSpi = resolvers.next();
                Iterator<Certificate> iter = resolverSpi.getIterator();
                if (iter.hasNext()) {
                    return iter;
                }
            }

            return null;
        }
    }
}
