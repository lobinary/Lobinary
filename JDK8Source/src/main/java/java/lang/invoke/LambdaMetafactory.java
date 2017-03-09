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
import java.util.Arrays;

/**
 * <p>Methods to facilitate the creation of simple "function objects" that
 * implement one or more interfaces by delegation to a provided {@link MethodHandle},
 * possibly after type adaptation and partial evaluation of arguments.  These
 * methods are typically used as <em>bootstrap methods</em> for {@code invokedynamic}
 * call sites, to support the <em>lambda expression</em> and <em>method
 * reference expression</em> features of the Java Programming Language.
 *
 * <p>Indirect access to the behavior specified by the provided {@code MethodHandle}
 * proceeds in order through three phases:
 * <ul>
 *     <li><em>Linkage</em> occurs when the methods in this class are invoked.
 *     They take as arguments an interface to be implemented (typically a
 *     <em>functional interface</em>, one with a single abstract method), a
 *     name and signature of a method from that interface to be implemented, a
 *     method handle describing the desired implementation behavior
 *     for that method, and possibly other additional metadata, and produce a
 *     {@link CallSite} whose target can be used to create suitable function
 *     objects.  Linkage may involve dynamically loading a new class that
 *     implements the target interface. The {@code CallSite} can be considered a
 *     "factory" for function objects and so these linkage methods are referred
 *     to as "metafactories".</li>
 *
 *     <li><em>Capture</em> occurs when the {@code CallSite}'s target is
 *     invoked, typically through an {@code invokedynamic} call site,
 *     producing a function object.  This may occur many times for
 *     a single factory {@code CallSite}.  Capture may involve allocation of a
 *     new function object, or may return an existing function object.  The
 *     behavior {@code MethodHandle} may have additional parameters beyond those
 *     of the specified interface method; these are referred to as <em>captured
 *     parameters</em>, which must be provided as arguments to the
 *     {@code CallSite} target, and which may be early-bound to the behavior
 *     {@code MethodHandle}.  The number of captured parameters and their types
 *     are determined during linkage.</li>
 *
 *     <li><em>Invocation</em> occurs when an implemented interface method
 *     is invoked on a function object.  This may occur many times for a single
 *     function object.  The method referenced by the behavior {@code MethodHandle}
 *     is invoked with the captured arguments and any additional arguments
 *     provided on invocation, as if by {@link MethodHandle#invoke(Object...)}.</li>
 * </ul>
 *
 * <p>It is sometimes useful to restrict the set of inputs or results permitted
 * at invocation.  For example, when the generic interface {@code Predicate<T>}
 * is parameterized as {@code Predicate<String>}, the input must be a
 * {@code String}, even though the method to implement allows any {@code Object}.
 * At linkage time, an additional {@link MethodType} parameter describes the
 * "instantiated" method type; on invocation, the arguments and eventual result
 * are checked against this {@code MethodType}.
 *
 * <p>This class provides two forms of linkage methods: a standard version
 * ({@link #metafactory(MethodHandles.Lookup, String, MethodType, MethodType, MethodHandle, MethodType)})
 * using an optimized protocol, and an alternate version
 * {@link #altMetafactory(MethodHandles.Lookup, String, MethodType, Object...)}).
 * The alternate version is a generalization of the standard version, providing
 * additional control over the behavior of the generated function objects via
 * flags and additional arguments.  The alternate version adds the ability to
 * manage the following attributes of function objects:
 *
 * <ul>
 *     <li><em>Bridging.</em>  It is sometimes useful to implement multiple
 *     variations of the method signature, involving argument or return type
 *     adaptation.  This occurs when multiple distinct VM signatures for a method
 *     are logically considered to be the same method by the language.  The
 *     flag {@code FLAG_BRIDGES} indicates that a list of additional
 *     {@code MethodType}s will be provided, each of which will be implemented
 *     by the resulting function object.  These methods will share the same
 *     name and instantiated type.</li>
 *
 *     <li><em>Multiple interfaces.</em>  If needed, more than one interface
 *     can be implemented by the function object.  (These additional interfaces
 *     are typically marker interfaces with no methods.)  The flag {@code FLAG_MARKERS}
 *     indicates that a list of additional interfaces will be provided, each of
 *     which should be implemented by the resulting function object.</li>
 *
 *     <li><em>Serializability.</em>  The generated function objects do not
 *     generally support serialization.  If desired, {@code FLAG_SERIALIZABLE}
 *     can be used to indicate that the function objects should be serializable.
 *     Serializable function objects will use, as their serialized form,
 *     instances of the class {@code SerializedLambda}, which requires additional
 *     assistance from the capturing class (the class described by the
 *     {@link MethodHandles.Lookup} parameter {@code caller}); see
 *     {@link SerializedLambda} for details.</li>
 * </ul>
 *
 * <p>Assume the linkage arguments are as follows:
 * <ul>
 *      <li>{@code invokedType} (describing the {@code CallSite} signature) has
 *      K parameters of types (D1..Dk) and return type Rd;</li>
 *      <li>{@code samMethodType} (describing the implemented method type) has N
 *      parameters, of types (U1..Un) and return type Ru;</li>
 *      <li>{@code implMethod} (the {@code MethodHandle} providing the
 *      implementation has M parameters, of types (A1..Am) and return type Ra
 *      (if the method describes an instance method, the method type of this
 *      method handle already includes an extra first argument corresponding to
 *      the receiver);</li>
 *      <li>{@code instantiatedMethodType} (allowing restrictions on invocation)
 *      has N parameters, of types (T1..Tn) and return type Rt.</li>
 * </ul>
 *
 * <p>Then the following linkage invariants must hold:
 * <ul>
 *     <li>Rd is an interface</li>
 *     <li>{@code implMethod} is a <em>direct method handle</em></li>
 *     <li>{@code samMethodType} and {@code instantiatedMethodType} have the same
 *     arity N, and for i=1..N, Ti and Ui are the same type, or Ti and Ui are
 *     both reference types and Ti is a subtype of Ui</li>
 *     <li>Either Rt and Ru are the same type, or both are reference types and
 *     Rt is a subtype of Ru</li>
 *     <li>K + N = M</li>
 *     <li>For i=1..K, Di = Ai</li>
 *     <li>For i=1..N, Ti is adaptable to Aj, where j=i+k</li>
 *     <li>The return type Rt is void, or the return type Ra is not void and is
 *     adaptable to Rt</li>
 * </ul>
 *
 * <p>Further, at capture time, if {@code implMethod} corresponds to an instance
 * method, and there are any capture arguments ({@code K > 0}), then the first
 * capture argument (corresponding to the receiver) must be non-null.
 *
 * <p>A type Q is considered adaptable to S as follows:
 * <table summary="adaptable types">
 *     <tr><th>Q</th><th>S</th><th>Link-time checks</th><th>Invocation-time checks</th></tr>
 *     <tr>
 *         <td>Primitive</td><td>Primitive</td>
 *         <td>Q can be converted to S via a primitive widening conversion</td>
 *         <td>None</td>
 *     </tr>
 *     <tr>
 *         <td>Primitive</td><td>Reference</td>
 *         <td>S is a supertype of the Wrapper(Q)</td>
 *         <td>Cast from Wrapper(Q) to S</td>
 *     </tr>
 *     <tr>
 *         <td>Reference</td><td>Primitive</td>
 *         <td>for parameter types: Q is a primitive wrapper and Primitive(Q)
 *         can be widened to S
 *         <br>for return types: If Q is a primitive wrapper, check that
 *         Primitive(Q) can be widened to S</td>
 *         <td>If Q is not a primitive wrapper, cast Q to the base Wrapper(S);
 *         for example Number for numeric types</td>
 *     </tr>
 *     <tr>
 *         <td>Reference</td><td>Reference</td>
 *         <td>for parameter types: S is a supertype of Q
 *         <br>for return types: none</td>
 *         <td>Cast from Q to S</td>
 *     </tr>
 * </table>
 *
 * @apiNote These linkage methods are designed to support the evaluation
 * of <em>lambda expressions</em> and <em>method references</em> in the Java
 * Language.  For every lambda expressions or method reference in the source code,
 * there is a target type which is a functional interface.  Evaluating a lambda
 * expression produces an object of its target type. The recommended mechanism
 * for evaluating lambda expressions is to desugar the lambda body to a method,
 * invoke an invokedynamic call site whose static argument list describes the
 * sole method of the functional interface and the desugared implementation
 * method, and returns an object (the lambda object) that implements the target
 * type. (For method references, the implementation method is simply the
 * referenced method; no desugaring is needed.)
 *
 * <p>The argument list of the implementation method and the argument list of
 * the interface method(s) may differ in several ways.  The implementation
 * methods may have additional arguments to accommodate arguments captured by
 * the lambda expression; there may also be differences resulting from permitted
 * adaptations of arguments, such as casting, boxing, unboxing, and primitive
 * widening. (Varargs adaptations are not handled by the metafactories; these are
 * expected to be handled by the caller.)
 *
 * <p>Invokedynamic call sites have two argument lists: a static argument list
 * and a dynamic argument list.  The static argument list is stored in the
 * constant pool; the dynamic argument is pushed on the operand stack at capture
 * time.  The bootstrap method has access to the entire static argument list
 * (which in this case, includes information describing the implementation method,
 * the target interface, and the target interface method(s)), as well as a
 * method signature describing the number and static types (but not the values)
 * of the dynamic arguments and the static return type of the invokedynamic site.
 *
 * @implNote The implementation method is described with a method handle. In
 * theory, any method handle could be used. Currently supported are direct method
 * handles representing invocation of virtual, interface, constructor and static
 * methods.
 * <p>
 *  <p>可能在类型适配和参数的部分评估之后,通过委派给所提供的{@link MethodHandle}来实现一个或多个接口的简单"函数对象"的创建的方法。
 * 这些方法通常用作{@code invokedynamic}调用网站的<em> bootstrap方法</em>,以支持<em>方法引用表达式</em>的<em> Java编程语言。
 * 
 *  <p>对提供的{@code MethodHandle}指定的行为的间接访问按顺序进行三个阶段：
 * <ul>
 *  <li>当调用此类中的方法时,会发生链接</em>。
 * 它们将要实现的接口(通常是功能接口,一个具有单个抽象方法),来自要实现的接口的方法的名称和签名,描述期望的接口的方法句柄该方法的实现行为,以及可能的其他附加元数据,并产生{@link CallSite}
 * ,其目标可以用于创建合适的函数对象。
 *  <li>当调用此类中的方法时,会发生链接</em>。链接可以涉及动态加载实现目标接口的新类。 {@code CallSite}可以被认为是函数对象的"工厂",因此这些链接方法被称为"元产品"。
 * </li>。
 * 
 * 当调用{@code CallSite}的目标(通常是通过{@code invokedynamic})调用网站生成函数对象时,会发生<li>捕获</em>。
 * 对于单个工厂{@code CallSite},这可能会出现很多次。捕获可以涉及新功能对象的分配,或者可以返回现有功能对象。
 * 行为{@code MethodHandle}可能具有超出指定接口方法的参数的附加参数;这些被称为<em>捕获的参数</em>,它必须作为{@code CallSite}目标的参数提供,并且可能早于行为{@code MethodHandle}
 * 。
 * 对于单个工厂{@code CallSite},这可能会出现很多次。捕获可以涉及新功能对象的分配,或者可以返回现有功能对象。在链接期间确定捕获的参数的数量及其类型。</li>。
 * 
 *  <li>当在函数对象上调用实现的接口方法时,会发生调用</em>。对于单个函数对象,这可能发生多次。
 * 由行为{@code MethodHandle}引用的方法将使用捕获的参数和调用时提供的任何其他参数(如通过{@link MethodHandle#invoke(Object ...)})调用。
 * </li>。
 * </ul>
 * 
 * <p>有时,限制调用时允许的输入或结果集是有用的。
 * 例如,当通用接口{@code Predicate <T>}被参数化为{@code Predicate <String>}时,输入必须是{@code String},即使实现的方法允许任何{@code Object }
 * 。
 * <p>有时,限制调用时允许的输入或结果集是有用的。
 * 在链接时,额外的{@link MethodType}参数描述"实例化"方法类型;在调用时,根据此{@code MethodType}检查参数和最终结果。
 * 
 *  <p>此类提供两种形式的链接方法：标准版本({@link #metafactory(MethodHandles.Lookup,String,MethodType,MethodType,MethodHandle,MethodType)}
 * )使用优化的协议, #altMetafactory(MethodHandles.Lookup,String,MethodType,Object ...)})。
 * 备用版本是标准版本的泛化,通过标志和附加参数提供对生成的函数对象的行为的附加控制。备用版本增加了管理函数对象的以下属性的能力：。
 * 
 * <ul>
 * <li> </em>实施方法签名的多个变体有时很有用,涉及参数或返回类型自适应。这发生在方法的多个不同的VM签名在逻辑上被语言视为同一方法时。
 * 标志{@code FLAG_BRIDGES}表示将提供额外的{@code MethodType}列表,每个将由生成的函数对象实现。这些方法将共享相同的名称和实例化的类型。</li>。
 * 
 *  <li> <em>多个接口。</em>如果需要,可以通过函数对象实现多个接口。 (这些附加接口通常是没有方法的标记接口。
 * )标志{@code FLAG_MARKERS}表示将提供一个附加接口列表,每个接口都应该由结果函数对象实现。</li>。
 * 
 *  <li> <em>可串行化。</em>生成的函数对象通常不支持序列化。如果需要,可以使用{@code FLAG_SERIALIZABLE}来表示函数对象应该是可序列化的。
 * 可串行化函数对象将使用类{@code SerializedLambda}的实例作为它们的序列化形式,这需要捕获类(由{@link MethodHandles.Lookup}参数{@code caller}
 * 描述的类)的额外帮助;有关详情,请参阅{@link SerializedLambda}。
 *  <li> <em>可串行化。</em>生成的函数对象通常不支持序列化。如果需要,可以使用{@code FLAG_SERIALIZABLE}来表示函数对象应该是可序列化的。</li>。
 * </ul>
 * 
 *  <p>假设连接参数如下：
 * <ul>
 * <li> {@ code invokedType}(描述{@code CallSite}签名)有K个参数类型(D1..Dk)和返回类型Rd; </li> <li> {@ code samMethodType}
 * 方法类型)具有N个参数,类型(U1..Un)和返回类型Ru; </li> <li> {@ code implMethod}({@code MethodHandle} ..Am)和返回类型Ra(如果方法描
 * 述了一个实例方法,这个方法的方法类型已经包括了对应接收者的额外的第一个参数); </li> <li> {@ code instantiatedMethodType}对调用的限制)具有N个参数,类型(T1
 * ..Tn)和返回类型Rt。
 * </li>。
 * </ul>
 * 
 *  <p>那么以下链接不变量必须保持：
 * <ul>
 *  <li> Rd是一个接口</li> <li> {@ code implMethod}是一个<em>直接方法句柄</em> </li> <li> {@ code samMethodType}和{@code instantiatedMethodType}
 * 对于i = 1..N,Ti和Ui是相同的类型,或者Ti和Ui都是参考类型,Ti是Ui的子类型</li> <li> Rt和Ru是相同类型或两者是参考类型,并且Rt是Ru的子类型</li> <li> K +
 *  N = M </li> <li>对于i = 1..K,Di = Ai </li>对于i = 1..N,Ti适用于Aj,其中j = i + k </li> <li>返回类型Rt是void或者返回类型Ra
 * 不是void并且适用于Rt < / li>。
 * </ul>
 * 
 * <p>此外,在捕获时,如果{@code implMethod}对应于实例方法,并且有任何捕获参数({@code K> 0}),则第一个捕获参数(对应于接收者)必须非空。
 * 
 *  <p>类型Q被认为适用于S如下：
 * <table summary="adaptable types">
 *  <tr> <th> <th> </th> </th> </p> </p> </p>
 * <tr>
 *  <td>原始</td> <td>原始</td> <td> Q可以通过原始扩展转换转换为S </td> <td>无</td>
 * </tr>
 * <tr>
 *  <td>参考</td> <td> S是封装(Q)</td> <td>从封装(Q)转换为S </td>的超类型
 * </tr>
 * <tr>
 *  <td>参考</td> <td>原语</td> <td>对于参数类型：Q是原语包装器,原语(Q)可以扩展为S <br>用于返回类型：如果Q是原语包装器,检查原语(Q)可以加宽到S </td> <td>
 * 如果Q不是原始包装器,则将Q转换到基本包装器(S);例如数字类型的数字</td>。
 * </tr>
 * <tr>
 *  <td>参考</td> <td>参数</td> <td>参数类型：S是返回类型的Q <br>的超类型：none </td> <td> / td>
 * </tr>
 * </table>
 * 
 * @apiNote这些链接方法旨在支持在Java语言中评估<em> lambda表达式</em>和<em>方法引用</em>。
 * 对于源代码中的每个lambda表达式或方法引用,都有一个目标类型,它是一个功能接口。评估lambda表达式会生成其目标类型的对象。
 */
public class LambdaMetafactory {

    /** Flag for alternate metafactories indicating the lambda object
    /* <p>
    /* 推荐用于评估lambda表达式的机制是将lambda主体解析为方法,调用调用的动态调用站点,其静态参数列表描述函数接口的唯一方法和desugared实现方法,并返回一个对象(lambda对象)实现目标类
    /* 型。
    /* 对于源代码中的每个lambda表达式或方法引用,都有一个目标类型,它是一个功能接口。评估lambda表达式会生成其目标类型的对象。 (对于方法引用,实现方法只是引用的方法;不需要desugaring。
    /* )。
    /* 
    /*  <p>实现方法的参数列表和接口方法的参数列表可以在几种方式上不同。
    /* 实现方法可以具有附加的参数以适应由lambda表达式捕获的参数;也可能存在由于参数的允许适配引起的差异,例如投射,装箱,拆箱和原始扩宽。
    /*  (Varargs修改不由元处理器处理;这些修改预期由调用者处理。)。
    /* 
    /* <p> Invokedynamic调用网站有两个参数列表：静态参数列表和动态参数列表。静态参数列表存储在常量池中;动态参数在捕获时被推送到操作数栈上。
    /* 引导方法可以访问整个静态参数列表(在这种情况下,包括描述实现方法,目标接口和目标接口方法的信息),以及描述数量和静态类型的方法签名(但不是值)的动态参数和调用动态站点的静态返回类型。
    /* 
    /*  @implNote使用方法句柄描述实现方法。理论上,可以使用任何方法句柄。当前支持的是表示调用虚拟,接口,构造函数和静态方法的直接方法句柄。
    /* 
    /* 
     * must be serializable */
    public static final int FLAG_SERIALIZABLE = 1 << 0;

    /**
     * Flag for alternate metafactories indicating the lambda object implements
     * other marker interfaces
     * besides Serializable
     * <p>
     */
    public static final int FLAG_MARKERS = 1 << 1;

    /**
     * Flag for alternate metafactories indicating the lambda object requires
     * additional bridge methods
     * <p>
     *  用于指示lambda对象的备用元攻击的标志实现除了Serializable之外的其他标记接口
     * 
     */
    public static final int FLAG_BRIDGES = 1 << 2;

    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final MethodType[] EMPTY_MT_ARRAY = new MethodType[0];

    /**
     * Facilitates the creation of simple "function objects" that implement one
     * or more interfaces by delegation to a provided {@link MethodHandle},
     * after appropriate type adaptation and partial evaluation of arguments.
     * Typically used as a <em>bootstrap method</em> for {@code invokedynamic}
     * call sites, to support the <em>lambda expression</em> and <em>method
     * reference expression</em> features of the Java Programming Language.
     *
     * <p>This is the standard, streamlined metafactory; additional flexibility
     * is provided by {@link #altMetafactory(MethodHandles.Lookup, String, MethodType, Object...)}.
     * A general description of the behavior of this method is provided
     * {@link LambdaMetafactory above}.
     *
     * <p>When the target of the {@code CallSite} returned from this method is
     * invoked, the resulting function objects are instances of a class which
     * implements the interface named by the return type of {@code invokedType},
     * declares a method with the name given by {@code invokedName} and the
     * signature given by {@code samMethodType}.  It may also override additional
     * methods from {@code Object}.
     *
     * <p>
     *  用于指示lambda对象的替代元攻击的标志需要额外的桥接方法
     * 
     * 
     * @param caller Represents a lookup context with the accessibility
     *               privileges of the caller.  When used with {@code invokedynamic},
     *               this is stacked automatically by the VM.
     * @param invokedName The name of the method to implement.  When used with
     *                    {@code invokedynamic}, this is provided by the
     *                    {@code NameAndType} of the {@code InvokeDynamic}
     *                    structure and is stacked automatically by the VM.
     * @param invokedType The expected signature of the {@code CallSite}.  The
     *                    parameter types represent the types of capture variables;
     *                    the return type is the interface to implement.   When
     *                    used with {@code invokedynamic}, this is provided by
     *                    the {@code NameAndType} of the {@code InvokeDynamic}
     *                    structure and is stacked automatically by the VM.
     *                    In the event that the implementation method is an
     *                    instance method and this signature has any parameters,
     *                    the first parameter in the invocation signature must
     *                    correspond to the receiver.
     * @param samMethodType Signature and return type of method to be implemented
     *                      by the function object.
     * @param implMethod A direct method handle describing the implementation
     *                   method which should be called (with suitable adaptation
     *                   of argument types, return types, and with captured
     *                   arguments prepended to the invocation arguments) at
     *                   invocation time.
     * @param instantiatedMethodType The signature and return type that should
     *                               be enforced dynamically at invocation time.
     *                               This may be the same as {@code samMethodType},
     *                               or may be a specialization of it.
     * @return a CallSite whose target can be used to perform capture, generating
     *         instances of the interface named by {@code invokedType}
     * @throws LambdaConversionException If any of the linkage invariants
     *                                   described {@link LambdaMetafactory above}
     *                                   are violated
     */
    public static CallSite metafactory(MethodHandles.Lookup caller,
                                       String invokedName,
                                       MethodType invokedType,
                                       MethodType samMethodType,
                                       MethodHandle implMethod,
                                       MethodType instantiatedMethodType)
            throws LambdaConversionException {
        AbstractValidatingLambdaMetafactory mf;
        mf = new InnerClassLambdaMetafactory(caller, invokedType,
                                             invokedName, samMethodType,
                                             implMethod, instantiatedMethodType,
                                             false, EMPTY_CLASS_ARRAY, EMPTY_MT_ARRAY);
        mf.validateMetafactoryArgs();
        return mf.buildCallSite();
    }

    /**
     * Facilitates the creation of simple "function objects" that implement one
     * or more interfaces by delegation to a provided {@link MethodHandle},
     * after appropriate type adaptation and partial evaluation of arguments.
     * Typically used as a <em>bootstrap method</em> for {@code invokedynamic}
     * call sites, to support the <em>lambda expression</em> and <em>method
     * reference expression</em> features of the Java Programming Language.
     *
     * <p>This is the general, more flexible metafactory; a streamlined version
     * is provided by {@link #metafactory(java.lang.invoke.MethodHandles.Lookup,
     * String, MethodType, MethodType, MethodHandle, MethodType)}.
     * A general description of the behavior of this method is provided
     * {@link LambdaMetafactory above}.
     *
     * <p>The argument list for this method includes three fixed parameters,
     * corresponding to the parameters automatically stacked by the VM for the
     * bootstrap method in an {@code invokedynamic} invocation, and an {@code Object[]}
     * parameter that contains additional parameters.  The declared argument
     * list for this method is:
     *
     * <pre>{@code
     *  CallSite altMetafactory(MethodHandles.Lookup caller,
     *                          String invokedName,
     *                          MethodType invokedType,
     *                          Object... args)
     * }</pre>
     *
     * <p>but it behaves as if the argument list is as follows:
     *
     * <pre>{@code
     *  CallSite altMetafactory(MethodHandles.Lookup caller,
     *                          String invokedName,
     *                          MethodType invokedType,
     *                          MethodType samMethodType,
     *                          MethodHandle implMethod,
     *                          MethodType instantiatedMethodType,
     *                          int flags,
     *                          int markerInterfaceCount,  // IF flags has MARKERS set
     *                          Class... markerInterfaces, // IF flags has MARKERS set
     *                          int bridgeCount,           // IF flags has BRIDGES set
     *                          MethodType... bridges      // IF flags has BRIDGES set
     *                          )
     * }</pre>
     *
     * <p>Arguments that appear in the argument list for
     * {@link #metafactory(MethodHandles.Lookup, String, MethodType, MethodType, MethodHandle, MethodType)}
     * have the same specification as in that method.  The additional arguments
     * are interpreted as follows:
     * <ul>
     *     <li>{@code flags} indicates additional options; this is a bitwise
     *     OR of desired flags.  Defined flags are {@link #FLAG_BRIDGES},
     *     {@link #FLAG_MARKERS}, and {@link #FLAG_SERIALIZABLE}.</li>
     *     <li>{@code markerInterfaceCount} is the number of additional interfaces
     *     the function object should implement, and is present if and only if the
     *     {@code FLAG_MARKERS} flag is set.</li>
     *     <li>{@code markerInterfaces} is a variable-length list of additional
     *     interfaces to implement, whose length equals {@code markerInterfaceCount},
     *     and is present if and only if the {@code FLAG_MARKERS} flag is set.</li>
     *     <li>{@code bridgeCount} is the number of additional method signatures
     *     the function object should implement, and is present if and only if
     *     the {@code FLAG_BRIDGES} flag is set.</li>
     *     <li>{@code bridges} is a variable-length list of additional
     *     methods signatures to implement, whose length equals {@code bridgeCount},
     *     and is present if and only if the {@code FLAG_BRIDGES} flag is set.</li>
     * </ul>
     *
     * <p>Each class named by {@code markerInterfaces} is subject to the same
     * restrictions as {@code Rd}, the return type of {@code invokedType},
     * as described {@link LambdaMetafactory above}.  Each {@code MethodType}
     * named by {@code bridges} is subject to the same restrictions as
     * {@code samMethodType}, as described {@link LambdaMetafactory above}.
     *
     * <p>When FLAG_SERIALIZABLE is set in {@code flags}, the function objects
     * will implement {@code Serializable}, and will have a {@code writeReplace}
     * method that returns an appropriate {@link SerializedLambda}.  The
     * {@code caller} class must have an appropriate {@code $deserializeLambda$}
     * method, as described in {@link SerializedLambda}.
     *
     * <p>When the target of the {@code CallSite} returned from this method is
     * invoked, the resulting function objects are instances of a class with
     * the following properties:
     * <ul>
     *     <li>The class implements the interface named by the return type
     *     of {@code invokedType} and any interfaces named by {@code markerInterfaces}</li>
     *     <li>The class declares methods with the name given by {@code invokedName},
     *     and the signature given by {@code samMethodType} and additional signatures
     *     given by {@code bridges}</li>
     *     <li>The class may override methods from {@code Object}, and may
     *     implement methods related to serialization.</li>
     * </ul>
     *
     * <p>
     *  通过委派到提供的{@link MethodHandle},在适当的类型适配和参数的部分评估之后,便于创建实现一个或多个接口的简单"函数对象"。
     * 通常用作{@code invokedynamic}调用网站的<em> bootstrap方法</em>,以支持Java编程的<em> lambda表达式和<em>方法引用表达式</em>语言。
     * 
     * <p>这是标准的,流线型的诠释;额外的灵活性由{@link #altMetafactory(MethodHandles.Lookup,String,MethodType,Object ...)}提供。
     * 提供此方法的行为的一般描述{@link LambdaMetafactory above}。
     * 
     *  <p>当调用从此方法返回的{@code CallSite}的目标时,生成的函数对象是实现由{@code invokedType}的返回类型命名的接口的类的实例,声明一个方法名称由{@code invokedName}
     * 给出,而签名由{@code samMethodType}给出。
     * 它还可以覆盖{@code Object}中的其他方法。
     * 
     * 
     * @param caller Represents a lookup context with the accessibility
     *               privileges of the caller.  When used with {@code invokedynamic},
     *               this is stacked automatically by the VM.
     * @param invokedName The name of the method to implement.  When used with
     *                    {@code invokedynamic}, this is provided by the
     *                    {@code NameAndType} of the {@code InvokeDynamic}
     *                    structure and is stacked automatically by the VM.
     * @param invokedType The expected signature of the {@code CallSite}.  The
     *                    parameter types represent the types of capture variables;
     *                    the return type is the interface to implement.   When
     *                    used with {@code invokedynamic}, this is provided by
     *                    the {@code NameAndType} of the {@code InvokeDynamic}
     *                    structure and is stacked automatically by the VM.
     *                    In the event that the implementation method is an
     *                    instance method and this signature has any parameters,
     *                    the first parameter in the invocation signature must
     *                    correspond to the receiver.
     * @param  args       An {@code Object[]} array containing the required
     *                    arguments {@code samMethodType}, {@code implMethod},
     *                    {@code instantiatedMethodType}, {@code flags}, and any
     *                    optional arguments, as described
     *                    {@link #altMetafactory(MethodHandles.Lookup, String, MethodType, Object...)} above}
     * @return a CallSite whose target can be used to perform capture, generating
     *         instances of the interface named by {@code invokedType}
     * @throws LambdaConversionException If any of the linkage invariants
     *                                   described {@link LambdaMetafactory above}
     *                                   are violated
     */
    public static CallSite altMetafactory(MethodHandles.Lookup caller,
                                          String invokedName,
                                          MethodType invokedType,
                                          Object... args)
            throws LambdaConversionException {
        MethodType samMethodType = (MethodType)args[0];
        MethodHandle implMethod = (MethodHandle)args[1];
        MethodType instantiatedMethodType = (MethodType)args[2];
        int flags = (Integer) args[3];
        Class<?>[] markerInterfaces;
        MethodType[] bridges;
        int argIndex = 4;
        if ((flags & FLAG_MARKERS) != 0) {
            int markerCount = (Integer) args[argIndex++];
            markerInterfaces = new Class<?>[markerCount];
            System.arraycopy(args, argIndex, markerInterfaces, 0, markerCount);
            argIndex += markerCount;
        }
        else
            markerInterfaces = EMPTY_CLASS_ARRAY;
        if ((flags & FLAG_BRIDGES) != 0) {
            int bridgeCount = (Integer) args[argIndex++];
            bridges = new MethodType[bridgeCount];
            System.arraycopy(args, argIndex, bridges, 0, bridgeCount);
            argIndex += bridgeCount;
        }
        else
            bridges = EMPTY_MT_ARRAY;

        boolean isSerializable = ((flags & FLAG_SERIALIZABLE) != 0);
        if (isSerializable) {
            boolean foundSerializableSupertype = Serializable.class.isAssignableFrom(invokedType.returnType());
            for (Class<?> c : markerInterfaces)
                foundSerializableSupertype |= Serializable.class.isAssignableFrom(c);
            if (!foundSerializableSupertype) {
                markerInterfaces = Arrays.copyOf(markerInterfaces, markerInterfaces.length + 1);
                markerInterfaces[markerInterfaces.length-1] = Serializable.class;
            }
        }

        AbstractValidatingLambdaMetafactory mf
                = new InnerClassLambdaMetafactory(caller, invokedType,
                                                  invokedName, samMethodType,
                                                  implMethod,
                                                  instantiatedMethodType,
                                                  isSerializable,
                                                  markerInterfaces, bridges);
        mf.validateMetafactoryArgs();
        return mf.buildCallSite();
    }
}
