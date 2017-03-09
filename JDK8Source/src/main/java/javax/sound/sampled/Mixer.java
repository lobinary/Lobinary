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


/**
 * A mixer is an audio device with one or more lines.  It need not be
 * designed for mixing audio signals.  A mixer that actually mixes audio
 * has multiple input (source) lines and at least one output (target) line.
 * The former are often instances of classes that implement
 * <code>{@link SourceDataLine}</code>,
 * and the latter, <code>{@link TargetDataLine}</code>.  <code>{@link Port}</code>
 * objects, too, are either source lines or target lines.
 * A mixer can accept prerecorded, loopable sound as input, by having
 * some of its source lines be instances of objects that implement the
 * <code>{@link Clip}</code> interface.
 * <p>
 * Through methods of the <code>Line</code> interface, which <code>Mixer</code> extends,
 * a mixer might provide a set of controls that are global to the mixer.  For example,
 * the mixer can have a master gain control.  These global controls are distinct
 * from the controls belonging to each of the mixer's individual lines.
 * <p>
 * Some mixers, especially
 * those with internal digital mixing capabilities, may provide
 * additional capabilities by implementing the <code>DataLine</code> interface.
 * <p>
 * A mixer can support synchronization of its lines.  When one line in
 * a synchronized group is started or stopped, the other lines in the group
 * automatically start or stop simultaneously with the explicitly affected one.
 *
 * <p>
 *  混音器是具有一条或多条线路的音频设备。它不需要被设计用于混合音频信号。实际混合音频的混频器具有多个输入(源)线和至少一个输出(目标)线。
 * 前者通常是实现<code> {@ link SourceDataLine} </code>的类的实例,后者是<code> {@ link TargetDataLine} </code>。
 *  <code> {@ link Port} </code>对象也是源行或目标行。
 * 混音器可以接受预先录制的,可循环的声音作为输入,通过使其一些源行是实现<code> {@ link Clip} </code>接口的对象的实例。
 * <p>
 *  通过Mixer </code>扩展的<code> Line </code>接口的方法,混音器可能提供一组对混音器是全局的控制。例如,混频器可以具有主增益控制。
 * 这些全局控制与属于每个混频器各个线路的控制不同。
 * <p>
 *  一些混频器,特别是具有内部数字混频能力的混频器,可以通过实现<code> DataLine </code>接口提供额外的功能。
 * <p>
 *  混频器可以支持其线路的同步。当同步组中的一行开始或停止时,组中的其他行会自动与显式影响的行同时启动或停止。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public interface Mixer extends Line {

    /**
     * Obtains information about this mixer, including the product's name,
     * version, vendor, etc.
     * <p>
     * 获取有关此调音台的信息,包括产品名称,版本,供应商等。
     * 
     * 
     * @return a mixer info object that describes this mixer
     * @see Mixer.Info
     */
    public Info getMixerInfo();


    /**
     * Obtains information about the set of source lines supported
     * by this mixer.
     * Some source lines may only be available when this mixer is open.
     * <p>
     *  获取有关此混音器支持的源线组的信息。一些源线只能在混频器打开时使用。
     * 
     * 
     * @return array of <code>Line.Info</code> objects representing source lines
     * for this mixer.  If no source lines are supported,
     * an array of length 0 is returned.
     */
    public Line.Info[] getSourceLineInfo();

    /**
     * Obtains information about the set of target lines supported
     * by this mixer.
     * Some target lines may only be available when this mixer is open.
     * <p>
     *  获取有关该混音器支持的目标线组的信息。一些目标线可能仅在混音器打开时可用。
     * 
     * 
     * @return array of <code>Line.Info</code> objects representing target lines
     * for this mixer.  If no target lines are supported,
     * an array of length 0 is returned.
     */
    public Line.Info[] getTargetLineInfo();


    /**
     * Obtains information about source lines of a particular type supported
     * by the mixer.
     * Some source lines may only be available when this mixer is open.
     * <p>
     *  获取有关混频器支持的特定类型源线的信息。一些源线只能在混频器打开时使用。
     * 
     * 
     * @param info a <code>Line.Info</code> object describing lines about which information
     * is queried
     * @return an array of <code>Line.Info</code> objects describing source lines matching
     * the type requested.  If no matching source lines are supported, an array of length 0
     * is returned.
     */
    public Line.Info[] getSourceLineInfo(Line.Info info);


    /**
     * Obtains information about target lines of a particular type supported
     * by the mixer.
     * Some target lines may only be available when this mixer is open.
     * <p>
     *  获取有关混频器支持的特定类型目标线的信息。一些目标线可能仅在混音器打开时可用。
     * 
     * 
     * @param info a <code>Line.Info</code> object describing lines about which information
     * is queried
     * @return an array of <code>Line.Info</code> objects describing target lines matching
     * the type requested.  If no matching target lines are supported, an array of length 0
     * is returned.
     */
    public Line.Info[] getTargetLineInfo(Line.Info info);


    /**
     * Indicates whether the mixer supports a line (or lines) that match
     * the specified <code>Line.Info</code> object.
     * Some lines may only be supported when this mixer is open.
     * <p>
     *  指示混音器是否支持与指定的<code> Line.Info </code>对象匹配的一行(或多行)。只有在打开混音器时才支持某些线路。
     * 
     * 
     * @param info describes the line for which support is queried
     * @return <code>true</code> if at least one matching line is
     * supported, <code>false</code> otherwise
     */
    public boolean isLineSupported(Line.Info info);

    /**
     * Obtains a line that is available for use and that matches the description
     * in the specified <code>Line.Info</code> object.
     *
     * <p>If a <code>DataLine</code> is requested, and <code>info</code>
     * is an instance of <code>DataLine.Info</code> specifying at
     * least one fully qualified audio format, the last one
     * will be used as the default format of the returned
     * <code>DataLine</code>.
     *
     * <p>
     *  获取可供使用并与指定的<code> Line.Info </code>对象中的描述匹配的行。
     * 
     *  <p>如果请求了<code> DataLine </code>,并且<code> info </code>是指定至少一个完全限定音频格式的<code> DataLine.Info </code>一个将
     * 被用作返回的<code> DataLine </code>的默认格式。
     * 
     * 
     * @param info describes the desired line
     * @return a line that is available for use and that matches the description
     * in the specified {@code Line.Info} object
     * @throws LineUnavailableException if a matching line
     * is not available due to resource restrictions
     * @throws IllegalArgumentException if this mixer does
     * not support any lines matching the description
     * @throws SecurityException if a matching line
     * is not available due to security restrictions
     */
    public Line getLine(Line.Info info) throws LineUnavailableException;

    //$$fb 2002-04-12: fix for 4667258: behavior of Mixer.getMaxLines(Line.Info) method doesn't match the spec
    /**
     * Obtains the approximate maximum number of lines of the requested type that can be open
     * simultaneously on the mixer.
     *
     * Certain types of mixers do not have a hard bound and may allow opening more lines.
     * Since certain lines are a shared resource, a mixer may not be able to open the maximum
     * number of lines if another process has opened lines of this mixer.
     *
     * The requested type is any line that matches the description in
     * the provided <code>Line.Info</code> object.  For example, if the info
     * object represents a speaker
     * port, and the mixer supports exactly one speaker port, this method
     * should return 1.  If the info object represents a source data line
     * and the mixer supports the use of 32 source data lines simultaneously,
     * the return value should be 32.
     * If there is no limit, this function returns <code>AudioSystem.NOT_SPECIFIED</code>.
     * <p>
     *  获取可以在混音器上同时打开的请求类型的近似最大行数。
     * 
     * 某些类型的混合器没有硬界,并且可以允许打开更多的线。由于某些线是共享资源,如果另一个进程打开了该混合器的线,则混合器可能不能打开最大数量的线。
     * 
     *  请求的类型是与提供的<code> Line.Info </code>对象中的描述匹配的任何行。
     * 例如,如果info对象表示扬声器端口,并且混音器仅支持一个扬声器端口,则该方法应返回1.如果info对象表示源数据线并且混音器同时支持使用32条源数据线,则返回值应为32.如果没有限制,此函数返回<code>
     *  AudioSystem.NOT_SPECIFIED </code>。
     *  请求的类型是与提供的<code> Line.Info </code>对象中的描述匹配的任何行。
     * 
     * 
     * @param info a <code>Line.Info</code> that describes the line for which
     * the number of supported instances is queried
     * @return the maximum number of matching lines supported, or <code>AudioSystem.NOT_SPECIFIED</code>
     */
    public int getMaxLines(Line.Info info);


    /**
     * Obtains the set of all source lines currently open to this mixer.
     *
     * <p>
     *  获取当前打开到此混合器的所有源线的集合。
     * 
     * 
     * @return the source lines currently open to the mixer.
     * If no source lines are currently open to this mixer,  an
     * array of length 0 is returned.
     * @throws SecurityException if the matching lines
     * are not available due to security restrictions
     */
    public Line[] getSourceLines();

    /**
     * Obtains the set of all target lines currently open from this mixer.
     *
     * <p>
     *  从此混音器获取当前打开的所有目标线的集合。
     * 
     * 
     * @return target lines currently open from the mixer.
     * If no target lines are currently open from this mixer, an
     * array of length 0 is returned.
     * @throws SecurityException if the matching lines
     * are not available due to security restrictions
     */
    public Line[] getTargetLines();

    /**
     * Synchronizes two or more lines.  Any subsequent command that starts or stops
     * audio playback or capture for one of these lines will exert the
     * same effect on the other lines in the group, so that they start or stop playing or
     * capturing data simultaneously.
     *
     * <p>
     *  同步两个或多个行。开始或停止音频回放或捕获这些线路之一的任何后续命令将对组中的其它线路施加相同的效果,使得它们同时开始或停止播放或捕获数据。
     * 
     * 
     * @param lines the lines that should be synchronized
     * @param maintainSync <code>true</code> if the synchronization
     * must be precisely maintained (i.e., the synchronization must be sample-accurate)
     * at all times during operation of the lines , or <code>false</code>
     * if precise synchronization is required only during start and stop operations
     *
     * @throws IllegalArgumentException if the lines cannot be synchronized.
     * This may occur if the lines are of different types or have different
     * formats for which this mixer does not support synchronization, or if
     * all lines specified do not belong to this mixer.
     */
    public void synchronize(Line[] lines, boolean maintainSync);

    /**
     * Releases synchronization for the specified lines.  The array must
     * be identical to one for which synchronization has already been
     * established; otherwise an exception may be thrown.  However, <code>null</code>
     * may be specified, in which case all currently synchronized lines that belong
     * to this mixer are unsynchronized.
     * <p>
     *  释放指定行的同步。该阵列必须与已经建立同步的阵列相同;否则可能抛出异常。然而,可以指定<code> null </code>,在这种情况下,属于该混合器的所有当前同步的线是不同步的。
     * 
     * 
     * @param lines the synchronized lines for which synchronization should be
     * released, or <code>null</code> for all this mixer's synchronized lines
     *
     * @throws IllegalArgumentException if the lines cannot be unsynchronized.
     * This may occur if the argument specified does not exactly match a set
     * of lines for which synchronization has already been established.
     */
    public void unsynchronize(Line[] lines);


    /**
     * Reports whether this mixer supports synchronization of the specified set of lines.
     *
     * <p>
     * 报告此混音器是否支持指定的线路集的同步。
     * 
     * 
     * @param lines the set of lines for which synchronization support is queried
     * @param maintainSync <code>true</code> if the synchronization
     * must be precisely maintained (i.e., the synchronization must be sample-accurate)
     * at all times during operation of the lines , or <code>false</code>
     * if precise synchronization is required only during start and stop operations
     *
     * @return <code>true</code> if the lines can be synchronized, <code>false</code>
     * otherwise
     */
    public boolean isSynchronizationSupported(Line[] lines, boolean maintainSync);


    /**
     * The <code>Mixer.Info</code> class represents information about an audio mixer,
     * including the product's name, version, and vendor, along with a textual
     * description.  This information may be retrieved through the
     * {@link Mixer#getMixerInfo() getMixerInfo}
     * method of the <code>Mixer</code> interface.
     *
     * <p>
     *  <code> Mixer.Info </code>类表示有关音频混音器的信息,包括产品名称,版本和供应商,以及文本描述。
     * 可以通过<code> Mixer </code>接口的{@link Mixer#getMixerInfo()getMixerInfo}方法检索此信息。
     * 
     * 
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Info {

        /**
         * Mixer name.
         * <p>
         *  混合器名称。
         * 
         */
        private final String name;

        /**
         * Mixer vendor.
         * <p>
         *  搅拌机供应商。
         * 
         */
        private final String vendor;

        /**
         * Mixer description.
         * <p>
         *  混音器说明。
         * 
         */
        private final String description;

        /**
         * Mixer version.
         * <p>
         *  混频器版本。
         * 
         */
        private final String version;

        /**
         * Constructs a mixer's info object, passing it the given
         * textual information.
         * <p>
         *  构造混音器的信息对象,传递给定的文本信息。
         * 
         * 
         * @param name the name of the mixer
         * @param vendor the company who manufactures or creates the hardware
         * or software mixer
         * @param description descriptive text about the mixer
         * @param version version information for the mixer
         */
        protected Info(String name, String vendor, String description, String version) {

            this.name = name;
            this.vendor = vendor;
            this.description = description;
            this.version = version;
        }


        /**
         * Indicates whether two info objects are equal, returning <code>true</code> if
         * they are identical.
         * <p>
         *  指示两个信息对象是否相等,如果它们相同,则返回<code> true </code>。
         * 
         * 
         * @param obj the reference object with which to compare this info
         * object
         * @return <code>true</code> if this info object is the same as the
         * <code>obj</code> argument; <code>false</code> otherwise
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        /**
         * Finalizes the hashcode method.
         *
         * <p>
         *  完成哈希码方法。
         * 
         * 
         * @return the hashcode for this object
         */
        public final int hashCode() {
            return super.hashCode();
        }

        /**
         * Obtains the name of the mixer.
         * <p>
         *  获取混音器的名称。
         * 
         * 
         * @return a string that names the mixer
         */
        public final String getName() {
            return name;
        }

        /**
         * Obtains the vendor of the mixer.
         * <p>
         *  获取搅拌机的供应商。
         * 
         * 
         * @return a string that names the mixer's vendor
         */
        public final String getVendor() {
            return vendor;
        }

        /**
         * Obtains the description of the mixer.
         * <p>
         *  获取混音器的描述。
         * 
         * 
         * @return a textual description of the mixer
         */
        public final String getDescription() {
            return description;
        }

        /**
         * Obtains the version of the mixer.
         * <p>
         *  获取混音器的版本。
         * 
         * 
         * @return textual version information for the mixer
         */
        public final String getVersion() {
            return version;
        }

        /**
         * Provides a string representation of the mixer info.
         * <p>
         *  提供混音器信息的字符串表示形式。
         * 
         * @return a string describing the info object
         */
        public final String toString() {
            return (name + ", version " + version);
        }
    } // class Info
}
