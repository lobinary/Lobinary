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
 * A typesafe enumeration of the access control entry types.
 *
 * <p>
 *  访问控制条目类型的类型安全枚举。
 * 
 * 
 * @since 1.7
 */

public enum AclEntryType {
    /**
     * Explicitly grants access to a file or directory.
     * <p>
     *  明确授予对文件或目录的访问权限。
     * 
     */
    ALLOW,

    /**
     * Explicitly denies access to a file or directory.
     * <p>
     *  明确拒绝对文件或目录的访问。
     * 
     */
    DENY,

    /**
     * Log, in a system dependent way, the access specified in the
     * permissions component of the ACL entry.
     * <p>
     *  以系统依赖的方式记录ACL条目的permissions组件中指定的访问。
     * 
     */
    AUDIT,

    /**
     * Generate an alarm, in a system dependent way, the access specified in the
     * permissions component of the ACL entry.
     * <p>
     *  以系统依赖的方式生成警报,在ACL条目的permissions组件中指定的访问。
     */
    ALARM
}
