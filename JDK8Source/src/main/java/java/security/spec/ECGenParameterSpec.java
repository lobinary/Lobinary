/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.security.spec;

/**
 * This immutable class specifies the set of parameters used for
 * generating elliptic curve (EC) domain parameters.
 *
 * <p>
 *  这个不可变类指定了用于生成椭圆曲线(EC)域参数的参数集。
 * 
 * 
 * @see AlgorithmParameterSpec
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class ECGenParameterSpec implements AlgorithmParameterSpec {

    private String name;

    /**
     * Creates a parameter specification for EC parameter
     * generation using a standard (or predefined) name
     * {@code stdName} in order to generate the corresponding
     * (precomputed) elliptic curve domain parameters. For the
     * list of supported names, please consult the documentation
     * of provider whose implementation will be used.
     * <p>
     *  使用标准(或预定义)名称{@code stdName}创建EC参数生成的参数规范,以便生成相应的(预先计算的)椭圆曲线域参数。有关受支持的名称的列表,请参阅将使用其实现的提供程序的文档。
     * 
     * 
     * @param stdName the standard name of the to-be-generated EC
     * domain parameters.
     * @exception NullPointerException if {@code stdName}
     * is null.
     */
    public ECGenParameterSpec(String stdName) {
        if (stdName == null) {
            throw new NullPointerException("stdName is null");
        }
        this.name = stdName;
    }

    /**
     * Returns the standard or predefined name of the
     * to-be-generated EC domain parameters.
     * <p>
     *  返回要生成的EC域参数的标准或预定义名称。
     * 
     * @return the standard or predefined name.
     */
    public String getName() {
        return name;
    }
}
