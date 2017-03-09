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
 * The <code>ReverbType</code> class provides methods for
 * accessing various reverberation settings to be applied to
 * an audio signal.
 * <p>
 * Reverberation simulates the reflection of sound off of
 * the walls, ceiling, and floor of a room.  Depending on
 * the size of the room, and how absorbent or reflective the materials in the
 * room's surfaces are, the sound might bounce around for a
 * long time before dying away.
 * <p>
 * The reverberation parameters provided by <code>ReverbType</code> consist
 * of the delay time and intensity of early reflections, the delay time and
 * intensity of late reflections, and an overall decay time.
 * Early reflections are the initial individual low-order reflections of the
 * direct signal off the surfaces in the room.
 * The late Reflections are the dense, high-order reflections that characterize
 * the room's reverberation.
 * The delay times for the start of these two reflection types give the listener
 * a sense of the overall size and complexity of the room's shape and contents.
 * The larger the room, the longer the reflection delay times.
 * The early and late reflections' intensities define the gain (in decibels) of the reflected
 * signals as compared to the direct signal.  These intensities give the
 * listener an impression of the absorptive nature of the surfaces and objects
 * in the room.
 * The decay time defines how long the reverberation takes to exponentially
 * decay until it is no longer perceptible ("effective zero").
 * The larger and less absorbent the surfaces, the longer the decay time.
 * <p>
 * The set of parameters defined here may not include all aspects of reverberation
 * as specified by some systems.  For example, the Midi Manufacturer's Association
 * (MMA) has an Interactive Audio Special Interest Group (IASIG), which has a
 * 3-D Working Group that has defined a Level 2 Spec (I3DL2).  I3DL2
 * supports filtering of reverberation and
 * control of reverb density.  These properties are not included in the JavaSound 1.0
 * definition of a reverb control.  In such a case, the implementing system
 * should either extend the defined reverb control to include additional
 * parameters, or else interpret the system's additional capabilities in a way that fits
 * the model described here.
 * <p>
 * If implementing JavaSound on a I3DL2-compliant device:
 * <ul>
 * <li>Filtering is disabled (high-frequency attenuations are set to 0.0 dB)
 * <li>Density parameters are set to midway between minimum and maximum
 * </ul>
 * <p>
 * The following table shows what parameter values an implementation might use for a
 * representative set of reverberation settings.
 * <p>
 *
 * <b>Reverberation Types and Parameters</b>
 * <p>
 * <table border=1 cellpadding=5 summary="reverb types and params: decay time, late intensity, late delay, early intensity, and early delay">
 *
 * <tr>
 *  <th>Type</th>
 *  <th>Decay Time (ms)</th>
 *  <th>Late Intensity (dB)</th>
 *  <th>Late Delay (ms)</th>
 *  <th>Early Intensity (dB)</th>
 *  <th>Early Delay(ms)</th>
 * </tr>
 *
 * <tr>
 *  <td>Cavern</td>
 *  <td>2250</td>
 *  <td>-2.0</td>
 *  <td>41.3</td>
 *  <td>-1.4</td>
 *  <td>10.3</td>
 * </tr>
 *
 * <tr>
 *  <td>Dungeon</td>
 *  <td>1600</td>
 *  <td>-1.0</td>
 *  <td>10.3</td>
 *  <td>-0.7</td>
 *  <td>2.6</td>
 * </tr>
 *
 * <tr>
 *  <td>Garage</td>
 *  <td>900</td>
 *  <td>-6.0</td>
 *  <td>14.7</td>
 *  <td>-4.0</td>
 *  <td>3.9</td>
 * </tr>
 *
 * <tr>
 *  <td>Acoustic Lab</td>
 *  <td>280</td>
 *  <td>-3.0</td>
 *  <td>8.0</td>
 *  <td>-2.0</td>
 *  <td>2.0</td>
 * </tr>
 *
 * <tr>
 *  <td>Closet</td>
 *  <td>150</td>
 *  <td>-10.0</td>
 *  <td>2.5</td>
 *  <td>-7.0</td>
 *  <td>0.6</td>
 * </tr>
 *
 * </table>
 *
 * <p>
 *  <code> ReverbType </code>类提供用于访问要应用于音频信号的各种混响设置的方法。
 * <p>
 *  混响模拟声音从房间的墙壁,天花板和地板的反射。根据房间的大小,以及房间表面材料的吸收性或反射性,声音可能会在消失前长时间反弹。
 * <p>
 * 由<code> ReverbType </code>提供的混响参数包括早期反射的延迟时间和强度,延迟时间和晚期反射的强度,以及总的衰减时间。早期反射是直接信号离开房间表面的初始单独低阶反射。
 * 晚期的反射是表征房间混响的密集,高阶反射。这两种反射类型的开始的延迟时间给予听者对房间的形状和内容的总体尺寸和复杂性的感觉。房间越大,反射延迟时间越长。
 * 早期和晚期反射强度定义与直接信号相比反射信号的增益(以分贝为单位)。这些强度给予听者对房间中的表面和物体的吸收性质的印象。衰减时间定义混响采取指数衰减多长时间,直到它不再可感知("有效零")。
 * 表面越大且吸收越少,衰减时间越长。
 * <p>
 * 这里定义的参数集合可以不包括由一些系统指定的混响的所有方面。例如,Midi制造商协会(MMA)具有交互式音频特殊兴趣组(IASIG),其具有定义了2级规范(I3DL2)的3-D工作组。
 *  I3DL2支持混响的混响和控制的混响密度。这些属性不包括在混响控制的JavaSound 1.0定义中。
 * 在这种情况下,实现系统应该扩展定义的混响控制以包括附加参数,或者以适合这里描述的模型的方式解释系统的附加能力。
 * <p>
 *  如果在符合I3DL2的设备上实现JavaSound：
 * <ul>
 *  <li>禁用过滤(高频衰减设置为0.0 dB)<li>密度参数设置为最小值和最大值之间的中间值
 * </ul>
 * <p>
 *  下表显示实施可能对一组代表性的混响设置使用的参数值。
 * <p>
 * 
 *  <b>混响类型和参数</b>
 * <p>
 * <table border=1 cellpadding=5 summary="reverb types and params: decay time, late intensity, late delay, early intensity, and early delay">
 * 
 * <tr>
 *  <th>类型</th> <th>延迟时间(ms)</th> <th>迟滞强度(dB)</th> <th> dB)<th>早延迟(ms)</th>
 * </tr>
 * 
 * <tr>
 *  <td>洞穴</td> <td> 2250 </td> <td> -2.0 </td> <td> 41.3 </td> <td> -1.4 </td> <td> 10.3 </td>
 * </tr>
 * 
 * <tr>
 *  <td> Dungeon </td> <td> 1600 </td> <td> -1.0 </td> <td> 10.3 </td> <td> -0.7 </td> <td>
 * </tr>
 * 
 * <tr>
 *  <td>车库</td> <td> 900 </td> <td> -6.0 </td> <td> 14.7 </td> <td> -4.0 </td>
 * </tr>
 * 
 * <tr>
 * <td> Acoustic Lab </td> <td> 280 </td> <td> -3.0 </td> <td> 8.0 </td> <td> -2.0 </td> <td> 2.0 </td >
 * 。
 * </tr>
 * 
 * <tr>
 *  <td> Closet </td> <td> 150 </td> <td> -10.0 </td> <td> 2.5 </td> <td> -7.0 </td> <td> 0.6 </td>
 * </tr>
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public class ReverbType {

    /**
     * Descriptive name of the reverb type..
     * <p>
     * </table>
     * 
     */
    private String name;

    /**
     * Early reflection delay in microseconds.
     * <p>
     *  混响类型的描述性名称..
     * 
     */
    private int earlyReflectionDelay;

    /**
     * Early reflection intensity.
     * <p>
     *  早反射延迟微秒。
     * 
     */
    private float earlyReflectionIntensity;

    /**
     * Late reflection delay in microseconds.
     * <p>
     *  早期反射强度。
     * 
     */
    private int lateReflectionDelay;

    /**
     * Late reflection intensity.
     * <p>
     *  晚反射延迟(微秒)。
     * 
     */
    private float lateReflectionIntensity;

    /**
     * Total decay time
     * <p>
     *  晚反射强度。
     * 
     */
    private int decayTime;


    /**
     * Constructs a new reverb type that has the specified reverberation
     * parameter values.
     * <p>
     *  总衰减时间
     * 
     * 
     * @param name the name of the new reverb type, or a zero-length <code>String</code>
     * @param earlyReflectionDelay the new type's early reflection delay time in microseconds
     * @param earlyReflectionIntensity the new type's early reflection intensity in dB
     * @param lateReflectionDelay the new type's late reflection delay time in microseconds
     * @param lateReflectionIntensity the new type's late reflection intensity in dB
     * @param decayTime the new type's decay time in microseconds
     */
    protected ReverbType(String name, int earlyReflectionDelay, float earlyReflectionIntensity, int lateReflectionDelay, float lateReflectionIntensity, int decayTime) {

        this.name = name;
        this.earlyReflectionDelay = earlyReflectionDelay;
        this.earlyReflectionIntensity = earlyReflectionIntensity;
        this.lateReflectionDelay = lateReflectionDelay;
        this.lateReflectionIntensity = lateReflectionIntensity;
        this.decayTime = decayTime;
    }


    /**
     * Obtains the name of this reverb type.
     * <p>
     *  构造具有指定混响参数值的新混响类型。
     * 
     * 
     * @return the name of this reverb type
     * @since 1.5
     */
    public String getName() {
            return name;
    }


    /**
     * Returns the early reflection delay time in microseconds.
     * This is the amount of time between when the direct signal is
     * heard and when the first early reflections are heard.
     * <p>
     *  获取此混响类型的名称。
     * 
     * 
     * @return  early reflection delay time for this reverb type, in microseconds
     */
    public final int getEarlyReflectionDelay() {
        return earlyReflectionDelay;
    }


    /**
     * Returns the early reflection intensity in decibels.
     * This is the amplitude attenuation of the first early reflections
     * relative to the direct signal.
     * <p>
     *  返回早期反射延迟时间(以微秒为单位)。这是在听到直接信号和听到第一次早期反射之间的时间量。
     * 
     * 
     * @return  early reflection intensity for this reverb type, in dB
     */
    public final float getEarlyReflectionIntensity() {
        return earlyReflectionIntensity;
    }


    /**
     * Returns the late reflection delay time in microseconds.
     * This is the amount of time between when the first early reflections
     * are heard and when the first late reflections are heard.
     * <p>
     *  返回以分贝为单位的早期反射强度。这是相对于直接信号的第一早期反射的振幅衰减。
     * 
     * 
     * @return  late reflection delay time for this reverb type, in microseconds
     */
    public final int getLateReflectionDelay() {
        return lateReflectionDelay;
    }


    /**
     * Returns the late reflection intensity in decibels.
     * This is the amplitude attenuation of the first late reflections
     * relative to the direct signal.
     * <p>
     *  返回延迟反射延迟时间(以微秒为单位)。这是从听到第一次早期反射到听到第一次晚期反射之间的时间量。
     * 
     * 
     * @return  late reflection intensity for this reverb type, in dB
     */
    public final float getLateReflectionIntensity() {
        return lateReflectionIntensity;
    }


    /**
     * Obtains the decay time, which is the amount of time over which the
     * late reflections attenuate to effective zero.  The effective zero
     * value is implementation-dependent.
     * <p>
     *  返回晚反射强度(以分贝为单位)。这是第一晚期反射相对于直接信号的振幅衰减。
     * 
     * 
     * @return  the decay time of the late reflections, in microseconds
     */
    public final int getDecayTime() {
        return decayTime;
    }


    /**
     * Indicates whether the specified object is equal to this reverb type,
     * returning <code>true</code> if the objects are identical.
     * <p>
     *  获得衰减时间,即延迟反射衰减到有效零的时间量。有效零值是实现相关的。
     * 
     * 
     * @param obj the reference object with which to compare
     * @return <code>true</code> if this reverb type is the same as
     * <code>obj</code>; <code>false</code> otherwise
     */
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * Finalizes the hashcode method.
     * <p>
     *  指示指定的对象是否等于此混响类型,如果对象相同,则返回<code> true </code>。
     * 
     */
    public final int hashCode() {
        return super.hashCode();
    }


    /**
     * Provides a <code>String</code> representation of the reverb type,
     * including its name and its parameter settings.
     * The exact contents of the string may vary between implementations of
     * Java Sound.
     * <p>
     *  完成哈希码方法。
     * 
     * 
     * @return reverberation type name and description
     */
    public final String toString() {

        //$$fb2001-07-20: fix for bug 4385060: The "name" attribute of class "ReverbType" is not accessible.
        //return (super.toString() + ", early reflection delay " + earlyReflectionDelay +
        return (name + ", early reflection delay " + earlyReflectionDelay +
                " ns, early reflection intensity " + earlyReflectionIntensity +
                " dB, late deflection delay " + lateReflectionDelay +
                " ns, late reflection intensity " + lateReflectionIntensity +
                " dB, decay time " +  decayTime);
    }

} // class ReverbType
