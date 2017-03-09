/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.annotation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The Resource annotation marks a resource that is needed
 * by the application.  This annotation may be applied to an
 * application component class, or to fields or methods of the
 * component class.  When the annotation is applied to a
 * field or method, the container will inject an instance
 * of the requested resource into the application component
 * when the component is initialized.  If the annotation is
 * applied to the component class, the annotation declares a
 * resource that the application will look up at runtime. <p>
 *
 * Even though this annotation is not marked Inherited, deployment
 * tools are required to examine all superclasses of any component
 * class to discover all uses of this annotation in all superclasses.
 * All such annotation instances specify resources that are needed
 * by the application component.  Note that this annotation may
 * appear on private fields and methods of superclasses; the container
 * is required to perform injection in these cases as well.
 *
 * <p>
 *  资源注释标记应用程序所需的资源。此注释可以应用于应用组件类,或应用于组件类的字段或方法。当注释应用于字段或方法时,容器将在组件初始化时将所请求资源的实例注入到应用程序组件中。
 * 如果注释应用于组件类,则注释声明应用程序将在运行时查找的资源。 <p>。
 * 
 *  即使此注释未标记为继承,部署工具也需要检查任何组件类的所有超类,以发现所有超类中此注释的所有使用。所有这样的注释实例指定应用组件所需的资源。
 * 注意,这个注释可能出现在私有字段和超类的方法上;在这些情况下也要求容器进行注射。
 * 
 * 
 * @since Common Annotations 1.0
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface Resource {
    /**
     * The JNDI name of the resource.  For field annotations,
     * the default is the field name.  For method annotations,
     * the default is the JavaBeans property name corresponding
     * to the method.  For class annotations, there is no default
     * and this must be specified.
     * <p>
     *  资源的JNDI名称。对于字段注释,默认值为字段名称。对于方法注释,默认值是与方法对应的JavaBeans属性名称。对于类注解,没有默认值,必须指定。
     * 
     */
    String name() default "";

    /**
     * The name of the resource that the reference points to. It can
     * link to any compatible resource using the global JNDI names.
     *
     * <p>
     *  引用指向的资源的名称。它可以使用全局JNDI名称链接到任何兼容的资源。
     * 
     * 
     * @since Common Annotations 1.1
     */

    String lookup() default "";

    /**
     * The Java type of the resource.  For field annotations,
     * the default is the type of the field.  For method annotations,
     * the default is the type of the JavaBeans property.
     * For class annotations, there is no default and this must be
     * specified.
     * <p>
     * 资源的Java类型。对于字段注释,默认值为字段的类型。对于方法注释,缺省值是JavaBeans属性的类型。对于类注解,没有默认值,必须指定。
     * 
     */
    Class<?> type() default java.lang.Object.class;

    /**
     * The two possible authentication types for a resource.
     * <p>
     *  资源的两种可能的认证类型。
     * 
     */
    enum AuthenticationType {
            CONTAINER,
            APPLICATION
    }

    /**
     * The authentication type to use for this resource.
     * This may be specified for resources representing a
     * connection factory of any supported type, and must
     * not be specified for resources of other types.
     * <p>
     *  用于此资源的身份验证类型。这可以被指定用于表示任何支持类型的连接工厂的资源,并且不能被指定用于其他类型的资源。
     * 
     */
    AuthenticationType authenticationType() default AuthenticationType.CONTAINER;

    /**
     * Indicates whether this resource can be shared between
     * this component and other components.
     * This may be specified for resources representing a
     * connection factory of any supported type, and must
     * not be specified for resources of other types.
     * <p>
     *  指示此资源是否可以在此组件和其他组件之间共享。这可以被指定用于表示任何支持类型的连接工厂的资源,并且不能被指定用于其他类型的资源。
     * 
     */
    boolean shareable() default true;

    /**
     * A product specific name that this resource should be mapped to.
     * The name of this resource, as defined by the <code>name</code>
     * element or defaulted, is a name that is local to the application
     * component using the resource.  (It's a name in the JNDI
     * <code>java:comp/env</code> namespace.)  Many application servers
     * provide a way to map these local names to names of resources
     * known to the application server.  This mapped name is often a
     * <i>global</i> JNDI name, but may be a name of any form. <p>
     *
     * Application servers are not required to support any particular
     * form or type of mapped name, nor the ability to use mapped names.
     * The mapped name is product-dependent and often installation-dependent.
     * No use of a mapped name is portable.
     * <p>
     *  此资源应映射到的产品特定名称。由<code> name </code>元素或defaulted定义的此资源的名称是使用该资源的应用程序组件的本地名称。
     *  (它是JNDI <code> java：comp / env </code>命名空间中的一个名称。)许多应用程序服务器提供了一种将这些本地名称映射到应用程序服务器已知的资源名称的方法。
     * 此映射名称通常是<i>全局</i> JNDI名称,但可以是任何形式的名称。 <p>。
     * 
     *  应用程序服务器不需要支持任何特定形式或类型的映射名称,也不能使用映射名称。映射的名称是产品相关的,通常是安装相关的。不使用映射名称是可移植的。
     */
    String mappedName() default "";

    /**
     * Description of this resource.  The description is expected
     * to be in the default language of the system on which the
     * application is deployed.  The description can be presented
     * to the Deployer to help in choosing the correct resource.
     * <p>
     * 
     */
    String description() default "";
}
