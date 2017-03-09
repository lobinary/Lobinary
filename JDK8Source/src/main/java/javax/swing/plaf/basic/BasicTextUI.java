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
package javax.swing.plaf.basic;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.im.InputContext;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.SynthUI;
import sun.swing.DefaultLookup;
import sun.awt.AppContext;
import javax.swing.plaf.basic.DragRecognitionSupport.BeforeDrag;

/**
 * <p>
 * Basis of a text components look-and-feel.  This provides the
 * basic editor view and controller services that may be useful
 * when creating a look-and-feel for an extension of
 * <code>JTextComponent</code>.
 * <p>
 * Most state is held in the associated <code>JTextComponent</code>
 * as bound properties, and the UI installs default values for the
 * various properties.  This default will install something for
 * all of the properties.  Typically, a LAF implementation will
 * do more however.  At a minimum, a LAF would generally install
 * key bindings.
 * <p>
 * This class also provides some concurrency support if the
 * <code>Document</code> associated with the JTextComponent is a subclass of
 * <code>AbstractDocument</code>.  Access to the View (or View hierarchy) is
 * serialized between any thread mutating the model and the Swing
 * event thread (which is expected to render, do model/view coordinate
 * translation, etc).  <em>Any access to the root view should first
 * acquire a read-lock on the AbstractDocument and release that lock
 * in a finally block.</em>
 * <p>
 * An important method to define is the {@link #getPropertyPrefix} method
 * which is used as the basis of the keys used to fetch defaults
 * from the UIManager.  The string should reflect the type of
 * TextUI (eg. TextField, TextArea, etc) without the particular
 * LAF part of the name (eg Metal, Motif, etc).
 * <p>
 * To build a view of the model, one of the following strategies
 * can be employed.
 * <ol>
 * <li>
 * One strategy is to simply redefine the
 * ViewFactory interface in the UI.  By default, this UI itself acts
 * as the factory for View implementations.  This is useful
 * for simple factories.  To do this reimplement the
 * {@link #create} method.
 * <li>
 * A common strategy for creating more complex types of documents
 * is to have the EditorKit implementation return a factory.  Since
 * the EditorKit ties all of the pieces necessary to maintain a type
 * of document, the factory is typically an important part of that
 * and should be produced by the EditorKit implementation.
 * </ol>
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
 * <p>
 * <p>
 *  文本组件外观的基础。这提供了在为<code> JTextComponent </code>的扩展创建外观时可能有用的基本编辑器视图和控制器服务。
 * <p>
 *  大多数状态都保存在关联的<code> JTextComponent </code>中作为绑定属性,UI会为各种属性安装默认值。这个默认值将为所有属性安装一些东西。通常,LAF实现将做更多。
 * 至少,LAF通常安装密钥绑定。
 * <p>
 *  如果与JTextComponent相关联的<code> Document </code>是<code> AbstractDocument </code>的子类,则此类还提供了一些并发支持。
 * 对任何改变模型的线程和Swing事件线程(期望呈现,执行模型/视图坐标翻译等)之间的View(或View层次结构)的访问被序列化。
 *  <em>对根视图的任何访问应首先获取AbstractDocument的读锁,并在finally块中释放该锁。</em>。
 * <p>
 *  定义的一个重要方法是{@link #getPropertyPrefix}方法,该方法用作用于从UIManager获取默认值的键的基础。
 * 字符串应该反映TextUI的类型(例如,TextField,TextArea等),而没有名称的特定LAF部分(例如Metal,Motif等)。
 * <p>
 * 为了构建模型的视图,可以采用以下策略之一。
 * <ol>
 * <li>
 *  一个策略是简单地在UI中重新定义ViewFactory接口。默认情况下,此UI本身作为View实现的工厂。这对简单的工厂很有用。为此,请重新实现{@link #create}方法。
 * <li>
 *  创建更复杂类型的文档的常见策略是使EditorKit实现返回工厂。因为EditorKit绑定了维护一个文档类型所需的所有部分,所以工厂通常是其中的重要组成部分,应该由EditorKit实现生成。
 * </ol>
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Timothy Prinzing
 * @author Shannon Hickey (drag and drop)
 */
public abstract class BasicTextUI extends TextUI implements ViewFactory {

    /**
     * Creates a new UI.
     * <p>
     *  创建新的UI。
     * 
     */
    public BasicTextUI() {
        painted = false;
    }

    /**
     * Creates the object to use for a caret.  By default an
     * instance of BasicCaret is created.  This method
     * can be redefined to provide something else that implements
     * the InputPosition interface or a subclass of JCaret.
     *
     * <p>
     *  创建用于插入符的对象。默认情况下创建BasicCaret的实例。此方法可以重新定义,以提供其他实现InputPosition接口或JCaret的子类。
     * 
     * 
     * @return the caret object
     */
    protected Caret createCaret() {
        return new BasicCaret();
    }

    /**
     * Creates the object to use for adding highlights.  By default
     * an instance of BasicHighlighter is created.  This method
     * can be redefined to provide something else that implements
     * the Highlighter interface or a subclass of DefaultHighlighter.
     *
     * <p>
     * 创建用于添加高光的对象。默认情况下创建BasicHighlighter的实例。此方法可以重新定义,以提供其他实现Highlighter界面或DefaultHighlighter的子类。
     * 
     * 
     * @return the highlighter
     */
    protected Highlighter createHighlighter() {
        return new BasicHighlighter();
    }

    /**
     * Fetches the name of the keymap that will be installed/used
     * by default for this UI. This is implemented to create a
     * name based upon the classname.  The name is the the name
     * of the class with the package prefix removed.
     *
     * <p>
     *  获取将为此UI默认安装/使用的键映射的名称。这是实现基于类名创建一个名称。名称是删除了包前缀的类的名称。
     * 
     * 
     * @return the name
     */
    protected String getKeymapName() {
        String nm = getClass().getName();
        int index = nm.lastIndexOf('.');
        if (index >= 0) {
            nm = nm.substring(index+1, nm.length());
        }
        return nm;
    }

    /**
     * Creates the keymap to use for the text component, and installs
     * any necessary bindings into it.  By default, the keymap is
     * shared between all instances of this type of TextUI. The
     * keymap has the name defined by the getKeymapName method.  If the
     * keymap is not found, then DEFAULT_KEYMAP from JTextComponent is used.
     * <p>
     * The set of bindings used to create the keymap is fetched
     * from the UIManager using a key formed by combining the
     * {@link #getPropertyPrefix} method
     * and the string <code>.keyBindings</code>.  The type is expected
     * to be <code>JTextComponent.KeyBinding[]</code>.
     *
     * <p>
     *  创建用于文本组件的键映射,并在其中安装任何必需的绑定。默认情况下,键盘映射在此类型的TextUI的所有实例之间共享。键映射具有由getKeymapName方法定义的名称。
     * 如果未找到键盘映射,则使用来自JTextComponent的DEFAULT_KEYMAP。
     * <p>
     *  用于创建键映射的绑定集合是使用通过组合{@link #getPropertyPrefix}方法和字符串<code> .keyBindings </code>形成的键从UIManager获取的。
     * 类型应为<code> JTextComponent.KeyBinding [] </code>。
     * 
     * 
     * @return the keymap
     * @see #getKeymapName
     * @see javax.swing.text.JTextComponent
     */
    protected Keymap createKeymap() {
        String nm = getKeymapName();
        Keymap map = JTextComponent.getKeymap(nm);
        if (map == null) {
            Keymap parent = JTextComponent.getKeymap(JTextComponent.DEFAULT_KEYMAP);
            map = JTextComponent.addKeymap(nm, parent);
            String prefix = getPropertyPrefix();
            Object o = DefaultLookup.get(editor, this,
                prefix + ".keyBindings");
            if ((o != null) && (o instanceof JTextComponent.KeyBinding[])) {
                JTextComponent.KeyBinding[] bindings = (JTextComponent.KeyBinding[]) o;
                JTextComponent.loadKeymap(map, bindings, getComponent().getActions());
            }
        }
        return map;
    }

    /**
     * This method gets called when a bound property is changed
     * on the associated JTextComponent.  This is a hook
     * which UI implementations may change to reflect how the
     * UI displays bound properties of JTextComponent subclasses.
     * This is implemented to do nothing (i.e. the response to
     * properties in JTextComponent itself are handled prior
     * to calling this method).
     *
     * This implementation updates the background of the text
     * component if the editable and/or enabled state changes.
     *
     * <p>
     *  当在相关联的JTextComponent上更改绑定属性时,将调用此方法。这是一个钩子,UI实现可以改变以反映UI如何显示JTextComponent子类的绑定属性。
     * 这被实现为不执行任何操作(即,在调用此方法之前处理对JTextComponent本身中的属性的响应)。
     * 
     * 如果可编辑和/或启用状态改变,则该实现更新文本组件的背景。
     * 
     * 
     * @param evt the property change event
     */
    protected void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("editable") ||
                evt.getPropertyName().equals("enabled")) {

            updateBackground((JTextComponent)evt.getSource());
        }
    }

    /**
     * Updates the background of the text component based on whether the
     * text component is editable and/or enabled.
     *
     * <p>
     *  根据文本组件是否可编辑和/或启用更新文本组件的背景。
     * 
     * 
     * @param c the JTextComponent that needs its background color updated
     */
    private void updateBackground(JTextComponent c) {
        // This is a temporary workaround.
        // This code does not correctly deal with Synth (Synth doesn't use
        // properties like this), nor does it deal with the situation where
        // the developer grabs the color from a JLabel and sets it as
        // the background for a JTextArea in all look and feels. The problem
        // scenario results if the Color obtained for the Label and TextArea
        // is ==, which is the case for the windows look and feel.
        // Until an appropriate solution is found, the code is being
        // reverted to what it was before the original fix.
        if (this instanceof SynthUI || (c instanceof JTextArea)) {
            return;
        }
        Color background = c.getBackground();
        if (background instanceof UIResource) {
            String prefix = getPropertyPrefix();

            Color disabledBG =
                DefaultLookup.getColor(c, this, prefix + ".disabledBackground", null);
            Color inactiveBG =
                DefaultLookup.getColor(c, this, prefix + ".inactiveBackground", null);
            Color bg =
                DefaultLookup.getColor(c, this, prefix + ".background", null);

            /* In an ideal situation, the following check would not be necessary
             * and we would replace the color any time the previous color was a
             * UIResouce. However, it turns out that there is existing code that
             * uses the following inadvisable pattern to turn a text area into
             * what appears to be a multi-line label:
             *
             * JLabel label = new JLabel();
             * JTextArea area = new JTextArea();
             * area.setBackground(label.getBackground());
             * area.setEditable(false);
             *
             * JLabel's default background is a UIResource. As such, just
             * checking for UIResource would have us always changing the
             * background away from what the developer wanted.
             *
             * Therefore, for JTextArea/JEditorPane, we'll additionally check
             * that the color we're about to replace matches one that was
             * installed by us from the UIDefaults.
             * <p>
             *  并且我们将在以前的颜色是UIResouce时替换颜色。然而,事实证明,现有的代码使用以下不合适的模式将文本区域转换为似乎是多行标签：
             * 
             *  JLabel label = new JLabel(); JTextArea area = new JTextArea(); area.setBackground(label.getBackgroun
             * d()); area.setEditable(false);。
             * 
             *  JLabel的默认背景是一个UIResource。因此,只是检查UIResource会让我们总是改变背景从开发人员想要的。
             * 
             *  因此,对于JTextArea / JEditorPane,我们还要检查我们要替换的颜色是否与我们从UIDefaults安装的颜色匹配。
             * 
             */
            if ((c instanceof JTextArea || c instanceof JEditorPane)
                    && background != disabledBG
                    && background != inactiveBG
                    && background != bg) {

                return;
            }

            Color newColor = null;
            if (!c.isEnabled()) {
                newColor = disabledBG;
            }
            if (newColor == null && !c.isEditable()) {
                newColor = inactiveBG;
            }
            if (newColor == null) {
                newColor = bg;
            }
            if (newColor != null && newColor != background) {
                c.setBackground(newColor);
            }
        }
    }

    /**
     * Gets the name used as a key to look up properties through the
     * UIManager.  This is used as a prefix to all the standard
     * text properties.
     *
     * <p>
     *  获取用作通过UIManager查找属性的键的名称。这用作所有标准文本属性的前缀。
     * 
     * 
     * @return the name
     */
    protected abstract String getPropertyPrefix();

    /**
     * Initializes component properties, such as font, foreground,
     * background, caret color, selection color, selected text color,
     * disabled text color, and border color.  The font, foreground, and
     * background properties are only set if their current value is either null
     * or a UIResource, other properties are set if the current
     * value is null.
     *
     * <p>
     *  初始化组件属性,例如字体,前景,背景,插入符号颜色,选择颜色,选定的文本颜色,禁用的文本颜色和边框颜色。
     * 仅当字体,前景和背景属性的当前值为null或UIResource时,才设置它们,如果当前值为null,则设置其他属性。
     * 
     * 
     * @see #uninstallDefaults
     * @see #installUI
     */
    protected void installDefaults()
    {
        String prefix = getPropertyPrefix();
        Font f = editor.getFont();
        if ((f == null) || (f instanceof UIResource)) {
            editor.setFont(UIManager.getFont(prefix + ".font"));
        }

        Color bg = editor.getBackground();
        if ((bg == null) || (bg instanceof UIResource)) {
            editor.setBackground(UIManager.getColor(prefix + ".background"));
        }

        Color fg = editor.getForeground();
        if ((fg == null) || (fg instanceof UIResource)) {
            editor.setForeground(UIManager.getColor(prefix + ".foreground"));
        }

        Color color = editor.getCaretColor();
        if ((color == null) || (color instanceof UIResource)) {
            editor.setCaretColor(UIManager.getColor(prefix + ".caretForeground"));
        }

        Color s = editor.getSelectionColor();
        if ((s == null) || (s instanceof UIResource)) {
            editor.setSelectionColor(UIManager.getColor(prefix + ".selectionBackground"));
        }

        Color sfg = editor.getSelectedTextColor();
        if ((sfg == null) || (sfg instanceof UIResource)) {
            editor.setSelectedTextColor(UIManager.getColor(prefix + ".selectionForeground"));
        }

        Color dfg = editor.getDisabledTextColor();
        if ((dfg == null) || (dfg instanceof UIResource)) {
            editor.setDisabledTextColor(UIManager.getColor(prefix + ".inactiveForeground"));
        }

        Border b = editor.getBorder();
        if ((b == null) || (b instanceof UIResource)) {
            editor.setBorder(UIManager.getBorder(prefix + ".border"));
        }

        Insets margin = editor.getMargin();
        if (margin == null || margin instanceof UIResource) {
            editor.setMargin(UIManager.getInsets(prefix + ".margin"));
        }

        updateCursor();
    }

    private void installDefaults2() {
        editor.addMouseListener(dragListener);
        editor.addMouseMotionListener(dragListener);

        String prefix = getPropertyPrefix();

        Caret caret = editor.getCaret();
        if (caret == null || caret instanceof UIResource) {
            caret = createCaret();
            editor.setCaret(caret);

            int rate = DefaultLookup.getInt(getComponent(), this, prefix + ".caretBlinkRate", 500);
            caret.setBlinkRate(rate);
        }

        Highlighter highlighter = editor.getHighlighter();
        if (highlighter == null || highlighter instanceof UIResource) {
            editor.setHighlighter(createHighlighter());
        }

        TransferHandler th = editor.getTransferHandler();
        if (th == null || th instanceof UIResource) {
            editor.setTransferHandler(getTransferHandler());
        }
    }

    /**
     * Sets the component properties that have not been explicitly overridden
     * to {@code null}.  A property is considered overridden if its current
     * value is not a {@code UIResource}.
     *
     * <p>
     * 将尚未显式覆盖的组件属性设置为{@code null}。如果某个属性的当前值不是{@code UIResource},则会被视为覆盖。
     * 
     * 
     * @see #installDefaults
     * @see #uninstallUI
     */
    protected void uninstallDefaults()
    {
        editor.removeMouseListener(dragListener);
        editor.removeMouseMotionListener(dragListener);

        if (editor.getCaretColor() instanceof UIResource) {
            editor.setCaretColor(null);
        }

        if (editor.getSelectionColor() instanceof UIResource) {
            editor.setSelectionColor(null);
        }

        if (editor.getDisabledTextColor() instanceof UIResource) {
            editor.setDisabledTextColor(null);
        }

        if (editor.getSelectedTextColor() instanceof UIResource) {
            editor.setSelectedTextColor(null);
        }

        if (editor.getBorder() instanceof UIResource) {
            editor.setBorder(null);
        }

        if (editor.getMargin() instanceof UIResource) {
            editor.setMargin(null);
        }

        if (editor.getCaret() instanceof UIResource) {
            editor.setCaret(null);
        }

        if (editor.getHighlighter() instanceof UIResource) {
            editor.setHighlighter(null);
        }

        if (editor.getTransferHandler() instanceof UIResource) {
            editor.setTransferHandler(null);
        }

        if (editor.getCursor() instanceof UIResource) {
            editor.setCursor(null);
        }
    }

    /**
     * Installs listeners for the UI.
     * <p>
     *  为UI安装侦听器。
     * 
     */
    protected void installListeners() {
    }

    /**
     * Uninstalls listeners for the UI.
     * <p>
     *  卸载UI的侦听器。
     * 
     */
    protected void uninstallListeners() {
    }

    protected void installKeyboardActions() {
        // backward compatibility support... keymaps for the UI
        // are now installed in the more friendly input map.
        editor.setKeymap(createKeymap());

        InputMap km = getInputMap();
        if (km != null) {
            SwingUtilities.replaceUIInputMap(editor, JComponent.WHEN_FOCUSED,
                                             km);
        }

        ActionMap map = getActionMap();
        if (map != null) {
            SwingUtilities.replaceUIActionMap(editor, map);
        }

        updateFocusAcceleratorBinding(false);
    }

    /**
     * Get the InputMap to use for the UI.
     * <p>
     *  获取要用于UI的InputMap。
     * 
     */
    InputMap getInputMap() {
        InputMap map = new InputMapUIResource();

        InputMap shared =
            (InputMap)DefaultLookup.get(editor, this,
            getPropertyPrefix() + ".focusInputMap");
        if (shared != null) {
            map.setParent(shared);
        }
        return map;
    }

    /**
     * Invoked when the focus accelerator changes, this will update the
     * key bindings as necessary.
     * <p>
     *  当焦点加速器更改时调用,这将根据需要更新键绑定。
     * 
     */
    void updateFocusAcceleratorBinding(boolean changed) {
        char accelerator = editor.getFocusAccelerator();

        if (changed || accelerator != '\0') {
            InputMap km = SwingUtilities.getUIInputMap
                        (editor, JComponent.WHEN_IN_FOCUSED_WINDOW);

            if (km == null && accelerator != '\0') {
                km = new ComponentInputMapUIResource(editor);
                SwingUtilities.replaceUIInputMap(editor, JComponent.
                                                 WHEN_IN_FOCUSED_WINDOW, km);
                ActionMap am = getActionMap();
                SwingUtilities.replaceUIActionMap(editor, am);
            }
            if (km != null) {
                km.clear();
                if (accelerator != '\0') {
                    km.put(KeyStroke.getKeyStroke(accelerator, BasicLookAndFeel.getFocusAcceleratorKeyMask()), "requestFocus");
                }
            }
        }
    }


    /**
     * Invoked when editable property is changed.
     *
     * removing 'TAB' and 'SHIFT-TAB' from traversalKeysSet in case
     * editor is editable
     * adding 'TAB' and 'SHIFT-TAB' to traversalKeysSet in case
     * editor is non editable
     * <p>
     *  在可编辑属性更改时调用。
     * 
     *  在编辑器可编辑的情况下从traversalKeysSet中删除'TAB'和'SHIFT-TAB',在编辑器不可编辑的情况下将TAB和SHIFT-TAB添加到traversalKeysSet
     * 
     */

    void updateFocusTraversalKeys() {
        /*
         * Fix for 4514331 Non-editable JTextArea and similar
         * should allow Tab to keyboard - accessibility
         * <p>
         *  修复4514331不可编辑的JTextArea和类似应该允许Tab键盘 - 辅助功能
         * 
         */
        EditorKit editorKit = getEditorKit(editor);
        if ( editorKit != null
             && editorKit instanceof DefaultEditorKit) {
            Set<AWTKeyStroke> storedForwardTraversalKeys = editor.
                getFocusTraversalKeys(KeyboardFocusManager.
                                      FORWARD_TRAVERSAL_KEYS);
            Set<AWTKeyStroke> storedBackwardTraversalKeys = editor.
                getFocusTraversalKeys(KeyboardFocusManager.
                                      BACKWARD_TRAVERSAL_KEYS);
            Set<AWTKeyStroke> forwardTraversalKeys =
                new HashSet<AWTKeyStroke>(storedForwardTraversalKeys);
            Set<AWTKeyStroke> backwardTraversalKeys =
                new HashSet<AWTKeyStroke>(storedBackwardTraversalKeys);
            if (editor.isEditable()) {
                forwardTraversalKeys.
                    remove(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
                backwardTraversalKeys.
                    remove(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,
                                                  InputEvent.SHIFT_MASK));
            } else {
                forwardTraversalKeys.add(KeyStroke.
                                         getKeyStroke(KeyEvent.VK_TAB, 0));
                backwardTraversalKeys.
                    add(KeyStroke.
                        getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK));
            }
            LookAndFeel.installProperty(editor,
                                        "focusTraversalKeysForward",
                                         forwardTraversalKeys);
            LookAndFeel.installProperty(editor,
                                        "focusTraversalKeysBackward",
                                         backwardTraversalKeys);
        }

    }

    /**
     * As needed updates cursor for the target editor.
     * <p>
     *  根据需要更新目标编辑器的游标。
     * 
     */
    private void updateCursor() {
        if ((! editor.isCursorSet())
               || editor.getCursor() instanceof UIResource) {
            Cursor cursor = (editor.isEditable()) ? textCursor : null;
            editor.setCursor(cursor);
        }
    }

    /**
     * Returns the <code>TransferHandler</code> that will be installed if
     * their isn't one installed on the <code>JTextComponent</code>.
     * <p>
     *  返回如果<code> JTextComponent </code>中没有安装的<code> TransferHandler </code>,它将被安装。
     * 
     */
    TransferHandler getTransferHandler() {
        return defaultTransferHandler;
    }

    /**
     * Fetch an action map to use.
     * <p>
     *  获取要使用的操作映射。
     * 
     */
    ActionMap getActionMap() {
        String mapName = getPropertyPrefix() + ".actionMap";
        ActionMap map = (ActionMap)UIManager.get(mapName);

        if (map == null) {
            map = createActionMap();
            if (map != null) {
                UIManager.getLookAndFeelDefaults().put(mapName, map);
            }
        }
        ActionMap componentMap = new ActionMapUIResource();
        componentMap.put("requestFocus", new FocusAction());
        /*
         * fix for bug 4515750
         * JTextField & non-editable JTextArea bind return key - default btn not accessible
         *
         * Wrap the return action so that it is only enabled when the
         * component is editable. This allows the default button to be
         * processed when the text component has focus and isn't editable.
         *
         * <p>
         *  修复错误4515750 JTextField和不可编辑的JTextArea绑定返回键 - 默认btn不可访问
         * 
         *  包装返回操作,以便仅在组件可编辑时启用。这允许在文本组件具有焦点且不可编辑时处理默认按钮。
         * 
         */
        if (getEditorKit(editor) instanceof DefaultEditorKit) {
            if (map != null) {
                Object obj = map.get(DefaultEditorKit.insertBreakAction);
                if (obj != null
                    && obj instanceof DefaultEditorKit.InsertBreakAction) {
                    Action action =  new TextActionWrapper((TextAction)obj);
                    componentMap.put(action.getValue(Action.NAME),action);
                }
            }
        }
        if (map != null) {
            componentMap.setParent(map);
        }
        return componentMap;
    }

    /**
     * Create a default action map.  This is basically the
     * set of actions found exported by the component.
     * <p>
     *  创建默认操作映射。这基本上是组件导出的一组找到的操作。
     * 
     */
    ActionMap createActionMap() {
        ActionMap map = new ActionMapUIResource();
        Action[] actions = editor.getActions();
        //System.out.println("building map for UI: " + getPropertyPrefix());
        int n = actions.length;
        for (int i = 0; i < n; i++) {
            Action a = actions[i];
            map.put(a.getValue(Action.NAME), a);
            //System.out.println("  " + a.getValue(Action.NAME));
        }
        map.put(TransferHandler.getCutAction().getValue(Action.NAME),
                TransferHandler.getCutAction());
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
                TransferHandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
                TransferHandler.getPasteAction());
        return map;
    }

    protected void uninstallKeyboardActions() {
        editor.setKeymap(null);
        SwingUtilities.replaceUIInputMap(editor, JComponent.
                                         WHEN_IN_FOCUSED_WINDOW, null);
        SwingUtilities.replaceUIActionMap(editor, null);
    }

    /**
     * Paints a background for the view.  This will only be
     * called if isOpaque() on the associated component is
     * true.  The default is to paint the background color
     * of the component.
     *
     * <p>
     *  为视图绘制背景。只有当相关组件上的isOpaque()为true时,才会调用此方法。默认是绘制组件的背景颜色。
     * 
     * 
     * @param g the graphics context
     */
    protected void paintBackground(Graphics g) {
        g.setColor(editor.getBackground());
        g.fillRect(0, 0, editor.getWidth(), editor.getHeight());
    }

    /**
     * Fetches the text component associated with this
     * UI implementation.  This will be null until
     * the ui has been installed.
     *
     * <p>
     * 获取与此UI实现相关联的文本组件。这将是null,直到ui已安装。
     * 
     * 
     * @return the editor component
     */
    protected final JTextComponent getComponent() {
        return editor;
    }

    /**
     * Flags model changes.
     * This is called whenever the model has changed.
     * It is implemented to rebuild the view hierarchy
     * to represent the default root element of the
     * associated model.
     * <p>
     *  标志模型更改。每当模型更改时调用。它被实现为重建视图层次以表示相关联模型的默认根元素。
     * 
     */
    protected void modelChanged() {
        // create a view hierarchy
        ViewFactory f = rootView.getViewFactory();
        Document doc = editor.getDocument();
        Element elem = doc.getDefaultRootElement();
        setView(f.create(elem));
    }

    /**
     * Sets the current root of the view hierarchy and calls invalidate().
     * If there were any child components, they will be removed (i.e.
     * there are assumed to have come from components embedded in views).
     *
     * <p>
     *  设置视图层次结构的当前根,并调用invalidate()。如果有任何子组件,它们将被删除(即,假定来自嵌入在视图中的组件)。
     * 
     * 
     * @param v the root view
     */
    protected final void setView(View v) {
        rootView.setView(v);
        painted = false;
        editor.revalidate();
        editor.repaint();
    }

    /**
     * Paints the interface safely with a guarantee that
     * the model won't change from the view of this thread.
     * This does the following things, rendering from
     * back to front.
     * <ol>
     * <li>
     * If the component is marked as opaque, the background
     * is painted in the current background color of the
     * component.
     * <li>
     * The highlights (if any) are painted.
     * <li>
     * The view hierarchy is painted.
     * <li>
     * The caret is painted.
     * </ol>
     *
     * <p>
     *  安全地绘制接口,并保证模型不会从此线程的视图更改。这做了以下事情,从后面到前面渲染。
     * <ol>
     * <li>
     *  如果组件标记为不透明,则背景将在组件的当前背景颜色中绘制。
     * <li>
     *  亮点(如果有)画。
     * <li>
     *  视图层次结构已绘制。
     * <li>
     *  插入符号被绘。
     * </ol>
     * 
     * 
     * @param g the graphics context
     */
    protected void paintSafely(Graphics g) {
        painted = true;
        Highlighter highlighter = editor.getHighlighter();
        Caret caret = editor.getCaret();

        // paint the background
        if (editor.isOpaque()) {
            paintBackground(g);
        }

        // paint the highlights
        if (highlighter != null) {
            highlighter.paint(g);
        }

        // paint the view hierarchy
        Rectangle alloc = getVisibleEditorRect();
        if (alloc != null) {
            rootView.paint(g, alloc);
        }

        // paint the caret
        if (caret != null) {
            caret.paint(g);
        }

        if (dropCaret != null) {
            dropCaret.paint(g);
        }
    }

    // --- ComponentUI methods --------------------------------------------

    /**
     * Installs the UI for a component.  This does the following
     * things.
     * <ol>
     * <li>
     * Sets the associated component to opaque if the opaque property
     * has not already been set by the client program. This will cause the
     * component's background color to be painted.
     * <li>
     * Installs the default caret and highlighter into the
     * associated component. These properties are only set if their
     * current value is either {@code null} or an instance of
     * {@link UIResource}.
     * <li>
     * Attaches to the editor and model.  If there is no
     * model, a default one is created.
     * <li>
     * Creates the view factory and the view hierarchy used
     * to represent the model.
     * </ol>
     *
     * <p>
     *  安装组件的UI。这做了以下事情。
     * <ol>
     * <li>
     *  如果不透明属性尚未由客户端程序设置,则将相关组件设置为opaque。这将导致组件的背景颜色被绘制。
     * <li>
     *  将默认插入符号和荧光笔安装到关联的组件中。仅当这些属性的当前值为{@code null}或{@link UIResource}的实例时,才设置这些属性。
     * <li>
     *  附加到编辑器和模型。如果没有模型,则创建默认模型。
     * <li>
     *  创建视图工厂和用于表示模型的视图层次结构。
     * </ol>
     * 
     * 
     * @param c the editor component
     * @see ComponentUI#installUI
     */
    public void installUI(JComponent c) {
        if (c instanceof JTextComponent) {
            editor = (JTextComponent) c;

            // common case is background painted... this can
            // easily be changed by subclasses or from outside
            // of the component.
            LookAndFeel.installProperty(editor, "opaque", Boolean.TRUE);
            LookAndFeel.installProperty(editor, "autoscrolls", Boolean.TRUE);

            // install defaults
            installDefaults();
            installDefaults2();

            // attach to the model and editor
            editor.addPropertyChangeListener(updateHandler);
            Document doc = editor.getDocument();
            if (doc == null) {
                // no model, create a default one.  This will
                // fire a notification to the updateHandler
                // which takes care of the rest.
                editor.setDocument(getEditorKit(editor).createDefaultDocument());
            } else {
                doc.addDocumentListener(updateHandler);
                modelChanged();
            }

            // install keymap
            installListeners();
            installKeyboardActions();

            LayoutManager oldLayout = editor.getLayout();
            if ((oldLayout == null) || (oldLayout instanceof UIResource)) {
                // by default, use default LayoutManger implementation that
                // will position the components associated with a View object.
                editor.setLayout(updateHandler);
            }

            updateBackground(editor);
        } else {
            throw new Error("TextUI needs JTextComponent");
        }
    }

    /**
     * Deinstalls the UI for a component.  This removes the listeners,
     * uninstalls the highlighter, removes views, and nulls out the keymap.
     *
     * <p>
     * 卸载组件的UI。这将删除侦听器,卸载轮廓色,删除视图,并清除键映射。
     * 
     * 
     * @param c the editor component
     * @see ComponentUI#uninstallUI
     */
    public void uninstallUI(JComponent c) {
        // detach from the model
        editor.removePropertyChangeListener(updateHandler);
        editor.getDocument().removeDocumentListener(updateHandler);

        // view part
        painted = false;
        uninstallDefaults();
        rootView.setView(null);
        c.removeAll();
        LayoutManager lm = c.getLayout();
        if (lm instanceof UIResource) {
            c.setLayout(null);
        }

        // controller part
        uninstallKeyboardActions();
        uninstallListeners();

        editor = null;
    }

    /**
     * Superclass paints background in an uncontrollable way
     * (i.e. one might want an image tiled into the background).
     * To prevent this from happening twice, this method is
     * reimplemented to simply paint.
     * <p>
     * <em>NOTE:</em> NOTE: Superclass is also not thread-safe in its
     * rendering of the background, although that is not an issue with the
     * default rendering.
     * <p>
     *  超级油漆以不可控制的方式绘制背景(即,人们可能想要将图像平铺在背景中)。为了防止这种情况发生两次,这个方法重新实现简单的绘画。
     * <p>
     *  <em>注意：</em>注意：超类在其渲染背景时也不是线程安全的,虽然这不是默认渲染的问题。
     * 
     */
    public void update(Graphics g, JComponent c) {
        paint(g, c);
    }

    /**
     * Paints the interface.  This is routed to the
     * paintSafely method under the guarantee that
     * the model won't change from the view of this thread
     * while it's rendering (if the associated model is
     * derived from AbstractDocument).  This enables the
     * model to potentially be updated asynchronously.
     *
     * <p>
     *  绘制界面。这被路由到paintSafely方法,保证在渲染(如果关联模型从AbstractDocument派生)时模型不会从此线程的视图更改。这使得模型可能被异步更新。
     * 
     * 
     * @param g the graphics context
     * @param c the editor component
     */
    public final void paint(Graphics g, JComponent c) {
        if ((rootView.getViewCount() > 0) && (rootView.getView(0) != null)) {
            Document doc = editor.getDocument();
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readLock();
            }
            try {
                paintSafely(g);
            } finally {
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).readUnlock();
                }
            }
        }
    }

    /**
     * Gets the preferred size for the editor component.  If the component
     * has been given a size prior to receiving this request, it will
     * set the size of the view hierarchy to reflect the size of the component
     * before requesting the preferred size of the view hierarchy.  This
     * allows formatted views to format to the current component size before
     * answering the request.  Other views don't care about currently formatted
     * size and give the same answer either way.
     *
     * <p>
     *  获取编辑器组件的首选大小。如果组件在接收到此请求之前已经被赋予了大小,则它将在请求视图层次结构的优选大小之前设置视图层级的大小以反映该组件的大小。
     * 这允许格式化的视图在应答请求之前格式化为当前组件大小。其他视图不关心当前格式化的大小,并给出相同的答案。
     * 
     * 
     * @param c the editor component
     * @return the size
     */
    public Dimension getPreferredSize(JComponent c) {
        Document doc = editor.getDocument();
        Insets i = c.getInsets();
        Dimension d = c.getSize();

        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            if ((d.width > (i.left + i.right)) && (d.height > (i.top + i.bottom))) {
                rootView.setSize(d.width - i.left - i.right, d.height - i.top - i.bottom);
            }
            else if (d.width == 0 && d.height == 0) {
                // Probably haven't been layed out yet, force some sort of
                // initial sizing.
                rootView.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            }
            d.width = (int) Math.min((long) rootView.getPreferredSpan(View.X_AXIS) +
                                     (long) i.left + (long) i.right, Integer.MAX_VALUE);
            d.height = (int) Math.min((long) rootView.getPreferredSpan(View.Y_AXIS) +
                                      (long) i.top + (long) i.bottom, Integer.MAX_VALUE);
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return d;
    }

    /**
     * Gets the minimum size for the editor component.
     *
     * <p>
     *  获取编辑器组件的最小大小。
     * 
     * 
     * @param c the editor component
     * @return the size
     */
    public Dimension getMinimumSize(JComponent c) {
        Document doc = editor.getDocument();
        Insets i = c.getInsets();
        Dimension d = new Dimension();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            d.width = (int) rootView.getMinimumSpan(View.X_AXIS) + i.left + i.right;
            d.height = (int)  rootView.getMinimumSpan(View.Y_AXIS) + i.top + i.bottom;
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return d;
    }

    /**
     * Gets the maximum size for the editor component.
     *
     * <p>
     *  获取编辑器组件的最大大小。
     * 
     * 
     * @param c the editor component
     * @return the size
     */
    public Dimension getMaximumSize(JComponent c) {
        Document doc = editor.getDocument();
        Insets i = c.getInsets();
        Dimension d = new Dimension();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            d.width = (int) Math.min((long) rootView.getMaximumSpan(View.X_AXIS) +
                                     (long) i.left + (long) i.right, Integer.MAX_VALUE);
            d.height = (int) Math.min((long) rootView.getMaximumSpan(View.Y_AXIS) +
                                      (long) i.top + (long) i.bottom, Integer.MAX_VALUE);
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return d;
    }

    // ---- TextUI methods -------------------------------------------


    /**
     * Gets the allocation to give the root View.  Due
     * to an unfortunate set of historical events this
     * method is inappropriately named.  The Rectangle
     * returned has nothing to do with visibility.
     * The component must have a non-zero positive size for
     * this translation to be computed.
     *
     * <p>
     * 获取分配给根视图。由于一组不幸的历史事件,此方法被命名不当。返回的Rectangle与可见性无关。组件必须具有非零的正大小才能计算此转换。
     * 
     * 
     * @return the bounding box for the root view
     */
    protected Rectangle getVisibleEditorRect() {
        Rectangle alloc = editor.getBounds();
        if ((alloc.width > 0) && (alloc.height > 0)) {
            alloc.x = alloc.y = 0;
            Insets insets = editor.getInsets();
            alloc.x += insets.left;
            alloc.y += insets.top;
            alloc.width -= insets.left + insets.right;
            alloc.height -= insets.top + insets.bottom;
            return alloc;
        }
        return null;
    }

    /**
     * Converts the given location in the model to a place in
     * the view coordinate system.
     * The component must have a non-zero positive size for
     * this translation to be computed.
     *
     * <p>
     *  将模型中的给定位置转换为视图坐标系中的位置。组件必须具有非零的正大小才能计算此转换。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @param pos the local location in the model to translate &gt;= 0
     * @return the coordinates as a rectangle, null if the model is not painted
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     * @see TextUI#modelToView
     */
    public Rectangle modelToView(JTextComponent tc, int pos) throws BadLocationException {
        return modelToView(tc, pos, Position.Bias.Forward);
    }

    /**
     * Converts the given location in the model to a place in
     * the view coordinate system.
     * The component must have a non-zero positive size for
     * this translation to be computed.
     *
     * <p>
     *  将模型中的给定位置转换为视图坐标系中的位置。组件必须具有非零的正大小才能计算此转换。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @param pos the local location in the model to translate &gt;= 0
     * @return the coordinates as a rectangle, null if the model is not painted
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     * @see TextUI#modelToView
     */
    public Rectangle modelToView(JTextComponent tc, int pos, Position.Bias bias) throws BadLocationException {
        Document doc = editor.getDocument();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            Rectangle alloc = getVisibleEditorRect();
            if (alloc != null) {
                rootView.setSize(alloc.width, alloc.height);
                Shape s = rootView.modelToView(pos, alloc, bias);
                if (s != null) {
                  return s.getBounds();
                }
            }
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return null;
    }

    /**
     * Converts the given place in the view coordinate system
     * to the nearest representative location in the model.
     * The component must have a non-zero positive size for
     * this translation to be computed.
     *
     * <p>
     *  将视图坐标系中的给定位置转换为模型中最近的代表位置。组件必须具有非零的正大小才能计算此转换。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @param pt the location in the view to translate.  This
     *  should be in the same coordinate system as the mouse events.
     * @return the offset from the start of the document &gt;= 0,
     *   -1 if not painted
     * @see TextUI#viewToModel
     */
    public int viewToModel(JTextComponent tc, Point pt) {
        return viewToModel(tc, pt, discardBias);
    }

    /**
     * Converts the given place in the view coordinate system
     * to the nearest representative location in the model.
     * The component must have a non-zero positive size for
     * this translation to be computed.
     *
     * <p>
     *  将视图坐标系中的给定位置转换为模型中最近的代表位置。组件必须具有非零的正大小才能计算此转换。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @param pt the location in the view to translate.  This
     *  should be in the same coordinate system as the mouse events.
     * @return the offset from the start of the document &gt;= 0,
     *   -1 if the component doesn't yet have a positive size.
     * @see TextUI#viewToModel
     */
    public int viewToModel(JTextComponent tc, Point pt,
                           Position.Bias[] biasReturn) {
        int offs = -1;
        Document doc = editor.getDocument();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            Rectangle alloc = getVisibleEditorRect();
            if (alloc != null) {
                rootView.setSize(alloc.width, alloc.height);
                offs = rootView.viewToModel(pt.x, pt.y, alloc, biasReturn);
            }
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return offs;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public int getNextVisualPositionFrom(JTextComponent t, int pos,
                    Position.Bias b, int direction, Position.Bias[] biasRet)
                    throws BadLocationException{
        Document doc = editor.getDocument();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument)doc).readLock();
        }
        try {
            if (painted) {
                Rectangle alloc = getVisibleEditorRect();
                if (alloc != null) {
                    rootView.setSize(alloc.width, alloc.height);
                }
                return rootView.getNextVisualPositionFrom(pos, b, alloc, direction,
                                                          biasRet);
            }
        } finally {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readUnlock();
            }
        }
        return -1;
    }

    /**
     * Causes the portion of the view responsible for the
     * given part of the model to be repainted.  Does nothing if
     * the view is not currently painted.
     *
     * <p>
     *  导致对模型的给定部分负责的部分重新绘制。如果视图当前未绘制,则不执行任何操作。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     * @see TextUI#damageRange
     */
    public void damageRange(JTextComponent tc, int p0, int p1) {
        damageRange(tc, p0, p1, Position.Bias.Forward, Position.Bias.Backward);
    }

    /**
     * Causes the portion of the view responsible for the
     * given part of the model to be repainted.
     *
     * <p>
     *  导致对模型的给定部分负责的部分重新绘制。
     * 
     * 
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     */
    public void damageRange(JTextComponent t, int p0, int p1,
                            Position.Bias p0Bias, Position.Bias p1Bias) {
        if (painted) {
            Rectangle alloc = getVisibleEditorRect();
            if (alloc != null) {
                Document doc = t.getDocument();
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).readLock();
                }
                try {
                    rootView.setSize(alloc.width, alloc.height);
                    Shape toDamage = rootView.modelToView(p0, p0Bias,
                            p1, p1Bias, alloc);
                    Rectangle rect = (toDamage instanceof Rectangle) ?
                            (Rectangle)toDamage : toDamage.getBounds();
                    editor.repaint(rect.x, rect.y, rect.width, rect.height);
                } catch (BadLocationException e) {
                } finally {
                    if (doc instanceof AbstractDocument) {
                        ((AbstractDocument)doc).readUnlock();
                    }
                }
            }
        }
    }

    /**
     * Fetches the EditorKit for the UI.
     *
     * <p>
     *  获取UI的EditorKit。
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @return the editor capabilities
     * @see TextUI#getEditorKit
     */
    public EditorKit getEditorKit(JTextComponent tc) {
        return defaultKit;
    }

    /**
     * Fetches a View with the allocation of the associated
     * text component (i.e. the root of the hierarchy) that
     * can be traversed to determine how the model is being
     * represented spatially.
     * <p>
     * <font color=red><b>NOTE:</b>The View hierarchy can
     * be traversed from the root view, and other things
     * can be done as well.  Things done in this way cannot
     * be protected like simple method calls through the TextUI.
     * Therefore, proper operation in the presence of concurrency
     * must be arranged by any logic that calls this method!
     * </font>
     *
     * <p>
     *  使用可以被遍历的相关联文本分量(即,分层的根)的分配来获取视图,以确定如何在空间上表示模型。
     * <p>
     * <font color = red> <b>注意：</b>可以从根视图遍历视图层次结构,也可以执行其他操作。以这种方式完成的事情不能像通过TextUI的简单方法调用那样受到保护。
     * 因此,在并发性存在的情况下,正确的操作必须由调用此方法的任何逻辑安排！。
     * </font>
     * 
     * 
     * @param tc the text component for which this UI is installed
     * @return the view
     * @see TextUI#getRootView
     */
    public View getRootView(JTextComponent tc) {
        return rootView;
    }


    /**
     * Returns the string to be used as the tooltip at the passed in location.
     * This forwards the method onto the root View.
     *
     * <p>
     *  返回要在传入的位置处用作工具提示的字符串。这将该方法转发到根视图。
     * 
     * 
     * @see javax.swing.text.JTextComponent#getToolTipText
     * @see javax.swing.text.View#getToolTipText
     * @since 1.4
     */
    public String getToolTipText(JTextComponent t, Point pt) {
        if (!painted) {
            return null;
        }
        Document doc = editor.getDocument();
        String tt = null;
        Rectangle alloc = getVisibleEditorRect();

        if (alloc != null) {
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).readLock();
            }
            try {
                tt = rootView.getToolTipText(pt.x, pt.y, alloc);
            } finally {
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument)doc).readUnlock();
                }
            }
        }
        return tt;
    }

    // --- ViewFactory methods ------------------------------

    /**
     * Creates a view for an element.
     * If a subclass wishes to directly implement the factory
     * producing the view(s), it should reimplement this
     * method.  By default it simply returns null indicating
     * it is unable to represent the element.
     *
     * <p>
     *  为元素创建视图。如果一个子类希望直接实现产生视图的工厂,它应该重新实现这个方法。默认情况下,它只返回null,表示无法表示元素。
     * 
     * 
     * @param elem the element
     * @return the view
     */
    public View create(Element elem) {
        return null;
    }

    /**
     * Creates a view for an element.
     * If a subclass wishes to directly implement the factory
     * producing the view(s), it should reimplement this
     * method.  By default it simply returns null indicating
     * it is unable to represent the part of the element.
     *
     * <p>
     *  为元素创建视图。如果一个子类希望直接实现产生视图的工厂,它应该重新实现这个方法。默认情况下,它只返回null,表示它不能表示元素的一部分。
     * 
     * 
     * @param elem the element
     * @param p0 the starting offset &gt;= 0
     * @param p1 the ending offset &gt;= p0
     * @return the view
     */
    public View create(Element elem, int p0, int p1) {
        return null;
    }

    public static class BasicCaret extends DefaultCaret implements UIResource {}

    public static class BasicHighlighter extends DefaultHighlighter implements UIResource {}

    static class BasicCursor extends Cursor implements UIResource {
        BasicCursor(int type) {
            super(type);
        }

        BasicCursor(String name) {
            super(name);
        }
    }

    private static BasicCursor textCursor = new BasicCursor(Cursor.TEXT_CURSOR);
    // ----- member variables ---------------------------------------

    private static final EditorKit defaultKit = new DefaultEditorKit();
    transient JTextComponent editor;
    transient boolean painted;
    transient RootView rootView = new RootView();
    transient UpdateHandler updateHandler = new UpdateHandler();
    private static final TransferHandler defaultTransferHandler = new TextTransferHandler();
    private final DragListener dragListener = getDragListener();
    private static final Position.Bias[] discardBias = new Position.Bias[1];
    private DefaultCaret dropCaret;

    /**
     * Root view that acts as a gateway between the component
     * and the View hierarchy.
     * <p>
     *  作为组件和View层次结构之间的网关的根视图。
     * 
     */
    class RootView extends View {

        RootView() {
            super(null);
        }

        void setView(View v) {
            View oldView = view;
            view = null;
            if (oldView != null) {
                // get rid of back reference so that the old
                // hierarchy can be garbage collected.
                oldView.setParent(null);
            }
            if (v != null) {
                v.setParent(this);
            }
            view = v;
        }

        /**
         * Fetches the attributes to use when rendering.  At the root
         * level there are no attributes.  If an attribute is resolved
         * up the view hierarchy this is the end of the line.
         * <p>
         *  获取渲染时要使用的属性。在根级别没有属性。如果属性在视图层次结构中被解析,则这是行的结尾。
         * 
         */
        public AttributeSet getAttributes() {
            return null;
        }

        /**
         * Determines the preferred span for this view along an axis.
         *
         * <p>
         *  确定沿着轴的此视图的首选跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getPreferredSpan(int axis) {
            if (view != null) {
                return view.getPreferredSpan(axis);
            }
            return 10;
        }

        /**
         * Determines the minimum span for this view along an axis.
         *
         * <p>
         *  确定沿轴的此视图的最小跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getMinimumSpan(int axis) {
            if (view != null) {
                return view.getMinimumSpan(axis);
            }
            return 10;
        }

        /**
         * Determines the maximum span for this view along an axis.
         *
         * <p>
         *  确定沿轴的此视图的最大跨度。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the span the view would like to be rendered into.
         *         Typically the view is told to render into the span
         *         that is returned, although there is no guarantee.
         *         The parent may choose to resize or break the view.
         */
        public float getMaximumSpan(int axis) {
            return Integer.MAX_VALUE;
        }

        /**
         * Specifies that a preference has changed.
         * Child views can call this on the parent to indicate that
         * the preference has changed.  The root view routes this to
         * invalidate on the hosting component.
         * <p>
         * This can be called on a different thread from the
         * event dispatching thread and is basically unsafe to
         * propagate into the component.  To make this safe,
         * the operation is transferred over to the event dispatching
         * thread for completion.  It is a design goal that all view
         * methods be safe to call without concern for concurrency,
         * and this behavior helps make that true.
         *
         * <p>
         * 指定首选项已更改。子视图可以在父对象上调用此选项来指示首选项已更改。根视图将此路由到主机组件上的invalidate。
         * <p>
         *  这可以在与事件分派线程不同的线程上被调用,并且基本上不安全地传播到组件中。为了使这个安全,操作被转移到事件分派线程完成。
         * 这是一个设计目标,所有的视图方法可以安全地调用而不关心并发,这种行为有助于使真实。
         * 
         * 
         * @param child the child view
         * @param width true if the width preference has changed
         * @param height true if the height preference has changed
         */
        public void preferenceChanged(View child, boolean width, boolean height) {
            editor.revalidate();
        }

        /**
         * Determines the desired alignment for this view along an axis.
         *
         * <p>
         *  确定沿着轴的该视图的期望对准。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the desired alignment, where 0.0 indicates the origin
         *     and 1.0 the full span away from the origin
         */
        public float getAlignment(int axis) {
            if (view != null) {
                return view.getAlignment(axis);
            }
            return 0;
        }

        /**
         * Renders the view.
         *
         * <p>
         *  渲染视图。
         * 
         * 
         * @param g the graphics context
         * @param allocation the region to render into
         */
        public void paint(Graphics g, Shape allocation) {
            if (view != null) {
                Rectangle alloc = (allocation instanceof Rectangle) ?
                          (Rectangle)allocation : allocation.getBounds();
                setSize(alloc.width, alloc.height);
                view.paint(g, allocation);
            }
        }

        /**
         * Sets the view parent.
         *
         * <p>
         *  设置视图父视图。
         * 
         * 
         * @param parent the parent view
         */
        public void setParent(View parent) {
            throw new Error("Can't set parent on root view");
        }

        /**
         * Returns the number of views in this view.  Since
         * this view simply wraps the root of the view hierarchy
         * it has exactly one child.
         *
         * <p>
         *  返回此视图中的视图数。因为这个视图只包含视图层次结构的根,它只有一个孩子。
         * 
         * 
         * @return the number of views
         * @see #getView
         */
        public int getViewCount() {
            return 1;
        }

        /**
         * Gets the n-th view in this container.
         *
         * <p>
         *  获取此容器中的第n个视图。
         * 
         * 
         * @param n the number of the view to get
         * @return the view
         */
        public View getView(int n) {
            return view;
        }

        /**
         * Returns the child view index representing the given position in
         * the model.  This is implemented to return the index of the only
         * child.
         *
         * <p>
         *  返回表示模型中给定位置的子视图索引。这被实现为返回唯一的孩子的索引。
         * 
         * 
         * @param pos the position &gt;= 0
         * @return  index of the view representing the given position, or
         *   -1 if no view represents that position
         * @since 1.3
         */
        public int getViewIndex(int pos, Position.Bias b) {
            return 0;
        }

        /**
         * Fetches the allocation for the given child view.
         * This enables finding out where various views
         * are located, without assuming the views store
         * their location.  This returns the given allocation
         * since this view simply acts as a gateway between
         * the view hierarchy and the associated component.
         *
         * <p>
         *  获取给定子视图的分配。这使得能够找到各种视图位于何处,而不假定视图存储它们的位置。这返回给定的分配,因为该视图仅仅作为视图层次结构和相关组件之间的网关。
         * 
         * 
         * @param index the index of the child
         * @param a  the allocation to this view.
         * @return the allocation to the child
         */
        public Shape getChildAllocation(int index, Shape a) {
            return a;
        }

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.
         *
         * <p>
         *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
         * 
         * 
         * @param pos the position to convert
         * @param a the allocated region to render into
         * @return the bounding box of the given position
         */
        public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
            if (view != null) {
                return view.modelToView(pos, a, b);
            }
            return null;
        }

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.
         *
         * <p>
         *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
         * 
         * 
         * @param p0 the position to convert &gt;= 0
         * @param b0 the bias toward the previous character or the
         *  next character represented by p0, in case the
         *  position is a boundary of two views.
         * @param p1 the position to convert &gt;= 0
         * @param b1 the bias toward the previous character or the
         *  next character represented by p1, in case the
         *  position is a boundary of two views.
         * @param a the allocated region to render into
         * @return the bounding box of the given position is returned
         * @exception BadLocationException  if the given position does
         *   not represent a valid location in the associated document
         * @exception IllegalArgumentException for an invalid bias argument
         * @see View#viewToModel
         */
        public Shape modelToView(int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a) throws BadLocationException {
            if (view != null) {
                return view.modelToView(p0, b0, p1, b1, a);
            }
            return null;
        }

        /**
         * Provides a mapping from the view coordinate space to the logical
         * coordinate space of the model.
         *
         * <p>
         * 提供从视图坐标空间到模型的逻辑坐标空间的映射。
         * 
         * 
         * @param x x coordinate of the view location to convert
         * @param y y coordinate of the view location to convert
         * @param a the allocated region to render into
         * @return the location within the model that best represents the
         *    given point in the view
         */
        public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
            if (view != null) {
                int retValue = view.viewToModel(x, y, a, bias);
                return retValue;
            }
            return -1;
        }

        /**
         * Provides a way to determine the next visually represented model
         * location that one might place a caret.  Some views may not be visible,
         * they might not be in the same order found in the model, or they just
         * might not allow access to some of the locations in the model.
         * This method enables specifying a position to convert
         * within the range of &gt;=0.  If the value is -1, a position
         * will be calculated automatically.  If the value &lt; -1,
         * the {@code BadLocationException} will be thrown.
         *
         * <p>
         *  提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
         * 该方法使得能够指定在> = 0的范围内转换的位置。如果值为-1,将自动计算位置。如果值&lt; -1,将抛出{@code BadLocationException}。
         * 
         * 
         * @param pos the position to convert &gt;= 0
         * @param a the allocated region to render into
         * @param direction the direction from the current position that can
         *  be thought of as the arrow keys typically found on a keyboard.
         *  This may be SwingConstants.WEST, SwingConstants.EAST,
         *  SwingConstants.NORTH, or SwingConstants.SOUTH.
         * @return the location within the model that best represents the next
         *  location visual position.
         * @exception BadLocationException the given position is not a valid
         *                                 position within the document
         * @exception IllegalArgumentException for an invalid direction
         */
        public int getNextVisualPositionFrom(int pos, Position.Bias b, Shape a,
                                             int direction,
                                             Position.Bias[] biasRet)
            throws BadLocationException {
            if (pos < -1) {
                throw new BadLocationException("invalid position", pos);
            }
            if( view != null ) {
                int nextPos = view.getNextVisualPositionFrom(pos, b, a,
                                                     direction, biasRet);
                if(nextPos != -1) {
                    pos = nextPos;
                }
                else {
                    biasRet[0] = b;
                }
            }
            return pos;
        }

        /**
         * Gives notification that something was inserted into the document
         * in a location that this view is responsible for.
         *
         * <p>
         *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。
         * 
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         */
        public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (view != null) {
                view.insertUpdate(e, a, f);
            }
        }

        /**
         * Gives notification that something was removed from the document
         * in a location that this view is responsible for.
         *
         * <p>
         *  提供通知,说明该视图负责的位置中的文档被删除了。
         * 
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         */
        public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (view != null) {
                view.removeUpdate(e, a, f);
            }
        }

        /**
         * Gives notification from the document that attributes were changed
         * in a location that this view is responsible for.
         *
         * <p>
         *  从文档中提供属性在此视图负责的位置中更改的通知。
         * 
         * 
         * @param e the change information from the associated document
         * @param a the current allocation of the view
         * @param f the factory to use to rebuild if the view has children
         */
        public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
            if (view != null) {
                view.changedUpdate(e, a, f);
            }
        }

        /**
         * Returns the document model underlying the view.
         *
         * <p>
         *  返回视图下的文档模型。
         * 
         * 
         * @return the model
         */
        public Document getDocument() {
            return editor.getDocument();
        }

        /**
         * Returns the starting offset into the model for this view.
         *
         * <p>
         *  返回此视图的模型中的起始偏移量。
         * 
         * 
         * @return the starting offset
         */
        public int getStartOffset() {
            if (view != null) {
                return view.getStartOffset();
            }
            return getElement().getStartOffset();
        }

        /**
         * Returns the ending offset into the model for this view.
         *
         * <p>
         *  返回此视图的模型中的结束偏移量。
         * 
         * 
         * @return the ending offset
         */
        public int getEndOffset() {
            if (view != null) {
                return view.getEndOffset();
            }
            return getElement().getEndOffset();
        }

        /**
         * Gets the element that this view is mapped to.
         *
         * <p>
         *  获取此视图映射到的元素。
         * 
         * 
         * @return the view
         */
        public Element getElement() {
            if (view != null) {
                return view.getElement();
            }
            return editor.getDocument().getDefaultRootElement();
        }

        /**
         * Breaks this view on the given axis at the given length.
         *
         * <p>
         *  在给定长度的给定轴上中断此视图。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @param len specifies where a break is desired in the span
         * @param the current allocation of the view
         * @return the fragment of the view that represents the given span
         *   if the view can be broken, otherwise null
         */
        public View breakView(int axis, float len, Shape a) {
            throw new Error("Can't break root view");
        }

        /**
         * Determines the resizability of the view along the
         * given axis.  A value of 0 or less is not resizable.
         *
         * <p>
         *  确定沿给定轴的视图的可重新调整性。值为0或更小不可调整大小。
         * 
         * 
         * @param axis may be either X_AXIS or Y_AXIS
         * @return the weight
         */
        public int getResizeWeight(int axis) {
            if (view != null) {
                return view.getResizeWeight(axis);
            }
            return 0;
        }

        /**
         * Sets the view size.
         *
         * <p>
         *  设置视图大小。
         * 
         * 
         * @param width the width
         * @param height the height
         */
        public void setSize(float width, float height) {
            if (view != null) {
                view.setSize(width, height);
            }
        }

        /**
         * Fetches the container hosting the view.  This is useful for
         * things like scheduling a repaint, finding out the host
         * components font, etc.  The default implementation
         * of this is to forward the query to the parent view.
         *
         * <p>
         * 获取托管视图的容器。这对于调度重绘,查找主机组件字体等事情非常有用。默认实现是将查询转发到父视图。
         * 
         * 
         * @return the container
         */
        public Container getContainer() {
            return editor;
        }

        /**
         * Fetches the factory to be used for building the
         * various view fragments that make up the view that
         * represents the model.  This is what determines
         * how the model will be represented.  This is implemented
         * to fetch the factory provided by the associated
         * EditorKit unless that is null, in which case this
         * simply returns the BasicTextUI itself which allows
         * subclasses to implement a simple factory directly without
         * creating extra objects.
         *
         * <p>
         *  获取用于构建构成表示模型的视图的各种视图片段的工厂。这是决定如何表示模型的方法。
         * 这被实现为获取由相关EditorKit提供的工厂,除非它是null,在这种情况下,这简单地返回BasicTextUI本身,它允许子类直接实现一个简单的工厂,而不创建额外的对象。
         * 
         * 
         * @return the factory
         */
        public ViewFactory getViewFactory() {
            EditorKit kit = getEditorKit(editor);
            ViewFactory f = kit.getViewFactory();
            if (f != null) {
                return f;
            }
            return BasicTextUI.this;
        }

        private View view;

    }

    /**
     * Handles updates from various places.  If the model is changed,
     * this class unregisters as a listener to the old model and
     * registers with the new model.  If the document model changes,
     * the change is forwarded to the root view.  If the focus
     * accelerator changes, a new keystroke is registered to request
     * focus.
     * <p>
     *  处理来自各个地方的更新。如果模型更改,此类将取消注册为旧模型的侦听器,并注册新模型。如果文档模型更改,则更改将转发到根视图。如果焦点加速器改变,则注册新的击键以请求焦点。
     * 
     */
    class UpdateHandler implements PropertyChangeListener, DocumentListener, LayoutManager2, UIResource {

        // --- PropertyChangeListener methods -----------------------

        /**
         * This method gets called when a bound property is changed.
         * We are looking for document changes on the editor.
         * <p>
         *  当绑定属性更改时,将调用此方法。我们正在编辑器上查找文档更改。
         * 
         */
        public final void propertyChange(PropertyChangeEvent evt) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            String propertyName = evt.getPropertyName();
            if ((oldValue instanceof Document) || (newValue instanceof Document)) {
                if (oldValue != null) {
                    ((Document)oldValue).removeDocumentListener(this);
                    i18nView = false;
                }
                if (newValue != null) {
                    ((Document)newValue).addDocumentListener(this);
                    if ("document" == propertyName) {
                        setView(null);
                        BasicTextUI.this.propertyChange(evt);
                        modelChanged();
                        return;
                    }
                }
                modelChanged();
            }
            if ("focusAccelerator" == propertyName) {
                updateFocusAcceleratorBinding(true);
            } else if ("componentOrientation" == propertyName) {
                // Changes in ComponentOrientation require the views to be
                // rebuilt.
                modelChanged();
            } else if ("font" == propertyName) {
                modelChanged();
            } else if ("dropLocation" == propertyName) {
                dropIndexChanged();
            } else if ("editable" == propertyName) {
                updateCursor();
                modelChanged();
            }
            BasicTextUI.this.propertyChange(evt);
        }

        private void dropIndexChanged() {
            if (editor.getDropMode() == DropMode.USE_SELECTION) {
                return;
            }

            JTextComponent.DropLocation dropLocation = editor.getDropLocation();

            if (dropLocation == null) {
                if (dropCaret != null) {
                    dropCaret.deinstall(editor);
                    editor.repaint(dropCaret);
                    dropCaret = null;
                }
            } else {
                if (dropCaret == null) {
                    dropCaret = new BasicCaret();
                    dropCaret.install(editor);
                    dropCaret.setVisible(true);
                }

                dropCaret.setDot(dropLocation.getIndex(),
                                 dropLocation.getBias());
            }
        }

        // --- DocumentListener methods -----------------------

        /**
         * The insert notification.  Gets sent to the root of the view structure
         * that represents the portion of the model being represented by the
         * editor.  The factory is added as an argument to the update so that
         * the views can update themselves in a dynamic (not hardcoded) way.
         *
         * <p>
         *  插入通知。获取发送到视图结构的根,代表由编辑器表示的模型部分。工厂作为参数添加到更新中,以便视图可以以动态(而不是硬编码)的方式更新自身。
         * 
         * 
         * @param e  The change notification from the currently associated
         *  document.
         * @see DocumentListener#insertUpdate
         */
        public final void insertUpdate(DocumentEvent e) {
            Document doc = e.getDocument();
            Object o = doc.getProperty("i18n");
            if (o instanceof Boolean) {
                Boolean i18nFlag = (Boolean) o;
                if (i18nFlag.booleanValue() != i18nView) {
                    // i18n flag changed, rebuild the view
                    i18nView = i18nFlag.booleanValue();
                    modelChanged();
                    return;
                }
            }

            // normal insert update
            Rectangle alloc = (painted) ? getVisibleEditorRect() : null;
            rootView.insertUpdate(e, alloc, rootView.getViewFactory());
        }

        /**
         * The remove notification.  Gets sent to the root of the view structure
         * that represents the portion of the model being represented by the
         * editor.  The factory is added as an argument to the update so that
         * the views can update themselves in a dynamic (not hardcoded) way.
         *
         * <p>
         * 删除通知。获取发送到视图结构的根,代表由编辑器表示的模型部分。工厂作为参数添加到更新中,以便视图可以以动态(而不是硬编码)的方式更新自身。
         * 
         * 
         * @param e  The change notification from the currently associated
         *  document.
         * @see DocumentListener#removeUpdate
         */
        public final void removeUpdate(DocumentEvent e) {
            Rectangle alloc = (painted) ? getVisibleEditorRect() : null;
            rootView.removeUpdate(e, alloc, rootView.getViewFactory());
        }

        /**
         * The change notification.  Gets sent to the root of the view structure
         * that represents the portion of the model being represented by the
         * editor.  The factory is added as an argument to the update so that
         * the views can update themselves in a dynamic (not hardcoded) way.
         *
         * <p>
         *  更改通知。获取发送到视图结构的根,代表由编辑器表示的模型部分。工厂作为参数添加到更新中,以便视图可以以动态(而不是硬编码)的方式更新自身。
         * 
         * 
         * @param e  The change notification from the currently associated
         *  document.
         * @see DocumentListener#changedUpdate(DocumentEvent)
         */
        public final void changedUpdate(DocumentEvent e) {
            Rectangle alloc = (painted) ? getVisibleEditorRect() : null;
            rootView.changedUpdate(e, alloc, rootView.getViewFactory());
        }

        // --- LayoutManager2 methods --------------------------------

        /**
         * Adds the specified component with the specified name to
         * the layout.
         * <p>
         *  将具有指定名称的指定组件添加到布局。
         * 
         * 
         * @param name the component name
         * @param comp the component to be added
         */
        public void addLayoutComponent(String name, Component comp) {
            // not supported
        }

        /**
         * Removes the specified component from the layout.
         * <p>
         *  从布局中删除指定的组件。
         * 
         * 
         * @param comp the component to be removed
         */
        public void removeLayoutComponent(Component comp) {
            if (constraints != null) {
                // remove the constraint record
                constraints.remove(comp);
            }
        }

        /**
         * Calculates the preferred size dimensions for the specified
         * panel given the components in the specified parent container.
         * <p>
         *  在给定指定父容器中的组件的情况下,计算指定面板的首选大小维。
         * 
         * 
         * @param parent the component to be laid out
         *
         * @see #minimumLayoutSize
         */
        public Dimension preferredLayoutSize(Container parent) {
            // should not be called (JComponent uses UI instead)
            return null;
        }

        /**
         * Calculates the minimum size dimensions for the specified
         * panel given the components in the specified parent container.
         * <p>
         *  给定指定父容器中的组件时,计算指定面板的最小大小维。
         * 
         * 
         * @param parent the component to be laid out
         * @see #preferredLayoutSize
         */
        public Dimension minimumLayoutSize(Container parent) {
            // should not be called (JComponent uses UI instead)
            return null;
        }

        /**
         * Lays out the container in the specified panel.  This is
         * implemented to position all components that were added
         * with a View object as a constraint.  The current allocation
         * of the associated View is used as the location of the
         * component.
         * <p>
         * A read-lock is acquired on the document to prevent the
         * view tree from being modified while the layout process
         * is active.
         *
         * <p>
         *  将容器放在指定面板中。这被实现为将通过View对象添加的所有组件定位为约束。相关视图的当前分配用作组件的位置。
         * <p>
         *  在文档上获取读锁定以防止在布局过程活动时修改视图树。
         * 
         * 
         * @param parent the component which needs to be laid out
         */
        public void layoutContainer(Container parent) {
            if ((constraints != null) && (! constraints.isEmpty())) {
                Rectangle alloc = getVisibleEditorRect();
                if (alloc != null) {
                    Document doc = editor.getDocument();
                    if (doc instanceof AbstractDocument) {
                        ((AbstractDocument)doc).readLock();
                    }
                    try {
                        rootView.setSize(alloc.width, alloc.height);
                        Enumeration<Component> components = constraints.keys();
                        while (components.hasMoreElements()) {
                            Component comp = components.nextElement();
                            View v = (View) constraints.get(comp);
                            Shape ca = calculateViewPosition(alloc, v);
                            if (ca != null) {
                                Rectangle compAlloc = (ca instanceof Rectangle) ?
                                    (Rectangle) ca : ca.getBounds();
                                comp.setBounds(compAlloc);
                            }
                        }
                    } finally {
                        if (doc instanceof AbstractDocument) {
                            ((AbstractDocument)doc).readUnlock();
                        }
                    }
                }
            }
        }

        /**
         * Find the Shape representing the given view.
         * <p>
         *  找到表示给定视图的Shape。
         * 
         */
        Shape calculateViewPosition(Shape alloc, View v) {
            int pos = v.getStartOffset();
            View child = null;
            for (View parent = rootView; (parent != null) && (parent != v); parent = child) {
                int index = parent.getViewIndex(pos, Position.Bias.Forward);
                alloc = parent.getChildAllocation(index, alloc);
                child = parent.getView(index);
            }
            return (child != null) ? alloc : null;
        }

        /**
         * Adds the specified component to the layout, using the specified
         * constraint object.  We only store those components that were added
         * with a constraint that is of type View.
         *
         * <p>
         *  使用指定的约束对象将指定的组件添加到布局。我们只存储添加了类型为View的约束的那些组件。
         * 
         * 
         * @param comp the component to be added
         * @param constraint  where/how the component is added to the layout.
         */
        public void addLayoutComponent(Component comp, Object constraint) {
            if (constraint instanceof View) {
                if (constraints == null) {
                    constraints = new Hashtable<Component, Object>(7);
                }
                constraints.put(comp, constraint);
            }
        }

        /**
         * Returns the maximum size of this component.
         * <p>
         * 返回此组件的最大大小。
         * 
         * 
         * @see java.awt.Component#getMinimumSize()
         * @see java.awt.Component#getPreferredSize()
         * @see LayoutManager
         */
        public Dimension maximumLayoutSize(Container target) {
            // should not be called (JComponent uses UI instead)
            return null;
        }

        /**
         * Returns the alignment along the x axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         * <p>
         *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
         * 
         */
        public float getLayoutAlignmentX(Container target) {
            return 0.5f;
        }

        /**
         * Returns the alignment along the y axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         * <p>
         *  返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
         * 
         */
        public float getLayoutAlignmentY(Container target) {
            return 0.5f;
        }

        /**
         * Invalidates the layout, indicating that if the layout manager
         * has cached information it should be discarded.
         * <p>
         *  使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃。
         * 
         */
        public void invalidateLayout(Container target) {
        }

        /**
         * The "layout constraints" for the LayoutManager2 implementation.
         * These are View objects for those components that are represented
         * by a View in the View tree.
         * <p>
         *  LayoutManager2实现的"布局约束"。这些是由View树中的视图表示的那些组件的View对象。
         * 
         */
        private Hashtable<Component, Object> constraints;

        private boolean i18nView = false;
    }

    /**
     * Wrapper for text actions to return isEnabled false in case editor is non editable
     * <p>
     *  包装文本操作返回isEnabled false,以防编辑器不可编辑
     * 
     */
    class TextActionWrapper extends TextAction {
        public TextActionWrapper(TextAction action) {
            super((String)action.getValue(Action.NAME));
            this.action = action;
        }
        /**
         * The operation to perform when this action is triggered.
         *
         * <p>
         *  触发此操作时执行的操作。
         * 
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
        }
        public boolean isEnabled() {
            return (editor == null || editor.isEditable()) ? action.isEnabled() : false;
        }
        TextAction action = null;
    }


    /**
     * Registered in the ActionMap.
     * <p>
     *  注册在ActionMap中。
     * 
     */
    class FocusAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            editor.requestFocus();
        }

        public boolean isEnabled() {
            return editor.isEditable();
        }
    }

    private static DragListener getDragListener() {
        synchronized(DragListener.class) {
            DragListener listener =
                (DragListener)AppContext.getAppContext().
                    get(DragListener.class);

            if (listener == null) {
                listener = new DragListener();
                AppContext.getAppContext().put(DragListener.class, listener);
            }

            return listener;
        }
    }

    /**
     * Listens for mouse events for the purposes of detecting drag gestures.
     * BasicTextUI will maintain one of these per AppContext.
     * <p>
     *  监听鼠标事件,以检测拖动手势。 BasicTextUI将根据每个AppContext维护其中一个。
     * 
     */
    static class DragListener extends MouseInputAdapter
                              implements BeforeDrag {

        private boolean dragStarted;

        public void dragStarting(MouseEvent me) {
            dragStarted = true;
        }

        public void mousePressed(MouseEvent e) {
            JTextComponent c = (JTextComponent)e.getSource();
            if (c.getDragEnabled()) {
                dragStarted = false;
                if (isDragPossible(e) && DragRecognitionSupport.mousePressed(e)) {
                    e.consume();
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            JTextComponent c = (JTextComponent)e.getSource();
            if (c.getDragEnabled()) {
                if (dragStarted) {
                    e.consume();
                }

                DragRecognitionSupport.mouseReleased(e);
            }
        }

        public void mouseDragged(MouseEvent e) {
            JTextComponent c = (JTextComponent)e.getSource();
            if (c.getDragEnabled()) {
                if (dragStarted || DragRecognitionSupport.mouseDragged(e, this)) {
                    e.consume();
                }
            }
        }

        /**
         * Determines if the following are true:
         * <ul>
         * <li>the component is enabled
         * <li>the press event is located over a selection
         * </ul>
         * <p>
         *  确定以下是否为真：
         * <ul>
         *  <li>该组件已启用<li>按事件位于选择内
         * </ul>
         */
        protected boolean isDragPossible(MouseEvent e) {
            JTextComponent c = (JTextComponent)e.getSource();
            if (c.isEnabled()) {
                Caret caret = c.getCaret();
                int dot = caret.getDot();
                int mark = caret.getMark();
                if (dot != mark) {
                    Point p = new Point(e.getX(), e.getY());
                    int pos = c.viewToModel(p);

                    int p0 = Math.min(dot, mark);
                    int p1 = Math.max(dot, mark);
                    if ((pos >= p0) && (pos < p1)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class TextTransferHandler extends TransferHandler implements UIResource {

        private JTextComponent exportComp;
        private boolean shouldRemove;
        private int p0;
        private int p1;

        /**
         * Whether or not this is a drop using
         * <code>DropMode.INSERT</code>.
         * <p>
         *  无论这是否是使用<code> DropMode.INSERT </code>的删除。
         * 
         */
        private boolean modeBetween = false;

        /**
         * Whether or not this is a drop.
         * <p>
         *  这是否是一个下降。
         * 
         */
        private boolean isDrop = false;

        /**
         * The drop action.
         * <p>
         *  下降动作。
         * 
         */
        private int dropAction = MOVE;

        /**
         * The drop bias.
         * <p>
         *  下降偏差。
         * 
         */
        private Position.Bias dropBias;

        /**
         * Try to find a flavor that can be used to import a Transferable.
         * The set of usable flavors are tried in the following order:
         * <ol>
         *     <li>First, an attempt is made to find a flavor matching the content type
         *         of the EditorKit for the component.
         *     <li>Second, an attempt to find a text/plain flavor is made.
         *     <li>Third, an attempt to find a flavor representing a String reference
         *         in the same VM is made.
         *     <li>Lastly, DataFlavor.stringFlavor is searched for.
         * </ol>
         * <p>
         * 尝试找到可用于导入可转移项的风格。可用风味的集合按以下顺序尝试：
         * <ol>
         *  <li>首先,尝试查找与组件的EditorKit的内容类型匹配的flavor。 <li>其次,尝试寻找文本/纯朴风味。 <li>第三,尝试在同一个VM中查找表示String引用的flavor。
         *  <li>最后,搜索DataFlavor.stringFlavor。
         * </ol>
         */
        protected DataFlavor getImportFlavor(DataFlavor[] flavors, JTextComponent c) {
            DataFlavor plainFlavor = null;
            DataFlavor refFlavor = null;
            DataFlavor stringFlavor = null;

            if (c instanceof JEditorPane) {
                for (int i = 0; i < flavors.length; i++) {
                    String mime = flavors[i].getMimeType();
                    if (mime.startsWith(((JEditorPane)c).getEditorKit().getContentType())) {
                        return flavors[i];
                    } else if (plainFlavor == null && mime.startsWith("text/plain")) {
                        plainFlavor = flavors[i];
                    } else if (refFlavor == null && mime.startsWith("application/x-java-jvm-local-objectref")
                                                 && flavors[i].getRepresentationClass() == java.lang.String.class) {
                        refFlavor = flavors[i];
                    } else if (stringFlavor == null && flavors[i].equals(DataFlavor.stringFlavor)) {
                        stringFlavor = flavors[i];
                    }
                }
                if (plainFlavor != null) {
                    return plainFlavor;
                } else if (refFlavor != null) {
                    return refFlavor;
                } else if (stringFlavor != null) {
                    return stringFlavor;
                }
                return null;
            }


            for (int i = 0; i < flavors.length; i++) {
                String mime = flavors[i].getMimeType();
                if (mime.startsWith("text/plain")) {
                    return flavors[i];
                } else if (refFlavor == null && mime.startsWith("application/x-java-jvm-local-objectref")
                                             && flavors[i].getRepresentationClass() == java.lang.String.class) {
                    refFlavor = flavors[i];
                } else if (stringFlavor == null && flavors[i].equals(DataFlavor.stringFlavor)) {
                    stringFlavor = flavors[i];
                }
            }
            if (refFlavor != null) {
                return refFlavor;
            } else if (stringFlavor != null) {
                return stringFlavor;
            }
            return null;
        }

        /**
         * Import the given stream data into the text component.
         * <p>
         *  将给定的流数据导入文本组件。
         * 
         */
        protected void handleReaderImport(Reader in, JTextComponent c, boolean useRead)
                                               throws BadLocationException, IOException {
            if (useRead) {
                int startPosition = c.getSelectionStart();
                int endPosition = c.getSelectionEnd();
                int length = endPosition - startPosition;
                EditorKit kit = c.getUI().getEditorKit(c);
                Document doc = c.getDocument();
                if (length > 0) {
                    doc.remove(startPosition, length);
                }
                kit.read(in, doc, startPosition);
            } else {
                char[] buff = new char[1024];
                int nch;
                boolean lastWasCR = false;
                int last;
                StringBuffer sbuff = null;

                // Read in a block at a time, mapping \r\n to \n, as well as single
                // \r to \n.
                while ((nch = in.read(buff, 0, buff.length)) != -1) {
                    if (sbuff == null) {
                        sbuff = new StringBuffer(nch);
                    }
                    last = 0;
                    for(int counter = 0; counter < nch; counter++) {
                        switch(buff[counter]) {
                        case '\r':
                            if (lastWasCR) {
                                if (counter == 0) {
                                    sbuff.append('\n');
                                } else {
                                    buff[counter - 1] = '\n';
                                }
                            } else {
                                lastWasCR = true;
                            }
                            break;
                        case '\n':
                            if (lastWasCR) {
                                if (counter > (last + 1)) {
                                    sbuff.append(buff, last, counter - last - 1);
                                }
                                // else nothing to do, can skip \r, next write will
                                // write \n
                                lastWasCR = false;
                                last = counter;
                            }
                            break;
                        default:
                            if (lastWasCR) {
                                if (counter == 0) {
                                    sbuff.append('\n');
                                } else {
                                    buff[counter - 1] = '\n';
                                }
                                lastWasCR = false;
                            }
                            break;
                        }
                    }
                    if (last < nch) {
                        if (lastWasCR) {
                            if (last < (nch - 1)) {
                                sbuff.append(buff, last, nch - last - 1);
                            }
                        } else {
                            sbuff.append(buff, last, nch - last);
                        }
                    }
                }
                if (lastWasCR) {
                    sbuff.append('\n');
                }
                c.replaceSelection(sbuff != null ? sbuff.toString() : "");
            }
        }

        // --- TransferHandler methods ------------------------------------

        /**
         * This is the type of transfer actions supported by the source.  Some models are
         * not mutable, so a transfer operation of COPY only should
         * be advertised in that case.
         *
         * <p>
         *  这是源支持的传输操作的类型。一些模型不可变,因此在这种情况下应该公布仅COPY的传送操作。
         * 
         * 
         * @param c  The component holding the data to be transfered.  This
         *  argument is provided to enable sharing of TransferHandlers by
         *  multiple components.
         * @return  This is implemented to return NONE if the component is a JPasswordField
         *  since exporting data via user gestures is not allowed.  If the text component is
         *  editable, COPY_OR_MOVE is returned, otherwise just COPY is allowed.
         */
        public int getSourceActions(JComponent c) {
            if (c instanceof JPasswordField &&
                c.getClientProperty("JPasswordField.cutCopyAllowed") !=
                Boolean.TRUE) {
                return NONE;
            }

            return ((JTextComponent)c).isEditable() ? COPY_OR_MOVE : COPY;
        }

        /**
         * Create a Transferable to use as the source for a data transfer.
         *
         * <p>
         *  创建一个可转移以用作数据传输的源。
         * 
         * 
         * @param comp  The component holding the data to be transfered.  This
         *  argument is provided to enable sharing of TransferHandlers by
         *  multiple components.
         * @return  The representation of the data to be transfered.
         *
         */
        protected Transferable createTransferable(JComponent comp) {
            exportComp = (JTextComponent)comp;
            shouldRemove = true;
            p0 = exportComp.getSelectionStart();
            p1 = exportComp.getSelectionEnd();
            return (p0 != p1) ? (new TextTransferable(exportComp, p0, p1)) : null;
        }

        /**
         * This method is called after data has been exported.  This method should remove
         * the data that was transfered if the action was MOVE.
         *
         * <p>
         *  此方法在导出数据后调用。如果操作为MOVE,此方法应删除已传输的数据。
         * 
         * 
         * @param source The component that was the source of the data.
         * @param data   The data that was transferred or possibly null
         *               if the action is <code>NONE</code>.
         * @param action The actual action that was performed.
         */
        protected void exportDone(JComponent source, Transferable data, int action) {
            // only remove the text if shouldRemove has not been set to
            // false by importData and only if the action is a move
            if (shouldRemove && action == MOVE) {
                TextTransferable t = (TextTransferable)data;
                t.removeText();
            }

            exportComp = null;
        }

        public boolean importData(TransferSupport support) {
            isDrop = support.isDrop();

            if (isDrop) {
                modeBetween =
                    ((JTextComponent)support.getComponent()).getDropMode() == DropMode.INSERT;

                dropBias = ((JTextComponent.DropLocation)support.getDropLocation()).getBias();

                dropAction = support.getDropAction();
            }

            try {
                return super.importData(support);
            } finally {
                isDrop = false;
                modeBetween = false;
                dropBias = null;
                dropAction = MOVE;
            }
        }

        /**
         * This method causes a transfer to a component from a clipboard or a
         * DND drop operation.  The Transferable represents the data to be
         * imported into the component.
         *
         * <p>
         *  此方法导致从剪贴板或DND拖放操作到组件的传输。 Transferable表示要导入到组件中的数据。
         * 
         * 
         * @param comp  The component to receive the transfer.  This
         *  argument is provided to enable sharing of TransferHandlers by
         *  multiple components.
         * @param t     The data to import
         * @return  true if the data was inserted into the component, false otherwise.
         */
        public boolean importData(JComponent comp, Transferable t) {
            JTextComponent c = (JTextComponent)comp;

            int pos = modeBetween
                      ? c.getDropLocation().getIndex() : c.getCaretPosition();

            // if we are importing to the same component that we exported from
            // then don't actually do anything if the drop location is inside
            // the drag location and set shouldRemove to false so that exportDone
            // knows not to remove any data
            if (dropAction == MOVE && c == exportComp && pos >= p0 && pos <= p1) {
                shouldRemove = false;
                return true;
            }

            boolean imported = false;
            DataFlavor importFlavor = getImportFlavor(t.getTransferDataFlavors(), c);
            if (importFlavor != null) {
                try {
                    boolean useRead = false;
                    if (comp instanceof JEditorPane) {
                        JEditorPane ep = (JEditorPane)comp;
                        if (!ep.getContentType().startsWith("text/plain") &&
                                importFlavor.getMimeType().startsWith(ep.getContentType())) {
                            useRead = true;
                        }
                    }
                    InputContext ic = c.getInputContext();
                    if (ic != null) {
                        ic.endComposition();
                    }
                    Reader r = importFlavor.getReaderForText(t);

                    if (modeBetween) {
                        Caret caret = c.getCaret();
                        if (caret instanceof DefaultCaret) {
                            ((DefaultCaret)caret).setDot(pos, dropBias);
                        } else {
                            c.setCaretPosition(pos);
                        }
                    }

                    handleReaderImport(r, c, useRead);

                    if (isDrop) {
                        c.requestFocus();
                        Caret caret = c.getCaret();
                        if (caret instanceof DefaultCaret) {
                            int newPos = caret.getDot();
                            Position.Bias newBias = ((DefaultCaret)caret).getDotBias();

                            ((DefaultCaret)caret).setDot(pos, dropBias);
                            ((DefaultCaret)caret).moveDot(newPos, newBias);
                        } else {
                            c.select(pos, c.getCaretPosition());
                        }
                    }

                    imported = true;
                } catch (UnsupportedFlavorException ufe) {
                } catch (BadLocationException ble) {
                } catch (IOException ioe) {
                }
            }
            return imported;
        }

        /**
         * This method indicates if a component would accept an import of the given
         * set of data flavors prior to actually attempting to import it.
         *
         * <p>
         *  此方法指示组件在实际尝试导入之前是否将接受给定数据集的导入。
         * 
         * 
         * @param comp  The component to receive the transfer.  This
         *  argument is provided to enable sharing of TransferHandlers by
         *  multiple components.
         * @param flavors  The data formats available
         * @return  true if the data can be inserted into the component, false otherwise.
         */
        public boolean canImport(JComponent comp, DataFlavor[] flavors) {
            JTextComponent c = (JTextComponent)comp;
            if (!(c.isEditable() && c.isEnabled())) {
                return false;
            }
            return (getImportFlavor(flavors, c) != null);
        }

        /**
         * A possible implementation of the Transferable interface
         * for text components.  For a JEditorPane with a rich set
         * of EditorKit implementations, conversions could be made
         * giving a wider set of formats.  This is implemented to
         * offer up only the active content type and text/plain
         * (if that is not the active format) since that can be
         * extracted from other formats.
         * <p>
         * 用于文本组件的Transferable接口的可能实现。对于具有丰富的EditorKit实现集的JEditorPane,可以进行转换以提供更广泛的格式集。
         * 这被实现为仅提供活动内容类型和文本/纯文本(如果不是活动格式),因为可以从其他格式提取。
         * 
         */
        static class TextTransferable extends BasicTransferable {

            TextTransferable(JTextComponent c, int start, int end) {
                super(null, null);

                this.c = c;

                Document doc = c.getDocument();

                try {
                    p0 = doc.createPosition(start);
                    p1 = doc.createPosition(end);

                    plainData = c.getSelectedText();

                    if (c instanceof JEditorPane) {
                        JEditorPane ep = (JEditorPane)c;

                        mimeType = ep.getContentType();

                        if (mimeType.startsWith("text/plain")) {
                            return;
                        }

                        StringWriter sw = new StringWriter(p1.getOffset() - p0.getOffset());
                        ep.getEditorKit().write(sw, doc, p0.getOffset(), p1.getOffset() - p0.getOffset());

                        if (mimeType.startsWith("text/html")) {
                            htmlData = sw.toString();
                        } else {
                            richText = sw.toString();
                        }
                    }
                } catch (BadLocationException ble) {
                } catch (IOException ioe) {
                }
            }

            void removeText() {
                if ((p0 != null) && (p1 != null) && (p0.getOffset() != p1.getOffset())) {
                    try {
                        Document doc = c.getDocument();
                        doc.remove(p0.getOffset(), p1.getOffset() - p0.getOffset());
                    } catch (BadLocationException e) {
                    }
                }
            }

            // ---- EditorKit other than plain or HTML text -----------------------

            /**
             * If the EditorKit is not for text/plain or text/html, that format
             * is supported through the "richer flavors" part of BasicTransferable.
             * <p>
             *  如果EditorKit不是text / plain或text / html,那么通过BasicTransferable的"更丰富的flavor"部分支持该格式。
             * 
             */
            protected DataFlavor[] getRicherFlavors() {
                if (richText == null) {
                    return null;
                }

                try {
                    DataFlavor[] flavors = new DataFlavor[3];
                    flavors[0] = new DataFlavor(mimeType + ";class=java.lang.String");
                    flavors[1] = new DataFlavor(mimeType + ";class=java.io.Reader");
                    flavors[2] = new DataFlavor(mimeType + ";class=java.io.InputStream;charset=unicode");
                    return flavors;
                } catch (ClassNotFoundException cle) {
                    // fall through to unsupported (should not happen)
                }

                return null;
            }

            /**
             * The only richer format supported is the file list flavor
             * <p>
             *  支持的唯一更丰富的格式是文件列表风格
             */
            protected Object getRicherData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (richText == null) {
                    return null;
                }

                if (String.class.equals(flavor.getRepresentationClass())) {
                    return richText;
                } else if (Reader.class.equals(flavor.getRepresentationClass())) {
                    return new StringReader(richText);
                } else if (InputStream.class.equals(flavor.getRepresentationClass())) {
                    return new StringBufferInputStream(richText);
                }
                throw new UnsupportedFlavorException(flavor);
            }

            Position p0;
            Position p1;
            String mimeType;
            String richText;
            JTextComponent c;
        }

    }

}
