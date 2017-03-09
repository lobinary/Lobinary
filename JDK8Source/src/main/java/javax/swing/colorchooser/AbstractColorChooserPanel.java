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

package javax.swing.colorchooser;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 * This is the abstract superclass for color choosers.  If you want to add
 * a new color chooser panel into a <code>JColorChooser</code>, subclass
 * this class.
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
 *  这是颜色选择器的抽象超类。如果你想添加一个新的颜色选择器面板到<code> JColorChooser </code>,这个类的子类。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Tom Santos
 * @author Steve Wilson
 */
public abstract class AbstractColorChooserPanel extends JPanel {

    private final PropertyChangeListener enabledListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent event) {
            Object value = event.getNewValue();
            if (value instanceof Boolean) {
                setEnabled((Boolean) value);
            }
        }
    };

    /**
     *
     * <p>
     */
    private JColorChooser chooser;

    /**
      * Invoked automatically when the model's state changes.
      * It is also called by <code>installChooserPanel</code> to allow
      * you to set up the initial state of your chooser.
      * Override this method to update your <code>ChooserPanel</code>.
      * <p>
      *  当模型的状态更改时自动调用。它也被<code> installChooserPanel </code>调用以允许您设置选择器的初始状态。
      * 覆盖此方法以更新您的<code> ChooserPanel </code>。
      * 
      */
    public abstract void updateChooser();

    /**
     * Builds a new chooser panel.
     * <p>
     *  构建新的选择器面板。
     * 
     */
    protected abstract void buildChooser();

    /**
     * Returns a string containing the display name of the panel.
     * <p>
     *  返回包含面板的显示名称的字符串。
     * 
     * 
     * @return the name of the display panel
     */
    public abstract String getDisplayName();

    /**
     * Provides a hint to the look and feel as to the
     * <code>KeyEvent.VK</code> constant that can be used as a mnemonic to
     * access the panel. A return value &lt;= 0 indicates there is no mnemonic.
     * <p>
     * The return value here is a hint, it is ultimately up to the look
     * and feel to honor the return value in some meaningful way.
     * <p>
     * This implementation returns 0, indicating the
     * <code>AbstractColorChooserPanel</code> does not support a mnemonic,
     * subclasses wishing a mnemonic will need to override this.
     *
     * <p>
     *  提供了对可以用作访问面板的助记符的<code> KeyEvent.VK </code>常量的外观和感觉的提示。返回值<= 0表示没有助记符。
     * <p>
     *  这里的返回值是一个提示,它最终取决于外观和感觉,以一些有意义的方式来兑现返回值。
     * <p>
     * 此实现返回0,表示<code> AbstractColorChooserPanel </code>不支持助记符,希望助记符的子类将需要覆盖此。
     * 
     * 
     * @return KeyEvent.VK constant identifying the mnemonic; &lt;= 0 for no
     *         mnemonic
     * @see #getDisplayedMnemonicIndex
     * @since 1.4
     */
    public int getMnemonic() {
        return 0;
    }

    /**
     * Provides a hint to the look and feel as to the index of the character in
     * <code>getDisplayName</code> that should be visually identified as the
     * mnemonic. The look and feel should only use this if
     * <code>getMnemonic</code> returns a value &gt; 0.
     * <p>
     * The return value here is a hint, it is ultimately up to the look
     * and feel to honor the return value in some meaningful way. For example,
     * a look and feel may wish to render each
     * <code>AbstractColorChooserPanel</code> in a <code>JTabbedPane</code>,
     * and further use this return value to underline a character in
     * the <code>getDisplayName</code>.
     * <p>
     * This implementation returns -1, indicating the
     * <code>AbstractColorChooserPanel</code> does not support a mnemonic,
     * subclasses wishing a mnemonic will need to override this.
     *
     * <p>
     *  提供对<code> getDisplayName </code>中应该被视觉识别为助记符的字符的索引的外观和感觉的提示。
     * 只有在<code> getMnemonic </code>返回一个值&gt; 0。
     * <p>
     *  这里的返回值是一个提示,它最终取决于外观和感觉,以一些有意义的方式来兑现返回值。
     * 例如,一个外观和感觉可能希望在<code> JTabbedPane </code>中呈现每个<code> AbstractColorChooserPanel </code>,并进一步使用此返回值来强调<code>
     *  getDisplayName </code >。
     *  这里的返回值是一个提示,它最终取决于外观和感觉,以一些有意义的方式来兑现返回值。
     * <p>
     *  此实现返回-1,表示<code> AbstractColorChooserPanel </code>不支持助记符,希望助记符的子类将需要覆盖此。
     * 
     * 
     * @return Character index to render mnemonic for; -1 to provide no
     *                   visual identifier for this panel.
     * @see #getMnemonic
     * @since 1.4
     */
    public int getDisplayedMnemonicIndex() {
        return -1;
    }

    /**
     * Returns the small display icon for the panel.
     * <p>
     *  返回面板的小显示图标。
     * 
     * 
     * @return the small display icon
     */
    public abstract Icon getSmallDisplayIcon();

    /**
     * Returns the large display icon for the panel.
     * <p>
     *  返回面板的大显示图标。
     * 
     * 
     * @return the large display icon
     */
    public abstract Icon getLargeDisplayIcon();

    /**
     * Invoked when the panel is added to the chooser.
     * If you override this, be sure to call <code>super</code>.
     * <p>
     *  将面板添加到选择器时调用。如果你重写这个,一定要调用<code> super </code>。
     * 
     * 
     * @param enclosingChooser  the panel to be added
     * @exception RuntimeException  if the chooser panel has already been
     *                          installed
     */
    public void installChooserPanel(JColorChooser enclosingChooser) {
        if (chooser != null) {
            throw new RuntimeException ("This chooser panel is already installed");
        }
        chooser = enclosingChooser;
        chooser.addPropertyChangeListener("enabled", enabledListener);
        setEnabled(chooser.isEnabled());
        buildChooser();
        updateChooser();
    }

    /**
     * Invoked when the panel is removed from the chooser.
     * If override this, be sure to call <code>super</code>.
     * <p>
     *  当面板从选择器中移除时调用。如果覆盖这个,一定要调用<code> super </code>。
     * 
     */
  public void uninstallChooserPanel(JColorChooser enclosingChooser) {
        chooser.removePropertyChangeListener("enabled", enabledListener);
        chooser = null;
    }

    /**
      * Returns the model that the chooser panel is editing.
      * <p>
      *  返回选择器面板正在编辑的模型。
      * 
      * 
      * @return the <code>ColorSelectionModel</code> model this panel
      *         is editing
      */
    public ColorSelectionModel getColorSelectionModel() {
        return (this.chooser != null)
                ? this.chooser.getSelectionModel()
                : null;
    }

    /**
     * Returns the color that is currently selected.
     * <p>
     *  返回当前选择的颜色。
     * 
     * 
     * @return the <code>Color</code> that is selected
     */
    protected Color getColorFromModel() {
        ColorSelectionModel model = getColorSelectionModel();
        return (model != null)
                ? model.getSelectedColor()
                : null;
    }

    void setSelectedColor(Color color) {
        ColorSelectionModel model = getColorSelectionModel();
        if (model != null) {
            model.setSelectedColor(color);
        }
    }

    /**
     * Draws the panel.
     * <p>
     *  绘制面板。
     * 
     * 
     * @param g  the <code>Graphics</code> object
     */
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * Returns an integer from the defaults table. If <code>key</code> does
     * not map to a valid <code>Integer</code>, <code>default</code> is
     * returned.
     *
     * <p>
     * 从defaults表返回一个整数。如果<code>键</code>未映射到有效的<code> Integer </code>,则会返回<code> default </code>。
     * 
     * @param key  an <code>Object</code> specifying the int
     * @param defaultValue Returned value if <code>key</code> is not available,
     *                     or is not an Integer
     * @return the int
     */
    int getInt(Object key, int defaultValue) {
        Object value = UIManager.get(key, getLocale());

        if (value instanceof Integer) {
            return ((Integer)value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String)value);
            } catch (NumberFormatException nfe) {}
        }
        return defaultValue;
    }
}
