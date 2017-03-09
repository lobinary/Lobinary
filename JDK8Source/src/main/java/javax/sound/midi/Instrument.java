/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.net.URL;



/**
 * An instrument is a sound-synthesis algorithm with certain parameter
 * settings, usually designed to emulate a specific real-world
 * musical instrument or to achieve a specific sort of sound effect.
 * Instruments are typically stored in collections called soundbanks.
 * Before the instrument can be used to play notes, it must first be loaded
 * onto a synthesizer, and then it must be selected for use on
 * one or more channels, via a program-change command.  MIDI notes
 * that are subsequently received on those channels will be played using
 * the sound of the selected instrument.
 *
 * <p>
 *  仪器是具有某些参数设置的声音合成算法,通常被设计为模拟特定的真实世界乐器或实现特定类型的声音效果。仪器通常存储在称为声库的集合中。
 * 在仪器可用于播放音符之前,必须首先将其加载到合成器上,然后必须通过程序更改命令选择在一个或多个通道上使用。随后在这些通道上接收的MIDI音符将使用所选乐器的声音播放。
 * 
 * 
 * @see Soundbank
 * @see Soundbank#getInstruments
 * @see Patch
 * @see Synthesizer#loadInstrument(Instrument)
 * @see MidiChannel#programChange(int, int)
 * @author Kara Kytle
 */

public abstract class Instrument extends SoundbankResource {


    /**
     * Instrument patch
     * <p>
     *  仪器补丁
     * 
     */
    private final Patch patch;


    /**
     * Constructs a new MIDI instrument from the specified <code>Patch</code>.
     * When a subsequent request is made to load the
     * instrument, the sound bank will search its contents for this instrument's <code>Patch</code>,
     * and the instrument will be loaded into the synthesizer at the
     * bank and program location indicated by the <code>Patch</code> object.
     * <p>
     *  从指定的<code> Patch </code>构造一个新的MIDI乐器。
     * 当随后请求加载仪器时,声音库将搜索其仪器的<code> Patch </code>的内容,仪器将被加载到合并器中由<code>指示的库和程序位置> Patch </code>对象。
     * 
     * 
     * @param soundbank sound bank containing the instrument
     * @param patch the patch of this instrument
     * @param name the name of this instrument
     * @param dataClass the class used to represent the sample's data.
     *
     * @see Synthesizer#loadInstrument(Instrument)
     */
    protected Instrument(Soundbank soundbank, Patch patch, String name, Class<?> dataClass) {

        super(soundbank, name, dataClass);
        this.patch = patch;
    }


    /**
     * Obtains the <code>Patch</code> object that indicates the bank and program
     * numbers where this instrument is to be stored in the synthesizer.
     * <p>
     * 
     * @return this instrument's patch
     */
    public Patch getPatch() {
        return patch;
    }
}
