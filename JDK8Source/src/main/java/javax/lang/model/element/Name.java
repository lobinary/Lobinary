/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An immutable sequence of characters.  When created by the same
 * implementation, objects implementing this interface must obey the
 * general {@linkplain Object#equals equals contract} when compared
 * with each other.  Therefore, {@code Name} objects from the same
 * implementation are usable in collections while {@code Name}s from
 * different implementations may not work properly in collections.
 *
 * <p>An empty {@code Name} has a length of zero.
 *
 * <p>In the context of {@linkplain
 * javax.annotation.processing.ProcessingEnvironment annotation
 * processing}, the guarantees for "the same" implementation must
 * include contexts where the {@linkplain javax.annotation.processing
 * API mediated} side effects of {@linkplain
 * javax.annotation.processing.Processor processors} could be visible
 * to each other, including successive annotation processing
 * {@linkplain javax.annotation.processing.RoundEnvironment rounds}.
 *
 * <p>
 *  不可变的字符序列。当由相同的实现创建时,实现此接口的对象在彼此比较时必须遵守一般的{@linkplain Object#equals equals contract}。
 * 因此,来自同一实现的{@code Name}对象在集合中可用,而来自不同实现的{@code Name}可能无法在集合中正常工作。
 * 
 *  <p>空白{@code Name}的长度为零。
 * 
 *  <p>在{@linkplain javax.annotation.processing.ProcessingEnvironment注释处理}的上下文中,"同一个"实现的保证必须包括{@linkplain javax.annotation.processing API介导的}
 *  {@ linkplain javax.annotation.processing.Processor processor}可以彼此可见,包括连续注释处理{@linkplain javax.annotation.processing.RoundEnvironment rounds}
 * 。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see javax.lang.model.util.Elements#getName
 * @since 1.6
 */
public interface Name extends CharSequence {
    /**
     * Returns {@code true} if the argument represents the same
     * name as {@code this}, and {@code false} otherwise.
     *
     * <p>Note that the identity of a {@code Name} is a function both
     * of its content in terms of a sequence of characters as well as
     * the implementation which created it.
     *
     * <p>
     *  如果参数表示与{@code this}相同的名称,则返回{@code true},否则返回{@code false}。
     * 
     *  <p>请注意,{@code Name}的身份是其字符序列的内容以及创建它的实现的函数。
     * 
     * 
     * @param obj  the object to be compared with this element
     * @return {@code true} if the specified object represents the same
     *          name as this
     * @see Element#equals
     */
    boolean equals(Object obj);

    /**
     * Obeys the general contract of {@link Object#hashCode Object.hashCode}.
     *
     * <p>
     *  遵守{@link Object#hashCode Object.hashCode}的一般合同。
     * 
     * 
     * @see #equals
     */
    int hashCode();

    /**
     * Compares this name to the specified {@code CharSequence}. The result
     * is {@code true} if and only if this name represents the same sequence
     * of {@code char} values as the specified sequence.
     *
     * <p>
     * 将此名称与指定的{@code CharSequence}进行比较。如果且仅当此名称表示与指定序列相同的{@code char}值序列时,结果为{@code true}。
     * 
     * @return {@code true} if this name represents the same sequence
     * of {@code char} values as the specified sequence, {@code false}
     * otherwise
     *
     * @param cs The sequence to compare this name against
     * @see String#contentEquals(CharSequence)
     */
    boolean contentEquals(CharSequence cs);
}
