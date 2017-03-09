/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.stream;

import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.util.logging.PlatformLogger;

/**
 * Utility class for detecting inadvertent uses of boxing in
 * {@code java.util.stream} classes.  The detection is turned on or off based on
 * whether the system property {@code org.openjdk.java.util.stream.tripwire} is
 * considered {@code true} according to {@link Boolean#getBoolean(String)}.
 * This should normally be turned off for production use.
 *
 * @apiNote
 * Typical usage would be for boxing code to do:
 * <pre>{@code
 *     if (Tripwire.ENABLED)
 *         Tripwire.trip(getClass(), "{0} calling Sink.OfInt.accept(Integer)");
 * }</pre>
 *
 * <p>
 *  用于检测{@code java.util.stream}类中无意使用拳击的实用程序类。
 * 根据{@link Boolean#getBoolean(String)},系统属性{@code org.openjdk.java.util.stream.tripwire}是否被视为{@code true}
 * ,系统会打开或关闭检测。
 *  用于检测{@code java.util.stream}类中无意使用拳击的实用程序类。这通常应在生产使用时关闭。
 * 
 *  @apiNote典型的用法是对拳击代码做：<pre> {@ code if(Tripwire.ENABLED)Tripwire.trip(getClass(),"{0} call Sink.OfInt
 * 
 * @since 1.8
 */
final class Tripwire {
    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";

    /** Should debugging checks be enabled? */
    static final boolean ENABLED = AccessController.doPrivileged(
            (PrivilegedAction<Boolean>) () -> Boolean.getBoolean(TRIPWIRE_PROPERTY));

    private Tripwire() { }

    /**
     * Produces a log warning, using {@code PlatformLogger.getLogger(className)},
     * using the supplied message.  The class name of {@code trippingClass} will
     * be used as the first parameter to the message.
     *
     * <p>
     * .accept(Integer)"); } </pre>。
     * 
     * 
     * @param trippingClass Name of the class generating the message
     * @param msg A message format string of the type expected by
     * {@link PlatformLogger}
     */
    static void trip(Class<?> trippingClass, String msg) {
        PlatformLogger.getLogger(trippingClass.getName()).warning(msg, trippingClass.getName());
    }
}
