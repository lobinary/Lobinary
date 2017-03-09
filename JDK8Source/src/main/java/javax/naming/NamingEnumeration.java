/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

import java.util.Enumeration;

/**
  * This interface is for enumerating lists returned by
  * methods in the javax.naming and javax.naming.directory packages.
  * It extends Enumeration to allow as exceptions to be thrown during
  * the enumeration.
  *<p>
  * When a method such as list(), listBindings(), or search() returns
  * a NamingEnumeration, any exceptions encountered are reserved until
  * all results have been returned. At the end of the enumeration, the
  * exception is thrown (by hasMore());
  * <p>
  * For example, if the list() is
  * returning only a partial answer, the corresponding exception would
  * be PartialResultException. list() would first return a NamingEnumeration.
  * When the last of the results has been returned by the NamingEnumeration's
  * next(), invoking hasMore() would result in PartialResultException being thrown.
  *<p>
  * In another example, if a search() method was invoked with a specified
  * size limit of 'n'. If the answer consists of more than 'n' results,
  * search() would first return a NamingEnumeration.
  * When the n'th result has been returned by invoking next() on the
  * NamingEnumeration, a SizeLimitExceedException would then thrown when
  * hasMore() is invoked.
  *<p>
  * Note that if the program uses hasMoreElements() and nextElement() instead
  * to iterate through the NamingEnumeration, because these methods
  * cannot throw exceptions, no exception will be thrown. Instead,
  * in the previous example, after the n'th result has been returned by
  * nextElement(), invoking hasMoreElements() would return false.
  *<p>
  * Note also that NoSuchElementException is thrown if the program invokes
  * next() or nextElement() when there are no elements left in the enumeration.
  * The program can always avoid this exception by using hasMore() and
  * hasMoreElements() to check whether the end of the enumeration has been reached.
  *<p>
  * If an exception is thrown during an enumeration,
  * the enumeration becomes invalid.
  * Subsequent invocation of any method on that enumeration
  * will yield undefined results.
  *
  * <p>
  *  此接口用于枚举javax.naming和javax.naming.directory包中的方法返回的列表。它扩展枚举允许在枚举期间抛出异常。
  * p>
  *  当诸如list(),listBindings()或search()的方法返回NamingEnumeration时,所有遇到的异常都保留,直到所有结果都返回。
  * 在枚举结束时,抛出异常(通过hasMore());。
  * <p>
  *  例如,如果list()只返回部分答案,则相应的异常将是PartialResultException。 list()首先返回一个NamingEnumeration。
  * 当最后的结果由NamingEnumeration的next()返回时,调用hasMore()将导致抛出PartialResultException。
  * p>
  *  在另一个示例中,如果search()方法被调用时指定的大小限制为'n'。如果答案包含多于n个结果,search()将首先返回一个NamingEnumeration。
  * 当通过调用NamingEnumeration上的next()返回第n个结果时,当调用hasMore()时会抛出SizeLimitExceedException。
  * p>
  * 注意,如果程序使用hasMoreElements()和nextElement()而不是遍历NamingEnumeration,因为这些方法不能抛出异常,不会抛出异常。
  * 相反,在前面的示例中,在nextElement()返回第n个结果后,调用hasMoreElements()将返回false。
  * p>
  *  还要注意,当枚举中没有元素时,如果程序调用next()或nextElement(),则抛出NoSuchElementException。
  * 程序可以通过使用hasMore()和hasMoreElements()来检查是否已达到枚举的结束,从而避免此异常。
  * p>
  *  如果在枚举期间抛出异常,枚举将无效。随后调用该枚举上的任何方法将产生未定义的结果。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Context#list
  * @see Context#listBindings
  * @see javax.naming.directory.DirContext#search
  * @see javax.naming.directory.Attributes#getAll
  * @see javax.naming.directory.Attributes#getIDs
  * @see javax.naming.directory.Attribute#getAll
  * @since 1.3
  */
public interface NamingEnumeration<T> extends Enumeration<T> {
    /**
      * Retrieves the next element in the enumeration.
      * This method allows naming exceptions encountered while
      * retrieving the next element to be caught and handled
      * by the application.
      * <p>
      * Note that <tt>next()</tt> can also throw the runtime exception
      * NoSuchElementException to indicate that the caller is
      * attempting to enumerate beyond the end of the enumeration.
      * This is different from a NamingException, which indicates
      * that there was a problem in obtaining the next element,
      * for example, due to a referral or server unavailability, etc.
      *
      * <p>
      *  检索枚举中的下一个元素。此方法允许命名在检索要由应用程序捕获和处理的下一个元素时遇到的异常。
      * <p>
      *  注意,<tt> next()</tt>也可以抛出运行时异常NoSuchElementException以指示调用者正在尝试枚举超出枚举结束。
      * 这与NamingException不同,NamingException表示在获取下一个元素时出现问题,例如由于引用或服务器不可用等原因。
      * 
      * 
      * @return         The possibly null element in the enumeration.
      *     null is only valid for enumerations that can return
      *     null (e.g. Attribute.getAll() returns an enumeration of
      *     attribute values, and an attribute value can be null).
      * @exception NamingException If a naming exception is encountered while attempting
      *                 to retrieve the next element. See NamingException
      *                 and its subclasses for the possible naming exceptions.
      * @exception java.util.NoSuchElementException If attempting to get the next element when none is available.
      * @see java.util.Enumeration#nextElement
      */
    public T next() throws NamingException;

    /**
      * Determines whether there are any more elements in the enumeration.
      * This method allows naming exceptions encountered while
      * determining whether there are more elements to be caught and handled
      * by the application.
      *
      * <p>
      * 确定枚举中是否还有其他元素。此方法允许在确定是否有更多元素被应用程序捕获和处理时遇到的命名异常。
      * 
      * 
      * @return         true if there is more in the enumeration ; false otherwise.
      * @exception NamingException
      *                 If a naming exception is encountered while attempting
      *                 to determine whether there is another element
      *                 in the enumeration. See NamingException
      *                 and its subclasses for the possible naming exceptions.
      * @see java.util.Enumeration#hasMoreElements
      */
    public boolean hasMore() throws NamingException;

    /**
     * Closes this enumeration.
     *
     * After this method has been invoked on this enumeration, the
     * enumeration becomes invalid and subsequent invocation of any of
     * its methods will yield undefined results.
     * This method is intended for aborting an enumeration to free up resources.
     * If an enumeration proceeds to the end--that is, until
     * <tt>hasMoreElements()</tt> or <tt>hasMore()</tt> returns <tt>false</tt>--
     * resources will be freed up automatically and there is no need to
     * explicitly call <tt>close()</tt>.
     *<p>
     * This method indicates to the service provider that it is free
     * to release resources associated with the enumeration, and can
     * notify servers to cancel any outstanding requests. The <tt>close()</tt>
     * method is a hint to implementations for managing their resources.
     * Implementations are encouraged to use appropriate algorithms to
     * manage their resources when client omits the <tt>close()</tt> calls.
     *
     * <p>
     *  关闭此枚举。
     * 
     *  在此枚举上调用此方法后,枚举将无效,随后对其任何方法的调用将产生未定义的结果。此方法用于中止枚举以释放资源。
     * 如果枚举进行到结束 - 即,直到<tt> hasMoreElements()</tt>或<tt> hasMore()</tt>返回<tt> false </tt>  - 资源将被释放并且不需要显式调用<tt>
     *  close()</tt>。
     * 
     * @exception NamingException If a naming exception is encountered
     * while closing the enumeration.
     * @since 1.3
     */
    public void close() throws NamingException;
}
