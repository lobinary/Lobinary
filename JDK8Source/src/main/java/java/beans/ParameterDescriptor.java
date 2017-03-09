/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1997, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

/**
 * The ParameterDescriptor class allows bean implementors to provide
 * additional information on each of their parameters, beyond the
 * low level type information provided by the java.lang.reflect.Method
 * class.
 * <p>
 * Currently all our state comes from the FeatureDescriptor base class.
 * <p>
 *  ParameterDescriptor类允许bean实现者提供关于每个参数的额外信息,超出java.lang.reflect.Method类提供的低级类型信息。
 * <p>
 *  目前所有的状态都来自FeatureDescriptor基类。
 * 
 */

public class ParameterDescriptor extends FeatureDescriptor {

    /**
     * Public default constructor.
     * <p>
     *  公共默认构造函数。
     * 
     */
    public ParameterDescriptor() {
    }

    /**
     * Package private dup constructor.
     * This must isolate the new object from any changes to the old object.
     * <p>
     *  包私有dup构造函数。这必须将新对象与对旧对象的任何更改隔离。
     */
    ParameterDescriptor(ParameterDescriptor old) {
        super(old);
    }

}
