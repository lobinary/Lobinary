/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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


package javax.print.attribute;

import java.io.Serializable;
import java.net.URI;

/**
 * Class URISyntax is an abstract base class providing the common
 * implementation of all attributes whose value is a Uniform Resource
 * Identifier (URI). Once constructed, a URI attribute's value is immutable.
 * <P>
 *
 * <p>
 *  URISyntax类是一个抽象基类,提供其值为统一资源标识符(URI)的所有属性的通用实现。一旦构造,URI属性的值是不可变的。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public abstract class URISyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = -7842661210486401678L;

    /**
     * URI value of this URI attribute.
     * <p>
     *  此URI属性的URI值。
     * 
     * 
     * @serial
     */
    private URI uri;

    /**
     * Constructs a URI attribute with the specified URI.
     *
     * <p>
     *  构造具有指定URI的URI属性。
     * 
     * 
     * @param  uri  URI.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>uri</CODE> is null.
     */
    protected URISyntax(URI uri) {
        this.uri = verify (uri);
    }

    private static URI verify(URI uri) {
        if (uri == null) {
            throw new NullPointerException(" uri is null");
        }
        return uri;
    }

    /**
     * Returns this URI attribute's URI value.
     * <p>
     *  返回此URI属性的URI值。
     * 
     * 
     * @return the URI.
     */
    public URI getURI()  {
        return uri;
    }

    /**
     * Returns a hashcode for this URI attribute.
     *
     * <p>
     *  返回此URI属性的哈希码。
     * 
     * 
     * @return  A hashcode value for this object.
     */
    public int hashCode() {
        return uri.hashCode();
    }

    /**
     * Returns whether this URI attribute is equivalent to the passed in
     * object.
     * To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class URISyntax.
     * <LI>
     * This URI attribute's underlying URI and <CODE>object</CODE>'s
     * underlying URI are equal.
     * </OL>
     *
     * <p>
     *  返回此URI属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是URISyntax类的实例。
     * <LI>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this URI
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return(object != null &&
               object instanceof URISyntax &&
               this.uri.equals (((URISyntax) object).uri));
    }

    /**
     * Returns a String identifying this URI attribute. The String is the
     * string representation of the attribute's underlying URI.
     *
     * <p>
     *  此URI属性的底层URI和<CODE>对象</CODE>的底层URI是相等的。
     * </OL>
     * 
     * 
     * @return  A String identifying this object.
     */
    public String toString() {
        return uri.toString();
    }

}
