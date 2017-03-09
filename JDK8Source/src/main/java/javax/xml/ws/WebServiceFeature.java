/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;


/**
 * A WebServiceFeature is used to represent a feature that can be
 * enabled or disabled for a web service.
 * <p>
 * The JAX-WS specification will define some standard features and
 * JAX-WS implementors are free to define additional features if
 * necessary.  Vendor specific features may not be portable so
 * caution should be used when using them. Each Feature definition
 * MUST define a <code>public static final String ID</code>
 * that can be used in the Feature annotation to refer
 * to the feature. This ID MUST be unique across all features
 * of all vendors.  When defining a vendor specific feature ID,
 * use a vendor specific namespace in the ID string.
 *
 * <p>
 *  WebServiceFeature用于表示可以为Web服务启用或禁用的功能。
 * <p>
 *  JAX-WS规范将定义一些标准特性,如果需要,JAX-WS实现者可以自由定义附加特性。供应商特定的功能可能不是便携式的,所以在使用它们时应该小心。
 * 每个要素定义必须定义一个<code> public static final String ID </code>,可以在Feature注释中使用它来引用该特性。
 * 此ID必须在所有供应商的所有功能中是唯一的。在定义供应商特定的功能ID时,在ID字符串中使用供应商特定的命名空间。
 * 
 * 
 * @see javax.xml.ws.RespectBindingFeature
 * @see javax.xml.ws.soap.AddressingFeature
 * @see javax.xml.ws.soap.MTOMFeature
 *
 * @since 2.1
 */
public abstract class WebServiceFeature {
   /**
    * Each Feature definition MUST define a public static final
    * String ID that can be used in the Feature annotation to refer
    * to the feature.
    * <p>
    *  每个要素定义必须定义一个public static final String ID,可以在要素注释中使用它来引用要素。
    * 
    */
   // public static final String ID = "some unique feature Identifier";

   /**
    * Get the unique identifier for this WebServiceFeature.
    *
    * <p>
    *  获取此WebServiceFeature的唯一标识符。
    * 
    * 
    * @return the unique identifier for this feature.
    */
   public abstract String getID();

   /**
    * Specifies if the feature is enabled or disabled
    * <p>
    *  指定是否启用或禁用此功能
    * 
    */
   protected boolean enabled = false;


   protected WebServiceFeature(){}


   /**
    * Returns <code>true</code> if this feature is enabled.
    *
    * <p>
    *  如果启用此功能,则返回<code> true </code>。
    * 
    * @return <code>true</code> if and only if the feature is enabled .
    */
   public boolean isEnabled() {
       return enabled;
   }
}
