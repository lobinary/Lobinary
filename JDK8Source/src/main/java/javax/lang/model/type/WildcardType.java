/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.type;


/**
 * Represents a wildcard type argument.
 * Examples include:    <pre><tt>
 *   ?
 *   ? extends Number
 *   ? super T
 * </tt></pre>
 *
 * <p> A wildcard may have its upper bound explicitly set by an
 * {@code extends} clause, its lower bound explicitly set by a
 * {@code super} clause, or neither (but not both).
 *
 * <p>
 *  表示通配符类型参数。示例包括：<pre> <tt>? ? extends Number?超级T </tt> </pre>
 * 
 *  <p>通配符的上限可以由{@code extends}子句显式设置,其下限由{@code super}子句显式设置,或者两者都不显式设置。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface WildcardType extends TypeMirror {

    /**
     * Returns the upper bound of this wildcard.
     * If no upper bound is explicitly declared,
     * {@code null} is returned.
     *
     * <p>
     *  返回此通配符的上限。如果没有明确声明上界,则返回{@code null}。
     * 
     * 
     * @return the upper bound of this wildcard
     */
    TypeMirror getExtendsBound();

    /**
     * Returns the lower bound of this wildcard.
     * If no lower bound is explicitly declared,
     * {@code null} is returned.
     *
     * <p>
     *  返回此通配符的下限。如果没有明确声明下界,则返回{@code null}。
     * 
     * @return the lower bound of this wildcard
     */
    TypeMirror getSuperBound();
}
