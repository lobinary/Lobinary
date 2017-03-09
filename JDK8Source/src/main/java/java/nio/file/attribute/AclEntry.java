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

package java.nio.file.attribute;

import java.util.*;

/**
 * An entry in an access control list (ACL).
 *
 * <p> The ACL entry represented by this class is based on the ACL model
 * specified in <a href="http://www.ietf.org/rfc/rfc3530.txt"><i>RFC&nbsp;3530:
 * Network File System (NFS) version 4 Protocol</i></a>. Each entry has four
 * components as follows:
 *
 * <ol>
 *    <li><p> The {@link #type() type} component determines if the entry
 *    grants or denies access. </p></li>
 *
 *    <li><p> The {@link #principal() principal} component, sometimes called the
 *    "who" component, is a {@link UserPrincipal} corresponding to the identity
 *    that the entry grants or denies access
 *    </p></li>
 *
 *    <li><p> The {@link #permissions permissions} component is a set of
 *    {@link AclEntryPermission permissions}
 *    </p></li>
 *
 *    <li><p> The {@link #flags flags} component is a set of {@link AclEntryFlag
 *    flags} to indicate how entries are inherited and propagated </p></li>
 * </ol>
 *
 * <p> ACL entries are created using an associated {@link Builder} object by
 * invoking its {@link Builder#build build} method.
 *
 * <p> ACL entries are immutable and are safe for use by multiple concurrent
 * threads.
 *
 * <p>
 *  访问控制列表(ACL)中的条目。
 * 
 *  <p>此类别所代表的ACL条目基于<a href="http://www.ietf.org/rfc/rfc3530.txt"> <i> RFC&nbsp; 3530：网络文件系统中指定的ACL模型(N
 * FS)版本4协议</i> </a>。
 * 每个条目有四个组件,如下所示：。
 * 
 * <ol>
 *  <li> <p> {@link #type()type}组件确定条目是否授予或拒绝访问。 </p> </li>
 * 
 *  <li> <p> {@link #principal()principal}组件(有时称为"who"组件)是对应于条目授予或拒绝访问的身份的{@link UserPrincipal}。
 * </p> li>。
 * 
 *  <li> <p> {@link #permissions permissions}组件是一组{@link AclEntryPermission权限} </p> </li>
 * 
 *  <li> <p> {@link #flags flags}组件是一组{@link AclEntryFlag flags},用于指示如何继承和传播条目</p> </li>
 * </ol>
 * 
 *  <p>使用关联的{@link Builder}对象通过调用其{@link Builder#build build}方法创建ACL条目。
 * 
 *  <p> ACL条目是不可变的,可安全地用于多个并发线程。
 * 
 * 
 * @since 1.7
 */

public final class AclEntry {

    private final AclEntryType type;
    private final UserPrincipal who;
    private final Set<AclEntryPermission> perms;
    private final Set<AclEntryFlag> flags;

    // cached hash code
    private volatile int hash;

    // private constructor
    private AclEntry(AclEntryType type,
                     UserPrincipal who,
                     Set<AclEntryPermission> perms,
                     Set<AclEntryFlag> flags)
    {
        this.type = type;
        this.who = who;
        this.perms = perms;
        this.flags = flags;
    }

    /**
     * A builder of {@link AclEntry} objects.
     *
     * <p> A {@code Builder} object is obtained by invoking one of the {@link
     * AclEntry#newBuilder newBuilder} methods defined by the {@code AclEntry}
     * class.
     *
     * <p> Builder objects are mutable and are not safe for use by multiple
     * concurrent threads without appropriate synchronization.
     *
     * <p>
     *  {@link AclEntry}对象的构建器。
     * 
     *  <p> {@code Builder}对象是通过调用{@code AclEntry}类定义的{@link AclEntry#newBuilder newBuilder}方法之一获得的。
     * 
     * <p> Builder对象是可变的,并且不适合在没有适当同步的情况下由多个并发线程使用。
     * 
     * 
     * @since 1.7
     */
    public static final class Builder {
        private AclEntryType type;
        private UserPrincipal who;
        private Set<AclEntryPermission> perms;
        private Set<AclEntryFlag> flags;

        private Builder(AclEntryType type,
                        UserPrincipal who,
                        Set<AclEntryPermission> perms,
                        Set<AclEntryFlag> flags)
        {
            assert perms != null && flags != null;
            this.type = type;
            this.who = who;
            this.perms = perms;
            this.flags = flags;
        }

        /**
         * Constructs an {@link AclEntry} from the components of this builder.
         * The type and who components are required to have been set in order
         * to construct an {@code AclEntry}.
         *
         * <p>
         *  从此构建器的组件构造{@link AclEntry}。为了构造一个{@code AclEntry},需要设置类型和组件。
         * 
         * 
         * @return  a new ACL entry
         *
         * @throws  IllegalStateException
         *          if the type or who component have not been set
         */
        public AclEntry build() {
            if (type == null)
                throw new IllegalStateException("Missing type component");
            if (who == null)
                throw new IllegalStateException("Missing who component");
            return new AclEntry(type, who, perms, flags);
        }

        /**
         * Sets the type component of this builder.
         *
         * <p>
         *  设置此构建器的类型组件。
         * 
         * 
         * @param   type  the component type
         * @return  this builder
         */
        public Builder setType(AclEntryType type) {
            if (type == null)
                throw new NullPointerException();
            this.type = type;
            return this;
        }

        /**
         * Sets the principal component of this builder.
         *
         * <p>
         *  设置此构建器的主要组件。
         * 
         * 
         * @param   who  the principal component
         * @return  this builder
         */
        public Builder setPrincipal(UserPrincipal who) {
            if (who == null)
                throw new NullPointerException();
            this.who = who;
            return this;
        }

        // check set only contains elements of the given type
        private static void checkSet(Set<?> set, Class<?> type) {
            for (Object e: set) {
                if (e == null)
                    throw new NullPointerException();
                type.cast(e);
            }
        }

        /**
         * Sets the permissions component of this builder. On return, the
         * permissions component of this builder is a copy of the given set.
         *
         * <p>
         *  设置此构建器的权限组件。返回时,此构建器的权限组件是给定集的副本。
         * 
         * 
         * @param   perms  the permissions component
         * @return  this builder
         *
         * @throws  ClassCastException
         *          if the set contains elements that are not of type {@code
         *          AclEntryPermission}
         */
        public Builder setPermissions(Set<AclEntryPermission> perms) {
            if (perms.isEmpty()) {
                // EnumSet.copyOf does not allow empty set
                perms = Collections.emptySet();
            } else {
                // copy and check for erroneous elements
                perms = EnumSet.copyOf(perms);
                checkSet(perms, AclEntryPermission.class);
            }

            this.perms = perms;
            return this;
        }

        /**
         * Sets the permissions component of this builder. On return, the
         * permissions component of this builder is a copy of the permissions in
         * the given array.
         *
         * <p>
         *  设置此构建器的权限组件。返回时,此构建器的权限组件是给定数组中的权限的副本。
         * 
         * 
         * @param   perms  the permissions component
         * @return  this builder
         */
        public Builder setPermissions(AclEntryPermission... perms) {
            Set<AclEntryPermission> set = EnumSet.noneOf(AclEntryPermission.class);
            // copy and check for null elements
            for (AclEntryPermission p: perms) {
                if (p == null)
                    throw new NullPointerException();
                set.add(p);
            }
            this.perms = set;
            return this;
        }

        /**
         * Sets the flags component of this builder. On return, the flags
         * component of this builder is a copy of the given set.
         *
         * <p>
         *  设置此构建器的标志组件。返回时,此构建器的flags组件是给定集合的副本。
         * 
         * 
         * @param   flags  the flags component
         * @return  this builder
         *
         * @throws  ClassCastException
         *          if the set contains elements that are not of type {@code
         *          AclEntryFlag}
         */
        public Builder setFlags(Set<AclEntryFlag> flags) {
            if (flags.isEmpty()) {
                // EnumSet.copyOf does not allow empty set
                flags = Collections.emptySet();
            } else {
                // copy and check for erroneous elements
                flags = EnumSet.copyOf(flags);
                checkSet(flags, AclEntryFlag.class);
            }

            this.flags = flags;
            return this;
        }

        /**
         * Sets the flags component of this builder. On return, the flags
         * component of this builder is a copy of the flags in the given
         * array.
         *
         * <p>
         *  设置此构建器的标志组件。返回时,此构建器的flags组件是给定数组中的flags的副本。
         * 
         * 
         * @param   flags  the flags component
         * @return  this builder
         */
        public Builder setFlags(AclEntryFlag... flags) {
            Set<AclEntryFlag> set = EnumSet.noneOf(AclEntryFlag.class);
            // copy and check for null elements
            for (AclEntryFlag f: flags) {
                if (f == null)
                    throw new NullPointerException();
                set.add(f);
            }
            this.flags = set;
            return this;
        }
    }

    /**
     * Constructs a new builder. The initial value of the type and who
     * components is {@code null}. The initial value of the permissions and
     * flags components is the empty set.
     *
     * <p>
     *  构造新构建器。类型和who组件的初始值为{@code null}。权限和标志组件的初始值是空集。
     * 
     * 
     * @return  a new builder
     */
    public static Builder newBuilder() {
        Set<AclEntryPermission> perms = Collections.emptySet();
        Set<AclEntryFlag> flags = Collections.emptySet();
        return new Builder(null, null, perms, flags);
    }

    /**
     * Constructs a new builder with the components of an existing ACL entry.
     *
     * <p>
     *  使用现有ACL条目的组件构造新构建器。
     * 
     * 
     * @param   entry  an ACL entry
     * @return  a new builder
     */
    public static Builder newBuilder(AclEntry entry) {
        return new Builder(entry.type, entry.who, entry.perms, entry.flags);
    }

    /**
     * Returns the ACL entry type.
     *
     * <p>
     *  返回ACL条目类型。
     * 
     * 
     * @return the ACL entry type
     */
    public AclEntryType type() {
        return type;
    }

    /**
     * Returns the principal component.
     *
     * <p>
     *  返回主成分。
     * 
     * 
     * @return the principal component
     */
    public UserPrincipal principal() {
        return who;
    }

    /**
     * Returns a copy of the permissions component.
     *
     * <p> The returned set is a modifiable copy of the permissions.
     *
     * <p>
     *  返回权限组件的副本。
     * 
     *  <p>返回的集合是权限的可修改副本。
     * 
     * 
     * @return the permissions component
     */
    public Set<AclEntryPermission> permissions() {
        return new HashSet<AclEntryPermission>(perms);
    }

    /**
     * Returns a copy of the flags component.
     *
     * <p> The returned set is a modifiable copy of the flags.
     *
     * <p>
     *  返回flags组件的副本。
     * 
     *  <p>返回的集合是标记的可修改副本。
     * 
     * 
     * @return the flags component
     */
    public Set<AclEntryFlag> flags() {
        return new HashSet<AclEntryFlag>(flags);
    }

    /**
     * Compares the specified object with this ACL entry for equality.
     *
     * <p> If the given object is not an {@code AclEntry} then this method
     * immediately returns {@code false}.
     *
     * <p> For two ACL entries to be considered equals requires that they are
     * both the same type, their who components are equal, their permissions
     * components are equal, and their flags components are equal.
     *
     * <p> This method satisfies the general contract of the {@link
     * java.lang.Object#equals(Object) Object.equals} method. </p>
     *
     * <p>
     *  将指定的对象与此ACL条目进行比较以确保相等。
     * 
     * <p>如果给定的对象不是{@code AclEntry},则此方法立即返回{@code false}。
     * 
     *  <p>对于要考虑的两个ACL条目,等于要求它们都是相同类型,它们的组件相等,它们的权限组件相等,并且它们的标志组件相等。
     * 
     *  <p>此方法满足{@link java.lang.Object#equals(Object)Object.equals}方法的一般合同。 </p>
     * 
     * 
     * @param   ob   the object to which this object is to be compared
     *
     * @return  {@code true} if, and only if, the given object is an AclEntry that
     *          is identical to this AclEntry
     */
    @Override
    public boolean equals(Object ob) {
        if (ob == this)
            return true;
        if (ob == null || !(ob instanceof AclEntry))
            return false;
        AclEntry other = (AclEntry)ob;
        if (this.type != other.type)
            return false;
        if (!this.who.equals(other.who))
            return false;
        if (!this.perms.equals(other.perms))
            return false;
        if (!this.flags.equals(other.flags))
            return false;
        return true;
    }

    private static int hash(int h, Object o) {
        return h * 127 + o.hashCode();
    }

    /**
     * Returns the hash-code value for this ACL entry.
     *
     * <p> This method satisfies the general contract of the {@link
     * Object#hashCode} method.
     * <p>
     */
    @Override
    public int hashCode() {
        // return cached hash if available
        if (hash != 0)
            return hash;
        int h = type.hashCode();
        h = hash(h, who);
        h = hash(h, perms);
        h = hash(h, flags);
        hash = h;
        return hash;
    }

    /**
     * Returns the string representation of this ACL entry.
     *
     * <p>
     *  返回此ACL条目的哈希码值。
     * 
     *  <p>此方法满足{@link Object#hashCode}方法的一般合同。
     * 
     * 
     * @return  the string representation of this entry
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // who
        sb.append(who.getName());
        sb.append(':');

        // permissions
        for (AclEntryPermission perm: perms) {
            sb.append(perm.name());
            sb.append('/');
        }
        sb.setLength(sb.length()-1); // drop final slash
        sb.append(':');

        // flags
        if (!flags.isEmpty()) {
            for (AclEntryFlag flag: flags) {
                sb.append(flag.name());
                sb.append('/');
            }
            sb.setLength(sb.length()-1);  // drop final slash
            sb.append(':');
        }

        // type
        sb.append(type.name());
        return sb.toString();
    }
}
