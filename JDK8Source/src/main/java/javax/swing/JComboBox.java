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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;

import javax.swing.event.*;
import javax.swing.plaf.*;

import javax.accessibility.*;

/**
 * A component that combines a button or editable field and a drop-down list.
 * The user can select a value from the drop-down list, which appears at the
 * user's request. If you make the combo box editable, then the combo box
 * includes an editable field into which the user can type a value.
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
 * <p>
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html">How to Use Combo Boxes</a>
 * in <a href="https://docs.oracle.com/javase/tutorial/"><em>The Java Tutorial</em></a>
 * for further information.
 * <p>
 * <p>
 *  组合按钮或可编辑字段和下拉列表的组件。用户可以从根据用户请求显示的下拉列表中选择一个值。如果使组合框可编辑,则组合框包括可编辑字段,用户可以在其中键入值。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * <p>
 *  请参阅<a href ="https：// docs中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html">
 * 如何使用组合框</a>。
 *  oracle.com/javase/tutorial/"><em> Java教程</em> </a>了解更多信息。
 * <p>
 * 
 * @see ComboBoxModel
 * @see DefaultComboBoxModel
 *
 * @param <E> the type of the elements of this combo box
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: A combination of a text field and a drop-down list.
 *
 * @author Arnaud Weber
 * @author Mark Davidson
 */
public class JComboBox<E> extends JComponent
implements ItemSelectable,ListDataListener,ActionListener, Accessible {
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ComboBoxUI";

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #getModel
     * @see #setModel
     */
    protected ComboBoxModel<E>    dataModel;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #getRenderer
     * @see #setRenderer
     */
    protected ListCellRenderer<? super E> renderer;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     * 此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #getEditor
     * @see #setEditor
     */
    protected ComboBoxEditor       editor;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #getMaximumRowCount
     * @see #setMaximumRowCount
     */
    protected int maximumRowCount = 8;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #isEditable
     * @see #setEditable
     */
    protected boolean isEditable  = false;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #setKeySelectionManager
     * @see #getKeySelectionManager
     */
    protected KeySelectionManager keySelectionManager = null;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #setActionCommand
     * @see #getActionCommand
     */
    protected String actionCommand = "comboBoxChanged";
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #setLightWeightPopupEnabled
     * @see #isLightWeightPopupEnabled
     */
    protected boolean lightWeightPopupEnabled = JPopupMenu.getDefaultLightWeightPopupEnabled();

    /**
     * This protected field is implementation specific. Do not access directly
     * or override.
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。
     * 
     */
    protected Object selectedItemReminder = null;

    private E prototypeDisplayValue;

    // Flag to ensure that infinite loops do not occur with ActionEvents.
    private boolean firingActionEvent = false;

    // Flag to ensure the we don't get multiple ActionEvents on item selection.
    private boolean selectingItem = false;

    /**
     * Creates a <code>JComboBox</code> that takes its items from an
     * existing <code>ComboBoxModel</code>.  Since the
     * <code>ComboBoxModel</code> is provided, a combo box created using
     * this constructor does not create a default combo box model and
     * may impact how the insert, remove and add methods behave.
     *
     * <p>
     *  创建从现有<code> ComboBoxModel </code>获取其项目的<code> JComboBox </code>。
     * 由于提供了<code> ComboBoxModel </code>,使用此构造函数创建的组合框不会创建默认组合框模型,并且可能会影响插入,删除和添加方法的行为。
     * 
     * 
     * @param aModel the <code>ComboBoxModel</code> that provides the
     *          displayed list of items
     * @see DefaultComboBoxModel
     */
    public JComboBox(ComboBoxModel<E> aModel) {
        super();
        setModel(aModel);
        init();
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements
     * in the specified array.  By default the first item in the array
     * (and therefore the data model) becomes selected.
     *
     * <p>
     *  创建一个包含指定数组中的元素的<code> JComboBox </code>。默认情况下,数组中的第一个项目(因此也就是数据模型)被选中。
     * 
     * 
     * @param items  an array of objects to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public JComboBox(E[] items) {
        super();
        setModel(new DefaultComboBoxModel<E>(items));
        init();
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements
     * in the specified Vector.  By default the first item in the vector
     * (and therefore the data model) becomes selected.
     *
     * <p>
     *  创建一个包含指定向量中的元素的<code> JComboBox </code>。默认情况下,向量中的第一个项目(以及数据模型)将被选中。
     * 
     * 
     * @param items  an array of vectors to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public JComboBox(Vector<E> items) {
        super();
        setModel(new DefaultComboBoxModel<E>(items));
        init();
    }

    /**
     * Creates a <code>JComboBox</code> with a default data model.
     * The default data model is an empty list of objects.
     * Use <code>addItem</code> to add items.  By default the first item
     * in the data model becomes selected.
     *
     * <p>
     * 使用默认数据模型创建<code> JComboBox </code>。默认数据模型是一个空的对象列表。使用<code> addItem </code>添加项目。
     * 默认情况下,数据模型中的第一个项目被选中。
     * 
     * 
     * @see DefaultComboBoxModel
     */
    public JComboBox() {
        super();
        setModel(new DefaultComboBoxModel<E>());
        init();
    }

    private void init() {
        installAncestorListener();
        setUIProperty("opaque",true);
        updateUI();
    }

    protected void installAncestorListener() {
        addAncestorListener(new AncestorListener(){
                                public void ancestorAdded(AncestorEvent event){ hidePopup();}
                                public void ancestorRemoved(AncestorEvent event){ hidePopup();}
                                public void ancestorMoved(AncestorEvent event){
                                    if (event.getSource() != JComboBox.this)
                                        hidePopup();
                                }});
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the <code>ComboBoxUI</code> L&amp;F object
     * @see UIDefaults#getUI
     *
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(ComboBoxUI ui) {
        super.setUI(ui);
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
        setUI((ComboBoxUI)UIManager.getUI(this));

        ListCellRenderer<? super E> renderer = getRenderer();
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component)renderer);
        }
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "ComboBoxUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the ComboBoxUI object that renders this component
     */
    public ComboBoxUI getUI() {
        return(ComboBoxUI)ui;
    }

    /**
     * Sets the data model that the <code>JComboBox</code> uses to obtain
     * the list of items.
     *
     * <p>
     *  设置<code> JComboBox </code>用于获取项目列表的数据模型。
     * 
     * 
     * @param aModel the <code>ComboBoxModel</code> that provides the
     *  displayed list of items
     *
     * @beaninfo
     *        bound: true
     *  description: Model that the combo box uses to get data to display.
     */
    public void setModel(ComboBoxModel<E> aModel) {
        ComboBoxModel<E> oldModel = dataModel;
        if (oldModel != null) {
            oldModel.removeListDataListener(this);
        }
        dataModel = aModel;
        dataModel.addListDataListener(this);

        // set the current selected item.
        selectedItemReminder = dataModel.getSelectedItem();

        firePropertyChange( "model", oldModel, dataModel);
    }

    /**
     * Returns the data model currently used by the <code>JComboBox</code>.
     *
     * <p>
     *  返回<code> JComboBox </code>当前使用的数据模型。
     * 
     * 
     * @return the <code>ComboBoxModel</code> that provides the displayed
     *                  list of items
     */
    public ComboBoxModel<E> getModel() {
        return dataModel;
    }

    /*
     * Properties
     * <p>
     *  属性
     * 
     */

    /**
     * Sets the <code>lightWeightPopupEnabled</code> property, which
     * provides a hint as to whether or not a lightweight
     * <code>Component</code> should be used to contain the
     * <code>JComboBox</code>, versus a heavyweight
     * <code>Component</code> such as a <code>Panel</code>
     * or a <code>Window</code>.  The decision of lightweight
     * versus heavyweight is ultimately up to the
     * <code>JComboBox</code>.  Lightweight windows are more
     * efficient than heavyweight windows, but lightweight
     * and heavyweight components do not mix well in a GUI.
     * If your application mixes lightweight and heavyweight
     * components, you should disable lightweight popups.
     * The default value for the <code>lightWeightPopupEnabled</code>
     * property is <code>true</code>, unless otherwise specified
     * by the look and feel.  Some look and feels always use
     * heavyweight popups, no matter what the value of this property.
     * <p>
     * See the article <a href="http://www.oracle.com/technetwork/articles/java/mixing-components-433992.html">Mixing Heavy and Light Components</a>
     * This method fires a property changed event.
     *
     * <p>
     *  设置<code> lightWeightPopupEnabled </code>属性,它提供了一个提示,说明是否应该使用一个轻量级的<code> Component </code>来包含<code> 
     * JComboBox </code>代码>组件</code>,例如<code> Panel </code>或<code> Window </code>。
     * 轻量级和重量级的决定最终取决于<code> JComboBox </code>。轻量级窗口比重量级窗口更高效,但轻量级和重量级组件在GUI中不能很好地混合。
     * 如果您的应用程序混合轻量级和重量级组件,则应禁用轻量级弹出窗口。
     * 除非另有指定,否则<code> lightWeightPopupEnabled </code>属性的默认值为<code> true </code>。
     * 有些外观和感觉总是使用重量级的弹出窗口,无论这个属性的值。
     * <p>
     * 请参阅文章<a href="http://www.oracle.com/technetwork/articles/java/mixing-components-433992.html">混合重型和轻型组
     * 件</a>此方法触发属性已更改事件。
     * 
     * 
     * @param aFlag if <code>true</code>, lightweight popups are desired
     *
     * @beaninfo
     *        bound: true
     *       expert: true
     *  description: Set to <code>false</code> to require heavyweight popups.
     */
    public void setLightWeightPopupEnabled(boolean aFlag) {
        boolean oldFlag = lightWeightPopupEnabled;
        lightWeightPopupEnabled = aFlag;
        firePropertyChange("lightWeightPopupEnabled", oldFlag, lightWeightPopupEnabled);
    }

    /**
     * Gets the value of the <code>lightWeightPopupEnabled</code>
     * property.
     *
     * <p>
     *  获取<code> lightWeightPopupEnabled </code>属性的值。
     * 
     * 
     * @return the value of the <code>lightWeightPopupEnabled</code>
     *    property
     * @see #setLightWeightPopupEnabled
     */
    public boolean isLightWeightPopupEnabled() {
        return lightWeightPopupEnabled;
    }

    /**
     * Determines whether the <code>JComboBox</code> field is editable.
     * An editable <code>JComboBox</code> allows the user to type into the
     * field or selected an item from the list to initialize the field,
     * after which it can be edited. (The editing affects only the field,
     * the list item remains intact.) A non editable <code>JComboBox</code>
     * displays the selected item in the field,
     * but the selection cannot be modified.
     *
     * <p>
     *  确定<code> JComboBox </code>字段是否可编辑。可编辑的<code> JComboBox </code>允许用户键入字段或从列表中选择一个项目来初始化字段,之后可以编辑它。
     *  (编辑仅影响字段,列表项保持不变。)不可编辑的<code> JComboBox </code>在字段中显示所选项,但不能修改选择。
     * 
     * 
     * @param aFlag a boolean value, where true indicates that the
     *                  field is editable
     *
     * @beaninfo
     *        bound: true
     *    preferred: true
     *  description: If true, the user can type a new value in the combo box.
     */
    public void setEditable(boolean aFlag) {
        boolean oldFlag = isEditable;
        isEditable = aFlag;
        firePropertyChange( "editable", oldFlag, isEditable );
    }

    /**
     * Returns true if the <code>JComboBox</code> is editable.
     * By default, a combo box is not editable.
     *
     * <p>
     *  如果<code> JComboBox </code>可编辑,则返回true。默认情况下,组合框不可编辑。
     * 
     * 
     * @return true if the <code>JComboBox</code> is editable, else false
     */
    public boolean isEditable() {
        return isEditable;
    }

    /**
     * Sets the maximum number of rows the <code>JComboBox</code> displays.
     * If the number of objects in the model is greater than count,
     * the combo box uses a scrollbar.
     *
     * <p>
     *  设置<code> JComboBox </code>显示的最大行数。如果模型中的对象数量大于count,组合框使用滚动条。
     * 
     * 
     * @param count an integer specifying the maximum number of items to
     *              display in the list before using a scrollbar
     * @beaninfo
     *        bound: true
     *    preferred: true
     *  description: The maximum number of rows the popup should have
     */
    public void setMaximumRowCount(int count) {
        int oldCount = maximumRowCount;
        maximumRowCount = count;
        firePropertyChange( "maximumRowCount", oldCount, maximumRowCount );
    }

    /**
     * Returns the maximum number of items the combo box can display
     * without a scrollbar
     *
     * <p>
     *  返回组合框在没有滚动条的情况下可显示的最大项数
     * 
     * 
     * @return an integer specifying the maximum number of items that are
     *         displayed in the list before using a scrollbar
     */
    public int getMaximumRowCount() {
        return maximumRowCount;
    }

    /**
     * Sets the renderer that paints the list items and the item selected from the list in
     * the JComboBox field. The renderer is used if the JComboBox is not
     * editable. If it is editable, the editor is used to render and edit
     * the selected item.
     * <p>
     * The default renderer displays a string or an icon.
     * Other renderers can handle graphic images and composite items.
     * <p>
     * To display the selected item,
     * <code>aRenderer.getListCellRendererComponent</code>
     * is called, passing the list object and an index of -1.
     *
     * <p>
     *  设置描绘JComboBox字段中列表项和从列表中选择的项的渲染器。如果JComboBox不可编辑,则使用渲染器。如果它是可编辑的,则编辑器用于呈现和编辑所选项目。
     * <p>
     *  默认渲染器显示字符串或图标。其他渲染器可以处理图形图像和复合项目。
     * <p>
     * 为了显示所选项目,调用<code> aRenderer.getListCellRendererComponent </code>,传递列表对象和索引-1。
     * 
     * 
     * @param aRenderer  the <code>ListCellRenderer</code> that
     *                  displays the selected item
     * @see #setEditor
     * @beaninfo
     *      bound: true
     *     expert: true
     *  description: The renderer that paints the item selected in the list.
     */
    public void setRenderer(ListCellRenderer<? super E> aRenderer) {
        ListCellRenderer<? super E> oldRenderer = renderer;
        renderer = aRenderer;
        firePropertyChange( "renderer", oldRenderer, renderer );
        invalidate();
    }

    /**
     * Returns the renderer used to display the selected item in the
     * <code>JComboBox</code> field.
     *
     * <p>
     *  返回用于在<code> JComboBox </code>字段中显示所选项目的渲染器。
     * 
     * 
     * @return  the <code>ListCellRenderer</code> that displays
     *                  the selected item.
     */
    public ListCellRenderer<? super E> getRenderer() {
        return renderer;
    }

    /**
     * Sets the editor used to paint and edit the selected item in the
     * <code>JComboBox</code> field.  The editor is used only if the
     * receiving <code>JComboBox</code> is editable. If not editable,
     * the combo box uses the renderer to paint the selected item.
     *
     * <p>
     *  设置用于在<code> JComboBox </code>字段中绘制和编辑所选项目的编辑器。仅当接收<code> JComboBox </code>可编辑时,才使用编辑器。
     * 如果不可编辑,组合框使用渲染器绘制所选项目。
     * 
     * 
     * @param anEditor  the <code>ComboBoxEditor</code> that
     *                  displays the selected item
     * @see #setRenderer
     * @beaninfo
     *     bound: true
     *    expert: true
     *  description: The editor that combo box uses to edit the current value
     */
    public void setEditor(ComboBoxEditor anEditor) {
        ComboBoxEditor oldEditor = editor;

        if ( editor != null ) {
            editor.removeActionListener(this);
        }
        editor = anEditor;
        if ( editor != null ) {
            editor.addActionListener(this);
        }
        firePropertyChange( "editor", oldEditor, editor );
    }

    /**
     * Returns the editor used to paint and edit the selected item in the
     * <code>JComboBox</code> field.
     *
     * <p>
     *  返回用于绘制和编辑<code> JComboBox </code>字段中所选项目的编辑器。
     * 
     * 
     * @return the <code>ComboBoxEditor</code> that displays the selected item
     */
    public ComboBoxEditor getEditor() {
        return editor;
    }

    //
    // Selection
    //

    /**
     * Sets the selected item in the combo box display area to the object in
     * the argument.
     * If <code>anObject</code> is in the list, the display area shows
     * <code>anObject</code> selected.
     * <p>
     * If <code>anObject</code> is <i>not</i> in the list and the combo box is
     * uneditable, it will not change the current selection. For editable
     * combo boxes, the selection will change to <code>anObject</code>.
     * <p>
     * If this constitutes a change in the selected item,
     * <code>ItemListener</code>s added to the combo box will be notified with
     * one or two <code>ItemEvent</code>s.
     * If there is a current selected item, an <code>ItemEvent</code> will be
     * fired and the state change will be <code>ItemEvent.DESELECTED</code>.
     * If <code>anObject</code> is in the list and is not currently selected
     * then an <code>ItemEvent</code> will be fired and the state change will
     * be <code>ItemEvent.SELECTED</code>.
     * <p>
     * <code>ActionListener</code>s added to the combo box will be notified
     * with an <code>ActionEvent</code> when this method is called.
     *
     * <p>
     *  将组合框显示区域中的选定项目设置为参数中的对象。如果<code> anObject </code>在列表中,则显示区域显示<code> anObject </code>被选中。
     * <p>
     *  如果列表中的<code> anObject </code> <i>不是</i>,并且组合框是不可编辑的,则不会更改当前选择。
     * 对于可编辑组合框,选择将更改为<code> anObject </code>。
     * <p>
     *  如果这构成所选项目的改变,则将向一个或两个<code> ItemEvent </code>通知添加到组合框中的<code> ItemListener </code>。
     * 如果存在当前选择的项目,则将触发<code> ItemEvent </code>,并且状态改变将是<code> ItemEvent.DESELECTED </code>。
     * 如果<code> anObject </code>在列表中,并且当前未被选择,则会触发<code> ItemEvent </code>,状态更改将为<code> ItemEvent.SELECTED </code>
     * 。
     * 如果存在当前选择的项目,则将触发<code> ItemEvent </code>,并且状态改变将是<code> ItemEvent.DESELECTED </code>。
     * <p>
     * <code>当调用此方法时,添加到组合框中的ActionListener </code>将通过<code> ActionEvent </code>通知。
     * 
     * 
     * @param anObject  the list object to select; use <code>null</code> to
                        clear the selection
     * @beaninfo
     *    preferred:   true
     *    description: Sets the selected item in the JComboBox.
     */
    public void setSelectedItem(Object anObject) {
        Object oldSelection = selectedItemReminder;
        Object objectToSelect = anObject;
        if (oldSelection == null || !oldSelection.equals(anObject)) {

            if (anObject != null && !isEditable()) {
                // For non editable combo boxes, an invalid selection
                // will be rejected.
                boolean found = false;
                for (int i = 0; i < dataModel.getSize(); i++) {
                    E element = dataModel.getElementAt(i);
                    if (anObject.equals(element)) {
                        found = true;
                        objectToSelect = element;
                        break;
                    }
                }
                if (!found) {
                    return;
                }
            }

            // Must toggle the state of this flag since this method
            // call may result in ListDataEvents being fired.
            selectingItem = true;
            dataModel.setSelectedItem(objectToSelect);
            selectingItem = false;

            if (selectedItemReminder != dataModel.getSelectedItem()) {
                // in case a users implementation of ComboBoxModel
                // doesn't fire a ListDataEvent when the selection
                // changes.
                selectedItemChanged();
            }
        }
        fireActionEvent();
    }

    /**
     * Returns the current selected item.
     * <p>
     * If the combo box is editable, then this value may not have been added
     * to the combo box with <code>addItem</code>, <code>insertItemAt</code>
     * or the data constructors.
     *
     * <p>
     *  返回当前选定的项目。
     * <p>
     *  如果组合框是可编辑的,则此值可能未添加到包含<code> addItem </code>,<code> insertItemAt </code>或数据构造函数的组合框中。
     * 
     * 
     * @return the current selected Object
     * @see #setSelectedItem
     */
    public Object getSelectedItem() {
        return dataModel.getSelectedItem();
    }

    /**
     * Selects the item at index <code>anIndex</code>.
     *
     * <p>
     *  选择索引<code> anIndex </code>下的项目。
     * 
     * 
     * @param anIndex an integer specifying the list item to select,
     *                  where 0 specifies the first item in the list and -1 indicates no selection
     * @exception IllegalArgumentException if <code>anIndex</code> &lt; -1 or
     *                  <code>anIndex</code> is greater than or equal to size
     * @beaninfo
     *   preferred: true
     *  description: The item at index is selected.
     */
    public void setSelectedIndex(int anIndex) {
        int size = dataModel.getSize();

        if ( anIndex == -1 ) {
            setSelectedItem( null );
        } else if ( anIndex < -1 || anIndex >= size ) {
            throw new IllegalArgumentException("setSelectedIndex: " + anIndex + " out of bounds");
        } else {
            setSelectedItem(dataModel.getElementAt(anIndex));
        }
    }

    /**
     * Returns the first item in the list that matches the given item.
     * The result is not always defined if the <code>JComboBox</code>
     * allows selected items that are not in the list.
     * Returns -1 if there is no selected item or if the user specified
     * an item which is not in the list.

     * <p>
     *  返回列表中与给定项匹配的第一个项。如果<code> JComboBox </code>允许不在列表中的选定项目,则结果并不总是定义的。如果没有选定的项目或用户指定的项目不在列表中,则返回-1。
     * 
     * 
     * @return an integer specifying the currently selected list item,
     *                  where 0 specifies
     *                  the first item in the list;
     *                  or -1 if no item is selected or if
     *                  the currently selected item is not in the list
     */
    @Transient
    public int getSelectedIndex() {
        Object sObject = dataModel.getSelectedItem();
        int i,c;
        E obj;

        for ( i=0,c=dataModel.getSize();i<c;i++ ) {
            obj = dataModel.getElementAt(i);
            if ( obj != null && obj.equals(sObject) )
                return i;
        }
        return -1;
    }

    /**
     * Returns the "prototypical display" value - an Object used
     * for the calculation of the display height and width.
     *
     * <p>
     *  返回"原型显示"值 - 用于计算显示高度和宽度的对象。
     * 
     * 
     * @return the value of the <code>prototypeDisplayValue</code> property
     * @see #setPrototypeDisplayValue
     * @since 1.4
     */
    public E getPrototypeDisplayValue() {
        return prototypeDisplayValue;
    }

    /**
     * Sets the prototype display value used to calculate the size of the display
     * for the UI portion.
     * <p>
     * If a prototype display value is specified, the preferred size of
     * the combo box is calculated by configuring the renderer with the
     * prototype display value and obtaining its preferred size. Specifying
     * the preferred display value is often useful when the combo box will be
     * displaying large amounts of data. If no prototype display value has
     * been specified, the renderer must be configured for each value from
     * the model and its preferred size obtained, which can be
     * relatively expensive.
     *
     * <p>
     *  设置用于计算UI部分的显示大小的原型显示值。
     * <p>
     *  如果指定了原型显示值,则通过使用原型显示值配置渲染器并获得其优选大小来计算组合框的优选大小。当组合框将显示大量数据时,指定首选显示值通常很有用。
     * 如果未指定原型显示值,则必须为模型中的每个值及其获得的首选大小配置渲染器,这可能相对昂贵。
     * 
     * 
     * @param prototypeDisplayValue
     * @see #getPrototypeDisplayValue
     * @since 1.4
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The display prototype value, used to compute display width and height.
     */
    public void setPrototypeDisplayValue(E prototypeDisplayValue) {
        Object oldValue = this.prototypeDisplayValue;
        this.prototypeDisplayValue = prototypeDisplayValue;
        firePropertyChange("prototypeDisplayValue", oldValue, prototypeDisplayValue);
    }

    /**
     * Adds an item to the item list.
     * This method works only if the <code>JComboBox</code> uses a
     * mutable data model.
     * <p>
     * <strong>Warning:</strong>
     * Focus and keyboard navigation problems may arise if you add duplicate
     * String objects. A workaround is to add new objects instead of String
     * objects and make sure that the toString() method is defined.
     * For example:
     * <pre>
     *   comboBox.addItem(makeObj("Item 1"));
     *   comboBox.addItem(makeObj("Item 1"));
     *   ...
     *   private Object makeObj(final String item)  {
     *     return new Object() { public String toString() { return item; } };
     *   }
     * </pre>
     *
     * <p>
     *  将项目添加到项目列表。仅当<code> JComboBox </code>使用可变数据模型时,此方法才有效。
     * <p>
     * <strong>警告：</strong>如果您添加重复的String对象,则可能会出现焦点和键盘导航问题。解决方法是添加新对象而不是String对象,并确保定义了toString()方法。例如：
     * <pre>
     *  comboBox.addItem(makeObj("Item 1")); comboBox.addItem(makeObj("Item 1")); ... private Object makeObj
     * (final String item){return new Object(){public String toString(){return item; }}; }}。
     * </pre>
     * 
     * 
     * @param item the item to add to the list
     * @see MutableComboBoxModel
     */
    public void addItem(E item) {
        checkMutableComboBoxModel();
        ((MutableComboBoxModel<E>)dataModel).addElement(item);
    }

    /**
     * Inserts an item into the item list at a given index.
     * This method works only if the <code>JComboBox</code> uses a
     * mutable data model.
     *
     * <p>
     *  在给定索引的项目列表中插入项目。仅当<code> JComboBox </code>使用可变数据模型时,此方法才有效。
     * 
     * 
     * @param item the item to add to the list
     * @param index    an integer specifying the position at which
     *                  to add the item
     * @see MutableComboBoxModel
     */
    public void insertItemAt(E item, int index) {
        checkMutableComboBoxModel();
        ((MutableComboBoxModel<E>)dataModel).insertElementAt(item,index);
    }

    /**
     * Removes an item from the item list.
     * This method works only if the <code>JComboBox</code> uses a
     * mutable data model.
     *
     * <p>
     *  从项目列表中删除项目。仅当<code> JComboBox </code>使用可变数据模型时,此方法才有效。
     * 
     * 
     * @param anObject  the object to remove from the item list
     * @see MutableComboBoxModel
     */
    public void removeItem(Object anObject) {
        checkMutableComboBoxModel();
        ((MutableComboBoxModel)dataModel).removeElement(anObject);
    }

    /**
     * Removes the item at <code>anIndex</code>
     * This method works only if the <code>JComboBox</code> uses a
     * mutable data model.
     *
     * <p>
     *  删除<code> anIndex </code>上的项目此方法仅在<code> JComboBox </code>使用可变数据模型时才有效。
     * 
     * 
     * @param anIndex  an int specifying the index of the item to remove,
     *                  where 0
     *                  indicates the first item in the list
     * @see MutableComboBoxModel
     */
    public void removeItemAt(int anIndex) {
        checkMutableComboBoxModel();
        ((MutableComboBoxModel<E>)dataModel).removeElementAt( anIndex );
    }

    /**
     * Removes all items from the item list.
     * <p>
     *  从项目列表中删除所有项目。
     * 
     */
    public void removeAllItems() {
        checkMutableComboBoxModel();
        MutableComboBoxModel<E> model = (MutableComboBoxModel<E>)dataModel;
        int size = model.getSize();

        if ( model instanceof DefaultComboBoxModel ) {
            ((DefaultComboBoxModel)model).removeAllElements();
        }
        else {
            for ( int i = 0; i < size; ++i ) {
                E element = model.getElementAt( 0 );
                model.removeElement( element );
            }
        }
        selectedItemReminder = null;
        if (isEditable()) {
            editor.setItem(null);
        }
    }

    /**
     * Checks that the <code>dataModel</code> is an instance of
     * <code>MutableComboBoxModel</code>.  If not, it throws an exception.
     * <p>
     *  检查<code> dataModel </code>是<code> MutableComboBoxModel </code>的实例。如果不是,它抛出异常。
     * 
     * 
     * @exception RuntimeException if <code>dataModel</code> is not an
     *          instance of <code>MutableComboBoxModel</code>.
     */
    void checkMutableComboBoxModel() {
        if ( !(dataModel instanceof MutableComboBoxModel) )
            throw new RuntimeException("Cannot use this method with a non-Mutable data model.");
    }

    /**
     * Causes the combo box to display its popup window.
     * <p>
     *  使组合框显示其弹出窗口。
     * 
     * 
     * @see #setPopupVisible
     */
    public void showPopup() {
        setPopupVisible(true);
    }

    /**
     * Causes the combo box to close its popup window.
     * <p>
     *  使组合框关闭其弹出窗口。
     * 
     * 
     * @see #setPopupVisible
     */
    public void hidePopup() {
        setPopupVisible(false);
    }

    /**
     * Sets the visibility of the popup.
     * <p>
     *  设置弹出窗口的可见性。
     * 
     */
    public void setPopupVisible(boolean v) {
        getUI().setPopupVisible(this, v);
    }

    /**
     * Determines the visibility of the popup.
     *
     * <p>
     *  确定弹出窗口的可见性。
     * 
     * 
     * @return true if the popup is visible, otherwise returns false
     */
    public boolean isPopupVisible() {
        return getUI().isPopupVisible(this);
    }

    /** Selection **/

    /**
     * Adds an <code>ItemListener</code>.
     * <p>
     * <code>aListener</code> will receive one or two <code>ItemEvent</code>s when
     * the selected item changes.
     *
     * <p>
     *  添加<code> ItemListener </code>。
     * <p>
     *  当所选项目改变时,<code> aListener </code>会收到一个或两个<code> ItemEvent </code>。
     * 
     * 
     * @param aListener the <code>ItemListener</code> that is to be notified
     * @see #setSelectedItem
     */
    public void addItemListener(ItemListener aListener) {
        listenerList.add(ItemListener.class,aListener);
    }

    /** Removes an <code>ItemListener</code>.
     *
     * <p>
     * 
     * @param aListener  the <code>ItemListener</code> to remove
     */
    public void removeItemListener(ItemListener aListener) {
        listenerList.remove(ItemListener.class,aListener);
    }

    /**
     * Returns an array of all the <code>ItemListener</code>s added
     * to this JComboBox with addItemListener().
     *
     * <p>
     *  返回使用addItemListener()添加到此JComboBox的所有<code> ItemListener </code>数组。
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
     * Adds an <code>ActionListener</code>.
     * <p>
     * The <code>ActionListener</code> will receive an <code>ActionEvent</code>
     * when a selection has been made. If the combo box is editable, then
     * an <code>ActionEvent</code> will be fired when editing has stopped.
     *
     * <p>
     *  添加<code> ActionListener </code>。
     * <p>
     * 当做出选择时,<code> ActionListener </code>将接收到<code> ActionEvent </code>。
     * 如果组合框是可编辑的,那么当编辑停止时,会触发<code> ActionEvent </code>。
     * 
     * 
     * @param l  the <code>ActionListener</code> that is to be notified
     * @see #setSelectedItem
     */
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class,l);
    }

    /** Removes an <code>ActionListener</code>.
     *
     * <p>
     * 
     * @param l  the <code>ActionListener</code> to remove
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
     * to this JComboBox with addActionListener().
     *
     * <p>
     *  返回通过addActionListener()添加到此JComboBox的所有<code> ActionListener </code>数组。
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
     * Adds a <code>PopupMenu</code> listener which will listen to notification
     * messages from the popup portion of the combo box.
     * <p>
     * For all standard look and feels shipped with Java, the popup list
     * portion of combo box is implemented as a <code>JPopupMenu</code>.
     * A custom look and feel may not implement it this way and will
     * therefore not receive the notification.
     *
     * <p>
     *  添加一个<code> PopupMenu </code>侦听器,它将侦听来自组合框弹出部分的通知消息。
     * <p>
     *  对于Java提供的所有标准外观和感觉,组合框的弹出列表部分实现为<code> JPopupMenu </code>。自定义的外观和感觉可能不会这样实现,因此不会收到通知。
     * 
     * 
     * @param l  the <code>PopupMenuListener</code> to add
     * @since 1.4
     */
    public void addPopupMenuListener(PopupMenuListener l) {
        listenerList.add(PopupMenuListener.class,l);
    }

    /**
     * Removes a <code>PopupMenuListener</code>.
     *
     * <p>
     *  删除<code> PopupMenuListener </code>。
     * 
     * 
     * @param l  the <code>PopupMenuListener</code> to remove
     * @see #addPopupMenuListener
     * @since 1.4
     */
    public void removePopupMenuListener(PopupMenuListener l) {
        listenerList.remove(PopupMenuListener.class,l);
    }

    /**
     * Returns an array of all the <code>PopupMenuListener</code>s added
     * to this JComboBox with addPopupMenuListener().
     *
     * <p>
     *  返回使用addPopupMenuListener()添加到此JComboBox的所有<code> PopupMenuListener </code>数组。
     * 
     * 
     * @return all of the <code>PopupMenuListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public PopupMenuListener[] getPopupMenuListeners() {
        return listenerList.getListeners(PopupMenuListener.class);
    }

    /**
     * Notifies <code>PopupMenuListener</code>s that the popup portion of the
     * combo box will become visible.
     * <p>
     * This method is public but should not be called by anything other than
     * the UI delegate.
     * <p>
     *  通知<code> PopupMenuListener </code>,组合框的弹出部分将变为可见。
     * <p>
     *  此方法是公共的,但不应由除UI委托之外的任何其他方法调用。
     * 
     * 
     * @see #addPopupMenuListener
     * @since 1.4
     */
    public void firePopupMenuWillBecomeVisible() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuWillBecomeVisible(e);
            }
        }
    }

    /**
     * Notifies <code>PopupMenuListener</code>s that the popup portion of the
     * combo box has become invisible.
     * <p>
     * This method is public but should not be called by anything other than
     * the UI delegate.
     * <p>
     *  通知<code> PopupMenuListener </code>表明组合框的弹出部分已变为不可见。
     * <p>
     *  此方法是公共的,但不应由除UI委托之外的任何其他方法调用。
     * 
     * 
     * @see #addPopupMenuListener
     * @since 1.4
     */
    public void firePopupMenuWillBecomeInvisible() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuWillBecomeInvisible(e);
            }
        }
    }

    /**
     * Notifies <code>PopupMenuListener</code>s that the popup portion of the
     * combo box has been canceled.
     * <p>
     * This method is public but should not be called by anything other than
     * the UI delegate.
     * <p>
     *  通知<code> PopupMenuListener </code>表示组合框的弹出部分已取消。
     * <p>
     *  此方法是公共的,但不应由除UI委托之外的任何其他方法调用。
     * 
     * 
     * @see #addPopupMenuListener
     * @since 1.4
     */
    public void firePopupMenuCanceled() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuCanceled(e);
            }
        }
    }

    /**
     * Sets the action command that should be included in the event
     * sent to action listeners.
     *
     * <p>
     *  设置应发送到动作侦听器的事件中应包含的操作命令。
     * 
     * 
     * @param aCommand  a string containing the "command" that is sent
     *                  to action listeners; the same listener can then
     *                  do different things depending on the command it
     *                  receives
     */
    public void setActionCommand(String aCommand) {
        actionCommand = aCommand;
    }

    /**
     * Returns the action command that is included in the event sent to
     * action listeners.
     *
     * <p>
     * 返回发送到操作侦听器的事件中包含的操作命令。
     * 
     * 
     * @return  the string containing the "command" that is sent
     *          to action listeners.
     */
    public String getActionCommand() {
        return actionCommand;
    }

    private Action action;
    private PropertyChangeListener actionPropertyChangeListener;

    /**
     * Sets the <code>Action</code> for the <code>ActionEvent</code> source.
     * The new <code>Action</code> replaces any previously set
     * <code>Action</code> but does not affect <code>ActionListeners</code>
     * independently added with <code>addActionListener</code>.
     * If the <code>Action</code> is already a registered
     * <code>ActionListener</code> for the <code>ActionEvent</code> source,
     * it is not re-registered.
     * <p>
     * Setting the <code>Action</code> results in immediately changing
     * all the properties described in <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a>.
     * Subsequently, the combobox's properties are automatically updated
     * as the <code>Action</code>'s properties change.
     * <p>
     * This method uses three other methods to set
     * and help track the <code>Action</code>'s property values.
     * It uses the <code>configurePropertiesFromAction</code> method
     * to immediately change the combobox's properties.
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
     * 随后,随着<code> Action </code>的属性更改,组合框的属性会自动更新。
     * <p>
     *  此方法使用其他三种方法来设置和帮助跟踪<code> Action </code>的属性值。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改组合框的属性。
     * 要跟踪<code> Action </code>的属性值的更改,此方法注册<code> createActionPropertyChangeListener </code>返回的<code> Prope
     * rtyChangeListener </code>。
     * 它使用<code> configurePropertiesFromAction </code>方法立即更改组合框的属性。
     *  {@code Action}中的属性更改时,默认的{@code PropertyChangeListener}调用{@code actionPropertyChanged}方法。
     * 
     * 
     * @param a the <code>Action</code> for the <code>JComboBox</code>,
     *                  or <code>null</code>.
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
     * <code>ActionEvent</code> source, or <code>null</code> if no
     * <code>Action</code> is set.
     *
     * <p>
     *  如果未设置<code> Action </code>,则为此<code> ActionEvent </code>源或<code> null </code>返回当前设置的<code> Action </code>
     * 。
     * 
     * 
     * @return the <code>Action</code> for this <code>ActionEvent</code>
     *          source; or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the properties on this combobox to match those in the specified
     * <code>Action</code>.  Refer to <a href="Action.html#buttonActions">
     * Swing Components Supporting <code>Action</code></a> for more
     * details as to which properties this sets.
     *
     * <p>
     * 将此组合框上的属性设置为与指定的<code> Action </code>中的属性匹配。
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
     * Creates and returns a <code>PropertyChangeListener</code> that is
     * responsible for listening for changes from the specified
     * <code>Action</code> and updating the appropriate properties.
     * <p>
     * <b>Warning:</b> If you subclass this do not create an anonymous
     * inner class.  If you do the lifetime of the combobox will be tied to
     * that of the <code>Action</code>.
     *
     * <p>
     *  创建并返回一个<code> PropertyChangeListener </code>,它负责侦听来自指定<code> Action </code>的更改并更新相应的属性。
     * <p>
     *  <b>警告：</b>如果你子类化这不创建一个匿名内部类。如果你做的组合框的生命周期将绑定到<code> Action </code>。
     * 
     * 
     * @param a the combobox's action
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
        return new ComboBoxActionPropertyChangeListener(this, a);
    }

    /**
     * Updates the combobox's state in response to property changes in
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
     *  更新组合框的状态以响应关联操作中的属性更改。
     * 此方法从{@code createActionPropertyChangeListener}返回的{@code PropertyChangeListener}中调用。子类通常不需要调用这个。
     * 支持其他{@code Action}属性的子类应该覆盖此类和{@code configurePropertiesFromAction}。
     * <p>
     *  请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>中的表格,了解此方法设置的属性列表。
     * 
     * 
     * @param action the <code>Action</code> associated with this combobox
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
        } else if (Action.SHORT_DESCRIPTION == propertyName) {
            AbstractAction.setToolTipTextFromAction(this, action);
        }
    }

    private void setActionCommandFromAction(Action a) {
        setActionCommand((a != null) ?
                             (String)a.getValue(Action.ACTION_COMMAND_KEY) :
                             null);
    }


    private static class ComboBoxActionPropertyChangeListener
                 extends ActionPropertyChangeListener<JComboBox<?>> {
        ComboBoxActionPropertyChangeListener(JComboBox<?> b, Action a) {
            super(b, a);
        }
        protected void actionPropertyChanged(JComboBox<?> cb,
                                             Action action,
                                             PropertyChangeEvent e) {
            if (AbstractAction.shouldReconfigure(e)) {
                cb.configurePropertiesFromAction(action);
            } else {
                cb.actionPropertyChanged(action, e.getPropertyName());
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param e  the event of interest
     *
     * @see EventListenerList
     */
    protected void fireItemStateChanged(ItemEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for ( int i = listeners.length-2; i>=0; i-=2 ) {
            if ( listeners[i]==ItemListener.class ) {
                // Lazily create the event:
                // if (changeEvent == null)
                // changeEvent = new ChangeEvent(this);
                ((ItemListener)listeners[i+1]).itemStateChanged(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @see EventListenerList
     */
    protected void fireActionEvent() {
        if (!firingActionEvent) {
            // Set flag to ensure that an infinite loop is not created
            firingActionEvent = true;
            ActionEvent e = null;
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            long mostRecentEventTime = EventQueue.getMostRecentEventTime();
            int modifiers = 0;
            AWTEvent currentEvent = EventQueue.getCurrentEvent();
            if (currentEvent instanceof InputEvent) {
                modifiers = ((InputEvent)currentEvent).getModifiers();
            } else if (currentEvent instanceof ActionEvent) {
                modifiers = ((ActionEvent)currentEvent).getModifiers();
            }
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for ( int i = listeners.length-2; i>=0; i-=2 ) {
                if ( listeners[i]==ActionListener.class ) {
                    // Lazily create the event:
                    if ( e == null )
                        e = new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
                                            getActionCommand(),
                                            mostRecentEventTime, modifiers);
                    ((ActionListener)listeners[i+1]).actionPerformed(e);
                }
            }
            firingActionEvent = false;
        }
    }

    /**
     * This protected method is implementation specific. Do not access directly
     * or override.
     * <p>
     *  这种受保护的方法是实现特定的。不要直接访问或覆盖。
     * 
     */
    protected void selectedItemChanged() {
        if (selectedItemReminder != null ) {
            fireItemStateChanged(new ItemEvent(this,ItemEvent.ITEM_STATE_CHANGED,
                                               selectedItemReminder,
                                               ItemEvent.DESELECTED));
        }

        // set the new selected item.
        selectedItemReminder = dataModel.getSelectedItem();

        if (selectedItemReminder != null ) {
            fireItemStateChanged(new ItemEvent(this,ItemEvent.ITEM_STATE_CHANGED,
                                               selectedItemReminder,
                                               ItemEvent.SELECTED));
        }
    }

    /**
     * Returns an array containing the selected item.
     * This method is implemented for compatibility with
     * <code>ItemSelectable</code>.
     *
     * <p>
     * 返回包含所选项目的数组。此方法是为了与<code> ItemSelectable </code>兼容而实现的。
     * 
     * 
     * @return an array of <code>Objects</code> containing one
     *          element -- the selected item
     */
    public Object[] getSelectedObjects() {
        Object selectedObject = getSelectedItem();
        if ( selectedObject == null )
            return new Object[0];
        else {
            Object result[] = new Object[1];
            result[0] = selectedObject;
            return result;
        }
    }

    /**
     * This method is public as an implementation side effect.
     * do not call or override.
     * <p>
     *  这个方法是public的,作为实现的副作用。不要调用或覆盖。
     * 
     */
    public void actionPerformed(ActionEvent e) {
        ComboBoxEditor editor = getEditor();
        if ((editor != null) && (e != null) && (editor == e.getSource()
                || editor.getEditorComponent() == e.getSource())) {
            setPopupVisible(false);
            getModel().setSelectedItem(editor.getItem());
            String oldCommand = getActionCommand();
            setActionCommand("comboBoxEdited");
            fireActionEvent();
            setActionCommand(oldCommand);
        }
    }

    /**
     * This method is public as an implementation side effect.
     * do not call or override.
     * <p>
     *  这个方法是public的,作为实现的副作用。不要调用或覆盖。
     * 
     */
    public void contentsChanged(ListDataEvent e) {
        Object oldSelection = selectedItemReminder;
        Object newSelection = dataModel.getSelectedItem();
        if (oldSelection == null || !oldSelection.equals(newSelection)) {
            selectedItemChanged();
            if (!selectingItem) {
                fireActionEvent();
            }
        }
    }

    /**
     * This method is public as an implementation side effect.
     * do not call or override.
     * <p>
     *  这个方法是public的,作为实现的副作用。不要调用或覆盖。
     * 
     */
    public void intervalAdded(ListDataEvent e) {
        if (selectedItemReminder != dataModel.getSelectedItem()) {
            selectedItemChanged();
        }
    }

    /**
     * This method is public as an implementation side effect.
     * do not call or override.
     * <p>
     *  这个方法是public的,作为实现的副作用。不要调用或覆盖。
     * 
     */
    public void intervalRemoved(ListDataEvent e) {
        contentsChanged(e);
    }

    /**
     * Selects the list item that corresponds to the specified keyboard
     * character and returns true, if there is an item corresponding
     * to that character.  Otherwise, returns false.
     *
     * <p>
     *  选择与指定键盘字符相对应的列表项,并返回true,如果有与该字符相对应的项目。否则,返回false。
     * 
     * 
     * @param keyChar a char, typically this is a keyboard key
     *                  typed by the user
     */
    public boolean selectWithKeyChar(char keyChar) {
        int index;

        if ( keySelectionManager == null )
            keySelectionManager = createDefaultKeySelectionManager();

        index = keySelectionManager.selectionForKey(keyChar,getModel());
        if ( index != -1 ) {
            setSelectedIndex(index);
            return true;
        }
        else
            return false;
    }

    /**
     * Enables the combo box so that items can be selected. When the
     * combo box is disabled, items cannot be selected and values
     * cannot be typed into its field (if it is editable).
     *
     * <p>
     *  启用组合框,以便可以选择项目。当组合框被禁用时,不能选择项目,并且不能在其字段中键入值(如果它是可编辑的)。
     * 
     * 
     * @param b a boolean value, where true enables the component and
     *          false disables it
     * @beaninfo
     *        bound: true
     *    preferred: true
     *  description: Whether the combo box is enabled.
     */
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        firePropertyChange( "enabled", !isEnabled(), isEnabled() );
    }

    /**
     * Initializes the editor with the specified item.
     *
     * <p>
     *  使用指定的项目初始化编辑器。
     * 
     * 
     * @param anEditor the <code>ComboBoxEditor</code> that displays
     *                  the list item in the
     *                  combo box field and allows it to be edited
     * @param anItem   the object to display and edit in the field
     */
    public void configureEditor(ComboBoxEditor anEditor, Object anItem) {
        anEditor.setItem(anItem);
    }

    /**
     * Handles <code>KeyEvent</code>s, looking for the Tab key.
     * If the Tab key is found, the popup window is closed.
     *
     * <p>
     *  处理<code> KeyEvent </code>,找到Tab键。如果找到Tab键,则弹出窗口关闭。
     * 
     * 
     * @param e  the <code>KeyEvent</code> containing the keyboard
     *          key that was pressed
     */
    public void processKeyEvent(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_TAB ) {
            hidePopup();
        }
        super.processKeyEvent(e);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
        if (super.processKeyBinding(ks, e, condition, pressed)) {
            return true;
        }

        if (!isEditable() || condition != WHEN_FOCUSED || getEditor() == null
                || !Boolean.TRUE.equals(getClientProperty("JComboBox.isTableCellEditor"))) {
            return false;
        }

        Component editorComponent = getEditor().getEditorComponent();
        if (editorComponent instanceof JComponent) {
            JComponent component = (JComponent) editorComponent;
            return component.processKeyBinding(ks, e, WHEN_FOCUSED, pressed);
        }
        return false;
    }

    /**
     * Sets the object that translates a keyboard character into a list
     * selection. Typically, the first selection with a matching first
     * character becomes the selected item.
     *
     * @beaninfo
     *       expert: true
     *  description: The objects that changes the selection when a key is pressed.
     * <p>
     *  设置将键盘字符转换为列表选择的对象。通常,具有匹配的第一字符的第一选择变为所选择的项目。
     * 
     *  @beaninfo expert：true description：按下某个键时更改选择的对象。
     * 
     */
    public void setKeySelectionManager(KeySelectionManager aManager) {
        keySelectionManager = aManager;
    }

    /**
     * Returns the list's key-selection manager.
     *
     * <p>
     *  返回列表的键选择管理器。
     * 
     * 
     * @return the <code>KeySelectionManager</code> currently in use
     */
    public KeySelectionManager getKeySelectionManager() {
        return keySelectionManager;
    }

    /* Accessing the model */
    /**
     * Returns the number of items in the list.
     *
     * <p>
     *  返回列表中的项目数。
     * 
     * 
     * @return an integer equal to the number of items in the list
     */
    public int getItemCount() {
        return dataModel.getSize();
    }

    /**
     * Returns the list item at the specified index.  If <code>index</code>
     * is out of range (less than zero or greater than or equal to size)
     * it will return <code>null</code>.
     *
     * <p>
     *  返回指定索引处的列表项。如果<code> index </code>超出范围(小于零或大于或等于size),它将返回<code> null </code>。
     * 
     * 
     * @param index  an integer indicating the list position, where the first
     *               item starts at zero
     * @return the item at that list position; or
     *                  <code>null</code> if out of range
     */
    public E getItemAt(int index) {
        return dataModel.getElementAt(index);
    }

    /**
     * Returns an instance of the default key-selection manager.
     *
     * <p>
     * 返回默认键选择管理器的实例。
     * 
     * 
     * @return the <code>KeySelectionManager</code> currently used by the list
     * @see #setKeySelectionManager
     */
    protected KeySelectionManager createDefaultKeySelectionManager() {
        return new DefaultKeySelectionManager();
    }


    /**
     * The interface that defines a <code>KeySelectionManager</code>.
     * To qualify as a <code>KeySelectionManager</code>,
     * the class needs to implement the method
     * that identifies the list index given a character and the
     * combo box data model.
     * <p>
     *  定义<code> KeySelectionManager </code>的接口。
     * 为了限定为<code> KeySelectionManager </code>,类需要实现识别给定字符的列表索引和组合框数据模型的方法。
     * 
     */
    public interface KeySelectionManager {
        /** Given <code>aKey</code> and the model, returns the row
         *  that should become selected. Return -1 if no match was
         *  found.
         *
         * <p>
         *  应该选择。如果未找到匹配,则返回-1。
         * 
         * 
         * @param  aKey  a char value, usually indicating a keyboard key that
         *               was pressed
         * @param aModel a ComboBoxModel -- the component's data model, containing
         *               the list of selectable items
         * @return an int equal to the selected row, where 0 is the
         *         first item and -1 is none.
         */
        int selectionForKey(char aKey,ComboBoxModel aModel);
    }

    class DefaultKeySelectionManager implements KeySelectionManager, Serializable {
        public int selectionForKey(char aKey,ComboBoxModel aModel) {
            int i,c;
            int currentSelection = -1;
            Object selectedItem = aModel.getSelectedItem();
            String v;
            String pattern;

            if ( selectedItem != null ) {
                for ( i=0,c=aModel.getSize();i<c;i++ ) {
                    if ( selectedItem == aModel.getElementAt(i) ) {
                        currentSelection  =  i;
                        break;
                    }
                }
            }

            pattern = ("" + aKey).toLowerCase();
            aKey = pattern.charAt(0);

            for ( i = ++currentSelection, c = aModel.getSize() ; i < c ; i++ ) {
                Object elem = aModel.getElementAt(i);
                if (elem != null && elem.toString() != null) {
                    v = elem.toString().toLowerCase();
                    if ( v.length() > 0 && v.charAt(0) == aKey )
                        return i;
                }
            }

            for ( i = 0 ; i < currentSelection ; i ++ ) {
                Object elem = aModel.getElementAt(i);
                if (elem != null && elem.toString() != null) {
                    v = elem.toString().toLowerCase();
                    if ( v.length() > 0 && v.charAt(0) == aKey )
                        return i;
                }
            }
            return -1;
        }
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
     * Returns a string representation of this <code>JComboBox</code>.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JComboBox </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JComboBox</code>
     */
    protected String paramString() {
        String selectedItemReminderString = (selectedItemReminder != null ?
                                             selectedItemReminder.toString() :
                                             "");
        String isEditableString = (isEditable ? "true" : "false");
        String lightWeightPopupEnabledString = (lightWeightPopupEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",isEditable=" + isEditableString +
        ",lightWeightPopupEnabled=" + lightWeightPopupEnabledString +
        ",maximumRowCount=" + maximumRowCount +
        ",selectedItemReminder=" + selectedItemReminderString;
    }


///////////////////
// Accessibility support
///////////////////

    /**
     * Gets the AccessibleContext associated with this JComboBox.
     * For combo boxes, the AccessibleContext takes the form of an
     * AccessibleJComboBox.
     * A new AccessibleJComboBox instance is created if necessary.
     *
     * <p>
     *  获取与此JComboBox相关联的AccessibleContext。对于组合框,AccessibleContext采用AccessibleJComboBox的形式。
     * 如果需要,将创建一个新的AccessibleJComboBox实例。
     * 
     * 
     * @return an AccessibleJComboBox that serves as the
     *         AccessibleContext of this JComboBox
     */
    public AccessibleContext getAccessibleContext() {
        if ( accessibleContext == null ) {
            accessibleContext = new AccessibleJComboBox();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JComboBox</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to Combo Box user-interface elements.
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
     *  此类实现<code> JComboBox </code>类的辅助功能支持。它提供了适用于Combo Box用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJComboBox extends AccessibleJComponent
    implements AccessibleAction, AccessibleSelection {


        private JList popupList; // combo box popup list
        private Accessible previousSelectedAccessible = null;

        /**
         * Returns an AccessibleJComboBox instance
         * <p>
         *  返回一个AccessibleJComboBox实例
         * 
         * 
         * @since 1.4
         */
        public AccessibleJComboBox() {
            // set the combo box editor's accessible name and description
            JComboBox.this.addPropertyChangeListener(new AccessibleJComboBoxPropertyChangeListener());
            setEditorNameAndDescription();

            // Get the popup list
            Accessible a = getUI().getAccessibleChild(JComboBox.this, 0);
            if (a instanceof javax.swing.plaf.basic.ComboPopup) {
                // Listen for changes to the popup menu selection.
                popupList = ((javax.swing.plaf.basic.ComboPopup)a).getList();
                popupList.addListSelectionListener(
                    new AccessibleJComboBoxListSelectionListener());
            }
            // Listen for popup menu show/hide events
            JComboBox.this.addPopupMenuListener(
              new AccessibleJComboBoxPopupMenuListener());
        }

        /*
         * JComboBox PropertyChangeListener
         * <p>
         *  JComboBox PropertyChangeListener
         * 
         */
        private class AccessibleJComboBoxPropertyChangeListener
            implements PropertyChangeListener {

            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName() == "editor") {
                    // set the combo box editor's accessible name
                    // and description
                    setEditorNameAndDescription();
                }
            }
        }

        /*
         * Sets the combo box editor's accessible name and descripton
         * <p>
         *  设置组合框编辑器的可访问名称和描述符
         * 
         */
        private void setEditorNameAndDescription() {
            ComboBoxEditor editor = JComboBox.this.getEditor();
            if (editor != null) {
                Component comp = editor.getEditorComponent();
                if (comp instanceof Accessible) {
                    AccessibleContext ac = comp.getAccessibleContext();
                    if (ac != null) { // may be null
                        ac.setAccessibleName(getAccessibleName());
                        ac.setAccessibleDescription(getAccessibleDescription());
                    }
                }
            }
        }

        /*
         * Listener for combo box popup menu
         * TIGER - 4669379 4894434
         * <p>
         *  组合框弹出菜单的侦听器TIGER  -  4669379 4894434
         * 
         */
        private class AccessibleJComboBoxPopupMenuListener
            implements PopupMenuListener {

            /**
             *  This method is called before the popup menu becomes visible
             * <p>
             *  在弹出菜单变为可见之前调用此方法
             * 
             */
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // save the initial selection
                if (popupList == null) {
                    return;
                }
                int selectedIndex = popupList.getSelectedIndex();
                if (selectedIndex < 0) {
                    return;
                }
                previousSelectedAccessible =
                    popupList.getAccessibleContext().getAccessibleChild(selectedIndex);
            }

            /**
             * This method is called before the popup menu becomes invisible
             * Note that a JPopupMenu can become invisible any time
             * <p>
             *  此方法在弹出菜单变为不可见之前调用请注意,JPopupMenu可以随时变得不可见
             * 
             */
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // ignore
            }

            /**
             * This method is called when the popup menu is canceled
             * <p>
             *  当取消弹出菜单时调用此方法
             * 
             */
            public void popupMenuCanceled(PopupMenuEvent e) {
                // ignore
            }
        }

        /*
         * Handles changes to the popup list selection.
         * TIGER - 4669379 4894434 4933143
         * <p>
         *  处理对弹出列表选择的更改。 TIGER  -  4669379 4894434 4933143
         * 
         */
        private class AccessibleJComboBoxListSelectionListener
            implements ListSelectionListener {

            public void valueChanged(ListSelectionEvent e) {
                if (popupList == null) {
                    return;
                }

                // Get the selected popup list item.
                int selectedIndex = popupList.getSelectedIndex();
                if (selectedIndex < 0) {
                    return;
                }
                Accessible selectedAccessible =
                    popupList.getAccessibleContext().getAccessibleChild(selectedIndex);
                if (selectedAccessible == null) {
                    return;
                }

                // Fire a FOCUSED lost PropertyChangeEvent for the
                // previously selected list item.
                PropertyChangeEvent pce;

                if (previousSelectedAccessible != null) {
                    pce = new PropertyChangeEvent(previousSelectedAccessible,
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        AccessibleState.FOCUSED, null);
                    firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                       null, pce);
                }
                // Fire a FOCUSED gained PropertyChangeEvent for the
                // currently selected list item.
                pce = new PropertyChangeEvent(selectedAccessible,
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    null, AccessibleState.FOCUSED);
                firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                   null, pce);

                // Fire the ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY event
                // for the combo box.
                firePropertyChange(AccessibleContext.ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY,
                                   previousSelectedAccessible, selectedAccessible);

                // Save the previous selection.
                previousSelectedAccessible = selectedAccessible;
            }
        }


        /**
         * Returns the number of accessible children in the object.  If all
         * of the children of this object implement Accessible, than this
         * method should return the number of children of this object.
         *
         * <p>
         *  返回对象中可访问的子项数。如果这个对象的所有子对象实现Accessible,那么这个方法应该返回这个对象的子对象数。
         * 
         * 
         * @return the number of accessible children in the object.
         */
        public int getAccessibleChildrenCount() {
            // Always delegate to the UI if it exists
            if (ui != null) {
                return ui.getAccessibleChildrenCount(JComboBox.this);
            } else {
                return super.getAccessibleChildrenCount();
            }
        }

        /**
         * Returns the nth Accessible child of the object.
         * The child at index zero represents the popup.
         * If the combo box is editable, the child at index one
         * represents the editor.
         *
         * <p>
         *  返回对象的第n个Accessible子项。索引为零的子代表弹出窗口。如果组合框是可编辑的,索引为1的子代表编辑器。
         * 
         * 
         * @param i zero-based index of child
         * @return the nth Accessible child of the object
         */
        public Accessible getAccessibleChild(int i) {
            // Always delegate to the UI if it exists
            if (ui != null) {
                return ui.getAccessibleChild(JComboBox.this, i);
            } else {
               return super.getAccessibleChild(i);
            }
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
            return AccessibleRole.COMBO_BOX;
        }

        /**
         * Gets the state set of this object.  The AccessibleStateSet of
         * an object is composed of a set of unique AccessibleStates.
         * A change in the AccessibleStateSet of an object will cause a
         * PropertyChangeEvent to be fired for the ACCESSIBLE_STATE_PROPERTY
         * property.
         *
         * <p>
         * 获取此对象的状态集。对象的AccessibleStateSet由一组唯一的AccessibleStates组成。
         * 对象的AccessibleStateSet中的更改将导致针对ACCESSIBLE_STATE_PROPERTY属性触发PropertyChangeEvent。
         * 
         * 
         * @return an instance of AccessibleStateSet containing the
         * current state set of the object
         * @see AccessibleStateSet
         * @see AccessibleState
         * @see #addPropertyChangeListener
         *
         */
        public AccessibleStateSet getAccessibleStateSet() {
            // TIGER - 4489748
            AccessibleStateSet ass = super.getAccessibleStateSet();
            if (ass == null) {
                ass = new AccessibleStateSet();
            }
            if (JComboBox.this.isPopupVisible()) {
                ass.add(AccessibleState.EXPANDED);
            } else {
                ass.add(AccessibleState.COLLAPSED);
            }
            return ass;
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
         * Return a description of the specified action of the object.
         *
         * <p>
         *  返回对象的指定操作的描述。
         * 
         * 
         * @param i zero-based index of the actions
         */
        public String getAccessibleActionDescription(int i) {
            if (i == 0) {
                return UIManager.getString("ComboBox.togglePopupText");
            }
            else {
                return null;
            }
        }

        /**
         * Returns the number of Actions available in this object.  The
         * default behavior of a combo box is to have one action.
         *
         * <p>
         *  返回此对象中可用的操作数。组合框的默认行为是具有一个动作。
         * 
         * 
         * @return 1, the number of Actions in this object
         */
        public int getAccessibleActionCount() {
            return 1;
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
                setPopupVisible(!isPopupVisible());
                return true;
            }
            else {
                return false;
            }
        }


        /**
         * Get the AccessibleSelection associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleSelection interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleSelection。在为此类实现Java Accessibility API时,返回此对象,它负责代表自身实现AccessibleSelection接口。
         * 
         * 
         * @return this object
         */
        public AccessibleSelection getAccessibleSelection() {
            return this;
        }

        /**
         * Returns the number of Accessible children currently selected.
         * If no children are selected, the return value will be 0.
         *
         * <p>
         *  返回当前选定的可访问子项数。如果未选择任何子项,则返回值为0。
         * 
         * 
         * @return the number of items currently selected.
         * @since 1.3
         */
        public int getAccessibleSelectionCount() {
            Object o = JComboBox.this.getSelectedItem();
            if (o != null) {
                return 1;
            } else {
                return 0;
            }
        }

        /**
         * Returns an Accessible representing the specified selected child
         * in the popup.  If there isn't a selection, or there are
         * fewer children selected than the integer passed in, the return
         * value will be null.
         * <p>Note that the index represents the i-th selected child, which
         * is different from the i-th child.
         *
         * <p>
         *  返回表示弹出窗口中指定的选定子项的Accessible。如果没有选择,或者选择的子选择比传递的整数少,返回值将为null。 <p>请注意,索引表示第i个选定子项,它与第i个子项不同。
         * 
         * 
         * @param i the zero-based index of selected children
         * @return the i-th selected child
         * @see #getAccessibleSelectionCount
         * @since 1.3
         */
        public Accessible getAccessibleSelection(int i) {
            // Get the popup
            Accessible a =
                JComboBox.this.getUI().getAccessibleChild(JComboBox.this, 0);
            if (a != null &&
                a instanceof javax.swing.plaf.basic.ComboPopup) {

                // get the popup list
                JList list = ((javax.swing.plaf.basic.ComboPopup)a).getList();

                // return the i-th selection in the popup list
                AccessibleContext ac = list.getAccessibleContext();
                if (ac != null) {
                    AccessibleSelection as = ac.getAccessibleSelection();
                    if (as != null) {
                        return as.getAccessibleSelection(i);
                    }
                }
            }
            return null;
        }

        /**
         * Determines if the current child of this object is selected.
         *
         * <p>
         *  确定是否选择此对象的当前子项。
         * 
         * 
         * @return true if the current child of this object is selected;
         *              else false
         * @param i the zero-based index of the child in this Accessible
         * object.
         * @see AccessibleContext#getAccessibleChild
         * @since 1.3
         */
        public boolean isAccessibleChildSelected(int i) {
            return JComboBox.this.getSelectedIndex() == i;
        }

        /**
         * Adds the specified Accessible child of the object to the object's
         * selection.  If the object supports multiple selections,
         * the specified child is added to any existing selection, otherwise
         * it replaces any existing selection in the object.  If the
         * specified child is already selected, this method has no effect.
         *
         * <p>
         * 将对象的指定Accessible子项添加到对象的选择。如果对象支持多个选择,则将指定的子项添加到任何现有选择,否则将替换对象中的任何现有选择。如果已选择指定的子项,则此方法无效。
         * 
         * 
         * @param i the zero-based index of the child
         * @see AccessibleContext#getAccessibleChild
         * @since 1.3
         */
        public void addAccessibleSelection(int i) {
            // TIGER - 4856195
            clearAccessibleSelection();
            JComboBox.this.setSelectedIndex(i);
        }

        /**
         * Removes the specified child of the object from the object's
         * selection.  If the specified item isn't currently selected, this
         * method has no effect.
         *
         * <p>
         *  从对象的选择中删除对象的指定子项。如果当前未选择指定的项目,则此方法无效。
         * 
         * 
         * @param i the zero-based index of the child
         * @see AccessibleContext#getAccessibleChild
         * @since 1.3
         */
        public void removeAccessibleSelection(int i) {
            if (JComboBox.this.getSelectedIndex() == i) {
                clearAccessibleSelection();
            }
        }

        /**
         * Clears the selection in the object, so that no children in the
         * object are selected.
         * <p>
         *  清除对象中的选择,以便不会选择对象中的任何子对象。
         * 
         * 
         * @since 1.3
         */
        public void clearAccessibleSelection() {
            JComboBox.this.setSelectedIndex(-1);
        }

        /**
         * Causes every child of the object to be selected
         * if the object supports multiple selections.
         * <p>
         *  如果对象支持多个选择,则导致选择对象的每个子项。
         * 
         * 
         * @since 1.3
         */
        public void selectAllAccessibleSelection() {
            // do nothing since multiple selection is not supported
        }

//        public Accessible getAccessibleAt(Point p) {
//            Accessible a = getAccessibleChild(1);
//            if ( a != null ) {
//                return a; // the editor
//            }
//            else {
//                return getAccessibleChild(0); // the list
//            }
//        }
        private EditorAccessibleContext editorAccessibleContext = null;

        private class AccessibleEditor implements Accessible {
            public AccessibleContext getAccessibleContext() {
                if (editorAccessibleContext == null) {
                    Component c = JComboBox.this.getEditor().getEditorComponent();
                    if (c instanceof Accessible) {
                        editorAccessibleContext =
                            new EditorAccessibleContext((Accessible)c);
                    }
                }
                return editorAccessibleContext;
            }
        }

        /*
         * Wrapper class for the AccessibleContext implemented by the
         * combo box editor.  Delegates all method calls except
         * getAccessibleIndexInParent to the editor.  The
         * getAccessibleIndexInParent method returns the selected
         * index in the combo box.
         * <p>
         *  组合框编辑器实现的AccessibleContext的包装类。将除了getAccessibleIndexInParent之外的所有方法调用委派给编辑器。
         *  getAccessibleIndexInParent方法返回组合框中选定的索引。
         * 
         */
        private class EditorAccessibleContext extends AccessibleContext {

            private AccessibleContext ac;

            private EditorAccessibleContext() {
            }

            /*
            /* <p>
            /* 
             * @param a the AccessibleContext implemented by the
             * combo box editor
             */
            EditorAccessibleContext(Accessible a) {
                this.ac = a.getAccessibleContext();
            }

            /**
             * Gets the accessibleName property of this object.  The accessibleName
             * property of an object is a localized String that designates the purpose
             * of the object.  For example, the accessibleName property of a label
             * or button might be the text of the label or button itself.  In the
             * case of an object that doesn't display its name, the accessibleName
             * should still be set.  For example, in the case of a text field used
             * to enter the name of a city, the accessibleName for the en_US locale
             * could be 'city.'
             *
             * <p>
             *  获取此对象的accessibleName属性。对象的accessibleName属性是一个本地化的字符串,指定对象的用途。
             * 例如,标签或按钮的accessibleName属性可以是标签或按钮本身的文本。在对象不显示其名称的情况下,仍应设置accessibleName。
             * 例如,在用于输入城市名称的文本字段的情况下,en_US语言环境的accessibleName可以是"城市"。
             * 
             * 
             * @return the localized name of the object; null if this
             * object does not have a name
             *
             * @see #setAccessibleName
             */
            public String getAccessibleName() {
                return ac.getAccessibleName();
            }

            /**
             * Sets the localized accessible name of this object.  Changing the
             * name will cause a PropertyChangeEvent to be fired for the
             * ACCESSIBLE_NAME_PROPERTY property.
             *
             * <p>
             * 设置此对象的本地化可访问名称。更改名称将导致针对ACCESSIBLE_NAME_PROPERTY属性触发PropertyChangeEvent。
             * 
             * 
             * @param s the new localized name of the object.
             *
             * @see #getAccessibleName
             * @see #addPropertyChangeListener
             *
             * @beaninfo
             *    preferred:   true
             *    description: Sets the accessible name for the component.
             */
            public void setAccessibleName(String s) {
                ac.setAccessibleName(s);
            }

            /**
             * Gets the accessibleDescription property of this object.  The
             * accessibleDescription property of this object is a short localized
             * phrase describing the purpose of the object.  For example, in the
             * case of a 'Cancel' button, the accessibleDescription could be
             * 'Ignore changes and close dialog box.'
             *
             * <p>
             *  获取此对象的accessibleDescription属性。此对象的accessibleDescription属性是一个简短的本地化短语,用于描述对象的用途。
             * 例如,在"取消"按钮的情况下,accessibleDescription可以是"忽略更改并关闭对话框"。
             * 
             * 
             * @return the localized description of the object; null if
             * this object does not have a description
             *
             * @see #setAccessibleDescription
             */
            public String getAccessibleDescription() {
                return ac.getAccessibleDescription();
            }

            /**
             * Sets the accessible description of this object.  Changing the
             * name will cause a PropertyChangeEvent to be fired for the
             * ACCESSIBLE_DESCRIPTION_PROPERTY property.
             *
             * <p>
             *  设置此对象的可访问描述。更改名称将导致针对ACCESSIBLE_DESCRIPTION_PROPERTY属性触发PropertyChangeEvent。
             * 
             * 
             * @param s the new localized description of the object
             *
             * @see #setAccessibleName
             * @see #addPropertyChangeListener
             *
             * @beaninfo
             *    preferred:   true
             *    description: Sets the accessible description for the component.
             */
            public void setAccessibleDescription(String s) {
                ac.setAccessibleDescription(s);
            }

            /**
             * Gets the role of this object.  The role of the object is the generic
             * purpose or use of the class of this object.  For example, the role
             * of a push button is AccessibleRole.PUSH_BUTTON.  The roles in
             * AccessibleRole are provided so component developers can pick from
             * a set of predefined roles.  This enables assistive technologies to
             * provide a consistent interface to various tweaked subclasses of
             * components (e.g., use AccessibleRole.PUSH_BUTTON for all components
             * that act like a push button) as well as distinguish between subclasses
             * that behave differently (e.g., AccessibleRole.CHECK_BOX for check boxes
             * and AccessibleRole.RADIO_BUTTON for radio buttons).
             * <p>Note that the AccessibleRole class is also extensible, so
             * custom component developers can define their own AccessibleRole's
             * if the set of predefined roles is inadequate.
             *
             * <p>
             *  获取此对象的作用。对象的作用是该对象的类的通用目的或使用。例如,按钮的角色是AccessibleRole.PUSH_BUTTON。
             * 提供了AccessibleRole中的角色,因此组件开发人员可以从一组预定义角色中进行选择。
             * 这使得辅助技术能够为组件的各种调整子类提供一致的接口(例如,对于像按钮一样操作的所有组件使用AccessibleRole.PUSH_BUTTON),以及区分行为不同的子类(例如,用于复选框的Access
             * ibleRole.CHECK_BOX和AccessibleRole.RADIO_BUTTON的单选按钮)。
             * 提供了AccessibleRole中的角色,因此组件开发人员可以从一组预定义角色中进行选择。
             *  <p>请注意,AccessibleRole类也是可扩展的,因此如果预定义角色集合不足,自定义组件开发人员可以定义自己的AccessibleRole。
             * 
             * 
             * @return an instance of AccessibleRole describing the role of the object
             * @see AccessibleRole
             */
            public AccessibleRole getAccessibleRole() {
                return ac.getAccessibleRole();
            }

            /**
             * Gets the state set of this object.  The AccessibleStateSet of an object
             * is composed of a set of unique AccessibleStates.  A change in the
             * AccessibleStateSet of an object will cause a PropertyChangeEvent to
             * be fired for the ACCESSIBLE_STATE_PROPERTY property.
             *
             * <p>
             * 获取此对象的状态集。对象的AccessibleStateSet由一组唯一的AccessibleStates组成。
             * 对象的AccessibleStateSet中的更改将导致针对ACCESSIBLE_STATE_PROPERTY属性触发PropertyChangeEvent。
             * 
             * 
             * @return an instance of AccessibleStateSet containing the
             * current state set of the object
             * @see AccessibleStateSet
             * @see AccessibleState
             * @see #addPropertyChangeListener
             */
            public AccessibleStateSet getAccessibleStateSet() {
                return ac.getAccessibleStateSet();
            }

            /**
             * Gets the Accessible parent of this object.
             *
             * <p>
             *  获取此对象的可访问父级。
             * 
             * 
             * @return the Accessible parent of this object; null if this
             * object does not have an Accessible parent
             */
            public Accessible getAccessibleParent() {
                return ac.getAccessibleParent();
            }

            /**
             * Sets the Accessible parent of this object.  This is meant to be used
             * only in the situations where the actual component's parent should
             * not be treated as the component's accessible parent and is a method
             * that should only be called by the parent of the accessible child.
             *
             * <p>
             *  设置此对象的可访问父级。这意味着仅在实际组件的父代不应被视为该组件的可访问父代的情况下使用,并且是一种只应由可访问子代的父代调用的方法。
             * 
             * 
             * @param a - Accessible to be set as the parent
             */
            public void setAccessibleParent(Accessible a) {
                ac.setAccessibleParent(a);
            }

            /**
             * Gets the 0-based index of this object in its accessible parent.
             *
             * <p>
             *  获取此对象在其可访问父级中的基于0的索引。
             * 
             * 
             * @return the 0-based index of this object in its parent; -1 if this
             * object does not have an accessible parent.
             *
             * @see #getAccessibleParent
             * @see #getAccessibleChildrenCount
             * @see #getAccessibleChild
             */
            public int getAccessibleIndexInParent() {
                return JComboBox.this.getSelectedIndex();
            }

            /**
             * Returns the number of accessible children of the object.
             *
             * <p>
             *  返回对象的可访问子项数。
             * 
             * 
             * @return the number of accessible children of the object.
             */
            public int getAccessibleChildrenCount() {
                return ac.getAccessibleChildrenCount();
            }

            /**
             * Returns the specified Accessible child of the object.  The Accessible
             * children of an Accessible object are zero-based, so the first child
             * of an Accessible child is at index 0, the second child is at index 1,
             * and so on.
             *
             * <p>
             *  返回对象的指定Accessible子项。可访问对象的可访问子对象是基于零的,因此可访问子对象的第一个子对象位于索引0,第二个子对象位于索引1,依此类推。
             * 
             * 
             * @param i zero-based index of child
             * @return the Accessible child of the object
             * @see #getAccessibleChildrenCount
             */
            public Accessible getAccessibleChild(int i) {
                return ac.getAccessibleChild(i);
            }

            /**
             * Gets the locale of the component. If the component does not have a
             * locale, then the locale of its parent is returned.
             *
             * <p>
             *  获取组件的语言环境。如果组件没有语言环境,那么将返回其父组件的语言环境。
             * 
             * 
             * @return this component's locale.  If this component does not have
             * a locale, the locale of its parent is returned.
             *
             * @exception IllegalComponentStateException
             * If the Component does not have its own locale and has not yet been
             * added to a containment hierarchy such that the locale can be
             * determined from the containing parent.
             */
            public Locale getLocale() throws IllegalComponentStateException {
                return ac.getLocale();
            }

            /**
             * Adds a PropertyChangeListener to the listener list.
             * The listener is registered for all Accessible properties and will
             * be called when those properties change.
             *
             * <p>
             *  将PropertyChangeListener添加到侦听器列表。侦听器为所有可访问属性注册,并将在这些属性更改时调用。
             * 
             * 
             * @see #ACCESSIBLE_NAME_PROPERTY
             * @see #ACCESSIBLE_DESCRIPTION_PROPERTY
             * @see #ACCESSIBLE_STATE_PROPERTY
             * @see #ACCESSIBLE_VALUE_PROPERTY
             * @see #ACCESSIBLE_SELECTION_PROPERTY
             * @see #ACCESSIBLE_TEXT_PROPERTY
             * @see #ACCESSIBLE_VISIBLE_DATA_PROPERTY
             *
             * @param listener  The PropertyChangeListener to be added
             */
            public void addPropertyChangeListener(PropertyChangeListener listener) {
                ac.addPropertyChangeListener(listener);
            }

            /**
             * Removes a PropertyChangeListener from the listener list.
             * This removes a PropertyChangeListener that was registered
             * for all properties.
             *
             * <p>
             *  从侦听器列表中删除PropertyChangeListener。这将删除为所有属性注册的PropertyChangeListener。
             * 
             * 
             * @param listener  The PropertyChangeListener to be removed
             */
            public void removePropertyChangeListener(PropertyChangeListener listener) {
                ac.removePropertyChangeListener(listener);
            }

            /**
             * Gets the AccessibleAction associated with this object that supports
             * one or more actions.
             *
             * <p>
             *  获取与支持一个或多个操作的此对象关联的AccessibleAction。
             * 
             * 
             * @return AccessibleAction if supported by object; else return null
             * @see AccessibleAction
             */
            public AccessibleAction getAccessibleAction() {
                return ac.getAccessibleAction();
            }

            /**
             * Gets the AccessibleComponent associated with this object that has a
             * graphical representation.
             *
             * <p>
             * 获取与具有图形表示形式的此对象相关联的AccessibleComponent。
             * 
             * 
             * @return AccessibleComponent if supported by object; else return null
             * @see AccessibleComponent
             */
            public AccessibleComponent getAccessibleComponent() {
                return ac.getAccessibleComponent();
            }

            /**
             * Gets the AccessibleSelection associated with this object which allows its
             * Accessible children to be selected.
             *
             * <p>
             *  获取与此对象相关联的AccessibleSelection,以允许选择其可访问的子项。
             * 
             * 
             * @return AccessibleSelection if supported by object; else return null
             * @see AccessibleSelection
             */
            public AccessibleSelection getAccessibleSelection() {
                return ac.getAccessibleSelection();
            }

            /**
             * Gets the AccessibleText associated with this object presenting
             * text on the display.
             *
             * <p>
             *  获取与此对象相关联的AccessibleText在显示器上显示文本。
             * 
             * 
             * @return AccessibleText if supported by object; else return null
             * @see AccessibleText
             */
            public AccessibleText getAccessibleText() {
                return ac.getAccessibleText();
            }

            /**
             * Gets the AccessibleEditableText associated with this object
             * presenting editable text on the display.
             *
             * <p>
             *  获取与此对象相关联的AccessibleEditableText在显示屏上显示可编辑文本。
             * 
             * 
             * @return AccessibleEditableText if supported by object; else return null
             * @see AccessibleEditableText
             */
            public AccessibleEditableText getAccessibleEditableText() {
                return ac.getAccessibleEditableText();
            }

            /**
             * Gets the AccessibleValue associated with this object that supports a
             * Numerical value.
             *
             * <p>
             *  获取与支持数值的此对象相关联的AccessibleValue。
             * 
             * 
             * @return AccessibleValue if supported by object; else return null
             * @see AccessibleValue
             */
            public AccessibleValue getAccessibleValue() {
                return ac.getAccessibleValue();
            }

            /**
             * Gets the AccessibleIcons associated with an object that has
             * one or more associated icons
             *
             * <p>
             *  获取与具有一个或多个关联图标的对象相关联的AccessibleIcons
             * 
             * 
             * @return an array of AccessibleIcon if supported by object;
             * otherwise return null
             * @see AccessibleIcon
             */
            public AccessibleIcon [] getAccessibleIcon() {
                return ac.getAccessibleIcon();
            }

            /**
             * Gets the AccessibleRelationSet associated with an object
             *
             * <p>
             *  获取与对象关联的AccessibleRelationSet
             * 
             * 
             * @return an AccessibleRelationSet if supported by object;
             * otherwise return null
             * @see AccessibleRelationSet
             */
            public AccessibleRelationSet getAccessibleRelationSet() {
                return ac.getAccessibleRelationSet();
            }

            /**
             * Gets the AccessibleTable associated with an object
             *
             * <p>
             *  获取与对象关联的AccessibleTable
             * 
             * 
             * @return an AccessibleTable if supported by object;
             * otherwise return null
             * @see AccessibleTable
             */
            public AccessibleTable getAccessibleTable() {
                return ac.getAccessibleTable();
            }

            /**
             * Support for reporting bound property changes.  If oldValue and
             * newValue are not equal and the PropertyChangeEvent listener list
             * is not empty, then fire a PropertyChange event to each listener.
             * In general, this is for use by the Accessible objects themselves
             * and should not be called by an application program.
             * <p>
             *  支持报告绑定的属性更改。如果oldValue和newValue不相等,并且PropertyChangeEvent侦听器列表不为空,那么为每个侦听器触发一个PropertyChange事件。
             * 一般来说,这是由Accessible对象本身使用,不应该被应用程序调用。
             * 
             * @param propertyName  The programmatic name of the property that
             * was changed.
             * @param oldValue  The old value of the property.
             * @param newValue  The new value of the property.
             * @see java.beans.PropertyChangeSupport
             * @see #addPropertyChangeListener
             * @see #removePropertyChangeListener
             * @see #ACCESSIBLE_NAME_PROPERTY
             * @see #ACCESSIBLE_DESCRIPTION_PROPERTY
             * @see #ACCESSIBLE_STATE_PROPERTY
             * @see #ACCESSIBLE_VALUE_PROPERTY
             * @see #ACCESSIBLE_SELECTION_PROPERTY
             * @see #ACCESSIBLE_TEXT_PROPERTY
             * @see #ACCESSIBLE_VISIBLE_DATA_PROPERTY
             */
            public void firePropertyChange(String propertyName,
                                           Object oldValue,
                                           Object newValue) {
                ac.firePropertyChange(propertyName, oldValue, newValue);
            }
        }

    } // innerclass AccessibleJComboBox
}
