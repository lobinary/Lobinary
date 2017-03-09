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


// Aug 21, 2000:
//  Added ability to omit DOCTYPE declaration.
//  Reported by Lars Martin <lars@smb-tec.com>
// Aug 25, 2000:
//  Added ability to omit comments.
//  Contributed by Anupam Bagchi <abagchi@jtcsv.com>


package com.sun.org.apache.xml.internal.serialize;


import java.io.UnsupportedEncodingException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLDocument;


/**
 * Specifies an output format to control the serializer. Based on the
 * XSLT specification for output format, plus additional parameters.
 * Used to select the suitable serializer and determine how the
 * document should be formatted on output.
 * <p>
 * The two interesting constructors are:
 * <ul>
 * <li>{@link #OutputFormat(String,String,boolean)} creates a format
 *  for the specified method (XML, HTML, Text, etc), encoding and indentation
 * <li>{@link #OutputFormat(Document,String,boolean)} creates a format
 *  compatible with the document type (XML, HTML, Text, etc), encoding and
 *  indentation
 * </ul>
 *
 *
 * <p>
 *  指定用于控制序列化程序的输出格式。基于输出格式的XSLT规范,以及附加参数。用于选择合适的序列化程序,并确定如何在输出上格式化文档。
 * <p>
 *  这两个有趣的构造函数是：
 * <ul>
 *  <li> {@ link #OutputFormat(String,String,boolean)}为指定的方法(XML,HTML,Text等)创建格式,编码和缩进<li> {@ link #OutputFormat(Document,String,boolean )}
 * 创建与文档类型(XML,HTML,Text等)兼容的格式,编码和缩进。
 * </ul>
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 *         <a href="mailto:visco@intalio.com">Keith Visco</a>
 * @see Serializer
 * @see Method
 * @see LineSeparator
 */
public class OutputFormat
{


    public static class DTD
    {

        /**
         * Public identifier for HTML 4.01 (Strict) document type.
         * <p>
         *  HTML 4.01(严格)文档类型的公共标识符。
         * 
         */
        public static final String HTMLPublicId = "-//W3C//DTD HTML 4.01//EN";

        /**
         * System identifier for HTML 4.01 (Strict) document type.
         * <p>
         *  HTML 4.01(严格)文档类型的系统标识符。
         * 
         */
        public static final String HTMLSystemId =
            "http://www.w3.org/TR/html4/strict.dtd";

        /**
         * Public identifier for XHTML 1.0 (Strict) document type.
         * <p>
         *  XHTML 1.0(严格)文档类型的公共标识符。
         * 
         */
        public static final String XHTMLPublicId =
            "-//W3C//DTD XHTML 1.0 Strict//EN";

        /**
         * System identifier for XHTML 1.0 (Strict) document type.
         * <p>
         * XHTML 1.0(严格)文档类型的系统标识符。
         * 
         */
        public static final String XHTMLSystemId =
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";

    }


    public static class Defaults
    {

        /**
         * If indentation is turned on, the default identation
         * level is 4.
         *
         * <p>
         *  如果打开缩进,默认标识级别为4。
         * 
         * 
         * @see #setIndenting(boolean)
         */
        public static final int Indent = 4;

        /**
         * The default encoding for Web documents it UTF-8.
         *
         * <p>
         *  Web文档的默认编码为UTF-8。
         * 
         * 
         * @see #getEncoding()
         */
        public static final String Encoding = "UTF-8";

        /**
         * The default line width at which to break long lines
         * when identing. This is set to 72.
         * <p>
         *  标识时断开长行的默认线宽。这被设置为72。
         * 
         */
        public static final int LineWidth = 72;

    }


    /**
     * Holds the output method specified for this document,
     * or null if no method was specified.
     * <p>
     *  保留为此文档指定的输出方法,如果未指定方法,则为null。
     * 
     */
    private String _method;


    /**
     * Specifies the version of the output method.
     * <p>
     *  指定输出方法的版本。
     * 
     */
    private String _version;


    /**
     * The indentation level, or zero if no indentation
     * was requested.
     * <p>
     *  缩进级别,如果没有请求缩进,则为零。
     * 
     */
    private int _indent = 0;


    /**
     * The encoding to use, if an input stream is used.
     * The default is always UTF-8.
     * <p>
     *  要使用的编码,如果使用输入流。默认值始终为UTF-8。
     * 
     */
    private String _encoding = Defaults.Encoding;

    /**
     * The EncodingInfo instance for _encoding.
     * <p>
     *  _encoding的EncodingInfo实例。
     * 
     */
    private EncodingInfo _encodingInfo = null;

    // whether java names for encodings are permitted
    private boolean _allowJavaNames = false;

    /**
     * The specified media type or null.
     * <p>
     *  指定的媒体类型或null。
     * 
     */
    private String _mediaType;


    /**
     * The specified document type system identifier, or null.
     * <p>
     *  指定的文档类型系统标识符,或为null。
     * 
     */
    private String _doctypeSystem;


    /**
     * The specified document type public identifier, or null.
     * <p>
     *  指定的文档类型为public标识符,或为null。
     * 
     */
    private String _doctypePublic;


    /**
     * Ture if the XML declaration should be ommited;
     * <p>
     *  确保XML声明应该被忽略;
     * 
     */
    private boolean _omitXmlDeclaration = false;


    /**
     * Ture if the DOCTYPE declaration should be ommited;
     * <p>
     *  确定DOCTYPE声明是否应该被忽略;
     * 
     */
    private boolean _omitDoctype = false;


    /**
     * Ture if comments should be ommited;
     * <p>
     *  确定是否应该贬低评论;
     * 
     */
    private boolean _omitComments = false;


    /**
     * Ture if the comments should be ommited;
     * <p>
     *  确保意见应该被贬低;
     * 
     */
    private boolean _stripComments = false;


    /**
     * True if the document type should be marked as standalone.
     * <p>
     *  如果文档类型应标记为独立,则为True。
     * 
     */
    private boolean _standalone = false;


    /**
     * List of element tag names whose text node children must
     * be output as CDATA.
     * <p>
     *  元素标记名称的列表,其文本节点子代必须作为CDATA输出。
     * 
     */
    private String[] _cdataElements;


    /**
     * List of element tag names whose text node children must
     * be output unescaped.
     * <p>
     *  元素标记名称的列表,其文本节点子代必须非转义输出。
     * 
     */
    private String[] _nonEscapingElements;


    /**
     * The selected line separator.
     * <p>
     *  所选行分隔符。
     * 
     */
    private String _lineSeparator = LineSeparator.Web;


    /**
     * The line width at which to wrap long lines when indenting.
     * <p>
     *  缩进时换行长线的线宽。
     * 
     */
    private int _lineWidth = Defaults.LineWidth;


    /**
     * True if spaces should be preserved in elements that do not
     * specify otherwise, or specify the default behavior.
     * <p>
     *  如果应在未指定的元素中保留空格,则为True,或指定默认行为。
     * 
     */
    private boolean _preserve = false;
        /** If true, an empty string valued attribute is output as "". If false and
         * and we are using the HTMLSerializer, then only the attribute name is
         * serialized. Defaults to false for backwards compatibility.
         * <p>
         *  而我们正在使用HTMLSerializer,那么只有属性名称被序列化。向后兼容性默认为false。
         * 
         */
        private boolean _preserveEmptyAttributes = false;

    /**
     * Constructs a new output format with the default values.
     * <p>
     * 使用默认值构造新的输出格式。
     * 
     */
    public OutputFormat()
    {
    }


    /**
     * Constructs a new output format with the default values for
     * the specified method and encoding. If <tt>indent</tt>
     * is true, the document will be pretty printed with the default
     * indentation level and default line wrapping.
     *
     * <p>
     *  使用指定方法和编码的默认值构造新的输出格式。如果<tt>缩进</tt>为true,则文档将以默认缩进级别和默认换行符打印。
     * 
     * 
     * @param method The specified output method
     * @param encoding The specified encoding
     * @param indenting True for pretty printing
     * @see #setEncoding
     * @see #setIndenting
     * @see #setMethod
     */
    public OutputFormat( String method, String encoding, boolean indenting )
    {
        setMethod( method );
        setEncoding( encoding );
        setIndenting( indenting );
    }


    /**
     * Constructs a new output format with the proper method,
     * document type identifiers and media type for the specified
     * document.
     *
     * <p>
     *  使用适当的方法,文档类型标识符和指定文档的介质类型构造新的输出格式。
     * 
     * 
     * @param doc The document to output
     * @see #whichMethod
     */
    public OutputFormat( Document doc )
    {
        setMethod( whichMethod( doc ) );
        setDoctype( whichDoctypePublic( doc ), whichDoctypeSystem( doc ) );
        setMediaType( whichMediaType( getMethod() ) );
    }


    /**
     * Constructs a new output format with the proper method,
     * document type identifiers and media type for the specified
     * document, and with the specified encoding. If <tt>indent</tt>
     * is true, the document will be pretty printed with the default
     * indentation level and default line wrapping.
     *
     * <p>
     *  使用适当的方法,指定文档的文档类型标识符和媒体类型以及指定的编码构造新的输出格式。如果<tt>缩进</tt>为true,则文档将以默认缩进级别和默认换行符打印。
     * 
     * 
     * @param doc The document to output
     * @param encoding The specified encoding
     * @param indenting True for pretty printing
     * @see #setEncoding
     * @see #setIndenting
     * @see #whichMethod
     */
    public OutputFormat( Document doc, String encoding, boolean indenting )
    {
        this( doc );
        setEncoding( encoding );
        setIndenting( indenting );
    }


    /**
     * Returns the method specified for this output format.
     * Typically the method will be <tt>xml</tt>, <tt>html</tt>
     * or <tt>text</tt>, but it might be other values.
     * If no method was specified, null will be returned
     * and the most suitable method will be determined for
     * the document by calling {@link #whichMethod}.
     *
     * <p>
     *  返回为此输出格式指定的方法。通常,该方法将是<tt> xml </tt>,<tt> html </tt>或<tt> text </tt>,但它可能是其他值。
     * 如果没有指定方法,将返回null,并且将通过调用{@link #whichMethod}为文档确定最合适的方法。
     * 
     * 
     * @return The specified output method, or null
     */
    public String getMethod()
    {
        return _method;
    }


    /**
     * Sets the method for this output format.
     *
     * <p>
     *  设置此输出格式的方法。
     * 
     * 
     * @see #getMethod
     * @param method The output method, or null
     */
    public void setMethod( String method )
    {
        _method = method;
    }


    /**
     * Returns the version for this output method.
     * If no version was specified, will return null
     * and the default version number will be used.
     * If the serializerr does not support that particular
     * version, it should default to a supported version.
     *
     * <p>
     *  返回此输出方法的版本。如果未指定版本,将返回null,将使用默认版本号。如果serializerr不支持该特定版本,它应该默认为支持的版本。
     * 
     * 
     * @return The specified method version, or null
     */
    public String getVersion()
    {
        return _version;
    }


    /**
     * Sets the version for this output method.
     * For XML the value would be "1.0", for HTML
     * it would be "4.0".
     *
     * <p>
     *  设置此输出方法的版本。对于XML,值将是"1.0",对于HTML,它将是"4.0"。
     * 
     * 
     * @see #getVersion
     * @param version The output method version, or null
     */
    public void setVersion( String version )
    {
        _version = version;
    }


    /**
     * Returns the indentation specified. If no indentation
     * was specified, zero is returned and the document
     * should not be indented.
     *
     * <p>
     * 返回指定的缩进。如果未指定缩进,则返回零,并且文档不应缩进。
     * 
     * 
     * @return The indentation or zero
     * @see #setIndenting
     */
    public int getIndent()
    {
        return _indent;
    }


    /**
     * Returns true if indentation was specified.
     * <p>
     *  如果指定了缩进,则返回true。
     * 
     */
    public boolean getIndenting()
    {
        return ( _indent > 0 );
    }


    /**
     * Sets the indentation. The document will not be
     * indented if the indentation is set to zero.
     * Calling {@link #setIndenting} will reset this
     * value to zero (off) or the default (on).
     *
     * <p>
     *  设置缩进。如果缩进设置为零,则文档不会缩进。调用{@link #setIndenting}会将此值重置为零(关闭)或默认值(打开)。
     * 
     * 
     * @param indent The indentation, or zero
     */
    public void setIndent( int indent )
    {
        if ( indent < 0 )
            _indent = 0;
        else
            _indent = indent;
    }


    /**
     * Sets the indentation on and off. When set on, the default
     * indentation level and default line wrapping is used
     * (see {@link Defaults#Indent} and {@link Defaults#LineWidth}).
     * To specify a different indentation level or line wrapping,
     * use {@link #setIndent} and {@link #setLineWidth}.
     *
     * <p>
     *  设置打开和关闭缩进。当设置为on时,使用默认缩进级别和默认换行(参见{@link Defaults#Indent}和{@link Defaults#LineWidth})。
     * 要指定不同的缩进级别或换行符,请使用{@link #setIndent}和{@link #setLineWidth}。
     * 
     * 
     * @param on True if indentation should be on
     */
    public void setIndenting( boolean on )
    {
        if ( on ) {
            _indent = Defaults.Indent;
            _lineWidth = Defaults.LineWidth;
        } else {
            _indent = 0;
            _lineWidth = 0;
        }
    }


    /**
     * Returns the specified encoding. If no encoding was
     * specified, the default is always "UTF-8".
     *
     * <p>
     *  返回指定的编码。如果未指定编码,则默认值始终为"UTF-8"。
     * 
     * 
     * @return The encoding
     */
    public String getEncoding()
    {
        return _encoding;
    }


    /**
     * Sets the encoding for this output method. If no
     * encoding was specified, the default is always "UTF-8".
     * Make sure the encoding is compatible with the one
     * used by the {@link java.io.Writer}.
     *
     * <p>
     *  设置此输出方法的编码。如果未指定编码,则默认值始终为"UTF-8"。确保编码与{@link java.io.Writer}使用的编码兼容。
     * 
     * 
     * @see #getEncoding
     * @param encoding The encoding, or null
     */
    public void setEncoding( String encoding )
    {
        _encoding = encoding;
        _encodingInfo = null;
    }

    /**
     * Sets the encoding for this output method with an <code>EncodingInfo</code>
     * instance.
     * <p>
     *  使用<code> EncodingInfo </code>实例设置此输出方法的编码。
     * 
     */
    public void setEncoding(EncodingInfo encInfo) {
        _encoding = encInfo.getIANAName();
        _encodingInfo = encInfo;
    }

    /**
     * Returns an <code>EncodingInfo<code> instance for the encoding.
     *
     * <p>
     *  返回用于编码的<code> EncodingInfo <code>实例。
     * 
     * 
     * @see #setEncoding
     */
    public EncodingInfo getEncodingInfo() throws UnsupportedEncodingException {
        if (_encodingInfo == null)
            _encodingInfo = Encodings.getEncodingInfo(_encoding, _allowJavaNames);
        return _encodingInfo;
    }

    /**
     * Sets whether java encoding names are permitted
     * <p>
     *  设置是否允许使用java编码名称
     * 
     */
    public void setAllowJavaNames (boolean allow) {
        _allowJavaNames = allow;
    }

    /**
     * Returns whether java encoding names are permitted
     * <p>
     *  返回是否允许使用java编码名称
     * 
     */
    public boolean setAllowJavaNames () {
        return _allowJavaNames;
    }

    /**
     * Returns the specified media type, or null.
     * To determine the media type based on the
     * document type, use {@link #whichMediaType}.
     *
     * <p>
     *  返回指定的媒体类型,或为null。要根据文档类型确定介质类型,请使用{@link #whichMediaType}。
     * 
     * 
     * @return The specified media type, or null
     */
    public String getMediaType()
    {
        return _mediaType;
    }


    /**
     * Sets the media type.
     *
     * <p>
     *  设置介质类型。
     * 
     * 
     * @see #getMediaType
     * @param mediaType The specified media type
     */
    public void setMediaType( String mediaType )
    {
        _mediaType = mediaType;
    }


    /**
     * Sets the document type public and system identifiers.
     * Required only if the DOM Document or SAX events do not
     * specify the document type, and one must be present in
     * the serialized document. Any document type specified
     * by the DOM Document or SAX events will override these
     * values.
     *
     * <p>
     * 设置文档类型public和系统标识符。仅当DOM文档或SAX事件未指定文档类型,且必须在序列化文档中存在时才需要。 DOM文档或SAX事件指定的任何文档类型都将覆盖这些值。
     * 
     * 
     * @param publicId The public identifier, or null
     * @param systemId The system identifier, or null
     */
    public void setDoctype( String publicId, String systemId )
    {
        _doctypePublic = publicId;
        _doctypeSystem = systemId;
    }


    /**
     * Returns the specified document type public identifier,
     * or null.
     * <p>
     *  返回指定的文档类型public identifier,或null。
     * 
     */
    public String getDoctypePublic()
    {
        return _doctypePublic;
    }


    /**
     * Returns the specified document type system identifier,
     * or null.
     * <p>
     *  返回指定的文档类型系统标识,或null。
     * 
     */
    public String getDoctypeSystem()
    {
        return _doctypeSystem;
    }


    /**
     * Returns true if comments should be ommited.
     * The default is false.
     * <p>
     *  如果注释应该被忽略,则返回true。默认值为false。
     * 
     */
    public boolean getOmitComments()
    {
        return _omitComments;
    }


    /**
     * Sets comment omitting on and off.
     *
     * <p>
     *  设置忽略打开和关闭的注释。
     * 
     * 
     * @param omit True if comments should be ommited
     */
    public void setOmitComments( boolean omit )
    {
        _omitComments = omit;
    }


    /**
     * Returns true if the DOCTYPE declaration should
     * be ommited. The default is false.
     * <p>
     *  如果应该忽略DOCTYPE声明,则返回true。默认值为false。
     * 
     */
    public boolean getOmitDocumentType()
    {
        return _omitDoctype;
    }


    /**
     * Sets DOCTYPE declaration omitting on and off.
     *
     * <p>
     *  设置DOCTYPE声明省略开和关。
     * 
     * 
     * @param omit True if DOCTYPE declaration should be ommited
     */
    public void setOmitDocumentType( boolean omit )
    {
        _omitDoctype = omit;
    }


    /**
     * Returns true if the XML document declaration should
     * be ommited. The default is false.
     * <p>
     *  如果XML文档声明应该被忽略,则返回true。默认值为false。
     * 
     */
    public boolean getOmitXMLDeclaration()
    {
        return _omitXmlDeclaration;
    }


    /**
     * Sets XML declaration omitting on and off.
     *
     * <p>
     *  设置XML声明省略开和关。
     * 
     * 
     * @param omit True if XML declaration should be ommited
     */
    public void setOmitXMLDeclaration( boolean omit )
    {
        _omitXmlDeclaration = omit;
    }


    /**
     * Returns true if the document type is standalone.
     * The default is false.
     * <p>
     *  如果文档类型是独立的,则返回true。默认值为false。
     * 
     */
    public boolean getStandalone()
    {
        return _standalone;
    }


    /**
     * Sets document DTD standalone. The public and system
     * identifiers must be null for the document to be
     * serialized as standalone.
     *
     * <p>
     *  设置文档DTD独立。公共和系统标识符必须为空,才能将文档序列化为独立的。
     * 
     * 
     * @param standalone True if document DTD is standalone
     */
    public void setStandalone( boolean standalone )
    {
        _standalone = standalone;
    }


    /**
     * Returns a list of all the elements whose text node children
     * should be output as CDATA, or null if no such elements were
     * specified.
     * <p>
     *  返回其文本节点child应作为CDATA输出的所有元素的列表,如果没有指定此类元素,则返回null。
     * 
     */
    public String[] getCDataElements()
    {
        return _cdataElements;
    }


    /**
     * Returns true if the text node children of the given elements
     * should be output as CDATA.
     *
     * <p>
     *  如果给定元素的文本节点子元素应输出为CDATA,则返回true。
     * 
     * 
     * @param tagName The element's tag name
     * @return True if should serialize as CDATA
     */
    public boolean isCDataElement( String tagName )
    {
        int i;

        if ( _cdataElements == null )
            return false;
        for ( i = 0 ; i < _cdataElements.length ; ++i )
            if ( _cdataElements[ i ].equals( tagName ) )
                return true;
        return false;
    }


    /**
     * Sets the list of elements for which text node children
     * should be output as CDATA.
     *
     * <p>
     *  设置文本节点子元素应输出为CDATA的元素列表。
     * 
     * 
     * @param cdataElements List of CDATA element tag names
     */
    public void setCDataElements( String[] cdataElements )
    {
        _cdataElements = cdataElements;
    }


    /**
     * Returns a list of all the elements whose text node children
     * should be output unescaped (no character references), or null
     * if no such elements were specified.
     * <p>
     *  返回其文本节点child应该输出非转义(没有字符引用)的所有元素的列表,如果没有指定这样的元素,则返回null。
     * 
     */
    public String[] getNonEscapingElements()
    {
        return _nonEscapingElements;
    }


    /**
     * Returns true if the text node children of the given elements
     * should be output unescaped.
     *
     * <p>
     * 如果给定元素的文本节点儿童应该输出非转义,则返回true。
     * 
     * 
     * @param tagName The element's tag name
     * @return True if should serialize unescaped
     */
    public boolean isNonEscapingElement( String tagName )
    {
        int i;

        if ( _nonEscapingElements == null ) {
            return false;
        }
        for ( i = 0 ; i < _nonEscapingElements.length ; ++i )
            if ( _nonEscapingElements[ i ].equals( tagName ) )
                return true;
        return false;
    }


    /**
     * Sets the list of elements for which text node children
     * should be output unescaped (no character references).
     *
     * <p>
     *  设置应该为其输出文本节点子元素的元素列表(无字符引用)。
     * 
     * 
     * @param nonEscapingElements List of unescaped element tag names
     */
    public void setNonEscapingElements( String[] nonEscapingElements )
    {
        _nonEscapingElements = nonEscapingElements;
    }



    /**
     * Returns a specific line separator to use. The default is the
     * Web line separator (<tt>\n</tt>). A string is returned to
     * support double codes (CR + LF).
     *
     * <p>
     *  返回要使用的特定行分隔符。默认值为Web行分隔符(<tt> \ n </tt>)。返回一个字符串以支持双重代码(CR + LF)。
     * 
     * 
     * @return The specified line separator
     */
    public String getLineSeparator()
    {
        return _lineSeparator;
    }


    /**
     * Sets the line separator. The default is the Web line separator
     * (<tt>\n</tt>). The machine's line separator can be obtained
     * from the system property <tt>line.separator</tt>, but is only
     * useful if the document is edited on machines of the same type.
     * For general documents, use the Web line separator.
     *
     * <p>
     *  设置线分隔符。默认值为Web行分隔符(<tt> \ n </tt>)。机器的行分隔符可以从系统属性<tt> line.separator </tt>获取,但仅当文档在相同类型的机器上编辑时有用。
     * 对于一般文档,请使用Web行分隔符。
     * 
     * 
     * @param lineSeparator The specified line separator
     */
    public void setLineSeparator( String lineSeparator )
    {
        if ( lineSeparator == null )
            _lineSeparator =  LineSeparator.Web;
        else
            _lineSeparator = lineSeparator;
    }


    /**
     * Returns true if the default behavior for this format is to
     * preserve spaces. All elements that do not specify otherwise
     * or specify the default behavior will be formatted based on
     * this rule. All elements that specify space preserving will
     * always preserve space.
     * <p>
     *  如果此格式的默认行为是保留空格,则返回true。所有未指定或指定默认行为的元素将基于此规则进行格式化。指定空间保留的所有元素将始终保留空间。
     * 
     */
    public boolean getPreserveSpace()
    {
        return _preserve;
    }


    /**
     * Sets space preserving as the default behavior. The default is
     * space stripping and all elements that do not specify otherwise
     * or use the default value will not preserve spaces.
     *
     * <p>
     *  将空间保留设置为默认行为。默认值为空间剥离,所有未指定或使用默认值的元素将不保留空格。
     * 
     * 
     * @param preserve True if spaces should be preserved
     */
    public void setPreserveSpace( boolean preserve )
    {
        _preserve = preserve;
    }


    /**
     * Return the selected line width for breaking up long lines.
     * When indenting, and only when indenting, long lines will be
     * broken at space boundaries based on this line width.
     * No line wrapping occurs if this value is zero.
     * <p>
     *  返回所选的线宽以分解长线。当缩进时,并且仅当缩进时,基于该线宽,长线将在空间边界处被破坏。如果此值为零,则不会进行换行。
     * 
     */
    public int getLineWidth()
    {
        return _lineWidth;
    }


    /**
     * Sets the line width. If zero then no line wrapping will
     * occur. Calling {@link #setIndenting} will reset this
     * value to zero (off) or the default (on).
     *
     * <p>
     *  设置线宽。如果为零,则不会发生换行。调用{@link #setIndenting}会将此值重置为零(关闭)或默认值(打开)。
     * 
     * 
     * @param lineWidth The line width to use, zero for default
     * @see #getLineWidth
     * @see #setIndenting
     */
    public void setLineWidth( int lineWidth )
    {
        if ( lineWidth <= 0 )
            _lineWidth = 0;
        else
            _lineWidth = lineWidth;
    }
        /**
         * Returns the preserveEmptyAttribute flag. If flag is false, then'
         * attributes with empty string values are output as the attribute
         * name only (in HTML mode).
         * <p>
         * 返回preserveEmptyAttribute标志。如果flag为false,那么带有空字符串值的属性只作为属性名输出(在HTML模式下)。
         * 
         * 
         * @return preserve the preserve flag
         */     public boolean getPreserveEmptyAttributes () {          return _preserveEmptyAttributes;        }       /**
         * Sets the preserveEmptyAttribute flag. If flag is false, then'
         * attributes with empty string values are output as the attribute
         * name only (in HTML mode).
         * <p>
         *  设置preserveEmptyAttribute标志。如果flag为false,那么带有空字符串值的属性只作为属性名输出(在HTML模式下)。
         * 
         * 
         * @param preserve the preserve flag
         */     public void setPreserveEmptyAttributes (boolean preserve) {             _preserveEmptyAttributes = preserve;    }

    /**
     * Returns the last printable character based on the selected
     * encoding. Control characters and non-printable characters
     * are always printed as character references.
     * <p>
     *  根据所选的编码返回最后一个可打印字符。控制字符和不可打印字符总是作为字符引用打印。
     * 
     */
    public char getLastPrintable()
    {
        if ( getEncoding() != null &&
             ( getEncoding().equalsIgnoreCase( "ASCII" ) ) )
            return 0xFF;
        else
            return 0xFFFF;
    }


    /**
     * Determine the output method for the specified document.
     * If the document is an instance of {@link org.w3c.dom.html.HTMLDocument}
     * then the method is said to be <tt>html</tt>. If the root
     * element is 'html' and all text nodes preceding the root
     * element are all whitespace, then the method is said to be
     * <tt>html</tt>. Otherwise the method is <tt>xml</tt>.
     *
     * <p>
     *  确定指定文档的输出方法。如果文档是{@link org.w3c.dom.html.HTMLDocument}的实例,则该方法称为<tt> html </tt>。
     * 如果根元素是"html",并且根元素之前的所有文本节点都是空格,则该方法被称为<tt> html </tt>。否则方法为<tt> xml </tt>。
     * 
     * 
     * @param doc The document to check
     * @return The suitable method
     */
    public static String whichMethod( Document doc )
    {
        Node    node;
        String  value;
        int     i;

        // If document is derived from HTMLDocument then the default
        // method is html.
        if ( doc instanceof HTMLDocument )
            return Method.HTML;

        // Lookup the root element and the text nodes preceding it.
        // If root element is html and all text nodes contain whitespace
        // only, the method is html.

        // FIXME (SM) should we care about namespaces here?

        node = doc.getFirstChild();
        while (node != null) {
            // If the root element is html, the method is html.
            if ( node.getNodeType() == Node.ELEMENT_NODE ) {
                if ( node.getNodeName().equalsIgnoreCase( "html" ) ) {
                    return Method.HTML;
                } else if ( node.getNodeName().equalsIgnoreCase( "root" ) ) {
                    return Method.FOP;
                } else {
                    return Method.XML;
                }
            } else if ( node.getNodeType() == Node.TEXT_NODE ) {
                // If a text node preceding the root element contains
                // only whitespace, this might be html, otherwise it's
                // definitely xml.
                value = node.getNodeValue();
                for ( i = 0 ; i < value.length() ; ++i )
                    if ( value.charAt( i ) != 0x20 && value.charAt( i ) != 0x0A &&
                         value.charAt( i ) != 0x09 && value.charAt( i ) != 0x0D )
                        return Method.XML;
            }
            node = node.getNextSibling();
        }
        // Anything else, the method is xml.
        return Method.XML;
    }


    /**
     * Returns the document type public identifier
     * specified for this document, or null.
     * <p>
     *  返回为此文档指定的文档类型公共标识符,或null。
     * 
     */
    public static String whichDoctypePublic( Document doc )
    {
        DocumentType doctype;

           /*  DOM Level 2 was introduced into the code base*/
           doctype = doc.getDoctype();
           if ( doctype != null ) {
           // Note on catch: DOM Level 1 does not specify this method
           // and the code will throw a NoSuchMethodError
           try {
           return doctype.getPublicId();
           } catch ( Error except ) {  }
           }

        if ( doc instanceof HTMLDocument )
            return DTD.XHTMLPublicId;
        return null;
    }


    /**
     * Returns the document type system identifier
     * specified for this document, or null.
     * <p>
     *  返回为此文档指定的文档类型系统标识符,或null。
     * 
     */
    public static String whichDoctypeSystem( Document doc )
    {
        DocumentType doctype;

        /* DOM Level 2 was introduced into the code base*/
           doctype = doc.getDoctype();
           if ( doctype != null ) {
           // Note on catch: DOM Level 1 does not specify this method
           // and the code will throw a NoSuchMethodError
           try {
           return doctype.getSystemId();
           } catch ( Error except ) { }
           }

        if ( doc instanceof HTMLDocument )
            return DTD.XHTMLSystemId;
        return null;
    }


    /**
     * Returns the suitable media format for a document
     * output with the specified method.
     * <p>
     *  返回使用指定方法输出的文档的合适介质格式。
     */
    public static String whichMediaType( String method )
    {
        if ( method.equalsIgnoreCase( Method.XML ) )
            return "text/xml";
        if ( method.equalsIgnoreCase( Method.HTML ) )
            return "text/html";
        if ( method.equalsIgnoreCase( Method.XHTML ) )
            return "text/html";
        if ( method.equalsIgnoreCase( Method.TEXT ) )
            return "text/plain";
        if ( method.equalsIgnoreCase( Method.FOP ) )
            return "application/pdf";
        return null;
    }


}
