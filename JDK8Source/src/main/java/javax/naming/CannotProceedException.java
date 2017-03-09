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

package javax.naming;

import java.util.Hashtable;

/**
  * This exception is thrown to indicate that the operation reached
  * a point in the name where the operation cannot proceed any further.
  * When performing an operation on a composite name, a naming service
  * provider may reach a part of the name that does not belong to its
  * namespace.  At that point, it can construct a
  * CannotProceedException and then invoke methods provided by
  * javax.naming.spi.NamingManager (such as getContinuationContext())
  * to locate another provider to continue the operation.  If this is
  * not possible, this exception is raised to the caller of the
  * context operation.
  *<p>
  * If the program wants to handle this exception in particular, it
  * should catch CannotProceedException explicitly before attempting to
  * catch NamingException.
  *<p>
  * A CannotProceedException instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * CannotProceedException should lock the object.
  *
  * <p>
  *  抛出此异常以指示操作到达操作无法继续进行的名称中的某一点。当对复合名称执行操作时,命名服务提供程序可能会到达不属于其命名空间的名称的一部分。
  * 在这一点上,它可以构造一个CannotProceedException,然后调用javax.naming.spi.NamingManager提供的方法(如getContinuationContext()
  * )来定位另一个提供程序以继续操作。
  *  抛出此异常以指示操作到达操作无法继续进行的名称中的某一点。当对复合名称执行操作时,命名服务提供程序可能会到达不属于其命名空间的名称的一部分。如果这是不可能的,这个异常被提交给上下文操作的调用者。
  * p>
  *  如果程序想特别处理这个异常,它应该在捕获NamingException之前显式捕获CannotProceedException。
  * p>
  *  CannotProceedException实例不与并发多线程访问同步。多个线程尝试访问和修改CannotProceedException应该锁定对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

/*
  * The serialized form of a CannotProceedException object consists of
  * the serialized fields of its NamingException superclass, the remaining new
  * name (a Name object), the environment (a Hashtable), the altName field
  * (a Name object), and the serialized form of the altNameCtx field.
  * <p>
  *  CannotProceedException对象的序列化形式包括其NamingException超类的序列化字段,剩余的新名称(Name对象),环境(Hashtable),altName字段(Name
  * 对象)和altNameCtx的序列化形式领域。
  * 
  */


public class CannotProceedException extends NamingException {
    /**
     * Contains the remaining unresolved part of the second
     * "name" argument to Context.rename().
     * This information necessary for
     * continuing the Context.rename() operation.
     * <p>
     * This field is initialized to null.
     * It should not be manipulated directly:  it should
     * be accessed and updated using getRemainingName() and setRemainingName().
     * <p>
     * 包含Context.rename()的第二个"名称"参数的剩余未解析部分。此信息是继续Context.rename()操作所必需的。
     * <p>
     *  此字段初始化为null。它不应该直接操作：它应该使用getRemainingName()和setRemainingName()访问和更新。
     * 
     * 
     * @serial
     *
     * @see #getRemainingNewName
     * @see #setRemainingNewName
     */
    protected Name remainingNewName = null;

    /**
     * Contains the environment
     * relevant for the Context or DirContext method that cannot proceed.
     * <p>
     * This field is initialized to null.
     * It should not be manipulated directly:  it should be accessed
     * and updated using getEnvironment() and setEnvironment().
     * <p>
     *  包含与无法继续的Context或DirContext方法相关的环境。
     * <p>
     *  此字段初始化为null。它不应该直接操作：应该使用getEnvironment()和setEnvironment()访问和更新它。
     * 
     * 
     * @serial
     *
     * @see #getEnvironment
     * @see #setEnvironment
     */
    protected Hashtable<?,?> environment = null;

    /**
     * Contains the name of the resolved object, relative
     * to the context <code>altNameCtx</code>.  It is a composite name.
     * If null, then no name is specified.
     * See the <code>javax.naming.spi.ObjectFactory.getObjectInstance</code>
     * method for details on how this is used.
     * <p>
     * This field is initialized to null.
     * It should not be manipulated directly:  it should
     * be accessed and updated using getAltName() and setAltName().
     * <p>
     *  包含已解析对象的名称,相对于上下文<code> altNameCtx </code>。它是一个复合名称。如果为null,则不指定名称。
     * 有关如何使用它的详细信息,请参阅<code> javax.naming.spi.ObjectFactory.getObjectInstance </code>方法。
     * <p>
     *  此字段初始化为null。它不应该直接操作：它应该使用getAltName()和setAltName()访问和更新。
     * 
     * 
     * @serial
     *
     * @see #getAltName
     * @see #setAltName
     * @see #altNameCtx
     * @see javax.naming.spi.ObjectFactory#getObjectInstance
     */
    protected Name altName = null;

    /**
     * Contains the context relative to which
     * <code>altName</code> is specified.  If null, then the default initial
     * context is implied.
     * See the <code>javax.naming.spi.ObjectFactory.getObjectInstance</code>
     * method for details on how this is used.
     * <p>
     * This field is initialized to null.
     * It should not be manipulated directly:  it should
     * be accessed and updated using getAltNameCtx() and setAltNameCtx().
     * <p>
     *  包含指定<code> altName </code>的上下文。如果为null,则隐含默认的初始上下文。
     * 有关如何使用它的详细信息,请参阅<code> javax.naming.spi.ObjectFactory.getObjectInstance </code>方法。
     * <p>
     *  此字段初始化为null。它不应该直接操作：应该使用getAltNameCtx()和setAltNameCtx()来访问和更新它。
     * 
     * 
     * @serial
     *
     * @see #getAltNameCtx
     * @see #setAltNameCtx
     * @see #altName
     * @see javax.naming.spi.ObjectFactory#getObjectInstance
     */
    protected Context altNameCtx = null;

    /**
     * Constructs a new instance of CannotProceedException using an
     * explanation. All unspecified fields default to null.
     *
     * <p>
     *  使用解释构造CannotProceedException的新实例。所有未指定的字段默认为null。
     * 
     * 
     * @param   explanation     A possibly null string containing additional
     *                          detail about this exception.
     *   If null, this exception has no detail message.
     * @see java.lang.Throwable#getMessage
     */
    public CannotProceedException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of CannotProceedException.
      * All fields default to null.
      * <p>
      * 构造一个新的CannotProceedException实例。所有字段默认为null。
      * 
      */
    public CannotProceedException() {
        super();
    }

    /**
     * Retrieves the environment that was in effect when this exception
     * was created.
     * <p>
     *  检索创建此异常时生效的环境。
     * 
     * 
     * @return Possibly null environment property set.
     *          null means no environment was recorded for this exception.
     * @see #setEnvironment
     */
    public Hashtable<?,?> getEnvironment() {
        return environment;
    }

    /**
     * Sets the environment that will be returned when getEnvironment()
     * is called.
     * <p>
     *  设置在调用getEnvironment()时将返回的环境。
     * 
     * 
     * @param environment A possibly null environment property set.
     *          null means no environment is being recorded for
     *          this exception.
     * @see #getEnvironment
     */
    public void setEnvironment(Hashtable<?,?> environment) {
        this.environment = environment; // %%% clone it??
    }

    /**
     * Retrieves the "remaining new name" field of this exception, which is
     * used when this exception is thrown during a rename() operation.
     *
     * <p>
     *  检索此异常的"剩余新名称"字段,此字段在重命名()操作期间抛出此异常时使用。
     * 
     * 
     * @return The possibly null part of the new name that has not been resolved.
     *          It is a composite name. It can be null, which means
     *          the remaining new name field has not been set.
     *
     * @see #setRemainingNewName
     */
    public Name getRemainingNewName() {
        return remainingNewName;
    }

    /**
     * Sets the "remaining new name" field of this exception.
     * This is the value returned by <code>getRemainingNewName()</code>.
     *<p>
     * <tt>newName</tt> is a composite name. If the intent is to set
     * this field using a compound name or string, you must
     * "stringify" the compound name, and create a composite
     * name with a single component using the string. You can then
     * invoke this method using the resulting composite name.
     *<p>
     * A copy of <code>newName</code> is made and stored.
     * Subsequent changes to <code>name</code> does not
     * affect the copy in this NamingException and vice versa.
     *
     * <p>
     *  设置此异常的"剩余新名称"字段。这是<code> getRemainingNewName()</code>返回的值。
     * p>
     *  <tt> newName </tt>是复合名称。如果意图是使用化合物名称或字符串设置此字段,则必须"stringify"化合物名称,并使用该字符串使用单个组件创建复合名称。
     * 然后,可以使用生成的复合名称调用此方法。
     * p>
     *  制作并存储<code> newName </code>的副本。对<code> name </code>的后续更改不会影响此NamingException中的副本,反之亦然。
     * 
     * 
     * @param newName The possibly null name to set the "remaining new name" to.
     *          If null, it sets the remaining name field to null.
     *
     * @see #getRemainingNewName
     */
    public void setRemainingNewName(Name newName) {
        if (newName != null)
            this.remainingNewName = (Name)(newName.clone());
        else
            this.remainingNewName = null;
    }

    /**
     * Retrieves the <code>altName</code> field of this exception.
     * This is the name of the resolved object, relative to the context
     * <code>altNameCtx</code>. It will be used during a subsequent call to the
     * <code>javax.naming.spi.ObjectFactory.getObjectInstance</code> method.
     *
     * <p>
     *  检索此异常的<code> altName </code>字段。这是解析对象的名称,相对于上下文<code> altNameCtx </code>。
     * 它将在随后调用<code> javax.naming.spi.ObjectFactory.getObjectInstance </code>方法期间使用。
     * 
     * 
     * @return The name of the resolved object, relative to
     *          <code>altNameCtx</code>.
     *          It is a composite name.  If null, then no name is specified.
     *
     * @see #setAltName
     * @see #getAltNameCtx
     * @see javax.naming.spi.ObjectFactory#getObjectInstance
     */
    public Name getAltName() {
        return altName;
    }

    /**
     * Sets the <code>altName</code> field of this exception.
     *
     * <p>
     *  设置此异常的<code> altName </code>字段。
     * 
     * 
     * @param altName   The name of the resolved object, relative to
     *                  <code>altNameCtx</code>.
     *                  It is a composite name.
     *                  If null, then no name is specified.
     *
     * @see #getAltName
     * @see #setAltNameCtx
     */
    public void setAltName(Name altName) {
        this.altName = altName;
    }

    /**
     * Retrieves the <code>altNameCtx</code> field of this exception.
     * This is the context relative to which <code>altName</code> is named.
     * It will be used during a subsequent call to the
     * <code>javax.naming.spi.ObjectFactory.getObjectInstance</code> method.
     *
     * <p>
     * 检索此异常的<code> altNameCtx </code>字段。这是与<code> altName </code>命名的上下文相关。
     * 它将在后续调用<code> javax.naming.spi.ObjectFactory.getObjectInstance </code>方法期间使用。
     * 
     * 
     * @return  The context relative to which <code>altName</code> is named.
     *          If null, then the default initial context is implied.
     *
     * @see #setAltNameCtx
     * @see #getAltName
     * @see javax.naming.spi.ObjectFactory#getObjectInstance
     */
    public Context getAltNameCtx() {
        return altNameCtx;
    }

    /**
     * Sets the <code>altNameCtx</code> field of this exception.
     *
     * <p>
     *  设置此异常的<code> altNameCtx </code>字段。
     * 
     * 
     * @param altNameCtx
     *                  The context relative to which <code>altName</code>
     *                  is named.  If null, then the default initial context
     *                  is implied.
     *
     * @see #getAltNameCtx
     * @see #setAltName
     */
    public void setAltNameCtx(Context altNameCtx) {
        this.altNameCtx = altNameCtx;
    }


    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 1219724816191576813L;
}
