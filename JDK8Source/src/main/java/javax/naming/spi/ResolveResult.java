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

import javax.naming.Name;
import javax.naming.Context;
import javax.naming.CompositeName;
import javax.naming.InvalidNameException;

/**
  * This class represents the result of resolution of a name.
  * It contains the object to which name was resolved, and the portion
  * of the name that has not been resolved.
  *<p>
  * A ResolveResult instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single ResolveResult instance should lock the object.
  *
  * <p>
  *  此类表示名称解析的结果。它包含已解析名称的对象,以及尚未解析的名称部分。
  * p>
  *  ResolveResult实例不会与并发多线程访问同步。尝试访问和修改单个ResolveResult实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */
public class ResolveResult implements java.io.Serializable {
    /**
     * Field containing the Object that was resolved to successfully.
     * It can be null only when constructed using a subclass.
     * Constructors should always initialize this.
     * <p>
     *  包含已解析为成功的对象的字段。它只能在使用子类构造时为null。构造函数应该始终初始化它。
     * 
     * 
     * @serial
     */
    protected Object resolvedObj;
    /**
     * Field containing the remaining name yet to be resolved.
     * It can be null only when constructed using a subclass.
     * Constructors should always initialize this.
     * <p>
     *  包含剩余名称的字段尚未解析。它只能在使用子类构造时为null。构造函数应该始终初始化它。
     * 
     * 
     * @serial
     */
    protected Name remainingName;

    /**
      * Constructs an instance of ResolveResult with the
      * resolved object and remaining name both initialized to null.
      * <p>
      *  构造ResolveResult的实例,其中解析的对象和剩余名称都初始化为null。
      * 
      */
    protected ResolveResult() {
        resolvedObj = null;
        remainingName = null;
    }

    /**
      * Constructs a new instance of ResolveResult consisting of
      * the resolved object and the remaining unresolved component.
      *
      * <p>
      *  构造ResolveResult的新实例,由解析的对象和剩余的未解析的组件组成。
      * 
      * 
      * @param robj The non-null object resolved to.
      * @param rcomp The single remaining name component that has yet to be
      *                 resolved. Cannot be null (but can be empty).
      */
    public ResolveResult(Object robj, String rcomp) {
        resolvedObj = robj;
        try {
        remainingName = new CompositeName(rcomp);
//          remainingName.appendComponent(rcomp);
        } catch (InvalidNameException e) {
            // ignore; shouldn't happen
        }
    }

    /**
      * Constructs a new instance of ResolveResult consisting of
      * the resolved Object and the remaining name.
      *
      * <p>
      *  构造ResolveResult的新实例,由解析的对象和其余名称组成。
      * 
      * 
      * @param robj The non-null Object resolved to.
      * @param rname The non-null remaining name that has yet to be resolved.
      */
    public ResolveResult(Object robj, Name rname) {
        resolvedObj = robj;
        setRemainingName(rname);
    }

    /**
     * Retrieves the remaining unresolved portion of the name.
     *
     * <p>
     *  检索名称的剩余未解析部分。
     * 
     * 
     * @return The remaining unresolved portion of the name.
     *          Cannot be null but empty OK.
     * @see #appendRemainingName
     * @see #appendRemainingComponent
     * @see #setRemainingName
     */
    public Name getRemainingName() {
        return this.remainingName;
    }

    /**
     * Retrieves the Object to which resolution was successful.
     *
     * <p>
     *  检索解析成功的对象。
     * 
     * 
     * @return The Object to which resolution was successful. Cannot be null.
      * @see #setResolvedObj
     */
    public Object getResolvedObj() {
        return this.resolvedObj;
    }

    /**
      * Sets the remaining name field of this result to name.
      * A copy of name is made so that modifying the copy within
      * this ResolveResult does not affect <code>name</code> and
      * vice versa.
      *
      * <p>
      *  将此结果的其余名称字段设置为name。创建名称副本,以便在此ResolveResult中修改副本不会影响<code> name </code>,反之亦然。
      * 
      * 
      * @param name The name to set remaining name to. Cannot be null.
      * @see #getRemainingName
      * @see #appendRemainingName
      * @see #appendRemainingComponent
      */
    public void setRemainingName(Name name) {
        if (name != null)
            this.remainingName = (Name)(name.clone());
        else {
            // ??? should throw illegal argument exception
            this.remainingName = null;
        }
    }

    /**
      * Adds components to the end of remaining name.
      *
      * <p>
      *  将组件添加到剩余名称的末尾。
      * 
      * 
      * @param name The components to add. Can be null.
      * @see #getRemainingName
      * @see #setRemainingName
      * @see #appendRemainingComponent
      */
    public void appendRemainingName(Name name) {
//      System.out.println("appendingRemainingName: " + name.toString());
//      Exception e = new Exception();
//      e.printStackTrace();
        if (name != null) {
            if (this.remainingName != null) {
                try {
                    this.remainingName.addAll(name);
                } catch (InvalidNameException e) {
                    // ignore; shouldn't happen for composite name
                }
            } else {
                this.remainingName = (Name)(name.clone());
            }
        }
    }

    /**
      * Adds a single component to the end of remaining name.
      *
      * <p>
      * 将单个组件添加到剩余名称的末尾。
      * 
      * 
      * @param name The component to add. Can be null.
      * @see #getRemainingName
      * @see #appendRemainingName
      */
    public void appendRemainingComponent(String name) {
        if (name != null) {
            CompositeName rname = new CompositeName();
            try {
                rname.add(name);
            } catch (InvalidNameException e) {
                // ignore; shouldn't happen for empty composite name
            }
            appendRemainingName(rname);
        }
    }

    /**
      * Sets the resolved Object field of this result to obj.
      *
      * <p>
      *  将此结果的已解析对象字段设置为obj。
      * 
      * @param obj The object to use for setting the resolved obj field.
      *            Cannot be null.
      * @see #getResolvedObj
      */
    public void setResolvedObj(Object obj) {
        this.resolvedObj = obj;
        // ??? should check for null?
    }

    private static final long serialVersionUID = -4552108072002407559L;
}
