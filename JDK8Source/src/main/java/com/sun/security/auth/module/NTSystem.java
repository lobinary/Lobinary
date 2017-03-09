/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.auth.module;

/**
 * <p> This class implementation retrieves and makes available NT
 * security information for the current user.
 *
 * <p>
 *  <p>此类实现检索并为当前用户提供NT安全信息。
 * 
 */
@jdk.Exported
public class NTSystem {

    private native void getCurrent(boolean debug);
    private native long getImpersonationToken0();

    private String userName;
    private String domain;
    private String domainSID;
    private String userSID;
    private String groupIDs[];
    private String primaryGroupID;
    private long   impersonationToken;

    /**
     * Instantiate an <code>NTSystem</code> and load
     * the native library to access the underlying system information.
     * <p>
     *  实例化<code> NTSystem </code>并加载本机库以访问基础系统信息。
     * 
     */
    public NTSystem() {
        this(false);
    }

    /**
     * Instantiate an <code>NTSystem</code> and load
     * the native library to access the underlying system information.
     * <p>
     *  实例化<code> NTSystem </code>并加载本机库以访问基础系统信息。
     * 
     */
    NTSystem(boolean debug) {
        loadNative();
        getCurrent(debug);
    }

    /**
     * Get the username for the current NT user.
     *
     * <p>
     *
     * <p>
     *  获取当前NT用户的用户名。
     * 
     * <p>
     * 
     * 
     * @return the username for the current NT user.
     */
    public String getName() {
        return userName;
    }

    /**
     * Get the domain for the current NT user.
     *
     * <p>
     *
     * <p>
     *  获取当前NT用户的域。
     * 
     * <p>
     * 
     * 
     * @return the domain for the current NT user.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Get a printable SID for the current NT user's domain.
     *
     * <p>
     *
     * <p>
     *  为当前NT用户的域获取可打印的SID。
     * 
     * <p>
     * 
     * 
     * @return a printable SID for the current NT user's domain.
     */
    public String getDomainSID() {
        return domainSID;
    }

    /**
     * Get a printable SID for the current NT user.
     *
     * <p>
     *
     * <p>
     *  为当前NT用户获取可打印的SID。
     * 
     * <p>
     * 
     * 
     * @return a printable SID for the current NT user.
     */
    public String getUserSID() {
        return userSID;
    }

    /**
     * Get a printable primary group SID for the current NT user.
     *
     * <p>
     *
     * <p>
     *  获取当前NT用户的可打印主组SID。
     * 
     * <p>
     * 
     * 
     * @return the primary group SID for the current NT user.
     */
    public String getPrimaryGroupID() {
        return primaryGroupID;
    }

    /**
     * Get the printable group SIDs for the current NT user.
     *
     * <p>
     *
     * <p>
     *  获取当前NT用户的可打印组SID。
     * 
     * <p>
     * 
     * 
     * @return the group SIDs for the current NT user.
     */
    public String[] getGroupIDs() {
        return groupIDs == null ? null : groupIDs.clone();
    }

    /**
     * Get an impersonation token for the current NT user.
     *
     * <p>
     *
     * <p>
     *  获取当前NT用户的模拟令牌。
     * 
     * 
     * @return an impersonation token for the current NT user.
     */
    public synchronized long getImpersonationToken() {
        if (impersonationToken == 0) {
            impersonationToken = getImpersonationToken0();
        }
        return impersonationToken;
    }


    private void loadNative() {
        System.loadLibrary("jaas_nt");
    }
}
