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
 *     http://www.apache.org/licenses/LICENSE-2.0
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
/*
 * $Id: ToTextStream.java,v 1.2.4.1 2005/09/21 10:35:34 pvedula Exp $
 * <p>
 *  $ Id：ToTextStream.java,v 1.2.4.1 2005/09/21 10:35:34 pvedula Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;

import com.sun.org.apache.xml.internal.serializer.utils.MsgKey;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This class is not a public API.
 * It is only public because it is used in other packages.
 * This class converts SAX or SAX-like calls to a
 * serialized document for xsl:output method of "text".
 * @xsl.usage internal
 * <p>
 *  此类不是公共API。它只是公共的,因为它用于其他包。此类将SAX或类SAX调用转换为"text"的xsl：output方法的序列化文档。 @ xsl.usage internal
 * 
 */
public final class ToTextStream extends ToStream
{


  /**
   * Default constructor.
   * <p>
   *  默认构造函数。
   * 
   */
  public ToTextStream()
  {
    super();
  }



  /**
   * Receive notification of the beginning of a document.
   *
   * <p>The SAX parser will invoke this method only once, before any
   * other methods in this interface or in DTDHandler (except for
   * setDocumentLocator).</p>
   *
   * <p>
   *  接收文档开头的通知。
   * 
   *  <p> SAX解析器只会在此接口或DTDHandler中的任何其他方法(setDocumentLocator除外)之前调用此方法一次。</p>
   * 
   * 
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   *
   * @throws org.xml.sax.SAXException
   */
  protected void startDocumentInternal() throws org.xml.sax.SAXException
  {
    super.startDocumentInternal();

    m_needToCallStartDocument = false;

    // No action for the moment.
  }

  /**
   * Receive notification of the end of a document.
   *
   * <p>The SAX parser will invoke this method only once, and it will
   * be the last method invoked during the parse.  The parser shall
   * not invoke this method until it has either abandoned parsing
   * (because of an unrecoverable error) or reached the end of
   * input.</p>
   *
   * <p>
   *  接收文档结束的通知。
   * 
   * <p> SAX解析器将仅调用此方法一次,它将是解析期间调用的最后一个方法。解析器不应该调用此方法,直到它放弃了解析(因为一个不可恢复的错误)或到达输入的结束。</p>
   * 
   * 
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   *
   * @throws org.xml.sax.SAXException
   */
  public void endDocument() throws org.xml.sax.SAXException
  {
    flushPending();
    flushWriter();
    if (m_tracer != null)
        super.fireEndDoc();
  }

  /**
   * Receive notification of the beginning of an element.
   *
   * <p>The Parser will invoke this method at the beginning of every
   * element in the XML document; there will be a corresponding
   * endElement() event for every startElement() event (even when the
   * element is empty). All of the element's content will be
   * reported, in order, before the corresponding endElement()
   * event.</p>
   *
   * <p>If the element name has a namespace prefix, the prefix will
   * still be attached.  Note that the attribute list provided will
   * contain only attributes with explicit values (specified or
   * defaulted): #IMPLIED attributes will be omitted.</p>
   *
   *
   * <p>
   *  接收元素开头的通知。
   * 
   *  <p>解析器将在XML文档中的每个元素的开头调用此方法;每个startElement()事件都会有一个相应的endElement()事件(即使元素为空)。
   * 将在相应的endElement()事件之前按顺序报告所有元素的内容。</p>。
   * 
   *  <p>如果元素名称有命名空间前缀,则前缀仍将被附加。请注意,提供的属性列表将仅包含具有显式值(指定或默认值)的属性：#IMPLIED属性将被省略。</p>
   * 
   * 
   * @param namespaceURI The Namespace URI, or the empty string if the
   *        element has no Namespace URI or if Namespace
   *        processing is not being performed.
   * @param localName The local name (without prefix), or the
   *        empty string if Namespace processing is not being
   *        performed.
   * @param name The qualified name (with prefix), or the
   *        empty string if qualified names are not available.
   * @param atts The attributes attached to the element, if any.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see #endElement
   * @see org.xml.sax.AttributeList
   *
   * @throws org.xml.sax.SAXException
   */
  public void startElement(
          String namespaceURI, String localName, String name, Attributes atts)
            throws org.xml.sax.SAXException
  {
    // time to fire off startElement event
    if (m_tracer != null) {
        super.fireStartElem(name);
        this.firePseudoAttributes();
    }
    return;
  }

  /**
   * Receive notification of the end of an element.
   *
   * <p>The SAX parser will invoke this method at the end of every
   * element in the XML document; there will be a corresponding
   * startElement() event for every endElement() event (even when the
   * element is empty).</p>
   *
   * <p>If the element name has a namespace prefix, the prefix will
   * still be attached to the name.</p>
   *
   *
   * <p>
   *  接收元素结束的通知。
   * 
   *  <p> SAX解析器将在XML文档中每个元素的末尾调用此方法;每个endElement()事件都会有一个相应的startElement()事件(即使元素为空)。</p>
   * 
   *  <p>如果元素名称有名称空间前缀,则前缀仍会附加到名称。</p>
   * 
   * 
   * @param namespaceURI The Namespace URI, or the empty string if the
   *        element has no Namespace URI or if Namespace
   *        processing is not being performed.
   * @param localName The local name (without prefix), or the
   *        empty string if Namespace processing is not being
   *        performed.
   * @param name The qualified name (with prefix), or the
   *        empty string if qualified names are not available.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   *
   * @throws org.xml.sax.SAXException
   */
  public void endElement(String namespaceURI, String localName, String name)
          throws org.xml.sax.SAXException
  {
        if (m_tracer != null)
            super.fireEndElem(name);
  }

  /**
   * Receive notification of character data.
   *
   * <p>The Parser will call this method to report each chunk of
   * character data.  SAX parsers may return all contiguous character
   * data in a single chunk, or they may split it into several
   * chunks; however, all of the characters in any single event
   * must come from the same external entity, so that the Locator
   * provides useful information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>Note that some parsers will report whitespace using the
   * ignorableWhitespace() method rather than this one (validating
   * parsers must do so).</p>
   *
   * <p>
   *  接收字符数据的通知。
   * 
   * <p>解析器将调用此方法来报告每个字符数据块。
   *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>。
   * 
   *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   *  <p>请注意,一些解析器将使用ignorableWhitespace()方法而不是这一个(验证解析器必须这样做)报告空格。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see #ignorableWhitespace
   * @see org.xml.sax.Locator
   */
  public void characters(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {

    flushPending();

    try
    {
        if (inTemporaryOutputState()) {
            /* leave characters un-processed as we are
             * creating temporary output, the output generated by
             * this serializer will be input to a final serializer
             * later on and it will do the processing in final
             * output state (not temporary output state).
             *
             * A "temporary" ToTextStream serializer is used to
             * evaluate attribute value templates (for example),
             * and the result of evaluating such a thing
             * is fed into a final serializer later on.
             * <p>
             *  创建临时输出,此序列化器生成的输出将稍后输入到最终的串行器,并且它将在最终输出状态(不是临时输出状态)中进行处理。
             * 
             *  "临时"ToTextStream序列化程序用于评估属性值模板(例如),并且评估这样的事情的结果随后被馈送到最终的串行器。
             * 
             */
            m_writer.write(ch, start, length);
        }
        else {
            // In final output state we do process the characters!
            writeNormalizedChars(ch, start, length, m_lineSepUse);
        }

        if (m_tracer != null)
            super.fireCharEvent(ch, start, length);
    }
    catch(IOException ioe)
    {
      throw new SAXException(ioe);
    }
  }

  /**
   * If available, when the disable-output-escaping attribute is used,
   * output raw text without escaping.
   *
   * <p>
   *  如果可用,当使用disable-output-escaping属性时,输出原始文本而不转义。
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   *
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public void charactersRaw(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {

    try
    {
      writeNormalizedChars(ch, start, length, m_lineSepUse);
    }
    catch(IOException ioe)
    {
      throw new SAXException(ioe);
    }
  }

    /**
     * Normalize the characters, but don't escape.  Different from
     * SerializerToXML#writeNormalizedChars because it does not attempt to do
     * XML escaping at all.
     *
     * <p>
     *  规范化字符,但不逃逸。与SerializerToXML#writeNormalizedChars不同,因为它根本不尝试执行XML转义。
     * 
     * 
     * @param ch The characters from the XML document.
     * @param start The start position in the array.
     * @param length The number of characters to read from the array.
     * @param useLineSep true if the operating systems
     * end-of-line separator should be output rather than a new-line character.
     *
     * @throws IOException
     * @throws org.xml.sax.SAXException
     */
    void writeNormalizedChars(
        final char ch[],
            final int start,
            final int length,
            final boolean useLineSep)
            throws IOException, org.xml.sax.SAXException
    {
        final String encoding = getEncoding();
        final java.io.Writer writer = m_writer;
        final int end = start + length;

        /* copy a few "constants" before the loop for performance */
        final char S_LINEFEED = CharInfo.S_LINEFEED;

        // This for() loop always increments i by one at the end
        // of the loop.  Additional increments of i adjust for when
        // two input characters (a high/low UTF16 surrogate pair)
        // are processed.
        for (int i = start; i < end; i++) {
            final char c = ch[i];

            if (S_LINEFEED == c && useLineSep) {
                writer.write(m_lineSep, 0, m_lineSepLen);
                // one input char processed
            } else if (m_encodingInfo.isInEncoding(c)) {
                writer.write(c);
                // one input char processed
            } else if (Encodings.isHighUTF16Surrogate(c)) {
                final int codePoint = writeUTF16Surrogate(c, ch, i, end);
                if (codePoint != 0) {
                    // I think we can just emit the message,
                    // not crash and burn.
                    final String integralValue = Integer.toString(codePoint);
                    final String msg = Utils.messages.createMessage(
                        MsgKey.ER_ILLEGAL_CHARACTER,
                        new Object[] { integralValue, encoding });

                    //Older behavior was to throw the message,
                    //but newer gentler behavior is to write a message to System.err
                    //throw new SAXException(msg);
                    System.err.println(msg);

                }
                i++; // two input chars processed
            } else {
                // Don't know what to do with this char, it is
                // not in the encoding and not a high char in
                // a surrogate pair, so write out as an entity ref
                if (encoding != null) {
                    /* The output encoding is known,
                     * so somthing is wrong.
                     * <p>
                     *  所以一切都是错误的。
                     * 
                     */

                    // not in the encoding, so write out a character reference
                    writer.write('&');
                    writer.write('#');
                    writer.write(Integer.toString(c));
                    writer.write(';');

                    // I think we can just emit the message,
                    // not crash and burn.
                    final String integralValue = Integer.toString(c);
                    final String msg = Utils.messages.createMessage(
                        MsgKey.ER_ILLEGAL_CHARACTER,
                        new Object[] { integralValue, encoding });

                    //Older behavior was to throw the message,
                    //but newer gentler behavior is to write a message to System.err
                    //throw new SAXException(msg);
                    System.err.println(msg);
                } else {
                    /* The output encoding is not known,
                     * so just write it out as-is.
                     * <p>
                     *  所以只是写出来。
                     * 
                     */
                    writer.write(c);
                }

                // one input char was processed
            }
        }
    }

  /**
   * Receive notification of cdata.
   *
   * <p>The Parser will call this method to report each chunk of
   * character data.  SAX parsers may return all contiguous character
   * data in a single chunk, or they may split it into several
   * chunks; however, all of the characters in any single event
   * must come from the same external entity, so that the Locator
   * provides useful information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>Note that some parsers will report whitespace using the
   * ignorableWhitespace() method rather than this one (validating
   * parsers must do so).</p>
   *
   * <p>
   *  接收cdata的通知。
   * 
   * <p>解析器将调用此方法来报告每个字符数据块。
   *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>。
   * 
   *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   *  <p>请注意,一些解析器将使用ignorableWhitespace()方法而不是这一个(验证解析器必须这样做)报告空格。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see #ignorableWhitespace
   * @see org.xml.sax.Locator
   */
  public void cdata(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {
    try
    {
        writeNormalizedChars(ch, start, length, m_lineSepUse);
        if (m_tracer != null)
            super.fireCDATAEvent(ch, start, length);
    }
    catch(IOException ioe)
    {
      throw new SAXException(ioe);
    }
  }

  /**
   * Receive notification of ignorable whitespace in element content.
   *
   * <p>Validating Parsers must use this method to report each chunk
   * of ignorable whitespace (see the W3C XML 1.0 recommendation,
   * section 2.10): non-validating parsers may also use this method
   * if they are capable of parsing and using content models.</p>
   *
   * <p>SAX parsers may return all contiguous whitespace in a single
   * chunk, or they may split it into several chunks; however, all of
   * the characters in any single event must come from the same
   * external entity, so that the Locator provides useful
   * information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>
   *  在元素内容中接收可忽略的空格的通知。
   * 
   *  <p>验证Parsers必须使用此方法来报告每个可忽略的空格(请参阅W3C XML 1.0建议,第2.10节)：如果非验证解析器能够解析和使用内容模型,那么它们也可以使用此方法。 p>
   * 
   *  <p> SAX解析器可以返回单个块中的所有连续空格,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>
   * 
   *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see #characters
   *
   * @throws org.xml.sax.SAXException
   */
  public void ignorableWhitespace(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {

    try
    {
      writeNormalizedChars(ch, start, length, m_lineSepUse);
    }
    catch(IOException ioe)
    {
      throw new SAXException(ioe);
    }
  }

  /**
   * Receive notification of a processing instruction.
   *
   * <p>The Parser will invoke this method once for each processing
   * instruction found: note that processing instructions may occur
   * before or after the main document element.</p>
   *
   * <p>A SAX parser should never report an XML declaration (XML 1.0,
   * section 2.8) or a text declaration (XML 1.0, section 4.3.1)
   * using this method.</p>
   *
   * <p>
   *  接收处理指令的通知。
   * 
   *  <p>对于找到的每个处理指令,解析器将调用此方法一次：请注意,处理指令可能发生在主文档元素之前或之后。</p>
   * 
   * <p> SAX解析器不应使用此方法报告XML声明(XML 1.0,第2.8节)或文本声明(XML 1.0,第4.3.1节)。</p>
   * 
   * 
   * @param target The processing instruction target.
   * @param data The processing instruction data, or null if
   *        none was supplied.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   *
   * @throws org.xml.sax.SAXException
   */
  public void processingInstruction(String target, String data)
          throws org.xml.sax.SAXException
  {
    // flush anything pending first
    flushPending();

    if (m_tracer != null)
        super.fireEscapingEvent(target, data);
  }

  /**
   * Called when a Comment is to be constructed.
   * Note that Xalan will normally invoke the other version of this method.
   * %REVIEW% In fact, is this one ever needed, or was it a mistake?
   *
   * <p>
   *  在构建评论时调用。请注意,Xalan通常会调用此方法的其他版本。 ％REVIEW％事实上,这是一个需要,还是它是一个错误?
   * 
   * 
   * @param   data  The comment data.
   * @throws org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public void comment(String data) throws org.xml.sax.SAXException
  {
      final int length = data.length();
      if (length > m_charsBuff.length)
      {
          m_charsBuff = new char[length*2 + 1];
      }
      data.getChars(0, length, m_charsBuff, 0);
      comment(m_charsBuff, 0, length);
  }

  /**
   * Report an XML comment anywhere in the document.
   *
   * This callback will be used for comments inside or outside the
   * document element, including comments in the external DTD
   * subset (if read).
   *
   * <p>
   *  在文档中的任何位置报告XML注释。
   * 
   *  此回调将用于文档元素内部或外部的注释,包括外部DTD子集中的注释(如果已读取)。
   * 
   * 
   * @param ch An array holding the characters in the comment.
   * @param start The starting position in the array.
   * @param length The number of characters to use from the array.
   * @throws org.xml.sax.SAXException The application may raise an exception.
   */
  public void comment(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {

    flushPending();
    if (m_tracer != null)
        super.fireCommentEvent(ch, start, length);
  }

  /**
   * Receive notivication of a entityReference.
   *
   * <p>
   *  接收entityReference的通知。
   * 
   * 
   * @param name non-null reference to the name of the entity.
   *
   * @throws org.xml.sax.SAXException
   */
  public void entityReference(String name) throws org.xml.sax.SAXException
  {
        if (m_tracer != null)
            super.fireEntityReference(name);
  }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttribute(String, String, String, String, String)
     */
    public void addAttribute(
        String uri,
        String localName,
        String rawName,
        String type,
        String value,
        boolean XSLAttribute)
    {
        // do nothing, just forget all about the attribute
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException
    {
        // do nothing
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#endElement(String)
     */
    public void endElement(String elemName) throws SAXException
    {
        if (m_tracer != null)
            super.fireEndElem(elemName);
    }

    /**
     * From XSLTC
     * <p>
     *  来自XSLTC
     * 
     */
    public void startElement(
    String elementNamespaceURI,
    String elementLocalName,
    String elementName)
    throws SAXException
    {
        if (m_needToCallStartDocument)
            startDocumentInternal();
        // time to fire off startlement event.
        if (m_tracer != null) {
            super.fireStartElem(elementName);
            this.firePseudoAttributes();
        }

        return;
    }


    /**
     * From XSLTC
     * <p>
     *  来自XSLTC
     * 
     */
    public void characters(String characters)
    throws SAXException
    {
        final int length = characters.length();
        if (length > m_charsBuff.length)
        {
            m_charsBuff = new char[length*2 + 1];
        }
        characters.getChars(0, length, m_charsBuff, 0);
        characters(m_charsBuff, 0, length);
    }


    /**
     * From XSLTC
     * <p>
     *  来自XSLTC
     * 
     */
    public void addAttribute(String name, String value)
    {
        // do nothing, forget about the attribute
    }

    /**
     * Add a unique attribute
     * <p>
     *  添加唯一属性
     */
    public void addUniqueAttribute(String qName, String value, int flags)
        throws SAXException
    {
        // do nothing, forget about the attribute
    }

    public boolean startPrefixMapping(
        String prefix,
        String uri,
        boolean shouldFlush)
        throws SAXException
    {
        // no namespace support for HTML
        return false;
    }


    public void startPrefixMapping(String prefix, String uri)
        throws org.xml.sax.SAXException
    {
        // no namespace support for HTML
    }


    public void namespaceAfterStartElement(
        final String prefix,
        final String uri)
        throws SAXException
    {
        // no namespace support for HTML
    }

    public void flushPending() throws org.xml.sax.SAXException
    {
            if (m_needToCallStartDocument)
            {
                startDocumentInternal();
                m_needToCallStartDocument = false;
            }
    }
}
