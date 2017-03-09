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

package javax.sound.sampled;


/**
 * Ports are simple lines for input or output of audio to or from audio devices.
 * Common examples of ports that act as source lines (mixer inputs) include the microphone,
 * line input, and CD-ROM drive.  Ports that act as target lines (mixer outputs) include the
 * speaker, headphone, and line output.  You can access port using a <code>{@link Port.Info}</code>
 * object.
 *
 * <p>
 *  端口是用于将音频输入到音频设备或从音频设备输出音频的简单线路。作为源线路(混频器输入)的端口的常见示例包括麦克风,线路输入和CD-ROM驱动器。
 * 充当目标线(混频器输出)的端口包括扬声器,耳机和线路输出。您可以使用<code> {@ link Port.Info} </code>对象访问端口。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public interface Port extends Line {


    // INNER CLASSES


    /**
     * The <code>Port.Info</code> class extends <code>{@link Line.Info}</code>
     * with additional information specific to ports, including the port's name
     * and whether it is a source or a target for its mixer.
     * By definition, a port acts as either a source or a target to its mixer,
     * but not both.  (Audio input ports are sources; audio output ports are targets.)
     * <p>
     * To learn what ports are available, you can retrieve port info objects through the
     * <code>{@link Mixer#getSourceLineInfo getSourceLineInfo}</code> and
     * <code>{@link Mixer#getTargetLineInfo getTargetLineInfo}</code>
     * methods of the <code>Mixer</code> interface.  Instances of the
     * <code>Port.Info</code> class may also be constructed and used to obtain
     * lines matching the parameters specified in the <code>Port.Info</code> object.
     *
     * <p>
     *  <code> Port.Info </code>类扩展了<code> {@ link Line.Info} </code>以及特定于端口的附加信息,包括端口的名称以及它是混合器的源还是目标。
     * 根据定义,端口充当其混合器的源或目标,但不是两者。 (音频输入端口是源;音频输出端口是目标)。
     * <p>
     *  要了解哪些端口可用,您可以通过<code> <@ link Mixer#getSourceLineInfo getSourceLineInfo} </code>和<code> {@ link Mixer#getTargetLineInfo getTargetLineInfo}
     *  </code>代码> Mixer </code>接口。
     * 还可以构造<code> Port.Info </code>类的实例,并用于获得与<code> Port.Info </code>对象中指定的参数匹配的行。
     * 
     * 
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Info extends Line.Info {


        // AUDIO PORT TYPE DEFINES


        // SOURCE PORTS

        /**
         * A type of port that gets audio from a built-in microphone or a microphone jack.
         * <p>
         *  从内置麦克风或麦克风插孔获取音频的端口类型。
         * 
         */
        public static final Info MICROPHONE = new Info(Port.class,"MICROPHONE", true);

        /**
         * A type of port that gets audio from a line-level audio input jack.
         * <p>
         *  从线路级音频输入插孔获取音频的端口类型。
         * 
         */
        public static final Info LINE_IN = new Info(Port.class,"LINE_IN", true);

        /**
         * A type of port that gets audio from a CD-ROM drive.
         * <p>
         *  从CD-ROM驱动器获取音频的端口类型。
         * 
         */
        public static final Info COMPACT_DISC = new Info(Port.class,"COMPACT_DISC", true);


        // TARGET PORTS

        /**
         * A type of port that sends audio to a built-in speaker or a speaker jack.
         * <p>
         * 一种将音频发送到内置扬声器或扬声器插孔的端口类型。
         * 
         */
        public static final Info SPEAKER = new Info(Port.class,"SPEAKER", false);

        /**
         * A type of port that sends audio to a headphone jack.
         * <p>
         *  一种将音频发送到耳机插孔的端口。
         * 
         */
        public static final Info HEADPHONE = new Info(Port.class,"HEADPHONE", false);

        /**
         * A type of port that sends audio to a line-level audio output jack.
         * <p>
         *  将音频发送到线路级音频输出插孔的端口类型。
         * 
         */
        public static final Info LINE_OUT = new Info(Port.class,"LINE_OUT", false);


        // FUTURE DIRECTIONS...

        // telephone
        // DAT
        // DVD


        // INSTANCE VARIABLES

        private String name;
        private boolean isSource;


        // CONSTRUCTOR

        /**
         * Constructs a port's info object from the information given.
         * This constructor is typically used by an implementation
         * of Java Sound to describe a supported line.
         *
         * <p>
         *  从提供的信息构造端口的信息对象。此构造函数通常由Java Sound的实现用于描述支持的行。
         * 
         * 
         * @param lineClass the class of the port described by the info object.
         * @param name the string that names the port
         * @param isSource <code>true</code> if the port is a source port (such
         * as a microphone), <code>false</code> if the port is a target port
         * (such as a speaker).
         */
        public Info(Class<?> lineClass, String name, boolean isSource) {

            super(lineClass);
            this.name = name;
            this.isSource = isSource;
        }


        // METHODS

        /**
         * Obtains the name of the port.
         * <p>
         *  获取端口的名称。
         * 
         * 
         * @return the string that names the port
         */
        public String getName() {
            return name;
        }

        /**
         * Indicates whether the port is a source or a target for its mixer.
         * <p>
         *  指示端口是其混音器的源还是目标。
         * 
         * 
         * @return <code>true</code> if the port is a source port (such
         * as a microphone), <code>false</code> if the port is a target port
         * (such as a speaker).
         */
        public boolean isSource() {
            return isSource;
        }

        /**
         * Indicates whether this info object specified matches this one.
         * To match, the match requirements of the superclass must be
         * met and the types must be equal.
         * <p>
         *  指示此指定的信息对象是否与此匹配。要匹配,必须满足超类的匹配要求,类型必须相等。
         * 
         * 
         * @param info the info object for which the match is queried
         */
        public boolean matches(Line.Info info) {

            if (! (super.matches(info)) ) {
                return false;
            }

            if (!(name.equals(((Info)info).getName())) ) {
                return false;
            }

            if (! (isSource == ((Info)info).isSource()) ) {
                return false;
            }

            return true;
        }


        /**
         * Finalizes the equals method
         * <p>
         *  完成equals方法
         * 
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        /**
         * Finalizes the hashCode method
         * <p>
         *  完成hashCode方法
         * 
         */
        public final int hashCode() {
            return super.hashCode();
        }



        /**
         * Provides a <code>String</code> representation
         * of the port.
         * <p>
         *  提供端口的<code> String </code>表示形式。
         * 
         * @return  a string that describes the port
         */
        public final String toString() {
            return (name + ((isSource == true) ? " source" : " target") + " port");
        }

    } // class Info
}
