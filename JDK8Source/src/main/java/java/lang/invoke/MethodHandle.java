/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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


import java.util.*;

import static java.lang.invoke.MethodHandleStatics.*;

/**
 * A method handle is a typed, directly executable reference to an underlying method,
 * constructor, field, or similar low-level operation, with optional
 * transformations of arguments or return values.
 * These transformations are quite general, and include such patterns as
 * {@linkplain #asType conversion},
 * {@linkplain #bindTo insertion},
 * {@linkplain java.lang.invoke.MethodHandles#dropArguments deletion},
 * and {@linkplain java.lang.invoke.MethodHandles#filterArguments substitution}.
 *
 * <h1>Method handle contents</h1>
 * Method handles are dynamically and strongly typed according to their parameter and return types.
 * They are not distinguished by the name or the defining class of their underlying methods.
 * A method handle must be invoked using a symbolic type descriptor which matches
 * the method handle's own {@linkplain #type type descriptor}.
 * <p>
 * Every method handle reports its type descriptor via the {@link #type type} accessor.
 * This type descriptor is a {@link java.lang.invoke.MethodType MethodType} object,
 * whose structure is a series of classes, one of which is
 * the return type of the method (or {@code void.class} if none).
 * <p>
 * A method handle's type controls the types of invocations it accepts,
 * and the kinds of transformations that apply to it.
 * <p>
 * A method handle contains a pair of special invoker methods
 * called {@link #invokeExact invokeExact} and {@link #invoke invoke}.
 * Both invoker methods provide direct access to the method handle's
 * underlying method, constructor, field, or other operation,
 * as modified by transformations of arguments and return values.
 * Both invokers accept calls which exactly match the method handle's own type.
 * The plain, inexact invoker also accepts a range of other call types.
 * <p>
 * Method handles are immutable and have no visible state.
 * Of course, they can be bound to underlying methods or data which exhibit state.
 * With respect to the Java Memory Model, any method handle will behave
 * as if all of its (internal) fields are final variables.  This means that any method
 * handle made visible to the application will always be fully formed.
 * This is true even if the method handle is published through a shared
 * variable in a data race.
 * <p>
 * Method handles cannot be subclassed by the user.
 * Implementations may (or may not) create internal subclasses of {@code MethodHandle}
 * which may be visible via the {@link java.lang.Object#getClass Object.getClass}
 * operation.  The programmer should not draw conclusions about a method handle
 * from its specific class, as the method handle class hierarchy (if any)
 * may change from time to time or across implementations from different vendors.
 *
 * <h1>Method handle compilation</h1>
 * A Java method call expression naming {@code invokeExact} or {@code invoke}
 * can invoke a method handle from Java source code.
 * From the viewpoint of source code, these methods can take any arguments
 * and their result can be cast to any return type.
 * Formally this is accomplished by giving the invoker methods
 * {@code Object} return types and variable arity {@code Object} arguments,
 * but they have an additional quality called <em>signature polymorphism</em>
 * which connects this freedom of invocation directly to the JVM execution stack.
 * <p>
 * As is usual with virtual methods, source-level calls to {@code invokeExact}
 * and {@code invoke} compile to an {@code invokevirtual} instruction.
 * More unusually, the compiler must record the actual argument types,
 * and may not perform method invocation conversions on the arguments.
 * Instead, it must push them on the stack according to their own unconverted types.
 * The method handle object itself is pushed on the stack before the arguments.
 * The compiler then calls the method handle with a symbolic type descriptor which
 * describes the argument and return types.
 * <p>
 * To issue a complete symbolic type descriptor, the compiler must also determine
 * the return type.  This is based on a cast on the method invocation expression,
 * if there is one, or else {@code Object} if the invocation is an expression
 * or else {@code void} if the invocation is a statement.
 * The cast may be to a primitive type (but not {@code void}).
 * <p>
 * As a corner case, an uncasted {@code null} argument is given
 * a symbolic type descriptor of {@code java.lang.Void}.
 * The ambiguity with the type {@code Void} is harmless, since there are no references of type
 * {@code Void} except the null reference.
 *
 * <h1>Method handle invocation</h1>
 * The first time a {@code invokevirtual} instruction is executed
 * it is linked, by symbolically resolving the names in the instruction
 * and verifying that the method call is statically legal.
 * This is true of calls to {@code invokeExact} and {@code invoke}.
 * In this case, the symbolic type descriptor emitted by the compiler is checked for
 * correct syntax and names it contains are resolved.
 * Thus, an {@code invokevirtual} instruction which invokes
 * a method handle will always link, as long
 * as the symbolic type descriptor is syntactically well-formed
 * and the types exist.
 * <p>
 * When the {@code invokevirtual} is executed after linking,
 * the receiving method handle's type is first checked by the JVM
 * to ensure that it matches the symbolic type descriptor.
 * If the type match fails, it means that the method which the
 * caller is invoking is not present on the individual
 * method handle being invoked.
 * <p>
 * In the case of {@code invokeExact}, the type descriptor of the invocation
 * (after resolving symbolic type names) must exactly match the method type
 * of the receiving method handle.
 * In the case of plain, inexact {@code invoke}, the resolved type descriptor
 * must be a valid argument to the receiver's {@link #asType asType} method.
 * Thus, plain {@code invoke} is more permissive than {@code invokeExact}.
 * <p>
 * After type matching, a call to {@code invokeExact} directly
 * and immediately invoke the method handle's underlying method
 * (or other behavior, as the case may be).
 * <p>
 * A call to plain {@code invoke} works the same as a call to
 * {@code invokeExact}, if the symbolic type descriptor specified by the caller
 * exactly matches the method handle's own type.
 * If there is a type mismatch, {@code invoke} attempts
 * to adjust the type of the receiving method handle,
 * as if by a call to {@link #asType asType},
 * to obtain an exactly invokable method handle {@code M2}.
 * This allows a more powerful negotiation of method type
 * between caller and callee.
 * <p>
 * (<em>Note:</em> The adjusted method handle {@code M2} is not directly observable,
 * and implementations are therefore not required to materialize it.)
 *
 * <h1>Invocation checking</h1>
 * In typical programs, method handle type matching will usually succeed.
 * But if a match fails, the JVM will throw a {@link WrongMethodTypeException},
 * either directly (in the case of {@code invokeExact}) or indirectly as if
 * by a failed call to {@code asType} (in the case of {@code invoke}).
 * <p>
 * Thus, a method type mismatch which might show up as a linkage error
 * in a statically typed program can show up as
 * a dynamic {@code WrongMethodTypeException}
 * in a program which uses method handles.
 * <p>
 * Because method types contain "live" {@code Class} objects,
 * method type matching takes into account both types names and class loaders.
 * Thus, even if a method handle {@code M} is created in one
 * class loader {@code L1} and used in another {@code L2},
 * method handle calls are type-safe, because the caller's symbolic type
 * descriptor, as resolved in {@code L2},
 * is matched against the original callee method's symbolic type descriptor,
 * as resolved in {@code L1}.
 * The resolution in {@code L1} happens when {@code M} is created
 * and its type is assigned, while the resolution in {@code L2} happens
 * when the {@code invokevirtual} instruction is linked.
 * <p>
 * Apart from the checking of type descriptors,
 * a method handle's capability to call its underlying method is unrestricted.
 * If a method handle is formed on a non-public method by a class
 * that has access to that method, the resulting handle can be used
 * in any place by any caller who receives a reference to it.
 * <p>
 * Unlike with the Core Reflection API, where access is checked every time
 * a reflective method is invoked,
 * method handle access checking is performed
 * <a href="MethodHandles.Lookup.html#access">when the method handle is created</a>.
 * In the case of {@code ldc} (see below), access checking is performed as part of linking
 * the constant pool entry underlying the constant method handle.
 * <p>
 * Thus, handles to non-public methods, or to methods in non-public classes,
 * should generally be kept secret.
 * They should not be passed to untrusted code unless their use from
 * the untrusted code would be harmless.
 *
 * <h1>Method handle creation</h1>
 * Java code can create a method handle that directly accesses
 * any method, constructor, or field that is accessible to that code.
 * This is done via a reflective, capability-based API called
 * {@link java.lang.invoke.MethodHandles.Lookup MethodHandles.Lookup}
 * For example, a static method handle can be obtained
 * from {@link java.lang.invoke.MethodHandles.Lookup#findStatic Lookup.findStatic}.
 * There are also conversion methods from Core Reflection API objects,
 * such as {@link java.lang.invoke.MethodHandles.Lookup#unreflect Lookup.unreflect}.
 * <p>
 * Like classes and strings, method handles that correspond to accessible
 * fields, methods, and constructors can also be represented directly
 * in a class file's constant pool as constants to be loaded by {@code ldc} bytecodes.
 * A new type of constant pool entry, {@code CONSTANT_MethodHandle},
 * refers directly to an associated {@code CONSTANT_Methodref},
 * {@code CONSTANT_InterfaceMethodref}, or {@code CONSTANT_Fieldref}
 * constant pool entry.
 * (For full details on method handle constants,
 * see sections 4.4.8 and 5.4.3.5 of the Java Virtual Machine Specification.)
 * <p>
 * Method handles produced by lookups or constant loads from methods or
 * constructors with the variable arity modifier bit ({@code 0x0080})
 * have a corresponding variable arity, as if they were defined with
 * the help of {@link #asVarargsCollector asVarargsCollector}.
 * <p>
 * A method reference may refer either to a static or non-static method.
 * In the non-static case, the method handle type includes an explicit
 * receiver argument, prepended before any other arguments.
 * In the method handle's type, the initial receiver argument is typed
 * according to the class under which the method was initially requested.
 * (E.g., if a non-static method handle is obtained via {@code ldc},
 * the type of the receiver is the class named in the constant pool entry.)
 * <p>
 * Method handle constants are subject to the same link-time access checks
 * their corresponding bytecode instructions, and the {@code ldc} instruction
 * will throw corresponding linkage errors if the bytecode behaviors would
 * throw such errors.
 * <p>
 * As a corollary of this, access to protected members is restricted
 * to receivers only of the accessing class, or one of its subclasses,
 * and the accessing class must in turn be a subclass (or package sibling)
 * of the protected member's defining class.
 * If a method reference refers to a protected non-static method or field
 * of a class outside the current package, the receiver argument will
 * be narrowed to the type of the accessing class.
 * <p>
 * When a method handle to a virtual method is invoked, the method is
 * always looked up in the receiver (that is, the first argument).
 * <p>
 * A non-virtual method handle to a specific virtual method implementation
 * can also be created.  These do not perform virtual lookup based on
 * receiver type.  Such a method handle simulates the effect of
 * an {@code invokespecial} instruction to the same method.
 *
 * <h1>Usage examples</h1>
 * Here are some examples of usage:
 * <blockquote><pre>{@code
Object x, y; String s; int i;
MethodType mt; MethodHandle mh;
MethodHandles.Lookup lookup = MethodHandles.lookup();
// mt is (char,char)String
mt = MethodType.methodType(String.class, char.class, char.class);
mh = lookup.findVirtual(String.class, "replace", mt);
s = (String) mh.invokeExact("daddy",'d','n');
// invokeExact(Ljava/lang/String;CC)Ljava/lang/String;
assertEquals(s, "nanny");
// weakly typed invocation (using MHs.invoke)
s = (String) mh.invokeWithArguments("sappy", 'p', 'v');
assertEquals(s, "savvy");
// mt is (Object[])List
mt = MethodType.methodType(java.util.List.class, Object[].class);
mh = lookup.findStatic(java.util.Arrays.class, "asList", mt);
assert(mh.isVarargsCollector());
x = mh.invoke("one", "two");
// invoke(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
assertEquals(x, java.util.Arrays.asList("one","two"));
// mt is (Object,Object,Object)Object
mt = MethodType.genericMethodType(3);
mh = mh.asType(mt);
x = mh.invokeExact((Object)1, (Object)2, (Object)3);
// invokeExact(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
assertEquals(x, java.util.Arrays.asList(1,2,3));
// mt is ()int
mt = MethodType.methodType(int.class);
mh = lookup.findVirtual(java.util.List.class, "size", mt);
i = (int) mh.invokeExact(java.util.Arrays.asList(1,2,3));
// invokeExact(Ljava/util/List;)I
assert(i == 3);
mt = MethodType.methodType(void.class, String.class);
mh = lookup.findVirtual(java.io.PrintStream.class, "println", mt);
mh.invokeExact(System.out, "Hello, world.");
// invokeExact(Ljava/io/PrintStream;Ljava/lang/String;)V
 * }</pre></blockquote>
 * Each of the above calls to {@code invokeExact} or plain {@code invoke}
 * generates a single invokevirtual instruction with
 * the symbolic type descriptor indicated in the following comment.
 * In these examples, the helper method {@code assertEquals} is assumed to
 * be a method which calls {@link java.util.Objects#equals(Object,Object) Objects.equals}
 * on its arguments, and asserts that the result is true.
 *
 * <h1>Exceptions</h1>
 * The methods {@code invokeExact} and {@code invoke} are declared
 * to throw {@link java.lang.Throwable Throwable},
 * which is to say that there is no static restriction on what a method handle
 * can throw.  Since the JVM does not distinguish between checked
 * and unchecked exceptions (other than by their class, of course),
 * there is no particular effect on bytecode shape from ascribing
 * checked exceptions to method handle invocations.  But in Java source
 * code, methods which perform method handle calls must either explicitly
 * throw {@code Throwable}, or else must catch all
 * throwables locally, rethrowing only those which are legal in the context,
 * and wrapping ones which are illegal.
 *
 * <h1><a name="sigpoly"></a>Signature polymorphism</h1>
 * The unusual compilation and linkage behavior of
 * {@code invokeExact} and plain {@code invoke}
 * is referenced by the term <em>signature polymorphism</em>.
 * As defined in the Java Language Specification,
 * a signature polymorphic method is one which can operate with
 * any of a wide range of call signatures and return types.
 * <p>
 * In source code, a call to a signature polymorphic method will
 * compile, regardless of the requested symbolic type descriptor.
 * As usual, the Java compiler emits an {@code invokevirtual}
 * instruction with the given symbolic type descriptor against the named method.
 * The unusual part is that the symbolic type descriptor is derived from
 * the actual argument and return types, not from the method declaration.
 * <p>
 * When the JVM processes bytecode containing signature polymorphic calls,
 * it will successfully link any such call, regardless of its symbolic type descriptor.
 * (In order to retain type safety, the JVM will guard such calls with suitable
 * dynamic type checks, as described elsewhere.)
 * <p>
 * Bytecode generators, including the compiler back end, are required to emit
 * untransformed symbolic type descriptors for these methods.
 * Tools which determine symbolic linkage are required to accept such
 * untransformed descriptors, without reporting linkage errors.
 *
 * <h1>Interoperation between method handles and the Core Reflection API</h1>
 * Using factory methods in the {@link java.lang.invoke.MethodHandles.Lookup Lookup} API,
 * any class member represented by a Core Reflection API object
 * can be converted to a behaviorally equivalent method handle.
 * For example, a reflective {@link java.lang.reflect.Method Method} can
 * be converted to a method handle using
 * {@link java.lang.invoke.MethodHandles.Lookup#unreflect Lookup.unreflect}.
 * The resulting method handles generally provide more direct and efficient
 * access to the underlying class members.
 * <p>
 * As a special case,
 * when the Core Reflection API is used to view the signature polymorphic
 * methods {@code invokeExact} or plain {@code invoke} in this class,
 * they appear as ordinary non-polymorphic methods.
 * Their reflective appearance, as viewed by
 * {@link java.lang.Class#getDeclaredMethod Class.getDeclaredMethod},
 * is unaffected by their special status in this API.
 * For example, {@link java.lang.reflect.Method#getModifiers Method.getModifiers}
 * will report exactly those modifier bits required for any similarly
 * declared method, including in this case {@code native} and {@code varargs} bits.
 * <p>
 * As with any reflected method, these methods (when reflected) may be
 * invoked via {@link java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}.
 * However, such reflective calls do not result in method handle invocations.
 * Such a call, if passed the required argument
 * (a single one, of type {@code Object[]}), will ignore the argument and
 * will throw an {@code UnsupportedOperationException}.
 * <p>
 * Since {@code invokevirtual} instructions can natively
 * invoke method handles under any symbolic type descriptor, this reflective view conflicts
 * with the normal presentation of these methods via bytecodes.
 * Thus, these two native methods, when reflectively viewed by
 * {@code Class.getDeclaredMethod}, may be regarded as placeholders only.
 * <p>
 * In order to obtain an invoker method for a particular type descriptor,
 * use {@link java.lang.invoke.MethodHandles#exactInvoker MethodHandles.exactInvoker},
 * or {@link java.lang.invoke.MethodHandles#invoker MethodHandles.invoker}.
 * The {@link java.lang.invoke.MethodHandles.Lookup#findVirtual Lookup.findVirtual}
 * API is also able to return a method handle
 * to call {@code invokeExact} or plain {@code invoke},
 * for any specified type descriptor .
 *
 * <h1>Interoperation between method handles and Java generics</h1>
 * A method handle can be obtained on a method, constructor, or field
 * which is declared with Java generic types.
 * As with the Core Reflection API, the type of the method handle
 * will constructed from the erasure of the source-level type.
 * When a method handle is invoked, the types of its arguments
 * or the return value cast type may be generic types or type instances.
 * If this occurs, the compiler will replace those
 * types by their erasures when it constructs the symbolic type descriptor
 * for the {@code invokevirtual} instruction.
 * <p>
 * Method handles do not represent
 * their function-like types in terms of Java parameterized (generic) types,
 * because there are three mismatches between function-like types and parameterized
 * Java types.
 * <ul>
 * <li>Method types range over all possible arities,
 * from no arguments to up to the  <a href="MethodHandle.html#maxarity">maximum number</a> of allowed arguments.
 * Generics are not variadic, and so cannot represent this.</li>
 * <li>Method types can specify arguments of primitive types,
 * which Java generic types cannot range over.</li>
 * <li>Higher order functions over method handles (combinators) are
 * often generic across a wide range of function types, including
 * those of multiple arities.  It is impossible to represent such
 * genericity with a Java type parameter.</li>
 * </ul>
 *
 * <h1><a name="maxarity"></a>Arity limits</h1>
 * The JVM imposes on all methods and constructors of any kind an absolute
 * limit of 255 stacked arguments.  This limit can appear more restrictive
 * in certain cases:
 * <ul>
 * <li>A {@code long} or {@code double} argument counts (for purposes of arity limits) as two argument slots.
 * <li>A non-static method consumes an extra argument for the object on which the method is called.
 * <li>A constructor consumes an extra argument for the object which is being constructed.
 * <li>Since a method handle&rsquo;s {@code invoke} method (or other signature-polymorphic method) is non-virtual,
 *     it consumes an extra argument for the method handle itself, in addition to any non-virtual receiver object.
 * </ul>
 * These limits imply that certain method handles cannot be created, solely because of the JVM limit on stacked arguments.
 * For example, if a static JVM method accepts exactly 255 arguments, a method handle cannot be created for it.
 * Attempts to create method handles with impossible method types lead to an {@link IllegalArgumentException}.
 * In particular, a method handle&rsquo;s type must not have an arity of the exact maximum 255.
 *
 * <p>
 *  方法句柄是对底层方法,构造函数,字段或类似的低级操作的类型化,直接可执行的引用,具有参数或返回值的可选变换这些变换是非常通用的,包括{@linkplain #asType转换},{@linkplain #bindTo insertion}
 * ,{@linkplain javalanginvokeMethodHandles#dropArguments deletion}和{@linkplain javalanginvokeMethodHandles#filterArguments substitution}
 * 。
 * 
 * <h1>方法句柄内容</h1>方法句柄根据它们的参数和返回类型动态和强类型它们不通过其底层方法的名称或定义类来区分方法句柄必须使用符号类型描述符它匹配方法句柄自己的{@linkplain #type类型描述符}
 * 。
 * <p>
 *  每个方法句柄通过{@link #type type}访问器报告它的类型描述符这个类型描述符是一个{@link javalanginvokeMethodType MethodType}对象,它的结构是一
 * 系列类,其中之一是方法的返回类型(或{@code voidclass} if none)。
 * <p>
 *  方法句柄的类型控制它接受的调用类型,以及适用于它的转换类型
 * <p>
 * 方法句柄包含一对名为{@link #invokeExact invokeExact}和{@link #invoke invoke}的特殊调用器方法。
 * 两个调用器方法提供对方法句柄的底层方法,构造函数,字段或其他操作的直接访问,参数和返回值的转换两个调用者都接受与方法句柄自己类型完全匹配的调用。plain,不精确的调用者还接受一系列其他调用类型。
 * <p>
 * 方法句柄是不可变的,并且没有可见状态当​​然,它们可以绑定到底层方法或者显示关于Java内存模型的状态的数据,任何方法句柄将表现得好像所有的(内部)字段都是最终变量。
 * 意味着对应用程序可见的任何方法句柄将始终完全形成即使方法句柄通过数据竞争中的共享变量发布也是如此。
 * <p>
 * 方法句柄不能由用户子类实现可能(或可能不)创建{@code MethodHandle}的内部子类,它可能通过{@link javalangObject#getClass ObjectgetClass}操
 * 作可见。
 * 程序员不应从方法句柄它的特定类,作为方法句柄类层次结构(如果有的话)可能随时间或跨不同供应商的实现而改变。
 * 
 * <h1>方法句柄编译</h1> Java方法调用表达式命名{@code invokeExact}或{@code invoke}可以从Java源代码调用方法句柄从源代码的角度来看,这些方法可以接受任何参数
 * 和它们的结果可以转换为任何返回类型。
 * 正式地,这是通过提供调用者方法{@code Object}返回类型和变量arity {@code Object}参数来实现的,但它们具有称为<em>签名多态性的额外质量</em >它将这种调用自由直接连
 * 接到JVM执行堆栈。
 * <p>
 * 与虚拟方法一样,源代码级调用{@code invokeExact}和{@code invoke}编译为{@code invokevirtual}指令更奇怪的是,编译器必须记录实际的参数类型,并且不能执行
 * 方法调用对参数的转换,而是必须根据自己的未转换类型在堆栈上推送它们方法句柄对象本身在参数之前推送到栈上编译器然后使用符号类型描述符调用方法句柄,描述符的参数和返回类型。
 * <p>
 * 要发出一个完整的符号类型描述符,编译器还必须确定返回类型这是基于对方法调用表达式的转换(如果有),或者如果调用是一个表达式,则为{@code Object},否则{代码void}如果调用是语句转换可以是
 * 原始类型(但不是{@code void})。
 * <p>
 *  作为一个角的情况,一个未广播的{@code null}参数被赋予{@code javalangVoid}的符号类型描述符。
 * 类型为{@code Void}的歧义是无害的,因为没有类型{@code Void}除了空引用。
 * 
 * <h1>方法句柄调用</h1>第一次执行{@code invokevirtual}指令时,它通过符号解析指令中的名称并验证方法调用是静态合法而被链接。
 * 这对于{ @code invokeExact}和{@code invoke}在这种情况下,检查编译器发出的符号类型描述符是否包含正确的语法和名称,因此,调用方法句柄的{@code invokevirtual}
 * 指令将始终链接,只要符号类型描述符在语法上形成良好并且类型存在。
 * <h1>方法句柄调用</h1>第一次执行{@code invokevirtual}指令时,它通过符号解析指令中的名称并验证方法调用是静态合法而被链接。
 * <p>
 * 当链接之后执行{@code invokevirtual}时,首先由JVM检查接收方法句柄的类型,以确保它匹配符号类型描述符如果类型匹配失败,则意味着调用者调用的方法不是呈现在调用的单个方法句柄上
 * <p>
 *  在{@code invokeExact}的情况下,调用的类型描述符(在解析符号类型名称之后)必须与接收方法句柄的方法类型完全匹配。
 * 在纯粹,不完全{@code invoke}的情况下,解析类型描述符必须是接收者的{@link #asType asType}方法的有效参数因此,plain {@code invoke}比{@code invokeExact}
 * 。
 *  在{@code invokeExact}的情况下,调用的类型描述符(在解析符号类型名称之后)必须与接收方法句柄的方法类型完全匹配。
 * <p>
 * 在类型匹配之后,直接调用{@code invokeExact}并立即调用方法句柄的底层方法(或其他行为,视情况而定)
 * <p>
 *  如果调用者指定的符号类型描述符与方法句柄自己的类型完全匹配,那么对{@code invoke}的调用的工作方式与调用{@code invokeExact}的方法相同。
 * 如果存在类型不匹配,{@code invoke}尝试调整接收方法句柄的类型,好像通过调用{@link #asType asType},以获得一个精确可调用的方法句柄{@code M2}。
 * 这允许在调用者和被调用者之间进行更强大的方法类型协商。
 * <p>
 *  (<em>注意：</em>调整后的方法句柄{@code M2}不能直接观察到,因此实现不需要实现它)
 * 
 * <h1>调用检查</h1>在典型的程序中,方法句柄类型匹配通常会成功,但如果匹配失败,JVM将直接(在{@code invokeExact}的情况下)抛出一个{@link WrongMethodTypeException}
 * 或者通过失败的{@code asType}调用(在{@code invoke}的情况下)。
 * <p>
 *  因此,在静态类型程序中可能显示为链接错误的方法类型不匹配可能在使用方法句柄的程序中显示为动态{@code WrongMethodTypeException}
 * <p>
 * 因为方法类型包含"活"{@code Class}对象,所以方法类型匹配考虑了类型名和类加载器。
 * 因此,即使在一个类加载器{@code L1}中创建了一个方法句柄{@code M}用于另一个{@code L2},方法句柄调用是类型安全的,因为调用者的符号类型描述符(如在{@code L2}中解析的)
 * 与原始的callee方法的符号类型描述符匹配,如{@code L1}当{@code M}创建并分配了类型时,{@code L1}中的分辨率发生,而{@code invokevirtual}指令被链接时,
 * {@code L2}中的分辨率发生。
 * 因为方法类型包含"活"{@code Class}对象,所以方法类型匹配考虑了类型名和类加载器。
 * <p>
 * 除了检查类型描述符之外,方法句柄调用其底层方法的能力是不受限的如果一个方法句柄由一个可访问该方法的类在一个非公开的方法上形成,结果句柄可以在任何地方使用由接收到它的引用的任何调用者
 * <p>
 *  与Core Reflection API(每次调用反射方法时都会检查访问权限)不同,在创建方法句柄时执行方法句柄访问检查<a href=\"MethodHandlesLookuphtml#access\">
 *  </a>在{ @code ldc}(见下文),访问检查作为链接常量方法句柄下的常量池条目的一部分来执行。
 * <p>
 * 因此,非公共方法或非公共类中的方法的句柄通常应该保密。它们不应该被传递给不可信代码,除非它们从不可信代码的使用是无害的
 * 
 *  <h1>方法句柄创建</h1> Java代码可以创建一个方法句柄,该句柄可以直接访问该代码可访问的任何方法,构造函数或字段。
 * 这是通过一个反射的,基于能力的API来完成的,它称为{@link javalanginvokeMethodHandlesLookup MethodHandlesLookup }例如,一个静态方法句柄可以
 * 从{@link javalanginvokeMethodHandlesLookup#findStatic LookupfindStatic}获得。
 *  <h1>方法句柄创建</h1> Java代码可以创建一个方法句柄,该句柄可以直接访问该代码可访问的任何方法,构造函数或字段。
 * 还有来自Core Reflection API对象的转换方法,例如{@link javalanginvokeMethodHandlesLookup#unreflect Lookupunreflect}。
 *  <h1>方法句柄创建</h1> Java代码可以创建一个方法句柄,该句柄可以直接访问该代码可访问的任何方法,构造函数或字段。
 * <p>
 * 像类和字符串一样,对应于可访问字段,方法和构造器的方法句柄也可以直接在类文件的常量池中表示为由{@code ldc}字节码加载的常量。
 * 一种新类型的常量池条目{@代码CONSTANT_MethodHandle}直接指向相关的{@code CONSTANT_Methodref},{@code CONSTANT_InterfaceMethodref}
 * 或{@code CONSTANT_Fieldref}常量池条目(有关方法句柄常量的完整详细信息,请参阅Java虚拟机规范第448和5435节)。
 * 像类和字符串一样,对应于可访问字段,方法和构造器的方法句柄也可以直接在类文件的常量池中表示为由{@code ldc}字节码加载的常量。
 * <p>
 * 由具有变量arity修饰符位({@code 0x0080})的方法或构造函数的查找或常量加载产生的方法句柄具有相应的变量arity,就好像它们是借助{@link #asVarargsCollector asVarargsCollector}
 * 。
 * <p>
 *  方法引用可以指静态或非静态方法在非静态情况下,方法句柄类型包括显式接收器参数,在任何其他参数前面添加。
 * 在方法句柄类型中,初始接收器参数根据最初请求该方法的类(例如,如果通过{@code ldc}获得非静态方法句柄,则接收器的类型是在常量池条目中命名的类)。
 * <p>
 * 方法句柄常量经历相同的链接时间访问检查其对应的字节码指令,并且{@code ldc}指令将抛出相应的链接错误,如果字节码行为将抛出这样的错误
 * <p>
 *  作为其结果,对受保护成员的访问仅限于访问类或其子类中的一个的接收者,并且访问类必须又是受保护成员的定义类的子类(或包兄弟)引用指的是当前包外部的类的受保护的非静态方法或字段,接收器参数将缩小到访问类的
 * 类型。
 * <p>
 * 当调用虚方法的方法句柄时,该方法总是在接收器中查找(即第一个参数)
 * <p>
 *  还可以创建特定虚拟方法实现的非虚拟方法句柄这些不基于接收器类型执行虚拟查找这样的方法句柄模拟{@code invokespecial}指令对同一方法的影响
 * 
 * <h1>使用示例</h1>以下是一些使用示例：<blockquote> <pre> {@ code Object x,y;字符串s; int i; MethodType mt; MethodHandle mh; MethodHandlesLookup lookup = MethodHandleslookup(); // mt is(char,char)String mt = MethodTypemethodType(Stringclass,charclass,charclass); mh = lookupfindVirtual(Stringclass,"replace",mt); s =(String)mhinvokeExact("daddy",'d','n'); // invokeExact(Ljava / lang / String; CC)Ljava / lang / String; assertEquals(s,"nanny"); //弱类型调用(使用MHsinvoke)s =(String)mhinvokeWithArguments("sappy",'p','v'); assertEquals(s,"savvy"); // mt is(Object [])List mt = MethodTypemethodType(javautilListclass,Object [] class); mh = lookupfindStatic(javautilArraysclass,"asList",mt); assert(mhisVarargsCollector()); x = mhinvoke("one","two"); // invoke(Ljava / lang / String; Ljava / lang / String;)Ljava / lang / Object; assertEquals(x,javautilArraysasList("one","two")); // mt is(Object,Object,Object)Object mt = MethodTypegenericMethodType(3); mh = mhasType(mt); x = mhinvokeExact((Object)1,(Object)2,(Object)3); // invokeExact(Ljava / lang / Object; Ljava / lang / Object; Ljava / lang / Object;)Ljava / lang / Object; assertEquals(x,javautilArraysasList(1,2,3)); // mt is()int mt = MethodTypemethodType(intclass); mh = lookupfindVirtual(javautilListclass,"size",mt); i =(int)mhinvokeExact(javautilArraysasList(1,2,3)); // invokeExact(Ljava / util / List;)我断言(i == 3); mt = MethodTypemethodType(voidclass,Stringclass); mh = lookupfindVirtual(javaioPrintStreamclass,"println",mt); mhinvokeExact(Systemout,"Hello,world"; // invokeExact(Ljava / io / PrintStream; Ljava / lang / String;)V}
 *  </pre> </blockquote>上述每次调用{@code invokeExact}或plain {@code invoke}具有以下注释中指示的符号类型描述符的单调用虚拟指令在这些示例中,假定
 * 帮助方法{@code assertEquals}是在其参数上调用{@link javautilObjects#equals(Object,Object)Objectsequals}并断言结果是真的。
 * 
 * <h1>异常</h1>方法{@code invokeExact}和{@code invoke}被声明为抛出{@link javalangThrowable Throwable},这就是说,没有对方法句柄
 * 可以抛出的静态限制。
 *  JVM不区分已检查和未检查的异常(当然除了它们的类之外),对于将检查异常归于方法句柄调用没有特别影响字节码形状但是在Java源代码中,执行方法句柄调用的方法必须要么显式地抛出{@code Throwable}
 * ,要么必须在本地捕获所有throwables,只重写在上下文中合法的那些,以及包装非法的。
 * 
 * <h1> <a name=\"sigpoly\"> </a>签名多态性</h1> {@code invokeExact}和纯粹{@code invoke}的异常编译和链接行为由术语<em>签名多态性</em>
 * 如Java语言规范中定义的,签名多态方法是可以与任何广泛的调用签名和返回类型。
 * <p>
 *  在源代码中,对签名多态方法的调用将被编译,而不管所请求的符号类型描述符如同通常,Java编译器针对命名的方法发出具有给定符号类型描述符的{@code invokevirtual}指令。
 * 不寻常的部分是符号类型描述符从实际的参数和返回类型派生,而不是从方法声明。
 * <p>
 * 当JVM处理包含签名多态调用的字节码时,它将成功链接任何这样的调用,而不管其符号类型描述符(为了保持类型安全,JVM将通过适当的动态类型检查来保护这样的调用,如别处所述)
 * <p>
 *  包括编译器后端的字节码生成器需要为这些方法发出未转换的符号类型描述符确定符号链接的工具需要接受这种未转换的描述符,而不报告链接错误
 * 
 * <h1>方法句柄和Core Reflection API之间的互操作</h1>使用{@link javalanginvokeMethodHandlesLookup Lookup} API中的工厂方法,由
 * Core Reflection API对象表示的任何类成员都可以转换为行为相同的方法句柄例如,反射{@link javalangreflectMethod Method}可以使用{@link javalanginvokeMethodHandlesLookup#unreflect Lookupunreflect}
 * 转换为方法句柄。
 * 生成的方法句柄通常提供对底层类成员的更直接和有效的访问。
 * <p>
 * 作为一种特殊情况,当Core Reflection API用于查看此类中的签名多态方法{@code invokeExact}或纯{@code invoke}时,它们显示为普通的非多态方法。
 * 它们的反射外观, @link javalangClass#getDeclaredMethod ClassgetDeclaredMethod}不受其在此API中的特殊状态的影响例如,{@link javalangreflectMethod#getModifiers MethodgetModifiers}
 * 将报告任何类似声明的方法所需的修饰符位,包括{@code native}和{@code varargs}位。
 * 作为一种特殊情况,当Core Reflection API用于查看此类中的签名多态方法{@code invokeExact}或纯{@code invoke}时,它们显示为普通的非多态方法。
 * <p>
 * 与任何反射的方法一样,这些方法(当反射时)可以通过{@link javalangreflectMethod#invoke javalangreflectMethodinvoke}调用。
 * 但是,这种反射调用不会导致方法句柄调用这样的调用,如果传递所需的参数(一个单一的,的类型{@code Object []}),将忽略该参数,并将抛出一个{@code UnsupportedOperationException}
 * 。
 * 与任何反射的方法一样,这些方法(当反射时)可以通过{@link javalangreflectMethod#invoke javalangreflectMethodinvoke}调用。
 * <p>
 *  由于{@code invokevirtual}指令可以在任何符号类型描述符下本地调用方法句柄,所以这个反射视图与通过字节码的这些方法的正常表示冲突。
 * 因此,当{@code ClassgetDeclaredMethod}反射观察时,这两个本地方法可能仅被视为占位符。
 * <p>
 * 为了获得特定类型描述符的invoker方法,使用{@link javalanginvokeMethodHandles#exactInvoker MethodHandlesexactInvoker}或{@link javalanginvokeMethodHandles#invoker MethodHandlesinvoker}
 *  {@link javalanginvokeMethodHandlesLookup#findVirtual LookupfindVirtual} API也能够返回方法句柄为任何指定的类型描述符调用{@code invokeExact}
 * 或plain {@code invoke}。
 * 
 * <h1>方法句柄和Java泛型之间的互操作</h1>可以在使用Java泛型类型声明的方法,构造函数或字段上获取方法句柄与Core Reflection API一样,方法句柄的类型将被构造从源级类型的擦除
 * 当调用方法句柄时,其参数的类型或返回值类型转换类型可以是通用类型或类型实例如果发生这种情况,编译器将在构造时替换那些类型的消除{@code invokevirtual}指令的符号类型描述符。
 * <p>
 *  方法句柄不是用Java参数化(通用)类型表示类似函数的类型,因为类函数类型和参数化Java类型之间有三个不匹配
 * <ul>
 * <li>方法类型涵盖所有可能的arity,从无参数到允许的参数的<a href=\"MethodHandlehtml#maxarity\">最大数目</a>通用变量不可变,因此不能表示此</li > <li>
 * 方法类型可以指定基本类型的参数,而不是其范围的</li> <li>方法句柄(组合器)的高阶函数通常是泛泛的函数类型,包括多个arity使用Java类型参数</li>表示这种生成性是不可能的。
 * </ul>
 * 
 *  <h1> <a name=\"maxarity\"> </a>属性限制</h1> JVM对任何类型的所有方法和构造函数施加255个堆栈参数的绝对限制此限制在某些情况下可能显得更具限制性：
 * <ul>
 * <li>一个{@code long}或{@code double}参数计数为两个参数插槽</li> </li> <li>非静态方法会为调用方法的对象消耗额外的参数< li>构造函数为正在构造的对象消耗一
 * 个额外的参数<li>由于方法句柄{@code invoke}方法(或其他签名多态方法)是非虚拟的,因此它会消耗额外的参数方法句柄本身,以及任何非虚拟接收器对象。
 * </ul>
 * 这些限制意味着无法创建某些方法句柄,仅仅是因为JVM对堆栈参数的限制。
 * 例如,如果静态JVM方法只接受255个参数,则无法为其创建方法句柄尝试使用不可能的方法类型创建方法句柄导致{@link IllegalArgumentException}特别是,方法句柄的类型不能有精确
 * 的最大255。
 * 这些限制意味着无法创建某些方法句柄,仅仅是因为JVM对堆栈参数的限制。
 * 
 * 
 * @see MethodType
 * @see MethodHandles
 * @author John Rose, JSR 292 EG
 */
public abstract class MethodHandle {
    static { MethodHandleImpl.initStatics(); }

    /**
     * Internal marker interface which distinguishes (to the Java compiler)
     * those methods which are <a href="MethodHandle.html#sigpoly">signature polymorphic</a>.
     * <p>
     *  内部标记界面,用于区分(对于Java编译器)<a href=\"MethodHandlehtml#sigpoly\">标记多态性</a>
     * 
     */
    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @interface PolymorphicSignature { }

    private final MethodType type;
    /*private*/ final LambdaForm form;
    // form is not private so that invokers can easily fetch it
    /* <p>
    /*  //表单不是私有的,所以调用者可以轻松地获取它
    /* 
    /* 
    /*private*/ MethodHandle asTypeCache;
    // asTypeCache is not private so that invokers can easily fetch it

    /**
     * Reports the type of this method handle.
     * Every invocation of this method handle via {@code invokeExact} must exactly match this type.
     * <p>
     *  // asTypeCache不是私有的,所以调用者可以很容易地获取它
     * 
     * / **报告此方法句柄的类型每次通过{@code invokeExact}调用此方法句柄都必须与此类型完全匹配
     * 
     * 
     * @return the method handle type
     */
    public MethodType type() {
        return type;
    }

    /**
     * Package-private constructor for the method handle implementation hierarchy.
     * Method handle inheritance will be contained completely within
     * the {@code java.lang.invoke} package.
     * <p>
     *  方法句柄实现层次结构的Package-private构造方法方法句柄继承将完全包含在{@code javalanginvoke}包中
     * 
     */
    // @param type type (permanently assigned) of the new method handle
    /*non-public*/ MethodHandle(MethodType type, LambdaForm form) {
        type.getClass();  // explicit NPE
        form.getClass();  // explicit NPE
        this.type = type;
        this.form = form;

        form.prepare();  // TO DO:  Try to delay this step until just before invocation.
    }

    /**
     * Invokes the method handle, allowing any caller type descriptor, but requiring an exact type match.
     * The symbolic type descriptor at the call site of {@code invokeExact} must
     * exactly match this method handle's {@link #type type}.
     * No conversions are allowed on arguments or return values.
     * <p>
     * When this method is observed via the Core Reflection API,
     * it will appear as a single native method, taking an object array and returning an object.
     * If this native method is invoked directly via
     * {@link java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}, via JNI,
     * or indirectly via {@link java.lang.invoke.MethodHandles.Lookup#unreflect Lookup.unreflect},
     * it will throw an {@code UnsupportedOperationException}.
     * <p>
     *  typegetClass(); // explicit NPE formgetClass(); // explicit NPE thistype = type; thisform = form;
     * 
     *  formprepare(); // TO DO：尝试延迟此步骤直到调用之前}
     * 
     *  / **调用方法句柄,允许任何调用者类型描述符,但需要一个精确的类型匹配{@code invokeExact}的调用点处的符号类型描述符必须与此方法句柄的{@link #type type}完全匹配。
     * 允许在参数或返回值。
     * <p>
     * 当通过Core Reflection API观察这个方法时,它将作为单个本地方法出现,取一个对象数组并返回一个对象如果这个本地方法通过{@link javalangreflectMethod#invoke javalangreflectMethodinvoke}
     * 直接调用,通过JNI或间接通过{@link javalanginvokeMethodHandlesLookup#unreflect Lookupunreflect},它会抛出一个{@code UnsupportedOperationException}
     * 。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     * @throws WrongMethodTypeException if the target's type is not identical with the caller's symbolic type descriptor
     * @throws Throwable anything thrown by the underlying method propagates unchanged through the method handle call
     */
    public final native @PolymorphicSignature Object invokeExact(Object... args) throws Throwable;

    /**
     * Invokes the method handle, allowing any caller type descriptor,
     * and optionally performing conversions on arguments and return values.
     * <p>
     * If the call site's symbolic type descriptor exactly matches this method handle's {@link #type type},
     * the call proceeds as if by {@link #invokeExact invokeExact}.
     * <p>
     * Otherwise, the call proceeds as if this method handle were first
     * adjusted by calling {@link #asType asType} to adjust this method handle
     * to the required type, and then the call proceeds as if by
     * {@link #invokeExact invokeExact} on the adjusted method handle.
     * <p>
     * There is no guarantee that the {@code asType} call is actually made.
     * If the JVM can predict the results of making the call, it may perform
     * adaptations directly on the caller's arguments,
     * and call the target method handle according to its own exact type.
     * <p>
     * The resolved type descriptor at the call site of {@code invoke} must
     * be a valid argument to the receivers {@code asType} method.
     * In particular, the caller must specify the same argument arity
     * as the callee's type,
     * if the callee is not a {@linkplain #asVarargsCollector variable arity collector}.
     * <p>
     * When this method is observed via the Core Reflection API,
     * it will appear as a single native method, taking an object array and returning an object.
     * If this native method is invoked directly via
     * {@link java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}, via JNI,
     * or indirectly via {@link java.lang.invoke.MethodHandles.Lookup#unreflect Lookup.unreflect},
     * it will throw an {@code UnsupportedOperationException}.
     * <p>
     *  调用方法句柄,允许任何调用者类型描述符,并且可选地对参数和返回值执行转换
     * <p>
     *  如果调用站点的符号类型描述符与此方法句柄的{@link #type type}完全匹配,则调用将像{@link #invokeExact invokeExact}
     * <p>
     * 否则,调用将如同通过调用{@link #asType asType}来调整此方法句柄以将此方法句柄调整为所需类型,然后调用继续进行,如同通过{@link #invokeExact invokeExact}
     * 调用方法句柄。
     * <p>
     *  不能保证{@code asType}调用实际上被调用如果JVM可以预测调用的结果,它可以直接对调用者的参数执行适配,并根据其自己的确切类型调用目标方法句柄
     * <p>
     * 在{@code invoke}的调用点处解析的类型描述符必须是接收者{@code asType}方法的有效参数。
     * 特别地,调用者必须指定与被调用者的类型相同的参数,如果被调用者不是{@linkplain #asVarargsCollector variable arity collector}。
     * <p>
     *  当通过Core Reflection API观察这个方法时,它将作为单个本地方法出现,取一个对象数组并返回一个对象如果这个本地方法通过{@link javalangreflectMethod#invoke javalangreflectMethodinvoke}
     * 直接调用,通过JNI或间接通过{@link javalanginvokeMethodHandlesLookup#unreflect Lookupunreflect},它会抛出一个{@code UnsupportedOperationException}
     * 。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     * @throws WrongMethodTypeException if the target's type cannot be adjusted to the caller's symbolic type descriptor
     * @throws ClassCastException if the target's type can be adjusted to the caller, but a reference cast fails
     * @throws Throwable anything thrown by the underlying method propagates unchanged through the method handle call
     */
    public final native @PolymorphicSignature Object invoke(Object... args) throws Throwable;

    /**
     * Private method for trusted invocation of a method handle respecting simplified signatures.
     * Type mismatches will not throw {@code WrongMethodTypeException}, but could crash the JVM.
     * <p>
     * The caller signature is restricted to the following basic types:
     * Object, int, long, float, double, and void return.
     * <p>
     * The caller is responsible for maintaining type correctness by ensuring
     * that the each outgoing argument value is a member of the range of the corresponding
     * callee argument type.
     * (The caller should therefore issue appropriate casts and integer narrowing
     * operations on outgoing argument values.)
     * The caller can assume that the incoming result value is part of the range
     * of the callee's return type.
     * <p>
     * 用于信任调用符合简化签名的方法句柄的私有方法类型不匹配不会抛出{@code WrongMethodTypeException},但可能导致JVM崩溃
     * <p>
     *  调用者签名被限制为以下基本类型：Object,int,long,float,double和void return
     * <p>
     *  调用者负责通过确保每个输出参数值是相应被调用者参数类型的范围的成员来保持类型正确性(因此调用者应该对输出参数值发出适当的强制转换和整数缩减操作)。
     * 调用者可以假定传入结果值是被调用方返回类型范围的一部分。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     */
    /*non-public*/ final native @PolymorphicSignature Object invokeBasic(Object... args) throws Throwable;

    /**
     * Private method for trusted invocation of a MemberName of kind {@code REF_invokeVirtual}.
     * The caller signature is restricted to basic types as with {@code invokeBasic}.
     * The trailing (not leading) argument must be a MemberName.
     * <p>
     * / **用于可信调用类型MemberName的私有方法{@code REF_invokeVirtual}调用者签名仅限于使用{@code invokeBasic}的基本类型。
     * 尾随(不是引导)参数必须是MemberName。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     */
    /*non-public*/ static native @PolymorphicSignature Object linkToVirtual(Object... args) throws Throwable;

    /**
     * Private method for trusted invocation of a MemberName of kind {@code REF_invokeStatic}.
     * The caller signature is restricted to basic types as with {@code invokeBasic}.
     * The trailing (not leading) argument must be a MemberName.
     * <p>
     *  / **用于可信调用类型的MemberName的私有方法{@code REF_invokeStatic}调用者签名仅限于使用{@code invokeBasic}的基本类型。
     * 尾随(不是引导)参数必须是MemberName。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     */
    /*non-public*/ static native @PolymorphicSignature Object linkToStatic(Object... args) throws Throwable;

    /**
     * Private method for trusted invocation of a MemberName of kind {@code REF_invokeSpecial}.
     * The caller signature is restricted to basic types as with {@code invokeBasic}.
     * The trailing (not leading) argument must be a MemberName.
     * <p>
     *  / **用于可信调用类型MemberName的私有方法{@code REF_invokeSpecial}调用者签名仅限于使用{@code invokeBasic}的基本类型。
     * 尾随(不是引导)参数必须是MemberName。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     */
    /*non-public*/ static native @PolymorphicSignature Object linkToSpecial(Object... args) throws Throwable;

    /**
     * Private method for trusted invocation of a MemberName of kind {@code REF_invokeInterface}.
     * The caller signature is restricted to basic types as with {@code invokeBasic}.
     * The trailing (not leading) argument must be a MemberName.
     * <p>
     * / **用于可信调用类型MemberName的私有方法{@code REF_invokeInterface}调用者签名仅限于使用{@code invokeBasic}的基本类型。
     * 尾随(不是头)参数必须是MemberName。
     * 
     * 
     * @param args the signature-polymorphic parameter list, statically represented using varargs
     * @return the signature-polymorphic result, statically represented using {@code Object}
     */
    /*non-public*/ static native @PolymorphicSignature Object linkToInterface(Object... args) throws Throwable;

    /**
     * Performs a variable arity invocation, passing the arguments in the given list
     * to the method handle, as if via an inexact {@link #invoke invoke} from a call site
     * which mentions only the type {@code Object}, and whose arity is the length
     * of the argument list.
     * <p>
     * Specifically, execution proceeds as if by the following steps,
     * although the methods are not guaranteed to be called if the JVM
     * can predict their effects.
     * <ul>
     * <li>Determine the length of the argument array as {@code N}.
     *     For a null reference, {@code N=0}. </li>
     * <li>Determine the general type {@code TN} of {@code N} arguments as
     *     as {@code TN=MethodType.genericMethodType(N)}.</li>
     * <li>Force the original target method handle {@code MH0} to the
     *     required type, as {@code MH1 = MH0.asType(TN)}. </li>
     * <li>Spread the array into {@code N} separate arguments {@code A0, ...}. </li>
     * <li>Invoke the type-adjusted method handle on the unpacked arguments:
     *     MH1.invokeExact(A0, ...). </li>
     * <li>Take the return value as an {@code Object} reference. </li>
     * </ul>
     * <p>
     * Because of the action of the {@code asType} step, the following argument
     * conversions are applied as necessary:
     * <ul>
     * <li>reference casting
     * <li>unboxing
     * <li>widening primitive conversions
     * </ul>
     * <p>
     * The result returned by the call is boxed if it is a primitive,
     * or forced to null if the return type is void.
     * <p>
     * This call is equivalent to the following code:
     * <blockquote><pre>{@code
     * MethodHandle invoker = MethodHandles.spreadInvoker(this.type(), 0);
     * Object result = invoker.invokeExact(this, arguments);
     * }</pre></blockquote>
     * <p>
     * Unlike the signature polymorphic methods {@code invokeExact} and {@code invoke},
     * {@code invokeWithArguments} can be accessed normally via the Core Reflection API and JNI.
     * It can therefore be used as a bridge between native or reflective code and method handles.
     *
     * <p>
     *  / **执行变量arity调用,将给定列表中的参数传递给方法句柄,就像通过来自调用站点的不精确的{@link #invoke invoke},它仅提及类型{@code Object},并且arity是
     * 参数列表的长度。
     * <p>
     *  具体来说,执行如同通过以下步骤进行,尽管如果JVM可以预测它们的效果,则不能保证调用该方法
     * <ul>
     * <li>确定参数数组的长度为{@code N}对于空引用,{@code N = 0} </li> <li>确定{@code N}的一般类型{@code TN} </li> <li>将原始目标方法句柄{@code MH0}
     * 强制为所需类型,如{@code MH1 = MH0asType(TN)} </li > <li>将数组扩展到{@code N}单独的参数{@code A0,} </li> <li>在解压缩的参数上调用类
     * 型调整的方法句柄：MH1invokeExact(A0,)</li> li>将返回值作为{@code Object}引用</li>。
     * </ul>
     * <p>
     *  由于{@code asType}步骤的操作,必要时应用以下参数转换：
     * <ul>
     *  <li>参考投放<li>取消装箱<li>拓宽原始转换
     * </ul>
     * <p>
     * 如果调用返回的结果是一个原语,则返回的是boxed,如果返回类型为void,则强制返回null
     * <p>
     *  这个调用相当于下面的代码：<blockquote> <pre> {@ code MethodHandle invoker = MethodHandlesspreadInvoker(thistype(),0); Object result = invokerinvokeExact(this,arguments); }
     *  </pre> </blockquote>。
     * <p>
     *  与签名多态方法{@code invokeExact}和{@code invoke}不同,{@code invokeWithArguments}可以通过Core Reflection API和JNI正常
     * 访问。
     * 因此,它可以用作本机或反射代码和方法句柄之间的桥梁。
     * 
     * 
     * @param arguments the arguments to pass to the target
     * @return the result returned by the target
     * @throws ClassCastException if an argument cannot be converted by reference casting
     * @throws WrongMethodTypeException if the target's type cannot be adjusted to take the given number of {@code Object} arguments
     * @throws Throwable anything thrown by the target method invocation
     * @see MethodHandles#spreadInvoker
     */
    public Object invokeWithArguments(Object... arguments) throws Throwable {
        MethodType invocationType = MethodType.genericMethodType(arguments == null ? 0 : arguments.length);
        return invocationType.invokers().spreadInvoker(0).invokeExact(asType(invocationType), arguments);
    }

    /**
     * Performs a variable arity invocation, passing the arguments in the given array
     * to the method handle, as if via an inexact {@link #invoke invoke} from a call site
     * which mentions only the type {@code Object}, and whose arity is the length
     * of the argument array.
     * <p>
     * This method is also equivalent to the following code:
     * <blockquote><pre>{@code
     *   invokeWithArguments(arguments.toArray()
     * }</pre></blockquote>
     *
     * <p>
     * 执行可变arity调用,将给定数组中的参数传递给方法句柄,就像通过来自调用站点的不准确的{@link #invoke invoke},它仅提及类型{@code Object},并且其语义是参数数组的长度
     * 。
     * <p>
     *  此方法也等效于以下代码：<blockquote> <pre> {@ code invokeWithArguments(argumentstoArray()} </pre> </blockquote>。
     * 
     * 
     * @param arguments the arguments to pass to the target
     * @return the result returned by the target
     * @throws NullPointerException if {@code arguments} is a null reference
     * @throws ClassCastException if an argument cannot be converted by reference casting
     * @throws WrongMethodTypeException if the target's type cannot be adjusted to take the given number of {@code Object} arguments
     * @throws Throwable anything thrown by the target method invocation
     */
    public Object invokeWithArguments(java.util.List<?> arguments) throws Throwable {
        return invokeWithArguments(arguments.toArray());
    }

    /**
     * Produces an adapter method handle which adapts the type of the
     * current method handle to a new type.
     * The resulting method handle is guaranteed to report a type
     * which is equal to the desired new type.
     * <p>
     * If the original type and new type are equal, returns {@code this}.
     * <p>
     * The new method handle, when invoked, will perform the following
     * steps:
     * <ul>
     * <li>Convert the incoming argument list to match the original
     *     method handle's argument list.
     * <li>Invoke the original method handle on the converted argument list.
     * <li>Convert any result returned by the original method handle
     *     to the return type of new method handle.
     * </ul>
     * <p>
     * This method provides the crucial behavioral difference between
     * {@link #invokeExact invokeExact} and plain, inexact {@link #invoke invoke}.
     * The two methods
     * perform the same steps when the caller's type descriptor exactly m atches
     * the callee's, but when the types differ, plain {@link #invoke invoke}
     * also calls {@code asType} (or some internal equivalent) in order
     * to match up the caller's and callee's types.
     * <p>
     * If the current method is a variable arity method handle
     * argument list conversion may involve the conversion and collection
     * of several arguments into an array, as
     * {@linkplain #asVarargsCollector described elsewhere}.
     * In every other case, all conversions are applied <em>pairwise</em>,
     * which means that each argument or return value is converted to
     * exactly one argument or return value (or no return value).
     * The applied conversions are defined by consulting the
     * the corresponding component types of the old and new
     * method handle types.
     * <p>
     * Let <em>T0</em> and <em>T1</em> be corresponding new and old parameter types,
     * or old and new return types.  Specifically, for some valid index {@code i}, let
     * <em>T0</em>{@code =newType.parameterType(i)} and <em>T1</em>{@code =this.type().parameterType(i)}.
     * Or else, going the other way for return values, let
     * <em>T0</em>{@code =this.type().returnType()} and <em>T1</em>{@code =newType.returnType()}.
     * If the types are the same, the new method handle makes no change
     * to the corresponding argument or return value (if any).
     * Otherwise, one of the following conversions is applied
     * if possible:
     * <ul>
     * <li>If <em>T0</em> and <em>T1</em> are references, then a cast to <em>T1</em> is applied.
     *     (The types do not need to be related in any particular way.
     *     This is because a dynamic value of null can convert to any reference type.)
     * <li>If <em>T0</em> and <em>T1</em> are primitives, then a Java method invocation
     *     conversion (JLS 5.3) is applied, if one exists.
     *     (Specifically, <em>T0</em> must convert to <em>T1</em> by a widening primitive conversion.)
     * <li>If <em>T0</em> is a primitive and <em>T1</em> a reference,
     *     a Java casting conversion (JLS 5.5) is applied if one exists.
     *     (Specifically, the value is boxed from <em>T0</em> to its wrapper class,
     *     which is then widened as needed to <em>T1</em>.)
     * <li>If <em>T0</em> is a reference and <em>T1</em> a primitive, an unboxing
     *     conversion will be applied at runtime, possibly followed
     *     by a Java method invocation conversion (JLS 5.3)
     *     on the primitive value.  (These are the primitive widening conversions.)
     *     <em>T0</em> must be a wrapper class or a supertype of one.
     *     (In the case where <em>T0</em> is Object, these are the conversions
     *     allowed by {@link java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}.)
     *     The unboxing conversion must have a possibility of success, which means that
     *     if <em>T0</em> is not itself a wrapper class, there must exist at least one
     *     wrapper class <em>TW</em> which is a subtype of <em>T0</em> and whose unboxed
     *     primitive value can be widened to <em>T1</em>.
     * <li>If the return type <em>T1</em> is marked as void, any returned value is discarded
     * <li>If the return type <em>T0</em> is void and <em>T1</em> a reference, a null value is introduced.
     * <li>If the return type <em>T0</em> is void and <em>T1</em> a primitive,
     *     a zero value is introduced.
     * </ul>
     * (<em>Note:</em> Both <em>T0</em> and <em>T1</em> may be regarded as static types,
     * because neither corresponds specifically to the <em>dynamic type</em> of any
     * actual argument or return value.)
     * <p>
     * The method handle conversion cannot be made if any one of the required
     * pairwise conversions cannot be made.
     * <p>
     * At runtime, the conversions applied to reference arguments
     * or return values may require additional runtime checks which can fail.
     * An unboxing operation may fail because the original reference is null,
     * causing a {@link java.lang.NullPointerException NullPointerException}.
     * An unboxing operation or a reference cast may also fail on a reference
     * to an object of the wrong type,
     * causing a {@link java.lang.ClassCastException ClassCastException}.
     * Although an unboxing operation may accept several kinds of wrappers,
     * if none are available, a {@code ClassCastException} will be thrown.
     *
     * <p>
     *  生成适配器方法句柄,其将当前方法句柄的类型适配为新类型所得到的方法句柄被保证报告等于期望的新类型的类型
     * <p>
     *  如果原始类型和新类型相等,则返回{@code this}
     * <p>
     *  新方法句柄在被调用时将执行以下步骤：
     * <ul>
     * <li>转换传入的参数列表以匹配原始方法句柄的参数列表<li>在转换的参数列表上调用原始方法句柄<li>将原始方法句柄返回的任何结果转换为新方法句柄的返回类型
     * </ul>
     * <p>
     *  此方法提供{@link #invokeExact invokeExact}和plain,不精确{@link #invoke invoke}之间的关键行为差异当调用者的类型描述符精确地调用被调用者时,这
     * 两种方法执行相同的步骤,但是当类型不同时, plain {@link #invoke invoke}也调用{@code asType}(或一些内部等价物),以匹配调用者和被调用者的类型。
     * <p>
     * 如果当前方法是一个变量arity方法句柄参数列表转换可能涉及到转换和收集几个参数到数组,如{@linkplain #asVarargsCollector在其他地方描述}在其他情况下,所有的转换应用< em>
     * ,这意味着每个参数或返回值被转换为恰好一个参数或返回值(或没有返回值)。
     * 应用的转换通过查阅旧方法句柄类型和新方法句柄类型的相应组件类型。
     * <p>
     * 让<em> T0 <em>和<em> T1 </em>是相应的新旧参数类型,或旧的和新的返回类型。
     * 具体来说,对于一些有效的索引{@code i} / em> {@ code = newTypeparameterType(i)}和<em> T1 </em> {@ code = thistype()parameterType(i)}
     * 否则, / em> {@ code = thistype()returnType()}和<em> T1 </em> {@ code = newTypereturnType()}如果类型相同,新的方法句柄
     * 不会改变相应的参数,返回值(如果有)否则,如果可能,应用以下转换之一：。
     * 让<em> T0 <em>和<em> T1 </em>是相应的新旧参数类型,或旧的和新的返回类型。
     * <ul>
     * <li>如果<em> </em>和<em> T1 </em>是引用,则会应用到<em> T1 </em>的转换(类型不需要在任何特定方法这是因为null的动态值可以转换为任何引用类型)<li>如果<em>
     *  T0 </em>和<em> T1 </em>是原语,那么Java方法调用转换(JLS 53)如果存在(具体来说,<em> </em>必须通过扩展的基元转换转换为<em> T1 </em>)<li>如果
     * <em> T0 </em> <em> T1 </em>引用,如果存在Java转换转换(JLS 55)(具体来说,值从<em> T0 </em>加载到其包装器类,需要<em> T1 </em>)<li>如
     * 果<em> T0 </em>是引用而且<em> T1 </em>是原语,则将在运行时应用拆箱转换,可能后面是Java方法调用转换原始值(这些是原始扩展转换)<em>必须是一个包装器类或一个超类型(在<em>
     *  T0 </em>是Object的情况下,这些是允许的转换通过{@link javalangreflectMethod#invoke javalangreflectMethodinvoke})开箱转换必
     * 须有成功的可能性,这意味着如果<em> T0 </em>本身不是包装类,必须存在至少一个包装类<em> TW </em>,它是<em> T0 </em>的子类型,其开箱原始值可以扩展为<em> T1 </em>
     * <li>如果返回类型<em> T1 </em>标记为void,则会舍弃任何返回的值<li>如果返回类型<em> T0 </em>无效,<em> T1 </em >引用,引入一个空值<li>如果返回类型<em>
     *  T0 </em>是void和<em> T1 </em>。
     * </ul>
     * (<em>注意：</em> <em> </em>和<em> </em>都可以视为静态类型,因为这两种类型都不会特别对应<em>的任何实际参数或返回值)
     * <p>
     *  如果无法进行任何所需的成对转换,则无法进行方法句柄转换
     * <p>
     * 在运行时,应用于引用参数或返回值的转换可能需要其他可能失败的运行时检查解包装操作可能失败,因为原始引用为空,导致{@link javalangNullPointerException NullPointerException}
     * 取消装箱操作或引用转换也可能失败引用一个错误类型的对象引起一个{@link javalangClassCastException ClassCastException}虽然一个拆箱操作可以接受几种类型的
     * 包装器,如果没有可用的,一个{@code ClassCastException}将被抛出。
     * 
     * 
     * @param newType the expected type of the new method handle
     * @return a method handle which delegates to {@code this} after performing
     *           any necessary argument conversions, and arranges for any
     *           necessary return value conversions
     * @throws NullPointerException if {@code newType} is a null reference
     * @throws WrongMethodTypeException if the conversion cannot be made
     * @see MethodHandles#explicitCastArguments
     */
    public MethodHandle asType(MethodType newType) {
        // Fast path alternative to a heavyweight {@code asType} call.
        // Return 'this' if the conversion will be a no-op.
        if (newType == type) {
            return this;
        }
        // Return 'this.asTypeCache' if the conversion is already memoized.
        MethodHandle atc = asTypeCached(newType);
        if (atc != null) {
            return atc;
        }
        return asTypeUncached(newType);
    }

    private MethodHandle asTypeCached(MethodType newType) {
        MethodHandle atc = asTypeCache;
        if (atc != null && newType == atc.type) {
            return atc;
        }
        return null;
    }

    /** Override this to change asType behavior. */
    /*non-public*/ MethodHandle asTypeUncached(MethodType newType) {
        if (!type.isConvertibleTo(newType))
            throw new WrongMethodTypeException("cannot convert "+this+" to "+newType);
        return asTypeCache = MethodHandleImpl.makePairwiseConvert(this, newType, true);
    }

    /**
     * Makes an <em>array-spreading</em> method handle, which accepts a trailing array argument
     * and spreads its elements as positional arguments.
     * The new method handle adapts, as its <i>target</i>,
     * the current method handle.  The type of the adapter will be
     * the same as the type of the target, except that the final
     * {@code arrayLength} parameters of the target's type are replaced
     * by a single array parameter of type {@code arrayType}.
     * <p>
     * If the array element type differs from any of the corresponding
     * argument types on the original target,
     * the original target is adapted to take the array elements directly,
     * as if by a call to {@link #asType asType}.
     * <p>
     * When called, the adapter replaces a trailing array argument
     * by the array's elements, each as its own argument to the target.
     * (The order of the arguments is preserved.)
     * They are converted pairwise by casting and/or unboxing
     * to the types of the trailing parameters of the target.
     * Finally the target is called.
     * What the target eventually returns is returned unchanged by the adapter.
     * <p>
     * Before calling the target, the adapter verifies that the array
     * contains exactly enough elements to provide a correct argument count
     * to the target method handle.
     * (The array may also be null when zero elements are required.)
     * <p>
     * If, when the adapter is called, the supplied array argument does
     * not have the correct number of elements, the adapter will throw
     * an {@link IllegalArgumentException} instead of invoking the target.
     * <p>
     * Here are some simple examples of array-spreading method handles:
     * <blockquote><pre>{@code
MethodHandle equals = publicLookup()
  .findVirtual(String.class, "equals", methodType(boolean.class, Object.class));
assert( (boolean) equals.invokeExact("me", (Object)"me"));
assert(!(boolean) equals.invokeExact("me", (Object)"thee"));
// spread both arguments from a 2-array:
MethodHandle eq2 = equals.asSpreader(Object[].class, 2);
assert( (boolean) eq2.invokeExact(new Object[]{ "me", "me" }));
assert(!(boolean) eq2.invokeExact(new Object[]{ "me", "thee" }));
// try to spread from anything but a 2-array:
for (int n = 0; n <= 10; n++) {
  Object[] badArityArgs = (n == 2 ? null : new Object[n]);
  try { assert((boolean) eq2.invokeExact(badArityArgs) && false); }
  catch (IllegalArgumentException ex) { } // OK
}
// spread both arguments from a String array:
MethodHandle eq2s = equals.asSpreader(String[].class, 2);
assert( (boolean) eq2s.invokeExact(new String[]{ "me", "me" }));
assert(!(boolean) eq2s.invokeExact(new String[]{ "me", "thee" }));
// spread second arguments from a 1-array:
MethodHandle eq1 = equals.asSpreader(Object[].class, 1);
assert( (boolean) eq1.invokeExact("me", new Object[]{ "me" }));
assert(!(boolean) eq1.invokeExact("me", new Object[]{ "thee" }));
// spread no arguments from a 0-array or null:
MethodHandle eq0 = equals.asSpreader(Object[].class, 0);
assert( (boolean) eq0.invokeExact("me", (Object)"me", new Object[0]));
assert(!(boolean) eq0.invokeExact("me", (Object)"thee", (Object[])null));
// asSpreader and asCollector are approximate inverses:
for (int n = 0; n <= 2; n++) {
    for (Class<?> a : new Class<?>[]{Object[].class, String[].class, CharSequence[].class}) {
        MethodHandle equals2 = equals.asSpreader(a, n).asCollector(a, n);
        assert( (boolean) equals2.invokeWithArguments("me", "me"));
        assert(!(boolean) equals2.invokeWithArguments("me", "thee"));
    }
}
MethodHandle caToString = publicLookup()
  .findStatic(Arrays.class, "toString", methodType(String.class, char[].class));
assertEquals("[A, B, C]", (String) caToString.invokeExact("ABC".toCharArray()));
MethodHandle caString3 = caToString.asCollector(char[].class, 3);
assertEquals("[A, B, C]", (String) caString3.invokeExact('A', 'B', 'C'));
MethodHandle caToString2 = caString3.asSpreader(char[].class, 2);
assertEquals("[A, B, C]", (String) caToString2.invokeExact('A', "BC".toCharArray()));
     * }</pre></blockquote>
     * <p>
     *  if(！typeisConvertibleTo(newType))throw new WrongMethodTypeException("can not convert"+ this +"to"+ n
     * ewType); return asTypeCache = MethodHandleImplmakePairwiseConvert(this,newType,true); }}。
     * 
     * / **创建一个<em>数组扩展</em>方法句柄,它接受一个尾数组参数并将其元素作为位置参数传播新的方法句柄作为<i> target </i>方法句柄适配器的类型将与目标的类型相同,除了目标类型的最终
     * {@code arrayLength}参数被类型为{@code arrayType}的单个数组参数替换,。
     * <p>
     *  如果数组元素类型与原始目标上的任何对应的参数类型不同,则原始目标适用于直接获取数组元素,就像调用{@link #asType asType}
     * <p>
     * 当被调用时,适配器用数组的元素替换后面的数组参数,每个元素作为它自己的参数的参数(保留参数的顺序)。它们通过转换和/或拆箱成对转换为尾随参数的类型目标最后目标被称为最终返回的目标由适配器不变地返回
     * <p>
     *  在调用目标之前,适配器会验证数组是否包含足够多的元素,以便为目标方法句柄提供正确的参数计数(当需要零个元素时,该数组也可能为null)
     * <p>
     * 如果调用适配器时,提供的数组参数没有正确数量的元素,适配器将抛出一个{@link IllegalArgumentException}而不是调用目标
     * <p>
     * 下面是一些简单的数组扩展方法句柄示例：<blockquote> <pre> {@ code MethodHandle equals = publicLookup()findVirtual(Stringclass,"equals",methodType(booleanclass,Objectclass)); assert((boolean)equalsinvokeExact("me",(Object)"me")); assert(！(boolean)equalsinvokeExact("me",(Object)"thee")); //从两个数组传播这两个参数：MethodHandle eq2 = equalsasSpreader(Object [] class,2); assert((boolean)eq2invokeExact(new Object [] {"me","me"}
     * )); assert(！(boolean)eq2invokeExact(new Object [] {"me","thee"})); //尝试从除了2阵列之外的任何东西扩散：for(int n = 0;
     *  n <= 10; n ++){Object [] badArityArgs =(n == 2?null：new Object [n]); try {assert((boolean)eq2invokeExact(badArityArgs)&& false); } catch(IllegalArgumentException ex){} // OK} //从一个String数组传播两个参数：MethodHandle eq2s = equalsasSpreader(String [] class,2); assert((boolean)eq2sinvokeExact(new String [] {"me","me"})); assert(！(boolean)eq2sinvokeExact(new String [] {"me","thee"})); //从第一个数组扩展第二个参数：MethodHandle eq1 = equalsasSpreader(Object [] class,1); assert((boolean)eq1invokeExact("me",new Object [] {"me"})); assert(！(boolean)eq1invokeExact("me",new Object [] {"thee"})); //从0数组或null传播没有参数：MethodHandle eq0 = equalsasSpreader(Object [] class,0); assert((boolean)eq0invokeExact("me",(Object)"me",new Object [0])); assert(！(boolean)eq0invokeExact("me",(Object)"thee",(Object [])null)); // asSpreader和asCollector是近似反转的：for(int n = 0; n <= 2; n ++){for(Class < CharSequence [] class}){MethodHandle equals2 = equalsasSpreader(a,n)asCollector(a,n); assert((boolean)equals2invokeWithArguments("me","me")); assert(！(boolean)equals2invokeWithArguments("me","thee")); }} MethodHandle caToString = publicLookup()findStatic(Arraysclass,"toString",methodType(Stringclass,char [] class)); assertEquals("[A,B,C]",(String)caToStringinvokeExact("ABC"toCharArray())); MethodHandle caString3 = caToStringasCollector(char [] class,3); assertEquals("[A,B,C]",(String)caString3invokeExact('A','B','C')); MethodHandle caToString2 = caString3asSpreader(char []类,2); assertEquals("[A,B,C]",(String)caToString2invokeExact('A',"BC"toCharArray } </pre>
     *  </blockquote>。
     * 
     * 
     * @param arrayType usually {@code Object[]}, the type of the array argument from which to extract the spread arguments
     * @param arrayLength the number of arguments to spread from an incoming array argument
     * @return a new method handle which spreads its final array argument,
     *         before calling the original method handle
     * @throws NullPointerException if {@code arrayType} is a null reference
     * @throws IllegalArgumentException if {@code arrayType} is not an array type,
     *         or if target does not have at least
     *         {@code arrayLength} parameter types,
     *         or if {@code arrayLength} is negative,
     *         or if the resulting method handle's type would have
     *         <a href="MethodHandle.html#maxarity">too many parameters</a>
     * @throws WrongMethodTypeException if the implied {@code asType} call fails
     * @see #asCollector
     */
    public MethodHandle asSpreader(Class<?> arrayType, int arrayLength) {
        MethodType postSpreadType = asSpreaderChecks(arrayType, arrayLength);
        int arity = type().parameterCount();
        int spreadArgPos = arity - arrayLength;
        MethodHandle afterSpread = this.asType(postSpreadType);
        BoundMethodHandle mh = afterSpread.rebind();
        LambdaForm lform = mh.editor().spreadArgumentsForm(1 + spreadArgPos, arrayType, arrayLength);
        MethodType preSpreadType = postSpreadType.replaceParameterTypes(spreadArgPos, arity, arrayType);
        return mh.copyWith(preSpreadType, lform);
    }

    /**
     * See if {@code asSpreader} can be validly called with the given arguments.
     * Return the type of the method handle call after spreading but before conversions.
     * <p>
     * 看看是否可以使用给定的参数有效地调用{@code asSpreader}返回扩展后但转换前的方法句柄调用的类型
     * 
     */
    private MethodType asSpreaderChecks(Class<?> arrayType, int arrayLength) {
        spreadArrayChecks(arrayType, arrayLength);
        int nargs = type().parameterCount();
        if (nargs < arrayLength || arrayLength < 0)
            throw newIllegalArgumentException("bad spread array length");
        Class<?> arrayElement = arrayType.getComponentType();
        MethodType mtype = type();
        boolean match = true, fail = false;
        for (int i = nargs - arrayLength; i < nargs; i++) {
            Class<?> ptype = mtype.parameterType(i);
            if (ptype != arrayElement) {
                match = false;
                if (!MethodType.canConvert(arrayElement, ptype)) {
                    fail = true;
                    break;
                }
            }
        }
        if (match)  return mtype;
        MethodType needType = mtype.asSpreaderType(arrayType, arrayLength);
        if (!fail)  return needType;
        // elicit an error:
        this.asType(needType);
        throw newInternalError("should not return", null);
    }

    private void spreadArrayChecks(Class<?> arrayType, int arrayLength) {
        Class<?> arrayElement = arrayType.getComponentType();
        if (arrayElement == null)
            throw newIllegalArgumentException("not an array type", arrayType);
        if ((arrayLength & 0x7F) != arrayLength) {
            if ((arrayLength & 0xFF) != arrayLength)
                throw newIllegalArgumentException("array length is not legal", arrayLength);
            assert(arrayLength >= 128);
            if (arrayElement == long.class ||
                arrayElement == double.class)
                throw newIllegalArgumentException("array length is not legal for long[] or double[]", arrayLength);
        }
    }

    /**
     * Makes an <em>array-collecting</em> method handle, which accepts a given number of trailing
     * positional arguments and collects them into an array argument.
     * The new method handle adapts, as its <i>target</i>,
     * the current method handle.  The type of the adapter will be
     * the same as the type of the target, except that a single trailing
     * parameter (usually of type {@code arrayType}) is replaced by
     * {@code arrayLength} parameters whose type is element type of {@code arrayType}.
     * <p>
     * If the array type differs from the final argument type on the original target,
     * the original target is adapted to take the array type directly,
     * as if by a call to {@link #asType asType}.
     * <p>
     * When called, the adapter replaces its trailing {@code arrayLength}
     * arguments by a single new array of type {@code arrayType}, whose elements
     * comprise (in order) the replaced arguments.
     * Finally the target is called.
     * What the target eventually returns is returned unchanged by the adapter.
     * <p>
     * (The array may also be a shared constant when {@code arrayLength} is zero.)
     * <p>
     * (<em>Note:</em> The {@code arrayType} is often identical to the last
     * parameter type of the original target.
     * It is an explicit argument for symmetry with {@code asSpreader}, and also
     * to allow the target to use a simple {@code Object} as its last parameter type.)
     * <p>
     * In order to create a collecting adapter which is not restricted to a particular
     * number of collected arguments, use {@link #asVarargsCollector asVarargsCollector} instead.
     * <p>
     * Here are some examples of array-collecting method handles:
     * <blockquote><pre>{@code
MethodHandle deepToString = publicLookup()
  .findStatic(Arrays.class, "deepToString", methodType(String.class, Object[].class));
assertEquals("[won]",   (String) deepToString.invokeExact(new Object[]{"won"}));
MethodHandle ts1 = deepToString.asCollector(Object[].class, 1);
assertEquals(methodType(String.class, Object.class), ts1.type());
//assertEquals("[won]", (String) ts1.invokeExact(         new Object[]{"won"})); //FAIL
assertEquals("[[won]]", (String) ts1.invokeExact((Object) new Object[]{"won"}));
// arrayType can be a subtype of Object[]
MethodHandle ts2 = deepToString.asCollector(String[].class, 2);
assertEquals(methodType(String.class, String.class, String.class), ts2.type());
assertEquals("[two, too]", (String) ts2.invokeExact("two", "too"));
MethodHandle ts0 = deepToString.asCollector(Object[].class, 0);
assertEquals("[]", (String) ts0.invokeExact());
// collectors can be nested, Lisp-style
MethodHandle ts22 = deepToString.asCollector(Object[].class, 3).asCollector(String[].class, 2);
assertEquals("[A, B, [C, D]]", ((String) ts22.invokeExact((Object)'A', (Object)"B", "C", "D")));
// arrayType can be any primitive array type
MethodHandle bytesToString = publicLookup()
  .findStatic(Arrays.class, "toString", methodType(String.class, byte[].class))
  .asCollector(byte[].class, 3);
assertEquals("[1, 2, 3]", (String) bytesToString.invokeExact((byte)1, (byte)2, (byte)3));
MethodHandle longsToString = publicLookup()
  .findStatic(Arrays.class, "toString", methodType(String.class, long[].class))
  .asCollector(long[].class, 1);
assertEquals("[123]", (String) longsToString.invokeExact((long)123));
     * }</pre></blockquote>
     * <p>
     *  创建一个<em>数组收集</em>方法句柄,它接受给定数量的尾随位置参数并将它们收集到数组参数中。
     * 新方法句柄作为其<i>目标</i>方法句柄适配器的类型将与目标的类型相同,除了单个结尾参数(通常是类型为{@code arrayType})被{@code arrayLength}参数替换,其类型是元素
     * 类型为{ @code arrayType}。
     *  创建一个<em>数组收集</em>方法句柄,它接受给定数量的尾随位置参数并将它们收集到数组参数中。
     * <p>
     * 如果数组类型与原始目标上的最终参数类型不同,则原始目标适用于直接获取数组类型,就像调用{@link #asType asType}
     * <p>
     *  当调用时,适配器用一个类型为{@code arrayType}的新数组替换其后面的{@code arrayLength}参数,其元素包括(按顺序)替换的参数。
     * 最后,目标被称为目标最终返回的返回不变由适配器。
     * <p>
     *  (当{@code arrayLength}为0时,数组也可以是共享常量)
     * <p>
     * (<em>注意：</em> {@code arrayType}通常与原始目标的最后一个参数类型相同它是一个显式参数,用于{@code asSpreader}的对称性,也允许目标使用一个简单的{@code Object}
     * 作为它的最后一个参数类型)。
     * <p>
     *  为了创建一个不限于收集的特定数量参数的收集适配器,请改用{@link #asVarargsCollector asVarargsCollector}
     * <p>
     * 这里是一些数组收集方法句柄的例子：<block> </code> {@ code MethodHandle deepToString = publicLookup()findStatic(Arraysclass,"deepToString",methodType(Stringclass,Object [] class) assertEquals("[won]",(String)deepToStringinvokeExact(new Object [] {"won"}
     * )); methodHandle ts1 = deepToStringasCollector(Object [] class,1); assertEquals(methodType(Stringclas
     * s,Objectclass),ts1type()); // assertEquals("[won]",(String)ts1invokeExact(new Object [] {"won"})); //
     *  FAIL assertEquals("[[won]]",(String)ts1invokeExact((Object)new Object [] {"won"}) // arrayType可以是Obj
     * ect []的子类型MethodHandle ts2 = deepToStringasCollector(String [] class,2); assertEquals(methodType(Stri
     * ngclass,Stringclass,Stringclass),ts2type()); assertEquals("[two,too]",(String)ts2invokeExact("two","t
     * oo")); MethodHandle ts0 = deepToStringasCollector(Object [] class,0); assertEquals("[]",(String)ts0in
     * vokeExact()); //收集器可以嵌套,Lisp风格的MethodHandle ts22 = deepToStringasCollector(Object [] class,3)asCollec
     * tor(String [] class,2); assertEquals("[A,B,[C,D]]",((String)ts22invokeExact((Object)'A',(Object)"B","
     * C","D")) // arrayType可以是任何基本数组类型MethodHandle bytesToString = publicLookup()findStatic(Arraysclass,"to
     * String",methodType(Stringclass,byte [] class))asCollector(byte [] class,3); assertEquals("[1,2,3]",(S
     * tring)bytesToStringinvokeExact((byte)1,(byte)2,(byte)3)方法Handle longsToString = publicLookup()findSta
     * tic(Arraysclass,"toString",methodType(Stringclass,long [] class))asCollector类,1); assertEquals("[123]
     * ",(String)longsToStringinvokeExact((long)123)); } </pre> </blockquote>。
     * 
     * 
     * @param arrayType often {@code Object[]}, the type of the array argument which will collect the arguments
     * @param arrayLength the number of arguments to collect into a new array argument
     * @return a new method handle which collects some trailing argument
     *         into an array, before calling the original method handle
     * @throws NullPointerException if {@code arrayType} is a null reference
     * @throws IllegalArgumentException if {@code arrayType} is not an array type
     *         or {@code arrayType} is not assignable to this method handle's trailing parameter type,
     *         or {@code arrayLength} is not a legal array size,
     *         or the resulting method handle's type would have
     *         <a href="MethodHandle.html#maxarity">too many parameters</a>
     * @throws WrongMethodTypeException if the implied {@code asType} call fails
     * @see #asSpreader
     * @see #asVarargsCollector
     */
    public MethodHandle asCollector(Class<?> arrayType, int arrayLength) {
        asCollectorChecks(arrayType, arrayLength);
        int collectArgPos = type().parameterCount() - 1;
        BoundMethodHandle mh = rebind();
        MethodType resultType = type().asCollectorType(arrayType, arrayLength);
        MethodHandle newArray = MethodHandleImpl.varargsArray(arrayType, arrayLength);
        LambdaForm lform = mh.editor().collectArgumentArrayForm(1 + collectArgPos, newArray);
        if (lform != null) {
            return mh.copyWith(resultType, lform);
        }
        lform = mh.editor().collectArgumentsForm(1 + collectArgPos, newArray.type().basicType());
        return mh.copyWithExtendL(resultType, lform, newArray);
    }

    /**
     * See if {@code asCollector} can be validly called with the given arguments.
     * Return false if the last parameter is not an exact match to arrayType.
     * <p>
     * 看看是否可以使用给定的参数有效地调用{@code asCollector}返回false如果最后一个参数不是与arrayType的精确匹配
     * 
     */
    /*non-public*/ boolean asCollectorChecks(Class<?> arrayType, int arrayLength) {
        spreadArrayChecks(arrayType, arrayLength);
        int nargs = type().parameterCount();
        if (nargs != 0) {
            Class<?> lastParam = type().parameterType(nargs-1);
            if (lastParam == arrayType)  return true;
            if (lastParam.isAssignableFrom(arrayType))  return false;
        }
        throw newIllegalArgumentException("array type not assignable to trailing argument", this, arrayType);
    }

    /**
     * Makes a <em>variable arity</em> adapter which is able to accept
     * any number of trailing positional arguments and collect them
     * into an array argument.
     * <p>
     * The type and behavior of the adapter will be the same as
     * the type and behavior of the target, except that certain
     * {@code invoke} and {@code asType} requests can lead to
     * trailing positional arguments being collected into target's
     * trailing parameter.
     * Also, the last parameter type of the adapter will be
     * {@code arrayType}, even if the target has a different
     * last parameter type.
     * <p>
     * This transformation may return {@code this} if the method handle is
     * already of variable arity and its trailing parameter type
     * is identical to {@code arrayType}.
     * <p>
     * When called with {@link #invokeExact invokeExact}, the adapter invokes
     * the target with no argument changes.
     * (<em>Note:</em> This behavior is different from a
     * {@linkplain #asCollector fixed arity collector},
     * since it accepts a whole array of indeterminate length,
     * rather than a fixed number of arguments.)
     * <p>
     * When called with plain, inexact {@link #invoke invoke}, if the caller
     * type is the same as the adapter, the adapter invokes the target as with
     * {@code invokeExact}.
     * (This is the normal behavior for {@code invoke} when types match.)
     * <p>
     * Otherwise, if the caller and adapter arity are the same, and the
     * trailing parameter type of the caller is a reference type identical to
     * or assignable to the trailing parameter type of the adapter,
     * the arguments and return values are converted pairwise,
     * as if by {@link #asType asType} on a fixed arity
     * method handle.
     * <p>
     * Otherwise, the arities differ, or the adapter's trailing parameter
     * type is not assignable from the corresponding caller type.
     * In this case, the adapter replaces all trailing arguments from
     * the original trailing argument position onward, by
     * a new array of type {@code arrayType}, whose elements
     * comprise (in order) the replaced arguments.
     * <p>
     * The caller type must provides as least enough arguments,
     * and of the correct type, to satisfy the target's requirement for
     * positional arguments before the trailing array argument.
     * Thus, the caller must supply, at a minimum, {@code N-1} arguments,
     * where {@code N} is the arity of the target.
     * Also, there must exist conversions from the incoming arguments
     * to the target's arguments.
     * As with other uses of plain {@code invoke}, if these basic
     * requirements are not fulfilled, a {@code WrongMethodTypeException}
     * may be thrown.
     * <p>
     * In all cases, what the target eventually returns is returned unchanged by the adapter.
     * <p>
     * In the final case, it is exactly as if the target method handle were
     * temporarily adapted with a {@linkplain #asCollector fixed arity collector}
     * to the arity required by the caller type.
     * (As with {@code asCollector}, if the array length is zero,
     * a shared constant may be used instead of a new array.
     * If the implied call to {@code asCollector} would throw
     * an {@code IllegalArgumentException} or {@code WrongMethodTypeException},
     * the call to the variable arity adapter must throw
     * {@code WrongMethodTypeException}.)
     * <p>
     * The behavior of {@link #asType asType} is also specialized for
     * variable arity adapters, to maintain the invariant that
     * plain, inexact {@code invoke} is always equivalent to an {@code asType}
     * call to adjust the target type, followed by {@code invokeExact}.
     * Therefore, a variable arity adapter responds
     * to an {@code asType} request by building a fixed arity collector,
     * if and only if the adapter and requested type differ either
     * in arity or trailing argument type.
     * The resulting fixed arity collector has its type further adjusted
     * (if necessary) to the requested type by pairwise conversion,
     * as if by another application of {@code asType}.
     * <p>
     * When a method handle is obtained by executing an {@code ldc} instruction
     * of a {@code CONSTANT_MethodHandle} constant, and the target method is marked
     * as a variable arity method (with the modifier bit {@code 0x0080}),
     * the method handle will accept multiple arities, as if the method handle
     * constant were created by means of a call to {@code asVarargsCollector}.
     * <p>
     * In order to create a collecting adapter which collects a predetermined
     * number of arguments, and whose type reflects this predetermined number,
     * use {@link #asCollector asCollector} instead.
     * <p>
     * No method handle transformations produce new method handles with
     * variable arity, unless they are documented as doing so.
     * Therefore, besides {@code asVarargsCollector},
     * all methods in {@code MethodHandle} and {@code MethodHandles}
     * will return a method handle with fixed arity,
     * except in the cases where they are specified to return their original
     * operand (e.g., {@code asType} of the method handle's own type).
     * <p>
     * Calling {@code asVarargsCollector} on a method handle which is already
     * of variable arity will produce a method handle with the same type and behavior.
     * It may (or may not) return the original variable arity method handle.
     * <p>
     * Here is an example, of a list-making variable arity method handle:
     * <blockquote><pre>{@code
MethodHandle deepToString = publicLookup()
  .findStatic(Arrays.class, "deepToString", methodType(String.class, Object[].class));
MethodHandle ts1 = deepToString.asVarargsCollector(Object[].class);
assertEquals("[won]",   (String) ts1.invokeExact(    new Object[]{"won"}));
assertEquals("[won]",   (String) ts1.invoke(         new Object[]{"won"}));
assertEquals("[won]",   (String) ts1.invoke(                      "won" ));
assertEquals("[[won]]", (String) ts1.invoke((Object) new Object[]{"won"}));
// findStatic of Arrays.asList(...) produces a variable arity method handle:
MethodHandle asList = publicLookup()
  .findStatic(Arrays.class, "asList", methodType(List.class, Object[].class));
assertEquals(methodType(List.class, Object[].class), asList.type());
assert(asList.isVarargsCollector());
assertEquals("[]", asList.invoke().toString());
assertEquals("[1]", asList.invoke(1).toString());
assertEquals("[two, too]", asList.invoke("two", "too").toString());
String[] argv = { "three", "thee", "tee" };
assertEquals("[three, thee, tee]", asList.invoke(argv).toString());
assertEquals("[three, thee, tee]", asList.invoke((Object[])argv).toString());
List ls = (List) asList.invoke((Object)argv);
assertEquals(1, ls.size());
assertEquals("[three, thee, tee]", Arrays.toString((Object[])ls.get(0)));
     * }</pre></blockquote>
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * These rules are designed as a dynamically-typed variation
     * of the Java rules for variable arity methods.
     * In both cases, callers to a variable arity method or method handle
     * can either pass zero or more positional arguments, or else pass
     * pre-collected arrays of any length.  Users should be aware of the
     * special role of the final argument, and of the effect of a
     * type match on that final argument, which determines whether
     * or not a single trailing argument is interpreted as a whole
     * array or a single element of an array to be collected.
     * Note that the dynamic type of the trailing argument has no
     * effect on this decision, only a comparison between the symbolic
     * type descriptor of the call site and the type descriptor of the method handle.)
     *
     * <p>
     *  spreadArrayChecks(arrayType,arrayLength); int nargs = type()parameterCount(); if(nargs！= 0){Class <>> lastParam = type()parameterType(nargs-1); if(lastParam == arrayType)return true; if(lastParamisAssignableFrom(arrayType))return false; }
     *  throw newIllegalArgumentException("array type not assignable to trailing argument",this,arrayType); 
     * }}。
     * 
     *  / **创建一个可以接受任意数量的尾随位置参数的<em>变量arity </em>适配器,并将它们收集到数组参数中
     * <p>
     * 适配器的类型和行为将与目标的类型和行为相同,除了某些{@code invoke}和{@code asType}请求可能导致尾随位置参数被收集到目标的尾随参数中。
     * 另外,适配器的最后一个参数类型将是{@code arrayType},即使目标具有不同的最后一个参数类型。
     * <p>
     *  如果方法句柄已经是变量arity并且其尾参数类型与{@code arrayType}相同,则此转换可能返回{@code this}
     * <p>
     * 当使用{@link #invokeExact invokeExact}调用时,适配器调用没有参数更改的目标(<em>注意：</em>此行为与{@linkplain #asCollector固定收集器}不
     * 同,整数数组的不确定长度,而不是固定数量的参数)。
     * <p>
     *  当使用纯粹,不完全的{@link #invoke invoke}调用时,如果调用者类型与适配器相同,适配器使用{@code invokeExact}调用目标(这是{@code invoke}的正常行为
     * 类型匹配)。
     * <p>
     * 否则,如果调用者和适配器的性质相同,并且调用者的拖尾参数类型是与适配器的拖尾参数类型相同或可分配的引用类型,则参数和返回值被成对转换,如同通过{ @link #asType asType}在固定的方法句
     * 柄。
     * <p>
     *  否则,arity不同,或者适配器的拖尾参数类型不能从相应的调用方类型分配。
     * 在这种情况下,适配器用一个新的数组{@code arrayType}替换原始尾随参数位置向前的所有尾随参数,其元素包括(按顺序)替换的参数。
     * <p>
     * 调用者类型必须提供至少足够的参数和正确的类型,以满足目标对位置参数在尾数组参数之前的要求。因此,调用者必须至少提供{@code N-1}个参数,其中{@code N}是目标的实体。
     * 此外,必须存在从传入的参数到目标的参数的转换与其他对{@code invoke}的使用一样,如果这些基本要求没有被满足,{@code WrongMethodTypeException }可能会抛出。
     * <p>
     *  在所有情况下,目标最终返回的都由适配器不加改变地返回
     * <p>
     * 在最后的情况下,它就好像目标方法句柄暂时适应了一个{@linkplain #asCollector固定的收集器}到调用者类型所需的arity(与{@code asCollector}一样,如果数组长度为
     * 零,可以使用共享常量而不是新数组如果对{@code asCollector}的隐含调用将抛出一个{@code IllegalArgumentException}或{@code WrongMethodTypeException}
     * ,对变量arity适配器的调用必须抛出{@code WrongMethodTypeException})。
     * <p>
     * {@link #asType asType}的行为也专用于变量arity适配器,为了保持不变量,平滑,不精确的{@code invoke}总是等同于调整目标类型的{@code asType}调用,然后调
     * 用{@code invokeExact}因此,当且仅当适配器和请求的类型在arity或trailing参数类型中不同时,变量arity适配器通过构建固定的arity收集器来响应{@code asType}
     * 请求。
     * 所得到的固定arity收集器其类型通过成对转换进一步调整(如果需要)到所请求的类型,如同通过另一个{@code asType}。
     * <p>
     * 当通过执行{@code CONSTANT_MethodHandle}常数的{@code ldc}指令获得方法句柄,并且目标方法被标记为变量arity方法(使用修饰符位{@code 0x0080})时,方
     * 法句柄将接受多个arity,就好像方法句柄常量是通过调用{@code asVarargsCollector}。
     * <p>
     *  为了创建收集预定数量的参数并且其类型反映此预定数量的收集适配器,请改用{@link #asCollector asCollector}
     * <p>
     * 除非{@code asVarargsCollector},{@code MethodHandle}和{@code MethodHandles}中的所有方法都将返回一个具有固定数的方法句柄,除非它们被指定
     * 返回其原始操作数(例如,方法句柄自己的类型的{@code asType}),。
     * <p>
     *  在已经是变量arity的方法句柄上调用{@code asVarargsCollector}将产生一个具有相同类型和行为的方法句柄它可能(或可能不)返回原来的变量arity方法句柄
     * <p>
     * 这里是一个列表制作变量arity方法句柄的例子：<blockquote> <pre> {@ code MethodHandle deepToString = publicLookup()findStatic(Arraysclass,"deepToString",methodType(Stringclass,Object [] class) MethodHandle ts1 = deepToStringasVarargsCollector(Object [] class); assertEquals("[won]",(String)ts1invokeExact(new Object [] {"won"}
     * )); assertEquals("[won]",(String)ts1invoke(new Object [] {"won"})); assertEquals("[won]",(String)ts1i
     * nvoke("won")); assertEquals("[won]]",(String)ts1invoke((Object)new Object [] {"won"})); // findStatic
     * 的ArraysasList()产生一个变量arity方法句柄：MethodHandle asList = publicLookup()findStatic(Arraysclass,"asList",me
     * thodType(Listclass,Object [] class) assertEquals(methodType(List类,Object []类),asListtype()); assert(a
     * sListisVarargsCollector()); assertEquals("[]",asListinvoke()toString()); assertEquals("[1]",asListinv
     * oke(1)toString()); assertEquals("[two,too]",asListinvoke("two","too")toString()); String [] argv = {"three","thee","tee"}
     * ; assertEquals("[three,thee,tee]",asListinvoke(argv)toString()); assertEquals("[three,thee,tee]",asLi
     * 
     * @param arrayType often {@code Object[]}, the type of the array argument which will collect the arguments
     * @return a new method handle which can collect any number of trailing arguments
     *         into an array, before calling the original method handle
     * @throws NullPointerException if {@code arrayType} is a null reference
     * @throws IllegalArgumentException if {@code arrayType} is not an array type
     *         or {@code arrayType} is not assignable to this method handle's trailing parameter type
     * @see #asCollector
     * @see #isVarargsCollector
     * @see #asFixedArity
     */
    public MethodHandle asVarargsCollector(Class<?> arrayType) {
        arrayType.getClass(); // explicit NPE
        boolean lastMatch = asCollectorChecks(arrayType, 0);
        if (isVarargsCollector() && lastMatch)
            return this;
        return MethodHandleImpl.makeVarargsCollector(this, arrayType);
    }

    /**
     * Determines if this method handle
     * supports {@linkplain #asVarargsCollector variable arity} calls.
     * Such method handles arise from the following sources:
     * <ul>
     * <li>a call to {@linkplain #asVarargsCollector asVarargsCollector}
     * <li>a call to a {@linkplain java.lang.invoke.MethodHandles.Lookup lookup method}
     *     which resolves to a variable arity Java method or constructor
     * <li>an {@code ldc} instruction of a {@code CONSTANT_MethodHandle}
     *     which resolves to a variable arity Java method or constructor
     * </ul>
     * <p>
     * stinvoke((Object [])argv)toString()); List ls =(List)asListinvoke((Object)argv); assertEquals(1,lssiz
     * e()); assertEquals("[three,thee,tee]",ArraystoString((Object [])lsget(0))); } </pre> </blockquote>。
     * <p style="font-size:smaller;">
     * <em>讨论：</em>这些规则被设计为变量arity方法的Java规则的动态类型变体在这两种情况下,变量arity方法或方法句柄的调用者可以传递零个或多个位置参数, else传递任何长度的预先收集的数
     * 组用户应该知道最后一个参数的特殊作用,以及类型匹配对最后一个参数的影响,它决定了一个单个尾部参数是否被解释为一个整数数组或要收集的数组的单个元素请注意,拖尾参数的动态类型对此决策没有影响,只有调用点的符
     * 号类型描述符和方法句柄的类型描述符之间的比较)。
     * 
     * 
     * @return true if this method handle accepts more than one arity of plain, inexact {@code invoke} calls
     * @see #asVarargsCollector
     * @see #asFixedArity
     */
    public boolean isVarargsCollector() {
        return false;
    }

    /**
     * Makes a <em>fixed arity</em> method handle which is otherwise
     * equivalent to the current method handle.
     * <p>
     * If the current method handle is not of
     * {@linkplain #asVarargsCollector variable arity},
     * the current method handle is returned.
     * This is true even if the current method handle
     * could not be a valid input to {@code asVarargsCollector}.
     * <p>
     * Otherwise, the resulting fixed-arity method handle has the same
     * type and behavior of the current method handle,
     * except that {@link #isVarargsCollector isVarargsCollector}
     * will be false.
     * The fixed-arity method handle may (or may not) be the
     * a previous argument to {@code asVarargsCollector}.
     * <p>
     * Here is an example, of a list-making variable arity method handle:
     * <blockquote><pre>{@code
MethodHandle asListVar = publicLookup()
  .findStatic(Arrays.class, "asList", methodType(List.class, Object[].class))
  .asVarargsCollector(Object[].class);
MethodHandle asListFix = asListVar.asFixedArity();
assertEquals("[1]", asListVar.invoke(1).toString());
Exception caught = null;
try { asListFix.invoke((Object)1); }
catch (Exception ex) { caught = ex; }
assert(caught instanceof ClassCastException);
assertEquals("[two, too]", asListVar.invoke("two", "too").toString());
try { asListFix.invoke("two", "too"); }
catch (Exception ex) { caught = ex; }
assert(caught instanceof WrongMethodTypeException);
Object[] argv = { "three", "thee", "tee" };
assertEquals("[three, thee, tee]", asListVar.invoke(argv).toString());
assertEquals("[three, thee, tee]", asListFix.invoke(argv).toString());
assertEquals(1, ((List) asListVar.invoke((Object)argv)).size());
assertEquals("[three, thee, tee]", asListFix.invoke((Object)argv).toString());
     * }</pre></blockquote>
     *
     * <p>
     * 确定此方法句柄是否支持{@linkplain #asVarargsCollector variable arity}调用此类方法句柄源于以下来源：
     * <ul>
     *  <li>调用{@linkplain #asVarargsCollector asVarargsCollector} <li>调用{@linkplain javalanginvokeMethodHandlesLookup lookup method}
     * ,该方法解析为一个变量arity Java方法或构造函数<li> {@code ldc}指令{ @code CONSTANT_MethodHandle},它解析为一个变量arity Java方法或构造函
     * 数。
     * </ul>
     * 
     * @return a new method handle which accepts only a fixed number of arguments
     * @see #asVarargsCollector
     * @see #isVarargsCollector
     */
    public MethodHandle asFixedArity() {
        assert(!isVarargsCollector());
        return this;
    }

    /**
     * Binds a value {@code x} to the first argument of a method handle, without invoking it.
     * The new method handle adapts, as its <i>target</i>,
     * the current method handle by binding it to the given argument.
     * The type of the bound handle will be
     * the same as the type of the target, except that a single leading
     * reference parameter will be omitted.
     * <p>
     * When called, the bound handle inserts the given value {@code x}
     * as a new leading argument to the target.  The other arguments are
     * also passed unchanged.
     * What the target eventually returns is returned unchanged by the bound handle.
     * <p>
     * The reference {@code x} must be convertible to the first parameter
     * type of the target.
     * <p>
     * (<em>Note:</em>  Because method handles are immutable, the target method handle
     * retains its original type and behavior.)
     * <p>
     *  创建一个<em>固定的</em>方法句柄,否则等效于当前方法句柄
     * <p>
     * 如果当前方法句柄不是{@linkplain #asVarargsCollector variable arity},则返回当前方法句柄。
     * 即使当前方法句柄不能是{@code asVarargsCollector}的有效输入,也是true,。
     * <p>
     *  否则,得到的固定方法句柄具有与当前方法句柄相同的类型和行为,除了{@link #isVarargsCollector isVarargsCollector}将为false固定方法句柄可以(可能不是)前
     * 一个参数到{@code asVarargsCollector}。
     * <p>
     * 下面是一个列表制作变量arity方法句柄的示例：<blockquote> <pre> {@ code MethodHandle asListVar = publicLookup()findStatic(Arraysclass,"asList",methodType(Listclass,Object [] class))asVarargsCollector Object [] class); MethodHandle asListFix = asListVarasFixedArity(); assertEquals("[1]",asListVarinvoke(1)toString());异常catch = null; try {asListFixinvoke((Object)1); }
     *  catch(Exception ex){caught = ex; } assert(catch instanceof ClassCastException); assertEquals("[two,t
     * oo]",asListVarinvoke("two","too")toString()); try {asListFixinvoke("two","too"); } catch(Exception ex
     * ){caught = ex; } assert(caught instanceof WrongMethodTypeException); Object [] argv = {"three","thee","tee"}
     * ; assertEquals("[three,thee,tee]",asListVarinvoke(argv)toString()); assertEquals("[three,thee,tee]",a
     * sListFixinvoke(argv)toString()); assertEquals(1,((List)asListVarinvoke((Object)argv))size()); assertE
     * quals("[three,thee,tee]",asListFixinvoke((Object)argv)toString()); } </pre> </blockquote>。
     * 
     * 
     * @param x  the value to bind to the first argument of the target
     * @return a new method handle which prepends the given value to the incoming
     *         argument list, before calling the original method handle
     * @throws IllegalArgumentException if the target does not have a
     *         leading parameter type that is a reference type
     * @throws ClassCastException if {@code x} cannot be converted
     *         to the leading parameter type of the target
     * @see MethodHandles#insertArguments
     */
    public MethodHandle bindTo(Object x) {
        x = type.leadingReferenceParameter().cast(x);  // throw CCE if needed
        return bindArgumentL(0, x);
    }

    /**
     * Returns a string representation of the method handle,
     * starting with the string {@code "MethodHandle"} and
     * ending with the string representation of the method handle's type.
     * In other words, this method returns a string equal to the value of:
     * <blockquote><pre>{@code
     * "MethodHandle" + type().toString()
     * }</pre></blockquote>
     * <p>
     * (<em>Note:</em>  Future releases of this API may add further information
     * to the string representation.
     * Therefore, the present syntax should not be parsed by applications.)
     *
     * <p>
     * 将值{@code x}绑定到方法句柄的第一个参数,而不调用它新的方法句柄通过将当前方法句柄绑定到给定的参数来适配其<i> target </i>的绑定句柄将与目标的类型相同,除了将省略单个前导参考参数。
     * <p>
     *  当被调用时,绑定句柄插入给定的值{@code x}作为目标的新的引导参数。其他的参数也不被改变。目标最终返回什么被绑定的句柄
     * <p>
     *  引用{@code x}必须可转换为目标的第一个参数类型
     * <p>
     *  (<em>注意：</em>由于方法句柄是不可变的,目标方法句柄保留其原始类型和行为)
     * 
     * 
     * @return a string representation of the method handle
     */
    @Override
    public String toString() {
        if (DEBUG_METHOD_HANDLE_NAMES)  return "MethodHandle"+debugString();
        return standardString();
    }
    String standardString() {
        return "MethodHandle"+type;
    }
    /** Return a string with a several lines describing the method handle structure.
     *  This string would be suitable for display in an IDE debugger.
     * <p>
     * 返回方法句柄的字符串表示形式,以字符串{@code"MethodHandle"}开头,并以方法句柄类型的字符串表示形式结束换句话说,此方法返回一个等于以下值的字符串：<blockquote> < pre>
     *  {@ code"MethodHandle"+ type()toString()} </pre> </blockquote>。
     * <p>
     *  (<em>注意：</em>此API的未来版本可能会向字符串表示形式添加更多信息)因此,应用程序不应解析当前语法)
     * 
     */
    String debugString() {
        return type+" : "+internalForm()+internalProperties();
    }

    //// Implementation methods.
    //// Sub-classes can override these default implementations.
    //// All these methods assume arguments are already validated.

    // Other transforms to do:  convert, explicitCast, permute, drop, filter, fold, GWT, catch

    BoundMethodHandle bindArgumentL(int pos, Object value) {
        return rebind().bindArgumentL(pos, value);
    }

    /*non-public*/
    MethodHandle setVarargs(MemberName member) throws IllegalAccessException {
        if (!member.isVarargs())  return this;
        Class<?> arrayType = type().lastParameterType();
        if (arrayType.isArray()) {
            return MethodHandleImpl.makeVarargsCollector(this, arrayType);
        }
        throw member.makeAccessException("cannot make variable arity", null);
    }

    /*non-public*/
    MethodHandle viewAsType(MethodType newType, boolean strict) {
        // No actual conversions, just a new view of the same method.
        // Note that this operation must not produce a DirectMethodHandle,
        // because retyped DMHs, like any transformed MHs,
        // cannot be cracked into MethodHandleInfo.
        assert viewAsTypeChecks(newType, strict);
        BoundMethodHandle mh = rebind();
        assert(!((MethodHandle)mh instanceof DirectMethodHandle));
        return mh.copyWith(newType, mh.form);
    }

    /*non-public*/
    boolean viewAsTypeChecks(MethodType newType, boolean strict) {
        if (strict) {
            assert(type().isViewableAs(newType, true))
                : Arrays.asList(this, newType);
        } else {
            assert(type().basicType().isViewableAs(newType.basicType(), true))
                : Arrays.asList(this, newType);
        }
        return true;
    }

    // Decoding

    /*non-public*/
    LambdaForm internalForm() {
        return form;
    }

    /*non-public*/
    MemberName internalMemberName() {
        return null;  // DMH returns DMH.member
    }

    /*non-public*/
    Class<?> internalCallerClass() {
        return null;  // caller-bound MH for @CallerSensitive method returns caller
    }

    /*non-public*/
    MethodHandleImpl.Intrinsic intrinsicName() {
        // no special intrinsic meaning to most MHs
        return MethodHandleImpl.Intrinsic.NONE;
    }

    /*non-public*/
    MethodHandle withInternalMemberName(MemberName member, boolean isInvokeSpecial) {
        if (member != null) {
            return MethodHandleImpl.makeWrappedMember(this, member, isInvokeSpecial);
        } else if (internalMemberName() == null) {
            // The required internaMemberName is null, and this MH (like most) doesn't have one.
            return this;
        } else {
            // The following case is rare. Mask the internalMemberName by wrapping the MH in a BMH.
            MethodHandle result = rebind();
            assert (result.internalMemberName() == null);
            return result;
        }
    }

    /*non-public*/
    boolean isInvokeSpecial() {
        return false;  // DMH.Special returns true
    }

    /*non-public*/
    Object internalValues() {
        return null;
    }

    /*non-public*/
    Object internalProperties() {
        // Override to something to follow this.form, like "\n& FOO=bar"
        return "";
    }

    //// Method handle implementation methods.
    //// Sub-classes can override these default implementations.
    //// All these methods assume arguments are already validated.

    /*non-public*/
    abstract MethodHandle copyWith(MethodType mt, LambdaForm lf);

    /** Require this method handle to be a BMH, or else replace it with a "wrapper" BMH.
     *  Many transforms are implemented only for BMHs.
     * <p>
     *  此字符串将适合在IDE调试器中显示
     * 
     * 
     *  @return a behaviorally equivalent BMH
     */
    abstract BoundMethodHandle rebind();

    /**
     * Replace the old lambda form of this method handle with a new one.
     * The new one must be functionally equivalent to the old one.
     * Threads may continue running the old form indefinitely,
     * but it is likely that the new one will be preferred for new executions.
     * Use with discretion.
     * <p>
     *  许多变换仅针对BMH实现
     * 
     */
    /*non-public*/
    void updateForm(LambdaForm newForm) {
        if (form == newForm)  return;
        newForm.prepare();  // as in MethodHandle.<init>
        UNSAFE.putObject(this, FORM_OFFSET, newForm);
        UNSAFE.fullFence();
    }

    private static final long FORM_OFFSET;
    static {
        try {
            FORM_OFFSET = UNSAFE.objectFieldOffset(MethodHandle.class.getDeclaredField("form"));
        } catch (ReflectiveOperationException ex) {
            throw newInternalError(ex);
        }
    }
}
