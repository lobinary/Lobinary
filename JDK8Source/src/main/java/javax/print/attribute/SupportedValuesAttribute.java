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


package javax.print.attribute;

/**
 * Interface SupportedValuesAttribute is a tagging interface which a printing
 * attribute class implements to indicate the attribute describes the supported
 * values for another attribute. For example, if a Print Service instance
 * supports the {@link javax.print.attribute.standard.Copies Copies}
 * attribute, the Print Service instance will have a {@link
 * javax.print.attribute.standard.CopiesSupported CopiesSupported} attribute,
 * which is a SupportedValuesAttribute giving the legal values a client may
 * specify for the {@link javax.print.attribute.standard.Copies Copies}
 * attribute.
 * <P>
 *
 * <p>
 *  Interface SupportedValuesAttribute是打印属性类实现的标记接口,用于指示属性描述另一个属性支持的值。
 * 例如,如果打印服务实例支持{@link javax.print.attribute.standard.Copies Copies}属性,则Print Service实例将具有{@link javax.print.attribute.standard.CopiesSupported CopiesSupported}
 * 属性,该属性是SupportedValuesAttribute,提供客户端可以为{@link javax.print.attribute.standard.Copies Copies}属性指定的合法值。
 * 
 * @author  Alan Kaminsky
 */
public interface SupportedValuesAttribute extends Attribute {
}
