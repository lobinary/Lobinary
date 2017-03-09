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
 * $Id: AlgorithmMethod.java,v 1.4 2005/05/10 15:47:41 mullan Exp $
 * <p>
 *  $ Id：AlgorithmMethod.java,v 1.4 2005/05/10 15:47:41 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.security.spec.AlgorithmParameterSpec;

/**
 * An abstract representation of an algorithm defined in the XML Security
 * specifications. Subclasses represent specific types of XML security
 * algorithms, such as a {@link javax.xml.crypto.dsig.Transform}.
 *
 * <p>
 *  XML安全规范中定义的算法的抽象表示。子类表示特定类型的XML安全算法,例如{@link javax.xml.crypto.dsig.Transform}。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public interface AlgorithmMethod {

    /**
     * Returns the algorithm URI of this <code>AlgorithmMethod</code>.
     *
     * <p>
     *  返回此<code> AlgorithmMethod </code>的算法URI。
     * 
     * 
     * @return the algorithm URI of this <code>AlgorithmMethod</code>
     */
    String getAlgorithm();

    /**
     * Returns the algorithm parameters of this <code>AlgorithmMethod</code>.
     *
     * <p>
     *  返回此<code> AlgorithmMethod </code>的算法参数。
     * 
     * @return the algorithm parameters of this <code>AlgorithmMethod</code>.
     *    Returns <code>null</code> if this <code>AlgorithmMethod</code> does
     *    not require parameters and they are not specified.
     */
    AlgorithmParameterSpec getParameterSpec();
}
