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

package javax.sound.midi;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.net.URL;

import javax.sound.midi.spi.MidiFileWriter;
import javax.sound.midi.spi.MidiFileReader;
import javax.sound.midi.spi.SoundbankReader;
import javax.sound.midi.spi.MidiDeviceProvider;

import com.sun.media.sound.JDK13Services;
import com.sun.media.sound.ReferenceCountingDevice;
import com.sun.media.sound.AutoConnectSequencer;
import com.sun.media.sound.MidiDeviceReceiverEnvelope;
import com.sun.media.sound.MidiDeviceTransmitterEnvelope;


/**
 * The <code>MidiSystem</code> class provides access to the installed MIDI
 * system resources, including devices such as synthesizers, sequencers, and
 * MIDI input and output ports.  A typical simple MIDI application might
 * begin by invoking one or more <code>MidiSystem</code> methods to learn
 * what devices are installed and to obtain the ones needed in that
 * application.
 * <p>
 * The class also has methods for reading files, streams, and  URLs that
 * contain standard MIDI file data or soundbanks.  You can query the
 * <code>MidiSystem</code> for the format of a specified MIDI file.
 * <p>
 * You cannot instantiate a <code>MidiSystem</code>; all the methods are
 * static.
 *
 * <p>Properties can be used to specify default MIDI devices.
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
 *  <caption>MIDI System Property Keys</caption>
 *  <tr>
 *   <th>Property Key</th>
 *   <th>Interface</th>
 *   <th>Affected Method</th>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.midi.Receiver</code></td>
 *   <td>{@link Receiver}</td>
 *   <td>{@link #getReceiver}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.midi.Sequencer</code></td>
 *   <td>{@link Sequencer}</td>
 *   <td>{@link #getSequencer}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.midi.Synthesizer</code></td>
 *   <td>{@link Synthesizer}</td>
 *   <td>{@link #getSynthesizer}</td>
 *  </tr>
 *  <tr>
 *   <td><code>javax.sound.midi.Transmitter</code></td>
 *   <td>{@link Transmitter}</td>
 *   <td>{@link #getTransmitter}</td>
 *  </tr>
 * </table>
 *
 * The property value consists of the provider class name
 * and the device name, separated by the hash mark (&quot;#&quot;).
 * The provider class name is the fully-qualified
 * name of a concrete {@link javax.sound.midi.spi.MidiDeviceProvider
 * MIDI device provider} class. The device name is matched against
 * the <code>String</code> returned by the <code>getName</code>
 * method of <code>MidiDevice.Info</code>.
 * Either the class name, or the device name may be omitted.
 * If only the class name is specified, the trailing hash mark
 * is optional.
 *
 * <p>If the provider class is specified, and it can be
 * successfully retrieved from the installed providers,
 * the list of
 * <code>MidiDevice.Info</code> objects is retrieved
 * from the provider. Otherwise, or when these devices
 * do not provide a subsequent match, the list is retrieved
 * from {@link #getMidiDeviceInfo} to contain
 * all available <code>MidiDevice.Info</code> objects.
 *
 * <p>If a device name is specified, the resulting list of
 * <code>MidiDevice.Info</code> objects is searched:
 * the first one with a matching name, and whose
 * <code>MidiDevice</code> implements the
 * respective interface, will be returned.
 * If no matching <code>MidiDevice.Info</code> object
 * is found, or the device name is not specified,
 * the first suitable device from the resulting
 * list will be returned. For Sequencer and Synthesizer,
 * a device is suitable if it implements the respective
 * interface; whereas for Receiver and Transmitter, a device is
 * suitable if it
 * implements neither Sequencer nor Synthesizer and provides
 * at least one Receiver or Transmitter, respectively.
 *
 * For example, the property <code>javax.sound.midi.Receiver</code>
 * with a value
 * <code>&quot;com.sun.media.sound.MidiProvider#SunMIDI1&quot;</code>
 * will have the following consequences when
 * <code>getReceiver</code> is called:
 * if the class <code>com.sun.media.sound.MidiProvider</code> exists
 * in the list of installed MIDI device providers,
 * the first <code>Receiver</code> device with name
 * <code>&quot;SunMIDI1&quot;</code> will be returned. If it cannot
 * be found, the first <code>Receiver</code> from that provider
 * will be returned, regardless of name.
 * If there is none, the first <code>Receiver</code> with name
 * <code>&quot;SunMIDI1&quot;</code> in the list of all devices
 * (as returned by <code>getMidiDeviceInfo</code>) will be returned,
 * or, if not found, the first <code>Receiver</code> that can
 * be found in the list of all devices is returned.
 * If that fails, too, a <code>MidiUnavailableException</code>
 * is thrown.
 *
 * <p>
 *  <code> MidiSystem </code>类提供对安装的MIDI系统资源的访问,包括合成器,顺控程序和MIDI输入和输出端口等设备。
 * 典型的简单MIDI应用程序可以通过调用一个或多个<code> MidiSystem </code>方法来开始,以了解已安装的设备以及获取该应用程序所需的设备。
 * <p>
 *  该类还具有用于读取包含标准MIDI文件数据或声音库的文件,流和URL的方法。您可以查询<code> MidiSystem </code>以获取指定MIDI文件的格式。
 * <p>
 *  您不能实例化<code> MidiSystem </code>;所有的方法都是静态的。
 * 
 *  <p>属性可用于指定默认MIDI设备。考虑系统属性和属性文件。
 *  <code> sound.properties </code>属性文件从实现特定的位置读取(通常是Java安装目录中的<code> lib </code>目录)。
 * 如果属性作为系统属性和属性文件存在,则系统属性优先。如果未指定,则在可用设备中选择适当的默认值。
 * 属性文件的语法在{@link java.util.Properties#load(InputStream)Properties.load}中指定。下表列出了可用的属性键以及所考虑的方法：。
 * 
 * <table border=0>
 * <caption> MIDI系统属性键</caption>
 * <tr>
 *  <th>属性键</th> <th>接口</th> <th>受影响的方法</th>
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.midi.Receiver </code> </td> <td> {@ link Receiver} </td> <td> {@ link #getReceiver}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.midi.Sequencer </code> </td> <td> {@ link Sequencer} </td> <td> {@ link #getSequencer}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.midi.Synthesizer </code> </td> <td> {@ link Synthesizer} </td> <td> {@ link #getSynthesizer}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> <code> javax.sound.midi.Transmitter </code> </td> <td> {@ link Transmitter} </td> <td> {@ link #getTransmitter}
 *  </td>。
 * </tr>
 * </table>
 * 
 *  属性值由提供程序类名称和设备名称组成,由哈希标记("#")分隔。
 * 提供程序类名称是具体的{@link javax.sound.midi.spi.MidiDeviceProvider MIDI设备提供程序}类的完全限定名称。
 * 设备名称与<code> MidiDevice.Info </code>的<code> getName </code>方法返回的<code> String </code>匹配。可以省略类名称或设备名称。
 * 如果仅指定类名称,则尾随散列标记是可选的。
 * 
 *  <p>如果指定了提供程序类,并且可以从已安装的提供程序成功检索该提供程序类,则会从提供程序检索<code> MidiDevice.Info </code>对象的列表。
 * 否则,或者当这些设备不提供后续匹配时,从{@link #getMidiDeviceInfo}检索列表以包含所有可用的<code> MidiDevice.Info </code>对象。
 * 
 * <p>如果指定了设备名称,则搜索<code> MidiDevice.Info </code>对象的结果列表：第一个具有匹配名称,并且其<code> MidiDevice </code> ,将被退回。
 * 如果没有找到匹配的<code> MidiDevice.Info </code>对象,或者未指定设备名称,则将返回结果列表中的第一个合适的设备。
 * 对于序列器和合成器,如果设备实现各自的接口,则该设备是合适的;而对于接收器和发射器,如果其既实现定序器又不实现合成器并且分别提供至少一个接收器或发射器,则该设备是合适的。
 * 
 * 例如,具有值<code>"com.sun.media.sound.MidiProvider#SunMIDI1"</code>的属性<code> javax.sound.midi.Receiver </code>
 * 将具有以下结果：代码> getReceiver </code>被调用：如果安装的MIDI设备提供者列表中存在<code> com.sun.media.sound.MidiProvider </code>
 * 类,则第一个<code> Receiver </code>将返回名称<code>"SunMIDI1"</code>。
 * 如果找不到,则将返回该提供程序的第一个<code> Receiver </code>,而不管其名称。
 * 如果没有,将返回所有设备列表(由<code> getMidiDeviceInfo </code>返回)中名为<code>"SunMIDI1"</code>的第一个<code> Receiver </code>
 *  ,或者,如果未找到,则返回在所有设备的列表中可以找到的第一<code> Receiver </code>。
 * 如果找不到,则将返回该提供程序的第一个<code> Receiver </code>,而不管其名称。
 * 如果失败了,也会抛出一个<code> MidiUnavailableException </code>。
 * 
 * 
 * @author Kara Kytle
 * @author Florian Bomers
 * @author Matthias Pfisterer
 */
public class MidiSystem {

    /**
     * Private no-args constructor for ensuring against instantiation.
     * <p>
     *  专用无参数构造函数,用于确保防止实例化。
     * 
     */
    private MidiSystem() {
    }


    /**
     * Obtains an array of information objects representing
     * the set of all MIDI devices available on the system.
     * A returned information object can then be used to obtain the
     * corresponding device object, by invoking
     * {@link #getMidiDevice(MidiDevice.Info) getMidiDevice}.
     *
     * <p>
     *  获取表示系统上可用的所有MIDI设备集合的信息对象数组。
     * 然后,通过调用{@link #getMidiDevice(MidiDevice.Info)getMidiDevice},可以使用返回的信息对象来获得相应的设备对象。
     * 
     * 
     * @return an array of <code>MidiDevice.Info</code> objects, one
     * for each installed MIDI device.  If no such devices are installed,
     * an array of length 0 is returned.
     */
    public static MidiDevice.Info[] getMidiDeviceInfo() {
        List allInfos = new ArrayList();
        List providers = getMidiDeviceProviders();

        for(int i = 0; i < providers.size(); i++) {
            MidiDeviceProvider provider = (MidiDeviceProvider) providers.get(i);
            MidiDevice.Info[] tmpinfo = provider.getDeviceInfo();
            for (int j = 0; j < tmpinfo.length; j++) {
                allInfos.add( tmpinfo[j] );
            }
        }
        MidiDevice.Info[] infosArray = (MidiDevice.Info[]) allInfos.toArray(new MidiDevice.Info[0]);
        return infosArray;
    }


    /**
     * Obtains the requested MIDI device.
     *
     * <p>
     *  获取所请求的MIDI设备。
     * 
     * 
     * @param info a device information object representing the desired device.
     * @return the requested device
     * @throws MidiUnavailableException if the requested device is not available
     * due to resource restrictions
     * @throws IllegalArgumentException if the info object does not represent
     * a MIDI device installed on the system
     * @see #getMidiDeviceInfo
     */
    public static MidiDevice getMidiDevice(MidiDevice.Info info) throws MidiUnavailableException {
        List providers = getMidiDeviceProviders();

        for(int i = 0; i < providers.size(); i++) {
            MidiDeviceProvider provider = (MidiDeviceProvider) providers.get(i);
            if (provider.isDeviceSupported(info)) {
                MidiDevice device = provider.getDevice(info);
                return device;
            }
        }
        throw new IllegalArgumentException("Requested device not installed: " + info);
    }


    /**
     * Obtains a MIDI receiver from an external MIDI port
     * or other default device.
     * The returned receiver always implements
     * the {@code MidiDeviceReceiver} interface.
     *
     * <p>If the system property
     * <code>javax.sound.midi.Receiver</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to identify the device that provides the default receiver.
     * For details, refer to the {@link MidiSystem class description}.
     *
     * If a suitable MIDI port is not available, the Receiver is
     * retrieved from an installed synthesizer.
     *
     * <p>If a native receiver provided by the default device does not implement
     * the {@code MidiDeviceReceiver} interface, it will be wrapped in a
     * wrapper class that implements the {@code MidiDeviceReceiver} interface.
     * The corresponding {@code Receiver} method calls will be forwarded
     * to the native receiver.
     *
     * <p>If this method returns successfully, the {@link
     * javax.sound.midi.MidiDevice MidiDevice} the
     * <code>Receiver</code> belongs to is opened implicitly, if it is
     * not already open. It is possible to close an implicitly opened
     * device by calling {@link javax.sound.midi.Receiver#close close}
     * on the returned <code>Receiver</code>. All open <code>Receiver</code>
     * instances have to be closed in order to release system resources
     * hold by the <code>MidiDevice</code>. For a
     * detailed description of open/close behaviour see the class
     * description of {@link javax.sound.midi.MidiDevice MidiDevice}.
     *
     *
     * <p>
     *  从外部MIDI端口或其他默认设备获取MIDI接收器。返回的接收器总是实现{@code MidiDeviceReceiver}接口。
     * 
     * <p>如果系统属性<code> javax.sound.midi.Receiver </code>被定义或者它被定义在文件"sound.properties"中,则它被用于标识提供默认接收者的设备。
     * 有关详细信息,请参阅{@link MidiSystem类描述}。
     * 
     *  如果没有合适的MIDI端口,则从安装的合成器检索接收器。
     * 
     *  <p>如果默认设备提供的本地接收器不实现{@code MidiDeviceReceiver}接口,它将被包装在实现{@code MidiDeviceReceiver}接口的包装类中。
     * 相应的{@code Receiver}方法调用将转发到本机接收器。
     * 
     *  <p>如果此方法成功返回,则<code> Receiver </code>所属的{@link javax.sound.midi.MidiDevice MidiDevice}将隐式打开,如果它尚未打开。
     * 可以通过在返回的<code> Receiver </code>上调用{@link javax.sound.midi.Receiver#close close}关闭隐式打开的设备。
     * 所有打开的<code> Receiver </code>实例必须关闭,以释放由<code> MidiDevice </code>保存的系统资源。
     * 有关打开/关闭行为的详细描述,请参阅{@link javax.sound.midi.MidiDevice MidiDevice}的类描述。
     * 
     * 
     * @return the default MIDI receiver
     * @throws MidiUnavailableException if the default receiver is not
     *         available due to resource restrictions,
     *         or no device providing receivers is installed in the system
     */
    public static Receiver getReceiver() throws MidiUnavailableException {
        // may throw MidiUnavailableException
        MidiDevice device = getDefaultDeviceWrapper(Receiver.class);
        Receiver receiver;
        if (device instanceof ReferenceCountingDevice) {
            receiver = ((ReferenceCountingDevice) device).getReceiverReferenceCounting();
        } else {
            receiver = device.getReceiver();
        }
        if (!(receiver instanceof MidiDeviceReceiver)) {
            receiver = new MidiDeviceReceiverEnvelope(device, receiver);
        }
        return receiver;
    }


    /**
     * Obtains a MIDI transmitter from an external MIDI port
     * or other default source.
     * The returned transmitter always implements
     * the {@code MidiDeviceTransmitter} interface.
     *
     * <p>If the system property
     * <code>javax.sound.midi.Transmitter</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to identify the device that provides the default transmitter.
     * For details, refer to the {@link MidiSystem class description}.
     *
     * <p>If a native transmitter provided by the default device does not implement
     * the {@code MidiDeviceTransmitter} interface, it will be wrapped in a
     * wrapper class that implements the {@code MidiDeviceTransmitter} interface.
     * The corresponding {@code Transmitter} method calls will be forwarded
     * to the native transmitter.
     *
     * <p>If this method returns successfully, the {@link
     * javax.sound.midi.MidiDevice MidiDevice} the
     * <code>Transmitter</code> belongs to is opened implicitly, if it
     * is not already open. It is possible to close an implicitly
     * opened device by calling {@link
     * javax.sound.midi.Transmitter#close close} on the returned
     * <code>Transmitter</code>. All open <code>Transmitter</code>
     * instances have to be closed in order to release system resources
     * hold by the <code>MidiDevice</code>. For a detailed description
     * of open/close behaviour see the class description of {@link
     * javax.sound.midi.MidiDevice MidiDevice}.
     *
     * <p>
     *  从外部MIDI端口或其他默认源获取MIDI发送器。返回的发射器总是实现{@code MidiDeviceTransmitter}接口。
     * 
     * <p>如果系统属性<code> javax.sound.midi.Transmitter </code>被定义或者它被定义在文件"sound.properties"中,则它被用于标识提供默认发送器的设备
     * 。
     * 有关详细信息,请参阅{@link MidiSystem类描述}。
     * 
     *  <p>如果默认设备提供的本地发送器不实现{@code MidiDeviceTransmitter}接口,它将被封装在实现{@code MidiDeviceTransmitter}接口的包装类中。
     * 相应的{@code Transmitter}方法调用将被转发到本地发送器。
     * 
     *  <p>如果此方法成功返回,则<code> Transmitter </code>属于的{@link javax.sound.midi.MidiDevice MidiDevice}将隐式打开,如果它尚未
     * 打开。
     * 可以通过在返回的<code> Transmitter </code>上调用{@link javax.sound.midi.Transmitter#close close}关闭隐式打开的设备。
     * 所有打开的<code> Transmitter </code>实例必须关闭,以释放由<code> MidiDevice </code>保存的系统资源。
     * 有关打开/关闭行为的详细描述,请参阅{@link javax.sound.midi.MidiDevice MidiDevice}的类描述。
     * 
     * 
     * @return the default MIDI transmitter
     * @throws MidiUnavailableException if the default transmitter is not
     *         available due to resource restrictions,
     *         or no device providing transmitters is installed in the system
     */
    public static Transmitter getTransmitter() throws MidiUnavailableException {
        // may throw MidiUnavailableException
        MidiDevice device = getDefaultDeviceWrapper(Transmitter.class);
        Transmitter transmitter;
        if (device instanceof ReferenceCountingDevice) {
            transmitter = ((ReferenceCountingDevice) device).getTransmitterReferenceCounting();
        } else {
            transmitter = device.getTransmitter();
        }
        if (!(transmitter instanceof MidiDeviceTransmitter)) {
            transmitter = new MidiDeviceTransmitterEnvelope(device, transmitter);
        }
        return transmitter;
    }


    /**
     * Obtains the default synthesizer.
     *
     * <p>If the system property
     * <code>javax.sound.midi.Synthesizer</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to identify the default synthesizer.
     * For details, refer to the {@link MidiSystem class description}.
     *
     * <p>
     *  获取默认合成器。
     * 
     *  <p>如果系统属性<code> javax.sound.midi.Synthesizer </code>被定义或者它被定义在文件"sound.properties"中,则它被用来标识默认合成器。
     * 有关详细信息,请参阅{@link MidiSystem类描述}。
     * 
     * 
     * @return the default synthesizer
     * @throws MidiUnavailableException if the synthesizer is not
     *         available due to resource restrictions,
     *         or no synthesizer is installed in the system
     */
    public static Synthesizer getSynthesizer() throws MidiUnavailableException {
        // may throw MidiUnavailableException
        return (Synthesizer) getDefaultDeviceWrapper(Synthesizer.class);
    }


    /**
     * Obtains the default <code>Sequencer</code>, connected to
     * a default device.
     * The returned <code>Sequencer</code> instance is
     * connected to the default <code>Synthesizer</code>,
     * as returned by {@link #getSynthesizer}.
     * If there is no <code>Synthesizer</code>
     * available, or the default <code>Synthesizer</code>
     * cannot be opened, the <code>sequencer</code> is connected
     * to the default <code>Receiver</code>, as returned
     * by {@link #getReceiver}.
     * The connection is made by retrieving a <code>Transmitter</code>
     * instance from the <code>Sequencer</code> and setting its
     * <code>Receiver</code>.
     * Closing and re-opening the sequencer will restore the
     * connection to the default device.
     *
     * <p>This method is equivalent to calling
     * <code>getSequencer(true)</code>.
     *
     * <p>If the system property
     * <code>javax.sound.midi.Sequencer</code>
     * is defined or it is defined in the file &quot;sound.properties&quot;,
     * it is used to identify the default sequencer.
     * For details, refer to the {@link MidiSystem class description}.
     *
     * <p>
     * 获取连接到默认设备的默认<code> Sequencer </code>。
     * 返回的<code> Sequencer </code>实例连接到{@link #getSynthesizer}返回的默认<code> Synthesizer </code>。
     * 如果没有<code> Synthesizer </code>可用,或者默认的<code> Synthesizer </code>无法打开,则<code> sequencer </code>会连接到默认的
     * <code> Receiver </code >,由{@link #getReceiver}返回。
     * 返回的<code> Sequencer </code>实例连接到{@link #getSynthesizer}返回的默认<code> Synthesizer </code>。
     * 通过从<code> Sequencer </code>中检索<code> Transmitter </code>实例并设置其<code> Receiver </code>来进行连接。
     * 关闭并重新打开序列器将会恢复与默认设备的连接。
     * 
     *  <p>此方法等效于调用<code> getSequencer(true)</code>。
     * 
     *  <p>如果系统属性<code> javax.sound.midi.Sequencer </code>被定义或者它被定义在文件"sound.properties"中,则它被用于标识默认定序器。
     * 有关详细信息,请参阅{@link MidiSystem类描述}。
     * 
     * 
     * @return the default sequencer, connected to a default Receiver
     * @throws MidiUnavailableException if the sequencer is not
     *         available due to resource restrictions,
     *         or there is no <code>Receiver</code> available by any
     *         installed <code>MidiDevice</code>,
     *         or no sequencer is installed in the system.
     * @see #getSequencer(boolean)
     * @see #getSynthesizer
     * @see #getReceiver
     */
    public static Sequencer getSequencer() throws MidiUnavailableException {
        return getSequencer(true);
    }



    /**
     * Obtains the default <code>Sequencer</code>, optionally
     * connected to a default device.
     *
     * <p>If <code>connected</code> is true, the returned
     * <code>Sequencer</code> instance is
     * connected to the default <code>Synthesizer</code>,
     * as returned by {@link #getSynthesizer}.
     * If there is no <code>Synthesizer</code>
     * available, or the default <code>Synthesizer</code>
     * cannot be opened, the <code>sequencer</code> is connected
     * to the default <code>Receiver</code>, as returned
     * by {@link #getReceiver}.
     * The connection is made by retrieving a <code>Transmitter</code>
     * instance from the <code>Sequencer</code> and setting its
     * <code>Receiver</code>.
     * Closing and re-opening the sequencer will restore the
     * connection to the default device.
     *
     * <p>If <code>connected</code> is false, the returned
     * <code>Sequencer</code> instance is not connected, it
     * has no open <code>Transmitters</code>. In order to
     * play the sequencer on a MIDI device, or a <code>Synthesizer</code>,
     * it is necessary to get a <code>Transmitter</code> and set its
     * <code>Receiver</code>.
     *
     * <p>If the system property
     * <code>javax.sound.midi.Sequencer</code>
     * is defined or it is defined in the file "sound.properties",
     * it is used to identify the default sequencer.
     * For details, refer to the {@link MidiSystem class description}.
     *
     * <p>
     *  获取默认的<code> Sequencer </code>,可选择连接到默认设备。
     * 
     * <p>如果<code> connected </code>为true,则返回的<code> Sequencer </code>实例将连接到{@link #getSynthesizer}返回的默认<code>
     *  Synthesizer </code>。
     * 如果没有<code> Synthesizer </code>可用,或者默认的<code> Synthesizer </code>无法打开,则<code> sequencer </code>会连接到默认的
     * <code> Receiver </code >,由{@link #getReceiver}返回。
     * 通过从<code> Sequencer </code>中检索<code> Transmitter </code>实例并设置其<code> Receiver </code>来进行连接。
     * 关闭并重新打开序列器将会恢复与默认设备的连接。
     * 
     *  <p>如果<code> connected </code>为false,则返回的<code> Sequencer </code>实例未连接,它没有打开<code> Transmitters </code>
     * 。
     * 为了在MIDI设备或<code> Synthesizer </code>上播放音序器,需要获取<code> Transmitter </code>并设置其<code> Receiver </code>。
     * 
     *  <p>如果定义了系统属性<code> javax.sound.midi.Sequencer </code>或者它在文件"sound.properties"中定义,则用于标识默认顺控程序。
     * 有关详细信息,请参阅{@link MidiSystem类描述}。
     * 
     * 
     * @param connected whether or not the returned {@code Sequencer}
     * is connected to the default {@code Synthesizer}
     * @return the default sequencer
     * @throws MidiUnavailableException if the sequencer is not
     *         available due to resource restrictions,
     *         or no sequencer is installed in the system,
     *         or if <code>connected</code> is true, and there is
     *         no <code>Receiver</code> available by any installed
     *         <code>MidiDevice</code>
     * @see #getSynthesizer
     * @see #getReceiver
     * @since 1.5
     */
    public static Sequencer getSequencer(boolean connected)
        throws MidiUnavailableException {
        Sequencer seq = (Sequencer) getDefaultDeviceWrapper(Sequencer.class);

        if (connected) {
            // IMPORTANT: this code needs to be synch'ed with
            //            all AutoConnectSequencer instances,
            //            (e.g. RealTimeSequencer) because the
            //            same algorithm for synth retrieval
            //            needs to be used!

            Receiver rec = null;
            MidiUnavailableException mue = null;

            // first try to connect to the default synthesizer
            try {
                Synthesizer synth = getSynthesizer();
                if (synth instanceof ReferenceCountingDevice) {
                    rec = ((ReferenceCountingDevice) synth).getReceiverReferenceCounting();
                } else {
                    synth.open();
                    try {
                        rec = synth.getReceiver();
                    } finally {
                        // make sure that the synth is properly closed
                        if (rec == null) {
                            synth.close();
                        }
                    }
                }
            } catch (MidiUnavailableException e) {
                // something went wrong with synth
                if (e instanceof MidiUnavailableException) {
                    mue = (MidiUnavailableException) e;
                }
            }
            if (rec == null) {
                // then try to connect to the default Receiver
                try {
                    rec = MidiSystem.getReceiver();
                } catch (Exception e) {
                    // something went wrong. Nothing to do then!
                    if (e instanceof MidiUnavailableException) {
                        mue = (MidiUnavailableException) e;
                    }
                }
            }
            if (rec != null) {
                seq.getTransmitter().setReceiver(rec);
                if (seq instanceof AutoConnectSequencer) {
                    ((AutoConnectSequencer) seq).setAutoConnect(rec);
                }
            } else {
                if (mue != null) {
                    throw mue;
                }
                throw new MidiUnavailableException("no receiver available");
            }
        }
        return seq;
    }




    /**
     * Constructs a MIDI sound bank by reading it from the specified stream.
     * The stream must point to
     * a valid MIDI soundbank file.  In general, MIDI soundbank providers may
     * need to read some data from the stream before determining whether they
     * support it.  These parsers must
     * be able to mark the stream, read enough data to determine whether they
     * support the stream, and, if not, reset the stream's read pointer to
     * its original position.  If the input stream does not support this,
     * this method may fail with an IOException.
     * <p>
     * 通过从指定的流读取它构造一个MIDI声音库。流必须指向有效的MIDI声音库文件。一般来说,MIDI音乐提供者可能需要在确定它们是否支持之前从流中读取一些数据。
     * 这些解析器必须能够标记流,读取足够的数据以确定它们是否支持流,如果不支持,则将流的读指针重置为其原始位置。如果输入流不支持此操作,则此方法可能会失败,并显示IOException。
     * 
     * 
     * @param stream the source of the sound bank data.
     * @return the sound bank
     * @throws InvalidMidiDataException if the stream does not point to
     * valid MIDI soundbank data recognized by the system
     * @throws IOException if an I/O error occurred when loading the soundbank
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public static Soundbank getSoundbank(InputStream stream)
        throws InvalidMidiDataException, IOException {

        SoundbankReader sp = null;
        Soundbank s = null;

        List providers = getSoundbankReaders();

        for(int i = 0; i < providers.size(); i++) {
            sp = (SoundbankReader)providers.get(i);
            s = sp.getSoundbank(stream);

            if( s!= null) {
                return s;
            }
        }
        throw new InvalidMidiDataException("cannot get soundbank from stream");

    }


    /**
     * Constructs a <code>Soundbank</code> by reading it from the specified URL.
     * The URL must point to a valid MIDI soundbank file.
     *
     * <p>
     *  通过从指定的URL读取来构造<code> Soundbank </code>。网址必须指向有效的MIDI声音库文件。
     * 
     * 
     * @param url the source of the sound bank data
     * @return the sound bank
     * @throws InvalidMidiDataException if the URL does not point to valid MIDI
     * soundbank data recognized by the system
     * @throws IOException if an I/O error occurred when loading the soundbank
     */
    public static Soundbank getSoundbank(URL url)
        throws InvalidMidiDataException, IOException {

        SoundbankReader sp = null;
        Soundbank s = null;

        List providers = getSoundbankReaders();

        for(int i = 0; i < providers.size(); i++) {
            sp = (SoundbankReader)providers.get(i);
            s = sp.getSoundbank(url);

            if( s!= null) {
                return s;
            }
        }
        throw new InvalidMidiDataException("cannot get soundbank from stream");

    }


    /**
     * Constructs a <code>Soundbank</code> by reading it from the specified
     * <code>File</code>.
     * The <code>File</code> must point to a valid MIDI soundbank file.
     *
     * <p>
     *  通过从指定的<code> File </code>中读取<code> Soundbank </code>来构造<code>。 <code> File </code>必须指向有效的MIDI声音库文件。
     * 
     * 
     * @param file the source of the sound bank data
     * @return the sound bank
     * @throws InvalidMidiDataException if the <code>File</code> does not
     * point to valid MIDI soundbank data recognized by the system
     * @throws IOException if an I/O error occurred when loading the soundbank
     */
    public static Soundbank getSoundbank(File file)
        throws InvalidMidiDataException, IOException {

        SoundbankReader sp = null;
        Soundbank s = null;

        List providers = getSoundbankReaders();

        for(int i = 0; i < providers.size(); i++) {
            sp = (SoundbankReader)providers.get(i);
            s = sp.getSoundbank(file);

            if( s!= null) {
                return s;
            }
        }
        throw new InvalidMidiDataException("cannot get soundbank from stream");
    }



    /**
     * Obtains the MIDI file format of the data in the specified input stream.
     * The stream must point to valid MIDI file data for a file type recognized
     * by the system.
     * <p>
     * This method and/or the code it invokes may need to read some data from
     * the stream to determine whether its data format is supported.  The
     * implementation may therefore
     * need to mark the stream, read enough data to determine whether it is in
     * a supported format, and reset the stream's read pointer to its original
     * position.  If the input stream does not permit this set of operations,
     * this method may fail with an <code>IOException</code>.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while determining the file format.
     *
     * <p>
     *  获取指定输入流中数据的MIDI文件格式。流必须指向由系统识别的文件类型的有效MIDI文件数据。
     * <p>
     *  该方法和/或其调用的代码可能需要从流中读取一些数据以确定其数据格式是否被支持。因此,实现可能需要标记流,读取足够的数据以确定其是否处于支持的格式,并且将流的读指针重置为其原始位置。
     * 如果输入流不允许此组操作,则此方法可能会失败,并显示<code> IOException </code>。
     * <p>
     * 此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但在确定文件格式时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param stream the input stream from which file format information
     * should be extracted
     * @return an <code>MidiFileFormat</code> object describing the MIDI file
     * format
     * @throws InvalidMidiDataException if the stream does not point to valid
     * MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs while accessing the
     * stream
     * @see #getMidiFileFormat(URL)
     * @see #getMidiFileFormat(File)
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public static MidiFileFormat getMidiFileFormat(InputStream stream)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        MidiFileFormat format = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                format = reader.getMidiFileFormat( stream ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new InvalidMidiDataException("input stream is not a supported file type");
        } else {
            return format;
        }
    }


    /**
     * Obtains the MIDI file format of the data in the specified URL.  The URL
     * must point to valid MIDI file data for a file type recognized
     * by the system.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while determining the file format.
     *
     * <p>
     *  获取指定URL中的数据的MIDI文件格式。 URL必须指向系统可识别的文件类型的有效MIDI文件数据。
     * <p>
     *  此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但在确定文件格式时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param url the URL from which file format information should be
     * extracted
     * @return a <code>MidiFileFormat</code> object describing the MIDI file
     * format
     * @throws InvalidMidiDataException if the URL does not point to valid MIDI
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs while accessing the URL
     *
     * @see #getMidiFileFormat(InputStream)
     * @see #getMidiFileFormat(File)
     */
    public static MidiFileFormat getMidiFileFormat(URL url)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        MidiFileFormat format = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                format = reader.getMidiFileFormat( url ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new InvalidMidiDataException("url is not a supported file type");
        } else {
            return format;
        }
    }


    /**
     * Obtains the MIDI file format of the specified <code>File</code>.  The
     * <code>File</code> must point to valid MIDI file data for a file type
     * recognized by the system.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while determining the file format.
     *
     * <p>
     *  获取指定的<code> File </code>的MIDI文件格式。 <code> File </code>必须指向由系统识别的文件类型的有效MIDI文件数据。
     * <p>
     *  此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但在确定文件格式时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param file the <code>File</code> from which file format information
     * should be extracted
     * @return a <code>MidiFileFormat</code> object describing the MIDI file
     * format
     * @throws InvalidMidiDataException if the <code>File</code> does not point
     *  to valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs while accessing the file
     *
     * @see #getMidiFileFormat(InputStream)
     * @see #getMidiFileFormat(URL)
     */
    public static MidiFileFormat getMidiFileFormat(File file)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        MidiFileFormat format = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                format = reader.getMidiFileFormat( file ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( format==null ) {
            throw new InvalidMidiDataException("file is not a supported file type");
        } else {
            return format;
        }
    }


    /**
     * Obtains a MIDI sequence from the specified input stream.  The stream must
     * point to valid MIDI file data for a file type recognized
     * by the system.
     * <p>
     * This method and/or the code it invokes may need to read some data
     * from the stream to determine whether
     * its data format is supported.  The implementation may therefore
     * need to mark the stream, read enough data to determine whether it is in
     * a supported format, and reset the stream's read pointer to its original
     * position.  If the input stream does not permit this set of operations,
     * this method may fail with an <code>IOException</code>.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while constructing the <code>Sequence</code>
     * object from the file data.
     *
     * <p>
     * 从指定的输入流获取MIDI序列。流必须指向由系统识别的文件类型的有效MIDI文件数据。
     * <p>
     *  该方法和/或其调用的代码可能需要从流中读取一些数据以确定其数据格式是否被支持。因此,实现可能需要标记流,读取足够的数据以确定其是否处于支持的格式,并且将流的读指针重置为其原始位置。
     * 如果输入流不允许此组操作,则此方法可能会失败,并显示<code> IOException </code>。
     * <p>
     *  此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但是在从文件数据构造<code> Sequence </code>对象时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param stream the input stream from which the <code>Sequence</code>
     * should be constructed
     * @return a <code>Sequence</code> object based on the MIDI file data
     * contained in the input stream
     * @throws InvalidMidiDataException if the stream does not point to
     * valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs while accessing the
     * stream
     * @see InputStream#markSupported
     * @see InputStream#mark
     */
    public static Sequence getSequence(InputStream stream)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        Sequence sequence = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                sequence = reader.getSequence( stream ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( sequence==null ) {
            throw new InvalidMidiDataException("could not get sequence from input stream");
        } else {
            return sequence;
        }
    }


    /**
     * Obtains a MIDI sequence from the specified URL.  The URL must
     * point to valid MIDI file data for a file type recognized
     * by the system.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while constructing the <code>Sequence</code>
     * object from the file data.
     *
     * <p>
     *  从指定的URL获取MIDI序列。 URL必须指向系统可识别的文件类型的有效MIDI文件数据。
     * <p>
     * 此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但是在从文件数据构造<code> Sequence </code>对象时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param url the URL from which the <code>Sequence</code> should be
     * constructed
     * @return a <code>Sequence</code> object based on the MIDI file data
     * pointed to by the URL
     * @throws InvalidMidiDataException if the URL does not point to valid MIDI
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs while accessing the URL
     */
    public static Sequence getSequence(URL url)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        Sequence sequence = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                sequence = reader.getSequence( url ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( sequence==null ) {
            throw new InvalidMidiDataException("could not get sequence from URL");
        } else {
            return sequence;
        }
    }


    /**
     * Obtains a MIDI sequence from the specified <code>File</code>.
     * The <code>File</code> must point to valid MIDI file data
     * for a file type recognized by the system.
     * <p>
     * This operation can only succeed for files of a type which can be parsed
     * by an installed file reader.  It may fail with an InvalidMidiDataException
     * even for valid files if no compatible file reader is installed.  It
     * will also fail with an InvalidMidiDataException if a compatible file reader
     * is installed, but encounters errors while constructing the <code>Sequence</code>
     * object from the file data.
     *
     * <p>
     *  从指定的<code> File </code>获取MIDI序列。 <code> File </code>必须指向由系统识别的文件类型的有效MIDI文件数据。
     * <p>
     *  此操作只能对可由已安装的文件读取器解析的类型的文件成功。如果没有安装兼容的文件读取器,即使对于有效的文件,它也可能会失败并返回InvalidMidiDataException。
     * 如果安装了兼容文件读取器,但是在从文件数据构造<code> Sequence </code>对象时遇到错误,它也将失败并返回InvalidMidiDataException。
     * 
     * 
     * @param file the <code>File</code> from which the <code>Sequence</code>
     * should be constructed
     * @return a <code>Sequence</code> object based on the MIDI file data
     * pointed to by the File
     * @throws InvalidMidiDataException if the File does not point to valid MIDI
     * file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public static Sequence getSequence(File file)
        throws InvalidMidiDataException, IOException {

        List providers = getMidiFileReaders();
        Sequence sequence = null;

        for(int i = 0; i < providers.size(); i++) {
            MidiFileReader reader = (MidiFileReader) providers.get(i);
            try {
                sequence = reader.getSequence( file ); // throws IOException
                break;
            } catch (InvalidMidiDataException e) {
                continue;
            }
        }

        if( sequence==null ) {
            throw new InvalidMidiDataException("could not get sequence from file");
        } else {
            return sequence;
        }
    }


    /**
     * Obtains the set of MIDI file types for which file writing support is
     * provided by the system.
     * <p>
     *  获取系统提供文件写入支持的一组MIDI文件类型。
     * 
     * 
     * @return array of unique file types.  If no file types are supported,
     * an array of length 0 is returned.
     */
    public static int[] getMidiFileTypes() {

        List providers = getMidiFileWriters();
        Set allTypes = new HashSet();

        // gather from all the providers

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            int[] types = writer.getMidiFileTypes();
            for (int j = 0; j < types.length; j++ ) {
                allTypes.add(new Integer(types[j]));
            }
        }
        int resultTypes[] = new int[allTypes.size()];
        int index = 0;
        Iterator iterator = allTypes.iterator();
        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            resultTypes[index++] = integer.intValue();
        }
        return resultTypes;
    }


    /**
     * Indicates whether file writing support for the specified MIDI file type
     * is provided by the system.
     * <p>
     *  指示系统是否提供对指定MIDI文件类型的文件写入支持。
     * 
     * 
     * @param fileType the file type for which write capabilities are queried
     * @return <code>true</code> if the file type is supported,
     * otherwise <code>false</code>
     */
    public static boolean isFileTypeSupported(int fileType) {

        List providers = getMidiFileWriters();

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            if( writer.isFileTypeSupported(fileType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains the set of MIDI file types that the system can write from the
     * sequence specified.
     * <p>
     *  从指定的序列获取系统可以写入的一组MIDI文件类型。
     * 
     * 
     * @param sequence the sequence for which MIDI file type support
     * is queried
     * @return the set of unique supported file types.  If no file types are supported,
     * returns an array of length 0.
     */
    public static int[] getMidiFileTypes(Sequence sequence) {

        List providers = getMidiFileWriters();
        Set allTypes = new HashSet();

        // gather from all the providers

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            int[] types = writer.getMidiFileTypes(sequence);
            for (int j = 0; j < types.length; j++ ) {
                allTypes.add(new Integer(types[j]));
            }
        }
        int resultTypes[] = new int[allTypes.size()];
        int index = 0;
        Iterator iterator = allTypes.iterator();
        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            resultTypes[index++] = integer.intValue();
        }
        return resultTypes;
    }


    /**
     * Indicates whether a MIDI file of the file type specified can be written
     * from the sequence indicated.
     * <p>
     *  指示是否可以从指定的序列写入指定文件类型的MIDI文件。
     * 
     * 
     * @param fileType the file type for which write capabilities
     * are queried
     * @param sequence the sequence for which file writing support is queried
     * @return <code>true</code> if the file type is supported for this
     * sequence, otherwise <code>false</code>
     */
    public static boolean isFileTypeSupported(int fileType, Sequence sequence) {

        List providers = getMidiFileWriters();

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            if( writer.isFileTypeSupported(fileType,sequence)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Writes a stream of bytes representing a file of the MIDI file type
     * indicated to the output stream provided.
     * <p>
     *  写入表示所提供的输出流所指示的MIDI文件类型的文件的字节流。
     * 
     * 
     * @param in sequence containing MIDI data to be written to the file
     * @param fileType the file type of the file to be written to the output stream
     * @param out stream to which the file data should be written
     * @return the number of bytes written to the output stream
     * @throws IOException if an I/O exception occurs
     * @throws IllegalArgumentException if the file format is not supported by
     * the system
     * @see #isFileTypeSupported(int, Sequence)
     * @see     #getMidiFileTypes(Sequence)
     */
    public static int write(Sequence in, int fileType, OutputStream out) throws IOException {

        List providers = getMidiFileWriters();
        //$$fb 2002-04-17: Fix for 4635287: Standard MidiFileWriter cannot write empty Sequences
        int bytesWritten = -2;

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            if( writer.isFileTypeSupported( fileType, in ) ) {

                bytesWritten = writer.write(in, fileType, out);
                break;
            }
        }
        if (bytesWritten == -2) {
            throw new IllegalArgumentException("MIDI file type is not supported");
        }
        return bytesWritten;
    }


    /**
     * Writes a stream of bytes representing a file of the MIDI file type
     * indicated to the external file provided.
     * <p>
     * 写入表示指定给所提供的外部文件的MIDI文件类型的文件的字节流。
     * 
     * 
     * @param in sequence containing MIDI data to be written to the file
     * @param type the file type of the file to be written to the output stream
     * @param out external file to which the file data should be written
     * @return the number of bytes written to the file
     * @throws IOException if an I/O exception occurs
     * @throws IllegalArgumentException if the file type is not supported by
     * the system
     * @see #isFileTypeSupported(int, Sequence)
     * @see     #getMidiFileTypes(Sequence)
     */
    public static int write(Sequence in, int type, File out) throws IOException {

        List providers = getMidiFileWriters();
        //$$fb 2002-04-17: Fix for 4635287: Standard MidiFileWriter cannot write empty Sequences
        int bytesWritten = -2;

        for (int i = 0; i < providers.size(); i++ ) {
            MidiFileWriter writer = (MidiFileWriter) providers.get(i);
            if( writer.isFileTypeSupported( type, in ) ) {

                bytesWritten = writer.write(in, type, out);
                break;
            }
        }
        if (bytesWritten == -2) {
            throw new IllegalArgumentException("MIDI file type is not supported");
        }
        return bytesWritten;
    }



    // HELPER METHODS

    private static List getMidiDeviceProviders() {
        return getProviders(MidiDeviceProvider.class);
    }


    private static List getSoundbankReaders() {
        return getProviders(SoundbankReader.class);
    }


    private static List getMidiFileWriters() {
        return getProviders(MidiFileWriter.class);
    }


    private static List getMidiFileReaders() {
        return getProviders(MidiFileReader.class);
    }


    /** Attempts to locate and return a default MidiDevice of the specified
     * type.
     *
     * This method wraps {@link #getDefaultDevice}. It catches the
     * <code>IllegalArgumentException</code> thrown by
     * <code>getDefaultDevice</code> and instead throws a
     * <code>MidiUnavailableException</code>, with the catched
     * exception chained.
     *
     * <p>
     *  类型。
     * 
     *  此方法封装{@link #getDefaultDevice}。
     * 它捕获<code> getDefaultDevice </code>抛出的<code> IllegalArgumentException </code>,而引发一个<code> MidiUnavaila
     * bleException </code>,并将捕获的异常链接起来。
     *  此方法封装{@link #getDefaultDevice}。
     * 
     * 
     * @param deviceClass The requested device type, one of Synthesizer.class,
     * Sequencer.class, Receiver.class or Transmitter.class.
     * @throws  MidiUnavalableException on failure.
     */
    private static MidiDevice getDefaultDeviceWrapper(Class deviceClass)
        throws MidiUnavailableException{
        try {
            return getDefaultDevice(deviceClass);
        } catch (IllegalArgumentException iae) {
            MidiUnavailableException mae = new MidiUnavailableException();
            mae.initCause(iae);
            throw mae;
        }
    }


    /** Attempts to locate and return a default MidiDevice of the specified
     * type.
     *
     * <p>
     *  类型。
     * 
     * 
     * @param deviceClass The requested device type, one of Synthesizer.class,
     * Sequencer.class, Receiver.class or Transmitter.class.
     * @throws  IllegalArgumentException on failure.
     */
    private static MidiDevice getDefaultDevice(Class deviceClass) {
        List providers = getMidiDeviceProviders();
        String providerClassName = JDK13Services.getDefaultProviderClassName(deviceClass);
        String instanceName = JDK13Services.getDefaultInstanceName(deviceClass);
        MidiDevice device;

        if (providerClassName != null) {
            MidiDeviceProvider defaultProvider = getNamedProvider(providerClassName, providers);
            if (defaultProvider != null) {
                if (instanceName != null) {
                    device = getNamedDevice(instanceName, defaultProvider, deviceClass);
                    if (device != null) {
                        return device;
                    }
                }
                device = getFirstDevice(defaultProvider, deviceClass);
                if (device != null) {
                    return device;
                }
            }
        }

        /* Provider class not specified or cannot be found, or
           provider class specified, and no appropriate device available or
        /* <p>
        /*  指定的提供程序类,并且没有适当的设备可用或
        /* 
        /* 
           provider class and instance specified and instance cannot be found or is not appropriate */
        if (instanceName != null) {
            device = getNamedDevice(instanceName, providers, deviceClass);
            if (device != null) {
                return device;
            }
        }

        /* No default are specified, or if something is specified, everything
        /* <p>
        /* 
           failed. */
        device = getFirstDevice(providers, deviceClass);
        if (device != null) {
            return device;
        }
        throw new IllegalArgumentException("Requested device not installed");
    }



    /** Return a MidiDeviceProcider of a given class from the list of
        MidiDeviceProviders.

    /* <p>
    /*  MidiDeviceProviders。
    /* 
    /* 
        @param providerClassName The class name of the provider to be returned.
        @param provider The list of MidiDeviceProviders that is searched.
        @return A MidiDeviceProvider of the requested class, or null if none
        is found.
    */
    private static MidiDeviceProvider getNamedProvider(String providerClassName, List providers) {
        for(int i = 0; i < providers.size(); i++) {
            MidiDeviceProvider provider = (MidiDeviceProvider) providers.get(i);
            if (provider.getClass().getName().equals(providerClassName)) {
                return provider;
            }
        }
        return null;
    }


    /** Return a MidiDevice with a given name from a given MidiDeviceProvider.
    /* <p>
    /* 
        @param deviceName The name of the MidiDevice to be returned.
        @param provider The MidiDeviceProvider to check for MidiDevices.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.

        @return A MidiDevice matching the requirements, or null if none is found.
    */
    private static MidiDevice getNamedDevice(String deviceName,
                                             MidiDeviceProvider provider,
                                             Class deviceClass) {
        MidiDevice device;
        // try to get MIDI port
        device = getNamedDevice(deviceName, provider, deviceClass,
                                 false, false);
        if (device != null) {
            return device;
        }

        if (deviceClass == Receiver.class) {
            // try to get Synthesizer
            device = getNamedDevice(deviceName, provider, deviceClass,
                                     true, false);
            if (device != null) {
                return device;
            }
        }

        return null;
    }


    /** Return a MidiDevice with a given name from a given MidiDeviceProvider.
    /* <p>
    /* 
      @param deviceName The name of the MidiDevice to be returned.
      @param provider The MidiDeviceProvider to check for MidiDevices.
      @param deviceClass The requested device type, one of Synthesizer.class,
      Sequencer.class, Receiver.class or Transmitter.class.

      @return A MidiDevice matching the requirements, or null if none is found.
     */
    private static MidiDevice getNamedDevice(String deviceName,
                                             MidiDeviceProvider provider,
                                             Class deviceClass,
                                             boolean allowSynthesizer,
                                             boolean allowSequencer) {
        MidiDevice.Info[] infos = provider.getDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            if (infos[i].getName().equals(deviceName)) {
                MidiDevice device = provider.getDevice(infos[i]);
                if (isAppropriateDevice(device, deviceClass,
                                        allowSynthesizer, allowSequencer)) {
                    return device;
                }
            }
        }
        return null;
    }


    /** Return a MidiDevice with a given name from a list of
        MidiDeviceProviders.
    /* <p>
    /*  MidiDeviceProviders。
    /* 
    /* 
        @param deviceName The name of the MidiDevice to be returned.
        @param providers The List of MidiDeviceProviders to check for
        MidiDevices.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A Mixer matching the requirements, or null if none is found.
    */
    private static MidiDevice getNamedDevice(String deviceName,
                                             List providers,
                                             Class deviceClass) {
        MidiDevice device;
        // try to get MIDI port
        device = getNamedDevice(deviceName, providers, deviceClass,
                                 false, false);
        if (device != null) {
            return device;
        }

        if (deviceClass == Receiver.class) {
            // try to get Synthesizer
            device = getNamedDevice(deviceName, providers, deviceClass,
                                     true, false);
            if (device != null) {
                return device;
            }
        }

        return null;
    }


    /** Return a MidiDevice with a given name from a list of
        MidiDeviceProviders.
    /* <p>
    /*  MidiDeviceProviders。
    /* 
    /* 
        @param deviceName The name of the MidiDevice to be returned.
        @param providers The List of MidiDeviceProviders to check for
        MidiDevices.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A Mixer matching the requirements, or null if none is found.
     */
    private static MidiDevice getNamedDevice(String deviceName,
                                             List providers,
                                             Class deviceClass,
                                             boolean allowSynthesizer,
                                             boolean allowSequencer) {
        for(int i = 0; i < providers.size(); i++) {
            MidiDeviceProvider provider = (MidiDeviceProvider) providers.get(i);
            MidiDevice device = getNamedDevice(deviceName, provider,
                                               deviceClass,
                                               allowSynthesizer,
                                               allowSequencer);
            if (device != null) {
                return device;
            }
        }
        return null;
    }


    /** From a given MidiDeviceProvider, return the first appropriate device.
    /* <p>
    /* 
        @param provider The MidiDeviceProvider to check for MidiDevices.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A MidiDevice is considered appropriate, or null if no
        appropriate device is found.
    */
    private static MidiDevice getFirstDevice(MidiDeviceProvider provider,
                                             Class deviceClass) {
        MidiDevice device;
        // try to get MIDI port
        device = getFirstDevice(provider, deviceClass,
                                false, false);
        if (device != null) {
            return device;
        }

        if (deviceClass == Receiver.class) {
            // try to get Synthesizer
            device = getFirstDevice(provider, deviceClass,
                                    true, false);
            if (device != null) {
                return device;
            }
        }

        return null;
    }


    /** From a given MidiDeviceProvider, return the first appropriate device.
    /* <p>
    /* 
        @param provider The MidiDeviceProvider to check for MidiDevices.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A MidiDevice is considered appropriate, or null if no
        appropriate device is found.
     */
    private static MidiDevice getFirstDevice(MidiDeviceProvider provider,
                                             Class deviceClass,
                                             boolean allowSynthesizer,
                                             boolean allowSequencer) {
        MidiDevice.Info[] infos = provider.getDeviceInfo();
        for (int j = 0; j < infos.length; j++) {
            MidiDevice device = provider.getDevice(infos[j]);
            if (isAppropriateDevice(device, deviceClass,
                                    allowSynthesizer, allowSequencer)) {
                return device;
            }
        }
        return null;
    }


    /** From a List of MidiDeviceProviders, return the first appropriate
        MidiDevice.
    /* <p>
    /*  MidiDevice。
    /* 
    /* 
        @param providers The List of MidiDeviceProviders to search.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A MidiDevice that is considered appropriate, or null
        if none is found.
    */
    private static MidiDevice getFirstDevice(List providers,
                                             Class deviceClass) {
        MidiDevice device;
        // try to get MIDI port
        device = getFirstDevice(providers, deviceClass,
                                false, false);
        if (device != null) {
            return device;
        }

        if (deviceClass == Receiver.class) {
            // try to get Synthesizer
            device = getFirstDevice(providers, deviceClass,
                                    true, false);
            if (device != null) {
                return device;
            }
        }

        return null;
    }


    /** From a List of MidiDeviceProviders, return the first appropriate
        MidiDevice.
    /* <p>
    /*  MidiDevice。
    /* 
    /* 
        @param providers The List of MidiDeviceProviders to search.
        @param deviceClass The requested device type, one of Synthesizer.class,
        Sequencer.class, Receiver.class or Transmitter.class.
        @return A MidiDevice that is considered appropriate, or null
        if none is found.
     */
    private static MidiDevice getFirstDevice(List providers,
                                             Class deviceClass,
                                             boolean allowSynthesizer,
                                             boolean allowSequencer) {
        for(int i = 0; i < providers.size(); i++) {
            MidiDeviceProvider provider = (MidiDeviceProvider) providers.get(i);
            MidiDevice device = getFirstDevice(provider, deviceClass,
                                               allowSynthesizer,
                                               allowSequencer);
            if (device != null) {
                return device;
            }
        }
        return null;
    }


    /** Checks if a MidiDevice is appropriate.
        If deviceClass is Synthesizer or Sequencer, a device implementing
        the respective interface is considered appropriate. If deviceClass
        is Receiver or Transmitter, a device is considered appropriate if
        it implements neither Synthesizer nor Transmitter, and if it can
        provide at least one Receiver or Transmitter, respectively.

    /* <p>
    /*  如果deviceClass是Synthesizer或Sequencer,则实现相应接口的设备被认为是适当的。
    /* 如果deviceClass是Receiver或Transmitter,如果一个设备既不实现合成器也不实现发送器,并且它可以分别提供至少一个接收器或发送器,则认为该设备是适当的。
    /* 
    /* 
        @param device the MidiDevice to test
        @param allowSynthesizer if true, Synthesizers are considered
        appropriate. Otherwise only pure MidiDevices are considered
        appropriate (unless allowSequencer is true). This flag only has an
        effect for deviceClass Receiver and Transmitter. For other device
        classes (Sequencer and Synthesizer), this flag has no effect.
        @param allowSequencer if true, Sequencers are considered
        appropriate. Otherwise only pure MidiDevices are considered
        appropriate (unless allowSynthesizer is true). This flag only has an
        effect for deviceClass Receiver and Transmitter. For other device
        classes (Sequencer and Synthesizer), this flag has no effect.
        @return true if the device is considered appropriate according to the
        rules given above, false otherwise.
    */
    private static boolean isAppropriateDevice(MidiDevice device,
                                               Class deviceClass,
                                               boolean allowSynthesizer,
                                               boolean allowSequencer) {
        if (deviceClass.isInstance(device)) {
            // This clause is for deviceClass being either Synthesizer
            // or Sequencer.
            return true;
        } else {
            // Now the case that deviceClass is Transmitter or
            // Receiver. If neither allowSynthesizer nor allowSequencer is
            // true, we require device instances to be
            // neither Synthesizer nor Sequencer, since we only want
            // devices representing MIDI ports.
            // Otherwise, the respective type is accepted, too
            if ( (! (device instanceof Sequencer) &&
                  ! (device instanceof Synthesizer) ) ||
                 ((device instanceof Sequencer) && allowSequencer) ||
                 ((device instanceof Synthesizer) && allowSynthesizer)) {
                // And of cource, the device has to be able to provide
                // Receivers or Transmitters.
                if ((deviceClass == Receiver.class &&
                     device.getMaxReceivers() != 0) ||
                    (deviceClass == Transmitter.class &&
                     device.getMaxTransmitters() != 0)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Obtains the set of services currently installed on the system
     * using sun.misc.Service, the SPI mechanism in 1.3.
     * <p>
     * 
     * @return a List of instances of providers for the requested service.
     * If no providers are available, a List of length 0 will be returned.
     */
    private static List getProviders(Class providerClass) {
        return JDK13Services.getProviders(providerClass);
    }
}
