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

package java.nio.file.attribute;

/**
 * Defines the permissions for use with the permissions component of an ACL
 * {@link AclEntry entry}.
 *
 * <p>
 *  定义用于ACL {@link AclEntry entry}的权限组件的权限。
 * 
 * 
 * @since 1.7
 */

public enum AclEntryPermission {

    /**
     * Permission to read the data of the file.
     * <p>
     *  读取文件数据的权限。
     * 
     */
    READ_DATA,

    /**
     * Permission to modify the file's data.
     * <p>
     *  修改文件数据的权限。
     * 
     */
    WRITE_DATA,

    /**
     * Permission to append data to a file.
     * <p>
     *  将数据附加到文件的权限。
     * 
     */
    APPEND_DATA,

    /**
     * Permission to read the named attributes of a file.
     *
     * <p> <a href="http://www.ietf.org/rfc/rfc3530.txt">RFC&nbsp;3530: Network
     * File System (NFS) version 4 Protocol</a> defines <em>named attributes</em>
     * as opaque files associated with a file in the file system.
     * <p>
     *  读取文件的命名属性的权限。
     * 
     *  <p> <a href="http://www.ietf.org/rfc/rfc3530.txt"> RFC&nbsp; 3530：网络文件系统(NFS)版本4协议</a>定义了<em>命名属性</p>
     *  em>作为与文件系统中的文件相关联的不透明文件。
     * 
     */
    READ_NAMED_ATTRS,

    /**
     * Permission to write the named attributes of a file.
     *
     * <p> <a href="http://www.ietf.org/rfc/rfc3530.txt">RFC&nbsp;3530: Network
     * File System (NFS) version 4 Protocol</a> defines <em>named attributes</em>
     * as opaque files associated with a file in the file system.
     * <p>
     *  写入文件的命名属性的权限。
     * 
     *  <p> <a href="http://www.ietf.org/rfc/rfc3530.txt"> RFC&nbsp; 3530：网络文件系统(NFS)版本4协议</a>定义了<em>命名属性</p>
     *  em>作为与文件系统中的文件相关联的不透明文件。
     * 
     */
    WRITE_NAMED_ATTRS,

    /**
     * Permission to execute a file.
     * <p>
     *  执行文件的权限。
     * 
     */
    EXECUTE,

    /**
     * Permission to delete a file or directory within a directory.
     * <p>
     *  删除目录中的文件或目录的权限。
     * 
     */
    DELETE_CHILD,

    /**
     * The ability to read (non-acl) file attributes.
     * <p>
     *  读取(非acl)文件属性的能力。
     * 
     */
    READ_ATTRIBUTES,

    /**
     * The ability to write (non-acl) file attributes.
     * <p>
     *  写(非acl)文件属性的能力。
     * 
     */
    WRITE_ATTRIBUTES,

    /**
     * Permission to delete the file.
     * <p>
     *  删除文件的权限。
     * 
     */
    DELETE,

    /**
     * Permission to read the ACL attribute.
     * <p>
     *  读取ACL属性的权限。
     * 
     */
    READ_ACL,

    /**
     * Permission to write the ACL attribute.
     * <p>
     *  写入ACL属性的权限。
     * 
     */
    WRITE_ACL,

    /**
     * Permission to change the owner.
     * <p>
     *  更改所有者的权限。
     * 
     */
    WRITE_OWNER,

    /**
     * Permission to access file locally at the server with synchronous reads
     * and writes.
     * <p>
     *  在具有同步读取和写入的服务器上本地访问文件的权限。
     * 
     */
    SYNCHRONIZE;

    /**
     * Permission to list the entries of a directory (equal to {@link #READ_DATA})
     * <p>
     *  列出目录条目的权限(等于{@link #READ_DATA})
     * 
     */
    public static final AclEntryPermission LIST_DIRECTORY = READ_DATA;

    /**
     * Permission to add a new file to a directory (equal to {@link #WRITE_DATA})
     * <p>
     *  向目录添加新文件的权限(等于{@link #WRITE_DATA})
     * 
     */
    public static final AclEntryPermission ADD_FILE = WRITE_DATA;

    /**
     * Permission to create a subdirectory to a directory (equal to {@link #APPEND_DATA})
     * <p>
     * 创建目录的子目录的权限(等于{@link #APPEND_DATA})
     */
    public static final AclEntryPermission ADD_SUBDIRECTORY = APPEND_DATA;
}
