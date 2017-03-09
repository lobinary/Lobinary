/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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
package javax.tools;

import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * Interface to invoke Java&trade; programming language documentation tools from
 * programs.
 * <p>
 *  调用Java和交易的接口;编程语言文档工具。
 * 
 */
public interface DocumentationTool extends Tool, OptionChecker {
    /**
     * Creates a future for a documentation task with the given
     * components and arguments.  The task might not have
     * completed as described in the DocumentationTask interface.
     *
     * <p>If a file manager is provided, it must be able to handle all
     * locations defined in {@link DocumentationTool.Location},
     * as well as
     * {@link StandardLocation#SOURCE_PATH},
     * {@link StandardLocation#CLASS_PATH}, and
     * {@link StandardLocation#PLATFORM_CLASS_PATH}.
     *
     * <p>
     *  使用给定的组件和参数创建文档任务的未来。任务可能未完成,如DocumentationTask界面中所述。
     * 
     *  <p>如果提供了文件管理器,它必须能够处理{@link DocumentationTool.Location}中定义的所有位置,以及{@link StandardLocation#SOURCE_PATH}
     * ,{@link StandardLocation#CLASS_PATH}和{@ link StandardLocation#PLATFORM_CLASS_PATH}。
     * 
     * 
     * @param out a Writer for additional output from the tool;
     * use {@code System.err} if {@code null}
     *
     * @param fileManager a file manager; if {@code null} use the
     * tool's standard filemanager
     *
     * @param diagnosticListener a diagnostic listener; if {@code null}
     * use the tool's default method for reporting diagnostics
     *
     * @param docletClass a class providing the necessary methods required
     * of a doclet
     *
     * @param options documentation tool options and doclet options,
     * {@code null} means no options
     *
     * @param compilationUnits the compilation units to compile, {@code
     * null} means no compilation units
     *
     * @return an object representing the compilation
     *
     * @throws RuntimeException if an unrecoverable error
     * occurred in a user supplied component.  The
     * {@linkplain Throwable#getCause() cause} will be the error in
     * user code.
     *
     * @throws IllegalArgumentException if any of the given
     * compilation units are of other kind than
     * {@linkplain JavaFileObject.Kind#SOURCE source}
     */
    DocumentationTask getTask(Writer out,
                            JavaFileManager fileManager,
                            DiagnosticListener<? super JavaFileObject> diagnosticListener,
                            Class<?> docletClass,
                            Iterable<String> options,
                            Iterable<? extends JavaFileObject> compilationUnits);

    /**
     * Gets a new instance of the standard file manager implementation
     * for this tool.  The file manager will use the given diagnostic
     * listener for producing any non-fatal diagnostics.  Fatal errors
     * will be signaled with the appropriate exceptions.
     *
     * <p>The standard file manager will be automatically reopened if
     * it is accessed after calls to {@code flush} or {@code close}.
     * The standard file manager must be usable with other tools.
     *
     * <p>
     *  获取此工具的标准文件管理器实现的新实例。文件管理器将使用给定的诊断侦听器来产生任何非致命诊断。将用适当的例外信号通知致命错误。
     * 
     *  <p>如果在调用{@code flush}或{@code close}后访问标准文件管理器,它将自动重新打开。标准文件管理器必须可用于其他工具。
     * 
     * 
     * @param diagnosticListener a diagnostic listener for non-fatal
     * diagnostics; if {@code null} use the compiler's default method
     * for reporting diagnostics
     *
     * @param locale the locale to apply when formatting diagnostics;
     * {@code null} means the {@linkplain Locale#getDefault() default locale}.
     *
     * @param charset the character set used for decoding bytes; if
     * {@code null} use the platform default
     *
     * @return the standard file manager
     */
    StandardJavaFileManager getStandardFileManager(
        DiagnosticListener<? super JavaFileObject> diagnosticListener,
        Locale locale,
        Charset charset);

    /**
     * Interface representing a future for a documentation task.  The
     * task has not yet started.  To start the task, call
     * the {@linkplain #call call} method.
     *
     * <p>Before calling the call method, additional aspects of the
     * task can be configured, for example, by calling the
     * {@linkplain #setLocale setLocale} method.
     * <p>
     *  表示文档任务的未来的接口。任务尚未开始。要启动任务,请调用{@linkplain #call call}方法。
     * 
     *  <p>在调用调用方法之前,可以配置任务的其他方面,例如通过调用{@linkplain #setLocale setLocale}方法。
     * 
     */
    interface DocumentationTask extends Callable<Boolean> {
        /**
         * Set the locale to be applied when formatting diagnostics and
         * other localized data.
         *
         * <p>
         *  设置在格式化诊断和其他本地化数据时要应用的区域设置。
         * 
         * 
         * @param locale the locale to apply; {@code null} means apply no
         * locale
         * @throws IllegalStateException if the task has started
         */
        void setLocale(Locale locale);

        /**
         * Performs this documentation task.  The task may only
         * be performed once.  Subsequent calls to this method throw
         * IllegalStateException.
         *
         * <p>
         * 执行此文档任务。任务只能执行一次。对此方法的后续调用将抛出IllegalStateException。
         * 
         * 
         * @return true if and only all the files were processed without errors;
         * false otherwise
         *
         * @throws RuntimeException if an unrecoverable error occurred
         * in a user-supplied component.  The
         * {@linkplain Throwable#getCause() cause} will be the error
         * in user code.
         *
         * @throws IllegalStateException if called more than once
         */
        Boolean call();
    }

    /**
     * Locations specific to {@link DocumentationTool}.
     *
     * <p>
     *  {@link DocumentationTool}特定的位置。
     * 
     * 
     * @see StandardLocation
     */
    enum Location implements JavaFileManager.Location {
        /**
         * Location of new documentation files.
         * <p>
         *  新文档文件的位置。
         * 
         */
        DOCUMENTATION_OUTPUT,

        /**
         * Location to search for doclets.
         * <p>
         *  搜索doclets的位置。
         * 
         */
        DOCLET_PATH,

        /**
         * Location to search for taglets.
         * <p>
         *  搜索taglets的位置。
         */
        TAGLET_PATH;

        public String getName() { return name(); }

        public boolean isOutputLocation() {
            switch (this) {
                case DOCUMENTATION_OUTPUT:
                    return true;
                default:
                    return false;
            }
        }
    }

}
