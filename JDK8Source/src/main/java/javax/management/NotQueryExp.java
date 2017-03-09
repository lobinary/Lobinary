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
 * This class is used by the query-building mechanism to represent negations
 * of relational expressions.
 * <p>
 *  此类由查询构建机制用于表示关系表达式的否定。
 * 
 * 
 * @serial include
 *
 * @since 1.5
 */
class NotQueryExp extends QueryEval implements QueryExp {


    /* Serial version */
    private static final long serialVersionUID = 5269643775896723397L;

    /**
    /* <p>
    /* 
     * @serial The negated {@link QueryExp}
     */
    private QueryExp exp;


    /**
     * Basic Constructor.
     * <p>
     *  基本构造函数。
     * 
     */
    public NotQueryExp() {
    }

    /**
     * Creates a new NotQueryExp for negating the specified QueryExp.
     * <p>
     *  创建一个新的NotQueryExp用于否定指定的QueryExp。
     * 
     */
    public NotQueryExp(QueryExp q) {
        exp = q;
    }


    /**
     * Returns the negated query expression of the query.
     * <p>
     *  返回查询的否定查询表达式。
     * 
     */
    public QueryExp getNegatedExp()  {
        return exp;
    }

    /**
     * Applies the NotQueryExp on a MBean.
     *
     * <p>
     *  将NotQueryExp应用于MBean。
     * 
     * 
     * @param name The name of the MBean on which the NotQueryExp will be applied.
     *
     * @return  True if the query was successfully applied to the MBean, false otherwise.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        return exp.apply(name) == false;
    }

    /**
     * Returns the string representing the object.
     * <p>
     *  返回表示对象的字符串。
     */
    @Override
    public String toString()  {
        return "not (" + exp + ")";
    }
 }
