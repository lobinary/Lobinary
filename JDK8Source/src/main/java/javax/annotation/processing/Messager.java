/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

import javax.annotation.*;
import javax.tools.Diagnostic;
import javax.lang.model.element.*;

/**
 * A {@code Messager} provides the way for an annotation processor to
 * report error messages, warnings, and other notices.  Elements,
 * annotations, and annotation values can be passed to provide a
 * location hint for the message.  However, such location hints may be
 * unavailable or only approximate.
 *
 * <p>Printing a message with an {@linkplain
 * javax.tools.Diagnostic.Kind#ERROR error kind} will {@linkplain
 * RoundEnvironment#errorRaised raise an error}.
 *
 * <p>Note that the messages &quot;printed&quot; by methods in this
 * interface may or may not appear as textual output to a location
 * like {@link System#out} or {@link System#err}.  Implementations may
 * choose to present this information in a different fashion, such as
 * messages in a window.
 *
 * <p>
 *  {@code Messager}提供了注释处理器报告错误消息,警告和其他通知的方法。可以传递元素,注释和注释值以提供消息的位置提示。然而,这样的位置提示可能不可用或仅是近似。
 * 
 *  <p>使用{@linkplain javax.tools.Diagnostic.Kind#ERROR错误类型}打印邮件会{@linkplain RoundEnvironment#errorRaised引发错误}
 * 。
 * 
 *  <p>请注意,消息"打印"这个接口中的by方法可能会或可能不会作为文本输出出现在像{@link System#out}或{@link System#err}这样的位置。
 * 实现可以选择以不同的方式呈现该信息,例如窗口中的消息。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see ProcessingEnvironment#getLocale
 * @since 1.6
 */
public interface Messager {
    /**
     * Prints a message of the specified kind.
     *
     * <p>
     *  打印指定类型的消息。
     * 
     * 
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg);

    /**
     * Prints a message of the specified kind at the location of the
     * element.
     *
     * <p>
     *  在元素的位置打印指定类型的消息。
     * 
     * 
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the element to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation mirror of the annotated element.
     *
     * <p>
     *  在注释元素的注释镜像的位置打印指定类型的消息。
     * 
     * 
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the annotated element
     * @param a    the annotation to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation value inside the annotation mirror of the annotated
     * element.
     *
     * <p>
     *  在注释元素的注释镜像中的注释值的位置打印指定类型的消息。
     * 
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the annotated element
     * @param a    the annotation containing the annotation value
     * @param v    the annotation value to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind,
                      CharSequence msg,
                      Element e,
                      AnnotationMirror a,
                      AnnotationValue v);
}
