/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A <code>JTextArea</code> is a multi-line area that displays plain text.
 * It is intended to be a lightweight component that provides source
 * compatibility with the <code>java.awt.TextArea</code> class where it can
 * reasonably do so.
 * You can find information and examples of using all the text components in
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/text.html">Using Text Components</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 * This component has capabilities not found in the
 * <code>java.awt.TextArea</code> class.  The superclass should be
 * consulted for additional capabilities.
 * Alternative multi-line text classes with
 * more capabilities are <code>JTextPane</code> and <code>JEditorPane</code>.
 * <p>
 * The <code>java.awt.TextArea</code> internally handles scrolling.
 * <code>JTextArea</code> is different in that it doesn't manage scrolling,
 * but implements the swing <code>Scrollable</code> interface.  This allows it
 * to be placed inside a <code>JScrollPane</code> if scrolling
 * behavior is desired, and used directly if scrolling is not desired.
 * <p>
 * The <code>java.awt.TextArea</code> has the ability to do line wrapping.
 * This was controlled by the horizontal scrolling policy.  Since
 * scrolling is not done by <code>JTextArea</code> directly, backward
 * compatibility must be provided another way.  <code>JTextArea</code> has
 * a bound property for line wrapping that controls whether or
 * not it will wrap lines.  By default, the line wrapping property
 * is set to false (not wrapped).
 * <p>
 * <code>java.awt.TextArea</code> has two properties <code>rows</code>
 * and <code>columns</code> that are used to determine the preferred size.
 * <code>JTextArea</code> uses these properties to indicate the
 * preferred size of the viewport when placed inside a <code>JScrollPane</code>
 * to match the functionality provided by <code>java.awt.TextArea</code>.
 * <code>JTextArea</code> has a preferred size of what is needed to
 * display all of the text, so that it functions properly inside of
 * a <code>JScrollPane</code>.  If the value for <code>rows</code>
 * or <code>columns</code> is equal to zero,
 * the preferred size along that axis is used for
 * the viewport preferred size along the same axis.
 * <p>
 * The <code>java.awt.TextArea</code> could be monitored for changes by adding
 * a <code>TextListener</code> for <code>TextEvent</code>s.
 * In the <code>JTextComponent</code> based
 * components, changes are broadcasted from the model via a
 * <code>DocumentEvent</code> to <code>DocumentListeners</code>.
 * The <code>DocumentEvent</code> gives
 * the location of the change and the kind of change if desired.
 * The code fragment might look something like:
 * <pre>
 *    DocumentListener myListener = ??;
 *    JTextArea myArea = ??;
 *    myArea.getDocument().addDocumentListener(myListener);
 * </pre>
 *
 * <dl>
 * <dt><b><font size=+1>Newlines</font></b>
 * <dd>
 * For a discussion on how newlines are handled, see
 * <a href="text/DefaultEditorKit.html">DefaultEditorKit</a>.
 * </dl>
 *
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: A multi-line area that displays plain text.
 *
 * <p>
 *  <code> JTextArea </code>是一个显示纯文本的多行区域。
 * 它旨在是一个轻量级组件,它提供源代码与<code> java.awt.TextArea </code>类的兼容性,它可以合理地这样做。
 * 您可以在<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/text.html">使用文本组件</a>中找到使用所有文
 * 本组件的信息和示例, Java教程</em>中的<em>部分。
 * 它旨在是一个轻量级组件,它提供源代码与<code> java.awt.TextArea </code>类的兼容性,它可以合理地这样做。
 * 
 * <p>
 *  此组件具有<code> java.awt.TextArea </code>类中找不到的功能。应该咨询超类以获得更多功能。
 * 具有更多功能的替代多行文本类是<code> JTextPane </code>和<code> JEditorPane </code>。
 * <p>
 *  <code> java.awt.TextArea </code>在内部处理滚动。
 *  <code> JTextArea </code>的不同之处在于它不管理滚动,而是实现swing <code> Scrollable </code>接口。
 * 这允许它被放置在<code> JScrollPane </code>内,如果需要滚动行为,并直接使用,如果不需要滚动。
 * <p>
 * <code> java.awt.TextArea </code>有能力做线包装。这是由水平滚动策略控制的。
 * 由于滚动不是由<code> JTextArea </code>直接完成的,因此必须以另一种方式提供向后兼容性。
 *  <code> JTextArea </code>具有用于换行的bound属性,用于控制是否换行。默认情况下,行包装属性设置为false(不包装)。
 * <p>
 *  <code> java.awt.TextArea </code>有两个属性<code> rows </code>和<code> columns </code>,用于确定首选大小。
 *  <code> JTextArea </code>使用这些属性来指示当放置在<code> JScrollPane </code>中以匹配<code> java.awt.TextArea </code>提
 * 供的功能时视口的首选大小。
 *  <code> java.awt.TextArea </code>有两个属性<code> rows </code>和<code> columns </code>,用于确定首选大小。
 *  <code> JTextArea </code>具有显示所有文本所需的大小,以使其在<code> JScrollPane </code>中正常工作。
 * 如果<code> rows </code>或<code> columns </code>的值等于零,则沿着该轴的首选大小将用于沿同一轴的视口首选大小。
 * <p>
 * 可以通过为<code> TextEvent </code>添加<code> TextListener </code>来监视<code> java.awt.TextArea </code>在基于<code>
 *  JTextComponent </code>的组件中,通过<code> DocumentEvent </code>到<code> DocumentListeners </code>从模型广播更改。
 *  <code> DocumentEvent </code>给出更改的位置和更改的类型(如果需要)。代码片段可能如下所示：。
 * <pre>
 *  DocumentListener myListener = ??; JTextArea myArea = ??; myArea.getDocument()。
 * addDocumentListener(myListener);。
 * </pre>
 * 
 * <dl>
 *  <dt> <b> <font size = + 1>换行符</font> </b>
 * <dd>
 *  有关如何处理新行的讨论,请参见<a href="text/DefaultEditorKit.html"> DefaultEditorKit </a>。
 * </dl>
 * 
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：显示纯文本的多行区域。
 * 
 * 
 * @author  Timothy Prinzing
 * @see JTextPane
 * @see JEditorPane
 */
public class JTextArea extends JTextComponent {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "TextAreaUI";

    /**
     * Constructs a new TextArea.  A default model is set, the initial string
     * is null, and rows/columns are set to 0.
     * <p>
     *  构造新的TextArea。设置默认模型,初始字符串为null,并且rows / columns设置为0。
     * 
     */
    public JTextArea() {
        this(null, null, 0, 0);
    }

    /**
     * Constructs a new TextArea with the specified text displayed.
     * A default model is created and rows/columns are set to 0.
     *
     * <p>
     * 构造具有指定文本显示的新TextArea。创建默认模型,并将行/列设置为0。
     * 
     * 
     * @param text the text to be displayed, or null
     */
    public JTextArea(String text) {
        this(null, text, 0, 0);
    }

    /**
     * Constructs a new empty TextArea with the specified number of
     * rows and columns.  A default model is created, and the initial
     * string is null.
     *
     * <p>
     *  构造具有指定行数和列数的新的空TextArea。将创建一个默认模型,并且初始字符串为null。
     * 
     * 
     * @param rows the number of rows &gt;= 0
     * @param columns the number of columns &gt;= 0
     * @exception IllegalArgumentException if the rows or columns
     *  arguments are negative.
     */
    public JTextArea(int rows, int columns) {
        this(null, null, rows, columns);
    }

    /**
     * Constructs a new TextArea with the specified text and number
     * of rows and columns.  A default model is created.
     *
     * <p>
     *  构造具有指定文本和行数和列数的新TextArea。创建默认模型。
     * 
     * 
     * @param text the text to be displayed, or null
     * @param rows the number of rows &gt;= 0
     * @param columns the number of columns &gt;= 0
     * @exception IllegalArgumentException if the rows or columns
     *  arguments are negative.
     */
    public JTextArea(String text, int rows, int columns) {
        this(null, text, rows, columns);
    }

    /**
     * Constructs a new JTextArea with the given document model, and defaults
     * for all of the other arguments (null, 0, 0).
     *
     * <p>
     *  使用给定的文档模型构造新的JTextArea,并为所有其他参数(null,0,0)默认。
     * 
     * 
     * @param doc  the model to use
     */
    public JTextArea(Document doc) {
        this(doc, null, 0, 0);
    }

    /**
     * Constructs a new JTextArea with the specified number of rows
     * and columns, and the given model.  All of the constructors
     * feed through this constructor.
     *
     * <p>
     *  构造具有指定行数和列数以及给定模型的新JTextArea。所有的构造函数都通过这个构造函数。
     * 
     * 
     * @param doc the model to use, or create a default one if null
     * @param text the text to be displayed, null if none
     * @param rows the number of rows &gt;= 0
     * @param columns the number of columns &gt;= 0
     * @exception IllegalArgumentException if the rows or columns
     *  arguments are negative.
     */
    public JTextArea(Document doc, String text, int rows, int columns) {
        super();
        this.rows = rows;
        this.columns = columns;
        if (doc == null) {
            doc = createDefaultModel();
        }
        setDocument(doc);
        if (text != null) {
            setText(text);
            select(0, 0);
        }
        if (rows < 0) {
            throw new IllegalArgumentException("rows: " + rows);
        }
        if (columns < 0) {
            throw new IllegalArgumentException("columns: " + columns);
        }
        LookAndFeel.installProperty(this,
                                    "focusTraversalKeysForward",
                                    JComponent.
                                    getManagingFocusForwardTraversalKeys());
        LookAndFeel.installProperty(this,
                                    "focusTraversalKeysBackward",
                                    JComponent.
                                    getManagingFocusBackwardTraversalKeys());
    }

    /**
     * Returns the class ID for the UI.
     *
     * <p>
     *  返回UI的类ID。
     * 
     * 
     * @return the ID ("TextAreaUI")
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Creates the default implementation of the model
     * to be used at construction if one isn't explicitly
     * given.  A new instance of PlainDocument is returned.
     *
     * <p>
     *  如果没有明确给出,则创建要在构建时使用的模型的默认实现。返回一个PlainDocument的新实例。
     * 
     * 
     * @return the default document model
     */
    protected Document createDefaultModel() {
        return new PlainDocument();
    }

    /**
     * Sets the number of characters to expand tabs to.
     * This will be multiplied by the maximum advance for
     * variable width fonts.  A PropertyChange event ("tabSize") is fired
     * when the tab size changes.
     *
     * <p>
     *  设置要将标签扩展到的字符数。这将乘以可变宽度字体的最大提前量。当标签大小更改时,会触发PropertyChange事件("tabSize")。
     * 
     * 
     * @param size number of characters to expand to
     * @see #getTabSize
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: the number of characters to expand tabs to
     */
    public void setTabSize(int size) {
        Document doc = getDocument();
        if (doc != null) {
            int old = getTabSize();
            doc.putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(size));
            firePropertyChange("tabSize", old, size);
        }
    }

    /**
     * Gets the number of characters used to expand tabs.  If the document is
     * null or doesn't have a tab setting, return a default of 8.
     *
     * <p>
     *  获取用于扩展选项卡的字符数。如果文档为null或没有制表符设置,则返回默认值8。
     * 
     * 
     * @return the number of characters
     */
    public int getTabSize() {
        int size = 8;
        Document doc = getDocument();
        if (doc != null) {
            Integer i = (Integer) doc.getProperty(PlainDocument.tabSizeAttribute);
            if (i != null) {
                size = i.intValue();
            }
        }
        return size;
    }

    /**
     * Sets the line-wrapping policy of the text area.  If set
     * to true the lines will be wrapped if they are too long
     * to fit within the allocated width.  If set to false,
     * the lines will always be unwrapped.  A <code>PropertyChange</code>
     * event ("lineWrap") is fired when the policy is changed.
     * By default this property is false.
     *
     * <p>
     *  设置文本区域的换行策略。如果设置为true,那么如果它们太长以至于不能在分配的宽度内,则线将被包装。如果设置为false,那些行总是被解开。
     * 更改策略时会触发<code> PropertyChange </code>事件("lineWrap")。默认情况下,此属性为false。
     * 
     * 
     * @param wrap indicates if lines should be wrapped
     * @see #getLineWrap
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: should lines be wrapped
     */
    public void setLineWrap(boolean wrap) {
        boolean old = this.wrap;
        this.wrap = wrap;
        firePropertyChange("lineWrap", old, wrap);
    }

    /**
     * Gets the line-wrapping policy of the text area.  If set
     * to true the lines will be wrapped if they are too long
     * to fit within the allocated width.  If set to false,
     * the lines will always be unwrapped.
     *
     * <p>
     * 获取文本区域的换行策略。如果设置为true,那么如果它们太长以至于不能在分配的宽度内,则线将被包装。如果设置为false,那些行总是被解开。
     * 
     * 
     * @return if lines will be wrapped
     */
    public boolean getLineWrap() {
        return wrap;
    }

    /**
     * Sets the style of wrapping used if the text area is wrapping
     * lines.  If set to true the lines will be wrapped at word
     * boundaries (whitespace) if they are too long
     * to fit within the allocated width.  If set to false,
     * the lines will be wrapped at character boundaries.
     * By default this property is false.
     *
     * <p>
     *  设置在文本区域换行时使用的换行方式。如果设置为true,如果它们太长以至于不能在分配的宽度内,则行将被包裹在字边界(空白)。如果设置为false,那么行将在字符边界处包装。
     * 默认情况下,此属性为false。
     * 
     * 
     * @param word indicates if word boundaries should be used
     *   for line wrapping
     * @see #getWrapStyleWord
     * @beaninfo
     *   preferred: false
     *       bound: true
     * description: should wrapping occur at word boundaries
     */
    public void setWrapStyleWord(boolean word) {
        boolean old = this.word;
        this.word = word;
        firePropertyChange("wrapStyleWord", old, word);
    }

    /**
     * Gets the style of wrapping used if the text area is wrapping
     * lines.  If set to true the lines will be wrapped at word
     * boundaries (ie whitespace) if they are too long
     * to fit within the allocated width.  If set to false,
     * the lines will be wrapped at character boundaries.
     *
     * <p>
     *  如果文本区域包裹换行,获取使用的换行样式。如果设置为true,那么行将被包裹在字边界(即空格),如果它们太长以至于不能在分配的宽度内。如果设置为false,那么行将在字符边界处包装。
     * 
     * 
     * @return if the wrap style should be word boundaries
     *  instead of character boundaries
     * @see #setWrapStyleWord
     */
    public boolean getWrapStyleWord() {
        return word;
    }

    /**
     * Translates an offset into the components text to a
     * line number.
     *
     * <p>
     *  将偏移转换为组件文本到行号。
     * 
     * 
     * @param offset the offset &gt;= 0
     * @return the line number &gt;= 0
     * @exception BadLocationException thrown if the offset is
     *   less than zero or greater than the document length.
     */
    public int getLineOfOffset(int offset) throws BadLocationException {
        Document doc = getDocument();
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength()+1);
        } else {
            Element map = getDocument().getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    /**
     * Determines the number of lines contained in the area.
     *
     * <p>
     *  确定区域中包含的线数。
     * 
     * 
     * @return the number of lines &gt; 0
     */
    public int getLineCount() {
        Element map = getDocument().getDefaultRootElement();
        return map.getElementCount();
    }

    /**
     * Determines the offset of the start of the given line.
     *
     * <p>
     *  确定给定行开始的偏移量。
     * 
     * 
     * @param line  the line number to translate &gt;= 0
     * @return the offset &gt;= 0
     * @exception BadLocationException thrown if the line is
     * less than zero or greater or equal to the number of
     * lines contained in the document (as reported by
     * getLineCount).
     */
    public int getLineStartOffset(int line) throws BadLocationException {
        int lineCount = getLineCount();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= lineCount) {
            throw new BadLocationException("No such line", getDocument().getLength()+1);
        } else {
            Element map = getDocument().getDefaultRootElement();
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    /**
     * Determines the offset of the end of the given line.
     *
     * <p>
     *  确定给定行结束的偏移量。
     * 
     * 
     * @param line  the line &gt;= 0
     * @return the offset &gt;= 0
     * @exception BadLocationException Thrown if the line is
     * less than zero or greater or equal to the number of
     * lines contained in the document (as reported by
     * getLineCount).
     */
    public int getLineEndOffset(int line) throws BadLocationException {
        int lineCount = getLineCount();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= lineCount) {
            throw new BadLocationException("No such line", getDocument().getLength()+1);
        } else {
            Element map = getDocument().getDefaultRootElement();
            Element lineElem = map.getElement(line);
            int endOffset = lineElem.getEndOffset();
            // hide the implicit break at the end of the document
            return ((line == lineCount - 1) ? (endOffset - 1) : endOffset);
        }
    }

    // --- java.awt.TextArea methods ---------------------------------

    /**
     * Inserts the specified text at the specified position.  Does nothing
     * if the model is null or if the text is null or empty.
     *
     * <p>
     *  在指定位置插入指定的文本。如果模型为null或文本为空或为空,则不执行任何操作。
     * 
     * 
     * @param str the text to insert
     * @param pos the position at which to insert &gt;= 0
     * @exception IllegalArgumentException  if pos is an
     *  invalid position in the model
     * @see TextComponent#setText
     * @see #replaceRange
     */
    public void insert(String str, int pos) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(pos, str, null);
            } catch (BadLocationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /**
     * Appends the given text to the end of the document.  Does nothing if
     * the model is null or the string is null or empty.
     *
     * <p>
     *  将给定文本附加到文档的末尾。如果模型为null或字符串为null或空,则不执行任何操作。
     * 
     * 
     * @param str the text to insert
     * @see #insert
     */
    public void append(String str) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(doc.getLength(), str, null);
            } catch (BadLocationException e) {
            }
        }
    }

    /**
     * Replaces text from the indicated start to end position with the
     * new text specified.  Does nothing if the model is null.  Simply
     * does a delete if the new string is null or empty.
     *
     * <p>
     *  使用指定的新文本替换指定的开始到结束位置的文本。如果模型为null,则不执行任何操作。只要做一个删除,如果新的字符串为null或空。
     * 
     * 
     * @param str the text to use as the replacement
     * @param start the start position &gt;= 0
     * @param end the end position &gt;= start
     * @exception IllegalArgumentException  if part of the range is an
     *  invalid position in the model
     * @see #insert
     * @see #replaceRange
     */
    public void replaceRange(String str, int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("end before start");
        }
        Document doc = getDocument();
        if (doc != null) {
            try {
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).replace(start, end - start, str,
                                                    null);
                }
                else {
                    doc.remove(start, end - start);
                    doc.insertString(start, str, null);
                }
            } catch (BadLocationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /**
     * Returns the number of rows in the TextArea.
     *
     * <p>
     *  返回TextArea中的行数。
     * 
     * 
     * @return the number of rows &gt;= 0
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows for this TextArea.  Calls invalidate() after
     * setting the new value.
     *
     * <p>
     * 设置此TextArea的行数。设置新值后调用invalidate()。
     * 
     * 
     * @param rows the number of rows &gt;= 0
     * @exception IllegalArgumentException if rows is less than 0
     * @see #getRows
     * @beaninfo
     * description: the number of rows preferred for display
     */
    public void setRows(int rows) {
        int oldVal = this.rows;
        if (rows < 0) {
            throw new IllegalArgumentException("rows less than zero.");
        }
        if (rows != oldVal) {
            this.rows = rows;
            invalidate();
        }
    }

    /**
     * Defines the meaning of the height of a row.  This defaults to
     * the height of the font.
     *
     * <p>
     *  定义行的高度的含义。默认为字体的高度。
     * 
     * 
     * @return the height &gt;= 1
     */
    protected int getRowHeight() {
        if (rowHeight == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            rowHeight = metrics.getHeight();
        }
        return rowHeight;
    }

    /**
     * Returns the number of columns in the TextArea.
     *
     * <p>
     *  返回TextArea中的列数。
     * 
     * 
     * @return number of columns &gt;= 0
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns for this TextArea.  Does an invalidate()
     * after setting the new value.
     *
     * <p>
     *  设置此TextArea的列数。在设置新值后设置invalidate()。
     * 
     * 
     * @param columns the number of columns &gt;= 0
     * @exception IllegalArgumentException if columns is less than 0
     * @see #getColumns
     * @beaninfo
     * description: the number of columns preferred for display
     */
    public void setColumns(int columns) {
        int oldVal = this.columns;
        if (columns < 0) {
            throw new IllegalArgumentException("columns less than zero.");
        }
        if (columns != oldVal) {
            this.columns = columns;
            invalidate();
        }
    }

    /**
     * Gets column width.
     * The meaning of what a column is can be considered a fairly weak
     * notion for some fonts.  This method is used to define the width
     * of a column.  By default this is defined to be the width of the
     * character <em>m</em> for the font used.  This method can be
     * redefined to be some alternative amount.
     *
     * <p>
     *  获取列宽。列的含义对于某些字体可以被认为是一个相当弱的概念。此方法用于定义列的宽度。默认情况下,这被定义为所使用的字体的字符宽度<em> m </em>。这种方法可以重新定义为一些替代量。
     * 
     * 
     * @return the column width &gt;= 1
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            columnWidth = metrics.charWidth('m');
        }
        return columnWidth;
    }

    // --- Component methods -----------------------------------------

    /**
     * Returns the preferred size of the TextArea.  This is the
     * maximum of the size needed to display the text and the
     * size requested for the viewport.
     *
     * <p>
     *  返回TextArea的首选大小。这是显示文本和视口所需大小所需的最大大小。
     * 
     * 
     * @return the size
     */
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d = (d == null) ? new Dimension(400,400) : d;
        Insets insets = getInsets();

        if (columns != 0) {
            d.width = Math.max(d.width, columns * getColumnWidth() +
                    insets.left + insets.right);
        }
        if (rows != 0) {
            d.height = Math.max(d.height, rows * getRowHeight() +
                                insets.top + insets.bottom);
        }
        return d;
    }

    /**
     * Sets the current font.  This removes cached row height and column
     * width so the new font will be reflected, and calls revalidate().
     *
     * <p>
     *  设置当前字体。这将删除缓存的行高度和列宽度,以便反映新的字体,并调用revalidate()。
     * 
     * 
     * @param f the font to use as the current font
     */
    public void setFont(Font f) {
        super.setFont(f);
        rowHeight = 0;
        columnWidth = 0;
    }


    /**
     * Returns a string representation of this JTextArea. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JTextArea的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JTextArea.
     */
    protected String paramString() {
        String wrapString = (wrap ?
                             "true" : "false");
        String wordString = (word ?
                             "true" : "false");

        return super.paramString() +
        ",colums=" + columns +
        ",columWidth=" + columnWidth +
        ",rows=" + rows +
        ",rowHeight=" + rowHeight +
        ",word=" + wordString +
        ",wrap=" + wrapString;
    }

    // --- Scrollable methods ----------------------------------------

    /**
     * Returns true if a viewport should always force the width of this
     * Scrollable to match the width of the viewport.  This is implemented
     * to return true if the line wrapping policy is true, and false
     * if lines are not being wrapped.
     *
     * <p>
     *  如果视口应始终强制此Scrollable的宽度与视口的宽度匹配,则返回true。如果行包装策略为true,则返回true,如果没有包装行,则返回false。
     * 
     * 
     * @return true if a viewport should force the Scrollables width
     * to match its own.
     */
    public boolean getScrollableTracksViewportWidth() {
        return (wrap) ? true : super.getScrollableTracksViewportWidth();
    }

    /**
     * Returns the preferred size of the viewport if this component
     * is embedded in a JScrollPane.  This uses the desired column
     * and row settings if they have been set, otherwise the superclass
     * behavior is used.
     *
     * <p>
     * 如果此组件嵌入在JScrollPane中,则返回视口的首选大小。如果已设置它们,则使用所需的列和行设置,否则使用超类行为。
     * 
     * 
     * @return The preferredSize of a JViewport whose view is this Scrollable.
     * @see JViewport#getPreferredSize
     */
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = super.getPreferredScrollableViewportSize();
        size = (size == null) ? new Dimension(400,400) : size;
        Insets insets = getInsets();

        size.width = (columns == 0) ? size.width :
                columns * getColumnWidth() + insets.left + insets.right;
        size.height = (rows == 0) ? size.height :
                rows * getRowHeight() + insets.top + insets.bottom;
        return size;
    }

    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one new row
     * or column, depending on the value of orientation.  This is implemented
     * to use the values returned by the <code>getRowHeight</code> and
     * <code>getColumnWidth</code> methods.
     * <p>
     * Scrolling containers, like JScrollPane, will use this method
     * each time the user requests a unit scroll.
     *
     * <p>
     *  显示逻辑行或列的组件应计算滚动增量,这将完全暴露一个新行或列,具体取决于定向值。
     * 这被实现为使用<code> getRowHeight </code>和<code> getColumnWidth </code>方法返回的值。
     * <p>
     *  滚动容器(如JScrollPane)将在每次用户请求单元滚动时使用此方法。
     * 
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation Either SwingConstants.VERTICAL or
     *   SwingConstants.HORIZONTAL.
     * @param direction Less than zero to scroll up/left,
     *   greater than zero for down/right.
     * @return The "unit" increment for scrolling in the specified direction
     * @exception IllegalArgumentException for an invalid orientation
     * @see JScrollBar#setUnitIncrement
     * @see #getRowHeight
     * @see #getColumnWidth
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch (orientation) {
        case SwingConstants.VERTICAL:
            return getRowHeight();
        case SwingConstants.HORIZONTAL:
            return getColumnWidth();
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }

/////////////////
// Accessibility support
////////////////


    /**
     * Gets the AccessibleContext associated with this JTextArea.
     * For JTextAreas, the AccessibleContext takes the form of an
     * AccessibleJTextArea.
     * A new AccessibleJTextArea instance is created if necessary.
     *
     * <p>
     *  获取与此JTextArea关联的AccessibleContext。对于JTextAreas,AccessibleContext采用AccessibleJTextArea的形式。
     * 如果需要,将创建一个新的AccessibleJTextArea实例。
     * 
     * 
     * @return an AccessibleJTextArea that serves as the
     *         AccessibleContext of this JTextArea
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJTextArea();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JTextArea</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to text area user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  此类实现对<code> JTextArea </code>类的辅助功能支持。它提供了适用于文本区域用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJTextArea extends AccessibleJTextComponent {

        /**
         * Gets the state set of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleStateSet describing the states
         * of the object
         * @see AccessibleStateSet
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            states.add(AccessibleState.MULTI_LINE);
            return states;
        }
    }

    // --- variables -------------------------------------------------

    private int rows;
    private int columns;
    private int columnWidth;
    private int rowHeight;
    private boolean wrap;
    private boolean word;

}
