/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XMLReaderManager.java,v 1.2.4.1 2005/09/15 08:16:02 suresh_emailid Exp $
 * <p>
 *  $ Id：XMLReaderManager.java,v 1.2.4.1 2005/09/15 08:16:02 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.FactoryImpl;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Creates XMLReader objects and caches them for re-use.
 * This class follows the singleton pattern.
 * <p>
 *  创建XMLReader对象并将其缓存以供重用。这个类遵循单例模式。
 * 
 */
public class XMLReaderManager {

    private static final String NAMESPACES_FEATURE =
                             "http://xml.org/sax/features/namespaces";
    private static final String NAMESPACE_PREFIXES_FEATURE =
                             "http://xml.org/sax/features/namespace-prefixes";
    private static final XMLReaderManager m_singletonManager =
                                                     new XMLReaderManager();
    private static final String property = "org.xml.sax.driver";
    /**
     * Parser factory to be used to construct XMLReader objects
     * <p>
     *  解析器工厂用于构造XMLReader对象
     * 
     */
    private static SAXParserFactory m_parserFactory;

    /**
     * Cache of XMLReader objects
     * <p>
     *  XMLReader对象的缓存
     * 
     */
    private ThreadLocal m_readers;

    /**
     * Keeps track of whether an XMLReader object is in use.
     * <p>
     *  跟踪是否正在使用XMLReader对象。
     * 
     */
    private HashMap m_inUse;

    private boolean m_useServicesMechanism = true;

    private boolean _secureProcessing;
     /**
     * protocols allowed for external DTD references in source file and/or stylesheet.
     * <p>
     *  允许在源文件和/或样式表中的外部DTD引用的协议。
     * 
     */
    private String _accessExternalDTD = XalanConstants.EXTERNAL_ACCESS_DEFAULT;

    private XMLSecurityManager _xmlSecurityManager;

    /**
     * Hidden constructor
     * <p>
     *  隐藏构造函数
     * 
     */
    private XMLReaderManager() {
    }

    /**
     * Retrieves the singleton reader manager
     * <p>
     *  检索单身票读者经理
     * 
     */
    public static XMLReaderManager getInstance(boolean useServicesMechanism) {
        m_singletonManager.setServicesMechnism(useServicesMechanism);
        return m_singletonManager;
    }

    /**
     * Retrieves a cached XMLReader for this thread, or creates a new
     * XMLReader, if the existing reader is in use.  When the caller no
     * longer needs the reader, it must release it with a call to
     * {@link #releaseXMLReader}.
     * <p>
     *  检索此线程的缓存XMLReader,或创建新的XMLReader(如果现有阅读器正在使用)。当调用者不再需要阅读器时,它必须通过调用{@link #releaseXMLReader}来释放它。
     * 
     */
    public synchronized XMLReader getXMLReader() throws SAXException {
        XMLReader reader;

        if (m_readers == null) {
            // When the m_readers.get() method is called for the first time
            // on a thread, a new XMLReader will automatically be created.
            m_readers = new ThreadLocal();
        }

        if (m_inUse == null) {
            m_inUse = new HashMap();
        }

        // If the cached reader for this thread is in use, construct a new
        // one; otherwise, return the cached reader unless it isn't an
        // instance of the class set in the 'org.xml.sax.driver' property
        reader = (XMLReader) m_readers.get();
        boolean threadHasReader = (reader != null);
        String factory = SecuritySupport.getSystemProperty(property);
        if (threadHasReader && m_inUse.get(reader) != Boolean.TRUE &&
                ( factory == null || reader.getClass().getName().equals(factory))) {
            m_inUse.put(reader, Boolean.TRUE);
        } else {
            try {
                try {
                    // According to JAXP 1.2 specification, if a SAXSource
                    // is created using a SAX InputSource the Transformer or
                    // TransformerFactory creates a reader via the
                    // XMLReaderFactory if setXMLReader is not used
                    reader = XMLReaderFactory.createXMLReader();
                    try {
                        reader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, _secureProcessing);
                    } catch (SAXNotRecognizedException e) {
                        System.err.println("Warning:  " + reader.getClass().getName() + ": "
                                + e.getMessage());
                    }
                } catch (Exception e) {
                   try {
                        // If unable to create an instance, let's try to use
                        // the XMLReader from JAXP
                        if (m_parserFactory == null) {
                            m_parserFactory = FactoryImpl.getSAXFactory(m_useServicesMechanism);
                            m_parserFactory.setNamespaceAware(true);
                        }

                        reader = m_parserFactory.newSAXParser().getXMLReader();
                   } catch (ParserConfigurationException pce) {
                       throw pce;   // pass along pce
                   }
                }
                try {
                    reader.setFeature(NAMESPACES_FEATURE, true);
                    reader.setFeature(NAMESPACE_PREFIXES_FEATURE, false);
                } catch (SAXException se) {
                    // Try to carry on if we've got a parser that
                    // doesn't know about namespace prefixes.
                }
            } catch (ParserConfigurationException ex) {
                throw new SAXException(ex);
            } catch (FactoryConfigurationError ex1) {
                throw new SAXException(ex1.toString());
            } catch (NoSuchMethodError ex2) {
            } catch (AbstractMethodError ame) {
            }

            // Cache the XMLReader if this is the first time we've created
            // a reader for this thread.
            if (!threadHasReader) {
                m_readers.set(reader);
                m_inUse.put(reader, Boolean.TRUE);
            }
        }

        try {
            //reader is cached, but this property might have been reset
            reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, _accessExternalDTD);
        } catch (SAXException se) {
            System.err.println("Warning:  " + reader.getClass().getName() + ": "
                        + se.getMessage());
        }

        try {
            if (_xmlSecurityManager != null) {
                for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
                    reader.setProperty(limit.apiProperty(),
                            _xmlSecurityManager.getLimitValueAsString(limit));
                }
                if (_xmlSecurityManager.printEntityCountInfo()) {
                    reader.setProperty(XalanConstants.JDK_ENTITY_COUNT_INFO, XalanConstants.JDK_YES);
                }
            }
        } catch (SAXException se) {
            System.err.println("Warning:  " + reader.getClass().getName() + ": "
                        + se.getMessage());
        }

        return reader;
    }

    /**
     * Mark the cached XMLReader as available.  If the reader was not
     * actually in the cache, do nothing.
     *
     * <p>
     *  将缓存的XMLReader标记为可用。如果读者实际上不在缓存中,什么都不做。
     * 
     * 
     * @param reader The XMLReader that's being released.
     */
    public synchronized void releaseXMLReader(XMLReader reader) {
        // If the reader that's being released is the cached reader
        // for this thread, remove it from the m_isUse list.
        if (m_readers.get() == reader && reader != null) {
            m_inUse.remove(reader);
        }
    }
    /**
     * Return the state of the services mechanism feature.
     * <p>
     * 返回服务机制功能的状态。
     * 
     */
    public boolean useServicesMechnism() {
        return m_useServicesMechanism;
    }

    /**
     * Set the state of the services mechanism feature.
     * <p>
     *  设置服务机制功能的状态。
     * 
     */
    public void setServicesMechnism(boolean flag) {
        m_useServicesMechanism = flag;
    }

    /**
     * Set feature
     * <p>
     *  设置功能
     * 
     */
    public void setFeature(String name, boolean value) {
        if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            _secureProcessing = value;
        }
    }

    /**
     * Get property value
     * <p>
     *  获取属性值
     * 
     */
    public Object getProperty(String name) {
        if (name.equals(XMLConstants.ACCESS_EXTERNAL_DTD)) {
            return _accessExternalDTD;
        } else if (name.equals(XalanConstants.SECURITY_MANAGER)) {
            return _xmlSecurityManager;
        }
        return null;
    }

    /**
     * Set property.
     * <p>
     *  设置属性。
     */
    public void setProperty(String name, Object value) {
        if (name.equals(XMLConstants.ACCESS_EXTERNAL_DTD)) {
            _accessExternalDTD = (String)value;
        } else if (name.equals(XalanConstants.SECURITY_MANAGER)) {
            _xmlSecurityManager = (XMLSecurityManager)value;
        }
    }
}
