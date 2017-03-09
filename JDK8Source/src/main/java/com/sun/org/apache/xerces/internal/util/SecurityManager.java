/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2003 The Apache Software Foundation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2003 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款,最初是基于软件版权(c)1999,国际商业机器公司,http://www.apache.org。
 */

package com.sun.org.apache.xerces.internal.util;
import com.sun.org.apache.xerces.internal.impl.Constants;
/**
 * This class is a container for parser settings that relate to
 * security, or more specifically, it is intended to be used to prevent denial-of-service
 * attacks from being launched against a system running Xerces.
 * Any component that is aware of a denial-of-service attack that can arise
 * from its processing of a certain kind of document may query its Component Manager
 * for the property (http://apache.org/xml/properties/security-manager)
 * whose value will be an instance of this class.
 * If no value has been set for the property, the component should proceed in the "usual" (spec-compliant)
 * manner.  If a value has been set, then it must be the case that the component in
 * question needs to know what method of this class to query.  This class
 * will provide defaults for all known security issues, but will also provide
 * setters so that those values can be tailored by applications that care.
 *
 * <p>
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  Neil Graham, IBM
 *
 */
public final class SecurityManager {

    //
    // Constants
    //

    // default value for entity expansion limit
    private final static int DEFAULT_ENTITY_EXPANSION_LIMIT = 64000;

    /** Default value of number of nodes created. **/
    private final static int DEFAULT_MAX_OCCUR_NODE_LIMIT = 5000;

    //
    // Data
    //

        private final static int DEFAULT_ELEMENT_ATTRIBUTE_LIMIT = 10000;

    /** Entity expansion limit. **/
    private int entityExpansionLimit;

    /** W3C XML Schema maxOccurs limit. **/
    private int maxOccurLimit;

        private int fElementAttributeLimit;
    // default constructor.  Establishes default values for
    // all known security holes.
    /**
     * Default constructor.  Establishes default values
     * for known security vulnerabilities.
     * <p>
     * 此类是用于与安全性相关的解析器设置的容器,或者更具体地,它旨在用于防止针对运行Xerces的系统启动拒绝服务攻击。
     * 任何意识到由于处理某种文档而可能产生的拒绝服务攻击的组件可以向其组件管理器查询该属性(http://apache.org/xml/properties/security-manager)其值将是此类的实
     * 例。
     * 此类是用于与安全性相关的解析器设置的容器,或者更具体地,它旨在用于防止针对运行Xerces的系统启动拒绝服务攻击。如果没有为属性设置值,组件应以"通常"(规范兼容)的方式继续。
     * 如果已经设置了一个值,则必须是有问题的组件需要知道该类的什么方法来查询的情况。此类将为所有已知的安全问题提供默认值,但也将提供设置器,以便那些值可以由关心的应用程序定制。
     * 
     */
    public SecurityManager() {
        entityExpansionLimit = DEFAULT_ENTITY_EXPANSION_LIMIT;
        maxOccurLimit = DEFAULT_MAX_OCCUR_NODE_LIMIT ;
                fElementAttributeLimit = DEFAULT_ELEMENT_ATTRIBUTE_LIMIT;
                //We are reading system properties only once ,
                //at the time of creation of this object ,
                readSystemProperties();
    }

    /**
     * <p>Sets the number of entity expansions that the
     * parser should permit in a document.</p>
     *
     * <p>
     *  默认构造函数。建立已知安全漏洞的默认值。
     * 
     * 
     * @param limit the number of entity expansions
     * permitted in a document
     */
    public void setEntityExpansionLimit(int limit) {
        entityExpansionLimit = limit;
    }

    /**
     * <p>Returns the number of entity expansions
     * that the parser permits in a document.</p>
     *
     * <p>
     *  <p>设置解析器在文档中应允许的实体扩展数。</p>
     * 
     * 
     * @return the number of entity expansions
     * permitted in a document
     */
    public int getEntityExpansionLimit() {
        return entityExpansionLimit;
    }

    /**
     * <p>Sets the limit of the number of content model nodes
     * that may be created when building a grammar for a W3C
     * XML Schema that contains maxOccurs attributes with values
     * other than "unbounded".</p>
     *
     * <p>
     *  <p>返回解析器在文档中允许的实体扩展数。</p>
     * 
     * 
     * @param limit the maximum value for maxOccurs other
     * than "unbounded"
     */
    public void setMaxOccurNodeLimit(int limit){
        maxOccurLimit = limit;
    }

    /**
     * <p>Returns the limit of the number of content model nodes
     * that may be created when building a grammar for a W3C
     * XML Schema that contains maxOccurs attributes with values
     * other than "unbounded".</p>
     *
     * <p>
     *  <p>设置在为包含值为"unbounded"以外的maxOccurs属性的W3C XML模式构建语法时可能创建的内容模型节点数量的限制。</p>
     * 
     * 
     * @return the maximum value for maxOccurs other
     * than "unbounded"
     */
    public int getMaxOccurNodeLimit(){
        return maxOccurLimit;
    }

    public int getElementAttrLimit(){
                return fElementAttributeLimit;
        }

        public void setElementAttrLimit(int limit){
                fElementAttributeLimit = limit;
        }

        private void readSystemProperties(){

                try {
                        String value = System.getProperty(Constants.ENTITY_EXPANSION_LIMIT);
                        if(value != null && !value.equals("")){
                                entityExpansionLimit = Integer.parseInt(value);
                                if (entityExpansionLimit < 0)
                                        entityExpansionLimit = DEFAULT_ENTITY_EXPANSION_LIMIT;
                        }
                        else
                                entityExpansionLimit = DEFAULT_ENTITY_EXPANSION_LIMIT;
                }catch(Exception ex){}

                try {
                        String value = System.getProperty(Constants.MAX_OCCUR_LIMIT);
                        if(value != null && !value.equals("")){
                                maxOccurLimit = Integer.parseInt(value);
                                if (maxOccurLimit < 0)
                                        maxOccurLimit = DEFAULT_MAX_OCCUR_NODE_LIMIT;
                        }
                        else
                                maxOccurLimit = DEFAULT_MAX_OCCUR_NODE_LIMIT;
                }catch(Exception ex){}

                try {
                        String value = System.getProperty(Constants.ELEMENT_ATTRIBUTE_LIMIT);
                        if(value != null && !value.equals("")){
                                fElementAttributeLimit = Integer.parseInt(value);
                                if ( fElementAttributeLimit < 0)
                                        fElementAttributeLimit = DEFAULT_ELEMENT_ATTRIBUTE_LIMIT;
                        }
                        else
                                fElementAttributeLimit = DEFAULT_ELEMENT_ATTRIBUTE_LIMIT;

                }catch(Exception ex){}

        }

} // class SecurityManager
