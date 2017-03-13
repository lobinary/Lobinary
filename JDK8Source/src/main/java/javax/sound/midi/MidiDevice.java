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

import java.util.List;

 /**
 * <code>MidiDevice</code> is the base interface for all MIDI devices.
 * Common devices include synthesizers, sequencers, MIDI input ports, and MIDI
 * output ports.
 *
 * <p>A <code>MidiDevice</code> can be a transmitter or a receiver of
 * MIDI events, or both. Therefore, it can provide {@link Transmitter}
 * or {@link Receiver} instances (or both). Typically, MIDI IN ports
 * provide transmitters, MIDI OUT ports and synthesizers provide
 * receivers. A Sequencer typically provides transmitters for playback
 * and receivers for recording.
 *
 * <p>A <code>MidiDevice</code> can be opened and closed explicitly as
 * well as implicitly. Explicit opening is accomplished by calling
 * {@link #open}, explicit closing is done by calling {@link
 * #close} on the <code>MidiDevice</code> instance.
 * If an application opens a <code>MidiDevice</code>
 * explicitly, it has to close it explicitly to free system resources
 * and enable the application to exit cleanly. Implicit opening is
 * done by calling {@link javax.sound.midi.MidiSystem#getReceiver
 * MidiSystem.getReceiver} and {@link
 * javax.sound.midi.MidiSystem#getTransmitter
 * MidiSystem.getTransmitter}. The <code>MidiDevice</code> used by
 * <code>MidiSystem.getReceiver</code> and
 * <code>MidiSystem.getTransmitter</code> is implementation-dependant
 * unless the properties <code>javax.sound.midi.Receiver</code>
 * and <code>javax.sound.midi.Transmitter</code> are used (see the
 * description of properties to select default providers in
 * {@link javax.sound.midi.MidiSystem}). A <code>MidiDevice</code>
 * that was opened implicitly, is closed implicitly by closing the
 * <code>Receiver</code> or <code>Transmitter</code> that resulted in
 * opening it. If more than one implicitly opening
 * <code>Receiver</code> or <code>Transmitter</code> were obtained by
 * the application, the device is closed after the last
 * <code>Receiver</code> or <code>Transmitter</code> has been
 * closed. On the other hand, calling <code>getReceiver</code> or
 * <code>getTransmitter</code> on the device instance directly does
 * not open the device implicitly. Closing these
 * <code>Transmitter</code>s and <code>Receiver</code>s does not close
 * the device implicitly. To use a device with <code>Receiver</code>s
 * or <code>Transmitter</code>s obtained this way, the device has to
 * be opened and closed explicitly.
 *
 * <p>If implicit and explicit opening and closing are mixed on the
 * same <code>MidiDevice</code> instance, the following rules apply:
 *
 * <ul>
 * <li>After an explicit open (either before or after implicit
 * opens), the device will not be closed by implicit closing. The only
 * way to close an explicitly opened device is an explicit close.</li>
 *
 * <li>An explicit close always closes the device, even if it also has
 * been opened implicitly. A subsequent implicit close has no further
 * effect.</li>
 * </ul>
 *
 * To detect if a MidiDevice represents a hardware MIDI port, the
 * following programming technique can be used:
 *
 * <pre>{@code
 * MidiDevice device = ...;
 * if ( ! (device instanceof Sequencer) && ! (device instanceof Synthesizer)) {
 *   // we're now sure that device represents a MIDI port
 *   // ...
 * }
 * }</pre>
 *
 * <p>
 * A <code>MidiDevice</code> includes a <code>{@link MidiDevice.Info}</code> object
 * to provide manufacturer information and so on.
 *
 * <p>
 *  <code> MidiDevice </code>是所有MIDI设备的基本接口常见的设备包括合成器,音序器,MIDI输入端口和MIDI输出端口
 * 
 *  <p> <code> MidiDevice </code>可以是MIDI事件的发射器或接收器,或者两者。
 * 因此,它可以提供{@link Transmitter}或{@link Receiver}端口提供发射器,MIDI输出端口和合成器提供接收器音序器通常提供用于播放的发射器和用于记录的接收器。
 * 
 * <p>可以通过调用{@link #open}来显式地打开和关闭一个<code> MidiDevice </code>以及隐式打开,通过调用{@link #close}代码> MidiDevice </code>
 * 实例如果一个应用程序显式地打开一个<code> MidiDevice </code>,它必须显式地关闭它以释放系统资源,并使应用程序能够完全退出隐式打开是通过调用{@link javaxsoundmidiMidiSystem #getReceiver MidiSystemgetReceiver}
 * 和{@link javaxsoundmidiMidiSystem#getTransmitter MidiSystemgetTransmitter} <code> MidiSystemgetReceive
 * r </code>和<code> MidiSystemgetTransmitter </code>使用的<code> MidiDevice </code>是实现相关的,除非属性<code > javax
 * soundmidiReceiver </code>和<code> javaxsoundmidiTransmitter </code>(参见在{@link javaxsoundmidiMidiSystem}
 * 中选择默认提供者的属性的描述)隐式打开的<code> MidiDevice </code>通过关闭<code> Receiver < code>或<code> Transmitter </code>,导
 * 致打开它如果应用程序获得了多个隐式打开<code> Receiver </code>或<code> Transmitter </code>最后的<code> Receiver </code>或<code>
 *  Transmitter </code>已关闭另一方面,在设备实例上直接调用<code> getReceiver </code>或<code> getTransmitter </code>不隐式打开设备
 * 关闭这些<code> Transmitter </code>和<code> Receiver </code>不会隐式关闭设备要使用以这种方式获得的<code> Receiver </code>或<code>
 *  Transmitter </code>的设备,必须明确地打开和关闭设备。
 * 
 * <p>如果隐式和显式打开和关闭混合在同一个<code> MidiDevice </code>实例上,则适用以下规则：
 * 
 * <ul>
 *  <li>在显式打开之后(隐式打开之前或之后),设备不会被隐式关闭关闭关闭显式打开的设备的唯一方法是显式关闭</li>
 * 
 *  <li>显式关闭始终关闭设备,即使它也已隐式打开后续的隐式关闭没有进一步的影响</li>
 * </ul>
 * 
 *  为了检测MidiDevice是否表示硬件MIDI端口,可以使用以下编程技术：
 * 
 *  <pre> {@ code MidiDevice device =; if(！(device instanceof Sequencer)&&！(device instanceof Synthesizer)){//我们现在确定设备代表一个MIDI端口//}
 * } </pre>。
 * 
 * <p>
 * <code> MidiDevice </code>包括<code> {@ link MidiDeviceInfo} </code>对象以提供制造商信息等
 * 
 * 
 * @see Synthesizer
 * @see Sequencer
 * @see Receiver
 * @see Transmitter
 *
 * @author Kara Kytle
 * @author Florian Bomers
 */

public interface MidiDevice extends AutoCloseable {


    /**
     * Obtains information about the device, including its Java class and
     * <code>Strings</code> containing its name, vendor, and description.
     *
     * <p>
     *  获取有关设备的信息,包括其Java类和包含其名称,供应商和描述的<code> Strings </code>
     * 
     * 
     * @return device info
     */
    public Info getDeviceInfo();


    /**
     * Opens the device, indicating that it should now acquire any
     * system resources it requires and become operational.
     *
     * <p>An application opening a device explicitly with this call
     * has to close the device by calling {@link #close}. This is
     * necessary to release system resources and allow applications to
     * exit cleanly.
     *
     * <p>
     * Note that some devices, once closed, cannot be reopened.  Attempts
     * to reopen such a device will always result in a MidiUnavailableException.
     *
     * <p>
     *  打开设备,指示它现在应该获取它需要的任何系统资源并变得可操作
     * 
     *  <p>通过此调用显式打开设备的应用程序必须通过调用{@link #close}关闭设备。这是释放系统资源并允许应用程序完全退出
     * 
     * <p>
     *  注意,一些设备一旦关闭,就无法重新打开。尝试重新打开这样的设备将总会导致MidiUnavailableException
     * 
     * 
     * @throws MidiUnavailableException thrown if the device cannot be
     * opened due to resource restrictions.
     * @throws SecurityException thrown if the device cannot be
     * opened due to security restrictions.
     *
     * @see #close
     * @see #isOpen
     */
    public void open() throws MidiUnavailableException;


    /**
     * Closes the device, indicating that the device should now release
     * any system resources it is using.
     *
     * <p>All <code>Receiver</code> and <code>Transmitter</code> instances
     * open from this device are closed. This includes instances retrieved
     * via <code>MidiSystem</code>.
     *
     * <p>
     * 关闭设备,指示设备现在应释放其使用的任何系统资源
     * 
     *  <p>从此设备打开的所有<code> Receiver </code>和<code> Transmitter </code>实例已关闭包括通过<code> MidiSystem </code>
     * 
     * 
     * @see #open
     * @see #isOpen
     */
    public void close();


    /**
     * Reports whether the device is open.
     *
     * <p>
     *  报告设备是否打开
     * 
     * 
     * @return <code>true</code> if the device is open, otherwise
     * <code>false</code>
     * @see #open
     * @see #close
     */
    public boolean isOpen();


    /**
     * Obtains the current time-stamp of the device, in microseconds.
     * If a device supports time-stamps, it should start counting at
     * 0 when the device is opened and continue incrementing its
     * time-stamp in microseconds until the device is closed.
     * If it does not support time-stamps, it should always return
     * -1.
     * <p>
     *  获取设备的当前时间戳(以微秒为单位)如果设备支持时间戳,则应在设备打开时开始计数,并以微秒为单位继续递增其时间戳,直到设备关闭为止如果不支持时间戳,它应该总是返回-1
     * 
     * 
     * @return the current time-stamp of the device in microseconds,
     * or -1 if time-stamping is not supported by the device.
     */
    public long getMicrosecondPosition();


    /**
     * Obtains the maximum number of MIDI IN connections available on this
     * MIDI device for receiving MIDI data.
     * <p>
     *  获取此MIDI设备上可用于接收MIDI数据的最大MIDI IN连接数
     * 
     * 
     * @return maximum number of MIDI IN connections,
     * or -1 if an unlimited number of connections is available.
     */
    public int getMaxReceivers();


    /**
     * Obtains the maximum number of MIDI OUT connections available on this
     * MIDI device for transmitting MIDI data.
     * <p>
     * 获取此MIDI设备上用于传输MIDI数据的最大MIDI OUT连接数
     * 
     * 
     * @return maximum number of MIDI OUT connections,
     * or -1 if an unlimited number of connections is available.
     */
    public int getMaxTransmitters();


    /**
     * Obtains a MIDI IN receiver through which the MIDI device may receive
     * MIDI data.  The returned receiver must be closed when the application
     * has finished using it.
     *
     * <p>Usually the returned receiver implements
     * the {@code MidiDeviceReceiver} interface.
     *
     * <p>Obtaining a <code>Receiver</code> with this method does not
     * open the device. To be able to use the device, it has to be
     * opened explicitly by calling {@link #open}. Also, closing the
     * <code>Receiver</code> does not close the device. It has to be
     * closed explicitly by calling {@link #close}.
     *
     * <p>
     *  获得MIDI IN接收器,通过它MIDI设备可以接收MIDI数据返回的接收器必须在应用程序使用完毕后关闭
     * 
     *  <p>通常返回的接收器实现{@code MidiDeviceReceiver}接口
     * 
     *  <p>使用此方法获取<code> Receiver </code>无法打开设备要能够使用设备,必须通过调用{@link #open}显式打开此外,还要关闭<code> Receiver </code>
     * 不关闭设备它必须显式地关闭通过调用{@link #close}。
     * 
     * 
     * @return a receiver for the device.
     * @throws MidiUnavailableException thrown if a receiver is not available
     * due to resource restrictions
     * @see Receiver#close()
     */
    public Receiver getReceiver() throws MidiUnavailableException;


    /**
     * Returns all currently active, non-closed receivers
     * connected with this MidiDevice.
     * A receiver can be removed
     * from the device by closing it.
     *
     * <p>Usually the returned receivers implement
     * the {@code MidiDeviceReceiver} interface.
     *
     * <p>
     *  返回与此MidiDevice连接的所有当前活动的非封闭接收器A接收器可以通过关闭它从设备中删除
     * 
     * <p>通常返回的接收器实现{@code MidiDeviceReceiver}接口
     * 
     * 
     * @return an unmodifiable list of the open receivers
     * @since 1.5
     */
    List<Receiver> getReceivers();


    /**
     * Obtains a MIDI OUT connection from which the MIDI device will transmit
     * MIDI data  The returned transmitter must be closed when the application
     * has finished using it.
     *
     * <p>Usually the returned transmitter implements
     * the {@code MidiDeviceTransmitter} interface.
     *
     * <p>Obtaining a <code>Transmitter</code> with this method does not
     * open the device. To be able to use the device, it has to be
     * opened explicitly by calling {@link #open}. Also, closing the
     * <code>Transmitter</code> does not close the device. It has to be
     * closed explicitly by calling {@link #close}.
     *
     * <p>
     *  获得MIDI OUT连接,MIDI设备将从该MIDI连接传输MIDI数据。当应用程序使用完毕后,返回的发射器必须关闭
     * 
     *  <p>通常返回的发射器实现{@code MidiDeviceTransmitter}接口
     * 
     *  <p>使用此方法获取<code>发送器</code>不会打开设备要能够使用设备,必须通过调用{@link #open}显式打开。
     * 另外,关闭<code>发送器</code>不关闭设备它必须通过调用{@link #close}。
     * 
     * 
     * @return a MIDI OUT transmitter for the device.
     * @throws MidiUnavailableException thrown if a transmitter is not available
     * due to resource restrictions
     * @see Transmitter#close()
     */
    public Transmitter getTransmitter() throws MidiUnavailableException;


    /**
     * Returns all currently active, non-closed transmitters
     * connected with this MidiDevice.
     * A transmitter can be removed
     * from the device by closing it.
     *
     * <p>Usually the returned transmitters implement
     * the {@code MidiDeviceTransmitter} interface.
     *
     * <p>
     *  返回与此MidiDevice连接的所有当前活动的非闭合变送器A变送器可以通过关闭它从设备中删除
     * 
     * <p>通常返回的发射器实现{@code MidiDeviceTransmitter}接口
     * 
     * 
     * @return an unmodifiable list of the open transmitters
     * @since 1.5
     */
    List<Transmitter> getTransmitters();



    /**
     * A <code>MidiDevice.Info</code> object contains assorted
     * data about a <code>{@link MidiDevice}</code>, including its
     * name, the company who created it, and descriptive text.
     *
     * <p>
     *  <code> MidiDeviceInfo </code>对象包含有关<code> {@ link MidiDevice} </code>的各种数据,包括其名称,创建它的公司和描述性文本
     * 
     * 
     * @see MidiDevice#getDeviceInfo
     */
    public static class Info {

        /**
         * The device's name.
         * <p>
         *  设备名称
         * 
         */
        private String name;

        /**
         * The name of the company who provides the device.
         * <p>
         *  提供设备的公司的名称
         * 
         */
        private String vendor;

        /**
         * A description of the device.
         * <p>
         *  设备的描述
         * 
         */
        private String description;

        /**
         * Device version.
         * <p>
         *  设备版本
         * 
         */
        private String version;


        /**
         * Constructs a device info object.
         *
         * <p>
         *  构造设备信息对象
         * 
         * 
         * @param name the name of the device
         * @param vendor the name of the company who provides the device
         * @param description a description of the device
         * @param version version information for the device
         */
        protected Info(String name, String vendor, String description, String version) {

            this.name = name;
            this.vendor = vendor;
            this.description = description;
            this.version = version;
        }


        /**
         * Reports whether two objects are equal.
         * Returns <code>true</code> if the objects are identical.
         * <p>
         *  报告两个对象是否相等如果对象相同,则返回<code> true </code>
         * 
         * 
         * @param obj the reference object with which to compare this
         * object
         * @return <code>true</code> if this object is the same as the
         * <code>obj</code> argument; <code>false</code> otherwise
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }


        /**
         * Finalizes the hashcode method.
         * <p>
         *  完成哈希码方法
         * 
         */
        public final int hashCode() {
            return super.hashCode();
        }


        /**
         * Obtains the name of the device.
         *
         * <p>
         *  获取设备的名称
         * 
         * 
         * @return a string containing the device's name
         */
        public final String getName() {
            return name;
        }


        /**
         * Obtains the name of the company who supplies the device.
         * <p>
         *  获取提供设备的公司的名称
         * 
         * 
         * @return device the vendor's name
         */
        public final String getVendor() {
            return vendor;
        }


        /**
         * Obtains the description of the device.
         * <p>
         *  获取设备的描述
         * 
         * 
         * @return a description of the device
         */
        public final String getDescription() {
            return description;
        }


        /**
         * Obtains the version of the device.
         * <p>
         *  获取设备的版本
         * 
         * 
         * @return textual version information for the device.
         */
        public final String getVersion() {
            return version;
        }


        /**
         * Provides a string representation of the device information.

         * <p>
         *  提供设备信息的字符串表示形式
         * 
         * @return a description of the info object
         */
        public final String toString() {
            return name;
        }
    } // class Info


}
