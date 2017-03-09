/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

// java import
import java.io.Serializable;


/**
 * <p>Represents relational constraints similar to database query "where
 * clauses". Instances of QueryExp are returned by the static methods of the
 * {@link Query} class.</p>
 *
 * <p>It is possible, but not
 * recommended, to create custom queries by implementing this
 * interface.  In that case, it is better to extend the {@link
 * QueryEval} class than to implement the interface directly, so that
 * the {@link #setMBeanServer} method works correctly.
 *
 * <p>
 *  <p>表示与数据库查询"where clause"类似的关系约束。 QueryExp的实例由{@link Query}类的静态方法返回。</p>
 * 
 *  <p>可以(但不推荐)通过实现此界面来创建自定义查询。在这种情况下,最好扩展{@link QueryEval}类,而不是直接实现接口,以使{@link #setMBeanServer}方法正常工作。
 * 
 * 
 * @see MBeanServer#queryNames MBeanServer.queryNames
 * @since 1.5
 */
public interface QueryExp extends Serializable {


     /**
      * Applies the QueryExp on an MBean.
      *
      * <p>
      *  将QueryExp应用于MBean。
      * 
      * 
      * @param name The name of the MBean on which the QueryExp will be applied.
      *
      * @return  True if the query was successfully applied to the MBean, false otherwise
      *
      * @exception BadStringOperationException
      * @exception BadBinaryOpValueExpException
      * @exception BadAttributeValueExpException
      * @exception InvalidApplicationException
      */
     public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
         BadAttributeValueExpException, InvalidApplicationException ;

     /**
      * Sets the MBean server on which the query is to be performed.
      *
      * <p>
      *  设置要对其执行查询的MBean服务器。
      * 
      * @param s The MBean server on which the query is to be performed.
      */
     public void setMBeanServer(MBeanServer s) ;

 }
