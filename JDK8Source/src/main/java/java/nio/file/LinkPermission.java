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

import java.security.BasicPermission;

/**
 * The {@code Permission} class for link creation operations.
 *
 * <p> The following table provides a summary description of what the permission
 * allows, and discusses the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5
 *        summary="Table shows permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 * <tr>
 *   <td>hard</td>
 *   <td> Ability to add an existing file to a directory. This is sometimes
 *   known as creating a link, or hard link. </td>
 *   <td> Extreme care should be taken when granting this permission. It allows
 *   linking to any file or directory in the file system thus allowing the
 *   attacker access to all files. </td>
 * </tr>
 * <tr>
 *   <td>symbolic</td>
 *   <td> Ability to create symbolic links. </td>
 *   <td> Extreme care should be taken when granting this permission. It allows
 *   linking to any file or directory in the file system thus allowing the
 *   attacker to access to all files. </td>
 * </tr>
 * </table>
 *
 * <p>
 *  用于链接创建操作的{@code Permission}类。
 * 
 *  <p>下表提供了权限允许的摘要说明,并讨论了授予代码权限的风险。
 * 
 *  <table border = 1 cellpadding = 5
 * summary="Table shows permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * <tr>
 *  <td> hard </td> <td>可以将现有文件添加到目录。这有时被称为创建链接或硬链接。 </td> <td>在授予此权限时应格外小心。
 * 它允许链接到文件系统中的任何文件或目录,从而允许攻击者访问所有文件。 </td>。
 * 
 * @since 1.7
 *
 * @see Files#createLink
 * @see Files#createSymbolicLink
 */
public final class LinkPermission extends BasicPermission {
    static final long serialVersionUID = -1441492453772213220L;

    private void checkName(String name) {
        if (!name.equals("hard") && !name.equals("symbolic")) {
            throw new IllegalArgumentException("name: " + name);
        }
    }

    /**
     * Constructs a {@code LinkPermission} with the specified name.
     *
     * <p>
     * </tr>
     * <tr>
     *  <td>符号</td> <td>能够创建符号链接。 </td> <td>在授予此权限时应格外小心。它允许链接到文件系统中的任何文件或目录,从而允许攻击者访问所有文件。 </td>
     * </tr>
     * </table>
     * 
     * 
     * @param   name
     *          the name of the permission. It must be "hard" or "symbolic".
     *
     * @throws  IllegalArgumentException
     *          if name is empty or invalid
     */
    public LinkPermission(String name) {
        super(name);
        checkName(name);
    }

    /**
     * Constructs a {@code LinkPermission} with the specified name.
     *
     * <p>
     * 
     * @param   name
     *          the name of the permission; must be "hard" or "symbolic".
     * @param   actions
     *          the actions for the permission; must be the empty string or
     *          {@code null}
     *
     * @throws  IllegalArgumentException
     *          if name is empty or invalid, or actions is a non-empty string
     */
    public LinkPermission(String name, String actions) {
        super(name);
        checkName(name);
        if (actions != null && actions.length() > 0) {
            throw new IllegalArgumentException("actions: " + actions);
        }
    }
}
