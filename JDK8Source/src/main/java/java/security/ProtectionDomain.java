/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import sun.misc.JavaSecurityProtectionDomainAccess;
import static sun.misc.JavaSecurityProtectionDomainAccess.ProtectionDomainCache;
import sun.security.util.Debug;
import sun.security.util.SecurityConstants;
import sun.misc.JavaSecurityAccess;
import sun.misc.SharedSecrets;

/**
 *
 *<p>
 * This ProtectionDomain class encapsulates the characteristics of a domain,
 * which encloses a set of classes whose instances are granted a set
 * of permissions when being executed on behalf of a given set of Principals.
 * <p>
 * A static set of permissions can be bound to a ProtectionDomain when it is
 * constructed; such permissions are granted to the domain regardless of the
 * Policy in force. However, to support dynamic security policies, a
 * ProtectionDomain can also be constructed such that it is dynamically
 * mapped to a set of permissions by the current Policy whenever a permission
 * is checked.
 * <p>
 *
 * <p>
 * p>
 *  这个ProtectionDomain类封装了一个域的特征,该域包含一组类,当代表一组给定的Principals执行时,它们的实例被授予一组权限。
 * <p>
 *  静态权限集可以在构建时绑定到ProtectionDomain;这样的权限被授予域,而不管有效的策略。
 * 但是,为了支持动态安全策略,还可以构造ProtectionDomain,以便在检查权限时,通过当前策略将其动态映射到一组权限。
 * <p>
 * 
 * 
 * @author Li Gong
 * @author Roland Schemers
 * @author Gary Ellison
 */

public class ProtectionDomain {

    static {
        // Set up JavaSecurityAccess in SharedSecrets
        SharedSecrets.setJavaSecurityAccess(
            new JavaSecurityAccess() {
                public <T> T doIntersectionPrivilege(
                    PrivilegedAction<T> action,
                    final AccessControlContext stack,
                    final AccessControlContext context)
                {
                    if (action == null) {
                        throw new NullPointerException();
                    }
                    return AccessController.doPrivileged(
                        action,
                        new AccessControlContext(
                            stack.getContext(), context).optimize()
                    );
                }

                public <T> T doIntersectionPrivilege(
                    PrivilegedAction<T> action,
                    AccessControlContext context)
                {
                    return doIntersectionPrivilege(action,
                        AccessController.getContext(), context);
                }
            }
       );
    }

    /* CodeSource */
    private CodeSource codesource ;

    /* ClassLoader the protection domain was consed from */
    private ClassLoader classloader;

    /* Principals running-as within this protection domain */
    private Principal[] principals;

    /* the rights this protection domain is granted */
    private PermissionCollection permissions;

    /* if the permissions object has AllPermission */
    private boolean hasAllPerm = false;

    /* the PermissionCollection is static (pre 1.4 constructor)
    /* <p>
    /* 
       or dynamic (via a policy refresh) */
    private boolean staticPermissions;

    /*
     * An object used as a key when the ProtectionDomain is stored in a Map.
     * <p>
     *  当ProtectionDomain存储在Map中时用作键的对象。
     * 
     */
    final Key key = new Key();

    private static final Debug debug = Debug.getInstance("domain");

    /**
     * Creates a new ProtectionDomain with the given CodeSource and
     * Permissions. If the permissions object is not null, then
     *  {@code setReadOnly())} will be called on the passed in
     * Permissions object. The only permissions granted to this domain
     * are the ones specified; the current Policy will not be consulted.
     *
     * <p>
     *  使用给定的CodeSource和权限创建一个新的ProtectionDomain。
     * 如果permissions对象不为null,那么在传递的Permissions对象中将调用{@code setReadOnly())}。授予此域的唯一权限是指定的权限;将不会咨询当前的政策。
     * 
     * 
     * @param codesource the codesource associated with this domain
     * @param permissions the permissions granted to this domain
     */
    public ProtectionDomain(CodeSource codesource,
                            PermissionCollection permissions) {
        this.codesource = codesource;
        if (permissions != null) {
            this.permissions = permissions;
            this.permissions.setReadOnly();
            if (permissions instanceof Permissions &&
                ((Permissions)permissions).allPermission != null) {
                hasAllPerm = true;
            }
        }
        this.classloader = null;
        this.principals = new Principal[0];
        staticPermissions = true;
    }

    /**
     * Creates a new ProtectionDomain qualified by the given CodeSource,
     * Permissions, ClassLoader and array of Principals. If the
     * permissions object is not null, then {@code setReadOnly()}
     * will be called on the passed in Permissions object.
     * The permissions granted to this domain are dynamic; they include
     * both the static permissions passed to this constructor, and any
     * permissions granted to this domain by the current Policy at the
     * time a permission is checked.
     * <p>
     * This constructor is typically used by
     * {@link SecureClassLoader ClassLoaders}
     * and {@link DomainCombiner DomainCombiners} which delegate to
     * {@code Policy} to actively associate the permissions granted to
     * this domain. This constructor affords the
     * Policy provider the opportunity to augment the supplied
     * PermissionCollection to reflect policy changes.
     * <p>
     *
     * <p>
     * 创建由给定的CodeSource,Permissions,ClassLoader和Principals数组限定的新的ProtectionDomain。
     * 如果permissions对象不为null,那么在传递的Permissions对象中将调用{@code setReadOnly()}。
     * 授予此域的权限是动态的;它们包括传递给此构造函数的静态权限以及在检查权限时由当前策略授予此域的任何权限。
     * <p>
     *  此构造函数通常由{@link SecureClassLoader ClassLoaders}和{@link DomainCombiner DomainCombiners}使用,{@link DomainCombiner DomainCombiners}
     * 委托{@code Policy}主动关联授予此域的权限。
     * 此构造函数为策略提供程序提供了扩充提供的PermissionCollection以反映策略更改的机会。
     * <p>
     * 
     * 
     * @param codesource the CodeSource associated with this domain
     * @param permissions the permissions granted to this domain
     * @param classloader the ClassLoader associated with this domain
     * @param principals the array of Principals associated with this
     * domain. The contents of the array are copied to protect against
     * subsequent modification.
     * @see Policy#refresh
     * @see Policy#getPermissions(ProtectionDomain)
     * @since 1.4
     */
    public ProtectionDomain(CodeSource codesource,
                            PermissionCollection permissions,
                            ClassLoader classloader,
                            Principal[] principals) {
        this.codesource = codesource;
        if (permissions != null) {
            this.permissions = permissions;
            this.permissions.setReadOnly();
            if (permissions instanceof Permissions &&
                ((Permissions)permissions).allPermission != null) {
                hasAllPerm = true;
            }
        }
        this.classloader = classloader;
        this.principals = (principals != null ? principals.clone():
                           new Principal[0]);
        staticPermissions = false;
    }

    /**
     * Returns the CodeSource of this domain.
     * <p>
     *  返回此域的CodeSource。
     * 
     * 
     * @return the CodeSource of this domain which may be null.
     * @since 1.2
     */
    public final CodeSource getCodeSource() {
        return this.codesource;
    }


    /**
     * Returns the ClassLoader of this domain.
     * <p>
     *  返回此域的ClassLoader。
     * 
     * 
     * @return the ClassLoader of this domain which may be null.
     *
     * @since 1.4
     */
    public final ClassLoader getClassLoader() {
        return this.classloader;
    }


    /**
     * Returns an array of principals for this domain.
     * <p>
     *  返回此域的主体数组。
     * 
     * 
     * @return a non-null array of principals for this domain.
     * Returns a new array each time this method is called.
     *
     * @since 1.4
     */
    public final Principal[] getPrincipals() {
        return this.principals.clone();
    }

    /**
     * Returns the static permissions granted to this domain.
     *
     * <p>
     *  返回授予此域的静态权限。
     * 
     * 
     * @return the static set of permissions for this domain which may be null.
     * @see Policy#refresh
     * @see Policy#getPermissions(ProtectionDomain)
     */
    public final PermissionCollection getPermissions() {
        return permissions;
    }

    /**
     * Check and see if this ProtectionDomain implies the permissions
     * expressed in the Permission object.
     * <p>
     * The set of permissions evaluated is a function of whether the
     * ProtectionDomain was constructed with a static set of permissions
     * or it was bound to a dynamically mapped set of permissions.
     * <p>
     * If the ProtectionDomain was constructed to a
     * {@link #ProtectionDomain(CodeSource, PermissionCollection)
     * statically bound} PermissionCollection then the permission will
     * only be checked against the PermissionCollection supplied at
     * construction.
     * <p>
     * However, if the ProtectionDomain was constructed with
     * the constructor variant which supports
     * {@link #ProtectionDomain(CodeSource, PermissionCollection,
     * ClassLoader, java.security.Principal[]) dynamically binding}
     * permissions, then the permission will be checked against the
     * combination of the PermissionCollection supplied at construction and
     * the current Policy binding.
     * <p>
     *
     * <p>
     *  检查并查看此ProtectionDomain是否意味着在Permission对象中表达的权限。
     * <p>
     *  所评估的权限集是是否使用静态权限集构造ProtectionDomain的函数,还是绑定到动态映射的权限集的函数。
     * <p>
     * 如果ProtectionDomain构造为{@link #ProtectionDomain(CodeSource,PermissionCollection)静态绑定} PermissionCollect
     * ion,那么将仅对构造时提供的PermissionCollection检查权限。
     * <p>
     *  但是,如果ProtectionDomain是用支持{@link #ProtectionDomain(CodeSource,PermissionCollection,ClassLoader,java.security.Principal [])动态绑定}
     * 权限的构造函数变体构造的,则将根据PermissionCollection在施工时提供和当前的政策约束。
     * <p>
     * 
     * 
     * @param permission the Permission object to check.
     *
     * @return true if "permission" is implicit to this ProtectionDomain.
     */
    public boolean implies(Permission permission) {

        if (hasAllPerm) {
            // internal permission collection already has AllPermission -
            // no need to go to policy
            return true;
        }

        if (!staticPermissions &&
            Policy.getPolicyNoCheck().implies(this, permission))
            return true;
        if (permissions != null)
            return permissions.implies(permission);

        return false;
    }

    // called by the VM -- do not remove
    boolean impliesCreateAccessControlContext() {
        return implies(SecurityConstants.CREATE_ACC_PERMISSION);
    }

    /**
     * Convert a ProtectionDomain to a String.
     * <p>
     *  将ProtectionDomain转换为字符串。
     * 
     */
    @Override public String toString() {
        String pals = "<no principals>";
        if (principals != null && principals.length > 0) {
            StringBuilder palBuf = new StringBuilder("(principals ");

            for (int i = 0; i < principals.length; i++) {
                palBuf.append(principals[i].getClass().getName() +
                            " \"" + principals[i].getName() +
                            "\"");
                if (i < principals.length-1)
                    palBuf.append(",\n");
                else
                    palBuf.append(")\n");
            }
            pals = palBuf.toString();
        }

        // Check if policy is set; we don't want to load
        // the policy prematurely here
        PermissionCollection pc = Policy.isSet() && seeAllp() ?
                                      mergePermissions():
                                      getPermissions();

        return "ProtectionDomain "+
            " "+codesource+"\n"+
            " "+classloader+"\n"+
            " "+pals+"\n"+
            " "+pc+"\n";
    }

    /**
     * Return true (merge policy permissions) in the following cases:
     *
     * . SecurityManager is null
     *
     * . SecurityManager is not null,
     *          debug is not null,
     *          SecurityManager impelmentation is in bootclasspath,
     *          Policy implementation is in bootclasspath
     *          (the bootclasspath restrictions avoid recursion)
     *
     * . SecurityManager is not null,
     *          debug is null,
     *          caller has Policy.getPolicy permission
     * <p>
     *  在以下情况下返回true(合并策略权限)：
     * 
     *  。 SecurityManager为null
     * 
     *  。
     *  SecurityManager不为null,调试不为null,SecurityManager impelmentation在bootclasspath中,策略实现在bootclasspath(boot
     * classpath限制避免递归)。
     */
    private static boolean seeAllp() {
        SecurityManager sm = System.getSecurityManager();

        if (sm == null) {
            return true;
        } else {
            if (debug != null) {
                if (sm.getClass().getClassLoader() == null &&
                    Policy.getPolicyNoCheck().getClass().getClassLoader()
                                                                == null) {
                    return true;
                }
            } else {
                try {
                    sm.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
                    return true;
                } catch (SecurityException se) {
                    // fall thru and return false
                }
            }
        }

        return false;
    }

    private PermissionCollection mergePermissions() {
        if (staticPermissions)
            return permissions;

        PermissionCollection perms =
            java.security.AccessController.doPrivileged
            (new java.security.PrivilegedAction<PermissionCollection>() {
                    public PermissionCollection run() {
                        Policy p = Policy.getPolicyNoCheck();
                        return p.getPermissions(ProtectionDomain.this);
                    }
                });

        Permissions mergedPerms = new Permissions();
        int swag = 32;
        int vcap = 8;
        Enumeration<Permission> e;
        List<Permission> pdVector = new ArrayList<>(vcap);
        List<Permission> plVector = new ArrayList<>(swag);

        //
        // Build a vector of domain permissions for subsequent merge
        if (permissions != null) {
            synchronized (permissions) {
                e = permissions.elements();
                while (e.hasMoreElements()) {
                    pdVector.add(e.nextElement());
                }
            }
        }

        //
        // Build a vector of Policy permissions for subsequent merge
        if (perms != null) {
            synchronized (perms) {
                e = perms.elements();
                while (e.hasMoreElements()) {
                    plVector.add(e.nextElement());
                    vcap++;
                }
            }
        }

        if (perms != null && permissions != null) {
            //
            // Weed out the duplicates from the policy. Unless a refresh
            // has occurred since the pd was consed this should result in
            // an empty vector.
            synchronized (permissions) {
                e = permissions.elements();   // domain vs policy
                while (e.hasMoreElements()) {
                    Permission pdp = e.nextElement();
                    Class<?> pdpClass = pdp.getClass();
                    String pdpActions = pdp.getActions();
                    String pdpName = pdp.getName();
                    for (int i = 0; i < plVector.size(); i++) {
                        Permission pp = plVector.get(i);
                        if (pdpClass.isInstance(pp)) {
                            // The equals() method on some permissions
                            // have some side effects so this manual
                            // comparison is sufficient.
                            if (pdpName.equals(pp.getName()) &&
                                pdpActions.equals(pp.getActions())) {
                                plVector.remove(i);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (perms !=null) {
            // the order of adding to merged perms and permissions
            // needs to preserve the bugfix 4301064

            for (int i = plVector.size()-1; i >= 0; i--) {
                mergedPerms.add(plVector.get(i));
            }
        }
        if (permissions != null) {
            for (int i = pdVector.size()-1; i >= 0; i--) {
                mergedPerms.add(pdVector.get(i));
            }
        }

        return mergedPerms;
    }

    /**
     * Used for storing ProtectionDomains as keys in a Map.
     * <p>
     *  。
     * 
     *  。 SecurityManager不为null,调试为null,调用者具有Policy.getPolicy权限
     * 
     */
    final class Key {}

    static {
        SharedSecrets.setJavaSecurityProtectionDomainAccess(
            new JavaSecurityProtectionDomainAccess() {
                public ProtectionDomainCache getProtectionDomainCache() {
                    return new ProtectionDomainCache() {
                        private final Map<Key, PermissionCollection> map =
                            Collections.synchronizedMap
                                (new WeakHashMap<Key, PermissionCollection>());
                        public void put(ProtectionDomain pd,
                            PermissionCollection pc) {
                            map.put((pd == null ? null : pd.key), pc);
                        }
                        public PermissionCollection get(ProtectionDomain pd) {
                            return pd == null ? map.get(null) : map.get(pd.key);
                        }
                    };
                }
            });
    }
}
