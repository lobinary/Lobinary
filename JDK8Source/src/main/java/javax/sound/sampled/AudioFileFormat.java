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

package javax.sound.sampled;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * An instance of the <code>AudioFileFormat</code> class describes
 * an audio file, including the file type, the file's length in bytes,
 * the length in sample frames of the audio data contained in the file,
 * and the format of the audio data.
 * <p>
 * The <code>{@link AudioSystem}</code> class includes methods for determining the format
 * of an audio file, obtaining an audio input stream from an audio file, and
 * writing an audio file from an audio input stream.
 *
 * <p>An <code>AudioFileFormat</code> object can
 * include a set of properties. A property is a pair of key and value:
 * the key is of type <code>String</code>, the associated property
 * value is an arbitrary object.
 * Properties specify additional informational
 * meta data (like a author, copyright, or file duration).
 * Properties are optional information, and file reader and file
 * writer implementations are not required to provide or
 * recognize properties.
 *
 * <p>The following table lists some common properties that should
 * be used in implementations:
 *
 * <table border=1>
 *  <caption>Audio File Format Properties</caption>
 *  <tr>
 *   <th>Property key</th>
 *   <th>Value type</th>
 *   <th>Description</th>
 *  </tr>
 *  <tr>
 *   <td>&quot;duration&quot;</td>
 *   <td>{@link java.lang.Long Long}</td>
 *   <td>playback duration of the file in microseconds</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;author&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>name of the author of this file</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;title&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>title of this file</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;copyright&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>copyright message</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;date&quot;</td>
 *   <td>{@link java.util.Date Date}</td>
 *   <td>date of the recording or release</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;comment&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>an arbitrary text</td>
 *  </tr>
 * </table>
 *
 *
 * <p>
 *  <code> AudioFileFormat </code>类的实例描述了音频文件,包括文件类型,文件的字节长度,包含在文件中的音频数据的采样帧中的长度,以及音频数据的格式。
 * <p>
 *  <code> {@ link AudioSystem} </code>类包括用于确定音频文件的格式,从音频文件获得音频输入流以及从音频输入流写入音频文件的方法。
 * 
 *  <p> <code> AudioFileFormat </code>对象可以包含一组属性。属性是一对键和值：键是<code> String </code>类型,关联的属性值是一个任意对象。
 * 属性指定附加的信息元数据(如作者,版权或文件持续时间)。属性是可选信息,并且文件读取器和文件写入器实现不需要提供或识别属性。
 * 
 *  <p>下表列出了应在实施中使用的一些常见属性：
 * 
 * <table border=1>
 *  <caption>音频文件格式属性</caption>
 * <tr>
 *  <th>属性键</th> <th>值类型</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td>"duration"</td> <td> {@ link java.lang.Long Long} </td> <td>文件的播放持续时间(以微秒为单位)</td>
 * </tr>
 * <tr>
 *  <td>&quot; author&quot; </td> <td> {@ link java.lang.String String} </td> <td>此文件作者的姓名</td>
 * </tr>
 * <tr>
 * <td>&quot; title&quot; </td> <td> {@ link java.lang.String String} </td> <td>此文件的标题</td>
 * </tr>
 * <tr>
 *  <td>&quot; copyright&quot; </td> <td> {@ link java.lang.String String} </td> <td>版权讯息</td>
 * </tr>
 * <tr>
 *  <td>"date"</td> <td> {@ link java.util.Date Date} </td> <td>录制或发布日期</td>
 * </tr>
 * <tr>
 *  <td>&quot; comment&quot; </td> <td> {@ link java.lang.String String} </td> <td>任意文字</td>
 * </tr>
 * </table>
 * 
 * 
 * @author David Rivas
 * @author Kara Kytle
 * @author Florian Bomers
 * @see AudioInputStream
 * @since 1.3
 */
public class AudioFileFormat {


    // INSTANCE VARIABLES


    /**
     * File type.
     * <p>
     *  文件类型。
     * 
     */
    private Type type;

    /**
     * File length in bytes
     * <p>
     *  文件长度(字节)
     * 
     */
    private int byteLength;

    /**
     * Format of the audio data contained in the file.
     * <p>
     *  包含在文件中的音频数据的格式。
     * 
     */
    private AudioFormat format;

    /**
     * Audio data length in sample frames
     * <p>
     *  采样帧中的音频数据长度
     * 
     */
    private int frameLength;


    /** The set of properties */
    private HashMap<String, Object> properties;


    /**
     * Constructs an audio file format object.
     * This protected constructor is intended for use by providers of file-reading
     * services when returning information about an audio file or about supported audio file
     * formats.
     * <p>
     *  构造音频文件格式对象。此受保护的构造函数用于在返回有关音频文件或有关支持的音频文件格式的信息时由文件读取服务的提供者使用。
     * 
     * 
     * @param type the type of the audio file
     * @param byteLength the length of the file in bytes, or <code>AudioSystem.NOT_SPECIFIED</code>
     * @param format the format of the audio data contained in the file
     * @param frameLength the audio data length in sample frames, or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see #getType
     */
    protected AudioFileFormat(Type type, int byteLength, AudioFormat format, int frameLength) {

        this.type = type;
        this.byteLength = byteLength;
        this.format = format;
        this.frameLength = frameLength;
        this.properties = null;
    }


    /**
     * Constructs an audio file format object.
     * This public constructor may be used by applications to describe the
     * properties of a requested audio file.
     * <p>
     *  构造音频文件格式对象。这个公共构造器可以被应用使用以描述所请求的音频文件的属性。
     * 
     * 
     * @param type the type of the audio file
     * @param format the format of the audio data contained in the file
     * @param frameLength the audio data length in sample frames, or <code>AudioSystem.NOT_SPECIFIED</code>
     */
    public AudioFileFormat(Type type, AudioFormat format, int frameLength) {


        this(type,AudioSystem.NOT_SPECIFIED,format,frameLength);
    }

    /**
     * Construct an audio file format object with a set of
     * defined properties.
     * This public constructor may be used by applications to describe the
     * properties of a requested audio file. The properties map
     * will be copied to prevent any changes to it.
     *
     * <p>
     *  使用一组定义的属性构造音频文件格式对象。这个公共构造器可以被应用使用以描述所请求的音频文件的属性。将复制属性映射以防止对其进行任何更改。
     * 
     * 
     * @param type        the type of the audio file
     * @param format      the format of the audio data contained in the file
     * @param frameLength the audio data length in sample frames, or
     *                    <code>AudioSystem.NOT_SPECIFIED</code>
     * @param properties  a <code>Map&lt;String,Object&gt;</code> object
     *        with properties
     *
     * @since 1.5
     */
    public AudioFileFormat(Type type, AudioFormat format,
                           int frameLength, Map<String, Object> properties) {
        this(type,AudioSystem.NOT_SPECIFIED,format,frameLength);
        this.properties = new HashMap<String, Object>(properties);
    }


    /**
     * Obtains the audio file type, such as <code>WAVE</code> or <code>AU</code>.
     * <p>
     *  获取音频文件类型,例如<code> WAVE </code>或<code> AU </code>。
     * 
     * 
     * @return the audio file type
     *
     * @see Type#WAVE
     * @see Type#AU
     * @see Type#AIFF
     * @see Type#AIFC
     * @see Type#SND
     */
    public Type getType() {
        return type;
    }

    /**
     * Obtains the size in bytes of the entire audio file (not just its audio data).
     * <p>
     *  获取整个音频文件(不仅仅是其音频数据)的字节大小。
     * 
     * 
     * @return the audio file length in bytes
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getByteLength() {
        return byteLength;
    }

    /**
     * Obtains the format of the audio data contained in the audio file.
     * <p>
     *  获取音频文件中包含的音频数据的格式。
     * 
     * 
     * @return the audio data format
     */
    public AudioFormat getFormat() {
        return format;
    }

    /**
     * Obtains the length of the audio data contained in the file, expressed in sample frames.
     * <p>
     *  获取包含在文件中的音频数据的长度,以采样帧表示。
     * 
     * 
     * @return the number of sample frames of audio data in the file
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getFrameLength() {
        return frameLength;
    }

    /**
     * Obtain an unmodifiable map of properties.
     * The concept of properties is further explained in
     * the {@link AudioFileFormat class description}.
     *
     * <p>
     * 获取一个不可修改的属性映射。属性的概念在{@link AudioFileFormat类描述}中进一步解释。
     * 
     * 
     * @return a <code>Map&lt;String,Object&gt;</code> object containing
     *         all properties. If no properties are recognized, an empty map is
     *         returned.
     *
     * @see #getProperty(String)
     * @since 1.5
     */
    public Map<String,Object> properties() {
        Map<String,Object> ret;
        if (properties == null) {
            ret = new HashMap<String,Object>(0);
        } else {
            ret = (Map<String,Object>) (properties.clone());
        }
        return (Map<String,Object>) Collections.unmodifiableMap(ret);
    }


    /**
     * Obtain the property value specified by the key.
     * The concept of properties is further explained in
     * the {@link AudioFileFormat class description}.
     *
     * <p>If the specified property is not defined for a
     * particular file format, this method returns
     * <code>null</code>.
     *
     * <p>
     *  获取由键指定的属性值。属性的概念在{@link AudioFileFormat类描述}中进一步解释。
     * 
     *  <p>如果未为特定文件格式定义指定的属性,此方法将返回<code> null </code>。
     * 
     * 
     * @param key the key of the desired property
     * @return the value of the property with the specified key,
     *         or <code>null</code> if the property does not exist.
     *
     * @see #properties()
     * @since 1.5
     */
    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }


    /**
     * Provides a string representation of the file format.
     * <p>
     *  提供文件格式的字符串表示形式。
     * 
     * 
     * @return the file format as a string
     */
    public String toString() {

        StringBuffer buf = new StringBuffer();

        //$$fb2002-11-01: fix for 4672864: AudioFileFormat.toString() throws unexpected NullPointerException
        if (type != null) {
            buf.append(type.toString() + " (." + type.getExtension() + ") file");
        } else {
            buf.append("unknown file format");
        }

        if (byteLength != AudioSystem.NOT_SPECIFIED) {
            buf.append(", byte length: " + byteLength);
        }

        buf.append(", data format: " + format);

        if (frameLength != AudioSystem.NOT_SPECIFIED) {
            buf.append(", frame length: " + frameLength);
        }

        return new String(buf);
    }


    /**
     * An instance of the <code>Type</code> class represents one of the
     * standard types of audio file.  Static instances are provided for the
     * common types.
     * <p>
     *  <code> Type </code>类的实例表示音频文件的标准类型之一。为常见类型提供静态实例。
     * 
     */
    public static class Type {

        // FILE FORMAT TYPE DEFINES

        /**
         * Specifies a WAVE file.
         * <p>
         *  指定WAVE文件。
         * 
         */
        public static final Type WAVE = new Type("WAVE", "wav");

        /**
         * Specifies an AU file.
         * <p>
         *  指定AU文件。
         * 
         */
        public static final Type AU = new Type("AU", "au");

        /**
         * Specifies an AIFF file.
         * <p>
         *  指定AIFF文件。
         * 
         */
        public static final Type AIFF = new Type("AIFF", "aif");

        /**
         * Specifies an AIFF-C file.
         * <p>
         *  指定AIFF-C文件。
         * 
         */
        public static final Type AIFC = new Type("AIFF-C", "aifc");

        /**
         * Specifies a SND file.
         * <p>
         *  指定SND文件。
         * 
         */
        public static final Type SND = new Type("SND", "snd");


        // INSTANCE VARIABLES

        /**
         * File type name.
         * <p>
         *  文件类型名称。
         * 
         */
        private final String name;

        /**
         * File type extension.
         * <p>
         *  文件类型扩展名。
         * 
         */
        private final String extension;


        // CONSTRUCTOR

        /**
         * Constructs a file type.
         * <p>
         *  构造文件类型。
         * 
         * 
         * @param name the string that names the file type
         * @param extension the string that commonly marks the file type
         * without leading dot.
         */
        public Type(String name, String extension) {

            this.name = name;
            this.extension = extension;
        }


        // METHODS

        /**
         * Finalizes the equals method
         * <p>
         *  完成equals方法
         * 
         */
        public final boolean equals(Object obj) {
            if (toString() == null) {
                return (obj != null) && (obj.toString() == null);
            }
            if (obj instanceof Type) {
                return toString().equals(obj.toString());
            }
            return false;
        }

        /**
         * Finalizes the hashCode method
         * <p>
         *  完成hashCode方法
         * 
         */
        public final int hashCode() {
            if (toString() == null) {
                return 0;
            }
            return toString().hashCode();
        }

        /**
         * Provides the file type's name as the <code>String</code> representation
         * of the file type.
         * <p>
         *  将文件类型的名称提供为文件类型的<code> String </code>表示形式。
         * 
         * 
         * @return the file type's name
         */
        public final String toString() {
            return name;
        }

        /**
         * Obtains the common file name extension for this file type.
         * <p>
         *  获取此文件类型的公共文件扩展名。
         * 
         * @return file type extension
         */
        public String getExtension() {
            return extension;
        }

    } // class Type

} // class AudioFileFormat
