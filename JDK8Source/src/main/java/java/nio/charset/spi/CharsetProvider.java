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

package java.nio.charset.spi;

import java.nio.charset.Charset;
import java.util.Iterator;


/**
 * Charset service-provider class.
 *
 * <p> A charset provider is a concrete subclass of this class that has a
 * zero-argument constructor and some number of associated charset
 * implementation classes.  Charset providers may be installed in an instance
 * of the Java platform as extensions, that is, jar files placed into any of
 * the usual extension directories.  Providers may also be made available by
 * adding them to the applet or application class path or by some other
 * platform-specific means.  Charset providers are looked up via the current
 * thread's {@link java.lang.Thread#getContextClassLoader() context class
 * loader}.
 *
 * <p> A charset provider identifies itself with a provider-configuration file
 * named <tt>java.nio.charset.spi.CharsetProvider</tt> in the resource
 * directory <tt>META-INF/services</tt>.  The file should contain a list of
 * fully-qualified concrete charset-provider class names, one per line.  A line
 * is terminated by any one of a line feed (<tt>'\n'</tt>), a carriage return
 * (<tt>'\r'</tt>), or a carriage return followed immediately by a line feed.
 * Space and tab characters surrounding each name, as well as blank lines, are
 * ignored.  The comment character is <tt>'#'</tt> (<tt>'&#92;u0023'</tt>); on
 * each line all characters following the first comment character are ignored.
 * The file must be encoded in UTF-8.
 *
 * <p> If a particular concrete charset provider class is named in more than
 * one configuration file, or is named in the same configuration file more than
 * once, then the duplicates will be ignored.  The configuration file naming a
 * particular provider need not be in the same jar file or other distribution
 * unit as the provider itself.  The provider must be accessible from the same
 * class loader that was initially queried to locate the configuration file;
 * this is not necessarily the class loader that loaded the file. </p>
 *
 *
 * <p>
 *  Charset服务提供程序类。
 * 
 *  <p>字符集提供程序是此类的一个具体子类,它具有零参数构造函数和一些关联的字符集实现类。字符集提供程序可以作为扩展安装在Java平台的实例中,也就是说,jar文件放置在任何通常的扩展目录中。
 * 提供者也可以通过将它们添加到小应用程序或应用类路径或通过一些其他平台特定的装置而可用。
 * 通过当前线程的{@link java.lang.Thread#getContextClassLoader()上下文类加载器}查找字符集提供程序。
 * 
 *  <p>字符集提供程序使用资源目录<tt> META-INF / services </tt>中名为<tt> java.nio.charset.spi.CharsetProvider </tt>的提供程
 * 序配置文件标识自身。
 * 该文件应包含完全限定的具体charset-provider类名称列表,每行一个。一行通过换行符(<tt>'\ n'</tt>),回车符(<tt>'\ r'</tt>)或回车符换行。
 * 忽略每个名称周围的空格和制表符以及空行。注释字符为<tt>'#'</tt>(<tt>'\ u0023'</tt>);在每行上,忽略第一个注释字符后面的所有字符。该文件必须以UTF-8编码。
 * 
 * <p>如果特定的具体字符集提供程序类在多个配置文件中命名,或者在同一配置文件中多次命名,那么将忽略重复项。指定特定提供者的配置文件不需要位于与提供者本身相同的jar文件或其他分发单元中。
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see java.nio.charset.Charset
 */

public abstract class CharsetProvider {

    /**
     * Initializes a new charset provider.
     *
     * <p>
     * 提供程序必须可以从最初查询以查找配置文件的同一类加载器访问;这不一定是加载文件的类加载器。 </p>。
     * 
     * 
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     *          {@link RuntimePermission}<tt>("charsetProvider")</tt>
     */
    protected CharsetProvider() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new RuntimePermission("charsetProvider"));
    }

    /**
     * Creates an iterator that iterates over the charsets supported by this
     * provider.  This method is used in the implementation of the {@link
     * java.nio.charset.Charset#availableCharsets Charset.availableCharsets}
     * method.
     *
     * <p>
     *  初始化新的字符集提供程序。
     * 
     * 
     * @return  The new iterator
     */
    public abstract Iterator<Charset> charsets();

    /**
     * Retrieves a charset for the given charset name.
     *
     * <p>
     *  创建迭代器,遍历此提供程序支持的字符集。
     * 此方法用于实现{@link java.nio.charset.Charset#availableCharsets Charset.availableCharsets}方法。
     * 
     * 
     * @param  charsetName
     *         The name of the requested charset; may be either
     *         a canonical name or an alias
     *
     * @return  A charset object for the named charset,
     *          or <tt>null</tt> if the named charset
     *          is not supported by this provider
     */
    public abstract Charset charsetForName(String charsetName);

}
