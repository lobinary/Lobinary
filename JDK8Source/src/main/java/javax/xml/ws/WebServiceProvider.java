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

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * Used to annotate a Provider implementation class.
 *
 * <p>
 *  用于注释Provider实现类。
 * 
 * 
 * @since JAX-WS 2.0
 * @see javax.xml.ws.Provider
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServiceProvider {
    /**
     * Location of the WSDL description for the service.
     * <p>
     *  服务的WSDL描述的位置。
     * 
     */
    String wsdlLocation() default "";

    /**
     * Service name.
     * <p>
     *  服务名称。
     * 
     */
    String serviceName() default "";

    /**
     * Target namespace for the service
     * <p>
     *  目标服务的命名空间
     * 
     */
    String targetNamespace() default "";

    /**
     * Port name.
     * <p>
     *  端口名称。
     */
    String portName() default "";
}
