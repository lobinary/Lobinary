/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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
 * MIDI events contain a MIDI message and a corresponding time-stamp
 * expressed in ticks, and can represent the MIDI event information
 * stored in a MIDI file or a <code>{@link Sequence}</code> object.  The
 * duration of a tick is specified by the timing information contained
 * in the MIDI file or <code>Sequence</code> object.
 * <p>
 * In Java Sound, <code>MidiEvent</code> objects are typically contained in a
 * <code>{@link Track}</code>, and <code>Tracks</code> are likewise
 * contained in a <code>Sequence</code>.
 *
 *
 * <p>
 *  MIDI事件包含MIDI消息和以ticks表示的相应时间戳,并且可以表示存储在MIDI文件或<code> {@ link Sequence} </code>对象中的MIDI事件信息。
 *  tick的持续时间由包含在MIDI文件或<code> Sequence </code>对象中的定时信息指定。
 * <p>
 *  在Java Sound中,<code> MidiEvent </code>对象通常包含在<code> {@ link Track} </code>中,<code> Tracks </code> / c
 * ode>。
 * 
 * 
 * @author David Rivas
 * @author Kara Kytle
 */
public class MidiEvent {


    // Instance variables

    /**
     * The MIDI message for this event.
     * <p>
     *  此事件的MIDI消息。
     * 
     */
    private final MidiMessage message;


    /**
     * The tick value for this event.
     * <p>
     *  此事件的tick值。
     * 
     */
    private long tick;


    /**
     * Constructs a new <code>MidiEvent</code>.
     * <p>
     *  构造一个新的<code> MidiEvent </code>。
     * 
     * 
     * @param message the MIDI message contained in the event
     * @param tick the time-stamp for the event, in MIDI ticks
     */
    public MidiEvent(MidiMessage message, long tick) {

        this.message = message;
        this.tick = tick;
    }

    /**
     * Obtains the MIDI message contained in the event.
     * <p>
     *  获取事件中包含的MIDI消息。
     * 
     * 
     * @return the MIDI message
     */
    public MidiMessage getMessage() {
        return message;
    }


    /**
     * Sets the time-stamp for the event, in MIDI ticks
     * <p>
     *  在MIDI记号中设置事件的时间戳
     * 
     * 
     * @param tick the new time-stamp, in MIDI ticks
     */
    public void setTick(long tick) {
        this.tick = tick;
    }


    /**
     * Obtains the time-stamp for the event, in MIDI ticks
     * <p>
     *  在MIDI节拍中获取事件的时间戳
     * 
     * @return the time-stamp for the event, in MIDI ticks
     */
    public long getTick() {
        return tick;
    }
}
