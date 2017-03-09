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

package javax.sound.sampled;

/**
 * An <code>UnsupportedAudioFileException</code> is an exception indicating that an
 * operation failed because a file did not contain valid data of a recognized file
 * type and format.
 *
 * <p>
 *  <code> UnsupportedAudioFileException </code>是一个异常,指示操作失败,因为文件不包含可识别的文件类型和格式的有效数据。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
/*
 * An <code>UnsupportedAudioFileException</code> is an exception indicating that an
 * operation failed because a file did not contain valid data of a recognized file
 * type and format.
 *
 * <p>
 *  <code> UnsupportedAudioFileException </code>是一个异常,指示操作失败,因为文件不包含可识别的文件类型和格式的有效数据。
 * 
 * 
 * @author Kara Kytle
 */

public class UnsupportedAudioFileException extends Exception {

    /**
     * Constructs a <code>UnsupportedAudioFileException</code> that has
     * <code>null</code> as its error detail message.
     * <p>
     *  构造具有<code> null </code>作为其错误详细信息的<code> UnsupportedAudioFileException </code>。
     * 
     */
    public UnsupportedAudioFileException() {

        super();
    }


    /**
     * Constructs a <code>UnsupportedAudioFileException</code> that has
     * the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> UnsupportedAudioFileException </code>。
     * 
     * @param message a string containing the error detail message
     */
    public UnsupportedAudioFileException(String message) {

        super(message);
    }
}
