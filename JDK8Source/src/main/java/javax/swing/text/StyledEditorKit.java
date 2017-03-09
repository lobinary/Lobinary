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
package javax.swing.text;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.*;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * This is the set of things needed by a text component
 * to be a reasonably functioning editor for some <em>type</em>
 * of text document.  This implementation provides a default
 * implementation which treats text as styled text and
 * provides a minimal set of actions for editing styled text.
 *
 * <p>
 *  这是文本组件需要的一组事物,它是一些<em>类型</em>文本文档的合理功能的编辑器。此实现提供了一种默认实现,将文本视为样式文本,并提供了一组用于编辑样式文本的最小动作。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class StyledEditorKit extends DefaultEditorKit {

    /**
     * Creates a new EditorKit used for styled documents.
     * <p>
     *  创建用于样式文档的新EditorKit。
     * 
     */
    public StyledEditorKit() {
        createInputAttributeUpdated();
        createInputAttributes();
    }

    /**
     * Gets the input attributes for the pane.  When
     * the caret moves and there is no selection, the
     * input attributes are automatically mutated to
     * reflect the character attributes of the current
     * caret location.  The styled editing actions
     * use the input attributes to carry out their
     * actions.
     *
     * <p>
     *  获取窗格的输入属性。当插入符移动并且没有选择时,输入属性将自动更改以反映当前插入符位置的字符属性。样式编辑操作使用输入属性来执行其操作。
     * 
     * 
     * @return the attribute set
     */
    public MutableAttributeSet getInputAttributes() {
        return inputAttributes;
    }

    /**
     * Fetches the element representing the current
     * run of character attributes for the caret.
     *
     * <p>
     *  获取表示插入符的字符属性的当前运行的元素。
     * 
     * 
     * @return the element
     */
    public Element getCharacterAttributeRun() {
        return currentRun;
    }

    // --- EditorKit methods ---------------------------

    /**
     * Fetches the command list for the editor.  This is
     * the list of commands supported by the superclass
     * augmented by the collection of commands defined
     * locally for style operations.
     *
     * <p>
     *  获取编辑器的命令列表。这是由超类支持的命令列表,由本地为样式操作定义的命令集合扩充。
     * 
     * 
     * @return the command list
     */
    public Action[] getActions() {
        return TextAction.augmentList(super.getActions(), this.defaultActions);
    }

    /**
     * Creates an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * <p>
     *  创建适合此类型编辑器的未初始化文本存储模型。
     * 
     * 
     * @return the model
     */
    public Document createDefaultDocument() {
        return new DefaultStyledDocument();
    }

    /**
     * Called when the kit is being installed into
     * a JEditorPane.
     *
     * <p>
     *  当工具包安装到JEditorPane中时调用。
     * 
     * 
     * @param c the JEditorPane
     */
    public void install(JEditorPane c) {
        c.addCaretListener(inputAttributeUpdater);
        c.addPropertyChangeListener(inputAttributeUpdater);
        Caret caret = c.getCaret();
        if (caret != null) {
            inputAttributeUpdater.updateInputAttributes
                                  (caret.getDot(), caret.getMark(), c);
        }
    }

    /**
     * Called when the kit is being removed from the
     * JEditorPane.  This is used to unregister any
     * listeners that were attached.
     *
     * <p>
     *  在从JEditorPane中删除工具包时调用。这用于注销附加的任何侦听器。
     * 
     * 
     * @param c the JEditorPane
     */
    public void deinstall(JEditorPane c) {
        c.removeCaretListener(inputAttributeUpdater);
        c.removePropertyChangeListener(inputAttributeUpdater);

        // remove references to current document so it can be collected.
        currentRun = null;
        currentParagraph = null;
    }

   /**
     * Fetches a factory that is suitable for producing
     * views of any models that are produced by this
     * kit.  This is implemented to return View implementations
     * for the following kinds of elements:
     * <ul>
     * <li>AbstractDocument.ContentElementName
     * <li>AbstractDocument.ParagraphElementName
     * <li>AbstractDocument.SectionElementName
     * <li>StyleConstants.ComponentElementName
     * <li>StyleConstants.IconElementName
     * </ul>
     *
     * <p>
     *  获取适合于生产此套件生产的任何型号视图的工厂。这被实现为返回以下种类的元素的View实现：
     * <ul>
     * <li> AbstractDocument.ContentElementName <li> AbstractDocument.ParagraphElementName <li> AbstractDocu
     * ment.SectionElementName <li> StyleConstants.ComponentElementName <li> StyleConstants.IconElementName。
     * </ul>
     * 
     * 
     * @return the factory
     */
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    /**
     * Creates a copy of the editor kit.
     *
     * <p>
     *  创建编辑器工具包的副本。
     * 
     * 
     * @return the copy
     */
    public Object clone() {
        StyledEditorKit o = (StyledEditorKit)super.clone();
        o.currentRun = o.currentParagraph = null;
        o.createInputAttributeUpdated();
        o.createInputAttributes();
        return o;
    }

    /**
     * Creates the AttributeSet used for the selection.
     * <p>
     *  创建用于选择的AttributeSet。
     * 
     */
    private void createInputAttributes() {
        inputAttributes = new SimpleAttributeSet() {
            public AttributeSet getResolveParent() {
                return (currentParagraph != null) ?
                           currentParagraph.getAttributes() : null;
            }

            public Object clone() {
                return new SimpleAttributeSet(this);
            }
        };
    }

    /**
     * Creates a new <code>AttributeTracker</code>.
     * <p>
     *  创建新的<code> AttributeTracker </code>。
     * 
     */
    private void createInputAttributeUpdated() {
        inputAttributeUpdater = new AttributeTracker();
    }


    private static final ViewFactory defaultFactory = new StyledViewFactory();

    Element currentRun;
    Element currentParagraph;

    /**
     * This is the set of attributes used to store the
     * input attributes.
     * <p>
     *  这是用于存储输入属性的属性集。
     * 
     */
    MutableAttributeSet inputAttributes;

    /**
     * This listener will be attached to the caret of
     * the text component that the EditorKit gets installed
     * into.  This should keep the input attributes updated
     * for use by the styled actions.
     * <p>
     *  此侦听器将附加到EditorKit安装到的文本组件的插入符号。这应该保持输入属性更新为样式化操作使用。
     * 
     */
    private AttributeTracker inputAttributeUpdater;

    /**
     * Tracks caret movement and keeps the input attributes set
     * to reflect the current set of attribute definitions at the
     * caret position.
     * <p>This implements PropertyChangeListener to update the
     * input attributes when the Document changes, as if the Document
     * changes the attributes will almost certainly change.
     * <p>
     *  跟踪插入符移动并保持输入属性设置为反映插入符位置处的当前属性定义集。
     *  <p>这实现了PropertyChangeListener,以在Document更改时更新输入属性,如同Document更改属性几乎肯定会更改。
     * 
     */
    class AttributeTracker implements CaretListener, PropertyChangeListener, Serializable {

        /**
         * Updates the attributes. <code>dot</code> and <code>mark</code>
         * mark give the positions of the selection in <code>c</code>.
         * <p>
         *  更新属性。 <code> dot </code>和<code> mark </code>标记给出<code> c </code>中选择的位置。
         * 
         */
        void updateInputAttributes(int dot, int mark, JTextComponent c) {
            // EditorKit might not have installed the StyledDocument yet.
            Document aDoc = c.getDocument();
            if (!(aDoc instanceof StyledDocument)) {
                return ;
            }
            int start = Math.min(dot, mark);
            // record current character attributes.
            StyledDocument doc = (StyledDocument)aDoc;
            // If nothing is selected, get the attributes from the character
            // before the start of the selection, otherwise get the attributes
            // from the character element at the start of the selection.
            Element run;
            currentParagraph = doc.getParagraphElement(start);
            if (currentParagraph.getStartOffset() == start || dot != mark) {
                // Get the attributes from the character at the selection
                // if in a different paragrah!
                run = doc.getCharacterElement(start);
            }
            else {
                run = doc.getCharacterElement(Math.max(start-1, 0));
            }
            if (run != currentRun) {
                    /*
                     * PENDING(prinz) All attributes that represent a single
                     * glyph position and can't be inserted into should be
                     * removed from the input attributes... this requires
                     * mixing in an interface to indicate that condition.
                     * When we can add things again this logic needs to be
                     * improved!!
                     * <p>
                     *  PENDING(prinz)表示单个字形位置并且不能插入的所有属性应该从输入属性中删除...这需要在接口中混合以指示该条件。当我们可以再添加东西这个逻辑需要改进！
                     * 
                     */
                currentRun = run;
                createInputAttributes(currentRun, getInputAttributes());
            }
        }

        public void propertyChange(PropertyChangeEvent evt) {
            Object newValue = evt.getNewValue();
            Object source = evt.getSource();

            if ((source instanceof JTextComponent) &&
                (newValue instanceof Document)) {
                // New document will have changed selection to 0,0.
                updateInputAttributes(0, 0, (JTextComponent)source);
            }
        }

        public void caretUpdate(CaretEvent e) {
            updateInputAttributes(e.getDot(), e.getMark(),
                                  (JTextComponent)e.getSource());
        }
    }

    /**
     * Copies the key/values in <code>element</code>s AttributeSet into
     * <code>set</code>. This does not copy component, icon, or element
     * names attributes. Subclasses may wish to refine what is and what
     * isn't copied here. But be sure to first remove all the attributes that
     * are in <code>set</code>.<p>
     * This is called anytime the caret moves over a different location.
     *
     * <p>
     * 将<code> element </code>的AttributeSet中的键/值复制到<code> set </code>中。这不会复制组件,图标或元素名称属性。
     * 子类可能希望细化什么是什么,什么是不复制的。但是请务必先删除<code> set </code>中的所有属性。<p>这是在插入符移动到其他位置时调用的。
     * 
     */
    protected void createInputAttributes(Element element,
                                         MutableAttributeSet set) {
        if (element.getAttributes().getAttributeCount() > 0
            || element.getEndOffset() - element.getStartOffset() > 1
            || element.getEndOffset() < element.getDocument().getLength()) {
            set.removeAttributes(set);
            set.addAttributes(element.getAttributes());
            set.removeAttribute(StyleConstants.ComponentAttribute);
            set.removeAttribute(StyleConstants.IconAttribute);
            set.removeAttribute(AbstractDocument.ElementNameAttribute);
            set.removeAttribute(StyleConstants.ComposedTextAttribute);
        }
    }

    // ---- default ViewFactory implementation ---------------------

    static class StyledViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }

    }

    // --- Action implementations ---------------------------------

    private static final Action[] defaultActions = {
        new FontFamilyAction("font-family-SansSerif", "SansSerif"),
        new FontFamilyAction("font-family-Monospaced", "Monospaced"),
        new FontFamilyAction("font-family-Serif", "Serif"),
        new FontSizeAction("font-size-8", 8),
        new FontSizeAction("font-size-10", 10),
        new FontSizeAction("font-size-12", 12),
        new FontSizeAction("font-size-14", 14),
        new FontSizeAction("font-size-16", 16),
        new FontSizeAction("font-size-18", 18),
        new FontSizeAction("font-size-24", 24),
        new FontSizeAction("font-size-36", 36),
        new FontSizeAction("font-size-48", 48),
        new AlignmentAction("left-justify", StyleConstants.ALIGN_LEFT),
        new AlignmentAction("center-justify", StyleConstants.ALIGN_CENTER),
        new AlignmentAction("right-justify", StyleConstants.ALIGN_RIGHT),
        new BoldAction(),
        new ItalicAction(),
        new StyledInsertBreakAction(),
        new UnderlineAction()
    };

    /**
     * An action that assumes it's being fired on a JEditorPane
     * with a StyledEditorKit (or subclass) installed.  This has
     * some convenience methods for causing character or paragraph
     * level attribute changes.  The convenience methods will
     * throw an IllegalArgumentException if the assumption of
     * a StyledDocument, a JEditorPane, or a StyledEditorKit
     * fail to be true.
     * <p>
     * The component that gets acted upon by the action
     * will be the source of the ActionEvent if the source
     * can be narrowed to a JEditorPane type.  If the source
     * can't be narrowed, the most recently focused text
     * component is changed.  If neither of these are the
     * case, the action cannot be performed.
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
     *  一个动作,假设它是在安装了StyledEditorKit(或子类)的JEditorPane上触发的。这有一些方便的方法来引起字符或段落级属性更改。
     * 如果一个StyledDocument,一个JEditorPane或一个StyledEditorKit的假设不成立,方便方法将抛出IllegalArgumentException异常。
     * <p>
     *  如果源可以缩小到JEditorPane类型,那么操作处理的组件将是ActionEvent的源。如果源不能缩小,则最近聚焦的文本成分被改变。如果这两种情况都不是这种情况,则无法执行操作。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public abstract static class StyledTextAction extends TextAction {

        /**
         * Creates a new StyledTextAction from a string action name.
         *
         * <p>
         *  从字符串操作名称创建新的StyledTextAction。
         * 
         * 
         * @param nm the name of the action
         */
        public StyledTextAction(String nm) {
            super(nm);
        }

        /**
         * Gets the target editor for an action.
         *
         * <p>
         * 获取操作的目标编辑器。
         * 
         * 
         * @param e the action event
         * @return the editor
         */
        protected final JEditorPane getEditor(ActionEvent e) {
            JTextComponent tcomp = getTextComponent(e);
            if (tcomp instanceof JEditorPane) {
                return (JEditorPane) tcomp;
            }
            return null;
        }

        /**
         * Gets the document associated with an editor pane.
         *
         * <p>
         *  获取与编辑器窗格关联的文档。
         * 
         * 
         * @param e the editor
         * @return the document
         * @exception IllegalArgumentException for the wrong document type
         */
        protected final StyledDocument getStyledDocument(JEditorPane e) {
            Document d = e.getDocument();
            if (d instanceof StyledDocument) {
                return (StyledDocument) d;
            }
            throw new IllegalArgumentException("document must be StyledDocument");
        }

        /**
         * Gets the editor kit associated with an editor pane.
         *
         * <p>
         *  获取与编辑器窗格相关联的编辑器工具包。
         * 
         * 
         * @param e the editor pane
         * @return the kit
         * @exception IllegalArgumentException for the wrong document type
         */
        protected final StyledEditorKit getStyledEditorKit(JEditorPane e) {
            EditorKit k = e.getEditorKit();
            if (k instanceof StyledEditorKit) {
                return (StyledEditorKit) k;
            }
            throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
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
         * @param editor the editor
         * @param attr the attributes
         * @param replace   if true, then replace the existing attributes first
         */
        protected final void setCharacterAttributes(JEditorPane editor,
                                              AttributeSet attr, boolean replace) {
            int p0 = editor.getSelectionStart();
            int p1 = editor.getSelectionEnd();
            if (p0 != p1) {
                StyledDocument doc = getStyledDocument(editor);
                doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
            }
            StyledEditorKit k = getStyledEditorKit(editor);
            MutableAttributeSet inputAttributes = k.getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }

        /**
         * Applies the given attributes to paragraphs.  If
         * there is a selection, the attributes are applied
         * to the paragraphs that intersect the selection.
         * if there is no selection, the attributes are applied
         * to the paragraph at the current caret position.
         *
         * <p>
         *  将给定的属性应用于段落。如果存在选择,则属性应用于与选择相交的段落。如果没有选择,则将属性应用于当前插入符位置处的段落。
         * 
         * 
         * @param editor the editor
         * @param attr the attributes
         * @param replace   if true, replace the existing attributes first
         */
        protected final void setParagraphAttributes(JEditorPane editor,
                                           AttributeSet attr, boolean replace) {
            int p0 = editor.getSelectionStart();
            int p1 = editor.getSelectionEnd();
            StyledDocument doc = getStyledDocument(editor);
            doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
        }

    }

    /**
     * An action to set the font family in the associated
     * JEditorPane.  This will use the family specified as
     * the command string on the ActionEvent if there is one,
     * otherwise the family that was initialized with will be used.
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
     *  在关联的JEditorPane中设置字体系列的操作。这将使用指定为ActionEvent上的命令字符串的族,如果有一个,否则将使用初始化的族。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class FontFamilyAction extends StyledTextAction {

        /**
         * Creates a new FontFamilyAction.
         *
         * <p>
         *  创建一个新的FontFamilyAction。
         * 
         * 
         * @param nm the action name
         * @param family the font family
         */
        public FontFamilyAction(String nm, String family) {
            super(nm);
            this.family = family;
        }

        /**
         * Sets the font family.
         *
         * <p>
         *  设置字体系列。
         * 
         * 
         * @param e the event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                String family = this.family;
                if ((e != null) && (e.getSource() == editor)) {
                    String s = e.getActionCommand();
                    if (s != null) {
                        family = s;
                    }
                }
                if (family != null) {
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setFontFamily(attr, family);
                    setCharacterAttributes(editor, attr, false);
                } else {
                    UIManager.getLookAndFeel().provideErrorFeedback(editor);
                }
            }
        }

        private String family;
    }

    /**
     * An action to set the font size in the associated
     * JEditorPane.  This will use the size specified as
     * the command string on the ActionEvent if there is one,
     * otherwise the size that was initialized with will be used.
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
     * 在关联的JEditorPane中设置字体大小的操作。这将使用在ActionEvent上指定为命令字符串的大小(如果有),否则将使用初始化的大小。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class FontSizeAction extends StyledTextAction {

        /**
         * Creates a new FontSizeAction.
         *
         * <p>
         *  创建一个新的FontSizeAction。
         * 
         * 
         * @param nm the action name
         * @param size the font size
         */
        public FontSizeAction(String nm, int size) {
            super(nm);
            this.size = size;
        }

        /**
         * Sets the font size.
         *
         * <p>
         *  设置字体大小。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                int size = this.size;
                if ((e != null) && (e.getSource() == editor)) {
                    String s = e.getActionCommand();
                    try {
                        size = Integer.parseInt(s, 10);
                    } catch (NumberFormatException nfe) {
                    }
                }
                if (size != 0) {
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setFontSize(attr, size);
                    setCharacterAttributes(editor, attr, false);
                } else {
                    UIManager.getLookAndFeel().provideErrorFeedback(editor);
                }
            }
        }

        private int size;
    }

    /**
     * An action to set foreground color.  This sets the
     * <code>StyleConstants.Foreground</code> attribute for the
     * currently selected range of the target JEditorPane.
     * This is done by calling
     * <code>StyledDocument.setCharacterAttributes</code>
     * on the styled document associated with the target
     * JEditorPane.
     * <p>
     * If the target text component is specified as the
     * source of the ActionEvent and there is a command string,
     * the command string will be interpreted as the foreground
     * color.  It will be interpreted by called
     * <code>Color.decode</code>, and should therefore be
     * legal input for that method.
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
     *  设置前景色的操作。这将为目标JEditorPane的当前选定范围设置<code> StyleConstants.Foreground </code>属性。
     * 这通过在与目标JEditorPane相关联的样式文档上调用<code> StyledDocument.setCharacterAttributes </code>来完成。
     * <p>
     *  如果目标文本组件被指定为ActionEvent的源并且有命令字符串,则命令字符串将被解释为前景颜色。
     * 它将通过调用<code> Color.decode </code>来解释,因此应该是该方法的合法输入。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class ForegroundAction extends StyledTextAction {

        /**
         * Creates a new ForegroundAction.
         *
         * <p>
         *  创建一个新的ForegroundAction。
         * 
         * 
         * @param nm the action name
         * @param fg the foreground color
         */
        public ForegroundAction(String nm, Color fg) {
            super(nm);
            this.fg = fg;
        }

        /**
         * Sets the foreground color.
         *
         * <p>
         *  设置前景颜色。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                Color fg = this.fg;
                if ((e != null) && (e.getSource() == editor)) {
                    String s = e.getActionCommand();
                    try {
                        fg = Color.decode(s);
                    } catch (NumberFormatException nfe) {
                    }
                }
                if (fg != null) {
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setForeground(attr, fg);
                    setCharacterAttributes(editor, attr, false);
                } else {
                    UIManager.getLookAndFeel().provideErrorFeedback(editor);
                }
            }
        }

        private Color fg;
    }

    /**
     * An action to set paragraph alignment.  This sets the
     * <code>StyleConstants.Alignment</code> attribute for the
     * currently selected range of the target JEditorPane.
     * This is done by calling
     * <code>StyledDocument.setParagraphAttributes</code>
     * on the styled document associated with the target
     * JEditorPane.
     * <p>
     * If the target text component is specified as the
     * source of the ActionEvent and there is a command string,
     * the command string will be interpreted as an integer
     * that should be one of the legal values for the
     * <code>StyleConstants.Alignment</code> attribute.
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
     *  设置段落对齐的操作。这将为目标JEditorPane的当前选定范围设置<code> StyleConstants.Alignment </code>属性。
     * 这通过在与目标JEditorPane相关联的样式文档上调用<code> StyledDocument.setParagraphAttributes </code>来完成。
     * <p>
     *  如果目标文本组件被指定为ActionEvent的源并且有一个命令字符串,则命令字符串将被解释为一个整数,该整数应该是<code> StyleConstants.Alignment </code>属性的
     * 合法值之一。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class AlignmentAction extends StyledTextAction {

        /**
         * Creates a new AlignmentAction.
         *
         * <p>
         *  创建一个新的AlignmentAction。
         * 
         * 
         * @param nm the action name
         * @param a the alignment &gt;= 0
         */
        public AlignmentAction(String nm, int a) {
            super(nm);
            this.a = a;
        }

        /**
         * Sets the alignment.
         *
         * <p>
         *  设置对齐方式。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                int a = this.a;
                if ((e != null) && (e.getSource() == editor)) {
                    String s = e.getActionCommand();
                    try {
                        a = Integer.parseInt(s, 10);
                    } catch (NumberFormatException nfe) {
                    }
                }
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setAlignment(attr, a);
                setParagraphAttributes(editor, attr, false);
            }
        }

        private int a;
    }

    /**
     * An action to toggle the bold attribute.
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
     * 用于切换粗体属性的操作。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class BoldAction extends StyledTextAction {

        /**
         * Constructs a new BoldAction.
         * <p>
         *  构造一个新的BoldAction。
         * 
         */
        public BoldAction() {
            super("font-bold");
        }

        /**
         * Toggles the bold attribute.
         *
         * <p>
         *  切换粗体属性。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean bold = (StyleConstants.isBold(attr)) ? false : true;
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setBold(sas, bold);
                setCharacterAttributes(editor, sas, false);
            }
        }
    }

    /**
     * An action to toggle the italic attribute.
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
     *  用于切换斜体属性的操作。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class ItalicAction extends StyledTextAction {

        /**
         * Constructs a new ItalicAction.
         * <p>
         *  构造一个新的ItalicAction。
         * 
         */
        public ItalicAction() {
            super("font-italic");
        }

        /**
         * Toggles the italic attribute.
         *
         * <p>
         *  切换斜体属性。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setItalic(sas, italic);
                setCharacterAttributes(editor, sas, false);
            }
        }
    }

    /**
     * An action to toggle the underline attribute.
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
     *  切换下划线属性的操作。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class UnderlineAction extends StyledTextAction {

        /**
         * Constructs a new UnderlineAction.
         * <p>
         * 构造一个新的UnderlineAction。
         * 
         */
        public UnderlineAction() {
            super("font-underline");
        }

        /**
         * Toggles the Underline attribute.
         *
         * <p>
         *  切换下划线属性。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JEditorPane editor = getEditor(e);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setUnderline(sas, underline);
                setCharacterAttributes(editor, sas, false);
            }
        }
    }


    /**
     * StyledInsertBreakAction has similar behavior to that of
     * <code>DefaultEditorKit.InsertBreakAction</code>. That is when
     * its <code>actionPerformed</code> method is invoked, a newline
     * is inserted. Beyond that, this will reset the input attributes to
     * what they were before the newline was inserted.
     * <p>
     *  StyledInsertBreakAction与<code> DefaultEditorKit.InsertBreakAction </code>的行为类似。
     * 这就是当它的<code> actionPerformed </code>方法被调用时,插入一个换行符。除此之外,这会将输入属性重置为插入换行符之前的内容。
     */
    static class StyledInsertBreakAction extends StyledTextAction {
        private SimpleAttributeSet tempSet;

        StyledInsertBreakAction() {
            super(insertBreakAction);
        }

        public void actionPerformed(ActionEvent e) {
            JEditorPane target = getEditor(e);

            if (target != null) {
                if ((!target.isEditable()) || (!target.isEnabled())) {
                    UIManager.getLookAndFeel().provideErrorFeedback(target);
                    return;
                }
                StyledEditorKit sek = getStyledEditorKit(target);

                if (tempSet != null) {
                    tempSet.removeAttributes(tempSet);
                }
                else {
                    tempSet = new SimpleAttributeSet();
                }
                tempSet.addAttributes(sek.getInputAttributes());
                target.replaceSelection("\n");

                MutableAttributeSet ia = sek.getInputAttributes();

                ia.removeAttributes(ia);
                ia.addAttributes(tempSet);
                tempSet.removeAttributes(tempSet);
            }
            else {
                // See if we are in a JTextComponent.
                JTextComponent text = getTextComponent(e);

                if (text != null) {
                    if ((!text.isEditable()) || (!text.isEnabled())) {
                        UIManager.getLookAndFeel().provideErrorFeedback(target);
                        return;
                    }
                    text.replaceSelection("\n");
                }
            }
        }
    }
}
