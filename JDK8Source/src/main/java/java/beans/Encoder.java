/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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
package java.beans;

import com.sun.beans.finder.PersistenceDelegateFinder;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * An <code>Encoder</code> is a class which can be used to create
 * files or streams that encode the state of a collection of
 * JavaBeans in terms of their public APIs. The <code>Encoder</code>,
 * in conjunction with its persistence delegates, is responsible for
 * breaking the object graph down into a series of <code>Statements</code>s
 * and <code>Expression</code>s which can be used to create it.
 * A subclass typically provides a syntax for these expressions
 * using some human readable form - like Java source code or XML.
 *
 * <p>
 *  <code> Encoder </code>是一个类,可用于创建文件或流,根据其公共API编码JavaBeans集合的状态。
 *  <code> Encoder </code>结合其持久性委托负责将对象图分解为一系列<code>语句</code>和<code>表达式</code>用于创建它。
 * 子类通常使用一些人类可读的形式(如Java源代码或XML)为这些表达式提供语法。
 * 
 * 
 * @since 1.4
 *
 * @author Philip Milne
 */

public class Encoder {
    private final PersistenceDelegateFinder finder = new PersistenceDelegateFinder();
    private Map<Object, Expression> bindings = new IdentityHashMap<>();
    private ExceptionListener exceptionListener;
    boolean executeStatements = true;
    private Map<Object, Object> attributes;

    /**
     * Write the specified object to the output stream.
     * The serialized form will denote a series of
     * expressions, the combined effect of which will create
     * an equivalent object when the input stream is read.
     * By default, the object is assumed to be a <em>JavaBean</em>
     * with a nullary constructor, whose state is defined by
     * the matching pairs of "setter" and "getter" methods
     * returned by the Introspector.
     *
     * <p>
     *  将指定的对象写入输出流。序列化形式将表示一系列表达式,其组合效果将在读取输入流时创建等效对象。
     * 默认情况下,对象假定为具有nullary构造函数的<em> JavaBean </em>,其状态由Introspector返回的匹配的"setter"和"getter"方法对定义。
     * 
     * 
     * @param o The object to be written to the stream.
     *
     * @see XMLDecoder#readObject
     */
    protected void writeObject(Object o) {
        if (o == this) {
            return;
        }
        PersistenceDelegate info = getPersistenceDelegate(o == null ? null : o.getClass());
        info.writeObject(o, this);
    }

    /**
     * Sets the exception handler for this stream to <code>exceptionListener</code>.
     * The exception handler is notified when this stream catches recoverable
     * exceptions.
     *
     * <p>
     *  将此流的异常处理程序设置为<code> exceptionListener </code>。当此流捕获可恢复异常时,将通知异常处理程序。
     * 
     * 
     * @param exceptionListener The exception handler for this stream;
     *       if <code>null</code> the default exception listener will be used.
     *
     * @see #getExceptionListener
     */
    public void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    /**
     * Gets the exception handler for this stream.
     *
     * <p>
     *  获取此流的异常处理程序。
     * 
     * 
     * @return The exception handler for this stream;
     *    Will return the default exception listener if this has not explicitly been set.
     *
     * @see #setExceptionListener
     */
    public ExceptionListener getExceptionListener() {
        return (exceptionListener != null) ? exceptionListener : Statement.defaultExceptionListener;
    }

    Object getValue(Expression exp) {
        try {
            return (exp == null) ? null : exp.getValue();
        }
        catch (Exception e) {
            getExceptionListener().exceptionThrown(e);
            throw new RuntimeException("failed to evaluate: " + exp.toString());
        }
    }

    /**
     * Returns the persistence delegate for the given type.
     * The persistence delegate is calculated by applying
     * the following rules in order:
     * <ol>
     * <li>
     * If a persistence delegate is associated with the given type
     * by using the {@link #setPersistenceDelegate} method
     * it is returned.
     * <li>
     * A persistence delegate is then looked up by the name
     * composed of the the fully qualified name of the given type
     * and the "PersistenceDelegate" postfix.
     * For example, a persistence delegate for the {@code Bean} class
     * should be named {@code BeanPersistenceDelegate}
     * and located in the same package.
     * <pre>
     * public class Bean { ... }
     * public class BeanPersistenceDelegate { ... }</pre>
     * The instance of the {@code BeanPersistenceDelegate} class
     * is returned for the {@code Bean} class.
     * <li>
     * If the type is {@code null},
     * a shared internal persistence delegate is returned
     * that encodes {@code null} value.
     * <li>
     * If the type is a {@code enum} declaration,
     * a shared internal persistence delegate is returned
     * that encodes constants of this enumeration
     * by their names.
     * <li>
     * If the type is a primitive type or the corresponding wrapper,
     * a shared internal persistence delegate is returned
     * that encodes values of the given type.
     * <li>
     * If the type is an array,
     * a shared internal persistence delegate is returned
     * that encodes an array of the appropriate type and length,
     * and each of its elements as if they are properties.
     * <li>
     * If the type is a proxy,
     * a shared internal persistence delegate is returned
     * that encodes a proxy instance by using
     * the {@link java.lang.reflect.Proxy#newProxyInstance} method.
     * <li>
     * If the {@link BeanInfo} for this type has a {@link BeanDescriptor}
     * which defined a "persistenceDelegate" attribute,
     * the value of this named attribute is returned.
     * <li>
     * In all other cases the default persistence delegate is returned.
     * The default persistence delegate assumes the type is a <em>JavaBean</em>,
     * implying that it has a default constructor and that its state
     * may be characterized by the matching pairs of "setter" and "getter"
     * methods returned by the {@link Introspector} class.
     * The default constructor is the constructor with the greatest number
     * of parameters that has the {@link ConstructorProperties} annotation.
     * If none of the constructors has the {@code ConstructorProperties} annotation,
     * then the nullary constructor (constructor with no parameters) will be used.
     * For example, in the following code fragment, the nullary constructor
     * for the {@code Foo} class will be used,
     * while the two-parameter constructor
     * for the {@code Bar} class will be used.
     * <pre>
     * public class Foo {
     *     public Foo() { ... }
     *     public Foo(int x) { ... }
     * }
     * public class Bar {
     *     public Bar() { ... }
     *     &#64;ConstructorProperties({"x"})
     *     public Bar(int x) { ... }
     *     &#64;ConstructorProperties({"x", "y"})
     *     public Bar(int x, int y) { ... }
     * }</pre>
     * </ol>
     *
     * <p>
     *  返回给定类型的持久性委托。持久性委托是通过按顺序应用以下规则计算的：
     * <ol>
     * <li>
     * 如果持久化委托通过使用{@link #setPersistenceDelegate}方法与给定类型相关联,则返回它。
     * <li>
     *  然后,通过由给定类型的完全限定名称和"PersistenceDelegate"后缀组成的名称查找持久性委托。
     * 例如,{@code Bean}类的持久委托应该命名为{@code BeanPersistenceDelegate},并且位于同一个包中。
     * <pre>
     *  public class Bean {...} public class BeanPersistenceDelegate {...} </pre> {@code Bean}类的{@code BeanPersistenceDelegate}
     * 类的实例被返回。
     * <li>
     *  如果类型为{@code null},那么将返回一个共享内部持久性委托,该代理对{@code null}值进行编码。
     * <li>
     *  如果类型是一个{@code enum}声明,将返回一个共享内部持久性委托,它通过名称对这个枚举的常量进行编码。
     * <li>
     *  如果类型是原始类型或相应的包装器,则返回共享内部持久性委托,其对给定类型的值进行编码。
     * <li>
     *  如果类型是数组,将返回一个共享内部持久性委托,该委托将编码适当类型和长度的数组,以及每个元素都是属性。
     * <li>
     *  如果类型是代理,则会返回共享内部持久性委托,通过使用{@link java.lang.reflect.Proxy#newProxyInstance}方法对代理实例进行编码。
     * <li>
     * 如果此类型的{@link BeanInfo}具有定义了"persistenceDelegate"属性的{@link BeanDescriptor},则返回此命名属性的值。
     * <li>
     *  在所有其他情况下,将返回缺省持久性委托。
     * 默认持久委托假定类型是一个JavaBean <em> </em>,意味着它有一个默认的构造函数,其状态可以由匹配的"setter"和"getter"方法的对{链接Introspector}类。
     * 默认构造函数是具有最大数量的具有{@link ConstructorProperties}注释的参数的构造函数。
     * 如果没有一个构造函数有{@code ConstructorProperties}注释,那么将使用nullary构造函数(没有参数的构造函数)。
     * 例如,在下面的代码片段中,将使用{@code Foo}类的nullary构造函数,而将使用{@code Bar}类的双参数构造函数。
     * 
     * @param type  the class of the objects
     * @return the persistence delegate for the given type
     *
     * @see #setPersistenceDelegate
     * @see java.beans.Introspector#getBeanInfo
     * @see java.beans.BeanInfo#getBeanDescriptor
     */
    public PersistenceDelegate getPersistenceDelegate(Class<?> type) {
        PersistenceDelegate pd = this.finder.find(type);
        if (pd == null) {
            pd = MetaData.getPersistenceDelegate(type);
            if (pd != null) {
                this.finder.register(type, pd);
            }
        }
        return pd;
    }

    /**
     * Associates the specified persistence delegate with the given type.
     *
     * <p>
     * <pre>
     *  public class Foo {public Foo(){...} public Foo(int x){...}} public class Bar {public Bar(){...} @Con
     * structorProperties({"x"})public Bar int x){...} @ConstructorProperties({"x","y"})public Bar(int x,int
     *  y){...}} </pre>。
     * </ol>
     * 
     * 
     * @param type  the class of objects that the specified persistence delegate applies to
     * @param delegate  the persistence delegate for instances of the given type
     *
     * @see #getPersistenceDelegate
     * @see java.beans.Introspector#getBeanInfo
     * @see java.beans.BeanInfo#getBeanDescriptor
     */
    public void setPersistenceDelegate(Class<?> type, PersistenceDelegate delegate) {
        this.finder.register(type, delegate);
    }

    /**
     * Removes the entry for this instance, returning the old entry.
     *
     * <p>
     *  将指定的persistence委托与给定的类型相关联。
     * 
     * 
     * @param oldInstance The entry that should be removed.
     * @return The entry that was removed.
     *
     * @see #get
     */
    public Object remove(Object oldInstance) {
        Expression exp = bindings.remove(oldInstance);
        return getValue(exp);
    }

    /**
     * Returns a tentative value for <code>oldInstance</code> in
     * the environment created by this stream. A persistence
     * delegate can use its <code>mutatesTo</code> method to
     * determine whether this value may be initialized to
     * form the equivalent object at the output or whether
     * a new object must be instantiated afresh. If the
     * stream has not yet seen this value, null is returned.
     *
     * <p>
     *  删除此实例的条目,返回旧条目。
     * 
     * 
     * @param  oldInstance The instance to be looked up.
     * @return The object, null if the object has not been seen before.
     */
    public Object get(Object oldInstance) {
        if (oldInstance == null || oldInstance == this ||
            oldInstance.getClass() == String.class) {
            return oldInstance;
        }
        Expression exp = bindings.get(oldInstance);
        return getValue(exp);
    }

    private Object writeObject1(Object oldInstance) {
        Object o = get(oldInstance);
        if (o == null) {
            writeObject(oldInstance);
            o = get(oldInstance);
        }
        return o;
    }

    private Statement cloneStatement(Statement oldExp) {
        Object oldTarget = oldExp.getTarget();
        Object newTarget = writeObject1(oldTarget);

        Object[] oldArgs = oldExp.getArguments();
        Object[] newArgs = new Object[oldArgs.length];
        for (int i = 0; i < oldArgs.length; i++) {
            newArgs[i] = writeObject1(oldArgs[i]);
        }
        Statement newExp = Statement.class.equals(oldExp.getClass())
                ? new Statement(newTarget, oldExp.getMethodName(), newArgs)
                : new Expression(newTarget, oldExp.getMethodName(), newArgs);
        newExp.loader = oldExp.loader;
        return newExp;
    }

    /**
     * Writes statement <code>oldStm</code> to the stream.
     * The <code>oldStm</code> should be written entirely
     * in terms of the callers environment, i.e. the
     * target and all arguments should be part of the
     * object graph being written. These expressions
     * represent a series of "what happened" expressions
     * which tell the output stream how to produce an
     * object graph like the original.
     * <p>
     * The implementation of this method will produce
     * a second expression to represent the same expression in
     * an environment that will exist when the stream is read.
     * This is achieved simply by calling <code>writeObject</code>
     * on the target and all the arguments and building a new
     * expression with the results.
     *
     * <p>
     * 返回此流创建的环境中<code> oldInstance </code>的临时值。
     * 持久性委托可以使用其<code> mutatesTo </code>方法来确定此值是否可以初始化以在输出处形成等效对象,或者是否必须重新实例化新对象。如果流尚未看到此值,则返回null。
     * 
     * 
     * @param oldStm The expression to be written to the stream.
     */
    public void writeStatement(Statement oldStm) {
        // System.out.println("writeStatement: " + oldExp);
        Statement newStm = cloneStatement(oldStm);
        if (oldStm.getTarget() != this && executeStatements) {
            try {
                newStm.execute();
            } catch (Exception e) {
                getExceptionListener().exceptionThrown(new Exception("Encoder: discarding statement "
                                                                     + newStm, e));
            }
        }
    }

    /**
     * The implementation first checks to see if an
     * expression with this value has already been written.
     * If not, the expression is cloned, using
     * the same procedure as <code>writeStatement</code>,
     * and the value of this expression is reconciled
     * with the value of the cloned expression
     * by calling <code>writeObject</code>.
     *
     * <p>
     *  将语句<code> oldStm </code>写入流。 <code> oldStm </code>应完全根据调用者环境编写,即目标和所有参数应该是正在写入的对象图的一部分。
     * 这些表达式表示一系列"发生了什么"的表达式,它们告诉输出流如何产生与原始对象图一样的对象图。
     * <p>
     *  此方法的实现将产生第二个表达式,以在读取流时存在的环境中表示相同的表达式。这是通过在目标和所有参数上调用<code> writeObject </code>并使用结果构建一个新的表达式来实现的。
     * 
     * 
     * @param oldExp The expression to be written to the stream.
     */
    public void writeExpression(Expression oldExp) {
        // System.out.println("Encoder::writeExpression: " + oldExp);
        Object oldValue = getValue(oldExp);
        if (get(oldValue) != null) {
            return;
        }
        bindings.put(oldValue, (Expression)cloneStatement(oldExp));
        writeObject(oldValue);
    }

    void clear() {
        bindings.clear();
    }

    // Package private method for setting an attributes table for the encoder
    void setAttribute(Object key, Object value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    Object getAttribute(Object key) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }
}
