/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

import  java.security.ProtectionDomain;

/*
 * Copyright 2003 Wily Technology, Inc.
 * <p>
 *  版权所有2003 Wily Technology,Inc.
 * 
 */

/**
 * An agent provides an implementation of this interface in order
 * to transform class files.
 * The transformation occurs before the class is defined by the JVM.
 * <P>
 * Note the term <i>class file</i> is used as defined in section 3.1 of
 * <cite>The Java&trade; Virtual Machine Specification</cite>,
 * to mean a sequence
 * of bytes in class file format, whether or not they reside in a file.
 *
 * <p>
 *  代理提供了此接口的实现,以便转换类文件。转换发生在JVM定义类之前。
 * <P>
 *  注意,术语<i>类文件</i>如<cite> Java&trade;虚拟机规范</cite>,表示类文件格式的字节序列,无论它们是否驻留在文件中。
 * 
 * 
 * @see     java.lang.instrument.Instrumentation
 * @see     java.lang.instrument.Instrumentation#addTransformer
 * @see     java.lang.instrument.Instrumentation#removeTransformer
 * @since   1.5
 */

public interface ClassFileTransformer {
    /**
     * The implementation of this method may transform the supplied class file and
     * return a new replacement class file.
     *
     * <P>
     * There are two kinds of transformers, determined by the <code>canRetransform</code>
     * parameter of
     * {@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer,boolean)}:
     *  <ul>
     *    <li><i>retransformation capable</i> transformers that were added with
     *        <code>canRetransform</code> as true
     *    </li>
     *    <li><i>retransformation incapable</i> transformers that were added with
     *        <code>canRetransform</code> as false or where added with
     *        {@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer)}
     *    </li>
     *  </ul>
     *
     * <P>
     * Once a transformer has been registered with
     * {@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer,boolean)
     * addTransformer},
     * the transformer will be called for every new class definition and every class redefinition.
     * Retransformation capable transformers will also be called on every class retransformation.
     * The request for a new class definition is made with
     * {@link java.lang.ClassLoader#defineClass ClassLoader.defineClass}
     * or its native equivalents.
     * The request for a class redefinition is made with
     * {@link java.lang.instrument.Instrumentation#redefineClasses Instrumentation.redefineClasses}
     * or its native equivalents.
     * The request for a class retransformation is made with
     * {@link java.lang.instrument.Instrumentation#retransformClasses Instrumentation.retransformClasses}
     * or its native equivalents.
     * The transformer is called during the processing of the request, before the class file bytes
     * have been verified or applied.
     * When there are multiple transformers, transformations are composed by chaining the
     * <code>transform</code> calls.
     * That is, the byte array returned by one call to <code>transform</code> becomes the input
     * (via the <code>classfileBuffer</code> parameter) to the next call.
     *
     * <P>
     * Transformations are applied in the following order:
     *  <ul>
     *    <li>Retransformation incapable transformers
     *    </li>
     *    <li>Retransformation incapable native transformers
     *    </li>
     *    <li>Retransformation capable transformers
     *    </li>
     *    <li>Retransformation capable native transformers
     *    </li>
     *  </ul>
     *
     * <P>
     * For retransformations, the retransformation incapable transformers are not
     * called, instead the result of the previous transformation is reused.
     * In all other cases, this method is called.
     * Within each of these groupings, transformers are called in the order registered.
     * Native transformers are provided by the <code>ClassFileLoadHook</code> event
     * in the Java Virtual Machine Tool Interface).
     *
     * <P>
     * The input (via the <code>classfileBuffer</code> parameter) to the first
     * transformer is:
     *  <ul>
     *    <li>for new class definition,
     *        the bytes passed to <code>ClassLoader.defineClass</code>
     *    </li>
     *    <li>for class redefinition,
     *        <code>definitions.getDefinitionClassFile()</code> where
     *        <code>definitions</code> is the parameter to
     *        {@link java.lang.instrument.Instrumentation#redefineClasses
     *         Instrumentation.redefineClasses}
     *    </li>
     *    <li>for class retransformation,
     *         the bytes passed to the new class definition or, if redefined,
     *         the last redefinition, with all transformations made by retransformation
     *         incapable transformers reapplied automatically and unaltered;
     *         for details see
     *         {@link java.lang.instrument.Instrumentation#retransformClasses
     *          Instrumentation.retransformClasses}
     *    </li>
     *  </ul>
     *
     * <P>
     * If the implementing method determines that no transformations are needed,
     * it should return <code>null</code>.
     * Otherwise, it should create a new <code>byte[]</code> array,
     * copy the input <code>classfileBuffer</code> into it,
     * along with all desired transformations, and return the new array.
     * The input <code>classfileBuffer</code> must not be modified.
     *
     * <P>
     * In the retransform and redefine cases,
     * the transformer must support the redefinition semantics:
     * if a class that the transformer changed during initial definition is later
     * retransformed or redefined, the
     * transformer must insure that the second class output class file is a legal
     * redefinition of the first output class file.
     *
     * <P>
     * If the transformer throws an exception (which it doesn't catch),
     * subsequent transformers will still be called and the load, redefine
     * or retransform will still be attempted.
     * Thus, throwing an exception has the same effect as returning <code>null</code>.
     * To prevent unexpected behavior when unchecked exceptions are generated
     * in transformer code, a transformer can catch <code>Throwable</code>.
     * If the transformer believes the <code>classFileBuffer</code> does not
     * represent a validly formatted class file, it should throw
     * an <code>IllegalClassFormatException</code>;
     * while this has the same effect as returning null. it facilitates the
     * logging or debugging of format corruptions.
     *
     * <p>
     *  此方法的实现可以转换提供的类文件并返回一个新的替换类文件。
     * 
     * <P>
     *  有两种变换器,由{@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer,boolean)}的<code>
     *  canRetransform </code>参数确定：。
     * <ul>
     *  已将<code> canRetransform </code>添加为true的<li> </i>可重新转换的</i>
     * </li>
     *  <code> canRetransform </code>为false或添加了{@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer)}
     * 的<li> </i>。
     * </li>
     * </ul>
     * 
     * <P>
     * 一旦变换器已经注册了{@link java.lang.instrument.Instrumentation#addTransformer(ClassFileTransformer,boolean)addTransformer}
     * ,变换器将被调用每个新的类定义和每个类重定义。
     * 每个类重新转换也将调用具有重新转换能力的变换器。
     * 使用{@link java.lang.ClassLoader#defineClass ClassLoader.defineClass}或其本机等效项来请求新的类定义。
     * 类重定义的请求是通过{@link java.lang.instrument.Instrumentation#redefineClasses Instrumentation.redefineClasses}
     * 或其本地等同物来完成的。
     * 使用{@link java.lang.ClassLoader#defineClass ClassLoader.defineClass}或其本机等效项来请求新的类定义。
     * 对类重新传输的请求使用{@link java.lang.instrument.Instrumentation#retransformClasses Instrumentation.retransformClasses}
     * 或其本地等同物。
     * 使用{@link java.lang.ClassLoader#defineClass ClassLoader.defineClass}或其本机等效项来请求新的类定义。
     * 在处理请求期间,在类文件字节被验证或应用之前调用变换器。当有多个变换器时,通过链接<code> transform </code>调用来组成变换。
     * 也就是说,一次调用<code> transform </code>返回的字节数组成为下一次调用的输入(通过<code> classfileBuffer </code>参数)。
     * 
     * <P>
     *  转换按以下顺序应用：
     * <ul>
     *  <li>重新转换无效变压器
     * </li>
     *  <li>重新转换无法使用的本机变压器
     * </li>
     *  <li>支持重新转换的变压器
     * </li>
     * 
     * @param loader                the defining loader of the class to be transformed,
     *                              may be <code>null</code> if the bootstrap loader
     * @param className             the name of the class in the internal form of fully
     *                              qualified class and interface names as defined in
     *                              <i>The Java Virtual Machine Specification</i>.
     *                              For example, <code>"java/util/List"</code>.
     * @param classBeingRedefined   if this is triggered by a redefine or retransform,
     *                              the class being redefined or retransformed;
     *                              if this is a class load, <code>null</code>
     * @param protectionDomain      the protection domain of the class being defined or redefined
     * @param classfileBuffer       the input byte buffer in class file format - must not be modified
     *
     * @throws IllegalClassFormatException if the input does not represent a well-formed class file
     * @return  a well-formed class file buffer (the result of the transform),
                or <code>null</code> if no transform is performed.
     * @see Instrumentation#redefineClasses
     */
    byte[]
    transform(  ClassLoader         loader,
                String              className,
                Class<?>            classBeingRedefined,
                ProtectionDomain    protectionDomain,
                byte[]              classfileBuffer)
        throws IllegalClassFormatException;
}
