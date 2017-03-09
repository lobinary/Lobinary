/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.IOException;

/**
 * Interface MultiDoc specifies the interface for an object that supplies more
 * than one piece of print data for a Print Job. "Doc" is a short,
 * easy-to-pronounce term that means "a piece of print data," and a "multidoc"
 * is a group of several docs. The client passes to the Print Job an object
 * that implements interface MultiDoc, and the Print Job calls methods on
 *  that object to obtain the print data.
 * <P>
 * Interface MultiDoc provides an abstraction similar to a "linked list" of
 * docs. A multidoc object is like a node in the linked list, containing the
 * current doc in the list and a pointer to the next node (multidoc) in the
 * list. The Print Job can call the multidoc's {@link #getDoc()
 * getDoc()} method to get the current doc. When it's ready to go
 * on to the next doc, the Print Job can call the multidoc's {@link #next()
 * next()} method to get the next multidoc, which contains the
 * next doc. So Print Job code for accessing a multidoc might look like this:
 * <PRE>
 *      void processMultiDoc(MultiDoc theMultiDoc) {
 *
 *          MultiDoc current = theMultiDoc;

 *          while (current != null) {
 *              processDoc (current.getDoc());
 *              current = current.next();
 *          }
 *      }
 * </PRE>
 * <P>
 * Of course, interface MultiDoc can be implemented in any way that fulfills
 * the contract; it doesn't have to use a linked list in the implementation.
 * <P>
 * To get all the print data for a multidoc print job, a Print Service
 * proxy could use either of two patterns:
 * <OL TYPE=1>
 * <LI>
 * The <B>interleaved</B> pattern: Get the doc from the current multidoc. Get
 * the print data representation object from the current doc. Get all the print
 * data from the print data representation object. Get the next multidoc from
 * the current multidoc, and repeat until there are no more. (The code example
 * above uses the interleaved pattern.)
 * <P>
 * <LI>
 * The <B>all-at-once</B> pattern: Get the doc from the current multidoc, and
 * save the doc in a list. Get the next multidoc from the current multidoc, and
 * repeat until there are no more. Then iterate over the list of saved docs. Get
 * the print data representation object from the current doc. Get all the print
 * data from the print data representation object. Go to the next doc in the
 * list, and repeat until there are no more.
 * </OL>
 * Now, consider a printing client that is generating print data on the fly and
 * does not have the resources to store more than one piece of print data at a
 * time. If the print service proxy used the all-at-once pattern to get the
 * print data, it would pose a problem for such a client; the client would have
 * to keep all the docs' print data around until the print service proxy comes
 * back and asks for them, which the client is not able to do. To work with such
 * a client, the print service proxy must use the interleaved pattern.
 * <P>
 * To address this problem, and to simplify the design of clients providing
* multiple docs to a Print Job, every Print Service proxy that supports
 * multidoc print jobs is required to access a MultiDoc object using the
 * interleaved pattern. That is, given a MultiDoc object, the print service
 * proxy will call {@link #getDoc() getDoc()} one or more times
 * until it successfully obtains the current Doc object. The print service proxy
 * will then obtain the current doc's print data, not proceeding until all the
 * print data is obtained or an unrecoverable error occurs. If it is able to
 * continue, the print service proxy will then call {@link #next()
 * next()} one or more times until it successfully obtains either
 * the next MultiDoc object or an indication that there are no more. An
 * implementation of interface MultiDoc can assume the print service proxy will
 * follow this interleaved pattern; for any other pattern of usage, the MultiDoc
 * implementation's behavior is unspecified.
 * <P>
 * There is no restriction on the number of client threads that may be
 * simultaneously accessing the same multidoc. Therefore, all implementations of
 * interface MultiDoc must be designed to be multiple thread safe. In fact, a
 * client thread could be adding docs to the end of the (conceptual) list while
 * a Print Job thread is simultaneously obtaining docs from the beginning of the
 * list; provided the multidoc object synchronizes the threads properly, the two
 * threads will not interfere with each other
 * <p>
 *  Interface MultiDoc指定为打印作业提供多个打印数据的对象的接口。 "Doc"是一个简短的易于发音的术语,意思是"一条打印数据","multidoc"是一组几个文档。
 * 客户端将实现接口MultiDoc的对象传递给打印作业,打印作业调用该对象上的方法以获取打印数据。
 * <P>
 *  Interface MultiDoc提供了一个类似于文档"链接列表"的抽象。多对象对象就像一个链表中的节点,包含列表中的当前文档和指向列表中下一个节点(multidoc)的指针。
 * 打印作业可以调用multidoc的{@link #getDoc()getDoc()}方法来获取当前文档。
 * 当它准备好继续下一个文档时,打印作业可以调用multidoc的{@link #next()next()}方法来获取下一个包含下一个文档的多标识。因此,用于访问多标识符的打印作业代码可能如下所示：。
 * <PRE>
 *  void processMultiDoc(MultiDoc theMultiDoc){
 * 
 *  MultiDoc current = theMultiDoc;
 * 
 *  while(current！= null){processDoc(current.getDoc()); current = current.next(); }}
 * </PRE>
 * <P>
 *  当然,MultiDoc接口可以以任何方式实现满足合同;它不必在实现中使用链接列表。
 * <P>
 * 要获取多标识打印作业的所有打印数据,打印服务代理可以使用以下两种模式之一：
 * <OL TYPE=1>
 * <LI>
 *  <B> interleaved </B>模式：从当前多标识获取文档。从当前文档获取打印数据表示对象。从打印数据表示对象获取所有打印数据。
 * 从当前的multidoc获取下一个multidoc,并重复,直到没有更多。 (上面的代码示例使用交织模式。)。
 * <P>
 * <LI>
 */

public interface MultiDoc {


    /**
     * Obtain the current doc object.
     *
     * <p>
     *  <b> all-at-once </b>模式：从当前多个文件中获取文档,并将文档保存在列表中。从当前的multidoc获取下一个multidoc,并重复,直到没有更多。然后遍历保存的文档列表。
     * 从当前文档获取打印数据表示对象。从打印数据表示对象获取所有打印数据。转到列表中的下一个文档,并重复,直到没有更多。
     * </OL>
     *  现在,考虑正在生成打印数据并且没有资源一次存储多于一个打印数据的打印客户端。
     * 如果打印服务代理使用all-at-once模式来获得打印数据,则这将对这样的客户端造成问题;客户端必须保留所有文档的打印数据,直到打印服务代理返回并请求它们,客户端不能这样做。
     * 要使用此类客户端,打印服务代理必须使用交错模式。
     * <P>
     * 为了解决这个问题,并且简化向打印作业提供多个文档的客户端的设计,需要支持多个打印作业的每个打印服务代理使用交错模式访问MultiDoc对象。
     * 也就是说,给定一个MultiDoc对象,打印服务代理将调用{@link #getDoc()getDoc()}一次或多次,直到它成功获取当前的Doc对象。
     * 打印服务代理然后将获得当前文档的打印数据,直到获得所有打印数据或发生不可恢复的错误为止,打印服务代理将不继续。
     * 如果它能够继续,则打印服务代理然后将调用{@link #next()next()}一次或多次,直到它成功地获得下一个MultiDoc对象或者没有更多的指示。
     * 
     * @return  Current doc object.
     *
     * @exception  IOException
     *     Thrown if a error occurred reading the document.
     */
    public Doc getDoc() throws IOException;

    /**
     * Go to the multidoc object that contains the next doc object in the
     * sequence of doc objects.
     *
     * <p>
     * 接口MultiDoc的实现可以假定打印服务代理将遵循该交织模式;对于任何其他使用模式,MultiDoc实现的行为未指定。
     * <P>
     *  对可以同时访问同一多个节点的客户端线程的数量没有限制。因此,接口MultiDoc的所有实现必须设计为多线程安全。
     * 事实上,客户端线程可以将文档添加到(概念)列表的末尾,而打印作业线程同时从列表的开始获取文档;只要多核对象正确地同步线程,两个线程就不会相互干扰。
     * 
     * 
     * @return  Multidoc object containing the next doc object, or null if
     * there are no further doc objects.
     *
     * @exception  IOException
     *     Thrown if an error occurred locating the next document
     */
    public MultiDoc next() throws IOException;

}
