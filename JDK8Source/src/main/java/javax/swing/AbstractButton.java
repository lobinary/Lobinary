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
import java.awt.image.*;
import java.text.*;
import java.awt.geom.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.accessibility.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.plaf.basic.*;
import java.util.*;

/**
 * Defines common behaviors for buttons and menu items.
 * <p>
 * Buttons can be configured, and to some degree controlled, by
 * <code><a href="Action.html">Action</a></code>s.  Using an
 * <code>Action</code> with a button has many benefits beyond directly
 * configuring a button.  Refer to <a href="Action.html#buttonActions">
 * Swing Components Supporting <code>Action</code></a> for more
 * details, and you can find more information in <a
 * href="https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html">How
 * to Use Actions</a>, a section in <em>The Java Tutorial</em>.
 * <p>
 * For further information see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">How to Use Buttons, Check Boxes, and Radio Buttons</a>,
 * a section in <em>The Java Tutorial</em>.
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
 *  定义按钮和菜单项的常见行为。
 * <p>
 *  可以通过<code> <a href="Action.html">操作</a> </code>配置按钮,并在某种程度上控制。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * <p>
 *  有关详情,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">如何使用按钮,复选框和
 * 单选按钮</a>,在Java教程</em>中。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Jeff Dinkins
 */
public abstract class AbstractButton extends JComponent implements ItemSelectable, SwingConstants {

    // *********************************
    // ******* Button properties *******
    // *********************************

    /** Identifies a change in the button model. */
    public static final String MODEL_CHANGED_PROPERTY = "model";
    /** Identifies a change in the button's text. */
    public static final String TEXT_CHANGED_PROPERTY = "text";
    /** Identifies a change to the button's mnemonic. */
    public static final String MNEMONIC_CHANGED_PROPERTY = "mnemonic";

    // Text positioning and alignment
    /** Identifies a change in the button's margins. */
    public static final String MARGIN_CHANGED_PROPERTY = "margin";
    /** Identifies a change in the button's vertical alignment. */
    public static final String VERTICAL_ALIGNMENT_CHANGED_PROPERTY = "verticalAlignment";
    /** Identifies a change in the button's horizontal alignment. */
    public static final String HORIZONTAL_ALIGNMENT_CHANGED_PROPERTY = "horizontalAlignment";

    /** Identifies a change in the button's vertical text position. */
    public static final String VERTICAL_TEXT_POSITION_CHANGED_PROPERTY = "verticalTextPosition";
    /** Identifies a change in the button's horizontal text position. */
    public static final String HORIZONTAL_TEXT_POSITION_CHANGED_PROPERTY = "horizontalTextPosition";

    // Paint options
    /**
     * Identifies a change to having the border drawn,
     * or having it not drawn.
     * <p>
     *  标识对已绘制边框或未绘制边框的更改。
     * 
     */
    public static final String BORDER_PAINTED_CHANGED_PROPERTY = "borderPainted";
    /**
     * Identifies a change to having the border highlighted when focused,
     * or not.
     * <p>
     *  标识在聚焦时是否突出显示边框的更改。
     * 
     */
    public static final String FOCUS_PAINTED_CHANGED_PROPERTY = "focusPainted";
    /**
     * Identifies a change from rollover enabled to disabled or back
     * to enabled.
     * <p>
     * 标识从启用翻转到停用或返回启用的更改。
     * 
     */
    public static final String ROLLOVER_ENABLED_CHANGED_PROPERTY = "rolloverEnabled";
    /**
     * Identifies a change to having the button paint the content area.
     * <p>
     *  标识将按钮绘制到内容区域的更改。
     * 
     */
    public static final String CONTENT_AREA_FILLED_CHANGED_PROPERTY = "contentAreaFilled";

    // Icons
    /** Identifies a change to the icon that represents the button. */
    public static final String ICON_CHANGED_PROPERTY = "icon";

    /**
     * Identifies a change to the icon used when the button has been
     * pressed.
     * <p>
     *  标识按钮被按下时使用的图标的更改。
     * 
     */
    public static final String PRESSED_ICON_CHANGED_PROPERTY = "pressedIcon";
    /**
     * Identifies a change to the icon used when the button has
     * been selected.
     * <p>
     *  标识在选择按钮时使用的图标的更改。
     * 
     */
    public static final String SELECTED_ICON_CHANGED_PROPERTY = "selectedIcon";

    /**
     * Identifies a change to the icon used when the cursor is over
     * the button.
     * <p>
     *  标识当光标在按钮上方时使用的图标的更改。
     * 
     */
    public static final String ROLLOVER_ICON_CHANGED_PROPERTY = "rolloverIcon";
    /**
     * Identifies a change to the icon used when the cursor is
     * over the button and it has been selected.
     * <p>
     *  标识当光标在按钮上方并且已被选择时使用的图标的更改。
     * 
     */
    public static final String ROLLOVER_SELECTED_ICON_CHANGED_PROPERTY = "rolloverSelectedIcon";

    /**
     * Identifies a change to the icon used when the button has
     * been disabled.
     * <p>
     *  标识对按钮已禁用时使用的图标的更改。
     * 
     */
    public static final String DISABLED_ICON_CHANGED_PROPERTY = "disabledIcon";
    /**
     * Identifies a change to the icon used when the button has been
     * disabled and selected.
     * <p>
     *  标识对按钮已禁用和选择时使用的图标的更改。
     * 
     */
    public static final String DISABLED_SELECTED_ICON_CHANGED_PROPERTY = "disabledSelectedIcon";


    /** The data model that determines the button's state. */
    protected ButtonModel model                = null;

    private String     text                    = ""; // for BeanBox
    private Insets     margin                  = null;
    private Insets     defaultMargin           = null;

    // Button icons
    // PENDING(jeff) - hold icons in an array
    private Icon       defaultIcon             = null;
    private Icon       pressedIcon             = null;
    private Icon       disabledIcon            = null;

    private Icon       selectedIcon            = null;
    private Icon       disabledSelectedIcon    = null;

    private Icon       rolloverIcon            = null;
    private Icon       rolloverSelectedIcon    = null;

    // Display properties
    private boolean    paintBorder             = true;
    private boolean    paintFocus              = true;
    private boolean    rolloverEnabled         = false;
    private boolean    contentAreaFilled         = true;

    // Icon/Label Alignment
    private int        verticalAlignment       = CENTER;
    private int        horizontalAlignment     = CENTER;

    private int        verticalTextPosition    = CENTER;
    private int        horizontalTextPosition  = TRAILING;

    private int        iconTextGap             = 4;

    private int        mnemonic;
    private int        mnemonicIndex           = -1;

    private long       multiClickThreshhold    = 0;

    private boolean    borderPaintedSet        = false;
    private boolean    rolloverEnabledSet      = false;
    private boolean    iconTextGapSet          = false;
    private boolean    contentAreaFilledSet    = false;

    // Whether or not we've set the LayoutManager.
    private boolean setLayout = false;

    // This is only used by JButton, promoted to avoid an extra
    // boolean field in JButton
    boolean defaultCapable = true;

    /**
     * Combined listeners: ActionListener, ChangeListener, ItemListener.
     * <p>
     *  组合侦听器：ActionListener,ChangeListener,ItemListener。
     * 
     */
    private Handler handler;

    /**
     * The button model's <code>changeListener</code>.
     * <p>
     *  按钮模型的<code> changeListener </code>。
     * 
     */
    protected ChangeListener changeListener = null;
    /**
     * The button model's <code>ActionListener</code>.
     * <p>
     *  按钮模型的<code> ActionListener </code>。
     * 
     */
    protected ActionListener actionListener = null;
    /**
     * The button model's <code>ItemListener</code>.
     * <p>
     *  按钮模型的<code> ItemListener </code>。
     * 
     */
    protected ItemListener itemListener = null;

    /**
     * Only one <code>ChangeEvent</code> is needed per button
     * instance since the
     * event's only state is the source property.  The source of events
     * generated is always "this".
     * <p>
     *  由于事件的唯一状态是源属性,因此每个按钮实例只需要一个<code> ChangeEvent </code>。生成的事件的源始终是"this"。
     * 
     */
    protected transient ChangeEvent changeEvent;

    private boolean hideActionText = false;

    /**
     * Sets the <code>hideActionText</code> property, which determines
     * whether the button displays text from the <code>Action</code>.
     * This is useful only if an <code>Action</code> has been
     * installed on the button.
     *
     * <p>
     *  设置<code> hideActionText </code>属性,该属性确定按钮是否显示来自<code> Action </code>的文本。
     * 这只有在按钮上安装了<code> Action </code>时才有用。
     * 
     * 
     * @param hideActionText <code>true</code> if the button's
     *                       <code>text</code> property should not reflect
     *                       that of the <code>Action</code>; the default is
     *                       <code>false</code>
     * @see <a href="Action.html#buttonActions">Swing Components Supporting
     *      <code>Action</code></a>
     * @since 1.6
     * @beaninfo
     *        bound: true
     *    expert: true
     *  description: Whether the text of the button should come from
     *               the <code>Action</code>.
     */
    public void setHideActionText(boolean hideActionText) {
        if (hideActionText != this.hideActionText) {
            this.hideActionText = hideActionText;
            if (getAction() != null) {
                setTextFromAction(getAction(), false);
            }
            firePropertyChange("hideActionText", !hideActionText,
                               hideActionText);
        }
    }

    /**
     * Returns the value of the <code>hideActionText</code> property, which
     * determines whether the button displays text from the
     * <code>Action</code>.  This is useful only if an <code>Action</code>
     * has been installed on the button.
     *
     * <p>
     *  返回<code> hideActionText </code>属性的值,该属性确定按钮是否显示来自<code> Action </code>的文本。
     * 这只有在按钮上安装了<code> Action </code>时才有用。
     * 
     * 
     * @return <code>true</code> if the button's <code>text</code>
     *         property should not reflect that of the
     *         <code>Action</code>; the default is <code>false</code>
     * @since 1.6
     */
    public boolean getHideActionText() {
        return hideActionText;
    }

    /**
     * Returns the button's text.
     * <p>
     *  返回按钮的文本。
     * 
     * 
     * @return the buttons text
     * @see #setText
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the button's text.
     * <p>
     *  设置按钮的文本。
     * 
     * 
     * @param text the string used to set the text
     * @see #getText
     * @beaninfo
     *        bound: true
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The button's text.
     */
    public void setText(String text) {
        String oldValue = this.text;
        this.text = text;
        firePropertyChange(TEXT_CHANGED_PROPERTY, oldValue, text);
        updateDisplayedMnemonicIndex(text, getMnemonic());

        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, text);
        }
        if (text == null || oldValue == null || !text.equals(oldValue)) {
            revalidate();
            repaint();
        }
    }


    /**
     * Returns the state of the button. True if the
     * toggle button is selected, false if it's not.
     * <p>
     * 返回按钮的状态。如果选择切换按钮,则为True,否则为false。
     * 
     * 
     * @return true if the toggle button is selected, otherwise false
     */
    public boolean isSelected() {
        return model.isSelected();
    }

    /**
     * Sets the state of the button. Note that this method does not
     * trigger an <code>actionEvent</code>.
     * Call <code>doClick</code> to perform a programmatic action change.
     *
     * <p>
     *  设置按钮的状态。请注意,此方法不会触发<code> actionEvent </code>。调用<code> doClick </code>可执行程序化操作更改。
     * 
     * 
     * @param b  true if the button is selected, otherwise false
     */
    public void setSelected(boolean b) {
        boolean oldValue = isSelected();

        // TIGER - 4840653
        // Removed code which fired an AccessibleState.SELECTED
        // PropertyChangeEvent since this resulted in two
        // identical events being fired since
        // AbstractButton.fireItemStateChanged also fires the
        // same event. This caused screen readers to speak the
        // name of the item twice.

        model.setSelected(b);
    }

    /**
     * Programmatically perform a "click". This does the same
     * thing as if the user had pressed and released the button.
     * <p>
     *  以编程方式执行"点击"。这做同样的事情,如果用户按下并释放按钮。
     * 
     */
    public void doClick() {
        doClick(68);
    }

    /**
     * Programmatically perform a "click". This does the same
     * thing as if the user had pressed and released the button.
     * The button stays visually "pressed" for <code>pressTime</code>
     *  milliseconds.
     *
     * <p>
     *  以编程方式执行"点击"。这做同样的事情,如果用户按下并释放按钮。对于<code> pressTime </code>毫秒,按钮保持视觉上"按下"。
     * 
     * 
     * @param pressTime the time to "hold down" the button, in milliseconds
     */
    public void doClick(int pressTime) {
        Dimension size = getSize();
        model.setArmed(true);
        model.setPressed(true);
        paintImmediately(new Rectangle(0,0, size.width, size.height));
        try {
            Thread.currentThread().sleep(pressTime);
        } catch(InterruptedException ie) {
        }
        model.setPressed(false);
        model.setArmed(false);
    }

    /**
     * Sets space for margin between the button's border and
     * the label. Setting to <code>null</code> will cause the button to
     * use the default margin.  The button's default <code>Border</code>
     * object will use this value to create the proper margin.
     * However, if a non-default border is set on the button,
     * it is that <code>Border</code> object's responsibility to create the
     * appropriate margin space (else this property will
     * effectively be ignored).
     *
     * <p>
     *  在按钮边框和标签之间设置边距空间。设置为<code> null </code>将使按钮使用默认边距。按钮的默认<code> Border </code>对象将使用此值创建适当的边距。
     * 但是,如果在按钮上设置了非默认边框,那么<code> Border </code>对象负责创建适当的边距空间(否则此属性将被有效地忽略)。
     * 
     * 
     * @param m the space between the border and the label
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The space between the button's border and the label.
     */
    public void setMargin(Insets m) {
        // Cache the old margin if it comes from the UI
        if(m instanceof UIResource) {
            defaultMargin = m;
        } else if(margin instanceof UIResource) {
            defaultMargin = margin;
        }

        // If the client passes in a null insets, restore the margin
        // from the UI if possible
        if(m == null && defaultMargin != null) {
            m = defaultMargin;
        }

        Insets old = margin;
        margin = m;
        firePropertyChange(MARGIN_CHANGED_PROPERTY, old, m);
        if (old == null || !old.equals(m)) {
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the margin between the button's border and
     * the label.
     *
     * <p>
     *  返回按钮边框和标签之间的边距。
     * 
     * 
     * @return an <code>Insets</code> object specifying the margin
     *          between the botton's border and the label
     * @see #setMargin
     */
    public Insets getMargin() {
        return (margin == null) ? null : (Insets) margin.clone();
    }

    /**
     * Returns the default icon.
     * <p>
     *  返回默认图标。
     * 
     * 
     * @return the default <code>Icon</code>
     * @see #setIcon
     */
    public Icon getIcon() {
        return defaultIcon;
    }

    /**
     * Sets the button's default icon. This icon is
     * also used as the "pressed" and "disabled" icon if
     * there is no explicitly set pressed icon.
     *
     * <p>
     *  设置按钮的默认图标。如果没有明确设置按下的图标,则该图标也用作"按下"和"禁用"图标。
     * 
     * 
     * @param defaultIcon the icon used as the default image
     * @see #getIcon
     * @see #setPressedIcon
     * @beaninfo
     *           bound: true
     *       attribute: visualUpdate true
     *     description: The button's default icon
     */
    public void setIcon(Icon defaultIcon) {
        Icon oldValue = this.defaultIcon;
        this.defaultIcon = defaultIcon;

        /* If the default icon has really changed and we had
         * generated the disabled icon for this component,
         * (i.e. setDisabledIcon() was never called) then
         * clear the disabledIcon field.
         * <p>
         *  生成此组件的禁用图标(即,从未调用setDisabledIcon()),然后清除disabledIcon字段。
         * 
         */
        if (defaultIcon != oldValue && (disabledIcon instanceof UIResource)) {
            disabledIcon = null;
        }

        firePropertyChange(ICON_CHANGED_PROPERTY, oldValue, defaultIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, defaultIcon);
        }
        if (defaultIcon != oldValue) {
            if (defaultIcon == null || oldValue == null ||
                defaultIcon.getIconWidth() != oldValue.getIconWidth() ||
                defaultIcon.getIconHeight() != oldValue.getIconHeight()) {
                revalidate();
            }
            repaint();
        }
    }

    /**
     * Returns the pressed icon for the button.
     * <p>
     *  返回按钮的按下图标。
     * 
     * 
     * @return the <code>pressedIcon</code> property
     * @see #setPressedIcon
     */
    public Icon getPressedIcon() {
        return pressedIcon;
    }

    /**
     * Sets the pressed icon for the button.
     * <p>
     *  设置按钮的按下图标。
     * 
     * 
     * @param pressedIcon the icon used as the "pressed" image
     * @see #getPressedIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The pressed icon for the button.
     */
    public void setPressedIcon(Icon pressedIcon) {
        Icon oldValue = this.pressedIcon;
        this.pressedIcon = pressedIcon;
        firePropertyChange(PRESSED_ICON_CHANGED_PROPERTY, oldValue, pressedIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, pressedIcon);
        }
        if (pressedIcon != oldValue) {
            if (getModel().isPressed()) {
                repaint();
            }
        }
    }

    /**
     * Returns the selected icon for the button.
     * <p>
     *  返回按钮的选定图标。
     * 
     * 
     * @return the <code>selectedIcon</code> property
     * @see #setSelectedIcon
     */
    public Icon getSelectedIcon() {
        return selectedIcon;
    }

    /**
     * Sets the selected icon for the button.
     * <p>
     * 设置按钮的选定图标。
     * 
     * 
     * @param selectedIcon the icon used as the "selected" image
     * @see #getSelectedIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The selected icon for the button.
     */
    public void setSelectedIcon(Icon selectedIcon) {
        Icon oldValue = this.selectedIcon;
        this.selectedIcon = selectedIcon;

        /* If the default selected icon has really changed and we had
         * generated the disabled selected icon for this component,
         * (i.e. setDisabledSelectedIcon() was never called) then
         * clear the disabledSelectedIcon field.
         * <p>
         *  生成此组件的已禁用的选定图标(即,从未调用setDisabledSelectedIcon()),然后清除disabledSelectedIcon字段。
         * 
         */
        if (selectedIcon != oldValue &&
            disabledSelectedIcon instanceof UIResource) {

            disabledSelectedIcon = null;
        }

        firePropertyChange(SELECTED_ICON_CHANGED_PROPERTY, oldValue, selectedIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, selectedIcon);
        }
        if (selectedIcon != oldValue) {
            if (isSelected()) {
                repaint();
            }
        }
    }

    /**
     * Returns the rollover icon for the button.
     * <p>
     *  返回按钮的翻转图标。
     * 
     * 
     * @return the <code>rolloverIcon</code> property
     * @see #setRolloverIcon
     */
    public Icon getRolloverIcon() {
        return rolloverIcon;
    }

    /**
     * Sets the rollover icon for the button.
     * <p>
     *  设置按钮的翻转图标。
     * 
     * 
     * @param rolloverIcon the icon used as the "rollover" image
     * @see #getRolloverIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The rollover icon for the button.
     */
    public void setRolloverIcon(Icon rolloverIcon) {
        Icon oldValue = this.rolloverIcon;
        this.rolloverIcon = rolloverIcon;
        firePropertyChange(ROLLOVER_ICON_CHANGED_PROPERTY, oldValue, rolloverIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, rolloverIcon);
        }
        setRolloverEnabled(true);
        if (rolloverIcon != oldValue) {
            // No way to determine whether we are currently in
            // a rollover state, so repaint regardless
            repaint();
        }

    }

    /**
     * Returns the rollover selection icon for the button.
     * <p>
     *  返回按钮的翻转选择图标。
     * 
     * 
     * @return the <code>rolloverSelectedIcon</code> property
     * @see #setRolloverSelectedIcon
     */
    public Icon getRolloverSelectedIcon() {
        return rolloverSelectedIcon;
    }

    /**
     * Sets the rollover selected icon for the button.
     * <p>
     *  设置按钮的翻转选择图标。
     * 
     * 
     * @param rolloverSelectedIcon the icon used as the
     *          "selected rollover" image
     * @see #getRolloverSelectedIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The rollover selected icon for the button.
     */
    public void setRolloverSelectedIcon(Icon rolloverSelectedIcon) {
        Icon oldValue = this.rolloverSelectedIcon;
        this.rolloverSelectedIcon = rolloverSelectedIcon;
        firePropertyChange(ROLLOVER_SELECTED_ICON_CHANGED_PROPERTY, oldValue, rolloverSelectedIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, rolloverSelectedIcon);
        }
        setRolloverEnabled(true);
        if (rolloverSelectedIcon != oldValue) {
            // No way to determine whether we are currently in
            // a rollover state, so repaint regardless
            if (isSelected()) {
                repaint();
            }
        }
    }

    /**
     * Returns the icon used by the button when it's disabled.
     * If no disabled icon has been set this will forward the call to
     * the look and feel to construct an appropriate disabled Icon.
     * <p>
     * Some look and feels might not render the disabled Icon, in which
     * case they will ignore this.
     *
     * <p>
     *  返回按钮被禁用时使用的图标。如果没有设置禁用图标,这将转发呼叫的外观和感觉来构造适当的禁用图标。
     * <p>
     *  一些外观和感觉可能不会呈现禁用的图标,在这种情况下,他们会忽略这一点。
     * 
     * 
     * @return the <code>disabledIcon</code> property
     * @see #getPressedIcon
     * @see #setDisabledIcon
     * @see javax.swing.LookAndFeel#getDisabledIcon
     */
    @Transient
    public Icon getDisabledIcon() {
        if (disabledIcon == null) {
            disabledIcon = UIManager.getLookAndFeel().getDisabledIcon(this, getIcon());
            if (disabledIcon != null) {
                firePropertyChange(DISABLED_ICON_CHANGED_PROPERTY, null, disabledIcon);
            }
        }
        return disabledIcon;
    }

    /**
     * Sets the disabled icon for the button.
     * <p>
     *  设置按钮的禁用图标。
     * 
     * 
     * @param disabledIcon the icon used as the disabled image
     * @see #getDisabledIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The disabled icon for the button.
     */
    public void setDisabledIcon(Icon disabledIcon) {
        Icon oldValue = this.disabledIcon;
        this.disabledIcon = disabledIcon;
        firePropertyChange(DISABLED_ICON_CHANGED_PROPERTY, oldValue, disabledIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, disabledIcon);
        }
        if (disabledIcon != oldValue) {
            if (!isEnabled()) {
                repaint();
            }
        }
    }

    /**
     * Returns the icon used by the button when it's disabled and selected.
     * If no disabled selection icon has been set, this will forward
     * the call to the LookAndFeel to construct an appropriate disabled
     * Icon from the selection icon if it has been set and to
     * <code>getDisabledIcon()</code> otherwise.
     * <p>
     * Some look and feels might not render the disabled selected Icon, in
     * which case they will ignore this.
     *
     * <p>
     *  返回按钮被禁用和选择时使用的图标。
     * 如果没有设置禁用的选择图标,这将转发对LookAndFeel的调用,以从选择图标(如果已设置)和<code> getDisabledIcon()</code>构造适当的禁用图标。
     * <p>
     *  一些外观和感觉可能不会呈现禁用的选定图标,在这种情况下,他们将忽略此。
     * 
     * 
     * @return the <code>disabledSelectedIcon</code> property
     * @see #getDisabledIcon
     * @see #setDisabledSelectedIcon
     * @see javax.swing.LookAndFeel#getDisabledSelectedIcon
     */
    public Icon getDisabledSelectedIcon() {
        if (disabledSelectedIcon == null) {
             if (selectedIcon != null) {
                 disabledSelectedIcon = UIManager.getLookAndFeel().
                         getDisabledSelectedIcon(this, getSelectedIcon());
             } else {
                 return getDisabledIcon();
             }
        }
        return disabledSelectedIcon;
    }

    /**
     * Sets the disabled selection icon for the button.
     * <p>
     *  设置按钮的禁用选择图标。
     * 
     * 
     * @param disabledSelectedIcon the icon used as the disabled
     *          selection image
     * @see #getDisabledSelectedIcon
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The disabled selection icon for the button.
     */
    public void setDisabledSelectedIcon(Icon disabledSelectedIcon) {
        Icon oldValue = this.disabledSelectedIcon;
        this.disabledSelectedIcon = disabledSelectedIcon;
        firePropertyChange(DISABLED_SELECTED_ICON_CHANGED_PROPERTY, oldValue, disabledSelectedIcon);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, disabledSelectedIcon);
        }
        if (disabledSelectedIcon != oldValue) {
            if (disabledSelectedIcon == null || oldValue == null ||
                disabledSelectedIcon.getIconWidth() != oldValue.getIconWidth() ||
                disabledSelectedIcon.getIconHeight() != oldValue.getIconHeight()) {
                revalidate();
            }
            if (!isEnabled() && isSelected()) {
                repaint();
            }
        }
    }

    /**
     * Returns the vertical alignment of the text and icon.
     *
     * <p>
     *  返回文本和图标的垂直对齐方式。
     * 
     * 
     * @return the <code>verticalAlignment</code> property, one of the
     *          following values:
     * <ul>
     * <li>{@code SwingConstants.CENTER} (the default)
     * <li>{@code SwingConstants.TOP}
     * <li>{@code SwingConstants.BOTTOM}
     * </ul>
     */
    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * Sets the vertical alignment of the icon and text.
     * <p>
     *  设置图标和文本的垂直对齐方式。
     * 
     * 
     * @param alignment one of the following values:
     * <ul>
     * <li>{@code SwingConstants.CENTER} (the default)
     * <li>{@code SwingConstants.TOP}
     * <li>{@code SwingConstants.BOTTOM}
     * </ul>
     * @throws IllegalArgumentException if the alignment is not one of the legal
     *         values listed above
     * @beaninfo
     *        bound: true
     *         enum: TOP    SwingConstants.TOP
     *               CENTER SwingConstants.CENTER
     *               BOTTOM  SwingConstants.BOTTOM
     *    attribute: visualUpdate true
     *  description: The vertical alignment of the icon and text.
     */
    public void setVerticalAlignment(int alignment) {
        if (alignment == verticalAlignment) return;
        int oldValue = verticalAlignment;
        verticalAlignment = checkVerticalKey(alignment, "verticalAlignment");
        firePropertyChange(VERTICAL_ALIGNMENT_CHANGED_PROPERTY, oldValue, verticalAlignment);         repaint();
    }

    /**
     * Returns the horizontal alignment of the icon and text.
     * {@code AbstractButton}'s default is {@code SwingConstants.CENTER},
     * but subclasses such as {@code JCheckBox} may use a different default.
     *
     * <p>
     *  返回图标和文本的水平对齐方式。
     *  {@code AbstractButton}的默认值为{@code SwingConstants.CENTER},但是子类(例如{@code JCheckBox})可能使用不同的默认值。
     * 
     * 
     * @return the <code>horizontalAlignment</code> property,
     *             one of the following values:
     * <ul>
     *   <li>{@code SwingConstants.RIGHT}
     *   <li>{@code SwingConstants.LEFT}
     *   <li>{@code SwingConstants.CENTER}
     *   <li>{@code SwingConstants.LEADING}
     *   <li>{@code SwingConstants.TRAILING}
     * </ul>
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the horizontal alignment of the icon and text.
     * {@code AbstractButton}'s default is {@code SwingConstants.CENTER},
     * but subclasses such as {@code JCheckBox} may use a different default.
     *
     * <p>
     * 设置图标和文本的水平对齐方式。
     *  {@code AbstractButton}的默认值为{@code SwingConstants.CENTER},但是子类(例如{@code JCheckBox})可能使用不同的默认值。
     * 
     * 
     * @param alignment the alignment value, one of the following values:
     * <ul>
     *   <li>{@code SwingConstants.RIGHT}
     *   <li>{@code SwingConstants.LEFT}
     *   <li>{@code SwingConstants.CENTER}
     *   <li>{@code SwingConstants.LEADING}
     *   <li>{@code SwingConstants.TRAILING}
     * </ul>
     * @throws IllegalArgumentException if the alignment is not one of the
     *         valid values
     * @beaninfo
     *        bound: true
     *         enum: LEFT     SwingConstants.LEFT
     *               CENTER   SwingConstants.CENTER
     *               RIGHT    SwingConstants.RIGHT
     *               LEADING  SwingConstants.LEADING
     *               TRAILING SwingConstants.TRAILING
     *    attribute: visualUpdate true
     *  description: The horizontal alignment of the icon and text.
     */
    public void setHorizontalAlignment(int alignment) {
        if (alignment == horizontalAlignment) return;
        int oldValue = horizontalAlignment;
        horizontalAlignment = checkHorizontalKey(alignment,
                                                 "horizontalAlignment");
        firePropertyChange(HORIZONTAL_ALIGNMENT_CHANGED_PROPERTY,
                           oldValue, horizontalAlignment);
        repaint();
    }


    /**
     * Returns the vertical position of the text relative to the icon.
     * <p>
     *  返回文本相对于图标的垂直位置。
     * 
     * 
     * @return the <code>verticalTextPosition</code> property,
     *          one of the following values:
     * <ul>
     * <li>{@code SwingConstants.CENTER} (the default)
     * <li>{@code SwingConstants.TOP}
     * <li>{@code SwingConstants.BOTTOM}
     * </ul>
     */
    public int getVerticalTextPosition() {
        return verticalTextPosition;
    }

    /**
     * Sets the vertical position of the text relative to the icon.
     * <p>
     *  设置文本相对于图标的垂直位置。
     * 
     * 
     * @param textPosition  one of the following values:
     * <ul>
     * <li>{@code SwingConstants.CENTER} (the default)
     * <li>{@code SwingConstants.TOP}
     * <li>{@code SwingConstants.BOTTOM}
     * </ul>
     * @beaninfo
     *        bound: true
     *         enum: TOP    SwingConstants.TOP
     *               CENTER SwingConstants.CENTER
     *               BOTTOM SwingConstants.BOTTOM
     *    attribute: visualUpdate true
     *  description: The vertical position of the text relative to the icon.
     */
    public void setVerticalTextPosition(int textPosition) {
        if (textPosition == verticalTextPosition) return;
        int oldValue = verticalTextPosition;
        verticalTextPosition = checkVerticalKey(textPosition, "verticalTextPosition");
        firePropertyChange(VERTICAL_TEXT_POSITION_CHANGED_PROPERTY, oldValue, verticalTextPosition);
        revalidate();
        repaint();
    }

    /**
     * Returns the horizontal position of the text relative to the icon.
     * <p>
     *  返回文本相对于图标的水平位置。
     * 
     * 
     * @return the <code>horizontalTextPosition</code> property,
     *          one of the following values:
     * <ul>
     * <li>{@code SwingConstants.RIGHT}
     * <li>{@code SwingConstants.LEFT}
     * <li>{@code SwingConstants.CENTER}
     * <li>{@code SwingConstants.LEADING}
     * <li>{@code SwingConstants.TRAILING} (the default)
     * </ul>
     */
    public int getHorizontalTextPosition() {
        return horizontalTextPosition;
    }

    /**
     * Sets the horizontal position of the text relative to the icon.
     * <p>
     *  设置文本相对于图标的水平位置。
     * 
     * 
     * @param textPosition one of the following values:
     * <ul>
     * <li>{@code SwingConstants.RIGHT}
     * <li>{@code SwingConstants.LEFT}
     * <li>{@code SwingConstants.CENTER}
     * <li>{@code SwingConstants.LEADING}
     * <li>{@code SwingConstants.TRAILING} (the default)
     * </ul>
     * @exception IllegalArgumentException if <code>textPosition</code>
     *          is not one of the legal values listed above
     * @beaninfo
     *        bound: true
     *         enum: LEFT     SwingConstants.LEFT
     *               CENTER   SwingConstants.CENTER
     *               RIGHT    SwingConstants.RIGHT
     *               LEADING  SwingConstants.LEADING
     *               TRAILING SwingConstants.TRAILING
     *    attribute: visualUpdate true
     *  description: The horizontal position of the text relative to the icon.
     */
    public void setHorizontalTextPosition(int textPosition) {
        if (textPosition == horizontalTextPosition) return;
        int oldValue = horizontalTextPosition;
        horizontalTextPosition = checkHorizontalKey(textPosition,
                                                    "horizontalTextPosition");
        firePropertyChange(HORIZONTAL_TEXT_POSITION_CHANGED_PROPERTY,
                           oldValue,
                           horizontalTextPosition);
        revalidate();
        repaint();
    }

    /**
     * Returns the amount of space between the text and the icon
     * displayed in this button.
     *
     * <p>
     *  返回文本和此按钮中显示的图标之间的空格大小。
     * 
     * 
     * @return an int equal to the number of pixels between the text
     *         and the icon.
     * @since 1.4
     * @see #setIconTextGap
     */
    public int getIconTextGap() {
        return iconTextGap;
    }

    /**
     * If both the icon and text properties are set, this property
     * defines the space between them.
     * <p>
     * The default value of this property is 4 pixels.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  如果同时设置了图标和文本属性,则此属性定义它们之间的空格。
     * <p>
     *  此属性的默认值为4像素。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @since 1.4
     * @see #getIconTextGap
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: If both the icon and text properties are set, this
     *               property defines the space between them.
     */
    public void setIconTextGap(int iconTextGap) {
        int oldValue = this.iconTextGap;
        this.iconTextGap = iconTextGap;
        iconTextGapSet = true;
        firePropertyChange("iconTextGap", oldValue, iconTextGap);
        if (iconTextGap != oldValue) {
            revalidate();
            repaint();
        }
    }

    /**
     * Verify that the {@code key} argument is a legal value for the
     * {@code horizontalAlignment} and {@code horizontalTextPosition}
     * properties. Valid values are:
     * <ul>
     *   <li>{@code SwingConstants.RIGHT}
     *   <li>{@code SwingConstants.LEFT}
     *   <li>{@code SwingConstants.CENTER}
     *   <li>{@code SwingConstants.LEADING}
     *   <li>{@code SwingConstants.TRAILING}
     * </ul>
     *
     * <p>
     *  验证{@code key}参数是{@code horizo​​ntalAlignment}和{@code horizo​​ntalTextPosition}属性的合法值。有效值为：
     * <ul>
     *  <li> {@ code SwingConstants.LIGHT} <li> {@ code SwingConstants.LEFTING} <li> {@ code SwingConstants.CENTER}
     *  <li> {@ code SwingConstants.LEADING}。
     * </ul>
     * 
     * 
     * @param key the property value to check
     * @param exception the message to use in the
     *        {@code IllegalArgumentException} that is thrown for an invalid
     *        value
     * @return the {@code key} argument
     * @exception IllegalArgumentException if key is not one of the legal
     *            values listed above
     * @see #setHorizontalTextPosition
     * @see #setHorizontalAlignment
     */
    protected int checkHorizontalKey(int key, String exception) {
        if ((key == LEFT) ||
            (key == CENTER) ||
            (key == RIGHT) ||
            (key == LEADING) ||
            (key == TRAILING)) {
            return key;
        } else {
            throw new IllegalArgumentException(exception);
        }
    }

    /**
     * Verify that the {@code key} argument is a legal value for the
     * vertical properties. Valid values are:
     * <ul>
     *   <li>{@code SwingConstants.CENTER}
     *   <li>{@code SwingConstants.TOP}
     *   <li>{@code SwingConstants.BOTTOM}
     * </ul>
     *
     * <p>
     *  验证{@code key}参数是垂直属性的合法值。有效值为：
     * <ul>
     *  <li> {@ code SwingConstants.CENTER} <li> {@ code SwingConstants.TOP} <li> {@ code SwingConstants.BOTTOM}
     * 。
     * </ul>
     * 
     * 
     * @param key the property value to check
     * @param exception the message to use in the
     *        {@code IllegalArgumentException} that is thrown for an invalid
     *        value
     * @return the {@code key} argument
     * @exception IllegalArgumentException if key is not one of the legal
     *            values listed above
     */
    protected int checkVerticalKey(int key, String exception) {
        if ((key == TOP) || (key == CENTER) || (key == BOTTOM)) {
            return key;
        } else {
            throw new IllegalArgumentException(exception);
        }
    }

    /**
     *{@inheritDoc}
     *
     * <p>
     *  @inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void removeNotify() {
        super.removeNotify();
        if(isRolloverEnabled()) {
            getModel().setRollover(false);
        }
    }

    /**
     * Sets the action command for this button.
     * <p>
     *  设置此按钮的操作命令。
     * 
     * 
     * @param actionCommand the action command for this button
     */
    public void setActionCommand(String actionCommand) {
        getModel().setActionCommand(actionCommand);
    }

    /**
     * Returns the action command for this button.
     * <p>
     *  返回此按钮的操作命令。
     * 
     * 
     * @return the action command for this button
     */
    public String getActionCommand() {
        String ac = getModel().getActionCommand();
        if(ac == null) {
            ac = getText();
        }
        return ac;
    }

    private Action action;
    private PropertyChangeListener actionPropertyChangeListener;

    /**
     * Sets the <code>Action</code>.
     * The new <code>Action</code> replaces any previously set
     * <code>Action</code> but does not affect <code>ActionListeners</code>
     * independently added with <code>addActionListener</code>.
     * If the <code>Action</code> is already a registered
     * <code>ActionListener</code> for the button, it is not re-registered.
     * <p>
     * Setting the <code>Action</code> results in immediately changing
     * all the properties described in <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a>.
     * Subsequently, the button's properties are automatically updated
     * as the <code>Action</code>'s properties change.
     * <p>
     * This method uses three other methods to set
     * and help track the <code>Action</code>'s property values.
     * It uses the <code>configurePropertiesFromAction</code> method
     * to immediately change the button's properties.
     * To track changes in the <code>Action</code>'s property values,
     * this method registers the <code>PropertyChangeListener</code>
     * returned by <code>createActionPropertyChangeListener</code>. The
     * default {@code PropertyChangeListener} invokes the
     * {@code actionPropertyChanged} method when a property in the
     * {@code Action} changes.
     *
     * <p>
     * 设置<code> Action </code>。
     * 新<code> Action </code>替换任何先前设置的<code> Action </code>,但不影响<code> addActionListener </code>独立添加的<code> 
     * ActionListeners </code>。
     * 设置<code> Action </code>。如果<code> Action </code>已经是按钮的注册<code> ActionListener </code>,则不会重新注册。
     * <p>
     *  设置<code> Action </code>会立即更改<a href="Action.html#buttonActions"> Swing组件支持<code> Action </code> </a>
     * 中描述的所有属性。
     * 随后,按钮的属性会随着<code> Action </code>的属性更改而自动更新。
     * <p>
     *  此方法使用其他三种方法来设置和帮助跟踪<code> Action </code>的属性值。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改按钮的属性。
     * 要跟踪<code> Action </code>的属性值的更改,此方法注册<code> createActionPropertyChangeListener </code>返回的<code> Prope
     * rtyChangeListener </code>。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改按钮的属性。
     *  {@code Action}中的属性更改时,默认的{@code PropertyChangeListener}调用{@code actionPropertyChanged}方法。
     * 
     * 
     * @param a the <code>Action</code> for the <code>AbstractButton</code>,
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
     * @return the <code>Action</code> for this <code>ActionEvent</code>
     *          source, or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the properties on this button to match those in the specified
     * <code>Action</code>.  Refer to <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a> for more
     * details as to which properties this sets.
     *
     * <p>
     * 设置此按钮上的属性以匹配指定的<code> Action </code>中的属性。
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
        setMnemonicFromAction(a);
        setTextFromAction(a, false);
        AbstractAction.setToolTipTextFromAction(this, a);
        setIconFromAction(a);
        setActionCommandFromAction(a);
        AbstractAction.setEnabledFromAction(this, a);
        if (AbstractAction.hasSelectedKey(a) &&
                shouldUpdateSelectedStateFromAction()) {
            setSelectedFromAction(a);
        }
        setDisplayedMnemonicIndexFromAction(a, false);
    }

    void clientPropertyChanged(Object key, Object oldValue,
                               Object newValue) {
        if (key == "hideActionText") {
            boolean current = (newValue instanceof Boolean) ?
                                (Boolean)newValue : false;
            if (getHideActionText() != current) {
                setHideActionText(current);
            }
        }
    }

    /**
     * Button subclasses that support mirroring the selected state from
     * the action should override this to return true.  AbstractButton's
     * implementation returns false.
     * <p>
     *  支持从操作中镜像选定状态的按钮子类应覆盖此子句以返回true。 AbstractButton的实现返回false。
     * 
     */
    boolean shouldUpdateSelectedStateFromAction() {
        return false;
    }

    /**
     * Updates the button's state in response to property changes in the
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
     *  更新按钮的状态以响应关联操作中的属性更改。
     * 此方法从{@code createActionPropertyChangeListener}返回的{@code PropertyChangeListener}中调用。子类通常不需要调用这个。
     * 支持其他{@code Action}属性的子类应该覆盖此类和{@code configurePropertiesFromAction}。
     * <p>
     *  请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>中的表格,了解此方法设置的属性列表。
     * 
     * 
     * @param action the <code>Action</code> associated with this button
     * @param propertyName the name of the property that changed
     * @since 1.6
     * @see Action
     * @see #configurePropertiesFromAction
     */
    protected void actionPropertyChanged(Action action, String propertyName) {
        if (propertyName == Action.NAME) {
            setTextFromAction(action, true);
        } else if (propertyName == "enabled") {
            AbstractAction.setEnabledFromAction(this, action);
        } else if (propertyName == Action.SHORT_DESCRIPTION) {
            AbstractAction.setToolTipTextFromAction(this, action);
        } else if (propertyName == Action.SMALL_ICON) {
            smallIconChanged(action);
        } else if (propertyName == Action.MNEMONIC_KEY) {
            setMnemonicFromAction(action);
        } else if (propertyName == Action.ACTION_COMMAND_KEY) {
            setActionCommandFromAction(action);
        } else if (propertyName == Action.SELECTED_KEY &&
                   AbstractAction.hasSelectedKey(action) &&
                   shouldUpdateSelectedStateFromAction()) {
            setSelectedFromAction(action);
        } else if (propertyName == Action.DISPLAYED_MNEMONIC_INDEX_KEY) {
            setDisplayedMnemonicIndexFromAction(action, true);
        } else if (propertyName == Action.LARGE_ICON_KEY) {
            largeIconChanged(action);
        }
    }

    private void setDisplayedMnemonicIndexFromAction(
            Action a, boolean fromPropertyChange) {
        Integer iValue = (a == null) ? null :
                (Integer)a.getValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY);
        if (fromPropertyChange || iValue != null) {
            int value;
            if (iValue == null) {
                value = -1;
            } else {
                value = iValue;
                String text = getText();
                if (text == null || value >= text.length()) {
                    value = -1;
                }
            }
            setDisplayedMnemonicIndex(value);
        }
    }

    private void setMnemonicFromAction(Action a) {
        Integer n = (a == null) ? null :
                                  (Integer)a.getValue(Action.MNEMONIC_KEY);
        setMnemonic((n == null) ? '\0' : n);
    }

    private void setTextFromAction(Action a, boolean propertyChange) {
        boolean hideText = getHideActionText();
        if (!propertyChange) {
            setText((a != null && !hideText) ?
                        (String)a.getValue(Action.NAME) : null);
        }
        else if (!hideText) {
            setText((String)a.getValue(Action.NAME));
        }
    }

    void setIconFromAction(Action a) {
        Icon icon = null;
        if (a != null) {
            icon = (Icon)a.getValue(Action.LARGE_ICON_KEY);
            if (icon == null) {
                icon = (Icon)a.getValue(Action.SMALL_ICON);
            }
        }
        setIcon(icon);
    }

    void smallIconChanged(Action a) {
        if (a.getValue(Action.LARGE_ICON_KEY) == null) {
            setIconFromAction(a);
        }
    }

    void largeIconChanged(Action a) {
        setIconFromAction(a);
    }

    private void setActionCommandFromAction(Action a) {
        setActionCommand((a != null) ?
                             (String)a.getValue(Action.ACTION_COMMAND_KEY) :
                             null);
    }

    /**
     * Sets the seleted state of the button from the action.  This is defined
     * here, but not wired up.  Subclasses like JToggleButton and
     * JCheckBoxMenuItem make use of it.
     *
     * <p>
     *  从动作设置按钮的选择状态。这里定义,但没有接线。像JToggleButton和JCheckBoxMenuItem这样的子类使用它。
     * 
     * 
     * @param a the Action
     */
    private void setSelectedFromAction(Action a) {
        boolean selected = false;
        if (a != null) {
            selected = AbstractAction.isSelected(a);
        }
        if (selected != isSelected()) {
            // This won't notify ActionListeners, but that should be
            // ok as the change is coming from the Action.
            setSelected(selected);
            // Make sure the change actually took effect
            if (!selected && isSelected()) {
                if (getModel() instanceof DefaultButtonModel) {
                    ButtonGroup group = ((DefaultButtonModel)getModel()).getGroup();
                    if (group != null) {
                        group.clearSelection();
                    }
                }
            }
        }
    }

    /**
     * Creates and returns a <code>PropertyChangeListener</code> that is
     * responsible for listening for changes from the specified
     * <code>Action</code> and updating the appropriate properties.
     * <p>
     * <b>Warning:</b> If you subclass this do not create an anonymous
     * inner class.  If you do the lifetime of the button will be tied to
     * that of the <code>Action</code>.
     *
     * <p>
     *  创建并返回一个<code> PropertyChangeListener </code>,它负责侦听来自指定<code> Action </code>的更改并更新相应的属性。
     * <p>
     *  <b>警告：</b>如果你子类化这不创建一个匿名内部类。如果你做的按钮的生命周期将绑定到<code> Action </code>。
     * 
     * 
     * @param a the button's action
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
        return createActionPropertyChangeListener0(a);
    }


    PropertyChangeListener createActionPropertyChangeListener0(Action a) {
        return new ButtonActionPropertyChangeListener(this, a);
    }

    @SuppressWarnings("serial")
    private static class ButtonActionPropertyChangeListener
                 extends ActionPropertyChangeListener<AbstractButton> {
        ButtonActionPropertyChangeListener(AbstractButton b, Action a) {
            super(b, a);
        }
        protected void actionPropertyChanged(AbstractButton button,
                                             Action action,
                                             PropertyChangeEvent e) {
            if (AbstractAction.shouldReconfigure(e)) {
                button.configurePropertiesFromAction(action);
            } else {
                button.actionPropertyChanged(action, e.getPropertyName());
            }
        }
    }

    /**
     * Gets the <code>borderPainted</code> property.
     *
     * <p>
     * 获取<code> borderPainted </code>属性。
     * 
     * 
     * @return the value of the <code>borderPainted</code> property
     * @see #setBorderPainted
     */
    public boolean isBorderPainted() {
        return paintBorder;
    }

    /**
     * Sets the <code>borderPainted</code> property.
     * If <code>true</code> and the button has a border,
     * the border is painted. The default value for the
     * <code>borderPainted</code> property is <code>true</code>.
     * <p>
     * Some look and feels might not support
     * the <code>borderPainted</code> property,
     * in which case they ignore this.
     *
     * <p>
     *  设置<code> borderPainted </code>属性。如果<code> true </code>并且按钮有边框,则绘制边框。
     *  <code> borderPainted </code>属性的默认值为<code> true </code>。
     * <p>
     *  一些外观和感觉可能不支持<code> borderPainted </code>属性,在这种情况下,他们忽略此。
     * 
     * 
     * @param b if true and border property is not <code>null</code>,
     *          the border is painted
     * @see #isBorderPainted
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether the border should be painted.
     */
    public void setBorderPainted(boolean b) {
        boolean oldValue = paintBorder;
        paintBorder = b;
        borderPaintedSet = true;
        firePropertyChange(BORDER_PAINTED_CHANGED_PROPERTY, oldValue, paintBorder);
        if (b != oldValue) {
            revalidate();
            repaint();
        }
    }

    /**
     * Paint the button's border if <code>BorderPainted</code>
     * property is true and the button has a border.
     * <p>
     *  如果<code> BorderPainted </code>属性为true并且按钮有边框,则绘制按钮的边框。
     * 
     * 
     * @param g the <code>Graphics</code> context in which to paint
     *
     * @see #paint
     * @see #setBorder
     */
    protected void paintBorder(Graphics g) {
        if (isBorderPainted()) {
            super.paintBorder(g);
        }
    }

    /**
     * Gets the <code>paintFocus</code> property.
     *
     * <p>
     *  获取<code> paintFocus </code>属性。
     * 
     * 
     * @return the <code>paintFocus</code> property
     * @see #setFocusPainted
     */
    public boolean isFocusPainted() {
        return paintFocus;
    }

    /**
     * Sets the <code>paintFocus</code> property, which must
     * be <code>true</code> for the focus state to be painted.
     * The default value for the <code>paintFocus</code> property
     * is <code>true</code>.
     * Some look and feels might not paint focus state;
     * they will ignore this property.
     *
     * <p>
     *  设置<code> paintFocus </code>属性,对于要绘制的焦点状态,它必须是<code> true </code>。
     *  <code> paintFocus </code>属性的默认值为<code> true </code>。一些外观和感觉可能不画焦点状态;他们将忽略此属性。
     * 
     * 
     * @param b if <code>true</code>, the focus state should be painted
     * @see #isFocusPainted
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether focus should be painted
     */
    public void setFocusPainted(boolean b) {
        boolean oldValue = paintFocus;
        paintFocus = b;
        firePropertyChange(FOCUS_PAINTED_CHANGED_PROPERTY, oldValue, paintFocus);
        if (b != oldValue && isFocusOwner()) {
            revalidate();
            repaint();
        }
    }

    /**
     * Gets the <code>contentAreaFilled</code> property.
     *
     * <p>
     *  获取<code> contentAreaFilled </code>属性。
     * 
     * 
     * @return the <code>contentAreaFilled</code> property
     * @see #setContentAreaFilled
     */
    public boolean isContentAreaFilled() {
        return contentAreaFilled;
    }

    /**
     * Sets the <code>contentAreaFilled</code> property.
     * If <code>true</code> the button will paint the content
     * area.  If you wish to have a transparent button, such as
     * an icon only button, for example, then you should set
     * this to <code>false</code>. Do not call <code>setOpaque(false)</code>.
     * The default value for the the <code>contentAreaFilled</code>
     * property is <code>true</code>.
     * <p>
     * This function may cause the component's opaque property to change.
     * <p>
     * The exact behavior of calling this function varies on a
     * component-by-component and L&amp;F-by-L&amp;F basis.
     *
     * <p>
     *  设置<code> contentAreaFilled </code>属性。如果<code> true </code>,该按钮将绘制内容区域。
     * 如果你想有一个透明按钮,例如一个只有图标的按钮,那么你应该设置为<code> false </code>。不要调用<code> setOpaque(false)</code>。
     *  <code> contentAreaFilled </code>属性的默认值为<code> true </code>。
     * <p>
     *  此函数可能会导致组件的opaque属性更改。
     * <p>
     *  调用该函数的确切行为在逐个组件和L&amp; F-L&amp; F的基础上变化。
     * 
     * 
     * @param b if true, the content should be filled; if false
     *          the content area is not filled
     * @see #isContentAreaFilled
     * @see #setOpaque
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether the button should paint the content area
     *               or leave it transparent.
     */
    public void setContentAreaFilled(boolean b) {
        boolean oldValue = contentAreaFilled;
        contentAreaFilled = b;
        contentAreaFilledSet = true;
        firePropertyChange(CONTENT_AREA_FILLED_CHANGED_PROPERTY, oldValue, contentAreaFilled);
        if (b != oldValue) {
            repaint();
        }
    }

    /**
     * Gets the <code>rolloverEnabled</code> property.
     *
     * <p>
     *  获取<code> rolloverEnabled </code>属性。
     * 
     * 
     * @return the value of the <code>rolloverEnabled</code> property
     * @see #setRolloverEnabled
     */
    public boolean isRolloverEnabled() {
        return rolloverEnabled;
    }

    /**
     * Sets the <code>rolloverEnabled</code> property, which
     * must be <code>true</code> for rollover effects to occur.
     * The default value for the <code>rolloverEnabled</code>
     * property is <code>false</code>.
     * Some look and feels might not implement rollover effects;
     * they will ignore this property.
     *
     * <p>
     * 设置<code> rolloverEnabled </code>属性,它必须是<code> true </code>才会发生翻转效果。
     *  <code> rolloverEnabled </code>属性的默认值为<code> false </code>。一些外观和感觉可能不会实现翻转效果;他们将忽略此属性。
     * 
     * 
     * @param b if <code>true</code>, rollover effects should be painted
     * @see #isRolloverEnabled
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether rollover effects should be enabled.
     */
    public void setRolloverEnabled(boolean b) {
        boolean oldValue = rolloverEnabled;
        rolloverEnabled = b;
        rolloverEnabledSet = true;
        firePropertyChange(ROLLOVER_ENABLED_CHANGED_PROPERTY, oldValue, rolloverEnabled);
        if (b != oldValue) {
            repaint();
        }
    }

    /**
     * Returns the keyboard mnemonic from the the current model.
     * <p>
     *  从当前模型返回键盘助记符。
     * 
     * 
     * @return the keyboard mnemonic from the model
     */
    public int getMnemonic() {
        return mnemonic;
    }

    /**
     * Sets the keyboard mnemonic on the current model.
     * The mnemonic is the key which when combined with the look and feel's
     * mouseless modifier (usually Alt) will activate this button
     * if focus is contained somewhere within this button's ancestor
     * window.
     * <p>
     * A mnemonic must correspond to a single key on the keyboard
     * and should be specified using one of the <code>VK_XXX</code>
     * keycodes defined in <code>java.awt.event.KeyEvent</code>.
     * These codes and the wider array of codes for international
     * keyboards may be obtained through
     * <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code>.
     * Mnemonics are case-insensitive, therefore a key event
     * with the corresponding keycode would cause the button to be
     * activated whether or not the Shift modifier was pressed.
     * <p>
     * If the character defined by the mnemonic is found within
     * the button's label string, the first occurrence of it
     * will be underlined to indicate the mnemonic to the user.
     *
     * <p>
     *  在当前模型上设置键盘助记符。助记符是当与外观和感觉无调剂修饰符(通常为Alt)组合时,如果焦点被包含在该按钮的祖先窗口内的某个位置,则激活该按钮的键。
     * <p>
     *  助记符必须对应于键盘上的单个键,并且应使用<code> java.awt.event.KeyEvent </code>中定义的<code> VK_XXX </code>键代码之一来指定。
     * 这些代码和国际键盘的更广泛的代码可以通过<code> java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>获得。
     * 助记符不区分大小写,因此具有相应键码的键事件将导致按钮被激活,无论是否按下Shift键。
     * <p>
     *  如果在按钮的标签字符串中找到由助记符定义的字符,则它的第一次出现将被加下划线以向用户指示助记符。
     * 
     * 
     * @param mnemonic the key code which represents the mnemonic
     * @see     java.awt.event.KeyEvent
     * @see     #setDisplayedMnemonicIndex
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: the keyboard character mnemonic
     */
    public void setMnemonic(int mnemonic) {
        int oldValue = getMnemonic();
        model.setMnemonic(mnemonic);
        updateMnemonicProperties();
    }

    /**
     * This method is now obsolete, please use <code>setMnemonic(int)</code>
     * to set the mnemonic for a button.  This method is only designed
     * to handle character values which fall between 'a' and 'z' or
     * 'A' and 'Z'.
     *
     * <p>
     *  此方法现在已过时,请使用<code> setMnemonic(int)</code>为按钮设置助记符。此方法仅设计为处理在'a'和'z'或'A'和'Z'之间的字符值。
     * 
     * 
     * @param mnemonic  a char specifying the mnemonic value
     * @see #setMnemonic(int)
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: the keyboard character mnemonic
     */
    public void setMnemonic(char mnemonic) {
        int vk = (int) mnemonic;
        if(vk >= 'a' && vk <='z')
            vk -= ('a' - 'A');
        setMnemonic(vk);
    }

    /**
     * Provides a hint to the look and feel as to which character in the
     * text should be decorated to represent the mnemonic. Not all look and
     * feels may support this. A value of -1 indicates either there is no
     * mnemonic, the mnemonic character is not contained in the string, or
     * the developer does not wish the mnemonic to be displayed.
     * <p>
     * The value of this is updated as the properties relating to the
     * mnemonic change (such as the mnemonic itself, the text...).
     * You should only ever have to call this if
     * you do not wish the default character to be underlined. For example, if
     * the text was 'Save As', with a mnemonic of 'a', and you wanted the 'A'
     * to be decorated, as 'Save <u>A</u>s', you would have to invoke
     * <code>setDisplayedMnemonicIndex(5)</code> after invoking
     * <code>setMnemonic(KeyEvent.VK_A)</code>.
     *
     * <p>
     * 提供关于文本中应当装饰哪个字符以表示助记符的外观和感觉的提示。不是所有的外观和感觉可能支持这一点。值-1表示没有助记符,助记符不包含在字符串中,或​​者开发人员不希望显示助记符。
     * <p>
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。
     * 例如,如果文本是"另存为",助记符为'a',并且您想要将'A'装饰为'保存<u> A </u>',则必须调用调用<code> setMnemonic(KeyEvent.VK_A)</code>之后的<code>
     *  setDisplayedMnemonicIndex(5)</code>。
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。
     * 
     * 
     * @since 1.4
     * @param index Index into the String to underline
     * @exception IllegalArgumentException will be thrown if <code>index</code>
     *            is &gt;= length of the text, or &lt; -1
     * @see #getDisplayedMnemonicIndex
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: the index into the String to draw the keyboard character
     *               mnemonic at
     */
    public void setDisplayedMnemonicIndex(int index)
                                          throws IllegalArgumentException {
        int oldValue = mnemonicIndex;
        if (index == -1) {
            mnemonicIndex = -1;
        } else {
            String text = getText();
            int textLength = (text == null) ? 0 : text.length();
            if (index < -1 || index >= textLength) {  // index out of range
                throw new IllegalArgumentException("index == " + index);
            }
        }
        mnemonicIndex = index;
        firePropertyChange("displayedMnemonicIndex", oldValue, index);
        if (index != oldValue) {
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the character, as an index, that the look and feel should
     * provide decoration for as representing the mnemonic character.
     *
     * <p>
     *  返回作为索引的字符,外观应该提供装饰作为代表助记符的字符。
     * 
     * 
     * @since 1.4
     * @return index representing mnemonic character
     * @see #setDisplayedMnemonicIndex
     */
    public int getDisplayedMnemonicIndex() {
        return mnemonicIndex;
    }

    /**
     * Update the displayedMnemonicIndex property. This method
     * is called when either text or mnemonic changes. The new
     * value of the displayedMnemonicIndex property is the index
     * of the first occurrence of mnemonic in text.
     * <p>
     *  更新displayedMnemonicIndex属性。当文本或助记符更改时调用此方法。 displayedMnemonicIndex属性的新值是文本中第一次出现助记符的索引。
     * 
     */
    private void updateDisplayedMnemonicIndex(String text, int mnemonic) {
        setDisplayedMnemonicIndex(
            SwingUtilities.findDisplayedMnemonicIndex(text, mnemonic));
    }

    /**
     * Brings the mnemonic property in accordance with model's mnemonic.
     * This is called when model's mnemonic changes. Also updates the
     * displayedMnemonicIndex property.
     * <p>
     *  根据模型的助记符提供助记符属性。这在模型的助记符改变时被调用。还更新displayedMnemonicIndex属性。
     * 
     */
    private void updateMnemonicProperties() {
        int newMnemonic = model.getMnemonic();
        if (mnemonic != newMnemonic) {
            int oldValue = mnemonic;
            mnemonic = newMnemonic;
            firePropertyChange(MNEMONIC_CHANGED_PROPERTY,
                               oldValue, mnemonic);
            updateDisplayedMnemonicIndex(getText(), mnemonic);
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the amount of time (in milliseconds) required between
     * mouse press events for the button to generate the corresponding
     * action events.  After the initial mouse press occurs (and action
     * event generated) any subsequent mouse press events which occur
     * on intervals less than the threshhold will be ignored and no
     * corresponding action event generated.  By default the threshhold is 0,
     * which means that for each mouse press, an action event will be
     * fired, no matter how quickly the mouse clicks occur.  In buttons
     * where this behavior is not desirable (for example, the "OK" button
     * in a dialog), this threshhold should be set to an appropriate
     * positive value.
     *
     * <p>
     * 设置鼠标按下事件之间按钮生成相应操作事件所需的时间(以毫秒为单位)。在初始鼠标按压发生(和动作事件生成)之后,小于阈值的间隔上发生的任何后续鼠标按压事件将被忽略,并且不生成相应的动作事件。
     * 默认情况下,阈值为0,这意味着对于每个鼠标按下,无论鼠标点击发生多快,都会触发一个动作事件。在不希望这种行为的按钮中(例如,对话框中的"确定"按钮),该阈值应设置为适当的正值。
     * 
     * 
     * @see #getMultiClickThreshhold
     * @param threshhold the amount of time required between mouse
     *        press events to generate corresponding action events
     * @exception   IllegalArgumentException if threshhold &lt; 0
     * @since 1.4
     */
    public void setMultiClickThreshhold(long threshhold) {
        if (threshhold < 0) {
            throw new IllegalArgumentException("threshhold must be >= 0");
        }
        this.multiClickThreshhold = threshhold;
    }

    /**
     * Gets the amount of time (in milliseconds) required between
     * mouse press events for the button to generate the corresponding
     * action events.
     *
     * <p>
     *  获取鼠标按下事件之间按钮生成相应操作事件所需的时间(以毫秒为单位)。
     * 
     * 
     * @see #setMultiClickThreshhold
     * @return the amount of time required between mouse press events
     *         to generate corresponding action events
     * @since 1.4
     */
    public long getMultiClickThreshhold() {
        return multiClickThreshhold;
    }

    /**
     * Returns the model that this button represents.
     * <p>
     *  返回此按钮代表的模型。
     * 
     * 
     * @return the <code>model</code> property
     * @see #setModel
     */
    public ButtonModel getModel() {
        return model;
    }

    /**
     * Sets the model that this button represents.
     * <p>
     *  设置此按钮代表的模型。
     * 
     * 
     * @param newModel the new <code>ButtonModel</code>
     * @see #getModel
     * @beaninfo
     *        bound: true
     *  description: Model that the Button uses.
     */
    public void setModel(ButtonModel newModel) {

        ButtonModel oldModel = getModel();

        if (oldModel != null) {
            oldModel.removeChangeListener(changeListener);
            oldModel.removeActionListener(actionListener);
            oldModel.removeItemListener(itemListener);
            changeListener = null;
            actionListener = null;
            itemListener = null;
        }

        model = newModel;

        if (newModel != null) {
            changeListener = createChangeListener();
            actionListener = createActionListener();
            itemListener = createItemListener();
            newModel.addChangeListener(changeListener);
            newModel.addActionListener(actionListener);
            newModel.addItemListener(itemListener);

            updateMnemonicProperties();
            //We invoke setEnabled() from JComponent
            //because setModel() can be called from a constructor
            //when the button is not fully initialized
            super.setEnabled(newModel.isEnabled());

        } else {
            mnemonic = '\0';
        }

        updateDisplayedMnemonicIndex(getText(), mnemonic);

        firePropertyChange(MODEL_CHANGED_PROPERTY, oldModel, newModel);
        if (newModel != oldModel) {
            revalidate();
            repaint();
        }
    }


    /**
     * Returns the L&amp;F object that renders this component.
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the ButtonUI object
     * @see #setUI
     */
    public ButtonUI getUI() {
        return (ButtonUI) ui;
    }


    /**
     * Sets the L&amp;F object that renders this component.
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui the <code>ButtonUI</code> L&amp;F object
     * @see #getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the LookAndFeel.
     */
    public void setUI(ButtonUI ui) {
        super.setUI(ui);
        // disabled icons are generated by the LF so they should be unset here
        if (disabledIcon instanceof UIResource) {
            setDisabledIcon(null);
        }
        if (disabledSelectedIcon instanceof UIResource) {
            setDisabledSelectedIcon(null);
        }
    }


    /**
     * Resets the UI property to a value from the current look
     * and feel.  Subtypes of <code>AbstractButton</code>
     * should override this to update the UI. For
     * example, <code>JButton</code> might do the following:
     * <pre>
     *      setUI((ButtonUI)UIManager.getUI(
     *          "ButtonUI", "javax.swing.plaf.basic.BasicButtonUI", this));
     * </pre>
     * <p>
     *  将UI属性重置为当前外观的值。 <code> AbstractButton </code>的子类型应该覆盖此更新UI。例如,<code> JButton </code>可能会执行以下操作：
     * <pre>
     *  setUI((ButtonUI)UIManager.getUI("ButtonUI","javax.swing.plaf.basic.BasicButtonUI",this));
     * </pre>
     */
    public void updateUI() {
    }

    /**
     * Adds the specified component to this container at the specified
     * index, refer to
     * {@link java.awt.Container#addImpl(Component, Object, int)}
     * for a complete description of this method.
     *
     * <p>
     *  在指定索引处将指定的组件添加到此容器中,有关此方法的完整说明,请参阅{@link java.awt.Container#addImpl(Component,Object,int)}。
     * 
     * 
     * @param     comp the component to be added
     * @param     constraints an object expressing layout constraints
     *                 for this component
     * @param     index the position in the container's list at which to
     *                 insert the component, where <code>-1</code>
     *                 means append to the end
     * @exception IllegalArgumentException if <code>index</code> is invalid
     * @exception IllegalArgumentException if adding the container's parent
     *                  to itself
     * @exception IllegalArgumentException if adding a window to a container
     * @since 1.5
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        if (!setLayout) {
            setLayout(new OverlayLayout(this));
        }
        super.addImpl(comp, constraints, index);
    }

    /**
     * Sets the layout manager for this container, refer to
     * {@link java.awt.Container#setLayout(LayoutManager)}
     * for a complete description of this method.
     *
     * <p>
     * 设置此容器的布局管理器,有关此方法的完整说明,请参阅{@link java.awt.Container#setLayout(LayoutManager)}。
     * 
     * 
     * @param mgr the specified layout manager
     * @since 1.5
     */
    public void setLayout(LayoutManager mgr) {
        setLayout = true;
        super.setLayout(mgr);
    }

    /**
     * Adds a <code>ChangeListener</code> to the button.
     * <p>
     *  向按钮添加<code> ChangeListener </code>。
     * 
     * 
     * @param l the listener to be added
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * Removes a ChangeListener from the button.
     * <p>
     *  从按钮中删除ChangeListener。
     * 
     * 
     * @param l the listener to be removed
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this AbstractButton with addChangeListener().
     *
     * <p>
     *  返回使用addChangeListener()添加到此AbstractButton的所有<code> ChangeListener </code>数组。
     * 
     * 
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created.
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。延迟创建事件实例。
     * 
     * 
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

    /**
     * Adds an <code>ActionListener</code> to the button.
     * <p>
     *  向按钮添加<code> ActionListener </code>。
     * 
     * 
     * @param l the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    /**
     * Removes an <code>ActionListener</code> from the button.
     * If the listener is the currently set <code>Action</code>
     * for the button, then the <code>Action</code>
     * is set to <code>null</code>.
     *
     * <p>
     *  从按钮中删除<code> ActionListener </code>。
     * 如果侦听器是当前为该按钮设置的<code> Action </code>,则<code> Action </code>设置为<code> null </code>。
     * 
     * 
     * @param l the listener to be removed
     */
    public void removeActionListener(ActionListener l) {
        if ((l != null) && (getAction() == l)) {
            setAction(null);
        } else {
            listenerList.remove(ActionListener.class, l);
        }
    }

    /**
     * Returns an array of all the <code>ActionListener</code>s added
     * to this AbstractButton with addActionListener().
     *
     * <p>
     *  返回通过addActionListener()添加到此AbstractButton的所有<code> ActionListener </code>数组。
     * 
     * 
     * @return all of the <code>ActionListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }

    /**
     * Subclasses that want to handle <code>ChangeEvents</code> differently
     * can override this to return another <code>ChangeListener</code>
     * implementation.
     *
     * <p>
     *  想要处理<code> ChangeEvents </code>的子类可以覆盖此方法,以返回另一个<code> ChangeListener </code>实现。
     * 
     * 
     * @return the new <code>ChangeListener</code>
     */
    protected ChangeListener createChangeListener() {
        return getHandler();
    }

    /**
     * Extends <code>ChangeListener</code> to be serializable.
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
     *  将<code> ChangeListener </code>扩展为可序列化。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class ButtonChangeListener implements ChangeListener, Serializable {
        // NOTE: This class is NOT used, instead the functionality has
        // been moved to Handler.
        ButtonChangeListener() {
        }

        public void stateChanged(ChangeEvent e) {
            getHandler().stateChanged(e);
        }
    }


    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>event</code>
     * parameter.
     *
     * <p>
     * 通知所有已注册有关此事件类型的通知的收件人。事件实例使用<code> event </code>参数进行延迟创建。
     * 
     * 
     * @param event  the <code>ActionEvent</code> object
     * @see EventListenerList
     */
    protected void fireActionPerformed(ActionEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                // Lazily create the event:
                if (e == null) {
                      String actionCommand = event.getActionCommand();
                      if(actionCommand == null) {
                         actionCommand = getActionCommand();
                      }
                      e = new ActionEvent(AbstractButton.this,
                                          ActionEvent.ACTION_PERFORMED,
                                          actionCommand,
                                          event.getWhen(),
                                          event.getModifiers());
                }
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>event</code> parameter.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用<code> event </code>参数进行延迟创建。
     * 
     * 
     * @param event  the <code>ItemEvent</code> object
     * @see EventListenerList
     */
    protected void fireItemStateChanged(ItemEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ItemEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ItemListener.class) {
                // Lazily create the event:
                if (e == null) {
                    e = new ItemEvent(AbstractButton.this,
                                      ItemEvent.ITEM_STATE_CHANGED,
                                      AbstractButton.this,
                                      event.getStateChange());
                }
                ((ItemListener)listeners[i+1]).itemStateChanged(e);
            }
        }
        if (accessibleContext != null) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    null, AccessibleState.SELECTED);
                accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
                    Integer.valueOf(0), Integer.valueOf(1));
            } else {
                accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    AccessibleState.SELECTED, null);
                accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
                    Integer.valueOf(1), Integer.valueOf(0));
            }
        }
    }


    protected ActionListener createActionListener() {
        return getHandler();
    }


    protected ItemListener createItemListener() {
        return getHandler();
    }


    /**
     * Enables (or disables) the button.
     * <p>
     *  启用(或禁用)按钮。
     * 
     * 
     * @param b  true to enable the button, otherwise false
     */
    public void setEnabled(boolean b) {
        if (!b && model.isRollover()) {
            model.setRollover(false);
        }
        super.setEnabled(b);
        model.setEnabled(b);
    }

    // *** Deprecated java.awt.Button APIs below *** //

    /**
     * Returns the label text.
     *
     * <p>
     *  返回标签文本。
     * 
     * 
     * @return a <code>String</code> containing the label
     * @deprecated - Replaced by <code>getText</code>
     */
    @Deprecated
    public String getLabel() {
        return getText();
    }

    /**
     * Sets the label text.
     *
     * <p>
     *  设置标签文本。
     * 
     * 
     * @param label  a <code>String</code> containing the text
     * @deprecated - Replaced by <code>setText(text)</code>
     * @beaninfo
     *        bound: true
     *  description: Replace by setText(text)
     */
    @Deprecated
    public void setLabel(String label) {
        setText(label);
    }

    /**
     * Adds an <code>ItemListener</code> to the <code>checkbox</code>.
     * <p>
     *  在<code>复选框</code>中添加<code> ItemListener </code>。
     * 
     * 
     * @param l  the <code>ItemListener</code> to be added
     */
    public void addItemListener(ItemListener l) {
        listenerList.add(ItemListener.class, l);
    }

    /**
     * Removes an <code>ItemListener</code> from the button.
     * <p>
     *  从按钮中删除<code> ItemListener </code>。
     * 
     * 
     * @param l the <code>ItemListener</code> to be removed
     */
    public void removeItemListener(ItemListener l) {
        listenerList.remove(ItemListener.class, l);
    }

    /**
     * Returns an array of all the <code>ItemListener</code>s added
     * to this AbstractButton with addItemListener().
     *
     * <p>
     *  返回使用addItemListener()添加到此AbstractButton的所有<code> ItemListener </code>数组。
     * 
     * 
     * @return all of the <code>ItemListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ItemListener[] getItemListeners() {
        return listenerList.getListeners(ItemListener.class);
    }

   /**
     * Returns an array (length 1) containing the label or
     * <code>null</code> if the button is not selected.
     *
     * <p>
     *  如果未选择此按钮,则返回包含标签或<code> null </code>的数组(长度1)。
     * 
     * 
     * @return an array containing 1 Object: the text of the button,
     *         if the item is selected; otherwise <code>null</code>
     */
    public Object[] getSelectedObjects() {
        if (isSelected() == false) {
            return null;
        }
        Object[] selectedObjects = new Object[1];
        selectedObjects[0] = getText();
        return selectedObjects;
    }

    protected void init(String text, Icon icon) {
        if(text != null) {
            setText(text);
        }

        if(icon != null) {
            setIcon(icon);
        }

        // Set the UI
        updateUI();

        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
    }


    /**
     * This is overridden to return false if the current <code>Icon</code>'s
     * <code>Image</code> is not equal to the
     * passed in <code>Image</code> <code>img</code>.
     *
     * <p>
     *  如果当前<code> Icon </code>的<code> Image </code>不等于<code> Image </code> <code> img </code>中传递的值, 。
     * 
     * 
     * @param img  the <code>Image</code> to be compared
     * @param infoflags flags used to repaint the button when the image
     *          is updated and which determine how much is to be painted
     * @param x  the x coordinate
     * @param y  the y coordinate
     * @param w  the width
     * @param h  the height
     * @see     java.awt.image.ImageObserver
     * @see     java.awt.Component#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean imageUpdate(Image img, int infoflags,
                               int x, int y, int w, int h) {
        Icon iconDisplayed = getIcon();
        if (iconDisplayed == null) {
            return false;
        }

        if (!model.isEnabled()) {
            if (model.isSelected()) {
                iconDisplayed = getDisabledSelectedIcon();
            } else {
                iconDisplayed = getDisabledIcon();
            }
        } else if (model.isPressed() && model.isArmed()) {
            iconDisplayed = getPressedIcon();
        } else if (isRolloverEnabled() && model.isRollover()) {
            if (model.isSelected()) {
                iconDisplayed = getRolloverSelectedIcon();
            } else {
                iconDisplayed = getRolloverIcon();
            }
        } else if (model.isSelected()) {
            iconDisplayed = getSelectedIcon();
        }

        if (!SwingUtilities.doesIconReferenceImage(iconDisplayed, img)) {
            // We don't know about this image, disable the notification so
            // we don't keep repainting.
            return false;
        }
        return super.imageUpdate(img, infoflags, x, y, w, h);
    }

    void setUIProperty(String propertyName, Object value) {
        if (propertyName == "borderPainted") {
            if (!borderPaintedSet) {
                setBorderPainted(((Boolean)value).booleanValue());
                borderPaintedSet = false;
            }
        } else if (propertyName == "rolloverEnabled") {
            if (!rolloverEnabledSet) {
                setRolloverEnabled(((Boolean)value).booleanValue());
                rolloverEnabledSet = false;
            }
        } else if (propertyName == "iconTextGap") {
            if (!iconTextGapSet) {
                setIconTextGap(((Number)value).intValue());
                iconTextGapSet = false;
            }
        } else if (propertyName == "contentAreaFilled") {
            if (!contentAreaFilledSet) {
                setContentAreaFilled(((Boolean)value).booleanValue());
                contentAreaFilledSet = false;
            }
        } else {
            super.setUIProperty(propertyName, value);
        }
    }

    /**
     * Returns a string representation of this <code>AbstractButton</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     * <P>
     * Overriding <code>paramString</code> to provide information about the
     * specific new aspects of the JFC components.
     *
     * <p>
     *  返回此<code> AbstractButton </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * <P>
     *  覆盖<code> paramString </code>以提供有关JFC组件的特定新方面的信息。
     * 
     * 
     * @return  a string representation of this <code>AbstractButton</code>
     */
    protected String paramString() {
        String defaultIconString = ((defaultIcon != null)
                                    && (defaultIcon != this) ?
                                    defaultIcon.toString() : "");
        String pressedIconString = ((pressedIcon != null)
                                    && (pressedIcon != this) ?
                                    pressedIcon.toString() : "");
        String disabledIconString = ((disabledIcon != null)
                                     && (disabledIcon != this) ?
                                     disabledIcon.toString() : "");
        String selectedIconString = ((selectedIcon != null)
                                     && (selectedIcon != this) ?
                                     selectedIcon.toString() : "");
        String disabledSelectedIconString = ((disabledSelectedIcon != null) &&
                                             (disabledSelectedIcon != this) ?
                                             disabledSelectedIcon.toString()
                                             : "");
        String rolloverIconString = ((rolloverIcon != null)
                                     && (rolloverIcon != this) ?
                                     rolloverIcon.toString() : "");
        String rolloverSelectedIconString = ((rolloverSelectedIcon != null) &&
                                             (rolloverSelectedIcon != this) ?
                                             rolloverSelectedIcon.toString()
                                             : "");
        String paintBorderString = (paintBorder ? "true" : "false");
        String paintFocusString = (paintFocus ? "true" : "false");
        String rolloverEnabledString = (rolloverEnabled ? "true" : "false");

        return super.paramString() +
        ",defaultIcon=" + defaultIconString +
        ",disabledIcon=" + disabledIconString +
        ",disabledSelectedIcon=" + disabledSelectedIconString +
        ",margin=" + margin +
        ",paintBorder=" + paintBorderString +
        ",paintFocus=" + paintFocusString +
        ",pressedIcon=" + pressedIconString +
        ",rolloverEnabled=" + rolloverEnabledString +
        ",rolloverIcon=" + rolloverIconString +
        ",rolloverSelectedIcon=" + rolloverSelectedIconString +
        ",selectedIcon=" + selectedIconString +
        ",text=" + text;
    }


    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }


    //
    // Listeners that are added to model
    //
    @SuppressWarnings("serial")
    class Handler implements ActionListener, ChangeListener, ItemListener,
                             Serializable {
        //
        // ChangeListener
        //
        public void stateChanged(ChangeEvent e) {
            Object source = e.getSource();

            updateMnemonicProperties();
            if (isEnabled() != model.isEnabled()) {
                setEnabled(model.isEnabled());
            }
            fireStateChanged();
            repaint();
        }

        //
        // ActionListener
        //
        public void actionPerformed(ActionEvent event) {
            fireActionPerformed(event);
        }

        //
        // ItemListener
        //
        public void itemStateChanged(ItemEvent event) {
            fireItemStateChanged(event);
            if (shouldUpdateSelectedStateFromAction()) {
                Action action = getAction();
                if (action != null && AbstractAction.hasSelectedKey(action)) {
                    boolean selected = isSelected();
                    boolean isActionSelected = AbstractAction.isSelected(
                              action);
                    if (isActionSelected != selected) {
                        action.putValue(Action.SELECTED_KEY, selected);
                    }
                }
            }
        }
    }

///////////////////
// Accessibility support
///////////////////
    /**
     * This class implements accessibility support for the
     * <code>AbstractButton</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to button and menu item
     * user-interface elements.
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
     * 这个类实现了对<code> AbstractButton </code>类的辅助功能支持。它提供了适用于按钮和菜单项用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     * 
     * @since 1.4
     */
    protected abstract class AccessibleAbstractButton
        extends AccessibleJComponent implements AccessibleAction,
        AccessibleValue, AccessibleText, AccessibleExtendedComponent {

        /**
         * Returns the accessible name of this object.
         *
         * <p>
         *  返回此对象的可访问名称。
         * 
         * 
         * @return the localized name of the object -- can be
         *              <code>null</code> if this
         *              object does not have a name
         */
        public String getAccessibleName() {
            String name = accessibleName;

            if (name == null) {
                name = (String)getClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY);
            }
            if (name == null) {
                name = AbstractButton.this.getText();
            }
            if (name == null) {
                name = super.getAccessibleName();
            }
            return name;
        }

        /**
         * Get the AccessibleIcons associated with this object if one
         * or more exist.  Otherwise return null.
         * <p>
         *  如果一个或多个存在,获取与此对象相关联的AccessibleIcons。否则返回null。
         * 
         * 
         * @since 1.3
         */
        public AccessibleIcon [] getAccessibleIcon() {
            Icon defaultIcon = getIcon();

            if (defaultIcon instanceof Accessible) {
                AccessibleContext ac =
                    ((Accessible)defaultIcon).getAccessibleContext();
                if (ac != null && ac instanceof AccessibleIcon) {
                    return new AccessibleIcon[] { (AccessibleIcon)ac };
                }
            }
            return null;
        }

        /**
         * Get the state set of this object.
         *
         * <p>
         *  获取此对象的状态集。
         * 
         * 
         * @return an instance of AccessibleState containing the current state
         * of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
        AccessibleStateSet states = super.getAccessibleStateSet();
            if (getModel().isArmed()) {
                states.add(AccessibleState.ARMED);
            }
            if (isFocusOwner()) {
                states.add(AccessibleState.FOCUSED);
            }
            if (getModel().isPressed()) {
                states.add(AccessibleState.PRESSED);
            }
            if (isSelected()) {
                states.add(AccessibleState.CHECKED);
            }
            return states;
        }

        /**
         * Get the AccessibleRelationSet associated with this object if one
         * exists.  Otherwise return null.
         * <p>
         *  获取与此对象关联的AccessibleRelationSet(如果存在)。否则返回null。
         * 
         * 
         * @see AccessibleRelation
         * @since 1.3
         */
        public AccessibleRelationSet getAccessibleRelationSet() {

            // Check where the AccessibleContext's relation
            // set already contains a MEMBER_OF relation.
            AccessibleRelationSet relationSet
                = super.getAccessibleRelationSet();

            if (!relationSet.contains(AccessibleRelation.MEMBER_OF)) {
                // get the members of the button group if one exists
                ButtonModel model = getModel();
                if (model != null && model instanceof DefaultButtonModel) {
                    ButtonGroup group = ((DefaultButtonModel)model).getGroup();
                    if (group != null) {
                        // set the target of the MEMBER_OF relation to be
                        // the members of the button group.
                        int len = group.getButtonCount();
                        Object [] target = new Object[len];
                        Enumeration<AbstractButton> elem = group.getElements();
                        for (int i = 0; i < len; i++) {
                            if (elem.hasMoreElements()) {
                                target[i] = elem.nextElement();
                            }
                        }
                        AccessibleRelation relation =
                            new AccessibleRelation(AccessibleRelation.MEMBER_OF);
                        relation.setTarget(target);
                        relationSet.add(relation);
                    }
                }
            }
            return relationSet;
        }

        /**
         * Get the AccessibleAction associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleAction interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleAction。在为该类实现Java辅助功能API时,返回此对象,该对象负责代表自身实现AccessibleAction接口。
         * 
         * 
         * @return this object
         */
        public AccessibleAction getAccessibleAction() {
            return this;
        }

        /**
         * Get the AccessibleValue associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleValue interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleValue。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleValue接口。
         * 
         * 
         * @return this object
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }

        /**
         * Returns the number of Actions available in this object.  The
         * default behavior of a button is to have one action - toggle
         * the button.
         *
         * <p>
         *  返回此对象中可用的操作数。按钮的默认行为是具有一个动作 - 切换按钮。
         * 
         * 
         * @return 1, the number of Actions in this object
         */
        public int getAccessibleActionCount() {
            return 1;
        }

        /**
         * Return a description of the specified action of the object.
         *
         * <p>
         * 返回对象的指定操作的描述。
         * 
         * 
         * @param i zero-based index of the actions
         */
        public String getAccessibleActionDescription(int i) {
            if (i == 0) {
                return UIManager.getString("AbstractButton.clickText");
            } else {
                return null;
            }
        }

        /**
         * Perform the specified Action on the object
         *
         * <p>
         *  对对象执行指定的Action
         * 
         * 
         * @param i zero-based index of actions
         * @return true if the the action was performed; else false.
         */
        public boolean doAccessibleAction(int i) {
            if (i == 0) {
                doClick();
                return true;
            } else {
                return false;
            }
        }

        /**
         * Get the value of this object as a Number.
         *
         * <p>
         *  获取此对象的值作为数字。
         * 
         * 
         * @return An Integer of 0 if this isn't selected or an Integer of 1 if
         * this is selected.
         * @see AbstractButton#isSelected
         */
        public Number getCurrentAccessibleValue() {
            if (isSelected()) {
                return Integer.valueOf(1);
            } else {
                return Integer.valueOf(0);
            }
        }

        /**
         * Set the value of this object as a Number.
         *
         * <p>
         *  将此对象的值设置为Number。
         * 
         * 
         * @return True if the value was set.
         */
        public boolean setCurrentAccessibleValue(Number n) {
            // TIGER - 4422535
            if (n == null) {
                return false;
            }
            int i = n.intValue();
            if (i == 0) {
                setSelected(false);
            } else {
                setSelected(true);
            }
            return true;
        }

        /**
         * Get the minimum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最小值作为数字。
         * 
         * 
         * @return an Integer of 0.
         */
        public Number getMinimumAccessibleValue() {
            return Integer.valueOf(0);
        }

        /**
         * Get the maximum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最大值作为数字。
         * 
         * 
         * @return An Integer of 1.
         */
        public Number getMaximumAccessibleValue() {
            return Integer.valueOf(1);
        }


        /* AccessibleText ---------- */

        public AccessibleText getAccessibleText() {
            View view = (View)AbstractButton.this.getClientProperty("html");
            if (view != null) {
                return this;
            } else {
                return null;
            }
        }

        /**
         * Given a point in local coordinates, return the zero-based index
         * of the character under that Point.  If the point is invalid,
         * this method returns -1.
         *
         * Note: the AbstractButton must have a valid size (e.g. have
         * been added to a parent container whose ancestor container
         * is a valid top-level window) for this method to be able
         * to return a meaningful value.
         *
         * <p>
         *  给定一个点在局部坐标,返回该点下的字符的从零开始的索引。如果该点无效,则此方法返回-1。
         * 
         *  注意：AbstractButton必须具有有效的大小(例如,已经添加到父容器,其祖先容器是有效的顶级窗口),以使此方法能够返回有意义的值。
         * 
         * 
         * @param p the Point in local coordinates
         * @return the zero-based index of the character under Point p; if
         * Point is invalid returns -1.
         * @since 1.3
         */
        public int getIndexAtPoint(Point p) {
            View view = (View) AbstractButton.this.getClientProperty("html");
            if (view != null) {
                Rectangle r = getTextRectangle();
                if (r == null) {
                    return -1;
                }
                Rectangle2D.Float shape =
                    new Rectangle2D.Float(r.x, r.y, r.width, r.height);
                Position.Bias bias[] = new Position.Bias[1];
                return view.viewToModel(p.x, p.y, shape, bias);
            } else {
                return -1;
            }
        }

        /**
         * Determine the bounding box of the character at the given
         * index into the string.  The bounds are returned in local
         * coordinates.  If the index is invalid an empty rectangle is
         * returned.
         *
         * Note: the AbstractButton must have a valid size (e.g. have
         * been added to a parent container whose ancestor container
         * is a valid top-level window) for this method to be able
         * to return a meaningful value.
         *
         * <p>
         *  确定给定索引处字符的边界框到字符串中。边界在本地坐标中返回。如果索引无效,则返回一个空的矩形。
         * 
         *  注意：AbstractButton必须具有有效的大小(例如,已经添加到父容器,其祖先容器是有效的顶级窗口),以使此方法能够返回有意义的值。
         * 
         * 
         * @param i the index into the String
         * @return the screen coordinates of the character's the bounding box,
         * if index is invalid returns an empty rectangle.
         * @since 1.3
         */
        public Rectangle getCharacterBounds(int i) {
            View view = (View) AbstractButton.this.getClientProperty("html");
            if (view != null) {
                Rectangle r = getTextRectangle();
                if (r == null) {
                    return null;
                }
                Rectangle2D.Float shape =
                    new Rectangle2D.Float(r.x, r.y, r.width, r.height);
                try {
                    Shape charShape =
                        view.modelToView(i, shape, Position.Bias.Forward);
                    return charShape.getBounds();
                } catch (BadLocationException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        /**
         * Return the number of characters (valid indicies)
         *
         * <p>
         *  返回字符数(有效指示)
         * 
         * 
         * @return the number of characters
         * @since 1.3
         */
        public int getCharCount() {
            View view = (View) AbstractButton.this.getClientProperty("html");
            if (view != null) {
                Document d = view.getDocument();
                if (d instanceof StyledDocument) {
                    StyledDocument doc = (StyledDocument)d;
                    return doc.getLength();
                }
            }
            return accessibleContext.getAccessibleName().length();
        }

        /**
         * Return the zero-based offset of the caret.
         *
         * Note: That to the right of the caret will have the same index
         * value as the offset (the caret is between two characters).
         * <p>
         *  返回插入符号的从零开始的偏移量。
         * 
         *  注意：插入符右侧的索引值与偏移量相同(插入符号在两个字符之间)。
         * 
         * 
         * @return the zero-based offset of the caret.
         * @since 1.3
         */
        public int getCaretPosition() {
            // There is no caret.
            return -1;
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
         * @since 1.3
         */
        public String getAtIndex(int part, int index) {
            if (index < 0 || index >= getCharCount()) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                try {
                    return getText(index, 1);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.WORD:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator words = BreakIterator.getWordInstance(getLocale());
                    words.setText(s);
                    int end = words.following(index);
                    return s.substring(words.previous(), end);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.SENTENCE:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator sentence =
                        BreakIterator.getSentenceInstance(getLocale());
                    sentence.setText(s);
                    int end = sentence.following(index);
                    return s.substring(sentence.previous(), end);
                } catch (BadLocationException e) {
                    return null;
                }
            default:
                return null;
            }
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
         * @since 1.3
         */
        public String getAfterIndex(int part, int index) {
            if (index < 0 || index >= getCharCount()) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                if (index+1 >= getCharCount()) {
                   return null;
                }
                try {
                    return getText(index+1, 1);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.WORD:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator words = BreakIterator.getWordInstance(getLocale());
                    words.setText(s);
                    int start = words.following(index);
                    if (start == BreakIterator.DONE || start >= s.length()) {
                        return null;
                    }
                    int end = words.following(start);
                    if (end == BreakIterator.DONE || end >= s.length()) {
                        return null;
                    }
                    return s.substring(start, end);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.SENTENCE:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator sentence =
                        BreakIterator.getSentenceInstance(getLocale());
                    sentence.setText(s);
                    int start = sentence.following(index);
                    if (start == BreakIterator.DONE || start > s.length()) {
                        return null;
                    }
                    int end = sentence.following(start);
                    if (end == BreakIterator.DONE || end > s.length()) {
                        return null;
                    }
                    return s.substring(start, end);
                } catch (BadLocationException e) {
                    return null;
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
         * 
         * @param part the AccessibleText.CHARACTER, AccessibleText.WORD,
         *   or AccessibleText.SENTENCE to retrieve
         * @param index an index within the text &gt;= 0
         * @return the letter, word, or sentence, null for an invalid index
         *  or part
         * @since 1.3
         */
        public String getBeforeIndex(int part, int index) {
            if (index < 0 || index > getCharCount()-1) {
                return null;
            }
            switch (part) {
            case AccessibleText.CHARACTER:
                if (index == 0) {
                    return null;
                }
                try {
                    return getText(index-1, 1);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.WORD:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator words = BreakIterator.getWordInstance(getLocale());
                    words.setText(s);
                    int end = words.following(index);
                    end = words.previous();
                    int start = words.previous();
                    if (start == BreakIterator.DONE) {
                        return null;
                    }
                    return s.substring(start, end);
                } catch (BadLocationException e) {
                    return null;
                }
            case AccessibleText.SENTENCE:
                try {
                    String s = getText(0, getCharCount());
                    BreakIterator sentence =
                        BreakIterator.getSentenceInstance(getLocale());
                    sentence.setText(s);
                    int end = sentence.following(index);
                    end = sentence.previous();
                    int start = sentence.previous();
                    if (start == BreakIterator.DONE) {
                        return null;
                    }
                    return s.substring(start, end);
                } catch (BadLocationException e) {
                    return null;
                }
            default:
                return null;
            }
        }

        /**
         * Return the AttributeSet for a given character at a given index
         *
         * <p>
         *  返回给定字符在给定索引的AttributeSet
         * 
         * 
         * @param i the zero-based index into the text
         * @return the AttributeSet of the character
         * @since 1.3
         */
        public AttributeSet getCharacterAttribute(int i) {
            View view = (View) AbstractButton.this.getClientProperty("html");
            if (view != null) {
                Document d = view.getDocument();
                if (d instanceof StyledDocument) {
                    StyledDocument doc = (StyledDocument)d;
                    Element elem = doc.getCharacterElement(i);
                    if (elem != null) {
                        return elem.getAttributes();
                    }
                }
            }
            return null;
        }

        /**
         * Returns the start offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         *
         * <p>
         * 返回所选文本内的起始偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
         * 
         * 
         * @return the index into the text of the start of the selection
         * @since 1.3
         */
        public int getSelectionStart() {
            // Text cannot be selected.
            return -1;
        }

        /**
         * Returns the end offset within the selected text.
         * If there is no selection, but there is
         * a caret, the start and end offsets will be the same.
         *
         * <p>
         *  返回所选文本内的结束偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
         * 
         * 
         * @return the index into the text of the end of the selection
         * @since 1.3
         */
        public int getSelectionEnd() {
            // Text cannot be selected.
            return -1;
        }

        /**
         * Returns the portion of the text that is selected.
         *
         * <p>
         *  返回所选文本的部分。
         * 
         * 
         * @return the String portion of the text that is selected
         * @since 1.3
         */
        public String getSelectedText() {
            // Text cannot be selected.
            return null;
        }

        /*
         * Returns the text substring starting at the specified
         * offset with the specified length.
         * <p>
         *  返回以指定长度在指定偏移处开始的文本子字符串。
         * 
         */
        private String getText(int offset, int length)
            throws BadLocationException {

            View view = (View) AbstractButton.this.getClientProperty("html");
            if (view != null) {
                Document d = view.getDocument();
                if (d instanceof StyledDocument) {
                    StyledDocument doc = (StyledDocument)d;
                    return doc.getText(offset, length);
                }
            }
            return null;
        }

        /*
         * Returns the bounding rectangle for the component text.
         * <p>
         *  返回组件文本的边界矩形。
         * 
         */
        private Rectangle getTextRectangle() {

            String text = AbstractButton.this.getText();
            Icon icon = (AbstractButton.this.isEnabled()) ? AbstractButton.this.getIcon() : AbstractButton.this.getDisabledIcon();

            if ((icon == null) && (text == null)) {
                return null;
            }

            Rectangle paintIconR = new Rectangle();
            Rectangle paintTextR = new Rectangle();
            Rectangle paintViewR = new Rectangle();
            Insets paintViewInsets = new Insets(0, 0, 0, 0);

            paintViewInsets = AbstractButton.this.getInsets(paintViewInsets);
            paintViewR.x = paintViewInsets.left;
            paintViewR.y = paintViewInsets.top;
            paintViewR.width = AbstractButton.this.getWidth() - (paintViewInsets.left + paintViewInsets.right);
            paintViewR.height = AbstractButton.this.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

            String clippedText = SwingUtilities.layoutCompoundLabel(
                AbstractButton.this,
                getFontMetrics(getFont()),
                text,
                icon,
                AbstractButton.this.getVerticalAlignment(),
                AbstractButton.this.getHorizontalAlignment(),
                AbstractButton.this.getVerticalTextPosition(),
                AbstractButton.this.getHorizontalTextPosition(),
                paintViewR,
                paintIconR,
                paintTextR,
                0);

            return paintTextR;
        }

        // ----- AccessibleExtendedComponent

        /**
         * Returns the AccessibleExtendedComponent
         *
         * <p>
         *  返回AccessibleExtendedComponent
         * 
         * 
         * @return the AccessibleExtendedComponent
         */
        AccessibleExtendedComponent getAccessibleExtendedComponent() {
            return this;
        }

        /**
         * Returns the tool tip text
         *
         * <p>
         *  返回工具提示文本
         * 
         * 
         * @return the tool tip text, if supported, of the object;
         * otherwise, null
         * @since 1.4
         */
        public String getToolTipText() {
            return AbstractButton.this.getToolTipText();
        }

        /**
         * Returns the titled border text
         *
         * <p>
         *  返回标题边框文本
         * 
         * 
         * @return the titled border text, if supported, of the object;
         * otherwise, null
         * @since 1.4
         */
        public String getTitledBorderText() {
            return super.getTitledBorderText();
        }

        /**
         * Returns key bindings associated with this object
         *
         * <p>
         *  返回与此对象关联的键绑定
         * 
         * 
         * @return the key bindings, if supported, of the object;
         * otherwise, null
         * @see AccessibleKeyBinding
         * @since 1.4
         */
        public AccessibleKeyBinding getAccessibleKeyBinding() {
            int mnemonic = AbstractButton.this.getMnemonic();
            if (mnemonic == 0) {
                return null;
            }
            return new ButtonKeyBinding(mnemonic);
        }

        class ButtonKeyBinding implements AccessibleKeyBinding {
            int mnemonic;

            ButtonKeyBinding(int mnemonic) {
                this.mnemonic = mnemonic;
            }

            /**
             * Returns the number of key bindings for this object
             *
             * <p>
             *  返回此对象的键绑定数
             * 
             * 
             * @return the zero-based number of key bindings for this object
             */
            public int getAccessibleKeyBindingCount() {
                return 1;
            }

            /**
             * Returns a key binding for this object.  The value returned is an
             * java.lang.Object which must be cast to appropriate type depending
             * on the underlying implementation of the key.  For example, if the
             * Object returned is a javax.swing.KeyStroke, the user of this
             * method should do the following:
             * <nf><code>
             * Component c = <get the component that has the key bindings>
             * AccessibleContext ac = c.getAccessibleContext();
             * AccessibleKeyBinding akb = ac.getAccessibleKeyBinding();
             * for (int i = 0; i < akb.getAccessibleKeyBindingCount(); i++) {
             *     Object o = akb.getAccessibleKeyBinding(i);
             *     if (o instanceof javax.swing.KeyStroke) {
             *         javax.swing.KeyStroke keyStroke = (javax.swing.KeyStroke)o;
             *         <do something with the key binding>
             *     }
             * }
             * </code></nf>
             *
             * <p>
             *  返回此对象的键绑定。返回的值是一个java.lang.Object,它必须根据键的底层实现转换为适当的类型。
             * 例如,如果返回的对象是javax.swing.KeyStroke,则此方法的用户应执行以下操作：<nf> <code> Component c = <获取具有键绑定的组件> AccessibleCont
             * ext ac = c.getAccessibleContext (); AccessibleKeyBinding akb = ac.getAccessibleKeyBinding(); for(int 
             * i = 0; i <akb.getAccessibleKeyBindingCount(); i ++){Object o = akb.getAccessibleKeyBinding(i); if(o instanceof javax.swing.KeyStroke){javax.swing.KeyStroke keyStroke =(javax.swing.KeyStroke)o;。
             * 
             * @param i zero-based index of the key bindings
             * @return a javax.lang.Object which specifies the key binding
             * @exception IllegalArgumentException if the index is
             * out of bounds
             * @see #getAccessibleKeyBindingCount
             */
            public java.lang.Object getAccessibleKeyBinding(int i) {
                if (i != 0) {
                    throw new IllegalArgumentException();
                }
                return KeyStroke.getKeyStroke(mnemonic, 0);
            }
        }
    }
}
