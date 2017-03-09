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
import java.awt.event.ActionEvent;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

/**
 * A text component that can be marked up with attributes that are
 * represented graphically.
 * You can find how-to information and examples of using text panes in
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/text.html">Using Text Components</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 * This component models paragraphs
 * that are composed of runs of character level attributes.  Each
 * paragraph may have a logical style attached to it which contains
 * the default attributes to use if not overridden by attributes set
 * on the paragraph or character run.  Components and images may
 * be embedded in the flow of text.
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
 *   attribute: isContainer true
 * description: A text component that can be marked up with attributes that are graphically represented.
 *
 * <p>
 *  可以用图形表示的属性标记的文本组件。
 * 您可以在<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/text.html">使用文本组件</a>中找到如何使用文
 * 本窗格的说明信息和示例, Java教程</em>中的一个部分。
 *  可以用图形表示的属性标记的文本组件。
 * 
 * <p>
 *  此组件对由字符级属性运行组成的段落进行建模。每个段落可以具有附加到它的逻辑样式,其包含如果没有被段落或字符运行上设置的属性重写而使用的默认属性。组件和图像可以嵌入在文本流中。
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
 * @beaninfo attribute：isContainer true description：可以用图形表示的属性标记的文本组件。
 * 
 * 
 * @author  Timothy Prinzing
 * @see javax.swing.text.StyledEditorKit
 */
public class JTextPane extends JEditorPane {

    /**
     * Creates a new <code>JTextPane</code>.  A new instance of
     * <code>StyledEditorKit</code> is
     * created and set, and the document model set to <code>null</code>.
     * <p>
     *  创建新的<code> JTextPane </code>。创建并设置<code> StyledEditorKit </code>的新实例,并将文档模型设置为<code> null </code>。
     * 
     */
    public JTextPane() {
        super();
        EditorKit editorKit = createDefaultEditorKit();
        String contentType = editorKit.getContentType();
        if (contentType != null
            && getEditorKitClassNameForContentType(contentType) ==
                 defaultEditorKitMap.get(contentType)) {
            setEditorKitForContentType(contentType, editorKit);
        }
        setEditorKit(editorKit);
    }

    /**
     * Creates a new <code>JTextPane</code>, with a specified document model.
     * A new instance of <code>javax.swing.text.StyledEditorKit</code>
     *  is created and set.
     *
     * <p>
     *  使用指定的文档模型创建新的<code> JTextPane </code>。创建并设置<code> javax.swing.text.StyledEditorKit </code>的新实例。
     * 
     * 
     * @param doc the document model
     */
    public JTextPane(StyledDocument doc) {
        this();
        setStyledDocument(doc);
    }

    /**
     * Returns the class ID for the UI.
     *
     * <p>
     *  返回UI的类ID。
     * 
     * 
     * @return the string "TextPaneUI"
     *
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Associates the editor with a text document.  This
     * must be a <code>StyledDocument</code>.
     *
     * <p>
     *  将编辑器与文本文档相关联。这必须是<code> StyledDocument </code>。
     * 
     * 
     * @param doc  the document to display/edit
     * @exception IllegalArgumentException  if <code>doc</code> can't
     *   be narrowed to a <code>StyledDocument</code> which is the
     *   required type of model for this text component
     */
    public void setDocument(Document doc) {
        if (doc instanceof StyledDocument) {
            super.setDocument(doc);
        } else {
            throw new IllegalArgumentException("Model must be StyledDocument");
        }
    }

    /**
     * Associates the editor with a text document.
     * The currently registered factory is used to build a view for
     * the document, which gets displayed by the editor.
     *
     * <p>
     *  将编辑器与文本文档相关联。当前注册的工厂用于构建文档的视图,该视图由编辑器显示。
     * 
     * 
     * @param doc  the document to display/edit
     */
    public void setStyledDocument(StyledDocument doc) {
        super.setDocument(doc);
    }

    /**
     * Fetches the model associated with the editor.
     *
     * <p>
     *  获取与编辑器关联的模型。
     * 
     * 
     * @return the model
     */
    public StyledDocument getStyledDocument() {
        return (StyledDocument) getDocument();
    }

    /**
     * Replaces the currently selected content with new content
     * represented by the given string.  If there is no selection
     * this amounts to an insert of the given text.  If there
     * is no replacement text this amounts to a removal of the
     * current selection.  The replacement text will have the
     * attributes currently defined for input at the point of
     * insertion.  If the document is not editable, beep and return.
     *
     * <p>
     *  用由给定字符串表示的新内容替换当前选择的内容。如果没有选择,这相当于给定文本的插入。如果没有替换文本,则相当于删除当前选择。替换文本将具有当前为插入点处的输入定义的属性。
     * 如果文档不可编辑,哔声并返回。
     * 
     * 
     * @param content  the content to replace the selection with
     */
    @Override
    public void replaceSelection(String content) {
        replaceSelection(content, true);
    }

    private void replaceSelection(String content, boolean checkEditable) {
        if (checkEditable && !isEditable()) {
            UIManager.getLookAndFeel().provideErrorFeedback(JTextPane.this);
            return;
        }
        Document doc = getStyledDocument();
        if (doc != null) {
            try {
                Caret caret = getCaret();
                boolean composedTextSaved = saveComposedText(caret.getDot());
                int p0 = Math.min(caret.getDot(), caret.getMark());
                int p1 = Math.max(caret.getDot(), caret.getMark());
                AttributeSet attr = getInputAttributes().copyAttributes();
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).replace(p0, p1 - p0, content,attr);
                }
                else {
                    if (p0 != p1) {
                        doc.remove(p0, p1 - p0);
                    }
                    if (content != null && content.length() > 0) {
                        doc.insertString(p0, content, attr);
                    }
                }
                if (composedTextSaved) {
                    restoreComposedText();
                }
            } catch (BadLocationException e) {
                UIManager.getLookAndFeel().provideErrorFeedback(JTextPane.this);
            }
        }
    }

    /**
     * Inserts a component into the document as a replacement
     * for the currently selected content.  If there is no
     * selection the component is effectively inserted at the
     * current position of the caret.  This is represented in
     * the associated document as an attribute of one character
     * of content.
     * <p>
     * The component given is the actual component used by the
     * JTextPane.  Since components cannot be a child of more than
     * one container, this method should not be used in situations
     * where the model is shared by text components.
     * <p>
     * The component is placed relative to the text baseline
     * according to the value returned by
     * <code>Component.getAlignmentY</code>.  For Swing components
     * this value can be conveniently set using the method
     * <code>JComponent.setAlignmentY</code>.  For example, setting
     * a value of <code>0.75</code> will cause 75 percent of the
     * component to be above the baseline, and 25 percent of the
     * component to be below the baseline.
     *
     * <p>
     *  将组件插入到文档中以替换当前选定的内容。如果没有选择,则组件有效地插入到插入符号的当前位置。这在相关联的文档中被表示为一个字符的内容的属性。
     * <p>
     * 给定的组件是JTextPane使用的实际组件。由于组件不能是多个容器的子对象,因此此方法不应在模型由文本组件共享的情况下使用。
     * <p>
     *  组件根据<code> Component.getAlignmentY </code>返回的值相对于文本基线放置。
     * 对于Swing组件,可以使用方法<code> JComponent.setAlignmentY </code>方便地设置此值。
     * 例如,设置<code> 0.75 </code>的值将使75％的组件高于基线,25％的组件低于基线。
     * 
     * 
     * @param c    the component to insert
     */
    public void insertComponent(Component c) {
        MutableAttributeSet inputAttributes = getInputAttributes();
        inputAttributes.removeAttributes(inputAttributes);
        StyleConstants.setComponent(inputAttributes, c);
        replaceSelection(" ", false);
        inputAttributes.removeAttributes(inputAttributes);
    }

    /**
     * Inserts an icon into the document as a replacement
     * for the currently selected content.  If there is no
     * selection the icon is effectively inserted at the
     * current position of the caret.  This is represented in
     * the associated document as an attribute of one character
     * of content.
     *
     * <p>
     *  在文档中插入图标​​,替换当前选定的内容。如果没有选择,则图标被有效地插入到插入符号的当前位置。这在相关联的文档中被表示为一个字符的内容的属性。
     * 
     * 
     * @param g    the icon to insert
     * @see Icon
     */
    public void insertIcon(Icon g) {
        MutableAttributeSet inputAttributes = getInputAttributes();
        inputAttributes.removeAttributes(inputAttributes);
        StyleConstants.setIcon(inputAttributes, g);
        replaceSelection(" ", false);
        inputAttributes.removeAttributes(inputAttributes);
    }

    /**
     * Adds a new style into the logical style hierarchy.  Style attributes
     * resolve from bottom up so an attribute specified in a child
     * will override an attribute specified in the parent.
     *
     * <p>
     *  在逻辑样式层次结构中添加新样式。样式属性从下到上解析,因此子级中指定的属性将覆盖父级中指定的属性。
     * 
     * 
     * @param nm   the name of the style (must be unique within the
     *   collection of named styles).  The name may be <code>null</code>
     *   if the style is unnamed, but the caller is responsible
     *   for managing the reference returned as an unnamed style can't
     *   be fetched by name.  An unnamed style may be useful for things
     *   like character attribute overrides such as found in a style
     *   run.
     * @param parent the parent style.  This may be <code>null</code>
     *   if unspecified
     *   attributes need not be resolved in some other style.
     * @return the new <code>Style</code>
     */
    public Style addStyle(String nm, Style parent) {
        StyledDocument doc = getStyledDocument();
        return doc.addStyle(nm, parent);
    }

    /**
     * Removes a named non-<code>null</code> style previously added to
     * the document.
     *
     * <p>
     *  删除以前添加到文档中的命名非<code> null </code>样式。
     * 
     * 
     * @param nm  the name of the style to remove
     */
    public void removeStyle(String nm) {
        StyledDocument doc = getStyledDocument();
        doc.removeStyle(nm);
    }

    /**
     * Fetches a named non-<code>null</code> style previously added.
     *
     * <p>
     *  获取先前添加的命名非<code> null </code>样式。
     * 
     * 
     * @param nm  the name of the style
     * @return the <code>Style</code>
     */
    public Style getStyle(String nm) {
        StyledDocument doc = getStyledDocument();
        return doc.getStyle(nm);
    }

    /**
     * Sets the logical style to use for the paragraph at the
     * current caret position.  If attributes aren't explicitly set
     * for character and paragraph attributes they will resolve
     * through the logical style assigned to the paragraph, which
     * in term may resolve through some hierarchy completely
     * independent of the element hierarchy in the document.
     *
     * <p>
     * 设置当前插入位置处段落使用的逻辑样式。如果没有为字符和段落属性显式设置属性,则它们将通过分配给段落的逻辑样式解析,其在术语中可以通过完全独立于文档中的元素层次结构的某个层次来解析。
     * 
     * 
     * @param s  the logical style to assign to the paragraph,
     *          or <code>null</code> for no style
     */
    public void setLogicalStyle(Style s) {
        StyledDocument doc = getStyledDocument();
        doc.setLogicalStyle(getCaretPosition(), s);
    }

    /**
     * Fetches the logical style assigned to the paragraph represented
     * by the current position of the caret, or <code>null</code>.
     *
     * <p>
     *  获取分配给由插入符号的当前位置表示的段落或<code> null </code>的逻辑样式。
     * 
     * 
     * @return the <code>Style</code>
     */
    public Style getLogicalStyle() {
        StyledDocument doc = getStyledDocument();
        return doc.getLogicalStyle(getCaretPosition());
    }

    /**
     * Fetches the character attributes in effect at the
     * current location of the caret, or <code>null</code>.
     *
     * <p>
     *  获取插入符当前位置生效的字符属性,或<code> null </code>。
     * 
     * 
     * @return the attributes, or <code>null</code>
     */
    public AttributeSet getCharacterAttributes() {
        StyledDocument doc = getStyledDocument();
        Element run = doc.getCharacterElement(getCaretPosition());
        if (run != null) {
            return run.getAttributes();
        }
        return null;
    }

    /**
     * Applies the given attributes to character
     * content.  If there is a selection, the attributes
     * are applied to the selection range.  If there
     * is no selection, the attributes are applied to
     * the input attribute set which defines the attributes
     * for any new text that gets inserted.
     *
     * <p>
     *  将给定的属性应用于字符内容。如果存在选择,则属性应用于选择范围。如果没有选择,则属性应用于输入属性集,该属性集定义插入的任何新文本的属性。
     * 
     * 
     * @param attr the attributes
     * @param replace if true, then replace the existing attributes first
     */
    public void setCharacterAttributes(AttributeSet attr, boolean replace) {
        int p0 = getSelectionStart();
        int p1 = getSelectionEnd();
        if (p0 != p1) {
            StyledDocument doc = getStyledDocument();
            doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
        } else {
            MutableAttributeSet inputAttributes = getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }
    }

    /**
     * Fetches the current paragraph attributes in effect
     * at the location of the caret, or <code>null</code> if none.
     *
     * <p>
     *  获取在插入符位置生效的当前段落属性,如果没有则为<code> null </code>。
     * 
     * 
     * @return the attributes
     */
    public AttributeSet getParagraphAttributes() {
        StyledDocument doc = getStyledDocument();
        Element paragraph = doc.getParagraphElement(getCaretPosition());
        if (paragraph != null) {
            return paragraph.getAttributes();
        }
        return null;
    }

    /**
     * Applies the given attributes to paragraphs.  If
     * there is a selection, the attributes are applied
     * to the paragraphs that intersect the selection.
     * If there is no selection, the attributes are applied
     * to the paragraph at the current caret position.
     *
     * <p>
     *  将给定的属性应用于段落。如果存在选择,则属性应用于与选择相交的段落。如果没有选择,则属性应用于当前插入位置处的段落。
     * 
     * 
     * @param attr the non-<code>null</code> attributes
     * @param replace if true, replace the existing attributes first
     */
    public void setParagraphAttributes(AttributeSet attr, boolean replace) {
        int p0 = getSelectionStart();
        int p1 = getSelectionEnd();
        StyledDocument doc = getStyledDocument();
        doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
    }

    /**
     * Gets the input attributes for the pane.
     *
     * <p>
     *  获取窗格的输入属性。
     * 
     * 
     * @return the attributes
     */
    public MutableAttributeSet getInputAttributes() {
        return getStyledEditorKit().getInputAttributes();
    }

    /**
     * Gets the editor kit.
     *
     * <p>
     *  获取编辑器工具包。
     * 
     * 
     * @return the editor kit
     */
    protected final StyledEditorKit getStyledEditorKit() {
        return (StyledEditorKit) getEditorKit();
    }

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "TextPaneUI";


    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     *
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
     * 
     * 
     * @param s the output stream
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


    // --- JEditorPane ------------------------------------

    /**
     * Creates the <code>EditorKit</code> to use by default.  This
     * is implemented to return <code>javax.swing.text.StyledEditorKit</code>.
     *
     * <p>
     * 创建默认使用的<code> EditorKit </code>。这是实现返回<code> javax.swing.text.StyledEditorKit </code>。
     * 
     * 
     * @return the editor kit
     */
    protected EditorKit createDefaultEditorKit() {
        return new StyledEditorKit();
    }

    /**
     * Sets the currently installed kit for handling
     * content.  This is the bound property that
     * establishes the content type of the editor.
     *
     * <p>
     *  设置当前安装的用于处理内容的工具包。这是用于建立编辑器的内容类型的bound属性。
     * 
     * 
     * @param kit the desired editor behavior
     * @exception IllegalArgumentException if kit is not a
     *          <code>StyledEditorKit</code>
     */
    public final void setEditorKit(EditorKit kit) {
        if (kit instanceof StyledEditorKit) {
            super.setEditorKit(kit);
        } else {
            throw new IllegalArgumentException("Must be StyledEditorKit");
        }
    }

    /**
     * Returns a string representation of this <code>JTextPane</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JTextPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * @return  a string representation of this <code>JTextPane</code>
     */
    protected String paramString() {
        return super.paramString();
    }

}
