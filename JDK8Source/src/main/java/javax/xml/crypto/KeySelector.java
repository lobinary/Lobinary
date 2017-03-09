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
/*
 * $Id: KeySelector.java,v 1.6 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：KeySelector.java,v 1.6 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.security.Key;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

/**
 * A selector that finds and returns a key using the data contained in a
 * {@link KeyInfo} object. An example of an implementation of
 * this class is one that searches a {@link java.security.KeyStore} for
 * trusted keys that match information contained in a <code>KeyInfo</code>.
 *
 * <p>Whether or not the returned key is trusted and the mechanisms
 * used to determine that is implementation-specific.
 *
 * <p>
 *  使用{@link KeyInfo}对象中包含的数据查找和返回密钥的选择器。
 * 此类的实现的示例是搜索{@link java.security.KeyStore}中与包含在<code> KeyInfo </code>中的信息匹配的可信密钥的示例。
 * 
 *  <p>返回的键是否受信任,并且用于确定该键的机制是否是实现特定的。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public abstract class KeySelector {

    /**
     * The purpose of the key that is to be selected.
     * <p>
     *  要选择的键的用途。
     * 
     */
    public static class Purpose {

        private final String name;

        private Purpose(String name)    { this.name = name; }

        /**
         * Returns a string representation of this purpose ("sign",
         * "verify", "encrypt", or "decrypt").
         *
         * <p>
         *  返回此目的的字符串表示形式("sign","verify","encrypt"或"decrypt")。
         * 
         * 
         * @return a string representation of this purpose
         */
        public String toString()        { return name; }

        /**
         * A key for signing.
         * <p>
         *  签名的钥匙。
         * 
         */
        public static final Purpose SIGN = new Purpose("sign");
        /**
         * A key for verifying.
         * <p>
         *  验证的关键。
         * 
         */
        public static final Purpose VERIFY = new Purpose("verify");
        /**
         * A key for encrypting.
         * <p>
         *  加密的密钥。
         * 
         */
        public static final Purpose ENCRYPT = new Purpose("encrypt");
        /**
         * A key for decrypting.
         * <p>
         *  用于解密的密钥。
         * 
         */
        public static final Purpose DECRYPT = new Purpose("decrypt");
    }

    /**
     * Default no-args constructor; intended for invocation by subclasses only.
     * <p>
     *  默认无参数构造函数;仅供子类调用。
     * 
     */
    protected KeySelector() {}

    /**
     * Attempts to find a key that satisfies the specified constraints.
     *
     * <p>
     *  尝试查找满足指定约束的键。
     * 
     * 
     * @param keyInfo a <code>KeyInfo</code> (may be <code>null</code>)
     * @param purpose the key's purpose ({@link Purpose#SIGN},
     *    {@link Purpose#VERIFY}, {@link Purpose#ENCRYPT}, or
     *    {@link Purpose#DECRYPT})
     * @param method the algorithm method that this key is to be used for.
     *    Only keys that are compatible with the algorithm and meet the
     *    constraints of the specified algorithm should be returned.
     * @param context an <code>XMLCryptoContext</code> that may contain
     *    useful information for finding an appropriate key. If this key
     *    selector supports resolving {@link RetrievalMethod} types, the
     *    context's <code>baseURI</code> and <code>dereferencer</code>
     *    parameters (if specified) should be used by the selector to
     *    resolve and dereference the URI.
     * @return the result of the key selector
     * @throws KeySelectorException if an exceptional condition occurs while
     *    attempting to find a key. Note that an inability to find a key is not
     *    considered an exception (<code>null</code> should be
     *    returned in that case). However, an error condition (ex: network
     *    communications failure) that prevented the <code>KeySelector</code>
     *    from finding a potential key should be considered an exception.
     * @throws ClassCastException if the data type of <code>method</code>
     *    is not supported by this key selector
     */
    public abstract KeySelectorResult select(KeyInfo keyInfo, Purpose purpose,
        AlgorithmMethod method, XMLCryptoContext context)
        throws KeySelectorException;

    /**
     * Returns a <code>KeySelector</code> that always selects the specified
     * key, regardless of the <code>KeyInfo</code> passed to it.
     *
     * <p>
     *  返回一个始终选择指定键的<code> KeySelector </code>,而不管传递给它的<code> KeyInfo </code>。
     * 
     * @param key the sole key to be stored in the key selector
     * @return a key selector that always selects the specified key
     * @throws NullPointerException if <code>key</code> is <code>null</code>
     */
    public static KeySelector singletonKeySelector(Key key) {
        return new SingletonKeySelector(key);
    }

    private static class SingletonKeySelector extends KeySelector {
        private final Key key;

        SingletonKeySelector(Key key) {
            if (key == null) {
                throw new NullPointerException();
            }
            this.key = key;
        }

        public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose,
            AlgorithmMethod method, XMLCryptoContext context)
            throws KeySelectorException {

            return new KeySelectorResult() {
                public Key getKey() {
                    return key;
                }
            };
        }
    }
}
