/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004,2005 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */


// Sep 14, 2000:
//  Fixed comments to preserve whitespaces and add a line break
//  when indenting. Reported by Gervase Markham <gerv@gerv.net>
// Sep 14, 2000:
//  Fixed serializer to report IO exception directly, instead at
//  the end of document processing.
//  Reported by Patrick Higgins <phiggins@transzap.com>
// Sep 13, 2000:
//   CR in character data will print as &#0D;
// Aug 25, 2000:
//   Fixed processing instruction printing inside element content
//   to not escape content. Reported by Mikael Staldal
//   <d96-mst@d.kth.se>
// Aug 25, 2000:
//   Added ability to omit comments.
//   Contributed by Anupam Bagchi <abagchi@jtcsv.com>
// Aug 26, 2000:
//   Fixed bug in newline handling when preserving spaces.
//   Contributed by Mike Dusseault <mdusseault@home.com>
// Aug 29, 2000:
//   Fixed state.unescaped not being set to false when
//   entering element state.
//   Reported by Lowell Vaughn <lvaughn@agillion.com>


package com.sun.org.apache.xml.internal.serialize;


import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
import com.sun.org.apache.xerces.internal.dom.DOMLocatorImpl;
import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
import com.sun.org.apache.xerces.internal.util.XMLChar;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSSerializerFilter;
import org.w3c.dom.traversal.NodeFilter;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

/**
 * Base class for a serializer supporting both DOM and SAX pretty
 * serializing of XML/HTML/XHTML documents. Derives classes perform
 * the method-specific serializing, this class provides the common
 * serializing mechanisms.
 * <p>
 * The serializer must be initialized with the proper writer and
 * output format before it can be used by calling {@link #setOutputCharStream}
 * or {@link #setOutputByteStream} for the writer and {@link #setOutputFormat}
 * for the output format.
 * <p>
 * The serializer can be reused any number of times, but cannot
 * be used concurrently by two threads.
 * <p>
 * If an output stream is used, the encoding is taken from the
 * output format (defaults to <tt>UTF-8</tt>). If a writer is
 * used, make sure the writer uses the same encoding (if applies)
 * as specified in the output format.
 * <p>
 * The serializer supports both DOM and SAX. DOM serializing is done
 * by calling {@link #serialize(Document)} and SAX serializing is done by firing
 * SAX events and using the serializer as a document handler.
 * This also applies to derived class.
 * <p>
 * If an I/O exception occurs while serializing, the serializer
 * will not throw an exception directly, but only throw it
 * at the end of serializing (either DOM or SAX's {@link
 * org.xml.sax.DocumentHandler#endDocument}.
 * <p>
 * For elements that are not specified as whitespace preserving,
 * the serializer will potentially break long text lines at space
 * boundaries, indent lines, and serialize elements on separate
 * lines. Line terminators will be regarded as spaces, and
 * spaces at beginning of line will be stripped.
 * <p>
 * When indenting, the serializer is capable of detecting seemingly
 * element content, and serializing these elements indented on separate
 * lines. An element is serialized indented when it is the first or
 * last child of an element, or immediate following or preceding
 * another element.
 *
 *
 * <p>
 *  支持DOM和SAX的序列化的基类相当串行化XML / HTML / XHTML文档。派生类执行方法特定的序列化,此类提供常见的序列化机制。
 * <p>
 *  在通过调用{@link #setOutputCharStream}或{@link #setOutputByteStream}作为写入器和使用{@link #setOutputFormat}作为输出格式
 * ,序列化器必须使用正确的写入器和输出格式进行初始化。
 * <p>
 *  序列化器可以被重用任何次数,但不能由两个线程并发使用。
 * <p>
 *  如果使用输出流,则从输出格式(默认为<tt> UTF-8 </tt>)获取编码。如果使用写入程序,请确保写入程序使用与输出格式中指定的相同的编码(如果适用)。
 * <p>
 * 序列化器支持DOM和SAX。 DOM序列化通过调用{@link #serialize(Document)}来完成,SAX序列化通过触发SAX事件并使用序列化器作为文档处理程序来完成。这也适用于派生类。
 * <p>
 *  如果在序列化时发生I / O异常,则序列化器不会直接抛出异常,而只是在序列化结束时抛出异常(DOM或SAX的{@link org.xml.sax.DocumentHandler#endDocument}
 * 。
 * <p>
 *  对于未指定为空白保留的元素,序列化程序可能会在空格边界,缩进行和序列化元素在单独的行上断开长文本行。行终止符将被视为空格,行开始处的空格将被删除。
 * <p>
 *  当缩进时,序列化器能够检测看似元素的内容,并且将这些元素序列化在单独的行上。当元素是元素的第一个或最后一个子元素,或紧跟在另一个元素之后或之前时,元素被序列化缩进。
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 * @author <a href="mailto:rahul.srivastava@sun.com">Rahul Srivastava</a>
 * @author Elena Litani, IBM
 * @author Sunitha Reddy, Sun Microsystems
 * @see Serializer
 * @see LSSerializer
 */
public abstract class BaseMarkupSerializer
    implements ContentHandler, DocumentHandler, LexicalHandler,
               DTDHandler, DeclHandler, DOMSerializer, Serializer
{

    // DOM L3 implementation
    protected short features = 0xFFFFFFFF;
    protected DOMErrorHandler fDOMErrorHandler;
    protected final DOMErrorImpl fDOMError = new DOMErrorImpl();
    protected LSSerializerFilter fDOMFilter;

    protected EncodingInfo _encodingInfo;


    /**
     * Holds array of all element states that have been entered.
     * The array is automatically resized. When leaving an element,
     * it's state is not removed but reused when later returning
     * to the same nesting level.
     * <p>
     *  保存已输入的所有元素状态的数组。将自动调整数组大小。当离开元素时,它的状态不会被删除,而是在以后返回到相同的嵌套级别时重用。
     * 
     */
    private ElementState[]  _elementStates;


    /**
     * The index of the next state to place in the array,
     * or one plus the index of the current state. When zero,
     * we are in no state.
     * <p>
     *  要放置在数组中的下一个状态的索引,或者一个加上当前状态的索引。当为零时,我们处于无状态。
     * 
     */
    private int             _elementStateCount;


    /**
     * Vector holding comments and PIs that come before the root
     * element (even after it), see {@link #serializePreRoot}.
     * <p>
     *  包含在根元素之前(甚至之后)的注释和PI的向量,请参阅{@link #serializePreRoot}。
     * 
     */
    private Vector          _preRoot;


    /**
     * If the document has been started (header serialized), this
     * flag is set to true so it's not started twice.
     * <p>
     * 如果文档已经启动(头序列化),则此标志设置为true,因此它不会启动两次。
     * 
     */
    protected boolean       _started;


    /**
     * True if the serializer has been prepared. This flag is set
     * to false when the serializer is reset prior to using it,
     * and to true after it has been prepared for usage.
     * <p>
     *  如果序列化器已准备就为true。当串行器在使用它之前被复位时,该标志被设置为假,而在使用它之后被设置为真。
     * 
     */
    private boolean         _prepared;


    /**
     * Association between namespace URIs (keys) and prefixes (values).
     * Accumulated here prior to starting an element and placing this
     * list in the element state.
     * <p>
     *  命名空间URI(键)和前缀(值)之间的关联。在启动元素之前在此处累积,并将此列表置于元素状态。
     * 
     */
    protected Hashtable     _prefixes;


    /**
     * The system identifier of the document type, if known.
     * <p>
     *  文档类型的系统标识符(如果已知)。
     * 
     */
    protected String        _docTypePublicId;


    /**
     * The system identifier of the document type, if known.
     * <p>
     *  文档类型的系统标识符(如果已知)。
     * 
     */
    protected String        _docTypeSystemId;


    /**
     * The output format associated with this serializer. This will never
     * be a null reference. If no format was passed to the constructor,
     * the default one for this document type will be used. The format
     * object is never changed by the serializer.
     * <p>
     *  与此序列化关联的输出格式。这永远不会是一个null引用。如果没有格式传递给构造函数,则将使用此文档类型的默认格式。格式对象从不被串行器改变。
     * 
     */
    protected OutputFormat   _format;


    /**
     * The printer used for printing text parts.
     * <p>
     *  打印机用于打印文本部件。
     * 
     */
    protected Printer       _printer;


    /**
     * True if indenting printer.
     * <p>
     *  如果缩进打印机,则为True。
     * 
     */
    protected boolean       _indenting;

    /** Temporary buffer to store character data */
    protected final StringBuffer fStrBuffer = new StringBuffer(40);

    /**
     * The underlying writer.
     * <p>
     *  底层作家。
     * 
     */
    private Writer          _writer;


    /**
     * The output stream.
     * <p>
     *  输出流。
     * 
     */
    private OutputStream    _output;

    /** Current node that is being processed  */
    protected Node fCurrentNode = null;



    //--------------------------------//
    // Constructor and initialization //
    //--------------------------------//


    /**
     * Protected constructor can only be used by derived class.
     * Must initialize the serializer before serializing any document,
     * by calling {@link #setOutputCharStream} or {@link #setOutputByteStream}
                 * first
                 * <p>
                 *  受保护的构造函数只能由派生类使用。
                 * 必须在序列化任何文档之前初始化序列化程序,方法是先调用{@link #setOutputCharStream}或{@link #setOutputByteStream}。
                 * 
     */
    protected BaseMarkupSerializer( OutputFormat format )
    {
        int i;

        _elementStates = new ElementState[ 10 ];
        for ( i = 0 ; i < _elementStates.length ; ++i )
            _elementStates[ i ] = new ElementState();
        _format = format;
    }


    public DocumentHandler asDocumentHandler()
        throws IOException
    {
        prepare();
        return this;
    }


    public ContentHandler asContentHandler()
        throws IOException
    {
        prepare();
        return this;
    }


    public DOMSerializer asDOMSerializer()
        throws IOException
    {
        prepare();
        return this;
    }


    public void setOutputByteStream( OutputStream output )
    {
        if ( output == null ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN,
                                                           "ArgumentIsNull", new Object[]{"output"});
            throw new NullPointerException(msg);
        }
        _output = output;
        _writer = null;
        reset();
    }


    public void setOutputCharStream( Writer writer )
    {
        if ( writer == null ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN,
                                                           "ArgumentIsNull", new Object[]{"writer"});
            throw new NullPointerException(msg);
        }
        _writer = writer;
        _output = null;
        reset();
    }


    public void setOutputFormat( OutputFormat format )
    {
        if ( format == null ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN,
                                                           "ArgumentIsNull", new Object[]{"format"});
            throw new NullPointerException(msg);
        }
        _format = format;
        reset();
    }


    public boolean reset()
    {
        if ( _elementStateCount > 1 ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN,
                                                           "ResetInMiddle", null);
            throw new IllegalStateException(msg);
        }
        _prepared = false;
        fCurrentNode = null;
        fStrBuffer.setLength(0);
        return true;
    }


    protected void prepare()
        throws IOException
    {
        if ( _prepared )
            return;

        if ( _writer == null && _output == null ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN,
                                                           "NoWriterSupplied", null);
            throw new IOException(msg);
        }
        // If the output stream has been set, use it to construct
        // the writer. It is possible that the serializer has been
        // reused with the same output stream and different encoding.

        _encodingInfo = _format.getEncodingInfo();

        if ( _output != null ) {
            _writer = _encodingInfo.getWriter(_output);
        }

        if ( _format.getIndenting() ) {
            _indenting = true;
            _printer = new IndentPrinter( _writer, _format );
        } else {
            _indenting = false;
            _printer = new Printer( _writer, _format );
        }

        ElementState state;

        _elementStateCount = 0;
        state = _elementStates[ 0 ];
        state.namespaceURI = null;
        state.localName = null;
        state.rawName = null;
        state.preserveSpace = _format.getPreserveSpace();
        state.empty = true;
        state.afterElement = false;
        state.afterComment = false;
        state.doCData = state.inCData = false;
        state.prefixes = null;

        _docTypePublicId = _format.getDoctypePublic();
        _docTypeSystemId = _format.getDoctypeSystem();
        _started = false;
        _prepared = true;
    }



    //----------------------------------//
    // DOM document serializing methods //
    //----------------------------------//


    /**
     * Serializes the DOM element using the previously specified
     * writer and output format. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     *  使用先前指定的writer和输出格式序列化DOM元素。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param elem The element to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( Element elem )
        throws IOException
    {
        reset();
        prepare();
        serializeNode( elem );
        _printer.flush();
        if ( _printer.getException() != null )
            throw _printer.getException();
    }

    /**
     * Serializes a node using the previously specified
     * writer and output format. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     *  使用先前指定的写入器和输出格式对节点进行序列化。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param node Node to serialize
     * @throws IOException An I/O exception occured while serializing
     */
    public void serialize( Node node ) throws IOException {
        reset();
        prepare();
        serializeNode( node );
        //Print any PIs and Comments which appeared in 'node'
        serializePreRoot();
        _printer.flush();
        if ( _printer.getException() != null )
            throw _printer.getException();
    }

    /**
     * Serializes the DOM document fragmnt using the previously specified
     * writer and output format. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     * 使用先前指定的writer和输出格式序列化DOM文档Fragmnt。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param elem The element to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( DocumentFragment frag )
        throws IOException
    {
        reset();
        prepare();
        serializeNode( frag );
        _printer.flush();
        if ( _printer.getException() != null )
            throw _printer.getException();
    }


    /**
     * Serializes the DOM document using the previously specified
     * writer and output format. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     *  使用先前指定的writer和输出格式序列化DOM文档。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param doc The document to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( Document doc )
        throws IOException
    {
        reset();
        prepare();
        serializeNode( doc );
        serializePreRoot();
        _printer.flush();
        if ( _printer.getException() != null )
            throw _printer.getException();
    }


    //------------------------------------------//
    // SAX document handler serializing methods //
    //------------------------------------------//


    public void startDocument()
        throws SAXException
    {
        try {
            prepare();
        } catch ( IOException except ) {
            throw new SAXException( except.toString() );
        }
        // Nothing to do here. All the magic happens in startDocument(String)
    }


    public void characters( char[] chars, int start, int length )
        throws SAXException
    {
        ElementState state;

        try {
        state = content();

        // Check if text should be print as CDATA section or unescaped
        // based on elements listed in the output format (the element
        // state) or whether we are inside a CDATA section or entity.

        if ( state.inCData || state.doCData ) {
            int          saveIndent;

            // Print a CDATA section. The text is not escaped, but ']]>'
            // appearing in the code must be identified and dealt with.
            // The contents of a text node is considered space preserving.
            if ( ! state.inCData ) {
                _printer.printText( "<![CDATA[" );
                state.inCData = true;
            }
            saveIndent = _printer.getNextIndent();
            _printer.setNextIndent( 0 );
            char ch;
            final int end = start + length;
            for ( int index = start ; index < end; ++index ) {
                ch = chars[index];
                if ( ch == ']' && index + 2 < end &&
                     chars[ index + 1 ] == ']' && chars[ index + 2 ] == '>' ) {
                    _printer.printText("]]]]><![CDATA[>");
                    index +=2;
                    continue;
                }
                if (!XMLChar.isValid(ch)) {
                    // check if it is surrogate
                    if (++index < end) {
                        surrogates(ch, chars[index]);
                    }
                    else {
                        fatalError("The character '"+(char)ch+"' is an invalid XML character");
                    }
                    continue;
                } else {
                    if ( ( ch >= ' ' && _encodingInfo.isPrintable((char)ch) && ch != 0xF7 ) ||
                        ch == '\n' || ch == '\r' || ch == '\t' ) {
                        _printer.printText((char)ch);
                    } else {
                        // The character is not printable -- split CDATA section
                        _printer.printText("]]>&#x");
                        _printer.printText(Integer.toHexString(ch));
                        _printer.printText(";<![CDATA[");
                    }
                }
            }
            _printer.setNextIndent( saveIndent );

        } else {

            int saveIndent;

            if ( state.preserveSpace ) {
                // If preserving space then hold of indentation so no
                // excessive spaces are printed at line breaks, escape
                // the text content without replacing spaces and print
                // the text breaking only at line breaks.
                saveIndent = _printer.getNextIndent();
                _printer.setNextIndent( 0 );
                printText( chars, start, length, true, state.unescaped );
                _printer.setNextIndent( saveIndent );
            } else {
                printText( chars, start, length, false, state.unescaped );
            }
        }
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public void ignorableWhitespace( char[] chars, int start, int length )
        throws SAXException
    {
        int i;

        try {
        content();

        // Print ignorable whitespaces only when indenting, after
        // all they are indentation. Cancel the indentation to
        // not indent twice.
        if ( _indenting ) {
            _printer.setThisIndent( 0 );
            for ( i = start ; length-- > 0 ; ++i )
                _printer.printText( chars[ i ] );
        }
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public final void processingInstruction( String target, String code )
        throws SAXException
    {
        try {
            processingInstructionIO( target, code );
        } catch ( IOException except ) {
        throw new SAXException( except );
        }
    }

    public void processingInstructionIO( String target, String code )
        throws IOException
    {
        int          index;
        ElementState state;

        state = content();

        // Create the processing instruction textual representation.
        // Make sure we don't have '?>' inside either target or code.
        index = target.indexOf( "?>" );
        if ( index >= 0 )
            fStrBuffer.append( "<?" ).append( target.substring( 0, index ) );
        else
            fStrBuffer.append( "<?" ).append( target );
        if ( code != null ) {
            fStrBuffer.append( ' ' );
            index = code.indexOf( "?>" );
            if ( index >= 0 )
                fStrBuffer.append( code.substring( 0, index ) );
            else
                fStrBuffer.append( code );
        }
        fStrBuffer.append( "?>" );

        // If before the root element (or after it), do not print
        // the PI directly but place it in the pre-root vector.
        if ( isDocumentState() ) {
            if ( _preRoot == null )
                _preRoot = new Vector();
            _preRoot.addElement( fStrBuffer.toString() );
        } else {
            _printer.indent();
            printText( fStrBuffer.toString(), true, true );
            _printer.unindent();
            if ( _indenting )
            state.afterElement = true;
        }

        fStrBuffer.setLength(0);
    }


    public void comment( char[] chars, int start, int length )
        throws SAXException
    {
        try {
        comment( new String( chars, start, length ) );
        } catch ( IOException except ) {
            throw new SAXException( except );
    }
    }


    public void comment( String text )
        throws IOException
    {
        int          index;
        ElementState state;

        if ( _format.getOmitComments() )
            return;

        state  = content();
        // Create the processing comment textual representation.
        // Make sure we don't have '-->' inside the comment.
        index = text.indexOf( "-->" );
        if ( index >= 0 )
            fStrBuffer.append( "<!--" ).append( text.substring( 0, index ) ).append( "-->" );
        else
            fStrBuffer.append( "<!--" ).append( text ).append( "-->" );

        // If before the root element (or after it), do not print
        // the comment directly but place it in the pre-root vector.
        if ( isDocumentState() ) {
            if ( _preRoot == null )
                _preRoot = new Vector();
            _preRoot.addElement( fStrBuffer.toString() );
        } else {
            // Indent this element on a new line if the first
            // content of the parent element or immediately
            // following an element.
            if ( _indenting && ! state.preserveSpace)
                _printer.breakLine();
                                                _printer.indent();
            printText( fStrBuffer.toString(), true, true );
                                                _printer.unindent();
            if ( _indenting )
                state.afterElement = true;
        }

        fStrBuffer.setLength(0);
        state.afterComment = true;
        state.afterElement = false;
    }


    public void startCDATA()
    {
        ElementState state;

        state = getElementState();
        state.doCData = true;
    }


    public void endCDATA()
    {
        ElementState state;

        state = getElementState();
        state.doCData = false;
    }


    public void startNonEscaping()
    {
        ElementState state;

        state = getElementState();
        state.unescaped = true;
    }


    public void endNonEscaping()
    {
        ElementState state;

        state = getElementState();
        state.unescaped = false;
    }


    public void startPreserving()
    {
        ElementState state;

        state = getElementState();
        state.preserveSpace = true;
    }


    public void endPreserving()
    {
        ElementState state;

        state = getElementState();
        state.preserveSpace = false;
    }


    /**
     * Called at the end of the document to wrap it up.
     * Will flush the output stream and throw an exception
     * if any I/O error occured while serializing.
     *
     * <p>
     *  在文档结尾处调用以将其包装起来。如果在序列化时发生任何I / O错误,将刷新输出流并抛出异常。
     * 
     * 
     * @throws SAXException An I/O exception occured during
     *  serializing
     */
    public void endDocument()
        throws SAXException
    {
        try {
        // Print all the elements accumulated outside of
        // the root element.
        serializePreRoot();
        // Flush the output, this is necessary for fStrBuffered output.
        _printer.flush();
        } catch ( IOException except ) {
            throw new SAXException( except );
    }
    }


    public void startEntity( String name )
    {
        // ???
    }


    public void endEntity( String name )
    {
        // ???
    }


    public void setDocumentLocator( Locator locator )
    {
        // Nothing to do
    }


    //-----------------------------------------//
    // SAX content handler serializing methods //
    //-----------------------------------------//


    public void skippedEntity ( String name )
        throws SAXException
    {
        try {
        endCDATA();
        content();
        _printer.printText( '&' );
        _printer.printText( name );
        _printer.printText( ';' );
        } catch ( IOException except ) {
            throw new SAXException( except );
    }
    }


    public void startPrefixMapping( String prefix, String uri )
        throws SAXException
    {
        if ( _prefixes == null )
            _prefixes = new Hashtable();
        _prefixes.put( uri, prefix == null ? "" : prefix );
    }


    public void endPrefixMapping( String prefix )
        throws SAXException
    {
    }


    //------------------------------------------//
    // SAX DTD/Decl handler serializing methods //
    //------------------------------------------//


    public final void startDTD( String name, String publicId, String systemId )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        _docTypePublicId = publicId;
        _docTypeSystemId = systemId;

        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public void endDTD()
    {
        // Nothing to do here, all the magic occurs in startDocument(String).
    }


    public void elementDecl( String name, String model )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        _printer.printText( "<!ELEMENT " );
        _printer.printText( name );
        _printer.printText( ' ' );
        _printer.printText( model );
        _printer.printText( '>' );
        if ( _indenting )
            _printer.breakLine();
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public void attributeDecl( String eName, String aName, String type,
                               String valueDefault, String value )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        _printer.printText( "<!ATTLIST " );
        _printer.printText( eName );
        _printer.printText( ' ' );
        _printer.printText( aName );
        _printer.printText( ' ' );
        _printer.printText( type );
        if ( valueDefault != null ) {
            _printer.printText( ' ' );
            _printer.printText( valueDefault );
        }
        if ( value != null ) {
            _printer.printText( " \"" );
            printEscaped( value );
            _printer.printText( '"' );
        }
        _printer.printText( '>' );
        if ( _indenting )
            _printer.breakLine();
        } catch ( IOException except ) {
            throw new SAXException( except );
    }
    }


    public void internalEntityDecl( String name, String value )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        _printer.printText( "<!ENTITY " );
        _printer.printText( name );
        _printer.printText( " \"" );
        printEscaped( value );
        _printer.printText( "\">" );
        if ( _indenting )
            _printer.breakLine();
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public void externalEntityDecl( String name, String publicId, String systemId )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        unparsedEntityDecl( name, publicId, systemId, null );
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    public void unparsedEntityDecl( String name, String publicId,
                                    String systemId, String notationName )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        if ( publicId == null ) {
            _printer.printText( "<!ENTITY " );
            _printer.printText( name );
            _printer.printText( " SYSTEM " );
            printDoctypeURL( systemId );
        } else {
            _printer.printText( "<!ENTITY " );
            _printer.printText( name );
            _printer.printText( " PUBLIC " );
            printDoctypeURL( publicId );
            _printer.printText( ' ' );
            printDoctypeURL( systemId );
        }
        if ( notationName != null ) {
            _printer.printText( " NDATA " );
            _printer.printText( notationName );
        }
        _printer.printText( '>' );
        if ( _indenting )
            _printer.breakLine();
        } catch ( IOException except ) {
            throw new SAXException( except );
    }
    }


    public void notationDecl( String name, String publicId, String systemId )
        throws SAXException
    {
        try {
        _printer.enterDTD();
        if ( publicId != null ) {
            _printer.printText( "<!NOTATION " );
            _printer.printText( name );
            _printer.printText( " PUBLIC " );
            printDoctypeURL( publicId );
            if ( systemId != null ) {
                _printer.printText( ' ' );
                printDoctypeURL( systemId );
            }
        } else {
            _printer.printText( "<!NOTATION " );
            _printer.printText( name );
            _printer.printText( " SYSTEM " );
            printDoctypeURL( systemId );
        }
        _printer.printText( '>' );
        if ( _indenting )
            _printer.breakLine();
        } catch ( IOException except ) {
            throw new SAXException( except );
        }
    }


    //------------------------------------------//
    // Generic node serializing methods methods //
    //------------------------------------------//


    /**
     * Serialize the DOM node. This method is shared across XML, HTML and XHTML
     * serializers and the differences are masked out in a separate {@link
     * #serializeElement}.
     *
     * <p>
     *  序列化DOM节点。此方法在XML,HTML和XHTML序列化程序之间共享,并且在单独的{@link #serializeElement}中屏蔽差异。
     * 
     * 
     * @param node The node to serialize
     * @see #serializeElement
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    protected void serializeNode( Node node )
        throws IOException
    {
        fCurrentNode = node;

        // Based on the node type call the suitable SAX handler.
        // Only comments entities and documents which are not
        // handled by SAX are serialized directly.
        switch ( node.getNodeType() ) {
        case Node.TEXT_NODE : {
            String text;

            text = node.getNodeValue();
            if ( text != null ) {
                if (fDOMFilter !=null &&
                    (fDOMFilter.getWhatToShow() & NodeFilter.SHOW_TEXT)!= 0) {
                    short code = fDOMFilter.acceptNode(node);
                    switch (code) {
                        case NodeFilter.FILTER_REJECT:
                        case NodeFilter.FILTER_SKIP: {
                            break;
                        }
                        default: {
                            characters(text);
                        }
                    }
                }
                else if ( !_indenting || getElementState().preserveSpace
                     || (text.replace('\n',' ').trim().length() != 0))
                    characters( text );

            }
            break;
        }

        case Node.CDATA_SECTION_NODE : {
            String text = node.getNodeValue();
            if ((features & DOMSerializerImpl.CDATA) != 0) {
                if (text != null) {
                    if (fDOMFilter != null
                        && (fDOMFilter.getWhatToShow()
                            & NodeFilter.SHOW_CDATA_SECTION)
                            != 0) {
                        short code = fDOMFilter.acceptNode(node);
                        switch (code) {
                            case NodeFilter.FILTER_REJECT :
                            case NodeFilter.FILTER_SKIP :
                                {
                                    // skip the CDATA node
                                    return;
                                }
                            default :
                                {
                                    //fall through..
                                }
                        }
                    }
                    startCDATA();
                    characters(text);
                    endCDATA();
                }
            } else {
                // transform into a text node
                characters(text);
            }
            break;
        }
        case Node.COMMENT_NODE : {
            String text;

            if ( ! _format.getOmitComments() ) {
                text = node.getNodeValue();
                if ( text != null ) {

                    if (fDOMFilter !=null &&
                          (fDOMFilter.getWhatToShow() & NodeFilter.SHOW_COMMENT)!= 0) {
                          short code = fDOMFilter.acceptNode(node);
                          switch (code) {
                              case NodeFilter.FILTER_REJECT:
                              case NodeFilter.FILTER_SKIP: {
                                  // skip the comment node
                                  return;
                              }
                              default: {
                                   // fall through
                              }
                          }
                    }
                    comment( text );
                }
            }
            break;
        }

        case Node.ENTITY_REFERENCE_NODE : {
            Node         child;

            endCDATA();
            content();

            if (((features & DOMSerializerImpl.ENTITIES) != 0)
                || (node.getFirstChild() == null)) {
                if (fDOMFilter !=null &&
                      (fDOMFilter.getWhatToShow() & NodeFilter.SHOW_ENTITY_REFERENCE)!= 0) {
                      short code = fDOMFilter.acceptNode(node);
                      switch (code) {
                        case NodeFilter.FILTER_REJECT:{
                            return; // remove the node
                          }
                          case NodeFilter.FILTER_SKIP: {
                              child = node.getFirstChild();
                              while ( child != null ) {
                                  serializeNode( child );
                                  child = child.getNextSibling();
                              }
                              return;
                          }

                          default: {
                               // fall through
                          }
                      }
                  }
                checkUnboundNamespacePrefixedNode(node);

                _printer.printText("&");
                _printer.printText(node.getNodeName());
                _printer.printText(";");
            }
            else {
                child = node.getFirstChild();
                while ( child != null ) {
                    serializeNode( child );
                    child = child.getNextSibling();
                }
            }

            break;
        }

        case Node.PROCESSING_INSTRUCTION_NODE : {

            if (fDOMFilter !=null &&
                  (fDOMFilter.getWhatToShow() & NodeFilter.SHOW_PROCESSING_INSTRUCTION)!= 0) {
                  short code = fDOMFilter.acceptNode(node);
                  switch (code) {
                    case NodeFilter.FILTER_REJECT:
                    case NodeFilter.FILTER_SKIP: {
                          return;  // skip this node
                    }
                    default: { // fall through
                    }
                  }
            }
            processingInstructionIO( node.getNodeName(), node.getNodeValue() );
            break;
        }
        case Node.ELEMENT_NODE :  {

            if (fDOMFilter !=null &&
                  (fDOMFilter.getWhatToShow() & NodeFilter.SHOW_ELEMENT)!= 0) {
                  short code = fDOMFilter.acceptNode(node);
                  switch (code) {
                    case NodeFilter.FILTER_REJECT: {
                        return;
                    }
                    case NodeFilter.FILTER_SKIP: {
                        Node child = node.getFirstChild();
                        while ( child != null ) {
                            serializeNode( child );
                            child = child.getNextSibling();
                        }
                        return;  // skip this node
                    }

                    default: { // fall through
                    }
                  }
            }
            serializeElement( (Element) node );
            break;
        }
        case Node.DOCUMENT_NODE : {
            DocumentType      docType;
            DOMImplementation domImpl;
            NamedNodeMap      map;
            Entity            entity;
            Notation          notation;
            int               i;

            serializeDocument();

            // If there is a document type, use the SAX events to
            // serialize it.
            docType = ( (Document) node ).getDoctype();
            if (docType != null) {
                // DOM Level 2 (or higher)
                domImpl = ( (Document) node ).getImplementation();
                try {
                    String internal;

                    _printer.enterDTD();
                    _docTypePublicId = docType.getPublicId();
                    _docTypeSystemId = docType.getSystemId();
                    internal = docType.getInternalSubset();
                    if ( internal != null && internal.length() > 0 )
                        _printer.printText( internal );
                    endDTD();
                }
                // DOM Level 1 -- does implementation have methods?
                catch (NoSuchMethodError nsme) {
                    Class docTypeClass = docType.getClass();

                    String docTypePublicId = null;
                    String docTypeSystemId = null;
                    try {
                        java.lang.reflect.Method getPublicId = docTypeClass.getMethod("getPublicId", (Class[]) null);
                        if (getPublicId.getReturnType().equals(String.class)) {
                            docTypePublicId = (String)getPublicId.invoke(docType, (Object[]) null);
                        }
                    }
                    catch (Exception e) {
                        // ignore
                    }
                    try {
                        java.lang.reflect.Method getSystemId = docTypeClass.getMethod("getSystemId", (Class[]) null);
                        if (getSystemId.getReturnType().equals(String.class)) {
                            docTypeSystemId = (String)getSystemId.invoke(docType, (Object[]) null);
                        }
                    }
                    catch (Exception e) {
                        // ignore
                    }
                    _printer.enterDTD();
                    _docTypePublicId = docTypePublicId;
                    _docTypeSystemId = docTypeSystemId;
                    endDTD();
                }

                serializeDTD(docType.getName());

            }
            _started = true;

            // !! Fall through
        }
        case Node.DOCUMENT_FRAGMENT_NODE : {
            Node         child;

            // By definition this will happen if the node is a document,
            // document fragment, etc. Just serialize its contents. It will
            // work well for other nodes that we do not know how to serialize.
            child = node.getFirstChild();
            while ( child != null ) {
                serializeNode( child );
                child = child.getNextSibling();
            }
            break;
        }

        default:
            break;
        }
    }


    /* Serializes XML Declaration, according to 'xml-declaration' property.
    /* <p>
     */
    protected void serializeDocument()throws IOException {
        int    i;

        String dtd = _printer.leaveDTD();
        if (! _started) {

            if (! _format.getOmitXMLDeclaration()) {
                StringBuffer    buffer;

                // Serialize the document declaration appreaing at the head
                // of very XML document (unless asked not to).
                buffer = new StringBuffer( "<?xml version=\"" );
                if (_format.getVersion() != null)
                    buffer.append( _format.getVersion() );
                else
                    buffer.append( "1.0" );
                buffer.append( '"' );
                String format_encoding =  _format.getEncoding();
                if (format_encoding != null) {
                    buffer.append( " encoding=\"" );
                    buffer.append( format_encoding );
                    buffer.append( '"' );
                }
                if (_format.getStandalone() && _docTypeSystemId == null &&
                    _docTypePublicId == null)
                    buffer.append( " standalone=\"yes\"" );
                buffer.append( "?>" );
                _printer.printText( buffer );
                _printer.breakLine();
            }
        }

        // Always serialize these, even if not te first root element.
        serializePreRoot();

    }

    /* Serializes DTD, if present.
    /* <p>
     */
    protected void serializeDTD(String name) throws IOException{

        String dtd = _printer.leaveDTD();
        if (! _format.getOmitDocumentType()) {
            if (_docTypeSystemId != null) {
                // System identifier must be specified to print DOCTYPE.
                // If public identifier is specified print 'PUBLIC
                // <public> <system>', if not, print 'SYSTEM <system>'.
                _printer.printText( "<!DOCTYPE " );
                _printer.printText( name );
                if (_docTypePublicId != null) {
                    _printer.printText( " PUBLIC " );
                    printDoctypeURL( _docTypePublicId );
                    if (_indenting) {
                        _printer.breakLine();
                        for (int i = 0 ; i < 18 + name.length() ; ++i)
                            _printer.printText( " " );
                    } else
                        _printer.printText( " " );
                    printDoctypeURL( _docTypeSystemId );
                } else {
                    _printer.printText( " SYSTEM " );
                    printDoctypeURL( _docTypeSystemId );
                }

                // If we accumulated any DTD contents while printing.
                // this would be the place to print it.
                if (dtd != null && dtd.length() > 0) {
                    _printer.printText( " [" );
                    printText( dtd, true, true );
                    _printer.printText( ']' );
                }

                _printer.printText( ">" );
                _printer.breakLine();
            } else if (dtd != null && dtd.length() > 0) {
                _printer.printText( "<!DOCTYPE " );
                _printer.printText( name );
                _printer.printText( " [" );
                printText( dtd, true, true );
                _printer.printText( "]>" );
                _printer.breakLine();
            }
        }
    }


    /**
     * Must be called by a method about to print any type of content.
     * If the element was just opened, the opening tag is closed and
     * will be matched to a closing tag. Returns the current element
     * state with <tt>empty</tt> and <tt>afterElement</tt> set to false.
     *
     * <p>
     *  必须由一个方法调用打印任何类型的内容。如果元素刚刚打开,则开始标记将关闭,并与结束标记相匹配。
     * 返回当前元素的状态,其中<tt> empty </tt>和<tt> afterElement </tt>设置为false。
     * 
     * 
     * @return The current element state
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    protected ElementState content()
        throws IOException
    {
        ElementState state;

        state = getElementState();
        if ( ! isDocumentState() ) {
            // Need to close CData section first
            if ( state.inCData && ! state.doCData ) {
                _printer.printText( "]]>" );
                state.inCData = false;
            }
            // If this is the first content in the element,
            // change the state to not-empty and close the
            // opening element tag.
            if ( state.empty ) {
                _printer.printText( '>' );
                state.empty = false;
            }
            // Except for one content type, all of them
            // are not last element. That one content
            // type will take care of itself.
            state.afterElement = false;
            // Except for one content type, all of them
            // are not last comment. That one content
            // type will take care of itself.
            state.afterComment = false;
        }
        return state;
    }


    /**
     * Called to print the text contents in the prevailing element format.
     * Since this method is capable of printing text as CDATA, it is used
     * for that purpose as well. White space handling is determined by the
     * current element state. In addition, the output format can dictate
     * whether the text is printed as CDATA or unescaped.
     *
     * <p>
     *  调用以当前元素格式打印文本内容。由于此方法能够将文本打印为CDATA,因此也用于此目的。空白处理由当前元素状态决定。此外,输出格式可以指示文本是作为CDATA打印还是未转义。
     * 
     * 
     * @param text The text to print
     * @param unescaped True is should print unescaped
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    protected void characters( String text )
        throws IOException
    {
        ElementState state;

        state = content();
        // Check if text should be print as CDATA section or unescaped
        // based on elements listed in the output format (the element
        // state) or whether we are inside a CDATA section or entity.

        if ( state.inCData || state.doCData ) {
            int          index;
            int          saveIndent;

            // Print a CDATA section. The text is not escaped, but ']]>'
            // appearing in the code must be identified and dealt with.
            // The contents of a text node is considered space preserving.
            if ( ! state.inCData ) {
                _printer.printText("<![CDATA[");
                state.inCData = true;
            }
            saveIndent = _printer.getNextIndent();
            _printer.setNextIndent( 0 );
            printCDATAText( text);
            _printer.setNextIndent( saveIndent );

        } else {

            int saveIndent;

            if ( state.preserveSpace ) {
                // If preserving space then hold of indentation so no
                // excessive spaces are printed at line breaks, escape
                // the text content without replacing spaces and print
                // the text breaking only at line breaks.
                saveIndent = _printer.getNextIndent();
                _printer.setNextIndent( 0 );
                printText( text, true, state.unescaped );
                _printer.setNextIndent( saveIndent );
            } else {
                printText( text, false, state.unescaped );
            }
        }
    }


    /**
     * Returns the suitable entity reference for this character value,
     * or null if no such entity exists. Calling this method with <tt>'&amp;'</tt>
     * will return <tt>"&amp;amp;"</tt>.
     *
     * <p>
     *  返回此字符值的合适的实体引用,如果不存在此类实体,则返回null。使用<tt>'&amp;'</tt>调用此方法将返回<tt>"&amp; amp;"</tt>。
     * 
     * 
     * @param ch Character value
     * @return Character entity name, or null
     */
    protected abstract String getEntityRef( int ch );


    /**
     * Called to serializee the DOM element. The element is serialized based on
     * the serializer's method (XML, HTML, XHTML).
     *
     * <p>
     *  被称为序列化DOM元素。基于序列化器的方法(XML,HTML,XHTML)对元素进行序列化。
     * 
     * 
     * @param elem The element to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    protected abstract void serializeElement( Element elem )
        throws IOException;


    /**
     * Comments and PIs cannot be serialized before the root element,
     * because the root element serializes the document type, which
     * generally comes first. Instead such PIs and comments are
     * accumulated inside a vector and serialized by calling this
     * method. Will be called when the root element is serialized
     * and when the document finished serializing.
     *
     * <p>
     * 注释和PI不能在根元素之前序列化,因为根元素将序列化文档类型,这通常首先。相反,这样的PI和注释被累积在向量内并通过调用该方法被串行化。当根元素被序列化并且文档完成序列化时将被调用。
     * 
     * 
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    protected void serializePreRoot()
        throws IOException
    {
        int i;

        if ( _preRoot != null ) {
            for ( i = 0 ; i < _preRoot.size() ; ++i ) {
                printText( (String) _preRoot.elementAt( i ), true, true );
                if ( _indenting )
                _printer.breakLine();
            }
            _preRoot.removeAllElements();
        }
    }


    //---------------------------------------------//
    // Text pretty printing and formatting methods //
    //---------------------------------------------//

    protected void printCDATAText( String text ) throws IOException {
        int length = text.length();
        char ch;

        for ( int index = 0 ; index <  length; ++index ) {
            ch = text.charAt( index );
            if (ch == ']'
                && index + 2 < length
                && text.charAt(index + 1) == ']'
                && text.charAt(index + 2) == '>') { // check for ']]>'
                if (fDOMErrorHandler != null) {
                    // REVISIT: this means that if DOM Error handler is not registered we don't report any
                    // fatal errors and might serialize not wellformed document
                    if ((features & DOMSerializerImpl.SPLITCDATA) == 0) {
                        String msg = DOMMessageFormatter.formatMessage(
                            DOMMessageFormatter.SERIALIZER_DOMAIN,
                            "EndingCDATA",
                            null);
                        if ((features & DOMSerializerImpl.WELLFORMED) != 0) {
                            // issue fatal error
                            modifyDOMError(msg, DOMError.SEVERITY_FATAL_ERROR, "wf-invalid-character", fCurrentNode);
                            fDOMErrorHandler.handleError(fDOMError);
                            throw new LSException(LSException.SERIALIZE_ERR, msg);
                        }
                        else {
                            // issue error
                            modifyDOMError(msg, DOMError.SEVERITY_ERROR, "cdata-section-not-splitted", fCurrentNode);
                            if (!fDOMErrorHandler.handleError(fDOMError)) {
                                throw new LSException(LSException.SERIALIZE_ERR, msg);
                            }
                        }
                    } else {
                        // issue warning
                        String msg =
                            DOMMessageFormatter.formatMessage(
                                DOMMessageFormatter.SERIALIZER_DOMAIN,
                                "SplittingCDATA",
                                null);
                        modifyDOMError(
                            msg,
                            DOMError.SEVERITY_WARNING,
                            null, fCurrentNode);
                        fDOMErrorHandler.handleError(fDOMError);
                    }
                }
                // split CDATA section
                _printer.printText("]]]]><![CDATA[>");
                index += 2;
                continue;
            }

            if (!XMLChar.isValid(ch)) {
                // check if it is surrogate
                if (++index <length) {
                    surrogates(ch, text.charAt(index));
                }
                else {
                    fatalError("The character '"+(char)ch+"' is an invalid XML character");
                }
                continue;
            } else {
                if ( ( ch >= ' ' && _encodingInfo.isPrintable((char)ch) && ch != 0xF7 ) ||
                     ch == '\n' || ch == '\r' || ch == '\t' ) {
                    _printer.printText((char)ch);
                } else {

                    // The character is not printable -- split CDATA section
                    _printer.printText("]]>&#x");
                    _printer.printText(Integer.toHexString(ch));
                    _printer.printText(";<![CDATA[");
                }
            }
        }
    }


    protected void surrogates(int high, int low) throws IOException{
        if (XMLChar.isHighSurrogate(high)) {
            if (!XMLChar.isLowSurrogate(low)) {
                //Invalid XML
                fatalError("The character '"+(char)low+"' is an invalid XML character");
            }
            else {
                int supplemental = XMLChar.supplemental((char)high, (char)low);
                if (!XMLChar.isValid(supplemental)) {
                    //Invalid XML
                    fatalError("The character '"+(char)supplemental+"' is an invalid XML character");
                }
                else {
                    if (content().inCData ) {
                        _printer.printText("]]>&#x");
                        _printer.printText(Integer.toHexString(supplemental));
                        _printer.printText(";<![CDATA[");
                    }
                    else {
                        printHex(supplemental);
                    }
                }
            }
        } else {
            fatalError("The character '"+(char)high+"' is an invalid XML character");
        }

    }

    /**
     * Called to print additional text with whitespace handling.
     * If spaces are preserved, the text is printed as if by calling
     * {@link #printText(String,boolean,boolean)} with a call to {@link Printer#breakLine}
     * for each new line. If spaces are not preserved, the text is
     * broken at space boundaries if longer than the line width;
     * Multiple spaces are printed as such, but spaces at beginning
     * of line are removed.
     *
     * <p>
     *  调用打印额外的文本与空白处理。
     * 如果保留空格,则通过调用{@link #printText(String,boolean,boolean)}并对每个新行调用{@link Printer#breakLine}来打印文本。
     * 如果未保留空格,则如果长度超过线宽,文本将在空格边界被破坏;将打印多个空格,但删除行开头的空格。
     * 
     * 
     * @param text The text to print
     * @param preserveSpace Space preserving flag
     * @param unescaped Print unescaped
     */
    protected void printText( char[] chars, int start, int length,
                                    boolean preserveSpace, boolean unescaped )
        throws IOException
    {
        int index;
        char ch;

        if ( preserveSpace ) {
            // Preserving spaces: the text must print exactly as it is,
            // without breaking when spaces appear in the text and without
            // consolidating spaces. If a line terminator is used, a line
            // break will occur.
            while ( length-- > 0 ) {
                ch = chars[ start ];
                ++start;
                if ( ch == '\n' || ch == '\r' || unescaped )
                    _printer.printText( ch );
                else
                    printEscaped( ch );
            }
        } else {
            // Not preserving spaces: print one part at a time, and
            // use spaces between parts to break them into different
            // lines. Spaces at beginning of line will be stripped
            // by printing mechanism. Line terminator is treated
            // no different than other text part.
            while ( length-- > 0 ) {
                ch = chars[ start ];
                ++start;
                if ( ch == ' ' || ch == '\f' || ch == '\t' || ch == '\n' || ch == '\r' )
                    _printer.printSpace();
                else if ( unescaped )
                    _printer.printText( ch );
                else
                    printEscaped( ch );
            }
        }
    }


    protected void printText( String text, boolean preserveSpace, boolean unescaped )
        throws IOException
    {
        int index;
        char ch;

        if ( preserveSpace ) {
            // Preserving spaces: the text must print exactly as it is,
            // without breaking when spaces appear in the text and without
            // consolidating spaces. If a line terminator is used, a line
            // break will occur.
            for ( index = 0 ; index < text.length() ; ++index ) {
                ch = text.charAt( index );
                if ( ch == '\n' || ch == '\r' || unescaped )
                    _printer.printText( ch );
                else
                    printEscaped( ch );
            }
        } else {
            // Not preserving spaces: print one part at a time, and
            // use spaces between parts to break them into different
            // lines. Spaces at beginning of line will be stripped
            // by printing mechanism. Line terminator is treated
            // no different than other text part.
            for ( index = 0 ; index < text.length() ; ++index ) {
                ch = text.charAt( index );
                if ( ch == ' ' || ch == '\f' || ch == '\t' || ch == '\n' || ch == '\r' )
                    _printer.printSpace();
                else if ( unescaped )
                    _printer.printText( ch );
                else
                    printEscaped( ch );
            }
        }
    }


    /**
     * Print a document type public or system identifier URL.
     * Encapsulates the URL in double quotes, escapes non-printing
     * characters and print it equivalent to {@link #printText}.
     *
     * <p>
     *  打印文档类型公共或系统标识符URL。将URL封装在双引号中,转义非打印字符并打印等效于{@link #printText}。
     * 
     * 
     * @param url The document type url to print
     */
    protected void printDoctypeURL( String url )
        throws IOException
    {
        int                i;

        _printer.printText( '"' );
        for( i = 0 ; i < url.length() ; ++i ) {
            if ( url.charAt( i ) == '"' ||  url.charAt( i ) < 0x20 || url.charAt( i ) > 0x7F ) {
                _printer.printText( '%' );
                _printer.printText( Integer.toHexString( url.charAt( i ) ) );
            } else
                _printer.printText( url.charAt( i ) );
        }
        _printer.printText( '"' );
    }


    protected void printEscaped( int ch )
        throws IOException
    {
        String charRef;
        // If there is a suitable entity reference for this
        // character, print it. The list of available entity
        // references is almost but not identical between
        // XML and HTML.
        charRef = getEntityRef( ch );
        if ( charRef != null ) {
            _printer.printText( '&' );
            _printer.printText( charRef );
            _printer.printText( ';' );
        } else if ( ( ch >= ' ' && _encodingInfo.isPrintable((char)ch) && ch != 0xF7 ) ||
                    ch == '\n' || ch == '\r' || ch == '\t' ) {
            // Non printables are below ASCII space but not tab or line
            // terminator, ASCII delete, or above a certain Unicode threshold.
            if (ch < 0x10000) {
                _printer.printText((char)ch );
            } else {
                _printer.printText((char)(((ch-0x10000)>>10)+0xd800));
                _printer.printText((char)(((ch-0x10000)&0x3ff)+0xdc00));
            }
        } else {
                        printHex(ch);
        }
    }

        /**
         * Escapes chars
         * <p>
         *  转义字符
         * 
         */
         final void printHex( int ch) throws IOException {
                 _printer.printText( "&#x" );
                 _printer.printText(Integer.toHexString(ch));
                 _printer.printText( ';' );

         }


    /**
     * Escapes a string so it may be printed as text content or attribute
     * value. Non printable characters are escaped using character references.
     * Where the format specifies a deault entity reference, that reference
     * is used (e.g. <tt>&amp;lt;</tt>).
     *
     * <p>
     *  转义字符串,以便它可以作为文本内容或属性值打印。不可打印字符使用字符引用转义。如果格式指定了一个deault实体引用,则使用该引用(例如<tt>&amp; lt; </tt>)。
     * 
     * 
     * @param source The string to escape
     */
    protected void printEscaped( String source )
        throws IOException
    {
        for ( int i = 0 ; i < source.length() ; ++i ) {
            int ch = source.charAt(i);
            if ((ch & 0xfc00) == 0xd800 && i+1 < source.length()) {
                int lowch = source.charAt(i+1);
                if ((lowch & 0xfc00) == 0xdc00) {
                    ch = 0x10000 + ((ch-0xd800)<<10) + lowch-0xdc00;
                    i++;
                }
            }
            printEscaped(ch);
        }
    }


    //--------------------------------//
    // Element state handling methods //
    //--------------------------------//


    /**
     * Return the state of the current element.
     *
     * <p>
     *  返回当前元素的状态。
     * 
     * 
     * @return Current element state
     */
    protected ElementState getElementState()
    {
        return _elementStates[ _elementStateCount ];
    }


    /**
     * Enter a new element state for the specified element.
     * Tag name and space preserving is specified, element
     * state is initially empty.
     *
     * <p>
     *  为指定的元素输入新的元素状态。指定了标记名称和空间保留,元素状态最初为空。
     * 
     * 
     * @return Current element state, or null
     */
    protected ElementState enterElementState( String namespaceURI, String localName,
                                              String rawName, boolean preserveSpace )
    {
        ElementState state;

        if ( _elementStateCount + 1 == _elementStates.length ) {
            ElementState[] newStates;

            // Need to create a larger array of states. This does not happen
            // often, unless the document is really deep.
            newStates = new ElementState[ _elementStates.length + 10 ];
            for ( int i = 0 ; i < _elementStates.length ; ++i )
                newStates[ i ] = _elementStates[ i ];
            for ( int i = _elementStates.length ; i < newStates.length ; ++i )
                newStates[ i ] = new ElementState();
            _elementStates = newStates;
        }

        ++_elementStateCount;
        state = _elementStates[ _elementStateCount ];
        state.namespaceURI = namespaceURI;
        state.localName = localName;
        state.rawName = rawName;
        state.preserveSpace = preserveSpace;
        state.empty = true;
        state.afterElement = false;
        state.afterComment = false;
        state.doCData = state.inCData = false;
        state.unescaped = false;
        state.prefixes = _prefixes;

        _prefixes = null;
        return state;
    }


    /**
     * Leave the current element state and return to the
     * state of the parent element. If this was the root
     * element, return to the state of the document.
     *
     * <p>
     * 保留当前元素的状态并返回到父元素的状态。如果这是根元素,则返回到文档的状态。
     * 
     * 
     * @return Previous element state
     */
    protected ElementState leaveElementState()
    {
        if ( _elementStateCount > 0 ) {
            /*Corrected by David Blondeau (blondeau@intalio.com)*/
                _prefixes = null;
                //_prefixes = _elementStates[ _elementStateCount ].prefixes;
            -- _elementStateCount;
            return _elementStates[ _elementStateCount ];
        } else {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "Internal", null);
            throw new IllegalStateException(msg);
        }
    }


    /**
     * Returns true if in the state of the document.
     * Returns true before entering any element and after
     * leaving the root element.
     *
     * <p>
     *  如果在文档的状态,则返回true。在输入任何元素之前和离开根元素之后返回true。
     * 
     * 
     * @return True if in the state of the document
     */
    protected boolean isDocumentState()
    {
        return _elementStateCount == 0;
    }


    /**
     * Returns the namespace prefix for the specified URI.
     * If the URI has been mapped to a prefix, returns the
     * prefix, otherwise returns null.
     *
     * <p>
     *  返回指定URI的命名空间前缀。如果URI已映射到前缀,则返回前缀,否则返回null。
     * 
     * 
     * @param namespaceURI The namespace URI
     * @return The namespace prefix if known, or null
     */
    protected String getPrefix( String namespaceURI )
    {
        String    prefix;

        if ( _prefixes != null ) {
            prefix = (String) _prefixes.get( namespaceURI );
            if ( prefix != null )
                return prefix;
        }
        if ( _elementStateCount == 0 )
            return null;
        else {
            for ( int i = _elementStateCount ; i > 0 ; --i ) {
                if ( _elementStates[ i ].prefixes != null ) {
                    prefix = (String) _elementStates[ i ].prefixes.get( namespaceURI );
                    if ( prefix != null )
                        return prefix;
                }
            }
        }
        return null;
    }

    /**
     * The method modifies global DOM error object
     *
     * <p>
     *  该方法修改全局DOM错误对象
     * 
     * 
     * @param message
     * @param severity
     * @param type
     * @return a DOMError
     */
    protected DOMError modifyDOMError(String message, short severity, String type, Node node){
            fDOMError.reset();
            fDOMError.fMessage = message;
            fDOMError.fType = type;
            fDOMError.fSeverity = severity;
            fDOMError.fLocator = new DOMLocatorImpl(-1, -1, -1, node, null);
            return fDOMError;

    }


    protected void fatalError(String message) throws IOException{
        if (fDOMErrorHandler != null) {
            modifyDOMError(message, DOMError.SEVERITY_FATAL_ERROR, null, fCurrentNode);
            fDOMErrorHandler.handleError(fDOMError);
        }
        else {
            throw new IOException(message);
        }
    }

        /**
         * DOM level 3:
         * Check a node to determine if it contains unbound namespace prefixes.
         *
         * <p>
         *  DOM级别3：检查节点以确定其是否包含未绑定的命名空间前缀。
         * 
         * @param node The node to check for unbound namespace prefices
         */
         protected void checkUnboundNamespacePrefixedNode (Node node) throws IOException{

         }
}
