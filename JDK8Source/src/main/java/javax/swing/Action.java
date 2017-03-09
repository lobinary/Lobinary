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
import java.beans.*;

/**
 * The <code>Action</code> interface provides a useful extension to the
 * <code>ActionListener</code>
 * interface in cases where the same functionality may be accessed by
 * several controls.
 * <p>
 * In addition to the <code>actionPerformed</code> method defined by the
 * <code>ActionListener</code> interface, this interface allows the
 * application to define, in a single place:
 * <ul>
 * <li>One or more text strings that describe the function. These strings
 *     can be used, for example, to display the flyover text for a button
 *     or to set the text in a menu item.
 * <li>One or more icons that depict the function. These icons can be used
 *     for the images in a menu control, or for composite entries in a more
 *     sophisticated user interface.
 * <li>The enabled/disabled state of the functionality. Instead of having
 *     to separately disable the menu item and the toolbar button, the
 *     application can disable the function that implements this interface.
 *     All components which are registered as listeners for the state change
 *     then know to disable event generation for that item and to modify the
 *     display accordingly.
 * </ul>
 * <p>
 * This interface can be added to an existing class or used to create an
 * adapter (typically, by subclassing <code>AbstractAction</code>).
 * The <code>Action</code> object
 * can then be added to multiple <code>Action</code>-aware containers
 * and connected to <code>Action</code>-capable
 * components. The GUI controls can then be activated or
 * deactivated all at once by invoking the <code>Action</code> object's
 * <code>setEnabled</code> method.
 * <p>
 * Note that <code>Action</code> implementations tend to be more expensive
 * in terms of storage than a typical <code>ActionListener</code>,
 * which does not offer the benefits of centralized control of
 * functionality and broadcast of property changes.  For this reason,
 * you should take care to only use <code>Action</code>s where their benefits
 * are desired, and use simple <code>ActionListener</code>s elsewhere.
 * <br>
 *
 * <h3><a name="buttonActions"></a>Swing Components Supporting <code>Action</code></h3>
 * <p>
 * Many of Swing's components have an <code>Action</code> property.  When
 * an <code>Action</code> is set on a component, the following things
 * happen:
 * <ul>
 * <li>The <code>Action</code> is added as an <code>ActionListener</code> to
 *     the component.
 * <li>The component configures some of its properties to match the
 *      <code>Action</code>.
 * <li>The component installs a <code>PropertyChangeListener</code> on the
 *     <code>Action</code> so that the component can change its properties
 *     to reflect changes in the <code>Action</code>'s properties.
 * </ul>
 * <p>
 * The following table describes the properties used by
 * <code>Swing</code> components that support <code>Actions</code>.
 * In the table, <em>button</em> refers to any
 * <code>AbstractButton</code> subclass, which includes not only
 * <code>JButton</code> but also classes such as
 * <code>JMenuItem</code>. Unless otherwise stated, a
 * <code>null</code> property value in an <code>Action</code> (or a
 * <code>Action</code> that is <code>null</code>) results in the
 * button's corresponding property being set to <code>null</code>.
 *
 * <table border="1" cellpadding="1" cellspacing="0"
 *         summary="Supported Action properties">
 *  <tr valign="top"  align="left">
 *    <th style="background-color:#CCCCFF" align="left">Component Property
 *    <th style="background-color:#CCCCFF" align="left">Components
 *    <th style="background-color:#CCCCFF" align="left">Action Key
 *    <th style="background-color:#CCCCFF" align="left">Notes
 *  <tr valign="top"  align="left">
 *      <td><b><code>enabled</code></b>
 *      <td>All
 *      <td>The <code>isEnabled</code> method
 *      <td>&nbsp;
 *  <tr valign="top"  align="left">
 *      <td><b><code>toolTipText</code></b>
 *      <td>All
 *      <td><code>SHORT_DESCRIPTION</code>
 *      <td>&nbsp;
 *  <tr valign="top"  align="left">
 *      <td><b><code>actionCommand</code></b>
 *      <td>All
 *      <td><code>ACTION_COMMAND_KEY</code>
 *      <td>&nbsp;
 *  <tr valign="top"  align="left">
 *      <td><b><code>mnemonic</code></b>
 *      <td>All buttons
 *      <td><code>MNEMONIC_KEY</code>
 *      <td>A <code>null</code> value or <code>Action</code> results in the
 *          button's <code>mnemonic</code> property being set to
 *          <code>'\0'</code>.
 *  <tr valign="top"  align="left">
 *      <td><b><code>text</code></b>
 *      <td>All buttons
 *      <td><code>NAME</code>
 *      <td>If you do not want the text of the button to mirror that
 *          of the <code>Action</code>, set the property
 *          <code>hideActionText</code> to <code>true</code>.  If
 *          <code>hideActionText</code> is <code>true</code>, setting the
 *          <code>Action</code> changes the text of the button to
 *          <code>null</code> and any changes to <code>NAME</code>
 *          are ignored.  <code>hideActionText</code> is useful for
 *          tool bar buttons that typically only show an <code>Icon</code>.
 *          <code>JToolBar.add(Action)</code> sets the property to
 *          <code>true</code> if the <code>Action</code> has a
 *          non-<code>null</code> value for <code>LARGE_ICON_KEY</code> or
 *          <code>SMALL_ICON</code>.
 *  <tr valign="top"  align="left">
 *      <td><b><code>displayedMnemonicIndex</code></b>
 *      <td>All buttons
 *      <td><code>DISPLAYED_MNEMONIC_INDEX_KEY</code>
 *      <td>If the value of <code>DISPLAYED_MNEMONIC_INDEX_KEY</code> is
 *          beyond the bounds of the text, it is ignored.  When
 *          <code>setAction</code> is called, if the value from the
 *          <code>Action</code> is <code>null</code>, the displayed
 *          mnemonic index is not updated.  In any subsequent changes to
 *          <code>DISPLAYED_MNEMONIC_INDEX_KEY</code>, <code>null</code>
 *          is treated as -1.
 *  <tr valign="top"  align="left">
 *      <td><b><code>icon</code></b>
 *      <td>All buttons except of <code>JCheckBox</code>,
 *      <code>JToggleButton</code> and <code>JRadioButton</code>.
 *      <td>either <code>LARGE_ICON_KEY</code> or
 *          <code>SMALL_ICON</code>
 *     <td>The <code>JMenuItem</code> subclasses only use
 *         <code>SMALL_ICON</code>.  All other buttons will use
 *         <code>LARGE_ICON_KEY</code>; if the value is <code>null</code> they
 *         use <code>SMALL_ICON</code>.
 *  <tr valign="top"  align="left">
 *      <td><b><code>accelerator</code></b>
 *      <td>All <code>JMenuItem</code> subclasses, with the exception of
 *          <code>JMenu</code>.
 *      <td><code>ACCELERATOR_KEY</code>
 *      <td>&nbsp;
 *  <tr valign="top"  align="left">
 *      <td><b><code>selected</code></b>
 *      <td><code>JToggleButton</code>, <code>JCheckBox</code>,
 *          <code>JRadioButton</code>, <code>JCheckBoxMenuItem</code> and
 *          <code>JRadioButtonMenuItem</code>
 *      <td><code>SELECTED_KEY</code>
 *      <td>Components that honor this property only use
 *          the value if it is {@code non-null}. For example, if
 *          you set an {@code Action} that has a {@code null}
 *          value for {@code SELECTED_KEY} on a {@code JToggleButton}, the
 *          {@code JToggleButton} will not update it's selected state in
 *          any way. Similarly, any time the {@code JToggleButton}'s
 *          selected state changes it will only set the value back on
 *          the {@code Action} if the {@code Action} has a {@code non-null}
 *          value for {@code SELECTED_KEY}.
 *          <br>
 *          Components that honor this property keep their selected state
 *          in sync with this property. When the same {@code Action} is used
 *          with multiple components, all the components keep their selected
 *          state in sync with this property. Mutually exclusive
 *          buttons, such as {@code JToggleButton}s in a {@code ButtonGroup},
 *          force only one of the buttons to be selected. As such, do not
 *          use the same {@code Action} that defines a value for the
 *          {@code SELECTED_KEY} property with multiple mutually
 *          exclusive buttons.
 * </table>
 * <p>
 * <code>JPopupMenu</code>, <code>JToolBar</code> and <code>JMenu</code>
 * all provide convenience methods for creating a component and setting the
 * <code>Action</code> on the corresponding component.  Refer to each of
 * these classes for more information.
 * <p>
 * <code>Action</code> uses <code>PropertyChangeListener</code> to
 * inform listeners the <code>Action</code> has changed.  The beans
 * specification indicates that a <code>null</code> property name can
 * be used to indicate multiple values have changed.  By default Swing
 * components that take an <code>Action</code> do not handle such a
 * change.  To indicate that Swing should treat <code>null</code>
 * according to the beans specification set the system property
 * <code>swing.actions.reconfigureOnNull</code> to the <code>String</code>
 * value <code>true</code>.
 *
 * <p>
 *  在同一功能可能被几个控件访问的情况下,<code> Action </code>接口为<code> ActionListener </code>接口提供了一个有用的扩展。
 * <p>
 *  除了由<code> ActionListener </code>接口定义的<code> actionPerformed </code>方法,此接口允许应用程序在单个位置定义：
 * <ul>
 *  <li>描述函数的一个或多个文本字符串。这些字符串可用于例如显示按钮的悬停文本或设置菜单项中的文本。 <li>描述功能的一个或多个图标。
 * 这些图标可用于菜单控件中的图像,或用于更复杂的用户界面中的复合条目。 <li>功能的启用/禁用状态。代替必须单独禁用菜单项和工具栏按钮,应用可以禁用实现该接口的功能。
 * 作为状态改变的监听器注册的所有组件然后知道禁用该项目的事件生成并相应地修改显示。
 * </ul>
 * <p>
 * 此接口可以添加到现有类或用于创建适配器(通常,通过子类化<code> AbstractAction </code>)。
 * 然后可以将<code> Action </code>对象添加到多个<code> Action </code> -aware容器,并连接到<code> Action </code>然后可以通过调用<code>
 *  Action </code>对象的<code> setEnabled </code>方法立即激活或停用GUI控件。
 * 此接口可以添加到现有类或用于创建适配器(通常,通过子类化<code> AbstractAction </code>)。
 * <p>
 *  注意,与典型的<code> ActionListener </code>相比,<code> Action </code>实现在存储方面往往更昂贵,它不提供集中控制功能和广播属性更改的好处。
 * 出于这个原因,你应该注意只使用<code> Action </code>来获得他们的好处,并在其他地方使用简单的<code> ActionListener </code>。
 * <br>
 * 
 *  <h3> <a name="buttonActions"> </a> Swing组件支持<code>操作</code> </h3>
 * <p>
 *  许多Swing的组件都有一个<code> Action </code>属性。当在组件上设置<code> Action </code>时,会发生以下情况：
 * <ul>
 *  <li> <code> Action </code>作为<code> ActionListener </code>添加到组件。
 *  <li>组件配置其某些属性以匹配<code> Action </code>。
 *  <li>组件在<code> Action </code>上安装<code> PropertyChangeListener </code>,以便组件可以更改其属性以反映<code> Action </code>
 * 的属性中的更改。
 *  <li>组件配置其某些属性以匹配<code> Action </code>。
 * </ul>
 * <p>
 * 下表描述了支持<code> Actions </code>的<code> Swing </code>组件使用的属性。
 * 在表格中,<em>按钮</em>指的是任何<code> AbstractButton </code>子类,它不仅包括<code> JButton </code>,还包括类如<code> JMenuIte
 * m </code> 。
 * 下表描述了支持<code> Actions </code>的<code> Swing </code>组件使用的属性。
 * 除非另有说明,<code> Action </code>(或<code> Action </code>,即<code> null </code>)中的<code> null </code>属性值会导致按
 * 钮的相应属性设置为<code> null </code>。
 * 下表描述了支持<code> Actions </code>的<code> Swing </code>组件使用的属性。
 * 
 *  <table border ="1"cellpadding ="1"cellspacing ="0"
 * summary="Supported Action properties">
 * <tr valign="top"  align="left">
 *  <th style ="background-color：#CCCCFF"align ="left">组件属性<th style ="background-color：#CCCCFF"align ="left">
 * 组件<th style ="background-color：#CCCCFF "align ="left">动作键<th style ="background-color：#CCCCFF"align ="left">
 * 笔记。
 * <tr valign="top"  align="left">
 *  <td> <b> <code> isEnabled </code>方法<td> </b> <td> </
 * <tr valign="top"  align="left">
 *  <td> <b> <code> tooltipText </code> </b> <td>全部<td> <code> SHORT_DESCRIPTION </code> <td>
 * <tr valign="top"  align="left">
 *  <td> <b> <code> actionCommand </code> </b> <td>全部<td> <code> ACTION_COMMAND_KEY </code> <td>
 * <tr valign="top"  align="left">
 *  <td> <b> <code>助记符</code> </b> <td>所有按钮<td> <code> MNEMONIC_KEY </code> <td> A <code> null </code> >
 *  Action </code>会导致按钮的<code> mnemonic </code>属性设置为<code>'\ 0'</code>。
 * <tr valign="top"  align="left">
 * <td> <b> <code> text </code> </b> <td>所有按钮<td> <code> NAME </code> <td>如果您不想让按钮的文字<code> Action </code>
 * ,将属性<code> hideActionText </code>设置为<code> true </code>。
 * 如果<code> hideActionText </code>是<code> true </code>,设置<code> Action </code>会将按钮的文本更改为<code> null </code>
 *  > NAME </code>被忽略。
 *  <code> hideActionText </code>对于通常只显示一个<code> Icon </code>的工具栏按钮很有用。
 * 如果<code> Action </code>对于<code> null </code>值</code>,<code> JToolBar.add(Action)</code>将属性设置为<code> t
 * rue </code>代码> LARGE_ICON_KEY </code>或<code> SMALL_ICON </code>。
 *  <code> hideActionText </code>对于通常只显示一个<code> Icon </code>的工具栏按钮很有用。
 * <tr valign="top"  align="left">
 *  <td> <b> <code> DISPLAY </span> </span> </span> </span> </span> </span>超出了文本的界限,它被忽略。
 * 当调用<code> setAction </code>时,如果<code> Action </code>的值为<code> null </code>,则不更新显示的助记符索引。
 * 在对<code> DISPLAYED_MNEMONIC_INDEX_KEY </code>的任何后续更改中,<code> null </code>被视为-1。
 * <tr valign="top"  align="left">
 * <td> <b> <code>图标</code> </b> <td>除<code> JCheckBox </code>,<code> JToggleButton </code>和<code> JRadi
 * oButton </code >。
 *  <td> <code> LARGE_ICON_KEY </code>或<code> SMALL_ICON </code> <td> <code> JMenuItem </code>子类只能使用<code>
 *  SMALL_ICON </code>。
 * 所有其他按钮将使用<code> LARGE_ICON_KEY </code>;如果值为<code> null </code>,则使用<code> SMALL_ICON </code>。
 * <tr valign="top"  align="left">
 *  <td> <b> <code>加速器</code> </b> <td>所有<code> JMenuItem </code>子类,除了<code> JMenu </code>。
 *  <td> <code> ACCELERATOR_KEY </code> <td>。
 * <tr valign="top"  align="left">
 *  <td> <b> <code> selected </code> </b> <td> <code> JToggleButton </code>,<code> JCheckBox </code>,<code>
 *  JRadioButton </code> > JCheckBoxMenuItem </code>和<code> JRadioButtonMenuItem </code> <td> <code> SEL
 * ECTED_KEY </code> <td>只有使用{@code非null}时才使用的属性。
 * 例如,如果您在{@code JToggleButton}上设置{@code Action}在{@code JToggleButton}上的{@code SELECTED_KEY}具有{@code null}
 * 值,则{@code JToggleButton}不会以任何方式更新其选择状态。
 * 类似地,如果{@code Action}的{@code非null}值为{@code Action},{@code Action}中的所有状态都将更改为{@code Action} SELECTED_KE
 * Y}。
 * <br>
 * 尊重此属性的组件将使其所选状态与此属性保持同步。当对多个组件使用相同的{@code Action}时,所有组件都保持其选定状态与此属性同步。
 * 互斥按钮(例如{@code ButtonGroup}中的{@code JToggleButton})只强制选择其中一个按钮。
 * 因此,请不要使用{@code Action}为{@code SELECTED_KEY}属性定义多个互斥按钮的值。
 * </table>
 * <p>
 *  <code> JPopupMenu </code>,<code> JToolBar </code>和<code> JMenu </code>都提供了方便的方法来创建组件并在相应的组件上设置<code>
 * 
 * @author Georges Saab
 * @see AbstractAction
 */
public interface Action extends ActionListener {
    /**
     * Useful constants that can be used as the storage-retrieval key
     * when setting or getting one of this object's properties (text
     * or icon).
     * <p>
     *  Action </code>。
     * 有关更多信息,请参阅每个类。
     * <p>
     *  <code> Action </code>使用<code> PropertyChangeListener </code>来通知侦听器<code> Action </code>已更改。
     *  bean规范指示<code> null </code>属性名称可用于指示多个值已更改。默认情况下,采用<code> Action </code>的Swing组件不会处理这样的更改。
     * 为了指示Swing应该根据bean规范处理<code> null </code>,将系统属性<code> swing.actions.reconfigureOnNull </code>设置为<code>
     *  String </code> value <code> true </code>。
     *  bean规范指示<code> null </code>属性名称可用于指示多个值已更改。默认情况下,采用<code> Action </code>的Swing组件不会处理这样的更改。
     * 
     */
    /**
     * Not currently used.
     * <p>
     *  在设置或获取此对象的某个属性(文本或图标)时,可用作存储 - 检索键的有用常数。
     * 
     */
    public static final String DEFAULT = "Default";
    /**
     * The key used for storing the <code>String</code> name
     * for the action, used for a menu or button.
     * <p>
     *  目前未使用。
     * 
     */
    public static final String NAME = "Name";
    /**
     * The key used for storing a short <code>String</code>
     * description for the action, used for tooltip text.
     * <p>
     * 用于存储操作的<code> String </code>名称的键,用于菜单或按钮。
     * 
     */
    public static final String SHORT_DESCRIPTION = "ShortDescription";
    /**
     * The key used for storing a longer <code>String</code>
     * description for the action, could be used for context-sensitive help.
     * <p>
     *  用于存储操作的短<code> String </code>描述的键,用于工具提示文本。
     * 
     */
    public static final String LONG_DESCRIPTION = "LongDescription";
    /**
     * The key used for storing a small <code>Icon</code>, such
     * as <code>ImageIcon</code>.  This is typically used with
     * menus such as <code>JMenuItem</code>.
     * <p>
     * If the same <code>Action</code> is used with menus and buttons you'll
     * typically specify both a <code>SMALL_ICON</code> and a
     * <code>LARGE_ICON_KEY</code>.  The menu will use the
     * <code>SMALL_ICON</code> and the button will use the
     * <code>LARGE_ICON_KEY</code>.
     * <p>
     *  用于存储操作的更长的<code> String </code>描述的键可用于上下文相关帮助。
     * 
     */
    public static final String SMALL_ICON = "SmallIcon";

    /**
     * The key used to determine the command <code>String</code> for the
     * <code>ActionEvent</code> that will be created when an
     * <code>Action</code> is going to be notified as the result of
     * residing in a <code>Keymap</code> associated with a
     * <code>JComponent</code>.
     * <p>
     *  用于存储小<code> Icon </code>的键,例如<code> ImageIcon </code>。这通常用于诸如<code> JMenuItem </code>之类的菜单。
     * <p>
     *  如果对菜单和按钮使用相同的<code> Action </code>,则通常会同时指定<code> SMALL_ICON </code>和<code> LARGE_ICON_KEY </code>。
     * 菜单将使用<code> SMALL_ICON </code>,该按钮将使用<code> LARGE_ICON_KEY </code>。
     * 
     */
    public static final String ACTION_COMMAND_KEY = "ActionCommandKey";

    /**
     * The key used for storing a <code>KeyStroke</code> to be used as the
     * accelerator for the action.
     *
     * <p>
     *  用于确定<code> Action </code>的命令<code> String </code>的命令将会在<code> Action </code>被作为驻留结果通知时创建与<code> JCom
     * ponent </code>相关联的<code>键映射</code>。
     * 
     * 
     * @since 1.3
     */
    public static final String ACCELERATOR_KEY="AcceleratorKey";

    /**
     * The key used for storing an <code>Integer</code> that corresponds to
     * one of the <code>KeyEvent</code> key codes.  The value is
     * commonly used to specify a mnemonic.  For example:
     * <code>myAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A)</code>
     * sets the mnemonic of <code>myAction</code> to 'a', while
     * <code>myAction.putValue(Action.MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar('\u0444'))</code>
     * sets the mnemonic of <code>myAction</code> to Cyrillic letter "Ef".
     *
     * <p>
     *  用于存储要用作操作的加速器的<code> KeyStroke </code>的键。
     * 
     * 
     * @since 1.3
     */
    public static final String MNEMONIC_KEY="MnemonicKey";

    /**
     * The key used for storing a <code>Boolean</code> that corresponds
     * to the selected state.  This is typically used only for components
     * that have a meaningful selection state.  For example,
     * <code>JRadioButton</code> and <code>JCheckBox</code> make use of
     * this but instances of <code>JMenu</code> don't.
     * <p>
     * This property differs from the others in that it is both read
     * by the component and set by the component.  For example,
     * if an <code>Action</code> is attached to a <code>JCheckBox</code>
     * the selected state of the <code>JCheckBox</code> will be set from
     * that of the <code>Action</code>.  If the user clicks on the
     * <code>JCheckBox</code> the selected state of the <code>JCheckBox</code>
     * <b>and</b> the <code>Action</code> will <b>both</b> be updated.
     * <p>
     * Note: the value of this field is prefixed with 'Swing' to
     * avoid possible collisions with existing <code>Actions</code>.
     *
     * <p>
     * 用于存储对应于<code> KeyEvent </code>键代码之一的<code> Integer </code>的键。该值通常用于指定助记符。
     * 例如：<code> myAction.putValue(Action.MNEMONIC_KEY,KeyEvent.VK_A)</code>将<code> myAction </code>的助记符设置为'
     * a',而<code> myAction.putValue(Action.MNEMONIC_KEY ,KeyEvent.getExtendedKeyCodeForChar('\ u0444'))</code>
     * 将<code> myAction </code>的助记符设置为Cyrillic字母"Ef"。
     * 用于存储对应于<code> KeyEvent </code>键代码之一的<code> Integer </code>的键。该值通常用于指定助记符。
     * 
     * 
     * @since 1.6
     */
    public static final String SELECTED_KEY = "SwingSelectedKey";

    /**
     * The key used for storing an <code>Integer</code> that corresponds
     * to the index in the text (identified by the <code>NAME</code>
     * property) that the decoration for a mnemonic should be rendered at.  If
     * the value of this property is greater than or equal to the length of
     * the text, it will treated as -1.
     * <p>
     * Note: the value of this field is prefixed with 'Swing' to
     * avoid possible collisions with existing <code>Actions</code>.
     *
     * <p>
     *  用于存储对应于所选状态的<code> Boolean </code>的键。这通常仅用于具有有意义的选择状态的组件。
     * 例如,<code> JRadioButton </code>和<code> JCheckBox </code>使用这个,但是<code> JMenu </code>的实例不会。
     * <p>
     *  此属性与其他属性的不同之处在于,它由组件读取并由组件设置。
     * 例如,如果<code> Action </code>附加到<code> JCheckBox </code>,则<code> JCheckBox </code>的选定状态将从<code> / code>。
     *  此属性与其他属性的不同之处在于,它由组件读取并由组件设置。
     * 如果用户点击<code> JCheckBox </code> <code> JCheckBox </code> <b>和</b>的选择状态,<code> Action </code> / b>更新。
     * <p>
     *  注意：此字段的值以"Swing"作为前缀,以避免与现有<code>操作</code>发生可能的冲突。
     * 
     * 
     * @see AbstractButton#setDisplayedMnemonicIndex
     * @since 1.6
     */
    public static final String DISPLAYED_MNEMONIC_INDEX_KEY =
                                 "SwingDisplayedMnemonicIndexKey";

    /**
     * The key used for storing an <code>Icon</code>.  This is typically
     * used by buttons, such as <code>JButton</code> and
     * <code>JToggleButton</code>.
     * <p>
     * If the same <code>Action</code> is used with menus and buttons you'll
     * typically specify both a <code>SMALL_ICON</code> and a
     * <code>LARGE_ICON_KEY</code>.  The menu will use the
     * <code>SMALL_ICON</code> and the button the <code>LARGE_ICON_KEY</code>.
     * <p>
     * Note: the value of this field is prefixed with 'Swing' to
     * avoid possible collisions with existing <code>Actions</code>.
     *
     * <p>
     * 用于存储对应于文本(由<code> NAME </code>属性标识)中的索引的<code> Integer </code>的键用于存储助记符的装饰。
     * 如果此属性的值大于或等于文本的长度,它将被视为-1。
     * <p>
     *  注意：此字段的值以"Swing"作为前缀,以避免与现有<code>操作</code>发生可能的冲突。
     * 
     * 
     * @since 1.6
     */
    public static final String LARGE_ICON_KEY = "SwingLargeIconKey";

    /**
     * Gets one of this object's properties
     * using the associated key.
     * <p>
     *  用于存储<code> Icon </code>的键。这通常由按钮使用,例如<code> JButton </code>和<code> JToggleButton </code>。
     * <p>
     *  如果对菜单和按钮使用相同的<code> Action </code>,则通常会同时指定<code> SMALL_ICON </code>和<code> LARGE_ICON_KEY </code>。
     * 菜单将使用<code> SMALL_ICON </code>和<code> LARGE_ICON_KEY </code>按钮。
     * <p>
     *  注意：此字段的值以"Swing"作为前缀,以避免与现有<code>操作</code>发生可能的冲突。
     * 
     * 
     * @see #putValue
     */
    public Object getValue(String key);
    /**
     * Sets one of this object's properties
     * using the associated key. If the value has
     * changed, a <code>PropertyChangeEvent</code> is sent
     * to listeners.
     *
     * <p>
     *  使用关联键获取此对象的某个属性。
     * 
     * 
     * @param key    a <code>String</code> containing the key
     * @param value  an <code>Object</code> value
     */
    public void putValue(String key, Object value);

    /**
     * Sets the enabled state of the <code>Action</code>.  When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     * If the value has changed, a <code>PropertyChangeEvent</code> is sent
     * to listeners.
     *
     * <p>
     *  使用关联的键设置此对象的属性之一。如果值已更改,将向侦听器发送<code> PropertyChangeEvent </code>。
     * 
     * 
     * @param  b true to enable this <code>Action</code>, false to disable it
     */
    public void setEnabled(boolean b);
    /**
     * Returns the enabled state of the <code>Action</code>. When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     *
     * <p>
     *  设置<code> Action </code>的启用状态。启用时,与此对象相关联的任何组件都是活动的,并且能够触发此对象的<code> actionPerformed </code>方法。
     * 如果值已更改,将向侦听器发送<code> PropertyChangeEvent </code>。
     * 
     * 
     * @return true if this <code>Action</code> is enabled
     */
    public boolean isEnabled();

    /**
     * Adds a <code>PropertyChange</code> listener. Containers and attached
     * components use these methods to register interest in this
     * <code>Action</code> object. When its enabled state or other property
     * changes, the registered listeners are informed of the change.
     *
     * <p>
     * 返回<code> Action </code>的启用状态。启用时,与此对象相关联的任何组件都是活动的,并且能够触发此对象的<code> actionPerformed </code>方法。
     * 
     * 
     * @param listener  a <code>PropertyChangeListener</code> object
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Removes a <code>PropertyChange</code> listener.
     *
     * <p>
     *  添加一个<code> PropertyChange </code>侦听器。容器和附加组件使用这些方法来注册对此<code> Action </code>对象的兴趣。
     * 当其启用状态或其他属性改变时,向注册的侦听器通知该改变。
     * 
     * 
     * @param listener  a <code>PropertyChangeListener</code> object
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

}
