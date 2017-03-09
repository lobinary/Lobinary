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

package javax.sound.midi;

import java.util.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import com.sun.media.sound.MidiUtils;

/**
 * A MIDI track is an independent stream of MIDI events (time-stamped MIDI
 * data) that can be stored along with other tracks in a standard MIDI file.
 * The MIDI specification allows only 16 channels of MIDI data, but tracks
 * are a way to get around this limitation.  A MIDI file can contain any number
 * of tracks, each containing its own stream of up to 16 channels of MIDI data.
 * <p>
 * A <code>Track</code> occupies a middle level in the hierarchy of data played
 * by a <code>{@link Sequencer}</code>: sequencers play sequences, which contain tracks,
 * which contain MIDI events.  A sequencer may provide controls that mute
 * or solo individual tracks.
 * <p>
 * The timing information and resolution for a track is controlled by and stored
 * in the sequence containing the track. A given <code>Track</code>
 * is considered to belong to the particular <code>{@link Sequence}</code> that
 * maintains its timing. For this reason, a new (empty) track is created by calling the
 * <code>{@link Sequence#createTrack}</code> method, rather than by directly invoking a
 * <code>Track</code> constructor.
 * <p>
 * The <code>Track</code> class provides methods to edit the track by adding
 * or removing <code>MidiEvent</code> objects from it.  These operations keep
 * the event list in the correct time order.  Methods are also
 * included to obtain the track's size, in terms of either the number of events
 * it contains or its duration in ticks.
 *
 * <p>
 *  MIDI轨道是独立的MIDI事件流(带时间戳的MIDI数据),可以与标准MIDI文件中的其他轨道一起存储。 MIDI规格只允许16声道的MIDI数据,但是轨道是一种解决这个限制的方法。
 *  MIDI文件可以包含任何数量的轨道,每个轨道包含其自己的流的多达16个通道的MIDI数据。
 * <p>
 *  <code> Track </code>占据由<code> {@ link Sequencer} </code>：序列器播放序列播放的数据层次结构的中间层,其中包含包含MIDI事件的轨道。
 * 定序器可以提供使各个轨道静音或独奏的控制。
 * <p>
 *  轨道的定时信息和分辨率由包含轨道的序列控制并存储在其中。给定的<code> Track </code>被认为属于保持其时序的特定<code> {@ link Sequence} </code>。
 * 因此,通过调用<code> {@ link Sequence#createTrack} </code>方法而不是直接调用<code> Track </code>构造函数来创建新的(空)轨道。
 * <p>
 * <code> Track </code>类提供了通过添加或删除<code> MidiEvent </code>对象来编辑轨道的方法。这些操作按正确的时间顺序保留事件列表。
 * 还包括方法以根据其包含的事件的数量或其在tick中的持续时间来获得轨道的大小。
 * 
 * 
 * @see Sequencer#setTrackMute
 * @see Sequencer#setTrackSolo
 *
 * @author Kara Kytle
 * @author Florian Bomers
 */
public class Track {

    // TODO: use arrays for faster access

    // the list containing the events
    private ArrayList eventsList = new ArrayList();

    // use a hashset to detect duplicate events in add(MidiEvent)
    private HashSet set = new HashSet();

    private MidiEvent eotEvent;


    /**
     * Package-private constructor.  Constructs a new, empty Track object,
     * which initially contains one event, the meta-event End of Track.
     * <p>
     *  包私有构造函数。构造一个新的,空的Track对象,它最初包含一个事件,元事件End of Track。
     * 
     */
    Track() {
        // start with the end of track event
        MetaMessage eot = new ImmutableEndOfTrack();
        eotEvent = new MidiEvent(eot, 0);
        eventsList.add(eotEvent);
        set.add(eotEvent);
    }

    /**
     * Adds a new event to the track.  However, if the event is already
     * contained in the track, it is not added again.  The list of events
     * is kept in time order, meaning that this event inserted at the
     * appropriate place in the list, not necessarily at the end.
     *
     * <p>
     *  向轨道添加新事件。但是,如果事件已包含在轨道中,则不会再添加。事件列表按时间顺序保存,这意味着此事件在列表中的适当位置插入,不一定在结尾。
     * 
     * 
     * @param event the event to add
     * @return <code>true</code> if the event did not already exist in the
     * track and was added, otherwise <code>false</code>
     */
    public boolean add(MidiEvent event) {
        if (event == null) {
            return false;
        }
        synchronized(eventsList) {

            if (!set.contains(event)) {
                int eventsCount = eventsList.size();

                // get the last event
                MidiEvent lastEvent = null;
                if (eventsCount > 0) {
                    lastEvent = (MidiEvent) eventsList.get(eventsCount - 1);
                }
                // sanity check that we have a correct end-of-track
                if (lastEvent != eotEvent) {
                    // if there is no eot event, add our immutable instance again
                    if (lastEvent != null) {
                        // set eotEvent's tick to the last tick of the track
                        eotEvent.setTick(lastEvent.getTick());
                    } else {
                        // if the events list is empty, just set the tick to 0
                        eotEvent.setTick(0);
                    }
                    // we needn't check for a duplicate of eotEvent in "eventsList",
                    // since then it would appear in the set.
                    eventsList.add(eotEvent);
                    set.add(eotEvent);
                    eventsCount = eventsList.size();
                }

                // first see if we are trying to add
                // and endoftrack event.
                if (MidiUtils.isMetaEndOfTrack(event.getMessage())) {
                    // since end of track event is useful
                    // for delays at the end of a track, we want to keep
                    // the tick value requested here if it is greater
                    // than the one on the eot we are maintaining.
                    // Otherwise, we only want a single eot event, so ignore.
                    if (event.getTick() > eotEvent.getTick()) {
                        eotEvent.setTick(event.getTick());
                    }
                    return true;
                }

                // prevent duplicates
                set.add(event);

                // insert event such that events is sorted in increasing
                // tick order
                int i = eventsCount;
                for ( ; i > 0; i--) {
                    if (event.getTick() >= ((MidiEvent)eventsList.get(i-1)).getTick()) {
                        break;
                    }
                }
                if (i == eventsCount) {
                    // we're adding an event after the
                    // tick value of our eot, so push the eot out.
                    // Always add at the end for better performance:
                    // this saves all the checks and arraycopy when inserting

                    // overwrite eot with new event
                    eventsList.set(eventsCount - 1, event);
                    // set new time of eot, if necessary
                    if (eotEvent.getTick() < event.getTick()) {
                        eotEvent.setTick(event.getTick());
                    }
                    // add eot again at the end
                    eventsList.add(eotEvent);
                } else {
                    eventsList.add(i, event);
                }
                return true;
            }
        }

        return false;
    }


    /**
     * Removes the specified event from the track.
     * <p>
     *  从轨道中删除指定的事件。
     * 
     * 
     * @param event the event to remove
     * @return <code>true</code> if the event existed in the track and was removed,
     * otherwise <code>false</code>
     */
    public boolean remove(MidiEvent event) {

        // this implementation allows removing the EOT event.
        // pretty bad, but would probably be too risky to
        // change behavior now, in case someone does tricks like:
        //
        // while (track.size() > 0) track.remove(track.get(track.size() - 1));

        // also, would it make sense to adjust the EOT's time
        // to the last event, if the last non-EOT event is removed?
        // Or: document that the ticks() length will not be reduced
        // by deleting events (unless the EOT event is removed)
        synchronized(eventsList) {
            if (set.remove(event)) {
                int i = eventsList.indexOf(event);
                if (i >= 0) {
                    eventsList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Obtains the event at the specified index.
     * <p>
     *  在指定的索引处获取事件。
     * 
     * 
     * @param index the location of the desired event in the event vector
     * @throws ArrayIndexOutOfBoundsException  if the
     * specified index is negative or not less than the current size of
     * this track.
     * @see #size
     * @return the event at the specified index
     */
    public MidiEvent get(int index) throws ArrayIndexOutOfBoundsException {
        try {
            synchronized(eventsList) {
                return (MidiEvent)eventsList.get(index);
            }
        } catch (IndexOutOfBoundsException ioobe) {
            throw new ArrayIndexOutOfBoundsException(ioobe.getMessage());
        }
    }


    /**
     * Obtains the number of events in this track.
     * <p>
     *  获取此轨道中的事件数。
     * 
     * 
     * @return the size of the track's event vector
     */
    public int size() {
        synchronized(eventsList) {
            return eventsList.size();
        }
    }


    /**
     * Obtains the length of the track, expressed in MIDI ticks.  (The
     * duration of a tick in seconds is determined by the timing resolution
     * of the <code>Sequence</code> containing this track, and also by
     * the tempo of the music as set by the sequencer.)
     * <p>
     *  获取轨道的长度,以MIDI刻度表示。 (以秒为单位的节拍持续时间由包含此曲目的<code> Sequence </code>的定时分辨率决定,也由音序器设置的音乐节奏决定)。
     * 
     * @return the duration, in ticks
     * @see Sequence#Sequence(float, int)
     * @see Sequencer#setTempoInBPM(float)
     * @see Sequencer#getTickPosition()
     */
    public long ticks() {
        long ret = 0;
        synchronized (eventsList) {
            if (eventsList.size() > 0) {
                ret = ((MidiEvent)eventsList.get(eventsList.size() - 1)).getTick();
            }
        }
        return ret;
    }

    private static class ImmutableEndOfTrack extends MetaMessage {
        private ImmutableEndOfTrack() {
            super(new byte[3]);
            data[0] = (byte) META;
            data[1] = MidiUtils.META_END_OF_TRACK_TYPE;
            data[2] = 0;
        }

        public void setMessage(int type, byte[] data, int length) throws InvalidMidiDataException {
            throw new InvalidMidiDataException("cannot modify end of track message");
        }
    }

}
