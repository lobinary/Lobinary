/***** Lobxxx Translate Finished ******/
/*
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
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 *  The <code>TypeInfo</code> interface represents a type referenced from
 * <code>Element</code> or <code>Attr</code> nodes, specified in the schemas
 * associated with the document. The type is a pair of a namespace URI and
 * name properties, and depends on the document's schema.
 * <p> If the document's schema is an XML DTD [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>], the values
 * are computed as follows:
 * <ul>
 * <li> If this type is referenced from an
 * <code>Attr</code> node, <code>typeNamespace</code> is
 * <code>"http://www.w3.org/TR/REC-xml"</code> and <code>typeName</code>
 * represents the <b>[attribute type]</b> property in the [<a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204/'>XML Information Set</a>]
 * . If there is no declaration for the attribute, <code>typeNamespace</code>
 *  and <code>typeName</code> are <code>null</code>.
 * </li>
 * <li> If this type is
 * referenced from an <code>Element</code> node, <code>typeNamespace</code>
 * and <code>typeName</code> are <code>null</code>.
 * </li>
 * </ul>
 * <p> If the document's schema is an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
 * , the values are computed as follows using the post-schema-validation
 * infoset contributions (also called PSVI contributions):
 * <ul>
 * <li> If the <b>[validity]</b> property exists AND is <em>"invalid"</em> or <em>"notKnown"</em>: the {target namespace} and {name} properties of the declared type if
 * available, otherwise <code>null</code>.
 * <p ><b>Note:</b>  At the time of writing, the XML Schema specification does
 * not require exposing the declared type. Thus, DOM implementations might
 * choose not to provide type information if validity is not valid.
 * </li>
 * <li> If the <b>[validity]</b> property exists and is <em>"valid"</em>:
 * <ol>
 * <li> If <b>[member type definition]</b> exists:
 * <ol>
 * <li>If {name} is not absent, then expose {name} and {target
 * namespace} properties of the <b>[member type definition]</b> property;
 * </li>
 * <li>Otherwise, expose the namespace and local name of the
 * corresponding anonymous type name.
 * </li>
 * </ol>
 * </li>
 * <li> If the <b>[type definition]</b> property exists:
 * <ol>
 * <li>If {name} is not absent, then expose {name} and {target
 * namespace} properties of the <b>[type definition]</b> property;
 * </li>
 * <li>Otherwise, expose the namespace and local name of the
 * corresponding anonymous type name.
 * </li>
 * </ol>
 * </li>
 * <li> If the <b>[member type definition anonymous]</b> exists:
 * <ol>
 * <li>If it is false, then expose <b>[member type definition name]</b> and <b>[member type definition namespace]</b> properties;
 * </li>
 * <li>Otherwise, expose the namespace and local name of the
 * corresponding anonymous type name.
 * </li>
 * </ol>
 * </li>
 * <li> If the <b>[type definition anonymous]</b> exists:
 * <ol>
 * <li>If it is false, then expose <b>[type definition name]</b> and <b>[type definition namespace]</b> properties;
 * </li>
 * <li>Otherwise, expose the namespace and local name of the
 * corresponding anonymous type name.
 * </li>
 * </ol>
 * </li>
 * </ol>
 * </li>
 * </ul>
 * <p ><b>Note:</b>  Other schema languages are outside the scope of the W3C
 * and therefore should define how to represent their type systems using
 * <code>TypeInfo</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> TypeInfo </code>接口表示从与文档相关联的模式中指定的<code> Element </code>或<code> Attr </code>节点引用的类型。
 * 类型是一对命名空间URI和名称属性,并且取决于文档的模式<p>如果文档的模式是XML DTD [<a href='http://wwww3org/TR/2004/REC-xml-20040204'> X
 * ML 10 </a >],则计算如下：。
 * <code> TypeInfo </code>接口表示从与文档相关联的模式中指定的<code> Element </code>或<code> Attr </code>节点引用的类型。
 * <ul>
 * <li>如果此类型是从<code> Attr </code>节点引用的,<code> typeNamespace </code>是<code>"http：// wwww3org / TR / REC-x
 * ml"</code> <code> typeName </code>代表[<a href='http://wwww3org/TR/2004/REC-xml-infoset-20040204/'>]中的<b>
 *  [attribute type] </b> XML信息集</a>]如果属性没有声明,则<code> typeNamespace </code>和<code> typeName </code>。
 * </li>
 *  <li>如果从<code> Element </code>节点引用此类型,则<code> typeNamespace </code>和<code> typeName </code>
 * </li>
 * </ul>
 * <p>如果文档的模式是XML模式[<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/'> XML模式第1部分</a>],则值为使用后模式
 * 验证信息集贡献(也称为PSVI贡献)计算如下：。
 * <ul>
 *  <li>如果<b> [validity] </b>属性存在且<em>"无效"</em>或<em>"notKnown"</em>：{target namespace}注释：</b>在编写时,XML Sc
 * hema规范不需要公开声明的类型因此,DOM实现如果有效性无效,则可以选择不提供类型信息。
 * </li>
 *  <li>如果<b> [validity] </b>属性存在且<em>"有效"</em>：
 * <ol>
 *  <li>如果存在<b> [成员类型定义] </b>：
 * <ol>
 * <li>如果{name}不存在,则显示<b> [member type definition] </b>属性的{name}和{target namespace}属性;
 * </li>
 *  <li>否则,公开相应匿名类型名称的命名空间和本地名称
 * </li>
 * </ol>
 * </li>
 *  <li>如果<b> [type definition] </b>属性存在：
 * <ol>
 *  <li>如果{name}不存在,则显示<b> [type definition] </b>属性的{name}和{target namespace}属性;
 * </li>
 *  <li>否则,公开相应匿名类型名称的命名空间和本地名称
 * </li>
 * </ol>
 * </li>
 *  <li>如果存在<b> [成员类型定义anonymous] </b>：
 * <ol>
 *  <li>如果为false,则公开<b> [成员类型定义名称] </b>和<b> [成员类型定义命名空间] </b>属性;
 * </li>
 *  <li>否则,公开相应匿名类型名称的命名空间和本地名称
 * </li>
 * </ol>
 * </li>
 * <li>如果存在<b> [类型定义匿名] </b>：
 * <ol>
 * 
 * @since DOM Level 3
 */
public interface TypeInfo {
    /**
     *  The name of a type declared for the associated element or attribute,
     * or <code>null</code> if unknown.
     * <p>
     *  <li>如果为false,则显示<b> [类型定义名称] </b>和<b> [类型定义命名空间] </b>属性;
     * </li>
     *  <li>否则,公开相应匿名类型名称的命名空间和本地名称
     * </li>
     * </ol>
     * </li>
     * </ol>
     * </li>
     * </ul>
     *  <p> <b>注意：</b>其他模式语言超出了W3C的范围,因此应该定义如何使用<code> TypeInfo </code> <p> ='http：// wwww3org / TR / 2004 /
     *  REC-DOM-Level-3-Core-20040407'> Document Object Model(DOM)Level 3 Core Specification </a>。
     * 
     */
    public String getTypeName();

    /**
     *  The namespace of the type declared for the associated element or
     * attribute or <code>null</code> if the element does not have
     * declaration or if no namespace information is available.
     * <p>
     *  为关联的元素或属性声明的类型的名称,或<code> null </code>(如果未知)
     * 
     */
    public String getTypeNamespace();

    // DerivationMethods
    /**
     *  If the document's schema is an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
     * , this constant represents the derivation by <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#key-typeRestriction'>
     * restriction</a> if complex types are involved, or a <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#element-restriction'>
     * restriction</a> if simple types are involved.
     * <br>  The reference type definition is derived by restriction from the
     * other type definition if the other type definition is the same as the
     * reference type definition, or if the other type definition can be
     * reached recursively following the {base type definition} property
     * from the reference type definition, and all the <em>derivation methods</em> involved are restriction.
     * <p>
     * 如果元素没有声明或没有命名空间信息可用,则为相关元素或属性声明的类型的命名空间或<code> null </code>
     * 
     */
    public static final int DERIVATION_RESTRICTION    = 0x00000001;
    /**
     *  If the document's schema is an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
     * , this constant represents the derivation by <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#key-typeExtension'>
     * extension</a>.
     * <br>  The reference type definition is derived by extension from the
     * other type definition if the other type definition can be reached
     * recursively following the {base type definition} property from the
     * reference type definition, and at least one of the <em>derivation methods</em> involved is an extension.
     * <p>
     * 如果文档的模式是XML模式[<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/'> XML模式第1部分</a>],此常数表示<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/#key-typeRestriction'>
     * 限制</a>(如果涉及复杂类型),或<a href ='http：/ / wwww3org / TR / 2001 / REC-xmlschema-1-20010502 /#element-restriction'>
     * 限制</a>(如果涉及简单类型)<br>如果其他类型定义与引用类型定义相同,或者如果其他类型定义可以在{base type definition}属性之后递归到达,则引用类型定义由其他类型定义的限制派生
     * 。
     * 引用类型定义,以及所涉及的所有<em>派生方法</em>都是限制。
     * 
     */
    public static final int DERIVATION_EXTENSION      = 0x00000002;
    /**
     *  If the document's schema is an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
     * , this constant represents the <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#element-union'>
     * union</a> if simple types are involved.
     * <br> The reference type definition is derived by union from the other
     * type definition if there exists two type definitions T1 and T2 such
     * as the reference type definition is derived from T1 by
     * <code>DERIVATION_RESTRICTION</code> or
     * <code>DERIVATION_EXTENSION</code>, T2 is derived from the other type
     * definition by <code>DERIVATION_RESTRICTION</code>, T1 has {variety} <em>union</em>, and one of the {member type definitions} is T2. Note that T1 could be
     * the same as the reference type definition, and T2 could be the same
     * as the other type definition.
     * <p>
     * 如果文档的模式是XML模式[<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/'> XML模式第1部分</a>],此常数表示<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/#key-typeExtension'>
     * 扩展程序</a> <br>引用类型定义是通过其他类型定义的扩展名派生的如果可以在来自引用类型定义的{基本类型定义}属性之后递归地达到其他类型定义,并且涉及的至少一个引导方法是扩展。
     * 
     */
    public static final int DERIVATION_UNION          = 0x00000004;
    /**
     *  If the document's schema is an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
     * , this constant represents the <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#element-list'>list</a>.
     * <br> The reference type definition is derived by list from the other
     * type definition if there exists two type definitions T1 and T2 such
     * as the reference type definition is derived from T1 by
     * <code>DERIVATION_RESTRICTION</code> or
     * <code>DERIVATION_EXTENSION</code>, T2 is derived from the other type
     * definition by <code>DERIVATION_RESTRICTION</code>, T1 has {variety} <em>list</em>, and T2 is the {item type definition}. Note that T1 could be the same as
     * the reference type definition, and T2 could be the same as the other
     * type definition.
     * <p>
     * 如果文档的模式是XML模式[<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/'> XML模式第1部分</a>],此常数表示<a href ='http：// wwww3org / TR / 2001 / REC-xmlschema-1-20010502 /#element-union'>
     *  union </a>如果涉及简单类型<br>引用类型定义由union其他类型定义如果存在两个类型定义T1和T2,例如引用类型定义是由<code> DERIVATION_RESTRICTION </code>
     * 或<code> DERIVATION_EXTENSION </code>从T1派生的,T2是从其他类型定义<code> DERIVATION_RESTRICTION </code>,T1有{variety}
     *  <em> union </em>,其中一个{member type definitions}是T2注意,T1可以与引用类型定义相同,T2可以与其他类型定义相同。
     * 
     */
    public static final int DERIVATION_LIST           = 0x00000008;

    /**
     *  This method returns if there is a derivation between the reference
     * type definition, i.e. the <code>TypeInfo</code> on which the method
     * is being called, and the other type definition, i.e. the one passed
     * as parameters.
     * <p>
     * 如果文档的模式是XML模式[<a href='http://wwww3org/TR/2001/REC-xmlschema-1-20010502/'> XML模式第1部分</a>],此常数表示<a href ='http：// wwww3org / TR / 2001 / REC-xmlschema-1-20010502 /#element-list'>
     * 列表</a> <br>引用类型定义由其他类型定义的列表派生,如果有存在两个类型定义T1和T2,例如引用类型定义是由<code> DERIVATION_RESTRICTION </code>或<code>
     *  DERIVATION_EXTENSION </code>从T1派生的,T2是从<code> DERIVATION_RESTRICTION < / code>,T1具有{品种}列表</em>,T2是{item type definition}
     * 。
     * 注意,T1可以与引用类型定义相同,T2可以与其他类型相同定义。
     * 
     * @param typeNamespaceArg  the namespace of the other type definition.
     * @param typeNameArg  the name of the other type definition.
     * @param derivationMethod  the type of derivation and conditions applied
     *   between two types, as described in the list of constants provided
     *   in this interface.
     * @return  If the document's schema is a DTD or no schema is associated
     *   with the document, this method will always return <code>false</code>
     *   .  If the document's schema is an XML Schema, the method will return
     *   <code>true</code> if the reference type definition is derived from
     *   the other type definition according to the derivation parameter. If
     *   the value of the parameter is <code>0</code> (no bit is set to
     *   <code>1</code> for the <code>derivationMethod</code> parameter),
     *   the method will return <code>true</code> if the other type
     *   definition can be reached by recursing any combination of {base
     *   type definition}, {item type definition}, or {member type
     *   definitions} from the reference type definition.
     */
    public boolean isDerivedFrom(String typeNamespaceArg,
                                 String typeNameArg,
                                 int derivationMethod);

}
