/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <p>{@code MidiDeviceTransmitter} is a {@code Transmitter} which represents
 * a MIDI input connector of a {@code MidiDevice}
 * (see {@link MidiDevice#getTransmitter()}).
 *
 * <p>
 *  <p> {@ code MidiDeviceTransmitter}是一个{@code Transmitter},代表{@code MidiDevice}的MIDI输入连接器(请参阅{@link MidiDevice#getTransmitter()}
 * )。
 * 
 * 
 * @since 1.7
 */
public interface MidiDeviceTransmitter extends Transmitter {

    /**
     * Obtains a MidiDevice object which is an owner of this Transmitter.
     * <p>
     * 
     * @return a MidiDevice object which is an owner of this Transmitter
     */
    public MidiDevice getMidiDevice();
}
