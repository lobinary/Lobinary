/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.instrument;

import  java.io.File;
import  java.io.IOException;
import  java.util.jar.JarFile;

/*
 * Copyright 2003 Wily Technology, Inc.
 * <p>
 *  版权所有2003 Wily Technology,Inc
 * 
 */

/**
 * This class provides services needed to instrument Java
 * programming language code.
 * Instrumentation is the addition of byte-codes to methods for the
 * purpose of gathering data to be utilized by tools.
 * Since the changes are purely additive, these tools do not modify
 * application state or behavior.
 * Examples of such benign tools include monitoring agents, profilers,
 * coverage analyzers, and event loggers.
 *
 * <P>
 * There are two ways to obtain an instance of the
 * <code>Instrumentation</code> interface:
 *
 * <ol>
 *   <li><p> When a JVM is launched in a way that indicates an agent
 *     class. In that case an <code>Instrumentation</code> instance
 *     is passed to the <code>premain</code> method of the agent class.
 *     </p></li>
 *   <li><p> When a JVM provides a mechanism to start agents sometime
 *     after the JVM is launched. In that case an <code>Instrumentation</code>
 *     instance is passed to the <code>agentmain</code> method of the
 *     agent code. </p> </li>
 * </ol>
 * <p>
 * These mechanisms are described in the
 * {@linkplain java.lang.instrument package specification}.
 * <p>
 * Once an agent acquires an <code>Instrumentation</code> instance,
 * the agent may call methods on the instance at any time.
 *
 * <p>
 *  这个类提供仪器Java编程语言代码所需的服务Instrumentation是为了收集要由工具使用的数据而向方法中添加字节码由于这些更改是完全相加的,因此这些工具不会修改应用程序状态或行为。
 * 这种良性工具包括监视代理,分析器,覆盖分析器和事件记录器。
 * 
 * <P>
 *  有两种方法来获取<code> Instrumentation </code>接口的实例：
 * 
 * <ol>
 * <li> <p>当JVM以指示代理类的方式启动时在这种情况下,将<code> Instrumentation </code>实例传递给代理类的<code> premain </code> / p> </li>
 *  <li> <p>当JVM提供一种机制在启动JVM后的某个时间启动代理在这种情况下,<code> Instrumentation </code>实例被传递到<code> agentmain < / code>
 * 代理代码</p>的方法。
 * </ol>
 * <p>
 *  这些机制在{@linkplain javalanginstrument package specification}
 * <p>
 *  代理程序获取<code> Instrumentation </code>实例后,代理程序可以随时调用实例上的方法
 * 
 * 
 * @since   1.5
 */
public interface Instrumentation {
    /**
     * Registers the supplied transformer. All future class definitions
     * will be seen by the transformer, except definitions of classes upon which any
     * registered transformer is dependent.
     * The transformer is called when classes are loaded, when they are
     * {@linkplain #redefineClasses redefined}. and if <code>canRetransform</code> is true,
     * when they are {@linkplain #retransformClasses retransformed}.
     * See {@link java.lang.instrument.ClassFileTransformer#transform
     * ClassFileTransformer.transform} for the order
     * of transform calls.
     * If a transformer throws
     * an exception during execution, the JVM will still call the other registered
     * transformers in order. The same transformer may be added more than once,
     * but it is strongly discouraged -- avoid this by creating a new instance of
     * transformer class.
     * <P>
     * This method is intended for use in instrumentation, as described in the
     * {@linkplain Instrumentation class specification}.
     *
     * <p>
     * 寄存器提供的变量所有未来的类定义将被变压器看到,除了定义任何注册的变量所依赖的类当变量被加载时,当{@linkplain #redefineClasses redefined}和<code> canRe
     * transform </code>为true,当它们是{@linkplain #retransformClasses retransformed}请参阅{@link javalanginstrumentClassFileTransformer#transform ClassFileTransformertransform}
     * 的转换调用顺序如果变压器在执行期间抛出异常,JVM仍将调用其他注册变压器按顺序同一个变压器可以多次添加,但是强烈不鼓励 - 通过创建一个新的变压器类实例。
     * <P>
     * 此方法旨在用于仪器,如{@linkplain Instrumentation类规范}中所述,
     * 
     * 
     * @param transformer          the transformer to register
     * @param canRetransform       can this transformer's transformations be retransformed
     * @throws java.lang.NullPointerException if passed a <code>null</code> transformer
     * @throws java.lang.UnsupportedOperationException if <code>canRetransform</code>
     * is true and the current configuration of the JVM does not allow
     * retransformation ({@link #isRetransformClassesSupported} is false)
     * @since 1.6
     */
    void
    addTransformer(ClassFileTransformer transformer, boolean canRetransform);

    /**
     * Registers the supplied transformer.
     * <P>
     * Same as <code>addTransformer(transformer, false)</code>.
     *
     * <p>
     *  注册提供的变压器
     * <P>
     *  与<code> addTransformer(transformer,false)</code>相同
     * 
     * 
     * @param transformer          the transformer to register
     * @throws java.lang.NullPointerException if passed a <code>null</code> transformer
     * @see    #addTransformer(ClassFileTransformer,boolean)
     */
    void
    addTransformer(ClassFileTransformer transformer);

    /**
     * Unregisters the supplied transformer. Future class definitions will
     * not be shown to the transformer. Removes the most-recently-added matching
     * instance of the transformer. Due to the multi-threaded nature of
     * class loading, it is possible for a transformer to receive calls
     * after it has been removed. Transformers should be written defensively
     * to expect this situation.
     *
     * <p>
     *  取消注册提供的变量未来的类定义将不会显示给变压器删除变压器的最近添加的匹配实例由于类加载的多线程特性,变压器可能在它已经被接收之后接收调用删除变形金刚应该写防御预期这种情况
     * 
     * 
     * @param transformer          the transformer to unregister
     * @return  true if the transformer was found and removed, false if the
     *           transformer was not found
     * @throws java.lang.NullPointerException if passed a <code>null</code> transformer
     */
    boolean
    removeTransformer(ClassFileTransformer transformer);

    /**
     * Returns whether or not the current JVM configuration supports retransformation
     * of classes.
     * The ability to retransform an already loaded class is an optional capability
     * of a JVM.
     * Retransformation will only be supported if the
     * <code>Can-Retransform-Classes</code> manifest attribute is set to
     * <code>true</code> in the agent JAR file (as described in the
     * {@linkplain java.lang.instrument package specification}) and the JVM supports
     * this capability.
     * During a single instantiation of a single JVM, multiple calls to this
     * method will always return the same answer.
     * <p>
     * 返回当前JVM配置是否支持类的重新转换重新转换已经加载的类的能力是JVM重新转换的可选功能,只有在设置了<code> Can-Retransform-Classes </code>清单属性到代理JAR文
     * 件中的<code> true </code>(如{@linkplain javalanginstrument package specification}中所述),并且JVM支持此功能。
     * 在单个JVM的单实例化期间,对此方法的多个调用将始终返回同样的答案。
     * 
     * 
     * @return  true if the current JVM configuration supports retransformation of
     *          classes, false if not.
     * @see #retransformClasses
     * @since 1.6
     */
    boolean
    isRetransformClassesSupported();

    /**
     * Retransform the supplied set of classes.
     *
     * <P>
     * This function facilitates the instrumentation
     * of already loaded classes.
     * When classes are initially loaded or when they are
     * {@linkplain #redefineClasses redefined},
     * the initial class file bytes can be transformed with the
     * {@link java.lang.instrument.ClassFileTransformer ClassFileTransformer}.
     * This function reruns the transformation process
     * (whether or not a transformation has previously occurred).
     * This retransformation follows these steps:
     *  <ul>
     *    <li>starting from the initial class file bytes
     *    </li>
     *    <li>for each transformer that was added with <code>canRetransform</code>
     *      false, the bytes returned by
     *      {@link java.lang.instrument.ClassFileTransformer#transform transform}
     *      during the last class load or redefine are
     *      reused as the output of the transformation; note that this is
     *      equivalent to reapplying the previous transformation, unaltered;
     *      except that
     *      {@link java.lang.instrument.ClassFileTransformer#transform transform}
     *      is not called
     *    </li>
     *    <li>for each transformer that was added with <code>canRetransform</code>
     *      true, the
     *      {@link java.lang.instrument.ClassFileTransformer#transform transform}
     *      method is called in these transformers
     *    </li>
     *    <li>the transformed class file bytes are installed as the new
     *      definition of the class
     *    </li>
     *  </ul>
     * <P>
     *
     * The order of transformation is described in the
     * {@link java.lang.instrument.ClassFileTransformer#transform transform} method.
     * This same order is used in the automatic reapplication of retransformation
     * incapable transforms.
     * <P>
     *
     * The initial class file bytes represent the bytes passed to
     * {@link java.lang.ClassLoader#defineClass ClassLoader.defineClass} or
     * {@link #redefineClasses redefineClasses}
     * (before any transformations
     *  were applied), however they might not exactly match them.
     *  The constant pool might not have the same layout or contents.
     *  The constant pool may have more or fewer entries.
     *  Constant pool entries may be in a different order; however,
     *  constant pool indices in the bytecodes of methods will correspond.
     *  Some attributes may not be present.
     *  Where order is not meaningful, for example the order of methods,
     *  order might not be preserved.
     *
     * <P>
     * This method operates on
     * a set in order to allow interdependent changes to more than one class at the same time
     * (a retransformation of class A can require a retransformation of class B).
     *
     * <P>
     * If a retransformed method has active stack frames, those active frames continue to
     * run the bytecodes of the original method.
     * The retransformed method will be used on new invokes.
     *
     * <P>
     * This method does not cause any initialization except that which would occur
     * under the customary JVM semantics. In other words, redefining a class
     * does not cause its initializers to be run. The values of static variables
     * will remain as they were prior to the call.
     *
     * <P>
     * Instances of the retransformed class are not affected.
     *
     * <P>
     * The retransformation may change method bodies, the constant pool and attributes.
     * The retransformation must not add, remove or rename fields or methods, change the
     * signatures of methods, or change inheritance.  These restrictions maybe be
     * lifted in future versions.  The class file bytes are not checked, verified and installed
     * until after the transformations have been applied, if the resultant bytes are in
     * error this method will throw an exception.
     *
     * <P>
     * If this method throws an exception, no classes have been retransformed.
     * <P>
     * This method is intended for use in instrumentation, as described in the
     * {@linkplain Instrumentation class specification}.
     *
     * <p>
     *  重新转换提供的类集
     * 
     * <P>
     * 此函数便于已加载类的检测当类最初加载时或当它们是{@linkplain #redefineClasses redefined}时,可以使用{@link javalanginstrumentClassFileTransformer ClassFileTransformer}
     * 转换初始类文件字节。
     * 此函数重新运行转换过程或者先前没有发生转换)。此重新转换遵循以下步骤：。
     * <ul>
     *  <li>从初始类文件字节开始
     * </li>
     * <li>对于添加了<code> canRetransform </code> false的每个变换器,在最后一次类加载或重新定义期间由{@link javalanginstrumentClassFileTransformer#transform transform}
     * 返回的字节被重复用作转换的输出;注意这等同于重新应用先前的变换,未改变;只是不调用{@link javalanginstrumentClassFileTransformer#transform transform}
     * 。
     * </li>
     *  <li>对于添加了<code> canRetransform </code> true的每个转换器,在这些转换器中调用{@link javalanginstrumentClassFileTransformer#transform transform}
     * 方法。
     * </li>
     *  <li>将转换后的类文件字节安装为类的新定义
     * </li>
     * </ul>
     * <P>
     * 
     * 转换的顺序在{@link javalanginstrumentClassFileTransformer#transform transform}方法中描述。同样的顺序用于自动重新应用重新转换不能转换
     * <P>
     * 
     * 初始类文件字节表示传递给{@link javalangClassLoader#defineClass ClassLoaderdefineClass}或{@link #redefineClasses redefineClasses}
     * (在应用任何转换之前)的字节,但它们可能不完全匹配常量池可能没有相同的布局或内容常量池可以具有更多或更少的条目常量池条目可以按不同的顺序;然而,方法的字节码中的常量池索引将对应一些属性可能不存在其中顺序
     * 无意义,例如方法的顺序,顺序可能不被保留。
     * 
     * <P>
     * 该方法对一组进行操作,以便允许在同一时间对多于一个类的相互依赖的改变(A类的重新转换可以要求B类的重新转换)
     * 
     * <P>
     *  如果重新转换的方法具有活动堆栈帧,那些活动帧继续运行原始方法的字节码。重新转换的方法将用于新的调用
     * 
     * <P>
     *  此方法不会导致任何初始化,除了在常规JVM语义下会发生的情况。换句话说,重新定义类不会导致其初始化器运行静态变量的值将保持为它们在调用之前的值
     * 
     * <P>
     *  重新转换的类的实例不受影响
     * 
     * <P>
     * 重新转换可能会改变方法体,常量池和属性重新转换不能添加,删除或重命名字段或方法,更改方法的签名或更改继承这些限制可能会在将来的版本中取消类文件字节未检查,验证并安装,直到应用变换后,如果结果字节有错误,
     * 此方法将抛出异常。
     * 
     * <P>
     *  如果此方法抛出异常,则不会重新转换类
     * <P>
     *  此方法旨在用于仪器,如{@linkplain Instrumentation类规范}中所述,
     * 
     * 
     * @param classes array of classes to retransform;
     *                a zero-length array is allowed, in this case, this method does nothing
     * @throws java.lang.instrument.UnmodifiableClassException if a specified class cannot be modified
     * ({@link #isModifiableClass} would return <code>false</code>)
     * @throws java.lang.UnsupportedOperationException if the current configuration of the JVM does not allow
     * retransformation ({@link #isRetransformClassesSupported} is false) or the retransformation attempted
     * to make unsupported changes
     * @throws java.lang.ClassFormatError if the data did not contain a valid class
     * @throws java.lang.NoClassDefFoundError if the name in the class file is not equal to the name of the class
     * @throws java.lang.UnsupportedClassVersionError if the class file version numbers are not supported
     * @throws java.lang.ClassCircularityError if the new classes contain a circularity
     * @throws java.lang.LinkageError if a linkage error occurs
     * @throws java.lang.NullPointerException if the supplied classes  array or any of its components
     *                                        is <code>null</code>.
     *
     * @see #isRetransformClassesSupported
     * @see #addTransformer
     * @see java.lang.instrument.ClassFileTransformer
     * @since 1.6
     */
    void
    retransformClasses(Class<?>... classes) throws UnmodifiableClassException;

    /**
     * Returns whether or not the current JVM configuration supports redefinition
     * of classes.
     * The ability to redefine an already loaded class is an optional capability
     * of a JVM.
     * Redefinition will only be supported if the
     * <code>Can-Redefine-Classes</code> manifest attribute is set to
     * <code>true</code> in the agent JAR file (as described in the
     * {@linkplain java.lang.instrument package specification}) and the JVM supports
     * this capability.
     * During a single instantiation of a single JVM, multiple calls to this
     * method will always return the same answer.
     * <p>
     * 返回当前JVM配置是否支持类的重新定义重新定义已加载类的能力是JVM重定义的可选功能,只有在设置了<code> Can-Redefine-Classes </code>清单属性到代理JAR文件中的<code>
     *  true </code>(如{@linkplain javalanginstrument package specification}中所述),并且JVM支持此功能。
     * 在单个JVM的单实例化期间,对此方法的多个调用将始终返回同样的答案。
     * 
     * 
     * @return  true if the current JVM configuration supports redefinition of classes,
     * false if not.
     * @see #redefineClasses
     */
    boolean
    isRedefineClassesSupported();

    /**
     * Redefine the supplied set of classes using the supplied class files.
     *
     * <P>
     * This method is used to replace the definition of a class without reference
     * to the existing class file bytes, as one might do when recompiling from source
     * for fix-and-continue debugging.
     * Where the existing class file bytes are to be transformed (for
     * example in bytecode instrumentation)
     * {@link #retransformClasses retransformClasses}
     * should be used.
     *
     * <P>
     * This method operates on
     * a set in order to allow interdependent changes to more than one class at the same time
     * (a redefinition of class A can require a redefinition of class B).
     *
     * <P>
     * If a redefined method has active stack frames, those active frames continue to
     * run the bytecodes of the original method.
     * The redefined method will be used on new invokes.
     *
     * <P>
     * This method does not cause any initialization except that which would occur
     * under the customary JVM semantics. In other words, redefining a class
     * does not cause its initializers to be run. The values of static variables
     * will remain as they were prior to the call.
     *
     * <P>
     * Instances of the redefined class are not affected.
     *
     * <P>
     * The redefinition may change method bodies, the constant pool and attributes.
     * The redefinition must not add, remove or rename fields or methods, change the
     * signatures of methods, or change inheritance.  These restrictions maybe be
     * lifted in future versions.  The class file bytes are not checked, verified and installed
     * until after the transformations have been applied, if the resultant bytes are in
     * error this method will throw an exception.
     *
     * <P>
     * If this method throws an exception, no classes have been redefined.
     * <P>
     * This method is intended for use in instrumentation, as described in the
     * {@linkplain Instrumentation class specification}.
     *
     * <p>
     *  使用提供的类文件重新定义提供的一组类
     * 
     * <P>
     * 此方法用于替换类的定义,而不引用现有类文件字节,因为可能在从源重新编译以进行修复并继续调试时执行此操作在要转换现有类文件字节的位置(例如,以字节码instrumentation){@link #retransformClasses retransformClasses}
     * 。
     * 
     * <P>
     *  此方法对一组进行操作,以允许同时对多个类进行相互依赖的更改(A类的重新定义可能需要重新定义B类)
     * 
     * <P>
     *  如果重定义的方法具有活动的堆栈帧,那些活动帧继续运行原始方法的字节码。重新定义的方法将用于新的调用
     * 
     * <P>
     * 此方法不会导致任何初始化,除了在常规JVM语义下会发生的情况。换句话说,重新定义类不会导致其初始化器运行静态变量的值将保持为它们在调用之前的值
     * 
     * <P>
     *  重定义类的实例不受影响
     * 
     * <P>
     *  重定义可能会更改方法体,常量池和属性重定义不能添加,删除或重命名字段或方法,更改方法的签名或更改继承这些限制可能会在将来的版本中取消类文件字节未检查,验证并安装,直到应用变换后,如果结果字节有错误,此
     * 方法将抛出异常。
     * 
     * <P>
     * 如果此方法抛出异常,则不会重新定义任何类
     * <P>
     *  此方法旨在用于仪器,如{@linkplain Instrumentation类规范}中所述,
     * 
     * 
     * @param definitions array of classes to redefine with corresponding definitions;
     *                    a zero-length array is allowed, in this case, this method does nothing
     * @throws java.lang.instrument.UnmodifiableClassException if a specified class cannot be modified
     * ({@link #isModifiableClass} would return <code>false</code>)
     * @throws java.lang.UnsupportedOperationException if the current configuration of the JVM does not allow
     * redefinition ({@link #isRedefineClassesSupported} is false) or the redefinition attempted
     * to make unsupported changes
     * @throws java.lang.ClassFormatError if the data did not contain a valid class
     * @throws java.lang.NoClassDefFoundError if the name in the class file is not equal to the name of the class
     * @throws java.lang.UnsupportedClassVersionError if the class file version numbers are not supported
     * @throws java.lang.ClassCircularityError if the new classes contain a circularity
     * @throws java.lang.LinkageError if a linkage error occurs
     * @throws java.lang.NullPointerException if the supplied definitions array or any of its components
     * is <code>null</code>
     * @throws java.lang.ClassNotFoundException Can never be thrown (present for compatibility reasons only)
     *
     * @see #isRedefineClassesSupported
     * @see #addTransformer
     * @see java.lang.instrument.ClassFileTransformer
     */
    void
    redefineClasses(ClassDefinition... definitions)
        throws  ClassNotFoundException, UnmodifiableClassException;


    /**
     * Determines whether a class is modifiable by
     * {@linkplain #retransformClasses retransformation}
     * or {@linkplain #redefineClasses redefinition}.
     * If a class is modifiable then this method returns <code>true</code>.
     * If a class is not modifiable then this method returns <code>false</code>.
     * <P>
     * For a class to be retransformed, {@link #isRetransformClassesSupported} must also be true.
     * But the value of <code>isRetransformClassesSupported()</code> does not influence the value
     * returned by this function.
     * For a class to be redefined, {@link #isRedefineClassesSupported} must also be true.
     * But the value of <code>isRedefineClassesSupported()</code> does not influence the value
     * returned by this function.
     * <P>
     * Primitive classes (for example, <code>java.lang.Integer.TYPE</code>)
     * and array classes are never modifiable.
     *
     * <p>
     *  确定类是否可以通过{@linkplain #retransformClasses重新转换}或{@linkplain #redefineClasses重新定义}修改如果类是可修改的,那么此方法返回<code>
     *  true </code>如果类不可修改, code> false </code>。
     * <P>
     * 对于要重新转换的类,{@link #isRetransformClassesSupported}也必须为true但<code> isRetransformClassesSupported()</code>
     * 的值不影响此函数返回的值对于要重新定义的类,{@link #isRedefineClassesSupported}也必须为true但<code> isRedefineClassesSupported()
     * </code>的值不影响此函数返回的值。
     * <P>
     *  原始类(例如,<code> javalangIntegerTYPE </code>)和数组类从不可修改
     * 
     * 
     * @param theClass the class to check for being modifiable
     * @return whether or not the argument class is modifiable
     * @throws java.lang.NullPointerException if the specified class is <code>null</code>.
     *
     * @see #retransformClasses
     * @see #isRetransformClassesSupported
     * @see #redefineClasses
     * @see #isRedefineClassesSupported
     * @since 1.6
     */
    boolean
    isModifiableClass(Class<?> theClass);

    /**
     * Returns an array of all classes currently loaded by the JVM.
     *
     * <p>
     *  返回当前由JVM加载的所有类的数组
     * 
     * 
     * @return an array containing all the classes loaded by the JVM, zero-length if there are none
     */
    @SuppressWarnings("rawtypes")
    Class[]
    getAllLoadedClasses();

    /**
     * Returns an array of all classes for which <code>loader</code> is an initiating loader.
     * If the supplied loader is <code>null</code>, classes initiated by the bootstrap class
     * loader are returned.
     *
     * <p>
     *  返回<code> loader </code>是启动加载器的所有类的数组如果提供的加载器是<code> null </code>,则返回由引导类加载器启动的类
     * 
     * 
     * @param loader          the loader whose initiated class list will be returned
     * @return an array containing all the classes for which loader is an initiating loader,
     *          zero-length if there are none
     */
    @SuppressWarnings("rawtypes")
    Class[]
    getInitiatedClasses(ClassLoader loader);

    /**
     * Returns an implementation-specific approximation of the amount of storage consumed by
     * the specified object. The result may include some or all of the object's overhead,
     * and thus is useful for comparison within an implementation but not between implementations.
     *
     * The estimate may change during a single invocation of the JVM.
     *
     * <p>
     * 返回指定对象所消耗的存储量的实现特定近似结果该结果可能包括对象的一些或全部开销,因此对于实现中的比较是有用的,但是实现之间的比较不是有用的
     * 
     *  估计可能在JVM的单个调用期间改变
     * 
     * 
     * @param objectToSize     the object to size
     * @return an implementation-specific approximation of the amount of storage consumed by the specified object
     * @throws java.lang.NullPointerException if the supplied Object is <code>null</code>.
     */
    long
    getObjectSize(Object objectToSize);


    /**
     * Specifies a JAR file with instrumentation classes to be defined by the
     * bootstrap class loader.
     *
     * <p> When the virtual machine's built-in class loader, known as the "bootstrap
     * class loader", unsuccessfully searches for a class, the entries in the {@link
     * java.util.jar.JarFile JAR file} will be searched as well.
     *
     * <p> This method may be used multiple times to add multiple JAR files to be
     * searched in the order that this method was invoked.
     *
     * <p> The agent should take care to ensure that the JAR does not contain any
     * classes or resources other than those to be defined by the bootstrap
     * class loader for the purpose of instrumentation.
     * Failure to observe this warning could result in unexpected
     * behavior that is difficult to diagnose. For example, suppose there is a
     * loader L, and L's parent for delegation is the bootstrap class loader.
     * Furthermore, a method in class C, a class defined by L, makes reference to
     * a non-public accessor class C$1. If the JAR file contains a class C$1 then
     * the delegation to the bootstrap class loader will cause C$1 to be defined
     * by the bootstrap class loader. In this example an <code>IllegalAccessError</code>
     * will be thrown that may cause the application to fail. One approach to
     * avoiding these types of issues, is to use a unique package name for the
     * instrumentation classes.
     *
     * <p>
     * <cite>The Java&trade; Virtual Machine Specification</cite>
     * specifies that a subsequent attempt to resolve a symbolic
     * reference that the Java virtual machine has previously unsuccessfully attempted
     * to resolve always fails with the same error that was thrown as a result of the
     * initial resolution attempt. Consequently, if the JAR file contains an entry
     * that corresponds to a class for which the Java virtual machine has
     * unsuccessfully attempted to resolve a reference, then subsequent attempts to
     * resolve that reference will fail with the same error as the initial attempt.
     *
     * <p>
     *  指定具有要由引导类装入器定义的检测类的JAR文件
     * 
     *  <p>当虚拟机的内置类加载器(称为"引导类加载器")未能成功搜索某个类时,将搜索{@link javautiljarJarFile JAR文件}中的条目
     * 
     *  <p>此方法可以多次使用,以按照调用此方法的顺序添加多个要搜索的JAR文件
     * 
     * <p>代理应注意确保JAR不包含任何类别或资源,而不是由引导类加载器为仪器定义的类或资源。
     * 不遵守此警告可能会导致意外的行为,这很难诊断例如,假设有一个加载器L,并且L的父代表委托是引导类加载器此外,在由C定义的类C中的方法引用非公共访问器类C $ 1如果JAR文件包含类C $ 1,则对引导类
     * 加载器的委派将导致C $ 1由引导类加载器定义在此示例中,将抛出可能导致应用程序失败的<code> IllegalAccessError </code>避免这些类型的问题的一种方法是对仪器类使用唯一的包
     * 名称。
     * <p>代理应注意确保JAR不包含任何类别或资源,而不是由引导类加载器为仪器定义的类或资源。
     * 
     * <p>
     * <cite> Java&trade;虚拟机规范</cite>指定解析Java虚拟机先前未成功尝试解析的符号引用的后续尝试失败,并且具有与初始解析尝试的结果相同的错误。
     * 因此,如果JAR文件包含与Java虚拟机未成功尝试解析引用的类相对应的条目,则解析该引用的后续尝试将失败,并出现与初始尝试相同的错误。
     * 
     * 
     * @param   jarfile
     *          The JAR file to be searched when the bootstrap class loader
     *          unsuccessfully searches for a class.
     *
     * @throws  NullPointerException
     *          If <code>jarfile</code> is <code>null</code>.
     *
     * @see     #appendToSystemClassLoaderSearch
     * @see     java.lang.ClassLoader
     * @see     java.util.jar.JarFile
     *
     * @since 1.6
     */
    void
    appendToBootstrapClassLoaderSearch(JarFile jarfile);

    /**
     * Specifies a JAR file with instrumentation classes to be defined by the
     * system class loader.
     *
     * When the system class loader for delegation (see
     * {@link java.lang.ClassLoader#getSystemClassLoader getSystemClassLoader()})
     * unsuccessfully searches for a class, the entries in the {@link
     * java.util.jar.JarFile JarFile} will be searched as well.
     *
     * <p> This method may be used multiple times to add multiple JAR files to be
     * searched in the order that this method was invoked.
     *
     * <p> The agent should take care to ensure that the JAR does not contain any
     * classes or resources other than those to be defined by the system class
     * loader for the purpose of instrumentation.
     * Failure to observe this warning could result in unexpected
     * behavior that is difficult to diagnose (see
     * {@link #appendToBootstrapClassLoaderSearch
     * appendToBootstrapClassLoaderSearch}).
     *
     * <p> The system class loader supports adding a JAR file to be searched if
     * it implements a method named <code>appendToClassPathForInstrumentation</code>
     * which takes a single parameter of type <code>java.lang.String</code>. The
     * method is not required to have <code>public</code> access. The name of
     * the JAR file is obtained by invoking the {@link java.util.zip.ZipFile#getName
     * getName()} method on the <code>jarfile</code> and this is provided as the
     * parameter to the <code>appendToClassPathForInstrumentation</code> method.
     *
     * <p>
     * <cite>The Java&trade; Virtual Machine Specification</cite>
     * specifies that a subsequent attempt to resolve a symbolic
     * reference that the Java virtual machine has previously unsuccessfully attempted
     * to resolve always fails with the same error that was thrown as a result of the
     * initial resolution attempt. Consequently, if the JAR file contains an entry
     * that corresponds to a class for which the Java virtual machine has
     * unsuccessfully attempted to resolve a reference, then subsequent attempts to
     * resolve that reference will fail with the same error as the initial attempt.
     *
     * <p> This method does not change the value of <code>java.class.path</code>
     * {@link java.lang.System#getProperties system property}.
     *
     * <p>
     *  指定具有要由系统类装入器定义的检测类的JAR文件
     * 
     * 当用于委派的系统类装载器(参见{@link javalangClassLoader#getSystemClassLoader getSystemClassLoader()})未成功搜索某个类时,将搜索{@link javautiljarJarFile JarFile}
     * 中的条目。
     * 
     *  <p>此方法可以多次使用,以按照调用此方法的顺序添加多个要搜索的JAR文件
     * 
     *  <p>代理应注意确保JAR不包含任何类别或资源,而不是由系统类加载器为仪器定义的类或资源。
     * 不遵守此警告可能会导致意外的行为,这很难诊断(请参阅{@link #appendToBootstrapClassLoaderSearch appendToBootstrapClassLoaderSearch}
     * )。
     *  <p>代理应注意确保JAR不包含任何类别或资源,而不是由系统类加载器为仪器定义的类或资源。
     * 
     * <p>如果实现一个名为<code> appendToClassPathForInstrumentation </code>的方法,系统类加载器支持添加一个要搜索的JAR文件,该方法需要一个类型为<code>
     *  javalangString </code>的参数。
     * 有<code> public </code>访问JAR文件的名称是通过调用<code> jarfile </code>上的{@link javautilzipZipFile#getName getName()}
     * 方法获得的,这是作为参数提供的<code> appendToClassPathForInstrumentation </code>方法。
     * 
     * <p>
     * <cite> Java&trade;虚拟机规范</cite>指定解析Java虚拟机先前未成功尝试解析的符号引用的后续尝试失败,并且具有与初始解析尝试的结果相同的错误。
     * 因此,如果JAR文件包含与Java虚拟机未成功尝试解析引用的类相对应的条目,则解析该引用的后续尝试将失败,并出现与初始尝试相同的错误。
     * 
     *  <p>此方法不会更改<code> javaclasspath </code> {@link javalangSystem#getProperties系统属性}的值。
     * 
     * 
     * @param   jarfile
     *          The JAR file to be searched when the system class loader
     *          unsuccessfully searches for a class.
     *
     * @throws  UnsupportedOperationException
     *          If the system class loader does not support appending a
     *          a JAR file to be searched.
     *
     * @throws  NullPointerException
     *          If <code>jarfile</code> is <code>null</code>.
     *
     * @see     #appendToBootstrapClassLoaderSearch
     * @see     java.lang.ClassLoader#getSystemClassLoader
     * @see     java.util.jar.JarFile
     * @since 1.6
     */
    void
    appendToSystemClassLoaderSearch(JarFile jarfile);

    /**
     * Returns whether the current JVM configuration supports
     * {@linkplain #setNativeMethodPrefix(ClassFileTransformer,String)
     * setting a native method prefix}.
     * The ability to set a native method prefix is an optional
     * capability of a JVM.
     * Setting a native method prefix will only be supported if the
     * <code>Can-Set-Native-Method-Prefix</code> manifest attribute is set to
     * <code>true</code> in the agent JAR file (as described in the
     * {@linkplain java.lang.instrument package specification}) and the JVM supports
     * this capability.
     * During a single instantiation of a single JVM, multiple
     * calls to this method will always return the same answer.
     * <p>
     * 返回当前JVM配置是否支持{@linkplain #setNativeMethodPrefix(ClassFileTransformer,String)设置本地方法前缀}设置本地方法前缀的能力是JVM的
     * 可选功能设置本地方法前缀将仅在<代理> JAR文件中的代码> Can-Set-Native-Method-Prefix </code>清单属性设置为<code> true </code>,并且JVM支持
     * 此功能在单个JVM的单实例化期间,对此方法的多个调用将始终返回相同的答案。
     * 
     * 
     * @return  true if the current JVM configuration supports
     * setting a native method prefix, false if not.
     * @see #setNativeMethodPrefix
     * @since 1.6
     */
    boolean
    isNativeMethodPrefixSupported();

    /**
     * This method modifies the failure handling of
     * native method resolution by allowing retry
     * with a prefix applied to the name.
     * When used with the
     * {@link java.lang.instrument.ClassFileTransformer ClassFileTransformer},
     * it enables native methods to be
     * instrumented.
     * <p>
     * Since native methods cannot be directly instrumented
     * (they have no bytecodes), they must be wrapped with
     * a non-native method which can be instrumented.
     * For example, if we had:
     * <pre>
     *   native boolean foo(int x);</pre>
     * <p>
     * We could transform the class file (with the
     * ClassFileTransformer during the initial definition
     * of the class) so that this becomes:
     * <pre>
     *   boolean foo(int x) {
     *     <i>... record entry to foo ...</i>
     *     return wrapped_foo(x);
     *   }
     *
     *   native boolean wrapped_foo(int x);</pre>
     * <p>
     * Where <code>foo</code> becomes a wrapper for the actual native
     * method with the appended prefix "wrapped_".  Note that
     * "wrapped_" would be a poor choice of prefix since it
     * might conceivably form the name of an existing method
     * thus something like "$$$MyAgentWrapped$$$_" would be
     * better but would make these examples less readable.
     * <p>
     * The wrapper will allow data to be collected on the native
     * method call, but now the problem becomes linking up the
     * wrapped method with the native implementation.
     * That is, the method <code>wrapped_foo</code> needs to be
     * resolved to the native implementation of <code>foo</code>,
     * which might be:
     * <pre>
     *   Java_somePackage_someClass_foo(JNIEnv* env, jint x)</pre>
     * <p>
     * This function allows the prefix to be specified and the
     * proper resolution to occur.
     * Specifically, when the standard resolution fails, the
     * resolution is retried taking the prefix into consideration.
     * There are two ways that resolution occurs, explicit
     * resolution with the JNI function <code>RegisterNatives</code>
     * and the normal automatic resolution.  For
     * <code>RegisterNatives</code>, the JVM will attempt this
     * association:
     * <pre>{@code
     *   method(foo) -> nativeImplementation(foo)
     * }</pre>
     * <p>
     * When this fails, the resolution will be retried with
     * the specified prefix prepended to the method name,
     * yielding the correct resolution:
     * <pre>{@code
     *   method(wrapped_foo) -> nativeImplementation(foo)
     * }</pre>
     * <p>
     * For automatic resolution, the JVM will attempt:
     * <pre>{@code
     *   method(wrapped_foo) -> nativeImplementation(wrapped_foo)
     * }</pre>
     * <p>
     * When this fails, the resolution will be retried with
     * the specified prefix deleted from the implementation name,
     * yielding the correct resolution:
     * <pre>{@code
     *   method(wrapped_foo) -> nativeImplementation(foo)
     * }</pre>
     * <p>
     * Note that since the prefix is only used when standard
     * resolution fails, native methods can be wrapped selectively.
     * <p>
     * Since each <code>ClassFileTransformer</code>
     * can do its own transformation of the bytecodes, more
     * than one layer of wrappers may be applied. Thus each
     * transformer needs its own prefix.  Since transformations
     * are applied in order, the prefixes, if applied, will
     * be applied in the same order
     * (see {@link #addTransformer(ClassFileTransformer,boolean) addTransformer}).
     * Thus if three transformers applied
     * wrappers, <code>foo</code> might become
     * <code>$trans3_$trans2_$trans1_foo</code>.  But if, say,
     * the second transformer did not apply a wrapper to
     * <code>foo</code> it would be just
     * <code>$trans3_$trans1_foo</code>.  To be able to
     * efficiently determine the sequence of prefixes,
     * an intermediate prefix is only applied if its non-native
     * wrapper exists.  Thus, in the last example, even though
     * <code>$trans1_foo</code> is not a native method, the
     * <code>$trans1_</code> prefix is applied since
     * <code>$trans1_foo</code> exists.
     *
     * <p>
     * 此方法通过允许使用应用于名称的前缀重试来修改本地方法解析的失败处理当与{@link javalanginstrumentClassFileTransformer ClassFileTransformer}
     * 一起使用时,它允许对本地方法进行检测。
     * <p>
     *  因为本地方法不能被直接检测(它们没有字节码),所以它们必须用非本地方法包装,这可以被检测例如,如果我们有：
     * <pre>
     *  native boolean foo(int x); </pre>
     * <p>
     *  我们可以转换类文件(在类的初始定义期间使用ClassFileTransformer),这样变成：
     * <pre>
     *  boolean foo(int x){<i>将条目记录到foo </i> return wrapped_foo(x); }}
     * 
     *  native boolean wrapped_foo(int x); </pre>
     * <p>
     * 其中<code> foo </code>成为具有附加前缀"wrapped_"的实际本地方法的包装器。
     * 请注意,"wrapped_"将是前缀的一个可怜的选择,因为它可能会形成现有方法的名称, "$$$ MyAgentWrapped $$$ _"会更好,但会使这些例子较少可读性。
     * <p>
     *  包装器将允许在本地方法调用上收集数据,但是现在问题变得将包装方法与本地实现链接起来。
     * 也就是说,方法<code> wrapped_foo </code>需要解析为本机实现<code> foo </code>,可能是：。
     * <pre>
     *  Java_somePackage_someClass_foo(JNIEnv * env,jint x)</pre>
     * <p>
     * 此函数允许指定前缀并发生正确的分辨率。特别地,当标准分辨率失败时,将考虑前缀重试分辨率。
     * 
     * @param   transformer
     *          The ClassFileTransformer which wraps using this prefix.
     * @param   prefix
     *          The prefix to apply to wrapped native methods when
     *          retrying a failed native method resolution. If prefix
     *          is either <code>null</code> or the empty string, then
     *          failed native method resolutions are not retried for
     *          this transformer.
     * @throws java.lang.NullPointerException if passed a <code>null</code> transformer.
     * @throws java.lang.UnsupportedOperationException if the current configuration of
     *           the JVM does not allow setting a native method prefix
     *           ({@link #isNativeMethodPrefixSupported} is false).
     * @throws java.lang.IllegalArgumentException if the transformer is not registered
     *           (see {@link #addTransformer(ClassFileTransformer,boolean) addTransformer}).
     *
     * @since 1.6
     */
    void
    setNativeMethodPrefix(ClassFileTransformer transformer, String prefix);
}
