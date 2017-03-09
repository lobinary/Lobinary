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

import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

/**
 * A visitor of files. An implementation of this interface is provided to the
 * {@link Files#walkFileTree Files.walkFileTree} methods to visit each file in
 * a file tree.
 *
 * <p> <b>Usage Examples:</b>
 * Suppose we want to delete a file tree. In that case, each directory should
 * be deleted after the entries in the directory are deleted.
 * <pre>
 *     Path start = ...
 *     Files.walkFileTree(start, new SimpleFileVisitor&lt;Path&gt;() {
 *         &#64;Override
 *         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
 *             throws IOException
 *         {
 *             Files.delete(file);
 *             return FileVisitResult.CONTINUE;
 *         }
 *         &#64;Override
 *         public FileVisitResult postVisitDirectory(Path dir, IOException e)
 *             throws IOException
 *         {
 *             if (e == null) {
 *                 Files.delete(dir);
 *                 return FileVisitResult.CONTINUE;
 *             } else {
 *                 // directory iteration failed
 *                 throw e;
 *             }
 *         }
 *     });
 * </pre>
 * <p> Furthermore, suppose we want to copy a file tree to a target location.
 * In that case, symbolic links should be followed and the target directory
 * should be created before the entries in the directory are copied.
 * <pre>
 *     final Path source = ...
 *     final Path target = ...
 *
 *     Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
 *         new SimpleFileVisitor&lt;Path&gt;() {
 *             &#64;Override
 *             public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
 *                 throws IOException
 *             {
 *                 Path targetdir = target.resolve(source.relativize(dir));
 *                 try {
 *                     Files.copy(dir, targetdir);
 *                 } catch (FileAlreadyExistsException e) {
 *                      if (!Files.isDirectory(targetdir))
 *                          throw e;
 *                 }
 *                 return CONTINUE;
 *             }
 *             &#64;Override
 *             public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
 *                 throws IOException
 *             {
 *                 Files.copy(file, target.resolve(source.relativize(file)));
 *                 return CONTINUE;
 *             }
 *         });
 * </pre>
 *
 * <p>
 *  文件的访问者。此接口的实现提供给{@link Files#walkFileTree Files.walkFileTree}方法来访问文件树中的每个文件。
 * 
 *  <p> <b>使用示例：</b>假设我们要删除一个文件树。在这种情况下,应删除目录中的条目后删除每个目录。
 * <pre>
 *  路径开始= ... Files.walkFileTree(start,new SimpleFileVisitor&lt; Path&gt;(){@覆盖public FileVisitResult visitFile(路径文件,BasicFileAttributes attrs)throws IOException {Files.delete(file); return FileVisitResult.CONTINUE;}
 *  @Override public FileVisitResult postVisitDirectory(Path dir,IOException e)throws IOException {if(e == null){Files.delete(dir); return FileVisitResult.CONTINUE;}
 *  else {//目录迭代失败throw e;}}};。
 * </pre>
 *  <p>此外,假设我们要将文件树复制到目标位置。在这种情况下,应遵循符号链接,并且应在目录中的条目复制之前创建目标目录。
 * <pre>
 *  final Path source = ... final路径目标= ...
 * 
 * Files.walkFileTree(source,EnumSet.of(FileVisitOption.FOLLOW_LINKS),Integer.MAX_VALUE,new SimpleFileVi
 * sitor&lt; Path&gt;(){@覆盖public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)throws IOException {Path targetdir = target.resolve .relativize(dir)); try {Files.copy(dir,targetdir);}
 *  catch(FileAlreadyExistsException e){if(！Files.isDirectory(targetdir))throw e;} return CONTINUE;} @Ov
 * erride public FileVisitResult visitFile文件,BasicFileAttributes attrs)throws IOException {Files.copy(file,target.resolve(source.relativize(file))); return CONTINUE;}
 * });。
 * </pre>
 * 
 * 
 * @since 1.7
 */

public interface FileVisitor<T> {

    /**
     * Invoked for a directory before entries in the directory are visited.
     *
     * <p> If this method returns {@link FileVisitResult#CONTINUE CONTINUE},
     * then entries in the directory are visited. If this method returns {@link
     * FileVisitResult#SKIP_SUBTREE SKIP_SUBTREE} or {@link
     * FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS} then entries in the
     * directory (and any descendants) will not be visited.
     *
     * <p>
     *  在访问目录中的条目之前调用目录。
     * 
     *  <p>如果此方法返回{@link FileVisitResult#CONTINUE CONTINUE},则会访问目录中的条目。
     * 如果此方法返回{@link FileVisitResult#SKIP_SUBTREE SKIP_SUBTREE}或{@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS}
     * ,则不会访问目录(以及任何后代)中的条目。
     *  <p>如果此方法返回{@link FileVisitResult#CONTINUE CONTINUE},则会访问目录中的条目。
     * 
     * 
     * @param   dir
     *          a reference to the directory
     * @param   attrs
     *          the directory's basic attributes
     *
     * @return  the visit result
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs)
        throws IOException;

    /**
     * Invoked for a file in a directory.
     *
     * <p>
     *  针对目录中的文件调用。
     * 
     * 
     * @param   file
     *          a reference to the file
     * @param   attrs
     *          the file's basic attributes
     *
     * @return  the visit result
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    FileVisitResult visitFile(T file, BasicFileAttributes attrs)
        throws IOException;

    /**
     * Invoked for a file that could not be visited. This method is invoked
     * if the file's attributes could not be read, the file is a directory
     * that could not be opened, and other reasons.
     *
     * <p>
     *  针对无法访问的文件调用。如果无法读取文件的属性,文件是无法打开的目录以及其他原因,则调用此方法。
     * 
     * 
     * @param   file
     *          a reference to the file
     * @param   exc
     *          the I/O exception that prevented the file from being visited
     *
     * @return  the visit result
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    FileVisitResult visitFileFailed(T file, IOException exc)
        throws IOException;

    /**
     * Invoked for a directory after entries in the directory, and all of their
     * descendants, have been visited. This method is also invoked when iteration
     * of the directory completes prematurely (by a {@link #visitFile visitFile}
     * method returning {@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS},
     * or an I/O error when iterating over the directory).
     *
     * <p>
     * 在目录中的条目及其所有后代被访问后调用目录。
     * 当目录的迭代过早完成(通过返回{@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS}的{@link #visitFile visitFile}方法,或者在
     * 遍历目录时发生I / O错误)时,也会调用此方法。
     * 
     * @param   dir
     *          a reference to the directory
     * @param   exc
     *          {@code null} if the iteration of the directory completes without
     *          an error; otherwise the I/O exception that caused the iteration
     *          of the directory to complete prematurely
     *
     * @return  the visit result
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    FileVisitResult postVisitDirectory(T dir, IOException exc)
        throws IOException;
}
