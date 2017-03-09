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
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.InvalidMidiDataException;

/**
 * A {@code MidiFileReader} supplies MIDI file-reading services. Classes
 * implementing this interface can parse the format information from one or more
 * types of MIDI file, and can produce a {@link Sequence} object from files of
 * these types.
 *
 * <p>
 *  {@code MidiFileReader}提供MIDI文件阅读服务。
 * 实现此接口的类可以解析来自一种或多种类型的MIDI文件的格式信息,并且可以从这些类型的文件产生{@link Sequence}对象。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class MidiFileReader {

    /**
     * Obtains the MIDI file format of the input stream provided. The stream
     * must point to valid MIDI file data. In general, MIDI file readers may
     * need to read some data from the stream before determining whether they
     * support it. These parsers must be able to mark the stream, read enough
     * data to determine whether they support the stream, and, if not, reset the
     * stream's read pointer to its original position. If the input stream does
     * not support this, this method may fail with an {@code IOException}.
     *
     * <p>
     *  获取所提供的输入流的MIDI文件格式。流必须指向有效的MIDI文件数据。一般来说,MIDI文件读取器可能需要从流中读取一些数据,然后再确定是否支持它。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。如果输入流不支持此方法,则此方法可能会失败,并显示{@code IOException}。
     * 
     * 
     * @param  stream the input stream from which file format information
     *         should be extracted
     * @return a {@code MidiFileFormat} object describing the MIDI file format
     * @throws InvalidMidiDataException if the stream does not point to valid
     *         MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public abstract MidiFileFormat getMidiFileFormat(InputStream stream)
            throws InvalidMidiDataException, IOException;

    /**
     * Obtains the MIDI file format of the URL provided. The URL must point to
     * valid MIDI file data.
     *
     * <p>
     *  获取所提供的URL的MIDI文件格式。网址必须指向有效的MIDI文件数据。
     * 
     * 
     * @param  url the URL from which file format information should be
     *         extracted
     * @return a {@code MidiFileFormat} object describing the MIDI file format
     * @throws InvalidMidiDataException if the URL does not point to valid MIDI
     *         file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract MidiFileFormat getMidiFileFormat(URL url)
            throws InvalidMidiDataException, IOException;

    /**
     * Obtains the MIDI file format of the {@code File} provided. The
     * {@code File} must point to valid MIDI file data.
     *
     * <p>
     *  获取提供的{@code File}的MIDI文件格式。 {@code File}必须指向有效的MIDI文件数据。
     * 
     * 
     * @param  file the {@code File} from which file format information should
     *         be extracted
     * @return a {@code MidiFileFormat} object describing the MIDI file format
     * @throws InvalidMidiDataException if the {@code File} does not point to
     *         valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract MidiFileFormat getMidiFileFormat(File file)
            throws InvalidMidiDataException, IOException;

    /**
     * Obtains a MIDI sequence from the input stream provided. The stream must
     * point to valid MIDI file data. In general, MIDI file readers may need to
     * read some data from the stream before determining whether they support
     * it. These parsers must be able to mark the stream, read enough data to
     * determine whether they support the stream, and, if not, reset the
     * stream's read pointer to its original position. If the input stream does
     * not support this, this method may fail with an IOException.
     *
     * <p>
     * 从提供的输入流获取MIDI序列。流必须指向有效的MIDI文件数据。一般来说,MIDI文件读取器可能需要从流中读取一些数据,然后再确定是否支持它。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。如果输入流不支持此操作,则此方法可能会失败,并显示IOException。
     * 
     * 
     * @param  stream the input stream from which the {@code Sequence} should
     *         be constructed
     * @return a {@code Sequence} object based on the MIDI file data contained
     *         in the input stream.
     * @throws InvalidMidiDataException if the stream does not point to valid
     *         MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public abstract Sequence getSequence(InputStream stream)
            throws InvalidMidiDataException, IOException;

    /**
     * Obtains a MIDI sequence from the URL provided. The URL must point to
     * valid MIDI file data.
     *
     * <p>
     *  从提供的URL获取MIDI序列。网址必须指向有效的MIDI文件数据。
     * 
     * 
     * @param  url the URL for which the {@code Sequence} should be constructed
     * @return a {@code Sequence} object based on the MIDI file data pointed to
     *         by the URL
     * @throws InvalidMidiDataException if the URL does not point to valid MIDI
     *         file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract Sequence getSequence(URL url)
            throws InvalidMidiDataException, IOException;

    /**
     * Obtains a MIDI sequence from the {@code File} provided. The {@code File}
     * must point to valid MIDI file data.
     *
     * <p>
     *  从{@code File}提供的MIDI序列。 {@code File}必须指向有效的MIDI文件数据。
     * 
     * @param  file the {@code File} from which the {@code Sequence} should be
     *         constructed
     * @return a {@code Sequence} object based on the MIDI file data pointed to
     *         by the {@code File}
     * @throws InvalidMidiDataException if the {@code File} does not point to
     *         valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract Sequence getSequence(File file)
            throws InvalidMidiDataException, IOException;
}
