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
 * <p>Represents attributes used as arguments to relational constraints,
 * where the attribute must be in an MBean of a specified {@linkplain
 * MBeanInfo#getClassName() class}. A QualifiedAttributeValueExp may be used
 * anywhere a ValueExp is required.
 *
 * <p>
 *  <p>表示用作关系约束的参数的属性,其中属性必须在指定的{@linkplain MBeanInfo#getClassName()类的MBean中。
 *  QualifiedAttributeValueExp可以在需要ValueExp的任何地方使用。
 * 
 * 
 * @serial include
 *
 * @since 1.5
 */
class QualifiedAttributeValueExp extends AttributeValueExp   {


    /* Serial version */
    private static final long serialVersionUID = 8832517277410933254L;

    /**
    /* <p>
    /* 
     * @serial The attribute class name
     */
    private String className;


    /**
     * Basic Constructor.
     * <p>
     *  基本构造函数。
     * 
     * 
     * @deprecated see {@link AttributeValueExp#AttributeValueExp()}
     */
    @Deprecated
    public QualifiedAttributeValueExp() {
    }

    /**
     * Creates a new QualifiedAttributeValueExp representing the specified object
     * attribute, named attr with class name className.
     * <p>
     *  创建一个新的QualifiedAttributeValueExp表示指定的对象属性,命名为attr,类名为className。
     * 
     */
    public QualifiedAttributeValueExp(String className, String attr) {
        super(attr);
        this.className = className;
    }


    /**
     * Returns a string representation of the class name of the attribute.
     * <p>
     *  返回属性的类名称的字符串表示形式。
     * 
     */
    public String getAttrClassName()  {
        return className;
    }

    /**
     * Applies the QualifiedAttributeValueExp to an MBean.
     *
     * <p>
     *  将QualifiedAttributeValueExp应用于MBean。
     * 
     * 
     * @param name The name of the MBean on which the QualifiedAttributeValueExp will be applied.
     *
     * @return  The ValueExp.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    @Override
    public ValueExp apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException  {
        try {
            MBeanServer server = QueryEval.getMBeanServer();
            String v = server.getObjectInstance(name).getClassName();

            if (v.equals(className)) {
                return super.apply(name);
            }
            throw new InvalidApplicationException("Class name is " + v +
                                                  ", should be " + className);

        } catch (Exception e) {
            throw new InvalidApplicationException("Qualified attribute: " + e);
            /* Can happen if MBean disappears between the time we
               construct the list of MBeans to query and the time we
               evaluate the query on this MBean, or if
            /* <p>
            /*  构造要查询的MBean的列表,以及我们评估此MBean上的查询的时间,或if
            /* 
            /* 
               getObjectInstance throws SecurityException.  */
        }
    }

    /**
     * Returns the string representing its value
     * <p>
     *  返回表示其值的字符串
     */
    @Override
    public String toString()  {
        if (className != null) {
            return className + "." + super.toString();
        } else {
            return super.toString();
        }
    }

}
