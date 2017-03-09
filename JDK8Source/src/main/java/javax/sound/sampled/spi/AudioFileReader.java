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

package javax.sound.sampled.spi;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Provider for audio file reading services.  Classes providing concrete
 * implementations can parse the format information from one or more types of
 * audio file, and can produce audio input streams from files of these types.
 *
 * <p>
 *  音频文件阅读服务提供商。提供具体实现的类可以解析来自一种或多种类型的音频文件的格式信息,并且可以从这些类型的文件产生音频输入流。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class AudioFileReader {

    /**
     * Obtains the audio file format of the input stream provided.  The stream must
     * point to valid audio file data.  In general, audio file readers may
     * need to read some data from the stream before determining whether they
     * support it.  These parsers must
     * be able to mark the stream, read enough data to determine whether they
     * support the stream, and, if not, reset the stream's read pointer to its original
     * position.  If the input stream does not support this, this method may fail
     * with an <code>IOException</code>.
     * <p>
     *  获取所提供的输入流的音频文件格式。流必须指向有效的音频文件数据。一般来说,音频文件读取器可能需要在确定它们是否支持之前从流中读取一些数据。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。
     * 如果输入流不支持此方法,此方法可能会失败,并显示<code> IOException </code>。
     * 
     * 
     * @param stream the input stream from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the audio file format
     * @throws UnsupportedAudioFileException if the stream does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public abstract AudioFileFormat getAudioFileFormat(InputStream stream) throws UnsupportedAudioFileException, IOException;

    /**
     * Obtains the audio file format of the URL provided.  The URL must
     * point to valid audio file data.
     * <p>
     *  获取提供的URL的音频文件格式。网址必须指向有效的音频文件数据。
     * 
     * 
     * @param url the URL from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the audio file format
     * @throws UnsupportedAudioFileException if the URL does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileException, IOException;

    /**
     * Obtains the audio file format of the <code>File</code> provided.  The <code>File</code> must
     * point to valid audio file data.
     * <p>
     *  获取所提供的<code> File </code>的音频文件格式。 <code> File </code>必须指向有效的音频文件数据。
     * 
     * 
     * @param file the <code>File</code> from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the audio file format
     * @throws UnsupportedAudioFileException if the <code>File</code> does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract AudioFileFormat getAudioFileFormat(File file) throws UnsupportedAudioFileException, IOException;

    /**
     * Obtains an audio input stream from the input stream provided.  The stream must
     * point to valid audio file data.  In general, audio file readers may
     * need to read some data from the stream before determining whether they
     * support it.  These parsers must
     * be able to mark the stream, read enough data to determine whether they
     * support the stream, and, if not, reset the stream's read pointer to its original
     * position.  If the input stream does not support this, this method may fail
     * with an <code>IOException</code>.
     * <p>
     * 从提供的输入流获取音频输入流。流必须指向有效的音频文件数据。一般来说,音频文件读取器可能需要在确定它们是否支持之前从流中读取一些数据。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。
     * 如果输入流不支持此方法,此方法可能会失败,并显示<code> IOException </code>。
     * 
     * 
     * @param stream the input stream from which the <code>AudioInputStream</code> should be
     * constructed
     * @return an <code>AudioInputStream</code> object based on the audio file data contained
     * in the input stream.
     * @throws UnsupportedAudioFileException if the stream does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public abstract AudioInputStream getAudioInputStream(InputStream stream) throws UnsupportedAudioFileException, IOException;

    /**
     * Obtains an audio input stream from the URL provided.  The URL must
     * point to valid audio file data.
     * <p>
     *  从提供的URL获取音频输入流。网址必须指向有效的音频文件数据。
     * 
     * 
     * @param url the URL for which the <code>AudioInputStream</code> should be
     * constructed
     * @return an <code>AudioInputStream</code> object based on the audio file data pointed
     * to by the URL
     * @throws UnsupportedAudioFileException if the URL does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract AudioInputStream getAudioInputStream(URL url) throws UnsupportedAudioFileException, IOException;

    /**
     * Obtains an audio input stream from the <code>File</code> provided.  The <code>File</code> must
     * point to valid audio file data.
     * <p>
     *  从提供的<code> File </code>获取音频输入流。 <code> File </code>必须指向有效的音频文件数据。
     * 
     * @param file the <code>File</code> for which the <code>AudioInputStream</code> should be
     * constructed
     * @return an <code>AudioInputStream</code> object based on the audio file data pointed
     * to by the File
     * @throws UnsupportedAudioFileException if the <code>File</code> does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public abstract AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException;
}
