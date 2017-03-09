/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.swing.text;

import java.io.Writer;
import java.io.IOException;
import java.util.Enumeration;

/**
 * AbstractWriter is an abstract class that actually
 * does the work of writing out the element tree
 * including the attributes.  In terms of how much is
 * written out per line, the writer defaults to 100.
 * But this value can be set by subclasses.
 *
 * <p>
 *  AbstractWriter是一个抽象类,它实际上完成了写出包括属性的元素树的工作。根据每行写出多少,写者默认为100.但是这个值可以通过子类设置。
 * 
 * 
 * @author Sunita Mani
 */

public abstract class AbstractWriter {

    private ElementIterator it;
    private Writer out;
    private int indentLevel = 0;
    private int indentSpace = 2;
    private Document doc = null;
    private int maxLineLength = 100;
    private int currLength = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    // If (indentLevel * indentSpace) becomes >= maxLineLength, this will
    // get incremened instead of indentLevel to avoid indenting going greater
    // than line length.
    private int offsetIndent = 0;

    /**
     * String used for end of line. If the Document has the property
     * EndOfLineStringProperty, it will be used for newlines. Otherwise
     * the System property line.separator will be used. The line separator
     * can also be set.
     * <p>
     *  用于行结束的字符串。如果文档具有属性EndOfLineStringProperty,它将用于换行。否则将使用系统属性line.separator。也可以设置线分隔符。
     * 
     */
    private String lineSeparator;

    /**
     * True indicates that when writing, the line can be split, false
     * indicates that even if the line is > than max line length it should
     * not be split.
     * <p>
     *  True表示写入时,行可以拆分,false表示即使行>大于最大行长度,也不应拆分。
     * 
     */
    private boolean canWrapLines;

    /**
     * True while the current line is empty. This will remain true after
     * indenting.
     * <p>
     *  当当前行为空时为True。这将在缩进后保持为真。
     * 
     */
    private boolean isLineEmpty;

    /**
     * Used when indenting. Will contain the spaces.
     * <p>
     *  用于缩进。将包含空格。
     * 
     */
    private char[] indentChars;

    /**
     * Used when writing out a string.
     * <p>
     *  在写出字符串时使用。
     * 
     */
    private char[] tempChars;

    /**
     * This is used in <code>writeLineSeparator</code> instead of
     * tempChars. If tempChars were used it would mean write couldn't invoke
     * <code>writeLineSeparator</code> as it might have been passed
     * tempChars.
     * <p>
     *  这在<code> writeLineSeparator </code>中使用,而不是tempChars。
     * 如果使用tempChars,这意味着写不能调用<code> writeLineSeparator </code>,因为它可能已经传递tempChars。
     * 
     */
    private char[] newlineChars;

    /**
     * Used for writing text.
     * <p>
     *  用于写文本。
     * 
     */
    private Segment segment;

    /**
     * How the text packages models newlines.
     * <p>
     *  文本包如何模型换行。
     * 
     * 
     * @see #getLineSeparator
     */
    protected static final char NEWLINE = '\n';


    /**
     * Creates a new AbstractWriter.
     * Initializes the ElementIterator with the default
     * root of the document.
     *
     * <p>
     *  创建一个新的AbstractWriter。使用文档的默认根初始化ElementIterator。
     * 
     * 
     * @param w a Writer.
     * @param doc a Document
     */
    protected AbstractWriter(Writer w, Document doc) {
        this(w, doc, 0, doc.getLength());
    }

    /**
     * Creates a new AbstractWriter.
     * Initializes the ElementIterator with the
     * element passed in.
     *
     * <p>
     *  创建一个新的AbstractWriter。使用传入的元素初始化ElementIterator。
     * 
     * 
     * @param w a Writer
     * @param doc an Element
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     */
    protected AbstractWriter(Writer w, Document doc, int pos, int len) {
        this.doc = doc;
        it = new ElementIterator(doc.getDefaultRootElement());
        out = w;
        startOffset = pos;
        endOffset = pos + len;
        Object docNewline = doc.getProperty(DefaultEditorKit.
                                       EndOfLineStringProperty);
        if (docNewline instanceof String) {
            setLineSeparator((String)docNewline);
        }
        else {
            String newline = null;
            try {
                newline = System.getProperty("line.separator");
            } catch (SecurityException se) {}
            if (newline == null) {
                // Should not get here, but if we do it means we could not
                // find a newline string, use \n in this case.
                newline = "\n";
            }
            setLineSeparator(newline);
        }
        canWrapLines = true;
    }

    /**
     * Creates a new AbstractWriter.
     * Initializes the ElementIterator with the
     * element passed in.
     *
     * <p>
     *  创建一个新的AbstractWriter。使用传入的元素初始化ElementIterator。
     * 
     * 
     * @param w a Writer
     * @param root an Element
     */
    protected AbstractWriter(Writer w, Element root) {
        this(w, root, 0, root.getEndOffset());
    }

    /**
     * Creates a new AbstractWriter.
     * Initializes the ElementIterator with the
     * element passed in.
     *
     * <p>
     * 创建一个新的AbstractWriter。使用传入的元素初始化ElementIterator。
     * 
     * 
     * @param w a Writer
     * @param root an Element
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     */
    protected AbstractWriter(Writer w, Element root, int pos, int len) {
        this.doc = root.getDocument();
        it = new ElementIterator(root);
        out = w;
        startOffset = pos;
        endOffset = pos + len;
        canWrapLines = true;
    }

    /**
     * Returns the first offset to be output.
     *
     * <p>
     *  返回要输出的第一个偏移量。
     * 
     * 
     * @since 1.3
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * Returns the last offset to be output.
     *
     * <p>
     *  返回要输出的最后一个偏移量。
     * 
     * 
     * @since 1.3
     */
    public int getEndOffset() {
        return endOffset;
    }

    /**
     * Fetches the ElementIterator.
     *
     * <p>
     *  获取ElementIterator。
     * 
     * 
     * @return the ElementIterator.
     */
    protected ElementIterator getElementIterator() {
        return it;
    }

    /**
     * Returns the Writer that is used to output the content.
     *
     * <p>
     *  返回用于输出内容的Writer。
     * 
     * 
     * @since 1.3
     */
    protected Writer getWriter() {
        return out;
    }

    /**
     * Fetches the document.
     *
     * <p>
     *  获取文档。
     * 
     * 
     * @return the Document.
     */
    protected Document getDocument() {
        return doc;
    }

    /**
     * This method determines whether the current element
     * is in the range specified.  When no range is specified,
     * the range is initialized to be the entire document.
     * inRange() returns true if the range specified intersects
     * with the element's range.
     *
     * <p>
     *  此方法确定当前元素是否在指定的范围内。当未指定范围时,范围将初始化为整个文档。如果指定的范围与元素的范围相交,inRange()将返回true。
     * 
     * 
     * @param  next an Element.
     * @return boolean that indicates whether the element
     *         is in the range.
     */
    protected boolean inRange(Element next) {
        int startOffset = getStartOffset();
        int endOffset = getEndOffset();
        if ((next.getStartOffset() >= startOffset &&
             next.getStartOffset()  < endOffset) ||
            (startOffset >= next.getStartOffset() &&
             startOffset < next.getEndOffset())) {
            return true;
        }
        return false;
    }

    /**
     * This abstract method needs to be implemented
     * by subclasses.  Its responsibility is to
     * iterate over the elements and use the write()
     * methods to generate output in the desired format.
     * <p>
     *  这个抽象方法需要通过子类来实现。它的职责是迭代元素并使用write()方法以所需的格式生成输出。
     * 
     */
    abstract protected void write() throws IOException, BadLocationException;

    /**
     * Returns the text associated with the element.
     * The assumption here is that the element is a
     * leaf element.  Throws a BadLocationException
     * when encountered.
     *
     * <p>
     *  返回与元素关联的文本。这里的假设是元素是叶元素。遇到时抛出BadLocationException。
     * 
     * 
     * @param     elem an <code>Element</code>
     * @exception BadLocationException if pos represents an invalid
     *            location within the document
     * @return    the text as a <code>String</code>
     */
    protected String getText(Element elem) throws BadLocationException {
        return doc.getText(elem.getStartOffset(),
                           elem.getEndOffset() - elem.getStartOffset());
    }


    /**
     * Writes out text.  If a range is specified when the constructor
     * is invoked, then only the appropriate range of text is written
     * out.
     *
     * <p>
     *  写出文本。如果在调用构造函数时指定了范围,则只写出适当的文本范围。
     * 
     * 
     * @param     elem an Element.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void text(Element elem) throws BadLocationException,
                                             IOException {
        int start = Math.max(getStartOffset(), elem.getStartOffset());
        int end = Math.min(getEndOffset(), elem.getEndOffset());
        if (start < end) {
            if (segment == null) {
                segment = new Segment();
            }
            getDocument().getText(start, end - start, segment);
            if (segment.count > 0) {
                write(segment.array, segment.offset, segment.count);
            }
        }
    }

    /**
     * Enables subclasses to set the number of characters they
     * want written per line.   The default is 100.
     *
     * <p>
     *  启用子类以设置每行要写入的字符数。默认值为100。
     * 
     * 
     * @param l the maximum line length.
     */
    protected void setLineLength(int l) {
        maxLineLength = l;
    }

    /**
     * Returns the maximum line length.
     *
     * <p>
     *  返回最大行长度。
     * 
     * 
     * @since 1.3
     */
    protected int getLineLength() {
        return maxLineLength;
    }

    /**
     * Sets the current line length.
     *
     * <p>
     *  设置当前行长度。
     * 
     * 
     * @since 1.3
     */
    protected void setCurrentLineLength(int length) {
        currLength = length;
        isLineEmpty = (currLength == 0);
    }

    /**
     * Returns the current line length.
     *
     * <p>
     *  返回当前行长度。
     * 
     * 
     * @since 1.3
     */
    protected int getCurrentLineLength() {
        return currLength;
    }

    /**
     * Returns true if the current line should be considered empty. This
     * is true when <code>getCurrentLineLength</code> == 0 ||
     * <code>indent</code> has been invoked on an empty line.
     *
     * <p>
     *  如果当前行应该被视为空,则返回true。当<code> getCurrentLineLength </code> == 0 ||时,这是真的<code>缩进</code>已在空行上调用。
     * 
     * 
     * @since 1.3
     */
    protected boolean isLineEmpty() {
        return isLineEmpty;
    }

    /**
     * Sets whether or not lines can be wrapped. This can be toggled
     * during the writing of lines. For example, outputting HTML might
     * set this to false when outputting a quoted string.
     *
     * <p>
     * 设置是否可以包装行。这可以在写入行期间切换。例如,输出HTML时,可以在输出带引号的字符串时将其设置为false。
     * 
     * 
     * @since 1.3
     */
    protected void setCanWrapLines(boolean newValue) {
        canWrapLines = newValue;
    }

    /**
     * Returns whether or not the lines can be wrapped. If this is false
     * no lineSeparator's will be output.
     *
     * <p>
     *  返回是否可以包装行。如果这是假的,不会输出lineSeparator的。
     * 
     * 
     * @since 1.3
     */
    protected boolean getCanWrapLines() {
        return canWrapLines;
    }

    /**
     * Enables subclasses to specify how many spaces an indent
     * maps to. When indentation takes place, the indent level
     * is multiplied by this mapping.  The default is 2.
     *
     * <p>
     *  启用子类以指定缩进映射到的空格数。当缩进发生时,缩进级别乘以此映射。默认值为2。
     * 
     * 
     * @param space an int representing the space to indent mapping.
     */
    protected void setIndentSpace(int space) {
        indentSpace = space;
    }

    /**
     * Returns the amount of space to indent.
     *
     * <p>
     *  返回缩进的空格量。
     * 
     * 
     * @since 1.3
     */
    protected int getIndentSpace() {
        return indentSpace;
    }

    /**
     * Sets the String used to represent newlines. This is initialized
     * in the constructor from either the Document, or the System property
     * line.separator.
     *
     * <p>
     *  设置用于表示换行符的字符串。这在构造函数中从Document或者系统属性line.separator初始化。
     * 
     * 
     * @since 1.3
     */
    public void setLineSeparator(String value) {
        lineSeparator = value;
    }

    /**
     * Returns the string used to represent newlines.
     *
     * <p>
     *  返回用于表示换行符的字符串。
     * 
     * 
     * @since 1.3
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * Increments the indent level. If indenting would cause
     * <code>getIndentSpace()</code> *<code>getIndentLevel()</code> to be &gt;
     * than <code>getLineLength()</code> this will not cause an indent.
     * <p>
     *  增加缩进级别。
     * 如果缩进会导致<code> getIndentSpace()</code> * <code> getIndentLevel()</code>比<code> getLineLength()</code>这
     * 不会导致缩进。
     *  增加缩进级别。
     * 
     */
    protected void incrIndent() {
        // Only increment to a certain point.
        if (offsetIndent > 0) {
            offsetIndent++;
        }
        else {
            if (++indentLevel * getIndentSpace() >= getLineLength()) {
                offsetIndent++;
                --indentLevel;
            }
        }
    }

    /**
     * Decrements the indent level.
     * <p>
     *  减少缩进级别。
     * 
     */
    protected void decrIndent() {
        if (offsetIndent > 0) {
            --offsetIndent;
        }
        else {
            indentLevel--;
        }
    }

    /**
     * Returns the current indentation level. That is, the number of times
     * <code>incrIndent</code> has been invoked minus the number of times
     * <code>decrIndent</code> has been invoked.
     *
     * <p>
     *  返回当前缩进级别。也就是说,已调用<code> incrIndent </code>的次数减去已调用<code> decrIndent </code>的次数。
     * 
     * 
     * @since 1.3
     */
    protected int getIndentLevel() {
        return indentLevel;
    }

    /**
     * Does indentation. The number of spaces written
     * out is indent level times the space to map mapping. If the current
     * line is empty, this will not make it so that the current line is
     * still considered empty.
     *
     * <p>
     *  缩进。写出的空格数是映射空间映射的缩进级别。如果当前行为空,则不会使当前行仍为空。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void indent() throws IOException {
        int max = getIndentLevel() * getIndentSpace();
        if (indentChars == null || max > indentChars.length) {
            indentChars = new char[max];
            for (int counter = 0; counter < max; counter++) {
                indentChars[counter] = ' ';
            }
        }
        int length = getCurrentLineLength();
        boolean wasEmpty = isLineEmpty();
        output(indentChars, 0, max);
        if (wasEmpty && length == 0) {
            isLineEmpty = true;
        }
    }

    /**
     * Writes out a character. This is implemented to invoke
     * the <code>write</code> method that takes a char[].
     *
     * <p>
     *  写出一个字符。这被实现以调用采用char []的<code> write </code>方法。
     * 
     * 
     * @param     ch a char.
     * @exception IOException on any I/O error
     */
    protected void write(char ch) throws IOException {
        if (tempChars == null) {
            tempChars = new char[128];
        }
        tempChars[0] = ch;
        write(tempChars, 0, 1);
    }

    /**
     * Writes out a string. This is implemented to invoke the
     * <code>write</code> method that takes a char[].
     *
     * <p>
     *  写出一个字符串。这被实现以调用采用char []的<code> write </code>方法。
     * 
     * 
     * @param     content a String.
     * @exception IOException on any I/O error
     */
    protected void write(String content) throws IOException {
        if (content == null) {
            return;
        }
        int size = content.length();
        if (tempChars == null || tempChars.length < size) {
            tempChars = new char[size];
        }
        content.getChars(0, size, tempChars, 0);
        write(tempChars, 0, size);
    }

    /**
     * Writes the line separator. This invokes <code>output</code> directly
     * as well as setting the <code>lineLength</code> to 0.
     *
     * <p>
     * 写入行分隔符。这直接调用<code>输出</code>以及将<code> lineLength </code>设置为0。
     * 
     * 
     * @since 1.3
     */
    protected void writeLineSeparator() throws IOException {
        String newline = getLineSeparator();
        int length = newline.length();
        if (newlineChars == null || newlineChars.length < length) {
            newlineChars = new char[length];
        }
        newline.getChars(0, length, newlineChars, 0);
        output(newlineChars, 0, length);
        setCurrentLineLength(0);
    }

    /**
     * All write methods call into this one. If <code>getCanWrapLines()</code>
     * returns false, this will call <code>output</code> with each sequence
     * of <code>chars</code> that doesn't contain a NEWLINE, followed
     * by a call to <code>writeLineSeparator</code>. On the other hand,
     * if <code>getCanWrapLines()</code> returns true, this will split the
     * string, as necessary, so <code>getLineLength</code> is honored.
     * The only exception is if the current string contains no whitespace,
     * and won't fit in which case the line length will exceed
     * <code>getLineLength</code>.
     *
     * <p>
     *  所有的写入方法调用这一个。
     * 如果<code> getCanWrapLines()</code>返回false,则会为<code> chars </code>的每个序列调用<code> output </code>,其中不包含NEW
     * LINE, <code> writeLineSeparator </code>。
     *  所有的写入方法调用这一个。
     * 另一方面,如果<code> getCanWrapLines()</code>返回true,这将根据需要拆分字符串,因此<code> getLineLength </code>被授权。
     * 唯一的例外是如果当前字符串不包含空格,并且不适合在这种情况下行长度将超过<code> getLineLength </code>。
     * 
     * 
     * @since 1.3
     */
    protected void write(char[] chars, int startIndex, int length)
                   throws IOException {
        if (!getCanWrapLines()) {
            // We can not break string, just track if a newline
            // is in it.
            int lastIndex = startIndex;
            int endIndex = startIndex + length;
            int newlineIndex = indexOf(chars, NEWLINE, startIndex, endIndex);
            while (newlineIndex != -1) {
                if (newlineIndex > lastIndex) {
                    output(chars, lastIndex, newlineIndex - lastIndex);
                }
                writeLineSeparator();
                lastIndex = newlineIndex + 1;
                newlineIndex = indexOf(chars, '\n', lastIndex, endIndex);
            }
            if (lastIndex < endIndex) {
                output(chars, lastIndex, endIndex - lastIndex);
            }
        }
        else {
            // We can break chars if the length exceeds maxLength.
            int lastIndex = startIndex;
            int endIndex = startIndex + length;
            int lineLength = getCurrentLineLength();
            int maxLength = getLineLength();

            while (lastIndex < endIndex) {
                int newlineIndex = indexOf(chars, NEWLINE, lastIndex,
                                           endIndex);
                boolean needsNewline = false;
                boolean forceNewLine = false;

                lineLength = getCurrentLineLength();
                if (newlineIndex != -1 && (lineLength +
                              (newlineIndex - lastIndex)) < maxLength) {
                    if (newlineIndex > lastIndex) {
                        output(chars, lastIndex, newlineIndex - lastIndex);
                    }
                    lastIndex = newlineIndex + 1;
                    forceNewLine = true;
                }
                else if (newlineIndex == -1 && (lineLength +
                                (endIndex - lastIndex)) < maxLength) {
                    if (endIndex > lastIndex) {
                        output(chars, lastIndex, endIndex - lastIndex);
                    }
                    lastIndex = endIndex;
                }
                else {
                    // Need to break chars, find a place to split chars at,
                    // from lastIndex to endIndex,
                    // or maxLength - lineLength whichever is smaller
                    int breakPoint = -1;
                    int maxBreak = Math.min(endIndex - lastIndex,
                                            maxLength - lineLength - 1);
                    int counter = 0;
                    while (counter < maxBreak) {
                        if (Character.isWhitespace(chars[counter +
                                                        lastIndex])) {
                            breakPoint = counter;
                        }
                        counter++;
                    }
                    if (breakPoint != -1) {
                        // Found a place to break at.
                        breakPoint += lastIndex + 1;
                        output(chars, lastIndex, breakPoint - lastIndex);
                        lastIndex = breakPoint;
                        needsNewline = true;
                    }
                    else {
                        // No where good to break.

                        // find the next whitespace, or write out the
                        // whole string.
                            // maxBreak will be negative if current line too
                            // long.
                            counter = Math.max(0, maxBreak);
                            maxBreak = endIndex - lastIndex;
                            while (counter < maxBreak) {
                                if (Character.isWhitespace(chars[counter +
                                                                lastIndex])) {
                                    breakPoint = counter;
                                    break;
                                }
                                counter++;
                            }
                            if (breakPoint == -1) {
                                output(chars, lastIndex, endIndex - lastIndex);
                                breakPoint = endIndex;
                            }
                            else {
                                breakPoint += lastIndex;
                                if (chars[breakPoint] == NEWLINE) {
                                    output(chars, lastIndex, breakPoint++ -
                                           lastIndex);
                                forceNewLine = true;
                                }
                                else {
                                    output(chars, lastIndex, ++breakPoint -
                                              lastIndex);
                                needsNewline = true;
                                }
                            }
                            lastIndex = breakPoint;
                        }
                    }
                if (forceNewLine || needsNewline || lastIndex < endIndex) {
                    writeLineSeparator();
                    if (lastIndex < endIndex || !forceNewLine) {
                        indent();
                    }
                }
            }
        }
    }

    /**
     * Writes out the set of attributes as " &lt;name&gt;=&lt;value&gt;"
     * pairs. It throws an IOException when encountered.
     *
     * <p>
     *  将属性集写为"&lt; name&gt; =&lt; value&gt;"对。遇到时会抛出IOException。
     * 
     * 
     * @param     attr an AttributeSet.
     * @exception IOException on any I/O error
     */
    protected void writeAttributes(AttributeSet attr) throws IOException {

        Enumeration names = attr.getAttributeNames();
        while (names.hasMoreElements()) {
            Object name = names.nextElement();
            write(" " + name + "=" + attr.getAttribute(name));
        }
    }

    /**
     * The last stop in writing out content. All the write methods eventually
     * make it to this method, which invokes <code>write</code> on the
     * Writer.
     * <p>This method also updates the line length based on
     * <code>length</code>. If this is invoked to output a newline, the
     * current line length will need to be reset as will no longer be
     * valid. If it is up to the caller to do this. Use
     * <code>writeLineSeparator</code> to write out a newline, which will
     * property update the current line length.
     *
     * <p>
     *  写出内容的最后一站。所有的写方法最终都使用这个方法,它调用Writer上的<code> write </code>。 <p>此方法还会根据<code> length </code>更新行长度。
     * 如果调用此命令来输出换行符,则当前行长度将需要重置,因为它将不再有效。如果是由调用者做这个。使用<code> writeLineSeparator </code>写出一个换行符,它将更新当前行长度。
     * 
     * 
     * @since 1.3
     */
    protected void output(char[] content, int start, int length)
                   throws IOException {
        getWriter().write(content, start, length);
        setCurrentLineLength(getCurrentLineLength() + length);
    }

    /**
     * Support method to locate an occurrence of a particular character.
     * <p>
     */
    private int indexOf(char[] chars, char sChar, int startIndex,
                        int endIndex) {
        while(startIndex < endIndex) {
            if (chars[startIndex] == sChar) {
                return startIndex;
            }
            startIndex++;
        }
        return -1;
    }
}
