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

package javax.tools;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import static javax.tools.JavaFileObject.Kind;

/**
 * File manager for tools operating on Java&trade; programming language
 * source and class files.  In this context, <em>file</em> means an
 * abstraction of regular files and other sources of data.
 *
 * <p>When constructing new JavaFileObjects, the file manager must
 * determine where to create them.  For example, if a file manager
 * manages regular files on a file system, it would most likely have a
 * current/working directory to use as default location when creating
 * or finding files.  A number of hints can be provided to a file
 * manager as to where to create files.  Any file manager might choose
 * to ignore these hints.
 *
 * <p>Some methods in this interface use class names.  Such class
 * names must be given in the Java Virtual Machine internal form of
 * fully qualified class and interface names.  For convenience '.'
 * and '/' are interchangeable.  The internal form is defined in
 * chapter four of
 * <cite>The Java&trade; Virtual Machine Specification</cite>.

 * <blockquote><p>
 *   <i>Discussion:</i> this means that the names
 *   "java/lang.package-info", "java/lang/package-info",
 *   "java.lang.package-info", are valid and equivalent.  Compare to
 *   binary name as defined in
 *   <cite>The Java&trade; Language Specification</cite>,
 *   section 13.1 "The Form of a Binary".
 * </p></blockquote>
 *
 * <p>The case of names is significant.  All names should be treated
 * as case-sensitive.  For example, some file systems have
 * case-insensitive, case-aware file names.  File objects representing
 * such files should take care to preserve case by using {@link
 * java.io.File#getCanonicalFile} or similar means.  If the system is
 * not case-aware, file objects must use other means to preserve case.
 *
 * <p><em><a name="relative_name">Relative names</a>:</em> some
 * methods in this interface use relative names.  A relative name is a
 * non-null, non-empty sequence of path segments separated by '/'.
 * '.' or '..'  are invalid path segments.  A valid relative name must
 * match the "path-rootless" rule of <a
 * href="http://www.ietf.org/rfc/rfc3986.txt">RFC&nbsp;3986</a>,
 * section&nbsp;3.3.  Informally, this should be true:
 *
 * <!-- URI.create(relativeName).normalize().getPath().equals(relativeName) -->
 * <pre>  URI.{@linkplain java.net.URI#create create}(relativeName).{@linkplain java.net.URI#normalize normalize}().{@linkplain java.net.URI#getPath getPath}().equals(relativeName)</pre>
 *
 * <p>All methods in this interface might throw a SecurityException.
 *
 * <p>An object of this interface is not required to support
 * multi-threaded access, that is, be synchronized.  However, it must
 * support concurrent access to different file objects created by this
 * object.
 *
 * <p><em>Implementation note:</em> a consequence of this requirement
 * is that a trivial implementation of output to a {@linkplain
 * java.util.jar.JarOutputStream} is not a sufficient implementation.
 * That is, rather than creating a JavaFileObject that returns the
 * JarOutputStream directly, the contents must be cached until closed
 * and then written to the JarOutputStream.
 *
 * <p>Unless explicitly allowed, all methods in this interface might
 * throw a NullPointerException if given a {@code null} argument.
 *
 * <p>
 *  Java和贸易上运行的工具的文件管理器;编程语言源和类文件。在此上下文中,<em> </em>文件意味着常规文件和其他数据源的抽象。
 * 
 *  <p>在构建新的JavaFileObjects时,文件管理器必须确定在何处创建它们。例如,如果文件管理器管理文件系统上的常规文件,则它很可能具有当前/工作目录以在创建或查找文件时用作默认位置。
 * 可以向文件管理器提供关于在哪里创建文件的多个提示。任何文件管理器可能会选择忽略这些提示。
 * 
 *  <p>此接口中的一些方法使用类名称。这些类名必须在Java虚拟机内部形式的完全限定类和接口名中给出。为了方便 '。'和"/"可以互换。
 * 内部形式在<cite> Java&trade;虚拟机规范</cite>。
 * 
 *  <blockquote> <p> <i>讨论：</i>这意味着名称"java / lang.package-info","java / lang / package-info","java.lang.
 * package-是有效和等效的。
 * 与<cite> Java&trade;中定义的二进制名称进行比较;语言规范</cite>,第13.1节"二进制的形式"。 </p> </blockquote>。
 * 
 * <p>名称的情况很重要。所有名称都应视为区分大小写。例如,某些文件系统具有区分大小写,区分大小写的文件名。
 * 表示此类文件的文件对象应注意使用{@link java.io.File#getCanonicalFile}或类似方法保留大小写。如果系统不区分大小写,则文件对象必须使用其他方式保留大小写。
 * 
 *  <p> <em> <a name="relative_name">相对名称</a>：</em>此界面中的一些方法使用相对名称。相对名称是由"/"分隔的路径段的非空,非空序列。 ''。
 * 或".."是无效路径段。
 * 有效的相对名称必须与<a href="http://www.ietf.org/rfc/rfc3986.txt"> RFC&nbsp; 3986 </a>(第3.3节)的"无pathless"规则匹配。
 * 非正式地,这应该是真的：。
 * 
 * <!-- URI.create(relativeName).normalize().getPath().equals(relativeName) -->
 *  <pre> URI.{@linkplain java.net.URI#create create}(relativeName)。
 * {@ linkplain java.net.URI#normalize normalize}()。
 * {@ linkplain java.net.URI#getPath getPath}() .equals(relativeName)</pre>。
 * 
 *  <p>此接口中的所有方法都可能抛出SecurityException。
 * 
 *  <p>此接口的对象不需要支持多线程访问,也就是同步。但是,它必须支持对由此对象创建的不同文件对象的并发访问。
 * 
 * <p> <em>实现说明：</em>此要求的后果是,对{@linkplain java.util.jar.JarOutputStream}的输出的一个小的实现不是一个足够的实现。
 * 也就是说,不是创建直接返回JarOutputStream的JavaFileObject,内容必须缓存,直到关闭,然后写入JarOutputStream。
 * 
 *  <p>除非明确允许,否则如果给定{@code null}参数,此接口中的所有方法都可能抛出NullPointerException。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @see JavaFileObject
 * @see FileObject
 * @since 1.6
 */
public interface JavaFileManager extends Closeable, Flushable, OptionChecker {

    /**
     * Interface for locations of file objects.  Used by file managers
     * to determine where to place or search for file objects.
     * <p>
     *  文件对象的位置接口。由文件管理器用于确定在哪里放置或搜索文件对象。
     * 
     */
    interface Location {
        /**
         * Gets the name of this location.
         *
         * <p>
         *  获取此位置的名称。
         * 
         * 
         * @return a name
         */
        String getName();

        /**
         * Determines if this is an output location.  An output
         * location is a location that is conventionally used for
         * output.
         *
         * <p>
         *  确定这是否是输出位置。输出位置是通常用于输出的位置。
         * 
         * 
         * @return true if this is an output location, false otherwise
         */
        boolean isOutputLocation();
    }

    /**
     * Gets a class loader for loading plug-ins from the given
     * location.  For example, to load annotation processors, a
     * compiler will request a class loader for the {@link
     * StandardLocation#ANNOTATION_PROCESSOR_PATH
     * ANNOTATION_PROCESSOR_PATH} location.
     *
     * <p>
     *  获取从给定位置加载插件的类加载器。
     * 例如,要加载注释处理器,编译器将为{@link StandardLocation#ANNOTATION_PROCESSOR_PATH ANNOTATION_PROCESSOR_PATH}位置请求类加载器
     * 。
     *  获取从给定位置加载插件的类加载器。
     * 
     * 
     * @param location a location
     * @return a class loader for the given location; or {@code null}
     * if loading plug-ins from the given location is disabled or if
     * the location is not known
     * @throws SecurityException if a class loader can not be created
     * in the current security context
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    ClassLoader getClassLoader(Location location);

    /**
     * Lists all file objects matching the given criteria in the given
     * location.  List file objects in "subpackages" if recurse is
     * true.
     *
     * <p>Note: even if the given location is unknown to this file
     * manager, it may not return {@code null}.  Also, an unknown
     * location may not cause an exception.
     *
     * <p>
     *  列出与给定位置中的给定条件匹配的所有文件对象。如果recurse为true,则列出"subpackages"中的文件对象。
     * 
     *  <p>注意：即使该文件管理器未知给定位置,也不能返回{@code null}。此外,未知位置可能不会导致异常。
     * 
     * 
     * @param location     a location
     * @param packageName  a package name
     * @param kinds        return objects only of these kinds
     * @param recurse      if true include "subpackages"
     * @return an Iterable of file objects matching the given criteria
     * @throws IOException if an I/O error occurred, or if {@link
     * #close} has been called and this file manager cannot be
     * reopened
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    Iterable<JavaFileObject> list(Location location,
                                  String packageName,
                                  Set<Kind> kinds,
                                  boolean recurse)
        throws IOException;

    /**
     * Infers a binary name of a file object based on a location.  The
     * binary name returned might not be a valid binary name according to
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  基于位置来表示文件对象的二进制名称。返回的二进制名称可能不是有效的二进制名称,根据<cite> Java&trade;语言规范</cite>。
     * 
     * 
     * @param location a location
     * @param file a file object
     * @return a binary name or {@code null} the file object is not
     * found in the given location
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    String inferBinaryName(Location location, JavaFileObject file);

    /**
     * Compares two file objects and return true if they represent the
     * same underlying object.
     *
     * <p>
     * 比较两个文件对象,如果它们表示相同的基础对象,则返回true。
     * 
     * 
     * @param a a file object
     * @param b a file object
     * @return true if the given file objects represent the same
     * underlying object
     *
     * @throws IllegalArgumentException if either of the arguments
     * were created with another file manager and this file manager
     * does not support foreign file objects
     */
    boolean isSameFile(FileObject a, FileObject b);

    /**
     * Handles one option.  If {@code current} is an option to this
     * file manager it will consume any arguments to that option from
     * {@code remaining} and return true, otherwise return false.
     *
     * <p>
     *  处理一个选项。如果{@code current}是这个文件管理器的一个选项,它将从{@code remaining}消耗该选项的任何参数,并返回true,否则返回false。
     * 
     * 
     * @param current current option
     * @param remaining remaining options
     * @return true if this option was handled by this file manager,
     * false otherwise
     * @throws IllegalArgumentException if this option to this file
     * manager is used incorrectly
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    boolean handleOption(String current, Iterator<String> remaining);

    /**
     * Determines if a location is known to this file manager.
     *
     * <p>
     *  确定该文件管理器是否知道位置。
     * 
     * 
     * @param location a location
     * @return true if the location is known
     */
    boolean hasLocation(Location location);

    /**
     * Gets a {@linkplain JavaFileObject file object} for input
     * representing the specified class of the specified kind in the
     * given location.
     *
     * <p>
     *  获取表示给定位置中指定类型的指定类的输入的{@linkplain JavaFileObject文件对象}。
     * 
     * 
     * @param location a location
     * @param className the name of a class
     * @param kind the kind of file, must be one of {@link
     * JavaFileObject.Kind#SOURCE SOURCE} or {@link
     * JavaFileObject.Kind#CLASS CLASS}
     * @return a file object, might return {@code null} if the
     * file does not exist
     * @throws IllegalArgumentException if the location is not known
     * to this file manager and the file manager does not support
     * unknown locations, or if the kind is not valid
     * @throws IOException if an I/O error occurred, or if {@link
     * #close} has been called and this file manager cannot be
     * reopened
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    JavaFileObject getJavaFileForInput(Location location,
                                       String className,
                                       Kind kind)
        throws IOException;

    /**
     * Gets a {@linkplain JavaFileObject file object} for output
     * representing the specified class of the specified kind in the
     * given location.
     *
     * <p>Optionally, this file manager might consider the sibling as
     * a hint for where to place the output.  The exact semantics of
     * this hint is unspecified.  The JDK compiler, javac, for
     * example, will place class files in the same directories as
     * originating source files unless a class file output directory
     * is provided.  To facilitate this behavior, javac might provide
     * the originating source file as sibling when calling this
     * method.
     *
     * <p>
     *  获取表示给定位置中指定类型的指定类的输出的{@linkplain JavaFileObject文件对象}。
     * 
     *  <p>(可选)此文件管理器可能会将同级视为提示放置输出的位置。这个提示的确切语义是未指定的。例如,JDK编译器javac将类文件放置在与源文件相同的目录中,除非提供类文件输出目录。
     * 为了方便此行为,javac可能在调用此方法时将源文件提供为同级。
     * 
     * 
     * @param location a location
     * @param className the name of a class
     * @param kind the kind of file, must be one of {@link
     * JavaFileObject.Kind#SOURCE SOURCE} or {@link
     * JavaFileObject.Kind#CLASS CLASS}
     * @param sibling a file object to be used as hint for placement;
     * might be {@code null}
     * @return a file object for output
     * @throws IllegalArgumentException if sibling is not known to
     * this file manager, or if the location is not known to this file
     * manager and the file manager does not support unknown
     * locations, or if the kind is not valid
     * @throws IOException if an I/O error occurred, or if {@link
     * #close} has been called and this file manager cannot be
     * reopened
     * @throws IllegalStateException {@link #close} has been called
     * and this file manager cannot be reopened
     */
    JavaFileObject getJavaFileForOutput(Location location,
                                        String className,
                                        Kind kind,
                                        FileObject sibling)
        throws IOException;

    /**
     * Gets a {@linkplain FileObject file object} for input
     * representing the specified <a href="JavaFileManager.html#relative_name">relative
     * name</a> in the specified package in the given location.
     *
     * <p>If the returned object represents a {@linkplain
     * JavaFileObject.Kind#SOURCE source} or {@linkplain
     * JavaFileObject.Kind#CLASS class} file, it must be an instance
     * of {@link JavaFileObject}.
     *
     * <p>Informally, the file object returned by this method is
     * located in the concatenation of the location, package name, and
     * relative name.  For example, to locate the properties file
     * "resources/compiler.properties" in the package
     * "com.sun.tools.javac" in the {@linkplain
     * StandardLocation#SOURCE_PATH SOURCE_PATH} location, this method
     * might be called like so:
     *
     * <pre>getFileForInput(SOURCE_PATH, "com.sun.tools.javac", "resources/compiler.properties");</pre>
     *
     * <p>If the call was executed on Windows, with SOURCE_PATH set to
     * <code>"C:\Documents&nbsp;and&nbsp;Settings\UncleBob\src\share\classes"</code>,
     * a valid result would be a file object representing the file
     * <code>"C:\Documents&nbsp;and&nbsp;Settings\UncleBob\src\share\classes\com\sun\tools\javac\resources\compiler.properties"</code>.
     *
     * <p>
     *  获取表示给定位置中指定包中指定的<a href="JavaFileManager.html#relative_name">相对名称</a>的输入的{@linkplain FileObject文件对象}
     * 。
     * 
     *  <p>如果返回的对象代表{@linkplain JavaFileObject.Kind#SOURCE source}或{@linkplain JavaFileObject.Kind#CLASS class}
     * 文件,则它必须是{@link JavaFileObject}的实例。
     * 
     * <p>非正式地,此方法返回的文件对象位于位置,包名称和相对名称的并置中。
     * 例如,要在{@linkplain StandardLocation#SOURCE_PATH SOURCE_PATH}位置的软件包"com.sun.tools.javac"中找到属性文件"resource
     * s / compiler.properties",可能会调用此方法,如下所示：。
     * <p>非正式地,此方法返回的文件对象位于位置,包名称和相对名称的并置中。
     * 
     *  <pre> getFileForInput(SOURCE_PATH,"com.sun.tools.javac","resources / compiler.properties"); </pre>
     * 
     *  <p>如果在Windows上执行调用,并且SOURCE_PATH设置为<code>"C：\ Documents&nbsp;和Settings \ UncleBob \ src \ share \ cl
     * asses"</code>,则有效的结果将是表示文件<code>"C：\ Documents&nbsp;和Settings \ UncleBob \ src \ share \ classes \ co
     * m \ sun \ tools \ javac \ resources \ compiler.properties"</code>。
     * 
     * 
     * @param location a location
     * @param packageName a package name
     * @param relativeName a relative name
     * @return a file object, might return {@code null} if the file
     * does not exist
     * @throws IllegalArgumentException if the location is not known
     * to this file manager and the file manager does not support
     * unknown locations, or if {@code relativeName} is not valid
     * @throws IOException if an I/O error occurred, or if {@link
     * #close} has been called and this file manager cannot be
     * reopened
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    FileObject getFileForInput(Location location,
                               String packageName,
                               String relativeName)
        throws IOException;

    /**
     * Gets a {@linkplain FileObject file object} for output
     * representing the specified <a href="JavaFileManager.html#relative_name">relative
     * name</a> in the specified package in the given location.
     *
     * <p>Optionally, this file manager might consider the sibling as
     * a hint for where to place the output.  The exact semantics of
     * this hint is unspecified.  The JDK compiler, javac, for
     * example, will place class files in the same directories as
     * originating source files unless a class file output directory
     * is provided.  To facilitate this behavior, javac might provide
     * the originating source file as sibling when calling this
     * method.
     *
     * <p>If the returned object represents a {@linkplain
     * JavaFileObject.Kind#SOURCE source} or {@linkplain
     * JavaFileObject.Kind#CLASS class} file, it must be an instance
     * of {@link JavaFileObject}.
     *
     * <p>Informally, the file object returned by this method is
     * located in the concatenation of the location, package name, and
     * relative name or next to the sibling argument.  See {@link
     * #getFileForInput getFileForInput} for an example.
     *
     * <p>
     *  获取表示给定位置中指定包中指定的<a href="JavaFileManager.html#relative_name">相对名称</a>的输出的{@linkplain FileObject文件对象}
     * 。
     * 
     *  <p>(可选)此文件管理器可能会将同级视为提示放置输出的位置。这个提示的确切语义是未指定的。例如,JDK编译器javac将类文件放置在与源文件相同的目录中,除非提供类文件输出目录。
     * 为了方便此行为,javac可能在调用此方法时将源文件提供为同级。
     * 
     * <p>如果返回的对象代表{@linkplain JavaFileObject.Kind#SOURCE source}或{@linkplain JavaFileObject.Kind#CLASS class}
     * 文件,则它必须是{@link JavaFileObject}的实例。
     * 
     * 
     * @param location a location
     * @param packageName a package name
     * @param relativeName a relative name
     * @param sibling a file object to be used as hint for placement;
     * might be {@code null}
     * @return a file object
     * @throws IllegalArgumentException if sibling is not known to
     * this file manager, or if the location is not known to this file
     * manager and the file manager does not support unknown
     * locations, or if {@code relativeName} is not valid
     * @throws IOException if an I/O error occurred, or if {@link
     * #close} has been called and this file manager cannot be
     * reopened
     * @throws IllegalStateException if {@link #close} has been called
     * and this file manager cannot be reopened
     */
    FileObject getFileForOutput(Location location,
                                String packageName,
                                String relativeName,
                                FileObject sibling)
        throws IOException;

    /**
     * Flushes any resources opened for output by this file manager
     * directly or indirectly.  Flushing a closed file manager has no
     * effect.
     *
     * <p>
     *  <p>非正式地,此方法返回的文件对象位于位置,包名称和相对名称的并置中,或位于兄弟参数旁边。有关示例,请参见{@link #getFileForInput getFileForInput}。
     * 
     * 
     * @throws IOException if an I/O error occurred
     * @see #close
     */
    void flush() throws IOException;

    /**
     * Releases any resources opened by this file manager directly or
     * indirectly.  This might render this file manager useless and
     * the effect of subsequent calls to methods on this object or any
     * objects obtained through this object is undefined unless
     * explicitly allowed.  However, closing a file manager which has
     * already been closed has no effect.
     *
     * <p>
     *  清除由此文件管理器直接或间接打开以输出的任何资源。刷新已关闭的文件管理器没有任何效果。
     * 
     * 
     * @throws IOException if an I/O error occurred
     * @see #flush
     */
    void close() throws IOException;
}
