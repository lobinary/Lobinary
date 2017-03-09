/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: XMLCryptoContext.java,v 1.6 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：XMLCryptoContext.java,v 1.6 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

/**
 * Contains common context information for XML cryptographic operations.
 *
 * <p>This interface contains methods for setting and retrieving properties
 * that affect the processing of XML signatures or XML encrypted structures.
 *
 * <p>Note that <code>XMLCryptoContext</code> instances can contain information
 * and state specific to the XML cryptographic structure it is used with.
 * The results are unpredictable if an <code>XMLCryptoContext</code> is
 * used with multiple structures (for example, you should not use the same
 * {@link javax.xml.crypto.dsig.XMLValidateContext} instance to validate two
 * different {@link javax.xml.crypto.dsig.XMLSignature} objects).
 *
 * <p>
 *  包含用于XML加密操作的公共上下文信息。
 * 
 *  <p>此界面包含用于设置和检索影响XML签名或XML加密结构处理的属性的方法。
 * 
 *  <p>请注意,<code> XMLCryptoContext </code>实例可以包含特定于与其一起使用的XML加密结构的信息和状态。
 * 如果使用具有多个结构的<code> XMLCryptoContext </code>,结果是不可预测的(例如,您不应该使用相同的{@link javax.xml.crypto.dsig.XMLValidateContext}
 * 实例来验证两个不同的{@link javax.xml.crypto.dsig.XMLSignature}对象)。
 *  <p>请注意,<code> XMLCryptoContext </code>实例可以包含特定于与其一起使用的XML加密结构的信息和状态。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public interface XMLCryptoContext {

    /**
     * Returns the base URI.
     *
     * <p>
     *  返回基本URI。
     * 
     * 
     * @return the base URI, or <code>null</code> if not specified
     * @see #setBaseURI(String)
     */
    String getBaseURI();

    /**
     * Sets the base URI.
     *
     * <p>
     *  设置基本URI。
     * 
     * 
     * @param baseURI the base URI, or <code>null</code> to remove current
     *    value
     * @throws IllegalArgumentException if <code>baseURI</code> is not RFC
     *    2396 compliant
     * @see #getBaseURI
     */
    void setBaseURI(String baseURI);

    /**
     * Returns the key selector for finding a key.
     *
     * <p>
     *  返回用于查找键的键选择器。
     * 
     * 
     * @return the key selector, or <code>null</code> if not specified
     * @see #setKeySelector(KeySelector)
     */
    KeySelector getKeySelector();

    /**
     * Sets the key selector for finding a key.
     *
     * <p>
     *  设置用于查找键的键选择器。
     * 
     * 
     * @param ks the key selector, or <code>null</code> to remove the current
     *    setting
     * @see #getKeySelector
     */
    void setKeySelector(KeySelector ks);

    /**
     * Returns a <code>URIDereferencer</code> that is used to dereference
     * {@link URIReference}s.
     *
     * <p>
     *  返回用于取消引用{@link URIReference}的<code> URIDereferencer </code>。
     * 
     * 
     * @return the <code>URIDereferencer</code>, or <code>null</code> if not
     *    specified
     * @see #setURIDereferencer(URIDereferencer)
     */
    URIDereferencer getURIDereferencer();

    /**
     * Sets a <code>URIDereferencer</code> that is used to dereference
     * {@link URIReference}s. The specified <code>URIDereferencer</code>
     * is used in place of an implementation's default
     * <code>URIDereferencer</code>.
     *
     * <p>
     *  设置用于取消引用{@link URIReference}的<code> URIDereferencer </code>。
     * 指定的<code> URIDereferencer </code>用于代替实现的默认<code> URIDereferencer </code>。
     * 
     * 
     * @param dereferencer the <code>URIDereferencer</code>, or
     *    <code>null</code> to remove any current setting
     * @see #getURIDereferencer
     */
    void setURIDereferencer(URIDereferencer dereferencer);

    /**
     * Returns the namespace prefix that the specified namespace URI is
     * associated with. Returns the specified default prefix if the specified
     * namespace URI has not been bound to a prefix. To bind a namespace URI
     * to a prefix, call the {@link #putNamespacePrefix putNamespacePrefix}
     * method.
     *
     * <p>
     * 返回指定的命名空间URI与之关联的命名空间前缀。如果指定的命名空间URI未绑定到前缀,则返回指定的默认前缀。
     * 要将命名空间URI绑定到前缀,请调用{@link #putNamespacePrefix putNamespacePrefix}方法。
     * 
     * 
     * @param namespaceURI a namespace URI
     * @param defaultPrefix the prefix to be returned in the event that the
     *    the specified namespace URI has not been bound to a prefix.
     * @return the prefix that is associated with the specified namespace URI,
     *    or <code>defaultPrefix</code> if the URI is not registered. If
     *    the namespace URI is registered but has no prefix, an empty string
     *    (<code>""</code>) is returned.
     * @throws NullPointerException if <code>namespaceURI</code> is
     *    <code>null</code>
     * @see #putNamespacePrefix(String, String)
     */
    String getNamespacePrefix(String namespaceURI, String defaultPrefix);

    /**
     * Maps the specified namespace URI to the specified prefix. If there is
     * already a prefix associated with the specified namespace URI, the old
     * prefix is replaced by the specified prefix.
     *
     * <p>
     *  将指定的命名空间URI映射到指定的前缀。如果已有与指定的命名空间URI相关联的前缀,则旧前缀将替换为指定的前缀。
     * 
     * 
     * @param namespaceURI a namespace URI
     * @param prefix a namespace prefix (or <code>null</code> to remove any
     *    existing mapping). Specifying the empty string (<code>""</code>)
     *    binds no prefix to the namespace URI.
     * @return the previous prefix associated with the specified namespace
     *    URI, or <code>null</code> if there was none
     * @throws NullPointerException if <code>namespaceURI</code> is
     *    <code>null</code>
     * @see #getNamespacePrefix(String, String)
     */
    String putNamespacePrefix(String namespaceURI, String prefix);

    /**
     * Returns the default namespace prefix. The default namespace prefix
     * is the prefix for all namespace URIs not explicitly set by the
     * {@link #putNamespacePrefix putNamespacePrefix} method.
     *
     * <p>
     *  返回默认的命名空间前缀。默认命名空间前缀是未由{@link #putNamespacePrefix putNamespacePrefix}方法显式设置的所有命名空间URI的前缀。
     * 
     * 
     * @return the default namespace prefix, or <code>null</code> if none has
     *    been set.
     * @see #setDefaultNamespacePrefix(String)
     */
    String getDefaultNamespacePrefix();

    /**
     * Sets the default namespace prefix. This sets the namespace prefix for
     * all namespace URIs not explicitly set by the {@link #putNamespacePrefix
     * putNamespacePrefix} method.
     *
     * <p>
     *  设置默认命名空间前缀。这会为未由{@link #putNamespacePrefix putNamespacePrefix}方法显式设置的所有命名空间URI设置命名空间前缀。
     * 
     * 
     * @param defaultPrefix the default namespace prefix, or <code>null</code>
     *    to remove the current setting. Specify the empty string
     *    (<code>""</code>) to bind no prefix.
     * @see #getDefaultNamespacePrefix
     */
    void setDefaultNamespacePrefix(String defaultPrefix);

    /**
     * Sets the specified property.
     *
     * <p>
     *  设置指定的属性。
     * 
     * 
     * @param name the name of the property
     * @param value the value of the property to be set
     * @return the previous value of the specified property, or
     *    <code>null</code> if it did not have a value
     * @throws NullPointerException if <code>name</code> is <code>null</code>
     * @see #getProperty(String)
     */
    Object setProperty(String name, Object value);

    /**
     * Returns the value of the specified property.
     *
     * <p>
     *  返回指定属性的值。
     * 
     * 
     * @param name the name of the property
     * @return the current value of the specified property, or
     *    <code>null</code> if it does not have a value
     * @throws NullPointerException if <code>name</code> is <code>null</code>
     * @see #setProperty(String, Object)
     */
    Object getProperty(String name);

    /**
     * Returns the value to which this context maps the specified key.
     *
     * <p>More formally, if this context contains a mapping from a key
     * <code>k</code> to a value <code>v</code> such that
     * <code>(key==null ? k==null : key.equals(k))</code>, then this method
     * returns <code>v</code>; otherwise it returns <code>null</code>. (There
     * can be at most one such mapping.)
     *
     * <p>This method is useful for retrieving arbitrary information that is
     * specific to the cryptographic operation that this context is used for.
     *
     * <p>
     *  返回此上下文映射指定键的值。
     * 
     *  更正式地说,如果这个上下文包含从密钥<code> k </code>到值<code> v </code>的映射,使得<code>(key == null?k == null： key.equals(k
     * ))</code>,则此方法返回<code> v </code>;否则返回<code> null </code>。
     *  (最多只能有一个这样的映射。)。
     * 
     *  <p>此方法对于检索特定于此上下文用于的加密操作的任意信息非常有用。
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which this context maps the specified key, or
     *    <code>null</code> if there is no mapping for the key
     * @see #put(Object, Object)
     */
    Object get(Object key);

    /**
     * Associates the specified value with the specified key in this context.
     * If the context previously contained a mapping for this key, the old
     * value is replaced by the specified value.
     *
     * <p>This method is useful for storing arbitrary information that is
     * specific to the cryptographic operation that this context is used for.
     *
     * <p>
     * 
     * 
     * @param key key with which the specified value is to be associated with
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key, or <code>null</code>
     *    if there was no mapping for the key
     * @throws IllegalArgumentException if some aspect of this key or value
     *    prevents it from being stored in this context
     * @see #get(Object)
     */
    Object put(Object key, Object value);
}
