/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.spi;

import java.util.Hashtable;
import javax.naming.NamingException;

 /**
  * This interface represents a builder that creates object factories.
  *<p>
  * The JNDI framework allows for object implementations to
  * be loaded in dynamically via <em>object factories</em>.
  * For example, when looking up a printer bound in the name space,
  * if the print service binds printer names to References, the printer
  * Reference could be used to create a printer object, so that
  * the caller of lookup can directly operate on the printer object
  * after the lookup.  An ObjectFactory is responsible for creating
  * objects of a specific type.  JNDI uses a default policy for using
  * and loading object factories.  You can override this default policy
  * by calling <tt>NamingManager.setObjectFactoryBuilder()</tt> with an ObjectFactoryBuilder,
  * which contains the program-defined way of creating/loading
  * object factories.
  * Any <tt>ObjectFactoryBuilder</tt> implementation must implement this
  * interface that for creating object factories.
  *
  * <p>
  *  此接口表示创建对象工厂的构建器。
  * p>
  *  JNDI框架允许对象实现通过<em>对象工厂</em>动态加载。
  * 例如,当查找命名空间中绑定的打印机时,如果打印服务将打印机名称绑定到引用,则打印机引用可以用于创建打印机对象,以便查找的调用者可以直接对打印机对象进行操作查找。
  *  ObjectFactory负责创建特定类型的对象。 JNDI使用默认策略来使用和加载对象工厂。
  * 您可以通过使用ObjectFactoryBuilder调用<tt> NamingManager.setObjectFactoryBuilder()</tt>来覆盖此默认策略,ObjectFactoryB
  * uilder包含程序定义的创建/加载对象工厂的方式。
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see ObjectFactory
  * @see NamingManager#getObjectInstance
  * @see NamingManager#setObjectFactoryBuilder
  * @since 1.3
  */
public interface ObjectFactoryBuilder {
    /**
      * Creates a new object factory using the environment supplied.
      *<p>
      * The environment parameter is owned by the caller.
      * The implementation will not modify the object or keep a reference
      * to it, although it may keep a reference to a clone or copy.
      *
      * <p>
      *  ObjectFactory负责创建特定类型的对象。 JNDI使用默认策略来使用和加载对象工厂。任何<tt> ObjectFactoryBuilder </tt>实现必须实现此接口,用于创建对象工厂。
      * 
      * 
      * @param obj The possibly null object for which to create a factory.
      * @param environment Environment to use when creating the factory.
      *                 Can be null.
      * @return A non-null new instance of an ObjectFactory.
      * @exception NamingException If an object factory cannot be created.
      *
      */
    public ObjectFactory createObjectFactory(Object obj,
                                             Hashtable<?,?> environment)
        throws NamingException;
}
