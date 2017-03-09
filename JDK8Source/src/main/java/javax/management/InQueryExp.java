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
class InQueryExp extends QueryEval implements QueryExp {

    /* Serial version */
    private static final long serialVersionUID = -5801329450358952434L;

    /**
    /* <p>
    /* 
     * @serial The {@link ValueExp} to be found
     */
    private ValueExp val;

    /**
    /* <p>
    /* 
     * @serial The array of {@link ValueExp} to be searched
     */
    private ValueExp[]  valueList;


    /**
     * Basic Constructor.
     * <p>
     *  基本构造函数。
     * 
     */
    public InQueryExp() {
    }

    /**
     * Creates a new InQueryExp with the specified ValueExp to be found in
     * a specified array of ValueExp.
     * <p>
     *  创建一个新的InQueryExp,并在指定的ValueExp数组中找到指定的ValueExp。
     * 
     */
    public InQueryExp(ValueExp v1, ValueExp items[]) {
        val       = v1;
        valueList = items;
    }


    /**
     * Returns the checked value of the query.
     * <p>
     *  返回检查的查询值。
     * 
     */
    public ValueExp getCheckedValue()  {
        return val;
    }

    /**
     * Returns the array of values of the query.
     * <p>
     *  返回查询的值的数组。
     * 
     */
    public ValueExp[] getExplicitValues()  {
        return valueList;
    }

    /**
     * Applies the InQueryExp on a MBean.
     *
     * <p>
     *  将InQueryExp应用于MBean。
     * 
     * 
     * @param name The name of the MBean on which the InQueryExp will be applied.
     *
     * @return  True if the query was successfully applied to the MBean, false otherwise.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    public boolean apply(ObjectName name)
    throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        if (valueList != null) {
            ValueExp v      = val.apply(name);
            boolean numeric = v instanceof NumericValueExp;

            for (ValueExp element : valueList) {
                element = element.apply(name);
                if (numeric) {
                    if (((NumericValueExp) element).doubleValue() ==
                        ((NumericValueExp) v).doubleValue()) {
                        return true;
                    }
                } else {
                    if (((StringValueExp) element).getValue().equals(
                        ((StringValueExp) v).getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the string representing the object.
     * <p>
     *  返回表示对象的字符串。
     */
    public String toString()  {
        return val + " in (" + generateValueList() + ")";
    }


    private String generateValueList() {
        if (valueList == null || valueList.length == 0) {
            return "";
        }

        final StringBuilder result =
                new StringBuilder(valueList[0].toString());

        for (int i = 1; i < valueList.length; i++) {
            result.append(", ");
            result.append(valueList[i]);
        }

        return result.toString();
    }

 }
