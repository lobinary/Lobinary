/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.loading;

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import java.util.Enumeration;

import javax.management.*;



/**
 * Exposes the remote management interface of the MLet
 * MBean.
 *
 * <p>
 *  显示MLet MBean的远程管理接口。
 * 
 * 
 * @since 1.5
 */
public interface MLetMBean   {


    /**
     * Loads a text file containing MLET tags that define the MBeans
     * to be added to the MBean server. The location of the text file is
     * specified by a URL. The text file is read using the UTF-8
     * encoding. The MBeans specified in the MLET file will be
     * instantiated and registered in the MBean server.
     *
     * <p>
     *  加载包含定义要添加到MBean服务器的MBean的MLET标记的文本文件。文本文件的位置由URL指定。使用UTF-8编码读取文本文件。
     * 在MLET文件中指定的MBean将被实例化并在MBean服务器中注册。
     * 
     * 
     * @param url The URL of the text file to be loaded as String object.
     *
     * @return A set containing one entry per MLET tag in the m-let
     * text file loaded.  Each entry specifies either the
     * ObjectInstance for the created MBean, or a throwable object
     * (that is, an error or an exception) if the MBean could not be
     * created.
     *
     * @exception ServiceNotFoundException One of the following errors
     * has occurred: The m-let text file does not contain an MLET tag,
     * the m-let text file is not found, a mandatory attribute of the
     * MLET tag is not specified, the value of url is malformed.
     */
    public Set<Object> getMBeansFromURL(String url)
            throws ServiceNotFoundException;

    /**
     * Loads a text file containing MLET tags that define the MBeans
     * to be added to the MBean server. The location of the text file is
     * specified by a URL. The text file is read using the UTF-8
     * encoding. The MBeans specified in the MLET file will be
     * instantiated and registered in the MBean server.
     *
     * <p>
     *  加载包含定义要添加到MBean服务器的MBean的MLET标记的文本文件。文本文件的位置由URL指定。使用UTF-8编码读取文本文件。
     * 在MLET文件中指定的MBean将被实例化并在MBean服务器中注册。
     * 
     * 
     * @param url The URL of the text file to be loaded as URL object.
     *
     * @return A set containing one entry per MLET tag in the m-let
     * text file loaded.  Each entry specifies either the
     * ObjectInstance for the created MBean, or a throwable object
     * (that is, an error or an exception) if the MBean could not be
     * created.
     *
     * @exception ServiceNotFoundException One of the following errors
     * has occurred: The m-let text file does not contain an MLET tag,
     * the m-let text file is not found, a mandatory attribute of the
     * MLET tag is not specified, the value of url is null.
     */
    public Set<Object> getMBeansFromURL(URL url)
            throws ServiceNotFoundException;

    /**
     * Appends the specified URL to the list of URLs to search for classes and
     * resources.
     *
     * <p>
     *  将指定的URL附加到URL列表中以搜索类和资源。
     * 
     * 
     * @param url the URL to add.
     */
    public void addURL(URL url) ;

    /**
     * Appends the specified URL to the list of URLs to search for classes and
     * resources.
     *
     * <p>
     *  将指定的URL附加到URL列表中以搜索类和资源。
     * 
     * 
     * @param url the URL to add.
     *
     * @exception ServiceNotFoundException The specified URL is malformed.
     */
    public void addURL(String url) throws ServiceNotFoundException;

    /**
     * Returns the search path of URLs for loading classes and resources.
     * This includes the original list of URLs specified to the constructor,
     * along with any URLs subsequently appended by the addURL() method.
     *
     * <p>
     *  返回用于加载类和资源的网址的搜索路径。这包括指定给构造函数的原始URL列表,以及随后由addURL()方法附加的任何URL。
     * 
     * 
     * @return the list of URLs.
     */
    public URL[] getURLs();

    /** Finds the resource with the given name.
     * A resource is some data (images, audio, text, etc) that can be accessed by class code in a way that is
     *   independent of the location of the code.
     *   The name of a resource is a "/"-separated path name that identifies the resource.
     *
     * <p>
     *  资源是可以通过类代码以独立于代码的位置的方式访问的一些数据(图像,音频,文本等)。资源的名称是标识资源的"/"分隔的路径名。
     * 
     * 
     * @param name The resource name
     *
     * @return  An URL for reading the resource, or null if the resource could not be found or the caller doesn't have adequate privileges to get the
     * resource.
     */
    public URL getResource(String name);

    /** Returns an input stream for reading the specified resource. The search order is described in the documentation for
     *  getResource(String).
     *
     * <p>
     *  getResource(String)。
     * 
     * 
     * @param name  The resource name
     *
     * @return An input stream for reading the resource, or null if the resource could not be found
     *
     */
    public InputStream getResourceAsStream(String name);

    /**
     * Finds all the resources with the given name. A resource is some
     * data (images, audio, text, etc) that can be accessed by class
     * code in a way that is independent of the location of the code.
     * The name of a resource is a "/"-separated path name that
     * identifies the resource.
     *
     * <p>
     * 查找具有给定名称的所有资源。资源是可以通过类代码以独立于代码的位置的方式访问的一些数据(图像,音频,文本等)。资源的名称是标识资源的"/"分隔的路径名。
     * 
     * 
     * @param name The  resource name.
     *
     * @return An enumeration of URL to the resource. If no resources
     * could be found, the enumeration will be empty. Resources that
     * cannot be accessed will not be in the enumeration.
     *
     * @exception IOException if an I/O exception occurs when
     * searching for resources.
     */
    public Enumeration<URL> getResources(String name) throws IOException;

    /**
     * Gets the current directory used by the library loader for
     * storing native libraries before they are loaded into memory.
     *
     * <p>
     *  获取库加载器用于在将本机库加载到内存之前存储本机库所使用的当前目录。
     * 
     * 
     * @return The current directory used by the library loader.
     *
     * @see #setLibraryDirectory
     *
     * @throws UnsupportedOperationException if this implementation
     * does not support storing native libraries in this way.
     */
    public String getLibraryDirectory();

    /**
     * Sets the directory used by the library loader for storing
     * native libraries before they are loaded into memory.
     *
     * <p>
     *  设置库加载器用于在将本机库加载到内存之前存储本机库的目录。
     * 
     * @param libdir The directory used by the library loader.
     *
     * @see #getLibraryDirectory
     *
     * @throws UnsupportedOperationException if this implementation
     * does not support storing native libraries in this way.
     */
    public void setLibraryDirectory(String libdir);

 }
