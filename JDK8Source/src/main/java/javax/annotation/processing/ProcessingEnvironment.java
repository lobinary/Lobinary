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

package javax.annotation.processing;

import java.util.Map;
import java.util.Locale;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * An annotation processing tool framework will {@linkplain
 * Processor#init provide an annotation processor with an object
 * implementing this interface} so the processor can use facilities
 * provided by the framework to write new files, report error
 * messages, and find other utilities.
 *
 * <p>Third parties may wish to provide value-add wrappers around the
 * facility objects from this interface, for example a {@code Filer}
 * extension that allows multiple processors to coordinate writing out
 * a single source file.  To enable this, for processors running in a
 * context where their side effects via the API could be visible to
 * each other, the tool infrastructure must provide corresponding
 * facility objects that are {@code .equals}, {@code Filer}s that are
 * {@code .equals}, and so on.  In addition, the tool invocation must
 * be able to be configured such that from the perspective of the
 * running annotation processors, at least the chosen subset of helper
 * classes are viewed as being loaded by the same class loader.
 * (Since the facility objects manage shared state, the implementation
 * of a wrapper class must know whether or not the same base facility
 * object has been wrapped before.)
 *
 * <p>
 *  注释处理工具框架将{@linkplain Processor#init为注解处理器提供实现此接口的对象},所以处理器可以使用框架提供的工具写入新文件,报告错误消息,并找到其他实用程序。
 * 
 *  <p>第三方可能希望通过此接口在设施对象周围提供增值包装,例如{@code Filer}扩展,允许多个处理器协调写出单个源文件。
 * 为了实现这一点,对于在通过API它们的副作用可以彼此可见的上下文中运行的处理器,工具基础设施必须提供对应的设施对象{@code .equals},{@code Filer} @code .equals}
 * ,等等。
 *  <p>第三方可能希望通过此接口在设施对象周围提供增值包装,例如{@code Filer}扩展,允许多个处理器协调写出单个源文件。
 * 此外,工具调用必须能够被配置,使得从运行的注解处理器的角度来看,至少所选择的助手类的子集被视为由相同的类加载器加载。
 *  (因为设施对象管理共享状态,所以包装类的实现必须知道相同的基本设施对象是否已经被包装)。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface ProcessingEnvironment {
    /**
     * Returns the processor-specific options passed to the annotation
     * processing tool.  Options are returned in the form of a map from
     * option name to option value.  For an option with no value, the
     * corresponding value in the map is {@code null}.
     *
     * <p>See documentation of the particular tool infrastructure
     * being used for details on how to pass in processor-specific
     * options.  For example, a command-line implementation may
     * distinguish processor-specific options by prefixing them with a
     * known string like {@code "-A"}; other tool implementations may
     * follow different conventions or provide alternative mechanisms.
     * A given implementation may also provide implementation-specific
     * ways of finding options passed to the tool in addition to the
     * processor-specific options.
     *
     * <p>
     * 返回传递到注释处理工具的特定于处理器的选项。选项以选项名称到选项值的映射形式返回。对于没有值的选项,映射中的对应值为{@code null}。
     * 
     *  <p>有关如何传递特定于处理器的选项的详细信息,请参阅正在使用的特定工具基础结构的文档。
     * 例如,命令行实现可以通过用诸如{@code"-A"}的已知字符串前缀来区分处理器特定的选项;其他工具实现可以遵循不同的约定或提供替代机制。
     * 除了处理器特定的选项之外,给定的实现还可以提供查找传递给工具的选项的特定于实现的方式。
     * 
     * 
     * @return the processor-specific options passed to the tool
     */
    Map<String,String> getOptions();

    /**
     * Returns the messager used to report errors, warnings, and other
     * notices.
     *
     * <p>
     *  返回用于报告错误,警告和其他通知的邮件。
     * 
     * 
     * @return the messager
     */
    Messager getMessager();

    /**
     * Returns the filer used to create new source, class, or auxiliary
     * files.
     *
     * <p>
     *  返回用于创建新的源,类或辅助文件的filer。
     * 
     * 
     * @return the filer
     */
    Filer getFiler();

    /**
     * Returns an implementation of some utility methods for
     * operating on elements
     *
     * <p>
     *  返回用于对元素进行操作的一些实用程序方法的实现
     * 
     * 
     * @return element utilities
     */
    Elements getElementUtils();

    /**
     * Returns an implementation of some utility methods for
     * operating on types.
     *
     * <p>
     *  返回一些用于在类型上操作的实用程序方法的实现。
     * 
     * 
     * @return type utilities
     */
    Types getTypeUtils();

    /**
     * Returns the source version that any generated {@linkplain
     * Filer#createSourceFile source} and {@linkplain
     * Filer#createClassFile class} files should conform to.
     *
     * <p>
     *  返回任何生成的{@linkplain Filer#createSourceFile source}和{@linkplain Filer#createClassFile class}文件应符合的源版本。
     * 
     * 
     * @return the source version to which generated source and class
     *         files should conform to
     * @see Processor#getSupportedSourceVersion
     */
    SourceVersion getSourceVersion();

    /**
     * Returns the current locale or {@code null} if no locale is in
     * effect.  The locale can be be used to provide localized
     * {@linkplain Messager messages}.
     *
     * <p>
     *  返回当前语言环境或{@code null}(如果没有语言环境生效)。区域设置可用于提供本地化的{@linkplain Messager messages}。
     * 
     * @return the current locale or {@code null} if no locale is in
     * effect
     */
    Locale getLocale();
}
