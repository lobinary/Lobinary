/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.spi;

import java.io.File;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

/**
 * The service provider interface (SPI) for
 * <code>ImageInputStream</code>s.  For more information on service
 * provider interfaces, see the class comment for the
 * <code>IIORegistry</code> class.
 *
 * <p> This interface allows arbitrary objects to be "wrapped" by
 * instances of <code>ImageInputStream</code>.  For example,
 * a particular <code>ImageInputStreamSpi</code> might allow
 * a generic <code>InputStream</code> to be used as an input source;
 * another might take input from a <code>URL</code>.
 *
 * <p> By treating the creation of <code>ImageInputStream</code>s as a
 * pluggable service, it becomes possible to handle future input
 * sources without changing the API.  Also, high-performance
 * implementations of <code>ImageInputStream</code> (for example,
 * native implementations for a particular platform) can be installed
 * and used transparently by applications.
 *
 * <p>
 *  用于<code> ImageInputStream </code>的服务提供程序接口(SPI)。有关服务提供程序接口的更多信息,请参阅<code> IIORegistry </code>类的类注释。
 * 
 *  <p>此接口允许任意对象由<code> ImageInputStream </code>的实例"封装"。
 * 例如,特定的<code> ImageInputStreamSpi </code>可能允许将一个通用的<code> InputStream </code>用作输入源;另一个可能接受来自<code> URL
 *  </code>的输入。
 *  <p>此接口允许任意对象由<code> ImageInputStream </code>的实例"封装"。
 * 
 *  <p>通过将<code> ImageInputStream </code>的创建视为可插入服务,可以处理未来的输入源,而无需更改API。
 * 此外,<code> ImageInputStream </code>(例如,特定平台的本机实现)的高性能实现可以由应用程序透明地安装和使用。
 * 
 * 
 * @see IIORegistry
 * @see javax.imageio.stream.ImageInputStream
 *
 */
public abstract class ImageInputStreamSpi extends IIOServiceProvider {

    /**
     * A <code>Class</code> object indicating the legal object type
     * for use by the <code>createInputStreamInstance</code> method.
     * <p>
     *  代表<code> createInputStreamInstance </code>方法使用的合法对象类型的<code> Class </code>对象。
     * 
     */
    protected Class<?> inputClass;

    /**
     * Constructs a blank <code>ImageInputStreamSpi</code>.  It is up
     * to the subclass to initialize instance variables and/or
     * override method implementations in order to provide working
     * versions of all methods.
     * <p>
     *  构造一个空白<code> ImageInputStreamSpi </code>。它是由子类初始化实例变量和/或覆盖方法实现为了提供所有方法的工作版本。
     * 
     */
    protected ImageInputStreamSpi() {
    }

    /**
     * Constructs an <code>ImageInputStreamSpi</code> with a given set
     * of values.
     *
     * <p>
     *  用给定的一组值构造一个<code> ImageInputStreamSpi </code>。
     * 
     * 
     * @param vendorName the vendor name.
     * @param version a version identifier.
     * @param inputClass a <code>Class</code> object indicating the
     * legal object type for use by the
     * <code>createInputStreamInstance</code> method.
     *
     * @exception IllegalArgumentException if <code>vendorName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>version</code>
     * is <code>null</code>.
     */
    public ImageInputStreamSpi(String vendorName,
                               String version,
                               Class<?> inputClass) {
        super(vendorName, version);
        this.inputClass = inputClass;
    }

    /**
     * Returns a <code>Class</code> object representing the class or
     * interface type that must be implemented by an input source in
     * order to be "wrapped" in an <code>ImageInputStream</code> via
     * the <code>createInputStreamInstance</code> method.
     *
     * <p> Typical return values might include
     * <code>InputStream.class</code> or <code>URL.class</code>, but
     * any class may be used.
     *
     * <p>
     * 返回一个表示必须由输入源实现的类或接口类型的<code> Class </code>对象,以便通过<code> createInputStreamInstance </code>在<code> Imag
     * eInputStream </code>代码>方法。
     * 
     *  <p>典型的返回值可能包括<code> InputStream.class </code>或<code> URL.class </code>,但可以使用任何类。
     * 
     * 
     * @return a <code>Class</code> variable.
     *
     * @see #createInputStreamInstance(Object, boolean, File)
     */
    public Class<?> getInputClass() {
        return inputClass;
    }

    /**
     * Returns <code>true</code> if the <code>ImageInputStream</code>
     * implementation associated with this service provider can
     * optionally make use of a cache file for improved performance
     * and/or memory footrprint.  If <code>false</code>, the value of
     * the <code>useCache</code> argument to
     * <code>createInputStreamInstance</code> will be ignored.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     *  如果与此服务提供程序相关联的<code> ImageInputStream </code>实现可选择使用缓存文件以提高性能和/或内存占用,则返回<code> true </code>。
     * 如果<code> false </code>,则<code> createInputStreamInstance </code>的<code> useCache </code>参数的值将被忽略。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if a cache file can be used by the
     * input streams created by this service provider.
     */
    public boolean canUseCacheFile() {
        return false;
    }

    /**
     * Returns <code>true</code> if the <code>ImageInputStream</code>
     * implementation associated with this service provider requires
     * the use of a cache <code>File</code>.  If <code>true</code>,
     * the value of the <code>useCache</code> argument to
     * <code>createInputStreamInstance</code> will be ignored.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     *  如果与此服务提供商相关联的<code> ImageInputStream </code>实现要求使用缓存<code> File </code>,则返回<code> true </code>。
     * 如果<code> true </code>,则<code> createInputStreamInstance </code>的<code> useCache </code>参数的值将被忽略。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if a cache file is needed by the
     * input streams created by this service provider.
     */
    public boolean needsCacheFile() {
        return false;
    }

    /**
     * Returns an instance of the <code>ImageInputStream</code>
     * implementation associated with this service provider.  If the
     * use of a cache file is optional, the <code>useCache</code>
     * parameter will be consulted.  Where a cache is required, or
     * not applicable, the value of <code>useCache</code> will be ignored.
     *
     * <p>
     *  返回与此服务提供商相关联的<code> ImageInputStream </code>实现的实例。如果使用高速缓存文件是可选的,那么将参考<code> useCache </code>参数。
     * 在需要缓存或不适用的情况下,将忽略<code> useCache </code>的值。
     * 
     * 
     * @param input an object of the class type returned by
     * <code>getInputClass</code>.
     * @param useCache a <code>boolean</code> indicating whether a
     * cache file should be used, in cases where it is optional.
     * @param cacheDir a <code>File</code> indicating where the
     * cache file should be created, or <code>null</code> to use the
     * system directory.
     *
     * @return an <code>ImageInputStream</code> instance.
     *
     * @exception IllegalArgumentException if <code>input</code> is
     * not an instance of the correct class or is <code>null</code>.
     * @exception IllegalArgumentException if a cache file is needed
     * but <code>cacheDir</code> is non-<code>null</code> and is not a
     * directory.
     * @exception IOException if a cache file is needed but cannot be
     * created.
     *
     * @see #getInputClass
     * @see #canUseCacheFile
     * @see #needsCacheFile
     */
    public abstract ImageInputStream
        createInputStreamInstance(Object input,
                                  boolean useCache,
                                  File cacheDir) throws IOException;

    /**
     * Returns an instance of the <code>ImageInputStream</code>
     * implementation associated with this service provider.  A cache
     * file will be created in the system-dependent default
     * temporary-file directory, if needed.
     *
     * <p>
     * 
     * @param input an object of the class type returned by
     * <code>getInputClass</code>.
     *
     * @return an <code>ImageInputStream</code> instance.
     *
     * @exception IllegalArgumentException if <code>input</code> is
     * not an instance of the correct class or is <code>null</code>.
     * @exception IOException if a cache file is needed but cannot be
     * created.
     *
     * @see #getInputClass()
     */
    public ImageInputStream createInputStreamInstance(Object input)
        throws IOException {
        return createInputStreamInstance(input, true, null);
    }
}
