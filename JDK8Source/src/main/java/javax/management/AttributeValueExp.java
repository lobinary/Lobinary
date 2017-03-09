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


import com.sun.jmx.mbeanserver.Introspector;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * <p>Represents attributes used as arguments to relational constraints.
 * Instances of this class are usually obtained using {@link Query#attr(String)
 * Query.attr}.</p>
 *
 * <p>An <CODE>AttributeValueExp</CODE> may be used anywhere a
 * <CODE>ValueExp</CODE> is required.
 *
 * <p>
 *  <p>表示用作关系约束的参数的属性。这个类的实例通常使用{@link Query#attr(String)Query.attr}获得。</p>
 * 
 *  <p> <CODE> AttributeValueExp </CODE>可用于需要<CODE> ValueExp </CODE>的任何地方。
 * 
 * 
 * @since 1.5
 */
public class AttributeValueExp implements ValueExp  {


    /* Serial version */
    private static final long serialVersionUID = -7768025046539163385L;

    /**
    /* <p>
    /* 
     * @serial The name of the attribute
     */
    private String attr;

    /**
     * An <code>AttributeValueExp</code> with a null attribute.
     * <p>
     *  带有null属性的<code> AttributeValueExp </code>。
     * 
     * 
     * @deprecated An instance created with this constructor cannot be
     * used in a query.
     */
    @Deprecated
    public AttributeValueExp() {
    }

    /**
     * Creates a new <CODE>AttributeValueExp</CODE> representing the
     * specified object attribute, named attr.
     *
     * <p>
     *  创建一个新的<CODE> AttributeValueExp </CODE>,表示指定的对象属性,名为attr。
     * 
     * 
     * @param attr the name of the attribute whose value is the value
     * of this {@link ValueExp}.
     */
    public AttributeValueExp(String attr) {
        this.attr = attr;
    }

    /**
     * Returns a string representation of the name of the attribute.
     *
     * <p>
     *  返回属性名称的字符串表示形式。
     * 
     * 
     * @return the attribute name.
     */
    public String getAttributeName()  {
        return attr;
    }

    /**
     * <p>Applies the <CODE>AttributeValueExp</CODE> on an MBean.
     * This method calls {@link #getAttribute getAttribute(name)} and wraps
     * the result as a {@code ValueExp}.  The value returned by
     * {@code getAttribute} must be a {@code Number}, {@code String},
     * or {@code Boolean}; otherwise this method throws a
     * {@code BadAttributeValueExpException}, which will cause
     * the containing query to be false for this {@code name}.</p>
     *
     * <p>
     *  <p>在MBean上应用<CODE> AttributeValueExp </CODE>。
     * 此方法调用{@link #getAttribute getAttribute(name)},并将结果作为{@code ValueExp}包装。
     *  {@code getAttribute}返回的值必须是{@code Number},{@code String}或{@code Boolean};否则此方法会抛出{@code BadAttributeValueExpException}
     * ,这将导致包含的查询对于此{@code name}为false。
     * 此方法调用{@link #getAttribute getAttribute(name)},并将结果作为{@code ValueExp}包装。</p>。
     * 
     * 
     * @param name The name of the MBean on which the <CODE>AttributeValueExp</CODE> will be applied.
     *
     * @return  The <CODE>ValueExp</CODE>.
     *
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     *
     */
    @Override
    public ValueExp apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
        BadAttributeValueExpException, InvalidApplicationException {
        Object result = getAttribute(name);

        if (result instanceof Number) {
            return new NumericValueExp((Number)result);
        } else if (result instanceof String) {
            return new StringValueExp((String)result);
        } else if (result instanceof Boolean) {
            return new BooleanValueExp((Boolean)result);
        } else {
            throw new BadAttributeValueExpException(result);
        }
    }

    /**
     * Returns the string representing its value.
     * <p>
     *  返回表示其值的字符串。
     * 
     */
    @Override
    public String toString()  {
        return attr;
    }


    /**
     * Sets the MBean server on which the query is to be performed.
     *
     * <p>
     *  设置要对其执行查询的MBean服务器。
     * 
     * 
     * @param s The MBean server on which the query is to be performed.
     *
     * @deprecated This method has no effect.  The MBean Server used to
     * obtain an attribute value is {@link QueryEval#getMBeanServer()}.
     */
    /* There is no need for this method, because if a query is being
       evaluted an AttributeValueExp can only appear inside a QueryExp,
    /* <p>
    /*  evaluted一个AttributeValueExp只能出现在QueryExp内部,
    /* 
    /* 
       and that QueryExp will itself have done setMBeanServer.  */
    @Deprecated
    @Override
    public void setMBeanServer(MBeanServer s)  {
    }


    /**
     * <p>Return the value of the given attribute in the named MBean.
     * If the attempt to access the attribute generates an exception,
     * return null.</p>
     *
     * <p>The MBean Server used is the one returned by {@link
     * QueryEval#getMBeanServer()}.</p>
     *
     * <p>
     *  <p>返回指定MBean中给定属性的值。如果尝试访问该属性会生成异常,请返回null。</p>
     * 
     * 
     * @param name the name of the MBean whose attribute is to be returned.
     *
     * @return the value of the attribute, or null if it could not be
     * obtained.
     */
    protected Object getAttribute(ObjectName name) {
        try {
            // Get the value from the MBeanServer

            MBeanServer server = QueryEval.getMBeanServer();

            return server.getAttribute(name, attr);
        } catch (Exception re) {
            return null;
        }
    }
}
