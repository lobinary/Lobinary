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
 * $Id: SignatureMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * <p>
 *  $ Id：SignatureMethodParameterSpec.java,v 1.3 2005/05/10 16:40:17 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.spec;

import javax.xml.crypto.dsig.SignatureMethod;
import java.security.spec.AlgorithmParameterSpec;

/**
 * A specification of algorithm parameters for an XML {@link SignatureMethod}
 * algorithm. The purpose of this interface is to group (and provide type
 * safety for) all signature method parameter specifications. All signature
 * method parameter specifications must implement this interface.
 *
 * <p>
 *  XML {@link SignatureMethod}算法的算法参数规范。此接口的目的是对所有签名方法参数规范进行分组(并为其提供类型安全性)。所有签名方法参数规范必须实现此接口。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see SignatureMethod
 */
public interface SignatureMethodParameterSpec extends AlgorithmParameterSpec {}
