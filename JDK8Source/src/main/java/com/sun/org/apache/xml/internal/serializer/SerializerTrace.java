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
 * $Id: SerializerTrace.java,v 1.2.4.1 2005/09/15 08:15:24 suresh_emailid Exp $
 * <p>
 *  $ Id：SerializerTrace.java,v 1.2.4.1 2005/09/15 08:15:24 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import org.xml.sax.Attributes;

/**
 * This interface defines a set of integer constants that identify trace event
 * types.
 *
 * @xsl.usage internal
 * <p>
 *  此接口定义一组标识跟踪事件类型的整数常量。
 * 
 *  @ xsl.usage internal
 * 
 */

public interface SerializerTrace {

  /**
   * Event type generated when a document begins.
   *
   * <p>
   *  文档开始时生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_STARTDOCUMENT = 1;

  /**
   * Event type generated when a document ends.
   * <p>
   *  文档结束时生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_ENDDOCUMENT = 2;

  /**
   * Event type generated when an element begins (after the attributes have been processed but before the children have been added).
   * <p>
   *  当元素开始时(在处理属性之后但在添加子元素之前)生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_STARTELEMENT = 3;

  /**
   * Event type generated when an element ends, after it's children have been added.
   * <p>
   *  在元素添加完毕后生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_ENDELEMENT = 4;

  /**
   * Event type generated for character data (CDATA and Ignorable Whitespace have their own events).
   * <p>
   *  为字符数据生成的事件类型(CDATA和Ignorable Whitespace有自己的事件)。
   * 
   */
  public static final int EVENTTYPE_CHARACTERS = 5;

  /**
   * Event type generated for ignorable whitespace (I'm not sure how much this is actually called.
   * <p>
   *  为可忽略的空格生成的事件类型(我不知道这实际上是多少。
   * 
   */
  public static final int EVENTTYPE_IGNORABLEWHITESPACE = 6;

  /**
   * Event type generated for processing instructions.
   * <p>
   *  为处理指令生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_PI = 7;

  /**
   * Event type generated after a comment has been added.
   * <p>
   *  添加注释后生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_COMMENT = 8;

  /**
   * Event type generate after an entity ref is created.
   * <p>
   * 创建实体引用之后的事件类型生成。
   * 
   */
  public static final int EVENTTYPE_ENTITYREF = 9;

  /**
   * Event type generated after CDATA is generated.
   * <p>
   *  生成CDATA后生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_CDATA = 10;

  /**
   * Event type generated when characters might be written to an output stream,
   *  but  these characters never are. They will ultimately be written out via
   * EVENTTYPE_OUTPUT_CHARACTERS. This type is used as attributes are collected.
   * Whenever the attributes change this event type is fired. At the very end
   * however, when the attributes do not change anymore and are going to be
   * ouput to the document the real characters will be written out using the
   * EVENTTYPE_OUTPUT_CHARACTERS.
   * <p>
   *  当字符可能写入输出流时生成的事件类型,但这些字符从不是。它们最终将通过EVENTTYPE_OUTPUT_CHARACTERS写出。此类型用作收集属性。每当属性更改时,此事件类型被触发。
   * 然而,在最后,当属性不再改变并且要输出到文档时,真实的字符将使用EVENTTYPE_OUTPUT_CHARACTERS写出。
   * 
   */
  public static final int EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS = 11;

  /**
   * Event type generated when characters are written to an output stream.
   * <p>
   *  将字符写入输出流时生成的事件类型。
   * 
   */
  public static final int EVENTTYPE_OUTPUT_CHARACTERS = 12;


  /**
   * Tell if trace listeners are present.
   *
   * <p>
   *  告诉是否存在跟踪监听器。
   * 
   * 
   * @return True if there are trace listeners
   */
  public boolean hasTraceListeners();

  /**
   * Fire startDocument, endDocument events.
   *
   * <p>
   *  fire startDocument,endDocument事件。
   * 
   * 
   * @param eventType One of the EVENTTYPE_XXX constants.
   */
  public void fireGenerateEvent(int eventType);

  /**
   * Fire startElement, endElement events.
   *
   * <p>
   *  Fire startElement,endElement事件。
   * 
   * 
   * @param eventType One of the EVENTTYPE_XXX constants.
   * @param name The name of the element.
   * @param atts The SAX attribute list.
   */
  public void fireGenerateEvent(int eventType, String name, Attributes atts);

  /**
   * Fire characters, cdata events.
   *
   * <p>
   *  火字符,cdata事件。
   * 
   * 
   * @param eventType One of the EVENTTYPE_XXX constants.
   * @param ch The char array from the SAX event.
   * @param start The start offset to be used in the char array.
   * @param length The end offset to be used in the chara array.
   */
  public void fireGenerateEvent(int eventType, char ch[], int start, int length);

  /**
   * Fire processingInstruction events.
   *
   * <p>
   *  火处理指导事件。
   * 
   * 
   * @param eventType One of the EVENTTYPE_XXX constants.
   * @param name The name of the processing instruction.
   * @param data The processing instruction data.
   */
  public void fireGenerateEvent(int eventType, String name, String data);


  /**
   * Fire comment and entity ref events.
   *
   * <p>
   *  火灾评论和实体参考事件。
   * 
   * @param eventType One of the EVENTTYPE_XXX constants.
   * @param data The comment or entity ref data.
   */
  public void fireGenerateEvent(int eventType, String data);

}
