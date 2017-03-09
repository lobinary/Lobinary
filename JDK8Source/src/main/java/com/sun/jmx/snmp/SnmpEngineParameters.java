/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp;

import java.io.Serializable;

/**
 * This class is used to pass some specific parameters to an <CODE>
 * SnmpEngineFactory </CODE>.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此类用于将一些特定参数传递到<CODE> SnmpEngineFactory </CODE>。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public class SnmpEngineParameters implements Serializable {
    private static final long serialVersionUID = 3720556613478400808L;

    private UserAcl uacl = null;
    private String securityFile = null;
    private boolean encrypt = false;
    private SnmpEngineId engineId = null;

    /**
     * Sets the file to use for SNMP Runtime Lcd. If no file is provided, the default location will be checked.
     * <p>
     *  设置要用于SNMP Runtime Lcd的文件。如果没有提供文件,将检查默认位置。
     * 
     */
    public void setSecurityFile(String securityFile) {
        this.securityFile = securityFile;
    }

    /**
     * Gets the file to use for SNMP Runtime Lcd.
     * <p>
     *  获取要用于SNMP运行时Lcd的文件。
     * 
     * 
     * @return The security file.
     */
    public String getSecurityFile() {
        return securityFile;
    }
    /**
     * Sets a customized user ACL. User Acl is used in order to check
     * access for SNMP V3 requests. If no ACL is provided,
     * <CODE>com.sun.jmx.snmp.usm.UserAcl.UserAcl</CODE> is instantiated.
     * <p>
     *  设置自定义用户ACL。使用用户Acl为了检查SNMP V3请求的访问。
     * 如果未提供ACL,则会实例化<CODE> com.sun.jmx.snmp.usm.UserAcl.UserAcl </CODE>。
     * 
     * 
     * @param uacl The user ACL to use.
     */
    public void setUserAcl(UserAcl uacl) {
        this.uacl = uacl;
    }

    /**
     * Gets the customized user ACL.
     * <p>
     *  获取自定义用户ACL。
     * 
     * 
     * @return The customized user ACL.
     */
    public UserAcl getUserAcl() {
        return uacl;
    }

    /**
     * Activate SNMP V3 encryption. By default the encryption is not activated. Be sure that the security provider classes needed for DES are in your classpath (eg:JCE classes)
     *
     * <p>
     *  激活SNMP V3加密。默认情况下,加密未激活。确保DES所需的安全提供程序类在您的类路径中(例如：JCE类)
     * 
     */
    public void activateEncryption() {
        this.encrypt = true;
    }

    /**
     * Deactivate SNMP V3 encryption. By default the encryption is not activated. Be sure that the security provider classes needed for DES are in your classpath (eg:JCE classes)
     *
     * <p>
     *  停用SNMP V3加密。默认情况下,加密未激活。确保DES所需的安全提供程序类在您的类路径中(例如：JCE类)
     * 
     */
    public void deactivateEncryption() {
        this.encrypt = false;
    }

    /**
     * Check if encryption is activated. By default the encryption is not activated.
     * <p>
     *  检查加密是否已激活。默认情况下,加密未激活。
     * 
     * 
     * @return The encryption activation status.
     */
    public boolean isEncryptionEnabled() {
        return encrypt;
    }

    /**
     * Set the engine Id.
     * <p>
     *  设置引擎ID。
     * 
     * 
     * @param engineId The engine Id to use.
     */
    public void setEngineId(SnmpEngineId engineId) {
        this.engineId = engineId;
    }

    /**
     * Get the engine Id.
     * <p>
     *  获取引擎ID。
     * 
     * @return The engineId.
     */
    public SnmpEngineId getEngineId() {
        return engineId;
    }
}
