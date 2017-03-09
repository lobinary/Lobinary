/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */


package com.sun.org.apache.xml.internal.serialize;


import java.util.Hashtable;


/**
 * Holds the state of the currently serialized element.
 *
 *
 * <p>
 *  保存当前序列化元素的状态。
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 * @see BaseMarkupSerializer
 */
public class ElementState
{


    /**
     * The element's raw tag name (local or prefix:local).
     * <p>
     *  元素的原始标记名称(本地或前缀：本地)。
     * 
     */
    public String rawName;


    /**
     * The element's local tag name.
     * <p>
     *  元素的局部标记名称。
     * 
     */
    public String localName;


    /**
     * The element's namespace URI.
     * <p>
     *  元素的命名空间URI。
     * 
     */
    public String namespaceURI;


    /**
     * True if element is space preserving.
     * <p>
     *  如果元素是空间保留,则为true。
     * 
     */
    public boolean preserveSpace;


    /**
     * True if element is empty. Turns false immediately
     * after serializing the first contents of the element.
     * <p>
     *  如果元素为空,则为true。在序列化元素的第一个内容后立即变为false。
     * 
     */
    public boolean empty;


    /**
     * True if the last serialized node was an element node.
     * <p>
     *  如果最后一个序列化节点是元素节点,则为true。
     * 
     */
    public boolean afterElement;


    /**
     * True if the last serialized node was a comment node.
     * <p>
     *  如果最后一个序列化节点是注释节点,则为true。
     * 
     */
    public boolean afterComment;


    /**
     * True if textual content of current element should be
     * serialized as CDATA section.
     * <p>
     *  如果当前元素的文本内容应当序列化为CDATA节,则为True。
     * 
     */
    public boolean doCData;


    /**
     * True if textual content of current element should be
     * serialized as raw characters (unescaped).
     * <p>
     *  如果当前元素的文本内容应该序列化为原始字符(未转义),则为True。
     * 
     */
    public boolean unescaped;


    /**
     * True while inside CData and printing text as CData.
     * <p>
     *  True时,在CData和打印文本CData。
     * 
     */
    public boolean inCData;


    /**
     * Association between namespace URIs (keys) and prefixes (values).
     * <p>
     *  命名空间URI(键)和前缀(值)之间的关联。
     */
    public Hashtable prefixes;


}
