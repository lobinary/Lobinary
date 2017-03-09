/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This interface allows you to compute key localization and delta generation. It is useful when adding user in USM MIB. An instance of <CODE> SnmpUsmKeyHandler </CODE> is associated to each <CODE> SnmpEngine </CODE> object.
 * When computing key, an authentication algorithm is needed. The supported ones are : usmHMACMD5AuthProtocol and usmHMACSHAAuthProtocol.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此接口允许您计算密钥本地化和增量生成。当在USM MIB中添加用户时,这是有用的。
 *  <CODE> SnmpUsmKeyHandler </CODE>的实例与每个<CODE> SnmpEngine </CODE>对象相关联。当计算密钥时,需要认证算法。
 * 支持的是：usmHMACMD5AuthProtocol和usmHMACSHAAuthProtocol。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。
 * </b> </p>。
 * 
 * 
 * @since 1.5
 */
public interface SnmpUsmKeyHandler {

    /**
     * DES privacy algorithm key size. To be used when localizing privacy key
     * <p>
     *  DES隐私算法密钥大小。在本地化隐私密钥时使用
     * 
     */
    public static int DES_KEY_SIZE = 16;

    /**
     * DES privacy algorithm delta size. To be used when calculing privacy key delta.
     * <p>
     *  DES隐私算法delta大小。在计算隐私密钥delta时使用。
     * 
     */
    public static int DES_DELTA_SIZE = 16;

    /**
     * Translate a password to a key. It MUST be compliant to RFC 2574 description.
     * <p>
     *  将密码翻译为密钥。它必须符合RFC 2574描述。
     * 
     * 
     * @param algoName The authentication algorithm to use.
     * @param password Password to convert.
     * @return The key.
     * @exception IllegalArgumentException If the algorithm is unknown.
     */
    public byte[] password_to_key(String algoName, String password) throws IllegalArgumentException;
    /**
     * Localize the passed key using the passed <CODE>SnmpEngineId</CODE>. It MUST be compliant to RFC 2574 description.
     * <p>
     *  使用传递的<CODE> SnmpEngineId </CODE>本地化传递的密钥。它必须符合RFC 2574描述。
     * 
     * 
     * @param algoName The authentication algorithm to use.
     * @param key The key to localize;
     * @param engineId The Id used to localize the key.
     * @return The localized key.
     * @exception IllegalArgumentException If the algorithm is unknown.
     */
    public byte[] localizeAuthKey(String algoName, byte[] key, SnmpEngineId engineId) throws IllegalArgumentException;

    /**
     * Localize the passed privacy key using the passed <CODE>SnmpEngineId</CODE>. It MUST be compliant to RFC 2574 description.
     * <p>
     *  使用传递的<CODE> SnmpEngineId </CODE>本地化传递的隐私密钥。它必须符合RFC 2574描述。
     * 
     * 
     * @param algoName The authentication algorithm to use.
     * @param key The key to localize;
     * @param engineId The Id used to localize the key.
     * @param keysize The privacy algorithm key size.
     * @return The localized key.
     * @exception IllegalArgumentException If the algorithm is unknown.
     */
    public byte[] localizePrivKey(String algoName, byte[] key, SnmpEngineId engineId,int keysize) throws IllegalArgumentException;

    /**
     * Calculate the delta parameter needed when processing key change. This computation is done by the key change initiator. It MUST be compliant to RFC 2574 description.
     * <p>
     *  计算处理密钥更改时所需的增量参数。该计算由密钥改变发起者完成。它必须符合RFC 2574描述。
     * 
     * 
     * @param algoName The authentication algorithm to use.
     * @param oldKey The old key.
     * @param newKey The new key.
     * @param random The random value.
     * @return The delta.
     * @exception IllegalArgumentException If the algorithm is unknown.
     */
    public byte[] calculateAuthDelta(String algoName, byte[] oldKey, byte[] newKey, byte[] random) throws IllegalArgumentException;

    /**
     * Calculate the delta parameter needed when processing key change for a privacy algorithm. This computation is done by the key change initiator. It MUST be compliant to RFC 2574 description.
     * <p>
     *  计算处理隐私算法的密钥更改时所需的增量参数。该计算由密钥改变发起者完成。它必须符合RFC 2574描述。
     * 
     * @param algoName The authentication algorithm to use.
     * @param oldKey The old key.
     * @param newKey The new key.
     * @param random The random value.
     * @param deltaSize The algo delta size.
     * @return The delta.
     * @exception IllegalArgumentException If the algorithm is unknown.
     */
    public byte[] calculatePrivDelta(String algoName, byte[] oldKey, byte[] newKey, byte[] random, int deltaSize) throws IllegalArgumentException;

}
