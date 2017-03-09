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

package javax.annotation;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The Generated annotation is used to mark source code that has been generated.
 * It can also be used to differentiate user written code from generated code
 * in a single file. When used, the value element must have the name of the
 * code generator. The recommended convention is to use the fully qualified
 * name of the code generator in the value field .
 * <p>For example: com.company.package.classname.
 * The date element is used to indicate the date the source was generated.
 * The date element must follow the ISO 8601 standard. For example the date
 * element would have the following value 2001-07-04T12:08:56.235-0700
 * which represents 2001-07-04 12:08:56 local time in the U.S. Pacific
 * Time time zone.</p>
 * <p>The comment element is a place holder for any comments that the code
 * generator may want to include in the generated code.</p>
 *
 * <p>
 *  生成的注释用于标记已生成的源代码。它还可以用于在单个文件中区分用户编写的代码和生成的代码。使用时,value元素必须具有代码生成器的名称。建议的约定是在值字段中使用代码生成器的完全限定名称。
 *  <p>例如：com.company.package.classname。 date元素用于指示生成源的日期。日期元素必须遵循ISO 8601标准。
 * 例如,date元素的值为2001-07-04T12：08：56.235-0700,表示美国太平洋时间时区的当地时间2001-07-04 12:08:56。
 * </p> <p> comment元素是代码生成器可能希望包括在生成的代码中的任何注释的占位符。</p>。
 * 
 * 
 * @since Common Annotations 1.0
 */

@Documented
@Retention(SOURCE)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, METHOD, CONSTRUCTOR, FIELD,
        LOCAL_VARIABLE, PARAMETER})
public @interface Generated {
   /**
    * The value element MUST have the name of the code generator.
    * The recommended convention is to use the fully qualified name of the
    * code generator. For example: com.acme.generator.CodeGen.
    * <p>
    *  值元素必须具有代码生成器的名称。推荐的约定是使用代码生成器的完全限定名。例如：com.acme.generator.CodeGen。
    * 
    */
   String[] value();

   /**
    * Date when the source was generated.
    * <p>
    *  生成源的日期。
    * 
    */
   String date() default "";

   /**
    * A place holder for any comments that the code generator may want to
    * include in the generated code.
    * <p>
    *  代码生成器可能希望包括在生成的代码中的任何注释的占位符。
    */
   String comments() default "";
}
