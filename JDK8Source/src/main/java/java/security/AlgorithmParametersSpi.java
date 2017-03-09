/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code AlgorithmParameters} class, which is used to manage
 * algorithm parameters.
 *
 * <p> All the abstract methods in this class must be implemented by each
 * cryptographic service provider who wishes to supply parameter management
 * for a particular algorithm.
 *
 * <p>
 *  此类为{@code AlgorithmParameters}类定义了<i>服务提供者接口</i>(<b> SPI </b>),用于管理算法参数。
 * 
 *  <p>此类中的所有抽象方法必须由希望为特定算法提供参数管理的每个加密服务提供者实现。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see AlgorithmParameters
 * @see java.security.spec.AlgorithmParameterSpec
 * @see java.security.spec.DSAParameterSpec
 *
 * @since 1.2
 */

public abstract class AlgorithmParametersSpi {

    /**
     * Initializes this parameters object using the parameters
     * specified in {@code paramSpec}.
     *
     * <p>
     *  使用{@code paramSpec}中指定的参数初始化此参数对象。
     * 
     * 
     * @param paramSpec the parameter specification.
     *
     * @exception InvalidParameterSpecException if the given parameter
     * specification is inappropriate for the initialization of this parameter
     * object.
     */
    protected abstract void engineInit(AlgorithmParameterSpec paramSpec)
        throws InvalidParameterSpecException;

    /**
     * Imports the specified parameters and decodes them
     * according to the primary decoding format for parameters.
     * The primary decoding format for parameters is ASN.1, if an ASN.1
     * specification for this type of parameters exists.
     *
     * <p>
     *  导入指定的参数,根据参数的主解码格式进行解码。如果存在这种类型的参数的ASN.1规范,则参数的主解码格式是ASN.1。
     * 
     * 
     * @param params the encoded parameters.
     *
     * @exception IOException on decoding errors
     */
    protected abstract void engineInit(byte[] params)
        throws IOException;

    /**
     * Imports the parameters from {@code params} and
     * decodes them according to the specified decoding format.
     * If {@code format} is null, the
     * primary decoding format for parameters is used. The primary decoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * <p>
     *  从{@code params}导入参数,并根据指定的解码格式对其进行解码。如果{@code format}为null,则使用参数的主解码格式。
     * 如果存在用于这些参数的ASN.1规范,则主解码格式是ASN.1。
     * 
     * 
     * @param params the encoded parameters.
     *
     * @param format the name of the decoding format.
     *
     * @exception IOException on decoding errors
     */
    protected abstract void engineInit(byte[] params, String format)
        throws IOException;

    /**
     * Returns a (transparent) specification of this parameters
     * object.
     * {@code paramSpec} identifies the specification class in which
     * the parameters should be returned. It could, for example, be
     * {@code DSAParameterSpec.class}, to indicate that the
     * parameters should be returned in an instance of the
     * {@code DSAParameterSpec} class.
     *
     * <p>
     *  返回此参数对象的(透明)规范。 {@code paramSpec}标识应返回参数的规范类。
     * 例如,它可以是{@code DSAParameterSpec.class},以指示参数应在{@code DSAParameterSpec}类的实例中返回。
     * 
     * 
     * @param <T> the type of the parameter specification to be returned
     *
     * @param paramSpec the specification class in which
     * the parameters should be returned.
     *
     * @return the parameter specification.
     *
     * @exception InvalidParameterSpecException if the requested parameter
     * specification is inappropriate for this parameter object.
     */
    protected abstract
        <T extends AlgorithmParameterSpec>
        T engineGetParameterSpec(Class<T> paramSpec)
        throws InvalidParameterSpecException;

    /**
     * Returns the parameters in their primary encoding format.
     * The primary encoding format for parameters is ASN.1, if an ASN.1
     * specification for this type of parameters exists.
     *
     * <p>
     * 返回主要编码格式的参数。如果存在此类型的参数的ASN.1规范,则参数的主要编码格式为ASN.1。
     * 
     * 
     * @return the parameters encoded using their primary encoding format.
     *
     * @exception IOException on encoding errors.
     */
    protected abstract byte[] engineGetEncoded() throws IOException;

    /**
     * Returns the parameters encoded in the specified format.
     * If {@code format} is null, the
     * primary encoding format for parameters is used. The primary encoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * <p>
     *  返回以指定格式编码的参数。如果{@code format}为null,则使用参数的主要编码格式。如果存在这些参数的ASN.1规范,则主要编码格式为ASN.1。
     * 
     * 
     * @param format the name of the encoding format.
     *
     * @return the parameters encoded using the specified encoding scheme.
     *
     * @exception IOException on encoding errors.
     */
    protected abstract byte[] engineGetEncoded(String format)
        throws IOException;

    /**
     * Returns a formatted string describing the parameters.
     *
     * <p>
     *  返回描述参数的格式化字符串。
     * 
     * @return a formatted string describing the parameters.
     */
    protected abstract String engineToString();
}
