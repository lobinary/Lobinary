/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

/**
 * This is an example of a starting class for a doclet,
 * showing the entry-point methods.  A starting class must
 * import com.sun.javadoc.* and implement the
 * <code>start(RootDoc)</code> method, as described in the
 * <a href="package-summary.html#package_description">package
 * description</a>.  If the doclet takes command line options,
 * it must also implement <code>optionLength</code> and
 * <code>validOptions</code>.
 *
 * <p> A doclet supporting the language features added since 1.1
 * (such as generics and annotations) should indicate this
 * by implementing <code>languageVersion</code>.  In the absence of
 * this the doclet should not invoke any of the Doclet API methods
 * added since 1.5, and
 * the results of several other methods are modified so as
 * to conceal the new constructs (such as type parameters) from
 * the doclet.
 *
 * <p> To start the doclet, pass
 * <code>-doclet</code> followed by the fully-qualified
 * name of the starting class on the javadoc tool command line.
 * <p>
 *  这是doclet的开始类的示例,显示入口点方法。起始类必须导入com.sun.javadoc。
 * *并实施<code> start(RootDoc)</code>方法,如<a href="package-summary.html#package_description">包描述</code> a>。
 *  这是doclet的开始类的示例,显示入口点方法。起始类必须导入com.sun.javadoc。
 * 如果doclet接受命令行选项,它还必须实现<code> optionLength </code>和<code> validOptions </code>。
 * 
 *  <p>支持自1.1以来添加的语言特性(如泛型和注释)的doclet应该通过实现<code> languageVersion </code>来指明这一点。
 * 在没有这个doclet不应该调用自1.5以来添加的任何Doclet API方法,并且修改其他几个方法的结果,以便从doclet隐藏新的结构(如类型参数)。
 * 
 *  <p>要启动doclet,请在javadoc工具命令行上传递<code> -doclet </code>,然后输入起始类的完全限定名称。
 * 
 */
public abstract class Doclet {

    /**
     * Generate documentation here.
     * This method is required for all doclets.
     *
     * <p>
     *  在这里生成文档。所有doclet都需要此方法。
     * 
     * 
     * @return true on success.
     */
    public static boolean start(RootDoc root) {
        return true;
    }

    /**
     * Check for doclet-added options.  Returns the number of
     * arguments you must specify on the command line for the
     * given option.  For example, "-d docs" would return 2.
     * <P>
     * This method is required if the doclet contains any options.
     * If this method is missing, Javadoc will print an invalid flag
     * error for every option.
     *
     * <p>
     *  检查添加doclet的选项。返回您必须在命令行上为给定选项指定的参数数。例如,"-d docs"将返回2。
     * <P>
     *  如果doclet包含任何选项,则需要此方法。如果缺少此方法,Javadoc将为每个选项打印无效的标志错误。
     * 
     * 
     * @return number of arguments on the command line for an option
     *         including the option name itself.  Zero return means
     *         option not known.  Negative value means error occurred.
     */
    public static int optionLength(String option) {
        return 0;  // default is option unknown
    }

    /**
     * Check that options have the correct arguments.
     * <P>
     * This method is not required, but is recommended,
     * as every option will be considered valid if this method
     * is not present.  It will default gracefully (to true)
     * if absent.
     * <P>
     * Printing option related error messages (using the provided
     * DocErrorReporter) is the responsibility of this method.
     *
     * <p>
     * 检查选项是否具有正确的参数。
     * <P>
     *  此方法不是必需的,但建议,因为每个选项将被视为有效,如果此方法不存在。如果不存在,它将默认默认(true)。
     * <P>
     *  打印选项相关的错误消息(使用提供的DocErrorReporter)是此方法的职责。
     * 
     * 
     * @return true if the options are valid.
     */
    public static boolean validOptions(String options[][],
                                       DocErrorReporter reporter) {
        return true;  // default is options are valid
    }

    /**
     * Return the version of the Java Programming Language supported
     * by this doclet.
     * <p>
     * This method is required by any doclet supporting a language version
     * newer than 1.1.
     *
     * <p>
     * 
     * @return  the language version supported by this doclet.
     * @since 1.5
     */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_1;
    }
}
