/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown when an application tries to access an enum constant by name
 * and the enum type contains no constant with the specified name.
 * This exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * <p>
 *  当应用程序试图通过名称访问枚举常量并且枚举类型不包含具有指定名称的常量时抛出。
 * 此异常可由{@linkplain java.lang.reflect.AnnotatedElement API用于反映读取注释}抛出。
 * 
 * 
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since   1.5
 */
@SuppressWarnings("rawtypes") /* rawtypes are part of the public api */
public class EnumConstantNotPresentException extends RuntimeException {
    private static final long serialVersionUID = -6046998521960521108L;

    /**
     * The type of the missing enum constant.
     * <p>
     *  缺少的枚举常量的类型。
     * 
     */
    private Class<? extends Enum> enumType;

    /**
     * The name of the missing enum constant.
     * <p>
     *  缺少的枚举常量的名称。
     * 
     */
    private String constantName;

    /**
     * Constructs an <tt>EnumConstantNotPresentException</tt> for the
     * specified constant.
     *
     * <p>
     *  为指定的常量构造一个<tt> EnumConstantNotPresentException </tt>。
     * 
     * 
     * @param enumType the type of the missing enum constant
     * @param constantName the name of the missing enum constant
     */
    public EnumConstantNotPresentException(Class<? extends Enum> enumType,
                                           String constantName) {
        super(enumType.getName() + "." + constantName);
        this.enumType = enumType;
        this.constantName  = constantName;
    }

    /**
     * Returns the type of the missing enum constant.
     *
     * <p>
     *  返回缺少的枚举常量的类型。
     * 
     * 
     * @return the type of the missing enum constant
     */
    public Class<? extends Enum> enumType() { return enumType; }

    /**
     * Returns the name of the missing enum constant.
     *
     * <p>
     *  返回缺少的枚举常量的名称。
     * 
     * @return the name of the missing enum constant
     */
    public String constantName() { return constantName; }
}
