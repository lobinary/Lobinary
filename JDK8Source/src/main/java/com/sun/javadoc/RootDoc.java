/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Represents the root of the program structure information
 * for one run of javadoc.  From this root all other program
 * structure information can be extracted.
 * Also represents the command line information -- the
 * packages, classes and options specified by the user.
 *
 * <p>
 *  表示一次运行的javadoc的程序结构信息的根。从该根可以提取所有其他节目结构信息。还表示命令行信息 - 用户指定的包,类和选项。
 * 
 * 
 * @since 1.2
 * @author Robert Field
 */
public interface RootDoc extends Doc, DocErrorReporter {

    /**
     * Command line options.
     * <p>
     * For example, given:
     * <pre>
     *     javadoc -foo this that -bar other ...</pre>
     *
     * this method will return:
     * <pre>
     *      options()[0][0] = "-foo"
     *      options()[0][1] = "this"
     *      options()[0][2] = "that"
     *      options()[1][0] = "-bar"
     *      options()[1][1] = "other"</pre>
     *
     * <p>
     *  命令行选项。
     * <p>
     *  例如,给定：
     * <pre>
     *  javadoc -foo this that -bar other ... </pre>
     * 
     *  这个方法会返回：
     * <pre>
     *  options()[0] [1] ="this"options()[0] [2] =" "-bar"options()[1] [1] ="其他"</pre>
     * 
     * 
     * @return an array of arrays of String.
     */
    String[][] options();

    /**
     * Return the packages
     * <a href="package-summary.html#included">specified</a>
     * on the command line.
     * If <code>-subpackages</code> and <code>-exclude</code> options
     * are used, return all the non-excluded packages.
     *
     * <p>
     *  返回在命令行上<a href="package-summary.html#included">指定的</a>包。
     * 如果使用<code> -subpackages </code>和<code> -exclude </code>选项,则返回所有未排除的软件包。
     * 
     * 
     * @return packages specified on the command line.
     */
    PackageDoc[] specifiedPackages();

    /**
     * Return the classes and interfaces
     * <a href="package-summary.html#included">specified</a>
     * as source file names on the command line.
     *
     * <p>
     *  返回类和界面<a href="package-summary.html#included">指定</a>作为源文件名在命令行。
     * 
     * 
     * @return classes and interfaces specified on the command line.
     */
    ClassDoc[] specifiedClasses();

    /**
     * Return the
     * <a href="package-summary.html#included">included</a>
      classes and interfaces in all packages.
     *
     * <p>
     *  返回所有包中的<a href="package-summary.html#included">包含的</a>类和界面。
     * 
     * 
     * @return included classes and interfaces in all packages.
     */
    ClassDoc[] classes();

    /**
     * Return a PackageDoc for the specified package name.
     *
     * <p>
     *  返回指定包名称的PackageDoc。
     * 
     * 
     * @param name package name
     *
     * @return a PackageDoc holding the specified package, null if
     * this package is not referenced.
     */
    PackageDoc packageNamed(String name);

    /**
     * Return a ClassDoc for the specified class or interface name.
     *
     * <p>
     *  返回指定的类或接口名称的ClassDoc。
     * 
     * @param qualifiedName
     * <a href="package-summary.html#qualified">qualified</a>
     * class or package name
     *
     * @return a ClassDoc holding the specified class, null if
     * this class is not referenced.
     */
    ClassDoc classNamed(String qualifiedName);
}
