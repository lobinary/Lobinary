/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2014, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.midi.spi;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.midi.Sequence;

/**
 * A {@code MidiFileWriter} supplies MIDI file-writing services. Classes that
 * implement this interface can write one or more types of MIDI file from a
 * {@link Sequence} object.
 *
 * <p>
 *  {@code MidiFileWriter}提供MIDI文件写入服务。实现此接口的类可以从{@link Sequence}对象中编写一种或多种类型的MIDI文件。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class MidiFileWriter {

    /**
     * Obtains the set of MIDI file types for which file writing support is
     * provided by this file writer.
     *
     * <p>
     *  获取此文件写入程序提供文件写入支持的一组MIDI文件类型。
     * 
     * 
     * @return array of file types. If no file types are supported, an array of
     *         length 0 is returned.
     */
    public abstract int[] getMidiFileTypes();

    /**
     * Obtains the file types that this file writer can write from the sequence
     * specified.
     *
     * <p>
     *  获取此文件写入器可以从指定的序列写入的文件类型。
     * 
     * 
     * @param  sequence the sequence for which MIDI file type support is
     *         queried
     * @return array of file types. If no file types are supported, returns an
     *         array of length 0.
     */
    public abstract int[] getMidiFileTypes(Sequence sequence);

    /**
     * Indicates whether file writing support for the specified MIDI file type
     * is provided by this file writer.
     *
     * <p>
     *  指示该文件编写器是否提供对指定MIDI文件类型的文件写入支持。
     * 
     * 
     * @param  fileType the file type for which write capabilities are queried
     * @return {@code true} if the file type is supported, otherwise
     *         {@code false}
     */
    public boolean isFileTypeSupported(int fileType) {

        int types[] = getMidiFileTypes();
        for(int i=0; i<types.length; i++) {
            if( fileType == types[i] ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether a MIDI file of the file type specified can be written
     * from the sequence indicated.
     *
     * <p>
     *  指示是否可以从指定的序列写入指定文件类型的MIDI文件。
     * 
     * 
     * @param  fileType the file type for which write capabilities are queried
     * @param  sequence the sequence for which file writing support is queried
     * @return {@code true} if the file type is supported for this sequence,
     *         otherwise {@code false}
     */
    public boolean isFileTypeSupported(int fileType, Sequence sequence) {

        int types[] = getMidiFileTypes( sequence );
        for(int i=0; i<types.length; i++) {
            if( fileType == types[i] ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes a stream of bytes representing a MIDI file of the file type
     * indicated to the output stream provided.
     *
     * <p>
     *  将表示指定的文件类型的MIDI文件的字节流写入提供的输出流。
     * 
     * 
     * @param  in sequence containing MIDI data to be written to the file
     * @param  fileType type of the file to be written to the output stream
     * @param  out stream to which the file data should be written
     * @return the number of bytes written to the output stream
     * @throws IOException if an I/O exception occurs
     * @throws IllegalArgumentException if the file type is not supported by
     *         this file writer
     * @see #isFileTypeSupported(int, Sequence)
     * @see #getMidiFileTypes(Sequence)
     */
    public abstract int write(Sequence in, int fileType, OutputStream out)
            throws IOException;

    /**
     * Writes a stream of bytes representing a MIDI file of the file type
     * indicated to the external file provided.
     *
     * <p>
     *  将表示指定的文件类型的MIDI文件的字节流写入提供的外部文件。
     * 
     * @param  in sequence containing MIDI data to be written to the external
     *         file
     * @param  fileType type of the file to be written to the external file
     * @param  out external file to which the file data should be written
     * @return the number of bytes written to the file
     * @throws IOException if an I/O exception occurs
     * @throws IllegalArgumentException if the file type is not supported by
     *         this file writer
     * @see #isFileTypeSupported(int, Sequence)
     * @see #getMidiFileTypes(Sequence)
     */
    public abstract int write(Sequence in, int fileType, File out)
            throws IOException;
}
