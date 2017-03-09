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
 * This class is used by the query-building mechanism to represent binary
 * operations.
 * <p>
 *  此类由查询构建机制用于表示二进制操作。
 * 
 * 
 * @serial include
 *
 * @since 1.5
 */
class BinaryOpValueExp extends QueryEval implements ValueExp {

    /* Serial version */
    private static final long serialVersionUID = 1216286847881456786L;

    /**
    /* <p>
    /* 
     * @serial The operator
     */
    private int op;

    /**
    /* <p>
    /* 
     * @serial The first value
     */
    private ValueExp exp1;

    /**
    /* <p>
    /* 
     * @serial The second value
     */
    private ValueExp exp2;


    /**
     * Basic Constructor.
     * <p>
     *  基本构造函数。
     * 
     */
    public BinaryOpValueExp() {
    }

    /**
     * Creates a new BinaryOpValueExp using operator o applied on v1 and
     * v2 values.
     * <p>
     *  使用对v1和v2值应用的运算符o创建新的BinaryOpValueExp。
     * 
     */
    public BinaryOpValueExp(int o, ValueExp v1, ValueExp v2) {
        op   = o;
        exp1 = v1;
        exp2 = v2;
    }


    /**
     * Returns the operator of the value expression.
     * <p>
     *  返回值表达式的运算符。
     * 
     */
    public int getOperator()  {
        return op;
    }

    /**
     * Returns the left value of the value expression.
     * <p>
     *  返回值表达式的左侧值。
     * 
     */
    public ValueExp getLeftValue()  {
        return exp1;
    }

    /**
     * Returns the right value of the value expression.
     * <p>
     *  返回值表达式的正确值。
     * 
     */
    public ValueExp getRightValue()  {
        return exp2;
    }

    /**
     * Applies the BinaryOpValueExp on a MBean.
     *
     * <p>
     *  将BinaryOpValueExp应用于MBean。
     * 
     * 
     * @param name The name of the MBean on which the BinaryOpValueExp will be applied.
     *
     * @return  The ValueExp.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    public ValueExp apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        ValueExp val1 = exp1.apply(name);
        ValueExp val2 = exp2.apply(name);
        String sval1;
        String sval2;
        double dval1;
        double dval2;
        long   lval1;
        long   lval2;
        boolean numeric = val1 instanceof NumericValueExp;

        if (numeric) {
            if (((NumericValueExp)val1).isLong()) {
                lval1 = ((NumericValueExp)val1).longValue();
                lval2 = ((NumericValueExp)val2).longValue();

                switch (op) {
                case Query.PLUS:
                    return Query.value(lval1 + lval2);
                case Query.TIMES:
                    return Query.value(lval1 * lval2);
                case Query.MINUS:
                    return Query.value(lval1 - lval2);
                case Query.DIV:
                    return Query.value(lval1 / lval2);
                }

            } else {
                dval1 = ((NumericValueExp)val1).doubleValue();
                dval2 = ((NumericValueExp)val2).doubleValue();

                switch (op) {
                case Query.PLUS:
                    return Query.value(dval1 + dval2);
                case Query.TIMES:
                    return Query.value(dval1 * dval2);
                case Query.MINUS:
                    return Query.value(dval1 - dval2);
                case Query.DIV:
                    return Query.value(dval1 / dval2);
                }
            }
        } else {
            sval1 = ((StringValueExp)val1).getValue();
            sval2 = ((StringValueExp)val2).getValue();

            switch (op) {
            case Query.PLUS:
                return new StringValueExp(sval1 + sval2);
            default:
                throw new BadStringOperationException(opString());
            }
        }

        throw new BadBinaryOpValueExpException(this);
    }

    /**
     * Returns the string representing the object
     * <p>
     *  返回表示对象的字符串
     * 
     */
    public String toString()  {
        try {
            return parens(exp1, true) + " " + opString() + " " + parens(exp2, false);
        } catch (BadBinaryOpValueExpException ex) {
            return "invalid expression";
        }
    }

    /*
     * Add parentheses to the given subexpression if necessary to
     * preserve meaning.  Suppose this BinaryOpValueExp is
     * Query.times(Query.plus(Query.attr("A"), Query.attr("B")), Query.attr("C")).
     * Then the original toString() logic would return A + B * C.
     * We check precedences in order to return (A + B) * C, which is the
     * meaning of the ValueExp.
     *
     * We need to add parentheses if the unparenthesized expression would
     * be parsed as a different ValueExp from the original.
     * We cannot omit parentheses even when mathematically
     * the result would be equivalent, because we do not know whether the
     * numeric values will be integer or floating-point.  Addition and
     * multiplication are associative for integers but not always for
     * floating-point.
     *
     * So the rule is that we omit parentheses if the ValueExp
     * is (A op1 B) op2 C and the precedence of op1 is greater than or
     * equal to that of op2; or if the ValueExp is A op1 (B op2 C) and
     * the precedence of op2 is greater than that of op1.  (There are two
     * precedences: that of * and / is greater than that of + and -.)
     * The case of (A op1 B) op2 (C op3 D) applies each rule in turn.
     *
     * The following examples show the rules in action.  On the left,
     * the original ValueExp.  On the right, the string representation.
     *
     * (A + B) + C     A + B + C
     * (A * B) + C     A * B + C
     * (A + B) * C     (A + B) * C
     * (A * B) * C     A * B * C
     * A + (B + C)     A + (B + C)
     * A + (B * C)     A + B * C
     * A * (B + C)     A * (B + C)
     * A * (B * C)     A * (B * C)
     * <p>
     *  如果需要保留含义,请在给定的子表达式中添加括号。
     * 假设此BinaryOpValueExp是Query.times(Query.plus(Query.attr("A"),Query.attr("B")),Query.attr("C"))。
     * 然后原来的toString()逻辑将返回A + B * C.我们检查优先级为了返回(A + B)* C,这是ValueExp的含义。
     * 
     *  如果未加括号的表达式被解析为与原始值不同的ValueExp,我们需要添加括号。即使在数学上,结果是等效的,我们也不能省略括号,因为我们不知道数值是整数还是浮点数。
     * 加法和乘法是整数的关联,但不总是浮点的。
     * 
     * 所以规则是,如果ValueExp是(A op1 B)op2 C并且op1的优先级大于或等于op2的优先级,我们将省略括号;或者ValueExp是A op1(B op2 C),并且op2的优先级大于op1
     */
    private String parens(ValueExp subexp, boolean left)
    throws BadBinaryOpValueExpException {
        boolean omit;
        if (subexp instanceof BinaryOpValueExp) {
            int subop = ((BinaryOpValueExp) subexp).op;
            if (left)
                omit = (precedence(subop) >= precedence(op));
            else
                omit = (precedence(subop) > precedence(op));
        } else
            omit = true;

        if (omit)
            return subexp.toString();
        else
            return "(" + subexp + ")";
    }

    private int precedence(int xop) throws BadBinaryOpValueExpException {
        switch (xop) {
            case Query.PLUS: case Query.MINUS: return 0;
            case Query.TIMES: case Query.DIV: return 1;
            default:
                throw new BadBinaryOpValueExpException(this);
        }
    }

    private String opString() throws BadBinaryOpValueExpException {
        switch (op) {
        case Query.PLUS:
            return "+";
        case Query.TIMES:
            return "*";
        case Query.MINUS:
            return "-";
        case Query.DIV:
            return "/";
        }

        throw new BadBinaryOpValueExpException(this);
    }

    @Deprecated
    public void setMBeanServer(MBeanServer s) {
        super.setMBeanServer(s);
     }
 }
