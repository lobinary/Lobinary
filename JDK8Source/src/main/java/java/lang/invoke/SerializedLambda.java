/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.lang.invoke;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Objects;

/**
 * Serialized form of a lambda expression.  The properties of this class
 * represent the information that is present at the lambda factory site, including
 * static metafactory arguments such as the identity of the primary functional
 * interface method and the identity of the implementation method, as well as
 * dynamic metafactory arguments such as values captured from the lexical scope
 * at the time of lambda capture.
 *
 * <p>Implementors of serializable lambdas, such as compilers or language
 * runtime libraries, are expected to ensure that instances deserialize properly.
 * One means to do so is to ensure that the {@code writeReplace} method returns
 * an instance of {@code SerializedLambda}, rather than allowing default
 * serialization to proceed.
 *
 * <p>{@code SerializedLambda} has a {@code readResolve} method that looks for
 * a (possibly private) static method called
 * {@code $deserializeLambda$(SerializedLambda)} in the capturing class, invokes
 * that with itself as the first argument, and returns the result.  Lambda classes
 * implementing {@code $deserializeLambda$} are responsible for validating
 * that the properties of the {@code SerializedLambda} are consistent with a
 * lambda actually captured by that class.
 *
 * <p>
 *  lambda表达式的序列化形式。此类的属性表示存在于lambda工厂站点的信息,包括静态元篡改参数,例如主函数接口方法的身份和实现方法的标识,以及动态元篡改参数,例如从在λ捕获时的词汇作用域。
 * 
 *  <p>可序列化lambdas的实现者,如编译器或语言运行时库,应该确保实例反序列化正确。
 * 这样做的一个方法是确保{@code writeReplace}方法返回{@code SerializedLambda}的实例,而不是允许默认序列化继续进行。
 * 
 *  <p> {@ code SerializedLambda}有一个{@code readResolve}方法,用于在捕获类中查找一个名为{@code $ deserializeLambda $(SerializedLambda)}
 * 的静态方法(可能是私有的),使用自身作为第一个参数,并返回结果。
 * 实现{@code $ deserializeLambda $}的Lambda类负责验证{@code SerializedLambda}的属性是否与该类实际捕获的lambda一致。
 * 
 * 
 * @see LambdaMetafactory
 */
public final class SerializedLambda implements Serializable {
    private static final long serialVersionUID = 8025925345765570181L;
    private final Class<?> capturingClass;
    private final String functionalInterfaceClass;
    private final String functionalInterfaceMethodName;
    private final String functionalInterfaceMethodSignature;
    private final String implClass;
    private final String implMethodName;
    private final String implMethodSignature;
    private final int implMethodKind;
    private final String instantiatedMethodType;
    private final Object[] capturedArgs;

    /**
     * Create a {@code SerializedLambda} from the low-level information present
     * at the lambda factory site.
     *
     * <p>
     *  从lambda工厂站点的低级信息创建一个{@code SerializedLambda}。
     * 
     * 
     * @param capturingClass The class in which the lambda expression appears
     * @param functionalInterfaceClass Name, in slash-delimited form, of static
     *                                 type of the returned lambda object
     * @param functionalInterfaceMethodName Name of the functional interface
     *                                      method for the present at the
     *                                      lambda factory site
     * @param functionalInterfaceMethodSignature Signature of the functional
     *                                           interface method present at
     *                                           the lambda factory site
     * @param implMethodKind Method handle kind for the implementation method
     * @param implClass Name, in slash-delimited form, for the class holding
     *                  the implementation method
     * @param implMethodName Name of the implementation method
     * @param implMethodSignature Signature of the implementation method
     * @param instantiatedMethodType The signature of the primary functional
     *                               interface method after type variables
     *                               are substituted with their instantiation
     *                               from the capture site
     * @param capturedArgs The dynamic arguments to the lambda factory site,
     *                     which represent variables captured by
     *                     the lambda
     */
    public SerializedLambda(Class<?> capturingClass,
                            String functionalInterfaceClass,
                            String functionalInterfaceMethodName,
                            String functionalInterfaceMethodSignature,
                            int implMethodKind,
                            String implClass,
                            String implMethodName,
                            String implMethodSignature,
                            String instantiatedMethodType,
                            Object[] capturedArgs) {
        this.capturingClass = capturingClass;
        this.functionalInterfaceClass = functionalInterfaceClass;
        this.functionalInterfaceMethodName = functionalInterfaceMethodName;
        this.functionalInterfaceMethodSignature = functionalInterfaceMethodSignature;
        this.implMethodKind = implMethodKind;
        this.implClass = implClass;
        this.implMethodName = implMethodName;
        this.implMethodSignature = implMethodSignature;
        this.instantiatedMethodType = instantiatedMethodType;
        this.capturedArgs = Objects.requireNonNull(capturedArgs).clone();
    }

    /**
     * Get the name of the class that captured this lambda.
     * <p>
     *  获取捕获此lambda的类的名称。
     * 
     * 
     * @return the name of the class that captured this lambda
     */
    public String getCapturingClass() {
        return capturingClass.getName().replace('.', '/');
    }

    /**
     * Get the name of the invoked type to which this
     * lambda has been converted
     * <p>
     * 获取此lambda已转换到的调用类型的名称
     * 
     * 
     * @return the name of the functional interface class to which
     * this lambda has been converted
     */
    public String getFunctionalInterfaceClass() {
        return functionalInterfaceClass;
    }

    /**
     * Get the name of the primary method for the functional interface
     * to which this lambda has been converted.
     * <p>
     *  获取该lambda已转换到的函数接口的主方法的名称。
     * 
     * 
     * @return the name of the primary methods of the functional interface
     */
    public String getFunctionalInterfaceMethodName() {
        return functionalInterfaceMethodName;
    }

    /**
     * Get the signature of the primary method for the functional
     * interface to which this lambda has been converted.
     * <p>
     *  获取已转换此lambda的功能接口的主方法的签名。
     * 
     * 
     * @return the signature of the primary method of the functional
     * interface
     */
    public String getFunctionalInterfaceMethodSignature() {
        return functionalInterfaceMethodSignature;
    }

    /**
     * Get the name of the class containing the implementation
     * method.
     * <p>
     *  获取包含实现方法的类的名称。
     * 
     * 
     * @return the name of the class containing the implementation
     * method
     */
    public String getImplClass() {
        return implClass;
    }

    /**
     * Get the name of the implementation method.
     * <p>
     *  获取实现方法的名称。
     * 
     * 
     * @return the name of the implementation method
     */
    public String getImplMethodName() {
        return implMethodName;
    }

    /**
     * Get the signature of the implementation method.
     * <p>
     *  获取实现方法的签名。
     * 
     * 
     * @return the signature of the implementation method
     */
    public String getImplMethodSignature() {
        return implMethodSignature;
    }

    /**
     * Get the method handle kind (see {@link MethodHandleInfo}) of
     * the implementation method.
     * <p>
     *  获取实现方法的方法处理类型(参见{@link MethodHandleInfo})。
     * 
     * 
     * @return the method handle kind of the implementation method
     */
    public int getImplMethodKind() {
        return implMethodKind;
    }

    /**
     * Get the signature of the primary functional interface method
     * after type variables are substituted with their instantiation
     * from the capture site.
     * <p>
     *  在从捕获站点用其实例化替换类型变量后,获取主函数接口方法的签名。
     * 
     * 
     * @return the signature of the primary functional interface method
     * after type variable processing
     */
    public final String getInstantiatedMethodType() {
        return instantiatedMethodType;
    }

    /**
     * Get the count of dynamic arguments to the lambda capture site.
     * <p>
     *  获取lambda捕获站点的动态参数的计数。
     * 
     * 
     * @return the count of dynamic arguments to the lambda capture site
     */
    public int getCapturedArgCount() {
        return capturedArgs.length;
    }

    /**
     * Get a dynamic argument to the lambda capture site.
     * <p>
     *  获取lambda捕获站点的动态参数。
     * 
     * @param i the argument to capture
     * @return a dynamic argument to the lambda capture site
     */
    public Object getCapturedArg(int i) {
        return capturedArgs[i];
    }

    private Object readResolve() throws ReflectiveOperationException {
        try {
            Method deserialize = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
                @Override
                public Method run() throws Exception {
                    Method m = capturingClass.getDeclaredMethod("$deserializeLambda$", SerializedLambda.class);
                    m.setAccessible(true);
                    return m;
                }
            });

            return deserialize.invoke(null, this);
        }
        catch (PrivilegedActionException e) {
            Exception cause = e.getException();
            if (cause instanceof ReflectiveOperationException)
                throw (ReflectiveOperationException) cause;
            else if (cause instanceof RuntimeException)
                throw (RuntimeException) cause;
            else
                throw new RuntimeException("Exception in SerializedLambda.readResolve", e);
        }
    }

    @Override
    public String toString() {
        String implKind=MethodHandleInfo.referenceKindToString(implMethodKind);
        return String.format("SerializedLambda[%s=%s, %s=%s.%s:%s, " +
                             "%s=%s %s.%s:%s, %s=%s, %s=%d]",
                             "capturingClass", capturingClass,
                             "functionalInterfaceMethod", functionalInterfaceClass,
                               functionalInterfaceMethodName,
                               functionalInterfaceMethodSignature,
                             "implementation",
                               implKind,
                               implClass, implMethodName, implMethodSignature,
                             "instantiatedMethodType", instantiatedMethodType,
                             "numCaptured", capturedArgs.length);
    }
}
