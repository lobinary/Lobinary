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

import java.util.Vector;
import com.sun.media.sound.MidiUtils;


/**
 * A <code>Sequence</code> is a data structure containing musical
 * information (often an entire song or composition) that can be played
 * back by a <code>{@link Sequencer}</code> object. Specifically, the
 * <code>Sequence</code> contains timing
 * information and one or more tracks.  Each <code>{@link Track track}</code> consists of a
 * series of MIDI events (such as note-ons, note-offs, program changes, and meta-events).
 * The sequence's timing information specifies the type of unit that is used
 * to time-stamp the events in the sequence.
 * <p>
 * A <code>Sequence</code> can be created from a MIDI file by reading the file
 * into an input stream and invoking one of the <code>getSequence</code> methods of
 * {@link MidiSystem}.  A sequence can also be built from scratch by adding new
 * <code>Tracks</code> to an empty <code>Sequence</code>, and adding
 * <code>{@link MidiEvent}</code> objects to these <code>Tracks</code>.
 *
 * <p>
 *  <code> Sequence </code>是包含可以通过<code> {@ link Sequencer} </code>对象播放的音乐信息(通常是整首歌曲或作曲)的数据结构。
 * 具体地,<code> Sequence </code>包含定时信息和一个或多个轨道。
 * 每个<code> {@ link Track track} </code>包含一系列MIDI事件(例如音符,音符,节目变化和元事件)。序列的定时信息指定用于对序列中的事件加时间戳的单元的类型。
 * <p>
 *  通过将文件读入输入流并调用{@link MidiSystem}的<code> getSequence </code>方法,可以从MIDI文件创建<code> Sequence </code>。
 * 也可以通过向空的<code> Sequence </code>添加新的<code> Track </code>,并将<code> {@ link MidiEvent} </code>对象添加到这些<code>
 *  >曲目</code>。
 *  通过将文件读入输入流并调用{@link MidiSystem}的<code> getSequence </code>方法,可以从MIDI文件创建<code> Sequence </code>。
 * 
 * 
 * @see Sequencer#setSequence(java.io.InputStream stream)
 * @see Sequencer#setSequence(Sequence sequence)
 * @see Track#add(MidiEvent)
 * @see MidiFileFormat
 *
 * @author Kara Kytle
 */
public class Sequence {


    // Timing types

    /**
     * The tempo-based timing type, for which the resolution is expressed in pulses (ticks) per quarter note.
     * <p>
     *  基于速度的计时类型,其分辨率以每四分音符的脉冲(刻度)表示。
     * 
     * 
     * @see #Sequence(float, int)
     */
    public static final float PPQ                                                       = 0.0f;

    /**
     * The SMPTE-based timing type with 24 frames per second (resolution is expressed in ticks per frame).
     * <p>
     *  基于SMPTE的定时类型,每秒24帧(分辨率以每帧的ticks表示)。
     * 
     * 
     * @see #Sequence(float, int)
     */
    public static final float SMPTE_24                                          = 24.0f;

    /**
     * The SMPTE-based timing type with 25 frames per second (resolution is expressed in ticks per frame).
     * <p>
     *  基于SMPTE的定时类型,每秒25帧(分辨率以每帧的ticks表示)。
     * 
     * 
     * @see #Sequence(float, int)
     */
    public static final float SMPTE_25                                          = 25.0f;

    /**
     * The SMPTE-based timing type with 29.97 frames per second (resolution is expressed in ticks per frame).
     * <p>
     *  基于SMPTE的定时类型,每秒29.97帧(分辨率以每帧的ticks表示)。
     * 
     * 
     * @see #Sequence(float, int)
     */
    public static final float SMPTE_30DROP                                      = 29.97f;

    /**
     * The SMPTE-based timing type with 30 frames per second (resolution is expressed in ticks per frame).
     * <p>
     * 基于SMPTE的定时类型,每秒30帧(分辨率表示为每帧的ticks)。
     * 
     * 
     * @see #Sequence(float, int)
     */
    public static final float SMPTE_30                                          = 30.0f;


    // Variables

    /**
     * The timing division type of the sequence.
     * <p>
     *  序列的定时分割类型。
     * 
     * 
     * @see #PPQ
     * @see #SMPTE_24
     * @see #SMPTE_25
     * @see #SMPTE_30DROP
     * @see #SMPTE_30
     * @see #getDivisionType
     */
    protected float divisionType;

    /**
     * The timing resolution of the sequence.
     * <p>
     *  序列的定时分辨率。
     * 
     * 
     * @see #getResolution
     */
    protected int resolution;

    /**
     * The MIDI tracks in this sequence.
     * <p>
     *  这个序列中的MIDI音轨。
     * 
     * 
     * @see #getTracks
     */
    protected Vector<Track> tracks = new Vector<Track>();


    /**
     * Constructs a new MIDI sequence with the specified timing division
     * type and timing resolution.  The division type must be one of the
     * recognized MIDI timing types.  For tempo-based timing,
     * <code>divisionType</code> is PPQ (pulses per quarter note) and
     * the resolution is specified in ticks per beat.  For SMTPE timing,
     * <code>divisionType</code> specifies the number of frames per
     * second and the resolution is specified in ticks per frame.
     * The sequence will contain no initial tracks.  Tracks may be
     * added to or removed from the sequence using <code>{@link #createTrack}</code>
     * and <code>{@link #deleteTrack}</code>.
     *
     * <p>
     *  构造具有指定的时序分割类型和时序分辨率的新MIDI序列。除法类型必须是已识别的MIDI定时类型之一。
     * 对于基于节拍的时序,<code> divisionType </code>是PPQ(每四分音符的脉冲),分辨率以每拍的节拍数指定。
     * 对于SMTPE时序,<code> divisionType </code>指定每秒的帧数,并以每帧的分钟数指定分辨率。序列将不包含初始音轨。
     * 可以使用<code> {@ link #createTrack} </code>和<code> {@ link #deleteTrack} </code>将序列添加到序列中或从中删除序列。
     * 
     * 
     * @param divisionType the timing division type (PPQ or one of the SMPTE types)
     * @param resolution the timing resolution
     * @throws InvalidMidiDataException if <code>divisionType</code> is not valid
     *
     * @see #PPQ
     * @see #SMPTE_24
     * @see #SMPTE_25
     * @see #SMPTE_30DROP
     * @see #SMPTE_30
     * @see #getDivisionType
     * @see #getResolution
     * @see #getTracks
     */
    public Sequence(float divisionType, int resolution) throws InvalidMidiDataException {

        if (divisionType == PPQ)
            this.divisionType = PPQ;
        else if (divisionType == SMPTE_24)
            this.divisionType = SMPTE_24;
        else if (divisionType == SMPTE_25)
            this.divisionType = SMPTE_25;
        else if (divisionType == SMPTE_30DROP)
            this.divisionType = SMPTE_30DROP;
        else if (divisionType == SMPTE_30)
            this.divisionType = SMPTE_30;
        else throw new InvalidMidiDataException("Unsupported division type: " + divisionType);

        this.resolution = resolution;
    }


    /**
     * Constructs a new MIDI sequence with the specified timing division
     * type, timing resolution, and number of tracks.  The division type must be one of the
     * recognized MIDI timing types.  For tempo-based timing,
     * <code>divisionType</code> is PPQ (pulses per quarter note) and
     * the resolution is specified in ticks per beat.  For SMTPE timing,
     * <code>divisionType</code> specifies the number of frames per
     * second and the resolution is specified in ticks per frame.
     * The sequence will be initialized with the number of tracks specified by
     * <code>numTracks</code>. These tracks are initially empty (i.e.
     * they contain only the meta-event End of Track).
     * The tracks may be retrieved for editing using the <code>{@link #getTracks}</code>
     * method.  Additional tracks may be added, or existing tracks removed,
     * using <code>{@link #createTrack}</code> and <code>{@link #deleteTrack}</code>.
     *
     * <p>
     * 构造具有指定的时序分割类型,时序分辨率和轨道数的新MIDI序列。除法类型必须是已识别的MIDI定时类型之一。
     * 对于基于节拍的时序,<code> divisionType </code>是PPQ(每四分音符的脉冲),分辨率以每拍的节拍数指定。
     * 对于SMTPE时序,<code> divisionType </code>指定每秒的帧数,并以每帧的分钟数指定分辨率。序列将使用<code> numTracks </code>指定的轨道数进行初始化。
     * 这些轨道最初为空(即,它们仅包含元事件轨道结束)。您可以使用<code> {@ link #getTracks} </code>方法检索曲目进行编辑。
     * 您可以使用<code> {@ link #createTrack} </code>和<code> {@ link #deleteTrack} </code>添加其他曲目,或删除现有曲目。
     * 
     * 
     * @param divisionType the timing division type (PPQ or one of the SMPTE types)
     * @param resolution the timing resolution
     * @param numTracks the initial number of tracks in the sequence.
     * @throws InvalidMidiDataException if <code>divisionType</code> is not valid
     *
     * @see #PPQ
     * @see #SMPTE_24
     * @see #SMPTE_25
     * @see #SMPTE_30DROP
     * @see #SMPTE_30
     * @see #getDivisionType
     * @see #getResolution
     */
    public Sequence(float divisionType, int resolution, int numTracks) throws InvalidMidiDataException {

        if (divisionType == PPQ)
            this.divisionType = PPQ;
        else if (divisionType == SMPTE_24)
            this.divisionType = SMPTE_24;
        else if (divisionType == SMPTE_25)
            this.divisionType = SMPTE_25;
        else if (divisionType == SMPTE_30DROP)
            this.divisionType = SMPTE_30DROP;
        else if (divisionType == SMPTE_30)
            this.divisionType = SMPTE_30;
        else throw new InvalidMidiDataException("Unsupported division type: " + divisionType);

        this.resolution = resolution;

        for (int i = 0; i < numTracks; i++) {
            tracks.addElement(new Track());
        }
    }


    /**
     * Obtains the timing division type for this sequence.
     * <p>
     *  获取此序列的时序分割类型。
     * 
     * 
     * @return the division type (PPQ or one of the SMPTE types)
     *
     * @see #PPQ
     * @see #SMPTE_24
     * @see #SMPTE_25
     * @see #SMPTE_30DROP
     * @see #SMPTE_30
     * @see #Sequence(float, int)
     * @see MidiFileFormat#getDivisionType()
     */
    public float getDivisionType() {
        return divisionType;
    }


    /**
     * Obtains the timing resolution for this sequence.
     * If the sequence's division type is PPQ, the resolution is specified in ticks per beat.
     * For SMTPE timing, the resolution is specified in ticks per frame.
     *
     * <p>
     *  获取此序列的定时分辨率。如果序列的分割类型是PPQ,则分辨率以每拍的节拍数指定。对于SMTPE计时,分辨率以每帧的计数单位指定。
     * 
     * 
     * @return the number of ticks per beat (PPQ) or per frame (SMPTE)
     * @see #getDivisionType
     * @see #Sequence(float, int)
     * @see MidiFileFormat#getResolution()
     */
    public int getResolution() {
        return resolution;
    }


    /**
     * Creates a new, initially empty track as part of this sequence.
     * The track initially contains the meta-event End of Track.
     * The newly created track is returned.  All tracks in the sequence
     * may be retrieved using <code>{@link #getTracks}</code>.  Tracks may be
     * removed from the sequence using <code>{@link #deleteTrack}</code>.
     * <p>
     *  创建一个新的,最初为空的轨道作为此序列的一部分。轨道最初包含元事件结束轨道。返回新创建的轨道。可以使用<code> {@ link #getTracks} </code>检索序列中的所有曲目。
     * 曲目可以使用<code> {@ link #deleteTrack} </code>从序列中删除。
     * 
     * 
     * @return the newly created track
     */
    public Track createTrack() {

        Track track = new Track();
        tracks.addElement(track);

        return track;
    }


    /**
     * Removes the specified track from the sequence.
     * <p>
     *  从序列中删除指定的音轨。
     * 
     * 
     * @param track the track to remove
     * @return <code>true</code> if the track existed in the track and was removed,
     * otherwise <code>false</code>.
     *
     * @see #createTrack
     * @see #getTracks
     */
    public boolean deleteTrack(Track track) {

        synchronized(tracks) {

            return tracks.removeElement(track);
        }
    }


    /**
     * Obtains an array containing all the tracks in this sequence.
     * If the sequence contains no tracks, an array of length 0 is returned.
     * <p>
     * 获取包含此序列中所有轨道的数组。如果序列不包含磁道,则返回长度为0的数组。
     * 
     * 
     * @return the array of tracks
     *
     * @see #createTrack
     * @see #deleteTrack
     */
    public Track[] getTracks() {

        return (Track[]) tracks.toArray(new Track[tracks.size()]);
    }


    /**
     * Obtains the duration of this sequence, expressed in microseconds.
     * <p>
     *  获取此序列的持续时间,以微秒表示。
     * 
     * 
     * @return this sequence's duration in microseconds.
     */
    public long getMicrosecondLength() {

        return com.sun.media.sound.MidiUtils.tick2microsecond(this, getTickLength(), null);
    }


    /**
     * Obtains the duration of this sequence, expressed in MIDI ticks.
     *
     * <p>
     *  获得此序列的持续时间,以MIDI记号表示。
     * 
     * 
     * @return this sequence's length in ticks
     *
     * @see #getMicrosecondLength
     */
    public long getTickLength() {

        long length = 0;

        synchronized(tracks) {

            for(int i=0; i<tracks.size(); i++ ) {
                long temp = ((Track)tracks.elementAt(i)).ticks();
                if( temp>length ) {
                    length = temp;
                }
            }
            return length;
        }
    }


    /**
     * Obtains a list of patches referenced in this sequence.
     * This patch list may be used to load the required
     * <code>{@link Instrument}</code> objects
     * into a <code>{@link Synthesizer}</code>.
     *
     * <p>
     *  获取此序列中引用的修补程序列表。
     * 此修补程序列表可用于将所需的<code> {@ link Instrument} </code>对象加载到<code> {@ link Synthesizer} </code>中。
     * 
     * @return an array of <code>{@link Patch}</code> objects used in this sequence
     *
     * @see Synthesizer#loadInstruments(Soundbank, Patch[])
     */
    public Patch[] getPatchList() {

        // $$kk: 04.09.99: need to implement!!
        return new Patch[0];
    }
}
