/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.spi.FileSystemProvider;
import java.net.URI;

/**
 * This class consists exclusively of static methods that return a {@link Path}
 * by converting a path string or {@link URI}.
 *
 * <p>
 *  此类仅由通过转换路径字符串或{@link URI}返回{@link Path}的静态方法组成。
 * 
 * 
 * @since 1.7
 */

public final class Paths {
    private Paths() { }

    /**
     * Converts a path string, or a sequence of strings that when joined form
     * a path string, to a {@code Path}. If {@code more} does not specify any
     * elements then the value of the {@code first} parameter is the path string
     * to convert. If {@code more} specifies one or more elements then each
     * non-empty string, including {@code first}, is considered to be a sequence
     * of name elements (see {@link Path}) and is joined to form a path string.
     * The details as to how the Strings are joined is provider specific but
     * typically they will be joined using the {@link FileSystem#getSeparator
     * name-separator} as the separator. For example, if the name separator is
     * "{@code /}" and {@code getPath("/foo","bar","gus")} is invoked, then the
     * path string {@code "/foo/bar/gus"} is converted to a {@code Path}.
     * A {@code Path} representing an empty path is returned if {@code first}
     * is the empty string and {@code more} does not contain any non-empty
     * strings.
     *
     * <p> The {@code Path} is obtained by invoking the {@link FileSystem#getPath
     * getPath} method of the {@link FileSystems#getDefault default} {@link
     * FileSystem}.
     *
     * <p> Note that while this method is very convenient, using it will imply
     * an assumed reference to the default {@code FileSystem} and limit the
     * utility of the calling code. Hence it should not be used in library code
     * intended for flexible reuse. A more flexible alternative is to use an
     * existing {@code Path} instance as an anchor, such as:
     * <pre>
     *     Path dir = ...
     *     Path path = dir.resolve("file");
     * </pre>
     *
     * <p>
     *  将连接时形成路径字符串的路径字符串或字符串序列转换为{@code Path}。如果{@code more}没有指定任何元素,那么{@code first}参数的值是要转换的路径字符串。
     * 如果{@code more}指定一个或多个元素,则每个非空字符串(包括{@code first})被认为是一个名称元素的序列(参见{@link Path}),并被连接形成一个路径字符串。
     * 有关字符串如何连接的详细信息是提供程序特定的,但通常它们将使用{@link FileSystem#getSeparator name-separator}作为分隔符连接。
     * 例如,如果名称分隔符是"{@code /}"和{@code getPath("/ foo","bar","gus")}被调用,那么路径字符串{@code"/ foo / bar / gus"}转换为{@code Path}
     * 。
     * 有关字符串如何连接的详细信息是提供程序特定的,但通常它们将使用{@link FileSystem#getSeparator name-separator}作为分隔符连接。
     * 如果{@code first}是空字符串,而{@code more}不包含任何非空字符串,则会返回表示空路径的{@code Path}。
     * 
     *  <p> {@code Path}是通过调用{@link FileSystems#getDefault default} {@link FileSystem}的{@link FileSystem#getPath getPath}
     * 方法获得的。
     * 
     * <p>请注意,虽然这种方法非常方便,但使用它将暗示对默认{@code FileSystem}的假设引用,并限制调用代码的实用程序。因此,它不应该用于旨在灵活重用的库代码中。
     * 更灵活的替代方法是使用现有的{@code Path}实例作为锚点,例如：。
     * <pre>
     *  Path dir = ... Path path = dir.resolve("file");
     * </pre>
     * 
     * 
     * @param   first
     *          the path string or initial part of the path string
     * @param   more
     *          additional strings to be joined to form the path string
     *
     * @return  the resulting {@code Path}
     *
     * @throws  InvalidPathException
     *          if the path string cannot be converted to a {@code Path}
     *
     * @see FileSystem#getPath
     */
    public static Path get(String first, String... more) {
        return FileSystems.getDefault().getPath(first, more);
    }

    /**
     * Converts the given URI to a {@link Path} object.
     *
     * <p> This method iterates over the {@link FileSystemProvider#installedProviders()
     * installed} providers to locate the provider that is identified by the
     * URI {@link URI#getScheme scheme} of the given URI. URI schemes are
     * compared without regard to case. If the provider is found then its {@link
     * FileSystemProvider#getPath getPath} method is invoked to convert the
     * URI.
     *
     * <p> In the case of the default provider, identified by the URI scheme
     * "file", the given URI has a non-empty path component, and undefined query
     * and fragment components. Whether the authority component may be present
     * is platform specific. The returned {@code Path} is associated with the
     * {@link FileSystems#getDefault default} file system.
     *
     * <p> The default provider provides a similar <em>round-trip</em> guarantee
     * to the {@link java.io.File} class. For a given {@code Path} <i>p</i> it
     * is guaranteed that
     * <blockquote><tt>
     * Paths.get(</tt><i>p</i><tt>.{@link Path#toUri() toUri}()).equals(</tt>
     * <i>p</i><tt>.{@link Path#toAbsolutePath() toAbsolutePath}())</tt>
     * </blockquote>
     * so long as the original {@code Path}, the {@code URI}, and the new {@code
     * Path} are all created in (possibly different invocations of) the same
     * Java virtual machine. Whether other providers make any guarantees is
     * provider specific and therefore unspecified.
     *
     * <p>
     * 
     * @param   uri
     *          the URI to convert
     *
     * @return  the resulting {@code Path}
     *
     * @throws  IllegalArgumentException
     *          if preconditions on the {@code uri} parameter do not hold. The
     *          format of the URI is provider specific.
     * @throws  FileSystemNotFoundException
     *          The file system, identified by the URI, does not exist and
     *          cannot be created automatically, or the provider identified by
     *          the URI's scheme component is not installed
     * @throws  SecurityException
     *          if a security manager is installed and it denies an unspecified
     *          permission to access the file system
     */
    public static Path get(URI uri) {
        String scheme =  uri.getScheme();
        if (scheme == null)
            throw new IllegalArgumentException("Missing scheme");

        // check for default provider to avoid loading of installed providers
        if (scheme.equalsIgnoreCase("file"))
            return FileSystems.getDefault().provider().getPath(uri);

        // try to find provider
        for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
            if (provider.getScheme().equalsIgnoreCase(scheme)) {
                return provider.getPath(uri);
            }
        }

        throw new FileSystemNotFoundException("Provider \"" + scheme + "\" not installed");
    }
}
