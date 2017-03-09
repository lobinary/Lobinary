/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

/**
 * A PropertyEditor class provides support for GUIs that want to
 * allow users to edit a property value of a given type.
 * <p>
 * PropertyEditor supports a variety of different kinds of ways of
 * displaying and updating property values.  Most PropertyEditors will
 * only need to support a subset of the different options available in
 * this API.
 * <P>
 * Simple PropertyEditors may only support the getAsText and setAsText
 * methods and need not support (say) paintValue or getCustomEditor.  More
 * complex types may be unable to support getAsText and setAsText but will
 * instead support paintValue and getCustomEditor.
 * <p>
 * Every propertyEditor must support one or more of the three simple
 * display styles.  Thus it can either (1) support isPaintable or (2)
 * both return a non-null String[] from getTags() and return a non-null
 * value from getAsText or (3) simply return a non-null String from
 * getAsText().
 * <p>
 * Every property editor must support a call on setValue when the argument
 * object is of the type for which this is the corresponding propertyEditor.
 * In addition, each property editor must either support a custom editor,
 * or support setAsText.
 * <p>
 * Each PropertyEditor should have a null constructor.
 * <p>
 *  PropertyEditor类提供对希望允许用户编辑给定类型的属性值的GUI的支持。
 * <p>
 *  PropertyEditor支持各种不同类型的显示和更新属性值的方式。大多数PropertyEditor只需要支持此API中提供的不同选项的子集。
 * <P>
 *  简单的PropertyEditor只能支持getAsText和setAsText方法,不需要支持(比如说)paintValue或getCustomEditor。
 * 更复杂的类型可能无法支持getAsText和setAsText,但会支持paintValue和getCustomEditor。
 * <p>
 *  每个propertyEditor必须支持三个简单显示样式中的一个或多个。
 * 因此,它可以(1)支持isPaintable或(2)从getTags()返回一个非空的String [],并从getAsText返回非空值或(3)从getAsText()返回一个非空字符串, 。
 * <p>
 *  当参数对象是相应的propertyEditor类型时,每个属性编辑器都必须支持对setValue的调用。此外,每个属性编辑器必须支持自定义编辑器,或支持setAsText。
 * <p>
 *  每个PropertyEditor都应该有一个null构造函数。
 * 
 */

public interface PropertyEditor {

    /**
     * Set (or change) the object that is to be edited.  Primitive types such
     * as "int" must be wrapped as the corresponding object type such as
     * "java.lang.Integer".
     *
     * <p>
     *  设置(或更改)要编辑的对象。原始类型如"int"必须包装为相应的对象类型,如"java.lang.Integer"。
     * 
     * 
     * @param value The new target object to be edited.  Note that this
     *     object should not be modified by the PropertyEditor, rather
     *     the PropertyEditor should create a new object to hold any
     *     modified value.
     */
    void setValue(Object value);

    /**
     * Gets the property value.
     *
     * <p>
     *  获取属性值。
     * 
     * 
     * @return The value of the property.  Primitive types such as "int" will
     * be wrapped as the corresponding object type such as "java.lang.Integer".
     */

    Object getValue();

    //----------------------------------------------------------------------

    /**
     * Determines whether this property editor is paintable.
     *
     * <p>
     * 确定此属性编辑器是否可绘制。
     * 
     * 
     * @return  True if the class will honor the paintValue method.
     */

    boolean isPaintable();

    /**
     * Paint a representation of the value into a given area of screen
     * real estate.  Note that the propertyEditor is responsible for doing
     * its own clipping so that it fits into the given rectangle.
     * <p>
     * If the PropertyEditor doesn't honor paint requests (see isPaintable)
     * this method should be a silent noop.
     * <p>
     * The given Graphics object will have the default font, color, etc of
     * the parent container.  The PropertyEditor may change graphics attributes
     * such as font and color and doesn't need to restore the old values.
     *
     * <p>
     *  将值的表示绘制到屏幕空间的给定区域中。注意,propertyEditor负责进行自己的裁剪,使其适合给定的矩形。
     * <p>
     *  如果PropertyEditor不符合绘制请求(见isPaintable),这个方法应该是一个静默的noop。
     * <p>
     *  给定的Graphics对象将具有父容器的默认字体,颜色等。 PropertyEditor可以更改图形属性,如字体和颜色,并且不需要恢复旧值。
     * 
     * 
     * @param gfx  Graphics object to paint into.
     * @param box  Rectangle within graphics object into which we should paint.
     */
    void paintValue(java.awt.Graphics gfx, java.awt.Rectangle box);

    //----------------------------------------------------------------------

    /**
     * Returns a fragment of Java code that can be used to set a property
     * to match the editors current state. This method is intended
     * for use when generating Java code to reflect changes made through the
     * property editor.
     * <p>
     * The code fragment should be context free and must be a legal Java
     * expression as specified by the JLS.
     * <p>
     * Specifically, if the expression represents a computation then all
     * classes and static members should be fully qualified. This rule
     * applies to constructors, static methods and non primitive arguments.
     * <p>
     * Caution should be used when evaluating the expression as it may throw
     * exceptions. In particular, code generators must ensure that generated
     * code will compile in the presence of an expression that can throw
     * checked exceptions.
     * <p>
     * Example results are:
     * <ul>
     * <li>Primitive expresssion: <code>2</code>
     * <li>Class constructor: <code>new java.awt.Color(127,127,34)</code>
     * <li>Static field: <code>java.awt.Color.orange</code>
     * <li>Static method: <code>javax.swing.Box.createRigidArea(new
     *                                   java.awt.Dimension(0, 5))</code>
     * </ul>
     *
     * <p>
     *  返回一个Java代码片段,可用于设置一个属性以匹配editors当前状态。此方法适用于生成Java代码以反映通过属性编辑器所做的更改。
     * <p>
     *  代码片段应该是上下文无关的,并且必须是JLS指定的合法Java表达式。
     * <p>
     *  特别地,如果表达式表示计算,则所有类和静态成员应该是完全限定的。此规则适用于构造函数,静态方法和非原始参数。
     * <p>
     *  在计算表达式时应谨慎使用,因为它可能会抛出异常。特别是,代码生成器必须确保生成的代码在存在可以引发检查异常的表达式的情况下进行编译。
     * <p>
     *  示例结果是：
     * <ul>
     * <li>基本表达：<code> 2 </code> <li>类构造函数：<code> new java.awt.Color(127,127,34)</code> <li>静态字段：<code> java
     * .awt .Color.orange </code> <li>静态方法：<code> javax.swing.Box.createRigidArea(new java.awt.Dimension(0,5
     * ))</code>。
     * </ul>
     * 
     * 
     * @return a fragment of Java code representing an initializer for the
     *         current value. It should not contain a semi-colon
     *         ('<code>;</code>') to end the expression.
     */
    String getJavaInitializationString();

    //----------------------------------------------------------------------

    /**
     * Gets the property value as text.
     *
     * <p>
     *  将属性值作为文本获取。
     * 
     * 
     * @return The property value as a human editable string.
     * <p>   Returns null if the value can't be expressed as an editable string.
     * <p>   If a non-null value is returned, then the PropertyEditor should
     *       be prepared to parse that string back in setAsText().
     */
    String getAsText();

    /**
     * Set the property value by parsing a given String.  May raise
     * java.lang.IllegalArgumentException if either the String is
     * badly formatted or if this kind of property can't be expressed
     * as text.
     * <p>
     *  通过解析给定的字符串来设置属性值。如果字符串格式不正确或者此类属性不能表示为文本,则可能引发java.lang.IllegalArgumentException。
     * 
     * 
     * @param text  The string to be parsed.
     */
    void setAsText(String text) throws java.lang.IllegalArgumentException;

    //----------------------------------------------------------------------

    /**
     * If the property value must be one of a set of known tagged values,
     * then this method should return an array of the tags.  This can
     * be used to represent (for example) enum values.  If a PropertyEditor
     * supports tags, then it should support the use of setAsText with
     * a tag value as a way of setting the value and the use of getAsText
     * to identify the current value.
     *
     * <p>
     *  如果属性值必须是一组已知标记值中的一个,则此方法应返回一个标记数组。这可以用于表示(例如)枚举值。
     * 如果PropertyEditor支持标签,那么它应该支持使用带有标签值的setAsText作为设置值的方法,并使用getAsText来标识当前值。
     * 
     * 
     * @return The tag values for this property.  May be null if this
     *   property cannot be represented as a tagged value.
     *
     */
    String[] getTags();

    //----------------------------------------------------------------------

    /**
     * A PropertyEditor may choose to make available a full custom Component
     * that edits its property value.  It is the responsibility of the
     * PropertyEditor to hook itself up to its editor Component itself and
     * to report property value changes by firing a PropertyChange event.
     * <P>
     * The higher-level code that calls getCustomEditor may either embed
     * the Component in some larger property sheet, or it may put it in
     * its own individual dialog, or ...
     *
     * <p>
     *  PropertyEditor可以选择提供编辑其属性值的完全自定义组件。 PropertyEditor负责将自己挂钩到其编辑器组件本身,并通过触发PropertyChange事件来报告属性值更改。
     * <P>
     *  调用getCustomEditor的高级代码可以将组件嵌入到一些较大的属性表中,也可以将其放在单独的对话框中,或者...
     * 
     * 
     * @return A java.awt.Component that will allow a human to directly
     *      edit the current property value.  May be null if this is
     *      not supported.
     */

    java.awt.Component getCustomEditor();

    /**
     * Determines whether this property editor supports a custom editor.
     *
     * <p>
     *  确定此属性编辑器是否支持自定义编辑器。
     * 
     * 
     * @return  True if the propertyEditor can provide a custom editor.
     */
    boolean supportsCustomEditor();

    //----------------------------------------------------------------------

    /**
     * Adds a listener for the value change.
     * When the property editor changes its value
     * it should fire a {@link PropertyChangeEvent}
     * on all registered {@link PropertyChangeListener}s,
     * specifying the {@code null} value for the property name
     * and itself as the source.
     *
     * <p>
     * 为值更改添加侦听器。
     * 当属性编辑器更改其值时,应在所有注册的{@link PropertyChangeListener}上触发{@link PropertyChangeEvent},指定属性名称的{@code null}值,
     * 并将其自身作为源。
     * 为值更改添加侦听器。
     * 
     * @param listener  the {@link PropertyChangeListener} to add
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a listener for the value change.
     *
     * <p>
     * 
     * 
     * @param listener  the {@link PropertyChangeListener} to remove
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}
