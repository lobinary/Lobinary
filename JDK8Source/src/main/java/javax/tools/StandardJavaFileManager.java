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

package javax.tools;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * File manager based on {@linkplain File java.io.File}.  A common way
 * to obtain an instance of this class is using {@linkplain
 * JavaCompiler#getStandardFileManager
 * getStandardFileManager}, for example:
 *
 * <pre>
 *   JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 *   {@code DiagnosticCollector<JavaFileObject>} diagnostics =
 *       new {@code DiagnosticCollector<JavaFileObject>()};
 *   StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
 * </pre>
 *
 * This file manager creates file objects representing regular
 * {@linkplain File files},
 * {@linkplain java.util.zip.ZipEntry zip file entries}, or entries in
 * similar file system based containers.  Any file object returned
 * from a file manager implementing this interface must observe the
 * following behavior:
 *
 * <ul>
 *   <li>
 *     File names need not be canonical.
 *   </li>
 *   <li>
 *     For file objects representing regular files
 *     <ul>
 *       <li>
 *         the method <code>{@linkplain FileObject#delete()}</code>
 *         is equivalent to <code>{@linkplain File#delete()}</code>,
 *       </li>
 *       <li>
 *         the method <code>{@linkplain FileObject#getLastModified()}</code>
 *         is equivalent to <code>{@linkplain File#lastModified()}</code>,
 *       </li>
 *       <li>
 *         the methods <code>{@linkplain FileObject#getCharContent(boolean)}</code>,
 *         <code>{@linkplain FileObject#openInputStream()}</code>, and
 *         <code>{@linkplain FileObject#openReader(boolean)}</code>
 *         must succeed if the following would succeed (ignoring
 *         encoding issues):
 *         <blockquote>
 *           <pre>new {@linkplain java.io.FileInputStream#FileInputStream(File) FileInputStream}(new {@linkplain File#File(java.net.URI) File}({@linkplain FileObject fileObject}.{@linkplain FileObject#toUri() toUri}()))</pre>
 *         </blockquote>
 *       </li>
 *       <li>
 *         and the methods
 *         <code>{@linkplain FileObject#openOutputStream()}</code>, and
 *         <code>{@linkplain FileObject#openWriter()}</code> must
 *         succeed if the following would succeed (ignoring encoding
 *         issues):
 *         <blockquote>
 *           <pre>new {@linkplain java.io.FileOutputStream#FileOutputStream(File) FileOutputStream}(new {@linkplain File#File(java.net.URI) File}({@linkplain FileObject fileObject}.{@linkplain FileObject#toUri() toUri}()))</pre>
 *         </blockquote>
 *       </li>
 *     </ul>
 *   </li>
 *   <li>
 *     The {@linkplain java.net.URI URI} returned from
 *     <code>{@linkplain FileObject#toUri()}</code>
 *     <ul>
 *       <li>
 *         must be {@linkplain java.net.URI#isAbsolute() absolute} (have a schema), and
 *       </li>
 *       <li>
 *         must have a {@linkplain java.net.URI#normalize() normalized}
 *         {@linkplain java.net.URI#getPath() path component} which
 *         can be resolved without any process-specific context such
 *         as the current directory (file names must be absolute).
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * According to these rules, the following URIs, for example, are
 * allowed:
 * <ul>
 *   <li>
 *     <code>file:///C:/Documents%20and%20Settings/UncleBob/BobsApp/Test.java</code>
 *   </li>
 *   <li>
 *     <code>jar:///C:/Documents%20and%20Settings/UncleBob/lib/vendorA.jar!com/vendora/LibraryClass.class</code>
 *   </li>
 * </ul>
 * Whereas these are not (reason in parentheses):
 * <ul>
 *   <li>
 *     <code>file:BobsApp/Test.java</code> (the file name is relative
 *     and depend on the current directory)
 *   </li>
 *   <li>
 *     <code>jar:lib/vendorA.jar!com/vendora/LibraryClass.class</code>
 *     (the first half of the path depends on the current directory,
 *     whereas the component after ! is legal)
 *   </li>
 *   <li>
 *     <code>Test.java</code> (this URI depends on the current
 *     directory and does not have a schema)
 *   </li>
 *   <li>
 *     <code>jar:///C:/Documents%20and%20Settings/UncleBob/BobsApp/../lib/vendorA.jar!com/vendora/LibraryClass.class</code>
 *     (the path is not normalized)
 *   </li>
 * </ul>
 *
 * <p>
 *  文件管理器基于{@linkplain File java.io.File}。
 * 获取此类的实例的常见方法是使用{@linkplain JavaCompiler#getStandardFileManager getStandardFileManager},例如：。
 * 
 * <pre>
 *  JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); {@code DiagnosticCollector <JavaFileObject>}
 *  diagnostics = new {@code DiagnosticCollector <JavaFileObject>()}; StandardJavaFileManager fm = compi
 * ler.getStandardFileManager(diagnostics,null,null);。
 * </pre>
 * 
 *  此文件管理器创建表示常规{@linkplain文件文件},{@linkplain java.util.zip.ZipEntry zip文件条目}或类似基于文件系统的容器中的条目的文件对象。
 * 从实现此接口的文件管理器返回的任何文件对象必须遵守以下行为：。
 * 
 * <ul>
 * <li>
 *  文件名不必是规范的。
 * </li>
 * <li>
 *  用于表示常规文件的文件对象
 * <ul>
 * <li>
 *  方法<code> {@ linkplain FileObject#delete()} </code>等效于<code> {@ linkplain File#delete()} </code>
 * </li>
 * <li>
 *  方法<code> {@ linkplain FileObject#getLastModified()} </code>等效于<code> {@ linkplain File#lastModified()}
 *  </code>。
 * </li>
 * <li>
 *  方法<code> {@ linkplain FileObject#getCharContent(boolean)} </code>,<code> {@ linkplain FileObject#openInputStream()}
 *  </code> } </code>必须成功,如果以下将成功(忽略编码问题)：。
 * <blockquote>
 * <pre> new {@linkplain java.io.FileInputStream#FileInputStream(File)FileInputStream}(new {@linkplain File#File(java.net.URI)File}
 * ({@ linkplain FileObject fileObject}.{@linkplain FileObject#toUri ()toUri}()))</pre>。
 * </blockquote>
 * </li>
 * <li>
 *  如果以下操作成功(忽略编码问题),方法<code> {@ linkplain FileObject#openOutputStream()} </code>和<code> {@ linkplain FileObject#openWriter()}
 *  </code>。
 * <blockquote>
 *  <pre> new {@linkplain java.io.FileOutputStream#FileOutputStream(File)FileOutputStream}(new {@linkplain File#File(java.net.URI)File}
 * ({@ linkplain FileObject fileObject}.{@linkplain FileObject#toUri ()toUri}()))</pre>。
 * </blockquote>
 * </li>
 * </ul>
 * </li>
 * <li>
 *  从<code> {@ linkplain FileObject#toUri()} </code>返回的{@linkplain java.net.URI URI}
 * <ul>
 * <li>
 *  必须是{@linkplain java.net.URI#isAbsolute()absolute}(有一个模式),和
 * </li>
 * <li>
 *  必须具有{@linkplain java.net.URI#normalize()normalized} {@linkplain java.net.URI#getPath()path component),它可以在没有任何特定于进程的上下文(如当前目录)的情况下解析(文件名必须是绝对的)。
 * </li>
 * </ul>
 * </li>
 * </ul>
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface StandardJavaFileManager extends JavaFileManager {

    /**
     * Compares two file objects and return true if they represent the
     * same canonical file, zip file entry, or entry in any file
     * system based container.
     *
     * <p>
     *  根据这些规则,例如,允许以下URI：
     * <ul>
     * <li>
     *  <code> file：/// C：/Documents%20and%20Settings/UncleBob/BobsApp/Test.java </code>
     * </li>
     * <li>
     *  <code> jar：/// C：/Documents%20and%20Settings/UncleBob/lib/vendorA.jar！com / vendora / LibraryClass.c
     * lass </code>。
     * </li>
     * </ul>
     *  而这些不是(括号中的原因)：
     * <ul>
     * <li>
     *  <code>文件：BobsApp / Test.java </code>(文件名是相对的,取决于当前目录)
     * </li>
     * <li>
     * <code> jar：lib / vendorA.jar！com / vendora / LibraryClass.class </code>(路径的前半部分取决于当前目录,而！之后的组件是合法的)
     * </li>
     * <li>
     *  <code> Test.java </code>(此URI取决于当前目录,没有模式)
     * </li>
     * <li>
     *  <code> jar：/// C：/Documents%20and%20Settings/UncleBob/BobsApp/../lib/vendorA.jar！com / vendora / Lib
     * raryClass.class </code>(路径未标准化)。
     * </li>
     * </ul>
     * 
     * 
     * @param a a file object
     * @param b a file object
     * @return true if the given file objects represent the same
     * canonical file or zip file entry; false otherwise
     *
     * @throws IllegalArgumentException if either of the arguments
     * were created with another file manager implementation
     */
    boolean isSameFile(FileObject a, FileObject b);

    /**
     * Gets file objects representing the given files.
     *
     * <p>
     *  比较两个文件对象,如果它们表示相同的规范文件,zip文件条目或任何基于文件系统的容器中的条目,则返回true。
     * 
     * 
     * @param files a list of files
     * @return a list of file objects
     * @throws IllegalArgumentException if the list of files includes
     * a directory
     */
    Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(
        Iterable<? extends File> files);

    /**
     * Gets file objects representing the given files.
     * Convenience method equivalent to:
     *
     * <pre>
     *     getJavaFileObjectsFromFiles({@linkplain java.util.Arrays#asList Arrays.asList}(files))
     * </pre>
     *
     * <p>
     *  获取表示给定文件的文件对象。
     * 
     * 
     * @param files an array of files
     * @return a list of file objects
     * @throws IllegalArgumentException if the array of files includes
     * a directory
     * @throws NullPointerException if the given array contains null
     * elements
     */
    Iterable<? extends JavaFileObject> getJavaFileObjects(File... files);

    /**
     * Gets file objects representing the given file names.
     *
     * <p>
     *  获取表示给定文件的文件对象。方便方法等效于：
     * 
     * <pre>
     *  getJavaFileObjectsFromFiles({@ linkplain java.util.Arrays#asList Arrays.asList}(files))
     * </pre>
     * 
     * 
     * @param names a list of file names
     * @return a list of file objects
     * @throws IllegalArgumentException if the list of file names
     * includes a directory
     */
    Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(
        Iterable<String> names);

    /**
     * Gets file objects representing the given file names.
     * Convenience method equivalent to:
     *
     * <pre>
     *     getJavaFileObjectsFromStrings({@linkplain java.util.Arrays#asList Arrays.asList}(names))
     * </pre>
     *
     * <p>
     *  获取表示给定文件名的文件对象。
     * 
     * 
     * @param names a list of file names
     * @return a list of file objects
     * @throws IllegalArgumentException if the array of file names
     * includes a directory
     * @throws NullPointerException if the given array contains null
     * elements
     */
    Iterable<? extends JavaFileObject> getJavaFileObjects(String... names);

    /**
     * Associates the given path with the given location.  Any
     * previous value will be discarded.
     *
     * <p>
     *  获取表示给定文件名的文件对象。方便方法等效于：
     * 
     * <pre>
     *  getJavaFileObjectsFromStrings({@ linkplain java.util.Arrays#asList Arrays.asList}(names))
     * </pre>
     * 
     * 
     * @param location a location
     * @param path a list of files, if {@code null} use the default
     * path for this location
     * @see #getLocation
     * @throws IllegalArgumentException if location is an output
     * location and path does not contain exactly one element
     * @throws IOException if location is an output location and path
     * does not represent an existing directory
     */
    void setLocation(Location location, Iterable<? extends File> path)
        throws IOException;

    /**
     * Gets the path associated with the given location.
     *
     * <p>
     * 
     * @param location a location
     * @return a list of files or {@code null} if this location has no
     * associated path
     * @see #setLocation
     */
    Iterable<? extends File> getLocation(Location location);

}
