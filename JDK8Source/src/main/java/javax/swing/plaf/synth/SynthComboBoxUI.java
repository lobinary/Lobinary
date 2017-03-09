/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.synth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JComboBox}.
 *
 * <p>
 *  为{@link javax.swing.JComboBox}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Scott Violet
 * @since 1.7
 */
public class SynthComboBoxUI extends BasicComboBoxUI implements
                              PropertyChangeListener, SynthUI {
    private SynthStyle style;
    private boolean useListColors;

    /**
     * Used to adjust the location and size of the popup. Very useful for
     * situations such as we find in Nimbus where part of the border is used
     * to paint the focus. In such cases, the border is empty space, and not
     * part of the "visual" border, and in these cases, you'd like the popup
     * to be adjusted such that it looks as if it were next to the visual border.
     * You may want to use negative insets to get the right look.
     * <p>
     *  用于调整弹出窗口的位置和大小。非常有用的情况下,如我们发现在尼姆布斯的边界的一部分用于画的焦点。
     * 在这种情况下,边框是空白空间,而不是"可视"边框的一部分,在这些情况下,您需要调整弹出窗口,使其看起来像是在视觉边框旁边。你可能想使用负插入来获得正确的外观。
     * 
     */
    Insets popupInsets;

    /**
     * This flag may be set via UIDefaults. By default, it is false, to
     * preserve backwards compatibility. If true, then the combo will
     * "act as a button" when it is not editable.
     * <p>
     *  此标志可以通过UIDefaults设置。默认情况下,它为false,以保留向后兼容性。如果为true,那么当组合不可编辑时,它将"作为按钮"。
     * 
     */
    private boolean buttonWhenNotEditable;

    /**
     * A flag to indicate that the combo box and combo box button should
     * remain in the PRESSED state while the combo popup is visible.
     * <p>
     *  一个标志,指示组合框和组合框按钮应保持在PRESSED状态,而组合弹出窗口是可见的。
     * 
     */
    private boolean pressedWhenPopupVisible;

    /**
     * When buttonWhenNotEditable is true, this field is used to help make
     * the combo box appear and function as a button when the combo box is
     * not editable. In such a state, you can click anywhere on the button
     * to get it to open the popup. Also, anywhere you hover over the combo
     * will cause the entire combo to go into "rollover" state, and anywhere
     * you press will go into "pressed" state. This also keeps in sync the
     * state of the combo and the arrowButton.
     * <p>
     *  当buttonWhenNotEditable为true时,此字段用于在组合框无法编辑时帮助使组合框显示并用作按钮。在这种状态下,您可以单击按钮上的任何位置以使其打开弹出窗口。
     * 此外,任何地方,你悬停在组合将导致整个组合进入"翻转"状态,任何地方,你按将进入"按"状态。这也保持同步组合和arrowButton的状态。
     * 
     */
    private ButtonHandler buttonHandler;

    /**
     * Handler for repainting combo when editor component gains/looses focus
     * <p>
     *  当编辑器组件获得/失去焦点时重新绘制组合的处理程序
     * 
     */
    private EditorFocusHandler editorFocusHandler;

    /**
     * If true, then the cell renderer will be forced to be non-opaque when
     * used for rendering the selected item in the combo box (not in the list),
     * and forced to opaque after rendering the selected value.
     * <p>
     * 如果为true,则当用于在组合框(不在列表中)呈现所选项目时,单元格呈现器将被强制为非不透明的,并且在呈现所选择的值之后被强制为不透明。
     * 
     */
    private boolean forceOpaque = false;

    /**
     * Creates a new UI object for the given component.
     *
     * <p>
     *  为给定组件创建一个新的UI对象。
     * 
     * 
     * @param c component to create UI object for
     * @return the UI object
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthComboBoxUI();
    }

    /**
     * {@inheritDoc}
     *
     * Overridden to ensure that ButtonHandler is created prior to any of
     * the other installXXX methods, since several of them reference
     * buttonHandler.
     * <p>
     *  {@inheritDoc}
     * 
     *  重写以确保ButtonHandler在任何其他installXXX方法之前创建,因为其中几个引用了buttonHandler。
     * 
     */
    @Override
    public void installUI(JComponent c) {
        buttonHandler = new ButtonHandler();
        super.installUI(c);
    }

    @Override
    protected void installDefaults() {
        updateStyle(comboBox);
    }

    private void updateStyle(JComboBox comboBox) {
        SynthStyle oldStyle = style;
        SynthContext context = getContext(comboBox, ENABLED);

        style = SynthLookAndFeel.updateStyle(context, this);
        if (style != oldStyle) {
            padding = (Insets) style.get(context, "ComboBox.padding");
            popupInsets = (Insets)style.get(context, "ComboBox.popupInsets");
            useListColors = style.getBoolean(context,
                    "ComboBox.rendererUseListColors", true);
            buttonWhenNotEditable = style.getBoolean(context,
                    "ComboBox.buttonWhenNotEditable", false);
            pressedWhenPopupVisible = style.getBoolean(context,
                    "ComboBox.pressedWhenPopupVisible", false);
            squareButton = style.getBoolean(context,
                    "ComboBox.squareButton", true);

            if (oldStyle != null) {
                uninstallKeyboardActions();
                installKeyboardActions();
            }
            forceOpaque = style.getBoolean(context,
                    "ComboBox.forceOpaque", false);
        }
        context.dispose();

        if(listBox != null) {
            SynthLookAndFeel.updateStyles(listBox);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installListeners() {
        comboBox.addPropertyChangeListener(this);
        comboBox.addMouseListener(buttonHandler);
        editorFocusHandler = new EditorFocusHandler(comboBox);
        super.installListeners();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void uninstallUI(JComponent c) {
        if (popup instanceof SynthComboPopup) {
            ((SynthComboPopup)popup).removePopupMenuListener(buttonHandler);
        }
        super.uninstallUI(c);
        buttonHandler = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallDefaults() {
        SynthContext context = getContext(comboBox, ENABLED);

        style.uninstallDefaults(context);
        context.dispose();
        style = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallListeners() {
        editorFocusHandler.unregister();
        comboBox.removePropertyChangeListener(this);
        comboBox.removeMouseListener(buttonHandler);
        buttonHandler.pressed = false;
        buttonHandler.over = false;
        super.uninstallListeners();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public SynthContext getContext(JComponent c) {
        return getContext(c, getComponentState(c));
    }

    private SynthContext getContext(JComponent c, int state) {
        return SynthContext.getContext(c, style, state);
    }

    private int getComponentState(JComponent c) {
        // currently we have a broken situation where if a developer
        // takes the border from a JComboBox and sets it on a JTextField
        // then the codepath will eventually lead back to this method
        // but pass in a JTextField instead of JComboBox! In case this
        // happens, we just return the normal synth state for the component
        // instead of doing anything special
        if (!(c instanceof JComboBox)) return SynthLookAndFeel.getComponentState(c);

        JComboBox box = (JComboBox)c;
        if (shouldActLikeButton()) {
            int state = ENABLED;
            if ((!c.isEnabled())) {
                state = DISABLED;
            }
            if (buttonHandler.isPressed()) {
                state |= PRESSED;
            }
            if (buttonHandler.isRollover()) {
                state |= MOUSE_OVER;
            }
            if (box.isFocusOwner()) {
                state |= FOCUSED;
            }
            return state;
        } else {
            // for editable combos the editor component has the focus not the
            // combo box its self, so we should make the combo paint focused
            // when its editor has focus
            int basicState = SynthLookAndFeel.getComponentState(c);
            if (box.isEditable() &&
                     box.getEditor().getEditorComponent().isFocusOwner()) {
                basicState |= FOCUSED;
            }
            return basicState;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected ComboPopup createPopup() {
        SynthComboPopup p = new SynthComboPopup(comboBox);
        p.addPopupMenuListener(buttonHandler);
        return p;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected ListCellRenderer createRenderer() {
        return new SynthComboBoxRenderer();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected ComboBoxEditor createEditor() {
        return new SynthComboBoxEditor();
    }

    //
    // end UI Initialization
    //======================

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (SynthLookAndFeel.shouldUpdateStyle(e)) {
            updateStyle(comboBox);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected JButton createArrowButton() {
        SynthArrowButton button = new SynthArrowButton(SwingConstants.SOUTH);
        button.setName("ComboBox.arrowButton");
        button.setModel(buttonHandler);
        return button;
    }

    //=================================
    // begin ComponentUI Implementation

    /**
     * Notifies this UI delegate to repaint the specified component.
     * This method paints the component background, then calls
     * the {@link #paint(SynthContext,Graphics)} method.
     *
     * <p>In general, this method does not need to be overridden by subclasses.
     * All Look and Feel rendering code should reside in the {@code paint} method.
     *
     * <p>
     *  通知此UI代理重新绘制指定的组件。此方法绘制组件背景,然后调用{@link #paint(SynthContext,Graphics)}方法。
     * 
     *  <p>通常,此方法不需要被子类覆盖。所有Look and Feel渲染代码应该驻留在{@code paint}方法中。
     * 
     * 
     * @param g the {@code Graphics} object used for painting
     * @param c the component being painted
     * @see #paint(SynthContext,Graphics)
     */
    @Override
    public void update(Graphics g, JComponent c) {
        SynthContext context = getContext(c);

        SynthLookAndFeel.update(context, g);
        context.getPainter().paintComboBoxBackground(context, g, 0, 0,
                                                  c.getWidth(), c.getHeight());
        paint(context, g);
        context.dispose();
    }

    /**
     * Paints the specified component according to the Look and Feel.
     * <p>This method is not used by Synth Look and Feel.
     * Painting is handled by the {@link #paint(SynthContext,Graphics)} method.
     *
     * <p>
     *  根据外观来绘制指定的组件。 <p>此方法不被Synth Look and Feel使用。绘画由{@link #paint(SynthContext,Graphics)}方法处理。
     * 
     * 
     * @param g the {@code Graphics} object used for painting
     * @param c the component being painted
     * @see #paint(SynthContext,Graphics)
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        SynthContext context = getContext(c);

        paint(context, g);
        context.dispose();
    }

    /**
     * Paints the specified component.
     *
     * <p>
     *  绘制指定的组件。
     * 
     * 
     * @param context context for the component being painted
     * @param g the {@code Graphics} object used for painting
     * @see #update(Graphics,JComponent)
     */
    protected void paint(SynthContext context, Graphics g) {
        hasFocus = comboBox.hasFocus();
        if ( !comboBox.isEditable() ) {
            Rectangle r = rectangleForCurrentValue();
            paintCurrentValue(g,r,hasFocus);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintComboBoxBorder(context, g, x, y, w, h);
    }

    /**
     * Paints the currently selected item.
     * <p>
     *  绘制当前选定的项目。
     * 
     */
    @Override
    public void paintCurrentValue(Graphics g,Rectangle bounds,boolean hasFocus) {
        ListCellRenderer renderer = comboBox.getRenderer();
        Component c;

        c = renderer.getListCellRendererComponent(
                listBox, comboBox.getSelectedItem(), -1, false, false );

        // Fix for 4238829: should lay out the JPanel.
        boolean shouldValidate = false;
        if (c instanceof JPanel)  {
            shouldValidate = true;
        }

        if (c instanceof UIResource) {
            c.setName("ComboBox.renderer");
        }

        boolean force = forceOpaque && c instanceof JComponent;
        if (force) {
            ((JComponent)c).setOpaque(false);
        }

        int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
        if (padding != null) {
            x = bounds.x + padding.left;
            y = bounds.y + padding.top;
            w = bounds.width - (padding.left + padding.right);
            h = bounds.height - (padding.top + padding.bottom);
        }

        currentValuePane.paintComponent(g, c, comboBox, x, y, w, h, shouldValidate);

        if (force) {
            ((JComponent)c).setOpaque(true);
        }
    }

    /**
    /* <p>
    /* 
     * @return true if this combo box should act as one big button. Typically
     * only happens when buttonWhenNotEditable is true, and comboBox.isEditable
     * is false.
     */
    private boolean shouldActLikeButton() {
        return buttonWhenNotEditable && !comboBox.isEditable();
    }

    /**
     * Returns the default size of an empty display area of the combo box using
     * the current renderer and font.
     *
     * This method was overridden to use SynthComboBoxRenderer instead of
     * DefaultListCellRenderer as the default renderer when calculating the
     * size of the combo box. This is used in the case of the combo not having
     * any data.
     *
     * <p>
     *  使用当前渲染器和字体返回组合框的空显示区域的默认大小。
     * 
     *  在计算组合框的大小时,将覆盖此方法以使用SynthComboBoxRenderer而不是DefaultListCellRenderer作为默认渲染器。这在组合没有任何数据的情况下使用。
     * 
     * 
     * @return the size of an empty display area
     * @see #getDisplaySize
     */
    @Override
    protected Dimension getDefaultSize() {
        SynthComboBoxRenderer r = new SynthComboBoxRenderer();
        Dimension d = getSizeForComponent(r.getListCellRendererComponent(listBox, " ", -1, false, false));
        return new Dimension(d.width, d.height);
    }

    /**
     * From BasicComboBoxRenderer v 1.18.
     *
     * Be aware that SynthFileChooserUIImpl relies on the fact that the default
     * renderer installed on a Synth combo box is a JLabel. If this is changed,
     * then an assert will fail in SynthFileChooserUIImpl
     * <p>
     * 从BasicComboBoxRenderer v 1.18。
     * 
     *  请注意,SynthFileChooserUIImpl依赖于一个事实,即安装在Synth组合框上的默认渲染器是一个JLabel。
     * 如果这被改变,那么断言将在SynthFileChooserUIImpl中失败。
     * 
     */
    private class SynthComboBoxRenderer extends JLabel implements ListCellRenderer<Object>, UIResource {
        public SynthComboBoxRenderer() {
            super();
            setText(" ");
        }

        @Override
        public String getName() {
            // SynthComboBoxRenderer should have installed Name while constructor is working.
            // The setName invocation in the SynthComboBoxRenderer() constructor doesn't work
            // because of the opaque property is installed in the constructor based on the
            // component name (see GTKStyle.isOpaque())
            String name = super.getName();

            return name == null ? "ComboBox.renderer" : name;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                         int index, boolean isSelected, boolean cellHasFocus) {
            setName("ComboBox.listRenderer");
            SynthLookAndFeel.resetSelectedUI();
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (!useListColors) {
                    SynthLookAndFeel.setSelectedUI(
                         (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(),
                         SynthLabelUI.class), isSelected, cellHasFocus,
                         list.isEnabled(), false);
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setFont(list.getFont());

            if (value instanceof Icon) {
                setIcon((Icon)value);
                setText("");
            } else {
                String text = (value == null) ? " " : value.toString();

                if ("".equals(text)) {
                    text = " ";
                }
                setText(text);
            }

            // The renderer component should inherit the enabled and
            // orientation state of its parent combobox.  This is
            // especially needed for GTK comboboxes, where the
            // ListCellRenderer's state determines the visual state
            // of the combobox.
            if (comboBox != null){
                setEnabled(comboBox.isEnabled());
                setComponentOrientation(comboBox.getComponentOrientation());
            }

            return this;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            SynthLookAndFeel.resetSelectedUI();
        }
    }


    private static class SynthComboBoxEditor
            extends BasicComboBoxEditor.UIResource {

        @Override public JTextField createEditorComponent() {
            JTextField f = new JTextField("", 9);
            f.setName("ComboBox.textField");
            return f;
        }
    }


    /**
     * Handles all the logic for treating the combo as a button when it is
     * not editable, and when shouldActLikeButton() is true. This class is a
     * special ButtonModel, and installed on the arrowButton when appropriate.
     * It also is installed as a mouse listener and mouse motion listener on
     * the combo box. In this way, the state between the button and combo
     * are in sync. Whenever one is "over" both are. Whenever one is pressed,
     * both are.
     * <p>
     *  处理所有的逻辑,当它不可编辑的时候,当shouldActLikeButton()为true时,将组合作为一个按钮。
     * 这个类是一个特殊的ButtonModel,并在适当的时候安装在arrowButton上。它也作为鼠标监听器和鼠标移动侦听器安装在组合框上。这样,按钮和组合之间的状态是同步的。
     * 每当一个人"过",两者都是。每当一个人按下,两者都是。
     * 
     */
    private final class ButtonHandler extends DefaultButtonModel
            implements MouseListener, PopupMenuListener {
        /**
         * Indicates that the mouse is over the combo or the arrow button.
         * This field only has meaning if buttonWhenNotEnabled is true.
         * <p>
         *  表示鼠标位于组合框或箭头按钮上方。如果buttonWhenNotEnabled为true,此字段只有意义。
         * 
         */
        private boolean over;
        /**
         * Indicates that the combo or arrow button has been pressed. This
         * field only has meaning if buttonWhenNotEnabled is true.
         * <p>
         *  表示已按下组合键或箭头按钮。如果buttonWhenNotEnabled为true,此字段只有意义。
         * 
         */
        private boolean pressed;

        //------------------------------------------------------------------
        // State Methods
        //------------------------------------------------------------------

        /**
         * <p>Updates the internal "pressed" state. If shouldActLikeButton()
         * is true, and if this method call will change the internal state,
         * then the combo and button will be repainted.</p>
         *
         * <p>Note that this method is called either when a press event
         * occurs on the combo box, or on the arrow button.</p>
         * <p>
         *  <p>更新内部"按下"状态。如果shouldActLikeButton()为true,并且此方法调用将更改内部状态,则将重新绘制组合框和按钮。</p>
         * 
         *  <p>请注意,当组合框或箭头按钮上发生按下事件时,将调用此方法。</p>
         * 
         */
        private void updatePressed(boolean p) {
            this.pressed = p && isEnabled();
            if (shouldActLikeButton()) {
                comboBox.repaint();
            }
        }

        /**
         * <p>Updates the internal "over" state. If shouldActLikeButton()
         * is true, and if this method call will change the internal state,
         * then the combo and button will be repainted.</p>
         *
         * <p>Note that this method is called either when a mouseover/mouseoff event
         * occurs on the combo box, or on the arrow button.</p>
         * <p>
         *  <p>更新内部的"覆盖"状态。如果shouldActLikeButton()为true,并且此方法调用将更改内部状态,则将重新绘制组合框和按钮。</p>
         * 
         * <p>请注意,当组合框或箭头按钮上发生mouseover / mouseoff事件时,将调用此方法。</p>
         * 
         */
        private void updateOver(boolean o) {
            boolean old = isRollover();
            this.over = o && isEnabled();
            boolean newo = isRollover();
            if (shouldActLikeButton() && old != newo) {
                comboBox.repaint();
            }
        }

        //------------------------------------------------------------------
        // DefaultButtonModel Methods
        //------------------------------------------------------------------

        /**
         * @inheritDoc
         *
         * Ensures that isPressed() will return true if the combo is pressed,
         * or the arrowButton is pressed, <em>or</em> if the combo popup is
         * visible. This is the case because a combo box looks pressed when
         * the popup is visible, and so should the arrow button.
         * <p>
         *  @inheritDoc
         * 
         *  如果组合按钮可见,确保isPressed()将返回true,或按下arrowButton,则返回<em> </em>。这是这种情况,因为当弹出窗口可见时,组合框看起来按下,箭头按钮也应该如此。
         * 
         */
        @Override
        public boolean isPressed() {
            boolean b = shouldActLikeButton() ? pressed : super.isPressed();
            return b || (pressedWhenPopupVisible && comboBox.isPopupVisible());
        }

        /**
         * @inheritDoc
         *
         * Ensures that the armed state is in sync with the pressed state
         * if shouldActLikeButton is true. Without this method, the arrow
         * button will not look pressed when the popup is open, regardless
         * of the result of isPressed() alone.
         * <p>
         *  @inheritDoc
         * 
         *  如果shouldActLikeButton为true,确保布防状态与按下状态同步。没有这个方法,当弹出窗口打开时,不管isPressed()的结果如何,箭头按钮看起来不会按下。
         * 
         */
        @Override
        public boolean isArmed() {
            boolean b = shouldActLikeButton() ||
                        (pressedWhenPopupVisible && comboBox.isPopupVisible());
            return b ? isPressed() : super.isArmed();
        }

        /**
         * @inheritDoc
         *
         * Ensures that isRollover() will return true if the combo is
         * rolled over, or the arrowButton is rolled over.
         * <p>
         *  @inheritDoc
         * 
         *  如果组合滚动或arrowButton翻转,确保isRollover()将返回true。
         * 
         */
        @Override
        public boolean isRollover() {
            return shouldActLikeButton() ? over : super.isRollover();
        }

        /**
         * @inheritDoc
         *
         * Forwards pressed states to the internal "pressed" field
         * <p>
         *  @inheritDoc
         * 
         *  将按下的状态转到内部"按下"字段
         * 
         */
        @Override
        public void setPressed(boolean b) {
            super.setPressed(b);
            updatePressed(b);
        }

        /**
         * @inheritDoc
         *
         * Forwards rollover states to the internal "over" field
         * <p>
         *  @inheritDoc
         * 
         *  将翻转状态转换为内部"over"字段
         * 
         */
        @Override
        public void setRollover(boolean b) {
            super.setRollover(b);
            updateOver(b);
        }

        //------------------------------------------------------------------
        // MouseListener/MouseMotionListener Methods
        //------------------------------------------------------------------

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            updateOver(true);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            updateOver(false);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            updatePressed(true);
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            updatePressed(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        //------------------------------------------------------------------
        // PopupMenuListener Methods
        //------------------------------------------------------------------

        /**
         * @inheritDoc
         *
         * Ensures that the combo box is repainted when the popup is closed.
         * This avoids a bug where clicking off the combo wasn't causing a repaint,
         * and thus the combo box still looked pressed even when it was not.
         *
         * This bug was only noticed when acting as a button, but may be generally
         * present. If so, remove the if() block
         * <p>
         *  @inheritDoc
         * 
         *  确保在弹出窗口关闭时重新绘制组合框。这避免了一个错误,即单击组合不会导致重绘,因此组合框仍然看起来即使不是。
         * 
         *  这个错误只是作为一个按钮时注意到,但可能一般存在。如果是这样,请删除if()块
         * 
         */
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            if (shouldActLikeButton() || pressedWhenPopupVisible) {
                comboBox.repaint();
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    }

    /**
     * Handler for repainting combo when editor component gains/looses focus
     * <p>
     */
    private static class EditorFocusHandler implements FocusListener,
            PropertyChangeListener {
        private JComboBox comboBox;
        private ComboBoxEditor editor = null;
        private Component editorComponent = null;

        private EditorFocusHandler(JComboBox comboBox) {
            this.comboBox = comboBox;
            editor = comboBox.getEditor();
            if (editor != null){
                editorComponent = editor.getEditorComponent();
                if (editorComponent != null){
                    editorComponent.addFocusListener(this);
                }
            }
            comboBox.addPropertyChangeListener("editor",this);
        }

        public void unregister(){
            comboBox.removePropertyChangeListener(this);
            if (editorComponent!=null){
                editorComponent.removeFocusListener(this);
            }
        }

        /** Invoked when a component gains the keyboard focus. */
        public void focusGained(FocusEvent e) {
            // repaint whole combo on focus gain
            comboBox.repaint();
        }

        /** Invoked when a component loses the keyboard focus. */
        public void focusLost(FocusEvent e) {
            // repaint whole combo on focus loss
            comboBox.repaint();
        }

        /**
         * Called when the combos editor changes
         *
         * <p>
         *  当编辑器组件获得/失去焦点时重新绘制组合的处理程序
         * 
         * 
         * @param evt A PropertyChangeEvent object describing the event source and
         *            the property that has changed.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            ComboBoxEditor newEditor = comboBox.getEditor();
            if (editor != newEditor){
                if (editorComponent!=null){
                    editorComponent.removeFocusListener(this);
                }
                editor = newEditor;
                if (editor != null){
                    editorComponent = editor.getEditorComponent();
                    if (editorComponent != null){
                        editorComponent.addFocusListener(this);
                    }
                }
            }
        }
    }
}
