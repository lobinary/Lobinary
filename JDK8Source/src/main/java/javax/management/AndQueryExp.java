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
 * This class is used by the query building mechanism to represent conjunctions
 * of relational expressions.
 * <p>
 *  此类由查询构建机制用于表示关系表达式的连接。
 * 
 * 
 * @serial include
 *
 * @since 1.5
 */
class AndQueryExp extends QueryEval implements QueryExp {

    /* Serial version */
    private static final long serialVersionUID = -1081892073854801359L;

    /**
    /* <p>
    /* 
     * @serial The first QueryExp of the conjunction
     */
    private QueryExp exp1;

    /**
    /* <p>
    /* 
     * @serial The second QueryExp of the conjunction
     */
    private QueryExp exp2;


    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public AndQueryExp() {
    }

    /**
     * Creates a new AndQueryExp with q1 and q2 QueryExp.
     * <p>
     *  使用q1和q2 QueryExp创建一个新的AndQueryExp。
     * 
     */
    public AndQueryExp(QueryExp q1, QueryExp q2) {
        exp1 = q1;
        exp2 = q2;
    }


    /**
     * Returns the left query expression.
     * <p>
     *  返回左查询表达式。
     * 
     */
    public QueryExp getLeftExp()  {
        return exp1;
    }

    /**
     * Returns the right query expression.
     * <p>
     *  返回正确的查询表达式。
     * 
     */
    public QueryExp getRightExp()  {
        return exp2;
    }

    /**
     * Applies the AndQueryExp on a MBean.
     *
     * <p>
     *  将AndQueryExp应用于MBean。
     * 
     * 
     * @param name The name of the MBean on which the AndQueryExp will be applied.
     *
     * @return  True if the query was successfully applied to the MBean, false otherwise.
     *
     *
     * @exception BadStringOperationException The string passed to the method is invalid.
     * @exception BadBinaryOpValueExpException The expression passed to the method is invalid.
     * @exception BadAttributeValueExpException The attribute value passed to the method is invalid.
     * @exception InvalidApplicationException  An attempt has been made to apply a subquery expression to a
     * managed object or a qualified attribute expression to a managed object of the wrong class.
     */
    public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        return exp1.apply(name) && exp2.apply(name);
    }

   /**
    * Returns a string representation of this AndQueryExp
    * <p>
    *  返回此AndQueryExp的字符串表示形式
    */
    @Override
    public String toString() {
        return "(" + exp1 + ") and (" + exp2 + ")";
    }
}
