/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.DOMLocator;
import org.w3c.dom.Node;


/**
 * <code>DOMLocatorImpl</code> is an implementaion that describes a location (e.g.
 * where an error occured).
 * <p>See also the <a href='http://www.w3.org/TR/2001/WD-DOM-Level-3-Core-20010913'>Document Object Model (DOM) Level 3 Core Specification</a>.
 *
 * @xerces.internal
 *
 * <p>
 *  <code> DOMLocatorImpl </code>是描述位置(例如发生错误的位置)的实现。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2001/WD-DOM-Level-3-Core-20010913'>文档对象模型(DOM)3级核心规范< a>。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Gopal Sharma, SUN Microsystems Inc.
 */

public class DOMLocatorImpl implements DOMLocator {

    //
    // Data
    //

   /**
    * The column number where the error occured,
    * or -1 if there is no column number available.
    * <p>
    *  发生错误的列号,如果没有列号可用,则为-1。
    * 
    */
   public int fColumnNumber = -1;

   /**
    * The line number where the error occured,
    * or -1 if there is no line number available.
    * <p>
    *  发生错误的行号,如果没有行号可用,则为-1。
    * 
    */
   public int fLineNumber = -1;

   /** related data node*/
   public Node fRelatedNode = null;

   /**
    * The URI where the error occured,
    * or null if there is no URI available.
    * <p>
    *  发生错误的URI,如果没有可用的URI,则为null。
    * 
    */
   public String fUri = null;

   /**
    * The byte offset into the input source this locator is pointing to or -1
    * if there is no byte offset available
    * <p>
    *  该定位器指向的输入源的字节偏移量,如果没有可用的字节偏移量,则为-1
    * 
    */
   public int fByteOffset = -1;

   /**
    * The UTF-16, as defined in [Unicode] and Amendment 1 of [ISO/IEC 10646],
    * offset into the input source this locator is pointing to or -1 if there
    * is no UTF-16 offset available.
    * <p>
    * 如[Unicode]和[ISO / IEC 10646]修正案1中定义的UTF-16偏移到此定位器指向的输入源或-1,如果没有可用的UTF-16偏移。
    * 
    */
   public int fUtf16Offset = -1;

   //
   // Constructors
   //

   public DOMLocatorImpl(){
   }

   public DOMLocatorImpl (int lineNumber, int columnNumber, String uri ){
        fLineNumber = lineNumber ;
        fColumnNumber = columnNumber ;
        fUri = uri;
   } // DOMLocatorImpl (int lineNumber, int columnNumber, String uri )

   public DOMLocatorImpl (int lineNumber, int columnNumber, int utf16Offset, String uri ){
        fLineNumber = lineNumber ;
        fColumnNumber = columnNumber ;
        fUri = uri;
        fUtf16Offset = utf16Offset;
   } // DOMLocatorImpl (int lineNumber, int columnNumber, int utf16Offset, String uri )

   public DOMLocatorImpl (int lineNumber, int columnNumber, int byteoffset, Node relatedData, String uri ){
        fLineNumber = lineNumber ;
        fColumnNumber = columnNumber ;
        fByteOffset = byteoffset ;
        fRelatedNode = relatedData ;
        fUri = uri;
   } // DOMLocatorImpl (int lineNumber, int columnNumber, int offset, Node errorNode, String uri )

   public DOMLocatorImpl (int lineNumber, int columnNumber, int byteoffset, Node relatedData, String uri, int utf16Offset ){
        fLineNumber = lineNumber ;
        fColumnNumber = columnNumber ;
        fByteOffset = byteoffset ;
        fRelatedNode = relatedData ;
        fUri = uri;
        fUtf16Offset = utf16Offset;
   } // DOMLocatorImpl (int lineNumber, int columnNumber, int offset, Node errorNode, String uri )


  /**
   * The line number where the error occured, or -1 if there is no line
   * number available.
   * <p>
   *  发生错误的行号,如果没有行号可用,则为-1。
   * 
   */
   public int getLineNumber(){
        return fLineNumber;
   }

  /**
   * The column number where the error occured, or -1 if there is no column
   * number available.
   * <p>
   *  发生错误的列号,如果没有列号可用,则为-1。
   * 
   */
  public int getColumnNumber(){
        return fColumnNumber;
  }


  /**
   * The URI where the error occured, or null if there is no URI available.
   * <p>
   *  发生错误的URI,如果没有可用的URI,则为null。
   * 
   */
  public String getUri(){
        return fUri;
  }


  public Node getRelatedNode(){
    return fRelatedNode;
  }


  /**
   * The byte offset into the input source this locator is pointing to or -1
   * if there is no byte offset available
   * <p>
   *  该定位器指向的输入源的字节偏移量,如果没有可用的字节偏移量,则为-1
   * 
   */
  public int getByteOffset(){
        return fByteOffset;
  }

  /**
   * The UTF-16, as defined in [Unicode] and Amendment 1 of [ISO/IEC 10646],
   * offset into the input source this locator is pointing to or -1 if there
   * is no UTF-16 offset available.
   * <p>
   *  如[Unicode]和[ISO / IEC 10646]修正案1中定义的UTF-16偏移到此定位器指向的输入源或-1,如果没有可用的UTF-16偏移。
   */
  public int getUtf16Offset(){
        return fUtf16Offset;
  }

}// class DOMLocatorImpl
