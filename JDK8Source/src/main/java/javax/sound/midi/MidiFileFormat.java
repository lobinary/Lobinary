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

import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * A <code>MidiFileFormat</code> object encapsulates a MIDI file's
 * type, as well as its length and timing information.
 *
 * <p>A <code>MidiFileFormat</code> object can
 * include a set of properties. A property is a pair of key and value:
 * the key is of type <code>String</code>, the associated property
 * value is an arbitrary object.
 * Properties specify additional informational
 * meta data (like a author, or copyright).
 * Properties are optional information, and file reader and file
 * writer implementations are not required to provide or
 * recognize properties.
 *
 * <p>The following table lists some common properties that should
 * be used in implementations:
 *
 * <table border=1>
    <caption>MIDI File Format Properties</caption>
 *  <tr>
 *   <th>Property key</th>
 *   <th>Value type</th>
 *   <th>Description</th>
 *  </tr>
 *  <tr>
 *   <td>&quot;author&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>name of the author of this file</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;title&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>title of this file</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;copyright&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>copyright message</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;date&quot;</td>
 *   <td>{@link java.util.Date Date}</td>
 *   <td>date of the recording or release</td>
 *  </tr>
 *  <tr>
 *   <td>&quot;comment&quot;</td>
 *   <td>{@link java.lang.String String}</td>
 *   <td>an arbitrary text</td>
 *  </tr>
 * </table>
 *
 * <p>
 *  一个<code> MidiFileFormat </code>对象封装了一个MIDI文件的类型,以及它的长度和定时信息。
 * 
 *  <p> <code> MidiFileFormat </code>对象可以包含一组属性。属性是一对键和值：键是<code> String </code>类型,关联的属性值是一个任意对象。
 * 属性指定附加的信息元数据(如作者或版权)。属性是可选信息,并且文件读取器和文件写入器实现不需要提供或识别属性。
 * 
 *  <p>下表列出了应在实施中使用的一些常见属性：
 * 
 * <table border=1>
 *  <caption> MIDI文件格式属性</caption>
 * <tr>
 *  <th>属性键</th> <th>值类型</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td>&quot; author&quot; </td> <td> {@ link java.lang.String String} </td> <td>此文件作者的姓名</td>
 * </tr>
 * <tr>
 *  <td>&quot; title&quot; </td> <td> {@ link java.lang.String String} </td> <td>此文件的标题</td>
 * </tr>
 * <tr>
 *  <td>&quot; copyright&quot; </td> <td> {@ link java.lang.String String} </td> <td>版权讯息</td>
 * </tr>
 * <tr>
 *  <td>"date"</td> <td> {@ link java.util.Date Date} </td> <td>录制或发布日期</td>
 * </tr>
 * <tr>
 *  <td>&quot; comment&quot; </td> <td> {@ link java.lang.String String} </td> <td>任意文字</td>
 * </tr>
 * </table>
 * 
 * 
 * @see MidiSystem#getMidiFileFormat(java.io.File)
 * @see Sequencer#setSequence(java.io.InputStream stream)
 *
 * @author Kara Kytle
 * @author Florian Bomers
 */

public class MidiFileFormat {


    /**
     * Represents unknown length.
     * <p>
     *  表示未知长度。
     * 
     * 
     * @see #getByteLength
     * @see #getMicrosecondLength
     */
    public static final int UNKNOWN_LENGTH = -1;


    /**
     * The type of MIDI file.
     * <p>
     *  MIDI文件的类型。
     * 
     */
    protected int type;

    /**
     * The division type of the MIDI file.
     *
     * <p>
     *  MIDI文件的分割类型。
     * 
     * 
     * @see Sequence#PPQ
     * @see Sequence#SMPTE_24
     * @see Sequence#SMPTE_25
     * @see Sequence#SMPTE_30DROP
     * @see Sequence#SMPTE_30
     */
    protected float divisionType;

    /**
     * The timing resolution of the MIDI file.
     * <p>
     * MIDI文件的定时分辨率。
     * 
     */
    protected int resolution;

    /**
     * The length of the MIDI file in bytes.
     * <p>
     *  MIDI文件的长度(以字节为单位)。
     * 
     */
    protected int byteLength;

    /**
     * The duration of the MIDI file in microseconds.
     * <p>
     *  MIDI文件的持续时间(以微秒为单位)。
     * 
     */
    protected long microsecondLength;


    /** The set of properties */
    private HashMap<String, Object> properties;


    /**
     * Constructs a <code>MidiFileFormat</code>.
     *
     * <p>
     *  构造一个<code> MidiFileFormat </code>。
     * 
     * 
     * @param type the MIDI file type (0, 1, or 2)
     * @param divisionType the timing division type (PPQ or one of the SMPTE types)
     * @param resolution the timing resolution
     * @param bytes the length of the MIDI file in bytes, or UNKNOWN_LENGTH if not known
     * @param microseconds the duration of the file in microseconds, or UNKNOWN_LENGTH if not known
     * @see #UNKNOWN_LENGTH
     * @see Sequence#PPQ
     * @see Sequence#SMPTE_24
     * @see Sequence#SMPTE_25
     * @see Sequence#SMPTE_30DROP
     * @see Sequence#SMPTE_30
     */
    public MidiFileFormat(int type, float divisionType, int resolution, int bytes, long microseconds) {

        this.type = type;
        this.divisionType = divisionType;
        this.resolution = resolution;
        this.byteLength = bytes;
        this.microsecondLength = microseconds;
        this.properties = null;
    }


    /**
     * Construct a <code>MidiFileFormat</code> with a set of properties.
     *
     * <p>
     *  用一组属性构造一个<code> MidiFileFormat </code>。
     * 
     * 
     * @param type         the MIDI file type (0, 1, or 2)
     * @param divisionType the timing division type
     *      (PPQ or one of the SMPTE types)
     * @param resolution   the timing resolution
     * @param bytes the length of the MIDI file in bytes,
     *      or UNKNOWN_LENGTH if not known
     * @param microseconds the duration of the file in microseconds,
     *      or UNKNOWN_LENGTH if not known
     * @param properties  a <code>Map&lt;String,Object&gt;</code> object
     *        with properties
     *
     * @see #UNKNOWN_LENGTH
     * @see Sequence#PPQ
     * @see Sequence#SMPTE_24
     * @see Sequence#SMPTE_25
     * @see Sequence#SMPTE_30DROP
     * @see Sequence#SMPTE_30
     * @since 1.5
     */
    public MidiFileFormat(int type, float divisionType,
                          int resolution, int bytes,
                          long microseconds, Map<String, Object> properties) {
        this(type, divisionType, resolution, bytes, microseconds);
        this.properties = new HashMap<String, Object>(properties);
    }



    /**
     * Obtains the MIDI file type.
     * <p>
     *  获取MIDI文件类型。
     * 
     * 
     * @return the file's type (0, 1, or 2)
     */
    public int getType() {
        return type;
    }

    /**
     * Obtains the timing division type for the MIDI file.
     *
     * <p>
     *  获取MIDI文件的时序分割类型。
     * 
     * 
     * @return the division type (PPQ or one of the SMPTE types)
     *
     * @see Sequence#Sequence(float, int)
     * @see Sequence#PPQ
     * @see Sequence#SMPTE_24
     * @see Sequence#SMPTE_25
     * @see Sequence#SMPTE_30DROP
     * @see Sequence#SMPTE_30
     * @see Sequence#getDivisionType()
     */
    public float getDivisionType() {
        return divisionType;
    }


    /**
     * Obtains the timing resolution for the MIDI file.
     * If the division type is PPQ, the resolution is specified in ticks per beat.
     * For SMTPE timing, the resolution is specified in ticks per frame.
     *
     * <p>
     *  获取MIDI文件的定时分辨率。如果分割类型是PPQ,则分辨率以每拍的节拍数指定。对于SMTPE计时,分辨率以每帧的计数单位指定。
     * 
     * 
     * @return the number of ticks per beat (PPQ) or per frame (SMPTE)
     * @see #getDivisionType
     * @see Sequence#getResolution()
     */
    public int getResolution() {
        return resolution;
    }


    /**
     * Obtains the length of the MIDI file, expressed in 8-bit bytes.
     * <p>
     *  获取MIDI文件的长度,以8位字节表示。
     * 
     * 
     * @return the number of bytes in the file, or UNKNOWN_LENGTH if not known
     * @see #UNKNOWN_LENGTH
     */
    public int getByteLength() {
        return byteLength;
    }

    /**
     * Obtains the length of the MIDI file, expressed in microseconds.
     * <p>
     *  获取MIDI文件的长度,单位为微秒。
     * 
     * 
     * @return the file's duration in microseconds, or UNKNOWN_LENGTH if not known
     * @see Sequence#getMicrosecondLength()
     * @see #getByteLength
     * @see #UNKNOWN_LENGTH
     */
    public long getMicrosecondLength() {
        return microsecondLength;
    }

    /**
     * Obtain an unmodifiable map of properties.
     * The concept of properties is further explained in
     * the {@link MidiFileFormat class description}.
     *
     * <p>
     *  获取一个不可修改的属性映射。属性的概念在{@link MidiFileFormat类描述}中进一步解释。
     * 
     * 
     * @return a <code>Map&lt;String,Object&gt;</code> object containing
     *         all properties. If no properties are recognized, an empty map is
     *         returned.
     *
     * @see #getProperty(String)
     * @since 1.5
     */
    public Map<String,Object> properties() {
        Map<String,Object> ret;
        if (properties == null) {
            ret = new HashMap<String,Object>(0);
        } else {
            ret = (Map<String,Object>) (properties.clone());
        }
        return (Map<String,Object>) Collections.unmodifiableMap(ret);
    }


    /**
     * Obtain the property value specified by the key.
     * The concept of properties is further explained in
     * the {@link MidiFileFormat class description}.
     *
     * <p>If the specified property is not defined for a
     * particular file format, this method returns
     * <code>null</code>.
     *
     * <p>
     *  获取由键指定的属性值。属性的概念在{@link MidiFileFormat类描述}中进一步解释。
     * 
     * 
     * @param key the key of the desired property
     * @return the value of the property with the specified key,
     *         or <code>null</code> if the property does not exist.
     *
     * @see #properties()
     * @since 1.5
     */
    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }


}
