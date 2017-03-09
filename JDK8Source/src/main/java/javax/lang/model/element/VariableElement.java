/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.element;

import javax.lang.model.util.Elements;

/**
 * Represents a field, {@code enum} constant, method or constructor
 * parameter, local variable, resource variable, or exception
 * parameter.
 *
 * <p>
 *  表示字段,{@code enum}常量,方法或构造函数参数,局部变量,资源变量或异常参数。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface VariableElement extends Element {

    /**
     * Returns the value of this variable if this is a {@code final}
     * field initialized to a compile-time constant.  Returns {@code
     * null} otherwise.  The value will be of a primitive type or a
     * {@code String}.  If the value is of a primitive type, it is
     * wrapped in the appropriate wrapper class (such as {@link
     * Integer}).
     *
     * <p>Note that not all {@code final} fields will have
     * constant values.  In particular, {@code enum} constants are
     * <em>not</em> considered to be compile-time constants.  To have a
     * constant value, a field's type must be either a primitive type
     * or {@code String}.
     *
     * <p>
     *  如果这是一个初始化为编译时常量的{@code final}字段,则返回此变量的值。否则返回{@code null}。该值将是原始类型或{@code String}。
     * 如果值是原始类型,则将其包装在适当的包装类中(例如{@link Integer})。
     * 
     *  <p>请注意,并非所有{@code final}字段都具有常数值。特别是,{@code enum}常数不是</em>被认为是编译时常量。
     * 要具有常量值,字段的类型必须是原始类型或{@code String}。
     * 
     * 
     * @return the value of this variable if this is a {@code final}
     * field initialized to a compile-time constant, or {@code null}
     * otherwise
     *
     * @see Elements#getConstantExpression(Object)
     * @jls 15.28 Constant Expression
     * @jls 4.12.4 final Variables
     */
    Object getConstantValue();

    /**
     * Returns the simple name of this variable element.
     *
     * <p>For method and constructor parameters, the name of each
     * parameter must be distinct from the names of all other
     * parameters of the same executable.  If the original source
     * names are not available, an implementation may synthesize names
     * subject to the distinctness requirement above.
     *
     * <p>
     *  返回此变量元素的简单名称。
     * 
     *  <p>对于方法和构造函数参数,每个参数的名称必须与同一可执行文件的所有其他参数的名称不同。如果原始源名称不可用,则实现可以综合具有上述区别性要求的名称。
     * 
     * 
     * @return the simple name of this variable element
     */
    @Override
    Name getSimpleName();

    /**
     * Returns the enclosing element of this variable.
     *
     * The enclosing element of a method or constructor parameter is
     * the executable declaring the parameter.
     *
     * <p>
     *  返回此变量的包围元素。
     * 
     * 
     * @return the enclosing element of this variable
     */
    @Override
    Element getEnclosingElement();
}
