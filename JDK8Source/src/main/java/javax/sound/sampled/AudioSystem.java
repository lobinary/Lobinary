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
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.ArrayList;

import javax.sound.sampled.spi.AudioFileWriter;
import javax.sound.sampled.spi.AudioFileReader;
import javax.sound.sampled.spi.FormatConversionProvider;
import javax.sound.sampled.spi.MixerProvider;

import com.sun.media.sound.JDK13Services;

/* $fb TODO:
 * - consistent usage of (typed) collections
 * <p>
 *   - (类型化)集合的一致使用
 * 
 */


/**
 * The <code>AudioSystem</code> class acts as the entry point to the
 * sampled-audio system resources. This class lets you query and
 * access the mixers that are installed on the system.
 * <code>AudioSystem</code> includes a number of
 * methods for converting audio data between different formats, and for
 * translating between audio files and streams. It also provides a method
 * for obtaining a <code>{@link Line}</code> directly from the
 * <code>AudioSystem</code> without dealing explicitly
 * with mixers.
 *
 * <p>Properties can be used to specify the default mixer
 * for specific line types.
 * Both system properties and a properties file are considered.
 * The <code>sound.properties</code> properties file is read from
 * an implementation-specific location (typically it is the <code>lib</code>
 * directory in the Java installation directory).
 * If a property exists both as a system property and in the
 * properties file, the system property takes precedence. If none is
 * specified, a suitable default is chosen among the available devices.
 * The syntax of the properties file is specified in
 * {@link java.util.Properties#load(InputStream) Properties.load}. The
 * following table lists the available property keys and which methods
 * consider them:
 *
 * <table border=0>
 *  <caption>Audio System Property Keys</caption>
 *  <tr>
 *   <th>Property Key</th>
 *   <th>Interface</th>
 *   <th>Affected Method(s)</th>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.sampled.Clip</code></td>
 *   <td>{@link Clip}</td>
 *   <td>{@link #getLine}, {@link #getClip}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.sampled.Port</code></td>
 *   <td>{@link Port}</td>
 *   <td>{@link #getLine}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.sampled.SourceDataLine</code></td>
 *   <td>{@link SourceDataLine}</td>
 *   <td>{@link #getLine}, {@link #getSourceDataLine}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.sampled.TargetDataLine</code></td>
 *   <td>{@link TargetDataLine}</td>
 *   <td>{@link #getLine}, {@link #getTargetDataLine}</td>
 *  </tr>
 * </table>
 *
 * The property value consists of the provider class name
 * and the mixer name, separated by the hash mark (&quot;#&quot;).
 * The provider class name is the fully-qualified
 * name of a concrete {@link javax.sound.sampled.spi.MixerProvider
 * mixer provider} class. The mixer name is matched against
 * the <code>String</code> returned by the <code>getName</code>
 * method of <code>Mixer.Info</code>.
 * Either the class name, or the mixer name may be omitted.
 * If only the class name is specified, the trailing hash mark
 * is optional.
 *
 * <p>If the provider class is specified, and it can be
 * successfully retrieved from the installed providers, the list of
 * <code>Mixer.Info</code> objects is retrieved
 * from the provider. Otherwise, or when these mixers
 * do not provide a subsequent match, the list is retrieved
 * from {@link #getMixerInfo} to contain
 * all available <code>Mixer.Info</code> objects.
 *
 * <p>If a mixer name is specified, the resulting list of
 * <code>Mixer.Info</code> objects is searched:
 * the first one with a matching name, and whose
 * <code>Mixer</code> provides the
 * respective line interface, will be returned.
 * If no matching <code>Mixer.Info</code> object
 * is found, or the mixer name is not specified,
 * the first mixer from the resulting
 * list, which provides the respective line
 * interface, will be returned.
 *
 * For example, the property <code>javax.sound.sampled.Clip</code>
 * with a value
 * <code>&quot;com.sun.media.sound.MixerProvider#SunClip&quot;</code>
 * will have the following consequences when
 * <code>getLine</code> is called requesting a <code>Clip</code>
 * instance:
 * if the class <code>com.sun.media.sound.MixerProvider</code> exists
 * in the list of installed mixer providers,
 * the first <code>Clip</code> from the first mixer with name
 * <code>&quot;SunClip&quot;</code> will be returned. If it cannot
 * be found, the first <code>Clip</code> from the first mixer
 * of the specified provider will be returned, regardless of name.
 * If there is none, the first <code>Clip</code> from the first
 * <code>Mixer</code> with name
 * <code>&quot;SunClip&quot;</code> in the list of all mixers
 * (as returned by <code>getMixerInfo</code>) will be returned,
 * or, if not found, the first <code>Clip</code> of the first
 * <code>Mixer</code>that can be found in the list of all
 * mixers is returned.
 * If that fails, too, an <code>IllegalArgumentException</code>
 * is thrown.
 *
 * <p>
 *  <code> AudioSystem </code>类充当采样音频系统资源的入口点。此类允许您查询和访问系统上安装的混音器。
 *  <code> AudioSystem </code>包括用于在不同格式之间转换音频数据以及用于在音频文件和流之间进行转换的多种方法。
 * 它还提供了一种直接从<code> AudioSystem </code>获取<code> {@ link Line} </code>的方法,无需明确处理混音器。
 * 
 *  <p>属性可用于指定特定线类型的默认混音器。考虑系统属性和属性文件。
 *  <code> sound.properties </code>属性文件从实现特定的位置读取(通常是Java安装目录中的<code> lib </code>目录)。
 * 如果属性作为系统属性和属性文件存在,则系统属性优先。如果未指定,则在可用设备中选择适当的默认值。
 * 属性文件的语法在{@link java.util.Properties#load(InputStream)Properties.load}中指定。下表列出了可用的属性键以及所考虑的方法：。
 * 
 * <table border=0>
 *  <caption>音频系统属性键</caption>
 * <tr>
 * <th>属性键</th> <th>接口</th> <th>受影响的方法</th>
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.sampled.Clip </code> </td> <td> {@ link Clip} </td> <td> {@ link #getLine},{@link #getClip}
 *  / td>。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.sampled.Port </code> </td> <td> {@ link Port} </td> <td> {@ link #getLine} </td>
 * 。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.sampled.SourceDataLine </code> </td> <td> {@ link SourceDataLine} </td> <td>
 *  {@ link #getLine},{@link #getSourceDataLine} / td>。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.sampled.TargetDataLine </code> </td> <td> {@ link TargetDataLine} </td> <td>
 *  {@ link #getLine},{@link #getTargetDataLine} / td>。
 * </tr>
 * </table>
 * 
 *  属性值由提供程序类名称和混合器名称组成,由哈希标记("#")分隔。
 * 提供程序类名称是具体的{@link javax.sound.sampled.spi.MixerProvider混合器提供程序}类的完全限定名称。
 * 混合器名称与<code> Mixer.Info </code>的<code> getName </code>方法返回的<code> String </code>进行匹配。可以省略类名称或混合器名称。
 * 如果仅指定类名称,则尾随散列标记是可选的。
 * 
 *  <p>如果指定了提供程序类,并且可以从已安装的提供程序成功检索该提供程序类,则会从提供程序检索<code> Mixer.Info </code>对象的列表。
 * 否则,或者当这些混合器不提供后续匹配时,从{@link #getMixerInfo}检索该列表以包含所有可用的<code> Mixer.Info </code>对象。
 * 
 * <p>如果指定了混合器名称,将搜索结果的<code> Mixer.Info </code>对象列表：第一个具有匹配名称的对象,其<code> Mixer </code>提供相应的行接口,将返回。
 * 如果没有找到匹配的<code> Mixer.Info </code>对象,或者未指定混音器名称,则将返回提供相应线路接口的结果列表中的第一个混音器。
 * 
 *  例如,具有值<code>"com.sun.media.sound.MixerProvider#SunClip"</code>的属性<code> javax.sound.sampled.Clip </code>
 * 代码> getLine </code>调用请求<code> Clip </code>实例：如果安装的混音器提供程序列表中存在<code> com.sun.media.sound.MixerProvide
 * r </code>将返回来自具有名称<code>"SunClip"</code>的第一混合器的第一<code> Clip </code>。
 * 如果找不到,则将返回指定提供程序的第一个混合器的第一个<code> Clip </code>,而不管名称。
 * 如果没有,则从所有混合器的列表中的具有名称<code>"SunClip"</code>的第一<code> Mixer </code>中的第一<code> Clip </code>代码> getMixer
 * Info </code>),或者如果没有找到,则可以在所有混合器的列表中找到的第一<code> Mixer </code>的第一<code> Clip </code>回。
 * 如果找不到,则将返回指定提供程序的第一个混合器的第一个<code> Clip </code>,而不管名称。
 * 如果失败了,也会抛出一个<code> IllegalArgumentException </code>。
 * 
 * 
 * @author Kara Kytle
 * @author Florian Bomers
 * @author Matthias Pfisterer
 * @author Kevin P. Smith
 *
 * @see AudioFormat
 * @see AudioInputStream
 * @see Mixer
 * @see Line
 * @see Line.Info
 * @since 1.3
 */
public class AudioSystem {

    /**
     * An integer that stands for an unknown numeric value.
     * This value is appropriate only for signed quantities that do not
     * normally take negative values.  Examples include file sizes, frame
     * sizes, buffer sizes, and sample rates.
     * A number of Java Sound constructors accept
     * a value of <code>NOT_SPECIFIED</code> for such parameters.  Other
     * methods may also accept or return this value, as documented.
     * <p>
     * 表示未知数值的整数。此值仅适用于通常不带负值的有符号数量。示例包括文件大小,帧大小,缓冲区大小和采样率。
     * 许多Java Sound构造函数接受这些参数的值<code> NOT_SPECIFIED </code>。其他方法也可以接受或返回这个值,如记录。
     * 
     */
    public static final int NOT_SPECIFIED = -1;

    /**
     * Private no-args constructor for ensuring against instantiation.
     * <p>
     *  专用无参数构造函数,用于确保防止实例化。
     * 
     */
    private AudioSystem() {
    }


    /**
     * Obtains an array of mixer info objects that represents
     * the set of audio mixers that are currently installed on the system.
     * <p>
     *  获取表示当前安装在系统上的一组音频混合器的混合器信息对象数组。
     * 
     * 
     * @return an array of info objects for the currently installed mixers.  If no mixers
     * are available on the system, an array of length 0 is returned.
     * @see #getMixer
     */
    public static Mixer.Info[] getMixerInfo() {

        List infos = getMixerInfoList();
        Mixer.Info[] allInfos = (Mixer.Info[]) infos.toArray(new Mixer.Info[infos.size()]);
        return allInfos;
    }


    /**
     * Obtains the requested audio mixer.
     * <p>
     *  获取请求的音频混合器。
     * 
     * 
     * @param info a <code>Mixer.Info</code> object representing the desired
     * mixer, or <code>null</code> for the system default mixer
     * @return the requested mixer
     * @throws SecurityException if the requested mixer
     * is unavailable because of security restrictions
     * @throws IllegalArgumentException if the info object does not represent
     * a mixer installed on the system
     * @see #getMixerInfo
     */
    public static Mixer getMixer(Mixer.Info info) {

        Mixer mixer = null;
        List providers = getMixerProviders();

        for(int i = 0; i < providers.size(); i++ ) {

            try {
                return ((MixerProvider)providers.get(i)).getMixer(info);

            } catch (IllegalArgumentException e) {
            } catch (NullPointerException e) {
                // $$jb 08.20.99:  If the strings in the info object aren't
                // set, then Netscape (using jdk1.1.5) tends to throw
                // NPE's when doing some string manipulation.  This is
                // probably not the best fix, but is solves the problem
                // of the NPE in Netscape using local classes
                // $$jb 11.01.99: Replacing this patch.
            }
        }

        //$$fb if looking for default mixer, and not found yet, add a round of looking
        if (info == null) {
            for(int i = 0; i < providers.size(); i++ ) {
                try {
                    MixerProvider provider = (MixerProvider) providers.get(i);
                    Mixer.Info[] infos = provider.getMixerInfo();
                    // start from 0 to last device (do not reverse this order)
                    for (int ii = 0; ii < infos.length; ii++) {
                        try {
                            return provider.getMixer(infos[ii]);
                        } catch (IllegalArgumentException e) {
                            // this is not a good default device :)
                        }
                    }
                } catch (IllegalArgumentException e) {
                } catch (NullPointerException e) {
                }
            }
        }


        throw new IllegalArgumentException("Mixer not supported: "
                                           + (info!=null?info.toString():"null"));
    }


    //$$fb 2002-11-26: fix for 4757930: DOC: AudioSystem.getTarget/SourceLineInfo() is ambiguous
    /**
     * Obtains information about all source lines of a particular type that are supported
     * by the installed mixers.
     * <p>
     *  获取有关安装的混音器支持的特定类型的所有源线的信息。
     * 
     * 
     * @param info a <code>Line.Info</code> object that specifies the kind of
     * lines about which information is requested
     * @return an array of <code>Line.Info</code> objects describing source lines matching
     * the type requested.  If no matching source lines are supported, an array of length 0
     * is returned.
     *
     * @see Mixer#getSourceLineInfo(Line.Info)
     */
    public static Line.Info[] getSourceLineInfo(Line.Info info) {

        Vector vector = new Vector();
        Line.Info[] currentInfoArray;

        Mixer mixer;
        Line.Info fullInfo = null;
        Mixer.Info[] infoArray = getMixerInfo();

        for (int i = 0; i < infoArray.length; i++) {

            mixer = getMixer(infoArray[i]);

            currentInfoArray = mixer.getSourceLineInfo(info);
            for (int j = 0; j < currentInfoArray.length; j++) {
                vector.addElement(currentInfoArray[j]);
            }
        }

        Line.Info[] returnedArray = new Line.Info[vector.size()];

        for (int i = 0; i < returnedArray.length; i++) {
            returnedArray[i] = (Line.Info)vector.get(i);
        }

        return returnedArray;
    }


    /**
     * Obtains information about all target lines of a particular type that are supported
     * by the installed mixers.
     * <p>
     *  获取安装的混音器支持的特定类型的所有目标线的信息。
     * 
     * 
     * @param info a <code>Line.Info</code> object that specifies the kind of
     * lines about which information is requested
     * @return an array of <code>Line.Info</code> objects describing target lines matching
     * the type requested.  If no matching target lines are supported, an array of length 0
     * is returned.
     *
     * @see Mixer#getTargetLineInfo(Line.Info)
     */
    public static Line.Info[] getTargetLineInfo(Line.Info info) {

        Vector vector = new Vector();
        Line.Info[] currentInfoArray;

        Mixer mixer;
        Line.Info fullInfo = null;
        Mixer.Info[] infoArray = getMixerInfo();

        for (int i = 0; i < infoArray.length; i++) {

            mixer = getMixer(infoArray[i]);

            currentInfoArray = mixer.getTargetLineInfo(info);
            for (int j = 0; j < currentInfoArray.length; j++) {
                vector.addElement(currentInfoArray[j]);
            }
        }

        Line.Info[] returnedArray = new Line.Info[vector.size()];

        for (int i = 0; i < returnedArray.length; i++) {
            returnedArray[i] = (Line.Info)vector.get(i);
        }

        return returnedArray;
    }


    /**
     * Indicates whether the system supports any lines that match
     * the specified <code>Line.Info</code> object.  A line is supported if
     * any installed mixer supports it.
     * <p>
     *  指示系统是否支持与指定的<code> Line.Info </code>对象匹配的任何行。如果安装的混音器支持线路,则支持线路。
     * 
     * 
     * @param info a <code>Line.Info</code> object describing the line for which support is queried
     * @return <code>true</code> if at least one matching line is
     * supported, otherwise <code>false</code>
     *
     * @see Mixer#isLineSupported(Line.Info)
     */
    public static boolean isLineSupported(Line.Info info) {

        Mixer mixer;
        Mixer.Info[] infoArray = getMixerInfo();

        for (int i = 0; i < infoArray.length; i++) {

            if( infoArray[i] != null ) {
                mixer = getMixer(infoArray[i]);
                if (mixer.isLineSupported(info)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Obtains a line that matches the description in the specified
     * <code>Line.Info</code> object.
     *
     * <p>If a <code>DataLine</code> is requested, and <code>info</code>
     * is an instance of <code>DataLine.Info</code> specifying at least
     * one fully qualified audio format, the last one
     * will be used as the default format of the returned
     * <code>DataLine</code>.
     *
     * <p>If system properties
     * <code>javax.sound.sampled.Clip</code>,
     * <code>javax.sound.sampled.Port</code>,
     * <code>javax.sound.sampled.SourceDataLine</code> and
     * <code>javax.sound.sampled.TargetDataLine</code> are defined
     * or they are defined in the file &quot;sound.properties&quot;,
     * they are used to retrieve default lines.
     * For details, refer to the {@link AudioSystem class description}.
     *
     * If the respective property is not set, or the mixer
     * requested in the property is not installed or does not provide the
     * requested line, all installed mixers are queried for the
     * requested line type. A Line will be returned from the first mixer
     * providing the requested line type.
     *
     * <p>
     *  获取与指定的<code> Line.Info </code>对象中的描述匹配的行。
     * 
     *  <p>如果请求了<code> DataLine </code>,并且<code> info </code>是指定至少一个完全限定音频格式的<code> DataLine.Info </code>一个将
     * 被用作返回的<code> DataLine </code>的默认格式。
     * 
     * <p>如果系统属性<code> javax.sound.sampled.Clip </code>,<code> javax.sound.sampled.Port </code>,<code> javax
     * .sound.sampled.SourceDataLine </code>和<code> javax.sound.sampled.TargetDataLine </code>被定义或者它们被定义在文件"
     * sound.properties"中,它们被用来检索默认行。
     * 有关详细信息,请参阅{@link AudioSystem类说明}。
     * 
     *  如果未设置相应属性,或未在属性中请求的混音器未安装或未提供请求的线路,则会查询所有已安装的混音器的请求的线路类型。将从提供所请求的线类型的第一混合器返回线。
     * 
     * 
     * @param info a <code>Line.Info</code> object describing the desired kind of line
     * @return a line of the requested kind
     *
     * @throws LineUnavailableException if a matching line
     * is not available due to resource restrictions
     * @throws SecurityException if a matching line
     * is not available due to security restrictions
     * @throws IllegalArgumentException if the system does not
     * support at least one line matching the specified
     * <code>Line.Info</code> object
     * through any installed mixer
     */
    public static Line getLine(Line.Info info) throws LineUnavailableException {
        LineUnavailableException lue = null;
        List providers = getMixerProviders();


        // 1: try from default mixer for this line class
        try {
            Mixer mixer = getDefaultMixer(providers, info);
            if (mixer != null && mixer.isLineSupported(info)) {
                return mixer.getLine(info);
            }
        } catch (LineUnavailableException e) {
            lue = e;
        } catch (IllegalArgumentException iae) {
            // must not happen... but better to catch it here,
            // if plug-ins are badly written
        }


        // 2: if that doesn't work, try to find any mixing mixer
        for(int i = 0; i < providers.size(); i++) {
            MixerProvider provider = (MixerProvider) providers.get(i);
            Mixer.Info[] infos = provider.getMixerInfo();

            for (int j = 0; j < infos.length; j++) {
                try {
                    Mixer mixer = provider.getMixer(infos[j]);
                    // see if this is an appropriate mixer which can mix
                    if (isAppropriateMixer(mixer, info, true)) {
                        return mixer.getLine(info);
                    }
                } catch (LineUnavailableException e) {
                    lue = e;
                } catch (IllegalArgumentException iae) {
                    // must not happen... but better to catch it here,
                    // if plug-ins are badly written
                }
            }
        }


        // 3: if that didn't work, try to find any non-mixing mixer
        for(int i = 0; i < providers.size(); i++) {
            MixerProvider provider = (MixerProvider) providers.get(i);
            Mixer.Info[] infos = provider.getMixerInfo();
            for (int j = 0; j < infos.length; j++) {
                try {
                    Mixer mixer = provider.getMixer(infos[j]);
                    // see if this is an appropriate mixer which can mix
                    if (isAppropriateMixer(mixer, info, false)) {
                        return mixer.getLine(info);
                    }
                } catch (LineUnavailableException e) {
                    lue = e;
                } catch (IllegalArgumentException iae) {
                    // must not happen... but better to catch it here,
                    // if plug-ins are badly written
                }
            }
        }

        // if this line was supported but was not available, throw the last
        // LineUnavailableException we got (??).
        if (lue != null) {
            throw lue;
        }

        // otherwise, the requested line was not supported, so throw
        // an Illegal argument exception
        throw new IllegalArgumentException("No line matching " +
                                           info.toString() + " is supported.");
    }


    /**
     * Obtains a clip that can be used for playing back
     * an audio file or an audio stream. The returned clip
     * will be provided by the default system mixer, or,
     * if not possible, by any other mixer installed in the
     * system that supports a <code>Clip</code>
     * object.
     *
     * <p>The returned clip must be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioInputStream)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>If the system property
     * <code>javax.sound.sampled.Clip</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to retrieve the default clip.
     * For details, refer to the {@link AudioSystem class description}.
     *
     * <p>
     *  获取可用于播放音频文件或音频流的剪辑。返回的剪辑将由默认系统混合器提供,或者如果不可能,由安装在系统中支持<code> Clip </code>对象的任何其他混合器提供。
     * 
     *  <p>返回的剪辑必须使用<code> open(AudioFormat)</code>或<code> open(AudioInputStream)</code>方法打开。
     * 
     *  <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     *  <p>如果系统属性<code> javax.sound.sampled.Clip </code>已定义或已在文件"sound.properties"中定义,则它将用于检索默认剪辑。
     * 有关详细信息,请参阅{@link AudioSystem类说明}。
     * 
     * 
     * @return the desired clip object
     *
     * @throws LineUnavailableException if a clip object
     * is not available due to resource restrictions
     * @throws SecurityException if a clip object
     * is not available due to security restrictions
     * @throws IllegalArgumentException if the system does not
     * support at least one clip instance through any installed mixer
     *
     * @see #getClip(Mixer.Info)
     * @since 1.5
     */
    public static Clip getClip() throws LineUnavailableException{
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                             AudioSystem.NOT_SPECIFIED,
                                             16, 2, 4,
                                             AudioSystem.NOT_SPECIFIED, true);
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        return (Clip) AudioSystem.getLine(info);
    }


    /**
     * Obtains a clip from the specified mixer that can be
     * used for playing back an audio file or an audio stream.
     *
     * <p>The returned clip must be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioInputStream)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>
     *  从指定的混音器获取可用于播放音频文件或音频流的剪辑。
     * 
     * <p>返回的剪辑必须使用<code> open(AudioFormat)</code>或<code> open(AudioInputStream)</code>方法打开。
     * 
     *  <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     * 
     * @param mixerInfo a <code>Mixer.Info</code> object representing the
     * desired mixer, or <code>null</code> for the system default mixer
     * @return a clip object from the specified mixer
     *
     * @throws LineUnavailableException if a clip
     * is not available from this mixer due to resource restrictions
     * @throws SecurityException if a clip
     * is not available from this mixer due to security restrictions
     * @throws IllegalArgumentException if the system does not
     * support at least one clip through the specified mixer
     *
     * @see #getClip()
     * @since 1.5
     */
    public static Clip getClip(Mixer.Info mixerInfo) throws LineUnavailableException{
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                             AudioSystem.NOT_SPECIFIED,
                                             16, 2, 4,
                                             AudioSystem.NOT_SPECIFIED, true);
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Mixer mixer = AudioSystem.getMixer(mixerInfo);
        return (Clip) mixer.getLine(info);
    }


    /**
     * Obtains a source data line that can be used for playing back
     * audio data in the format specified by the
     * <code>AudioFormat</code> object. The returned line
     * will be provided by the default system mixer, or,
     * if not possible, by any other mixer installed in the
     * system that supports a matching
     * <code>SourceDataLine</code> object.
     *
     * <p>The returned line should be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioFormat, int)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>The returned <code>SourceDataLine</code>'s default
     * audio format will be initialized with <code>format</code>.
     *
     * <p>If the system property
     * <code>javax.sound.sampled.SourceDataLine</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to retrieve the default source data line.
     * For details, refer to the {@link AudioSystem class description}.
     *
     * <p>
     *  获取可用于以由<code> AudioFormat </code>对象指定的格式播放音频数据的源数据线。
     * 返回的行将由默认系统混合器提供,或者如果不可能,由系统中安装的支持匹配的<code> SourceDataLine </code>对象的任何其他混合器提供。
     * 
     *  <p>返回的行应该用<code> open(AudioFormat)</code>或<code> open(AudioFormat,int)</code>方法打开。
     * 
     *  <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     *  <p>返回的<code> SourceDataLine </code>的默认音频格式将使用<code> format </code>进行初始化。
     * 
     *  <p>如果定义了系统属性<code> javax.sound.sampled.SourceDataLine </code>或者它在文件"sound.properties"中定义,则它用于检索默认源数据
     * 行。
     * 有关详细信息,请参阅{@link AudioSystem类说明}。
     * 
     * 
     * @param format an <code>AudioFormat</code> object specifying
     *        the supported audio format of the returned line,
     *        or <code>null</code> for any audio format
     * @return the desired <code>SourceDataLine</code> object
     *
     * @throws LineUnavailableException if a matching source data line
     *         is not available due to resource restrictions
     * @throws SecurityException if a matching source data line
     *         is not available due to security restrictions
     * @throws IllegalArgumentException if the system does not
     *         support at least one source data line supporting the
     *         specified audio format through any installed mixer
     *
     * @see #getSourceDataLine(AudioFormat, Mixer.Info)
     * @since 1.5
     */
    public static SourceDataLine getSourceDataLine(AudioFormat format)
        throws LineUnavailableException{
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        return (SourceDataLine) AudioSystem.getLine(info);
    }


    /**
     * Obtains a source data line that can be used for playing back
     * audio data in the format specified by the
     * <code>AudioFormat</code> object, provided by the mixer
     * specified by the <code>Mixer.Info</code> object.
     *
     * <p>The returned line should be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioFormat, int)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>The returned <code>SourceDataLine</code>'s default
     * audio format will be initialized with <code>format</code>.
     *
     * <p>
     *  获取源数据行,可用于以由<code> Mixer.Info </code>对象指定的混合器提供的<code> AudioFormat </code>对象指定的格式重放音频数据。
     * 
     *  <p>返回的行应该用<code> open(AudioFormat)</code>或<code> open(AudioFormat,int)</code>方法打开。
     * 
     * <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     *  <p>返回的<code> SourceDataLine </code>的默认音频格式将使用<code> format </code>进行初始化。
     * 
     * 
     * @param format an <code>AudioFormat</code> object specifying
     *        the supported audio format of the returned line,
     *        or <code>null</code> for any audio format
     * @param mixerinfo a <code>Mixer.Info</code> object representing
     *        the desired mixer, or <code>null</code> for the system
     *        default mixer
     * @return the desired <code>SourceDataLine</code> object
     *
     * @throws LineUnavailableException if a matching source data
     *         line is not available from the specified mixer due
     *         to resource restrictions
     * @throws SecurityException if a matching source data line
     *         is not available from the specified mixer due to
     *         security restrictions
     * @throws IllegalArgumentException if the specified mixer does
     *         not support at least one source data line supporting
     *         the specified audio format
     *
     * @see #getSourceDataLine(AudioFormat)
     * @since 1.5
     */
    public static SourceDataLine getSourceDataLine(AudioFormat format,
                                                   Mixer.Info mixerinfo)
        throws LineUnavailableException{
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        Mixer mixer = AudioSystem.getMixer(mixerinfo);
        return (SourceDataLine) mixer.getLine(info);
  }


    /**
     * Obtains a target data line that can be used for recording
     * audio data in the format specified by the
     * <code>AudioFormat</code> object. The returned line
     * will be provided by the default system mixer, or,
     * if not possible, by any other mixer installed in the
     * system that supports a matching
     * <code>TargetDataLine</code> object.
     *
     * <p>The returned line should be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioFormat, int)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>The returned <code>TargetDataLine</code>'s default
     * audio format will be initialized with <code>format</code>.
     *
     * <p>If the system property
     * {@code javax.sound.sampled.TargetDataLine}
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to retrieve the default target data line.
     * For details, refer to the {@link AudioSystem class description}.
     *
     * <p>
     *  获取可用于以由<code> AudioFormat </code>对象指定的格式记录音频数据的目标数据行。
     * 返回的行将由默认系统混合器提供,或者如果不可能,由安装在系统中支持匹配的<code> TargetDataLine </code>对象的任何其他混合器提供。
     * 
     *  <p>返回的行应该用<code> open(AudioFormat)</code>或<code> open(AudioFormat,int)</code>方法打开。
     * 
     *  <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     *  <p>返回的<code> TargetDataLine </code>的默认音频格式将使用<code> format </code>进行初始化。
     * 
     *  <p>如果定义系统属性{@code javax.sound.sampled.TargetDataLine}或在文件"sound.properties"中定义系统属性,则它将用于检索默认目标数据行。
     * 有关详细信息,请参阅{@link AudioSystem类说明}。
     * 
     * 
     * @param format an <code>AudioFormat</code> object specifying
     *        the supported audio format of the returned line,
     *        or <code>null</code> for any audio format
     * @return the desired <code>TargetDataLine</code> object
     *
     * @throws LineUnavailableException if a matching target data line
     *         is not available due to resource restrictions
     * @throws SecurityException if a matching target data line
     *         is not available due to security restrictions
     * @throws IllegalArgumentException if the system does not
     *         support at least one target data line supporting the
     *         specified audio format through any installed mixer
     *
     * @see #getTargetDataLine(AudioFormat, Mixer.Info)
     * @see AudioPermission
     * @since 1.5
     */
    public static TargetDataLine getTargetDataLine(AudioFormat format)
        throws LineUnavailableException{

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        return (TargetDataLine) AudioSystem.getLine(info);
    }



    /**
     * Obtains a target data line that can be used for recording
     * audio data in the format specified by the
     * <code>AudioFormat</code> object, provided by the mixer
     * specified by the <code>Mixer.Info</code> object.
     *
     * <p>The returned line should be opened with the
     * <code>open(AudioFormat)</code> or
     * <code>open(AudioFormat, int)</code> method.
     *
     * <p>This is a high-level method that uses <code>getMixer</code>
     * and <code>getLine</code> internally.
     *
     * <p>The returned <code>TargetDataLine</code>'s default
     * audio format will be initialized with <code>format</code>.
     *
     * <p>
     *  获取可用于以由<code> Mixer.Info </code>对象指定的混合器提供的<code> AudioFormat </code>对象指定的格式记录音频数据的目标数据行。
     * 
     *  <p>返回的行应该用<code> open(AudioFormat)</code>或<code> open(AudioFormat,int)</code>方法打开。
     * 
     * <p>这是一种在内部使用<code> getMixer </code>和<code> getLine </code>的高级方法。
     * 
     *  <p>返回的<code> TargetDataLine </code>的默认音频格式将使用<code> format </code>进行初始化。
     * 
     * 
     * @param format an <code>AudioFormat</code> object specifying
     *        the supported audio format of the returned line,
     *        or <code>null</code> for any audio format
     * @param mixerinfo a <code>Mixer.Info</code> object representing the
     *        desired mixer, or <code>null</code> for the system default mixer
     * @return the desired <code>TargetDataLine</code> object
     *
     * @throws LineUnavailableException if a matching target data
     *         line is not available from the specified mixer due
     *         to resource restrictions
     * @throws SecurityException if a matching target data line
     *         is not available from the specified mixer due to
     *         security restrictions
     * @throws IllegalArgumentException if the specified mixer does
     *         not support at least one target data line supporting
     *         the specified audio format
     *
     * @see #getTargetDataLine(AudioFormat)
     * @see AudioPermission
     * @since 1.5
     */
    public static TargetDataLine getTargetDataLine(AudioFormat format,
                                                   Mixer.Info mixerinfo)
        throws LineUnavailableException {

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        Mixer mixer = AudioSystem.getMixer(mixerinfo);
        return (TargetDataLine) mixer.getLine(info);
    }


    // $$fb 2002-04-12: fix for 4662082: behavior of AudioSystem.getTargetEncodings() methods doesn't match the spec
    /**
     * Obtains the encodings that the system can obtain from an
     * audio input stream with the specified encoding using the set
     * of installed format converters.
     * <p>
     *  使用安装的格式转换器集合,从具有指定编码的音频输入流获得系统可以获得的编码。
     * 
     * 
     * @param sourceEncoding the encoding for which conversion support
     * is queried
     * @return array of encodings.  If <code>sourceEncoding</code>is not supported,
     * an array of length 0 is returned. Otherwise, the array will have a length
     * of at least 1, representing <code>sourceEncoding</code> (no conversion).
     */
    public static AudioFormat.Encoding[] getTargetEncodings(AudioFormat.Encoding sourceEncoding) {

        List codecs = getFormatConversionProviders();
        Vector encodings = new Vector();

        AudioFormat.Encoding encs[] = null;

        // gather from all the codecs
        for(int i=0; i<codecs.size(); i++ ) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            if( codec.isSourceEncodingSupported( sourceEncoding ) ) {
                encs = codec.getTargetEncodings();
                for (int j = 0; j < encs.length; j++) {
                    encodings.addElement( encs[j] );
                }
            }
        }
        AudioFormat.Encoding encs2[] = (AudioFormat.Encoding[]) encodings.toArray(new AudioFormat.Encoding[0]);
        return encs2;
    }



    // $$fb 2002-04-12: fix for 4662082: behavior of AudioSystem.getTargetEncodings() methods doesn't match the spec
    /**
     * Obtains the encodings that the system can obtain from an
     * audio input stream with the specified format using the set
     * of installed format converters.
     * <p>
     *  使用安装的格式转换器集合,从具有指定格式的音频输入流获取系统可以获得的编码。
     * 
     * 
     * @param sourceFormat the audio format for which conversion
     * is queried
     * @return array of encodings. If <code>sourceFormat</code>is not supported,
     * an array of length 0 is returned. Otherwise, the array will have a length
     * of at least 1, representing the encoding of <code>sourceFormat</code> (no conversion).
     */
    public static AudioFormat.Encoding[] getTargetEncodings(AudioFormat sourceFormat) {


        List codecs = getFormatConversionProviders();
        Vector encodings = new Vector();

        int size = 0;
        int index = 0;
        AudioFormat.Encoding encs[] = null;

        // gather from all the codecs

        for(int i=0; i<codecs.size(); i++ ) {
            encs = ((FormatConversionProvider) codecs.get(i)).getTargetEncodings(sourceFormat);
            size += encs.length;
            encodings.addElement( encs );
        }

        // now build a new array

        AudioFormat.Encoding encs2[] = new AudioFormat.Encoding[size];
        for(int i=0; i<encodings.size(); i++ ) {
            encs = (AudioFormat.Encoding [])(encodings.get(i));
            for(int j=0; j<encs.length; j++ ) {
                encs2[index++] = encs[j];
            }
        }
        return encs2;
    }


    /**
     * Indicates whether an audio input stream of the specified encoding
     * can be obtained from an audio input stream that has the specified
     * format.
     * <p>
     *  指示是否可从具有指定格式的音频输入流获取指定编码的音频输入流。
     * 
     * 
     * @param targetEncoding the desired encoding after conversion
     * @param sourceFormat the audio format before conversion
     * @return <code>true</code> if the conversion is supported,
     * otherwise <code>false</code>
     */
    public static boolean isConversionSupported(AudioFormat.Encoding targetEncoding, AudioFormat sourceFormat) {


        List codecs = getFormatConversionProviders();

        for(int i=0; i<codecs.size(); i++ ) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            if(codec.isConversionSupported(targetEncoding,sourceFormat) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains an audio input stream of the indicated encoding, by converting the
     * provided audio input stream.
     * <p>
     *  通过转换所提供的音频输入流来获得所指示的编码的音频输入流。
     * 
     * 
     * @param targetEncoding the desired encoding after conversion
     * @param sourceStream the stream to be converted
     * @return an audio input stream of the indicated encoding
     * @throws IllegalArgumentException if the conversion is not supported
     * @see #getTargetEncodings(AudioFormat.Encoding)
     * @see #getTargetEncodings(AudioFormat)
     * @see #isConversionSupported(AudioFormat.Encoding, AudioFormat)
     * @see #getAudioInputStream(AudioFormat, AudioInputStream)
     */
    public static AudioInputStream getAudioInputStream(AudioFormat.Encoding targetEncoding,
                                                       AudioInputStream sourceStream) {

        List codecs = getFormatConversionProviders();

        for(int i = 0; i < codecs.size(); i++) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            if( codec.isConversionSupported( targetEncoding, sourceStream.getFormat() ) ) {
                return codec.getAudioInputStream( targetEncoding, sourceStream );
            }
        }
        // we ran out of options, throw an exception
        throw new IllegalArgumentException("Unsupported conversion: " + targetEncoding + " from " + sourceStream.getFormat());
    }


    /**
     * Obtains the formats that have a particular encoding and that the system can
     * obtain from a stream of the specified format using the set of
     * installed format converters.
     * <p>
     *  获取具有特定编码的格式,并且系统可以使用一组安装的格式转换器从指定格式的流中获取。
     * 
     * 
     * @param targetEncoding the desired encoding after conversion
     * @param sourceFormat the audio format before conversion
     * @return array of formats.  If no formats of the specified
     * encoding are supported, an array of length 0 is returned.
     */
    public static AudioFormat[] getTargetFormats(AudioFormat.Encoding targetEncoding, AudioFormat sourceFormat) {

        List codecs = getFormatConversionProviders();
        Vector formats = new Vector();

        int size = 0;
        int index = 0;
        AudioFormat fmts[] = null;

        // gather from all the codecs

        for(int i=0; i<codecs.size(); i++ ) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            fmts = codec.getTargetFormats(targetEncoding, sourceFormat);
            size += fmts.length;
            formats.addElement( fmts );
        }

        // now build a new array

        AudioFormat fmts2[] = new AudioFormat[size];
        for(int i=0; i<formats.size(); i++ ) {
            fmts = (AudioFormat [])(formats.get(i));
            for(int j=0; j<fmts.length; j++ ) {
                fmts2[index++] = fmts[j];
            }
        }
        return fmts2;
    }


    /**
     * Indicates whether an audio input stream of a specified format
     * can be obtained from an audio input stream of another specified format.
     * <p>
     *  指示是否可以从另一指定格式的音频输入流获得指定格式的音频输入流。
     * 
     * 
     * @param targetFormat the desired audio format after conversion
     * @param sourceFormat the audio format before conversion
     * @return <code>true</code> if the conversion is supported,
     * otherwise <code>false</code>
     */

    public static boolean isConversionSupported(AudioFormat targetFormat, AudioFormat sourceFormat) {

        List codecs = getFormatConversionProviders();

        for(int i=0; i<codecs.size(); i++ ) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            if(codec.isConversionSupported(targetFormat, sourceFormat) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains an audio input stream of the indicated format, by converting the
     * provided audio input stream.
     * <p>
     *  通过转换所提供的音频输入流来获得所指示格式的音频输入流。
     * 
     * 
     * @param targetFormat the desired audio format after conversion
     * @param sourceStream the stream to be converted
     * @return an audio input stream of the indicated format
     * @throws IllegalArgumentException if the conversion is not supported
     * #see #getTargetEncodings(AudioFormat)
     * @see #getTargetFormats(AudioFormat.Encoding, AudioFormat)
     * @see #isConversionSupported(AudioFormat, AudioFormat)
     * @see #getAudioInputStream(AudioFormat.Encoding, AudioInputStream)
     */
    public static AudioInputStream getAudioInputStream(AudioFormat targetFormat,
                                                       AudioInputStream sourceStream) {

        if (sourceStream.getFormat().matches(targetFormat)) {
            return sourceStream;
        }

        List codecs = getFormatConversionProviders();

        for(int i = 0; i < codecs.size(); i++) {
            FormatConversionProvider codec = (FormatConversionProvider) codecs.get(i);
            if(codec.isConversionSupported(targetFormat,sourceStream.getFormat()) ) {
                return codec.getAudioInputStream(targetFormat,sourceStream);
            }
        }

        // we ran out of options...
        throw new IllegalArgumentException("Unsupported conversion: " + targetFormat + " from " + sourceStream.getFormat());
    }


    /**
     * Obtains the audio file format of the provided input stream.  The stream must
     * point to valid audio file data.  The implementation of this method may require
     * multiple parsers to examine the stream to determine whether they support it.
     * These parsers must be able to mark the stream, read enough data to determine whether they
     * support the stream, and, if not, reset the stream's read pointer to its original
     * position.  If the input stream does not support these operations, this method may fail
     * with an <code>IOException</code>.
     * <p>
     * 获取提供的输入流的音频文件格式。流必须指向有效的音频文件数据。该方法的实现可能需要多个解析器来检查流以确定它们是否支持它。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。
     * 如果输入流不支持这些操作,则此方法可能会失败,并显示<code> IOException </code>。
     * 
     * 
     * @param stream the input stream from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the stream's audio file format
     * @throws UnsupportedAudioFileException if the stream does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an input/output exception occurs
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public static AudioFileFormat getAudioFileFormat(InputStream stream)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioFileFormat format = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                format = reader.getAudioFileFormat( stream ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new UnsupportedAudioFileException("file is not a supported file type");
        } else {
            return format;
        }
    }

    /**
     * Obtains the audio file format of the specified URL.  The URL must
     * point to valid audio file data.
     * <p>
     *  获取指定URL的音频文件格式。网址必须指向有效的音频文件数据。
     * 
     * 
     * @param url the URL from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the audio file format
     * @throws UnsupportedAudioFileException if the URL does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an input/output exception occurs
     */
    public static AudioFileFormat getAudioFileFormat(URL url)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioFileFormat format = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                format = reader.getAudioFileFormat( url ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new UnsupportedAudioFileException("file is not a supported file type");
        } else {
            return format;
        }
    }

    /**
     * Obtains the audio file format of the specified <code>File</code>.  The <code>File</code> must
     * point to valid audio file data.
     * <p>
     *  获取指定的<code> File </code>的音频文件格式。 <code> File </code>必须指向有效的音频文件数据。
     * 
     * 
     * @param file the <code>File</code> from which file format information should be
     * extracted
     * @return an <code>AudioFileFormat</code> object describing the audio file format
     * @throws UnsupportedAudioFileException if the <code>File</code> does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public static AudioFileFormat getAudioFileFormat(File file)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioFileFormat format = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                format = reader.getAudioFileFormat( file ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new UnsupportedAudioFileException("file is not a supported file type");
        } else {
            return format;
        }
    }


    /**
     * Obtains an audio input stream from the provided input stream.  The stream must
     * point to valid audio file data.  The implementation of this method may
     * require multiple parsers to
     * examine the stream to determine whether they support it.  These parsers must
     * be able to mark the stream, read enough data to determine whether they
     * support the stream, and, if not, reset the stream's read pointer to its original
     * position.  If the input stream does not support these operation, this method may fail
     * with an <code>IOException</code>.
     * <p>
     *  从提供的输入流获取音频输入流。流必须指向有效的音频文件数据。该方法的实现可能需要多个解析器来检查流以确定它们是否支持它。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。
     * 如果输入流不支持这些操作,则此方法可能会失败,并显示<code> IOException </code>。
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
    public static AudioInputStream getAudioInputStream(InputStream stream)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioInputStream audioStream = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                audioStream = reader.getAudioInputStream( stream ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( audioStream==null ) {
            throw new UnsupportedAudioFileException("could not get audio input stream from input stream");
        } else {
            return audioStream;
        }
    }

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
    public static AudioInputStream getAudioInputStream(URL url)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioInputStream audioStream = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                audioStream = reader.getAudioInputStream( url ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( audioStream==null ) {
            throw new UnsupportedAudioFileException("could not get audio input stream from input URL");
        } else {
            return audioStream;
        }
    }

    /**
     * Obtains an audio input stream from the provided <code>File</code>.  The <code>File</code> must
     * point to valid audio file data.
     * <p>
     * 从提供的<code> File </code>获取音频输入流。 <code> File </code>必须指向有效的音频文件数据。
     * 
     * 
     * @param file the <code>File</code> for which the <code>AudioInputStream</code> should be
     * constructed
     * @return an <code>AudioInputStream</code> object based on the audio file data pointed
     * to by the <code>File</code>
     * @throws UnsupportedAudioFileException if the <code>File</code> does not point to valid audio
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public static AudioInputStream getAudioInputStream(File file)
        throws UnsupportedAudioFileException, IOException {

        List providers = getAudioFileReaders();
        AudioInputStream audioStream = null;

        for(int i = 0; i < providers.size(); i++ ) {
            AudioFileReader reader = (AudioFileReader) providers.get(i);
            try {
                audioStream = reader.getAudioInputStream( file ); // throws IOException
                break;
            } catch (UnsupportedAudioFileException e) {
                continue;
            }
        }

        if( audioStream==null ) {
            throw new UnsupportedAudioFileException("could not get audio input stream from input file");
        } else {
            return audioStream;
        }
    }


    /**
     * Obtains the file types for which file writing support is provided by the system.
     * <p>
     *  获取系统提供文件写入支持的文件类型。
     * 
     * 
     * @return array of unique file types.  If no file types are supported,
     * an array of length 0 is returned.
     */
    public static AudioFileFormat.Type[] getAudioFileTypes() {
        List providers = getAudioFileWriters();
        Set returnTypesSet = new HashSet();

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            AudioFileFormat.Type[] fileTypes = writer.getAudioFileTypes();
            for(int j=0; j < fileTypes.length; j++) {
                returnTypesSet.add(fileTypes[j]);
            }
        }
        AudioFileFormat.Type returnTypes[] = (AudioFileFormat.Type[])
            returnTypesSet.toArray(new AudioFileFormat.Type[0]);
        return returnTypes;
    }


    /**
     * Indicates whether file writing support for the specified file type is provided
     * by the system.
     * <p>
     *  指示系统是否提供对指定文件类型的文件写入支持。
     * 
     * 
     * @param fileType the file type for which write capabilities are queried
     * @return <code>true</code> if the file type is supported,
     * otherwise <code>false</code>
     */
    public static boolean isFileTypeSupported(AudioFileFormat.Type fileType) {

        List providers = getAudioFileWriters();

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            if (writer.isFileTypeSupported(fileType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains the file types that the system can write from the
     * audio input stream specified.
     * <p>
     *  从指定的音频输入流中获取系统可以写入的文件类型。
     * 
     * 
     * @param stream the audio input stream for which audio file type support
     * is queried
     * @return array of file types.  If no file types are supported,
     * an array of length 0 is returned.
     */
    public static AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream stream) {
        List providers = getAudioFileWriters();
        Set returnTypesSet = new HashSet();

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            AudioFileFormat.Type[] fileTypes = writer.getAudioFileTypes(stream);
            for(int j=0; j < fileTypes.length; j++) {
                returnTypesSet.add(fileTypes[j]);
            }
        }
        AudioFileFormat.Type returnTypes[] = (AudioFileFormat.Type[])
            returnTypesSet.toArray(new AudioFileFormat.Type[0]);
        return returnTypes;
    }


    /**
     * Indicates whether an audio file of the specified file type can be written
     * from the indicated audio input stream.
     * <p>
     *  指示是否可以从指示的音频输入流中写入指定文件类型的音频文件。
     * 
     * 
     * @param fileType the file type for which write capabilities are queried
     * @param stream the stream for which file-writing support is queried
     * @return <code>true</code> if the file type is supported for this audio input stream,
     * otherwise <code>false</code>
     */
    public static boolean isFileTypeSupported(AudioFileFormat.Type fileType,
                                              AudioInputStream stream) {

        List providers = getAudioFileWriters();

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            if(writer.isFileTypeSupported(fileType, stream)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Writes a stream of bytes representing an audio file of the specified file type
     * to the output stream provided.  Some file types require that
     * the length be written into the file header; such files cannot be written from
     * start to finish unless the length is known in advance.  An attempt
     * to write a file of such a type will fail with an IOException if the length in
     * the audio file type is <code>AudioSystem.NOT_SPECIFIED</code>.
     *
     * <p>
     *  将表示指定文件类型的音频文件的字节流写入提供的输出流。一些文件类型要求将长度写入文件头;这样的文件不能从头到尾写入,除非预先知道长度。
     * 如果音频文件类型中的长度为<code> AudioSystem.NOT_SPECIFIED </code>,则尝试写入此类型的文件将失败并显示IOException。
     * 
     * 
     * @param stream the audio input stream containing audio data to be
     * written to the file
     * @param fileType the kind of audio file to write
     * @param out the stream to which the file data should be written
     * @return the number of bytes written to the output stream
     * @throws IOException if an input/output exception occurs
     * @throws IllegalArgumentException if the file type is not supported by
     * the system
     * @see #isFileTypeSupported
     * @see     #getAudioFileTypes
     */
    public static int write(AudioInputStream stream, AudioFileFormat.Type fileType,
                            OutputStream out) throws IOException {

        List providers = getAudioFileWriters();
        int bytesWritten = 0;
        boolean flag = false;

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            try {
                bytesWritten = writer.write( stream, fileType, out ); // throws IOException
                flag = true;
                break;
            } catch (IllegalArgumentException e) {
                // thrown if this provider cannot write the sequence, try the next
                continue;
            }
        }
        if(!flag) {
            throw new IllegalArgumentException("could not write audio file: file type not supported: " + fileType);
        } else {
            return bytesWritten;
        }
    }


    /**
     * Writes a stream of bytes representing an audio file of the specified file type
     * to the external file provided.
     * <p>
     *  将表示指定文件类型的音频文件的字节流写入提供的外部文件。
     * 
     * 
     * @param stream the audio input stream containing audio data to be
     * written to the file
     * @param fileType the kind of audio file to write
     * @param out the external file to which the file data should be written
     * @return the number of bytes written to the file
     * @throws IOException if an I/O exception occurs
     * @throws IllegalArgumentException if the file type is not supported by
     * the system
     * @see #isFileTypeSupported
     * @see     #getAudioFileTypes
     */
    public static int write(AudioInputStream stream, AudioFileFormat.Type fileType,
                            File out) throws IOException {

        List providers = getAudioFileWriters();
        int bytesWritten = 0;
        boolean flag = false;

        for(int i=0; i < providers.size(); i++) {
            AudioFileWriter writer = (AudioFileWriter) providers.get(i);
            try {
                bytesWritten = writer.write( stream, fileType, out ); // throws IOException
                flag = true;
                break;
            } catch (IllegalArgumentException e) {
                // thrown if this provider cannot write the sequence, try the next
                continue;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException("could not write audio file: file type not supported: " + fileType);
        } else {
            return bytesWritten;
        }
    }


    // METHODS FOR INTERNAL IMPLEMENTATION USE

    /**
     * Obtains the set of MixerProviders on the system.
     * <p>
     *  在系统上获取MixerProvider集合。
     * 
     */
    private static List getMixerProviders() {
        return getProviders(MixerProvider.class);
    }


    /**
     * Obtains the set of format converters (codecs, transcoders, etc.)
     * that are currently installed on the system.
     * <p>
     *  获取当前安装在系统上的一组格式转换器(编解码器,代码转换器等)。
     * 
     * 
     * @return an array of
     * {@link javax.sound.sampled.spi.FormatConversionProvider
     * FormatConversionProvider}
     * objects representing the available format converters.  If no format
     * converters readers are available on the system, an array of length 0 is
     * returned.
     */
    private static List getFormatConversionProviders() {
        return getProviders(FormatConversionProvider.class);
    }


    /**
     * Obtains the set of audio file readers that are currently installed on the system.
     * <p>
     *  获取系统上当前安装的一组音频文件读取器。
     * 
     * 
     * @return a List of
     * {@link javax.sound.sampled.spi.AudioFileReader
     * AudioFileReader}
     * objects representing the installed audio file readers.  If no audio file
     * readers are available on the system, an empty List is returned.
     */
    private static List getAudioFileReaders() {
        return getProviders(AudioFileReader.class);
    }


    /**
     * Obtains the set of audio file writers that are currently installed on the system.
     * <p>
     *  获取系统上当前安装的一组音频文件写入程序。
     * 
     * 
     * @return a List of
     * {@link javax.sound.samples.spi.AudioFileWriter AudioFileWriter}
     * objects representing the available audio file writers.  If no audio file
     * writers are available on the system, an empty List is returned.
     */
    private static List getAudioFileWriters() {
        return getProviders(AudioFileWriter.class);
    }



    /** Attempts to locate and return a default Mixer that provides lines
     * of the specified type.
     *
     * <p>
     *  的指定类型。
     * 
     * 
     * @param providers the installed mixer providers
     * @param info The requested line type
     * TargetDataLine.class, Clip.class or Port.class.
     * @return a Mixer that matches the requirements, or null if no default mixer found
     */
    private static Mixer getDefaultMixer(List providers, Line.Info info) {
        Class lineClass = info.getLineClass();
        String providerClassName = JDK13Services.getDefaultProviderClassName(lineClass);
        String instanceName = JDK13Services.getDefaultInstanceName(lineClass);
        Mixer mixer;

        if (providerClassName != null) {
            MixerProvider defaultProvider = getNamedProvider(providerClassName, providers);
            if (defaultProvider != null) {
                if (instanceName != null) {
                    mixer = getNamedMixer(instanceName, defaultProvider, info);
                    if (mixer != null) {
                        return mixer;
                    }
                } else {
                    mixer = getFirstMixer(defaultProvider, info,
                                          false /* mixing not required*/);
                    if (mixer != null) {
                        return mixer;
                    }
                }

            }
        }

        /* Provider class not specified or
           provider class cannot be found, or
        /* <p>
        /*  if(mixer！= null){return mixer; }}
        /* 
        /*  }}
        /* 
        /* / *未指定Provider类或无法找到提供程序类
        /* 
        /* 
           provider class and instance specified and instance cannot be found or is not appropriate */
        if (instanceName != null) {
            mixer = getNamedMixer(instanceName, providers, info);
            if (mixer != null) {
                return mixer;
            }
        }


        /* No default are specified, or if something is specified, everything
        /* <p>
        /* 
           failed. */
        return null;
    }



    /** Return a MixerProvider of a given class from the list of
        MixerProviders.

        This method never requires the returned Mixer to do mixing.
    /* <p>
    /*  混合器提供商。
    /* 
    /*  这种方法从来不需要返回的混合器做混合。
    /* 
    /* 
        @param providerClassName The class name of the provider to be returned.
        @param providers The list of MixerProviders that is searched.
        @return A MixerProvider of the requested class, or null if none is
        found.
     */
    private static MixerProvider getNamedProvider(String providerClassName,
                                                  List providers) {
        for(int i = 0; i < providers.size(); i++) {
            MixerProvider provider = (MixerProvider) providers.get(i);
            if (provider.getClass().getName().equals(providerClassName)) {
                return provider;
            }
        }
        return null;
    }


    /** Return a Mixer with a given name from a given MixerProvider.
      This method never requires the returned Mixer to do mixing.
    /* <p>
    /*  这种方法从来不需要返回的混合器做混合。
    /* 
    /* 
      @param mixerName The name of the Mixer to be returned.
      @param provider The MixerProvider to check for Mixers.
      @param info The type of line the returned Mixer is required to
      support.

      @return A Mixer matching the requirements, or null if none is found.
     */
    private static Mixer getNamedMixer(String mixerName,
                                       MixerProvider provider,
                                       Line.Info info) {
        Mixer.Info[] infos = provider.getMixerInfo();
        for (int i = 0; i < infos.length; i++) {
            if (infos[i].getName().equals(mixerName)) {
                Mixer mixer = provider.getMixer(infos[i]);
                if (isAppropriateMixer(mixer, info, false)) {
                    return mixer;
                }
            }
        }
        return null;
    }


    /** From a List of MixerProviders, return a Mixer with a given name.
        This method never requires the returned Mixer to do mixing.
    /* <p>
    /*  这种方法从来不需要返回的混合器做混合。
    /* 
    /* 
        @param mixerName The name of the Mixer to be returned.
        @param providers The List of MixerProviders to check for Mixers.
        @param info The type of line the returned Mixer is required to
        support.
        @return A Mixer matching the requirements, or null if none is found.
     */
    private static Mixer getNamedMixer(String mixerName,
                                       List providers,
                                       Line.Info info) {
        for(int i = 0; i < providers.size(); i++) {
            MixerProvider provider = (MixerProvider) providers.get(i);
            Mixer mixer = getNamedMixer(mixerName, provider, info);
            if (mixer != null) {
                return mixer;
            }
        }
        return null;
    }


    /** From a given MixerProvider, return the first appropriate Mixer.
    /* <p>
    /* 
        @param provider The MixerProvider to check for Mixers.
        @param info The type of line the returned Mixer is required to
        support.
        @param isMixingRequired If true, only Mixers that support mixing are
        returned for line types of SourceDataLine and Clip.

        @return A Mixer that is considered appropriate, or null
        if none is found.
     */
    private static Mixer getFirstMixer(MixerProvider provider,
                                       Line.Info info,
                                       boolean isMixingRequired) {
        Mixer.Info[] infos = provider.getMixerInfo();
        for (int j = 0; j < infos.length; j++) {
            Mixer mixer = provider.getMixer(infos[j]);
            if (isAppropriateMixer(mixer, info, isMixingRequired)) {
                return mixer;
            }
        }
        return null;
    }


    /** Checks if a Mixer is appropriate.
        A Mixer is considered appropriate if it support the given line type.
        If isMixingRequired is true and the line type is an output one
        (SourceDataLine, Clip), the mixer is appropriate if it supports
        at least 2 (concurrent) lines of the given type.

    /* <p>
    /*  如果混合器支持给定的线类型,则认为它是适当的。
    /* 如果isMixingRequired为true,并且行类型是输出类型(SourceDataLine,Clip),则混合器是适当的,如果它支持给定类型的至少2(并发)行。
    /* 
    /* 
        @return true if the mixer is considered appropriate according to the
        rules given above, false otherwise.
     */
    private static boolean isAppropriateMixer(Mixer mixer,
                                              Line.Info lineInfo,
                                              boolean isMixingRequired) {
        if (! mixer.isLineSupported(lineInfo)) {
            return false;
        }
        Class lineClass = lineInfo.getLineClass();
        if (isMixingRequired
            && (SourceDataLine.class.isAssignableFrom(lineClass) ||
                Clip.class.isAssignableFrom(lineClass))) {
            int maxLines = mixer.getMaxLines(lineInfo);
            return ((maxLines == NOT_SPECIFIED) || (maxLines > 1));
        }
        return true;
    }



    /**
     * Like getMixerInfo, but return List
     * <p>
     *  就像getMixerInfo,但是返回List
     * 
     */
    private static List getMixerInfoList() {
        List providers = getMixerProviders();
        return getMixerInfoList(providers);
    }


    /**
     * Like getMixerInfo, but return List
     * <p>
     *  就像getMixerInfo,但是返回List
     * 
     */
    private static List getMixerInfoList(List providers) {
        List infos = new ArrayList();

        Mixer.Info[] someInfos; // per-mixer
        Mixer.Info[] allInfos;  // for all mixers

        for(int i = 0; i < providers.size(); i++ ) {
            someInfos = (Mixer.Info[])
                ((MixerProvider)providers.get(i)).getMixerInfo();

            for (int j = 0; j < someInfos.length; j++) {
                infos.add(someInfos[j]);
            }
        }

        return infos;
    }


    /**
     * Obtains the set of services currently installed on the system
     * using sun.misc.Service, the SPI mechanism in 1.3.
     * <p>
     *  使用sun.misc.Service获取当前安装在系统上的服务集,SPI机制在1.3。
     * 
     * @return a List of instances of providers for the requested service.
     * If no providers are available, a vector of length 0 will be returned.
     */
    private static List getProviders(Class providerClass) {
        return JDK13Services.getProviders(providerClass);
    }
}
