/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.util;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.util.Context;

/**
 * Provides access to functionality specific to the JDK Java Compiler, javac.
 *
 * <p>
 *  提供对JDK Java编译器,javac特定的功能的访问。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public abstract class JavacTask implements CompilationTask {

    /**
     * Get the {@code JavacTask} for a {@code ProcessingEnvironment}.
     * If the compiler is being invoked using a
     * {@link javax.tools.JavaCompiler.CompilationTask CompilationTask},
     * then that task will be returned.
     * <p>
     *  获取{@code JavacTask}一个{@code ProcessingEnvironment}。
     * 如果使用{@link javax.tools.JavaCompiler.CompilationTask CompilationTask}调用编译器,那么将返回该任务。
     * 
     * 
     * @param processingEnvironment the processing environment
     * @return the {@code JavacTask} for a {@code ProcessingEnvironment}
     * @since 1.8
     */
    public static JavacTask instance(ProcessingEnvironment processingEnvironment) {
        if (!processingEnvironment.getClass().getName().equals(
                "com.sun.tools.javac.processing.JavacProcessingEnvironment"))
            throw new IllegalArgumentException();
        Context c = ((JavacProcessingEnvironment) processingEnvironment).getContext();
        JavacTask t = c.get(JavacTask.class);
        return (t != null) ? t : new BasicJavacTask(c, true);
    }

    /**
     * Parse the specified files returning a list of abstract syntax trees.
     *
     * <p>
     *  解析返回抽象语法树列表的指定文件。
     * 
     * 
     * @return a list of abstract syntax trees
     * @throws IOException if an unhandled I/O error occurred in the compiler.
     * @throws IllegalStateException if the operation cannot be performed at this time.
     */
    public abstract Iterable<? extends CompilationUnitTree> parse()
        throws IOException;

    /**
     * Complete all analysis.
     *
     * <p>
     *  完成所有分析。
     * 
     * 
     * @return a list of elements that were analyzed
     * @throws IOException if an unhandled I/O error occurred in the compiler.
     * @throws IllegalStateException if the operation cannot be performed at this time.
     */
    public abstract Iterable<? extends Element> analyze() throws IOException;

    /**
     * Generate code.
     *
     * <p>
     *  生成代码。
     * 
     * 
     * @return a list of files that were generated
     * @throws IOException if an unhandled I/O error occurred in the compiler.
     * @throws IllegalStateException if the operation cannot be performed at this time.
     */
    public abstract Iterable<? extends JavaFileObject> generate() throws IOException;

    /**
     * The specified listener will receive notification of events
     * describing the progress of this compilation task.
     *
     * If another listener is receiving notifications as a result of a prior
     * call of this method, then that listener will no longer receive notifications.
     *
     * Informally, this method is equivalent to calling {@code removeTaskListener} for
     * any listener that has been previously set, followed by {@code addTaskListener}
     * for the new listener.
     *
     * <p>
     *  指定的侦听器将接收描述此编译任务进度的事件的通知。
     * 
     *  如果另一个侦听器由于此方法的先前调用而接收通知,则该侦听器将不再接收通知。
     * 
     *  非正式地,此方法等效于对先前设置的任何侦听器调用{@code removeTaskListener},然后为新侦听器调用{@code addTaskListener}。
     * 
     * 
     * @throws IllegalStateException if the specified listener has already been added.
     */
    public abstract void setTaskListener(TaskListener taskListener);

    /**
     * The specified listener will receive notification of events
     * describing the progress of this compilation task.
     *
     * This method may be called at any time before or during the compilation.
     *
     * <p>
     *  指定的侦听器将接收描述此编译任务进度的事件的通知。
     * 
     *  可以在编译之前或期间的任何时间调用此方法。
     * 
     * 
     * @throws IllegalStateException if the specified listener has already been added.
     * @since 1.8
     */
    public abstract void addTaskListener(TaskListener taskListener);

    /**
     * The specified listener will no longer receive notification of events
     * describing the progress of this compilation task.
     *
     * This method may be called at any time before or during the compilation.
     *
     * <p>
     *  指定的侦听器将不再接收描述此编译任务进度的事件的通知。
     * 
     *  可以在编译之前或期间的任何时间调用此方法。
     * 
     * 
     * @since 1.8
     */
    public abstract void removeTaskListener(TaskListener taskListener);

    /**
     * Get a type mirror of the tree node determined by the specified path.
     * This method has been superceded by methods on
     * {@link com.sun.source.util.Trees Trees}.
     * <p>
     * 获取由指定路径确定的树节点的类型镜像。此方法已被{@link com.sun.source.util.Trees Trees}上的方法替代。
     * 
     * 
     * @see com.sun.source.util.Trees#getTypeMirror
     */
    public abstract TypeMirror getTypeMirror(Iterable<? extends Tree> path);

    /**
     * Get a utility object for dealing with program elements.
     * <p>
     *  获取一个用于处理程序元素的实用程序对象。
     * 
     */
    public abstract Elements getElements();

    /**
     * Get a utility object for dealing with type mirrors.
     * <p>
     *  获取用于处理类型镜像的实用程序对象。
     */
    public abstract Types getTypes();
}
