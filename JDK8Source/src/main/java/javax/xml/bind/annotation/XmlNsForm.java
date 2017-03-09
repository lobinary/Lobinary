/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation;

/**
 * Enumeration of XML Schema namespace qualifications.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p><b>Usage</b>
 * <p>
 * The namespace qualification values are used in the annotations
 * defined in this packge. The enumeration values are mapped as follows:
 *
 * <p>
 * <table border="1" cellpadding="4" cellspacing="3">
 *   <tbody>
 *     <tr>
 *       <td><b>Enum Value</b></td>
 *       <td><b>XML Schema Value</b></td>
 *     </tr>
 *
 *     <tr valign="top">
 *       <td>UNQUALIFIED</td>
 *       <td>unqualified</td>
 *     </tr>
 *     <tr valign="top">
 *       <td>QUALIFIED</td>
 *       <td>qualified</td>
 *     </tr>
 *     <tr valign="top">
 *       <td>UNSET</td>
 *       <td>namespace qualification attribute is absent from the
 *           XML Schema fragment</td>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * <p>
 *  XML模式命名空间限定条件的枚举。
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  <p> <b>使用</b>
 * <p>
 *  命名空间限定值用在此packge中定义的注释中。枚举值映射如下：
 * 
 * <p>
 * <table border="1" cellpadding="4" cellspacing="3">
 * <tbody>
 * <tr>
 *  <td> <b>枚举值</b​​> </td> <td> <b> XML模式值</b​​> </td>
 * </tr>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 */
public enum XmlNsForm {UNQUALIFIED, QUALIFIED, UNSET}
