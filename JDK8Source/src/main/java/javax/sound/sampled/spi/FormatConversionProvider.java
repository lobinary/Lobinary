/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * A format conversion provider provides format conversion services
 * from one or more input formats to one or more output formats.
 * Converters include codecs, which encode and/or decode audio data,
 * as well as transcoders, etc.  Format converters provide methods for
 * determining what conversions are supported and for obtaining an audio
 * stream from which converted data can be read.
 * <p>
 * The source format represents the format of the incoming
 * audio data, which will be converted.
 * <p>
 * The target format represents the format of the processed, converted
 * audio data.  This is the format of the data that can be read from
 * the stream returned by one of the <code>getAudioInputStream</code> methods.
 *
 * <p>
 *  格式转换提供者提供从一个或多个输入格式到一个或多个输出格式的格式转换服务。转换器包括编码和/或解码音频数据的编解码器,以及代码转换器等。
 * 格式转换器提供用于确定支持什么转换以及用于获得可以从中读取转换数据的音频流的方法。
 * <p>
 *  源格式表示将被转换的输入音频数据的格式。
 * <p>
 *  目标格式表示经处理,转换的音频数据的格式。这是可以从由<code> getAudioInputStream </code>方法之一返回的流中读取的数据的格式。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class FormatConversionProvider {


    // NEW METHODS

    /**
     * Obtains the set of source format encodings from which format
     * conversion services are provided by this provider.
     * <p>
     *  获取此提供程序提供格式转换服务的源格式编码集。
     * 
     * 
     * @return array of source format encodings. If for some reason provider
     * does not provide any conversion services, an array of length 0 is
     * returned.
     */
    public abstract AudioFormat.Encoding[] getSourceEncodings();


    /**
     * Obtains the set of target format encodings to which format
     * conversion services are provided by this provider.
     * <p>
     *  获取此提供程序提供的格式转换服务的目标格式编码集。
     * 
     * 
     * @return array of target format encodings. If for some reason provider
     * does not provide any conversion services, an array of length 0 is
     * returned.
     */
    public abstract AudioFormat.Encoding[] getTargetEncodings();


    /**
     * Indicates whether the format converter supports conversion from the
     * specified source format encoding.
     * <p>
     *  指示格式转换器是否支持从指定的源格式编码进行转换。
     * 
     * 
     * @param sourceEncoding the source format encoding for which support is queried
     * @return <code>true</code> if the encoding is supported, otherwise <code>false</code>
     */
    public boolean isSourceEncodingSupported(AudioFormat.Encoding sourceEncoding){

        AudioFormat.Encoding sourceEncodings[] = getSourceEncodings();

        for(int i=0; i<sourceEncodings.length; i++) {
            if( sourceEncoding.equals( sourceEncodings[i]) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Indicates whether the format converter supports conversion to the
     * specified target format encoding.
     * <p>
     *  指示格式转换器是否支持转换为指定的目标格式编码。
     * 
     * 
     * @param targetEncoding the target format encoding for which support is queried
     * @return <code>true</code> if the encoding is supported, otherwise <code>false</code>
     */
    public boolean isTargetEncodingSupported(AudioFormat.Encoding targetEncoding){

        AudioFormat.Encoding targetEncodings[] = getTargetEncodings();

        for(int i=0; i<targetEncodings.length; i++) {
            if( targetEncoding.equals( targetEncodings[i]) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains the set of target format encodings supported by the format converter
     * given a particular source format.
     * If no target format encodings are supported for this source format,
     * an array of length 0 is returned.
     * <p>
     *  获取由给定特定源格式的格式转换器支持的一组目标格式编码。如果此源格式不支持目标格式编码,则返回长度为0的数组。
     * 
     * 
     * @param sourceFormat format of the incoming data
     * @return array of supported target format encodings.
     */
    public abstract AudioFormat.Encoding[] getTargetEncodings(AudioFormat sourceFormat);


    /**
     * Indicates whether the format converter supports conversion to a particular encoding
     * from a particular format.
     * <p>
     * 指示格式转换器是否支持从特定格式转换为特定编码。
     * 
     * 
     * @param targetEncoding desired encoding of the outgoing data
     * @param sourceFormat format of the incoming data
     * @return <code>true</code> if the conversion is supported, otherwise <code>false</code>
     */
    public boolean isConversionSupported(AudioFormat.Encoding targetEncoding, AudioFormat sourceFormat){

        AudioFormat.Encoding targetEncodings[] = getTargetEncodings(sourceFormat);

        for(int i=0; i<targetEncodings.length; i++) {
            if( targetEncoding.equals( targetEncodings[i]) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains the set of target formats with the encoding specified
     * supported by the format converter
     * If no target formats with the specified encoding are supported
     * for this source format, an array of length 0 is returned.
     * <p>
     *  使用格式转换器支持的编码获取目标格式集合如果此源格式不支持具有指定编码的目标格式,则返回长度为0的数组。
     * 
     * 
     * @param targetEncoding desired encoding of the stream after processing
     * @param sourceFormat format of the incoming data
     * @return array of supported target formats.
     */
    public abstract AudioFormat[] getTargetFormats(AudioFormat.Encoding targetEncoding, AudioFormat sourceFormat);


    /**
     * Indicates whether the format converter supports conversion to one
     * particular format from another.
     * <p>
     *  指示格式转换器是否支持从另一种格式转换为一种特定格式。
     * 
     * 
     * @param targetFormat desired format of outgoing data
     * @param sourceFormat format of the incoming data
     * @return <code>true</code> if the conversion is supported, otherwise <code>false</code>
     */
    public boolean isConversionSupported(AudioFormat targetFormat, AudioFormat sourceFormat){

        AudioFormat targetFormats[] = getTargetFormats( targetFormat.getEncoding(), sourceFormat );

        for(int i=0; i<targetFormats.length; i++) {
            if( targetFormat.matches( targetFormats[i] ) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains an audio input stream with the specified encoding from the given audio
     * input stream.
     * <p>
     *  从给定的音频输入流获得具有指定编码的音频输入流。
     * 
     * 
     * @param targetEncoding desired encoding of the stream after processing
     * @param sourceStream stream from which data to be processed should be read
     * @return stream from which processed data with the specified target encoding may be read
     * @throws IllegalArgumentException if the format combination supplied is
     * not supported.
     */
    public abstract AudioInputStream getAudioInputStream(AudioFormat.Encoding targetEncoding, AudioInputStream sourceStream);


    /**
     * Obtains an audio input stream with the specified format from the given audio
     * input stream.
     * <p>
     *  从给定的音频输入流获取具有指定格式的音频输入流。
     * 
     * @param targetFormat desired data format of the stream after processing
     * @param sourceStream stream from which data to be processed should be read
     * @return stream from which processed data with the specified format may be read
     * @throws IllegalArgumentException if the format combination supplied is
     * not supported.
     */
    public abstract AudioInputStream getAudioInputStream(AudioFormat targetFormat, AudioInputStream sourceStream);

}
