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



/**
 * Represents strings that are arguments to relational constraints.
 * A <CODE>StringValueExp</CODE> may be used anywhere a <CODE>ValueExp</CODE> is required.
 *
 * <p>
 *  表示作为关系约束的参数的字符串。可以在需要<CODE> ValueExp </CODE>的任何地方使用<CODE> StringValueExp </CODE>。
 * 
 * 
 * @since 1.5
 */
public class StringValueExp implements ValueExp   {

    /* Serial version */
    private static final long serialVersionUID = -3256390509806284044L;

    /**
    /* <p>
    /* 
     * @serial The string literal
     */
    private String val;

    /**
     * Basic constructor.
     * <p>
     *  基本构造函数。
     * 
     */
    public StringValueExp() {
    }

    /**
     * Creates a new <CODE>StringValueExp</CODE> representing the
     * given string.
     *
     * <p>
     *  创建一个表示给定字符串的新<CODE> StringValueExp </CODE>。
     * 
     * 
     * @param val the string that will be the value of this expression
     */
    public StringValueExp(String val) {
        this.val = val;
    }

    /**
     * Returns the string represented by the
     * <CODE>StringValueExp</CODE> instance.
     *
     * <p>
     *  返回由<CODE> StringValueExp </CODE>实例表示的字符串。
     * 
     * 
     * @return the string.
     */
    public String getValue()  {
        return val;
    }

    /**
     * Returns the string representing the object.
     * <p>
     *  返回表示对象的字符串。
     * 
     */
    public String toString()  {
        return "'" + val.replace("'", "''") + "'";
    }


    /**
     * Sets the MBean server on which the query is to be performed.
     *
     * <p>
     *  设置要对其执行查询的MBean服务器。
     * 
     * 
     * @param s The MBean server on which the query is to be performed.
     */
    /* There is no need for this method, because if a query is being
       evaluated a StringValueExp can only appear inside a QueryExp,
    /* <p>
    /*  评估一个StringValueExp只能出现在QueryExp内部,
    /* 
    /* 
       and that QueryExp will itself have done setMBeanServer.  */
    @Deprecated
    public void setMBeanServer(MBeanServer s)  { }

    /**
     * Applies the ValueExp on a MBean.
     *
     * <p>
     *  将ValueExp应用于MBean。
     * 
     * @param name The name of the MBean on which the ValueExp will be applied.
     *
     * @return  The <CODE>ValueExp</CODE>.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    public ValueExp apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        return this;
    }
 }
