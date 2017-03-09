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
 * A specification of the result of a certification path validator algorithm.
 * <p>
 * The purpose of this interface is to group (and provide type safety
 * for) all certification path validator results. All results returned
 * by the {@link CertPathValidator#validate CertPathValidator.validate}
 * method must implement this interface.
 *
 * <p>
 *  认证路径验证器算法的结果的规范。
 * <p>
 *  此接口的目的是为所有认证路径验证器结果分组(并提供类型安全)。
 *  {@link CertPathValidator#validate CertPathValidator.validate}方法返回的所有结果都必须实现此接口。
 * 
 * @see CertPathValidator
 *
 * @since       1.4
 * @author      Yassir Elley
 */
public interface CertPathValidatorResult extends Cloneable {

    /**
     * Makes a copy of this {@code CertPathValidatorResult}. Changes to the
     * copy will not affect the original and vice versa.
     *
     * <p>
     * 
     * 
     * @return a copy of this {@code CertPathValidatorResult}
     */
    Object clone();
}
