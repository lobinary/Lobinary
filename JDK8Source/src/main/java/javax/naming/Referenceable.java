/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

/**
  * This interface is implemented by an object that can provide a
  * Reference to itself.
  *<p>
  * A Reference represents a way of recording address information about
  * objects which themselves are not directly bound to the naming system.
  * Such objects can implement the Referenceable interface as a way
  * for programs that use that object to determine what its Reference is.
  * For example, when binding a object, if an object implements the
  * Referenceable interface, getReference() can be invoked on the object to
  * get its Reference to use for binding.
  *
  * <p>
  *  此接口由可以提供对其自身的引用的对象实现。
  * p>
  *  引用表示记录关于对象本身不直接绑定到命名系统的地址信息的方式。这样的对象可以实现引用接口,作为使用该对象来确定其引用的程序的一种方式。
  * 例如,当绑定一个对象时,如果一个对象实现了Referenceable接口,那么可以在该对象上调用getReference(),以获取其引用用于绑定。
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @author R. Vasudevan
  *
  * @see Context#bind
  * @see javax.naming.spi.NamingManager#getObjectInstance
  * @see Reference
  * @since 1.3
  */
public interface Referenceable {
    /**
      * Retrieves the Reference of this object.
      *
      * <p>
      * 
      * 
      * @return The non-null Reference of this object.
      * @exception NamingException If a naming exception was encountered
      *         while retrieving the reference.
      */
    Reference getReference() throws NamingException;
}
