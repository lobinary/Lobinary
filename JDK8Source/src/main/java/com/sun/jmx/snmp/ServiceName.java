/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp;

/**
 * Used for storing default values used by SNMP Runtime services.
 * <p><b>This API is an Oracle Corporation internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  用于存储SNMP运行时服务使用的默认值。 <p> <b>此API是Oracle Corporation内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */
public class ServiceName {

    // private constructor defined to "hide" the default public constructor
    private ServiceName() {
    }

    /**
     * The object name of the MBeanServer delegate object
     * <BR>
     * The value is <CODE>JMImplementation:type=MBeanServerDelegate</CODE>.
     * <p>
     *  MBeanServer委托对象的对象名称
     * <BR>
     *  值为<CODE> JMImplementation：type = MBeanServerDelegate </CODE>。
     * 
     */
    public static final String DELEGATE = "JMImplementation:type=MBeanServerDelegate" ;

    /**
     * The default key properties for registering the class loader of the MLet service.
     * <BR>
     * The value is <CODE>type=MLet</CODE>.
     * <p>
     *  用于注册MLet服务的类装入器的默认键属性。
     * <BR>
     *  值为<CODE> type = MLet </CODE>。
     * 
     */
    public static final String MLET = "type=MLet";

    /**
     * The default domain.
     * <BR>
     * The value is <CODE>DefaultDomain</CODE>.
     * <p>
     *  默认域。
     * <BR>
     *  值为<CODE> DefaultDomain </CODE>。
     * 
     */
    public static final String DOMAIN = "DefaultDomain";

    /**
     * The default port for the RMI connector.
     * <BR>
     * The value is <CODE>1099</CODE>.
     * <p>
     *  RMI连接器的默认端口。
     * <BR>
     *  值为<CODE> 1099 </CODE>。
     * 
     */
    public static final int RMI_CONNECTOR_PORT = 1099 ;

    /**
     * The default key properties for the RMI connector.
     * <BR>
     * The value is <CODE>name=RmiConnectorServer</CODE>.
     * <p>
     *  RMI连接器的默认键属性。
     * <BR>
     *  值为<CODE> name = RmiConnectorServer </CODE>。
     * 
     */
    public static final String RMI_CONNECTOR_SERVER = "name=RmiConnectorServer" ;

    /**
     * The default port for the SNMP adaptor.
     * <BR>
     * The value is <CODE>161</CODE>.
     * <p>
     *  SNMP适配器的默认端口。
     * <BR>
     *  值为<CODE> 161 </CODE>。
     * 
     */
    public static final int SNMP_ADAPTOR_PORT = 161 ;

    /**
     * The default key properties for the SNMP protocol adaptor.
     * <BR>
     * The value is <CODE>name=SnmpAdaptorServer</CODE>.
     * <p>
     *  SNMP协议适配器的默认键属性。
     * <BR>
     *  值为<CODE> name = SnmpAdaptorServer </CODE>。
     * 
     */
    public static final String SNMP_ADAPTOR_SERVER = "name=SnmpAdaptorServer" ;

    /**
     * The default port for the HTTP connector.
     * <BR>
     * The value is <CODE>8081</CODE>.
     * <p>
     *  HTTP连接器的默认端口。
     * <BR>
     *  值为<CODE> 8081 </CODE>。
     * 
     */
    public static final int HTTP_CONNECTOR_PORT = 8081 ;

    /**
     * The default key properties for the HTTP connector.
     * <BR>
     * The value is <CODE>name=HttpConnectorServer</CODE>.
     * <p>
     *  HTTP连接器的默认键属性。
     * <BR>
     *  值为<CODE> name = HttpConnectorServer </CODE>。
     * 
     */
    public static final String HTTP_CONNECTOR_SERVER = "name=HttpConnectorServer" ;

    /**
     * The default port for the HTTPS connector.
     * <BR>
     * The value is <CODE>8084</CODE>.
     * <p>
     *  HTTPS连接器的默认端口。
     * <BR>
     *  值为<CODE> 8084 </CODE>。
     * 
     */
    public static final int HTTPS_CONNECTOR_PORT = 8084 ;

    /**
     * The default key properties for the HTTPS connector.
     * <BR>
     * The value is <CODE>name=HttpsConnectorServer</CODE>.
     * <p>
     *  HTTPS连接器的默认键属性。
     * <BR>
     *  值为<CODE> name = HttpsConnectorServer </CODE>。
     * 
     */
    public static final String HTTPS_CONNECTOR_SERVER = "name=HttpsConnectorServer" ;

    /**
     * The default port for the HTML adaptor.
     * <BR>
     * The value is <CODE>8082</CODE>.
     * <p>
     *  HTML适配器的默认端口。
     * <BR>
     *  值为<CODE> 8082 </CODE>。
     * 
     */
    public static final int HTML_ADAPTOR_PORT = 8082 ;

    /**
     * The default key properties for the HTML protocol adaptor.
     * <BR>
     * The value is <CODE>name=HtmlAdaptorServer</CODE>.
     * <p>
     *  HTML协议适配器的默认键属性。
     * <BR>
     * 值为<CODE> name = HtmlAdaptorServer </CODE>。
     * 
     */
    public static final String HTML_ADAPTOR_SERVER = "name=HtmlAdaptorServer" ;

    /**
     * The name of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Java Management Extensions</CODE>.
     * <p>
     *  此产品实现的JMX规范的名称。
     * <BR>
     *  值为<CODE> Java管理扩展</CODE>。
     * 
     */
    public static final String JMX_SPEC_NAME = "Java Management Extensions";

    /**
     * The version of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>1.0 Final Release</CODE>.
     * <p>
     *  由本产品实现的JMX规范的版本。
     * <BR>
     *  值为<CODE> 1.0 Final Release </CODE>。
     * 
     */
    public static final String JMX_SPEC_VERSION = "1.2 Maintenance Release";

    /**
     * The vendor of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     * <p>
     *  本产品实现的JMX规范的供应商。
     * <BR>
     *  值为<CODE> Oracle Corporation </CODE>。
     * 
     */
    public static final String JMX_SPEC_VENDOR = "Oracle Corporation";

    /**
     * The name of the vendor of this product implementing the  JMX specification.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     * <p>
     *  实现JMX规范的此产品的供应商的名称。
     * <BR>
     *  值为<CODE> Oracle Corporation </CODE>。
     * 
     */
    public static final String JMX_IMPL_VENDOR = "Oracle Corporation";

    /**
      * The build number of the current product version, of the form <CODE>rXX</CODE>.
      * <p>
      *  当前产品版本的内部版本号,格式为<CODE> rXX </CODE>。
      * 
      */
    public static final String BUILD_NUMBER = "r01";

    /**
     * The version of this product implementing the  JMX specification.
     * <BR>
     * The value is <CODE>5.1_rXX</CODE>, where <CODE>rXX</CODE> is the <CODE>BUILD_NUMBER</CODE> .
     * <p>
     *  实现JMX规范的此产品的版本。
     * <BR>
     */
    public static final String JMX_IMPL_VERSION = "5.1_" + BUILD_NUMBER;

}
