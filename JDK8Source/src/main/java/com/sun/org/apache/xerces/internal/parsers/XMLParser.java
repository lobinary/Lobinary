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
 *      http://www.apache.org/licenses/LICENSE-2.0
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

package com.sun.org.apache.xerces.internal.parsers;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;

import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXNotRecognizedException;

/**
 * Base class of all XML-related parsers.
 * <p>
 * In addition to the features and properties recognized by the parser
 * configuration, this parser recognizes these additional features and
 * properties:
 * <ul>
 * <li>Properties
 *  <ul>
 *   <li>http://apache.org/xml/properties/internal/error-handler</li>
 *   <li>http://apache.org/xml/properties/internal/entity-resolver</li>
 *  </ul>
 * </ul>
 *
 * <p>
 *  所有与XML相关的解析器的基类。
 * <p>
 *  除了解析器配置识别的特性和属性之外,此解析器还识别这些附加的特性和属性：
 * <ul>
 *  <li>属性
 * <ul>
 *  <li> http://apache.org/xml/properties/internal/error-handler </li> <li> http://apache.org/xml/proper
 * ties/internal/entity-resolver </li>。
 * </ul>
 * </ul>
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLParser.java,v 1.5 2007/07/20 14:11:21 spericas Exp $
 */
public abstract class XMLParser {

    //
    // Constants
    //

    // properties

    /** Property identifier: entity resolver. */
    protected static final String ENTITY_RESOLVER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY;

    /** Property identifier: error handler. */
    protected static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    /** Recognized properties. */
    private static final String[] RECOGNIZED_PROPERTIES = {
        ENTITY_RESOLVER,
        ERROR_HANDLER,
    };

    //
    // Data
    //

    /** The parser configuration. */
    protected XMLParserConfiguration fConfiguration;

    /** The XML Security Manager. */
    XMLSecurityManager securityManager;

    /** The XML Security Property Manager. */
    XMLSecurityPropertyManager securityPropertyManager;


    //
    // Constructors
    //

    /**
     * Query the state of a feature.
     * <p>
     *  查询要素的状态。
     * 
     */
    public boolean getFeature(String featureId)
            throws SAXNotSupportedException, SAXNotRecognizedException {
        return fConfiguration.getFeature(featureId);

    }

    /**
     * Default Constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    protected XMLParser(XMLParserConfiguration config) {

        // save configuration
        fConfiguration = config;

        // add default recognized properties
        fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);

    } // <init>(XMLParserConfiguration)

    //
    // Public methods
    //

    /**
     * parse
     *
     * <p>
     *  解析
     * 
     * 
     * @param inputSource
     *
     * @exception XNIException
     * @exception java.io.IOException
     */
    public void parse(XMLInputSource inputSource)
        throws XNIException, IOException {
        // null indicates that the parser is called directly, initialize them
        if (securityManager == null) {
            securityManager = new XMLSecurityManager(true);
            fConfiguration.setProperty(Constants.SECURITY_MANAGER, securityManager);
        }
        if (securityPropertyManager == null) {
            securityPropertyManager = new XMLSecurityPropertyManager();
            fConfiguration.setProperty(Constants.XML_SECURITY_PROPERTY_MANAGER, securityPropertyManager);
        }

        reset();
        fConfiguration.parse(inputSource);

    } // parse(XMLInputSource)

    //
    // Protected methods
    //

    /**
     * reset all components before parsing
     * <p>
     *  在解析之前重置所有组件
     */
    protected void reset() throws XNIException {
    } // reset()

} // class XMLParser
