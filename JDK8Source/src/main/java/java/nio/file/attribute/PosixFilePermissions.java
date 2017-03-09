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

package java.nio.file.attribute;

import static java.nio.file.attribute.PosixFilePermission.*;
import java.util.*;

/**
 * This class consists exclusively of static methods that operate on sets of
 * {@link PosixFilePermission} objects.
 *
 * <p>
 *  这个类只包括对{@link PosixFilePermission}对象进行操作的静态方法。
 * 
 * 
 * @since 1.7
 */

public final class PosixFilePermissions {
    private PosixFilePermissions() { }

    // Write string representation of permission bits to {@code sb}.
    private static void writeBits(StringBuilder sb, boolean r, boolean w, boolean x) {
        if (r) {
            sb.append('r');
        } else {
            sb.append('-');
        }
        if (w) {
            sb.append('w');
        } else {
            sb.append('-');
        }
        if (x) {
            sb.append('x');
        } else {
            sb.append('-');
        }
    }

    /**
     * Returns the {@code String} representation of a set of permissions. It
     * is guaranteed that the returned {@code String} can be parsed by the
     * {@link #fromString} method.
     *
     * <p> If the set contains {@code null} or elements that are not of type
     * {@code PosixFilePermission} then these elements are ignored.
     *
     * <p>
     *  返回一组权限的{@code String}表示形式。它保证返回的{@code String}可以由{@link #fromString}方法解析。
     * 
     *  <p>如果集合包含{@code null}或不是类型为{@code PosixFilePermission}的元素,则会忽略这些元素。
     * 
     * 
     * @param   perms
     *          the set of permissions
     *
     * @return  the string representation of the permission set
     */
    public static String toString(Set<PosixFilePermission> perms) {
        StringBuilder sb = new StringBuilder(9);
        writeBits(sb, perms.contains(OWNER_READ), perms.contains(OWNER_WRITE),
          perms.contains(OWNER_EXECUTE));
        writeBits(sb, perms.contains(GROUP_READ), perms.contains(GROUP_WRITE),
          perms.contains(GROUP_EXECUTE));
        writeBits(sb, perms.contains(OTHERS_READ), perms.contains(OTHERS_WRITE),
          perms.contains(OTHERS_EXECUTE));
        return sb.toString();
    }

    private static boolean isSet(char c, char setValue) {
        if (c == setValue)
            return true;
        if (c == '-')
            return false;
        throw new IllegalArgumentException("Invalid mode");
    }
    private static boolean isR(char c) { return isSet(c, 'r'); }
    private static boolean isW(char c) { return isSet(c, 'w'); }
    private static boolean isX(char c) { return isSet(c, 'x'); }

    /**
     * Returns the set of permissions corresponding to a given {@code String}
     * representation.
     *
     * <p> The {@code perms} parameter is a {@code String} representing the
     * permissions. It has 9 characters that are interpreted as three sets of
     * three. The first set refers to the owner's permissions; the next to the
     * group permissions and the last to others. Within each set, the first
     * character is {@code 'r'} to indicate permission to read, the second
     * character is {@code 'w'} to indicate permission to write, and the third
     * character is {@code 'x'} for execute permission. Where a permission is
     * not set then the corresponding character is set to {@code '-'}.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we require the set of permissions that indicate the owner has read,
     * write, and execute permissions, the group has read and execute permissions
     * and others have none.
     * <pre>
     *   Set&lt;PosixFilePermission&gt; perms = PosixFilePermissions.fromString("rwxr-x---");
     * </pre>
     *
     * <p>
     *  返回与给定{@code String}表示形式对应的权限集。
     * 
     *  <p> {@code perms}参数是一个表示权限的{@code String}参数。它有9个字符,被解释为三组三个。第一个集合指的是所有者的权限;旁边的组权限和最后的对其他人。
     * 在每个集合中,第一个字符是{@code'r'}表示读取权限,第二个字符是{@code'w'}表示允许写入,第三个字符是{@code'x'}执行权限。
     * 如果未设置权限,则相应的字符将设置为{@code' - '}。
     * 
     *  <p> <b>用法示例：</b>假设我们需要一组权限,表明所有者具有读取,写入和执行权限,该组具有读取和执行权限,其他权限没有权限。
     * <pre>
     * 
     * @param   perms
     *          string representing a set of permissions
     *
     * @return  the resulting set of permissions
     *
     * @throws  IllegalArgumentException
     *          if the string cannot be converted to a set of permissions
     *
     * @see #toString(Set)
     */
    public static Set<PosixFilePermission> fromString(String perms) {
        if (perms.length() != 9)
            throw new IllegalArgumentException("Invalid mode");
        Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);
        if (isR(perms.charAt(0))) result.add(OWNER_READ);
        if (isW(perms.charAt(1))) result.add(OWNER_WRITE);
        if (isX(perms.charAt(2))) result.add(OWNER_EXECUTE);
        if (isR(perms.charAt(3))) result.add(GROUP_READ);
        if (isW(perms.charAt(4))) result.add(GROUP_WRITE);
        if (isX(perms.charAt(5))) result.add(GROUP_EXECUTE);
        if (isR(perms.charAt(6))) result.add(OTHERS_READ);
        if (isW(perms.charAt(7))) result.add(OTHERS_WRITE);
        if (isX(perms.charAt(8))) result.add(OTHERS_EXECUTE);
        return result;
    }

    /**
     * Creates a {@link FileAttribute}, encapsulating a copy of the given file
     * permissions, suitable for passing to the {@link java.nio.file.Files#createFile
     * createFile} or {@link java.nio.file.Files#createDirectory createDirectory}
     * methods.
     *
     * <p>
     *  设置&lt; PosixFilePermission&gt; perms = PosixFilePermissions.fromString("rwxr-x ---");
     * </pre>
     * 
     * 
     * @param   perms
     *          the set of permissions
     *
     * @return  an attribute encapsulating the given file permissions with
     *          {@link FileAttribute#name name} {@code "posix:permissions"}
     *
     * @throws  ClassCastException
     *          if the set contains elements that are not of type {@code
     *          PosixFilePermission}
     */
    public static FileAttribute<Set<PosixFilePermission>>
        asFileAttribute(Set<PosixFilePermission> perms)
    {
        // copy set and check for nulls (CCE will be thrown if an element is not
        // a PosixFilePermission)
        perms = new HashSet<PosixFilePermission>(perms);
        for (PosixFilePermission p: perms) {
            if (p == null)
                throw new NullPointerException();
        }
        final Set<PosixFilePermission> value = perms;
        return new FileAttribute<Set<PosixFilePermission>>() {
            @Override
            public String name() {
                return "posix:permissions";
            }
            @Override
            public Set<PosixFilePermission> value() {
                return Collections.unmodifiableSet(value);
            }
        };
    }
}
