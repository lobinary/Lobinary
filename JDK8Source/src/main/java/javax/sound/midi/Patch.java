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
 * A <code>Patch</code> object represents a location, on a MIDI
 * synthesizer, into which a single instrument is stored (loaded).
 * Every <code>Instrument</code> object has its own <code>Patch</code>
 * object that specifies the memory location
 * into which that instrument should be loaded. The
 * location is specified abstractly by a bank index and a program number (not by
 * any scheme that directly refers to a specific address or offset in RAM).
 * This is a hierarchical indexing scheme: MIDI provides for up to 16384 banks,
 * each of which contains up to 128 program locations.  For example, a
 * minimal sort of synthesizer might have only one bank of instruments, and
 * only 32 instruments (programs) in that bank.
 * <p>
 * To select what instrument should play the notes on a particular MIDI
 * channel, two kinds of MIDI message are used that specify a patch location:
 * a bank-select command, and a program-change channel command.  The Java Sound
 * equivalent is the
 * {@link MidiChannel#programChange(int, int) programChange(int, int)}
 * method of <code>MidiChannel</code>.
 *
 * <p>
 *  <code> Patch </code>对象表示在MIDI合成器上存储(加载)单个乐器的位置。
 * 每个<code> Instrument </code>对象都有自己的<code> Patch </code>对象,用于指定应加载该仪器的内存位置。
 * 该位置由银行索引和程序编号(不是通过直接引用RAM中的特定地址或偏移的任何方案)抽象地指定。这是一个分层索引方案：MIDI提供多达16384个库,每个库最多包含128个程序位置。
 * 例如,最小类型的合成器可能只有一组乐器,并且在该银行中只有32个乐器(节目)。
 * <p>
 *  为了选择什么乐器应该在特定MIDI通道上播放音符,使用两种MIDI消息来指定补丁位置：存储体选择命令和程序改变通道命令。
 *  Java Sound等价物是<code> MidiChannel </code>的{@link MidiChannel#programChange(int,int)programChange(int,int)}
 * 方法。
 *  为了选择什么乐器应该在特定MIDI通道上播放音符,使用两种MIDI消息来指定补丁位置：存储体选择命令和程序改变通道命令。
 * 
 * 
 * @see Instrument
 * @see Instrument#getPatch()
 * @see MidiChannel#programChange(int, int)
 * @see Synthesizer#loadInstruments(Soundbank, Patch[])
 * @see Soundbank
 * @see Sequence#getPatchList()
 *
 * @author Kara Kytle
 */

public class Patch {


    /**
     * Bank index
     * <p>
     *  银行指数
     * 
     */
    private final int bank;


    /**
     * Program change number
     * <p>
     *  程序更改号
     * 
     */
    private final int program;


    /**
     * Constructs a new patch object from the specified bank and program
     * numbers.
     * <p>
     *  从指定的库和程序编号构造新的修补程序对象。
     * 
     * 
     * @param bank the bank index (in the range from 0 to 16383)
     * @param program the program index (in the range from 0 to 127)
     */
    public Patch(int bank, int program) {

        this.bank = bank;
        this.program = program;
    }


    /**
     * Returns the number of the bank that contains the instrument
     * whose location this <code>Patch</code> specifies.
     * <p>
     *  返回包含该<code> Patch </code>指定位置的仪器的库的编号。
     * 
     * 
     * @return the bank number, whose range is from 0 to 16383
     * @see MidiChannel#programChange(int, int)
     */
    public int getBank() {

        return bank;
    }


    /**
     * Returns the index, within
     * a bank, of the instrument whose location this <code>Patch</code> specifies.
     * <p>
     * 返回此代码<code> Patch </code>指定的仪器的库内的索引。
     * 
     * @return the instrument's program number, whose range is from 0 to 127
     *
     * @see MidiChannel#getProgram
     * @see MidiChannel#programChange(int)
     * @see MidiChannel#programChange(int, int)
     */
    public int getProgram() {

        return program;
    }
}
