/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
 *  版权所有2001-2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.parsers;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;

/**
 * This configuration allows Xerces to behave in a security-conscious manner; that is,
 * it permits applications to instruct Xerces to limit certain
 * operations that could be exploited by malicious document authors to cause a denail-of-service
 * attack when the document is parsed.
 *
 * In addition to the features and properties recognized by the base
 * parser configuration, this class recognizes these additional
 * features and properties:
 * <ul>
 * <li>Properties
 *  <ul>
 *   <li>http://apache.org/xml/properties/security-manager</li>
 *  </ul>
 * </ul>
 *
 * <p>
 *  此配置允许Xerces以安全意识的方式运行;也就是说,它允许应用程序指示Xerces限制可能被恶意文档作者利用的某些操作,以在解析文档时导致服务拒绝攻击。
 * 
 *  除了基本解析器配置识别的功能和属性之外,此类还识别这些附加的功能和属性：
 * <ul>
 *  <li>属性
 * <ul>
 *  <li> http://apache.org/xml/properties/security-manager </li>
 * </ul>
 * </ul>
 * 
 * 
 * @author Neil Graham, IBM
 *
 * @version $Id: SecurityConfiguration.java,v 1.6 2010-11-01 04:40:09 joehw Exp $
 */
public class SecurityConfiguration extends XIncludeAwareParserConfiguration
{

    //
    // Constants
    //

    protected static final String SECURITY_MANAGER_PROPERTY =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SECURITY_MANAGER_PROPERTY;

    //
    // Constructors
    //

    /** Default constructor. */
    public SecurityConfiguration () {
        this(null, null, null);
    } // <init>()

    /**
     * Constructs a parser configuration using the specified symbol table.
     *
     * <p>
     *  使用指定的符号表构造解析器配置。
     * 
     * 
     * @param symbolTable The symbol table to use.
     */
    public SecurityConfiguration (SymbolTable symbolTable) {
        this(symbolTable, null, null);
    } // <init>(SymbolTable)

    /**
     * Constructs a parser configuration using the specified symbol table and
     * grammar pool.
     * <p>
     * <strong>REVISIT:</strong>
     * Grammar pool will be updated when the new validation engine is
     * implemented.
     *
     * <p>
     *  使用指定的符号表和语法池构造解析器配置。
     * <p>
     *  <strong> REVISIT：</strong>当实施新的验​​证引擎时,语法池将更新。
     * 
     * 
     * @param symbolTable The symbol table to use.
     * @param grammarPool The grammar pool to use.
     */
    public SecurityConfiguration (SymbolTable symbolTable,
                                         XMLGrammarPool grammarPool) {
        this(symbolTable, grammarPool, null);
    } // <init>(SymbolTable,XMLGrammarPool)

    /**
     * Constructs a parser configuration using the specified symbol table,
     * grammar pool, and parent settings.
     * <p>
     * <strong>REVISIT:</strong>
     * Grammar pool will be updated when the new validation engine is
     * implemented.
     *
     * <p>
     * 使用指定的符号表,语法池和父设置构造一个解析器配置。
     * <p>
     * 
     * @param symbolTable    The symbol table to use.
     * @param grammarPool    The grammar pool to use.
     * @param parentSettings The parent settings.
     */
    public SecurityConfiguration (SymbolTable symbolTable,
                                         XMLGrammarPool grammarPool,
                                         XMLComponentManager parentSettings) {
        super(symbolTable, grammarPool, parentSettings);

        // create the SecurityManager property:
        setProperty(SECURITY_MANAGER_PROPERTY, new XMLSecurityManager(true));
    } // <init>(SymbolTable,XMLGrammarPool)

} // class SecurityConfiguration
