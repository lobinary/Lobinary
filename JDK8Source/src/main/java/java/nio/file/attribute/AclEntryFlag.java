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
 * Defines the flags for used by the flags component of an ACL {@link AclEntry
 * entry}.
 *
 * <p> In this release, this class does not define flags related to {@link
 * AclEntryType#AUDIT} and {@link AclEntryType#ALARM} entry types.
 *
 * <p>
 *  定义ACL {@link AclEntry entry}的flags组件使用的标志。
 * 
 *  <p>在此版本中,此类不定义与{@link AclEntryType#AUDIT}和{@link AclEntryType#ALARM}条目类型相关的标志。
 * 
 * 
 * @since 1.7
 */

public enum AclEntryFlag {

    /**
     * Can be placed on a directory and indicates that the ACL entry should be
     * added to each new non-directory file created.
     * <p>
     *  可以放在一个目录上,并指示ACL条目应添加到每个创建的新非目录文件。
     * 
     */
    FILE_INHERIT,

    /**
     * Can be placed on a directory and indicates that the ACL entry should be
     * added to each new directory created.
     * <p>
     *  可以放在一个目录上,并指示ACL条目应添加到创建的每个新目录。
     * 
     */
    DIRECTORY_INHERIT,

    /**
     * Can be placed on a directory to indicate that the ACL entry should not
     * be placed on the newly created directory which is inheritable by
     * subdirectories of the created directory.
     * <p>
     *  可以放在一个目录上,以指示ACL条目不应该放在新创建的目录上,该目录可以由创建的目录的子目录继承。
     * 
     */
    NO_PROPAGATE_INHERIT,

    /**
     * Can be placed on a directory but does not apply to the directory,
     * only to newly created files/directories as specified by the
     * {@link #FILE_INHERIT} and {@link #DIRECTORY_INHERIT} flags.
     * <p>
     *  可以放置在目录上,但不适用于目录,仅适用于{@link #FILE_INHERIT}和{@link #DIRECTORY_INHERIT}标志指定的新创建的文件/目录。
     */
    INHERIT_ONLY;
}
