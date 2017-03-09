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

package javax.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.colorchooser.*;
import javax.swing.plaf.ColorChooserUI;
import javax.accessibility.*;

import sun.swing.SwingUtilities2;


/**
 * <code>JColorChooser</code> provides a pane of controls designed to allow
 * a user to manipulate and select a color.
 * For information about using color choosers, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/colorchooser.html">How to Use Color Choosers</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 *
 * This class provides three levels of API:
 * <ol>
 * <li>A static convenience method which shows a modal color-chooser
 * dialog and returns the color selected by the user.
 * <li>A static convenience method for creating a color-chooser dialog
 * where <code>ActionListeners</code> can be specified to be invoked when
 * the user presses one of the dialog buttons.
 * <li>The ability to create instances of <code>JColorChooser</code> panes
 * directly (within any container). <code>PropertyChange</code> listeners
 * can be added to detect when the current "color" property changes.
 * </ol>
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
 *
 * @beaninfo
 *      attribute: isContainer false
 *    description: A component that supports selecting a Color.
 *
 *
 * <p>
 *  <code> JColorChooser </code>提供了一系列控件,旨在允许用户操作和选择颜色。
 * 有关使用颜色选择器的信息,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/colorchooser.html">
 * 如何使用颜色选择器</a>, em> Java教程</em>。
 *  <code> JColorChooser </code>提供了一系列控件,旨在允许用户操作和选择颜色。
 * 
 * <p>
 * 
 *  这个类提供了三个级别的API：
 * <ol>
 *  <li>静态便利方法,显示模态颜色选择器对话框,并返回用户选择的颜色。
 *  <li>创建颜色选择器对话框的静态方便方法,其中<code> ActionListeners </code>可以指定为在用户按下其中一个对话框按钮时调用。
 *  <li>直接(在任何容器内)创建<code> JColorChooser </code>窗格实例的功能。
 * 可以添加<code> PropertyChange </code>侦听器来检测当前"颜色"属性何时更改。
 * </ol>
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：支持选择颜色的组件。
 * 
 * 
 * @author James Gosling
 * @author Amy Fowler
 * @author Steve Wilson
 */
public class JColorChooser extends JComponent implements Accessible {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ColorChooserUI";

    private ColorSelectionModel selectionModel;

    private JComponent previewPanel = ColorChooserComponentFactory.getPreviewPanel();

    private AbstractColorChooserPanel[] chooserPanels = new AbstractColorChooserPanel[0];

    private boolean dragEnabled;

    /**
     * The selection model property name.
     * <p>
     *  选择模型属性名称。
     * 
     */
    public static final String      SELECTION_MODEL_PROPERTY = "selectionModel";

    /**
     * The preview panel property name.
     * <p>
     *  预览面板属性名称。
     * 
     */
    public static final String      PREVIEW_PANEL_PROPERTY = "previewPanel";

    /**
     * The chooserPanel array property name.
     * <p>
     *  chooserPanel数组属性名称。
     * 
     */
    public static final String      CHOOSER_PANELS_PROPERTY = "chooserPanels";


    /**
     * Shows a modal color-chooser dialog and blocks until the
     * dialog is hidden.  If the user presses the "OK" button, then
     * this method hides/disposes the dialog and returns the selected color.
     * If the user presses the "Cancel" button or closes the dialog without
     * pressing "OK", then this method hides/disposes the dialog and returns
     * <code>null</code>.
     *
     * <p>
     *  显示模态颜色选择器对话框并阻止,直到对话框被隐藏。如果用户按下"确定"按钮,则该方法隐藏/布置对话框并返回所选择的颜色。
     * 如果用户按下"取消"按钮或关闭对话框而不按下"确定",则此方法隐藏/处置对话框并返回<code> null </code>。
     * 
     * 
     * @param component    the parent <code>Component</code> for the dialog
     * @param title        the String containing the dialog's title
     * @param initialColor the initial Color set when the color-chooser is shown
     * @return the selected color or <code>null</code> if the user opted out
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static Color showDialog(Component component,
        String title, Color initialColor) throws HeadlessException {

        final JColorChooser pane = new JColorChooser(initialColor != null?
                                               initialColor : Color.white);

        ColorTracker ok = new ColorTracker(pane);
        JDialog dialog = createDialog(component, title, true, pane, ok, null);

        dialog.addComponentListener(new ColorChooserDialog.DisposeOnClose());

        dialog.show(); // blocks until user brings dialog down...

        return ok.getColor();
    }


    /**
     * Creates and returns a new dialog containing the specified
     * <code>ColorChooser</code> pane along with "OK", "Cancel", and "Reset"
     * buttons. If the "OK" or "Cancel" buttons are pressed, the dialog is
     * automatically hidden (but not disposed).  If the "Reset"
     * button is pressed, the color-chooser's color will be reset to the
     * color which was set the last time <code>show</code> was invoked on the
     * dialog and the dialog will remain showing.
     *
     * <p>
     *  创建并返回包含指定的<code> ColorChooser </code>窗格的新对话框以及"确定","取消"和"重置"按钮。如果按下"确定"或"取消"按钮,对话框将自动隐藏(但不会丢弃)。
     * 如果按下"复位"按钮,颜色选择器的颜色将被重置为上次在对话框上调用<code>显示</code>时设置的颜色,对话框将保持显示。
     * 
     * 
     * @param c              the parent component for the dialog
     * @param title          the title for the dialog
     * @param modal          a boolean. When true, the remainder of the program
     *                       is inactive until the dialog is closed.
     * @param chooserPane    the color-chooser to be placed inside the dialog
     * @param okListener     the ActionListener invoked when "OK" is pressed
     * @param cancelListener the ActionListener invoked when "Cancel" is pressed
     * @return a new dialog containing the color-chooser pane
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static JDialog createDialog(Component c, String title, boolean modal,
        JColorChooser chooserPane, ActionListener okListener,
        ActionListener cancelListener) throws HeadlessException {

        Window window = JOptionPane.getWindowForComponent(c);
        ColorChooserDialog dialog;
        if (window instanceof Frame) {
            dialog = new ColorChooserDialog((Frame)window, title, modal, c, chooserPane,
                                            okListener, cancelListener);
        } else {
            dialog = new ColorChooserDialog((Dialog)window, title, modal, c, chooserPane,
                                            okListener, cancelListener);
        }
        dialog.getAccessibleContext().setAccessibleDescription(title);
        return dialog;
    }

    /**
     * Creates a color chooser pane with an initial color of white.
     * <p>
     *  创建一个初始颜色为白色的颜色选择器窗格。
     * 
     */
    public JColorChooser() {
        this(Color.white);
    }

    /**
     * Creates a color chooser pane with the specified initial color.
     *
     * <p>
     * 创建具有指定初始颜色的颜色选择器窗格。
     * 
     * 
     * @param initialColor the initial color set in the chooser
     */
    public JColorChooser(Color initialColor) {
        this( new DefaultColorSelectionModel(initialColor) );

    }

    /**
     * Creates a color chooser pane with the specified
     * <code>ColorSelectionModel</code>.
     *
     * <p>
     *  使用指定的<code> ColorSelectionModel </code>创建颜色选择器窗格。
     * 
     * 
     * @param model the <code>ColorSelectionModel</code> to be used
     */
    public JColorChooser(ColorSelectionModel model) {
        selectionModel = model;
        updateUI();
        dragEnabled = false;
    }

    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the <code>ColorChooserUI</code> object that renders
     *          this component
     */
    public ColorChooserUI getUI() {
        return (ColorChooserUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the <code>ColorChooserUI</code> L&amp;F object
     * @see UIDefaults#getUI
     *
     * @beaninfo
     *       bound: true
     *      hidden: true
     * description: The UI object that implements the color chooser's LookAndFeel.
     */
    public void setUI(ColorChooserUI ui) {
        super.setUI(ui);
    }

    /**
     * Notification from the <code>UIManager</code> that the L&amp;F has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * <p>
     *  来自<code> UIManager </code>的通知表示L&amp; F已更改。使用<code> UIManager </code>中的最新版本替换当前的UI对象。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((ColorChooserUI)UIManager.getUI(this));
    }

    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "ColorChooserUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Gets the current color value from the color chooser.
     * By default, this delegates to the model.
     *
     * <p>
     *  从颜色选择器获取当前颜色值。默认情况下,这将委派给模型。
     * 
     * 
     * @return the current color value of the color chooser
     */
    public Color getColor() {
        return selectionModel.getSelectedColor();
    }

    /**
     * Sets the current color of the color chooser to the specified color.
     * The <code>ColorSelectionModel</code> will fire a <code>ChangeEvent</code>
     * <p>
     *  将颜色选择器的当前颜色设置为指定的颜色。 <code> ColorSelectionModel </code>会触发一个<code> ChangeEvent </code>
     * 
     * 
     * @param color the color to be set in the color chooser
     * @see JComponent#addPropertyChangeListener
     *
     * @beaninfo
     *       bound: false
     *      hidden: false
     * description: The current color the chooser is to display.
     */
    public void setColor(Color color) {
        selectionModel.setSelectedColor(color);

    }

    /**
     * Sets the current color of the color chooser to the
     * specified RGB color.  Note that the values of red, green,
     * and blue should be between the numbers 0 and 255, inclusive.
     *
     * <p>
     *  将颜色选择器的当前颜色设置为指定的RGB颜色。请注意,红色,绿色和蓝色的值应在数字0和255之间(包括0和255)。
     * 
     * 
     * @param r   an int specifying the amount of Red
     * @param g   an int specifying the amount of Green
     * @param b   an int specifying the amount of Blue
     * @exception IllegalArgumentException if r,g,b values are out of range
     * @see java.awt.Color
     */
    public void setColor(int r, int g, int b) {
        setColor(new Color(r,g,b));
    }

    /**
     * Sets the current color of the color chooser to the
     * specified color.
     *
     * <p>
     *  将颜色选择器的当前颜色设置为指定的颜色。
     * 
     * 
     * @param c an integer value that sets the current color in the chooser
     *          where the low-order 8 bits specify the Blue value,
     *          the next 8 bits specify the Green value, and the 8 bits
     *          above that specify the Red value.
     */
    public void setColor(int c) {
        setColor((c >> 16) & 0xFF, (c >> 8) & 0xFF, c & 0xFF);
    }

    /**
     * Sets the <code>dragEnabled</code> property,
     * which must be <code>true</code> to enable
     * automatic drag handling (the first part of drag and drop)
     * on this component.
     * The <code>transferHandler</code> property needs to be set
     * to a non-<code>null</code> value for the drag to do
     * anything.  The default value of the <code>dragEnabled</code>
     * property
     * is <code>false</code>.
     *
     * <p>
     *
     * When automatic drag handling is enabled,
     * most look and feels begin a drag-and-drop operation
     * when the user presses the mouse button over the preview panel.
     * Some look and feels might not support automatic drag and drop;
     * they will ignore this property.  You can work around such
     * look and feels by modifying the component
     * to directly call the <code>exportAsDrag</code> method of a
     * <code>TransferHandler</code>.
     *
     * <p>
     *  设置<code> dragEnabled </code>属性,必须<code> true </code>才能在此组件上启用自动拖动处理(拖放的第一部分)。
     * 对于拖动执行任何操作,<code> transferHandler </code>属性需要设置为非<code> null </code>值。
     *  <code> dragEnabled </code>属性的默认值为<code> false </code>。
     * 
     * <p>
     * 
     * 当启用自动拖动处理时,当用户在预览面板上按下鼠标按钮时,大多数外观和感觉开始拖放操作。一些外观和感觉可能不支持自动拖放;他们将忽略此属性。
     * 你可以通过修改组件来直接调用<code> TransferHandler </code>的<code> exportAsDrag </code>方法来解决这种外观和感觉。
     * 
     * 
     * @param b the value to set the <code>dragEnabled</code> property to
     * @exception HeadlessException if
     *            <code>b</code> is <code>true</code> and
     *            <code>GraphicsEnvironment.isHeadless()</code>
     *            returns <code>true</code>
     *
     * @since 1.4
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     *
     * @beaninfo
     *  description: Determines whether automatic drag handling is enabled.
     *        bound: false
     */
    public void setDragEnabled(boolean b) {
        if (b && GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
        dragEnabled = b;
    }

    /**
     * Gets the value of the <code>dragEnabled</code> property.
     *
     * <p>
     *  获取<code> dragEnabled </code>属性的值。
     * 
     * 
     * @return  the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled() {
        return dragEnabled;
    }

    /**
     * Sets the current preview panel.
     * This will fire a <code>PropertyChangeEvent</code> for the property
     * named "previewPanel".
     *
     * <p>
     *  设置当前预览面板。这将为名为"previewPanel"的属性触发<code> PropertyChangeEvent </code>。
     * 
     * 
     * @param preview the <code>JComponent</code> which displays the current color
     * @see JComponent#addPropertyChangeListener
     *
     * @beaninfo
     *       bound: true
     *      hidden: true
     * description: The UI component which displays the current color.
     */
    public void setPreviewPanel(JComponent preview) {

        if (previewPanel != preview) {
            JComponent oldPreview = previewPanel;
            previewPanel = preview;
            firePropertyChange(JColorChooser.PREVIEW_PANEL_PROPERTY, oldPreview, preview);
        }
    }

    /**
     * Returns the preview panel that shows a chosen color.
     *
     * <p>
     *  返回显示所选颜色的预览面板。
     * 
     * 
     * @return a <code>JComponent</code> object -- the preview panel
     */
    public JComponent getPreviewPanel() {
        return previewPanel;
    }

    /**
     * Adds a color chooser panel to the color chooser.
     *
     * <p>
     *  向颜色选择器添加颜色选择器面板。
     * 
     * 
     * @param panel the <code>AbstractColorChooserPanel</code> to be added
     */
    public void addChooserPanel( AbstractColorChooserPanel panel ) {
        AbstractColorChooserPanel[] oldPanels = getChooserPanels();
        AbstractColorChooserPanel[] newPanels = new AbstractColorChooserPanel[oldPanels.length+1];
        System.arraycopy(oldPanels, 0, newPanels, 0, oldPanels.length);
        newPanels[newPanels.length-1] = panel;
        setChooserPanels(newPanels);
    }

    /**
     * Removes the Color Panel specified.
     *
     * <p>
     *  删除指定的颜色面板。
     * 
     * 
     * @param panel   a string that specifies the panel to be removed
     * @return the color panel
     * @exception IllegalArgumentException if panel is not in list of
     *                  known chooser panels
     */
    public AbstractColorChooserPanel removeChooserPanel( AbstractColorChooserPanel panel ) {


        int containedAt = -1;

        for (int i = 0; i < chooserPanels.length; i++) {
            if (chooserPanels[i] == panel) {
                containedAt = i;
                break;
            }
        }
        if (containedAt == -1) {
            throw new IllegalArgumentException("chooser panel not in this chooser");
        }

        AbstractColorChooserPanel[] newArray = new AbstractColorChooserPanel[chooserPanels.length-1];

        if (containedAt == chooserPanels.length-1) {  // at end
            System.arraycopy(chooserPanels, 0, newArray, 0, newArray.length);
        }
        else if (containedAt == 0) {  // at start
            System.arraycopy(chooserPanels, 1, newArray, 0, newArray.length);
        }
        else {  // in middle
            System.arraycopy(chooserPanels, 0, newArray, 0, containedAt);
            System.arraycopy(chooserPanels, containedAt+1,
                             newArray, containedAt, (chooserPanels.length - containedAt - 1));
        }

        setChooserPanels(newArray);

        return panel;
    }


    /**
     * Specifies the Color Panels used to choose a color value.
     *
     * <p>
     *  指定用于选择颜色值的颜色面板。
     * 
     * 
     * @param panels  an array of <code>AbstractColorChooserPanel</code>
     *          objects
     *
     * @beaninfo
     *       bound: true
     *      hidden: true
     * description: An array of different chooser types.
     */
    public void setChooserPanels( AbstractColorChooserPanel[] panels) {
        AbstractColorChooserPanel[] oldValue = chooserPanels;
        chooserPanels = panels;
        firePropertyChange(CHOOSER_PANELS_PROPERTY, oldValue, panels);
    }

    /**
     * Returns the specified color panels.
     *
     * <p>
     *  返回指定的颜色面板。
     * 
     * 
     * @return an array of <code>AbstractColorChooserPanel</code> objects
     */
    public AbstractColorChooserPanel[] getChooserPanels() {
        return chooserPanels;
    }

    /**
     * Returns the data model that handles color selections.
     *
     * <p>
     *  返回处理颜色选择的数据模型。
     * 
     * 
     * @return a <code>ColorSelectionModel</code> object
     */
    public ColorSelectionModel getSelectionModel() {
        return selectionModel;
    }


    /**
     * Sets the model containing the selected color.
     *
     * <p>
     *  设置包含所选颜色的模型。
     * 
     * 
     * @param newModel   the new <code>ColorSelectionModel</code> object
     *
     * @beaninfo
     *       bound: true
     *      hidden: true
     * description: The model which contains the currently selected color.
     */
    public void setSelectionModel(ColorSelectionModel newModel ) {
        ColorSelectionModel oldModel = selectionModel;
        selectionModel = newModel;
        firePropertyChange(JColorChooser.SELECTION_MODEL_PROPERTY, oldModel, newModel);
    }


    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
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
     * Returns a string representation of this <code>JColorChooser</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JColorChooser </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JColorChooser</code>
     */
    protected String paramString() {
        StringBuffer chooserPanelsString = new StringBuffer("");
        for (int i=0; i<chooserPanels.length; i++) {
            chooserPanelsString.append("[" + chooserPanels[i].toString()
                                       + "]");
        }
        String previewPanelString = (previewPanel != null ?
                                     previewPanel.toString() : "");

        return super.paramString() +
        ",chooserPanels=" + chooserPanelsString.toString() +
        ",previewPanel=" + previewPanelString;
    }

/////////////////
// Accessibility support
////////////////

    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JColorChooser.
     * For color choosers, the AccessibleContext takes the form of an
     * AccessibleJColorChooser.
     * A new AccessibleJColorChooser instance is created if necessary.
     *
     * <p>
     * 获取与此JColorChooser相关联的AccessibleContext。对于颜色选择器,AccessibleContext采用AccessibleJColorChooser的形式。
     * 如果需要,将创建一个新的AccessibleJColorChooser实例。
     * 
     * 
     * @return an AccessibleJColorChooser that serves as the
     *         AccessibleContext of this JColorChooser
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJColorChooser();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JColorChooser</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to color chooser user-interface
     * elements.
     * <p>
     *  这个类实现了对<code> JColorChooser </code>类的辅助功能支持。它提供了适用于颜色选择器用户界面元素的Java可访问性API的实现。
     * 
     */
    protected class AccessibleJColorChooser extends AccessibleJComponent {

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
            return AccessibleRole.COLOR_CHOOSER;
        }

    } // inner class AccessibleJColorChooser
}


/*
 * Class which builds a color chooser dialog consisting of
 * a JColorChooser with "Ok", "Cancel", and "Reset" buttons.
 *
 * Note: This needs to be fixed to deal with localization!
 * <p>
 *  类,它构建一个由带有"Ok","Cancel"和"Reset"按钮的JColorChooser组成的颜色选择器对话框。
 * 
 *  注意：这需要固定处理本地化！
 * 
 */
class ColorChooserDialog extends JDialog {
    private Color initialColor;
    private JColorChooser chooserPane;
    private JButton cancelButton;

    public ColorChooserDialog(Dialog owner, String title, boolean modal,
        Component c, JColorChooser chooserPane,
        ActionListener okListener, ActionListener cancelListener)
        throws HeadlessException {
        super(owner, title, modal);
        initColorChooserDialog(c, chooserPane, okListener, cancelListener);
    }

    public ColorChooserDialog(Frame owner, String title, boolean modal,
        Component c, JColorChooser chooserPane,
        ActionListener okListener, ActionListener cancelListener)
        throws HeadlessException {
        super(owner, title, modal);
        initColorChooserDialog(c, chooserPane, okListener, cancelListener);
    }

    protected void initColorChooserDialog(Component c, JColorChooser chooserPane,
        ActionListener okListener, ActionListener cancelListener) {
        //setResizable(false);

        this.chooserPane = chooserPane;

        Locale locale = getLocale();
        String okString = UIManager.getString("ColorChooser.okText", locale);
        String cancelString = UIManager.getString("ColorChooser.cancelText", locale);
        String resetString = UIManager.getString("ColorChooser.resetText", locale);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(chooserPane, BorderLayout.CENTER);

        /*
         * Create Lower button panel
         * <p>
         */
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton(okString);
        getRootPane().setDefaultButton(okButton);
        okButton.getAccessibleContext().setAccessibleDescription(okString);
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        if (okListener != null) {
            okButton.addActionListener(okListener);
        }
        buttonPane.add(okButton);

        cancelButton = new JButton(cancelString);
        cancelButton.getAccessibleContext().setAccessibleDescription(cancelString);

        // The following few lines are used to register esc to close the dialog
        Action cancelKeyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ((AbstractButton)e.getSource()).fireActionPerformed(e);
            }
        };
        KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        InputMap inputMap = cancelButton.getInputMap(JComponent.
                                                     WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = cancelButton.getActionMap();
        if (inputMap != null && actionMap != null) {
            inputMap.put(cancelKeyStroke, "cancel");
            actionMap.put("cancel", cancelKeyAction);
        }
        // end esc handling

        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        if (cancelListener != null) {
            cancelButton.addActionListener(cancelListener);
        }
        buttonPane.add(cancelButton);

        JButton resetButton = new JButton(resetString);
        resetButton.getAccessibleContext().setAccessibleDescription(resetString);
        resetButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               reset();
           }
        });
        int mnemonic = SwingUtilities2.getUIDefaultsInt("ColorChooser.resetMnemonic", locale, -1);
        if (mnemonic != -1) {
            resetButton.setMnemonic(mnemonic);
        }
        buttonPane.add(resetButton);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
            UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
            }
        }
        applyComponentOrientation(((c == null) ? getRootPane() : c).getComponentOrientation());

        pack();
        setLocationRelativeTo(c);

        this.addWindowListener(new Closer());
    }

    public void show() {
        initialColor = chooserPane.getColor();
        super.show();
    }

    public void reset() {
        chooserPane.setColor(initialColor);
    }

    class Closer extends WindowAdapter implements Serializable{
        public void windowClosing(WindowEvent e) {
            cancelButton.doClick(0);
            Window w = e.getWindow();
            w.hide();
        }
    }

    static class DisposeOnClose extends ComponentAdapter implements Serializable{
        public void componentHidden(ComponentEvent e) {
            Window w = (Window)e.getComponent();
            w.dispose();
        }
    }

}

class ColorTracker implements ActionListener, Serializable {
    JColorChooser chooser;
    Color color;

    public ColorTracker(JColorChooser c) {
        chooser = c;
    }

    public void actionPerformed(ActionEvent e) {
        color = chooser.getColor();
    }

    public Color getColor() {
        return color;
    }
}
