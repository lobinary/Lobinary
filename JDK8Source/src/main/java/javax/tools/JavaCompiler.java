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

package javax.tools;

import java.io.File;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.Callable;
import javax.annotation.processing.Processor;

/**
 * Interface to invoke Java&trade; programming language compilers from
 * programs.
 *
 * <p>The compiler might generate diagnostics during compilation (for
 * example, error messages).  If a diagnostic listener is provided,
 * the diagnostics will be supplied to the listener.  If no listener
 * is provided, the diagnostics will be formatted in an unspecified
 * format and written to the default output, which is {@code
 * System.err} unless otherwise specified.  Even if a diagnostic
 * listener is supplied, some diagnostics might not fit in a {@code
 * Diagnostic} and will be written to the default output.
 *
 * <p>A compiler tool has an associated standard file manager, which
 * is the file manager that is native to the tool (or built-in).  The
 * standard file manager can be obtained by calling {@linkplain
 * #getStandardFileManager getStandardFileManager}.
 *
 * <p>A compiler tool must function with any file manager as long as
 * any additional requirements as detailed in the methods below are
 * met.  If no file manager is provided, the compiler tool will use a
 * standard file manager such as the one returned by {@linkplain
 * #getStandardFileManager getStandardFileManager}.
 *
 * <p>An instance implementing this interface must conform to
 * <cite>The Java&trade; Language Specification</cite>
 * and generate class files conforming to
 * <cite>The Java&trade; Virtual Machine Specification</cite>.
 * The versions of these
 * specifications are defined in the {@linkplain Tool} interface.
 *
 * Additionally, an instance of this interface supporting {@link
 * javax.lang.model.SourceVersion#RELEASE_6 SourceVersion.RELEASE_6}
 * or higher must also support {@linkplain javax.annotation.processing
 * annotation processing}.
 *
 * <p>The compiler relies on two services: {@linkplain
 * DiagnosticListener diagnostic listener} and {@linkplain
 * JavaFileManager file manager}.  Although most classes and
 * interfaces in this package defines an API for compilers (and
 * tools in general) the interfaces {@linkplain DiagnosticListener},
 * {@linkplain JavaFileManager}, {@linkplain FileObject}, and
 * {@linkplain JavaFileObject} are not intended to be used in
 * applications.  Instead these interfaces are intended to be
 * implemented and used to provide customized services for a
 * compiler and thus defines an SPI for compilers.
 *
 * <p>There are a number of classes and interfaces in this package
 * which are designed to ease the implementation of the SPI to
 * customize the behavior of a compiler:
 *
 * <dl>
 *   <dt>{@link StandardJavaFileManager}</dt>
 *   <dd>
 *
 *     Every compiler which implements this interface provides a
 *     standard file manager for operating on regular {@linkplain
 *     java.io.File files}.  The StandardJavaFileManager interface
 *     defines additional methods for creating file objects from
 *     regular files.
 *
 *     <p>The standard file manager serves two purposes:
 *
 *     <ul>
 *       <li>basic building block for customizing how a compiler reads
 *       and writes files</li>
 *       <li>sharing between multiple compilation tasks</li>
 *     </ul>
 *
 *     <p>Reusing a file manager can potentially reduce overhead of
 *     scanning the file system and reading jar files.  Although there
 *     might be no reduction in overhead, a standard file manager must
 *     work with multiple sequential compilations making the following
 *     example a recommended coding pattern:
 *
 *     <pre>
 *       File[] files1 = ... ; // input for first compilation task
 *       File[] files2 = ... ; // input for second compilation task
 *
 *       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 *       StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
 *
 *       {@code Iterable<? extends JavaFileObject>} compilationUnits1 =
 *           fileManager.getJavaFileObjectsFromFiles({@linkplain java.util.Arrays#asList Arrays.asList}(files1));
 *       compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
 *
 *       {@code Iterable<? extends JavaFileObject>} compilationUnits2 =
 *           fileManager.getJavaFileObjects(files2); // use alternative method
 *       // reuse the same file manager to allow caching of jar files
 *       compiler.getTask(null, fileManager, null, null, null, compilationUnits2).call();
 *
 *       fileManager.close();</pre>
 *
 *   </dd>
 *
 *   <dt>{@link DiagnosticCollector}</dt>
 *   <dd>
 *     Used to collect diagnostics in a list, for example:
 *     <pre>
 *       {@code Iterable<? extends JavaFileObject>} compilationUnits = ...;
 *       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 *       {@code DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();}
 *       StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
 *       compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
 *
 *       for ({@code Diagnostic<? extends JavaFileObject>} diagnostic : diagnostics.getDiagnostics())
 *           System.out.format("Error on line %d in %s%n",
 *                             diagnostic.getLineNumber(),
 *                             diagnostic.getSource().toUri());
 *
 *       fileManager.close();</pre>
 *   </dd>
 *
 *   <dt>
 *     {@link ForwardingJavaFileManager}, {@link ForwardingFileObject}, and
 *     {@link ForwardingJavaFileObject}
 *   </dt>
 *   <dd>
 *
 *     Subclassing is not available for overriding the behavior of a
 *     standard file manager as it is created by calling a method on a
 *     compiler, not by invoking a constructor.  Instead forwarding
 *     (or delegation) should be used.  These classes makes it easy to
 *     forward most calls to a given file manager or file object while
 *     allowing customizing behavior.  For example, consider how to
 *     log all calls to {@linkplain JavaFileManager#flush}:
 *
 *     <pre>
 *       final {@linkplain java.util.logging.Logger Logger} logger = ...;
 *       {@code Iterable<? extends JavaFileObject>} compilationUnits = ...;
 *       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 *       StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
 *       JavaFileManager fileManager = new ForwardingJavaFileManager(stdFileManager) {
 *           public void flush() throws IOException {
 *               logger.entering(StandardJavaFileManager.class.getName(), "flush");
 *               super.flush();
 *               logger.exiting(StandardJavaFileManager.class.getName(), "flush");
 *           }
 *       };
 *       compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();</pre>
 *   </dd>
 *
 *   <dt>{@link SimpleJavaFileObject}</dt>
 *   <dd>
 *
 *     This class provides a basic file object implementation which
 *     can be used as building block for creating file objects.  For
 *     example, here is how to define a file object which represent
 *     source code stored in a string:
 *
 *     <pre>
 *       /**
 *        * A file object used to represent source coming from a string.
 *        {@code *}/
 *       public class JavaSourceFromString extends SimpleJavaFileObject {
 *           /**
 *            * The source code of this "file".
 *            {@code *}/
 *           final String code;
 *
 *           /**
 *            * Constructs a new JavaSourceFromString.
 *            * {@code @}param name the name of the compilation unit represented by this file object
 *            * {@code @}param code the source code for the compilation unit represented by this file object
 *            {@code *}/
 *           JavaSourceFromString(String name, String code) {
 *               super({@linkplain java.net.URI#create URI.create}("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
 *                     Kind.SOURCE);
 *               this.code = code;
 *           }
 *
 *           {@code @}Override
 *           public CharSequence getCharContent(boolean ignoreEncodingErrors) {
 *               return code;
 *           }
 *       }</pre>
 *   </dd>
 * </dl>
 *
 * <p>
 *  调用Java和交易的接口;编程语言编译器。
 * 
 *  <p>编译器可能在编译期间生成诊断(例如,错误消息)。如果提供了诊断侦听器,则诊断将提供给侦听器。
 * 如果没有提供侦听器,则诊断将以未指定的格式进行格式化,并写入缺省输出,即{@code System.err},除非另有规定。
 * 即使提供了诊断侦听器,某些诊断可能不适合{@code Diagnostic},并且将被写入默认输出。
 * 
 *  <p>编译器工具具有关联的标准文件管理器,它是工具(或内置的)本地的文件管理器。
 * 可以通过调用{@linkplain #getStandardFileManager getStandardFileManager}获取标准文件管理器。
 * 
 *  <p>编译器工具必须与任何文件管理器一起运行,只要满足以下方法中详述的任何其他要求即可。
 * 如果没有提供文件管理器,编译器工具将使用标准文件管理器,例如由{@linkplain #getStandardFileManager getStandardFileManager}返回的文件管理器。
 * 
 * <p>实现此接口的实例必须符合<cite> Java&trade;语言规范</cite>并生成符合<cite> Java&trade;虚拟机规范</cite>。
 * 这些规范的版本在{@linkplain Tool}界面中定义。
 * 
 *  此外,支持{@link javax.lang.model.SourceVersion#RELEASE_6 SourceVersion.RELEASE_6}或更高版本的此接口的实例还必须支持{@linkplain javax.annotation.processing注释处理}
 * 。
 * 
 *  <p>编译器依赖于两个服务：{@linkplain DiagnosticListener诊断侦听器}和{@linkplain JavaFileManager文件管理器}。
 * 虽然这个包中的大多数类和接口为编译器(和一般的工具)定义了一个API,但是接口{@linkplain DiagnosticListener},{@linkplain JavaFileManager},{@linkplain FileObject}
 * 和{@linkplain JavaFileObject}用于应用。
 *  <p>编译器依赖于两个服务：{@linkplain DiagnosticListener诊断侦听器}和{@linkplain JavaFileManager文件管理器}。
 * 相反,这些接口旨在被实现和用于为编译器提供定制服务,并且因此为编译器定义SPI。
 * 
 *  <p>在这个包中有许多类和接口,旨在简化SPI的实现以定制编译器的行为：
 * 
 * <dl>
 *  <dt> {@ link StandardJavaFileManager} </dt>
 * <dd>
 * 
 * 每个实现这个接口的编译器提供了一个标准的文件管理器,用于操作普通的{@linkplain java.io.File files}。
 *  StandardJavaFileManager接口定义了从常规文件创建文件对象的其他方法。
 * 
 *  <p>标准文件管理器有两个目的：
 * 
 * <ul>
 *  <li>用于定制编译器如何读取和写入文件的基本构建块</li> <li>在多个编译任务之间共享</li>
 * </ul>
 * 
 *  <p>重新使用文件管理器可以减少扫描文件系统和读取jar文件的开销。虽然可能没有减少开销,标准文件管理器必须使用多个顺序编译,使下面的示例成为推荐的编码模式：
 * 
 * <pre>
 *  File [] files1 = ...; //第一个编译任务的输入文件[] files2 = ...; //输入第二个编译任务
 * 
 *  JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); StandardJavaFileManager fileManager = 
 * compiler.getStandardFileManager(null,null,null);。
 * 
 *  {@code Iterable <? extends JavaFileObject>} compilationUnits1 = fileManager.getJavaFileObjectsFromFi
 * les({@ linkplain java.util.Arrays#asList Arrays.asList}(files1)); compiler.getTask(null,fileManager,n
 * ull,null,null,compilationUnits1).call();。
 * 
 * {@code Iterable <? extends JavaFileObject>} compilationUnits2 = fileManager.getJavaFileObjects(files2
 * ); //使用替代方法//重用相同的文件管理器来允许缓存jar文件compiler.getTask(null,fileManager,null,null,null,compilationUnits2).
 * call();。
 * 
 *  fileManager.close(); </pre>
 * 
 * </dd>
 * 
 *  <dt> {@ link DiagnosticCollector} </dt>
 * <dd>
 *  用于在列表中收集诊断信息,例如：
 * <pre>
 *  {@code Iterable <? extends JavaFileObject>} compilationUnits = ...; JavaCompiler compiler = ToolProv
 * ider.getSystemJavaCompiler(); {@code DiagnosticCollector <JavaFileObject> diagnostics = new DiagnosticCollector <JavaFileObject>();}
 *  StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics,null,null); compil
 * er.getTask(null,fileManager,diagnostics,null,null,compilationUnits).call();。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @see DiagnosticListener
 * @see Diagnostic
 * @see JavaFileManager
 * @since 1.6
 */
public interface JavaCompiler extends Tool, OptionChecker {

    /**
     * Creates a future for a compilation task with the given
     * components and arguments.  The compilation might not have
     * completed as described in the CompilationTask interface.
     *
     * <p>If a file manager is provided, it must be able to handle all
     * locations defined in {@link StandardLocation}.
     *
     * <p>Note that annotation processing can process both the
     * compilation units of source code to be compiled, passed with
     * the {@code compilationUnits} parameter, as well as class
     * files, whose names are passed with the {@code classes}
     * parameter.
     *
     * <p>
     *  for({@code Diagnostic <?extends JavaFileObject>} diagnostic：diagnostics.getDiagnostics())System.out.
     * format("％s％n行上的错误",diagnostic.getLineNumber(),diagnostic.getSource .toUri());。
     * 
     *  fileManager.close(); </pre>
     * </dd>
     * 
     * <dt>
     *  {@link ForwardingJavaFileManager},{@link ForwardingFileObject}和{@link ForwardingJavaFileObject}
     * </dt>
     * <dd>
     * 
     * 子类化不可用于覆盖标准文件管理器的行为,因为它是通过在编译器上调用方法而不是通过调用构造函数创建的。应该使用转发(或委派)。
     * 这些类使得很容易将大多数调用转发到给定的文件管理器或文件对象,同时允许自定义行为。例如,考虑如何记录对{@linkplain JavaFileManager#flush}的所有调用：。
     * 
     * <pre>
     *  final {@linkplain java.util.logging.Logger Logger} logger = ...; {@code Iterable <? extends JavaFileObject>}
     *  compilationUnits = ...; JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); StandardJavaFi
     * leManager stdFileManager = compiler.getStandardFileManager(null,null,null); JavaFileManager fileManag
     * er = new ForwardingJavaFileManager(stdFileManager){public void flush()throws IOException {logger.entering(StandardJavaFileManager.class.getName(),"flush"); super.flush(); logger.exiting(StandardJavaFileManager.class.getName(),"flush"); }
     * }; compile.getTask(null,fileManager,null,null,null,compilationUnits).call(); </pre>。
     * </dd>
     * 
     *  <dt> {@ link SimpleJavaFileObject} </dt>
     * <dd>
     * 
     *  这个类提供了一个基本的文件对象实现,可以用作创建文件对象的构建块。例如,这里是如何定义代表存储在字符串中的源代码的文件对象：
     * 
     * <pre>
     * / ** *用于表示来自字符串的源的文件对象。
     *  {@code *} / public class JavaSourceFromString extends SimpleJavaFileObject {/ ** *这个"文件"的源代码。
     *  {@code *} / final String code;。
     * 
     *  / ** *构造一个新的JavaSourceFromString。
     *  * {@code @} param名称由此文件对象表示的编译单元的名称* {@code @} param代码此文件对象表示的编译单元的源代码{@code *} / JavaSourceFromStri
     * 
     * @param out a Writer for additional output from the compiler;
     * use {@code System.err} if {@code null}
     * @param fileManager a file manager; if {@code null} use the
     * compiler's standard filemanager
     * @param diagnosticListener a diagnostic listener; if {@code
     * null} use the compiler's default method for reporting
     * diagnostics
     * @param options compiler options, {@code null} means no options
     * @param classes names of classes to be processed by annotation
     * processing, {@code null} means no class names
     * @param compilationUnits the compilation units to compile, {@code
     * null} means no compilation units
     * @return an object representing the compilation
     * @throws RuntimeException if an unrecoverable error
     * occurred in a user supplied component.  The
     * {@linkplain Throwable#getCause() cause} will be the error in
     * user code.
     * @throws IllegalArgumentException if any of the options are invalid,
     * or if any of the given compilation units are of other kind than
     * {@linkplain JavaFileObject.Kind#SOURCE source}
     */
    CompilationTask getTask(Writer out,
                            JavaFileManager fileManager,
                            DiagnosticListener<? super JavaFileObject> diagnosticListener,
                            Iterable<String> options,
                            Iterable<String> classes,
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
     * ng(String name, String code){super({@ linkplain java.net.URI#create URI.create}("string：///"+ name.re
     * place('。
     *  / ** *构造一个新的JavaSourceFromString。','/')+ Kind.SOURCE.extension),Kind 。资源); this.code = code; }}。
     * 
     *  {@code @}覆盖public CharSequence getCharContent(boolean ignoreEncodingErrors){return code; }} </pre>
     * </dd>
     * </dl>
     * 
     * 
     * @param diagnosticListener a diagnostic listener for non-fatal
     * diagnostics; if {@code null} use the compiler's default method
     * for reporting diagnostics
     * @param locale the locale to apply when formatting diagnostics;
     * {@code null} means the {@linkplain Locale#getDefault() default locale}.
     * @param charset the character set used for decoding bytes; if
     * {@code null} use the platform default
     * @return the standard file manager
     */
    StandardJavaFileManager getStandardFileManager(
        DiagnosticListener<? super JavaFileObject> diagnosticListener,
        Locale locale,
        Charset charset);

    /**
     * Interface representing a future for a compilation task.  The
     * compilation task has not yet started.  To start the task, call
     * the {@linkplain #call call} method.
     *
     * <p>Before calling the call method, additional aspects of the
     * task can be configured, for example, by calling the
     * {@linkplain #setProcessors setProcessors} method.
     * <p>
     *  使用给定的组件和参数创建编译任务的未来。编译可能未按CompilationTask界面中所述完成。
     * 
     *  <p>如果提供了文件管理器,它必须能够处理{@link StandardLocation}中定义的所有位置。
     * 
     *  <p>请注意,注释处理可以处理要编译的源代码的编译单元,使用{@code compilationUnits}参数传递,还可以处理类文件,其名称使用{@code classes}参数传递。
     * 
     */
    interface CompilationTask extends Callable<Boolean> {

        /**
         * Sets processors (for annotation processing).  This will
         * bypass the normal discovery mechanism.
         *
         * <p>
         * 获取此工具的标准文件管理器实现的新实例。文件管理器将使用给定的诊断侦听器来产生任何非致命诊断。将用适当的例外信号通知致命错误。
         * 
         *  <p>如果在调用{@code flush}或{@code close}后访问标准文件管理器,它将自动重新打开。标准文件管理器必须可用于其他工具。
         * 
         * 
         * @param processors processors (for annotation processing)
         * @throws IllegalStateException if the task has started
         */
        void setProcessors(Iterable<? extends Processor> processors);

        /**
         * Set the locale to be applied when formatting diagnostics and
         * other localized data.
         *
         * <p>
         *  表示编译任务的未来的接口。编译任务尚未开始。要启动任务,请调用{@linkplain #call call}方法。
         * 
         *  <p>在调用调用方法之前,可以配置任务的其他方面,例如,调用{@linkplain #setProcessors setProcessors}方法。
         * 
         * 
         * @param locale the locale to apply; {@code null} means apply no
         * locale
         * @throws IllegalStateException if the task has started
         */
        void setLocale(Locale locale);

        /**
         * Performs this compilation task.  The compilation may only
         * be performed once.  Subsequent calls to this method throw
         * IllegalStateException.
         *
         * <p>
         *  设置处理器(用于注释处理)。这将绕过正常的发现机制。
         * 
         * 
         * @return true if and only all the files compiled without errors;
         * false otherwise
         *
         * @throws RuntimeException if an unrecoverable error occurred
         * in a user-supplied component.  The
         * {@linkplain Throwable#getCause() cause} will be the error
         * in user code.
         * @throws IllegalStateException if called more than once
         */
        Boolean call();
    }
}
