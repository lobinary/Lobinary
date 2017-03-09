/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>Soundbank</code> contains a set of <code>Instruments</code>
 * that can be loaded into a <code>Synthesizer</code>.
 * Note that a Java Sound <code>Soundbank</code> is different from a MIDI bank.
 * MIDI permits up to 16383 banks, each containing up to 128 instruments
 * (also sometimes called programs, patches, or timbres).
 * However, a <code>Soundbank</code> can contain 16383 times 128 instruments,
 * because the instruments within a <code>Soundbank</code> are indexed by both
 * a MIDI program number and a MIDI bank number (via a <code>Patch</code>
 * object). Thus, a <code>Soundbank</code> can be thought of as a collection
 * of MIDI banks.
 * <p>
 * <code>Soundbank</code> includes methods that return <code>String</code>
 * objects containing the sound bank's name, manufacturer, version number, and
 * description.  The precise content and format of these strings is left
 * to the implementor.
 * <p>
 * Different synthesizers use a variety of synthesis techniques.  A common
 * one is wavetable synthesis, in which a segment of recorded sound is
 * played back, often with looping and pitch change.  The Downloadable Sound
 * (DLS) format uses segments of recorded sound, as does the Headspace Engine.
 * <code>Soundbanks</code> and <code>Instruments</code> that are based on
 * wavetable synthesis (or other uses of stored sound recordings) should
 * typically implement the <code>getResources()</code>
 * method to provide access to these recorded segments.  This is optional,
 * however; the method can return an zero-length array if the synthesis technique
 * doesn't use sampled sound (FM synthesis and physical modeling are examples
 * of such techniques), or if it does but the implementor chooses not to make the
 * samples accessible.
 *
 * <p>
 *  <code> Soundbank </code>包含一组可以加载到<code> Synthesizer </code>中的<code> Instruments </code>。
 * 注意,Java Sound <code> Soundbank </code>与MIDI库不同。 MIDI允许最多16383个存储库,每个存储库最多包含128个乐器(有时也称为程序,音色或音色)。
 * 然而,<code> Soundbank </code>可以包含16383乘以128个乐器,因为<code> Soundbank </code>中的乐器由MIDI程序编号和MIDI库编号索引(通过<code>
 *  Patch </code>对象)。
 * 注意,Java Sound <code> Soundbank </code>与MIDI库不同。 MIDI允许最多16383个存储库,每个存储库最多包含128个乐器(有时也称为程序,音色或音色)。
 * 因此,<code> Soundbank </code>可以被认为是MIDI库的集合。
 * <p>
 *  <code> Soundbank </code>包括返回包含声音库名称,制造商,版本号和描述的<code> String </code>对象的方法。这些字符串的精确内容和格式留给实现者。
 * <p>
 * 不同的合成器使用各种合成技术。常见的是波表合成,其中记录的声音的段被回放,通常具有循环和音调改变。可下载声音(DLS)格式使用记录的声音段,如同顶空引擎。
 * 基于波表合成(或存储的声音记录的其他用途)的<code> Soundbanks </code>和<code> Instruments </code>应当典型地实现<code> getResources(
 * )</code>到这些记录的段。
 * 不同的合成器使用各种合成技术。常见的是波表合成,其中记录的声音的段被回放,通常具有循环和音调改变。可下载声音(DLS)格式使用记录的声音段,如同顶空引擎。
 * 这是可选的,但是;如果合成技术不使用采样声音(FM合成和物理建模是这些技术的示例),或者如果它是但是实现者选择不使样本可访问,则该方法可以返回零长度数组。
 * 
 * 
 * @see Synthesizer#getDefaultSoundbank
 * @see Synthesizer#isSoundbankSupported
 * @see Synthesizer#loadInstruments(Soundbank, Patch[])
 * @see Patch
 * @see Instrument
 * @see SoundbankResource
 *
 * @author David Rivas
 * @author Kara Kytle
 */

public interface Soundbank {


    /**
     * Obtains the name of the sound bank.
     * <p>
     *  获取声音库的名称。
     * 
     * 
     * @return a <code>String</code> naming the sound bank
     */
    public String getName();

    /**
     * Obtains the version string for the sound bank.
     * <p>
     *  获取声音库的版本字符串。
     * 
     * 
     * @return a <code>String</code> that indicates the sound bank's version
     */
    public String getVersion();

    /**
     * Obtains a <code>string</code> naming the company that provides the
     * sound bank
     * <p>
     *  获取一个<code> string </code>命名提供声音库的公司
     * 
     * 
     * @return the vendor string
     */
    public String getVendor();

    /**
     * Obtains a textual description of the sound bank, suitable for display.
     * <p>
     *  获得适合显示的声音库的文本描述。
     * 
     * 
     * @return a <code>String</code> that describes the sound bank
     */
    public String getDescription();


    /**
     * Extracts a list of non-Instrument resources contained in the sound bank.
     * <p>
     *  提取声音库中包含的非仪器资源的列表。
     * 
     * 
     * @return an array of resources, excluding instruments.  If the sound bank contains
     * no resources (other than instruments), returns an array of length 0.
     */
    public SoundbankResource[] getResources();


    /**
     * Obtains a list of instruments contained in this sound bank.
     * <p>
     *  获取此声音库中包含的乐器的列表。
     * 
     * 
     * @return an array of the <code>Instruments</code> in this
     * <code>SoundBank</code>
     * If the sound bank contains no instruments, returns an array of length 0.
     *
     * @see Synthesizer#getLoadedInstruments
     * @see #getInstrument(Patch)
     */
    public Instrument[] getInstruments();

    /**
     * Obtains an <code>Instrument</code> from the given <code>Patch</code>.
     * <p>
     *  从给定的<code> Patch </code>获取<code> Instrument </code>。
     * 
     * @param patch a <code>Patch</code> object specifying the bank index
     * and program change number
     * @return the requested instrument, or <code>null</code> if the
     * sound bank doesn't contain that instrument
     *
     * @see #getInstruments
     * @see Synthesizer#loadInstruments(Soundbank, Patch[])
     */
    public Instrument getInstrument(Patch patch);


}
