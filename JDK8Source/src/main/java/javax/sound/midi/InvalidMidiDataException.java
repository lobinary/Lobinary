/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.midi;


/**
 * An <code>InvalidMidiDataException</code> indicates that inappropriate MIDI
 * data was encountered. This often means that the data is invalid in and of
 * itself, from the perspective of the MIDI specification.  An example would
 * be an undefined status byte.  However, the exception might simply
 * mean that the data was invalid in the context it was used, or that
 * the object to which the data was given was unable to parse or use it.
 * For example, a file reader might not be able to parse a Type 2 MIDI file, even
 * though that format is defined in the MIDI specification.
 *
 * <p>
 *  <code> InvalidMidiDataException </code>表示遇到了不适当的MIDI数据。这通常意味着从MIDI规范的角度来看,数据本身是无效的。一个例子是未定义的状态字节。
 * 但是,异常可能仅仅意味着数据在其使用的上下文中无效,或者给定数据的对象无法解析或使用它。例如,文件读取器可能无法解析类型2 MIDI文件,即使该格式在MIDI规范中定义。
 * 
 * 
 * @author Kara Kytle
 */
public class InvalidMidiDataException extends Exception {

    /**
     * Constructs an <code>InvalidMidiDataException</code> with
     * <code>null</code> for its error detail message.
     * <p>
     *  使用<code> null </code>为其错误详细信息构造<code> InvalidMidiDataException </code>。
     * 
     */
    public InvalidMidiDataException() {

        super();
    }


    /**
     *  Constructs an <code>InvalidMidiDataException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> InvalidMidiDataException </code>。
     * 
     * @param message the string to display as an error detail message
     */
    public InvalidMidiDataException(String message) {

        super(message);
    }
}
