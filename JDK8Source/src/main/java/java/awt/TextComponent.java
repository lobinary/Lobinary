/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import java.awt.peer.TextComponentPeer;
import java.awt.event.*;
import java.util.EventListener;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import sun.awt.InputMethodSupport;
import java.text.BreakIterator;
import javax.swing.text.AttributeSet;
import javax.accessibility.*;
import java.awt.im.InputMethodRequests;
import sun.security.util.SecurityConstants;

/**
 * The <code>TextComponent</code> class is the superclass of
 * any component that allows the editing of some text.
 * <p>
 * A text component embodies a string of text.  The
 * <code>TextComponent</code> class defines a set of methods
 * that determine whether or not this text is editable. If the
 * component is editable, it defines another set of methods
 * that supports a text insertion caret.
 * <p>
 * In addition, the class defines methods that are used
 * to maintain a current <em>selection</em> from the text.
 * The text selection, a substring of the component's text,
 * is the target of editing operations. It is also referred
 * to as the <em>selected text</em>.
 *
 * <p>
 *  <code> TextComponent </code>类是允许编辑某些文本的任何组件的超类。
 * <p>
 *  文本组件包含一串文本。 <code> TextComponent </code>类定义了一组方法,用于确定此文本是否可编辑。如果组件是可编辑的,它定义另一组支持文本插入插入符号的方法。
 * <p>
 *  此外,该类定义了用于从文本维护当前<em>选择</em>的方法。文本选择是组件文本的子字符串,是编辑操作的目标。它也称为<em>选定的文本</em>。
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public class TextComponent extends Component implements Accessible {

    /**
     * The value of the text.
     * A <code>null</code> value is the same as "".
     *
     * <p>
     *  文本的值。 <code> null </code>值与""相同。
     * 
     * 
     * @serial
     * @see #setText(String)
     * @see #getText()
     */
    String text;

    /**
     * A boolean indicating whether or not this
     * <code>TextComponent</code> is editable.
     * It will be <code>true</code> if the text component
     * is editable and <code>false</code> if not.
     *
     * <p>
     *  一个布尔值,指示此<code> TextComponent </code>是否可编辑。如果文本组件可编辑,则为<code> true </code>,否则为<code> true </code>。
     * 
     * 
     * @serial
     * @see #isEditable()
     */
    boolean editable = true;

    /**
     * The selection refers to the selected text, and the
     * <code>selectionStart</code> is the start position
     * of the selected text.
     *
     * <p>
     *  选择引用所选文本,<code> selectionStart </code>是所选文本的开始位置。
     * 
     * 
     * @serial
     * @see #getSelectionStart()
     * @see #setSelectionStart(int)
     */
    int selectionStart;

    /**
     * The selection refers to the selected text, and the
     * <code>selectionEnd</code>
     * is the end position of the selected text.
     *
     * <p>
     *  选择是指所选文本,<code> selectionEnd </code>是所选文本的结束位置。
     * 
     * 
     * @serial
     * @see #getSelectionEnd()
     * @see #setSelectionEnd(int)
     */
    int selectionEnd;

    // A flag used to tell whether the background has been set by
    // developer code (as opposed to AWT code).  Used to determine
    // the background color of non-editable TextComponents.
    boolean backgroundSetByClientCode = false;

    transient protected TextListener textListener;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -2214773872412987419L;

    /**
     * Constructs a new text component initialized with the
     * specified text. Sets the value of the cursor to
     * <code>Cursor.TEXT_CURSOR</code>.
     * <p>
     *  构造使用指定文本初始化的新文本组件。将光标的值设置为<code> Cursor.TEXT_CURSOR </code>。
     * 
     * 
     * @param      text       the text to be displayed; if
     *             <code>text</code> is <code>null</code>, the empty
     *             string <code>""</code> will be displayed
     * @exception  HeadlessException if
     *             <code>GraphicsEnvironment.isHeadless</code>
     *             returns true
     * @see        java.awt.GraphicsEnvironment#isHeadless
     * @see        java.awt.Cursor
     */
    TextComponent(String text) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        this.text = (text != null) ? text : "";
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    }

    private void enableInputMethodsIfNecessary() {
        if (checkForEnableIM) {
            checkForEnableIM = false;
            try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                boolean shouldEnable = false;
                if (toolkit instanceof InputMethodSupport) {
                    shouldEnable = ((InputMethodSupport)toolkit)
                      .enableInputMethodsForTextComponent();
                }
                enableInputMethods(shouldEnable);
            } catch (Exception e) {
                // if something bad happens, just don't enable input methods
            }
        }
    }

    /**
     * Enables or disables input method support for this text component. If input
     * method support is enabled and the text component also processes key events,
     * incoming events are offered to the current input method and will only be
     * processed by the component or dispatched to its listeners if the input method
     * does not consume them. Whether and how input method support for this text
     * component is enabled or disabled by default is implementation dependent.
     *
     * <p>
     * 启用或禁用此文本组件的输入法支持。如果启用了输入法支持并且文本组件还处理关键事件,则传入事件将提供给当前输入法,并且只有在组件处理或者如果输入法不消耗它们时,才会被分派给它的监听器。
     * 此文本组件的输入方法支持是否以及如何启用或禁用是默认实现相关的。
     * 
     * 
     * @param enable true to enable, false to disable
     * @see #processKeyEvent
     * @since 1.2
     */
    public void enableInputMethods(boolean enable) {
        checkForEnableIM = false;
        super.enableInputMethods(enable);
    }

    boolean areInputMethodsEnabled() {
        // moved from the constructor above to here and addNotify below,
        // this call will initialize the toolkit if not already initialized.
        if (checkForEnableIM) {
            enableInputMethodsIfNecessary();
        }

        // TextComponent handles key events without touching the eventMask or
        // having a key listener, so just check whether the flag is set
        return (eventMask & AWTEvent.INPUT_METHODS_ENABLED_MASK) != 0;
    }

    public InputMethodRequests getInputMethodRequests() {
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) return peer.getInputMethodRequests();
        else return null;
    }



    /**
     * Makes this Component displayable by connecting it to a
     * native screen resource.
     * This method is called internally by the toolkit and should
     * not be called directly by programs.
     * <p>
     *  将此组件连接到本机屏幕资源,使其可显示。此方法由工具包在内部调用,不应由程序直接调用。
     * 
     * 
     * @see       java.awt.TextComponent#removeNotify
     */
    public void addNotify() {
        super.addNotify();
        enableInputMethodsIfNecessary();
    }

    /**
     * Removes the <code>TextComponent</code>'s peer.
     * The peer allows us to modify the appearance of the
     * <code>TextComponent</code> without changing its
     * functionality.
     * <p>
     *  删除<code> TextComponent </code>的对等体。对等体允许我们在不改变其功能的情况下修改<code> TextComponent </code>的外观。
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            TextComponentPeer peer = (TextComponentPeer)this.peer;
            if (peer != null) {
                text = peer.getText();
                selectionStart = peer.getSelectionStart();
                selectionEnd = peer.getSelectionEnd();
            }
            super.removeNotify();
        }
    }

    /**
     * Sets the text that is presented by this
     * text component to be the specified text.
     * <p>
     *  将此文本组件显示的文本设置为指定的文本。
     * 
     * 
     * @param       t   the new text;
     *                  if this parameter is <code>null</code> then
     *                  the text is set to the empty string ""
     * @see         java.awt.TextComponent#getText
     */
    public synchronized void setText(String t) {
        boolean skipTextEvent = (text == null || text.isEmpty())
                && (t == null || t.isEmpty());
        text = (t != null) ? t : "";
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        // Please note that we do not want to post an event
        // if TextArea.setText() or TextField.setText() replaces an empty text
        // by an empty text, that is, if component's text remains unchanged.
        if (peer != null && !skipTextEvent) {
            peer.setText(text);
        }
    }

    /**
     * Returns the text that is presented by this text component.
     * By default, this is an empty string.
     *
     * <p>
     *  返回此文本组件显示的文本。默认情况下,这是一个空字符串。
     * 
     * 
     * @return the value of this <code>TextComponent</code>
     * @see     java.awt.TextComponent#setText
     */
    public synchronized String getText() {
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            text = peer.getText();
        }
        return text;
    }

    /**
     * Returns the selected text from the text that is
     * presented by this text component.
     * <p>
     *  从此文本组件显示的文本返回所选文本。
     * 
     * 
     * @return      the selected text of this text component
     * @see         java.awt.TextComponent#select
     */
    public synchronized String getSelectedText() {
        return getText().substring(getSelectionStart(), getSelectionEnd());
    }

    /**
     * Indicates whether or not this text component is editable.
     * <p>
     *  指示此文本组件是否可编辑。
     * 
     * 
     * @return     <code>true</code> if this text component is
     *                  editable; <code>false</code> otherwise.
     * @see        java.awt.TextComponent#setEditable
     * @since      JDK1.0
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets the flag that determines whether or not this
     * text component is editable.
     * <p>
     * If the flag is set to <code>true</code>, this text component
     * becomes user editable. If the flag is set to <code>false</code>,
     * the user cannot change the text of this text component.
     * By default, non-editable text components have a background color
     * of SystemColor.control.  This default can be overridden by
     * calling setBackground.
     *
     * <p>
     *  设置确定此文本组件是否可编辑的标志。
     * <p>
     * 如果标志设置为<code> true </code>,则此文本组件将变为用户可编辑的。如果标志设置为<code> false </code>,则用户无法更改此文本组件的文本。
     * 默认情况下,不可编辑的文本组件具有SystemColor.control的背景颜色。可以通过调用setBackground来覆盖此默认值。
     * 
     * 
     * @param     b   a flag indicating whether this text component
     *                      is user editable.
     * @see       java.awt.TextComponent#isEditable
     * @since     JDK1.0
     */
    public synchronized void setEditable(boolean b) {
        if (editable == b) {
            return;
        }

        editable = b;
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            peer.setEditable(b);
        }
    }

    /**
     * Gets the background color of this text component.
     *
     * By default, non-editable text components have a background color
     * of SystemColor.control.  This default can be overridden by
     * calling setBackground.
     *
     * <p>
     *  获取此文本组件的背景颜色。
     * 
     *  默认情况下,不可编辑的文本组件具有SystemColor.control的背景颜色。可以通过调用setBackground来覆盖此默认值。
     * 
     * 
     * @return This text component's background color.
     *         If this text component does not have a background color,
     *         the background color of its parent is returned.
     * @see #setBackground(Color)
     * @since JDK1.0
     */
    public Color getBackground() {
        if (!editable && !backgroundSetByClientCode) {
            return SystemColor.control;
        }

        return super.getBackground();
    }

    /**
     * Sets the background color of this text component.
     *
     * <p>
     *  设置此文本组件的背景颜色。
     * 
     * 
     * @param c The color to become this text component's color.
     *        If this parameter is null then this text component
     *        will inherit the background color of its parent.
     * @see #getBackground()
     * @since JDK1.0
     */
    public void setBackground(Color c) {
        backgroundSetByClientCode = true;
        super.setBackground(c);
    }

    /**
     * Gets the start position of the selected text in
     * this text component.
     * <p>
     *  获取此文本组件中所选文本的开始位置。
     * 
     * 
     * @return      the start position of the selected text
     * @see         java.awt.TextComponent#setSelectionStart
     * @see         java.awt.TextComponent#getSelectionEnd
     */
    public synchronized int getSelectionStart() {
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            selectionStart = peer.getSelectionStart();
        }
        return selectionStart;
    }

    /**
     * Sets the selection start for this text component to
     * the specified position. The new start point is constrained
     * to be at or before the current selection end. It also
     * cannot be set to less than zero, the beginning of the
     * component's text.
     * If the caller supplies a value for <code>selectionStart</code>
     * that is out of bounds, the method enforces these constraints
     * silently, and without failure.
     * <p>
     *  将此文本组件的选择开始设置为指定位置。新的起始点被限制在当前选择结束处或之前。它也不能设置为小于零,组件的文本的开始。
     * 如果调用者为<code> selectionStart </code>提供了超出范围的值,则该方法将静默地执行这些约束,而不会失败。
     * 
     * 
     * @param       selectionStart   the start position of the
     *                        selected text
     * @see         java.awt.TextComponent#getSelectionStart
     * @see         java.awt.TextComponent#setSelectionEnd
     * @since       JDK1.1
     */
    public synchronized void setSelectionStart(int selectionStart) {
        /* Route through select method to enforce consistent policy
         * between selectionStart and selectionEnd.
         * <p>
         *  在selectionStart和selectionEnd之间。
         * 
         */
        select(selectionStart, getSelectionEnd());
    }

    /**
     * Gets the end position of the selected text in
     * this text component.
     * <p>
     *  获取此文本组件中所选文本的结束位置。
     * 
     * 
     * @return      the end position of the selected text
     * @see         java.awt.TextComponent#setSelectionEnd
     * @see         java.awt.TextComponent#getSelectionStart
     */
    public synchronized int getSelectionEnd() {
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            selectionEnd = peer.getSelectionEnd();
        }
        return selectionEnd;
    }

    /**
     * Sets the selection end for this text component to
     * the specified position. The new end point is constrained
     * to be at or after the current selection start. It also
     * cannot be set beyond the end of the component's text.
     * If the caller supplies a value for <code>selectionEnd</code>
     * that is out of bounds, the method enforces these constraints
     * silently, and without failure.
     * <p>
     * 将此文本组件的选择结束设置为指定位置。新端点被限制在当前选择开始处或之后。它也不能设置超过组件的文本的结尾。
     * 如果调用者为<code> selectionEnd </code>提供了超出范围的值,那么该方法将静默地执行这些约束,而不会失败。
     * 
     * 
     * @param       selectionEnd   the end position of the
     *                        selected text
     * @see         java.awt.TextComponent#getSelectionEnd
     * @see         java.awt.TextComponent#setSelectionStart
     * @since       JDK1.1
     */
    public synchronized void setSelectionEnd(int selectionEnd) {
        /* Route through select method to enforce consistent policy
         * between selectionStart and selectionEnd.
         * <p>
         *  在selectionStart和selectionEnd之间。
         * 
         */
        select(getSelectionStart(), selectionEnd);
    }

    /**
     * Selects the text between the specified start and end positions.
     * <p>
     * This method sets the start and end positions of the
     * selected text, enforcing the restriction that the start position
     * must be greater than or equal to zero.  The end position must be
     * greater than or equal to the start position, and less than or
     * equal to the length of the text component's text.  The
     * character positions are indexed starting with zero.
     * The length of the selection is
     * <code>endPosition</code> - <code>startPosition</code>, so the
     * character at <code>endPosition</code> is not selected.
     * If the start and end positions of the selected text are equal,
     * all text is deselected.
     * <p>
     * If the caller supplies values that are inconsistent or out of
     * bounds, the method enforces these constraints silently, and
     * without failure. Specifically, if the start position or end
     * position is greater than the length of the text, it is reset to
     * equal the text length. If the start position is less than zero,
     * it is reset to zero, and if the end position is less than the
     * start position, it is reset to the start position.
     *
     * <p>
     *  选择指定的开始和结束位置之间的文本。
     * <p>
     *  此方法设置所选文本的开始和结束位置,强制开始位置必须大于或等于零的限制。结束位置必须大于或等于开始位置,且小于或等于文本组件文本的长度。字符位置从零开始索引。
     * 选择的长度为<code> endPosition </code>  -  <code> startPosition </code>,因此未选择<code> endPosition </code>处的字符
     * 。
     *  此方法设置所选文本的开始和结束位置,强制开始位置必须大于或等于零的限制。结束位置必须大于或等于开始位置,且小于或等于文本组件文本的长度。字符位置从零开始索引。
     * 如果所选文本的开始和结束位置相等,则取消选择所有文本。
     * <p>
     *  如果调用者提供不一致或超出范围的值,那么该方法将静默地执行这些约束,而不会失败。具体来说,如果开始位置或结束位置大于文本的长度,则将其重置为等于文本长度。
     * 如果起始位置小于零,则将其重置为零,如果结束位置小于起始位置,则将其重置为起始位置。
     * 
     * 
     * @param        selectionStart the zero-based index of the first
                       character (<code>char</code> value) to be selected
     * @param        selectionEnd the zero-based end position of the
                       text to be selected; the character (<code>char</code> value) at
                       <code>selectionEnd</code> is not selected
     * @see          java.awt.TextComponent#setSelectionStart
     * @see          java.awt.TextComponent#setSelectionEnd
     * @see          java.awt.TextComponent#selectAll
     */
    public synchronized void select(int selectionStart, int selectionEnd) {
        String text = getText();
        if (selectionStart < 0) {
            selectionStart = 0;
        }
        if (selectionStart > text.length()) {
            selectionStart = text.length();
        }
        if (selectionEnd > text.length()) {
            selectionEnd = text.length();
        }
        if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
        }

        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;

        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            peer.select(selectionStart, selectionEnd);
        }
    }

    /**
     * Selects all the text in this text component.
     * <p>
     * 选择此文本组件中的所有文本。
     * 
     * 
     * @see        java.awt.TextComponent#select
     */
    public synchronized void selectAll() {
        this.selectionStart = 0;
        this.selectionEnd = getText().length();

        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            peer.select(selectionStart, selectionEnd);
        }
    }

    /**
     * Sets the position of the text insertion caret.
     * The caret position is constrained to be between 0
     * and the last character of the text, inclusive.
     * If the passed-in value is greater than this range,
     * the value is set to the last character (or 0 if
     * the <code>TextComponent</code> contains no text)
     * and no error is returned.  If the passed-in value is
     * less than 0, an <code>IllegalArgumentException</code>
     * is thrown.
     *
     * <p>
     *  设置文本插入插入符号的位置。插入符位置被限制在0和文本的最后一个字符之间(含)。
     * 如果传入的值大于此范围,则将该值设置为最后一个字符(如果<code> TextComponent </code>不包含文本,则为0),并且不返回错误。
     * 如果传入的值小于0,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param        position the position of the text insertion caret
     * @exception    IllegalArgumentException if <code>position</code>
     *               is less than zero
     * @since        JDK1.1
     */
    public synchronized void setCaretPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("position less than zero.");
        }

        int maxposition = getText().length();
        if (position > maxposition) {
            position = maxposition;
        }

        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            peer.setCaretPosition(position);
        } else {
            select(position, position);
        }
    }

    /**
     * Returns the position of the text insertion caret.
     * The caret position is constrained to be between 0
     * and the last character of the text, inclusive.
     * If the text or caret have not been set, the default
     * caret position is 0.
     *
     * <p>
     *  返回文本插入插入符的位置。插入符位置被限制在0和文本的最后一个字符之间(含)。如果未设置文本或插入符号,则默认插入符号位置为0。
     * 
     * 
     * @return       the position of the text insertion caret
     * @see #setCaretPosition(int)
     * @since        JDK1.1
     */
    public synchronized int getCaretPosition() {
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        int position = 0;

        if (peer != null) {
            position = peer.getCaretPosition();
        } else {
            position = selectionStart;
        }
        int maxposition = getText().length();
        if (position > maxposition) {
            position = maxposition;
        }
        return position;
    }

    /**
     * Adds the specified text event listener to receive text events
     * from this text component.
     * If <code>l</code> is <code>null</code>, no exception is
     * thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的文本事件侦听器以从此文本组件接收文本事件。如果<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param l the text event listener
     * @see             #removeTextListener
     * @see             #getTextListeners
     * @see             java.awt.event.TextListener
     */
    public synchronized void addTextListener(TextListener l) {
        if (l == null) {
            return;
        }
        textListener = AWTEventMulticaster.add(textListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified text event listener so that it no longer
     * receives text events from this text component
     * If <code>l</code> is <code>null</code>, no exception is
     * thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的文本事件侦听器,以使其不再从此文本组件接收文本事件如果<code> l </code>是<code> null </code>,则不会抛出异常并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param           l     the text listener
     * @see             #addTextListener
     * @see             #getTextListeners
     * @see             java.awt.event.TextListener
     * @since           JDK1.1
     */
    public synchronized void removeTextListener(TextListener l) {
        if (l == null) {
            return;
        }
        textListener = AWTEventMulticaster.remove(textListener, l);
    }

    /**
     * Returns an array of all the text listeners
     * registered on this text component.
     *
     * <p>
     *  返回在此文本组件上注册的所有文本侦听器的数组。
     * 
     * 
     * @return all of this text component's <code>TextListener</code>s
     *         or an empty array if no text
     *         listeners are currently registered
     *
     *
     * @see #addTextListener
     * @see #removeTextListener
     * @since 1.4
     */
    public synchronized TextListener[] getTextListeners() {
        return getListeners(TextListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this <code>TextComponent</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>TextComponent</code> <code>t</code>
     * for its text listeners with the following code:
     *
     * <pre>TextListener[] tls = (TextListener[])(t.getListeners(TextListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     * 返回当前在<code> TextComponent </code>上注册为<code> <em> Foo </em> Listener </code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * 
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询其文本侦听器的<code> TextComponent </code> <code> t </code>：。
     * 
     *  <pre> TextListener [] tls =(TextListener [])(t.getListeners(TextListener.class)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组。
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this text component,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getTextListeners
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if  (listenerType == TextListener.class) {
            l = textListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        if (e.id == TextEvent.TEXT_VALUE_CHANGED) {
            if ((eventMask & AWTEvent.TEXT_EVENT_MASK) != 0 ||
                textListener != null) {
                return true;
            }
            return false;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this text component. If the event is a
     * <code>TextEvent</code>, it invokes the <code>processTextEvent</code>
     * method else it invokes its superclass's <code>processEvent</code>.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  处理此文本组件上的事件。
     * 如果事件是一个<code> TextEvent </code>,它调用<code> processTextEvent </code>方法,否则调用它的超类的<code> processEvent </code>
     * 。
     *  处理此文本组件上的事件。 <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param e the event
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof TextEvent) {
            processTextEvent((TextEvent)e);
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes text events occurring on this text component by
     * dispatching them to any registered <code>TextListener</code> objects.
     * <p>
     * NOTE: This method will not be called unless text events
     * are enabled for this component. This happens when one of the
     * following occurs:
     * <ul>
     * <li>A <code>TextListener</code> object is registered
     * via <code>addTextListener</code>
     * <li>Text events are enabled via <code>enableEvents</code>
     * </ul>
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将文本事件分派到任何已注册的<code> TextListener </code>对象来处理在此文本组件上发生的文本事件。
     * <p>
     *  注意：除非为此组件启用文本事件,否则不会调用此方法。发生以下情况之一时会发生这种情况：
     * <ul>
     *  <li>通过<code> addEvents </code>启用文本事件</code>通过<code> addTextListener </code>
     * </ul>
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param e the text event
     * @see Component#enableEvents
     */
    protected void processTextEvent(TextEvent e) {
        TextListener listener = textListener;
        if (listener != null) {
            int id = e.getID();
            switch (id) {
            case TextEvent.TEXT_VALUE_CHANGED:
                listener.textValueChanged(e);
                break;
            }
        }
    }

    /**
     * Returns a string representing the state of this
     * <code>TextComponent</code>. This
     * method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     * 返回表示此<code> TextComponent </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return      the parameter string of this text component
     */
    protected String paramString() {
        String str = super.paramString() + ",text=" + getText();
        if (editable) {
            str += ",editable";
        }
        return str + ",selection=" + getSelectionStart() + "-" + getSelectionEnd();
    }

    /**
     * Assigns a valid value to the canAccessClipboard instance variable.
     * <p>
     *  为canAccessClipboard实例变量分配有效的值。
     * 
     */
    private boolean canAccessClipboard() {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) return true;
        try {
            sm.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
            return true;
        } catch (SecurityException e) {}
        return false;
    }

    /*
     * Serialization support.
     * <p>
     *  序列化支持。
     * 
     */
    /**
     * The textComponent SerializedDataVersion.
     *
     * <p>
     *  textComponent SerializedDataVersion。
     * 
     * 
     * @serial
     */
    private int textComponentSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable TextListener(s) as optional data.
     * The non-serializable TextListener(s) are detected and
     * no attempt is made to serialize them.
     *
     * <p>
     *  将缺省可序列化字段写入流。将可序列化TextListener列表作为可选数据。检测到不可序列化的TextListener,并且不尝试将它们序列化。
     * 
     * 
     * @serialData Null terminated sequence of zero or more pairs.
     *             A pair consists of a String and Object.
     *             The String indicates the type of object and
     *             is one of the following :
     *             textListenerK indicating and TextListener object.
     *
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#textListenerK
     */
    private void writeObject(java.io.ObjectOutputStream s)
      throws IOException
    {
        // Serialization support.  Since the value of the fields
        // selectionStart, selectionEnd, and text aren't necessarily
        // up to date, we sync them up with the peer before serializing.
        TextComponentPeer peer = (TextComponentPeer)this.peer;
        if (peer != null) {
            text = peer.getText();
            selectionStart = peer.getSelectionStart();
            selectionEnd = peer.getSelectionEnd();
        }

        s.defaultWriteObject();

        AWTEventMulticaster.save(s, textListenerK, textListener);
        s.writeObject(null);
    }

    /**
     * Read the ObjectInputStream, and if it isn't null,
     * add a listener to receive text events fired by the
     * TextComponent.  Unrecognized keys or values will be
     * ignored.
     *
     * <p>
     *  读取ObjectInputStream,如果它不为null,添加一个监听器来接收TextComponent触发的文本事件。无法识别的键或值将被忽略。
     * 
     * 
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless()</code> returns
     * <code>true</code>
     * @see #removeTextListener
     * @see #addTextListener
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException
    {
        GraphicsEnvironment.checkHeadless();
        s.defaultReadObject();

        // Make sure the state we just read in for text,
        // selectionStart and selectionEnd has legal values
        this.text = (text != null) ? text : "";
        select(selectionStart, selectionEnd);

        Object keyOrNull;
        while(null != (keyOrNull = s.readObject())) {
            String key = ((String)keyOrNull).intern();

            if (textListenerK == key) {
                addTextListener((TextListener)(s.readObject()));
            } else {
                // skip value for unrecognized key
                s.readObject();
            }
        }
        enableInputMethodsIfNecessary();
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this TextComponent.
     * For text components, the AccessibleContext takes the form of an
     * AccessibleAWTTextComponent.
     * A new AccessibleAWTTextComponent instance is created if necessary.
     *
     * <p>
     *  获取与此TextComponent相关联的AccessibleContext。对于文本组件,AccessibleContext采用AccessibleAWTTextComponent的形式。
     * 如果需要,将创建一个新的AccessibleAWTTextComponent实例。
     * 
     * 
     * @return an AccessibleAWTTextComponent that serves as the
     *         AccessibleContext of this TextComponent
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTTextComponent();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>TextComponent</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to text component user-interface
     * elements.
     * <p>
     *  此类实现<code> TextComponent </code>类的辅助功能支持。它提供了适用于文本组件用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTTextComponent extends AccessibleAWTComponent
        implements AccessibleText, TextListener
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 3631432373506317811L;

        /**
         * Constructs an AccessibleAWTTextComponent.  Adds a listener to track
         * caret change.
         * <p>
         *  构造一个AccessibleAWTTextComponent。添加监听器以跟踪插入符号更改。
         * 
         */
        public AccessibleAWTTextComponent() {
            TextComponent.this.addTextListener(this);
        }

        /**
         * TextListener notification of a text value change.
         * <p>
         *  TextListener通知文本值更改。
         * 
         */
        public void textValueChanged(TextEvent textEvent)  {
            Integer cpos = Integer.valueOf(TextComponent.this.getCaretPosition());
            firePropertyChange(ACCESSIBLE_TEXT_PROPERTY, null, cpos);
        }

        /**
         * Gets the state set of the TextComponent.
         * The AccessibleStateSet of an object is composed of a set of
         * unique AccessibleStates.  A change in the AccessibleStateSet
         * of an object will cause a PropertyChangeEvent to be fired
         * for the AccessibleContext.ACCESSIBLE_STATE_PROPERTY property.
         *
         * <p>
         * 获取TextComponent的状态集。对象的AccessibleStateSet由一组唯一的AccessibleStates组成。
         * 对象的AccessibleStateSet中的更改将导致针对AccessibleContext.ACCESSIBLE_STATE_PROPERTY属性触发PropertyChangeEvent。
         * 
         * 
         * @return an instance of AccessibleStateSet containing the
         * current state set of the object
         * @see AccessibleStateSet
         * @see AccessibleState
         * @see #addPropertyChangeListener
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (TextComponent.this.isEditable()) {
                states.add(AccessibleState.EDITABLE);
            }
            return states;
        }


        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object (AccessibleRole.TEXT)
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.TEXT;
        }

        /**
         * Get the AccessibleText associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleText interface on behalf of itself.
         *
         * <p>
         *  获取与此对象相关联的AccessibleText。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleText接口。
         * 
         * 
         * @return this object
         */
        public AccessibleText getAccessibleText() {
            return this;
        }


        // --- interface AccessibleText methods ------------------------

        /**
         * Many of these methods are just convenience methods; they
         * just call the equivalent on the parent
         * <p>
         *  许多这些方法只是方便的方法;他们只是调用父类的等价
         * 
         */

        /**
         * Given a point in local coordinates, return the zero-based index
         * of the character under that Point.  If the point is invalid,
         * this method returns -1.
         *
         * <p>
         *  给定一个点在局部坐标,返回该点下的字符的从零开始的索引。如果该点无效,则此方法返回-1。
         * 
         * 
         * @param p the Point in local coordinates
         * @return the zero-based index of the character under Point p.
         */
        public int getIndexAtPoint(Point p) {
            return -1;
        }

        /**
         * Determines the bounding box of the character at the given
         * index into the string.  The bounds are returned in local
         * coordinates.  If the index is invalid a null rectangle
         * is returned.
         *
         * <p>
         *  确定给定索引处字符的边界框到字符串中。边界在本地坐标中返回。如果索引无效,则返回空矩形。
         * 
         * 
         * @param i the index into the String &gt;= 0
         * @return the screen coordinates of the character's bounding box
         */
        public Rectangle getCharacterBounds(int i) {
            return null;
        }

        /**
         * Returns the number of characters (valid indicies)
         *
         * <p>
         *  返回字符数(有效的标记)
         * 
         * 
         * @return the number of characters &gt;= 0
         */
        public int getCharCount() {
            return TextComponent.this.getText().length();
        }

        /**
         * Returns the zero-based offset of the caret.
         *
         * Note: The character to the right of the caret will have the
         * same index value as the offset (the caret is between
         * two characters).
         *
         * <p>
         *  返回插入符号的从零开始的偏移量。
         * 
         *  注意：插入符右侧的字符将具有与偏移量相同的索引值(插入符号在两个字符之间)。
         * 
         * 
         * @return the zero-based offset of the caret.
         */
        public int getCaretPosition() {
            return TextComponent.this.getCaretPosition();
        }

        /**
         * Returns the AttributeSet for a given character (at a given index).
         *
         * <p>
         *  返回给定字符的AttributeSet(在给定索引处)。
         * 
         * 
         * @param i the zero-based index into the text
         * @return the AttributeSet of the character
         */
        public AttributeSet getCharacterAttribute(int i) {
            return null; // No attributes in TextComponent
        }

        /**
         * Returns the start offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         * Return 0 if the text is empty, or the caret position
         * if no selection.
         *
         * <p>
         *  返回所选文本内的起始偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。如果文本为空,返回0,如果没有选择则返回插入符位置。
         * 
         * 
         * @return the index into the text of the start of the selection &gt;= 0
         */
        public int getSelectionStart() {
            return TextComponent.this.getSelectionStart();
        }

        /**
         * Returns the end offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         * Return 0 if the text is empty, or the caret position
         * if no selection.
         *
         * <p>
         * 返回所选文本内的结束偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。如果文本为空,返回0,如果没有选择则返回插入符位置。
         * 
         * 
         * @return the index into the text of the end of the selection &gt;= 0
         */
        public int getSelectionEnd() {
            return TextComponent.this.getSelectionEnd();
        }

        /**
         * Returns the portion of the text that is selected.
         *
         * <p>
         *  返回所选文本的部分。
         * 
         * 
         * @return the text, null if no selection
         */
        public String getSelectedText() {
            String selText = TextComponent.this.getSelectedText();
            // Fix for 4256662
            if (selText == null || selText.equals("")) {
                return null;
            }
            return selText;
        }

        /**
         * Returns the String at a given index.
         *
         * <p>
         *  返回给定索引处的String。
         * 
         * 
         * @param part the AccessibleText.CHARACTER, AccessibleText.WORD,
         * or AccessibleText.SENTENCE to retrieve
         * @param index an index within the text &gt;= 0
         * @return the letter, word, or sentence,
         *   null for an invalid index or part
         */
        public String getAtIndex(int part, int index) {
            if (index < 0 || index >= TextComponent.this.getText().length()) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                return TextComponent.this.getText().substring(index, index+1);
            case AccessibleText.WORD:  {
                    String s = TextComponent.this.getText();
                    BreakIterator words = BreakIterator.getWordInstance();
                    words.setText(s);
                    int end = words.following(index);
                    return s.substring(words.previous(), end);
                }
            case AccessibleText.SENTENCE:  {
                    String s = TextComponent.this.getText();
                    BreakIterator sentence = BreakIterator.getSentenceInstance();
                    sentence.setText(s);
                    int end = sentence.following(index);
                    return s.substring(sentence.previous(), end);
                }
            default:
                return null;
            }
        }

        private static final boolean NEXT = true;
        private static final boolean PREVIOUS = false;

        /**
         * Needed to unify forward and backward searching.
         * The method assumes that s is the text assigned to words.
         * <p>
         *  需要统一向前和向后搜索。该方法假设s是分配给单词的文本。
         * 
         */
        private int findWordLimit(int index, BreakIterator words, boolean direction,
                                         String s) {
            // Fix for 4256660 and 4256661.
            // Words iterator is different from character and sentence iterators
            // in that end of one word is not necessarily start of another word.
            // Please see java.text.BreakIterator JavaDoc. The code below is
            // based on nextWordStartAfter example from BreakIterator.java.
            int last = (direction == NEXT) ? words.following(index)
                                           : words.preceding(index);
            int current = (direction == NEXT) ? words.next()
                                              : words.previous();
            while (current != BreakIterator.DONE) {
                for (int p = Math.min(last, current); p < Math.max(last, current); p++) {
                    if (Character.isLetter(s.charAt(p))) {
                        return last;
                    }
                }
                last = current;
                current = (direction == NEXT) ? words.next()
                                              : words.previous();
            }
            return BreakIterator.DONE;
        }

        /**
         * Returns the String after a given index.
         *
         * <p>
         *  返回给定索引后的String。
         * 
         * 
         * @param part the AccessibleText.CHARACTER, AccessibleText.WORD,
         * or AccessibleText.SENTENCE to retrieve
         * @param index an index within the text &gt;= 0
         * @return the letter, word, or sentence, null for an invalid
         *  index or part
         */
        public String getAfterIndex(int part, int index) {
            if (index < 0 || index >= TextComponent.this.getText().length()) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                if (index+1 >= TextComponent.this.getText().length()) {
                   return null;
                }
                return TextComponent.this.getText().substring(index+1, index+2);
            case AccessibleText.WORD:  {
                    String s = TextComponent.this.getText();
                    BreakIterator words = BreakIterator.getWordInstance();
                    words.setText(s);
                    int start = findWordLimit(index, words, NEXT, s);
                    if (start == BreakIterator.DONE || start >= s.length()) {
                        return null;
                    }
                    int end = words.following(start);
                    if (end == BreakIterator.DONE || end >= s.length()) {
                        return null;
                    }
                    return s.substring(start, end);
                }
            case AccessibleText.SENTENCE:  {
                    String s = TextComponent.this.getText();
                    BreakIterator sentence = BreakIterator.getSentenceInstance();
                    sentence.setText(s);
                    int start = sentence.following(index);
                    if (start == BreakIterator.DONE || start >= s.length()) {
                        return null;
                    }
                    int end = sentence.following(start);
                    if (end == BreakIterator.DONE || end >= s.length()) {
                        return null;
                    }
                    return s.substring(start, end);
                }
            default:
                return null;
            }
        }


        /**
         * Returns the String before a given index.
         *
         * <p>
         *  返回给定索引之前的String。
         * 
         * @param part the AccessibleText.CHARACTER, AccessibleText.WORD,
         *   or AccessibleText.SENTENCE to retrieve
         * @param index an index within the text &gt;= 0
         * @return the letter, word, or sentence, null for an invalid index
         *  or part
         */
        public String getBeforeIndex(int part, int index) {
            if (index < 0 || index > TextComponent.this.getText().length()-1) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                if (index == 0) {
                    return null;
                }
                return TextComponent.this.getText().substring(index-1, index);
            case AccessibleText.WORD:  {
                    String s = TextComponent.this.getText();
                    BreakIterator words = BreakIterator.getWordInstance();
                    words.setText(s);
                    int end = findWordLimit(index, words, PREVIOUS, s);
                    if (end == BreakIterator.DONE) {
                        return null;
                    }
                    int start = words.preceding(end);
                    if (start == BreakIterator.DONE) {
                        return null;
                    }
                    return s.substring(start, end);
                }
            case AccessibleText.SENTENCE:  {
                    String s = TextComponent.this.getText();
                    BreakIterator sentence = BreakIterator.getSentenceInstance();
                    sentence.setText(s);
                    int end = sentence.following(index);
                    end = sentence.previous();
                    int start = sentence.previous();
                    if (start == BreakIterator.DONE) {
                        return null;
                    }
                    return s.substring(start, end);
                }
            default:
                return null;
            }
        }
    }  // end of AccessibleAWTTextComponent

    private boolean checkForEnableIM = true;
}
