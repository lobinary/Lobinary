/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2002, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>VoiceStatus</code> object contains information about the current
 * status of one of the voices produced by a {@link Synthesizer}.
 * <p>
 * MIDI synthesizers are generally capable of producing some maximum number of
 * simultaneous notes, also referred to as voices.  A voice is a stream
 * of successive single notes, and the process of assigning incoming MIDI notes to
 * specific voices is known as voice allocation.
 * However, the voice-allocation algorithm and the contents of each voice are
 * normally internal to a MIDI synthesizer and hidden from outside view.  One can, of
 * course, learn from MIDI messages which notes the synthesizer is playing, and
 * one might be able deduce something about the assignment of notes to voices.
 * But MIDI itself does not provide a means to report which notes a
 * synthesizer has assigned to which voice, nor even to report how many voices
 * the synthesizer is capable of synthesizing.
 * <p>
 * In Java Sound, however, a
 * <code>Synthesizer</code> class can expose the contents of its voices through its
 * {@link Synthesizer#getVoiceStatus() getVoiceStatus()} method.
 * This behavior is recommended but optional;
 * synthesizers that don't expose their voice allocation simply return a
 * zero-length array. A <code>Synthesizer</code> that does report its voice status
 * should maintain this information at
 * all times for all of its voices, whether they are currently sounding or
 * not.  In other words, a given type of <code>Synthesizer</code> always has a fixed
 * number of voices, equal to the maximum number of simultaneous notes it is
 * capable of sounding.
 * <p>
 * <A NAME="description_of_active"></A>
 * If the voice is not currently processing a MIDI note, it
 * is considered inactive.  A voice is inactive when it has
 * been given no note-on commands, or when every note-on command received has
 * been terminated by a corresponding note-off (or by an "all notes off"
 * message).  For example, this happens when a synthesizer capable of playing 16
 * simultaneous notes is told to play a four-note chord; only
 * four voices are active in this case (assuming no earlier notes are still playing).
 * Usually, a voice whose status is reported as active is producing audible sound, but this
 * is not always true; it depends on the details of the instrument (that
 * is, the synthesis algorithm) and how long the note has been going on.
 * For example, a voice may be synthesizing the sound of a single hand-clap.  Because
 * this sound dies away so quickly, it may become inaudible before a note-off
 * message is received.  In such a situation, the voice is still considered active
 * even though no sound is currently being produced.
 * <p>
 * Besides its active or inactive status, the <code>VoiceStatus</code> class
 * provides fields that reveal the voice's current MIDI channel, bank and
 * program number, MIDI note number, and MIDI volume.  All of these can
 * change during the course of a voice.  While the voice is inactive, each
 * of these fields has an unspecified value, so you should check the active
 * field first.
 *
 * <p>
 *  <code> VoiceStatus </code>对象包含有关{@link Synthesizer}生成的其中一个语音的当前状态的信息。
 * <p>
 *  MIDI合成器通常能够产生一些最大数量的同时音符,也称为语音。语音是连续单音符的流,并且将输入MIDI音符分配到特定语音的过程被称为语音分配。
 * 然而,语音分配算法和每个语音的内容通常在MIDI合成器的内部并且从外部视图隐藏。当然,可以从MIDI消息学习合成器正在播放的音符,并且人们可以推断出关于将音符分配给语音的一些事情。
 * 但是MIDI本身不提供报告合成器已经分配给哪个声音的哪个音符的方法,甚至不报告合成器能够合成多少个声音。
 * <p>
 * 然而,在Java Sound中,<code> Synthesizer </code>类可以通过其{@link Synthesizer#getVoiceStatus()getVoiceStatus()}方
 * 法暴露其语音的内容。
 * 此行为是建议的但可选;不暴露其语音分配的合成器简单地返回零长度数组。一个报告其语音状态的<code> Synthesizer </code>应该随时为所有的语音保持这个信息,无论它们当前是否发声。
 * 换句话说,给定类型的<code> Synthesizer </code>总是具有固定数量的语音,等于它能够发出的同时音符的最大数量。
 * <p>
 * <A NAME="description_of_active"> </A>如果语音当前未处理MIDI音符,则被视为无效。
 * 当没有给出音符开命令时,或者当接收的每个音符开命令已经被相应的音符关闭(或通过"所有音符关"消息)终止时,语音是无效的。
 * 例如,当能够播放16个同时音符的合成器被告知播放四音符和弦时,发生这种情况;在这种情况下只有四个声音处于活动状态(假设没有更早的音符仍在播放)。
 * 通常,状态被报告为活动的语音产生可听见的声音,但这并不总是真的;它取决于仪器的细节(即合成算法)以及音符已经持续多长时间。例如,语音可以合成单个手拍的声音。
 * 因为这个声音很快就消失了,所以在收到音符关闭消息之前,它可能变得听不清楚。在这种情况下,即使当前没有声音正在产生,仍然认为语音是活动的。
 * <p>
 *  除了活动或非活动状态,<code> VoiceStatus </code>类提供了显示语音当前MIDI通道,库和程序编号,MIDI音符编号和MIDI音量的字段。所有这些都可以在声音的过程中改变。
 * 当语音处于非活动状态时,每个字段都有一个未指定的值,因此您应该首先检查活动字段。
 * 
 * 
 * @see Synthesizer#getMaxPolyphony
 * @see Synthesizer#getVoiceStatus
 *
 * @author David Rivas
 * @author Kara Kytle
 */

public class VoiceStatus {


    /**
     * Indicates whether the voice is currently processing a MIDI note.
     * See the explanation of
     * <A HREF="#description_of_active">active and inactive voices</A>.
     * <p>
     * 指示语音当前是否正在处理MIDI音符。请参阅<A HREF="#description_of_active">有效和无效声音</A>的说明。
     * 
     */
    public boolean active = false;


    /**
     * The MIDI channel on which this voice is playing.  The value is a
     * zero-based channel number if the voice is active, or
     * unspecified if the voice is inactive.
     *
     * <p>
     *  播放此语音的MIDI通道。如果语音处于活动状态,则该值为基于零的频道号;如果语音处于非活动状态,则该值为未指定。
     * 
     * 
     * @see MidiChannel
     * @see #active
     */
    public int channel = 0;


    /**
     * The bank number of the instrument that this voice is currently using.
     * This is a number dictated by the MIDI bank-select message; it does not
     * refer to a <code>SoundBank</code> object.
     * The value ranges from 0 to 16383 if the voice is active, and is
     * unspecified if the voice is inactive.
     * <p>
     *  此语音当前正在使用的仪器的银行号。这是由MIDI库选择消息指定的数字;它不引用<code> SoundBank </code>对象。
     * 如果语音处于活动状态,该值的范围为0到16383,如果语音处于非活动状态,则该值不受限制。
     * 
     * 
     * @see Patch
     * @see Soundbank
     * @see #active
     * @see MidiChannel#programChange(int, int)
     */
    public int bank = 0;


    /**
     * The program number of the instrument that this voice is currently using.
     * The value ranges from 0 to 127 if the voice is active, and is
     * unspecified if the voice is inactive.
     *
     * <p>
     *  此语音当前正在使用的乐器的节目编号。如果语音处于活动状态,该值的范围为0到127,如果语音处于非活动状态,则该值的范围为未指定。
     * 
     * 
     * @see MidiChannel#getProgram
     * @see Patch
     * @see #active
     */
    public int program = 0;


    /**
     * The MIDI note that this voice is playing.  The range for an active voice
     * is from 0 to 127 in semitones, with 60 referring to Middle C.
     * The value is unspecified if the voice is inactive.
     *
     * <p>
     *  该音色正在播放的MIDI音符。活动语音的范围是半音中从0到127,其中60指代中间C.如果语音处于非活动状态,该值是未指定的。
     * 
     * 
     * @see MidiChannel#noteOn
     * @see #active
     */
    public int note = 0;


    /**
     * The current MIDI volume level for the voice.
     * The value ranges from 0 to 127 if the voice is active, and is
     * unspecified if the voice is inactive.
     * <p>
     * Note that this value does not necessarily reflect
     * the instantaneous level of the sound produced by this
     * voice; that level is the result of  many contributing
     * factors, including the current instrument and the
     * shape of the amplitude envelope it produces.
     *
     * <p>
     *  当前MIDI音量级别的声音。如果语音处于活动状态,该值的范围为0到127,如果语音处于非活动状态,该值的范围为不确定。
     * <p>
     * 
     * @see #active
     */
    public int volume = 0;
}
