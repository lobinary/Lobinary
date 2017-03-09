/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * This class is used to allow multiple resources declarations.
 *
 * <p>
 *  这个类用于允许多个资源声明。
 * 
 * 
 * @see javax.annotation.Resource
 * @since Common Annotations 1.0
 */

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Resources {
   /**
    * Array used for multiple resource declarations.
    * <p>
    *  用于多个资源声明的数组。
    */
   Resource[] value();
}
