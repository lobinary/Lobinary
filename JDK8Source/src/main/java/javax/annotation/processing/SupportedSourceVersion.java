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

package javax.annotation.processing;

import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import javax.lang.model.SourceVersion;


/**
 * An annotation used to indicate the latest source version an
 * annotation processor supports.  The {@link
 * Processor#getSupportedSourceVersion} method can construct its
 * result from the value of this annotation, as done by {@link
 * AbstractProcessor#getSupportedSourceVersion}.
 *
 * <p>
 *  用于指示注释处理器支持的最新源版本的注释。
 *  {@link Processor#getSupportedSourceVersion}方法可以根据此注释的值构造其结果,如{@link AbstractProcessor#getSupportedSourceVersion}
 * 所做。
 *  用于指示注释处理器支持的最新源版本的注释。
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface SupportedSourceVersion {
    /**
     * Returns the latest supported source version.
     * <p>
     * 
     * 
     * @return the latest supported source version
     */
    SourceVersion value();
}
