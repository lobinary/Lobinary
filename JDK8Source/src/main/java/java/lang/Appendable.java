/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;

/**
 * An object to which <tt>char</tt> sequences and values can be appended.  The
 * <tt>Appendable</tt> interface must be implemented by any class whose
 * instances are intended to receive formatted output from a {@link
 * java.util.Formatter}.
 *
 * <p> The characters to be appended should be valid Unicode characters as
 * described in <a href="Character.html#unicode">Unicode Character
 * Representation</a>.  Note that supplementary characters may be composed of
 * multiple 16-bit <tt>char</tt> values.
 *
 * <p> Appendables are not necessarily safe for multithreaded access.  Thread
 * safety is the responsibility of classes that extend and implement this
 * interface.
 *
 * <p> Since this interface may be implemented by existing classes
 * with different styles of error handling there is no guarantee that
 * errors will be propagated to the invoker.
 *
 * <p>
 *  可以附加<tt> char </tt>序列和值的对象。
 *  <tt> Appendable </tt>接口必须由任何类实现,其实例旨在接收{@link java.util.Formatter}的格式化输出。
 * 
 *  <p>要附加的字符应为有效的Unicode字符,如<a href="Character.html#unicode"> Unicode字符表示形式</a>中所述。
 * 请注意,补充字符可以由多个16位<tt> char </tt>值组成。
 * 
 *  <p>附件不一定对多线程访问安全。线程安全是扩展和实现此接口的类的职责。
 * 
 *  <p>由于这个接口可以通过具有不同风格的错误处理的现有类来实现,因此不能保证错误将被传播到调用者。
 * 
 * 
 * @since 1.5
 */
public interface Appendable {

    /**
     * Appends the specified character sequence to this <tt>Appendable</tt>.
     *
     * <p> Depending on which class implements the character sequence
     * <tt>csq</tt>, the entire sequence may not be appended.  For
     * instance, if <tt>csq</tt> is a {@link java.nio.CharBuffer} then
     * the subsequence to append is defined by the buffer's position and limit.
     *
     * <p>
     *  将指定的字符序列附加到此<tt>可附件</tt>。
     * 
     *  <p>根据哪个类实现字符序列<tt> csq </tt>,可能不会附加整个序列。
     * 例如,如果<tt> csq </tt>是{@link java.nio.CharBuffer},则追加的子序列由缓冲区的位置和限制定义。
     * 
     * 
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this Appendable.
     *
     * @return  A reference to this <tt>Appendable</tt>
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    Appendable append(CharSequence csq) throws IOException;

    /**
     * Appends a subsequence of the specified character sequence to this
     * <tt>Appendable</tt>.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.append(csq.subSequence(start, end)) </pre>
     *
     * <p>
     *  将指定字符序列的子序列附加到此<tt>可附件</tt>。
     * 
     * <p>当<tt> csq </tt>不是<tt> null </tt>时,对<tt> out.append(csq,start,end)</tt>形式的此方法的调用完全与调用相同的方式
     * 
     * <pre>
     * 
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  A reference to this <tt>Appendable</tt>
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    Appendable append(CharSequence csq, int start, int end) throws IOException;

    /**
     * Appends the specified character to this <tt>Appendable</tt>.
     *
     * <p>
     *  out.append(csq.subSequence(start,end))</pre>
     * 
     * 
     * @param  c
     *         The character to append
     *
     * @return  A reference to this <tt>Appendable</tt>
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    Appendable append(char c) throws IOException;
}
