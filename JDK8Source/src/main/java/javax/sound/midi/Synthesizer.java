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

import javax.sound.sampled.Control;


/**
 * A <code>Synthesizer</code> generates sound.  This usually happens when one of
 * the <code>Synthesizer</code>'s {@link MidiChannel} objects receives a
 * {@link MidiChannel#noteOn(int, int) noteOn} message, either
 * directly or via the <code>Synthesizer</code> object.
 * Many <code>Synthesizer</code>s support <code>Receivers</code>, through which
 * MIDI events can be delivered to the <code>Synthesizer</code>.
 * In such cases, the <code>Synthesizer</code> typically responds by sending
 * a corresponding message to the appropriate <code>MidiChannel</code>, or by
 * processing the event itself if the event isn't one of the MIDI channel
 * messages.
 * <p>
 * The <code>Synthesizer</code> interface includes methods for loading and
 * unloading instruments from soundbanks.  An instrument is a specification for synthesizing a
 * certain type of sound, whether that sound emulates a traditional instrument or is
 * some kind of sound effect or other imaginary sound. A soundbank is a collection of instruments, organized
 * by bank and program number (via the instrument's <code>Patch</code> object).
 * Different <code>Synthesizer</code> classes might implement different sound-synthesis
 * techniques, meaning that some instruments and not others might be compatible with a
 * given synthesizer.
 * Also, synthesizers may have a limited amount of memory for instruments, meaning
 * that not every soundbank and instrument can be used by every synthesizer, even if
 * the synthesis technique is compatible.
 * To see whether the instruments from
 * a certain soundbank can be played by a given synthesizer, invoke the
 * {@link #isSoundbankSupported(Soundbank) isSoundbankSupported} method of
 * <code>Synthesizer</code>.
 * <p>
 * "Loading" an instrument means that that instrument becomes available for
 * synthesizing notes.  The instrument is loaded into the bank and
 * program location specified by its <code>Patch</code> object.  Loading does
 * not necessarily mean that subsequently played notes will immediately have
 * the sound of this newly loaded instrument.  For the instrument to play notes,
 * one of the synthesizer's <code>MidiChannel</code> objects must receive (or have received)
 * a program-change message that causes that particular instrument's
 * bank and program number to be selected.
 *
 * <p>
 *  <code>合成器</code>生成声音。
 * 这通常发生在<code> Synthesizer </code>的{@link MidiChannel}对象之一直接或通过<code> Synthesizer <note>接收到一个{@link MidiChannel#noteOn(int,int)noteOn}
 *  / code>对象。
 *  <code>合成器</code>生成声音。
 * 许多<code> Synthesizer </code>支持<code> Receivers </code>,通过它可以将MIDI事件传递到<code> Synthesizer </code>。
 * 在这种情况下,<code> Synthesizer </code>通常通过将相应的消息发送到适当的<code> MidiChannel </code>来响应,或者如果事件不是MIDI通道消息之一, 。
 * <p>
 * <code> Synthesizer </code>界面包括从声音库中加载和卸载乐器的方法。仪器是用于合成某种类型的声音的规范,无论声音仿真传统乐器还是某种声音效果或其他假想声音。
 * 声音库是由银行和程序编号组织的仪器集合(通过仪器的<code> Patch </code>对象)。
 * 不同的<code> Synthesizer </code>类可能实现不同的声音合成技术,这意味着一些乐器而不是其他乐器可能与给定的合成器兼容。
 * 此外,合成器可以具有用于仪器的有限量的存储器,这意味着并不是每个合成器都可以使用每个声音库和乐器,即使合成技术是兼容的。
 * 要查看某个声音库的乐器是否可以由给定的合成器播放,请调用<code> Synthesizer </code>的{@link #isSoundbankSupported(Soundbank)isSoundbankSupported}
 * 方法。
 * 此外,合成器可以具有用于仪器的有限量的存储器,这意味着并不是每个合成器都可以使用每个声音库和乐器,即使合成技术是兼容的。
 * <p>
 * "加载"仪器意味着该仪器可用于合成音符。仪器加载到由其<code> Patch </code>对象指定的库和程序位置。加载不一定意味着随后播放的音符将立即具有该新加载的乐器的声音。
 * 对于乐器播放音符,合成器的<code> MidiChannel </code>对象必须接收(或已经接收到)一个程序改变消息,该消息使得选择特定乐器的组和程序号。
 * 
 * 
 * @see MidiSystem#getSynthesizer
 * @see Soundbank
 * @see Instrument
 * @see MidiChannel#programChange(int, int)
 * @see Receiver
 * @see Transmitter
 * @see MidiDevice
 *
 * @author Kara Kytle
 */
public interface Synthesizer extends MidiDevice {


    // SYNTHESIZER METHODS


    /**
     * Obtains the maximum number of notes that this synthesizer can sound simultaneously.
     * <p>
     *  获取该合成器可同时发出的最大音符数。
     * 
     * 
     * @return the maximum number of simultaneous notes
     * @see #getVoiceStatus
     */
    public int getMaxPolyphony();


    /**
     * Obtains the processing latency incurred by this synthesizer, expressed in
     * microseconds.  This latency measures the worst-case delay between the
     * time a MIDI message is delivered to the synthesizer and the time that the
     * synthesizer actually produces the corresponding result.
     * <p>
     * Although the latency is expressed in microseconds, a synthesizer's actual measured
     * delay may vary over a wider range than this resolution suggests.  For example,
     * a synthesizer might have a worst-case delay of a few milliseconds or more.
     *
     * <p>
     *  获得由该合成器引起的处理延迟,以微秒表示。此延迟测量MIDI消息传送到合成器的时间与合成器实际产生相应结果的时间之间的最坏情况的延迟。
     * <p>
     *  虽然延迟以微秒表示,但合成器的实际测量延迟可以在比该分辨率建议的更宽的范围上变化。例如,合成器可能具有几毫秒或更多的最坏情况延迟。
     * 
     * 
     * @return the worst-case delay, in microseconds
     */
    public long getLatency();


    /**
     * Obtains the set of MIDI channels controlled by this synthesizer.  Each
     * non-null element in the returned array is a <code>MidiChannel</code> that
     * receives the MIDI messages sent on that channel number.
     * <p>
     * The MIDI 1.0 specification provides for 16 channels, so this
     * method returns an array of at least 16 elements.  However, if this synthesizer
     * doesn't make use of all 16 channels, some of the elements of the array
     * might be <code>null</code>, so you should check each element
     * before using it.
     * <p>
     *  获取由此合成器控制的MIDI通道集。返回数组中的每个非空元素都是一个<code> MidiChannel </code>,用于接收在该通道号上发送的MIDI消息。
     * <p>
     * MIDI 1.0规范提供16个通道,因此此方法返回至少16个元素的数组。
     * 但是,如果这个合成器不使用所有16​​个通道,数组的一些元素可能是<code> null </code>,因此您应该在使用它之前检查每个元素。
     * 
     * 
     * @return an array of the <code>MidiChannel</code> objects managed by this
     * <code>Synthesizer</code>.  Some of the array elements may be <code>null</code>.
     */
    public MidiChannel[] getChannels();


    /**
     * Obtains the current status of the voices produced by this synthesizer.
     * If this class of <code>Synthesizer</code> does not provide voice
     * information, the returned array will always be of length 0.  Otherwise,
     * its length is always equal to the total number of voices, as returned by
     * <code>getMaxPolyphony()</code>.  (See the <code>VoiceStatus</code> class
     * description for an explanation of synthesizer voices.)
     *
     * <p>
     *  获取由此合成器产生的语音的当前状态。
     * 如果这个类<code> Synthesizer </code>不提供语音信息,返回的数组总是长度为0.否则,它的长度总是等于语音的总数,由<code> getMaxPolyphony )</code>。
     *  获取由此合成器产生的语音的当前状态。 (有关合成器语音的解释,请参阅<code> VoiceStatus </code>类描述)。
     * 
     * 
     * @return an array of <code>VoiceStatus</code> objects that supply
     * information about the corresponding synthesizer voices
     * @see #getMaxPolyphony
     * @see VoiceStatus
     */
    public VoiceStatus[] getVoiceStatus();


    /**
     * Informs the caller whether this synthesizer is capable of loading
     * instruments from the specified soundbank.
     * If the soundbank is unsupported, any attempts to load instruments from
     * it will result in an <code>IllegalArgumentException</code>.
     * <p>
     *  通知呼叫者此合成器是否能够从指定的声音库加载乐器。如果声音库不受支持,任何尝试加载仪器将导致一个<code> IllegalArgumentException </code>。
     * 
     * 
     * @param soundbank soundbank for which support is queried
     * @return <code>true</code> if the soundbank is supported, otherwise <code>false</code>
     * @see #loadInstruments
     * @see #loadAllInstruments
     * @see #unloadInstruments
     * @see #unloadAllInstruments
     * @see #getDefaultSoundbank
     */
    public boolean isSoundbankSupported(Soundbank soundbank);


    /**
     * Makes a particular instrument available for synthesis.  This instrument
     * is loaded into the patch location specified by its <code>Patch</code>
     * object, so that if a program-change message is
     * received (or has been received) that causes that patch to be selected,
     * subsequent notes will be played using the sound of
     * <code>instrument</code>.  If the specified instrument is already loaded,
     * this method does nothing and returns <code>true</code>.
     * <p>
     * The instrument must be part of a soundbank
     * that this <code>Synthesizer</code> supports.  (To make sure, you can use
     * the <code>getSoundbank</code> method of <code>Instrument</code> and the
     * <code>isSoundbankSupported</code> method of <code>Synthesizer</code>.)
     * <p>
     *  使特定仪器可用于合成。
     * 此仪器将加载到由其<code> Patch </code>对象指定的补丁位置,以便如果接收到(或已接收到)导致该补丁被选择的程序更改消息,则将播放后续注释使用<code> instrument </code>
     * 的声音。
     *  使特定仪器可用于合成。如果指定的仪器已经加载,此方法不执行任何操作,并返回<code> true </code>。
     * <p>
     * 仪器必须是此<code> Synthesizer </code>支持的声音库的一部分。
     *  (为了确保,您可以使用<code> Instrument </code>的<code> getSoundbank </code>方法和<code> Synthesizer </code>的<code>
     *  isSoundbankSupported </code>。
     * 仪器必须是此<code> Synthesizer </code>支持的声音库的一部分。
     * 
     * 
     * @param instrument instrument to load
     * @return <code>true</code> if the instrument is successfully loaded (or
     * already had been), <code>false</code> if the instrument could not be
     * loaded (for example, if the synthesizer has insufficient
     * memory to load it)
     * @throws IllegalArgumentException if this
     * <code>Synthesizer</code> doesn't support the specified instrument's
     * soundbank
     * @see #unloadInstrument
     * @see #loadInstruments
     * @see #loadAllInstruments
     * @see #remapInstrument
     * @see SoundbankResource#getSoundbank
     * @see MidiChannel#programChange(int, int)
     */
    public boolean loadInstrument(Instrument instrument);


    /**
     * Unloads a particular instrument.
     * <p>
     *  卸载特定仪器。
     * 
     * 
     * @param instrument instrument to unload
     * @throws IllegalArgumentException if this
     * <code>Synthesizer</code> doesn't support the specified instrument's
     * soundbank
     * @see #loadInstrument
     * @see #unloadInstruments
     * @see #unloadAllInstruments
     * @see #getLoadedInstruments
     * @see #remapInstrument
     */
    public void unloadInstrument(Instrument instrument);


    /**
     * Remaps an instrument. Instrument <code>to</code> takes the
     * place of instrument <code>from</code>.<br>
     * For example, if <code>from</code> was located at bank number 2,
     * program number 11, remapping causes that bank and program location
     * to be occupied instead by <code>to</code>.<br>
     * If the function succeeds,  instrument <code>from</code> is unloaded.
     * <p>To cancel the remapping reload instrument <code>from</code> by
     * invoking one of {@link #loadInstrument}, {@link #loadInstruments}
     * or {@link #loadAllInstruments}.
     *
     * <p>
     *  重新连接乐器。仪器<code>到</code>代替仪器<code>从</code>。
     * <br>例如,如果<code>从</code>位于银行号2,程序号11,导致该组和程序位置由<code>代替</code>。<br>如果函数成功,则从</code>中移除<code>的仪器将被卸载。
     *  <p>通过调用{@link #loadInstrument},{@link #loadInstruments}或{@link #loadAllInstruments}之一来取消重新映射重载仪器<code>
     * 从</code>。
     * <br>例如,如果<code>从</code>位于银行号2,程序号11,导致该组和程序位置由<code>代替</code>。<br>如果函数成功,则从</code>中移除<code>的仪器将被卸载。
     * 
     * 
     * @param from the <code>Instrument</code> object to be replaced
     * @param to the <code>Instrument</code> object to be used in place
     * of the old instrument, it should be loaded into the synthesizer
     * @return <code>true</code> if the instrument successfully remapped,
     * <code>false</code> if feature is not implemented by synthesizer
     * @throws IllegalArgumentException if instrument
     * <code>from</code> or instrument <code>to</code> aren't supported by
     * synthesizer or if instrument <code>to</code> is not loaded
     * @throws NullPointerException if <code>from</code> or
     * <code>to</code> parameters have null value
     * @see #loadInstrument
     * @see #loadInstruments
     * @see #loadAllInstruments
     */
    public boolean remapInstrument(Instrument from, Instrument to);


    /**
     * Obtains the default soundbank for the synthesizer, if one exists.
     * (Some synthesizers provide a default or built-in soundbank.)
     * If a synthesizer doesn't have a default soundbank, instruments must
     * be loaded explicitly from an external soundbank.
     * <p>
     *  获取合成器的默认声音库(如果存在)。 (某些合成器提供默认或内置声音库。)如果合成器没有默认声音库,则必须从外部声音库中明确加载乐器。
     * 
     * 
     * @return default soundbank, or <code>null</code> if one does not exist.
     * @see #isSoundbankSupported
     */
    public Soundbank getDefaultSoundbank();


    /**
     * Obtains a list of instruments that come with the synthesizer.  These
     * instruments might be built into the synthesizer, or they might be
     * part of a default soundbank provided with the synthesizer, etc.
     * <p>
     * Note that you don't use this method  to find out which instruments are
     * currently loaded onto the synthesizer; for that purpose, you use
     * <code>getLoadedInstruments()</code>.
     * Nor does the method indicate all the instruments that can be loaded onto
     * the synthesizer; it only indicates the subset that come with the synthesizer.
     * To learn whether another instrument can be loaded, you can invoke
     * <code>isSoundbankSupported()</code>, and if the instrument's
     * <code>Soundbank</code> is supported, you can try loading the instrument.
     *
     * <p>
     *  获取合成器附带的乐器列表。这些仪器可以内置在合成器中,或者它们可以是合成器提供的默认声音库的一部分等。
     * <p>
     * 请注意,您不使用此方法来确定当前加载到合成器上的仪器;为此,您可以使用<code> getLoadedInstruments()</code>。
     * 该方法也不指示可以加载到合成器上的所有仪器;它只表示与合成器一起来的子集。
     * 要了解是否可以加载另一个仪器,您可以调用<code> isSoundbankSupported()</code>,如果支持仪器的<code> Soundbank </code>,您可以尝试加载仪器。
     * 
     * 
     * @return list of available instruments. If the synthesizer
     * has no instruments coming with it, an array of length 0 is returned.
     * @see #getLoadedInstruments
     * @see #isSoundbankSupported(Soundbank)
     * @see #loadInstrument
     */
    public Instrument[] getAvailableInstruments();


    /**
     * Obtains a list of the instruments that are currently loaded onto this
     * <code>Synthesizer</code>.
     * <p>
     *  获取当前加载到此<code> Synthesizer </code>上的仪器的列表。
     * 
     * 
     * @return a list of currently loaded instruments
     * @see #loadInstrument
     * @see #getAvailableInstruments
     * @see Soundbank#getInstruments
     */
    public Instrument[] getLoadedInstruments();


    /**
     * Loads onto the <code>Synthesizer</code> all instruments contained
     * in the specified <code>Soundbank</code>.
     * <p>
     *  将<code> Synthesizer </code>加载到指定的<code> Soundbank </code>中包含的所有乐器。
     * 
     * 
     * @param soundbank the <code>Soundbank</code> whose are instruments are
     * to be loaded
     * @return <code>true</code> if the instruments are all successfully loaded (or
     * already had been), <code>false</code> if any instrument could not be
     * loaded (for example, if the <code>Synthesizer</code> had insufficient memory)
     * @throws IllegalArgumentException if the requested soundbank is
     * incompatible with this synthesizer.
     * @see #isSoundbankSupported
     * @see #loadInstrument
     * @see #loadInstruments
     */
    public boolean loadAllInstruments(Soundbank soundbank);



    /**
     * Unloads all instruments contained in the specified <code>Soundbank</code>.
     * <p>
     *  卸载指定的<code> Soundbank </code>中包含的所有乐器。
     * 
     * 
     * @param soundbank soundbank containing instruments to unload
     * @throws IllegalArgumentException thrown if the soundbank is not supported.
     * @see #isSoundbankSupported
     * @see #unloadInstrument
     * @see #unloadInstruments
     */
    public void unloadAllInstruments(Soundbank soundbank);


    /**
     * Loads the instruments referenced by the specified patches, from the
     * specified <code>Soundbank</code>.  Each of the <code>Patch</code> objects
     * indicates a bank and program number; the <code>Instrument</code> that
     * has the matching <code>Patch</code> is loaded into that bank and program
     * location.
     * <p>
     *  从指定的<code> Soundbank </code>加载指定补丁所引用的仪器。
     * 每个<code> Patch </code>对象表示一个组和程序号;将具有匹配的<code> Patch </code>的<code> Instrument </code>加载到该银行和程序位置。
     * 
     * 
     * @param soundbank the <code>Soundbank</code> containing the instruments to load
     * @param patchList list of patches for which instruments should be loaded
     * @return <code>true</code> if the instruments are all successfully loaded (or
     * already had been), <code>false</code> if any instrument could not be
     * loaded (for example, if the <code>Synthesizer</code> had insufficient memory)
     * @throws IllegalArgumentException thrown if the soundbank is not supported.
     * @see #isSoundbankSupported
     * @see Instrument#getPatch
     * @see #loadAllInstruments
     * @see #loadInstrument
     * @see Soundbank#getInstrument(Patch)
     * @see Sequence#getPatchList()
     */
    public boolean loadInstruments(Soundbank soundbank, Patch[] patchList);

    /**
     * Unloads the instruments referenced by the specified patches, from the MIDI sound bank specified.
     * <p>
     *  从指定的MIDI声音库中卸载由指定的音色引用的乐器。
     * 
     * 
     * @param soundbank soundbank containing instruments to unload
     * @param patchList list of patches for which instruments should be unloaded
     * @throws IllegalArgumentException thrown if the soundbank is not supported.
     *
     * @see #unloadInstrument
     * @see #unloadAllInstruments
     * @see #isSoundbankSupported
     * @see Instrument#getPatch
     * @see #loadInstruments
     */
    public void unloadInstruments(Soundbank soundbank, Patch[] patchList);


    // RECEIVER METHODS

    /**
     * Obtains the name of the receiver.
     * <p>
     *  获取接收器的名称。
     * 
     * 
     * @return receiver name
     */
    //  public abstract String getName();


    /**
     * Opens the receiver.
     * <p>
     *  打开接收器。
     * 
     * 
     * @throws MidiUnavailableException if the receiver is cannot be opened,
     * usually because the MIDI device is in use by another application.
     * @throws SecurityException if the receiver cannot be opened due to security
     * restrictions.
     */
    //  public abstract void open() throws MidiUnavailableException, SecurityException;


    /**
     * Closes the receiver.
     * <p>
     *  关闭接收器。
     * 
     */
    //  public abstract void close();


    /**
     * Sends a MIDI event to the receiver.
     * <p>
     *  向接收器发送MIDI事件。
     * 
     * 
     * @param event event to send.
     * @throws IllegalStateException if the receiver is not open.
     */
    //  public void send(MidiEvent event) throws IllegalStateException {
    //
    //  }


    /**
     * Obtains the set of controls supported by the
     * element.  If no controls are supported, returns an
     * array of length 0.
     * <p>
     *  获取元素支持的控件集。如果不支持控件,则返回长度为0的数组。
     * 
     * 
     * @return set of controls
     */
    // $$kk: 03.04.99: josh bloch recommends getting rid of this:
    // what can you really do with a set of untyped controls??
    // $$kk: 03.05.99: i am putting this back in.  for one thing,
    // you can check the length and know whether you should keep
    // looking....
    // public Control[] getControls();

    /**
     * Obtains the specified control.
     * <p>
     *  获取指定的控件。
     * 
     * @param controlClass class of the requested control
     * @return requested control object, or null if the
     * control is not supported.
     */
    // public Control getControl(Class controlClass);
}
