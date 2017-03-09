/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code Policy} class.
 * All the abstract methods in this class must be implemented by each
 * service provider who wishes to supply a Policy implementation.
 *
 * <p> Subclass implementations of this abstract class must provide
 * a public constructor that takes a {@code Policy.Parameters}
 * object as an input parameter.  This constructor also must throw
 * an IllegalArgumentException if it does not understand the
 * {@code Policy.Parameters} input.
 *
 *
 * <p>
 *  此类为{@code Policy}类定义了<i>服务提供程序接口</i>(<b> SPI </b>)。该类中的所有抽象方法必须由希望提供策略实现的每个服务提供者实现。
 * 
 *  <p>此抽象类的子类实现必须提供一个公共构造函数,它将{@code Policy.Parameters}对象作为输入参数。
 * 如果不理解{@code Policy.Parameters}输入,此构造函数也必须抛出IllegalArgumentException。
 * 
 * 
 * @since 1.6
 */

public abstract class PolicySpi {

    /**
     * Check whether the policy has granted a Permission to a ProtectionDomain.
     *
     * <p>
     *  检查策略是否已授予对ProtectionDomain的权限。
     * 
     * 
     * @param domain the ProtectionDomain to check.
     *
     * @param permission check whether this permission is granted to the
     *          specified domain.
     *
     * @return boolean true if the permission is granted to the domain.
     */
    protected abstract boolean engineImplies
        (ProtectionDomain domain, Permission permission);

    /**
     * Refreshes/reloads the policy configuration. The behavior of this method
     * depends on the implementation. For example, calling {@code refresh}
     * on a file-based policy will cause the file to be re-read.
     *
     * <p> The default implementation of this method does nothing.
     * This method should be overridden if a refresh operation is supported
     * by the policy implementation.
     * <p>
     *  刷新/重新加载策略配置。此方法的行为取决于实现。例如,在基于文件的策略上调用{@code refresh}将导致该文件被重新读取。
     * 
     *  <p>此方法的默认实现不执行任何操作。如果策略实现支持刷新操作,则应覆盖此方法。
     * 
     */
    protected void engineRefresh() { }

    /**
     * Return a PermissionCollection object containing the set of
     * permissions granted to the specified CodeSource.
     *
     * <p> The default implementation of this method returns
     * Policy.UNSUPPORTED_EMPTY_COLLECTION object.  This method can be
     * overridden if the policy implementation can return a set of
     * permissions granted to a CodeSource.
     *
     * <p>
     *  返回一个PermissionCollection对象,其中包含授予指定CodeSource的权限集。
     * 
     *  <p>此方法的默认实现返回Policy.UNSUPPORTED_EMPTY_COLLECTION对象。如果策略实现可以返回授予CodeSource的一组权限,则可以覆盖此方法。
     * 
     * 
     * @param codesource the CodeSource to which the returned
     *          PermissionCollection has been granted.
     *
     * @return a set of permissions granted to the specified CodeSource.
     *          If this operation is supported, the returned
     *          set of permissions must be a new mutable instance
     *          and it must support heterogeneous Permission types.
     *          If this operation is not supported,
     *          Policy.UNSUPPORTED_EMPTY_COLLECTION is returned.
     */
    protected PermissionCollection engineGetPermissions
                                        (CodeSource codesource) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }

    /**
     * Return a PermissionCollection object containing the set of
     * permissions granted to the specified ProtectionDomain.
     *
     * <p> The default implementation of this method returns
     * Policy.UNSUPPORTED_EMPTY_COLLECTION object.  This method can be
     * overridden if the policy implementation can return a set of
     * permissions granted to a ProtectionDomain.
     *
     * <p>
     * 返回包含授予指定ProtectionDomain的权限集的PermissionCollection对象。
     * 
     * 
     * @param domain the ProtectionDomain to which the returned
     *          PermissionCollection has been granted.
     *
     * @return a set of permissions granted to the specified ProtectionDomain.
     *          If this operation is supported, the returned
     *          set of permissions must be a new mutable instance
     *          and it must support heterogeneous Permission types.
     *          If this operation is not supported,
     *          Policy.UNSUPPORTED_EMPTY_COLLECTION is returned.
     */
    protected PermissionCollection engineGetPermissions
                                        (ProtectionDomain domain) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }
}
