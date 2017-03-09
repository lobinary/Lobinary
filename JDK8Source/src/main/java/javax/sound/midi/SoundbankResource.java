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

/**
 * A <code>SoundbankResource</code> represents any audio resource stored
 * in a <code>{@link Soundbank}</code>.  Common soundbank resources include:
 * <ul>
 * <li>Instruments.  An instrument may be specified in a variety of
 * ways.  However, all soundbanks have some mechanism for defining
 * instruments.  In doing so, they may reference other resources
 * stored in the soundbank.  Each instrument has a <code>Patch</code>
 * which specifies the MIDI program and bank by which it may be
 * referenced in MIDI messages.  Instrument information may be
 * stored in <code>{@link Instrument}</code> objects.
 * <li>Audio samples.  A sample typically is a sampled audio waveform
 * which contains a short sound recording whose duration is a fraction of
 * a second, or at most a few seconds.  These audio samples may be
 * used by a <code>{@link Synthesizer}</code> to synthesize sound in response to MIDI
 * commands, or extracted for use by an application.
 * (The terminology reflects musicians' use of the word "sample" to refer
 * collectively to a series of contiguous audio samples or frames, rather than
 * to a single, instantaneous sample.)
 * The data class for an audio sample will be an object
 * that encapsulates the audio sample data itself and information
 * about how to interpret it (the format of the audio data), such
 * as an <code>{@link javax.sound.sampled.AudioInputStream}</code>.     </li>
 * <li>Embedded sequences.  A sound bank may contain built-in
 * song data stored in a data object such as a <code>{@link Sequence}</code>.
 * </ul>
 * <p>
 * Synthesizers that use wavetable synthesis or related
 * techniques play back the audio in a sample when
 * synthesizing notes, often when emulating the real-world instrument that
 * was originally recorded.  However, there is not necessarily a one-to-one
 * correspondence between the <code>Instruments</code> and samples
 * in a <code>Soundbank</code>.  A single <code>Instrument</code> can use
 * multiple SoundbankResources (typically for notes of dissimilar pitch or
 * brightness).  Also, more than one <code>Instrument</code> can use the same
 * sample.
 *
 * <p>
 *  <code> SoundbankResource </code>表示存储在<code> {@ link Soundbank} </code>中的任何音频资源。常用音箱资源包括：
 * <ul>
 * <li>乐器。可以以多种方式指定仪器。然而,所有声音库都有一些机制来定义乐器。在这样做时,他们可以引用存储在声音库中的其他资源。
 * 每个乐器都有一个<code> Patch </code>,它指定MIDI程序和可以在MIDI消息中引用的程序库。
 * 仪器信息可存储在<code> {@ link Instrument} </code>对象中。 <li>音频示例。采样通常是采样的音频波形,其包含短的声音记录,其持续时间是一秒的一部分,或者至多几秒。
 * 这些音频样本可以由<code> {@ link Synthesizer} </code>使用以响应于MIDI命令来合成声音,或者被提取以供应用使用。
 *  (该术语反映了音乐家对单词"sample"的使用以共同地指代一系列连续的音频样本或帧,而不是单个瞬时样本。
 * )音频样本的数据类将是封装音频样本数据本身以及关于如何解释它(音频数据的格式)的信息,例如<code> {@ link javax.sound.sampled.AudioInputStream} </code>
 * 。
 *  (该术语反映了音乐家对单词"sample"的使用以共同地指代一系列连续的音频样本或帧,而不是单个瞬时样本。 </li> <li>嵌入序列。
 * 声音库可以包含存储在数据对象中的内置歌曲数据,例如<code> {@ link Sequence} </code>。
 * </ul>
 * <p>
 * 使用波表合成或相关技术的合成器在合成音符时回放样本中的音频,通常在模拟最初记录的真实世界仪器时。
 * 然而,<code> Instruments </code>和<code> Soundbank </code>中的样本之间不一定是一一对应的。
 * 单个<code> Instrument </code>可以使用多个SoundbankResources(通常用于不同音高或亮度的音符)。
 * 此外,多个<code> Instrument </code>可以使用同一个示例。
 * 
 * 
 * @author Kara Kytle
 */

public abstract class SoundbankResource {


    /**
     * The sound bank that contains the <code>SoundbankResources</code>
     * <p>
     *  包含<code> SoundbankResources </code>的声音库
     * 
     */
    private final Soundbank soundBank;


    /**
     * The name of the <code>SoundbankResource</code>
     * <p>
     *  <code> SoundbankResource </code>的名称
     * 
     */
    private final String name;


    /**
     * The class used to represent the sample's data.
     * <p>
     *  用于表示样本数据的类。
     * 
     */
    private final Class dataClass;


    /**
     * The wavetable index.
     * <p>
     *  波表指数。
     * 
     */
    //private final int index;


    /**
     * Constructs a new <code>SoundbankResource</code> from the given sound bank
     * and wavetable index.  (Setting the <code>SoundbankResource's</code> name,
     * sampled audio data, and instruments is a subclass responsibility.)
     * <p>
     *  从给定的声音库和wavetable索引构造一个新的<code> SoundbankResource </code>。
     *  (设置<code> SoundbankResource的</code>名称,采样音频数据和乐器是一个子类责任。)。
     * 
     * 
     * @param soundBank the sound bank containing this <code>SoundbankResource</code>
     * @param name the name of the sample
     * @param dataClass the class used to represent the sample's data
     *
     * @see #getSoundbank
     * @see #getName
     * @see #getDataClass
     * @see #getData
     */
    protected SoundbankResource(Soundbank soundBank, String name, Class<?> dataClass) {

        this.soundBank = soundBank;
        this.name = name;
        this.dataClass = dataClass;
    }


    /**
     * Obtains the sound bank that contains this <code>SoundbankResource</code>.
     * <p>
     *  获取包含此<Sound> SoundbankResource </code>的声音库。
     * 
     * 
     * @return the sound bank in which this <code>SoundbankResource</code> is stored
     */
    public Soundbank getSoundbank() {
        return soundBank;
    }


    /**
     * Obtains the name of the resource.  This should generally be a string
     * descriptive of the resource.
     * <p>
     *  获取资源的名称。这通常应该是一个描述资源的字符串。
     * 
     * 
     * @return the instrument's name
     */
    public String getName() {
        return name;
    }


    /**
     * Obtains the class used by this sample to represent its data.
     * The object returned by <code>getData</code> will be of this
     * class.  If this <code>SoundbankResource</code> object does not support
     * direct access to its data, returns <code>null</code>.
     * <p>
     *  获取此样本用来表示其数据的类。 <code> getData </code>返回的对象将是此类的对象。
     * 如果这个<code> SoundbankResource </code>对象不支持直接访问它的数据,返回<code> null </code>。
     * 
     * 
     * @return the class used to represent the sample's data, or
     * null if the data is not accessible
     */
    public Class<?> getDataClass() {
        return dataClass;
    }


    /**
     * Obtains the sampled audio that is stored in this <code>SoundbankResource</code>.
     * The type of object returned depends on the implementation of the
     * concrete class, and may be queried using <code>getDataClass</code>.
     * <p>
     * 获取存储在此<code> SoundbankResource </code>中的采样音频。返回的对象类型取决于具体类的实现,并且可以使用<code> getDataClass </code>查询。
     * 
     * 
     * @return an object containing the sampled audio data
     * @see #getDataClass
     */
    public abstract Object getData();


    /**
     * Obtains the index of this <code>SoundbankResource</code> into the
     * <code>Soundbank's</code> set of <code>SoundbankResources</code>.
     * <p>
     *  获取<code> SoundbankResource </code>的索引到<code> Soundbank的</code>集合<code> SoundbankResources </code>中。
     * 
     * 
     * @return the wavetable index
     */
    //public int getIndex() {
    //  return index;
    //}


    /**
     * Obtains a list of the instruments in the sound bank that use the
     * <code>SoundbankResource</code> for sound synthesis.
     * <p>
     *  获取声音库中使用<code> SoundbankResource </code>进行声音合成的乐器的列表。
     * 
     * @return an array of <code>Instruments</code> that reference this
     * <code>SoundbankResource</code>
     *
     * @see Instrument#getSamples
     */
    //public abstract Instrument[] getInstruments();
}
