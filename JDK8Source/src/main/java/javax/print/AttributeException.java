/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import javax.print.attribute.Attribute;

/**
 * Interface AttributeException is a mixin interface which a subclass of
 * {@link
 * PrintException PrintException} can implement to report an error condition
 * involving one or more printing attributes that a particular Print
 * Service instance does not support. Either the attribute is not supported at
 * all, or the attribute is supported but the particular specified value is not
 * supported. The Print Service API does not define any print exception
 * classes that implement interface AttributeException, that being left to the
 * Print Service implementor's discretion.
 *
 * <p>
 *  Interface AttributeException是一个mixin接口,{@link PrintException PrintException}的子类可以实现它来报告涉及特定打印服务实例不支持
 * 的一个或多个打印属性的错误条件。
 * 不支持该属性,或者支持该属性,但不支持特定的指定值。打印服务API不定义任何实现接口AttributeException的打印异常类,由打印服务实现者自行决定。
 * 
 */

public interface AttributeException {


    /**
     * Returns the array of printing attribute classes for which the Print
     * Service instance does not support the attribute at all, or null if
     * there are no such attributes. The objects in the returned array are
     * classes that extend the base interface
     * {@link javax.print.attribute.Attribute Attribute}.
     *
     * <p>
     *  返回打印服务实例根本不支持该属性的打印属性类的数组,如果没有此类属性,则返回null。
     * 返回数组中的对象是扩展基本接口{@link javax.print.attribute.Attribute Attribute}的类。
     * 
     * 
     * @return unsupported attribute classes
     */
    public Class[] getUnsupportedAttributes();

    /**
     * Returns the array of printing attributes for which the Print Service
     * instance supports the attribute but does not support that particular
     * value of the attribute, or null if there are no such attribute values.
     *
     * <p>
     * 
     * @return unsupported attribute values
     */
    public Attribute[] getUnsupportedValues();

    }
