/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.io.IOException;


/**
 * A hardware or software device that plays back a MIDI
 * <code>{@link Sequence sequence}</code> is known as a <em>sequencer</em>.
 * A MIDI sequence contains lists of time-stamped MIDI data, such as
 * might be read from a standard MIDI file.  Most
 * sequencers also provide functions for creating and editing sequences.
 * <p>
 * The <code>Sequencer</code> interface includes methods for the following
 * basic MIDI sequencer operations:
 * <ul>
 * <li>obtaining a sequence from MIDI file data</li>
 * <li>starting and stopping playback</li>
 * <li>moving to an arbitrary position in the sequence</li>
 * <li>changing the tempo (speed) of playback</li>
 * <li>synchronizing playback to an internal clock or to received MIDI
 * messages</li>
 * <li>controlling the timing of another device</li>
 * </ul>
 * In addition, the following operations are supported, either directly, or
 * indirectly through objects that the <code>Sequencer</code> has access to:
 * <ul>
 * <li>editing the data by adding or deleting individual MIDI events or entire
 * tracks</li>
 * <li>muting or soloing individual tracks in the sequence</li>
 * <li>notifying listener objects about any meta-events or
 * control-change events encountered while playing back the sequence.</li>
 * </ul>
 *
 * <p>
 *  回放MIDI <code> {@ link Sequence sequence} </code>的硬件或软件设备称为<em>音序器</em>。
 *  MIDI序列包含时间标记的MIDI数据的列表,例如可以从标准MIDI文件读取。大多数测序器还提供用于创建和编辑序列的功能。
 * <p>
 *  <code> Sequencer </code>接口包括用于以下基本MIDI序列器操作的方法：
 * <ul>
 *  <li>从MIDI文件数据获取序列</li> <li>开始和停止播放</li> <li>移动到序列中的任意位置</li> <li> </li> <li>将播放同步到内部时钟或接收MIDI消息</li>
 *  <li>控制另一个设备的时间</li>。
 * </ul>
 *  此外,直接或通过<code> Sequencer </code>可访问的对象间接支持以下操作：
 * <ul>
 *  <li>通过添加或删除个别MIDI事件或整个音轨来编辑数据</li> <li>按顺序静音或独奏单个曲目</li> <li>通知监听器对象任何元事件或控件更改播放序列时遇到的事件。</li>
 * </ul>
 * 
 * 
 * @see Sequencer.SyncMode
 * @see #addMetaEventListener
 * @see ControllerEventListener
 * @see Receiver
 * @see Transmitter
 * @see MidiDevice
 *
 * @author Kara Kytle
 * @author Florian Bomers
 */
public interface Sequencer extends MidiDevice {


    /**
     * A value indicating that looping should continue
     * indefinitely rather than complete after a specific
     * number of loops.
     *
     * <p>
     *  指示循环应该在特定数目的循环之后无限期地继续而不是完成的值。
     * 
     * 
     * @see #setLoopCount
     * @since 1.5
     */
    public static final int LOOP_CONTINUOUSLY = -1;



    /**
     * Sets the current sequence on which the sequencer operates.
     *
     * <p>This method can be called even if the
     * <code>Sequencer</code> is closed.
     *
     * <p>
     *  设置顺控程序操作的当前序列。
     * 
     * <p>即使<code> Sequencer </code>关闭,也可以调用此方法。
     * 
     * 
     * @param sequence the sequence to be loaded.
     * @throws InvalidMidiDataException if the sequence contains invalid
     * MIDI data, or is not supported.
     */
    public void setSequence(Sequence sequence) throws InvalidMidiDataException;


    /**
     * Sets the current sequence on which the sequencer operates.
     * The stream must point to MIDI file data.
     *
     * <p>This method can be called even if the
     * <code>Sequencer</code> is closed.
     *
     * <p>
     *  设置顺控程序操作的当前序列。流必须指向MIDI文件数据。
     * 
     *  <p>即使<code> Sequencer </code>关闭,也可以调用此方法。
     * 
     * 
     * @param stream stream containing MIDI file data.
     * @throws IOException if an I/O exception occurs during reading of the stream.
     * @throws InvalidMidiDataException if invalid data is encountered
     * in the stream, or the stream is not supported.
     */
    public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException;


    /**
     * Obtains the sequence on which the Sequencer is currently operating.
     *
     * <p>This method can be called even if the
     * <code>Sequencer</code> is closed.
     *
     * <p>
     *  获取序列器当前正在操作的序列。
     * 
     *  <p>即使<code> Sequencer </code>关闭,也可以调用此方法。
     * 
     * 
     * @return the current sequence, or <code>null</code> if no sequence is currently set.
     */
    public Sequence getSequence();


    /**
     * Starts playback of the MIDI data in the currently
     * loaded sequence.
     * Playback will begin from the current position.
     * If the playback position reaches the loop end point,
     * and the loop count is greater than 0, playback will
     * resume at the loop start point for the number of
     * repetitions set with <code>setLoopCount</code>.
     * After that, or if the loop count is 0, playback will
     * continue to play to the end of the sequence.
     *
     * <p>The implementation ensures that the synthesizer
     * is brought to a consistent state when jumping
     * to the loop start point by sending appropriate
     * controllers, pitch bend, and program change events.
     *
     * <p>
     *  以当前加载的序列开始播放MIDI数据。播放将从当前位置开始。
     * 如果播放位置到达循环结束点,并且循环计数大于0,则将在循环开始点处重新开始使用<code> setLoopCount </code>设置的重复次数。
     * 之后,或者如果循环计数为0,播放将继续播放到序列的结尾。
     * 
     *  <p>该实现确保当通过发送适当的控制器,音高弯曲和程序改变事件跳转到循环开始点时,使合成器处于一致状态。
     * 
     * 
     * @throws IllegalStateException if the <code>Sequencer</code> is
     * closed.
     *
     * @see #setLoopStartPoint
     * @see #setLoopEndPoint
     * @see #setLoopCount
     * @see #stop
     */
    public void start();


    /**
     * Stops recording, if active, and playback of the currently loaded sequence,
     * if any.
     *
     * <p>
     *  停止记录(如果活动),并回放当前加载的序列(如果有)。
     * 
     * 
     * @throws IllegalStateException if the <code>Sequencer</code> is
     * closed.
     *
     * @see #start
     * @see #isRunning
     */
    public void stop();


    /**
     * Indicates whether the Sequencer is currently running.  The default is <code>false</code>.
     * The Sequencer starts running when either <code>{@link #start}</code> or <code>{@link #startRecording}</code>
     * is called.  <code>isRunning</code> then returns <code>true</code> until playback of the
     * sequence completes or <code>{@link #stop}</code> is called.
     * <p>
     *  指示序列生成器当前是否正在运行。默认值为<code> false </code>。
     * 当调用<code> {@ link #start} </code>或<code> {@ link #startRecording} </code>时,序列器开始运行。
     *  <code> isRunning </code>然后返回<code> true </code>,直到序列的播放完成或调用<code> {@ link #stop} </code>。
     * 
     * 
     * @return <code>true</code> if the Sequencer is running, otherwise <code>false</code>
     */
    public boolean isRunning();


    /**
     * Starts recording and playback of MIDI data.  Data is recorded to all enabled tracks,
     * on the channel(s) for which they were enabled.  Recording begins at the current position
     * of the sequencer.   Any events already in the track are overwritten for the duration
     * of the recording session.  Events from the currently loaded sequence,
     * if any, are delivered to the sequencer's transmitter(s) along with messages
     * received during recording.
     * <p>
     * Note that tracks are not by default enabled for recording.  In order to record MIDI data,
     * at least one track must be specifically enabled for recording.
     *
     * <p>
     * 开始录制和播放MIDI数据。数据被记录到所有已启用的轨道,在启用它们的通道上。记录从序列发生器的当前位置开始。已在轨道中的任何事件在记录会话的持续时间内被覆盖。
     * 来自当前加载的序列的事件(如果有)将与记录期间接收的消息一起传送到定序器的发射器。
     * <p>
     *  请注意,默认情况下不启用轨道进行录制。为了记录MIDI数据,至少必须特别启用一个磁道进行记录。
     * 
     * 
     * @throws IllegalStateException if the <code>Sequencer</code> is
     * closed.
     *
     * @see #startRecording
     * @see #recordEnable
     * @see #recordDisable
     */
    public void startRecording();


    /**
     * Stops recording, if active.  Playback of the current sequence continues.
     *
     * <p>
     *  如果活动,停止录制。继续播放当前序列。
     * 
     * 
     * @throws IllegalStateException if the <code>Sequencer</code> is
     * closed.
     *
     * @see #startRecording
     * @see #isRecording
     */
    public void stopRecording();


    /**
     * Indicates whether the Sequencer is currently recording.  The default is <code>false</code>.
     * The Sequencer begins recording when <code>{@link #startRecording}</code> is called,
     * and then returns <code>true</code> until <code>{@link #stop}</code> or <code>{@link #stopRecording}</code>
     * is called.
     * <p>
     *  指示序列发生器当前是否正在记录。默认值为<code> false </code>。
     * 调用<code> {@ link #startRecording} </code>时,序列器开始记录,然后返回<code> true </code>,直到<code> {@ link #stop} </code>
     *  {@link #stopRecording} </code>被调用。
     *  指示序列发生器当前是否正在记录。默认值为<code> false </code>。
     * 
     * 
     * @return <code>true</code> if the Sequencer is recording, otherwise <code>false</code>
     */
    public boolean isRecording();


    /**
     * Prepares the specified track for recording events received on a particular channel.
     * Once enabled, a track will receive events when recording is active.
     * <p>
     *  准备指定的轨道以记录在特定通道上接收的事件。启用后,轨道将在录制活动时接收事件。
     * 
     * 
     * @param track the track to which events will be recorded
     * @param channel the channel on which events will be received.  If -1 is specified
     * for the channel value, the track will receive data from all channels.
     * @throws IllegalArgumentException thrown if the track is not part of the current
     * sequence.
     */
    public void recordEnable(Track track, int channel);


    /**
     * Disables recording to the specified track.  Events will no longer be recorded
     * into this track.
     * <p>
     *  禁用录制到指定的轨道。活动将不再记录到此曲目中。
     * 
     * 
     * @param track the track to disable for recording, or <code>null</code> to disable
     * recording for all tracks.
     */
    public void recordDisable(Track track);


    /**
     * Obtains the current tempo, expressed in beats per minute.  The
     * actual tempo of playback is the product of the returned value
     * and the tempo factor.
     *
     * <p>
     *  获取当前速度,以每分钟的节拍表示。播放的实际速度是返回值和速度因子的乘积。
     * 
     * 
     * @return the current tempo in beats per minute
     *
     * @see #getTempoFactor
     * @see #setTempoInBPM(float)
     * @see #getTempoInMPQ
     */
    public float getTempoInBPM();


    /**
     * Sets the tempo in beats per minute.   The actual tempo of playback
     * is the product of the specified value and the tempo factor.
     *
     * <p>
     *  以每分钟的拍数设置速度。播放的实际速度是指定值和速度因子的乘积。
     * 
     * 
     * @param bpm desired new tempo in beats per minute
     * @see #getTempoFactor
     * @see #setTempoInMPQ(float)
     * @see #getTempoInBPM
     */
    public void setTempoInBPM(float bpm);


    /**
     * Obtains the current tempo, expressed in microseconds per quarter
     * note.  The actual tempo of playback is the product of the returned
     * value and the tempo factor.
     *
     * <p>
     * 获取当前速度,以每四分音符的微秒表示。播放的实际速度是返回值和速度因子的乘积。
     * 
     * 
     * @return the current tempo in microseconds per quarter note
     * @see #getTempoFactor
     * @see #setTempoInMPQ(float)
     * @see #getTempoInBPM
     */
    public float getTempoInMPQ();


    /**
     * Sets the tempo in microseconds per quarter note.  The actual tempo
     * of playback is the product of the specified value and the tempo
     * factor.
     *
     * <p>
     *  以每四分音符的微秒为单位设置速度。播放的实际速度是指定值和速度因子的乘积。
     * 
     * 
     * @param mpq desired new tempo in microseconds per quarter note.
     * @see #getTempoFactor
     * @see #setTempoInBPM(float)
     * @see #getTempoInMPQ
     */
    public void setTempoInMPQ(float mpq);


    /**
     * Scales the sequencer's actual playback tempo by the factor provided.
     * The default is 1.0.  A value of 1.0 represents the natural rate (the
     * tempo specified in the sequence), 2.0 means twice as fast, etc.
     * The tempo factor does not affect the values returned by
     * <code>{@link #getTempoInMPQ}</code> and <code>{@link #getTempoInBPM}</code>.
     * Those values indicate the tempo prior to scaling.
     * <p>
     * Note that the tempo factor cannot be adjusted when external
     * synchronization is used.  In that situation,
     * <code>setTempoFactor</code> always sets the tempo factor to 1.0.
     *
     * <p>
     *  按提供的系数缩放音序器的实际播放速度。默认值为1.0。值1.0表示自然率(在序列中指定的速度),2.0表示快两倍等。
     * 速度因子不影响<code> {@ link #getTempoInMPQ} </code>和<代码> {@ link #getTempoInBPM} </code>。这些值表示缩放之前的速度。
     * <p>
     *  请注意,使用外部同步时无法调整速度系数。在这种情况下,<code> setTempoFactor </code>始终将速度因子设置为1.0。
     * 
     * 
     * @param factor the requested tempo scalar
     * @see #getTempoFactor
     */
    public void setTempoFactor(float factor);


    /**
     * Returns the current tempo factor for the sequencer.  The default is
     * 1.0.
     *
     * <p>
     *  返回音序器的当前速度因子。默认值为1.0。
     * 
     * 
     * @return tempo factor.
     * @see #setTempoFactor(float)
     */
    public float getTempoFactor();


    /**
     * Obtains the length of the current sequence, expressed in MIDI ticks,
     * or 0 if no sequence is set.
     * <p>
     *  获取当前序列的长度,以MIDI刻度表示,如果没有设置序列,则为0。
     * 
     * 
     * @return length of the sequence in ticks
     */
    public long getTickLength();


    /**
     * Obtains the current position in the sequence, expressed in MIDI
     * ticks.  (The duration of a tick in seconds is determined both by
     * the tempo and by the timing resolution stored in the
     * <code>{@link Sequence}</code>.)
     *
     * <p>
     *  获取序列中的当前位置,以MIDI记号表示。 (以秒为单位的节拍持续时间由节奏和存储在<code> {@ link Sequence} </code>中的时间分辨率确定。)
     * 
     * 
     * @return current tick
     * @see #setTickPosition
     */
    public long getTickPosition();


    /**
     * Sets the current sequencer position in MIDI ticks
     * <p>
     *  在MIDI节拍中设置当前的音序器位置
     * 
     * 
     * @param tick the desired tick position
     * @see #getTickPosition
     */
    public void setTickPosition(long tick);


    /**
     * Obtains the length of the current sequence, expressed in microseconds,
     * or 0 if no sequence is set.
     * <p>
     *  获取当前序列的长度(以微秒表示),如果没有设置序列,则为0。
     * 
     * 
     * @return length of the sequence in microseconds.
     */
    public long getMicrosecondLength();


    /**
     * Obtains the current position in the sequence, expressed in
     * microseconds.
     * <p>
     *  获取序列中的当前位置,单位为微秒。
     * 
     * 
     * @return the current position in microseconds
     * @see #setMicrosecondPosition
     */
    public long getMicrosecondPosition();


    /**
     * Sets the current position in the sequence, expressed in microseconds
     * <p>
     * 设置序列中的当前位置,单位为微秒
     * 
     * 
     * @param microseconds desired position in microseconds
     * @see #getMicrosecondPosition
     */
    public void setMicrosecondPosition(long microseconds);


    /**
     * Sets the source of timing information used by this sequencer.
     * The sequencer synchronizes to the master, which is the internal clock,
     * MIDI clock, or MIDI time code, depending on the value of
     * <code>sync</code>.  The <code>sync</code> argument must be one
     * of the supported modes, as returned by
     * <code>{@link #getMasterSyncModes}</code>.
     *
     * <p>
     *  设置此序列器使用的定时信息的来源。根据<code> sync </code>的值,定序器与主机同步,这是内部时钟,MIDI时钟或MIDI时间码。
     *  <code> sync </code>参数必须是受支持的模式之一,由<code> {@ link #getMasterSyncModes} </code>返回。
     * 
     * 
     * @param sync the desired master synchronization mode
     *
     * @see SyncMode#INTERNAL_CLOCK
     * @see SyncMode#MIDI_SYNC
     * @see SyncMode#MIDI_TIME_CODE
     * @see #getMasterSyncMode
     */
    public void setMasterSyncMode(SyncMode sync);


    /**
     * Obtains the current master synchronization mode for this sequencer.
     *
     * <p>
     *  获取此顺控程序的当前主站同步模式。
     * 
     * 
     * @return the current master synchronization mode
     *
     * @see #setMasterSyncMode(Sequencer.SyncMode)
     * @see #getMasterSyncModes
     */
    public SyncMode getMasterSyncMode();


    /**
     * Obtains the set of master synchronization modes supported by this
     * sequencer.
     *
     * <p>
     *  获取此序列器支持的主同步模式集。
     * 
     * 
     * @return the available master synchronization modes
     *
     * @see SyncMode#INTERNAL_CLOCK
     * @see SyncMode#MIDI_SYNC
     * @see SyncMode#MIDI_TIME_CODE
     * @see #getMasterSyncMode
     * @see #setMasterSyncMode(Sequencer.SyncMode)
     */
    public SyncMode[] getMasterSyncModes();


    /**
     * Sets the slave synchronization mode for the sequencer.
     * This indicates the type of timing information sent by the sequencer
     * to its receiver.  The <code>sync</code> argument must be one
     * of the supported modes, as returned by
     * <code>{@link #getSlaveSyncModes}</code>.
     *
     * <p>
     *  设置顺控程序的从站同步模式。这指示定序器发送到其接收器的定时信息的类型。
     *  <code> sync </code>参数必须是<code> {@ link #getSlaveSyncModes} </code>返回的支持模式之一。
     * 
     * 
     * @param sync the desired slave synchronization mode
     *
     * @see SyncMode#MIDI_SYNC
     * @see SyncMode#MIDI_TIME_CODE
     * @see SyncMode#NO_SYNC
     * @see #getSlaveSyncModes
     */
    public void setSlaveSyncMode(SyncMode sync);


    /**
     * Obtains the current slave synchronization mode for this sequencer.
     *
     * <p>
     *  获取此顺控程序的当前从同步模式。
     * 
     * 
     * @return the current slave synchronization mode
     *
     * @see #setSlaveSyncMode(Sequencer.SyncMode)
     * @see #getSlaveSyncModes
     */
    public SyncMode getSlaveSyncMode();


    /**
     * Obtains the set of slave synchronization modes supported by the sequencer.
     *
     * <p>
     *  获取定序器支持的从站同步模式集。
     * 
     * 
     * @return the available slave synchronization modes
     *
     * @see SyncMode#MIDI_SYNC
     * @see SyncMode#MIDI_TIME_CODE
     * @see SyncMode#NO_SYNC
     */
    public SyncMode[] getSlaveSyncModes();


    /**
     * Sets the mute state for a track.  This method may fail for a number
     * of reasons.  For example, the track number specified may not be valid
     * for the current sequence, or the sequencer may not support this functionality.
     * An application which needs to verify whether this operation succeeded should
     * follow this call with a call to <code>{@link #getTrackMute}</code>.
     *
     * <p>
     *  设置曲目的静音状态。此方法可能会失败的原因有很多。例如,指定的轨道号对当前序列可能不有效,或者序列器可能不支持此功能。
     * 需要验证此操作是否成功的应用程序应在此调用之后调用<code> {@ link #getTrackMute} </code>。
     * 
     * 
     * @param track the track number.  Tracks in the current sequence are numbered
     * from 0 to the number of tracks in the sequence minus 1.
     * @param mute the new mute state for the track.  <code>true</code> implies the
     * track should be muted, <code>false</code> implies the track should be unmuted.
     * @see #getSequence
     */
    public void setTrackMute(int track, boolean mute);


    /**
     * Obtains the current mute state for a track.  The default mute
     * state for all tracks which have not been muted is false.  In any
     * case where the specified track has not been muted, this method should
     * return false.  This applies if the sequencer does not support muting
     * of tracks, and if the specified track index is not valid.
     *
     * <p>
     * 获取轨道的当前静音状态。所有尚未静音的音轨的默认静音状态为false。在指定轨道未静音的任何情况下,此方法应返回false。如果音序器不支持音轨静音,并且指定的音轨索引无效,则适用。
     * 
     * 
     * @param track the track number.  Tracks in the current sequence are numbered
     * from 0 to the number of tracks in the sequence minus 1.
     * @return <code>true</code> if muted, <code>false</code> if not.
     */
    public boolean getTrackMute(int track);

    /**
     * Sets the solo state for a track.  If <code>solo</code> is <code>true</code>
     * only this track and other solo'd tracks will sound. If <code>solo</code>
     * is <code>false</code> then only other solo'd tracks will sound, unless no
     * tracks are solo'd in which case all un-muted tracks will sound.
     * <p>
     * This method may fail for a number
     * of reasons.  For example, the track number specified may not be valid
     * for the current sequence, or the sequencer may not support this functionality.
     * An application which needs to verify whether this operation succeeded should
     * follow this call with a call to <code>{@link #getTrackSolo}</code>.
     *
     * <p>
     *  设置轨道的独奏状态。如果<code> solo </code>是<code> true </code>,只有这个轨道和其他独奏的轨道会发出声音。
     * 如果<code> solo </code>是<code> false </code>,那么只有其他单独的轨道将会发出声音,除非没有轨道独奏,在这种情况下所有未静音的轨道都会发出声音。
     * <p>
     *  此方法可能会失败的原因有很多。例如,指定的轨道号对当前序列可能不有效,或者序列器可能不支持此功能。
     * 需要验证此操作是否成功的应用程序应在此调用之后调用<code> {@ link #getTrackSolo} </code>。
     * 
     * 
     * @param track the track number.  Tracks in the current sequence are numbered
     * from 0 to the number of tracks in the sequence minus 1.
     * @param solo the new solo state for the track.  <code>true</code> implies the
     * track should be solo'd, <code>false</code> implies the track should not be solo'd.
     * @see #getSequence
     */
    public void setTrackSolo(int track, boolean solo);


    /**
     * Obtains the current solo state for a track.  The default mute
     * state for all tracks which have not been solo'd is false.  In any
     * case where the specified track has not been solo'd, this method should
     * return false.  This applies if the sequencer does not support soloing
     * of tracks, and if the specified track index is not valid.
     *
     * <p>
     *  获取轨道的当前独奏状态。所有未独奏的音轨的默认静音状态为假。在任何情况下,指定的轨道没有solo'd,这个方法应该返回false。如果定序器不支持曲目的独奏,并且如果指定的曲目索引无效,则适用。
     * 
     * 
     * @param track the track number.  Tracks in the current sequence are numbered
     * from 0 to the number of tracks in the sequence minus 1.
     * @return <code>true</code> if solo'd, <code>false</code> if not.
     */
    public boolean getTrackSolo(int track);


    /**
     * Registers a meta-event listener to receive
     * notification whenever a meta-event is encountered in the sequence
     * and processed by the sequencer. This method can fail if, for
     * instance,this class of sequencer does not support meta-event
     * notification.
     *
     * <p>
     * 注册元事件侦听器以在序列中遇到元事件并由序列器处理时接收通知。例如,如果此类定序器不支持元事件通知,则此方法可能失败。
     * 
     * 
     * @param listener listener to add
     * @return <code>true</code> if the listener was successfully added,
     * otherwise <code>false</code>
     *
     * @see #removeMetaEventListener
     * @see MetaEventListener
     * @see MetaMessage
     */
    public boolean addMetaEventListener(MetaEventListener listener);


    /**
     * Removes the specified meta-event listener from this sequencer's
     * list of registered listeners, if in fact the listener is registered.
     *
     * <p>
     *  从该顺控程序的注册侦听器列表中删除指定的元事件侦听器,如果事实上侦听器已注册。
     * 
     * 
     * @param listener the meta-event listener to remove
     * @see #addMetaEventListener
     */
    public void removeMetaEventListener(MetaEventListener listener);


    /**
     * Registers a controller event listener to receive notification
     * whenever the sequencer processes a control-change event of the
     * requested type or types.  The types are specified by the
     * <code>controllers</code> argument, which should contain an array of
     * MIDI controller numbers.  (Each number should be between 0 and 127,
     * inclusive.  See the MIDI 1.0 Specification for the numbers that
     * correspond to various types of controllers.)
     * <p>
     * The returned array contains the MIDI controller
     * numbers for which the listener will now receive events.
     * Some sequencers might not support controller event notification, in
     * which case the array has a length of 0.  Other sequencers might
     * support notification for some controllers but not all.
     * This method may be invoked repeatedly.
     * Each time, the returned array indicates all the controllers
     * that the listener will be notified about, not only the controllers
     * requested in that particular invocation.
     *
     * <p>
     *  注册控制器事件侦听器,以便在顺控程序处理所请求类型的控制更改事件时接收通知。类型由<code> controllers </code>参数指定,它应该包含一个MIDI控制器数字数组。
     *  (每个数字应介于0和127之间,包括1和1)。有关与各种类型控制器相对应的数字,请参阅MIDI 1.0规范。
     * <p>
     *  返回的数组包含MIDI控制器号,监听器现在将接收事件。某些定序器可能不支持控制器事件通知,在这种情况下,数组的长度为0.其他定序器可能支持某些控制器的通知,但不是全部。可以重复调用此方法。
     * 每次,返回的数组指示将通知监听器的所有控制器,而不仅仅是在特定调用中请求的控制器。
     * 
     * 
     * @param listener the controller event listener to add to the list of
     * registered listeners
     * @param controllers the MIDI controller numbers for which change
     * notification is requested
     * @return the numbers of all the MIDI controllers whose changes will
     * now be reported to the specified listener
     *
     * @see #removeControllerEventListener
     * @see ControllerEventListener
     */
    public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers);


    /**
     * Removes a controller event listener's interest in one or more
     * types of controller event. The <code>controllers</code> argument
     * is an array of MIDI numbers corresponding to the  controllers for
     * which the listener should no longer receive change notifications.
     * To completely remove this listener from the list of registered
     * listeners, pass in <code>null</code> for <code>controllers</code>.
     * The returned array contains the MIDI controller
     * numbers for which the listener will now receive events.  The
     * array has a length of 0 if the listener will not receive
     * change notifications for any controllers.
     *
     * <p>
     * 删除控制器事件侦听器对一种或多种类型的控制器事件的​​兴趣。 <code> controllers </code>参数是一个MIDI数字数组,对应于控制器,监听器不应该再接收到更改通知。
     * 要从注册的侦听器列表中完全删除此侦听器,请传入<code> controllers </code>的<code> null </code>。返回的数组包含MIDI控制器号,监听器现在将接收事件。
     * 如果侦听器不会接收任何控制器的更改通知,则该数组的长度为0。
     * 
     * 
     * @param listener old listener
     * @param controllers the MIDI controller numbers for which change
     * notification should be cancelled, or <code>null</code> to cancel
     * for all controllers
     * @return the numbers of all the MIDI controllers whose changes will
     * now be reported to the specified listener
     *
     * @see #addControllerEventListener
     */
    public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers);


    /**
     * Sets the first MIDI tick that will be
     * played in the loop. If the loop count is
     * greater than 0, playback will jump to this
     * point when reaching the loop end point.
     *
     * <p>A value of 0 for the starting point means the
     * beginning of the loaded sequence. The starting
     * point must be lower than or equal to the ending
     * point, and it must fall within the size of the
     * loaded sequence.
     *
     * <p>A sequencer's loop start point defaults to
     * start of the sequence.
     *
     * <p>
     *  设置将在循环中播放的第一个MIDI节拍。如果循环计数大于0,则当到达循环结束点时,播放将跳转到此点。
     * 
     *  <p>起始点的值0表示加载序列的开始。起点必须小于或等于终点,并且它必须在加载序列的大小之内。
     * 
     *  <p>序列器的循环开始点默认为序列的开始。
     * 
     * 
     * @param tick the loop's starting position,
     *        in MIDI ticks (zero-based)
     * @throws IllegalArgumentException if the requested
     *         loop start point cannot be set, usually because
     *         it falls outside the sequence's
     *         duration or because the start point is
     *         after the end point
     *
     * @see #setLoopEndPoint
     * @see #setLoopCount
     * @see #getLoopStartPoint
     * @see #start
     * @since 1.5
     */
    public void setLoopStartPoint(long tick);


    /**
     * Obtains the start position of the loop,
     * in MIDI ticks.
     *
     * <p>
     *  在MIDI节拍中获取循环的开始位置。
     * 
     * 
     * @return the start position of the loop,
               in MIDI ticks (zero-based)
     * @see #setLoopStartPoint
     * @since 1.5
     */
    public long getLoopStartPoint();


    /**
     * Sets the last MIDI tick that will be played in
     * the loop. If the loop count is 0, the loop end
     * point has no effect and playback continues to
     * play when reaching the loop end point.
     *
     * <p>A value of -1 for the ending point
     * indicates the last tick of the sequence.
     * Otherwise, the ending point must be greater
     * than or equal to the starting point, and it must
     * fall within the size of the loaded sequence.
     *
     * <p>A sequencer's loop end point defaults to -1,
     * meaning the end of the sequence.
     *
     * <p>
     *  设置在循环中播放的最后一个MIDI节拍。如果循环计数为0,循环结束点不起作用,当到达循环结束点时,播放继续播放。
     * 
     *  <p>结束点的值-1表示序列的最后一个tick。否则,结束点必须大于或等于起点,并且它必须在加载序列的大小之内。
     * 
     * <p>序列器的循环结束点默认为-1,表示序列的结尾。
     * 
     * 
     * @param tick the loop's ending position,
     *        in MIDI ticks (zero-based), or
     *        -1 to indicate the final tick
     * @throws IllegalArgumentException if the requested
     *         loop point cannot be set, usually because
     *         it falls outside the sequence's
     *         duration or because the ending point is
     *         before the starting point
     *
     * @see #setLoopStartPoint
     * @see #setLoopCount
     * @see #getLoopEndPoint
     * @see #start
     * @since 1.5
     */
    public void setLoopEndPoint(long tick);


    /**
     * Obtains the end position of the loop,
     * in MIDI ticks.
     *
     * <p>
     *  在MIDI节拍中获取循环的结束位置。
     * 
     * 
     * @return the end position of the loop, in MIDI
     *         ticks (zero-based), or -1 to indicate
     *         the end of the sequence
     * @see #setLoopEndPoint
     * @since 1.5
     */
    public long getLoopEndPoint();


    /**
     * Sets the number of repetitions of the loop for
     * playback.
     * When the playback position reaches the loop end point,
     * it will loop back to the loop start point
     * <code>count</code> times, after which playback will
     * continue to play to the end of the sequence.
     * <p>
     * If the current position when this method is invoked
     * is greater than the loop end point, playback
     * continues to the end of the sequence without looping,
     * unless the loop end point is changed subsequently.
     * <p>
     * A <code>count</code> value of 0 disables looping:
     * playback will continue at the loop end point, and it
     * will not loop back to the loop start point.
     * This is a sequencer's default.
     *
     * <p>If playback is stopped during looping, the
     * current loop status is cleared; subsequent start
     * requests are not affected by an interrupted loop
     * operation.
     *
     * <p>
     *  设置播放循环的重复次数。当播放位置到达循环结束点时,它将循环回到循环开始点<code> count </code>次,之后播放将继续播放到序列结束。
     * <p>
     *  如果调用此方法时的当前位置大于循环结束点,则播放继续到序列的结尾而不循环,除非循环结束点随后更改。
     * <p>
     *  <code> count </code>值为0将禁用循环：播放将在循环结束点继续,并且不会循环回循环开始点。这是顺控程序的默认值。
     * 
     *  <p>如果在循环期间播放停止,当前循环状态将被清除;后续的启动请求不受中断循环操作的影响。
     * 
     * 
     * @param count the number of times playback should
     *        loop back from the loop's end position
     *        to the loop's start position, or
     *        <code>{@link #LOOP_CONTINUOUSLY}</code>
     *        to indicate that looping should
     *        continue until interrupted
     *
     * @throws IllegalArgumentException if <code>count</code> is
     * negative and not equal to {@link #LOOP_CONTINUOUSLY}
     *
     * @see #setLoopStartPoint
     * @see #setLoopEndPoint
     * @see #getLoopCount
     * @see #start
     * @since 1.5
     */
    public void setLoopCount(int count);


    /**
     * Obtains the number of repetitions for
     * playback.
     *
     * <p>
     *  获取播放的重复次数。
     * 
     * 
     * @return the number of loops after which
     *         playback plays to the end of the
     *         sequence
     * @see #setLoopCount
     * @see #start
     * @since 1.5
     */
    public int getLoopCount();

    /**
     * A <code>SyncMode</code> object represents one of the ways in which
     * a MIDI sequencer's notion of time can be synchronized with a master
     * or slave device.
     * If the sequencer is being synchronized to a master, the
     * sequencer revises its current time in response to messages from
     * the master.  If the sequencer has a slave, the sequencer
     * similarly sends messages to control the slave's timing.
     * <p>
     * There are three predefined modes that specify possible masters
     * for a sequencer: <code>INTERNAL_CLOCK</code>,
     * <code>MIDI_SYNC</code>, and <code>MIDI_TIME_CODE</code>.  The
     * latter two work if the sequencer receives MIDI messages from
     * another device.  In these two modes, the sequencer's time gets reset
     * based on system real-time timing clock messages or MIDI time code
     * (MTC) messages, respectively.  These two modes can also be used
     * as slave modes, in which case the sequencer sends the corresponding
     * types of MIDI messages to its receiver (whether or not the sequencer
     * is also receiving them from a master).  A fourth mode,
     * <code>NO_SYNC</code>, is used to indicate that the sequencer should
     * not control its receiver's timing.
     *
     * <p>
     *  <code> SyncMode </code>对象表示MIDI顺控程序的时间概念可以与主设备或从设备同步的方式之一。如果顺控程序正在与主站同步,则序列发生器将根据来自主站的消息修改其当前时间。
     * 如果定序器有从机,定序器类似地发送消息以控制从机的定时。
     * <p>
     * 有三种预定义模式,指定顺控程序可能的主机：<code> INTERNAL_CLOCK </code>,<code> MIDI_SYNC </code>和<code> MIDI_TIME_CODE </code>
     * 。
     * 如果定序器从另一个设备接收MIDI消息,后两个工作。在这两种模式下,定序器的时间分别基于系统实时定时时钟消息或MIDI时间码(MTC)消息进行复位。
     * 这两种模式也可以用作从模式,在这种情况下,定序器将相应类型的MIDI消息发送到其接收器(无论定序器是否也从主器件接收它们)。
     * 第四模式,<code> NO_SYNC </code>,用于指示定序器不应该控制其接收器的定时。
     * 
     * 
     * @see Sequencer#setMasterSyncMode(Sequencer.SyncMode)
     * @see Sequencer#setSlaveSyncMode(Sequencer.SyncMode)
     */
    public static class SyncMode {

        /**
         * Synchronization mode name.
         * <p>
         *  同步模式名称。
         * 
         */
        private String name;

        /**
         * Constructs a synchronization mode.
         * <p>
         *  构造同步模式。
         * 
         * 
         * @param name name of the synchronization mode
         */
        protected SyncMode(String name) {

            this.name = name;
        }


        /**
         * Determines whether two objects are equal.
         * Returns <code>true</code> if the objects are identical
         * <p>
         *  确定两个对象是否相等。如果对象相同,则返回<code> true </code>
         * 
         * 
         * @param obj the reference object with which to compare
         * @return <code>true</code> if this object is the same as the
         * <code>obj</code> argument, <code>false</code> otherwise
         */
        public final boolean equals(Object obj) {

            return super.equals(obj);
        }


        /**
         * Finalizes the hashcode method.
         * <p>
         *  完成哈希码方法。
         * 
         */
        public final int hashCode() {

            return super.hashCode();
        }


        /**
         * Provides this synchronization mode's name as the string
         * representation of the mode.
         * <p>
         *  将此同步模式的名称提供为模式的字符串表示形式。
         * 
         * 
         * @return the name of this synchronization mode
         */
        public final String toString() {

            return name;
        }


        /**
         * A master synchronization mode that makes the sequencer get
         * its timing information from its internal clock.  This is not
         * a legal slave sync mode.
         * <p>
         *  主同步模式,使定序器从其内部时钟获取其定时信息。这不是一个合法的从同步模式。
         * 
         */
        public static final SyncMode INTERNAL_CLOCK             = new SyncMode("Internal Clock");


        /**
         * A master or slave synchronization mode that specifies the
         * use of MIDI clock
         * messages.  If this mode is used as the master sync mode,
         * the sequencer gets its timing information from system real-time
         * MIDI clock messages.  This mode only applies as the master sync
         * mode for sequencers that are also MIDI receivers.  If this is the
         * slave sync mode, the sequencer sends system real-time MIDI clock
         * messages to its receiver.  MIDI clock messages are sent at a rate
         * of 24 per quarter note.
         * <p>
         * 主或从同步模式,指定使用MIDI时钟消息。如果此模式用作主同步模式,则序列器从系统实时MIDI时钟消息获取其定时信息。此模式仅适用于也是MIDI接收器的定序器的主同步模式。
         * 如果这是从同步模式,定序器发送系统实时MIDI时钟消息到其接收器。 MIDI时钟消息以每四分音符24的速率发送。
         * 
         */
        public static final SyncMode MIDI_SYNC                  = new SyncMode("MIDI Sync");


        /**
         * A master or slave synchronization mode that specifies the
         * use of MIDI Time Code.
         * If this mode is used as the master sync mode,
         * the sequencer gets its timing information from MIDI Time Code
         * messages.  This mode only applies as the master sync
         * mode to sequencers that are also MIDI receivers.  If this
         * mode is used as the
         * slave sync mode, the sequencer sends MIDI Time Code
         * messages to its receiver.  (See the MIDI 1.0 Detailed
         * Specification for a description of MIDI Time Code.)
         * <p>
         *  主或从同步模式,指定使用MIDI时间码。如果此模式用作主同步模式,则序列器从MIDI时间码消息获取其定时信息。此模式仅作为主同步模式应用于也是MIDI接收器的音序器。
         * 如果此模式用作从同步模式,序列发生器将MIDI时间码消息发送到其接收器。 (有关MIDI时间码的说明,请参见"MIDI 1.0详细规格"。)。
         * 
         */
        public static final SyncMode MIDI_TIME_CODE             = new SyncMode("MIDI Time Code");


        /**
         * A slave synchronization mode indicating that no timing information
         * should be sent to the receiver.  This is not a legal master sync
         * mode.
         * <p>
         */
        public static final SyncMode NO_SYNC                            = new SyncMode("No Timing");

    } // class SyncMode
}
