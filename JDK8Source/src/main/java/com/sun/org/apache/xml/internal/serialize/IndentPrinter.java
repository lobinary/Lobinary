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


import java.io.Writer;
import java.io.StringWriter;
import java.io.IOException;


/**
 * Extends {@link Printer} and adds support for indentation and line
 * wrapping.
 *
 * <p>
 *  扩展{@link打印机},并添加了对缩进和换行的支持。
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 */
public class IndentPrinter
    extends Printer
{


    /**
     * Holds the currently accumulating text line. This buffer will constantly
     * be reused by deleting its contents instead of reallocating it.
     * <p>
     *  保持当前累积的文本行。这个缓冲区将不断地通过删除它的内容而不是重新分配它的重用。
     * 
     */
    private StringBuffer    _line;


    /**
     * Holds the currently accumulating text that follows {@link #_line}.
     * When the end of the part is identified by a call to {@link #printSpace}
     * or {@link #breakLine}, this part is added to the accumulated line.
     * <p>
     *  保留{@link #_line}后面当前累积的文本。当通过调用{@link #printSpace}或{@link #breakLine}来识别部件的结尾时,该部件将添加到累计行。
     * 
     */
    private StringBuffer    _text;


    /**
     * Counts how many white spaces come between the accumulated line and the
     * current accumulated text. Multiple spaces at the end of the a line
     * will not be printed.
     * <p>
     *  计算累积行和当前累积文本之间有多少个空格。不会打印一行末尾的多个空格。
     * 
     */
    private int             _spaces;


    /**
     * Holds the indentation for the current line that is now accumulating in
     * memory and will be sent for printing shortly.
     * <p>
     *  保持当前行的缩进,该行现在正累积在内存中,并且很快将发送打印。
     * 
     */
    private int             _thisIndent;


    /**
     * Holds the indentation for the next line to be printed. After this line is
     * printed, {@link #_nextIndent} is assigned to {@link #_thisIndent}.
     * <p>
     * 保留要打印的下一行的缩进。打印此行后,{@link #_nextIndent}会分配给{@link #_thisIndent}。
     * 
     */
    private int             _nextIndent;


    public IndentPrinter( Writer writer, OutputFormat format)
    {
        super( writer, format );
        // Initialize everything for a first/second run.
        _line = new StringBuffer( 80 );
        _text = new StringBuffer( 20 );
        _spaces = 0;
        _thisIndent = _nextIndent = 0;
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
    {
        // Can only enter DTD state once. Once we're out of DTD
        // state, can no longer re-enter it.
        if ( _dtdWriter == null ) {
            _line.append( _text );
            _text = new StringBuffer( 20 );
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
    {
        // Only works if we're going out of DTD mode.
        if ( _writer == _dtdWriter ) {
            _line.append( _text );
            _text = new StringBuffer( 20 );
            flushLine( false );
            _writer = _docWriter;
            return _dtdWriter.toString();
        } else
            return null;
    }


    /**
     * Called to print additional text. Each time this method is called
     * it accumulates more text. When a space is printed ({@link
     * #printSpace}) all the accumulated text becomes one part and is
     * added to the accumulate line. When a line is long enough, it can
     * be broken at its text boundary.
     *
     * <p>
     *  调用打印其他文本。每次调用此方法时,它会累积更多文本。打印空格时({@link #printSpace}),所有累积的文本将变为一个部分,并添加到累加线。当线条足够长时,它可以在其文本边界处断开。
     * 
     * 
     * @param text The text to print
     */
    public void printText( String text )
    {
        _text.append( text );
    }


    public void printText( StringBuffer text )
    {
        _text.append( text.toString() );
    }


    public void printText( char ch )
    {
        _text.append( ch );
    }


    public void printText( char[] chars, int start, int length )
    {
        _text.append( chars, start, length );
    }


    /**
     * Called to print a single space between text parts that may be
     * broken into separate lines. Must not be called to print a space
     * when preserving spaces. The text accumulated so far with {@link
     * #printText} will be added to the accumulated line, and a space
     * separator will be counted. If the line accumulated so far is
     * long enough, it will be printed.
     * <p>
     *  调用在文本部分之间打印一个单独的空格,这些空格可能被分成单独的行。不能调用以在保留空格时打印空格。目前使用{@link #printText}累积的文本将被添加到累计行,并计算空格分隔符。
     * 如果到目前为止积累的行足够长,它将被打印。
     * 
     */
    public void printSpace()
    {
        // The line consists of the text accumulated in _line,
        // followed by one or more spaces as counted by _spaces,
        // followed by more space accumulated in _text:
        // -  Text is printed and accumulated into _text.
        // -  A space is printed, so _text is added to _line and
        //    a space is counted.
        // -  More text is printed and accumulated into _text.
        // -  A space is printed, the previous spaces are added
        //    to _line, the _text is added to _line, and a new
        //    space is counted.

        // If text was accumulated with printText(), then the space
        // means we have to move that text into the line and
        // start accumulating new text with printText().
        if ( _text.length() > 0 ) {
            // If the text breaks a line bounary, wrap to the next line.
            // The printed line size consists of the indentation we're going
            // to use next, the accumulated line so far, some spaces and the
            // accumulated text so far.
            if ( _format.getLineWidth() > 0 &&
                 _thisIndent + _line.length() + _spaces + _text.length() > _format.getLineWidth() ) {
                flushLine( false );
                try {
                    // Print line and new line, then zero the line contents.
                    _writer.write( _format.getLineSeparator() );
                } catch ( IOException except ) {
                    // We don't throw an exception, but hold it
                    // until the end of the document.
                    if ( _exception == null )
                        _exception = except;
                }
            }

            // Add as many spaces as we accumulaed before.
            // At the end of this loop, _spaces is zero.
            while ( _spaces > 0 ) {
                _line.append( ' ' );
                --_spaces;
            }
            _line.append( _text );
            _text = new StringBuffer( 20 );
        }
        // Starting a new word: accumulate the text between the line
        // and this new word; not a new word: just add another space.
        ++_spaces;
    }


    /**
     * Called to print a line consisting of the text accumulated so
     * far. This is equivalent to calling {@link #printSpace} but
     * forcing the line to print and starting a new line ({@link
     * #printSpace} will only start a new line if the current line
     * is long enough).
     * <p>
     *  调用打印由到目前为止累积的文本组成的一行。这相当于调用{@link #printSpace},但强制打印并开始新行({@link #printSpace}只会在当前行足够长的时候开始新行)。
     * 
     */
    public void breakLine()
    {
        breakLine( false );
    }


    public void breakLine( boolean preserveSpace )
    {
        // Equivalent to calling printSpace and forcing a flushLine.
        if ( _text.length() > 0 ) {
            while ( _spaces > 0 ) {
                _line.append( ' ' );
                --_spaces;
            }
            _line.append( _text );
            _text = new StringBuffer( 20 );
        }
        flushLine( preserveSpace );
        try {
            // Print line and new line, then zero the line contents.
            _writer.write( _format.getLineSeparator() );
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
        }
    }


    /**
     * Flushes the line accumulated so far to the writer and get ready
     * to accumulate the next line. This method is called by {@link
     * #printText} and {@link #printSpace} when the accumulated line plus
     * accumulated text are two long to fit on a given line. At the end of
     * this method _line is empty and _spaces is zero.
     * <p>
     * 将到目前为止累积的行刷新到写入器,并准备好累加下一行。当累积行加上累积文本长度为两个以适合给定行时,此方法由{@link #printText}和{@link #printSpace}调用。
     * 在此方法结束时,_line为空,_spaces为零。
     * 
     */
    public void flushLine( boolean preserveSpace )
    {
        int     indent;

        if ( _line.length() > 0 ) {
            try {

                if ( _format.getIndenting() && ! preserveSpace ) {
                    // Make sure the indentation does not blow us away.
                    indent = _thisIndent;
                    if ( ( 2 * indent ) > _format.getLineWidth() && _format.getLineWidth() > 0 )
                        indent = _format.getLineWidth() / 2;
                    // Print the indentation as spaces and set the current
                    // indentation to the next expected indentation.
                    while ( indent > 0 ) {
                        _writer.write( ' ' );
                        --indent;
                    }
                }
                _thisIndent = _nextIndent;

                // There is no need to print the spaces at the end of the line,
                // they are simply stripped and replaced with a single line
                // separator.
                _spaces = 0;
                _writer.write( _line.toString() );

                _line = new StringBuffer( 40 );
            } catch ( IOException except ) {
                // We don't throw an exception, but hold it
                // until the end of the document.
                if ( _exception == null )
                    _exception = except;
            }
        }
    }


    /**
     * Flush the output stream. Must be called when done printing
     * the document, otherwise some text might be buffered.
     * <p>
     *  刷新输出流。必须在完成打印文档时调用,否则某些文本可能会被缓冲。
     * 
     */
    public void flush()
    {
        if ( _line.length() > 0 || _text.length() > 0 )
            breakLine();
        try {
            _writer.flush();
        } catch ( IOException except ) {
            // We don't throw an exception, but hold it
            // until the end of the document.
            if ( _exception == null )
                _exception = except;
        }
    }


    /**
     * Increment the indentation for the next line.
     * <p>
     *  增加下一行的缩进。
     * 
     */
    public void indent()
    {
        _nextIndent += _format.getIndent();
    }


    /**
     * Decrement the indentation for the next line.
     * <p>
     *  减少下一行的缩进。
     */
    public void unindent()
    {
        _nextIndent -= _format.getIndent();
        if ( _nextIndent < 0 )
            _nextIndent = 0;
        // If there is no current line and we're de-identing then
        // this indentation level is actually the next level.
        if ( ( _line.length() + _spaces + _text.length() ) == 0 )
            _thisIndent = _nextIndent;
    }


    public int getNextIndent()
    {
        return _nextIndent;
    }


    public void setNextIndent( int indent )
    {
        _nextIndent = indent;
    }


    public void setThisIndent( int indent )
    {
        _thisIndent = indent;
    }


}
