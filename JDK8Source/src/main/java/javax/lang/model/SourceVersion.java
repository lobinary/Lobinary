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

package javax.lang.model;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * Source versions of the Java&trade; programming language.
 *
 * See the appropriate edition of
 * <cite>The Java&trade; Language Specification</cite>
 * for information about a particular source version.
 *
 * <p>Note that additional source version constants will be added to
 * model future releases of the language.
 *
 * <p>
 *  源代码版本的Java&trade;编程语言。
 * 
 *  请参阅相应版本的<cite> Java和贸易;语言规范</cite>有关特定源版本的信息。
 * 
 *  <p>请注意,将添加其他源版本常量以建模该语言的未来版本。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public enum SourceVersion {
    /*
     * Summary of language evolution
     * 1.1: nested classes
     * 1.2: strictfp
     * 1.3: no changes
     * 1.4: assert
     * 1.5: annotations, generics, autoboxing, var-args...
     * 1.6: no changes
     * 1.7: diamond syntax, try-with-resources, etc.
     * 1.8: lambda expressions and default methods
     * <p>
     *  语言演化摘要1.1：嵌套类1.2：strictfp 1.3：无变化1.4：assert 1.5：注释,泛型,autoboxing,var-args ... 1.6：无变化1.7：菱形语法,try-wi
     * th-resources等1.8 ：lambda表达式和默认方法。
     * 
     */

    /**
     * The original version.
     *
     * The language described in
     * <cite>The Java&trade; Language Specification, First Edition</cite>.
     * <p>
     *  原始版本。
     * 
     *  Java&trade;中描述的语言。语言规范,第一版</cite>。
     * 
     */
    RELEASE_0,

    /**
     * The version recognized by the Java Platform 1.1.
     *
     * The language is {@code RELEASE_0} augmented with nested classes as described in the 1.1 update to
     * <cite>The Java&trade; Language Specification, First Edition</cite>.
     * <p>
     *  Java Platform 1.1识别的版本。
     * 
     *  该语言是{@code RELEASE_0}增加了嵌套类,如1.1版本更新<cite> Java&trade;语言规范,第一版</cite>。
     * 
     */
    RELEASE_1,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.2.
     *
     * The language described in
     * <cite>The Java&trade; Language Specification,
     * Second Edition</cite>, which includes the {@code
     * strictfp} modifier.
     * <p>
     *  由Java 2平台,标准版,v 1.2识别的版本。
     * 
     *  Java&trade;中描述的语言。语言规范第二版</cite>,其中包括{@code strictfp}修饰符。
     * 
     */
    RELEASE_2,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.3.
     *
     * No major changes from {@code RELEASE_2}.
     * <p>
     *  Java 2平台,标准版,v 1.3识别的版本。
     * 
     *  {@code RELEASE_2}没有重大变化。
     * 
     */
    RELEASE_3,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.4.
     *
     * Added a simple assertion facility.
     * <p>
     *  Java 2平台,标准版,v 1.4识别的版本。
     * 
     *  添加了一个简单的断言设施。
     * 
     */
    RELEASE_4,

    /**
     * The version recognized by the Java 2 Platform, Standard
     * Edition 5.0.
     *
     * The language described in
     * <cite>The Java&trade; Language Specification,
     * Third Edition</cite>.  First release to support
     * generics, annotations, autoboxing, var-args, enhanced {@code
     * for} loop, and hexadecimal floating-point literals.
     * <p>
     * 由Java 2平台,标准版5.0识别的版本。
     * 
     *  Java&trade;中描述的语言。语言规范,第三版</cite>。第一版支持泛型,注释,自动装箱,var-args,增强的{@code for}循环和十六进制浮点文字。
     * 
     */
    RELEASE_5,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 6.
     *
     * No major changes from {@code RELEASE_5}.
     * <p>
     *  由Java Platform,Standard Edition 6识别的版本。
     * 
     *  {@code RELEASE_5}没有重大变化。
     * 
     */
    RELEASE_6,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 7.
     *
     * Additions in this release include, diamond syntax for
     * constructors, {@code try}-with-resources, strings in switch,
     * binary literals, and multi-catch.
     * <p>
     *  由Java Platform,Standard Edition 7识别的版本。
     * 
     *  此版本中的添加包括构造函数的钻石语法,{@code try} -with-resources,switch中的字符串,二进制文字和多重捕获。
     * 
     * 
     * @since 1.7
     */
    RELEASE_7,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 8.
     *
     * Additions in this release include lambda expressions and default methods.
     * <p>
     *  由Java Platform,Standard Edition 8识别的版本。
     * 
     *  此版本中的添加项包括lambda表达式和默认方法。
     * 
     * 
     * @since 1.8
     */
    RELEASE_8;

    // Note that when adding constants for newer releases, the
    // behavior of latest() and latestSupported() must be updated too.

    /**
     * Returns the latest source version that can be modeled.
     *
     * <p>
     *  返回可以建模的最新源版本。
     * 
     * 
     * @return the latest source version that can be modeled
     */
    public static SourceVersion latest() {
        return RELEASE_8;
    }

    private static final SourceVersion latestSupported = getLatestSupported();

    private static SourceVersion getLatestSupported() {
        try {
            String specVersion = System.getProperty("java.specification.version");

            if ("1.8".equals(specVersion))
                return RELEASE_8;
            else if("1.7".equals(specVersion))
                return RELEASE_7;
            else if("1.6".equals(specVersion))
                return RELEASE_6;
        } catch (SecurityException se) {}

        return RELEASE_5;
    }

    /**
     * Returns the latest source version fully supported by the
     * current execution environment.  {@code RELEASE_5} or later must
     * be returned.
     *
     * <p>
     *  返回当前执行环境完全支持的最新源版本。必须返回{@code RELEASE_5}或更高版本。
     * 
     * 
     * @return the latest source version that is fully supported
     */
    public static SourceVersion latestSupported() {
        return latestSupported;
    }

    /**
     * Returns whether or not {@code name} is a syntactically valid
     * identifier (simple name) or keyword in the latest source
     * version.  The method returns {@code true} if the name consists
     * of an initial character for which {@link
     * Character#isJavaIdentifierStart(int)} returns {@code true},
     * followed only by characters for which {@link
     * Character#isJavaIdentifierPart(int)} returns {@code true}.
     * This pattern matches regular identifiers, keywords, and the
     * literals {@code "true"}, {@code "false"}, and {@code "null"}.
     * The method returns {@code false} for all other strings.
     *
     * <p>
     * 返回{@code name}是否是最新源版本中语法有效的标识符(简单名称)或关键字。
     * 如果名称由{@link Character#isJavaIdentifierStart(int)}返回的初始字符{@code true}返回{@code true},该方法返回{@code true},
     * 后跟只有{@link Character#isJavaIdentifierPart(int) }返回{@code true}。
     * 返回{@code name}是否是最新源版本中语法有效的标识符(简单名称)或关键字。
     * 此模式与常规标识符,关键字和文字{@code"true"},{@code"false"}和{@code"null"}匹配。该方法对所有其他字符串返回{@code false}。
     * 
     * 
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid identifier or keyword, {@code false}
     * otherwise.
     */
    public static boolean isIdentifier(CharSequence name) {
        String id = name.toString();

        if (id.length() == 0) {
            return false;
        }
        int cp = id.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
            return false;
        }
        for (int i = Character.charCount(cp);
                i < id.length();
                i += Character.charCount(cp)) {
            cp = id.codePointAt(i);
            if (!Character.isJavaIdentifierPart(cp)) {
                return false;
            }
        }
        return true;
    }

    /**
     *  Returns whether or not {@code name} is a syntactically valid
     *  qualified name in the latest source version.  Unlike {@link
     *  #isIdentifier isIdentifier}, this method returns {@code false}
     *  for keywords and literals.
     *
     * <p>
     * 
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid name, {@code false} otherwise.
     * @jls 6.2 Names and Identifiers
     */
    public static boolean isName(CharSequence name) {
        String id = name.toString();

        for(String s : id.split("\\.", -1)) {
            if (!isIdentifier(s) || isKeyword(s))
                return false;
        }
        return true;
    }

    private final static Set<String> keywords;
    static {
        Set<String> s = new HashSet<String>();
        String [] kws = {
            "abstract", "continue",     "for",          "new",          "switch",
            "assert",   "default",      "if",           "package",      "synchronized",
            "boolean",  "do",           "goto",         "private",      "this",
            "break",    "double",       "implements",   "protected",    "throw",
            "byte",     "else",         "import",       "public",       "throws",
            "case",     "enum",         "instanceof",   "return",       "transient",
            "catch",    "extends",      "int",          "short",        "try",
            "char",     "final",        "interface",    "static",       "void",
            "class",    "finally",      "long",         "strictfp",     "volatile",
            "const",    "float",        "native",       "super",        "while",
            // literals
            "null",     "true",         "false"
        };
        for(String kw : kws)
            s.add(kw);
        keywords = Collections.unmodifiableSet(s);
    }

    /**
     *  Returns whether or not {@code s} is a keyword or literal in the
     *  latest source version.
     *
     * <p>
     *  返回{@code name}是否是最新源版本中语法有效的有效名称。与{@link #isIdentifier isIdentifier}不同,此方法会为关键字和文字返回{@code false}。
     * 
     * 
     * @param s the string to check
     * @return {@code true} if {@code s} is a keyword or literal, {@code false} otherwise.
     */
    public static boolean isKeyword(CharSequence s) {
        String keywordOrLiteral = s.toString();
        return keywords.contains(keywordOrLiteral);
    }
}
