/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

import java.lang.ref.Reference;

/**
 * A BeanDescriptor provides global information about a "bean",
 * including its Java class, its displayName, etc.
 * <p>
 * This is one of the kinds of descriptor returned by a BeanInfo object,
 * which also returns descriptors for properties, method, and events.
 * <p>
 *  BeanDescriptor提供关于"bean"的全局信息,包括它的Java类,它的displayName等。
 * <p>
 *  这是BeanInfo对象返回的一种描述符,它也返回属性,方法和事件的描述符。
 * 
 */

public class BeanDescriptor extends FeatureDescriptor {

    private Reference<? extends Class<?>> beanClassRef;
    private Reference<? extends Class<?>> customizerClassRef;

    /**
     * Create a BeanDescriptor for a bean that doesn't have a customizer.
     *
     * <p>
     *  为没有自定义程序的bean创建BeanDescriptor。
     * 
     * 
     * @param beanClass  The Class object of the Java class that implements
     *          the bean.  For example sun.beans.OurButton.class.
     */
    public BeanDescriptor(Class<?> beanClass) {
        this(beanClass, null);
    }

    /**
     * Create a BeanDescriptor for a bean that has a customizer.
     *
     * <p>
     *  为具有自定义程序的bean创建BeanDescriptor。
     * 
     * 
     * @param beanClass  The Class object of the Java class that implements
     *          the bean.  For example sun.beans.OurButton.class.
     * @param customizerClass  The Class object of the Java class that implements
     *          the bean's Customizer.  For example sun.beans.OurButtonCustomizer.class.
     */
    public BeanDescriptor(Class<?> beanClass, Class<?> customizerClass) {
        this.beanClassRef = getWeakReference(beanClass);
        this.customizerClassRef = getWeakReference(customizerClass);

        String name = beanClass.getName();
        while (name.indexOf('.') >= 0) {
            name = name.substring(name.indexOf('.')+1);
        }
        setName(name);
    }

    /**
     * Gets the bean's Class object.
     *
     * <p>
     *  获取bean的Class对象。
     * 
     * 
     * @return The Class object for the bean.
     */
    public Class<?> getBeanClass() {
        return (this.beanClassRef != null)
                ? this.beanClassRef.get()
                : null;
    }

    /**
     * Gets the Class object for the bean's customizer.
     *
     * <p>
     *  获取Bean的定制器的Class对象。
     * 
     * 
     * @return The Class object for the bean's customizer.  This may
     * be null if the bean doesn't have a customizer.
     */
    public Class<?> getCustomizerClass() {
        return (this.customizerClassRef != null)
                ? this.customizerClassRef.get()
                : null;
    }

    /*
     * Package-private dup constructor
     * This must isolate the new object from any changes to the old object.
     * <p>
     *  Package-private dup constructor这必须将新对象与对旧对象的任何更改隔离开来。
     */
    BeanDescriptor(BeanDescriptor old) {
        super(old);
        beanClassRef = old.beanClassRef;
        customizerClassRef = old.customizerClassRef;
    }

    void appendTo(StringBuilder sb) {
        appendTo(sb, "beanClass", this.beanClassRef);
        appendTo(sb, "customizerClass", this.customizerClassRef);
    }
}
