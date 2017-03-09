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
 * $Id: DigestMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * <p>
 *  $ Id：DigestMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.DigestMethod;
import java.security.spec.AlgorithmParameterSpec;

/**
 * A specification of algorithm parameters for a {@link DigestMethod}
 * algorithm. The purpose of this interface is to group (and provide type
 * safety for) all digest method parameter specifications. All digest method
 * parameter specifications must implement this interface.
 *
 * <p>
 *  {@link DigestMethod}算法的算法参数规范。此接口的目的是将所有摘要方法参数规范分组(并提供类型安全性)。所有digest方法参数规范必须实现此接口。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see DigestMethod
 */
public interface DigestMethodParameterSpec extends AlgorithmParameterSpec {}
