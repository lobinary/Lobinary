/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.annotation;
import java.lang.reflect.Method;

/**
 * Thrown to indicate that a program has attempted to access an element of
 * an annotation whose type has changed after the annotation was compiled
 * (or serialized).
 * This exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * <p>
 *  抛出以指示程序试图访问注释在编译(或序列化)之后类型已更改的注释的元素。
 * 此异常可由{@linkplain java.lang.reflect.AnnotatedElement API用于反映读取注释}抛出。
 * 
 * 
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since 1.5
 */
public class AnnotationTypeMismatchException extends RuntimeException {
    private static final long serialVersionUID = 8125925355765570191L;

    /**
     * The <tt>Method</tt> object for the annotation element.
     * <p>
     *  注释元素的<tt>方法</tt>对象。
     * 
     */
    private final Method element;

    /**
     * The (erroneous) type of data found in the annotation.  This string
     * may, but is not required to, contain the value as well.  The exact
     * format of the string is unspecified.
     * <p>
     *  在注释中找到的(错误)数据类型。此字符串可以,但不是必需,也包含该值。字符串的确切格式未指定。
     * 
     */
    private final String foundType;

    /**
     * Constructs an AnnotationTypeMismatchException for the specified
     * annotation type element and found data type.
     *
     * <p>
     *  为指定的注记类型元素和找到的数据类型构造AnnotationTypeMismatchException。
     * 
     * 
     * @param element the <tt>Method</tt> object for the annotation element
     * @param foundType the (erroneous) type of data found in the annotation.
     *        This string may, but is not required to, contain the value
     *        as well.  The exact format of the string is unspecified.
     */
    public AnnotationTypeMismatchException(Method element, String foundType) {
        super("Incorrectly typed data found for annotation element " + element
              + " (Found data of type " + foundType + ")");
        this.element = element;
        this.foundType = foundType;
    }

    /**
     * Returns the <tt>Method</tt> object for the incorrectly typed element.
     *
     * <p>
     *  返回错误类型元素的<tt>方法</tt>对象。
     * 
     * 
     * @return the <tt>Method</tt> object for the incorrectly typed element
     */
    public Method element() {
        return this.element;
    }

    /**
     * Returns the type of data found in the incorrectly typed element.
     * The returned string may, but is not required to, contain the value
     * as well.  The exact format of the string is unspecified.
     *
     * <p>
     *  返回在类型不正确的元素中找到的数据类型。返回的字符串可以,但不一定要包含该值。字符串的确切格式未指定。
     * 
     * @return the type of data found in the incorrectly typed element
     */
    public String foundType() {
        return this.foundType;
    }
}
