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

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.*;
import java.text.*;
import java.awt.geom.*;
import java.beans.Transient;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.plaf.LabelUI;
import javax.accessibility.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.plaf.basic.*;
import java.util.*;


/**
 * A display area for a short text string or an image,
 * or both.
 * A label does not react to input events.
 * As a result, it cannot get the keyboard focus.
 * A label can, however, display a keyboard alternative
 * as a convenience for a nearby component
 * that has a keyboard alternative but can't display it.
 * <p>
 * A <code>JLabel</code> object can display
 * either text, an image, or both.
 * You can specify where in the label's display area
 * the label's contents are aligned
 * by setting the vertical and horizontal alignment.
 * By default, labels are vertically centered
 * in their display area.
 * Text-only labels are leading edge aligned, by default;
 * image-only labels are horizontally centered, by default.
 * <p>
 * You can also specify the position of the text
 * relative to the image.
 * By default, text is on the trailing edge of the image,
 * with the text and image vertically aligned.
 * <p>
 * A label's leading and trailing edge are determined from the value of its
 * {@link java.awt.ComponentOrientation} property.  At present, the default
 * ComponentOrientation setting maps the leading edge to left and the trailing
 * edge to right.
 *
 * <p>
 * Finally, you can use the <code>setIconTextGap</code> method
 * to specify how many pixels
 * should appear between the text and the image.
 * The default is 4 pixels.
 * <p>
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/label.html">How to Use Labels</a>
 * in <em>The Java Tutorial</em>
 * for further documentation.
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
 * description: A component that displays a short string and an icon.
 *
 * <p>
 *  用于短文本字符串或图像或两者的显示区域。标签不会对输入事件做出反应。因此,它无法获得键盘焦点。然而,标签可以显示键盘替代,作为具有键盘替代但不能显示它的附近组件的方便。
 * <p>
 *  <code> JLabel </code>对象可以显示文本,图像或两者。您可以通过设置垂直和水平对齐来指定标签的显示区域中标签内容的对齐位置。默认情况下,标签在其显示区域中垂直居中。
 * 默认情况下,纯文本标签是前边对齐的;默认情况下,仅图像标签水平居中。
 * <p>
 *  您还可以指定文本相对于图像的位置。默认情况下,文本在图像的后缘,文本和图像垂直对齐。
 * <p>
 *  标签的前缘和后缘由其{@link java.awt.ComponentOrientation}属性的值确定。
 * 目前,默认的ComponentOrientation设置将前边缘映射到左边,将后边缘映射到右边。
 * 
 * <p>
 *  最后,您可以使用<code> setIconTextGap </code>方法来指定在文本和图像之间应该出现多少像素。默认值为4像素。
 * <p>
 * 有关详情,请参阅Java教程</em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/label.html">
 * 如何使用标签</a>文档。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：显示短字符串和图标的组件。
 * 
 * 
 * @author Hans Muller
 */
@SuppressWarnings("serial")
public class JLabel extends JComponent implements SwingConstants, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "LabelUI";

    private int mnemonic = '\0';
    private int mnemonicIndex = -1;

    private String text = "";         // "" rather than null, for BeanBox
    private Icon defaultIcon = null;
    private Icon disabledIcon = null;
    private boolean disabledIconSet = false;

    private int verticalAlignment = CENTER;
    private int horizontalAlignment = LEADING;
    private int verticalTextPosition = CENTER;
    private int horizontalTextPosition = TRAILING;
    private int iconTextGap = 4;

    protected Component labelFor = null;

    /**
     * Client property key used to determine what label is labeling the
     * component.  This is generally not used by labels, but is instead
     * used by components such as text areas that are being labeled by
     * labels.  When the labelFor property of a label is set, it will
     * automatically set the LABELED_BY_PROPERTY of the component being
     * labelled.
     *
     * <p>
     *  客户端属性键,用于确定为组件标记的标签。这通常不由标签使用,而是由组件(例如由标签标记的文本区域)使用。
     * 设置标签的labelFor属性时,它会自动设置要标记的组件的LABELED_BY_PROPERTY。
     * 
     * 
     * @see #setLabelFor
     */
    static final String LABELED_BY_PROPERTY = "labeledBy";

    /**
     * Creates a <code>JLabel</code> instance with the specified
     * text, image, and horizontal alignment.
     * The label is centered vertically in its display area.
     * The text is on the trailing edge of the image.
     *
     * <p>
     *  创建具有指定的文本,图像和水平对齐方式的<code> JLabel </code>实例。标签在其显示区域中垂直居中。文本位于图像的后缘。
     * 
     * 
     * @param text  The text to be displayed by the label.
     * @param icon  The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public JLabel(String text, Icon icon, int horizontalAlignment) {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified
     * text and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * <p>
     * 创建具有指定文本和水平对齐方式的<code> JLabel </code>实例。标签在其显示区域中垂直居中。
     * 
     * 
     * @param text  The text to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public JLabel(String text, int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     *
     * <p>
     *  使用指定的文本创建<code> JLabel </code>实例。标签与其显示区域的前边缘对齐,并垂直居中。
     * 
     * 
     * @param text  The text to be displayed by the label.
     */
    public JLabel(String text) {
        this(text, null, LEADING);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified
     * image and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * <p>
     *  创建具有指定图像和水平对齐方式的<code> JLabel </code>实例。标签在其显示区域中垂直居中。
     * 
     * 
     * @param image  The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public JLabel(Icon image, int horizontalAlignment) {
        this(null, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image.
     * The label is centered vertically and horizontally
     * in its display area.
     *
     * <p>
     *  使用指定的图像创建<code> JLabel </code>实例。标签在其显示区域中垂直和水平居中。
     * 
     * 
     * @param image  The image to be displayed by the label.
     */
    public JLabel(Icon image) {
        this(null, image, CENTER);
    }

    /**
     * Creates a <code>JLabel</code> instance with
     * no image and with an empty string for the title.
     * The label is centered vertically
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge
     * of the label's display area.
     * <p>
     *  创建一个没有图像并带有标题的空字符串的<code> JLabel </code>实例。标签在其显示区域中垂直居中。标签的内容一旦设置,将显示在标签显示区域的前缘。
     * 
     */
    public JLabel() {
        this("", null, LEADING);
    }


    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return LabelUI object
     */
    public LabelUI getUI() {
        return (LabelUI)ui;
    }


    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the LabelUI L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(LabelUI ui) {
        super.setUI(ui);
        // disabled icon is generated by LF so it should be unset here
        if (!disabledIconSet && disabledIcon != null) {
            setDisabledIcon(null);
        }
    }


    /**
     * Resets the UI property to a value from the current look and feel.
     *
     * <p>
     *  将UI属性重置为当前外观的值。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((LabelUI)UIManager.getUI(this));
    }


    /**
     * Returns a string that specifies the name of the l&amp;f class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的l&amp; f类的名称。
     * 
     * 
     * @return String "LabelUI"
     *
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Returns the text string that the label displays.
     *
     * <p>
     *  返回标签显示的文本字符串。
     * 
     * 
     * @return a String
     * @see #setText
     */
    public String getText() {
        return text;
    }


    /**
     * Defines the single line of text this component will display.  If
     * the value of text is null or empty string, nothing is displayed.
     * <p>
     * The default value of this property is null.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  定义此组件将显示的单行文本。如果text的值为null或空字符串,则不显示任何内容。
     * <p>
     *  此属性的缺省值为null。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @see #setVerticalTextPosition
     * @see #setHorizontalTextPosition
     * @see #setIcon
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Defines the single line of text this component will display.
     */
    public void setText(String text) {

        String oldAccessibleName = null;
        if (accessibleContext != null) {
            oldAccessibleName = accessibleContext.getAccessibleName();
        }

        String oldValue = this.text;
        this.text = text;
        firePropertyChange("text", oldValue, text);

        setDisplayedMnemonicIndex(
                      SwingUtilities.findDisplayedMnemonicIndex(
                                          text, getDisplayedMnemonic()));

        if ((accessibleContext != null)
            && (accessibleContext.getAccessibleName() != oldAccessibleName)) {
                accessibleContext.firePropertyChange(
                        AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                        oldAccessibleName,
                        accessibleContext.getAccessibleName());
        }
        if (text == null || oldValue == null || !text.equals(oldValue)) {
            revalidate();
            repaint();
        }
    }


    /**
     * Returns the graphic image (glyph, icon) that the label displays.
     *
     * <p>
     *  返回标签显示的图形图像(字形,图标)。
     * 
     * 
     * @return an Icon
     * @see #setIcon
     */
    public Icon getIcon() {
        return defaultIcon;
    }

    /**
     * Defines the icon this component will display.  If
     * the value of icon is null, nothing is displayed.
     * <p>
     * The default value of this property is null.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 定义此组件将显示的图标。如果icon的值为null,则不显示任何内容。
     * <p>
     *  此属性的缺省值为null。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @see #setVerticalTextPosition
     * @see #setHorizontalTextPosition
     * @see #getIcon
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The icon this component will display.
     */
    public void setIcon(Icon icon) {
        Icon oldValue = defaultIcon;
        defaultIcon = icon;

        /* If the default icon has really changed and we had
         * generated the disabled icon for this component
         * (in other words, setDisabledIcon() was never called), then
         * clear the disabledIcon field.
         * <p>
         *  生成此组件的禁用图标(换句话说,setDisabledIcon()从未被调用),然后清除disabledIcon字段。
         * 
         */
        if ((defaultIcon != oldValue) && !disabledIconSet) {
            disabledIcon = null;
        }

        firePropertyChange("icon", oldValue, defaultIcon);

        if ((accessibleContext != null) && (oldValue != defaultIcon)) {
                accessibleContext.firePropertyChange(
                        AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                        oldValue, defaultIcon);
        }

        /* If the default icon has changed and the new one is
         * a different size, then revalidate.   Repaint if the
         * default icon has changed.
         * <p>
         *  不同的大小,然后重新验证。重新绘制默认图标是否已更改。
         * 
         */
        if (defaultIcon != oldValue) {
            if ((defaultIcon == null) ||
                (oldValue == null) ||
                (defaultIcon.getIconWidth() != oldValue.getIconWidth()) ||
                (defaultIcon.getIconHeight() != oldValue.getIconHeight())) {
                revalidate();
            }
            repaint();
        }
    }


    /**
     * Returns the icon used by the label when it's disabled.
     * If no disabled icon has been set this will forward the call to
     * the look and feel to construct an appropriate disabled Icon.
     * <p>
     * Some look and feels might not render the disabled Icon, in which
     * case they will ignore this.
     *
     * <p>
     *  返回标签已停用时使用的图标。如果没有设置禁用图标,这将转发呼叫的外观和感觉来构造适当的禁用图标。
     * <p>
     *  一些外观和感觉可能不会呈现禁用的图标,在这种情况下,他们会忽略这一点。
     * 
     * 
     * @return the <code>disabledIcon</code> property
     * @see #setDisabledIcon
     * @see javax.swing.LookAndFeel#getDisabledIcon
     * @see ImageIcon
     */
    @Transient
    public Icon getDisabledIcon() {
        if (!disabledIconSet && disabledIcon == null && defaultIcon != null) {
            disabledIcon = UIManager.getLookAndFeel().getDisabledIcon(this, defaultIcon);
            if (disabledIcon != null) {
                firePropertyChange("disabledIcon", null, disabledIcon);
            }
        }
        return disabledIcon;
    }


    /**
     * Set the icon to be displayed if this JLabel is "disabled"
     * (JLabel.setEnabled(false)).
     * <p>
     * The default value of this property is null.
     *
     * <p>
     *  如果此JLabel为"disabled"(JLabel.setEnabled(false)),请设置要显示的图标。
     * <p>
     *  此属性的缺省值为null。
     * 
     * 
     * @param disabledIcon the Icon to display when the component is disabled
     * @see #getDisabledIcon
     * @see #setEnabled
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The icon to display if the label is disabled.
     */
    public void setDisabledIcon(Icon disabledIcon) {
        Icon oldValue = this.disabledIcon;
        this.disabledIcon = disabledIcon;
        disabledIconSet = (disabledIcon != null);
        firePropertyChange("disabledIcon", oldValue, disabledIcon);
        if (disabledIcon != oldValue) {
            if (disabledIcon == null || oldValue == null ||
                disabledIcon.getIconWidth() != oldValue.getIconWidth() ||
                disabledIcon.getIconHeight() != oldValue.getIconHeight()) {
                revalidate();
            }
            if (!isEnabled()) {
                repaint();
            }
        }
    }


    /**
     * Specify a keycode that indicates a mnemonic key.
     * This property is used when the label is part of a larger component.
     * If the labelFor property of the label is not null, the label will
     * call the requestFocus method of the component specified by the
     * labelFor property when the mnemonic is activated.
     *
     * <p>
     *  指定指示助记键的键码。当标签是较大组件的一部分时,将使用此属性。
     * 如果标签的labelFor属性不为null,则当激活助记符时,标签将调用由labelFor属性指定的组件的requestFocus方法。
     * 
     * 
     * @see #getLabelFor
     * @see #setLabelFor
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The mnemonic keycode.
     */
    public void setDisplayedMnemonic(int key) {
        int oldKey = mnemonic;
        mnemonic = key;
        firePropertyChange("displayedMnemonic", oldKey, mnemonic);

        setDisplayedMnemonicIndex(
            SwingUtilities.findDisplayedMnemonicIndex(getText(), mnemonic));

        if (key != oldKey) {
            revalidate();
            repaint();
        }
    }


    /**
     * Specifies the displayedMnemonic as a char value.
     *
     * <p>
     *  将displayedMnemonic指定为char值。
     * 
     * 
     * @param aChar  a char specifying the mnemonic to display
     * @see #setDisplayedMnemonic(int)
     */
    public void setDisplayedMnemonic(char aChar) {
        int vk = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(aChar);
        if (vk != java.awt.event.KeyEvent.VK_UNDEFINED) {
            setDisplayedMnemonic(vk);
        }
    }


    /**
     * Return the keycode that indicates a mnemonic key.
     * This property is used when the label is part of a larger component.
     * If the labelFor property of the label is not null, the label will
     * call the requestFocus method of the component specified by the
     * labelFor property when the mnemonic is activated.
     *
     * <p>
     *  返回指示助记键的键码。当标签是较大组件的一部分时,将使用此属性。
     * 如果标签的labelFor属性不为null,则当激活助记符时,标签将调用由labelFor属性指定的组件的requestFocus方法。
     * 
     * 
     * @return int value for the mnemonic key
     *
     * @see #getLabelFor
     * @see #setLabelFor
     */
    public int getDisplayedMnemonic() {
        return mnemonic;
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
     * <code>setDisplayedMnemonic(KeyEvent.VK_A)</code>.
     *
     * <p>
     * 提供关于文本中应当装饰哪个字符以表示助记符的外观和感觉的提示。不是所有的外观和感觉可能支持这一点。值-1表示没有助记符,助记符不包含在字符串中,或​​者开发人员不希望显示助记符。
     * <p>
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。
     * 例如,如果文本是"另存为",助记符为'a',并且您想要将'A'装饰为'保存<u> A </u>',则必须调用<code> setDisplayedMnemonicIndex(5)</code>在调用<code>
     *  setDisplayedMnemonic(KeyEvent.VK_A)</code>之后。
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。
     * 
     * 
     * @since 1.4
     * @param index Index into the String to underline
     * @exception IllegalArgumentException will be thrown if <code>index</code>
     *            is &gt;= length of the text, or &lt; -1
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
     * Verify that key is a legal value for the horizontalAlignment properties.
     *
     * <p>
     *  验证该键是horizo​​ntalAlignment属性的合法值。
     * 
     * 
     * @param key the property value to check
     * @param message the IllegalArgumentException detail message
     * @exception IllegalArgumentException if key isn't LEFT, CENTER, RIGHT,
     * LEADING or TRAILING.
     * @see #setHorizontalTextPosition
     * @see #setHorizontalAlignment
     */
    protected int checkHorizontalKey(int key, String message) {
        if ((key == LEFT) ||
            (key == CENTER) ||
            (key == RIGHT) ||
            (key == LEADING) ||
            (key == TRAILING)) {
            return key;
        }
        else {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Verify that key is a legal value for the
     * verticalAlignment or verticalTextPosition properties.
     *
     * <p>
     *  验证该键是verticalAlignment或verticalTextPosition属性的合法值。
     * 
     * 
     * @param key the property value to check
     * @param message the IllegalArgumentException detail message
     * @exception IllegalArgumentException if key isn't TOP, CENTER, or BOTTOM.
     * @see #setVerticalAlignment
     * @see #setVerticalTextPosition
     */
    protected int checkVerticalKey(int key, String message) {
        if ((key == TOP) || (key == CENTER) || (key == BOTTOM)) {
            return key;
        }
        else {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Returns the amount of space between the text and the icon
     * displayed in this label.
     *
     * <p>
     *  返回文本和此标签中显示的图标之间的空格大小。
     * 
     * 
     * @return an int equal to the number of pixels between the text
     *         and the icon.
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
        firePropertyChange("iconTextGap", oldValue, iconTextGap);
        if (iconTextGap != oldValue) {
            revalidate();
            repaint();
        }
    }



    /**
     * Returns the alignment of the label's contents along the Y axis.
     *
     * <p>
     *  返回标签内容沿Y轴的对齐方式。
     * 
     * 
     * @return   The value of the verticalAlignment property, one of the
     *           following constants defined in <code>SwingConstants</code>:
     *           <code>TOP</code>,
     *           <code>CENTER</code>, or
     *           <code>BOTTOM</code>.
     *
     * @see SwingConstants
     * @see #setVerticalAlignment
     */
    public int getVerticalAlignment() {
        return verticalAlignment;
    }


    /**
     * Sets the alignment of the label's contents along the Y axis.
     * <p>
     * The default value of this property is CENTER.
     *
     * <p>
     *  设置标签内容沿Y轴的对齐方式。
     * <p>
     * 此属性的默认值为CENTER。
     * 
     * 
     * @param alignment One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>TOP</code>,
     *           <code>CENTER</code> (the default), or
     *           <code>BOTTOM</code>.
     *
     * @see SwingConstants
     * @see #getVerticalAlignment
     * @beaninfo
     *        bound: true
     *         enum: TOP    SwingConstants.TOP
     *               CENTER SwingConstants.CENTER
     *               BOTTOM SwingConstants.BOTTOM
     *    attribute: visualUpdate true
     *  description: The alignment of the label's contents along the Y axis.
     */
    public void setVerticalAlignment(int alignment) {
        if (alignment == verticalAlignment) return;
        int oldValue = verticalAlignment;
        verticalAlignment = checkVerticalKey(alignment, "verticalAlignment");
        firePropertyChange("verticalAlignment", oldValue, verticalAlignment);
        repaint();
    }


    /**
     * Returns the alignment of the label's contents along the X axis.
     *
     * <p>
     *  返回标签内容沿X轴的对齐方式。
     * 
     * 
     * @return   The value of the horizontalAlignment property, one of the
     *           following constants defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     *
     * @see #setHorizontalAlignment
     * @see SwingConstants
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the alignment of the label's contents along the X axis.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置标签内容沿X轴的对齐方式。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param alignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code> (the default for image-only labels),
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> (the default for text-only labels) or
     *           <code>TRAILING</code>.
     *
     * @see SwingConstants
     * @see #getHorizontalAlignment
     * @beaninfo
     *        bound: true
     *         enum: LEFT     SwingConstants.LEFT
     *               CENTER   SwingConstants.CENTER
     *               RIGHT    SwingConstants.RIGHT
     *               LEADING  SwingConstants.LEADING
     *               TRAILING SwingConstants.TRAILING
     *    attribute: visualUpdate true
     *  description: The alignment of the label's content along the X axis.
     */
    public void setHorizontalAlignment(int alignment) {
        if (alignment == horizontalAlignment) return;
        int oldValue = horizontalAlignment;
        horizontalAlignment = checkHorizontalKey(alignment,
                                                 "horizontalAlignment");
        firePropertyChange("horizontalAlignment",
                           oldValue, horizontalAlignment);
        repaint();
    }


    /**
     * Returns the vertical position of the label's text,
     * relative to its image.
     *
     * <p>
     *  返回标签文本相对于其图片的垂直位置。
     * 
     * 
     * @return   One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>TOP</code>,
     *           <code>CENTER</code>, or
     *           <code>BOTTOM</code>.
     *
     * @see #setVerticalTextPosition
     * @see SwingConstants
     */
    public int getVerticalTextPosition() {
        return verticalTextPosition;
    }


    /**
     * Sets the vertical position of the label's text,
     * relative to its image.
     * <p>
     * The default value of this property is CENTER.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置标签文本相对于其图像的垂直位置。
     * <p>
     *  此属性的默认值为CENTER。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param textPosition  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>TOP</code>,
     *           <code>CENTER</code> (the default), or
     *           <code>BOTTOM</code>.
     *
     * @see SwingConstants
     * @see #getVerticalTextPosition
     * @beaninfo
     *        bound: true
     *         enum: TOP    SwingConstants.TOP
     *               CENTER SwingConstants.CENTER
     *               BOTTOM SwingConstants.BOTTOM
     *       expert: true
     *    attribute: visualUpdate true
     *  description: The vertical position of the text relative to it's image.
     */
    public void setVerticalTextPosition(int textPosition) {
        if (textPosition == verticalTextPosition) return;
        int old = verticalTextPosition;
        verticalTextPosition = checkVerticalKey(textPosition,
                                                "verticalTextPosition");
        firePropertyChange("verticalTextPosition", old, verticalTextPosition);
        revalidate();
        repaint();
    }


    /**
     * Returns the horizontal position of the label's text,
     * relative to its image.
     *
     * <p>
     *  返回标签文本相对于其图像的水平位置。
     * 
     * 
     * @return   One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     *
     * @see SwingConstants
     */
    public int getHorizontalTextPosition() {
        return horizontalTextPosition;
    }


    /**
     * Sets the horizontal position of the label's text,
     * relative to its image.
     *
     * <p>
     *  设置标签文本相对于其图像的水平位置。
     * 
     * 
     * @param textPosition  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code>, or
     *           <code>TRAILING</code> (the default).
     * @exception IllegalArgumentException
     *
     * @see SwingConstants
     * @beaninfo
     *       expert: true
     *        bound: true
     *         enum: LEFT     SwingConstants.LEFT
     *               CENTER   SwingConstants.CENTER
     *               RIGHT    SwingConstants.RIGHT
     *               LEADING  SwingConstants.LEADING
     *               TRAILING SwingConstants.TRAILING
     *    attribute: visualUpdate true
     *  description: The horizontal position of the label's text,
     *               relative to its image.
     */
    public void setHorizontalTextPosition(int textPosition) {
        int old = horizontalTextPosition;
        this.horizontalTextPosition = checkHorizontalKey(textPosition,
                                                "horizontalTextPosition");
        firePropertyChange("horizontalTextPosition",
                           old, horizontalTextPosition);
        revalidate();
        repaint();
    }


    /**
     * This is overridden to return false if the current Icon's Image is
     * not equal to the passed in Image <code>img</code>.
     *
     * <p>
     *  如果当前图标的图像不等于图像<code> img </code>中传递的图像,则覆盖此属性返回false。
     * 
     * 
     * @see     java.awt.image.ImageObserver
     * @see     java.awt.Component#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public boolean imageUpdate(Image img, int infoflags,
                               int x, int y, int w, int h) {
        // Don't use getDisabledIcon, will trigger creation of icon if icon
        // not set.
        if (!isShowing() ||
            !SwingUtilities.doesIconReferenceImage(getIcon(), img) &&
            !SwingUtilities.doesIconReferenceImage(disabledIcon, img)) {

            return false;
        }
        return super.imageUpdate(img, infoflags, x, y, w, h);
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


    /**
     * Returns a string representation of this JLabel. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JLabel的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JLabel.
     */
    protected String paramString() {
        String textString = (text != null ?
                             text : "");
        String defaultIconString = ((defaultIcon != null)
                                    && (defaultIcon != this)  ?
                                    defaultIcon.toString() : "");
        String disabledIconString = ((disabledIcon != null)
                                     && (disabledIcon != this) ?
                                     disabledIcon.toString() : "");
        String labelForString = (labelFor  != null ?
                                 labelFor.toString() : "");
        String verticalAlignmentString;
        if (verticalAlignment == TOP) {
            verticalAlignmentString = "TOP";
        } else if (verticalAlignment == CENTER) {
            verticalAlignmentString = "CENTER";
        } else if (verticalAlignment == BOTTOM) {
            verticalAlignmentString = "BOTTOM";
        } else verticalAlignmentString = "";
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
        String verticalTextPositionString;
        if (verticalTextPosition == TOP) {
            verticalTextPositionString = "TOP";
        } else if (verticalTextPosition == CENTER) {
            verticalTextPositionString = "CENTER";
        } else if (verticalTextPosition == BOTTOM) {
            verticalTextPositionString = "BOTTOM";
        } else verticalTextPositionString = "";
        String horizontalTextPositionString;
        if (horizontalTextPosition == LEFT) {
            horizontalTextPositionString = "LEFT";
        } else if (horizontalTextPosition == CENTER) {
            horizontalTextPositionString = "CENTER";
        } else if (horizontalTextPosition == RIGHT) {
            horizontalTextPositionString = "RIGHT";
        } else if (horizontalTextPosition == LEADING) {
            horizontalTextPositionString = "LEADING";
        } else if (horizontalTextPosition == TRAILING) {
            horizontalTextPositionString = "TRAILING";
        } else horizontalTextPositionString = "";

        return super.paramString() +
        ",defaultIcon=" + defaultIconString +
        ",disabledIcon=" + disabledIconString +
        ",horizontalAlignment=" + horizontalAlignmentString +
        ",horizontalTextPosition=" + horizontalTextPositionString +
        ",iconTextGap=" + iconTextGap +
        ",labelFor=" + labelForString +
        ",text=" + textString +
        ",verticalAlignment=" + verticalAlignmentString +
        ",verticalTextPosition=" + verticalTextPositionString;
    }

    /**
     * --- Accessibility Support ---
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Get the component this is labelling.
     *
     * <p>
     *  获取正在标记的组件。
     * 
     * 
     * @return the Component this is labelling.  Can be null if this
     * does not label a Component.  If the displayedMnemonic
     * property is set and the labelFor property is also set, the label
     * will call the requestFocus method of the component specified by the
     * labelFor property when the mnemonic is activated.
     *
     * @see #getDisplayedMnemonic
     * @see #setDisplayedMnemonic
     */
    public Component getLabelFor() {
        return labelFor;
    }

    /**
     * Set the component this is labelling.  Can be null if this does not
     * label a Component.  If the displayedMnemonic property is set
     * and the labelFor property is also set, the label will
     * call the requestFocus method of the component specified by the
     * labelFor property when the mnemonic is activated.
     *
     * <p>
     *  设置要标记的组件。如果这不标记组件,可以为null。
     * 如果设置了displayedMnemonic属性并且还设置了labelFor属性,则当激活助记符时,标签将调用由labelFor属性指定的组件的requestFocus方法。
     * 
     * 
     * @param c  the Component this label is for, or null if the label is
     *           not the label for a component
     *
     * @see #getDisplayedMnemonic
     * @see #setDisplayedMnemonic
     *
     * @beaninfo
     *        bound: true
     *  description: The component this is labelling.
     */
    public void setLabelFor(Component c) {
        Component oldC = labelFor;
        labelFor = c;
        firePropertyChange("labelFor", oldC, c);

        if (oldC instanceof JComponent) {
            ((JComponent)oldC).putClientProperty(LABELED_BY_PROPERTY, null);
        }
        if (c instanceof JComponent) {
            ((JComponent)c).putClientProperty(LABELED_BY_PROPERTY, this);
        }
    }

    /**
     * Get the AccessibleContext of this object
     *
     * <p>
     *  获取此对象的AccessibleContext
     * 
     * 
     * @return the AccessibleContext of this object
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this Label.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJLabel();
        }
        return accessibleContext;
    }

    /**
     * The class used to obtain the accessible role for this object.
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
     * 用于获取此对象的可访问角色的类。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleJLabel extends AccessibleJComponent
        implements AccessibleText, AccessibleExtendedComponent {

        /**
         * Get the accessible name of this object.
         *
         * <p>
         *  获取此对象的可访问名称。
         * 
         * 
         * @return the localized name of the object -- can be null if this
         * object does not have a name
         * @see AccessibleContext#setAccessibleName
         */
        public String getAccessibleName() {
            String name = accessibleName;

            if (name == null) {
                name = (String)getClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY);
            }
            if (name == null) {
                name = JLabel.this.getText();
            }
            if (name == null) {
                name = super.getAccessibleName();
            }
            return name;
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.LABEL;
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
            Icon icon = getIcon();
            if (icon instanceof Accessible) {
                AccessibleContext ac =
                ((Accessible)icon).getAccessibleContext();
                if (ac != null && ac instanceof AccessibleIcon) {
                    return new AccessibleIcon[] { (AccessibleIcon)ac };
                }
            }
            return null;
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
            // set already contains a LABEL_FOR relation.
            AccessibleRelationSet relationSet
                = super.getAccessibleRelationSet();

            if (!relationSet.contains(AccessibleRelation.LABEL_FOR)) {
                Component c = JLabel.this.getLabelFor();
                if (c != null) {
                    AccessibleRelation relation
                        = new AccessibleRelation(AccessibleRelation.LABEL_FOR);
                    relation.setTarget(c);
                    relationSet.add(relation);
                }
            }
            return relationSet;
        }


        /* AccessibleText ---------- */

        public AccessibleText getAccessibleText() {
            View view = (View)JLabel.this.getClientProperty("html");
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
         * <p>
         *  给定一个点在局部坐标,返回该点下的字符的从零开始的索引。如果该点无效,则此方法返回-1。
         * 
         * 
         * @param p the Point in local coordinates
         * @return the zero-based index of the character under Point p; if
         * Point is invalid returns -1.
         * @since 1.3
         */
        public int getIndexAtPoint(Point p) {
            View view = (View) JLabel.this.getClientProperty("html");
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
         * Returns the bounding box of the character at the given
         * index in the string.  The bounds are returned in local
         * coordinates. If the index is invalid, <code>null</code> is returned.
         *
         * <p>
         *  返回字符串中给定索引处的字符的边界框。边界在本地坐标中返回。如果索引无效,则返回<code> null </code>。
         * 
         * 
         * @param i the index into the String
         * @return the screen coordinates of the character's bounding box.
         * If the index is invalid, <code>null</code> is returned.
         * @since 1.3
         */
        public Rectangle getCharacterBounds(int i) {
            View view = (View) JLabel.this.getClientProperty("html");
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
            View view = (View) JLabel.this.getClientProperty("html");
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
            View view = (View) JLabel.this.getClientProperty("html");
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

            View view = (View) JLabel.this.getClientProperty("html");
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

            String text = JLabel.this.getText();
            Icon icon = (JLabel.this.isEnabled()) ? JLabel.this.getIcon() : JLabel.this.getDisabledIcon();

            if ((icon == null) && (text == null)) {
                return null;
            }

            Rectangle paintIconR = new Rectangle();
            Rectangle paintTextR = new Rectangle();
            Rectangle paintViewR = new Rectangle();
            Insets paintViewInsets = new Insets(0, 0, 0, 0);

            paintViewInsets = JLabel.this.getInsets(paintViewInsets);
            paintViewR.x = paintViewInsets.left;
            paintViewR.y = paintViewInsets.top;
            paintViewR.width = JLabel.this.getWidth() - (paintViewInsets.left + paintViewInsets.right);
            paintViewR.height = JLabel.this.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

            String clippedText = SwingUtilities.layoutCompoundLabel(
                (JComponent)JLabel.this,
                getFontMetrics(getFont()),
                text,
                icon,
                JLabel.this.getVerticalAlignment(),
                JLabel.this.getHorizontalAlignment(),
                JLabel.this.getVerticalTextPosition(),
                JLabel.this.getHorizontalTextPosition(),
                paintViewR,
                paintIconR,
                paintTextR,
                JLabel.this.getIconTextGap());

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
            return JLabel.this.getToolTipText();
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
            int mnemonic = JLabel.this.getDisplayedMnemonic();
            if (mnemonic == 0) {
                return null;
            }
            return new LabelKeyBinding(mnemonic);
        }

        class LabelKeyBinding implements AccessibleKeyBinding {
            int mnemonic;

            LabelKeyBinding(int mnemonic) {
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

    }  // AccessibleJComponent
}
