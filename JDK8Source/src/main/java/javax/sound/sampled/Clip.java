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

package javax.sound.sampled;

import java.io.InputStream;
import java.io.IOException;

/**
 * The <code>Clip</code> interface represents a special kind of data line whose
 * audio data can be loaded prior to playback, instead of being streamed in
 * real time.
 * <p>
 * Because the data is pre-loaded and has a known length, you can set a clip
 * to start playing at any position in its audio data.  You can also create a
 * loop, so that when the clip is played it will cycle repeatedly.  Loops are
 * specified with a starting and ending sample frame, along with the number of
 * times that the loop should be played.
 * <p>
 * Clips may be obtained from a <code>{@link Mixer}</code> that supports lines
 * of this type.  Data is loaded into a clip when it is opened.
 * <p>
 * Playback of an audio clip may be started and stopped using the <code>start</code>
 * and <code>stop</code> methods.  These methods do not reset the media position;
 * <code>start</code> causes playback to continue from the position where playback
 * was last stopped.  To restart playback from the beginning of the clip's audio
 * data, simply follow the invocation of <code>{@link DataLine#stop stop}</code>
 * with setFramePosition(0), which rewinds the media to the beginning
 * of the clip.
 *
 * <p>
 *  <code> Clip </code>接口代表一种特殊类型的数据线,其音频数据可以在回放之前加载,而不是实时流式传输。
 * <p>
 *  由于数据是预加载的并且具有已知长度,因此您可以设置剪辑以在其音频数据中的任何位置开始播放。您还可以创建一个循环,以便在播放剪辑时,它会重复循环。循环使用开始和结束采样帧指定,以及循环播放的次数。
 * <p>
 *  剪辑可以从支持此类型行的<code> {@ link Mixer} </code>获取。数据在打开时被加载到剪辑中。
 * <p>
 *  可以使用<code> start </code>和<code> stop </code>方法启动和停止音频剪辑的播放。
 * 这些方法不会重置介质位置; <code> start </code>使播放从上次停止播放的位置继续播放。
 * 要从剪辑的音频数据开始重新开始播放,只需按照setFramePosition(0)调用<code> {@ link DataLine#stop stop} </code>,即可将媒体倒回剪辑的开头。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public interface Clip extends DataLine {


    /**
     * A value indicating that looping should continue indefinitely rather than
     * complete after a specific number of loops.
     * <p>
     *  指示循环应该在特定数目的循环之后无限期地继续而不是完成的值。
     * 
     * 
     * @see #loop
     */
    public static final int LOOP_CONTINUOUSLY = -1;

    /**
     * Opens the clip, meaning that it should acquire any required
     * system resources and become operational.  The clip is opened
     * with the format and audio data indicated.
     * If this operation succeeds, the line is marked as open and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched
     * to the line's listeners.
     * <p>
     * Invoking this method on a line which is already open is illegal
     * and may result in an IllegalStateException.
     * <p>
     * Note that some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>{@link LineUnavailableException}</code>.
     *
     * <p>
     * 打开剪辑,这意味着它应该获取任何所需的系统资源并运行。剪辑以指定的格式和音频数据打开。
     * 如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,可能会导致IllegalStateException。
     * <p>
     *  注意,一些行,一旦关闭,不能重新打开。尝试重新打开此类行将始终导致<code> {@ link LineUnavailableException} </code>。
     * 
     * 
     * @param format the format of the supplied audio data
     * @param data a byte array containing audio data to load into the clip
     * @param offset the point at which to start copying, expressed in
     * <em>bytes</em> from the beginning of the array
     * @param bufferSize the number of <em>bytes</em>
     * of data to load into the clip from the array.
     * @throws LineUnavailableException if the line cannot be
     * opened due to resource restrictions
     * @throws IllegalArgumentException if the buffer size does not represent
     * an integral number of sample frames,
     * or if <code>format</code> is not fully specified or invalid
     * @throws IllegalStateException if the line is already open
     * @throws SecurityException if the line cannot be
     * opened due to security restrictions
     *
     * @see #close
     * @see #isOpen
     * @see LineListener
     */
    public void open(AudioFormat format, byte[] data, int offset, int bufferSize) throws LineUnavailableException;

    /**
     * Opens the clip with the format and audio data present in the provided audio
     * input stream.  Opening a clip means that it should acquire any required
     * system resources and become operational.  If this operation
     * input stream.  If this operation
     * succeeds, the line is marked open and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched
     * to the line's listeners.
     * <p>
     * Invoking this method on a line which is already open is illegal
     * and may result in an IllegalStateException.
     * <p>
     * Note that some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>{@link LineUnavailableException}</code>.
     *
     * <p>
     *  打开带有提供的音频输入流中存在的格式和音频数据的剪辑。打开剪辑意味着它应该获取任何所需的系统资源并运行。如果这个操作输入流。
     * 如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,可能会导致IllegalStateException。
     * <p>
     *  注意,一些行,一旦关闭,不能重新打开。尝试重新打开此类行将始终导致<code> {@ link LineUnavailableException} </code>。
     * 
     * 
     * @param stream an audio input stream from which audio data will be read into
     * the clip
     * @throws LineUnavailableException if the line cannot be
     * opened due to resource restrictions
     * @throws IOException if an I/O exception occurs during reading of
     * the stream
     * @throws IllegalArgumentException if the stream's audio format
     * is not fully specified or invalid
     * @throws IllegalStateException if the line is already open
     * @throws SecurityException if the line cannot be
     * opened due to security restrictions
     *
     * @see #close
     * @see #isOpen
     * @see LineListener
     */
    public void open(AudioInputStream stream) throws LineUnavailableException, IOException;

    /**
     * Obtains the media length in sample frames.
     * <p>
     *  获取样本帧中的介质长度。
     * 
     * 
     * @return the media length, expressed in sample frames,
     * or <code>AudioSystem.NOT_SPECIFIED</code> if the line is not open.
     * @see AudioSystem#NOT_SPECIFIED
     */
    public int getFrameLength();

    /**
     * Obtains the media duration in microseconds
     * <p>
     *  获取以微秒为单位的媒体持续时间
     * 
     * 
     * @return the media duration, expressed in microseconds,
     * or <code>AudioSystem.NOT_SPECIFIED</code> if the line is not open.
     * @see AudioSystem#NOT_SPECIFIED
     */
    public long getMicrosecondLength();

    /**
     * Sets the media position in sample frames.  The position is zero-based;
     * the first frame is frame number zero.  When the clip begins playing the
     * next time, it will start by playing the frame at this position.
     * <p>
     * To obtain the current position in sample frames, use the
     * <code>{@link DataLine#getFramePosition getFramePosition}</code>
     * method of <code>DataLine</code>.
     *
     * <p>
     * 在样品框中设置介质位置。位置是零为基础;第一帧是帧号零。当剪辑下次开始播放时,它将通过在此位置播放帧开始。
     * <p>
     *  要获取样本帧中的当前位置,请使用<code> DataLine </code>的<code> {@ link DataLine#getFramePosition getFramePosition} </code>
     * 方法。
     * 
     * 
     * @param frames the desired new media position, expressed in sample frames
     */
    public void setFramePosition(int frames);

    /**
     * Sets the media position in microseconds.  When the clip begins playing the
     * next time, it will start at this position.
     * The level of precision is not guaranteed.  For example, an implementation
     * might calculate the microsecond position from the current frame position
     * and the audio sample frame rate.  The precision in microseconds would
     * then be limited to the number of microseconds per sample frame.
     * <p>
     * To obtain the current position in microseconds, use the
     * <code>{@link DataLine#getMicrosecondPosition getMicrosecondPosition}</code>
     * method of <code>DataLine</code>.
     *
     * <p>
     *  以微秒为单位设置介质位置。当剪辑下次开始播放时,它将从此位置开始。不能保证精度水平。例如,实现可以从当前帧位置和音频采样帧速率计算微秒位置。微秒的精度将被限制为每个采样帧的微秒数。
     * <p>
     *  要获取当前位置(以微秒为单位),请使用<code> DataLine </code>的<code> {@ link DataLine#getMicrosecondPosition getMicrosecondPosition}
     *  </code>方法。
     * 
     * 
     * @param microseconds the desired new media position, expressed in microseconds
     */
    public void setMicrosecondPosition(long microseconds);

    /**
     * Sets the first and last sample frames that will be played in
     * the loop.  The ending point must be greater than
     * or equal to the starting point, and both must fall within the
     * the size of the loaded media.  A value of 0 for the starting
     * point means the beginning of the loaded media.  Similarly, a value of -1
     * for the ending point indicates the last frame of the media.
     * <p>
     *  设置将在循环中播放的第一个和最后一个采样帧。结束点必须大于或等于起点,并且两者必须在加载的介质的大小之内。起始点的值0表示加载介质的开始。类似地,对于结束点的值-1指示媒体的最后一帧。
     * 
     * 
     * @param start the loop's starting position, in sample frames (zero-based)
     * @param end the loop's ending position, in sample frames (zero-based), or
     * -1 to indicate the final frame
     * @throws IllegalArgumentException if the requested
     * loop points cannot be set, usually because one or both falls outside
     * the media's duration or because the ending point is
     * before the starting point
     */
    public void setLoopPoints(int start, int end);

    /**
     * Starts looping playback from the current position.   Playback will
     * continue to the loop's end point, then loop back to the loop start point
     * <code>count</code> times, and finally continue playback to the end of
     * the clip.
     * <p>
     * If the current position when this method is invoked is greater than the
     * loop end point, playback simply continues to the
     * end of the clip without looping.
     * <p>
     * A <code>count</code> value of 0 indicates that any current looping should
     * cease and playback should continue to the end of the clip.  The behavior
     * is undefined when this method is invoked with any other value during a
     * loop operation.
     * <p>
     * If playback is stopped during looping, the current loop status is
     * cleared; the behavior of subsequent loop and start requests is not
     * affected by an interrupted loop operation.
     *
     * <p>
     * 从当前位置开始循环播放。播放将继续到循环的结束点,然后循环回到循环开始点<code> count </code>次,最后继续播放到剪辑结束。
     * <p>
     *  如果调用此方法时的当前位置大于循环结束点,则播放只是继续到剪辑的结尾而不循环。
     * <p>
     * 
     * @param count the number of times playback should loop back from the
     * loop's end position to the loop's  start position, or
     * <code>{@link #LOOP_CONTINUOUSLY}</code> to indicate that looping should
     * continue until interrupted
     */
    public void loop(int count);
}
