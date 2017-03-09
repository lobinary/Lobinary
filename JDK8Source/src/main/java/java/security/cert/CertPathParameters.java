/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.cert;

/**
 * A specification of certification path algorithm parameters.
 * The purpose of this interface is to group (and provide type safety for)
 * all {@code CertPath} parameter specifications. All
 * {@code CertPath} parameter specifications must implement this
 * interface.
 *
 * <p>
 *  认证路径算法参数的规范。此接口的目的是对所有{@code CertPath}参数规范进行分组(并提供类型安全性)。所有{@code CertPath}参数规范都必须实现此接口。
 * 
 * 
 * @author      Yassir Elley
 * @see         CertPathValidator#validate(CertPath, CertPathParameters)
 * @see         CertPathBuilder#build(CertPathParameters)
 * @since       1.4
 */
public interface CertPathParameters extends Cloneable {

  /**
   * Makes a copy of this {@code CertPathParameters}. Changes to the
   * copy will not affect the original and vice versa.
   *
   * <p>
   *  创建此{@code CertPathParameters}的副本。对副本的更改不会影响原始副本,反之亦然。
   * 
   * @return a copy of this {@code CertPathParameters}
   */
  Object clone();
}
