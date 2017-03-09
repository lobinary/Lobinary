/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: ExcC14NParameterSpec.java,v 1.7 2005/05/13 18:45:42 mullan Exp $
 * <p>
 *  $ Id：ExcC14NParameterSpec.java,v 1.7 2005/05/13 18:45:42 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parameters for the W3C Recommendation:
 * <a href="http://www.w3.org/TR/xml-exc-c14n/">
 * Exclusive XML Canonicalization (C14N) algorithm</a>. The
 * parameters include an optional inclusive namespace prefix list. The XML
 * Schema Definition of the Exclusive XML Canonicalization parameters is
 * defined as:
 * <pre><code>
 * &lt;schema xmlns="http://www.w3.org/2001/XMLSchema"
 *         xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#"
 *         targetNamespace="http://www.w3.org/2001/10/xml-exc-c14n#"
 *         version="0.1" elementFormDefault="qualified"&gt;
 *
 * &lt;element name="InclusiveNamespaces" type="ec:InclusiveNamespaces"/&gt;
 * &lt;complexType name="InclusiveNamespaces"&gt;
 *   &lt;attribute name="PrefixList" type="xsd:string"/&gt;
 * &lt;/complexType&gt;
 * &lt;/schema&gt;
 * </code></pre>
 *
 * <p>
 *  W3C建议的参数：
 * <a href="http://www.w3.org/TR/xml-exc-c14n/">
 *  独家XML规范化(C14N)算法</a>。参数包括可选的包含名称空间前缀列表。
 * 独占XML规范化参数的XML模式定义定义为：<pre> <code>&lt; schema xmlns ="http://www.w3.org/2001/XMLSchema"xmlns：ec ="htt
 * p：// www .w3.org / 2001/10 / xml-exc-c14n#"targetNamespace ="http://www.w3.org/2001/10/xml-exc-c14n#"
 * version ="0.1"elementFormDefault ="qualified" &gt;。
 *  独家XML规范化(C14N)算法</a>。参数包括可选的包含名称空间前缀列表。
 * 
 *  &lt; element name ="InclusiveNamespaces"type ="ec：InclusiveNamespaces"/&gt; &lt; complexType name ="
 * InclusiveNamespaces"&gt; &lt; attribute name ="PrefixList"type ="xsd：string"/&gt; &lt; / complexType&
 * gt; &lt; / schema&gt; </code> </pre>。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see CanonicalizationMethod
 */
public final class ExcC14NParameterSpec implements C14NMethodParameterSpec {

    private List<String> preList;

    /**
     * Indicates the default namespace ("#default").
     * <p>
     *  表示默认命名空间("#default")。
     * 
     */
    public static final String DEFAULT = "#default";

    /**
     * Creates a <code>ExcC14NParameterSpec</code> with an empty prefix
     * list.
     * <p>
     *  创建一个带有空前缀列表的<code> ExcC14NParameterSpec </code>。
     * 
     */
    public ExcC14NParameterSpec() {
        preList = Collections.emptyList();
    }

    /**
     * Creates a <code>ExcC14NParameterSpec</code> with the specified list
     * of prefixes. The list is copied to protect against subsequent
     * modification.
     *
     * <p>
     *  使用指定的前缀列表创建<code> ExcC14NParameterSpec </code>。复制列表以防止后续修改。
     * 
     * 
     * @param prefixList the inclusive namespace prefix list. Each entry in
     *    the list is a <code>String</code> that represents a namespace prefix.
     * @throws NullPointerException if <code>prefixList</code> is
     *    <code>null</code>
     * @throws ClassCastException if any of the entries in the list are not
     *    of type <code>String</code>
     */
    @SuppressWarnings("rawtypes")
    public ExcC14NParameterSpec(List prefixList) {
        if (prefixList == null) {
            throw new NullPointerException("prefixList cannot be null");
        }
        List<?> copy = new ArrayList<>((List<?>)prefixList);
        for (int i = 0, size = copy.size(); i < size; i++) {
            if (!(copy.get(i) instanceof String)) {
                throw new ClassCastException("not a String");
            }
        }

        @SuppressWarnings("unchecked")
        List<String> temp = (List<String>)copy;

        preList = Collections.unmodifiableList(temp);
    }

    /**
     * Returns the inclusive namespace prefix list. Each entry in the list
     * is a <code>String</code> that represents a namespace prefix.
     *
     * <p>This implementation returns an {@link
     * java.util.Collections#unmodifiableList unmodifiable list}.
     *
     * <p>
     *  返回包含的名称空间前缀列表。列表中的每个条目都是代表命名空间前缀的<code> String </code>。
     * 
     * 
     * @return the inclusive namespace prefix list (may be empty but never
     *    <code>null</code>)
     */
    @SuppressWarnings("rawtypes")
    public List getPrefixList() {
        return preList;
    }
}
