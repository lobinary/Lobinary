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

/**
 * A <code>FloatControl</code> object provides control over a range of
 * floating-point values.  Float controls are often
 * represented in graphical user interfaces by continuously
 * adjustable objects such as sliders or rotary knobs.  Concrete subclasses
 * of <code>FloatControl</code> implement controls, such as gain and pan, that
 * affect a line's audio signal in some way that an application can manipulate.
 * The <code>{@link FloatControl.Type}</code>
 * inner class provides static instances of types that are used to
 * identify some common kinds of float control.
 * <p>
 * The <code>FloatControl</code> abstract class provides methods to set and get
 * the control's current floating-point value.  Other methods obtain the possible
 * range of values and the control's resolution (the smallest increment between
 * returned values).  Some float controls allow ramping to a
 * new value over a specified period of time.  <code>FloatControl</code> also
 * includes methods that return string labels for the minimum, maximum, and midpoint
 * positions of the control.
 *
 * <p>
 *  <code> FloatControl </code>对象提供对一系列浮点值的控制。浮动控制通常通过诸如滑块或旋钮的连续可调节的对象在图形用户界面中表示。
 *  <code> FloatControl </code>的具体子类实现了控件,例如增益和平移,它们以某种方式影响线的音频信号,应用程序可以操作它。
 *  <code> {@ link FloatControl.Type} </code>内部类提供了用于标识一些常见类型的浮点控件的类型的静态实例。
 * <p>
 *  <code> FloatControl </code>抽象类提供了设置和获取控件当前浮点值的方法。其他方法获得可能的值范围和控件的分辨率(返回值之间的最小增量)。
 * 一些浮动控件允许在指定的时间段内斜坡到一个新的值。 <code> FloatControl </code>还包括返回控件的最小,最大和中点位置的字符串标签的方法。
 * 
 * 
 * @see Line#getControls
 * @see Line#isControlSupported
 *
 * @author David Rivas
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class FloatControl extends Control {


    // INSTANCE VARIABLES


    // FINAL VARIABLES

    /**
     * The minimum supported value.
     * <p>
     *  支持的最小值。
     * 
     */
    private float minimum;

    /**
     * The maximum supported value.
     * <p>
     *  支持的最大值。
     * 
     */
    private float maximum;

    /**
     * The control's precision.
     * <p>
     *  控制精度。
     * 
     */
    private float precision;

    /**
     * The smallest time increment in which a value change
     * can be effected during a value shift, in microseconds.
     * <p>
     *  在值移位期间可以实现值改变的最小时间增量,以微秒为单位。
     * 
     */
    private int updatePeriod;


    /**
     * A label for the units in which the control values are expressed,
     * such as "dB" for decibels.
     * <p>
     *  用于表示控制值的单位的标签,例如"dB"表示分贝。
     * 
     */
    private final String units;

    /**
     * A label for the minimum value, such as "Left."
     * <p>
     * 最小值的标签,例如"左"。
     * 
     */
    private final String minLabel;

    /**
     * A label for the maximum value, such as "Right."
     * <p>
     *  最大值的标签,例如"右"。
     * 
     */
    private final String maxLabel;

    /**
     * A label for the mid-point value, such as "Center."
     * <p>
     *  中点值的标签,例如"中心"。
     * 
     */
    private final String midLabel;


    // STATE VARIABLES

    /**
     * The current value.
     * <p>
     *  当前值。
     * 
     */
    private float value;



    // CONSTRUCTORS


    /**
     * Constructs a new float control object with the given parameters
     *
     * <p>
     *  使用给定的参数构造一个新的浮点控制对象
     * 
     * 
     * @param type the kind of control represented by this float control object
     * @param minimum the smallest value permitted for the control
     * @param maximum the largest value permitted for the control
     * @param precision the resolution or granularity of the control.
     * This is the size of the increment between discrete valid values.
     * @param updatePeriod the smallest time interval, in microseconds, over which the control
     * can change from one discrete value to the next during a {@link #shift(float,float,int) shift}
     * @param initialValue the value that the control starts with when constructed
     * @param units the label for the units in which the control's values are expressed,
     * such as "dB" or "frames per second"
     * @param minLabel the label for the minimum value, such as "Left" or "Off"
     * @param midLabel the label for the midpoint value, such as "Center" or "Default"
     * @param maxLabel the label for the maximum value, such as "Right" or "Full"
     *
     * @throws IllegalArgumentException if {@code minimum} is greater
     *     than {@code maximum} or {@code initialValue} does not fall
     *     within the allowable range
     */
    protected FloatControl(Type type, float minimum, float maximum,
            float precision, int updatePeriod, float initialValue,
            String units, String minLabel, String midLabel, String maxLabel) {

        super(type);

        if (minimum > maximum) {
            throw new IllegalArgumentException("Minimum value " + minimum
                    + " exceeds maximum value " + maximum + ".");
        }
        if (initialValue < minimum) {
            throw new IllegalArgumentException("Initial value " + initialValue
                    + " smaller than allowable minimum value " + minimum + ".");
        }
        if (initialValue > maximum) {
            throw new IllegalArgumentException("Initial value " + initialValue
                    + " exceeds allowable maximum value " + maximum + ".");
        }


        this.minimum = minimum;
        this.maximum = maximum;

        this.precision = precision;
        this.updatePeriod = updatePeriod;
        this.value = initialValue;

        this.units = units;
        this.minLabel = ( (minLabel == null) ? "" : minLabel);
        this.midLabel = ( (midLabel == null) ? "" : midLabel);
        this.maxLabel = ( (maxLabel == null) ? "" : maxLabel);
    }


    /**
     * Constructs a new float control object with the given parameters.
     * The labels for the minimum, maximum, and mid-point values are set
     * to zero-length strings.
     *
     * <p>
     *  使用给定的参数构造一个新的浮点控制对象。最小值,最大值和中点值的标签设置为零长度字符串。
     * 
     * 
     * @param type the kind of control represented by this float control object
     * @param minimum the smallest value permitted for the control
     * @param maximum the largest value permitted for the control
     * @param precision the resolution or granularity of the control.
     * This is the size of the increment between discrete valid values.
     * @param updatePeriod the smallest time interval, in microseconds, over which the control
     * can change from one discrete value to the next during a {@link #shift(float,float,int) shift}
     * @param initialValue the value that the control starts with when constructed
     * @param units the label for the units in which the control's values are expressed,
     * such as "dB" or "frames per second"
     *
     * @throws IllegalArgumentException if {@code minimum} is greater
     *     than {@code maximum} or {@code initialValue} does not fall
     *     within the allowable range
     */
    protected FloatControl(Type type, float minimum, float maximum,
            float precision, int updatePeriod, float initialValue, String units) {
        this(type, minimum, maximum, precision, updatePeriod,
                initialValue, units, "", "", "");
    }



    // METHODS


    /**
     * Sets the current value for the control.  The default implementation
     * simply sets the value as indicated.  If the value indicated is greater
     * than the maximum value, or smaller than the minimum value, an
     * IllegalArgumentException is thrown.
     * Some controls require that their line be open before they can be affected
     * by setting a value.
     * <p>
     *  设置控件的当前值。默认实现只是按照指示设置值。如果指示的值大于最大值或小于最小值,则抛出IllegalArgumentException。一些控件要求在设置值之前,它们的行被打开。
     * 
     * 
     * @param newValue desired new value
     * @throws IllegalArgumentException if the value indicated does not fall
     * within the allowable range
     */
    public void setValue(float newValue) {

        if (newValue > maximum) {
            throw new IllegalArgumentException("Requested value " + newValue + " exceeds allowable maximum value " + maximum + ".");
        }

        if (newValue < minimum) {
            throw new IllegalArgumentException("Requested value " + newValue + " smaller than allowable minimum value " + minimum + ".");
        }

        value = newValue;
    }


    /**
     * Obtains this control's current value.
     * <p>
     *  获取此控件的当前值。
     * 
     * 
     * @return the current value
     */
    public float getValue() {
        return value;
    }


    /**
     * Obtains the maximum value permitted.
     * <p>
     *  获取允许的最大值。
     * 
     * 
     * @return the maximum allowable value
     */
    public float getMaximum() {
        return maximum;
    }


    /**
     * Obtains the minimum value permitted.
     * <p>
     *  获取允许的最小值。
     * 
     * 
     * @return the minimum allowable value
     */
    public float getMinimum() {
        return minimum;
    }


    /**
     * Obtains the label for the units in which the control's values are expressed,
     * such as "dB" or "frames per second."
     * <p>
     *  获取表示控制值的单位的标签,例如"dB"或"每秒帧数"。
     * 
     * 
     * @return the units label, or a zero-length string if no label
     */
    public String getUnits() {
        return units;
    }


    /**
     * Obtains the label for the minimum value, such as "Left" or "Off."
     * <p>
     *  获取最小值的标签,例如"Left"或"Off"。
     * 
     * 
     * @return the minimum value label, or a zero-length string if no label      * has been set
     */
    public String getMinLabel() {
        return minLabel;
    }


    /**
     * Obtains the label for the mid-point value, such as "Center" or "Default."
     * <p>
     *  获取中点值的标签,例如"中心"或"默认值"。
     * 
     * 
     * @return the mid-point value label, or a zero-length string if no label    * has been set
     */
    public String getMidLabel() {
        return midLabel;
    }


    /**
     * Obtains the label for the maximum value, such as "Right" or "Full."
     * <p>
     *  获取最大值的标签,例如"Right"或"Full"。
     * 
     * 
     * @return the maximum value label, or a zero-length string if no label      * has been set
     */
    public String getMaxLabel() {
        return maxLabel;
    }


    /**
     * Obtains the resolution or granularity of the control, in the units
     * that the control measures.
     * The precision is the size of the increment between discrete valid values
     * for this control, over the set of supported floating-point values.
     * <p>
     *  在控制措施的单位中获得控制的分辨率或粒度。精度是在该控制的离散有效值之间的增量的大小,在支持的浮点值的集合上。
     * 
     * 
     * @return the control's precision
     */
    public float getPrecision() {
        return precision;
    }


    /**
     * Obtains the smallest time interval, in microseconds, over which the control's value can
     * change during a shift.  The update period is the inverse of the frequency with which
     * the control updates its value during a shift.  If the implementation does not support value shifting over
     * time, it should set the control's value to the final value immediately
     * and return -1 from this method.
     *
     * <p>
     * 获取控制值在移位期间可以更改的最小时间间隔(以微秒为单位)。更新周期是控制在移位期间更新其值的频率的倒数。如果实现不支持随时间的值移位,它应该立即将控件的值设置为最终值,并从此方法返回-1。
     * 
     * 
     * @return update period in microseconds, or -1 if shifting over time is unsupported
     * @see #shift
     */
    public int getUpdatePeriod() {
        return updatePeriod;
    }


    /**
     * Changes the control value from the initial value to the final
     * value linearly over the specified time period, specified in microseconds.
     * This method returns without blocking; it does not wait for the shift
     * to complete.  An implementation should complete the operation within the time
     * specified.  The default implementation simply changes the value
     * to the final value immediately.
     *
     * <p>
     *  在指定的时间段内(以微秒为单位)线性地将控制值从初始值更改为最终值。这个方法返回没有阻塞;它不等待班完成。实现应在指定的时间内完成操作。默认实现只是立即将值更改为最终值。
     * 
     * 
     * @param from initial value at the beginning of the shift
     * @param to final value after the shift
     * @param microseconds maximum duration of the shift in microseconds
     *
     * @throws IllegalArgumentException if either {@code from} or {@code to}
     *     value does not fall within the allowable range
     *
     * @see #getUpdatePeriod
     */
    public void shift(float from, float to, int microseconds) {
        // test "from" value, "to" value will be tested by setValue()
        if (from < minimum) {
            throw new IllegalArgumentException("Requested value " + from
                    + " smaller than allowable minimum value " + minimum + ".");
        }
        if (from > maximum) {
            throw new IllegalArgumentException("Requested value " + from
                    + " exceeds allowable maximum value " + maximum + ".");
        }
        setValue(to);
    }


    // ABSTRACT METHOD IMPLEMENTATIONS: CONTROL


    /**
     * Provides a string representation of the control
     * <p>
     *  提供控件的字符串表示形式
     * 
     * 
     * @return a string description
     */
    public String toString() {
        return new String(getType() + " with current value: " + getValue() + " " + units +
                          " (range: " + minimum + " - " + maximum + ")");
    }


    // INNER CLASSES


    /**
     * An instance of the <code>FloatControl.Type</code> inner class identifies one kind of
     * float control.  Static instances are provided for the
     * common types.
     *
     * <p>
     *  <code> FloatControl.Type </code>内部类的实例标识一种float控件。为常见类型提供静态实例。
     * 
     * 
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Type extends Control.Type {


        // TYPE DEFINES


        // GAIN TYPES

        /**
         * Represents a control for the overall gain on a line.
         * <p>
         * Gain is a quantity in decibels (dB) that is added to the intrinsic
         * decibel level of the audio signal--that is, the level of
         * the signal before it is altered by the gain control.  A positive
         * gain amplifies (boosts) the signal's volume, and a negative gain
         * attenuates (cuts) it.
         * The gain setting defaults to a value of 0.0 dB, meaning the signal's
         * loudness is unaffected.   Note that gain measures dB, not amplitude.
         * The relationship between a gain in decibels and the corresponding
         * linear amplitude multiplier is:
         *
         *<CENTER><CODE> linearScalar = pow(10.0, gainDB/20.0) </CODE></CENTER>
         * <p>
         * The <code>FloatControl</code> class has methods to impose a maximum and
         * minimum allowable value for gain.  However, because an audio signal might
         * already be at a high amplitude, the maximum setting does not guarantee
         * that the signal will be undistorted when the gain is applied to it (unless
         * the maximum is zero or negative). To avoid numeric overflow from excessively
         * large gain settings, a gain control can implement
         * clipping, meaning that the signal's amplitude will be limited to the maximum
         * value representable by its audio format, instead of wrapping around.
         * <p>
         * These comments apply to gain controls in general, not just master gain controls.
         * A line can have more than one gain control.  For example, a mixer (which is
         * itself a line) might have a master gain control, an auxiliary return control,
         * a reverb return control, and, on each of its source lines, an individual aux
         * send and reverb send.
         *
         * <p>
         *  表示线路上总增益的控制。
         * <p>
         * 增益是以分贝(dB)为单位的数量,它被加到音频信号的固有分贝级,即在增益控制改变之前的信号电平。正增益放大(提升)信号的音量,负增益衰减(削减)。
         * 增益设置默认为0.0 dB的值,这意味着信号的响度不受影响。注意增益测量dB,而不是幅度。分贝增益和相应的线性幅度乘数之间的关系为：。
         * 
         *  CENTER> <CODE> linearScalar = pow(10.0,gainDB / 20.0)</CODE> </CENTER>
         * <p>
         *  <code> FloatControl </code>类具有对增益施加最大和最小允许值的方法。
         * 然而,由于音频信号可能已经处于高幅度,所以最大设置不能保证在对其应用增益时该信号不失真(除非最大值为零或负)。
         * 为了避免来自过大增益设置的数字溢出,增益控制可以实现限幅,这意味着信号的幅度将被限制为其音频格式可表示的最大值,而不是环绕。
         * <p>
         *  这些注释通常适用于增益控制,而不仅仅适用于主增益控制。线可以有多个增益控制。
         * 例如,混频器(其本身是线路)可以具有主增益控制,辅助返回控制,混响返回控制,并且在其每个源线上具有单独的辅助发送和混响发送。
         * 
         * 
         * @see #AUX_SEND
         * @see #AUX_RETURN
         * @see #REVERB_SEND
         * @see #REVERB_RETURN
         * @see #VOLUME
         */
        public static final Type MASTER_GAIN            = new Type("Master Gain");

        /**
         * Represents a control for the auxiliary send gain on a line.
         *
         * <p>
         * 表示线路上辅助发送增益的控制。
         * 
         * 
         * @see #MASTER_GAIN
         * @see #AUX_RETURN
         */
        public static final Type AUX_SEND                       = new Type("AUX Send");

        /**
         * Represents a control for the auxiliary return gain on a line.
         *
         * <p>
         *  表示线路上辅助返回增益的控制。
         * 
         * 
         * @see #MASTER_GAIN
         * @see #AUX_SEND
         */
        public static final Type AUX_RETURN                     = new Type("AUX Return");

        /**
         * Represents a control for the pre-reverb gain on a line.
         * This control may be used to affect how much
         * of a line's signal is directed to a mixer's internal reverberation unit.
         *
         * <p>
         *  表示线上预混音增益的控制。该控制可以用于影响线路的信号被引导到混频器的内部混响单元的多少。
         * 
         * 
         * @see #MASTER_GAIN
         * @see #REVERB_RETURN
         * @see EnumControl.Type#REVERB
         */
        public static final Type REVERB_SEND            = new Type("Reverb Send");

        /**
         * Represents a control for the post-reverb gain on a line.
         * This control may be used to control the relative amplitude
         * of the signal returned from an internal reverberation unit.
         *
         * <p>
         *  表示线上后混响增益的控制。该控制可以用于控制从内部混响单元返回的信号的相对幅度。
         * 
         * 
         * @see #MASTER_GAIN
         * @see #REVERB_SEND
         */
        public static final Type REVERB_RETURN          = new Type("Reverb Return");


        // VOLUME

        /**
         * Represents a control for the volume on a line.
         * <p>
         *  表示线上的音量的控件。
         * 
         */
        /*
         * $$kk: 08.30.99: ISSUE: what units?  linear or dB?
         * <p>
         *  $$ kk：08.30.99：ISSUE：什么单位?线性或dB?
         * 
         */
        public static final Type VOLUME                         = new Type("Volume");


        // PAN

        /**
         * Represents a control for the relative pan (left-right positioning)
         * of the signal.  The signal may be mono; the pan setting affects how
         * it is distributed by the mixer in a stereo mix.  The valid range of values is -1.0
         * (left channel only) to 1.0 (right channel
         * only).  The default is 0.0 (centered).
         *
         * <p>
         *  表示信号的相对平移(左右定位)的控件。信号可以是单声道;平移设置会影响混音器在立体声混音中的分配方式。值的有效范围是-1.0(仅左通道)至1.0(仅右通道)。默认值为0.0(居中)。
         * 
         * 
         * @see #BALANCE
         */
        public static final Type PAN                            = new Type("Pan");


        // BALANCE

        /**
         * Represents a control for the relative balance of a stereo signal
         * between two stereo speakers.  The valid range of values is -1.0 (left channel only) to 1.0 (right channel
         * only).  The default is 0.0 (centered).
         *
         * <p>
         *  表示两个立体声扬声器之间的立体声信号的相对平衡的控制。值的有效范围是-1.0(仅左通道)至1.0(仅右通道)。默认值为0.0(居中)。
         * 
         * 
         * @see #PAN
         */
        public static final Type BALANCE                        = new Type("Balance");


        // SAMPLE RATE

        /**
         * Represents a control that changes the sample rate of audio playback.  The net effect
         * of changing the sample rate depends on the relationship between
         * the media's natural rate and the rate that is set via this control.
         * The natural rate is the sample rate that is specified in the data line's
         * <code>AudioFormat</code> object.  For example, if the natural rate
         * of the media is 11025 samples per second and the sample rate is set
         * to 22050 samples per second, the media will play back at twice the
         * normal speed.
         * <p>
         * Changing the sample rate with this control does not affect the data line's
         * audio format.  Also note that whenever you change a sound's sample rate, a
         * change in the sound's pitch results.  For example, doubling the sample
         * rate has the effect of doubling the frequencies in the sound's spectrum,
         * which raises the pitch by an octave.
         * <p>
         * 表示更改音频播放采样率的控件。更改采样率的净效果取决于介质的自然速率和通过此控制设置的速率之间的关系。自然率是在数据行的<code> AudioFormat </code>对象中指定的采样率。
         * 例如,如果媒体的固有速率为每秒11025个样本,并且采样率设置为每秒22050个样本,则媒体将以正常速度的两倍进行回放。
         * <p>
         *  使用此控件更改采样率不会影响数据线的音频格式。还要注意,无论何时更改声音的采样率,都会导致声音音高的变化。例如,使采样率加倍具有使声音频谱中的频率加倍的效果,其将音调提高八度音阶。
         */
        public static final Type SAMPLE_RATE            = new Type("Sample Rate");


        // CONSTRUCTOR

        /**
         * Constructs a new float control type.
         * <p>
         * 
         * 
         * @param name  the name of the new float control type
         */
        protected Type(String name) {
            super(name);
        }

    } // class Type

} // class FloatControl
