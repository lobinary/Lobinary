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

import java.util.ServiceLoader;
import javax.tools.StandardLocation;

/**
 * The interface for a javac plug-in.
 *
 * <p>The javac plug-in mechanism allows a user to specify one or more plug-ins
 * on the javac command line, to be started soon after the compilation
 * has begun. Plug-ins are identified by a user-friendly name. Each plug-in that
 * is started will be passed an array of strings, which may be used to
 * provide the plug-in with values for any desired options or other arguments.
 *
 * <p>Plug-ins are located via a {@link ServiceLoader},
 * using the same class path as annotation processors (i.e.
 * {@link StandardLocation#ANNOTATION_PROCESSOR_PATH ANNOTATION_PROCESSOR_PATH} or
 * {@code -processorpath}).
 *
 * <p>It is expected that a typical plug-in will simply register a
 * {@link TaskListener} to be informed of events during the execution
 * of the compilation, and that the rest of the work will be done
 * by the task listener.
 *
 * <p>
 *  javac插件的接口。
 * 
 *  <p> javac插件机制允许用户在javac命令行上指定一个或多个插件,以便在编译开始后尽快启动。插件由用户友好的名称标识。
 * 启动的每个插件都将传递一个字符串数组,可用于为插件提供任何所需选项或其他参数的值。
 * 
 *  <p>插件通过{@link ServiceLoader}定位,使用与注释处理器相同的类路径(即{@link StandardLocation#ANNOTATION_PROCESSOR_PATH ANNOTATION_PROCESSOR_PATH}
 * 或{@code -processorpath})。
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public interface Plugin {
    /**
     * Get the user-friendly name of this plug-in.
     * <p>
     *  <p>期望典型的插件将简单地注册{@link TaskListener}以在编译的执行期间被通知事件,并且剩余的工作将由任务侦听器完成。
     * 
     * 
     * @return the user-friendly name of the plug-in
     */
    String getName();

    /**
     * Initialize the plug-in for a given compilation task.
     * <p>
     *  获取此插件的用户友好名称。
     * 
     * 
     * @param task The compilation task that has just been started
     * @param args Arguments, if any, for the plug-in
     */
    void init(JavacTask task, String... args);
}
