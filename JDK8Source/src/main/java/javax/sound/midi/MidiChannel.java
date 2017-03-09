/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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


/**
 * A <code>MidiChannel</code> object represents a single MIDI channel.
 * Generally, each <code>MidiChannel</code> method processes a like-named MIDI
 * "channel voice" or "channel mode" message as defined by the MIDI specification. However,
 * <code>MidiChannel</code> adds some "get" methods  that retrieve the value
 * most recently set by one of the standard MIDI channel messages.  Similarly,
 * methods for per-channel solo and mute have been added.
 * <p>
 * A <code>{@link Synthesizer}</code> object has a collection
 * of <code>MidiChannels</code>, usually one for each of the 16 channels
 * prescribed by the MIDI 1.0 specification.  The <code>Synthesizer</code>
 * generates sound when its <code>MidiChannels</code> receive
 * <code>noteOn</code> messages.
 * <p>
 * See the MIDI 1.0 Specification for more information about the prescribed
 * behavior of the MIDI channel messages, which are not exhaustively
 * documented here.  The specification is titled <code>MIDI Reference:
 * The Complete MIDI 1.0 Detailed Specification</code>, and is published by
 * the MIDI Manufacturer's Association (<a href = http://www.midi.org>
 * http://www.midi.org</a>).
 * <p>
 * MIDI was originally a protocol for reporting the gestures of a keyboard
 * musician.  This genesis is visible in the <code>MidiChannel</code> API, which
 * preserves such MIDI concepts as key number, key velocity, and key pressure.
 * It should be understood that the MIDI data does not necessarily originate
 * with a keyboard player (the source could be a different kind of musician, or
 * software).  Some devices might generate constant values for velocity
 * and pressure, regardless of how the note was performed.
 * Also, the MIDI specification often leaves it up to the
 * synthesizer to use the data in the way the implementor sees fit.  For
 * example, velocity data need not always be mapped to volume and/or brightness.
 *
 * <p>
 *  <code> MidiChannel </code>对象表示单个MIDI通道。
 * 通常,每个<code> MidiChannel </code>方法处理由MIDI规范定义的类似命名的MIDI"信道语音"或"信道模式"消息。
 * 然而,<code> MidiChannel </code>添加了一些"get"方法来检索最近由标准MIDI通道消息之一设置的值。类似地,添加了用于每声道独奏和静音的方法。
 * <p>
 *  <code> {@ link Synthesizer} </code>对象具有<code> MidiChannels </code>的集合,通常为MIDI 1.0规范规定的16个频道之一。
 *  <code> Synthesizer </code>在<code> MidiChannels </code>接收<code> noteOn </code>消息时生成声音。
 * <p>
 *  有关MIDI通道消息的规定行为的更多信息,请参阅MIDI 1.0规范,这里不一一列举。
 * 该规范的标题是<code> MIDI Reference：The Complete MIDI 1.0 Detailed Specification </code>,由MIDI制造商协会(<a href = http://www.midi.org>
 *  http：// www。
 *  有关MIDI通道消息的规定行为的更多信息,请参阅MIDI 1.0规范,这里不一一列举。 midi.org </a>)。
 * <p>
 * MIDI最初是用于报告键盘音乐家的手势的协议。这种起源在<code> MidiChannel </code> API中是可见的,它保留了诸如键号,键速度和键压力等MIDI概念。
 * 应当理解,MIDI数据不一定源于键盘播放器(源可以是不同种类的音乐家或软件)。一些设备可能生成速度和压力的恒定值,而不管如何执行音符。此外,MIDI规范经常留给合成器使用实现者认为合适的方式使用数据。
 * 例如,速度数据不需要总是被映射到音量和/或亮度。
 * 
 * 
 * @see Synthesizer#getChannels
 *
 * @author David Rivas
 * @author Kara Kytle
 */

public interface MidiChannel {

    /**
     * Starts the specified note sounding.  The key-down velocity
     * usually controls the note's volume and/or brightness.
     * If <code>velocity</code> is zero, this method instead acts like
     * {@link #noteOff(int)}, terminating the note.
     *
     * <p>
     *  开始指定的音符探测。按键速度通常控制音符的音量和/或亮度。如果<code> velocity </code>为零,这个方法的行为就像{@link #noteOff(int)},结束注释。
     * 
     * 
     * @param noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
     * @param velocity the speed with which the key was depressed
     *
     * @see #noteOff(int, int)
     */
    public void noteOn(int noteNumber, int velocity);

    /**
     * Turns the specified note off.  The key-up velocity, if not ignored, can
     * be used to affect how quickly the note decays.
     * In any case, the note might not die away instantaneously; its decay
     * rate is determined by the internals of the <code>Instrument</code>.
     * If the Hold Pedal (a controller; see
     * {@link #controlChange(int, int) controlChange})
     * is down, the effect of this method is deferred until the pedal is
     * released.
     *
     *
     * <p>
     *  关闭指定的音符。键上升速度,如果不被忽略,可用于影响音符衰减的速度。在任何情况下,音符可能不会立即消失;其衰减速率由<code> Instrument </code>的内部确定。
     * 如果Hold Pedal(一个控制器;参见{@link #controlChange(int,int)controlChange})是向下的,这种方法的效果推迟,直到释放踏板。
     * 
     * 
     * @param noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
     * @param velocity the speed with which the key was released
     *
     * @see #noteOff(int)
     * @see #noteOn
     * @see #allNotesOff
     * @see #allSoundOff
     */
    public void noteOff(int noteNumber, int velocity);

    /**
     * Turns the specified note off.
     *
     * <p>
     *  关闭指定的音符。
     * 
     * 
     * @param noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
     *
     * @see #noteOff(int, int)
     */
    public void noteOff(int noteNumber);

    /**
     * Reacts to a change in the specified note's key pressure.
     * Polyphonic key pressure
     * allows a keyboard player to press multiple keys simultaneously, each
     * with a different amount of pressure.  The pressure, if not ignored,
     * is typically used to vary such features as the volume, brightness,
     * or vibrato of the note.
     *
     * It is possible that the underlying synthesizer
     * does not support this MIDI message. In order
     * to verify that <code>setPolyPressure</code>
     * was successful, use <code>getPolyPressure</code>.
     *
     * <p>
     * 对指定单据的键压力的更改做出反应。复音键压力允许键盘演奏者同时按下多个键,每个键具有不同的压力量。压力,如果不被忽略,通常用于改变诸如音符的音量,亮度或颤音之类的特征。
     * 
     *  底层合成器可能不支持此MIDI消息。为了验证<code> setPolyPressure </code>是否成功,请使用<code> getPolyPressure </code>。
     * 
     * 
     * @param noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
     * @param pressure value for the specified key, from 0 to 127 (127 =
     * maximum pressure)
     *
     * @see #getPolyPressure(int)
     */
    public void setPolyPressure(int noteNumber, int pressure);

    /**
     * Obtains the pressure with which the specified key is being depressed.
     *
     * <p>
     *  获取按下指定键的压力。
     * 
     * 
     * @param noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
     *
     * If the device does not support setting poly pressure,
     * this method always returns 0. Calling
     * <code>setPolyPressure</code> will have no effect then.
     *
     * @return the amount of pressure for that note, from 0 to 127
     * (127 = maximum pressure)
     *
     * @see #setPolyPressure(int, int)
     */
    public int getPolyPressure(int noteNumber);

    /**
     * Reacts to a change in the keyboard pressure.  Channel
     * pressure indicates how hard the keyboard player is depressing
     * the entire keyboard.  This can be the maximum or
     * average of the per-key pressure-sensor values, as set by
     * <code>setPolyPressure</code>.  More commonly, it is a measurement of
     * a single sensor on a device that doesn't implement polyphonic key
     * pressure.  Pressure can be used to control various aspects of the sound,
     * as described under {@link #setPolyPressure(int, int) setPolyPressure}.
     *
     * It is possible that the underlying synthesizer
     * does not support this MIDI message. In order
     * to verify that <code>setChannelPressure</code>
     * was successful, use <code>getChannelPressure</code>.
     *
     * <p>
     *  反应到键盘压力的变化。通道压力表示键盘播放器按下整个键盘的硬度。这可以是由<code> setPolyPressure </code>设置的每个键的压力传感器值的最大值或平均值。
     * 更常见地,它是在不实现多音键压力的设备上的单个传感器的测量。压力可以用于控制声音的各个方面,如{@link #setPolyPressure(int,int)setPolyPressure}中所述。
     * 
     *  底层合成器可能不支持此MIDI消息。为了验证<code> setChannelPressure </code>是否成功,请使用<code> getChannelPressure </code>。
     * 
     * 
     * @param pressure the pressure with which the keyboard is being depressed,
     * from 0 to 127 (127 = maximum pressure)
     * @see #setPolyPressure(int, int)
     * @see #getChannelPressure
     */
    public void setChannelPressure(int pressure);

    /**
     * Obtains the channel's keyboard pressure.
     * If the device does not support setting channel pressure,
     * this method always returns 0. Calling
     * <code>setChannelPressure</code> will have no effect then.
     *
     * <p>
     *  获取通道的键盘压力。如果设备不支持设置通道压力,此方法总是返回0.调用<code> setChannelPressure </code>将没有效果。
     * 
     * 
     * @return the amount of pressure for that note,
     *         from 0 to 127 (127 = maximum pressure)
     *
     * @see #setChannelPressure(int)
     */
    public int getChannelPressure();

    /**
     * Reacts to a change in the specified controller's value.  A controller
     * is some control other than a keyboard key, such as a
     * switch, slider, pedal, wheel, or breath-pressure sensor.
     * The MIDI 1.0 Specification provides standard numbers for typical
     * controllers on MIDI devices, and describes the intended effect
     * for some of the controllers.
     * The way in which an
     * <code>Instrument</code> reacts to a controller change may be
     * specific to the <code>Instrument</code>.
     * <p>
     * The MIDI 1.0 Specification defines both 7-bit controllers
     * and 14-bit controllers.  Continuous controllers, such
     * as wheels and sliders, typically have 14 bits (two MIDI bytes),
     * while discrete controllers, such as switches, typically have 7 bits
     * (one MIDI byte).  Refer to the specification to see the
     * expected resolution for each type of control.
     * <p>
     * Controllers 64 through 95 (0x40 - 0x5F) allow 7-bit precision.
     * The value of a 7-bit controller is set completely by the
     * <code>value</code> argument.  An additional set of controllers
     * provide 14-bit precision by using two controller numbers, one
     * for the most significant 7 bits and another for the least significant
     * 7 bits.  Controller numbers 0 through 31 (0x00 - 0x1F) control the
     * most significant 7 bits of 14-bit controllers; controller numbers
     * 32 through 63 (0x20 - 0x3F) control the least significant 7 bits of
     * these controllers.  For example, controller number 7 (0x07) controls
     * the upper 7 bits of the channel volume controller, and controller
     * number 39 (0x27) controls the lower 7 bits.
     * The value of a 14-bit controller is determined
     * by the interaction of the two halves.  When the most significant 7 bits
     * of a controller are set (using controller numbers 0 through 31), the
     * lower 7 bits are automatically set to 0.  The corresponding controller
     * number for the lower 7 bits may then be used to further modulate the
     * controller value.
     *
     * It is possible that the underlying synthesizer
     * does not support a specific controller message. In order
     * to verify that a call to <code>controlChange</code>
     * was successful, use <code>getController</code>.
     *
     * <p>
     * 对指定控制器值的更改做出反应。控制器是除了键盘键之外的一些控制,例如开关,滑块,踏板,轮或呼吸压力传感器。
     *  MIDI 1.0规范为MIDI设备上的典型控制器提供了标准编号,并描述了某些控制器的预期效果。
     *  <code> Instrument </code>对控制器更改的反应方式可能特定于<code> Instrument </code>。
     * <p>
     *  MIDI 1.0规范定义了7位控制器和14位控制器。连续控制器,例如轮子和滑块,通常具有14位(两个MIDI字节),而离散控制器例如开关通常具有7位(一个MIDI字节)。
     * 请参阅规格以查看每种类型控制的预期分辨率。
     * <p>
     * 控制器64至95(0x40  -  0x5F)允许7位精度。 7位控制器的值由<code> value </code>参数完全设置。
     * 另一组控制器通过使用两个控制器号提供14位精度,一个用于最高有效7位,另一组用于最低有效7位。
     * 控制器号0至31(0x00  -  0x1F)控制14位控制器的最高有效位7位;控制器号32至63(0x20  -  0x3F)控制这些控制器的最低有效7位。
     * 例如,控制器号7(0x07)控制通道音量控制器的高7位,控制器号39(0x27)控制低7位。 14位控制器的值由两个半部分的相互作用决定。
     * 当设置控制器的最高有效7位(使用控制器号0到31)时,低7位自动设置为0.然后可以使用低7位的相应控制器号来进一步调制控制器值。
     * 
     *  底层合成器可能不支持特定的控制器消息。为了验证对<code> controlChange </code>的调用是否成功,请使用<code> getController </code>。
     * 
     * 
     * @param controller the controller number (0 to 127; see the MIDI
     * 1.0 Specification for the interpretation)
     * @param value the value to which the specified controller is changed (0 to 127)
     *
     * @see #getController(int)
     */
    public void controlChange(int controller, int value);

    /**
     * Obtains the current value of the specified controller.  The return
     * value is represented with 7 bits. For 14-bit controllers, the MSB and
     * LSB controller value needs to be obtained separately. For example,
     * the 14-bit value of the volume controller can be calculated by
     * multiplying the value of controller 7 (0x07, channel volume MSB)
     * with 128 and adding the
     * value of controller 39 (0x27, channel volume LSB).
     *
     * If the device does not support setting a specific controller,
     * this method returns 0 for that controller.
     * Calling <code>controlChange</code> will have no effect then.
     *
     * <p>
     * 获取指定控制器的当前值。返回值用7位表示。对于14位控制器,需要单独获取MSB和LSB控制器值。
     * 例如,音量控制器的14位值可以通过将控制器7的值(0x07,通道音量MSB)乘以128并加上控制器39的值(0x27,通道音量LSB)来计算。
     * 
     *  如果设备不支持设置特定控制器,则该方法将为该控制器返回0。调用<code> controlChange </code>将没有效果。
     * 
     * 
     * @param controller the number of the controller whose value is desired.
     * The allowed range is 0-127; see the MIDI
     * 1.0 Specification for the interpretation.
     *
     * @return the current value of the specified controller (0 to 127)
     *
     * @see #controlChange(int, int)
     */
    public int getController(int controller);

    /**
     * Changes a program (patch).  This selects a specific
     * instrument from the currently selected bank of instruments.
     * <p>
     * The MIDI specification does not
     * dictate whether notes that are already sounding should switch
     * to the new instrument (timbre) or continue with their original timbre
     * until terminated by a note-off.
     * <p>
     * The program number is zero-based (expressed from 0 to 127).
     * Note that MIDI hardware displays and literature about MIDI
     * typically use the range 1 to 128 instead.
     *
     * It is possible that the underlying synthesizer
     * does not support a specific program. In order
     * to verify that a call to <code>programChange</code>
     * was successful, use <code>getProgram</code>.
     *
     * <p>
     *  更改程序(修补程序)。这从当前选定的乐器组中选择特定乐器。
     * <p>
     *  MIDI规格不指示已经发出声音的音符是否应切换到新乐器(音色)或继续其原始音色,直到音符关闭终止。
     * <p>
     *  程序编号为0(从0到127表示)。注意,MIDI硬件显示和关于MIDI的文献通常使用范围1到128。
     * 
     *  底层合成器可能不支持特定的程序。为了验证对<code> programChange </code>的调用是否成功,请使用<code> getProgram </code>。
     * 
     * 
     * @param program the program number to switch to (0 to 127)
     *
     * @see #programChange(int, int)
     * @see #getProgram()
     */
    public void programChange(int program);

    /**
     * Changes the program using bank and program (patch) numbers.
     *
     * It is possible that the underlying synthesizer
     * does not support a specific bank, or program. In order
     * to verify that a call to <code>programChange</code>
     * was successful, use <code>getProgram</code> and
     * <code>getController</code>.
     * Since banks are changed by way of control changes,
     * you can verify the current bank with the following
     * statement:
     * <pre>
     *   int bank = (getController(0) * 128)
     *              + getController(32);
     * </pre>
     *
     * <p>
     *  使用库和程序(补丁)号更改程序。
     * 
     * 底层合成器可能不支持特定的银行或程序。
     * 为了验证对<code> programChange </code>的调用是否成功,请使用<code> getProgram </code>和<code> getController </code>。
     * 由于通过控制更改来更改库,因此可以使用以下语句验证当前库：。
     * <pre>
     *  int bank =(getController(0)* 128)+ getController(32);
     * </pre>
     * 
     * 
     * @param bank the bank number to switch to (0 to 16383)
     * @param program the program (patch) to use in the specified bank (0 to 127)
     * @see #programChange(int)
     * @see #getProgram()
     */
    public void programChange(int bank, int program);

    /**
     * Obtains the current program number for this channel.
     * <p>
     *  获取此通道的当前程序编号。
     * 
     * 
     * @return the program number of the currently selected patch
     * @see Patch#getProgram
     * @see Synthesizer#loadInstrument
     * @see #programChange(int)
     */
    public int getProgram();

    /**
     * Changes the pitch offset for all notes on this channel.
     * This affects all currently sounding notes as well as subsequent ones.
     * (For pitch bend to cease, the value needs to be reset to the
     * center position.)
     * <p> The MIDI specification
     * stipulates that pitch bend be a 14-bit value, where zero
     * is maximum downward bend, 16383 is maximum upward bend, and
     * 8192 is the center (no pitch bend).  The actual
     * amount of pitch change is not specified; it can be changed by
     * a pitch-bend sensitivity setting.  However, the General MIDI
     * specification says that the default range should be two semitones
     * up and down from center.
     *
     * It is possible that the underlying synthesizer
     * does not support this MIDI message. In order
     * to verify that <code>setPitchBend</code>
     * was successful, use <code>getPitchBend</code>.
     *
     * <p>
     *  更改此通道上所有音符的音高偏移。这会影响当前所有的音符以及后续的音符。 (对于音高弯曲停止,该值需要重置到中心位置。
     * )</p> MIDI规范规定音高弯曲为14位值,其中零为最大向下弯曲,16383为最大向上弯曲, 8192是中心(无螺距弯曲)。未指定实际音高变化量;它可以通过音高 - 弯曲灵敏度设置更改。
     * 然而,通用MIDI规范说,默认范围应该是从中心向上和向下两个半音。
     * 
     *  底层合成器可能不支持此MIDI消息。为了验证<code> setPitchBend </code>是否成功,请使用<code> getPitchBend </code>。
     * 
     * 
     * @param bend the amount of pitch change, as a nonnegative 14-bit value
     * (8192 = no bend)
     *
     * @see #getPitchBend
     */
    public void setPitchBend(int bend);

    /**
     * Obtains the upward or downward pitch offset for this channel.
     * If the device does not support setting pitch bend,
     * this method always returns 8192. Calling
     * <code>setPitchBend</code> will have no effect then.
     *
     * <p>
     *  获取此通道的向上或向下俯仰偏移。如果设备不支持设置音高弯曲,此方法总是返回8192.调用<code> setPitchBend </code>将没有效果。
     * 
     * 
     * @return bend amount, as a nonnegative 14-bit value (8192 = no bend)
     *
     * @see #setPitchBend(int)
     */
    public int getPitchBend();

    /**
     * Resets all the implemented controllers to their default values.
     *
     * <p>
     * 将所有实现的控制器重置为默认值。
     * 
     * 
     * @see #controlChange(int, int)
     */
    public void resetAllControllers();

    /**
     * Turns off all notes that are currently sounding on this channel.
     * The notes might not die away instantaneously; their decay
     * rate is determined by the internals of the <code>Instrument</code>.
     * If the Hold Pedal controller (see
     * {@link #controlChange(int, int) controlChange})
     * is down, the effect of this method is deferred until the pedal is
     * released.
     *
     * <p>
     *  关闭目前在此频道上播放的所有音符。笔记可能不会立即死亡;它们的衰减率由<code> Instrument </code>的内部确定。
     * 如果保持踏板控制器(参见{@link #controlChange(int,int)controlChange})是向下的,这种方法的效果推迟,直到踏板释放。
     * 
     * 
     * @see #allSoundOff
     * @see #noteOff(int)
     */
    public void allNotesOff();

    /**
     * Immediately turns off all sounding notes on this channel, ignoring the
     * state of the Hold Pedal and the internal decay rate of the current
     * <code>Instrument</code>.
     *
     * <p>
     *  立即关闭此通道上的所有声音音符,忽略Hold Pedal的状态和当前<code> Instrument </code>的内部衰减速率。
     * 
     * 
     * @see #allNotesOff
     */
    public void allSoundOff();

    /**
     * Turns local control on or off.  The default is for local control
     * to be on.  The "on" setting means that if a device is capable
     * of both synthesizing sound and transmitting MIDI messages,
     * it will synthesize sound in response to the note-on and
     * note-off messages that it itself transmits.  It will also respond
     * to messages received from other transmitting devices.
     * The "off" setting means that the synthesizer will ignore its
     * own transmitted MIDI messages, but not those received from other devices.
     *
     * It is possible that the underlying synthesizer
     * does not support local control. In order
     * to verify that a call to <code>localControl</code>
     * was successful, check the return value.
     *
     * <p>
     *  打开或关闭本地控制。默认为本地控制打开。 "开"设置意味着如果设备能够合成声音和传送MIDI消息,则其将响应于其自身发送的音符打开和音符关闭消息来合成声音。它还将响应从其他发送设备接收的消息。
     *  "关闭"设置意味着合成器将忽略其自己发送的MIDI消息,而不是从其他设备接收的消息。
     * 
     *  底层合成器可能不支持本地控制。为了验证对<code> localControl </code>的调用是否成功,请检查返回值。
     * 
     * 
     * @param on <code>true</code> to turn local control on, <code>false</code>
     *  to turn local control off
     * @return the new local-control value, or false
     *         if local control is not supported
     *
     */
    public boolean localControl(boolean on);

    /**
     * Turns mono mode on or off.  In mono mode, the channel synthesizes
     * only one note at a time.  In poly mode (identical to mono mode off),
     * the channel can synthesize multiple notes simultaneously.
     * The default is mono off (poly mode on).
     * <p>
     * "Mono" is short for the word "monophonic," which in this context
     * is opposed to the word "polyphonic" and refers to a single synthesizer
     * voice per MIDI channel.  It
     * has nothing to do with how many audio channels there might be
     * (as in "monophonic" versus "stereophonic" recordings).
     *
     * It is possible that the underlying synthesizer
     * does not support mono mode. In order
     * to verify that a call to <code>setMono</code>
     * was successful, use <code>getMono</code>.
     *
     * <p>
     *  打开或关闭单声道模式。在单声道模式下,频道每次只合成一个音符。在多模式(与单声道模式关闭相同)下,通道可以同时合成多个音符。默认值为单声道关闭(多模式打开)。
     * <p>
     * "单声道"是单词"单声道"的缩写,在本上下文中与单词"多音"相反,并且是指每个MIDI声道的单个合成器声音。它与可能有多少音频通道无关(如在"单声道"对"立体声"录音中)。
     * 
     *  底层合成器可能不支持单声道模式。为了验证对<code> setMono </code>的调用是否成功,请使用<code> getMono </code>。
     * 
     * 
     * @param on <code>true</code> to turn mono mode on, <code>false</code> to
     * turn it off (which means turning poly mode on).
     *
     * @see #getMono
     * @see VoiceStatus
     */
    public void setMono(boolean on);

    /**
     * Obtains the current mono/poly mode.
     * Synthesizers that do not allow changing mono/poly mode
     * will always return the same value, regardless
     * of calls to <code>setMono</code>.
     * <p>
     *  获取当前的单/多模式。不允许更改单/多模式的合成器将始终返回相同的值,而不管调用<code> setMono </code>。
     * 
     * 
     * @return <code>true</code> if mono mode is on, otherwise
     * <code>false</code> (meaning poly mode is on).
     *
     * @see #setMono(boolean)
     */
    public boolean getMono();

    /**
     * Turns omni mode on or off.  In omni mode, the channel responds
     * to messages sent on all channels.  When omni is off, the channel
     * responds only to messages sent on its channel number.
     * The default is omni off.
     *
     * It is possible that the underlying synthesizer
     * does not support omni mode. In order
     * to verify that <code>setOmni</code>
     * was successful, use <code>getOmni</code>.
     *
     * <p>
     *  打开或关闭全向模式。在全向模式下,通道会响应所有通道上发送的消息。当omni关闭时,通道只响应在其通道号上发送的消息。默认值为omni off。
     * 
     *  底层合成器可能不支持全向模式。为了验证<code> setOmni </code>是否成功,请使用<code> getOmni </code>。
     * 
     * 
     * @param on <code>true</code> to turn omni mode on, <code>false</code> to
     * turn it off.
     *
     * @see #getOmni
     * @see VoiceStatus
     */
    public void setOmni(boolean on);

    /**
     * Obtains the current omni mode.
     * Synthesizers that do not allow changing the omni mode
     * will always return the same value, regardless
     * of calls to <code>setOmni</code>.
     * <p>
     *  获取当前全向模式。不允许更改全向模式的合成器将始终返回相同的值,而不管调用<code> setOmni </code>。
     * 
     * 
     * @return <code>true</code> if omni mode is on, otherwise
     * <code>false</code> (meaning omni mode is off).
     *
     * @see #setOmni(boolean)
     */
    public boolean getOmni();

    /**
     * Sets the mute state for this channel. A value of
     * <code>true</code> means the channel is to be muted, <code>false</code>
     * means the channel can sound (if other channels are not soloed).
     * <p>
     * Unlike {@link #allSoundOff()}, this method
     * applies to only a specific channel, not to all channels.  Further, it
     * silences not only currently sounding notes, but also subsequently
     * received notes.
     *
     * It is possible that the underlying synthesizer
     * does not support muting channels. In order
     * to verify that a call to <code>setMute</code>
     * was successful, use <code>getMute</code>.
     *
     * <p>
     *  设置此通道的静音状态。 <code> true </code>的值表示要静音的频道,<code> false </code>表示频道可以播放(如果其他频道没有独奏)。
     * <p>
     * 与{@link #allSoundOff()}不同,此方法仅适用于特定频道,而不适用于所有频道。此外,它不仅当前发声,而且随后接收音符。
     * 
     *  底层合成器可能不支持静音通道。为了验证对<code> setMute </code>的调用是否成功,请使用<code> getMute </code>。
     * 
     * 
     * @param mute the new mute state
     *
     * @see #getMute
     * @see #setSolo(boolean)
     */
    public void setMute(boolean mute);

    /**
     * Obtains the current mute state for this channel.
     * If the underlying synthesizer does not support
     * muting this channel, this method always returns
     * <code>false</code>.
     *
     * <p>
     *  获取此通道的当前静音状态。如果底层合成器不支持静音这个通道,这个方法总是返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> the channel is muted,
     *         or <code>false</code> if not
     *
     * @see #setMute(boolean)
     */
    public boolean getMute();

    /**
     * Sets the solo state for this channel.
     * If <code>solo</code> is <code>true</code> only this channel
     * and other soloed channels will sound. If <code>solo</code>
     * is <code>false</code> then only other soloed channels will
     * sound, unless no channels are soloed, in which case all
     * unmuted channels will sound.
     *
     * It is possible that the underlying synthesizer
     * does not support solo channels. In order
     * to verify that a call to <code>setSolo</code>
     * was successful, use <code>getSolo</code>.
     *
     * <p>
     *  设置此通道的独奏状态。如果<code> solo </code>是<code> true </code>,只有此频道和其他独奏频道才会播放。
     * 如果<code> solo </code>是<code> false </code>,那么只有其他独奏的频道才会发出声音,除非没有频道独奏,在这种情况下,所有未静音的频道都会发出声音。
     * 
     *  底层合成器可能不支持独奏频道。为了验证对<code> setSolo </code>的调用是否成功,请使用<code> getSolo </code>。
     * 
     * @param soloState new solo state for the channel
     * @see #getSolo()
     */
    public void setSolo(boolean soloState);

    /**
     * Obtains the current solo state for this channel.
     * If the underlying synthesizer does not support
     * solo on this channel, this method always returns
     * <code>false</code>.
     *
     * <p>
     * 
     * 
     * @return <code>true</code> the channel is solo,
     *         or <code>false</code> if not
     *
     * @see #setSolo(boolean)
     */
    public boolean getSolo();
}
