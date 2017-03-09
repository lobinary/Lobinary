/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

/**
 * Location information.
 *
 * <p>
 *  地点信息。
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public interface XMLLocator {

    //
    // XMLLocator methods
    //

    /** Returns the public identifier. */
    public String getPublicId();

    /** Returns the literal system identifier. */
    public String getLiteralSystemId();

    /** Returns the base system identifier. */
    public String getBaseSystemId();

    /** Returns the expanded system identifier. */
    public String getExpandedSystemId();

    /** Returns the line number, or <code>-1</code> if no line number is available. */
    public int getLineNumber();

    /** Returns the column number, or <code>-1</code> if no column number is available. */
    public int getColumnNumber();

    /** Returns the character offset, or <code>-1</code> if no character offset is available. */
    public int getCharacterOffset();

    /**
     * Returns the encoding of the current entity.
     * Note that, for a given entity, this value can only be
     * considered final once the encoding declaration has been read (or once it
     * has been determined that there is no such declaration) since, no encoding
     * having been specified on the XMLInputSource, the parser
     * will make an initial "guess" which could be in error.
     * <p>
     *  返回当前实体的编码。
     * 注意,对于给定的实体,一旦读取了编码声明(或者一旦确定没有这样的声明),该值只能被认为是final,因为没有在XMLInputSource上指定编码,解析器将做一个可能是错误的初始"猜测"。
     * 
     */
    public String getEncoding();

    /**
     * Returns the XML version of the current entity. This will normally be the
     * value from the XML or text declaration or defaulted by the parser. Note that
     * that this value may be different than the version of the processing rules
     * applied to the current entity. For instance, an XML 1.1 document may refer to
     * XML 1.0 entities. In such a case the rules of XML 1.1 are applied to the entire
     * document. Also note that, for a given entity, this value can only be considered
     * final once the XML or text declaration has been read or once it has been
     * determined that there is no such declaration.
     * <p>
     * 返回当前实体的XML版本。这通常是来自XML或文本声明的值或由解析器默认的值。请注意,此值可能不同于应用于当前实体的处理规则的版本。例如,XML 1.1文档可以引用XML 1.0实体。
     * 在这种情况下,XML 1.1的规则应用于整个文档。还要注意,对于给定的实体,只有一旦读取了XML或文本声明,或者一旦确定没有这样的声明,该值只能被认为是final。
     */
    public String getXMLVersion();


} // interface XMLLocator
