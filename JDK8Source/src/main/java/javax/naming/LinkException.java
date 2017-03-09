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

/**
 * This exception is used to describe problems encounter while resolving links.
 * Addition information is added to the base NamingException for pinpointing
 * the problem with the link.
 *<p>
 * Analogous to how NamingException captures name resolution information,
 * LinkException captures "link"-name resolution information pinpointing
 * the problem encountered while resolving a link. All these fields may
 * be null.
 * <ul>
 * <li> Link Resolved Name. Portion of link name that has been resolved.
 * <li> Link Resolved Object. Object to which resolution of link name proceeded.
 * <li> Link Remaining Name. Portion of link name that has not been resolved.
 * <li> Link Explanation. Detail explaining why link resolution failed.
 *</ul>
 *
  *<p>
  * A LinkException instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single LinkException instance should lock the object.
  *
  * <p>
  *  此异常用于描述解析链接时遇到的问题。添加信息被添加到基本NamingException以精确定位链接的问题。
  * p>
  *  类似于NamingException捕获名称解析信息,LinkException捕获"链接" - 名称解析信息,指出解析链接时遇到的问题。所有这些字段可以为null。
  * <ul>
  *  <li>链接已解析的名称。已解析的链接名称的部分。 <li>链接已解析对象。继续进行链接名解析的对象。 <li>链接剩余名称。尚未解析的链接名称的部分。 <li>链接说明。
  * 详细解释链接解析失败的原因。
  * /ul>
  * 
  * p>
  *  LinkException实例未与并发多线程访问同步。尝试访问和修改单个LinkException实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Context#lookupLink
  * @see LinkRef
  * @since 1.3
  */


  /*<p>
  * The serialized form of a LinkException object consists of the
  * serialized fields of its NamingException superclass, the link resolved
  * name (a Name object), the link resolved object, link remaining name
  * (a Name object), and the link explanation String.
  * <p>
  *  LinkException对象的序列化形式由其NamingException超类,链接解析名(一个Name对象),链接解析对象,链接剩余名(一个Name对象)和链接解释字符串的序列化字段组成。
  * 
*/


public class LinkException extends NamingException {
    /**
     * Contains the part of the link that has been successfully resolved.
     * It is a composite name and can be null.
     * This field is initialized by the constructors.
     * You should access and manipulate this field
     * through its get and set methods.
     * <p>
     * 包含已成功解析的链接部分。它是一个复合名称,可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
     * 
     * 
     * @serial
     * @see #getLinkResolvedName
     * @see #setLinkResolvedName
     */
    protected Name linkResolvedName;

    /**
      * Contains the object to which resolution of the part of the link was successful.
      * Can be null. This field is initialized by the constructors.
      * You should access and manipulate this field
      * through its get and set methods.
      * <p>
      *  包含链接部分的解析成功的对象。可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
      * 
      * 
      * @serial
      * @see #getLinkResolvedObj
      * @see #setLinkResolvedObj
      */
    protected Object linkResolvedObj;

    /**
     * Contains the remaining link name that has not been resolved yet.
     * It is a composite name and can be null.
     * This field is initialized by the constructors.
     * You should access and manipulate this field
     * through its get and set methods.
     * <p>
     *  包含尚未解析的剩余链接名称。它是一个复合名称,可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
     * 
     * 
     * @serial
     * @see #getLinkRemainingName
     * @see #setLinkRemainingName
     */
    protected Name linkRemainingName;

    /**
     * Contains the exception of why resolution of the link failed.
     * Can be null. This field is initialized by the constructors.
     * You should access and manipulate this field
     * through its get and set methods.
     * <p>
     *  包含链接解析失败的例外。可以为null。此字段由构造函数初始化。您应该通过get和set方法访问和操作此字段。
     * 
     * 
     * @serial
     * @see #getLinkExplanation
     * @see #setLinkExplanation
     */
    protected String linkExplanation;

    /**
      * Constructs a new instance of LinkException with an explanation
      * All the other fields are initialized to null.
      * <p>
      *  构造一个带有解释的LinkException的新实例所有其他字段都初始化为null。
      * 
      * 
      * @param  explanation     A possibly null string containing additional
      *                         detail about this exception.
      * @see java.lang.Throwable#getMessage
      */
    public LinkException(String explanation) {
        super(explanation);
        linkResolvedName = null;
        linkResolvedObj = null;
        linkRemainingName = null;
        linkExplanation = null;
    }

    /**
      * Constructs a new instance of LinkException.
      * All the non-link-related and link-related fields are initialized to null.
      * <p>
      *  构造一个LinkException的新实例。所有非链接相关和链接相关字段均初始化为null。
      * 
      */
    public LinkException() {
        super();
        linkResolvedName = null;
        linkResolvedObj = null;
        linkRemainingName = null;
        linkExplanation = null;
    }

    /**
     * Retrieves the leading portion of the link name that was resolved
     * successfully.
     *
     * <p>
     *  检索已成功解析的链接名称的前导部分。
     * 
     * 
     * @return The part of the link name that was resolved successfully.
     *          It is a composite name. It can be null, which means
     *          the link resolved name field has not been set.
     * @see #getLinkResolvedObj
     * @see #setLinkResolvedName
     */
    public Name getLinkResolvedName() {
        return this.linkResolvedName;
    }

    /**
     * Retrieves the remaining unresolved portion of the link name.
     * <p>
     *  检索链接名称的剩余未解析部分。
     * 
     * 
     * @return The part of the link name that has not been resolved.
     *          It is a composite name. It can be null, which means
     *          the link remaining name field has not been set.
     * @see #setLinkRemainingName
     */
    public Name getLinkRemainingName() {
        return this.linkRemainingName;
    }

    /**
     * Retrieves the object to which resolution was successful.
     * This is the object to which the resolved link name is bound.
     *
     * <p>
     *  检索解析成功的对象。这是解析的链接名称绑定的对象。
     * 
     * 
     * @return The possibly null object that was resolved so far.
     * If null, it means the link resolved object field has not been set.
     * @see #getLinkResolvedName
     * @see #setLinkResolvedObj
     */
    public Object getLinkResolvedObj() {
        return this.linkResolvedObj;
    }

    /**
      * Retrieves the explanation associated with the problem encounter
      * when resolving a link.
      *
      * <p>
      *  检索解析链接时遇到的问题的解释。
      * 
      * 
      * @return The possibly null detail string explaining more about the problem
      * with resolving a link.
      *         If null, it means there is no
      *         link detail message for this exception.
      * @see #setLinkExplanation
      */
    public String getLinkExplanation() {
        return this.linkExplanation;
    }

    /**
      * Sets the explanation associated with the problem encounter
      * when resolving a link.
      *
      * <p>
      * 设置解析链接时与问题相关的说明。
      * 
      * 
      * @param msg The possibly null detail string explaining more about the problem
      * with resolving a link. If null, it means no detail will be recorded.
      * @see #getLinkExplanation
      */
    public void setLinkExplanation(String msg) {
        this.linkExplanation = msg;
    }

    /**
     * Sets the resolved link name field of this exception.
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
     *
     * <p>
     *  设置此异常的已解析的链接名称字段。
     * p>
     *  <tt> name </tt>是复合名称。如果意图是使用化合物名称或字符串设置此字段,则必须"stringify"化合物名称,并使用该字符串使用单个组件创建复合名称。
     * 然后,可以使用生成的复合名称调用此方法。
     * p>
     *  制作并存储<code> name </code>的副本。对<code> name </code>的后续更改不会影响此NamingException中的副本,反之亦然。
     * 
     * 
     * @param name The name to set resolved link name to. This can be null.
     *          If null, it sets the link resolved name field to null.
     * @see #getLinkResolvedName
     */
    public void setLinkResolvedName(Name name) {
        if (name != null) {
            this.linkResolvedName = (Name)(name.clone());
        } else {
            this.linkResolvedName = null;
        }
    }

    /**
     * Sets the remaining link name field of this exception.
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
     *  设置此异常的剩余链接名称字段。
     * p>
     *  <tt> name </tt>是复合名称。如果意图是使用化合物名称或字符串设置此字段,则必须"stringify"化合物名称,并使用该字符串使用单个组件创建复合名称。
     * 然后,可以使用生成的复合名称调用此方法。
     * p>
     *  制作并存储<code> name </code>的副本。对<code> name </code>的后续更改不会影响此NamingException中的副本,反之亦然。
     * 
     * 
     * @param name The name to set remaining link name to. This can be null.
     *  If null, it sets the remaining name field to null.
     * @see #getLinkRemainingName
     */
    public void setLinkRemainingName(Name name) {
        if (name != null)
            this.linkRemainingName = (Name)(name.clone());
        else
            this.linkRemainingName = null;
    }

    /**
     * Sets the link resolved object field of this exception.
     * This indicates the last successfully resolved object of link name.
     * <p>
     *  设置此异常的链接已解析对象字段。这表示链接名称的最后成功解析的对象。
     * 
     * 
     * @param obj The object to set link resolved object to. This can be null.
     *            If null, the link resolved object field is set to null.
     * @see #getLinkResolvedObj
     */
    public void setLinkResolvedObj(Object obj) {
        this.linkResolvedObj = obj;
    }

    /**
     * Generates the string representation of this exception.
     * This string consists of the NamingException information plus
     * the link's remaining name.
     * This string is used for debugging and not meant to be interpreted
     * programmatically.
     * <p>
     *  生成此异常的字符串表示形式。此字符串由NamingException信息和链接的其余名称组成。此字符串用于调试,不能以编程方式解释。
     * 
     * 
     * @return The non-null string representation of this link exception.
     */
    public String toString() {
        return super.toString() + "; Link Remaining Name: '" +
            this.linkRemainingName + "'";
    }

    /**
     * Generates the string representation of this exception.
     * This string consists of the NamingException information plus
     * the additional information of resolving the link.
     * If 'detail' is true, the string also contains information on
     * the link resolved object. If false, this method is the same
     * as the form of toString() that accepts no parameters.
     * This string is used for debugging and not meant to be interpreted
     * programmatically.
     *
     * <p>
     * 生成此异常的字符串表示形式。此字符串由NamingException信息和解析链接的附加信息组成。如果'detail'为true,该字符串还包含有关链接已解析对象的信息。
     * 如果为false,此方法与不接受参数的toString()的形式相同。此字符串用于调试,不能以编程方式解释。
     * 
     * 
     * @param   detail  If true, add information about the link resolved
     *                  object.
     * @return The non-null string representation of this link exception.
     */
    public String toString(boolean detail) {
        if (!detail || this.linkResolvedObj == null)
            return this.toString();

        return this.toString() + "; Link Resolved Object: " +
            this.linkResolvedObj;
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     */
    private static final long serialVersionUID = -7967662604076777712L;
};
