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

import sun.swing.SwingUtilities2;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * <code>JTextField</code> is a lightweight component that allows the editing
 * of a single line of text.
 * For information on and examples of using text fields,
 * see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html">How to Use Text Fields</a>
 * in <em>The Java Tutorial.</em>
 *
 * <p>
 * <code>JTextField</code> is intended to be source-compatible
 * with <code>java.awt.TextField</code> where it is reasonable to do so.  This
 * component has capabilities not found in the <code>java.awt.TextField</code>
 * class.  The superclass should be consulted for additional capabilities.
 * <p>
 * <code>JTextField</code> has a method to establish the string used as the
 * command string for the action event that gets fired.  The
 * <code>java.awt.TextField</code> used the text of the field as the command
 * string for the <code>ActionEvent</code>.
 * <code>JTextField</code> will use the command
 * string set with the <code>setActionCommand</code> method if not <code>null</code>,
 * otherwise it will use the text of the field as a compatibility with
 * <code>java.awt.TextField</code>.
 * <p>
 * The method <code>setEchoChar</code> and <code>getEchoChar</code>
 * are not provided directly to avoid a new implementation of a
 * pluggable look-and-feel inadvertently exposing password characters.
 * To provide password-like services a separate class <code>JPasswordField</code>
 * extends <code>JTextField</code> to provide this service with an independently
 * pluggable look-and-feel.
 * <p>
 * The <code>java.awt.TextField</code> could be monitored for changes by adding
 * a <code>TextListener</code> for <code>TextEvent</code>'s.
 * In the <code>JTextComponent</code> based
 * components, changes are broadcasted from the model via a
 * <code>DocumentEvent</code> to <code>DocumentListeners</code>.
 * The <code>DocumentEvent</code> gives
 * the location of the change and the kind of change if desired.
 * The code fragment might look something like:
 * <pre><code>
 * &nbsp;   DocumentListener myListener = ??;
 * &nbsp;   JTextField myArea = ??;
 * &nbsp;   myArea.getDocument().addDocumentListener(myListener);
 * </code></pre>
 * <p>
 * The horizontal alignment of <code>JTextField</code> can be set to be left
 * justified, leading justified, centered, right justified or trailing justified.
 * Right/trailing justification is useful if the required size
 * of the field text is smaller than the size allocated to it.
 * This is determined by the <code>setHorizontalAlignment</code>
 * and <code>getHorizontalAlignment</code> methods.  The default
 * is to be leading justified.
 * <p>
 * How the text field consumes VK_ENTER events depends
 * on whether the text field has any action listeners.
 * If so, then VK_ENTER results in the listeners
 * getting an ActionEvent,
 * and the VK_ENTER event is consumed.
 * This is compatible with how AWT text fields handle VK_ENTER events.
 * If the text field has no action listeners, then as of v 1.3 the VK_ENTER
 * event is not consumed.  Instead, the bindings of ancestor components
 * are processed, which enables the default button feature of
 * JFC/Swing to work.
 * <p>
 * Customized fields can easily be created by extending the model and
 * changing the default model provided.  For example, the following piece
 * of code will create a field that holds only upper case characters.  It
 * will work even if text is pasted into from the clipboard or it is altered via
 * programmatic changes.
 * <pre><code>

&nbsp;public class UpperCaseField extends JTextField {
&nbsp;
&nbsp;    public UpperCaseField(int cols) {
&nbsp;        super(cols);
&nbsp;    }
&nbsp;
&nbsp;    protected Document createDefaultModel() {
&nbsp;        return new UpperCaseDocument();
&nbsp;    }
&nbsp;
&nbsp;    static class UpperCaseDocument extends PlainDocument {
&nbsp;
&nbsp;        public void insertString(int offs, String str, AttributeSet a)
&nbsp;            throws BadLocationException {
&nbsp;
&nbsp;            if (str == null) {
&nbsp;                return;
&nbsp;            }
&nbsp;            char[] upper = str.toCharArray();
&nbsp;            for (int i = 0; i &lt; upper.length; i++) {
&nbsp;                upper[i] = Character.toUpperCase(upper[i]);
&nbsp;            }
&nbsp;            super.insertString(offs, new String(upper), a);
&nbsp;        }
&nbsp;    }
&nbsp;}

 * </code></pre>
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
 * description: A component which allows for the editing of a single line of text.
 *
 * <p>
 *  <code> JTextField </code>是一个轻量级组件,允许编辑单行文本。
 * 有关使用文本字段的信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html">
 * 如何使用文本字段</a>。
 *  <code> JTextField </code>是一个轻量级组件,允许编辑单行文本。 em> Java教程。</em>。
 * 
 * <p>
 *  <code> JTextField </code>旨在与源代码兼容</code> java.awt.TextField </code>,这是合理的。
 * 此组件具有<code> java.awt.TextField </code>类中找不到的功能。应该咨询超类以获得更多功能。
 * <p>
 *  <code> JTextField </code>有一个方法来建立字符串作为被触发的动作事件的命令字符串。
 *  <code> java.awt.TextField </code>将字段的文本用作<code> ActionEvent </code>的命令字符串。
 *  <code> JTextField </code>将使用<code> setActionCommand </code>方法设置的命令字符串,如果不是<code> null </code>,否则它将使用
 * 字段的文本作为与< code> java.awt.TextField </code>。
 *  <code> java.awt.TextField </code>将字段的文本用作<code> ActionEvent </code>的命令字符串。
 * <p>
 * 不直接提供方法<code> setEchoChar </code>和<code> getEchoChar </code>,以避免新的实现可插拔的外观并无意中暴露密码字符。
 * 为了提供类似密码的服务,单独的类<code> JPasswordField </code>扩展了<code> JTextField </code>,为这个服务提供了一个独立的可插拔的外观。
 * <p>
 *  可以通过为<code> TextEvent </code>的<code> TextListener </code>添加<code> java.awt.TextField </code>来监视更改。
 * 在基于<code> JTextComponent </code>的组件中,通过<code> DocumentEvent </code>到<code> DocumentListeners </code>从
 * 模型广播更改。
 *  可以通过为<code> TextEvent </code>的<code> TextListener </code>添加<code> java.awt.TextField </code>来监视更改。
 *  <code> DocumentEvent </code>给出更改的位置和更改的类型(如果需要)。
 * 代码片段可能看起来像：<pre> <code>&nbsp; DocumentListener myListener = ??; &nbsp; JTextField myArea = ??; &nbsp;
 *  myArea.getDocument()。
 *  <code> DocumentEvent </code>给出更改的位置和更改的类型(如果需要)。addDocumentListener(myListener); </code> </pre>。
 * <p>
 *  <code> JTextField </code>的水平对齐可以设置为左对齐,前导对齐,居中,右对齐或尾对齐。如果字段文本的所需大小小于分配给它的大小,则右/尾对齐方式很有用。
 * 这由<code> setHorizo​​ntalAlignment </code>和<code> getHorizo​​ntalAlignment </code>方法确定。默认值为前导对齐。
 * <p>
 * 文本字段如何消耗VK_ENTER事件取决于文本字段是否有任何操作侦听器。如果是这样,则VK_ENTER导致侦听器获取ActionEvent,并且VK_ENTER事件被消耗。
 * 这与AWT文本字段处理VK_ENTER事件的方式兼容。如果文本字段没有动作侦听器,则从v 1.3开始,不会消耗VK_ENTER事件。
 * 相反,处理祖先组件的绑定,这使得JFC / Swing的默认按钮功能能够工作。
 * <p>
 *  可以通过扩展模型并更改提供的默认模型轻松创建自定义字段。例如,以下代码段将创建一个只包含大写字符的字段。即使文本从剪贴板粘贴或通过编程更改更改,它也会工作。 <pre> <code>
 * 
 * &nbsp; public class UpperCaseField extends JTextField {&nbsp; &nbsp; public UpperCaseField(int cols){&nbsp;超级&nbsp; }
 * &nbsp; &nbsp; protected Document createDefaultModel(){&nbsp; return new UpperCaseDocument(); &nbsp; }
 * &nbsp; &nbsp;静态类UpperCaseDocument extends PlainDocument {&nbsp; &nbsp; public void insertString(int offs,String str,AttributeSet a)&nbsp; throws BadLocationException {&nbsp; &nbsp; if(str == null){&nbsp;返回; &nbsp; }
 * &nbsp; char [] upper = str.toCharArray(); &nbsp; for(int i = 0; i <upper.length; i ++){&nbsp; upper [i] = Character.toUpperCase(upper [i]); &nbsp; }&nbsp; super.insertString(offs,new String(upper),a); &nbsp; }&nbsp; }&nbsp;}。
 * 
 *  </code> </pre>
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * @beaninfo属性：isContainer false description：允许编辑单行文本的组件。
 * 
 * 
 * @author  Timothy Prinzing
 * @see #setActionCommand
 * @see JPasswordField
 * @see #addActionListener
 */
public class JTextField extends JTextComponent implements SwingConstants {

    /**
     * Constructs a new <code>TextField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     * <p>
     *  构造一个新的<code> TextField </code>。创建一个默认模型,初始字符串为<code> null </code>,列数设置为0。
     * 
     */
    public JTextField() {
        this(null, null, 0);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text. A default model is created and the number of
     * columns is 0.
     *
     * <p>
     *  构造一个用指定文本初始化的新<Text> TextField </code>。将创建一个默认模型,列数为0。
     * 
     * 
     * @param text the text to be displayed, or <code>null</code>
     */
    public JTextField(String text) {
        this(null, text, 0);
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * <p>
     *  用指定的列数构造一个新的空的<code> TextField </code>。将创建一个默认模型,并将初始字符串设置为<code> null </code>。
     * 
     * 
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public JTextField(int columns) {
        this(null, null, columns);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text and columns.  A default model is created.
     *
     * <p>
     *  构造一个用指定的文本和列初始化的新<Text> TextField </code>。创建默认模型。
     * 
     * 
     * @param text the text to be displayed, or <code>null</code>
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public JTextField(String text, int columns) {
        this(null, text, columns);
    }

    /**
     * Constructs a new <code>JTextField</code> that uses the given text
     * storage model and the given number of columns.
     * This is the constructor through which the other constructors feed.
     * If the document is <code>null</code>, a default model is created.
     *
     * <p>
     *  构造一个新的<code> JTextField </code>,它使用给定的文本存储模型和给定的列数。这是其他构造函数通过的构造函数。
     * 如果文档是<code> null </code>,则会创建一个默认模型。
     * 
     * 
     * @param doc  the text storage to use; if this is <code>null</code>,
     *          a default will be provided by calling the
     *          <code>createDefaultModel</code> method
     * @param text  the initial string to display, or <code>null</code>
     * @param columns  the number of columns to use to calculate
     *   the preferred width &gt;= 0; if <code>columns</code>
     *   is set to zero, the preferred width will be whatever
     *   naturally results from the component implementation
     * @exception IllegalArgumentException if <code>columns</code> &lt; 0
     */
    public JTextField(Document doc, String text, int columns) {
        if (columns < 0) {
            throw new IllegalArgumentException("columns less than zero.");
        }
        visibility = new DefaultBoundedRangeModel();
        visibility.addChangeListener(new ScrollRepainter());
        this.columns = columns;
        if (doc == null) {
            doc = createDefaultModel();
        }
        setDocument(doc);
        if (text != null) {
            setText(text);
        }
    }

    /**
     * Gets the class ID for a UI.
     *
     * <p>
     *  获取UI的类ID。
     * 
     * 
     * @return the string "TextFieldUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Associates the editor with a text document.
     * The currently registered factory is used to build a view for
     * the document, which gets displayed by the editor after revalidation.
     * A PropertyChange event ("document") is propagated to each listener.
     *
     * <p>
     *  将编辑器与文本文档相关联。当前注册的工厂用于构建文档的视图,在重新验证后由编辑器显示。 PropertyChange事件("文档")传播到每个侦听器。
     * 
     * 
     * @param doc  the document to display/edit
     * @see #getDocument
     * @beaninfo
     *  description: the text document model
     *        bound: true
     *       expert: true
     */
    public void setDocument(Document doc) {
        if (doc != null) {
            doc.putProperty("filterNewlines", Boolean.TRUE);
        }
        super.setDocument(doc);
    }

    /**
     * Calls to <code>revalidate</code> that come from within the
     * textfield itself will
     * be handled by validating the textfield, unless the textfield
     * is contained within a <code>JViewport</code>,
     * in which case this returns false.
     *
     * <p>
     *  对来自textfield本身的<code> revalidate </code>的调用将通过验证文本字段来处理,除非textfield包含在<code> JViewport </code>中,在这种情
     * 况下返回false。
     * 
     * 
     * @return if the parent of this textfield is a <code>JViewPort</code>
     *          return false, otherwise return true
     *
     * @see JComponent#revalidate
     * @see JComponent#isValidateRoot
     * @see java.awt.Container#isValidateRoot
     */
    @Override
    public boolean isValidateRoot() {
        return !(SwingUtilities.getUnwrappedParent(this) instanceof JViewport);
    }


    /**
     * Returns the horizontal alignment of the text.
     * Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     *
     * <p>
     *  返回文本的水平对齐方式。有效键为：
     * <ul>
     * <li> <code> JTextField.LEFT </code> <li> <code> JTextField.CENTER </code> <li> <code> JTextField.RIGH
     * T </code> <li> <code> JTextField.LEADING <代码> <li> <code> JTextField.TRAILING </code>。
     * </ul>
     * 
     * 
     * @return the horizontal alignment
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the horizontal alignment of the text.
     * Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     * <code>invalidate</code> and <code>repaint</code> are called when the
     * alignment is set,
     * and a <code>PropertyChange</code> event ("horizontalAlignment") is fired.
     *
     * <p>
     *  设置文本的水平对齐方式。有效键为：
     * <ul>
     *  <li> <code> JTextField.LEFT </code> <li> <code> JTextField.CENTER </code> <li> <code> JTextField.RIG
     * HT </code> <li> <code> JTextField.LEADING <代码> <li> <code> JTextField.TRAILING </code>。
     * </ul>
     *  当设置对齐并调用<code> PropertyChange </code>事件("horizo​​ntalAlignment")时,会调用<code> invalidate </code>和<code>
     *  repaint </code>。
     * 
     * 
     * @param alignment the alignment
     * @exception IllegalArgumentException if <code>alignment</code>
     *  is not a valid key
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Set the field alignment to LEFT, CENTER, RIGHT,
     *              LEADING (the default) or TRAILING
     *        enum: LEFT JTextField.LEFT CENTER JTextField.CENTER RIGHT JTextField.RIGHT
     *              LEADING JTextField.LEADING TRAILING JTextField.TRAILING
     */
     public void setHorizontalAlignment(int alignment) {
        if (alignment == horizontalAlignment) return;
        int oldValue = horizontalAlignment;
        if ((alignment == LEFT) || (alignment == CENTER) ||
            (alignment == RIGHT)|| (alignment == LEADING) ||
            (alignment == TRAILING)) {
            horizontalAlignment = alignment;
        } else {
            throw new IllegalArgumentException("horizontalAlignment");
        }
        firePropertyChange("horizontalAlignment", oldValue, horizontalAlignment);
        invalidate();
        repaint();
    }

    /**
     * Creates the default implementation of the model
     * to be used at construction if one isn't explicitly
     * given.  An instance of <code>PlainDocument</code> is returned.
     *
     * <p>
     *  如果没有明确给出,则创建要在构建时使用的模型的默认实现。返回<code> PlainDocument </code>的实例。
     * 
     * 
     * @return the default model implementation
     */
    protected Document createDefaultModel() {
        return new PlainDocument();
    }

    /**
     * Returns the number of columns in this <code>TextField</code>.
     *
     * <p>
     *  返回此<code> TextField </code>中的列数。
     * 
     * 
     * @return the number of columns &gt;= 0
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns in this <code>TextField</code>,
     * and then invalidate the layout.
     *
     * <p>
     *  设置此<code> TextField </code>中的列数,然后使布局无效。
     * 
     * 
     * @param columns the number of columns &gt;= 0
     * @exception IllegalArgumentException if <code>columns</code>
     *          is less than 0
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
     * Returns the column width.
     * The meaning of what a column is can be considered a fairly weak
     * notion for some fonts.  This method is used to define the width
     * of a column.  By default this is defined to be the width of the
     * character <em>m</em> for the font used.  This method can be
     * redefined to be some alternative amount
     *
     * <p>
     *  返回列宽。列的含义对于某些字体可以被认为是一个相当弱的概念。此方法用于定义列的宽度。默认情况下,这被定义为所使用的字体的字符宽度<em> m </em>。这种方法可以重新定义为一些替代量
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

    /**
     * Returns the preferred size <code>Dimensions</code> needed for this
     * <code>TextField</code>.  If a non-zero number of columns has been
     * set, the width is set to the columns multiplied by
     * the column width.
     *
     * <p>
     *  返回<code> TextField </code>所需的<code> Dimensions </code>。如果已设置非零数目的列,则将宽度设置为列乘以列宽。
     * 
     * 
     * @return the dimension of this textfield
     */
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if (columns != 0) {
            Insets insets = getInsets();
            size.width = columns * getColumnWidth() +
                insets.left + insets.right;
        }
        return size;
    }

    /**
     * Sets the current font.  This removes cached row height and column
     * width so the new font will be reflected.
     * <code>revalidate</code> is called after setting the font.
     *
     * <p>
     * 设置当前字体。这将删除缓存的行高度和列宽度,以便反映新的字体。 <code> revalidate </code>在设置字体后调用。
     * 
     * 
     * @param f the new font
     */
    public void setFont(Font f) {
        super.setFont(f);
        columnWidth = 0;
    }

    /**
     * Adds the specified action listener to receive
     * action events from this textfield.
     *
     * <p>
     *  添加指定的操作侦听器以从此文本字段接收操作事件。
     * 
     * 
     * @param l the action listener to be added
     */
    public synchronized void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    /**
     * Removes the specified action listener so that it no longer
     * receives action events from this textfield.
     *
     * <p>
     *  删除指定的操作侦听器,以使其不再从此文本字段接收操作事件。
     * 
     * 
     * @param l the action listener to be removed
     */
    public synchronized void removeActionListener(ActionListener l) {
        if ((l != null) && (getAction() == l)) {
            setAction(null);
        } else {
            listenerList.remove(ActionListener.class, l);
        }
    }

    /**
     * Returns an array of all the <code>ActionListener</code>s added
     * to this JTextField with addActionListener().
     *
     * <p>
     *  返回通过addActionListener()添加到此JTextField的所有<code> ActionListener </code>数组。
     * 
     * 
     * @return all of the <code>ActionListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public synchronized ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created.
     * The listener list is processed in last to
     * first order.
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。延迟创建事件实例。侦听器列表按照最后到第一顺序处理。
     * 
     * 
     * @see EventListenerList
     */
    protected void fireActionPerformed() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        int modifiers = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if (currentEvent instanceof InputEvent) {
            modifiers = ((InputEvent)currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
            modifiers = ((ActionEvent)currentEvent).getModifiers();
        }
        ActionEvent e =
            new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                            (command != null) ? command : getText(),
                            EventQueue.getMostRecentEventTime(), modifiers);

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /**
     * Sets the command string used for action events.
     *
     * <p>
     *  设置用于操作事件的命令字符串。
     * 
     * 
     * @param command the command string
     */
    public void setActionCommand(String command) {
        this.command = command;
    }

    private Action action;
    private PropertyChangeListener actionPropertyChangeListener;

    /**
     * Sets the <code>Action</code> for the <code>ActionEvent</code> source.
     * The new <code>Action</code> replaces
     * any previously set <code>Action</code> but does not affect
     * <code>ActionListeners</code> independently
     * added with <code>addActionListener</code>.
     * If the <code>Action</code> is already a registered
     * <code>ActionListener</code>
     * for the <code>ActionEvent</code> source, it is not re-registered.
     * <p>
     * Setting the <code>Action</code> results in immediately changing
     * all the properties described in <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a>.
     * Subsequently, the textfield's properties are automatically updated
     * as the <code>Action</code>'s properties change.
     * <p>
     * This method uses three other methods to set
     * and help track the <code>Action</code>'s property values.
     * It uses the <code>configurePropertiesFromAction</code> method
     * to immediately change the textfield's properties.
     * To track changes in the <code>Action</code>'s property values,
     * this method registers the <code>PropertyChangeListener</code>
     * returned by <code>createActionPropertyChangeListener</code>. The
     * default {@code PropertyChangeListener} invokes the
     * {@code actionPropertyChanged} method when a property in the
     * {@code Action} changes.
     *
     * <p>
     *  为<code> ActionEvent </code>源设置<code> Action </code>。
     * 新<code> Action </code>替换任何先前设置的<code> Action </code>,但不影响<code> addActionListener </code>独立添加的<code> 
     * ActionListeners </code>。
     *  为<code> ActionEvent </code>源设置<code> Action </code>。
     * 如果<code> Action </code>已经是<code> ActionEvent </code>源的注册<code> ActionListener </code>,则不会重新注册。
     * <p>
     *  设置<code> Action </code>会立即更改<a href="Action.html#buttonActions"> Swing组件支持<code> Action </code> </a>
     * 中描述的所有属性。
     * 随后,随着<code> Action </code>的属性更改,文本字段的属性会自动更新。
     * <p>
     * 此方法使用其他三种方法来设置和帮助跟踪<code> Action </code>的属性值。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改文本字段的属性。
     * 要跟踪<code> Action </code>的属性值的更改,此方法注册<code> createActionPropertyChangeListener </code>返回的<code> Prope
     * rtyChangeListener </code>。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改文本字段的属性。
     *  {@code Action}中的属性更改时,默认的{@code PropertyChangeListener}调用{@code actionPropertyChanged}方法。
     * 
     * 
     * @param a the <code>Action</code> for the <code>JTextField</code>,
     *          or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #getAction
     * @see #configurePropertiesFromAction
     * @see #createActionPropertyChangeListener
     * @see #actionPropertyChanged
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: the Action instance connected with this ActionEvent source
     */
    public void setAction(Action a) {
        Action oldValue = getAction();
        if (action==null || !action.equals(a)) {
            action = a;
            if (oldValue!=null) {
                removeActionListener(oldValue);
                oldValue.removePropertyChangeListener(actionPropertyChangeListener);
                actionPropertyChangeListener = null;
            }
            configurePropertiesFromAction(action);
            if (action!=null) {
                // Don't add if it is already a listener
                if (!isListener(ActionListener.class, action)) {
                    addActionListener(action);
                }
                // Reverse linkage:
                actionPropertyChangeListener = createActionPropertyChangeListener(action);
                action.addPropertyChangeListener(actionPropertyChangeListener);
            }
            firePropertyChange("action", oldValue, action);
        }
    }

    private boolean isListener(Class c, ActionListener a) {
        boolean isListener = false;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==c && listeners[i+1]==a) {
                    isListener=true;
            }
        }
        return isListener;
    }

    /**
     * Returns the currently set <code>Action</code> for this
     * <code>ActionEvent</code> source, or <code>null</code>
     * if no <code>Action</code> is set.
     *
     * <p>
     *  如果未设置<code> Action </code>,则为此<code> ActionEvent </code>源或<code> null </code>返回当前设置的<code> Action </code>
     * 。
     * 
     * 
     * @return the <code>Action</code> for this <code>ActionEvent</code> source,
     *          or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the properties on this textfield to match those in the specified
     * <code>Action</code>.  Refer to <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a> for more
     * details as to which properties this sets.
     *
     * <p>
     *  将此文本字段上的属性设置为与指定的<code> Action </code>中的属性相匹配。
     * 有关此设置的属性的更多详细信息,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code> Action </code> </a>。
     * 
     * 
     * @param a the <code>Action</code> from which to get the properties,
     *          or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    protected void configurePropertiesFromAction(Action a) {
        AbstractAction.setEnabledFromAction(this, a);
        AbstractAction.setToolTipTextFromAction(this, a);
        setActionCommandFromAction(a);
    }

    /**
     * Updates the textfield's state in response to property changes in
     * associated action. This method is invoked from the
     * {@code PropertyChangeListener} returned from
     * {@code createActionPropertyChangeListener}. Subclasses do not normally
     * need to invoke this. Subclasses that support additional {@code Action}
     * properties should override this and
     * {@code configurePropertiesFromAction}.
     * <p>
     * Refer to the table at <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a> for a list of
     * the properties this method sets.
     *
     * <p>
     *  更新文本字段的状态以响应关联操作中的属性更改。
     * 此方法从{@code createActionPropertyChangeListener}返回的{@code PropertyChangeListener}中调用。子类通常不需要调用这个。
     * 支持其他{@code Action}属性的子类应该覆盖此类和{@code configurePropertiesFromAction}。
     * <p>
     *  请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>中的表格,了解此方法设置的属性列表。
     * 
     * 
     * @param action the <code>Action</code> associated with this textfield
     * @param propertyName the name of the property that changed
     * @since 1.6
     * @see Action
     * @see #configurePropertiesFromAction
     */
    protected void actionPropertyChanged(Action action, String propertyName) {
        if (propertyName == Action.ACTION_COMMAND_KEY) {
            setActionCommandFromAction(action);
        } else if (propertyName == "enabled") {
            AbstractAction.setEnabledFromAction(this, action);
        } else if (propertyName == Action.SHORT_DESCRIPTION) {
            AbstractAction.setToolTipTextFromAction(this, action);
        }
    }

    private void setActionCommandFromAction(Action action) {
        setActionCommand((action == null) ? null :
                         (String)action.getValue(Action.ACTION_COMMAND_KEY));
    }

    /**
     * Creates and returns a <code>PropertyChangeListener</code> that is
     * responsible for listening for changes from the specified
     * <code>Action</code> and updating the appropriate properties.
     * <p>
     * <b>Warning:</b> If you subclass this do not create an anonymous
     * inner class.  If you do the lifetime of the textfield will be tied to
     * that of the <code>Action</code>.
     *
     * <p>
     * 创建并返回一个<code> PropertyChangeListener </code>,它负责侦听来自指定<code> Action </code>的更改并更新相应的属性。
     * <p>
     *  <b>警告：</b>如果你子类化这不创建一个匿名内部类。如果你做,文本字段的生命周期将绑定到<code> Action </code>的。
     * 
     * 
     * @param a the textfield's action
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
        return new TextFieldActionPropertyChangeListener(this, a);
    }

    private static class TextFieldActionPropertyChangeListener extends
                         ActionPropertyChangeListener<JTextField> {
        TextFieldActionPropertyChangeListener(JTextField tf, Action a) {
            super(tf, a);
        }

        protected void actionPropertyChanged(JTextField textField,
                                             Action action,
                                             PropertyChangeEvent e) {
            if (AbstractAction.shouldReconfigure(e)) {
                textField.configurePropertiesFromAction(action);
            } else {
                textField.actionPropertyChanged(action, e.getPropertyName());
            }
        }
    }

    /**
     * Fetches the command list for the editor.  This is
     * the list of commands supported by the plugged-in UI
     * augmented by the collection of commands that the
     * editor itself supports.  These are useful for binding
     * to events, such as in a keymap.
     *
     * <p>
     *  获取编辑器的命令列表。这是插件UI支持的命令列表,由编辑器本身支持的命令集合增强。这些对于绑定到事件是有用的,例如在键映射中。
     * 
     * 
     * @return the command list
     */
    public Action[] getActions() {
        return TextAction.augmentList(super.getActions(), defaultActions);
    }

    /**
     * Processes action events occurring on this textfield by
     * dispatching them to any registered <code>ActionListener</code> objects.
     * This is normally called by the controller registered with
     * textfield.
     * <p>
     *  通过将它们分发到任何注册的<code> ActionListener </code>对象,来处理在此文本字段上发生的操作事件。这通常由在文本字段中注册的控制器调用。
     * 
     */
    public void postActionEvent() {
        fireActionPerformed();
    }

    // --- Scrolling support -----------------------------------

    /**
     * Gets the visibility of the text field.  This can
     * be adjusted to change the location of the visible
     * area if the size of the field is greater than
     * the area that was allocated to the field.
     *
     * <p>
     * The fields look-and-feel implementation manages
     * the values of the minimum, maximum, and extent
     * properties on the <code>BoundedRangeModel</code>.
     *
     * <p>
     *  获取文本字段的可见性。如果字段的大小大于分配给字段的区域,则可以调整此项以更改可见区域的位置。
     * 
     * <p>
     *  字段外观实现管理<code> BoundedRangeModel </code>上的minimum,maximum和extent属性的值。
     * 
     * 
     * @return the visibility
     * @see BoundedRangeModel
     */
    public BoundedRangeModel getHorizontalVisibility() {
        return visibility;
    }

    /**
     * Gets the scroll offset, in pixels.
     *
     * <p>
     *  获取滚动偏移量,以像素为单位。
     * 
     * 
     * @return the offset &gt;= 0
     */
    public int getScrollOffset() {
        return visibility.getValue();
    }

    /**
     * Sets the scroll offset, in pixels.
     *
     * <p>
     *  设置滚动偏移量,以像素为单位。
     * 
     * 
     * @param scrollOffset the offset &gt;= 0
     */
    public void setScrollOffset(int scrollOffset) {
        visibility.setValue(scrollOffset);
    }

    /**
     * Scrolls the field left or right.
     *
     * <p>
     *  向左或向右滚动字段。
     * 
     * 
     * @param r the region to scroll
     */
    public void scrollRectToVisible(Rectangle r) {
        // convert to coordinate system of the bounded range
        Insets i = getInsets();
        int x0 = r.x + visibility.getValue() - i.left;
        int x1 = x0 + r.width;
        if (x0 < visibility.getValue()) {
            // Scroll to the left
            visibility.setValue(x0);
        } else if(x1 > visibility.getValue() + visibility.getExtent()) {
            // Scroll to the right
            visibility.setValue(x1 - visibility.getExtent());
        }
    }

    /**
     * Returns true if the receiver has an <code>ActionListener</code>
     * installed.
     * <p>
     *  如果接收器安装了<code> ActionListener </code>,则返回true。
     * 
     */
    boolean hasActionListener() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                return true;
            }
        }
        return false;
    }

    // --- variables -------------------------------------------

    /**
     * Name of the action to send notification that the
     * contents of the field have been accepted.  Typically
     * this is bound to a carriage-return.
     * <p>
     *  发送通知字段的内容已被接受的操作的名称。通常这被绑定到回车。
     * 
     */
    public static final String notifyAction = "notify-field-accept";

    private BoundedRangeModel visibility;
    private int horizontalAlignment = LEADING;
    private int columns;
    private int columnWidth;
    private String command;

    private static final Action[] defaultActions = {
        new NotifyAction()
    };

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "TextFieldUI";

    // --- Action implementations -----------------------------------

    // Note that JFormattedTextField.CommitAction extends this
    static class NotifyAction extends TextAction {

        NotifyAction() {
            super(notifyAction);
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getFocusedComponent();
            if (target instanceof JTextField) {
                JTextField field = (JTextField) target;
                field.postActionEvent();
            }
        }

        public boolean isEnabled() {
            JTextComponent target = getFocusedComponent();
            if (target instanceof JTextField) {
                return ((JTextField)target).hasActionListener();
            }
            return false;
        }
    }

    class ScrollRepainter implements ChangeListener, Serializable {

        public void stateChanged(ChangeEvent e) {
            repaint();
        }

    }


    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     * 有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
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


    /**
     * Returns a string representation of this <code>JTextField</code>.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JTextField </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JTextField</code>
     */
    protected String paramString() {
        String horizontalAlignmentString;
        if (horizontalAlignment == LEFT) {
            horizontalAlignmentString = "LEFT";
        } else if (horizontalAlignment == CENTER) {
            horizontalAlignmentString = "CENTER";
        } else if (horizontalAlignment == RIGHT) {
            horizontalAlignmentString = "RIGHT";
        } else if (horizontalAlignment == LEADING) {
            horizontalAlignmentString = "LEADING";
        } else if (horizontalAlignment == TRAILING) {
            horizontalAlignmentString = "TRAILING";
        } else horizontalAlignmentString = "";
        String commandString = (command != null ?
                                command : "");

        return super.paramString() +
        ",columns=" + columns +
        ",columnWidth=" + columnWidth +
        ",command=" + commandString +
        ",horizontalAlignment=" + horizontalAlignmentString;
    }


/////////////////
// Accessibility support
////////////////


    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>JTextField</code>. For <code>JTextFields</code>,
     * the <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJTextField</code>.
     * A new <code>AccessibleJTextField</code> instance is created
     * if necessary.
     *
     * <p>
     *  获取与此<code> JTextField </code>关联的<code> AccessibleContext </code>。
     * 对于<code> JTextFields </code>,<code> AccessibleContext </code>采用<code> AccessibleJTextField </code>的形式
     * 。
     *  获取与此<code> JTextField </code>关联的<code> AccessibleContext </code>。
     * 如果需要,将创建一个新的<code> AccessibleJTextField </code>实例。
     * 
     * 
     * @return an <code>AccessibleJTextField</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JTextField</code>
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJTextField();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JTextField</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to text field user-interface
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
     *  此类实现了<code> JTextField </code>类的辅助功能支持。它提供了适用于文本字段用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJTextField extends AccessibleJTextComponent {

        /**
         * Gets the state set of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleStateSet describing the states
         * of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            states.add(AccessibleState.SINGLE_LINE);
            return states;
        }
    }
}
