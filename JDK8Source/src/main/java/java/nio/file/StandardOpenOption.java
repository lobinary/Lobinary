/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines the standard open options.
 *
 * <p>
 *  定义标准打开选项。
 * 
 * 
 * @since 1.7
 */

public enum StandardOpenOption implements OpenOption {
    /**
     * Open for read access.
     * <p>
     *  打开以进行读访问。
     * 
     */
    READ,

    /**
     * Open for write access.
     * <p>
     *  打开以进行写访问。
     * 
     */
    WRITE,

    /**
     * If the file is opened for {@link #WRITE} access then bytes will be written
     * to the end of the file rather than the beginning.
     *
     * <p> If the file is opened for write access by other programs, then it
     * is file system specific if writing to the end of the file is atomic.
     * <p>
     *  如果为{@link #WRITE}访问打开文件,则字节将写入文件的结尾,而不是开头。
     * 
     *  <p>如果文件被其他程序打开以进行写访问,那么如果写入文件末尾是原子的,那么它是特定于文件系统的。
     * 
     */
    APPEND,

    /**
     * If the file already exists and it is opened for {@link #WRITE}
     * access, then its length is truncated to 0. This option is ignored
     * if the file is opened only for {@link #READ} access.
     * <p>
     *  如果文件已存在并且已为{@link #WRITE}访问打开,则其长度将被截断为0.如果仅为{@link #READ}访问打开文件,将忽略此选项。
     * 
     */
    TRUNCATE_EXISTING,

    /**
     * Create a new file if it does not exist.
     * This option is ignored if the {@link #CREATE_NEW} option is also set.
     * The check for the existence of the file and the creation of the file
     * if it does not exist is atomic with respect to other file system
     * operations.
     * <p>
     *  如果新文件不存在,请创建它。如果还设置了{@link #CREATE_NEW}选项,将忽略此选项。如果文件不存在,则检查文件的存在和文件的创建相对于其他文件系统操作是原子的。
     * 
     */
    CREATE,

    /**
     * Create a new file, failing if the file already exists.
     * The check for the existence of the file and the creation of the file
     * if it does not exist is atomic with respect to other file system
     * operations.
     * <p>
     *  创建新文件,如果文件已存在,则失败。如果文件不存在,则检查文件的存在和文件的创建相对于其他文件系统操作是原子的。
     * 
     */
    CREATE_NEW,

    /**
     * Delete on close. When this option is present then the implementation
     * makes a <em>best effort</em> attempt to delete the file when closed
     * by the appropriate {@code close} method. If the {@code close} method is
     * not invoked then a <em>best effort</em> attempt is made to delete the
     * file when the Java virtual machine terminates (either normally, as
     * defined by the Java Language Specification, or where possible, abnormally).
     * This option is primarily intended for use with <em>work files</em> that
     * are used solely by a single instance of the Java virtual machine. This
     * option is not recommended for use when opening files that are open
     * concurrently by other entities. Many of the details as to when and how
     * the file is deleted are implementation specific and therefore not
     * specified. In particular, an implementation may be unable to guarantee
     * that it deletes the expected file when replaced by an attacker while the
     * file is open. Consequently, security sensitive applications should take
     * care when using this option.
     *
     * <p> For security reasons, this option may imply the {@link
     * LinkOption#NOFOLLOW_LINKS} option. In other words, if the option is present
     * when opening an existing file that is a symbolic link then it may fail
     * (by throwing {@link java.io.IOException}).
     * <p>
     * 关闭时删除。当此选项存在时,实施会在通过适当的{@code close}方法关闭时尝试删除文件。
     * </em>如果未调用{@code close}方法,则会尝试在Java虚拟机终止时正常地执行文件(尽量按照Java语言规范定义),或尽可能地尝试删除该文件,异常)。
     * 此选项主要用于仅由Java虚拟机的单个实例使用的<em>工作文件</em>。当打开由其他实体同时打开的文件时,不建议使用此选项。关于文件何时以及如何被删除的许多细节是实现特定的,因此没有指定。
     * 特别地,当文件被打开时,实现可能无法保证其在被攻击者替换时删除期望的文件。因此,安全敏感应用程序在使用此选项时应小心。
     * 
     *  <p>出于安全考虑,此选项可能意味着{@link LinkOption#NOFOLLOW_LINKS}选项。
     * 换句话说,如果打开一个作为符号链接的现有文件时存在该选项,那么它可能失败(通过抛出{@link java.io.IOException})。
     * 
     */
    DELETE_ON_CLOSE,

    /**
     * Sparse file. When used with the {@link #CREATE_NEW} option then this
     * option provides a <em>hint</em> that the new file will be sparse. The
     * option is ignored when the file system does not support the creation of
     * sparse files.
     * <p>
     *  稀疏文件。当与{@link #CREATE_NEW}选项一起使用时,此选项会提供一个<em>提示<em> </em>新文件将是稀疏的。当文件系统不支持创建稀疏文件时,将忽略该选项。
     * 
     */
    SPARSE,

    /**
     * Requires that every update to the file's content or metadata be written
     * synchronously to the underlying storage device.
     *
     * <p>
     * 要求对文件内容或元数据的每次更新都与底层存储设备同步写入。
     * 
     * 
     * @see <a href="package-summary.html#integrity">Synchronized I/O file integrity</a>
     */
    SYNC,

    /**
     * Requires that every update to the file's content be written
     * synchronously to the underlying storage device.
     *
     * <p>
     *  要求对文件内容的每次更新都与底层存储设备同步写入。
     * 
     * @see <a href="package-summary.html#integrity">Synchronized I/O file integrity</a>
     */
    DSYNC;
}
