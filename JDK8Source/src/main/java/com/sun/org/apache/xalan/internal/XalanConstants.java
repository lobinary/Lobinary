/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.org.apache.xalan.internal;

import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

/**
 * Commonly used constants.
 *
 * <p>
 *  常用的常量。
 * 
 * 
 * @author Huizhe Wang, Oracle
 *
 * @version $Id: Constants.java,v 1.14 2011-06-07 04:39:40 joehw Exp $
 */
public final class XalanConstants {

    //
    // Constants
    //
    //Xerces security manager
    public static final String SECURITY_MANAGER =
            "http://apache.org/xml/properties/security-manager";

    //
    // Implementation limits: API properties
    //
    /** Oracle JAXP property prefix ("http://www.oracle.com/xml/jaxp/properties/"). */
    public static final String ORACLE_JAXP_PROPERTY_PREFIX =
        "http://www.oracle.com/xml/jaxp/properties/";
    /**
     * JDK entity expansion limit; Note that the existing system property
     * "entityExpansionLimit" with no prefix is still observed
     * <p>
     *  JDK实体扩展限制;注意,仍然观察到没有前缀的现有系统属性"entityExpansionLimit"
     * 
     */
    public static final String JDK_ENTITY_EXPANSION_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "entityExpansionLimit";

    /**
     * JDK element attribute limit; Note that the existing system property
     * "elementAttributeLimit" with no prefix is still observed
     * <p>
     *  JDK元素属性限制;注意,仍然观察到没有前缀的现有系统属性"elementAttributeLimit"
     * 
     */
    public static final String JDK_ELEMENT_ATTRIBUTE_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "elementAttributeLimit";

    /**
     * JDK maxOccur limit; Note that the existing system property
     * "maxOccurLimit" with no prefix is still observed
     * <p>
     *  JDK maxOccur限制;注意,仍然观察到没有前缀的现有系统属性"maxOccurLimit"
     * 
     */
    public static final String JDK_MAX_OCCUR_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "maxOccurLimit";

    /**
     * JDK total entity size limit
     * <p>
     *  JDK总实体大小限制
     * 
     */
    public static final String JDK_TOTAL_ENTITY_SIZE_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "totalEntitySizeLimit";

    /**
     * JDK maximum general entity size limit
     * <p>
     *  JDK最大一般实体大小限制
     * 
     */
    public static final String JDK_GENERAL_ENTITY_SIZE_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "maxGeneralEntitySizeLimit";
    /**
     * JDK maximum parameter entity size limit
     * <p>
     *  JDK最大参数实体大小限制
     * 
     */
    public static final String JDK_PARAMETER_ENTITY_SIZE_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "maxParameterEntitySizeLimit";
    /**
     * JDK maximum XML name limit
     * <p>
     *  JDK最大XML名称限制
     * 
     */
    public static final String JDK_XML_NAME_LIMIT =
            ORACLE_JAXP_PROPERTY_PREFIX + "maxXMLNameLimit";

    /**
     * JDK maxElementDepth limit
     * <p>
     *  JDK maxElementDepth限制
     * 
     */
    public static final String JDK_MAX_ELEMENT_DEPTH =
            ORACLE_JAXP_PROPERTY_PREFIX + "maxElementDepth";

    /**
     * JDK property indicating whether the parser shall print out entity
     * count information
     * Value: a string "yes" means print, "no" or any other string means not.
     * <p>
     *  JDK属性,指示解析器是否应打印出实体计数信息值：字符串"yes"表示打印,"否"或任何其他字符串表示不打印。
     * 
     */
    public static final String JDK_ENTITY_COUNT_INFO =
            ORACLE_JAXP_PROPERTY_PREFIX + "getEntityCountInfo";

    //
    // Implementation limits: corresponding System Properties of the above
    // API properties
    //
    /**
     * JDK entity expansion limit; Note that the existing system property
     * "entityExpansionLimit" with no prefix is still observed
     * <p>
     *  JDK实体扩展限制;注意,仍然观察到没有前缀的现有系统属性"entityExpansionLimit"
     * 
     */
    public static final String SP_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit";

    /**
     * JDK element attribute limit; Note that the existing system property
     * "elementAttributeLimit" with no prefix is still observed
     * <p>
     *  JDK元素属性限制;注意,仍然观察到没有前缀的现有系统属性"elementAttributeLimit"
     * 
     */
    public static final String SP_ELEMENT_ATTRIBUTE_LIMIT =  "jdk.xml.elementAttributeLimit";

    /**
     * JDK maxOccur limit; Note that the existing system property
     * "maxOccurLimit" with no prefix is still observed
     * <p>
     *  JDK maxOccur限制;注意,仍然观察到没有前缀的现有系统属性"maxOccurLimit"
     * 
     */
    public static final String SP_MAX_OCCUR_LIMIT = "jdk.xml.maxOccurLimit";

    /**
     * JDK total entity size limit
     * <p>
     *  JDK总实体大小限制
     * 
     */
    public static final String SP_TOTAL_ENTITY_SIZE_LIMIT = "jdk.xml.totalEntitySizeLimit";

    /**
     * JDK maximum general entity size limit
     * <p>
     *  JDK最大一般实体大小限制
     * 
     */
    public static final String SP_GENERAL_ENTITY_SIZE_LIMIT = "jdk.xml.maxGeneralEntitySizeLimit";
    /**
     * JDK maximum parameter entity size limit
     * <p>
     *  JDK最大参数实体大小限制
     * 
     */
    public static final String SP_PARAMETER_ENTITY_SIZE_LIMIT = "jdk.xml.maxParameterEntitySizeLimit";
    /**
     * JDK maximum XML name limit
     * <p>
     *  JDK最大XML名称限制
     * 
     */
    public static final String SP_XML_NAME_LIMIT = "jdk.xml.maxXMLNameLimit";

    /**
     * JDK maxElementDepth limit
     * <p>
     *  JDK maxElementDepth限制
     * 
     */
    public static final String SP_MAX_ELEMENT_DEPTH = "jdk.xml.maxElementDepth";

    /**
     * JDK TransformerFactory and Transformer attribute that specifies a class
     * loader that will be used for extension functions class loading
     * Value: a "null", the default value, means that the default EF class loading
     * path will be used.
     * Instance of ClassLoader: the specified instance of ClassLoader will be used
     * for extension functions loading during translation process
     * <p>
     * JDK TransformerFactory和Transformer属性,指定将用于扩展函数的类加载器类加载值：默认值"null"表示将使用默认的EF类加载路径。
     *  ClassLoader的实例：指定的ClassLoader实例将用于在翻译过程中加载扩展功能。
     * 
     */
    public static final String JDK_EXTENSION_CLASSLOADER = "jdk.xml.transform.extensionClassLoader";

    //legacy System Properties
    public final static String ENTITY_EXPANSION_LIMIT = "entityExpansionLimit";
    public static final String ELEMENT_ATTRIBUTE_LIMIT = "elementAttributeLimit" ;
    public final static String MAX_OCCUR_LIMIT = "maxOccurLimit";

    /**
     * A string "yes" that can be used for properties such as getEntityCountInfo
     * <p>
     *  可用于诸如getEntityCountInfo之类的属性的字符串"yes"
     * 
     */
    public static final String JDK_YES = "yes";

    // Oracle Feature:
    /**
     * <p>Use Service Mechanism</p>
     *
     * <ul>
     *   <li>
         * {@code true} instruct an object to use service mechanism to
         * find a service implementation. This is the default behavior.
         *   </li>
         *   <li>
         * {@code false} instruct an object to skip service mechanism and
         * use the default implementation for that service.
         *   </li>
         * </ul>
         * <p>
         *  <p>使用服务机制</p>
         * 
         * <ul>
         * <li>
         *  {@code true}指示对象使用服务机制来查找服务实现。这是默认行为。
         * </li>
         * <li>
         *  {@code false}指示对象跳过服务机制并使用该服务的默认实现。
         * </li>
         * </ul>
         */
    public static final String ORACLE_FEATURE_SERVICE_MECHANISM = "http://www.oracle.com/feature/use-service-mechanism";


    //System Properties corresponding to ACCESS_EXTERNAL_* properties
    public static final String SP_ACCESS_EXTERNAL_STYLESHEET = "javax.xml.accessExternalStylesheet";
    public static final String SP_ACCESS_EXTERNAL_DTD = "javax.xml.accessExternalDTD";

    //all access keyword
    public static final String ACCESS_EXTERNAL_ALL = "all";

    /**
     * Default value when FEATURE_SECURE_PROCESSING (FSP) is set to true
     * <p>
     *  当FEATURE_SECURE_PROCESSING(FSP)设置为true时的默认值
     * 
     */
    public static final String EXTERNAL_ACCESS_DEFAULT_FSP = "";

    /**
     * FEATURE_SECURE_PROCESSING (FSP) is false by default
     * <p>
     *  默认情况下,FEATURE_SECURE_PROCESSING(FSP)为false
     * 
     */
    public static final String EXTERNAL_ACCESS_DEFAULT = ACCESS_EXTERNAL_ALL;

    public static final String XML_SECURITY_PROPERTY_MANAGER =
            ORACLE_JAXP_PROPERTY_PREFIX + "xmlSecurityPropertyManager";

    /**
     * Feature enableExtensionFunctions
     * <p>
     *  Feature enableExtensionFunctions
     * 
     */
    public static final String ORACLE_ENABLE_EXTENSION_FUNCTION =
            ORACLE_JAXP_PROPERTY_PREFIX + "enableExtensionFunctions";
    public static final String SP_ORACLE_ENABLE_EXTENSION_FUNCTION = "javax.xml.enableExtensionFunctions";

    /**
     * Values for a feature
     * <p>
     *  功能的值
     * 
     */
    public static final String FEATURE_TRUE = "true";
    public static final String FEATURE_FALSE = "false";

    /**
     * Check if we're in jdk8 or above
     * <p>
     *  检查我们是否在jdk8或以上
     * 
     */
    public static final boolean IS_JDK8_OR_ABOVE = isJavaVersionAtLeast(8);

    /*
     * Check the version of the current JDK against that specified in the
     * parameter
     *
     * There is a proposal to change the java version string to:
     * MAJOR.MINOR.FU.CPU.PSU-BUILDNUMBER_BUGIDNUMBER_OPTIONAL
     * This method would work with both the current format and that proposed
     *
     * <p>
     *  根据参数中指定的版本检查当前JDK的版本
     * 
     * 
     * @param compareTo a JDK version to be compared to
     * @return true if the current version is the same or above that represented
     * by the parameter
     */
    public static boolean isJavaVersionAtLeast(int compareTo) {
        String javaVersion = SecuritySupport.getSystemProperty("java.version");
        String versions[] = javaVersion.split("\\.", 3);
        if (Integer.parseInt(versions[0]) >= compareTo ||
            Integer.parseInt(versions[1]) >= compareTo) {
            return true;
        }
        return false;
    }
} // class Constants
