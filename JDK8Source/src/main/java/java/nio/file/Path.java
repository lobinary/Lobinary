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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

/**
 * An object that may be used to locate a file in a file system. It will
 * typically represent a system dependent file path.
 *
 * <p> A {@code Path} represents a path that is hierarchical and composed of a
 * sequence of directory and file name elements separated by a special separator
 * or delimiter. A <em>root component</em>, that identifies a file system
 * hierarchy, may also be present. The name element that is <em>farthest</em>
 * from the root of the directory hierarchy is the name of a file or directory.
 * The other name elements are directory names. A {@code Path} can represent a
 * root, a root and a sequence of names, or simply one or more name elements.
 * A {@code Path} is considered to be an <i>empty path</i> if it consists
 * solely of one name element that is empty. Accessing a file using an
 * <i>empty path</i> is equivalent to accessing the default directory of the
 * file system. {@code Path} defines the {@link #getFileName() getFileName},
 * {@link #getParent getParent}, {@link #getRoot getRoot}, and {@link #subpath
 * subpath} methods to access the path components or a subsequence of its name
 * elements.
 *
 * <p> In addition to accessing the components of a path, a {@code Path} also
 * defines the {@link #resolve(Path) resolve} and {@link #resolveSibling(Path)
 * resolveSibling} methods to combine paths. The {@link #relativize relativize}
 * method that can be used to construct a relative path between two paths.
 * Paths can be {@link #compareTo compared}, and tested against each other using
 * the {@link #startsWith startsWith} and {@link #endsWith endsWith} methods.
 *
 * <p> This interface extends {@link Watchable} interface so that a directory
 * located by a path can be {@link #register registered} with a {@link
 * WatchService} and entries in the directory watched. </p>
 *
 * <p> <b>WARNING:</b> This interface is only intended to be implemented by
 * those developing custom file system implementations. Methods may be added to
 * this interface in future releases. </p>
 *
 * <h2>Accessing Files</h2>
 * <p> Paths may be used with the {@link Files} class to operate on files,
 * directories, and other types of files. For example, suppose we want a {@link
 * java.io.BufferedReader} to read text from a file "{@code access.log}". The
 * file is located in a directory "{@code logs}" relative to the current working
 * directory and is UTF-8 encoded.
 * <pre>
 *     Path path = FileSystems.getDefault().getPath("logs", "access.log");
 *     BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
 * </pre>
 *
 * <a name="interop"></a><h2>Interoperability</h2>
 * <p> Paths associated with the default {@link
 * java.nio.file.spi.FileSystemProvider provider} are generally interoperable
 * with the {@link java.io.File java.io.File} class. Paths created by other
 * providers are unlikely to be interoperable with the abstract path names
 * represented by {@code java.io.File}. The {@link java.io.File#toPath toPath}
 * method may be used to obtain a {@code Path} from the abstract path name
 * represented by a {@code java.io.File} object. The resulting {@code Path} can
 * be used to operate on the same file as the {@code java.io.File} object. In
 * addition, the {@link #toFile toFile} method is useful to construct a {@code
 * File} from the {@code String} representation of a {@code Path}.
 *
 * <h2>Concurrency</h2>
 * <p> Implementations of this interface are immutable and safe for use by
 * multiple concurrent threads.
 *
 * <p>
 *  可用于在文件系统中查找文件的对象。它通常表示系统相关的文件路径。
 * 
 *  <p> {@code Path}表示一个层次化的路径,由目录和文件名元素序列组成,由特殊分隔符或分隔符分隔。还可以存在标识文件系统层次结构的<em>根组件</em>。
 * 距目录层次结构根目录<em>最远的名称元素</em>是文件或目录的名称。其他名称元素是目录名称。 {@code Path}可以表示根,根和名称序列,或者简单地表示一个或多个名称元素。
 * 如果{@code Path}仅包含一个名称元素为空,则被视为<i>空路径</i>。使用<i>空路径</i>访问文件等效于访问文件系统的默认目录。
 *  {@code Path}定义访问路径组件或子序列的{@link #getFileName()getFileName},{@link #getParent getParent},{@link #getRoot getRoot}
 * 和{@link #subpath subpath}的名称元素。
 * 如果{@code Path}仅包含一个名称元素为空,则被视为<i>空路径</i>。使用<i>空路径</i>访问文件等效于访问文件系统的默认目录。
 * 
 * <p>除了访问路径的组件,{@code Path}还定义了{@link #resolve(Path)resolve}和{@link #resolveSibling(Path)resolveSibling}
 * 方法来组合路径。
 *  {@link #relativize relativize}方法,可用于构建两个路径之间的相对路径。
 * 路径可以是{@link #compareTo comparing},并使用{@link #startsWith startsWith}和{@link #endsWith endsWith}方法彼此测试。
 *  {@link #relativize relativize}方法,可用于构建两个路径之间的相对路径。
 * 
 *  <p>此接口扩展{@link Watchable}接口,以便路径所在的目录可以{@link #register注册}与{@link WatchService}和所监视目录中的条目。 </p>
 * 
 *  <p> <b>警告：</b>此接口仅供开发自定义文件系统实现的用户使用。在将来的版本中,可以将方法添加到此接口。 </p>
 * 
 *  <h2>访问文件</h2> <p>路径可以与{@link Files}类一起使用,以处理文件,目录和其他类型的文件。
 * 例如,假设我们想要一个{@link java.io.BufferedReader}从文件"{@code access.log}"中读取文本。
 * 该文件位于相对于当前工作目录的目录"{@code logs}"中,并且采用UTF-8编码。
 * <pre>
 *  路径path = FileSystems.getDefault()。
 * getPath("logs","access.log"); BufferedReader reader = Files.newBufferedReader(path,StandardCharsets.U
 * TF_8);。
 *  路径path = FileSystems.getDefault()。
 * </pre>
 * 
 * <a name="interop"> </a> <h2>互操作性</h2> <p>与默认{@link java.nio.file.spi.FileSystemProvider provider}相关联的
 * 路径通常可与{@link java.io.File java.io.File}类。
 * 其他提供程序创建的路径不太可能与{@code java.io.File}表示的抽象路径名称可互操作。
 *  {@link java.io.File#toPath toPath}方法可用于从由{@code java.io.File}对象表示的抽象路径名称获取{@code Path}。
 * 生成的{@code Path}可以用于与{@code java.io.File}对象相同的文件。
 * 此外,{@link #toFile toFile}方法可用于从{@code Path}的{@code String}表示构建{@code File}。
 * 
 *  <h2>并发</h2> <p>此接口的实现是不可变的,并且对于多个并发线程使用是安全的。
 * 
 * 
 * @since 1.7
 * @see Paths
 */

public interface Path
    extends Comparable<Path>, Iterable<Path>, Watchable
{
    /**
     * Returns the file system that created this object.
     *
     * <p>
     *  返回创建此对象的文件系统。
     * 
     * 
     * @return  the file system that created this object
     */
    FileSystem getFileSystem();

    /**
     * Tells whether or not this path is absolute.
     *
     * <p> An absolute path is complete in that it doesn't need to be combined
     * with other path information in order to locate a file.
     *
     * <p>
     *  告诉这个路径是否是绝对路径。
     * 
     *  <p>绝对路径是完整的,因为它不需要与其他路径信息组合以定位文件。
     * 
     * 
     * @return  {@code true} if, and only if, this path is absolute
     */
    boolean isAbsolute();

    /**
     * Returns the root component of this path as a {@code Path} object,
     * or {@code null} if this path does not have a root component.
     *
     * <p>
     *  将此路径的根组件作为{@code Path}对象返回,如果此路径没有根组件,则返回{@code null}。
     * 
     * 
     * @return  a path representing the root component of this path,
     *          or {@code null}
     */
    Path getRoot();

    /**
     * Returns the name of the file or directory denoted by this path as a
     * {@code Path} object. The file name is the <em>farthest</em> element from
     * the root in the directory hierarchy.
     *
     * <p>
     *  将由此路径表示的文件或目录的名称返回为{@code Path}对象。文件名是目录层次结构中距根目录的<em>最远的</em>元素。
     * 
     * 
     * @return  a path representing the name of the file or directory, or
     *          {@code null} if this path has zero elements
     */
    Path getFileName();

    /**
     * Returns the <em>parent path</em>, or {@code null} if this path does not
     * have a parent.
     *
     * <p> The parent of this path object consists of this path's root
     * component, if any, and each element in the path except for the
     * <em>farthest</em> from the root in the directory hierarchy. This method
     * does not access the file system; the path or its parent may not exist.
     * Furthermore, this method does not eliminate special names such as "."
     * and ".." that may be used in some implementations. On UNIX for example,
     * the parent of "{@code /a/b/c}" is "{@code /a/b}", and the parent of
     * {@code "x/y/.}" is "{@code x/y}". This method may be used with the {@link
     * #normalize normalize} method, to eliminate redundant names, for cases where
     * <em>shell-like</em> navigation is required.
     *
     * <p> If this path has one or more elements, and no root component, then
     * this method is equivalent to evaluating the expression:
     * <blockquote><pre>
     * subpath(0,&nbsp;getNameCount()-1);
     * </pre></blockquote>
     *
     * <p>
     *  返回<em>父路径</em>或{@code null}(如果此路径没有父路径)。
     * 
     * <p>此路径对象的父对象包含此路径的根组件(如果有)和路径中每个元素(目录层次结构中距根目录<em>最远的</em>除外)。此方法不访问文件系统;路径或其父级可能不存在。此外,该方法不消除诸如"。
     * "的特殊名称。和"..",其可以在一些实现中使用。例如,在UNIX上,"{@code / a / b / c}"的父代为"{@code / a / b}",父代{@code"x / y /。
     * }"为"{ @code x / y}"。此方法可与{@link #normalize normalize}方法一起使用,以消除需要使用类似于shell的导航的情况下的冗余名称。
     * 
     *  <p>如果此路径具有一个或多个元素,并且没有根组件,则此方法等效于评估表达式：<blockquote> <pre> subpath(0,getNameCount() -  1); </pre> </blockquote>
     * 。
     * 
     * 
     * @return  a path representing the path's parent
     */
    Path getParent();

    /**
     * Returns the number of name elements in the path.
     *
     * <p>
     *  返回路径中的名称元素数。
     * 
     * 
     * @return  the number of elements in the path, or {@code 0} if this path
     *          only represents a root component
     */
    int getNameCount();

    /**
     * Returns a name element of this path as a {@code Path} object.
     *
     * <p> The {@code index} parameter is the index of the name element to return.
     * The element that is <em>closest</em> to the root in the directory hierarchy
     * has index {@code 0}. The element that is <em>farthest</em> from the root
     * has index {@link #getNameCount count}{@code -1}.
     *
     * <p>
     *  以{@code Path}对象的形式返回此路径的name元素。
     * 
     *  <p> {@code index}参数是要返回的名称元素的索引。与目录层次结构中的根最接近的元素</em>具有索引{@code 0}。
     * 距根的<em>最远</em>的元素具有索引{@link #getNameCount count} {@ code -1}。
     * 
     * 
     * @param   index
     *          the index of the element
     *
     * @return  the name element
     *
     * @throws  IllegalArgumentException
     *          if {@code index} is negative, {@code index} is greater than or
     *          equal to the number of elements, or this path has zero name
     *          elements
     */
    Path getName(int index);

    /**
     * Returns a relative {@code Path} that is a subsequence of the name
     * elements of this path.
     *
     * <p> The {@code beginIndex} and {@code endIndex} parameters specify the
     * subsequence of name elements. The name that is <em>closest</em> to the root
     * in the directory hierarchy has index {@code 0}. The name that is
     * <em>farthest</em> from the root has index {@link #getNameCount
     * count}{@code -1}. The returned {@code Path} object has the name elements
     * that begin at {@code beginIndex} and extend to the element at index {@code
     * endIndex-1}.
     *
     * <p>
     *  返回一个相对的{@code Path},它是此路径的名称元素的子序列。
     * 
     * <p> {@code beginIndex}和{@code endIndex}参数指定名称元素的子序列。在目录层次结构中与根最接近的名称</em>具有索引{@code 0}。
     * 从根开始的<em>最远的</em>名称具有索引{@link #getNameCount count} {@ code -1}。
     * 返回的{@code Path}对象的名称元素从{@code beginIndex}开始,并延伸到索引{@code endIndex-1}处的元素。
     * 
     * 
     * @param   beginIndex
     *          the index of the first element, inclusive
     * @param   endIndex
     *          the index of the last element, exclusive
     *
     * @return  a new {@code Path} object that is a subsequence of the name
     *          elements in this {@code Path}
     *
     * @throws  IllegalArgumentException
     *          if {@code beginIndex} is negative, or greater than or equal to
     *          the number of elements. If {@code endIndex} is less than or
     *          equal to {@code beginIndex}, or larger than the number of elements.
     */
    Path subpath(int beginIndex, int endIndex);

    /**
     * Tests if this path starts with the given path.
     *
     * <p> This path <em>starts</em> with the given path if this path's root
     * component <em>starts</em> with the root component of the given path,
     * and this path starts with the same name elements as the given path.
     * If the given path has more name elements than this path then {@code false}
     * is returned.
     *
     * <p> Whether or not the root component of this path starts with the root
     * component of the given path is file system specific. If this path does
     * not have a root component and the given path has a root component then
     * this path does not start with the given path.
     *
     * <p> If the given path is associated with a different {@code FileSystem}
     * to this path then {@code false} is returned.
     *
     * <p>
     *  测试此路径是否以给定路径开头。
     * 
     *  <p>如果此路径的根组件<em>开始</em>,此路径</em>将使用给定路径启动</em>,并且此路径以与指定路径的根组件相同的名称元素开头,给定路径。
     * 如果给定路径具有比此路径更多的名称元素,则返回{@code false}。
     * 
     *  <p>此路径的根组件是否以给定路径的根组件开头,是文件系统特定的。如果此路径没有根组件,并且给定路径具有根组件,则此路径不以给定路径开始。
     * 
     *  <p>如果给定路径与不同的{@code FileSystem}与此路径相关联,则返回{@code false}。
     * 
     * 
     * @param   other
     *          the given path
     *
     * @return  {@code true} if this path starts with the given path; otherwise
     *          {@code false}
     */
    boolean startsWith(Path other);

    /**
     * Tests if this path starts with a {@code Path}, constructed by converting
     * the given path string, in exactly the manner specified by the {@link
     * #startsWith(Path) startsWith(Path)} method. On UNIX for example, the path
     * "{@code foo/bar}" starts with "{@code foo}" and "{@code foo/bar}". It
     * does not start with "{@code f}" or "{@code fo}".
     *
     * <p>
     *  测试此路径是否以通过转换给定路径字符串构造的{@code Path}开始,完全按照{@link #startsWith(Path)startsWith(Path)}方法指定的方式。
     * 例如,在UNIX上,路径"{@code foo / bar}"以"{@code foo}"和"{@code foo / bar}"开头。它不以"{@code f}"或"{@code fo}"开头。
     * 
     * 
     * @param   other
     *          the given path string
     *
     * @return  {@code true} if this path starts with the given path; otherwise
     *          {@code false}
     *
     * @throws  InvalidPathException
     *          If the path string cannot be converted to a Path.
     */
    boolean startsWith(String other);

    /**
     * Tests if this path ends with the given path.
     *
     * <p> If the given path has <em>N</em> elements, and no root component,
     * and this path has <em>N</em> or more elements, then this path ends with
     * the given path if the last <em>N</em> elements of each path, starting at
     * the element farthest from the root, are equal.
     *
     * <p> If the given path has a root component then this path ends with the
     * given path if the root component of this path <em>ends with</em> the root
     * component of the given path, and the corresponding elements of both paths
     * are equal. Whether or not the root component of this path ends with the
     * root component of the given path is file system specific. If this path
     * does not have a root component and the given path has a root component
     * then this path does not end with the given path.
     *
     * <p> If the given path is associated with a different {@code FileSystem}
     * to this path then {@code false} is returned.
     *
     * <p>
     * 测试此路径是否以给定路径结束。
     * 
     *  <p>如果给定路径具有<em> N </em>个元素,且没有根组件,并且此路径具有<em> N </em>个或更多元素,则此路径以给定路径结束从距离根最远的元素开始的每个路径的<em> N </em>
     * 元素相等。
     * 
     *  <p>如果给定路径具有根组件,则如果该路径的根组件以给定路径的根组件结束,则该路径以给定路径结束,并且两个路径的对应元素是相等的。此路径的根组件是否以给定路径的根组件结尾是文件系统特定的。
     * 如果此路径没有根组件,并且给定路径具有根组件,则此路径不以给定路径结束。
     * 
     *  <p>如果给定路径与不同的{@code FileSystem}与此路径相关联,则返回{@code false}。
     * 
     * 
     * @param   other
     *          the given path
     *
     * @return  {@code true} if this path ends with the given path; otherwise
     *          {@code false}
     */
    boolean endsWith(Path other);

    /**
     * Tests if this path ends with a {@code Path}, constructed by converting
     * the given path string, in exactly the manner specified by the {@link
     * #endsWith(Path) endsWith(Path)} method. On UNIX for example, the path
     * "{@code foo/bar}" ends with "{@code foo/bar}" and "{@code bar}". It does
     * not end with "{@code r}" or "{@code /bar}". Note that trailing separators
     * are not taken into account, and so invoking this method on the {@code
     * Path}"{@code foo/bar}" with the {@code String} "{@code bar/}" returns
     * {@code true}.
     *
     * <p>
     *  测试此路径是否以通过转换给定路径字符串构造的{@code Path}以{@link #endsWith(Path)endsWith(Path)}方法指定的方式结束。
     * 例如,在UNIX上,路径"{@code foo / bar}"以"{@code foo / bar}"和"{@code bar}"结尾。
     * 它不以"{@code r}​​"或"{@code / bar}"结尾。
     * 请注意,不考虑尾随分隔符,因此使用{@code String}"{@code bar /}"在{@code Path}"{@ code foo / bar}"上调用此方法会返回{@code真正}。
     * 
     * 
     * @param   other
     *          the given path string
     *
     * @return  {@code true} if this path ends with the given path; otherwise
     *          {@code false}
     *
     * @throws  InvalidPathException
     *          If the path string cannot be converted to a Path.
     */
    boolean endsWith(String other);

    /**
     * Returns a path that is this path with redundant name elements eliminated.
     *
     * <p> The precise definition of this method is implementation dependent but
     * in general it derives from this path, a path that does not contain
     * <em>redundant</em> name elements. In many file systems, the "{@code .}"
     * and "{@code ..}" are special names used to indicate the current directory
     * and parent directory. In such file systems all occurrences of "{@code .}"
     * are considered redundant. If a "{@code ..}" is preceded by a
     * non-"{@code ..}" name then both names are considered redundant (the
     * process to identify such names is repeated until it is no longer
     * applicable).
     *
     * <p> This method does not access the file system; the path may not locate
     * a file that exists. Eliminating "{@code ..}" and a preceding name from a
     * path may result in the path that locates a different file than the original
     * path. This can arise when the preceding name is a symbolic link.
     *
     * <p>
     *  返回一个路径,其中消除了冗余名称元素的此路径。
     * 
     * <p>此方法的精确定义与实现相关,但通常来源于此路径,即不包含<em>冗余名称元素的路径。在许多文件系统中,"{@code。}"和"{@code ..}"是用于指示当前目录和父目录的特殊名称。
     * 在这样的文件系统中,所有出现的"{@code。}"被认为是多余的。
     * 如果"{@code ..}"前面有一个非"{@ code ..}"名称,那么这两个名称将被视为多余的(识别此类名称的过程将重复,直到不再适用为止)。
     * 
     *  <p>此方法不访问文件系统;该路径可能找不到存在的文件。从路径中删除"{@code ..}"和前面的名称可能会导致找到与原始路径不同的文件的路径。当前面的名称是符号链接时,会出现这种情况。
     * 
     * 
     * @return  the resulting path or this path if it does not contain
     *          redundant name elements; an empty path is returned if this path
     *          does have a root component and all name elements are redundant
     *
     * @see #getParent
     * @see #toRealPath
     */
    Path normalize();

    // -- resolution and relativization --

    /**
     * Resolve the given path against this path.
     *
     * <p> If the {@code other} parameter is an {@link #isAbsolute() absolute}
     * path then this method trivially returns {@code other}. If {@code other}
     * is an <i>empty path</i> then this method trivially returns this path.
     * Otherwise this method considers this path to be a directory and resolves
     * the given path against this path. In the simplest case, the given path
     * does not have a {@link #getRoot root} component, in which case this method
     * <em>joins</em> the given path to this path and returns a resulting path
     * that {@link #endsWith ends} with the given path. Where the given path has
     * a root component then resolution is highly implementation dependent and
     * therefore unspecified.
     *
     * <p>
     *  根据此路径解析给定路径。
     * 
     * <p>如果{@code other}参数是{@link #isAbsolute()absolute}路径,那么此方法会简单地返回{@code other}。
     * 如果{@code other}是一个<i>空路径</i>,则此方法会简单地返回此路径。否则,此方法将此路径视为目录,并根据此路径解析给定路径。
     * 在最简单的情况下,给定路径没有{@link #getRoot root}组件,在这种情况下,此方法<em>连接</em>给定路径到此路径,并返回结果路径{@link# endsWith ends}与给定
     * 的路径。
     * 如果{@code other}是一个<i>空路径</i>,则此方法会简单地返回此路径。否则,此方法将此路径视为目录,并根据此路径解析给定路径。
     * 在给定路径具有根分量的情况下,分辨率高度依赖于实现,因此未指定。
     * 
     * 
     * @param   other
     *          the path to resolve against this path
     *
     * @return  the resulting path
     *
     * @see #relativize
     */
    Path resolve(Path other);

    /**
     * Converts a given path string to a {@code Path} and resolves it against
     * this {@code Path} in exactly the manner specified by the {@link
     * #resolve(Path) resolve} method. For example, suppose that the name
     * separator is "{@code /}" and a path represents "{@code foo/bar}", then
     * invoking this method with the path string "{@code gus}" will result in
     * the {@code Path} "{@code foo/bar/gus}".
     *
     * <p>
     *  将给定的路径字符串转换为{@code Path},并以{@link #resolve(Path)resolve}方法指定的方式将其解析为{@code Path}。
     * 例如,假设名称分隔符是"{@code /}",路径代表"{@code foo / bar}",则使用路径字符串"{@code gus}"调用此方法将导致{ @code Path}"{@code foo / bar / gus}
     * "。
     *  将给定的路径字符串转换为{@code Path},并以{@link #resolve(Path)resolve}方法指定的方式将其解析为{@code Path}。
     * 
     * 
     * @param   other
     *          the path string to resolve against this path
     *
     * @return  the resulting path
     *
     * @throws  InvalidPathException
     *          if the path string cannot be converted to a Path.
     *
     * @see FileSystem#getPath
     */
    Path resolve(String other);

    /**
     * Resolves the given path against this path's {@link #getParent parent}
     * path. This is useful where a file name needs to be <i>replaced</i> with
     * another file name. For example, suppose that the name separator is
     * "{@code /}" and a path represents "{@code dir1/dir2/foo}", then invoking
     * this method with the {@code Path} "{@code bar}" will result in the {@code
     * Path} "{@code dir1/dir2/bar}". If this path does not have a parent path,
     * or {@code other} is {@link #isAbsolute() absolute}, then this method
     * returns {@code other}. If {@code other} is an empty path then this method
     * returns this path's parent, or where this path doesn't have a parent, the
     * empty path.
     *
     * <p>
     * 根据此路径的{@link #getParent parent}路径解析指定的路径。这在文件名需要用另一个文件名替换<i> </i>时非常有用。
     * 例如,假设名称分隔符是"{@code /}",路径代表"{@code dir1 / dir2 / foo}",然后使用{@code Path} {@code bar}将导致{@code Path}"{@code dir1 / dir2 / bar}
     * "。
     * 根据此路径的{@link #getParent parent}路径解析指定的路径。这在文件名需要用另一个文件名替换<i> </i>时非常有用。
     * 如果此路径没有父路径,或{@code other}是{@link #isAbsolute()absolute},则此方法返回{@code other}。
     * 如果{@code other}是一个空路径,那么此方法将返回此路径的父路径,或者此路径没有父路径,即空路径。
     * 
     * 
     * @param   other
     *          the path to resolve against this path's parent
     *
     * @return  the resulting path
     *
     * @see #resolve(Path)
     */
    Path resolveSibling(Path other);

    /**
     * Converts a given path string to a {@code Path} and resolves it against
     * this path's {@link #getParent parent} path in exactly the manner
     * specified by the {@link #resolveSibling(Path) resolveSibling} method.
     *
     * <p>
     *  将给定的路径字符串转换为{@code Path},并按照{@link #getParent parent}路径完全按照{@link #resolveSibling(Path)resolveSibling}
     * 方法指定的方式将其解析。
     * 
     * 
     * @param   other
     *          the path string to resolve against this path's parent
     *
     * @return  the resulting path
     *
     * @throws  InvalidPathException
     *          if the path string cannot be converted to a Path.
     *
     * @see FileSystem#getPath
     */
    Path resolveSibling(String other);

    /**
     * Constructs a relative path between this path and a given path.
     *
     * <p> Relativization is the inverse of {@link #resolve(Path) resolution}.
     * This method attempts to construct a {@link #isAbsolute relative} path
     * that when {@link #resolve(Path) resolved} against this path, yields a
     * path that locates the same file as the given path. For example, on UNIX,
     * if this path is {@code "/a/b"} and the given path is {@code "/a/b/c/d"}
     * then the resulting relative path would be {@code "c/d"}. Where this
     * path and the given path do not have a {@link #getRoot root} component,
     * then a relative path can be constructed. A relative path cannot be
     * constructed if only one of the paths have a root component. Where both
     * paths have a root component then it is implementation dependent if a
     * relative path can be constructed. If this path and the given path are
     * {@link #equals equal} then an <i>empty path</i> is returned.
     *
     * <p> For any two {@link #normalize normalized} paths <i>p</i> and
     * <i>q</i>, where <i>q</i> does not have a root component,
     * <blockquote>
     *   <i>p</i><tt>.relativize(</tt><i>p</i><tt>.resolve(</tt><i>q</i><tt>)).equals(</tt><i>q</i><tt>)</tt>
     * </blockquote>
     *
     * <p> When symbolic links are supported, then whether the resulting path,
     * when resolved against this path, yields a path that can be used to locate
     * the {@link Files#isSameFile same} file as {@code other} is implementation
     * dependent. For example, if this path is  {@code "/a/b"} and the given
     * path is {@code "/a/x"} then the resulting relative path may be {@code
     * "../x"}. If {@code "b"} is a symbolic link then is implementation
     * dependent if {@code "a/b/../x"} would locate the same file as {@code "/a/x"}.
     *
     * <p>
     *  构造此路径和给定路径之间的相对路径。
     * 
     * <p>相对性是{@link #resolve(Path)resolution}的倒数。
     * 此方法尝试构造{@link #isAbsolute relative}路径,当{@link #resolve(Path)已解决}对此路径时,将生成一个路径,该路径定位与给定路径相同的文件。
     * 例如,在UNIX上,如果此路径是{@code"/ a / b"}并且给定路径是{@code"/ a / b / c / d"},则生成的相对路径将是{@code"光盘"}。
     * 在这个路径和给定路径没有{@link #getRoot root}组件的情况下,可以构造相对路径。如果只有一个路径具有根组件,则不能构造相对路径。
     * 在两个路径都具有根组件的情况下,如果可以构建相对路径,则它是依赖于实现的。如果此路径和给定路径是{@link #equals equal},则返回<i>空路径</i>。
     * 
     *  <p>对于任何两个{@link #normalize normalized}路径p </i>和<i> q </i>,其中<i> q </i>没有根组件,
     * <blockquote>
     *  <i> p </i> <tt> .relativize(</tt> <i> p </i> <tt> .resolve(</tt> <i> q </i> <tt>))。
     *  equals(</tt> <i> q </i> <tt>)</tt>。
     * </blockquote>
     * 
     * <p>当支持符号链接时,当根据此路径解析时,生成的路径是否可用于定位与{@code other}相关的{@link Files#isSameFile same}文件的路径是与实现相关的。
     * 例如,如果这个路径是{@code"/ a / b"}并且给定路径是{@code"/ a / x"},则所得到的相对路径可以是{@code"../x"}。
     * 如果{@code"b"}是一个符号链接,那么依赖于实现,如果{@code"a / b /../ x"}定位与{@code"/ a / x"}相同的文件。
     * 
     * 
     * @param   other
     *          the path to relativize against this path
     *
     * @return  the resulting relative path, or an empty path if both paths are
     *          equal
     *
     * @throws  IllegalArgumentException
     *          if {@code other} is not a {@code Path} that can be relativized
     *          against this path
     */
    Path relativize(Path other);

    /**
     * Returns a URI to represent this path.
     *
     * <p> This method constructs an absolute {@link URI} with a {@link
     * URI#getScheme() scheme} equal to the URI scheme that identifies the
     * provider. The exact form of the scheme specific part is highly provider
     * dependent.
     *
     * <p> In the case of the default provider, the URI is hierarchical with
     * a {@link URI#getPath() path} component that is absolute. The query and
     * fragment components are undefined. Whether the authority component is
     * defined or not is implementation dependent. There is no guarantee that
     * the {@code URI} may be used to construct a {@link java.io.File java.io.File}.
     * In particular, if this path represents a Universal Naming Convention (UNC)
     * path, then the UNC server name may be encoded in the authority component
     * of the resulting URI. In the case of the default provider, and the file
     * exists, and it can be determined that the file is a directory, then the
     * resulting {@code URI} will end with a slash.
     *
     * <p> The default provider provides a similar <em>round-trip</em> guarantee
     * to the {@link java.io.File} class. For a given {@code Path} <i>p</i> it
     * is guaranteed that
     * <blockquote><tt>
     * {@link Paths#get(URI) Paths.get}(</tt><i>p</i><tt>.toUri()).equals(</tt><i>p</i>
     * <tt>.{@link #toAbsolutePath() toAbsolutePath}())</tt>
     * </blockquote>
     * so long as the original {@code Path}, the {@code URI}, and the new {@code
     * Path} are all created in (possibly different invocations of) the same
     * Java virtual machine. Whether other providers make any guarantees is
     * provider specific and therefore unspecified.
     *
     * <p> When a file system is constructed to access the contents of a file
     * as a file system then it is highly implementation specific if the returned
     * URI represents the given path in the file system or it represents a
     * <em>compound</em> URI that encodes the URI of the enclosing file system.
     * A format for compound URIs is not defined in this release; such a scheme
     * may be added in a future release.
     *
     * <p>
     *  返回一个URI以表示此路径。
     * 
     *  <p>此方法使用等于标识提供程序的URI方案的{@link URI#getScheme()方案}构造绝对{@link URI}。方案特定部分的确切形式高度依赖于提供商。
     * 
     *  <p>在默认提供程序的情况下,URI是具有{@link URI#getPath()path}组件的层次结构,它是绝对的。查询和片段组件未定义。权限组件是否被定义是依赖于实现的。
     * 不能保证{@code URI}可以用于构造一个{@link java.io.File java.io.File}。
     * 特别地,如果该路径表示通用命名约定(UNC)路径,则UNC服务器名称可以被编码在所得到的URI的权限组件中。
     * 在默认提供者的情况下,并且文件存在,并且可以确定该文件是目录,则所得到的{@code URI}将以斜杠结束。
     * 
     * <p>默认提供商向{@link java.io.File}类提供了类似的<em>往返</em>保证。
     * 对于给定的{@code Path} <i> p </i>,保证<blockquote> <tt> {@link Paths#get(URI)Paths.get}(</tt> <i> p < i> <tt>
     *  .toUri())。
     * <p>默认提供商向{@link java.io.File}类提供了类似的<em>往返</em>保证。equals(</tt> <i> p </i> <tt>。
     * {@ link #toAbsolutePath()toAbsolutePath}())</tt>。
     * </blockquote>
     *  只要原始的{@code Path},{@code URI}和新的{@code Path}都是在同一个Java虚拟机中创建的(可能是不同的调用)。其他提供者是否提供任何保证是提供者特定的,因此未指定。
     * 
     *  <p>当文件系统被构造为作为文件系统访问文件的内容时,如果返回的URI表示文件系统中的给定路径或者它代表一个复合<em> </em>用于编码封闭文件系统的URI的URI。
     * 本版本中未定义复合URI的格式;这样的方案可以在将来的版本中添加。
     * 
     * 
     * @return  the URI representing this path
     *
     * @throws  java.io.IOError
     *          if an I/O error occurs obtaining the absolute path, or where a
     *          file system is constructed to access the contents of a file as
     *          a file system, and the URI of the enclosing file system cannot be
     *          obtained
     *
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager
     *          is installed, the {@link #toAbsolutePath toAbsolutePath} method
     *          throws a security exception.
     */
    URI toUri();

    /**
     * Returns a {@code Path} object representing the absolute path of this
     * path.
     *
     * <p> If this path is already {@link Path#isAbsolute absolute} then this
     * method simply returns this path. Otherwise, this method resolves the path
     * in an implementation dependent manner, typically by resolving the path
     * against a file system default directory. Depending on the implementation,
     * this method may throw an I/O error if the file system is not accessible.
     *
     * <p>
     *  返回表示此路径的绝对路径的{@code Path}对象。
     * 
     *  <p>如果此路径已经是{@l​​ink Path#isAbsolute absolute},则此方法只返回此路径。否则,此方法以实现相关方式解析路径,通常通过根据文件系统缺省目录解析路径。
     * 根据实现,如果文件系统不可访问,此方法可能会引发I / O错误。
     * 
     * 
     * @return  a {@code Path} object representing the absolute path
     *
     * @throws  java.io.IOError
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager
     *          is installed, and this path is not absolute, then the security
     *          manager's {@link SecurityManager#checkPropertyAccess(String)
     *          checkPropertyAccess} method is invoked to check access to the
     *          system property {@code user.dir}
     */
    Path toAbsolutePath();

    /**
     * Returns the <em>real</em> path of an existing file.
     *
     * <p> The precise definition of this method is implementation dependent but
     * in general it derives from this path, an {@link #isAbsolute absolute}
     * path that locates the {@link Files#isSameFile same} file as this path, but
     * with name elements that represent the actual name of the directories
     * and the file. For example, where filename comparisons on a file system
     * are case insensitive then the name elements represent the names in their
     * actual case. Additionally, the resulting path has redundant name
     * elements removed.
     *
     * <p> If this path is relative then its absolute path is first obtained,
     * as if by invoking the {@link #toAbsolutePath toAbsolutePath} method.
     *
     * <p> The {@code options} array may be used to indicate how symbolic links
     * are handled. By default, symbolic links are resolved to their final
     * target. If the option {@link LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS} is
     * present then this method does not resolve symbolic links.
     *
     * Some implementations allow special names such as "{@code ..}" to refer to
     * the parent directory. When deriving the <em>real path</em>, and a
     * "{@code ..}" (or equivalent) is preceded by a non-"{@code ..}" name then
     * an implementation will typically cause both names to be removed. When
     * not resolving symbolic links and the preceding name is a symbolic link
     * then the names are only removed if it guaranteed that the resulting path
     * will locate the same file as this path.
     *
     * <p>
     *  返回现有文件的<em> real </em>路径。
     * 
     * <p>这个方法的精确定义是依赖于实现的,但是一般来说,它从这个路径派生出{@link #isAbsolute absolute}路径,该路径定位{@link Files#isSameFile same}
     * 文件作为此路径,但具有名称元素它们表示目录和文件的实际名称。
     * 例如,如果文件系统上的文件名比较不区分大小写,则名称元素表示其实际情况下的名称。此外,生成的路径具有删除的冗余名称元素。
     * 
     *  <p>如果此路径是相对的,则首先获得其绝对路径,如同通过调用{@link #toAbsolutePath toAbsolutePath}方法。
     * 
     *  <p> {@code options}数组可用于指示如何处理符号链接。默认情况下,符号链接解析为其最终目标。
     * 如果存在{@link LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS}选项,则此方法不会解析符号链接。
     * 
     *  一些实现允许使用特殊名称,例如"{@code ..}"来引用父目录。
     * 当导出实际路径</em>,并且一个"{@code ..}"(或等价物)前面有一个非"{@ code ..}"名称时,实现通常会导致两个名称即将被删除。
     * 当不解析符号链接时,前面的名称是一个符号链接,那么只有在保证结果路径将找到与此路径相同的文件时才会删除这些名称。
     * 
     * 
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  an absolute path represent the <em>real</em> path of the file
     *          located by this object
     *
     * @throws  IOException
     *          if the file does not exist or an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager
     *          is installed, its {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file, and where
     *          this path is not absolute, its {@link SecurityManager#checkPropertyAccess(String)
     *          checkPropertyAccess} method is invoked to check access to the
     *          system property {@code user.dir}
     */
    Path toRealPath(LinkOption... options) throws IOException;

    /**
     * Returns a {@link File} object representing this path. Where this {@code
     * Path} is associated with the default provider, then this method is
     * equivalent to returning a {@code File} object constructed with the
     * {@code String} representation of this path.
     *
     * <p> If this path was created by invoking the {@code File} {@link
     * File#toPath toPath} method then there is no guarantee that the {@code
     * File} object returned by this method is {@link #equals equal} to the
     * original {@code File}.
     *
     * <p>
     * 返回表示此路径的{@link File}对象。在{@code Path}与默认提供程序相关联的情况下,此方法等同于返回使用此路径的{@code String}表示构造的{@code File}对象。
     * 
     *  <p>如果此路径是通过调用{@code File} {@link File#toPath toPath}方法创建的,那么不能保证此方法返回的{@code File}对象是{@link #equals equal}
     * 到原来的{@code File}。
     * 
     * 
     * @return  a {@code File} object representing this path
     *
     * @throws  UnsupportedOperationException
     *          if this {@code Path} is not associated with the default provider
     */
    File toFile();

    // -- watchable --

    /**
     * Registers the file located by this path with a watch service.
     *
     * <p> In this release, this path locates a directory that exists. The
     * directory is registered with the watch service so that entries in the
     * directory can be watched. The {@code events} parameter is the events to
     * register and may contain the following events:
     * <ul>
     *   <li>{@link StandardWatchEventKinds#ENTRY_CREATE ENTRY_CREATE} -
     *       entry created or moved into the directory</li>
     *   <li>{@link StandardWatchEventKinds#ENTRY_DELETE ENTRY_DELETE} -
     *        entry deleted or moved out of the directory</li>
     *   <li>{@link StandardWatchEventKinds#ENTRY_MODIFY ENTRY_MODIFY} -
     *        entry in directory was modified</li>
     * </ul>
     *
     * <p> The {@link WatchEvent#context context} for these events is the
     * relative path between the directory located by this path, and the path
     * that locates the directory entry that is created, deleted, or modified.
     *
     * <p> The set of events may include additional implementation specific
     * event that are not defined by the enum {@link StandardWatchEventKinds}
     *
     * <p> The {@code modifiers} parameter specifies <em>modifiers</em> that
     * qualify how the directory is registered. This release does not define any
     * <em>standard</em> modifiers. It may contain implementation specific
     * modifiers.
     *
     * <p> Where a file is registered with a watch service by means of a symbolic
     * link then it is implementation specific if the watch continues to depend
     * on the existence of the symbolic link after it is registered.
     *
     * <p>
     *  使用手表服务注册此路径所在的文件。
     * 
     *  <p>在此版本中,此路径查找存在的目录。该目录已注册到监视服务,以便可以监视目录中的条目。 {@code events}参数是要注册的事件,可能包含以下事件：
     * <ul>
     *  <li> {@ link StandardWatchEventKinds#ENTRY_CREATE ENTRY_CREATE}  - 创建或移动到目录中的条目</li> <li> {@ link StandardWatchEventKinds#ENTRY_DELETE ENTRY_DELETE}
     *   - 条目已删除或移出目录</li> <li> {@link StandardWatchEventKinds#ENTRY_MODIFY ENTRY_MODIFY}  - 目录中的条目已修改</li>。
     * </ul>
     * 
     *  <p>这些事件的{@link WatchEvent#context context}是此路径所在的目录与定位创建,删除或修改的目录条目的路径之间的相对路径。
     * 
     *  <p>事件集合可能包含未由枚举{@link StandardWatchEventKinds}定义的其他实现特定事件
     * 
     * <p> {@code modifiers}参数指定限定目录注册方式的<em>修饰符</em>。此版本没有定义任何<em>标准</em>修饰符。它可以包含实现特定的修饰符。
     * 
     *  <p>当通过符号链接向观察服务注册文件时,如果在注册之后手表继续依赖于符号链接的存在,则它是实现特定的。
     * 
     * 
     * @param   watcher
     *          the watch service to which this object is to be registered
     * @param   events
     *          the events for which this object should be registered
     * @param   modifiers
     *          the modifiers, if any, that modify how the object is registered
     *
     * @return  a key representing the registration of this object with the
     *          given watch service
     *
     * @throws  UnsupportedOperationException
     *          if unsupported events or modifiers are specified
     * @throws  IllegalArgumentException
     *          if an invalid combination of events or modifiers is specified
     * @throws  ClosedWatchServiceException
     *          if the watch service is closed
     * @throws  NotDirectoryException
     *          if the file is registered to watch the entries in a directory
     *          and the file is not a directory  <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     */
    @Override
    WatchKey register(WatchService watcher,
                      WatchEvent.Kind<?>[] events,
                      WatchEvent.Modifier... modifiers)
        throws IOException;

    /**
     * Registers the file located by this path with a watch service.
     *
     * <p> An invocation of this method behaves in exactly the same way as the
     * invocation
     * <pre>
     *     watchable.{@link #register(WatchService,WatchEvent.Kind[],WatchEvent.Modifier[]) register}(watcher, events, new WatchEvent.Modifier[0]);
     * </pre>
     *
     * <p> <b>Usage Example:</b>
     * Suppose we wish to register a directory for entry create, delete, and modify
     * events:
     * <pre>
     *     Path dir = ...
     *     WatchService watcher = ...
     *
     *     WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
     * </pre>
     * <p>
     *  使用手表服务注册此路径所在的文件。
     * 
     *  <p>此方法的调用行为与调用完全相同
     * <pre>
     *  看门人。
     * {@ link #register(WatchService,WatchEvent.Kind [],WatchEvent.Modifier [])register}(watcher,events,new
     *  WatchEvent.Modifier [0]);。
     *  看门人。
     * </pre>
     * 
     *  <p> <b>用法示例：</b>假设我们希望为创建,删除和修改事件注册目录：
     * <pre>
     *  Path dir = ... WatchService watcher = ...
     * 
     *  WatchKey key = dir.register(watcher,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
     * </pre>
     * 
     * @param   watcher
     *          The watch service to which this object is to be registered
     * @param   events
     *          The events for which this object should be registered
     *
     * @return  A key representing the registration of this object with the
     *          given watch service
     *
     * @throws  UnsupportedOperationException
     *          If unsupported events are specified
     * @throws  IllegalArgumentException
     *          If an invalid combination of events is specified
     * @throws  ClosedWatchServiceException
     *          If the watch service is closed
     * @throws  NotDirectoryException
     *          If the file is registered to watch the entries in a directory
     *          and the file is not a directory  <i>(optional specific exception)</i>
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     */
    @Override
    WatchKey register(WatchService watcher,
                      WatchEvent.Kind<?>... events)
        throws IOException;

    // -- Iterable --

    /**
     * Returns an iterator over the name elements of this path.
     *
     * <p> The first element returned by the iterator represents the name
     * element that is closest to the root in the directory hierarchy, the
     * second element is the next closest, and so on. The last element returned
     * is the name of the file or directory denoted by this path. The {@link
     * #getRoot root} component, if present, is not returned by the iterator.
     *
     * <p>
     *  返回此路径的名称元素的迭代器。
     * 
     *  <p>迭代器返回的第一个元素表示最接近目录层次结构中的根的name元素,第二个元素是下一个最接近的元素,依此类推。返回的最后一个元素是由此路径表示的文件或目录的名称。
     *  {@link #getRoot root}组件(如果存在)不会由迭代器返回。
     * 
     * 
     * @return  an iterator over the name elements of this path.
     */
    @Override
    Iterator<Path> iterator();

    // -- compareTo/equals/hashCode --

    /**
     * Compares two abstract paths lexicographically. The ordering defined by
     * this method is provider specific, and in the case of the default
     * provider, platform specific. This method does not access the file system
     * and neither file is required to exist.
     *
     * <p> This method may not be used to compare paths that are associated
     * with different file system providers.
     *
     * <p>
     * 按字典顺序比较两个抽象路径。此方法定义的顺序是特定于提供程序的,在默认提供程序的情况下,特定于平台。此方法不访问文件系统,两个文件都不需要存在。
     * 
     *  <p>此方法不能用于比较与不同文件系统提供程序关联的路径。
     * 
     * 
     * @param   other  the path compared to this path.
     *
     * @return  zero if the argument is {@link #equals equal} to this path, a
     *          value less than zero if this path is lexicographically less than
     *          the argument, or a value greater than zero if this path is
     *          lexicographically greater than the argument
     *
     * @throws  ClassCastException
     *          if the paths are associated with different providers
     */
    @Override
    int compareTo(Path other);

    /**
     * Tests this path for equality with the given object.
     *
     * <p> If the given object is not a Path, or is a Path associated with a
     * different {@code FileSystem}, then this method returns {@code false}.
     *
     * <p> Whether or not two path are equal depends on the file system
     * implementation. In some cases the paths are compared without regard
     * to case, and others are case sensitive. This method does not access the
     * file system and the file is not required to exist. Where required, the
     * {@link Files#isSameFile isSameFile} method may be used to check if two
     * paths locate the same file.
     *
     * <p> This method satisfies the general contract of the {@link
     * java.lang.Object#equals(Object) Object.equals} method. </p>
     *
     * <p>
     *  测试此路径与给定对象的相等性。
     * 
     *  <p>如果给定对象不是路径,或者是与不同{@code FileSystem}相关联的路径,则此方法返回{@code false}。
     * 
     *  <p>两个路径是否相等取决于文件系统实现。在一些情况下,比较路径而不考虑情况,并且其他路径是区分大小写的。此方法不访问文件系统,该文件不需要存在。
     * 如果需要,{@link Files#isSameFile isSameFile}方法可用于检查两个路径是否找到相同的文件。
     * 
     *  <p>此方法满足{@link java.lang.Object#equals(Object)Object.equals}方法的一般合同。 </p>
     * 
     * 
     * @param   other
     *          the object to which this object is to be compared
     *
     * @return  {@code true} if, and only if, the given object is a {@code Path}
     *          that is identical to this {@code Path}
     */
    boolean equals(Object other);

    /**
     * Computes a hash code for this path.
     *
     * <p> The hash code is based upon the components of the path, and
     * satisfies the general contract of the {@link Object#hashCode
     * Object.hashCode} method.
     *
     * <p>
     *  计算此路径的哈希码。
     * 
     *  <p>哈希码基于路径的组件,并满足{@link Object#hashCode Object.hashCode}方法的一般约定。
     * 
     * 
     * @return  the hash-code value for this path
     */
    int hashCode();

    /**
     * Returns the string representation of this path.
     *
     * <p> If this path was created by converting a path string using the
     * {@link FileSystem#getPath getPath} method then the path string returned
     * by this method may differ from the original String used to create the path.
     *
     * <p> The returned path string uses the default name {@link
     * FileSystem#getSeparator separator} to separate names in the path.
     *
     * <p>
     *  返回此路径的字符串表示形式。
     * 
     * <p>如果此路径是通过使用{@link FileSystem#getPath getPath}方法转换路径字符串创建的,则此方法返回的路径字符串可能与用于创建路径的原始字符串不同。
     * 
     * @return  the string representation of this path
     */
    String toString();
}
