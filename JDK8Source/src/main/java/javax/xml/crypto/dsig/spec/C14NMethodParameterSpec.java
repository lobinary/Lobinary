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
 * $Id: C14NMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * <p>
 *  $ Id：C14NMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.CanonicalizationMethod;

/**
 * A specification of algorithm parameters for a {@link CanonicalizationMethod}
 * Algorithm. The purpose of this interface is to group (and provide type
 * safety for) all canonicalization (C14N) parameter specifications. All
 * canonicalization parameter specifications must implement this interface.
 *
 * <p>
 *  {@link CanonicalizationMethod}算法的算法参数规范。此接口的目的是为所有规范化(C14N)参数规范分组(并提供类型安全)。所有规范化参数规范都必须实现此接口。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see CanonicalizationMethod
 */
public interface C14NMethodParameterSpec extends TransformParameterSpec {}
