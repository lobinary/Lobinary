/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.IOException;

/**
 * The abstract class {@code ContentHandler} is the superclass
 * of all classes that read an {@code Object} from a
 * {@code URLConnection}.
 * <p>
 * An application does not generally call the
 * {@code getContent} method in this class directly. Instead, an
 * application calls the {@code getContent} method in class
 * {@code URL} or in {@code URLConnection}.
 * The application's content handler factory (an instance of a class that
 * implements the interface {@code ContentHandlerFactory} set
 * up by a call to {@code setContentHandler}) is
 * called with a {@code String} giving the MIME type of the
 * object being received on the socket. The factory returns an
 * instance of a subclass of {@code ContentHandler}, and its
 * {@code getContent} method is called to create the object.
 * <p>
 * If no content handler could be found, URLConnection will
 * look for a content handler in a user-defineable set of places.
 * By default it looks in sun.net.www.content, but users can define a
 * vertical-bar delimited set of class prefixes to search through in
 * addition by defining the java.content.handler.pkgs property.
 * The class name must be of the form:
 * <pre>
 *     {package-prefix}.{major}.{minor}
 * e.g.
 *     YoyoDyne.experimental.text.plain
 * </pre>
 * If the loading of the content handler class would be performed by
 * a classloader that is outside of the delegation chain of the caller,
 * the JVM will need the RuntimePermission "getClassLoader".
 *
 * <p>
 *  抽象类{@code ContentHandler}是从{@code URLConnection}中读取{@code Object}的所有类的超类。
 * <p>
 *  应用程序通常不直接调用此类中的{@code getContent}方法。
 * 相反,应用程序调用类{@code URL}或{@code URLConnection}中的{@code getContent}方法。
 * 应用程序的内容处理程序工厂(实现通过调用{@code setContentHandler}设置的接口{@code ContentHandlerFactory}的类的实例)使用{@code String}
 * 调用,提供正在接收的对象的MIME类型在插座上。
 * 相反,应用程序调用类{@code URL}或{@code URLConnection}中的{@code getContent}方法。
 * 工厂返回{@code ContentHandler}的子类的实例,并调用其{@code getContent}方法来创建对象。
 * <p>
 *  如果没有找到内容处理程序,URLConnection将在用户可定义的场所集合中查找内容处理程序。
 * 默认情况下,它在sun.net.www.content中查找,但用户可以通过定义java.content.handler.pkgs属性来定义一个垂直条分隔的类前缀集,以此进行搜索。
 * 类名必须是以下形式：。
 * <pre>
 * 
 * @author  James Gosling
 * @see     java.net.ContentHandler#getContent(java.net.URLConnection)
 * @see     java.net.ContentHandlerFactory
 * @see     java.net.URL#getContent()
 * @see     java.net.URLConnection
 * @see     java.net.URLConnection#getContent()
 * @see     java.net.URLConnection#setContentHandlerFactory(java.net.ContentHandlerFactory)
 * @since   JDK1.0
 */
abstract public class ContentHandler {
    /**
     * Given a URL connect stream positioned at the beginning of the
     * representation of an object, this method reads that stream and
     * creates an object from it.
     *
     * <p>
     *  {package-prefix}。{major}。{minor}例如YoyoDyne.experimental.text.plain
     * </pre>
     * 如果内容处理程序类的加载将由在调用程序的委托链之外的类加载器执行,则JVM将需要RuntimePermission"getClassLoader"。
     * 
     * 
     * @param      urlc   a URL connection.
     * @return     the object read by the {@code ContentHandler}.
     * @exception  IOException  if an I/O error occurs while reading the object.
     */
    abstract public Object getContent(URLConnection urlc) throws IOException;

    /**
     * Given a URL connect stream positioned at the beginning of the
     * representation of an object, this method reads that stream and
     * creates an object that matches one of the types specified.
     *
     * The default implementation of this method should call getContent()
     * and screen the return type for a match of the suggested types.
     *
     * <p>
     *  给定位于对象的表示的开始处的URL连接流,该方法读取该流并从其创建对象。
     * 
     * 
     * @param      urlc   a URL connection.
     * @param      classes      an array of types requested
     * @return     the object read by the {@code ContentHandler} that is
     *                 the first match of the suggested types.
     *                 null if none of the requested  are supported.
     * @exception  IOException  if an I/O error occurs while reading the object.
     * @since 1.3
     */
    @SuppressWarnings("rawtypes")
    public Object getContent(URLConnection urlc, Class[] classes) throws IOException {
        Object obj = getContent(urlc);

        for (int i = 0; i < classes.length; i++) {
          if (classes[i].isInstance(obj)) {
                return obj;
          }
        }
        return null;
    }

}
