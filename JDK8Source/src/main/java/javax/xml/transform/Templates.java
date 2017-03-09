/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

import java.util.Properties;




/**
 * An object that implements this interface is the runtime representation of processed
 * transformation instructions.
 *
 * <p>Templates must be threadsafe for a given instance
 * over multiple threads running concurrently, and may
 * be used multiple times in a given session.</p>
 * <p>
 *  实现此接口的对象是处理的变换指令的运行时表示。
 * 
 *  <p>对于同时运行的多个线程,给定实例的模板必须是线程安全的,并且可以在给定会话中多次使用。</p>
 * 
 */
public interface Templates {

    /**
     * Create a new transformation context for this Templates object.
     *
     * <p>
     *  为此Templates对象创建一个新的转换上下文。
     * 
     * 
     * @return A valid non-null instance of a Transformer.
     *
     * @throws TransformerConfigurationException if a Transformer can not be created.
     */
    Transformer newTransformer() throws TransformerConfigurationException;

    /**
     * Get the properties corresponding to the effective xsl:output element.
     * The object returned will
     * be a clone of the internal values. Accordingly, it can be mutated
     * without mutating the Templates object, and then handed in to
     * {@link javax.xml.transform.Transformer#setOutputProperties}.
     *
     * <p>The properties returned should contain properties set by the stylesheet,
     * and these properties are "defaulted" by default properties specified by
     * <a href="http://www.w3.org/TR/xslt#output">section 16 of the
     * XSL Transformations (XSLT) W3C Recommendation</a>.  The properties that
     * were specifically set by the stylesheet should be in the base
     * Properties list, while the XSLT default properties that were not
     * specifically set should be in the "default" Properties list.  Thus,
     * getOutputProperties().getProperty(String key) will obtain any
     * property in that was set by the stylesheet, <em>or</em> the default
     * properties, while
     * getOutputProperties().get(String key) will only retrieve properties
     * that were explicitly set in the stylesheet.</p>
     *
     * <p>For XSLT,
     * <a href="http://www.w3.org/TR/xslt#attribute-value-templates">Attribute
     * Value Templates</a> attribute values will
     * be returned unexpanded (since there is no context at this point).  The
     * namespace prefixes inside Attribute Value Templates will be unexpanded,
     * so that they remain valid XPath values.</p>
     *
     * <p>
     *  获取与有效xsl：output元素对应的属性。返回的对象将是内部值的克隆。
     * 因此,它可以在不改变Templates对象的情况下进行突变,然后交给{@link javax.xml.transform.Transformer#setOutputProperties}。
     * 
     *  <p>返回的属性应包含样式表设置的属性,这些属性由<a href="http://www.w3.org/TR/xslt#output">第16节指定的默认属性"默认" XSL转换(XSLT)W3C建议
     * </a>。
     * 由样式表专门设置的属性应在基本属性列表中,而未特别设置的XSLT默认属性应在"默认"属性列表中。因此,getOutputProperties()。
     * getProperty(String key)将获取由样式表,<em>或</em>默认属性设置的任何属性,而getOutputProperties()。
     * 
     * @return A Properties object, never null.
     */
    Properties getOutputProperties();
}
