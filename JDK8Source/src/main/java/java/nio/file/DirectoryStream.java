/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

import java.util.Iterator;
import java.io.Closeable;
import java.io.IOException;

/**
 * An object to iterate over the entries in a directory. A directory stream
 * allows for the convenient use of the for-each construct to iterate over a
 * directory.
 *
 * <p> <b> While {@code DirectoryStream} extends {@code Iterable}, it is not a
 * general-purpose {@code Iterable} as it supports only a single {@code
 * Iterator}; invoking the {@link #iterator iterator} method to obtain a second
 * or subsequent iterator throws {@code IllegalStateException}. </b>
 *
 * <p> An important property of the directory stream's {@code Iterator} is that
 * its {@link Iterator#hasNext() hasNext} method is guaranteed to read-ahead by
 * at least one element. If {@code hasNext} method returns {@code true}, and is
 * followed by a call to the {@code next} method, it is guaranteed that the
 * {@code next} method will not throw an exception due to an I/O error, or
 * because the stream has been {@link #close closed}. The {@code Iterator} does
 * not support the {@link Iterator#remove remove} operation.
 *
 * <p> A {@code DirectoryStream} is opened upon creation and is closed by
 * invoking the {@code close} method. Closing a directory stream releases any
 * resources associated with the stream. Failure to close the stream may result
 * in a resource leak. The try-with-resources statement provides a useful
 * construct to ensure that the stream is closed:
 * <pre>
 *   Path dir = ...
 *   try (DirectoryStream&lt;Path&gt; stream = Files.newDirectoryStream(dir)) {
 *       for (Path entry: stream) {
 *           ...
 *       }
 *   }
 * </pre>
 *
 * <p> Once a directory stream is closed, then further access to the directory,
 * using the {@code Iterator}, behaves as if the end of stream has been reached.
 * Due to read-ahead, the {@code Iterator} may return one or more elements
 * after the directory stream has been closed. Once these buffered elements
 * have been read, then subsequent calls to the {@code hasNext} method returns
 * {@code false}, and subsequent calls to the {@code next} method will throw
 * {@code NoSuchElementException}.
 *
 * <p> A directory stream is not required to be <i>asynchronously closeable</i>.
 * If a thread is blocked on the directory stream's iterator reading from the
 * directory, and another thread invokes the {@code close} method, then the
 * second thread may block until the read operation is complete.
 *
 * <p> If an I/O error is encountered when accessing the directory then it
 * causes the {@code Iterator}'s {@code hasNext} or {@code next} methods to
 * throw {@link DirectoryIteratorException} with the {@link IOException} as the
 * cause. As stated above, the {@code hasNext} method is guaranteed to
 * read-ahead by at least one element. This means that if {@code hasNext} method
 * returns {@code true}, and is followed by a call to the {@code next} method,
 * then it is guaranteed that the {@code next} method will not fail with a
 * {@code DirectoryIteratorException}.
 *
 * <p> The elements returned by the iterator are in no specific order. Some file
 * systems maintain special links to the directory itself and the directory's
 * parent directory. Entries representing these links are not returned by the
 * iterator.
 *
 * <p> The iterator is <i>weakly consistent</i>. It is thread safe but does not
 * freeze the directory while iterating, so it may (or may not) reflect updates
 * to the directory that occur after the {@code DirectoryStream} is created.
 *
 * <p> <b>Usage Examples:</b>
 * Suppose we want a list of the source files in a directory. This example uses
 * both the for-each and try-with-resources constructs.
 * <pre>
 *   List&lt;Path&gt; listSourceFiles(Path dir) throws IOException {
 *       List&lt;Path&gt; result = new ArrayList&lt;&gt;();
 *       try (DirectoryStream&lt;Path&gt; stream = Files.newDirectoryStream(dir, "*.{c,h,cpp,hpp,java}")) {
 *           for (Path entry: stream) {
 *               result.add(entry);
 *           }
 *       } catch (DirectoryIteratorException ex) {
 *           // I/O error encounted during the iteration, the cause is an IOException
 *           throw ex.getCause();
 *       }
 *       return result;
 *   }
 * </pre>
 * <p>
 *  要在目录中的条目上进行迭代的对象。目录流允许方便地使用for-each构造来遍历目录。
 * 
 *  <p> <b>虽然{@code DirectoryStream}扩展了{@code Iterable},但它不是通用的{@code Iterable},因为它仅支持一个{@code Iterator}
 * ;调用{@link #iterator iterator}方法获取第二个或后续的迭代器throws {@code IllegalStateException}。
 *  </b>。
 * 
 *  <p>目录流{@code Iterator}的一个重要属性是它的{@link迭代器#hasNext()hasNext}方法保证至少有一个元素预读。
 * 如果{@code hasNext}方法返回{@code true},并且随后调用{@code next}方法,那么保证{@code next}方法不会因I / O错误,或者因为该流已{@link #close closed}
 * 。
 *  <p>目录流{@code Iterator}的一个重要属性是它的{@link迭代器#hasNext()hasNext}方法保证至少有一个元素预读。
 *  {@code Iterator}不支持{@link Iterator#remove remove}操作。
 * 
 *  <p> {@code DirectoryStream}在创建时打开,并通过调用{@code close}方法关闭。关闭目录流释放与流相关联的任何资源。无法关闭流可能会导致资源泄露。
 *  try-with-resources语句提供了一个有用的结构,以确保流被关闭：。
 * <pre>
 * 路径dir = ... try(DirectoryStream&lt; Path&gt; stream = Files.newDirectoryStream(dir)){for(Path entry：stream){...}
 * 。
 * </pre>
 * 
 *  <p>关闭目录流之后,使用{@code Iterator}进一步访问目录的行为就好像已经到达流的结尾。由于预读,{@code迭代器}可能在目录流关闭后返回一个或多个元素。
 * 一旦读取了这些缓冲的元素,则对{@code hasNext}方法的后续调用返回{@code false},随后对{@code next}方法的调用将抛出{@code NoSuchElementException}
 * 。
 *  <p>关闭目录流之后,使用{@code Iterator}进一步访问目录的行为就好像已经到达流的结尾。由于预读,{@code迭代器}可能在目录流关闭后返回一个或多个元素。
 * 
 *  <p>目录流不需要<i>异步关闭</i>。如果线程被阻塞在目录流的迭代器从目录读取,并且另一个线程调用{@code close}方法,则第二个线程可能阻塞,直到读取操作完成。
 * 
 *  <p>如果在访问目录时遇到I / O错误,则会导致{@code Iterator}的{@code hasNext}或{@code next}方法将{@link DirectoryIteratorException}
 * 与{@link IOException}为原因。
 * 
 * @param   <T>     The type of element returned by the iterator
 *
 * @since 1.7
 *
 * @see Files#newDirectoryStream(Path)
 */

public interface DirectoryStream<T>
    extends Closeable, Iterable<T> {
    /**
     * An interface that is implemented by objects that decide if a directory
     * entry should be accepted or filtered. A {@code Filter} is passed as the
     * parameter to the {@link Files#newDirectoryStream(Path,DirectoryStream.Filter)}
     * method when opening a directory to iterate over the entries in the
     * directory.
     *
     * <p>
     * 如上所述,{@code hasNext}方法保证由至少一个元素进行预读。
     * 这意味着如果{@code hasNext}方法返回{@code true},然后调用{@code next}方法,那么保证{@code next}方法不会失败,并且{ @code DirectoryIteratorException}
     * 。
     * 如上所述,{@code hasNext}方法保证由至少一个元素进行预读。
     * 
     * <p>迭代器返回的元素没有特定的顺序。一些文件系统保留到目录本身和目录的父目录的特殊链接。表示这些链接的条目不由迭代器返回。
     * 
     *  <p>迭代器<i>弱一致</i>。它是线程安全的,但在迭代时不会冻结目录,因此它可能(或可能不)反映在创建{@code DirectoryStream}之后发生的目录更新。
     * 
     *  <p> <b>使用示例：</b>假设我们想要一个目录中的源文件列表。此示例使用for-each和try-with-resources结构。
     * <pre>
     *  List&lt; Path&gt; listSourceFiles(Path dir)throws IOException {List&lt; Path&gt; result = new ArrayList&lt;&gt;(); try(DirectoryStream&lt; Path&gt; stream = Files.newDirectoryStream(dir,"*。
     * {c,h,cpp,hpp,java}")){for(Path entry：stream){result.add(entry); }} catch(DirectoryIteratorException e
     * x){//迭代过程中遇到的I / O错误,原因是一个IOException抛出ex.getCause(); } return result; }}。
     * 
     * @param   <T>     the type of the directory entry
     *
     * @since 1.7
     */
    @FunctionalInterface
    public static interface Filter<T> {
        /**
         * Decides if the given directory entry should be accepted or filtered.
         *
         * <p>
         * </pre>
         * 
         * @param   entry
         *          the directory entry to be tested
         *
         * @return  {@code true} if the directory entry should be accepted
         *
         * @throws  IOException
         *          If an I/O error occurs
         */
        boolean accept(T entry) throws IOException;
    }

    /**
     * Returns the iterator associated with this {@code DirectoryStream}.
     *
     * <p>
     *  由确定是否接受或过滤目录条目的对象实现的接口。
     * 打开目录以遍历目录中的条目时,会将{@code Filter}作为参数传递到{@link Files#newDirectoryStream(Path,DirectoryStream.Filter)}方法
     * 。
     *  由确定是否接受或过滤目录条目的对象实现的接口。
     * 
     * 
     * @return  the iterator associated with this {@code DirectoryStream}
     *
     * @throws  IllegalStateException
     *          if this directory stream is closed or the iterator has already
     *          been returned
     */
    @Override
    Iterator<T> iterator();
}
