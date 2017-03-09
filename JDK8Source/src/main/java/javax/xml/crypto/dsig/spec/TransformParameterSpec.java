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
 * $Id: TransformParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * <p>
 *  $ Id：TransformParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.Transform;
import java.security.spec.AlgorithmParameterSpec;

/**
 * A specification of algorithm parameters for a {@link Transform}
 * algorithm. The purpose of this interface is to group (and provide type
 * safety for) all transform parameter specifications. All transform parameter
 * specifications must implement this interface.
 *
 * <p>
 *  {@link Transform}算法的算法参数规范。此接口的目的是将所有变换参数规范分组(并为其提供类型安全性)。所有变换参数规范必须实现此接口。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see Transform
 */
public interface TransformParameterSpec extends AlgorithmParameterSpec {}
