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
 * $Id: XSLTTransformParameterSpec.java,v 1.4 2005/05/10 16:40:18 mullan Exp $
 * <p>
 *  $ Id：XSLTTransformParameterSpec.java,v 1.4 2005/05/10 16:40:18 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.XMLStructure;

/**
 * Parameters for the <a href="http://www.w3.org/TR/1999/REC-xslt-19991116">
 * XSLT Transform Algorithm</a>.
 * The parameters include a namespace-qualified stylesheet element.
 *
 * <p>An <code>XSLTTransformParameterSpec</code> is instantiated with a
 * mechanism-dependent (ex: DOM) stylesheet element. For example:
 * <pre>
 *   DOMStructure stylesheet = new DOMStructure(element)
 *   XSLTTransformParameterSpec spec = new XSLTransformParameterSpec(stylesheet);
 * </pre>
 * where <code>element</code> is an {@link org.w3c.dom.Element} containing
 * the namespace-qualified stylesheet element.
 *
 * <p>
 *  <a href="http://www.w3.org/TR/1999/REC-xslt-19991116"> XSLT变换算法</a>的参数。参数包括命名空间限定的样式表元素。
 * 
 *  <p> <code> XSLTTransformParameterSpec </code>使用机制依赖(ex：DOM)stylesheet元素实例化。例如：
 * <pre>
 *  DOMStructure stylesheet = new DOMStructure(element)XSLTTransformParameterSpec spec = new XSLTransfor
 * mParameterSpec(stylesheet);。
 * </pre>
 *  其中<code>元素</code>是包含命名空间限定的stylesheet元素的{@link org.w3c.dom.Element}。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see Transform
 */
public final class XSLTTransformParameterSpec implements TransformParameterSpec{
    private XMLStructure stylesheet;

    /**
     * Creates an <code>XSLTTransformParameterSpec</code> with the specified
     * stylesheet.
     *
     * <p>
     * 
     * 
     * @param stylesheet the XSLT stylesheet to be used
     * @throws NullPointerException if <code>stylesheet</code> is
     *    <code>null</code>
     */
    public XSLTTransformParameterSpec(XMLStructure stylesheet) {
        if (stylesheet == null) {
            throw new NullPointerException();
        }
        this.stylesheet = stylesheet;
    }

    /**
     * Returns the stylesheet.
     *
     * <p>
     *  使用指定的样式表创建<code> XSLTTransformParameterSpec </code>。
     * 
     * 
     * @return the stylesheet
     */
    public XMLStructure getStylesheet() {
        return stylesheet;
    }
}
