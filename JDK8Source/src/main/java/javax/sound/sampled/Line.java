/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>Line</code> interface represents a mono or multi-channel
 * audio feed. A line is an element of the digital audio
 * "pipeline," such as a mixer, an input or output port,
 * or a data path into or out of a mixer.
 * <p>
 * A line can have controls, such as gain, pan, and reverb.
 * The controls themselves are instances of classes that extend the
 * base <code>{@link Control}</code> class.
 * The <code>Line</code> interface provides two accessor methods for
 * obtaining the line's controls: <code>{@link #getControls getControls}</code> returns the
 * entire set, and <code>{@link #getControl getControl}</code> returns a single control of
 * specified type.
 * <p>
 * Lines exist in various states at different times.  When a line opens, it reserves system
 * resources for itself, and when it closes, these resources are freed for
 * other objects or applications. The <code>{@link #isOpen()}</code> method lets
 * you discover whether a line is open or closed.
 * An open line need not be processing data, however.  Such processing is
 * typically initiated by subinterface methods such as
 * <code>{@link SourceDataLine#write SourceDataLine.write}</code> and
 * <code>{@link TargetDataLine#read TargetDataLine.read}</code>.
 *<p>
 * You can register an object to receive notifications whenever the line's
 * state changes.  The object must implement the <code>{@link LineListener}</code>
 * interface, which consists of the single method
 * <code>{@link LineListener#update update}</code>.
 * This method will be invoked when a line opens and closes (and, if it's a
 * {@link DataLine}, when it starts and stops).
 *<p>
 * An object can be registered to listen to multiple lines.  The event it
 * receives in its <code>update</code> method will specify which line created
 * the event, what type of event it was
 * (<code>OPEN</code>, <code>CLOSE</code>, <code>START</code>, or <code>STOP</code>),
 * and how many sample frames the line had processed at the time the event occurred.
 * <p>
 * Certain line operations, such as open and close, can generate security
 * exceptions if invoked by unprivileged code when the line is a shared audio
 * resource.
 *
 * <p>
 *  <code> Line </code>界面表示单声道或多声道音频Feed。线是数字音频"管线"的元件,例如混频器,输入或输出端口或者进入或离开混频器的数据路径。
 * <p>
 *  线条可以有控件,如增益,平移和混响。控件本身是扩展基本<code> {@ link Control} </code>类的类的实例。
 *  <code> Line </code>接口提供了两个访问器方法来获取行控件：<code> {@ link #getControls getControls} </code>返回整个集合,<code> 
 * {@ link #getControl getControl} </code>返回指定类型的单个控件。
 *  线条可以有控件,如增益,平移和混响。控件本身是扩展基本<code> {@ link Control} </code>类的类的实例。
 * <p>
 *  线在不同的时间以不同的状态存在。当一个行打开时,它为自己保留系统资源,当它关闭时,这些资源将被释放用于其他对象或应用程序。
 * 使用<code> {@ link #isOpen()} </code>方法可以发现行是打开还是关闭。然而,开放线路不需要处理数据。
 * 这种处理通常由诸如<code> {@ link SourceDataLine#write SourceDataLine.write} </code>和<code> {@ link TargetDataLine#read TargetDataLine.read}
 *  </code>之类的子接口方法发起。
 * 使用<code> {@ link #isOpen()} </code>方法可以发现行是打开还是关闭。然而,开放线路不需要处理数据。
 * p>
 * 您可以注册对象以在线路的状态更改时接收通知。
 * 对象必须实现<code> {@ link LineListener} </code>接口,它由单个方法<code> {@ link LineListener#update update} </code>
 * 组成。
 * 您可以注册对象以在线路的状态更改时接收通知。当一个行打开和关闭时(以及如果它是一个{@link DataLine},当它开始和停止时)将调用此方法。
 * p>
 *  对象可以注册以侦听多行。
 * 在<code> update </code>方法中接收的事件将指定创建事件的行,它是什么类型的事件(<code> OPEN </code>,<code> CLOSE </code> > START </code>
 * 或<code> STOP </code>),以及行在事件发生时处理了多少个样本帧。
 *  对象可以注册以侦听多行。
 * <p>
 *  如果线路是共享音频资源,某些线路操作(例如打开和关闭)可能会生成安全性异常(如果由非特权代码调用)。
 * 
 * 
 * @author Kara Kytle
 *
 * @see LineEvent
 * @since 1.3
 */
public interface Line extends AutoCloseable {

    /**
     * Obtains the <code>Line.Info</code> object describing this
     * line.
     * <p>
     *  获取描述此行的<code> Line.Info </code>对象。
     * 
     * 
     * @return description of the line
     */
    public Line.Info getLineInfo();

    /**
     * Opens the line, indicating that it should acquire any required
     * system resources and become operational.
     * If this operation
     * succeeds, the line is marked as open, and an <code>OPEN</code> event is dispatched
     * to the line's listeners.
     * <p>
     * Note that some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in an <code>LineUnavailableException</code>.
     * <p>
     * Some types of lines have configurable properties that may affect
     * resource allocation.   For example, a <code>DataLine</code> must
     * be opened with a particular format and buffer size.  Such lines
     * should provide a mechanism for configuring these properties, such
     * as an additional <code>open</code> method or methods which allow
     * an application to specify the desired settings.
     * <p>
     * This method takes no arguments, and opens the line with the current
     * settings.  For <code>{@link SourceDataLine}</code> and
     * <code>{@link TargetDataLine}</code> objects, this means that the line is
     * opened with default settings.  For a <code>{@link Clip}</code>, however,
     * the buffer size is determined when data is loaded.  Since this method does not
     * allow the application to specify any data to load, an IllegalArgumentException
     * is thrown. Therefore, you should instead use one of the <code>open</code> methods
     * provided in the <code>Clip</code> interface to load data into the <code>Clip</code>.
     * <p>
     * For <code>DataLine</code>'s, if the <code>DataLine.Info</code>
     * object which was used to retrieve the line, specifies at least
     * one fully qualified audio format, the last one will be used
     * as the default format.
     *
     * <p>
     *  打开该行,表示它应该获取任何所需的系统资源并运行。如果此操作成功,则将该行标记为打开,并将<code> OPEN </code>事件分派到该行的侦听器。
     * <p>
     *  注意,一些行,一旦关闭,不能重新打开。尝试重新打开这样的行将总是导致<code> LineUnavailableException </code>。
     * <p>
     * 某些类型的线路具有可能影响资源分配的可配置属性。例如,必须使用特定格式和缓冲区大小打开<code> DataLine </code>。
     * 这样的行应该提供用于配置这些属性的机制,诸如允许应用指定期望的设置的附加的<code> open </code>方法。
     * <p>
     *  此方法不使用参数,并打开当前设置的行。
     * 对于<code> {@ link SourceDataLine} </code>和<code> {@ link TargetDataLine} </code>对象,这意味着该行使用默认设置打开。
     * 然而,对于<code> {@ link Clip} </code>,当加载数据时确定缓冲区大小。
     * 由于此方法不允许应用程序指定要加载的任何数据,因此会抛出IllegalArgumentException。
     * 因此,您应该使用<code> Clip </code>接口中提供的<code> open </code>方法之一将数据加载到<code> Clip </code>中。
     * <p>
     *  对于<code> DataLine </code>,如果用于检索行的<code> DataLine.Info </code>对象至少指定一个完全限定的音频格式,最后一个将被用作默认格式。
     * 
     * 
     * @throws IllegalArgumentException if this method is called on a Clip instance.
     * @throws LineUnavailableException if the line cannot be
     * opened due to resource restrictions.
     * @throws SecurityException if the line cannot be
     * opened due to security restrictions.
     *
     * @see #close
     * @see #isOpen
     * @see LineEvent
     * @see DataLine
     * @see Clip#open(AudioFormat, byte[], int, int)
     * @see Clip#open(AudioInputStream)
     */
    public void open() throws LineUnavailableException;


    /**
     * Closes the line, indicating that any system resources
     * in use by the line can be released.  If this operation
     * succeeds, the line is marked closed and a <code>CLOSE</code> event is dispatched
     * to the line's listeners.
     * <p>
     *  关闭该行,表示该行正在使用的任何系统资源都可以释放。如果此操作成功,则将该行标记为关闭,并将<code> CLOSE </code>事件分派到该行的侦听器。
     * 
     * 
     * @throws SecurityException if the line cannot be
     * closed due to security restrictions.
     *
     * @see #open
     * @see #isOpen
     * @see LineEvent
     */
    public void close();



    /**
     * Indicates whether the line is open, meaning that it has reserved
     * system resources and is operational, although it might not currently be
     * playing or capturing sound.
     * <p>
     * 指示线路是否打开,这意味着它已保留系统资源并且正在运行,尽管它当前可能不在播放或捕获声音。
     * 
     * 
     * @return <code>true</code> if the line is open, otherwise <code>false</code>
     *
     * @see #open()
     * @see #close()
     */
    public boolean isOpen();


    /**
     * Obtains the set of controls associated with this line.
     * Some controls may only be available when the line is open.
     * If there are no controls, this method returns an array of length 0.
     * <p>
     *  获取与此行相关联的控件集。某些控件只能在线路打开时使用。如果没有控件,则此方法返回长度为0的数组。
     * 
     * 
     * @return the array of controls
     * @see #getControl
     */
    public Control[] getControls();

    /**
     * Indicates whether the line supports a control of the specified type.
     * Some controls may only be available when the line is open.
     * <p>
     *  指示行是否支持指定类型的控件。某些控件只能在线路打开时使用。
     * 
     * 
     * @param control the type of the control for which support is queried
     * @return <code>true</code> if at least one control of the specified type is
     * supported, otherwise <code>false</code>.
     */
    public boolean isControlSupported(Control.Type control);


    /**
     * Obtains a control of the specified type,
     * if there is any.
     * Some controls may only be available when the line is open.
     * <p>
     *  获取指定类型的控件,如果有的话。某些控件只能在线路打开时使用。
     * 
     * 
     * @param control the type of the requested control
     * @return a control of the specified type
     * @throws IllegalArgumentException if a control of the specified type
     * is not supported
     * @see #getControls
     * @see #isControlSupported(Control.Type control)
     */
    public Control getControl(Control.Type control);


    /**
     * Adds a listener to this line.  Whenever the line's status changes, the
     * listener's <code>update()</code> method is called with a <code>LineEvent</code> object
     * that describes the change.
     * <p>
     *  向此行添加侦听器。每当行的状态改变时,监听器的<code> update()</code>方法就会用描述这个改变的<code> LineEvent </code>对象来调用。
     * 
     * 
     * @param listener the object to add as a listener to this line
     * @see #removeLineListener
     * @see LineListener#update
     * @see LineEvent
     */
    public void addLineListener(LineListener listener);


    /**
     * Removes the specified listener from this line's list of listeners.
     * <p>
     *  从此行的侦听器列表中删除指定的侦听器。
     * 
     * 
     * @param listener listener to remove
     * @see #addLineListener
     */
    public void removeLineListener(LineListener listener);


    /**
     * A <code>Line.Info</code> object contains information about a line.
     * The only information provided by <code>Line.Info</code> itself
     * is the Java class of the line.
     * A subclass of <code>Line.Info</code> adds other kinds of information
     * about the line.  This additional information depends on which <code>Line</code>
     * subinterface is implemented by the kind of line that the <code>Line.Info</code>
     * subclass describes.
     * <p>
     * A <code>Line.Info</code> can be retrieved using various methods of
     * the <code>Line</code>, <code>Mixer</code>, and <code>AudioSystem</code>
     * interfaces.  Other such methods let you pass a <code>Line.Info</code> as
     * an argument, to learn whether lines matching the specified configuration
     * are available and to obtain them.
     *
     * <p>
     *  <code> Line.Info </code>对象包含关于行的信息。由<code> Line.Info </code>本身提供的唯一信息是该行的Java类。
     *  <code> Line.Info </code>的子类添加了关于该行的其他种类的信息。
     * 这个附加信息取决于<code> Line </code>子接口是由<code> Line.Info </code>子类描述的那种行实现的。
     * <p>
     * 可以使用<code> Line </code>,<code> Mixer </code>和<code> AudioSystem </code>接口的各种方法检索<code> Line.Info </code>
     * 。
     * 其他这样的方法允许您传递一个<code> Line.Info </code>作为参数,以了解匹配指定配置的行是否可用并获取它们。
     * 
     * 
     * @author Kara Kytle
     *
     * @see Line#getLineInfo
     * @see Mixer#getSourceLineInfo
     * @see Mixer#getTargetLineInfo
     * @see Mixer#getLine <code>Mixer.getLine(Line.Info)</code>
     * @see Mixer#getSourceLineInfo(Line.Info) <code>Mixer.getSourceLineInfo(Line.Info)</code>
     * @see Mixer#getSourceLineInfo(Line.Info) <code>Mixer.getTargetLineInfo(Line.Info)</code>
     * @see Mixer#isLineSupported <code>Mixer.isLineSupported(Line.Info)</code>
     * @see AudioSystem#getLine <code>AudioSystem.getLine(Line.Info)</code>
     * @see AudioSystem#getSourceLineInfo <code>AudioSystem.getSourceLineInfo(Line.Info)</code>
     * @see AudioSystem#getTargetLineInfo <code>AudioSystem.getTargetLineInfo(Line.Info)</code>
     * @see AudioSystem#isLineSupported <code>AudioSystem.isLineSupported(Line.Info)</code>
     * @since 1.3
     */
    public static class Info {

        /**
         * The class of the line described by the info object.
         * <p>
         *  info对象描述的行的类。
         * 
         */
        private final Class lineClass;


        /**
         * Constructs an info object that describes a line of the specified class.
         * This constructor is typically used by an application to
         * describe a desired line.
         * <p>
         *  构造一个描述指定类的一行的信息对象。此构造函数通常由应用程序用来描述所需的行。
         * 
         * 
         * @param lineClass the class of the line that the new Line.Info object describes
         */
        public Info(Class<?> lineClass) {

            if (lineClass == null) {
                this.lineClass = Line.class;
            } else {
                this.lineClass = lineClass;
            }
        }



        /**
         * Obtains the class of the line that this Line.Info object describes.
         * <p>
         *  获取此Line.Info对象描述的行的类。
         * 
         * 
         * @return the described line's class
         */
        public Class<?> getLineClass() {
            return lineClass;
        }


        /**
         * Indicates whether the specified info object matches this one.
         * To match, the specified object must be identical to or
         * a special case of this one.  The specified info object
         * must be either an instance of the same class as this one,
         * or an instance of a sub-type of this one.  In addition, the
         * attributes of the specified object must be compatible with the
         * capabilities of this one.  Specifically, the routing configuration
         * for the specified info object must be compatible with that of this
         * one.
         * Subclasses may add other criteria to determine whether the two objects
         * match.
         *
         * <p>
         *  指示指定的信息对象是否与此信息对象匹配。要匹配,指定的对象必须与此对象相同或特殊情况。指定的info对象必须是与此类相同类的实例,或者是此类的子类型的实例。
         * 此外,指定对象的属性必须与此对象的功能兼容。具体来说,指定信息对象的路由配置必须与此路由配置兼容。子类可以添加其他标准来确定两个对象是否匹配。
         * 
         * 
         * @param info the info object which is being compared to this one
         * @return <code>true</code> if the specified object matches this one,
         * <code>false</code> otherwise
         */
        public boolean matches(Info info) {

            // $$kk: 08.30.99: is this backwards?
            // dataLine.matches(targetDataLine) == true: targetDataLine is always dataLine
            // targetDataLine.matches(dataLine) == false
            // so if i want to make sure i get a targetDataLine, i need:
            // targetDataLine.matches(prospective_match) == true
            // => prospective_match may be other things as well, but it is at least a targetDataLine
            // targetDataLine defines the requirements which prospective_match must meet.


            // "if this Class object represents a declared class, this method returns
            // true if the specified Object argument is an instance of the represented
            // class (or of any of its subclasses)"
            // GainControlClass.isInstance(MyGainObj) => true
            // GainControlClass.isInstance(MySpecialGainInterfaceObj) => true

            // this_class.isInstance(that_object)       => that object can by cast to this class
            //                                                                          => that_object's class may be a subtype of this_class
            //                                                                          => that may be more specific (subtype) of this

            // "If this Class object represents an interface, this method returns true
            // if the class or any superclass of the specified Object argument implements
            // this interface"
            // GainControlClass.isInstance(MyGainObj) => true
            // GainControlClass.isInstance(GenericControlObj) => may be false
            // => that may be more specific

            if (! (this.getClass().isInstance(info)) ) {
                return false;
            }


            // this.isAssignableFrom(that)  =>  this is same or super to that
            //                                                          =>      this is at least as general as that
            //                                                          =>      that may be subtype of this

            if (! (getLineClass().isAssignableFrom(info.getLineClass())) ) {
                return false;
            }

            return true;
        }


        /**
         * Obtains a textual description of the line info.
         * <p>
         * 
         * @return a string description
         */
        public String toString() {

            String fullPackagePath = "javax.sound.sampled.";
            String initialString = new String(getLineClass().toString());
            String finalString;

            int index = initialString.indexOf(fullPackagePath);

            if (index != -1) {
                finalString = initialString.substring(0, index) + initialString.substring( (index + fullPackagePath.length()), initialString.length() );
            } else {
                finalString = initialString;
            }

            return finalString;
        }

    } // class Info

} // interface Line
