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

package javax.naming;

/**
  * This is the superclass of all exceptions thrown by
  * operations in the Context and DirContext interfaces.
  * The nature of the failure is described by the name of the subclass.
  * This exception captures the information pinpointing where the operation
  * failed, such as where resolution last proceeded to.
  * <ul>
  * <li> Resolved Name. Portion of name that has been resolved.
  * <li> Resolved Object. Object to which resolution of name proceeded.
  * <li> Remaining Name. Portion of name that has not been resolved.
  * <li> Explanation. Detail explaining why name resolution failed.
  * <li> Root Exception. The exception that caused this naming exception
  *                     to be thrown.
  *</ul>
  * null is an acceptable value for any of these fields. When null,
  * it means that no such information has been recorded for that field.
  *<p>
  * A NamingException instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single NamingException instance should lock the object.
  *<p>
  * This exception has been retrofitted to conform to
  * the general purpose exception-chaining mechanism.  The
  * <i>root exception</i> (or <i>root cause</i>) is the same object as the
  * <i>cause</i> returned by the {@link Throwable#getCause()} method.
  *
  * <p>
  *  这是由Context和DirContext接口中的操作抛出的所有异常的超类。故障的性质由子类的名称描述。此异常捕获定位操作失败的位置的信息,例如上次进行解析的位置。
  * <ul>
  *  <li>已解析的名称。已解析的名称部分。 <li>已解决的对象。名称解析的对象继续进行的对象。 <li>剩余名称。尚未解决的名称部分。 <li>说明。详细解释名称解析失败的原因。 <li>根异常。
  * 引发此命名异常的异常被抛出。
  * /ul>
  *  null是这些字段中的任何一个的可接受值。当为null时,表示没有为该字段记录此类信息。
  * p>
  *  NamingException实例不与并发多线程访问同步。尝试访问和修改单个NamingException实例的多个线程应锁定该对象。
  * p>
  *  该异常已经被改进以符合通用目的异常链接机制。 <i>根异常</i>(或<i>根原因</i>)是与{@link Throwable#getCause()}方法返回的<i>原因</i>相同的对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */


public class NamingException extends Exception {
    /**
     * Contains the part of the name that has been successfully resolved.
     * It is a composite name and can be null.
     * This field is initialized by the constructors.
     * You should access and manipulate this field
     * through its get and set methods.
     * <p>
     * 包含已成功解析的名称部分。它是一个复合名称,可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
     * 
     * 
     * @serial
     * @see #getResolvedName
     * @see #setResolvedName
     */
    protected Name resolvedName;
    /**
      * Contains the object to which resolution of the part of the name was
      * successful. Can be null.
      * This field is initialized by the constructors.
      * You should access and manipulate this field
      * through its get and set methods.
      * <p>
      *  包含名称的部分的解析成功的对象。可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
      * 
      * 
      * @serial
      * @see #getResolvedObj
      * @see #setResolvedObj
      */
    protected Object resolvedObj;
    /**
     * Contains the remaining name that has not been resolved yet.
     * It is a composite name and can be null.
     * This field is initialized by the constructors.
     * You should access and manipulate this field
     * through its get, set, "append" methods.
     * <p>
     *  包含尚未解析的剩余名称。它是一个复合名称,可以为null。此字段由构造函数初始化。您应该通过其get,set,"append"方法访问和操作此字段。
     * 
     * 
     * @serial
     * @see #getRemainingName
     * @see #setRemainingName
     * @see #appendRemainingName
     * @see #appendRemainingComponent
     */
    protected Name remainingName;

    /**
     * Contains the original exception that caused this NamingException to
     * be thrown. This field is set if there is additional
     * information that could be obtained from the original
     * exception, or if the original exception could not be
     * mapped to a subclass of NamingException.
     * Can be null.
     *<p>
     * This field predates the general-purpose exception chaining facility.
     * The {@link #initCause(Throwable)} and {@link #getCause()} methods
     * are now the preferred means of accessing this information.
     *
     * <p>
     *  包含导致抛出此NamingException的原始异常。如果有可以从原始异常获取的附加信息,或者如果原始异常不能映射到NamingException的子类,则设置此字段。可以为null。
     * p>
     *  此字段早于通用的异常链接设施。 {@link #initCause(Throwable)}和{@link #getCause()}方法现在是访问此信息的首选方法。
     * 
     * 
     * @serial
     * @see #getRootCause
     * @see #setRootCause(Throwable)
     * @see #initCause(Throwable)
     * @see #getCause
     */
    protected Throwable rootException = null;

    /**
     * Constructs a new NamingException with an explanation.
     * All unspecified fields are set to null.
     *
     * <p>
     *  构造一个新的NamingException和一个解释。所有未指定的字段都设置为null。
     * 
     * 
     * @param   explanation     A possibly null string containing
     *                          additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public NamingException(String explanation) {
        super(explanation);
        resolvedName = remainingName = null;
        resolvedObj = null;
    }

    /**
      * Constructs a new NamingException.
      * All fields are set to null.
      * <p>
      *  构造一个新的NamingException。所有字段都设置为null。
      * 
      */
    public NamingException() {
        super();
        resolvedName = remainingName = null;
        resolvedObj = null;
    }

    /**
     * Retrieves the leading portion of the name that was resolved
     * successfully.
     *
     * <p>
     *  检索已成功解析的名称的前导部分。
     * 
     * 
     * @return The part of the name that was resolved successfully.
     *          It is a composite name. It can be null, which means
     *          the resolved name field has not been set.
     * @see #getResolvedObj
     * @see #setResolvedName
     */
    public Name getResolvedName() {
        return resolvedName;
    }

    /**
     * Retrieves the remaining unresolved portion of the name.
     * <p>
     *  检索名称的剩余未解析部分。
     * 
     * 
     * @return The part of the name that has not been resolved.
     *          It is a composite name. It can be null, which means
     *          the remaining name field has not been set.
     * @see #setRemainingName
     * @see #appendRemainingName
     * @see #appendRemainingComponent
     */
    public Name getRemainingName() {
        return remainingName;
    }

    /**
     * Retrieves the object to which resolution was successful.
     * This is the object to which the resolved name is bound.
     *
     * <p>
     * 检索解析成功的对象。这是已解析的名称所绑定的对象。
     * 
     * 
     * @return The possibly null object that was resolved so far.
     *  null means that the resolved object field has not been set.
     * @see #getResolvedName
     * @see #setResolvedObj
     */
    public Object getResolvedObj() {
        return resolvedObj;
    }

    /**
      * Retrieves the explanation associated with this exception.
      *
      * <p>
      *  检索与此异常相关的说明。
      * 
      * 
      * @return The possibly null detail string explaining more
      *         about this exception. If null, it means there is no
      *         detail message for this exception.
      *
      * @see java.lang.Throwable#getMessage
      */
    public String getExplanation() {
        return getMessage();
    }

    /**
     * Sets the resolved name field of this exception.
     *<p>
     * <tt>name</tt> is a composite name. If the intent is to set
     * this field using a compound name or string, you must
     * "stringify" the compound name, and create a composite
     * name with a single component using the string. You can then
     * invoke this method using the resulting composite name.
     *<p>
     * A copy of <code>name</code> is made and stored.
     * Subsequent changes to <code>name</code> does not
     * affect the copy in this NamingException and vice versa.
     *
     * <p>
     *  设置此异常的已解析的名称字段。
     * p>
     *  <tt> name </tt>是复合名称。如果意图是使用化合物名称或字符串设置此字段,则必须"stringify"化合物名称,并使用该字符串使用单个组件创建复合名称。
     * 然后,可以使用生成的复合名称调用此方法。
     * p>
     *  制作并存储<code> name </code>的副本。对<code> name </code>的后续更改不会影响此NamingException中的副本,反之亦然。
     * 
     * 
     * @param name The possibly null name to set resolved name to.
     *          If null, it sets the resolved name field to null.
     * @see #getResolvedName
     */
    public void setResolvedName(Name name) {
        if (name != null)
            resolvedName = (Name)(name.clone());
        else
            resolvedName = null;
    }

    /**
     * Sets the remaining name field of this exception.
     *<p>
     * <tt>name</tt> is a composite name. If the intent is to set
     * this field using a compound name or string, you must
     * "stringify" the compound name, and create a composite
     * name with a single component using the string. You can then
     * invoke this method using the resulting composite name.
     *<p>
     * A copy of <code>name</code> is made and stored.
     * Subsequent changes to <code>name</code> does not
     * affect the copy in this NamingException and vice versa.
     * <p>
     *  设置此异常的其余名称字段。
     * p>
     *  <tt> name </tt>是复合名称。如果意图是使用化合物名称或字符串设置此字段,则必须"stringify"化合物名称,并使用该字符串使用单个组件创建复合名称。
     * 然后,可以使用生成的复合名称调用此方法。
     * p>
     *  制作并存储<code> name </code>的副本。对<code> name </code>的后续更改不会影响此NamingException中的副本,反之亦然。
     * 
     * 
     * @param name The possibly null name to set remaining name to.
     *          If null, it sets the remaining name field to null.
     * @see #getRemainingName
     * @see #appendRemainingName
     * @see #appendRemainingComponent
     */
    public void setRemainingName(Name name) {
        if (name != null)
            remainingName = (Name)(name.clone());
        else
            remainingName = null;
    }

    /**
     * Sets the resolved object field of this exception.
     * <p>
     *  设置此异常的已解析对象字段。
     * 
     * 
     * @param obj The possibly null object to set resolved object to.
     *            If null, the resolved object field is set to null.
     * @see #getResolvedObj
     */
    public void setResolvedObj(Object obj) {
        resolvedObj = obj;
    }

    /**
      * Add name as the last component in remaining name.
      * <p>
      *  在剩余名称中添加名称作为最后一个组件。
      * 
      * 
      * @param name The component to add.
      *         If name is null, this method does not do anything.
      * @see #setRemainingName
      * @see #getRemainingName
      * @see #appendRemainingName
      */
    public void appendRemainingComponent(String name) {
        if (name != null) {
            try {
                if (remainingName == null) {
                    remainingName = new CompositeName();
                }
                remainingName.add(name);
            } catch (NamingException e) {
                throw new IllegalArgumentException(e.toString());
            }
        }
    }

    /**
      * Add components from 'name' as the last components in
      * remaining name.
      *<p>
      * <tt>name</tt> is a composite name. If the intent is to append
      * a compound name, you should "stringify" the compound name
      * then invoke the overloaded form that accepts a String parameter.
      *<p>
      * Subsequent changes to <code>name</code> does not
      * affect the remaining name field in this NamingException and vice versa.
      * <p>
      *  从"name"中添加组件作为其余名称中的最后一个组件。
      * p>
      * <tt> name </tt>是复合名称。如果意图是追加化合物名称,则应该"化合物"化合物名称,然后调用接受String参数的重载形式。
      * p>
      *  对<code> name </code>的后续更改不会影响此NamingException中的其余名称字段,反之亦然。
      * 
      * 
      * @param name The possibly null name containing ordered components to add.
      *                 If name is null, this method does not do anything.
      * @see #setRemainingName
      * @see #getRemainingName
      * @see #appendRemainingComponent
      */
    public void appendRemainingName(Name name) {
        if (name == null) {
            return;
        }
        if (remainingName != null) {
            try {
                remainingName.addAll(name);
            } catch (NamingException e) {
                throw new IllegalArgumentException(e.toString());
            }
        } else {
            remainingName = (Name)(name.clone());
        }
    }

    /**
      * Retrieves the root cause of this NamingException, if any.
      * The root cause of a naming exception is used when the service provider
      * wants to indicate to the caller a non-naming related exception
      * but at the same time wants to use the NamingException structure
      * to indicate how far the naming operation proceeded.
      *<p>
      * This method predates the general-purpose exception chaining facility.
      * The {@link #getCause()} method is now the preferred means of obtaining
      * this information.
      *
      * <p>
      *  检索此NamingException的根本原因(如果有)。
      * 当服务提供者想向调用者指示一个非命名相关的异常但同时想要使用NamingException结构来指示命名操作进行多远时,使用命名异常的根本原因。
      * p>
      *  此方法早于通用的异常链接设施。 {@link #getCause()}方法现在是获取此信息的首选方法。
      * 
      * 
      * @return The possibly null exception that caused this naming
      *    exception. If null, it means no root cause has been
      *    set for this naming exception.
      * @see #setRootCause
      * @see #rootException
      * @see #getCause
      */
    public Throwable getRootCause() {
        return rootException;
    }

    /**
      * Records the root cause of this NamingException.
      * If <tt>e</tt> is <tt>this</tt>, this method does not do anything.
      *<p>
      * This method predates the general-purpose exception chaining facility.
      * The {@link #initCause(Throwable)} method is now the preferred means
      * of recording this information.
      *
      * <p>
      *  记录此NamingException的根本原因。如果<tt> e </tt>是<tt>此</tt>,此方法不执行任何操作。
      * p>
      *  此方法早于通用的异常链接设施。 {@link #initCause(Throwable)}方法现在是记录此信息的首选方法。
      * 
      * 
      * @param e The possibly null exception that caused the naming
      *          operation to fail. If null, it means this naming
      *          exception has no root cause.
      * @see #getRootCause
      * @see #rootException
      * @see #initCause
      */
    public void setRootCause(Throwable e) {
        if (e != this) {
            rootException = e;
        }
    }

    /**
      * Returns the cause of this exception.  The cause is the
      * throwable that caused this naming exception to be thrown.
      * Returns <code>null</code> if the cause is nonexistent or
      * unknown.
      *
      * <p>
      *  返回此异常的原因。原因是throwable导致抛出这个命名异常。如果原因不存在或未知,则返回<code> null </code>。
      * 
      * 
      * @return  the cause of this exception, or <code>null</code> if the
      *          cause is nonexistent or unknown.
      * @see #initCause(Throwable)
      * @since 1.4
      */
    public Throwable getCause() {
        return getRootCause();
    }

    /**
      * Initializes the cause of this exception to the specified value.
      * The cause is the throwable that caused this naming exception to be
      * thrown.
      *<p>
      * This method may be called at most once.
      *
      * <p>
      *  将此异常的原因初始化为指定的值。原因是throwable导致抛出这个命名异常。
      * p>
      *  此方法最多可调用一次。
      * 
      * 
      * @param  cause   the cause, which is saved for later retrieval by
      *         the {@link #getCause()} method.  A <tt>null</tt> value
      *         indicates that the cause is nonexistent or unknown.
      * @return a reference to this <code>NamingException</code> instance.
      * @throws IllegalArgumentException if <code>cause</code> is this
      *         exception.  (A throwable cannot be its own cause.)
      * @throws IllegalStateException if this method has already
      *         been called on this exception.
      * @see #getCause
      * @since 1.4
      */
    public Throwable initCause(Throwable cause) {
        super.initCause(cause);
        setRootCause(cause);
        return this;
    }

    /**
     * Generates the string representation of this exception.
     * The string representation consists of this exception's class name,
     * its detailed message, and if it has a root cause, the string
     * representation of the root cause exception, followed by
     * the remaining name (if it is not null).
     * This string is used for debugging and not meant to be interpreted
     * programmatically.
     *
     * <p>
     * 生成此异常的字符串表示形式。字符串表示包括此异常的类名称,其详细消息,如果它有根本原因,根本原因异常的字符串表示,后面是剩余名称(如果它不为null)。此字符串用于调试,不能以编程方式解释。
     * 
     * 
     * @return The non-null string containing the string representation
     * of this exception.
     */
    public String toString() {
        String answer = super.toString();

        if (rootException != null) {
            answer += " [Root exception is " + rootException + "]";
        }
        if (remainingName != null) {
            answer += "; remaining name '" + remainingName + "'";
        }
        return answer;
    }

    /**
      * Generates the string representation in more detail.
      * This string representation consists of the information returned
      * by the toString() that takes no parameters, plus the string
      * representation of the resolved object (if it is not null).
      * This string is used for debugging and not meant to be interpreted
      * programmatically.
      *
      * <p>
      *  生成更详细的字符串表示。此字符串表示形式由toString()返回的不带参数的信息,以及解析对象的字符串表示形式(如果它不为空)组成。此字符串用于调试,不能以编程方式解释。
      * 
      * 
      * @param detail If true, include details about the resolved object
      *                 in addition to the other information.
      * @return The non-null string containing the string representation.
      */
    public String toString(boolean detail) {
        if (!detail || resolvedObj == null) {
            return toString();
        } else {
            return (toString() + "; resolved object " + resolvedObj);
        }
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -1299181962103167177L;
};
