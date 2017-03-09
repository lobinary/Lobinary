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


// Sep 14, 2000:
//  Fixed serializer to report IO exception directly, instead at
//  the end of document processing.
//  Reported by Patrick Higgins <phiggins@transzap.com>


package com.sun.org.apache.xml.internal.serialize;


import java.io.Writer;
import java.io.StringWriter;
import java.io.IOException;


/**
 * The printer is responsible for sending text to the output stream
 * or writer. This class performs direct writing for efficiency.
 * {@link IndentPrinter} supports indentation and line wrapping by
 * extending this class.
 *
 * <p>
 *  打印机负责将文本发送到输出流或写入器。这个类执行直接写作的效率。 {@link IndentPrinter}通过扩展此类来支持缩进和换行。
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 */
public class Printer
{


    /**
     * The output format associated with this serializer. This will never
     * be a null reference. If no format was passed to the constructor,
     * the default one for this document type will be used. The format
     * object is never changed by the serializer.
     * <p>
     *  与此序列化关联的输出格式。这永远不会是一个null引用。如果没有格式传递给构造函数,则将使用此文档类型的默认格式。格式对象从不被串行器改变。
     * 
     */
    protected final OutputFormat _format;


    /**
     * The writer to which the document is written.
     * <p>
     *  写入文档的作者。
     * 
     */
    protected Writer             _writer;


    /**
     * The DTD writer. When we switch to DTD mode, all output is
     * accumulated in this DTD writer. When we switch out of it,
     * the output is obtained as a string. Must not be reset to
     * null until we're done with the document.
     * <p>
     *  DTD writer。当我们切换到DTD模式时,所有输出都累积在此DTD写入器中。当我们切换出来时,输出作为字符串获得。在我们完成文档之前,不能重置为null。
     * 
     */
    protected StringWriter       _dtdWriter;


    /**
     * Holds a reference to the document writer while we are
     * in DTD mode.
     * <p>
     * 当我们处于DTD模式时,保持对文档写入器的引用。
     * 
     */
    protected Writer          _docWriter;


    /**
     * Holds the exception thrown by the serializer.  Exceptions do not cause
     * the serializer to quit, but are held and one is thrown at the end.
     * <p>
     *  保存序列化程序抛出的异常。异常不会导致序列化程序退出,而是保持,一个抛出结束。
     * 
     */
    protected IOException     _exception;


    /**
     * The size of the output buffer.
     * <p>
     *  输出缓冲区的大小。
     * 
     */
    private static final int BufferSize = 4096;


    /**
     * Output buffer.
     * <p>
     *  输出缓冲器。
     * 
     */
    private final char[]  _buffer = new char[ BufferSize ];


    /**
     * Position within the output buffer.
     * <p>
     *  在输出缓冲器内的位置。
     * 
     */
    private int           _pos = 0;


    public Printer( Writer writer, OutputFormat format)
    {
        _writer = writer;
        _format = format;
        _exception = null;
        _dtdWriter = null;
        _docWriter = null;
        _pos = 0;
    }


    public IOException getException()
    {
        return _exception;
    }


    /**
     * Called by any of the DTD handlers to enter DTD mode.
     * Once entered, all output will be accumulated in a string
     * that can be printed as part of the document's DTD.
     * This method may be called any number of time but will only
     * have affect the first time it's called. To exist DTD state
     * and get the accumulated DTD, call {@link #leaveDTD}.
     * <p>
     *  由任何DTD处理程序调用以进入DTD模式。一旦输入,所有输出将被积累在一个字符串中,该字符串可以打印为文档的DTD的一部分。这个方法可以被调用任何次数,但只会影响第一次调用。
     * 要存在DTD状态并获取累积的DTD,请调用{@link #leaveDTD}。
     * 
     */
    public void enterDTD()
        throws IOException
    {
        // Can only enter DTD state once. Once we're out of DTD
        // state, can no longer re-enter it.
        if ( _dtdWriter == null ) {
            flushLine( false );

                        _dtdWriter = new StringWriter();
            _docWriter = _writer;
            _writer = _dtdWriter;
        }
    }


    /**
     * Called by the root element to leave DTD mode and if any
     * DTD parts were printer, will return a string with their
     * textual content.
     * <p>
     *  由根元素调用以离开DTD模式,并且如果任何DTD部分是打印机,则将返回具有其文本内容的字符串。
     * 
     */
    public String leaveDTD()
        throws IOException
    {
        // Only works if we're going out of DTD mode.
        if ( _writer == _dtdWriter ) {
            flushLine( false );

                        _writer = _docWriter;
            return _dtdWriter.toString();
        } else
            return null;
    }


    public void printText( String text )
        throws IOException
    {
        try {
            int length = text.length();
            for ( int i = 0 ; i < length ; ++i ) {
                if ( _pos == BufferSize ) {
                    _writer.write( _buffer );
                    _pos = 0;
                }
                _buffer[ _pos ] = text.charAt( i );
                ++_pos;
            }
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void printText( StringBuffer text )
        throws IOException
    {
        try {
            int length = text.length();
            for ( int i = 0 ; i < length ; ++i ) {
                if ( _pos == BufferSize ) {
                    _writer.write( _buffer );
                    _pos = 0;
                }
                _buffer[ _pos ] = text.charAt( i );
                ++_pos;
            }
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void printText( char[] chars, int start, int length )
        throws IOException
    {
        try {
            while ( length-- > 0 ) {
                if ( _pos == BufferSize ) {
                    _writer.write( _buffer );
                    _pos = 0;
                }
                _buffer[ _pos ] = chars[ start ];
                ++start;
                ++_pos;
            }
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void printText( char ch )
        throws IOException
    {
        try {
            if ( _pos == BufferSize ) {
                _writer.write( _buffer );
                _pos = 0;
            }
            _buffer[ _pos ] = ch;
            ++_pos;
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void printSpace()
        throws IOException
    {
        try {
            if ( _pos == BufferSize ) {
                _writer.write( _buffer );
                _pos = 0;
            }
            _buffer[ _pos ] = ' ';
            ++_pos;
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void breakLine()
        throws IOException
    {
        try {
            if ( _pos == BufferSize ) {
                _writer.write( _buffer );
                _pos = 0;
            }
            _buffer[ _pos ] = '\n';
            ++_pos;
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
    }


    public void breakLine( boolean preserveSpace )
        throws IOException
    {
        breakLine();
    }


    public void flushLine( boolean preserveSpace )
        throws IOException
    {
        // Write anything left in the buffer into the writer.
        try {
            _writer.write( _buffer, 0, _pos );
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
        }
        _pos = 0;
    }


    /**
     * Flush the output stream. Must be called when done printing
     * the document, otherwise some text might be buffered.
     * <p>
     *  刷新输出流。必须在完成打印文档时调用,否则某些文本可能会被缓冲。
     */
    public void flush()
        throws IOException
    {
        try {
            _writer.write( _buffer, 0, _pos );
            _writer.flush();
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
            throw except;
        }
        _pos = 0;
    }


    public void indent()
    {
        // NOOP
    }


    public void unindent()
    {
        // NOOP
    }


    public int getNextIndent()
    {
        return 0;
    }


    public void setNextIndent( int indent )
    {
    }


    public void setThisIndent( int indent )
    {
    }


}
