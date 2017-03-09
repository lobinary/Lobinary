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

package javax.lang.model.element;

/**
 * The <i>nesting kind</i> of a type element.
 * Type elements come in four varieties:
 * top-level, member, local, and anonymous.
 * <i>Nesting kind</i> is a non-standard term used here to denote this
 * classification.
 *
 * <p>Note that it is possible additional nesting kinds will be added
 * in future versions of the platform.
 *
 * <p><b>Example:</b> The classes below are annotated with their nesting kind.
 * <blockquote><pre>
 *
 * import java.lang.annotation.*;
 * import static java.lang.annotation.RetentionPolicy.*;
 * import javax.lang.model.element.*;
 * import static javax.lang.model.element.NestingKind.*;
 *
 * &#64;Nesting(TOP_LEVEL)
 * public class NestingExamples {
 *     &#64;Nesting(MEMBER)
 *     static class MemberClass1{}
 *
 *     &#64;Nesting(MEMBER)
 *     class MemberClass2{}
 *
 *     public static void main(String... argv) {
 *         &#64;Nesting(LOCAL)
 *         class LocalClass{};
 *
 *         Class&lt;?&gt;[] classes = {
 *             NestingExamples.class,
 *             MemberClass1.class,
 *             MemberClass2.class,
 *             LocalClass.class
 *         };
 *
 *         for(Class&lt;?&gt; clazz : classes) {
 *             System.out.format("%s is %s%n",
 *                               clazz.getName(),
 *                               clazz.getAnnotation(Nesting.class).value());
 *         }
 *     }
 * }
 *
 * &#64;Retention(RUNTIME)
 * &#64;interface Nesting {
 *     NestingKind value();
 * }
 * </pre></blockquote>
 *
 * <p>
 *  类型元素的<i>嵌套类</i>。类型元素有四种类型：顶级,成员,本地和匿名。 <i>嵌套种类</i>是此处用于表示此分类的非标准术语。
 * 
 *  <p>请注意,可能会在平台的未来版本中添加其他嵌套类型。
 * 
 *  <p> <b>示例：</b>下面的类用注释嵌套类型。 <blockquote> <pre>
 * 
 *  import java.lang.annotation。*; import static java.lang.annotation.RetentionPolicy。
 * *; import javax.lang.model.element。*; import static javax.lang.model.element.NestingKind。*;。
 * 
 *  @Nesting(TOP_LEVEL)public class NestingExamples {@Nesting(MEMBER)static class MemberClass1 {}
 * 
 *  @Nesting(MEMBER)class MemberClass2 {}
 * 
 *  public static void main(String ... argv){@Nesting(LOCAL)class LocalClass {};
 * 
 *  Class&lt;?&gt; [] classes = {NestingExamples.class,MemberClass1.class,MemberClass2.class,LocalClass.class}
 * ;。
 * 
 *  for(Class&lt;?&gt; clazz：classes){System.out.format("％s is％s％n",clazz.getName(),clazz.getAnnotation(Nesting.class).value()); }
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public enum NestingKind {
    /**
     * A top-level type, not contained within another type.
     * <p>
     * }}。
     * 
     *  @Retention(RUNTIME)@interface嵌套{NestingKind value(); } </pre> </blockquote>
     * 
     */
    TOP_LEVEL,

    /**
     * A type that is a named member of another type.
     * <p>
     *  顶级类型,不包含在另一个类型中。
     * 
     */
    MEMBER,

    /**
     * A named type declared within a construct other than a type.
     * <p>
     *  一种类型,是另一种类型的命名成员。
     * 
     */
    LOCAL,

    /**
     * A type without a name.
     * <p>
     *  在类型之外的构造中声明的命名类型。
     * 
     */
    ANONYMOUS;

    /**
     * Does this constant correspond to a nested type element?
     * A <i>nested</i> type element is any that is not top-level.
     * An <i>inner</i> type element is any nested type element that
     * is not {@linkplain Modifier#STATIC static}.
     * <p>
     *  没有名称的类型。
     * 
     * 
     * @return whether or not the constant is nested
     */
    public boolean isNested() {
        return this != TOP_LEVEL;
    }
}
