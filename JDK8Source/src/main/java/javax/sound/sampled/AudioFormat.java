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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>AudioFormat</code> is the class that specifies a particular arrangement of data in a sound stream.
 * By examining the information stored in the audio format, you can discover how to interpret the bits in the
 * binary sound data.
 * <p>
 * Every data line has an audio format associated with its data stream. The audio format of a source (playback) data line indicates
 * what kind of data the data line expects to receive for output.  For a target (capture) data line, the audio format specifies the kind
 * of the data that can be read from the line.
 * Sound files also have audio formats, of course.  The <code>{@link AudioFileFormat}</code>
 * class encapsulates an <code>AudioFormat</code> in addition to other,
 * file-specific information.  Similarly, an <code>{@link AudioInputStream}</code> has an
 * <code>AudioFormat</code>.
 * <p>
 * The <code>AudioFormat</code> class accommodates a number of common sound-file encoding techniques, including
 * pulse-code modulation (PCM), mu-law encoding, and a-law encoding.  These encoding techniques are predefined,
 * but service providers can create new encoding types.
 * The encoding that a specific format uses is named by its <code>encoding</code> field.
 *<p>
 * In addition to the encoding, the audio format includes other properties that further specify the exact
 * arrangement of the data.
 * These include the number of channels, sample rate, sample size, byte order, frame rate, and frame size.
 * Sounds may have different numbers of audio channels: one for mono, two for stereo.
 * The sample rate measures how many "snapshots" (samples) of the sound pressure are taken per second, per channel.
 * (If the sound is stereo rather than mono, two samples are actually measured at each instant of time: one for the left channel,
 * and another for the right channel; however, the sample rate still measures the number per channel, so the rate is the same
 * regardless of the number of channels.   This is the standard use of the term.)
 * The sample size indicates how many bits are used to store each snapshot; 8 and 16 are typical values.
 * For 16-bit samples (or any other sample size larger than a byte),
 * byte order is important; the bytes in each sample are arranged in
 * either the "little-endian" or "big-endian" style.
 * For encodings like PCM, a frame consists of the set of samples for all channels at a given
 * point in time, and so the size of a frame (in bytes) is always equal to the size of a sample (in bytes) times
 * the number of channels.  However, with some other sorts of encodings a frame can contain
 * a bundle of compressed data for a whole series of samples, as well as additional, non-sample
 * data.  For such encodings, the sample rate and sample size refer to the data after it is decoded into PCM,
 * and so they are completely different from the frame rate and frame size.
 *
 * <p>An <code>AudioFormat</code> object can include a set of
 * properties. A property is a pair of key and value: the key
 * is of type <code>String</code>, the associated property
 * value is an arbitrary object. Properties specify
 * additional format specifications, like the bit rate for
 * compressed formats. Properties are mainly used as a means
 * to transport additional information of the audio format
 * to and from the service providers. Therefore, properties
 * are ignored in the {@link #matches(AudioFormat)} method.
 * However, methods which rely on the installed service
 * providers, like {@link AudioSystem#isConversionSupported
 * (AudioFormat, AudioFormat) isConversionSupported} may consider
 * properties, depending on the respective service provider
 * implementation.
 *
 * <p>The following table lists some common properties which
 * service providers should use, if applicable:
 *
 * <table border=0>
 *  <caption>Audio Format Properties</caption>
 *  <tr>
 *   <th>Property key</th>
 *   <th>Value type</th>
 *   <th>Description</th>
 *  </tr>
 *  <tr>
 *   <td>&quot;bitrate&quot;</td>
 *   <td>{@link java.lang.Integer Integer}</td>
 *   <td>average bit rate in bits per second</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;vbr&quot;</td>
 *   <td>{@link java.lang.Boolean Boolean}</td>
 *   <td><code>true</code>, if the file is encoded in variable bit
 *       rate (VBR)</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;quality&quot;</td>
 *   <td>{@link java.lang.Integer Integer}</td>
 *   <td>encoding/conversion quality, 1..100</td>
 *  </tr>
 * </table>
 *
 * <p>Vendors of service providers (plugins) are encouraged
 * to seek information about other already established
 * properties in third party plugins, and follow the same
 * conventions.
 *
 * <p>
 *  <code> AudioFormat </code>是指定声音流中特定数据排列的类。通过检查以音频格式存储的信息,您可以发现如何解释二进制声音数据中的位。
 * <p>
 *  每个数据线具有与其数据流相关联的音频格式。源(回放)数据线的音频格式指示数据线期望接收哪种数据用于输出。对于目标(捕获)数据线,音频格式指定可从该行读取的数据的种类。声音文件也有音频格式,当然。
 *  <code> {@ link AudioFileFormat} </code>类封装了一个<code> AudioFormat </code>以及其他特定于文件的信息。
 * 类似地,<code> {@ link AudioInputStream} </code>有一个<code> AudioFormat </code>。
 * <p>
 *  <code> AudioFormat </code>类适应许多常见的声音文件编码技术,包括脉冲编码调制(PCM),mu律编码和a律编码。这些编码技术是预定义的,但服务提供商可以创建新的编码类型。
 * 特定格式使用的编码由其<code> encoding </code>字段命名。
 * p>
 * 除了编码之外,音频格式包括进一步指定数据的精确布置的其他属性。这些包括通道数,采样率,样本大小,字节顺序,帧速率和帧大小。声音可能具有不同数量的音频通道：一个用于单声道,两个用于立体声。
 * 采样率测量每声道每声道采取的声压的"快照"(样本)的数量。
 *  (如果声音是立体声而不是单声道,实际上在每个时刻测量两个采样：一个用于左声道,另一个用于右声道;然而,采样率仍然测量每个通道的数量,因此速率为相同,而不管通道的数目,这是该术语的标准使用。
 * )样本大小指示用于存储每个快照的位数; 8和16是典型值。
 * 对于16位样本(或大于一个字节的任何其他样本大小),字节顺序很重要;每个样本中的字节以"little-endian"或"big-endian"样式排列。
 * 对于诸如PCM的编码,帧由在给定时间点的所有通道的样本集合组成,因此帧的大小(以字节为单位)总是等于样本的大小(以字节为单位)乘以数字的通道。
 * 然而,对于一些其他种类的编码,帧可以包含用于整个系列样本的压缩数据束以及附加的非样本数据。对于这样的编码,采样率和采样大小指的是在它被解码为PCM之后的数据,因此它们与帧速率和帧大小完全不同。
 * 
 * <p> <code> AudioFormat </code>对象可以包含一组属性。属性是一对键和值：键是<code> String </code>类型,关联的属性值是一个任意对象。
 * 属性指定附加的格式规范,例如压缩格式的比特率。属性主要用作向服务提供商传送音频格式的附加信息的手段。因此,在{@link #matches(AudioFormat)}方法中忽略属性。
 * 然而,依赖于已安装的服务提供商的方法,例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)isConversionSupported}
 * 可以考虑属性,这取决于相应的服务提供商实现。
 * 属性指定附加的格式规范,例如压缩格式的比特率。属性主要用作向服务提供商传送音频格式的附加信息的手段。因此,在{@link #matches(AudioFormat)}方法中忽略属性。
 * 
 *  <p>下表列出了服务提供商应使用的一些常见属性(如果适用)：
 * 
 * <table border=0>
 *  <caption>音频格式属性</caption>
 * <tr>
 *  <th>属性键</th> <th>值类型</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td>"bitrate"</td> <td> {@ link java.lang.Integer Integer} </td> <td>平均位速率,以位/秒为单位</td>
 * </tr>
 * <tr>
 *  <td>&lt; td&gt;&quot; vbr&quot; </td> <td> {@ link java.lang.Boolean Boolean} </td> <td> <code> true
 *  </code> VBR)</td>。
 * </tr>
 * <tr>
 *  <td>&quot; quality&quot; </td> <td> {@ link java.lang.Integer Integer} </td> <td> encoding / convers
 * ion quality,1..100 </td>。
 * </tr>
 * </table>
 * 
 * <p>鼓励服务提供商(插件)的供应商查找有关第三方插件中其他已经建立的属性的信息,并遵循相同的约定。
 * 
 * 
 * @author Kara Kytle
 * @author Florian Bomers
 * @see DataLine#getFormat
 * @see AudioInputStream#getFormat
 * @see AudioFileFormat
 * @see javax.sound.sampled.spi.FormatConversionProvider
 * @since 1.3
 */
public class AudioFormat {

    // INSTANCE VARIABLES


    /**
     * The audio encoding technique used by this format.
     * <p>
     *  这种格式使用的音频编码技术。
     * 
     */
    protected Encoding encoding;

    /**
     * The number of samples played or recorded per second, for sounds that have this format.
     * <p>
     *  每秒播放或记录的样本数,适用于具有此格式的声音。
     * 
     */
    protected float sampleRate;

    /**
     * The number of bits in each sample of a sound that has this format.
     * <p>
     *  具有此格式的声音的每个样本中的位数。
     * 
     */
    protected int sampleSizeInBits;

    /**
     * The number of audio channels in this format (1 for mono, 2 for stereo).
     * <p>
     *  此格式的音频通道数(1为单声道,2为立体声)。
     * 
     */
    protected int channels;

    /**
     * The number of bytes in each frame of a sound that has this format.
     * <p>
     *  具有此格式的声音的每个帧中的字节数。
     * 
     */
    protected int frameSize;

    /**
     * The number of frames played or recorded per second, for sounds that have this format.
     * <p>
     *  对于具有此格式的声音,每秒播放或记录的帧数。
     * 
     */
    protected float frameRate;

    /**
     * Indicates whether the audio data is stored in big-endian or little-endian order.
     * <p>
     *  指示音频数据是以big-endian还是little-endian顺序存储。
     * 
     */
    protected boolean bigEndian;


    /** The set of properties */
    private HashMap<String, Object> properties;


    /**
     * Constructs an <code>AudioFormat</code> with the given parameters.
     * The encoding specifies the convention used to represent the data.
     * The other parameters are further explained in the {@link AudioFormat
     * class description}.
     * <p>
     *  用给定的参数构造一个<code> AudioFormat </code>。编码指定用于表示数据的约定。其他参数在{@link AudioFormat类描述}中进一步解释。
     * 
     * 
     * @param encoding                  the audio encoding technique
     * @param sampleRate                the number of samples per second
     * @param sampleSizeInBits  the number of bits in each sample
     * @param channels                  the number of channels (1 for mono, 2 for stereo, and so on)
     * @param frameSize                 the number of bytes in each frame
     * @param frameRate                 the number of frames per second
     * @param bigEndian                 indicates whether the data for a single sample
     *                                                  is stored in big-endian byte order (<code>false</code>
     *                                                  means little-endian)
     */
    public AudioFormat(Encoding encoding, float sampleRate, int sampleSizeInBits,
                       int channels, int frameSize, float frameRate, boolean bigEndian) {

        this.encoding = encoding;
        this.sampleRate = sampleRate;
        this.sampleSizeInBits = sampleSizeInBits;
        this.channels = channels;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.bigEndian = bigEndian;
        this.properties = null;
    }


    /**
     * Constructs an <code>AudioFormat</code> with the given parameters.
     * The encoding specifies the convention used to represent the data.
     * The other parameters are further explained in the {@link AudioFormat
     * class description}.
     * <p>
     *  用给定的参数构造一个<code> AudioFormat </code>。编码指定用于表示数据的约定。其他参数在{@link AudioFormat类描述}中进一步解释。
     * 
     * 
     * @param encoding         the audio encoding technique
     * @param sampleRate       the number of samples per second
     * @param sampleSizeInBits the number of bits in each sample
     * @param channels         the number of channels (1 for mono, 2 for
     *                         stereo, and so on)
     * @param frameSize        the number of bytes in each frame
     * @param frameRate        the number of frames per second
     * @param bigEndian        indicates whether the data for a single sample
     *                         is stored in big-endian byte order
     *                         (<code>false</code> means little-endian)
     * @param properties       a <code>Map&lt;String,Object&gt;</code> object
     *                         containing format properties
     *
     * @since 1.5
     */
    public AudioFormat(Encoding encoding, float sampleRate,
                       int sampleSizeInBits, int channels,
                       int frameSize, float frameRate,
                       boolean bigEndian, Map<String, Object> properties) {
        this(encoding, sampleRate, sampleSizeInBits, channels,
             frameSize, frameRate, bigEndian);
        this.properties = new HashMap<String, Object>(properties);
    }


    /**
     * Constructs an <code>AudioFormat</code> with a linear PCM encoding and
     * the given parameters.  The frame size is set to the number of bytes
     * required to contain one sample from each channel, and the frame rate
     * is set to the sample rate.
     *
     * <p>
     *  构造一个具有线性PCM编码和给定参数的<code> AudioFormat </code>。帧大小被设置为包含来自每个通道的一个采样所需的字节数,并且帧速率被设置为采样速率。
     * 
     * 
     * @param sampleRate                the number of samples per second
     * @param sampleSizeInBits  the number of bits in each sample
     * @param channels                  the number of channels (1 for mono, 2 for stereo, and so on)
     * @param signed                    indicates whether the data is signed or unsigned
     * @param bigEndian                 indicates whether the data for a single sample
     *                                                  is stored in big-endian byte order (<code>false</code>
     *                                                  means little-endian)
     */
    public AudioFormat(float sampleRate, int sampleSizeInBits,
                       int channels, boolean signed, boolean bigEndian) {

        this((signed == true ? Encoding.PCM_SIGNED : Encoding.PCM_UNSIGNED),
             sampleRate,
             sampleSizeInBits,
             channels,
             (channels == AudioSystem.NOT_SPECIFIED || sampleSizeInBits == AudioSystem.NOT_SPECIFIED)?
             AudioSystem.NOT_SPECIFIED:
             ((sampleSizeInBits + 7) / 8) * channels,
             sampleRate,
             bigEndian);
    }

    /**
     * Obtains the type of encoding for sounds in this format.
     *
     * <p>
     *  获取此格式的声音的编码类型。
     * 
     * 
     * @return the encoding type
     * @see Encoding#PCM_SIGNED
     * @see Encoding#PCM_UNSIGNED
     * @see Encoding#ULAW
     * @see Encoding#ALAW
     */
    public Encoding getEncoding() {

        return encoding;
    }

    /**
     * Obtains the sample rate.
     * For compressed formats, the return value is the sample rate of the uncompressed
     * audio data.
     * When this AudioFormat is used for queries (e.g. {@link
     * AudioSystem#isConversionSupported(AudioFormat, AudioFormat)
     * AudioSystem.isConversionSupported}) or capabilities (e.g. {@link
     * DataLine.Info#getFormats() DataLine.Info.getFormats}), a sample rate of
     * <code>AudioSystem.NOT_SPECIFIED</code> means that any sample rate is
     * acceptable. <code>AudioSystem.NOT_SPECIFIED</code> is also returned when
     * the sample rate is not defined for this audio format.
     * <p>
     * 获取采样率。对于压缩格式,返回值是未压缩音频数据的采样率。
     * 当此AudioFormat用于查询(例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)AudioSystem.isConversionSupported}
     * )或功能(例如{@link DataLine.Info#getFormats()DataLine.Info.getFormats})时, of <code> AudioSystem.NOT_SPECIF
     * IED </code>表示任何采样率都是可以接受的。
     * 获取采样率。对于压缩格式,返回值是未压缩音频数据的采样率。当未为此音频格式定义采样率时,还会返回<code> AudioSystem.NOT_SPECIFIED </code>。
     * 
     * 
     * @return the number of samples per second,
     * or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see #getFrameRate()
     * @see AudioSystem#NOT_SPECIFIED
     */
    public float getSampleRate() {

        return sampleRate;
    }

    /**
     * Obtains the size of a sample.
     * For compressed formats, the return value is the sample size of the
     * uncompressed audio data.
     * When this AudioFormat is used for queries (e.g. {@link
     * AudioSystem#isConversionSupported(AudioFormat, AudioFormat)
     * AudioSystem.isConversionSupported}) or capabilities (e.g. {@link
     * DataLine.Info#getFormats() DataLine.Info.getFormats}), a sample size of
     * <code>AudioSystem.NOT_SPECIFIED</code> means that any sample size is
     * acceptable. <code>AudioSystem.NOT_SPECIFIED</code> is also returned when
     * the sample size is not defined for this audio format.
     * <p>
     *  获取样本的大小。对于压缩格式,返回值是未压缩音频数据的样本大小。
     * 当此AudioFormat用于查询(例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)AudioSystem.isConversionSupported}
     * )或能力(例如{@link DataLine.Info#getFormats()DataLine.Info.getFormats})时,样本大小of <code> AudioSystem.NOT_SPE
     * CIFIED </code>意味着任何样本大小都是可以接受的。
     *  获取样本的大小。对于压缩格式,返回值是未压缩音频数据的样本大小。当未为此音频格式定义样本大小时,还会返回<code> AudioSystem.NOT_SPECIFIED </code>。
     * 
     * 
     * @return the number of bits in each sample,
     * or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see #getFrameSize()
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getSampleSizeInBits() {

        return sampleSizeInBits;
    }

    /**
     * Obtains the number of channels.
     * When this AudioFormat is used for queries (e.g. {@link
     * AudioSystem#isConversionSupported(AudioFormat, AudioFormat)
     * AudioSystem.isConversionSupported}) or capabilities (e.g. {@link
     * DataLine.Info#getFormats() DataLine.Info.getFormats}), a return value of
     * <code>AudioSystem.NOT_SPECIFIED</code> means that any (positive) number of channels is
     * acceptable.
     * <p>
     * 获取通道数。
     * 当此AudioFormat用于查询(例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)AudioSystem.isConversionSupported}
     * )或能力(例如{@link DataLine.Info#getFormats()DataLine.Info.getFormats})时,返回值of <code> AudioSystem.NOT_SPEC
     * IFIED </code>意味着任何(正)数量的通道都是可接受的。
     * 获取通道数。
     * 
     * 
     * @return The number of channels (1 for mono, 2 for stereo, etc.),
     * or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getChannels() {

        return channels;
    }

    /**
     * Obtains the frame size in bytes.
     * When this AudioFormat is used for queries (e.g. {@link
     * AudioSystem#isConversionSupported(AudioFormat, AudioFormat)
     * AudioSystem.isConversionSupported}) or capabilities (e.g. {@link
     * DataLine.Info#getFormats() DataLine.Info.getFormats}), a frame size of
     * <code>AudioSystem.NOT_SPECIFIED</code> means that any frame size is
     * acceptable. <code>AudioSystem.NOT_SPECIFIED</code> is also returned when
     * the frame size is not defined for this audio format.
     * <p>
     *  以字节为单位获取帧大小。
     * 当此AudioFormat用于查询(例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)AudioSystem.isConversionSupported}
     * )或能力(例如{@link DataLine.Info#getFormats()DataLine.Info.getFormats})时, of <code> AudioSystem.NOT_SPECIF
     * IED </code>表示任何帧大小都是可以接受的。
     *  以字节为单位获取帧大小。当未为此音频格式定义帧大小时,还会返回<code> AudioSystem.NOT_SPECIFIED </code>。
     * 
     * 
     * @return the number of bytes per frame,
     * or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see #getSampleSizeInBits()
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getFrameSize() {

        return frameSize;
    }

    /**
     * Obtains the frame rate in frames per second.
     * When this AudioFormat is used for queries (e.g. {@link
     * AudioSystem#isConversionSupported(AudioFormat, AudioFormat)
     * AudioSystem.isConversionSupported}) or capabilities (e.g. {@link
     * DataLine.Info#getFormats() DataLine.Info.getFormats}), a frame rate of
     * <code>AudioSystem.NOT_SPECIFIED</code> means that any frame rate is
     * acceptable. <code>AudioSystem.NOT_SPECIFIED</code> is also returned when
     * the frame rate is not defined for this audio format.
     * <p>
     *  以每秒帧数获取帧速率。
     * 当这个AudioFormat用于查询(例如{@link AudioSystem#isConversionSupported(AudioFormat,AudioFormat)AudioSystem.isConversionSupported}
     * )或能力(例如{@link DataLine.Info#getFormats()DataLine.Info.getFormats}的<code> AudioSystem.NOT_SPECIFIED </code>
     * 表示任何帧速率都是可以接受的。
     *  以每秒帧数获取帧速率。当未为此音频格式定义帧速率时,还会返回<code> AudioSystem.NOT_SPECIFIED </code>。
     * 
     * 
     * @return the number of frames per second,
     * or <code>AudioSystem.NOT_SPECIFIED</code>
     *
     * @see #getSampleRate()
     * @see AudioSystem#NOT_SPECIFIED
     */
    public float getFrameRate() {

        return frameRate;
    }


    /**
     * Indicates whether the audio data is stored in big-endian or little-endian
     * byte order.  If the sample size is not more than one byte, the return value is
     * irrelevant.
     * <p>
     * 指示音频数据是以大尾数还是小尾数字节顺序存储。如果样本大小不超过一个字节,返回值是不相关的。
     * 
     * 
     * @return <code>true</code> if the data is stored in big-endian byte order,
     * <code>false</code> if little-endian
     */
    public boolean isBigEndian() {

        return bigEndian;
    }


    /**
     * Obtain an unmodifiable map of properties.
     * The concept of properties is further explained in
     * the {@link AudioFileFormat class description}.
     *
     * <p>
     *  获取一个不可修改的属性映射。属性的概念在{@link AudioFileFormat类描述}中进一步解释。
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
     * Indicates whether this format matches the one specified.
     * To match, two formats must have the same encoding,
     * and consistent values of the number of channels, sample rate, sample size,
     * frame rate, and frame size.
     * The values of the property are consistent if they are equal
     * or the specified format has the property value
     * {@code AudioSystem.NOT_SPECIFIED}.
     * The byte order (big-endian or little-endian) must be the same
     * if the sample size is greater than one byte.
     *
     * <p>
     *  指示此格式是否与指定的格式匹配。为了匹配,两种格式必须具有相同的编码,以及通道数,采样率,样本大小,帧速率和帧大小的一致值。
     * 如果属性的值相等或指定的格式具有属性值{@code AudioSystem.NOT_SPECIFIED},则属性的值是一致的。如果样本大小大于一个字节,则字节顺序(大端或小端)必须相同。
     * 
     * 
     * @param format format to test for match
     * @return {@code true} if this format matches the one specified,
     *         {@code false} otherwise.
     */
    public boolean matches(AudioFormat format) {
        if (format.getEncoding().equals(getEncoding())
                && (format.getChannels() == AudioSystem.NOT_SPECIFIED
                    || format.getChannels() == getChannels())
                && (format.getSampleRate() == (float)AudioSystem.NOT_SPECIFIED
                    || format.getSampleRate() == getSampleRate())
                && (format.getSampleSizeInBits() == AudioSystem.NOT_SPECIFIED
                    || format.getSampleSizeInBits() == getSampleSizeInBits())
                && (format.getFrameRate() == (float)AudioSystem.NOT_SPECIFIED
                    || format.getFrameRate() == getFrameRate())
                && (format.getFrameSize() == AudioSystem.NOT_SPECIFIED
                    || format.getFrameSize() == getFrameSize())
                && (getSampleSizeInBits() <= 8
                    || format.isBigEndian() == isBigEndian())) {
            return true;
        }
        return false;
    }


    /**
     * Returns a string that describes the format, such as:
     * "PCM SIGNED 22050 Hz 16 bit mono big-endian".  The contents of the string
     * may vary between implementations of Java Sound.
     *
     * <p>
     *  返回描述格式的字符串,例如："PCM SIGNED 22050 Hz 16 bit mono big-endian"。字符串的内容可能随Java Sound的实现而有所不同。
     * 
     * 
     * @return a string that describes the format parameters
     */
    public String toString() {
        String sEncoding = "";
        if (getEncoding() != null) {
            sEncoding = getEncoding().toString() + " ";
        }

        String sSampleRate;
        if (getSampleRate() == (float) AudioSystem.NOT_SPECIFIED) {
            sSampleRate = "unknown sample rate, ";
        } else {
            sSampleRate = "" + getSampleRate() + " Hz, ";
        }

        String sSampleSizeInBits;
        if (getSampleSizeInBits() == (float) AudioSystem.NOT_SPECIFIED) {
            sSampleSizeInBits = "unknown bits per sample, ";
        } else {
            sSampleSizeInBits = "" + getSampleSizeInBits() + " bit, ";
        }

        String sChannels;
        if (getChannels() == 1) {
            sChannels = "mono, ";
        } else
            if (getChannels() == 2) {
                sChannels = "stereo, ";
            } else {
                if (getChannels() == AudioSystem.NOT_SPECIFIED) {
                    sChannels = " unknown number of channels, ";
                } else {
                    sChannels = ""+getChannels()+" channels, ";
                }
            }

        String sFrameSize;
        if (getFrameSize() == (float) AudioSystem.NOT_SPECIFIED) {
            sFrameSize = "unknown frame size, ";
        } else {
            sFrameSize = "" + getFrameSize()+ " bytes/frame, ";
        }

        String sFrameRate = "";
        if (Math.abs(getSampleRate() - getFrameRate()) > 0.00001) {
            if (getFrameRate() == (float) AudioSystem.NOT_SPECIFIED) {
                sFrameRate = "unknown frame rate, ";
            } else {
                sFrameRate = getFrameRate() + " frames/second, ";
            }
        }

        String sEndian = "";
        if ((getEncoding().equals(Encoding.PCM_SIGNED)
             || getEncoding().equals(Encoding.PCM_UNSIGNED))
            && ((getSampleSizeInBits() > 8)
                || (getSampleSizeInBits() == AudioSystem.NOT_SPECIFIED))) {
            if (isBigEndian()) {
                sEndian = "big-endian";
            } else {
                sEndian = "little-endian";
            }
        }

        return sEncoding
            + sSampleRate
            + sSampleSizeInBits
            + sChannels
            + sFrameSize
            + sFrameRate
            + sEndian;

    }

    /**
     * The <code>Encoding</code> class  names the  specific type of data representation
     * used for an audio stream.   The encoding includes aspects of the
     * sound format other than the number of channels, sample rate, sample size,
     * frame rate, frame size, and byte order.
     * <p>
     * One ubiquitous type of audio encoding is pulse-code modulation (PCM),
     * which is simply a linear (proportional) representation of the sound
     * waveform.  With PCM, the number stored in each sample is proportional
     * to the instantaneous amplitude of the sound pressure at that point in
     * time.  The numbers may be signed or unsigned integers or floats.
     * Besides PCM, other encodings include mu-law and a-law, which are nonlinear
     * mappings of the sound amplitude that are often used for recording speech.
     * <p>
     * You can use a predefined encoding by referring to one of the static
     * objects created by this class, such as PCM_SIGNED or
     * PCM_UNSIGNED.  Service providers can create new encodings, such as
     * compressed audio formats, and make
     * these available through the <code>{@link AudioSystem}</code> class.
     * <p>
     * The <code>Encoding</code> class is static, so that all
     * <code>AudioFormat</code> objects that have the same encoding will refer
     * to the same object (rather than different instances of the same class).
     * This allows matches to be made by checking that two format's encodings
     * are equal.
     *
     * <p>
     *  <code> Encoding </code>类命名用于音频流的特定类型的数据表示。编码包括除了声道数量,采样率,样本大小,帧速率,帧大小和字节顺序之外的声音格式的方面。
     * <p>
     * 一种普遍存在的音频编码类型是脉冲编码调制(PCM),其简单地是声音波形的线性(成比例)表示。使用PCM,存储在每个样本中的数字与在该时间点的声压的瞬时幅度成比例。数字可以是有符号或无符号整数或浮点数。
     * 除了PCM,其他编码包括mu律和a律,其是经常用于记录语音的声音幅度的非线性映射。
     * <p>
     *  您可以通过引用此类创建的一个静态对象(例如PCM_SIGNED或PCM_UNSIGNED)来使用预定义的编码。
     * 服务提供商可以创建新的编码,例如压缩音频格式,并通过<code> {@ link AudioSystem} </code>类提供这些编码。
     * <p>
     *  <code> Encoding </code>类是静态的,因此所有具有相同编码的<code> AudioFormat </code>对象将引用同一个对象(而不是同一个类的不同实例)。
     * 这允许通过检查两个格式的编码是否相等来进行匹配。
     * 
     * 
     * @see AudioFormat
     * @see javax.sound.sampled.spi.FormatConversionProvider
     *
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Encoding {


        // ENCODING DEFINES

        /**
         * Specifies signed, linear PCM data.
         * <p>
         *  指定带符号的线性PCM数据。
         * 
         */
        public static final Encoding PCM_SIGNED = new Encoding("PCM_SIGNED");

        /**
         * Specifies unsigned, linear PCM data.
         * <p>
         *  指定无符号的线性PCM数据。
         * 
         */
        public static final Encoding PCM_UNSIGNED = new Encoding("PCM_UNSIGNED");

        /**
         * Specifies floating-point PCM data.
         *
         * <p>
         *  指定浮点PCM数据。
         * 
         * 
         * @since 1.7
         */
        public static final Encoding PCM_FLOAT = new Encoding("PCM_FLOAT");

        /**
         * Specifies u-law encoded data.
         * <p>
         *  指定u律编码数据。
         * 
         */
        public static final Encoding ULAW = new Encoding("ULAW");

        /**
         * Specifies a-law encoded data.
         * <p>
         *  指定a-law编码数据。
         * 
         */
        public static final Encoding ALAW = new Encoding("ALAW");


        // INSTANCE VARIABLES

        /**
         * Encoding name.
         * <p>
         *  编码名称。
         * 
         */
        private String name;


        // CONSTRUCTOR

        /**
         * Constructs a new encoding.
         * <p>
         *  构造新的编码。
         * 
         * 
         * @param name  the name of the new type of encoding
         */
        public Encoding(String name) {
            this.name = name;
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
            if (obj instanceof Encoding) {
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
         * Provides the <code>String</code> representation of the encoding.  This <code>String</code> is
         * the same name that was passed to the constructor.  For the predefined encodings, the name
         * is similar to the encoding's variable (field) name.  For example, <code>PCM_SIGNED.toString()</code> returns
         * the name "pcm_signed".
         *
         * <p>
         * 提供编码的<code> String </code>表示形式。此<code> String </code>与传递给构造函数的名称相同。对于预定义的编码,名称类似于编码的变量(字段)名称。
         * 例如,<code> PCM_SIGNED.toString()</code>返回名称"pcm_signed"。
         * 
         * @return the encoding name
         */
        public final String toString() {
            return name;
        }

    } // class Encoding
}
