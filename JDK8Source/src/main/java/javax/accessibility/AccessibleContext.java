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

package javax.accessibility;

import sun.awt.AWTAccessor;
import sun.awt.AppContext;

import java.util.Locale;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
import java.awt.IllegalComponentStateException;

/**
 * AccessibleContext represents the minimum information all accessible objects
 * return.  This information includes the accessible name, description, role,
 * and state of the object, as well as information about its parent and
 * children.  AccessibleContext also contains methods for
 * obtaining more specific accessibility information about a component.
 * If the component supports them, these methods will return an object that
 * implements one or more of the following interfaces:
 * <P><ul>
 * <li>{@link AccessibleAction} - the object can perform one or more actions.
 * This interface provides the standard mechanism for an assistive
 * technology to determine what those actions are and tell the object
 * to perform them.  Any object that can be manipulated should
 * support this interface.
 * <li>{@link AccessibleComponent} - the object has a graphical representation.
 * This interface provides the standard mechanism for an assistive
 * technology to determine and set the graphical representation of the
 * object.  Any object that is rendered on the screen should support
 * this interface.
 * <li>{@link  AccessibleSelection} - the object allows its children to be
 * selected.  This interface provides the standard mechanism for an
 * assistive technology to determine the currently selected children of the object
 * as well as modify its selection set.  Any object that has children
 * that can be selected should support this interface.
 * <li>{@link AccessibleText} - the object presents editable textual information
 * on the display.  This interface provides the standard mechanism for
 * an assistive technology to access that text via its content, attributes,
 * and spatial location.  Any object that contains editable text should
 * support this interface.
 * <li>{@link AccessibleValue} - the object supports a numerical value.  This
 * interface provides the standard mechanism for an assistive technology
 * to determine and set the current value of the object, as well as obtain its
 * minimum and maximum values.  Any object that supports a numerical value
 * should support this interface.</ul>
 *
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: Minimal information that all accessible objects return
 *

 * <p>
 * AccessibleContext表示最小信息所有可访问对象返回此信息包括对象的可访问名称,描述,角色和状态,以及有关其父和子项的信息AccessibleContext还包含用于获取有关组件的更多特定辅
 * 助功能信息的方法如果组件支持它们,这些方法将返回实现以下一个或多个接口的对象：<P> <ul> <li> {@ link AccessibleAction}  - 对象可以执行一个或多个操作此接口提供了
 * 标准机制一个辅助技术来确定这些动作是什么,并告诉对象执行它们任何可以被操作的对象都应该支持这个接口<li> {@ link AccessibleComponent}  - 对象具有图形表示此界面为辅助技
 * 术确定和设置对象的图形表示提供了标准机制。
 * 在屏幕上呈现的任何对象都应支持此界面<li> {@link AccessibleSelection}  - 对象允许选择其子项此接口为辅助技术提供标准机制,以确定对象的当前选定的子项以及修改其选择集任何
 * 具有可选择的子项的对象支持此界面<li> {@ link AccessibleText}  - 对象在显示屏上显示可编辑的文本信息此接口为辅助技术通过其内容,属性和空间位置访问文本提供了标准机制包含可编
 * 辑文本的任何对象都应支持此接口<li> {@ link AccessibleValue}  - 对象支持数值此接口提供用于辅助技术的标准机制以确定和设置对象的当前值,以及获得其最小值和最大值任何支持数值
 * 的对象应该支持该接口</ul>。
 * 
 * @beaninfo属性：isContainer false description：所有可访问对象返回的最小信息
 * 
 * 
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 * @author      Lynn Monsanto
 */
public abstract class AccessibleContext {

    /**
     * The AppContext that should be used to dispatch events for this
     * AccessibleContext
     * <p>
     *  应用于调度此AccessibleContext的事件的AppContext
     * 
     */
    private volatile AppContext targetAppContext;

    static {
        AWTAccessor.setAccessibleContextAccessor(new AWTAccessor.AccessibleContextAccessor() {
            @Override
            public void setAppContext(AccessibleContext accessibleContext, AppContext appContext) {
                accessibleContext.targetAppContext = appContext;
            }

            @Override
            public AppContext getAppContext(AccessibleContext accessibleContext) {
                return accessibleContext.targetAppContext;
            }
        });
    }

   /**
    * Constant used to determine when the accessibleName property has
    * changed.  The old value in the PropertyChangeEvent will be the old
    * accessibleName and the new value will be the new accessibleName.
    *
    * <p>
    *  用于确定accessibleName属性何时更改的常量PropertyChangeEvent中的旧值将是旧的accessibleName,新值将是新的accessibleName
    * 
    * 
    * @see #getAccessibleName
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_NAME_PROPERTY = "AccessibleName";

   /**
    * Constant used to determine when the accessibleDescription property has
    * changed.  The old value in the PropertyChangeEvent will be the
    * old accessibleDescription and the new value will be the new
    * accessibleDescription.
    *
    * <p>
    *  用于确定accessibleDescription属性何时更改的常量PropertyChangeEvent中的旧值将是旧的accessibleDescription,新值将是新的accessibleD
    * escription。
    * 
    * 
    * @see #getAccessibleDescription
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_DESCRIPTION_PROPERTY = "AccessibleDescription";

   /**
    * Constant used to determine when the accessibleStateSet property has
    * changed.  The old value will be the old AccessibleState and the new
    * value will be the new AccessibleState in the accessibleStateSet.
    * For example, if a component that supports the vertical and horizontal
    * states changes its orientation from vertical to horizontal, the old
    * value will be AccessibleState.VERTICAL and the new value will be
    * AccessibleState.HORIZONTAL.  Please note that either value can also
    * be null.  For example, when a component changes from being enabled
    * to disabled, the old value will be AccessibleState.ENABLED
    * and the new value will be null.
    *
    * <p>
    * 用于确定accessibleStateSet属性何时更改的常量旧值将是旧的AccessibleState,新值将是accessibleStateSet中的新AccessibleState例如,如果支持垂
    * 直和水平状态的组件将其方向从垂直变为水平,旧值将为AccessibleStateVERTICAL,新值将为AccessibleStateHORIZONTAL请注意,任一值也可以为null例如,当组件从启
    * 用更改为禁用时,旧值将为AccessibleStateENABLED,新值将为null。
    * 
    * 
    * @see #getAccessibleStateSet
    * @see AccessibleState
    * @see AccessibleStateSet
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_STATE_PROPERTY = "AccessibleState";

   /**
    * Constant used to determine when the accessibleValue property has
    * changed.  The old value in the PropertyChangeEvent will be a Number
    * representing the old value and the new value will be a Number
    * representing the new value
    *
    * <p>
    * 用于确定accessibleValue属性何时更改的常量PropertyChangeEvent中的旧值将是表示旧值的Number,新值将是表示新值的Number
    * 
    * 
    * @see #getAccessibleValue
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_VALUE_PROPERTY = "AccessibleValue";

   /**
    * Constant used to determine when the accessibleSelection has changed.
    * The old and new values in the PropertyChangeEvent are currently
    * reserved for future use.
    *
    * <p>
    *  用于确定accessibleSelection何时更改的常量PropertyChangeEvent中的旧值和新值当前保留以供将来使用
    * 
    * 
    * @see #getAccessibleSelection
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_SELECTION_PROPERTY = "AccessibleSelection";

   /**
    * Constant used to determine when the accessibleText caret has changed.
    * The old value in the PropertyChangeEvent will be an
    * integer representing the old caret position, and the new value will
    * be an integer representing the new/current caret position.
    *
    * <p>
    *  用于确定accessibleText插入符号何时已更改的常量PropertyChangeEvent中的旧值将是表示旧插入符号位置的整数,新值将是表示新/当前插入符号位置的整数
    * 
    * 
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_CARET_PROPERTY = "AccessibleCaret";

   /**
    * Constant used to determine when the visual appearance of the object
    * has changed.  The old and new values in the PropertyChangeEvent are
    * currently reserved for future use.
    *
    * <p>
    * 用于确定对象的可视外观何时已更改的常量PropertyChangeEvent中的旧值和新值当前保留以供将来使用
    * 
    * 
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_VISIBLE_DATA_PROPERTY = "AccessibleVisibleData";

   /**
    * Constant used to determine when Accessible children are added/removed
    * from the object.  If an Accessible child is being added, the old
    * value will be null and the new value will be the Accessible child.  If an
    * Accessible child is being removed, the old value will be the Accessible
    * child, and the new value will be null.
    *
    * <p>
    *  用于确定何时添加/从对象中删除可访问子项的常量如果正在添加可访问子项,则旧值将为null,新值将为可访问子项如果正在删除可访问子项,则旧值将为Accessible子项,并且新值将为null
    * 
    * 
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_CHILD_PROPERTY = "AccessibleChild";

   /**
    * Constant used to determine when the active descendant of a component
    * has changed.  The active descendant is used for objects such as
    * list, tree, and table, which may have transient children.  When the
    * active descendant has changed, the old value of the property change
    * event will be the Accessible representing the previous active child, and
    * the new value will be the Accessible representing the current active
    * child.
    *
    * <p>
    * 用于确定组件的活动后代何时更改的常量活动后代用于列表,树和表等对象,这可能具有临时子级当活动子级发生更改时,属性更改事件的旧值将是代表上一个活动子项的Accessible,新值将是表示当前活动子项的Ac
    * cessible。
    * 
    * 
    * @see #addPropertyChangeListener
    */
   public static final String ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY = "AccessibleActiveDescendant";

    /**
     * Constant used to indicate that the table caption has changed
     * The old value in the PropertyChangeEvent will be an Accessible
     * representing the previous table caption and the new value will
     * be an Accessible representing the new table caption.
     * <p>
     *  用于指示表标题已更改的常量PropertyChangeEvent中的旧值将是表示上一个表标题的Accessible,新值将是表示新表标题的Accessible
     * 
     * 
     * @see Accessible
     * @see AccessibleTable
     */
    public static final String ACCESSIBLE_TABLE_CAPTION_CHANGED =
        "accessibleTableCaptionChanged";

    /**
     * Constant used to indicate that the table summary has changed
     * The old value in the PropertyChangeEvent will be an Accessible
     * representing the previous table summary and the new value will
     * be an Accessible representing the new table summary.
     * <p>
     * 用于指示表摘要已更改的常量PropertyChangeEvent中的旧值将是表示上一个表摘要的Accessible,新值将是表示新表摘要的Accessible
     * 
     * 
     * @see Accessible
     * @see AccessibleTable
     */
    public static final String ACCESSIBLE_TABLE_SUMMARY_CHANGED =
        "accessibleTableSummaryChanged";

    /**
     * Constant used to indicate that table data has changed.
     * The old value in the PropertyChangeEvent will be null and the
     * new value will be an AccessibleTableModelChange representing
     * the table change.
     * <p>
     *  用于指示表数据已更改的常量PropertyChangeEvent中的旧值将为null,新值将为表示表更改的AccessibleTableModelChange
     * 
     * 
     * @see AccessibleTable
     * @see AccessibleTableModelChange
     */
    public static final String ACCESSIBLE_TABLE_MODEL_CHANGED =
        "accessibleTableModelChanged";

    /**
     * Constant used to indicate that the row header has changed
     * The old value in the PropertyChangeEvent will be null and the
     * new value will be an AccessibleTableModelChange representing
     * the header change.
     * <p>
     *  用于指示行标头已更改的常量PropertyChangeEvent中的旧值将为null,新值将为表示标头更改的AccessibleTableModelChange
     * 
     * 
     * @see AccessibleTable
     * @see AccessibleTableModelChange
     */
    public static final String ACCESSIBLE_TABLE_ROW_HEADER_CHANGED =
        "accessibleTableRowHeaderChanged";

    /**
     * Constant used to indicate that the row description has changed
     * The old value in the PropertyChangeEvent will be null and the
     * new value will be an Integer representing the row index.
     * <p>
     * 用于指示行描述已更改的常量PropertyChangeEvent中的旧值将为null,新值将为表示行索引的整数
     * 
     * 
     * @see AccessibleTable
     */
    public static final String ACCESSIBLE_TABLE_ROW_DESCRIPTION_CHANGED =
        "accessibleTableRowDescriptionChanged";

    /**
     * Constant used to indicate that the column header has changed
     * The old value in the PropertyChangeEvent will be null and the
     * new value will be an AccessibleTableModelChange representing
     * the header change.
     * <p>
     *  用于指示列标题已更改的常量PropertyChangeEvent中的旧值将为null,新值将为表示标题更改的AccessibleTableModelChange
     * 
     * 
     * @see AccessibleTable
     * @see AccessibleTableModelChange
     */
    public static final String ACCESSIBLE_TABLE_COLUMN_HEADER_CHANGED =
        "accessibleTableColumnHeaderChanged";

    /**
     * Constant used to indicate that the column description has changed
     * The old value in the PropertyChangeEvent will be null and the
     * new value will be an Integer representing the column index.
     * <p>
     *  用于指示列描述已更改的常量PropertyChangeEvent中的旧值将为null,新值将为表示列索引的整数
     * 
     * 
     * @see AccessibleTable
     */
    public static final String ACCESSIBLE_TABLE_COLUMN_DESCRIPTION_CHANGED =
        "accessibleTableColumnDescriptionChanged";

    /**
     * Constant used to indicate that the supported set of actions
     * has changed.  The old value in the PropertyChangeEvent will
     * be an Integer representing the old number of actions supported
     * and the new value will be an Integer representing the new
     * number of actions supported.
     * <p>
     * 用于指示支持的一组操作已更改的常量PropertyChangeEvent中的旧值将是一个表示支持的旧操作数的整数,新值将是一个整数,表示支持的操作的新数
     * 
     * 
     * @see AccessibleAction
     */
    public static final String ACCESSIBLE_ACTION_PROPERTY =
        "accessibleActionProperty";

    /**
     * Constant used to indicate that a hypertext element has received focus.
     * The old value in the PropertyChangeEvent will be an Integer
     * representing the start index in the document of the previous element
     * that had focus and the new value will be an Integer representing
     * the start index in the document of the current element that has
     * focus.  A value of -1 indicates that an element does not or did
     * not have focus.
     * <p>
     *  用于指示超文本元素已接收焦点的常量PropertyChangeEvent中的旧值将是表示具有焦点的上一元素的文档中的开始索引的整数,并且新值将是表示文档中的开始索引的整数具有焦点的当前元素值为-1表示
     * 元素没有或没有焦点。
     * 
     * 
     * @see AccessibleHyperlink
     */
    public static final String ACCESSIBLE_HYPERTEXT_OFFSET =
        "AccessibleHypertextOffset";

    /**
     * PropertyChangeEvent which indicates that text has changed.
     * <br>
     * For text insertion, the oldValue is null and the newValue
     * is an AccessibleTextSequence specifying the text that was
     * inserted.
     * <br>
     * For text deletion, the oldValue is an AccessibleTextSequence
     * specifying the text that was deleted and the newValue is null.
     * <br>
     * For text replacement, the oldValue is an AccessibleTextSequence
     * specifying the old text and the newValue is an AccessibleTextSequence
     * specifying the new text.
     *
     * <p>
     *  PropertyChangeEvent,表示文本已更改
     * <br>
     * 对于文本插入,oldValue为null,newValue是一个AccessibleTextSequence,指定插入的文本
     * <br>
     *  对于文本删除,oldValue是一个AccessibleTextSequence,指定被删除的文本,newValue为null
     * <br>
     *  对于文本替换,oldValue是指定旧文本的AccessibleTextSequence,newValue是指定新文本的AccessibleTextSequence
     * 
     * 
     * @see #getAccessibleText
     * @see #addPropertyChangeListener
     * @see AccessibleTextSequence
     */
    public static final String ACCESSIBLE_TEXT_PROPERTY
        = "AccessibleText";

    /**
     * PropertyChangeEvent which indicates that a significant change
     * has occurred to the children of a component like a tree or text.
     * This change notifies the event listener that it needs to
     * reacquire the state of the subcomponents. The oldValue is
     * null and the newValue is the component whose children have
     * become invalid.
     *
     * <p>
     *  PropertyChangeEvent,它指示组件的子元素(如树或文本)发生了显着更改此更改通知事件侦听器需要重新获取子组件的状态oldValue为null,newValue是其子元素已成为无效
     * 
     * 
     * @see #getAccessibleText
     * @see #addPropertyChangeListener
     * @see AccessibleTextSequence
     *
     * @since 1.5
     */
    public static final String ACCESSIBLE_INVALIDATE_CHILDREN =
        "accessibleInvalidateChildren";

     /**
     * PropertyChangeEvent which indicates that text attributes have changed.
     * <br>
     * For attribute insertion, the oldValue is null and the newValue
     * is an AccessibleAttributeSequence specifying the attributes that were
     * inserted.
     * <br>
     * For attribute deletion, the oldValue is an AccessibleAttributeSequence
     * specifying the attributes that were deleted and the newValue is null.
     * <br>
     * For attribute replacement, the oldValue is an AccessibleAttributeSequence
     * specifying the old attributes and the newValue is an
     * AccessibleAttributeSequence specifying the new attributes.
     *
     * <p>
     * PropertyChangeEvent,表示文本属性已更改
     * <br>
     *  对于属性插入,oldValue为null,newValue是一个AccessibleAttributeSequence,指定被插入的属性
     * <br>
     *  对于属性删除,oldValue是一个AccessibleAttributeSequence,指定被删除的属性,newValue为null
     * <br>
     *  对于属性替换,oldValue是一个指定旧属性的AccessibleAttributeSequence,newValue是一个指定新属性的AccessibleAttributeSequence
     * 
     * 
     * @see #getAccessibleText
     * @see #addPropertyChangeListener
     * @see AccessibleAttributeSequence
     *
     * @since 1.5
     */
    public static final String ACCESSIBLE_TEXT_ATTRIBUTES_CHANGED =
        "accessibleTextAttributesChanged";

   /**
     * PropertyChangeEvent which indicates that a change has occurred
     * in a component's bounds.
     * The oldValue is the old component bounds and the newValue is
     * the new component bounds.
     *
     * <p>
     *  PropertyChangeEvent,表示在组件的边界中发生了更改oldValue是旧的组件边界,newValue是新的组件边界
     * 
     * 
     * @see #addPropertyChangeListener
     *
     * @since 1.5
     */
    public static final String ACCESSIBLE_COMPONENT_BOUNDS_CHANGED =
        "accessibleComponentBoundsChanged";

    /**
     * The accessible parent of this object.
     *
     * <p>
     *  此对象的可访问父级
     * 
     * 
     * @see #getAccessibleParent
     * @see #setAccessibleParent
     */
    protected Accessible accessibleParent = null;

    /**
     * A localized String containing the name of the object.
     *
     * <p>
     * 包含对象名称的本地化字符串
     * 
     * 
     * @see #getAccessibleName
     * @see #setAccessibleName
     */
    protected String accessibleName = null;

    /**
     * A localized String containing the description of the object.
     *
     * <p>
     *  包含对象描述的本地化字符串
     * 
     * 
     * @see #getAccessibleDescription
     * @see #setAccessibleDescription
     */
    protected String accessibleDescription = null;

    /**
     * Used to handle the listener list for property change events.
     *
     * <p>
     *  用于处理属性更改事件的侦听器列表
     * 
     * 
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see #firePropertyChangeListener
     */
    private PropertyChangeSupport accessibleChangeSupport = null;

    /**
     * Used to represent the context's relation set
     * <p>
     *  用于表示上下文的关系集
     * 
     * 
     * @see #getAccessibleRelationSet
     */
    private AccessibleRelationSet relationSet
        = new AccessibleRelationSet();

    private Object nativeAXResource;

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
     *  获取此对象的accessibleName属性对象的accessibleName属性是指定对象的目的的本地化String例如,标签或按钮的accessibleName属性可能是标签或按钮本身的文本。
     * 在对象不显示其名称,则仍应设置accessibleName例如,在用于输入城市名称的文本字段的情况下,en_US语言环境的accessibleName可以是"city"。
     * 
     * 
     * @return the localized name of the object; null if this
     * object does not have a name
     *
     * @see #setAccessibleName
     */
    public String getAccessibleName() {
        return accessibleName;
    }

    /**
     * Sets the localized accessible name of this object.  Changing the
     * name will cause a PropertyChangeEvent to be fired for the
     * ACCESSIBLE_NAME_PROPERTY property.
     *
     * <p>
     * 设置此对象的本地化可访问名称更改名称将导致针对ACCESSIBLE_NAME_PROPERTY属性触发PropertyChangeEvent
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
        String oldName = accessibleName;
        accessibleName = s;
        firePropertyChange(ACCESSIBLE_NAME_PROPERTY,oldName,accessibleName);
    }

    /**
     * Gets the accessibleDescription property of this object.  The
     * accessibleDescription property of this object is a short localized
     * phrase describing the purpose of the object.  For example, in the
     * case of a 'Cancel' button, the accessibleDescription could be
     * 'Ignore changes and close dialog box.'
     *
     * <p>
     *  获取此对象的accessibleDescription属性此对象的accessibleDescription属性是一个简短的本地化短语,用于描述对象的用途例如,在"取消"按钮的情况下,accessib
     * leDescription可以是"忽略更改并关闭对话框"。
     * 
     * 
     * @return the localized description of the object; null if
     * this object does not have a description
     *
     * @see #setAccessibleDescription
     */
    public String getAccessibleDescription() {
        return accessibleDescription;
    }

    /**
     * Sets the accessible description of this object.  Changing the
     * name will cause a PropertyChangeEvent to be fired for the
     * ACCESSIBLE_DESCRIPTION_PROPERTY property.
     *
     * <p>
     *  设置此对象的可访问描述更改名称将导致针对ACCESSIBLE_DESCRIPTION_PROPERTY属性触发PropertyChangeEvent
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
        String oldDescription = accessibleDescription;
        accessibleDescription = s;
        firePropertyChange(ACCESSIBLE_DESCRIPTION_PROPERTY,
                           oldDescription,accessibleDescription);
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
     * 获取此对象的角色对象的角色是此对象的类的通用目的或使用例如,按钮的角色是AccessibleRolePUSH_BUTTON提供了AccessibleRole中的角色,因此组件开发人员可以从一组预定义角色
     * 这使得辅助技术能够为组件的各种调整子类提供一致的接口(例如,对于像按钮一样操作的所有组件使用AccessibleRolePUSH_BUTTON),以及区分行为不同的子类(例如,对于复选框的Accessi
     * bleRoleCHECK_BOX和对于复选框的AccessibleRoleRADIO_BUTTON单选按钮)<p>请注意,AccessibleRole类也是可扩展的,因此自定义组件开发人员可以定义自己的
     * AccessibleRole如果预定义角色的集合不足。
     * 
     * 
     * @return an instance of AccessibleRole describing the role of the object
     * @see AccessibleRole
     */
    public abstract AccessibleRole getAccessibleRole();

    /**
     * Gets the state set of this object.  The AccessibleStateSet of an object
     * is composed of a set of unique AccessibleStates.  A change in the
     * AccessibleStateSet of an object will cause a PropertyChangeEvent to
     * be fired for the ACCESSIBLE_STATE_PROPERTY property.
     *
     * <p>
     * 获取此对象的状态集对象的AccessibleStateSet由一组唯一的AccessibleStates组成对象的AccessibleStateSet中的更改将导致针对ACCESSIBLE_STATE_
     * PROPERTY属性触发PropertyChangeEvent。
     * 
     * 
     * @return an instance of AccessibleStateSet containing the
     * current state set of the object
     * @see AccessibleStateSet
     * @see AccessibleState
     * @see #addPropertyChangeListener
     */
    public abstract AccessibleStateSet getAccessibleStateSet();

    /**
     * Gets the Accessible parent of this object.
     *
     * <p>
     *  获取此对象的可访问父级
     * 
     * 
     * @return the Accessible parent of this object; null if this
     * object does not have an Accessible parent
     */
    public Accessible getAccessibleParent() {
        return accessibleParent;
    }

    /**
     * Sets the Accessible parent of this object.  This is meant to be used
     * only in the situations where the actual component's parent should
     * not be treated as the component's accessible parent and is a method
     * that should only be called by the parent of the accessible child.
     *
     * <p>
     *  设置此对象的可访问父对象这意味着仅在实际组件的父对象不应被视为组件的可访问父对象的情况下使用,并且是只应由可访问子对象的父对象调用的方法
     * 
     * 
     * @param a - Accessible to be set as the parent
     */
    public void setAccessibleParent(Accessible a) {
        accessibleParent = a;
    }

    /**
     * Gets the 0-based index of this object in its accessible parent.
     *
     * <p>
     *  获取此对象在其可访问父级中的基于0的索引
     * 
     * 
     * @return the 0-based index of this object in its parent; -1 if this
     * object does not have an accessible parent.
     *
     * @see #getAccessibleParent
     * @see #getAccessibleChildrenCount
     * @see #getAccessibleChild
     */
    public abstract int getAccessibleIndexInParent();

    /**
     * Returns the number of accessible children of the object.
     *
     * <p>
     *  返回对象的可访问子项数
     * 
     * 
     * @return the number of accessible children of the object.
     */
    public abstract int getAccessibleChildrenCount();

    /**
     * Returns the specified Accessible child of the object.  The Accessible
     * children of an Accessible object are zero-based, so the first child
     * of an Accessible child is at index 0, the second child is at index 1,
     * and so on.
     *
     * <p>
     * 返回对象的指定Accessible子项Accessible对象的Accessible子项是从零开始的,因此Accessible子项的第一个子项位于索引0,第二个子项位于索引1,依此类推
     * 
     * 
     * @param i zero-based index of child
     * @return the Accessible child of the object
     * @see #getAccessibleChildrenCount
     */
    public abstract Accessible getAccessibleChild(int i);

    /**
     * Gets the locale of the component. If the component does not have a
     * locale, then the locale of its parent is returned.
     *
     * <p>
     *  获取组件的语言环境如果组件没有语言环境,则返回其父语言环境的语言环境
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
    public abstract Locale getLocale() throws IllegalComponentStateException;

    /**
     * Adds a PropertyChangeListener to the listener list.
     * The listener is registered for all Accessible properties and will
     * be called when those properties change.
     *
     * <p>
     *  将PropertyChangeListener添加到侦听器列表侦听器为所有可访问属性注册,并将在这些属性更改时调用
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
        if (accessibleChangeSupport == null) {
            accessibleChangeSupport = new PropertyChangeSupport(this);
        }
        accessibleChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除PropertyChangeListener这将删除为所有属性注册的PropertyChangeListener
     * 
     * 
     * @param listener  The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (accessibleChangeSupport != null) {
            accessibleChangeSupport.removePropertyChangeListener(listener);
        }
    }

    /**
     * Gets the AccessibleAction associated with this object that supports
     * one or more actions.
     *
     * <p>
     *  获取与支持一个或多个操作的此对象关联的AccessibleAction
     * 
     * 
     * @return AccessibleAction if supported by object; else return null
     * @see AccessibleAction
     */
    public AccessibleAction getAccessibleAction() {
        return null;
    }

    /**
     * Gets the AccessibleComponent associated with this object that has a
     * graphical representation.
     *
     * <p>
     * 获取与具有图形表示形式的此对象相关联的AccessibleComponent
     * 
     * 
     * @return AccessibleComponent if supported by object; else return null
     * @see AccessibleComponent
     */
    public AccessibleComponent getAccessibleComponent() {
        return null;
    }

    /**
     * Gets the AccessibleSelection associated with this object which allows its
     * Accessible children to be selected.
     *
     * <p>
     *  获取与此对象相关联的AccessibleSelection,以允许选择其可访问的子项
     * 
     * 
     * @return AccessibleSelection if supported by object; else return null
     * @see AccessibleSelection
     */
    public AccessibleSelection getAccessibleSelection() {
        return null;
    }

    /**
     * Gets the AccessibleText associated with this object presenting
     * text on the display.
     *
     * <p>
     *  获取与此对象相关联的AccessibleText在显示器上显示文本
     * 
     * 
     * @return AccessibleText if supported by object; else return null
     * @see AccessibleText
     */
    public AccessibleText getAccessibleText() {
        return null;
    }

    /**
     * Gets the AccessibleEditableText associated with this object
     * presenting editable text on the display.
     *
     * <p>
     *  获取与此对象相关联的AccessibleEditableText在显示屏上显示可编辑文本
     * 
     * 
     * @return AccessibleEditableText if supported by object; else return null
     * @see AccessibleEditableText
     * @since 1.4
     */
    public AccessibleEditableText getAccessibleEditableText() {
        return null;
    }


    /**
     * Gets the AccessibleValue associated with this object that supports a
     * Numerical value.
     *
     * <p>
     *  获取与支持数值的此对象相关联的AccessibleValue
     * 
     * 
     * @return AccessibleValue if supported by object; else return null
     * @see AccessibleValue
     */
    public AccessibleValue getAccessibleValue() {
        return null;
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
     * @since 1.3
     */
    public AccessibleIcon [] getAccessibleIcon() {
        return null;
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
     * @since 1.3
     */
    public AccessibleRelationSet getAccessibleRelationSet() {
        return relationSet;
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
     * @since 1.3
     */
    public AccessibleTable getAccessibleTable() {
        return null;
    }

    /**
     * Support for reporting bound property changes.  If oldValue and
     * newValue are not equal and the PropertyChangeEvent listener list
     * is not empty, then fire a PropertyChange event to each listener.
     * In general, this is for use by the Accessible objects themselves
     * and should not be called by an application program.
     * <p>
     * 支持报告绑定的属性更改如果oldValue和newValue不相等,并且PropertyChangeEvent侦听器列表不为空,那么为每个侦听器触发PropertyChange事件一般来说,这是由Acc
     * essible对象本身使用,不应由应用程序调用程序。
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
        if (accessibleChangeSupport != null) {
            if (newValue instanceof PropertyChangeEvent) {
                PropertyChangeEvent pce = (PropertyChangeEvent)newValue;
                accessibleChangeSupport.firePropertyChange(pce);
            } else {
                accessibleChangeSupport.firePropertyChange(propertyName,
                                                           oldValue,
                                                           newValue);
            }
        }
    }
}
