/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.directory;

import javax.naming.NamingException;

/**
  * This exception is thrown when an attempt is
  * made to add, or remove, or modify an attribute, its identifier,
  * or its values that conflicts with the attribute's (schema) definition
  * or the attribute's state.
  * It is thrown in response to DirContext.modifyAttributes().
  * It contains a list of modifications that have not been performed, in the
  * order that they were supplied to modifyAttributes().
  * If the list is null, none of the modifications were performed successfully.
  *<p>
  * An AttributeModificationException instance is not synchronized
  * against concurrent multithreaded access. Multiple threads trying
  * to access and modify a single AttributeModification instance
  * should lock the object.
  *
  * <p>
  *  尝试添加,删除或修改属性,其标识符或与属性(模式)定义或属性状态冲突的值时,将抛出此异常。它响应DirContext.modifyAttributes()抛出。
  * 它包含尚未执行的修改列表,按修改提供给modifyAttributes()的顺序。如果列表为空,则没有成功执行任何修改。
  * p>
  *  AttributeModificationException实例不与并发多线程访问同步。尝试访问和修改单个AttributeModification实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirContext#modifyAttributes
  * @since 1.3
  */

/*
  *<p>
  * The serialized form of an AttributeModificationException object
  * consists of the serialized fields of its NamingException
  * superclass, followed by an array of ModificationItem objects.
  *
  * <p>
  * p>
  *  AttributeModificationException对象的序列化形式由其NamingException超类的序列化字段组成,后跟一个ModificationItem对象的数组。
  * 
*/


public class AttributeModificationException extends NamingException {
    /**
     * Contains the possibly null list of unexecuted modifications.
     * <p>
     *  包含可能为空的未执行修改列表。
     * 
     * 
     * @serial
     */
    private ModificationItem[] unexecs = null;

    /**
     * Constructs a new instance of AttributeModificationException using
     * an explanation. All other fields are set to null.
     *
     * <p>
     *  使用解释构造AttributeModificationException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * If null, this exception has no detail message.

     * @see java.lang.Throwable#getMessage
     */
    public AttributeModificationException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of AttributeModificationException.
      * All fields are set to null.
      * <p>
      *  构造AttributeModificationException的新实例。所有字段都设置为null。
      * 
      */
    public AttributeModificationException() {
        super();
    }

    /**
      * Sets the unexecuted modification list to be e.
      * Items in the list must appear in the same order in which they were
      * originally supplied in DirContext.modifyAttributes().
      * The first item in the list is the first one that was not executed.
      * If this list is null, none of the operations originally submitted
      * to modifyAttributes() were executed.

      * <p>
      * 将未执行的修改列表设置为e。列表中的项必须按照它们最初在DirContext.modifyAttributes()中提供的顺序显示。列表中的第一个项目是第一个未执行的项目。
      * 如果此列表为空,则不会执行最初提交到modifyAttributes()的操作。
      * 
      * 
      * @param e        The possibly null list of unexecuted modifications.
      * @see #getUnexecutedModifications
      */
    public void setUnexecutedModifications(ModificationItem[] e) {
        unexecs = e;
    }

    /**
      * Retrieves the unexecuted modification list.
      * Items in the list appear in the same order in which they were
      * originally supplied in DirContext.modifyAttributes().
      * The first item in the list is the first one that was not executed.
      * If this list is null, none of the operations originally submitted
      * to modifyAttributes() were executed.

      * <p>
      *  检索未执行的修改列表。列表中的项目按照它们最初在DirContext.modifyAttributes()中提供的顺序显示。列表中的第一个项目是第一个未执行的项目。
      * 如果此列表为空,则不会执行最初提交到modifyAttributes()的操作。
      * 
      * 
      * @return The possibly null unexecuted modification list.
      * @see #setUnexecutedModifications
      */
    public ModificationItem[] getUnexecutedModifications() {
        return unexecs;
    }

    /**
      * The string representation of this exception consists of
      * information about where the error occurred, and
      * the first unexecuted modification.
      * This string is meant for debugging and not mean to be interpreted
      * programmatically.
      * <p>
      *  此异常的字符串表示形式由有关错误发生位置和第一个未执行的修改的信息组成。这个字符串用于调试,而不是以编程方式解释。
      * 
      * 
      * @return The non-null string representation of this exception.
      */
    public String toString() {
        String orig = super.toString();
        if (unexecs != null) {
            orig += ("First unexecuted modification: " +
                     unexecs[0].toString());
        }
        return orig;
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 8060676069678710186L;
}
