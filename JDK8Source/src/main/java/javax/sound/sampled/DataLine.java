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

import java.util.Arrays;

/**
 * <code>DataLine</code> adds media-related functionality to its
 * superinterface, <code>{@link Line}</code>.  This functionality includes
 * transport-control methods that start, stop, drain, and flush
 * the audio data that passes through the line.  A data line can also
 * report the current position, volume, and audio format of the media.
 * Data lines are used for output of audio by means of the
 * subinterfaces <code>{@link SourceDataLine}</code> or
 * <code>{@link Clip}</code>, which allow an application program to write data.  Similarly,
 * audio input is handled by the subinterface <code>{@link TargetDataLine}</code>,
 * which allows data to be read.
 * <p>
 * A data line has an internal buffer in which
 * the incoming or outgoing audio data is queued.  The
 * <code>{@link #drain()}</code> method blocks until this internal buffer
 * becomes empty, usually because all queued data has been processed.  The
 * <code>{@link #flush()}</code> method discards any available queued data
 * from the internal buffer.
 * <p>
 * A data line produces <code>{@link LineEvent.Type#START START}</code> and
 * <code>{@link LineEvent.Type#STOP STOP}</code> events whenever
 * it begins or ceases active presentation or capture of data.  These events
 * can be generated in response to specific requests, or as a result of
 * less direct state changes.  For example, if <code>{@link #start()}</code> is called
 * on an inactive data line, and data is available for capture or playback, a
 * <code>START</code> event will be generated shortly, when data playback
 * or capture actually begins.  Or, if the flow of data to an active data
 * line is constricted so that a gap occurs in the presentation of data,
 * a <code>STOP</code> event is generated.
 * <p>
 * Mixers often support synchronized control of multiple data lines.
 * Synchronization can be established through the Mixer interface's
 * <code>{@link Mixer#synchronize synchronize}</code> method.
 * See the description of the <code>{@link Mixer Mixer}</code> interface
 * for a more complete description.
 *
 * <p>
 *  <code> DataLine </code>向其超级接口<code> {@ link Line} </code>添加了媒体相关功能。
 * 此功能包括启动,停止,排除和刷新通过该线路的音频数据的传输控制方法。数据线还可以报告媒体的当前位置,音量和音频格式。
 * 数据线用于通过子接口<code> {@ link SourceDataLine} </code>或<code> {@ link Clip} </code>输出音频,这允许应用程序写入数据。
 * 类似地,音频输入由子接口<code> {@ link TargetDataLine} </code>处理,允许读取数据。
 * <p>
 *  数据线具有内部缓冲器,输入或输出音频数据在该缓冲器中排队。 <code> {@ link #drain()} </code>方法阻塞,直到此内部缓冲区变为空,通常是因为所有排队数据都已处理。
 *  <code> {@ link #flush()} </code>方法会从内部缓冲区中丢弃任何可用的排队数据。
 * <p>
 * 数据行会在其开始或停止活动呈现或捕获时产生<code> {@ link LineEvent.Type#START START} </code>和<code> {@ link LineEvent.Type#STOP STOP}
 * 数据。
 * 这些事件可以响应于特定请求或作为较少直接状态改变的结果而生成。
 * 例如,如果在非活动数据行上调用<code> {@ link #start()} </code>,并且数据可用于捕获或播放,则会立即生成<code> START </code> ,当数据播放或捕获实际开始
 * 时。
 * 这些事件可以响应于特定请求或作为较少直接状态改变的结果而生成。或者,如果数据到活动数据线的流被收缩,使得在数据的呈现中出现间隙,则产生<code> STOP </code>事件。
 * <p>
 *  混频器通常支持多条数据线的同步控制。同步可以通过Mixer接口的<code> {@ link mixer#synchronize synchronize} </code>方法建立。
 * 有关更完整的说明,请参阅<code> {@ link Mixer Mixer} </code>界面的说明。
 * 
 * 
 * @author Kara Kytle
 * @see LineEvent
 * @since 1.3
 */
public interface DataLine extends Line {


    /**
     * Drains queued data from the line by continuing data I/O until the
     * data line's internal buffer has been emptied.
     * This method blocks until the draining is complete.  Because this is a
     * blocking method, it should be used with care.  If <code>drain()</code>
     * is invoked on a stopped line that has data in its queue, the method will
     * block until the line is running and the data queue becomes empty.  If
     * <code>drain()</code> is invoked by one thread, and another continues to
     * fill the data queue, the operation will not complete.
     * This method always returns when the data line is closed.
     *
     * <p>
     * 通过继续数据I / O,直到数据线的内部缓冲区已经清空,从该行中排队数据。此方法阻塞,直到排水完成。因为这是一个阻塞的方法,所以应该小心使用。
     * 如果在其队列中有数据的停止行上调用<code> drain()</code>,则该方法将阻塞,直到该行运行并且数据队列变为空。
     * 如果<code> drain()</code>被一个线程调用,而另一个线程继续填充数据队列,操作将不会完成。该方法总是在数据行关闭时返回。
     * 
     * 
     * @see #flush()
     */
    public void drain();

    /**
     * Flushes queued data from the line.  The flushed data is discarded.
     * In some cases, not all queued data can be discarded.  For example, a
     * mixer can flush data from the buffer for a specific input line, but any
     * unplayed data already in the output buffer (the result of the mix) will
     * still be played.  You can invoke this method after pausing a line (the
     * normal case) if you want to skip the "stale" data when you restart
     * playback or capture. (It is legal to flush a line that is not stopped,
     * but doing so on an active line is likely to cause a discontinuity in the
     * data, resulting in a perceptible click.)
     *
     * <p>
     *  从行中刷新排队的数据。丢弃刷新的数据。在一些情况下,不是所有排队的数据都可以被丢弃。例如,混合器可以从特定输入线的缓冲器中刷新数据,但是仍然在播放已经在输出缓冲器中的任何未播放的数据(混合的结果)。
     * 如果要在重新启动播放或捕获时跳过"过时"数据,可以在暂停行之后调用此方法(正常情况)。 (刷新未停止的行是合法的,但是在活动行上这样做可能会导致数据不连续,导致可察觉的点击)。
     * 
     * 
     * @see #stop()
     * @see #drain()
     */
    public void flush();

    /**
     * Allows a line to engage in data I/O.  If invoked on a line
     * that is already running, this method does nothing.  Unless the data in
     * the buffer has been flushed, the line resumes I/O starting
     * with the first frame that was unprocessed at the time the line was
     * stopped. When audio capture or playback starts, a
     * <code>{@link LineEvent.Type#START START}</code> event is generated.
     *
     * <p>
     * 允许线路参与数据I / O。如果在已经运行的行上调用,则此方法不执行任何操作。除非缓冲区中的数据已被刷新,否则该行从在该行停止时未处理的第一帧开始继续I / O。
     * 当音频捕获或播放开始时,会生成<code> {@ link LineEvent.Type#START START} </code>事件。
     * 
     * 
     * @see #stop()
     * @see #isRunning()
     * @see LineEvent
     */
    public void start();

    /**
     * Stops the line.  A stopped line should cease I/O activity.
     * If the line is open and running, however, it should retain the resources required
     * to resume activity.  A stopped line should retain any audio data in its buffer
     * instead of discarding it, so that upon resumption the I/O can continue where it left off,
     * if possible.  (This doesn't guarantee that there will never be discontinuities beyond the
     * current buffer, of course; if the stopped condition continues
     * for too long, input or output samples might be dropped.)  If desired, the retained data can be
     * discarded by invoking the <code>flush</code> method.
     * When audio capture or playback stops, a <code>{@link LineEvent.Type#STOP STOP}</code> event is generated.
     *
     * <p>
     *  停止线。停止的行应该停止I / O活动。然而,如果线路是打开和运行的,则它应该保留恢复活动所需的资源。
     * 停止线应在其缓冲区中保留任何音频数据,而不是丢弃它,以便在恢复时,I / O可以继续在其中断的地方,如果可能的话。
     *  (这并不保证当前缓冲区之外永远不会有不连续性,当然,如果停止条件持续太长时间,输入或输出样本可能被丢弃。)如果需要,可以通过调用<code> flush </code>方法。
     * 当音频捕获或播放停止时,会生成<code> {@ link LineEvent.Type#STOP STOP} </code>事件。
     * 
     * 
     * @see #start()
     * @see #isRunning()
     * @see #flush()
     * @see LineEvent
     */
    public void stop();

    /**
     * Indicates whether the line is running.  The default is <code>false</code>.
     * An open line begins running when the first data is presented in response to an
     * invocation of the <code>start</code> method, and continues
     * until presentation ceases in response to a call to <code>stop</code> or
     * because playback completes.
     * <p>
     *  指示线路是否正在运行。默认值为<code> false </code>。
     * 当响应于<code> start </code>方法的调用而呈现第一数据时,开放线路开始运行,并且继续,直到响应于对<code>停止</code>的调用而停止呈现,完成。
     * 
     * 
     * @return <code>true</code> if the line is running, otherwise <code>false</code>
     * @see #start()
     * @see #stop()
     */
    public boolean isRunning();

    /**
     * Indicates whether the line is engaging in active I/O (such as playback
     * or capture).  When an inactive line becomes active, it sends a
     * <code>{@link LineEvent.Type#START START}</code> event to its listeners.  Similarly, when
     * an active line becomes inactive, it sends a
     * <code>{@link LineEvent.Type#STOP STOP}</code> event.
     * <p>
     * 指示线路是否正在进行活动I / O(例如播放或捕获)。当非活动线路变为活动时,它向其侦听器发送<code> {@ link LineEvent.Type#START START} </code>事件。
     * 类似地,当活动线路变为不活动时,它发送<code> {@ link LineEvent.Type#STOP STOP} </code>事件。
     * 
     * 
     * @return <code>true</code> if the line is actively capturing or rendering
     * sound, otherwise <code>false</code>
     * @see #isOpen
     * @see #addLineListener
     * @see #removeLineListener
     * @see LineEvent
     * @see LineListener
     */
    public boolean isActive();

    /**
     * Obtains the current format (encoding, sample rate, number of channels,
     * etc.) of the data line's audio data.
     *
     * <p>If the line is not open and has never been opened, it returns
     * the default format. The default format is an implementation
     * specific audio format, or, if the <code>DataLine.Info</code>
     * object, which was used to retrieve this <code>DataLine</code>,
     * specifies at least one fully qualified audio format, the
     * last one will be used as the default format. Opening the
     * line with a specific audio format (e.g.
     * {@link SourceDataLine#open(AudioFormat)}) will override the
     * default format.
     *
     * <p>
     *  获取数据线的音频数据的当前格式(编码,采样率,通道数量等)。
     * 
     *  <p>如果该行未打开,并且从未打开,则返回默认格式。
     * 默认格式是实现特定的音频格式,或者如果用于检索此<code> DataLine </code>的<code> DataLine.Info </code>对象指定至少一个完全限定的音频格式,最后一个将被用
     * 作默认格式。
     *  <p>如果该行未打开,并且从未打开,则返回默认格式。以特定音频格式打开行(例如{@link SourceDataLine#open(AudioFormat)})将覆盖默认格式。
     * 
     * 
     * @return current audio data format
     * @see AudioFormat
     */
    public AudioFormat getFormat();

    /**
     * Obtains the maximum number of bytes of data that will fit in the data line's
     * internal buffer.  For a source data line, this is the size of the buffer to
     * which data can be written.  For a target data line, it is the size of
     * the buffer from which data can be read.  Note that
     * the units used are bytes, but will always correspond to an integral
     * number of sample frames of audio data.
     *
     * <p>
     *  获取将在数据线的内部缓冲区中容纳的数据的最大字节数。对于源数据线,这是可以写入数据的缓冲区的大小。对于目标数据线,它是可以从其读取数据的缓冲区的大小。
     * 注意,所使用的单位是字节,但总是对应于音频数据的整数个样本帧。
     * 
     * 
     * @return the size of the buffer in bytes
     */
    public int getBufferSize();

    /**
     * Obtains the number of bytes of data currently available to the
     * application for processing in the data line's internal buffer.  For a
     * source data line, this is the amount of data that can be written to the
     * buffer without blocking.  For a target data line, this is the amount of data
     * available to be read by the application.  For a clip, this value is always
     * 0 because the audio data is loaded into the buffer when the clip is opened,
     * and persists without modification until the clip is closed.
     * <p>
     * Note that the units used are bytes, but will always
     * correspond to an integral number of sample frames of audio data.
     * <p>
     * An application is guaranteed that a read or
     * write operation of up to the number of bytes returned from
     * <code>available()</code> will not block; however, there is no guarantee
     * that attempts to read or write more data will block.
     *
     * <p>
     * 获取当前可用于应用程序在数据行的内部缓冲区中进行处理的数据字节数。对于源数据线,这是可以写入缓冲区而不阻塞的数据量。对于目标数据行,这是应用程序可读取的数据量。
     * 对于剪辑,此值始终为0,因为音频数据在剪辑打开时加载到缓冲区中,并且持续保存,直到剪辑关闭为止。
     * <p>
     *  注意,所使用的单位是字节,但总是对应于音频数据的整数个样本帧。
     * <p>
     *  应用程序保证从<code> available()</code>返回的字节数的读或写操作不会被阻塞;但是,不能保证尝试读取或写入更多数据将被阻止。
     * 
     * 
     * @return the amount of data available, in bytes
     */
    public int available();

    /**
     * Obtains the current position in the audio data, in sample frames.
     * The frame position measures the number of sample
     * frames captured by, or rendered from, the line since it was opened.
     * This return value will wrap around after 2^31 frames. It is recommended
     * to use <code>getLongFramePosition</code> instead.
     *
     * <p>
     *  在采样帧中获取音频数据中的当前位置。框架位置测量从线被打开时捕获的线或从线渲染的样本帧的数量。这个返回值将在2 ^ 31帧之后循环。
     * 建议使用<code> getLongFramePosition </code>。
     * 
     * 
     * @return the number of frames already processed since the line was opened
     * @see #getLongFramePosition()
     */
    public int getFramePosition();


    /**
     * Obtains the current position in the audio data, in sample frames.
     * The frame position measures the number of sample
     * frames captured by, or rendered from, the line since it was opened.
     *
     * <p>
     *  在采样帧中获取音频数据中的当前位置。框架位置测量从线被打开时捕获的线或从线渲染的样本帧的数量。
     * 
     * 
     * @return the number of frames already processed since the line was opened
     * @since 1.5
     */
    public long getLongFramePosition();


    /**
     * Obtains the current position in the audio data, in microseconds.
     * The microsecond position measures the time corresponding to the number
     * of sample frames captured by, or rendered from, the line since it was opened.
     * The level of precision is not guaranteed.  For example, an implementation
     * might calculate the microsecond position from the current frame position
     * and the audio sample frame rate.  The precision in microseconds would
     * then be limited to the number of microseconds per sample frame.
     *
     * <p>
     * 获取音频数据中的当前位置(以微秒为单位)。微秒位置测量对应于由于线被打开而由线捕获或从线呈现的样本帧的数量的时间。不能保证精度水平。例如,实现可以从当前帧位置和音频采样帧速率计算微秒位置。
     * 微秒的精度将被限制为每个采样帧的微秒数。
     * 
     * 
     * @return the number of microseconds of data processed since the line was opened
     */
    public long getMicrosecondPosition();

    /**
     * Obtains the current volume level for the line.  This level is a measure
     * of the signal's current amplitude, and should not be confused with the
     * current setting of a gain control. The range is from 0.0 (silence) to
     * 1.0 (maximum possible amplitude for the sound waveform).  The units
     * measure linear amplitude, not decibels.
     *
     * <p>
     *  获取线路的当前音量级别。该电平是信号电流幅度的度量,不应与增益控制的电流设置相混淆。范围是从0.0(静音)到1.0(声音波形的最大可能振幅)。单位测量线性幅度,而不是分贝。
     * 
     * 
     * @return the current amplitude of the signal in this line, or
     * <code>{@link AudioSystem#NOT_SPECIFIED}</code>
     */
    public float getLevel();

    /**
     * Besides the class information inherited from its superclass,
     * <code>DataLine.Info</code> provides additional information specific to data lines.
     * This information includes:
     * <ul>
     * <li> the audio formats supported by the data line
     * <li> the minimum and maximum sizes of its internal buffer
     * </ul>
     * Because a <code>Line.Info</code> knows the class of the line its describes, a
     * <code>DataLine.Info</code> object can describe <code>DataLine</code>
     * subinterfaces such as <code>{@link SourceDataLine}</code>,
     * <code>{@link TargetDataLine}</code>, and <code>{@link Clip}</code>.
     * You can query a mixer for lines of any of these types, passing an appropriate
     * instance of <code>DataLine.Info</code> as the argument to a method such as
     * <code>{@link Mixer#getLine Mixer.getLine(Line.Info)}</code>.
     *
     * <p>
     *  除了继承自其超类的类信息之外,<code> DataLine.Info </code>还提供了特定于数据行的附加信息。此信息包括：
     * <ul>
     *  <li>数据线支持的音频格式<li>其内部缓冲区的最小和最大尺寸
     * </ul>
     * 因为<code> Line.Info </code>知道它描述的行的类,所以<code> DataLine.Info </code>对象可以描述<code> DataLine </code>子接口,例如
     * <code> { @link SourceDataLine} </code>,<code> {@ link TargetDataLine} </code>和<code> {@ link Clip} </code>
     * 。
     * 您可以查询混合器中任何类型的行,传递适当的实例<code> DataLine.Info </code>作为参数到一个方法,如<code> {@ link Mixer#getLine Mixer.getLine .Info)}
     *  </code>。
     * 
     * 
     * @see Line.Info
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Info extends Line.Info {

        private final AudioFormat[] formats;
        private final int minBufferSize;
        private final int maxBufferSize;

        /**
         * Constructs a data line's info object from the specified information,
         * which includes a set of supported audio formats and a range for the buffer size.
         * This constructor is typically used by mixer implementations
         * when returning information about a supported line.
         *
         * <p>
         *  从指定的信息构造数据行的信息对象,其中包括一组支持的音频格式和缓冲区大小的范围。此构造函数通常在返回有关支持行的信息时由混合器实现使用。
         * 
         * 
         * @param lineClass the class of the data line described by the info object
         * @param formats set of formats supported
         * @param minBufferSize minimum buffer size supported by the data line, in bytes
         * @param maxBufferSize maximum buffer size supported by the data line, in bytes
         */
        public Info(Class<?> lineClass, AudioFormat[] formats, int minBufferSize, int maxBufferSize) {

            super(lineClass);

            if (formats == null) {
                this.formats = new AudioFormat[0];
            } else {
                this.formats = Arrays.copyOf(formats, formats.length);
            }

            this.minBufferSize = minBufferSize;
            this.maxBufferSize = maxBufferSize;
        }


        /**
         * Constructs a data line's info object from the specified information,
         * which includes a single audio format and a desired buffer size.
         * This constructor is typically used by an application to
         * describe a desired line.
         *
         * <p>
         *  根据指定的信息构造数据行的信息对象,其中包括单个音频格式和所需的缓冲区大小。此构造函数通常由应用程序用来描述所需的行。
         * 
         * 
         * @param lineClass the class of the data line described by the info object
         * @param format desired format
         * @param bufferSize desired buffer size in bytes
         */
        public Info(Class<?> lineClass, AudioFormat format, int bufferSize) {

            super(lineClass);

            if (format == null) {
                this.formats = new AudioFormat[0];
            } else {
                this.formats = new AudioFormat[]{format};
            }

            this.minBufferSize = bufferSize;
            this.maxBufferSize = bufferSize;
        }


        /**
         * Constructs a data line's info object from the specified information,
         * which includes a single audio format.
         * This constructor is typically used by an application to
         * describe a desired line.
         *
         * <p>
         *  从指定的信息构造数据行的信息对象,其中包括单个音频格式。此构造函数通常由应用程序用来描述所需的行。
         * 
         * 
         * @param lineClass the class of the data line described by the info object
         * @param format desired format
         */
        public Info(Class<?> lineClass, AudioFormat format) {
            this(lineClass, format, AudioSystem.NOT_SPECIFIED);
        }


        /**
         * Obtains a set of audio formats supported by the data line.
         * Note that <code>isFormatSupported(AudioFormat)</code> might return
         * <code>true</code> for certain additional formats that are missing from
         * the set returned by <code>getFormats()</code>.  The reverse is not
         * the case: <code>isFormatSupported(AudioFormat)</code> is guaranteed to return
         * <code>true</code> for all formats returned by <code>getFormats()</code>.
         *
         * Some fields in the AudioFormat instances can be set to
         * {@link javax.sound.sampled.AudioSystem#NOT_SPECIFIED NOT_SPECIFIED}
         * if that field does not apply to the format,
         * or if the format supports a wide range of values for that field.
         * For example, a multi-channel device supporting up to
         * 64 channels, could set the channel field in the
         * <code>AudioFormat</code> instances returned by this
         * method to <code>NOT_SPECIFIED</code>.
         *
         * <p>
         * 获取数据线支持的一组音频格式。
         * 请注意,对于<code> getFormats()</code>返回的集合中缺少的某些其他格式,<code> isFormatSupported(AudioFormat)</code>可能返回<code>
         *  true </code>。
         * 获取数据线支持的一组音频格式。
         * 反之不是这样：<code> isFormatSupported(AudioFormat)</code>保证对<code> getFormats()</code>返回的所有格式返回<code> true 
         * </code>。
         * 获取数据线支持的一组音频格式。
         * 
         *  如果该字段不适用于格式,或者如果格式支持该字段的大范围值,则可以将AudioFormat实例中的某些字段设置为{@link javax.sound.sampled.AudioSystem#NOT_SPECIFIED NOT_SPECIFIED}
         * 。
         * 例如,支持多达64个通道的多通道设备可以将由此方法返回的<code> AudioFormat </code>实例中的通道字段设置为<code> NOT_SPECIFIED </code>。
         * 
         * 
         * @return a set of supported audio formats.
         * @see #isFormatSupported(AudioFormat)
         */
        public AudioFormat[] getFormats() {
            return Arrays.copyOf(formats, formats.length);
        }

        /**
         * Indicates whether this data line supports a particular audio format.
         * The default implementation of this method simply returns <code>true</code> if
         * the specified format matches any of the supported formats.
         *
         * <p>
         *  指示此数据线是否支持特定的音频格式。如果指定的格式匹配任何受支持的格式,则此方法的默认实现简单地返回<code> true </code>。
         * 
         * 
         * @param format the audio format for which support is queried.
         * @return <code>true</code> if the format is supported, otherwise <code>false</code>
         * @see #getFormats
         * @see AudioFormat#matches
         */
        public boolean isFormatSupported(AudioFormat format) {

            for (int i = 0; i < formats.length; i++) {
                if (format.matches(formats[i])) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Obtains the minimum buffer size supported by the data line.
         * <p>
         *  获取数据线支持的最小缓冲区大小。
         * 
         * 
         * @return minimum buffer size in bytes, or <code>AudioSystem.NOT_SPECIFIED</code>
         */
        public int getMinBufferSize() {
            return minBufferSize;
        }


        /**
         * Obtains the maximum buffer size supported by the data line.
         * <p>
         *  获取数据线支持的最大缓冲区大小。
         * 
         * 
         * @return maximum buffer size in bytes, or <code>AudioSystem.NOT_SPECIFIED</code>
         */
        public int getMaxBufferSize() {
            return maxBufferSize;
        }


        /**
         * Determines whether the specified info object matches this one.
         * To match, the superclass match requirements must be met.  In
         * addition, this object's minimum buffer size must be at least as
         * large as that of the object specified, its maximum buffer size must
         * be at most as large as that of the object specified, and all of its
         * formats must match formats supported by the object specified.
         * <p>
         * 确定指定的信息对象是否与此信息对象匹配。要匹配,必须满足超类匹配要求。
         * 此外,此对象的最小缓冲区大小必须至少与指定对象的大小一样大,其最大缓冲区大小必须至多等于指定对象的大小,并且其所有格式必须与该对象支持的格式匹配指定。
         * 
         * 
         * @return <code>true</code> if this object matches the one specified,
         * otherwise <code>false</code>.
         */
        public boolean matches(Line.Info info) {

            if (! (super.matches(info)) ) {
                return false;
            }

            Info dataLineInfo = (Info)info;

            // treat anything < 0 as NOT_SPECIFIED
            // demo code in old Java Sound Demo used a wrong buffer calculation
            // that would lead to arbitrary negative values
            if ((getMaxBufferSize() >= 0) && (dataLineInfo.getMaxBufferSize() >= 0)) {
                if (getMaxBufferSize() > dataLineInfo.getMaxBufferSize()) {
                    return false;
                }
            }

            if ((getMinBufferSize() >= 0) && (dataLineInfo.getMinBufferSize() >= 0)) {
                if (getMinBufferSize() < dataLineInfo.getMinBufferSize()) {
                    return false;
                }
            }

            AudioFormat[] localFormats = getFormats();

            if (localFormats != null) {

                for (int i = 0; i < localFormats.length; i++) {
                    if (! (localFormats[i] == null) ) {
                        if (! (dataLineInfo.isFormatSupported(localFormats[i])) ) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }

        /**
         * Obtains a textual description of the data line info.
         * <p>
         * 
         * @return a string description
         */
        public String toString() {

            StringBuffer buf = new StringBuffer();

            if ( (formats.length == 1) && (formats[0] != null) ) {
                buf.append(" supporting format " + formats[0]);
            } else if (getFormats().length > 1) {
                buf.append(" supporting " + getFormats().length + " audio formats");
            }

            if ( (minBufferSize != AudioSystem.NOT_SPECIFIED) && (maxBufferSize != AudioSystem.NOT_SPECIFIED) ) {
                buf.append(", and buffers of " + minBufferSize + " to " + maxBufferSize + " bytes");
            } else if ( (minBufferSize != AudioSystem.NOT_SPECIFIED) && (minBufferSize > 0) ) {
                buf.append(", and buffers of at least " + minBufferSize + " bytes");
            } else if (maxBufferSize != AudioSystem.NOT_SPECIFIED) {
                buf.append(", and buffers of up to " + minBufferSize + " bytes");
            }

            return new String(super.toString() + buf);
        }
    } // class Info

} // interface DataLine
