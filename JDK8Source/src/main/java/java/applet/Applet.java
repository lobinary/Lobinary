/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.applet;

import java.awt.*;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Locale;
import javax.accessibility.*;

/**
 * An applet is a small program that is intended not to be run on
 * its own, but rather to be embedded inside another application.
 * <p>
 * The <code>Applet</code> class must be the superclass of any
 * applet that is to be embedded in a Web page or viewed by the Java
 * Applet Viewer. The <code>Applet</code> class provides a standard
 * interface between applets and their environment.
 *
 * <p>
 *  小应用程序是一个小程序,它不能单独运行,而是嵌入到另一个应用程序中。
 * <p>
 *  <code> Applet </code>类必须是要嵌入Web页面或由Java Applet查看器查看的任何applet的超类。
 *  <code> Applet </code>类提供了小程序及其环境之间的标准接口。
 * 
 * 
 * @author      Arthur van Hoff
 * @author      Chris Warth
 * @since       JDK1.0
 */
public class Applet extends Panel {

    /**
     * Constructs a new Applet.
     * <p>
     * Note: Many methods in <code>java.applet.Applet</code>
     * may be invoked by the applet only after the applet is
     * fully constructed; applet should avoid calling methods
     * in <code>java.applet.Applet</code> in the constructor.
     *
     * <p>
     *  构造一个新的Applet。
     * <p>
     *  注意：<code> java.applet.Applet </code>中的许多方法只有在applet完全构造之后才能被applet调用; applet应该避免在构造函数中调用<code> java.
     * applet.Applet </code>中的方法。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    public Applet() throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
    }

    /**
     * Applets can be serialized but the following conventions MUST be followed:
     *
     * Before Serialization:
     * An applet must be in STOPPED state.
     *
     * After Deserialization:
     * The applet will be restored in STOPPED state (and most clients will
     * likely move it into RUNNING state).
     * The stub field will be restored by the reader.
     * <p>
     *  小程序可以序列化,但必须遵循以下约定：
     * 
     *  序列化之前：小程序必须处于STOPPED状态。
     * 
     *  反序列化后：小程序将恢复为STOPPED状态(并且大多数客户端可能将其移动到RUNNING状态)。存根字段将由读取器恢复。
     * 
     */
    transient private AppletStub stub;

    /* version ID for serialized form. */
    private static final long serialVersionUID = -5836846270535785031L;

    /**
     * Read an applet from an object input stream.
     * <p>
     *  从对象输入流读取applet。
     * 
     * 
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless()</code> returns
     * <code>true</code>
     * @serial
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
        s.defaultReadObject();
    }

    /**
     * Sets this applet's stub. This is done automatically by the system.
     * <p>If there is a security manager, its <code> checkPermission </code>
     * method is called with the
     * <code>AWTPermission("setAppletStub")</code>
     * permission if a stub has already been set.
     * <p>
     *  设置此applet的存根。这由系统自动完成。
     *  <p>如果有安全管理器,则如果已设置存根,则使用<code> AWTPermission("setAppletStub")</code>权限调用其<code> checkPermission </code>
     * 方法。
     *  设置此applet的存根。这由系统自动完成。
     * 
     * 
     * @param   stub   the new stub.
     * @exception SecurityException if the caller cannot set the stub
     */
    public final void setStub(AppletStub stub) {
        if (this.stub != null) {
            SecurityManager s = System.getSecurityManager();
            if (s != null) {
                s.checkPermission(new AWTPermission("setAppletStub"));
            }
        }
        this.stub = stub;
    }

    /**
     * Determines if this applet is active. An applet is marked active
     * just before its <code>start</code> method is called. It becomes
     * inactive just before its <code>stop</code> method is called.
     *
     * <p>
     * 确定此小程序是否处于活动状态。一个applet在调用<code> start </code>方法之前被标记为活动。它在调用<code> stop </code>方法之前变为无效。
     * 
     * 
     * @return  <code>true</code> if the applet is active;
     *          <code>false</code> otherwise.
     * @see     java.applet.Applet#start()
     * @see     java.applet.Applet#stop()
     */
    public boolean isActive() {
        if (stub != null) {
            return stub.isActive();
        } else {        // If stub field not filled in, applet never active
            return false;
        }
    }

    /**
     * Gets the URL of the document in which this applet is embedded.
     * For example, suppose an applet is contained
     * within the document:
     * <blockquote><pre>
     *    http://www.oracle.com/technetwork/java/index.html
     * </pre></blockquote>
     * The document base is:
     * <blockquote><pre>
     *    http://www.oracle.com/technetwork/java/index.html
     * </pre></blockquote>
     *
     * <p>
     *  获取嵌入此小程序的文档的URL。
     * 例如,假设文档中包含一个小程序：<blockquote> <pre> http://www.oracle.com/technetwork/java/index.html </pre> </blockquote>
     * 文档基础是：<blockquote > <pre> http://www.oracle.com/technetwork/java/index.html </pre> </blockquote>。
     *  获取嵌入此小程序的文档的URL。
     * 
     * 
     * @return  the {@link java.net.URL} of the document that contains this
     *          applet.
     * @see     java.applet.Applet#getCodeBase()
     */
    public URL getDocumentBase() {
        return stub.getDocumentBase();
    }

    /**
     * Gets the base URL. This is the URL of the directory which contains this applet.
     *
     * <p>
     *  获取基本URL。这是包含此applet的目录的URL。
     * 
     * 
     * @return  the base {@link java.net.URL} of
     *          the directory which contains this applet.
     * @see     java.applet.Applet#getDocumentBase()
     */
    public URL getCodeBase() {
        return stub.getCodeBase();
    }

    /**
     * Returns the value of the named parameter in the HTML tag. For
     * example, if this applet is specified as
     * <blockquote><pre>
     * &lt;applet code="Clock" width=50 height=50&gt;
     * &lt;param name=Color value="blue"&gt;
     * &lt;/applet&gt;
     * </pre></blockquote>
     * <p>
     * then a call to <code>getParameter("Color")</code> returns the
     * value <code>"blue"</code>.
     * <p>
     * The <code>name</code> argument is case insensitive.
     *
     * <p>
     *  返回HTML标记中的命名参数的值。
     * 例如,如果该applet被指定为<blockquote> <pre>&lt; applet code ="Clock"width = 50 height = 50&gt; &lt; param name
     *  = Color value ="blue"&gt; &lt; / applet&gt; </pre> </blockquote>。
     *  返回HTML标记中的命名参数的值。
     * <p>
     *  那么调用<code> getParameter("Color")</code>会返回值<code>"blue"</code>。
     * <p>
     *  <code> name </code>参数不区分大小写。
     * 
     * 
     * @param   name   a parameter name.
     * @return  the value of the named parameter,
     *          or <code>null</code> if not set.
     */
     public String getParameter(String name) {
         return stub.getParameter(name);
     }

    /**
     * Determines this applet's context, which allows the applet to
     * query and affect the environment in which it runs.
     * <p>
     * This environment of an applet represents the document that
     * contains the applet.
     *
     * <p>
     *  确定这个小程序的上下文,它允许小程序查询和影响它运行的环境。
     * <p>
     *  小应用程序的此环境表示包含小应用程序的文档。
     * 
     * 
     * @return  the applet's context.
     */
    public AppletContext getAppletContext() {
        return stub.getAppletContext();
    }

    /**
     * Requests that this applet be resized.
     *
     * <p>
     *  请求调整此applet的大小。
     * 
     * 
     * @param   width    the new requested width for the applet.
     * @param   height   the new requested height for the applet.
     */
    @SuppressWarnings("deprecation")
    public void resize(int width, int height) {
        Dimension d = size();
        if ((d.width != width) || (d.height != height)) {
            super.resize(width, height);
            if (stub != null) {
                stub.appletResize(width, height);
            }
        }
    }

    /**
     * Requests that this applet be resized.
     *
     * <p>
     *  请求调整此applet的大小。
     * 
     * 
     * @param   d   an object giving the new width and height.
     */
    @SuppressWarnings("deprecation")
    public void resize(Dimension d) {
        resize(d.width, d.height);
    }

    /**
     * Indicates if this container is a validate root.
     * <p>
     * {@code Applet} objects are the validate roots, and, therefore, they
     * override this method to return {@code true}.
     *
     * <p>
     *  指示此容器是否为验证根。
     * <p>
     *  {@code Applet}对象是验证根,因此,它们覆盖此方法以返回{@code true}。
     * 
     * 
     * @return {@code true}
     * @since 1.7
     * @see java.awt.Container#isValidateRoot
     */
    @Override
    public boolean isValidateRoot() {
        return true;
    }

    /**
     * Requests that the argument string be displayed in the
     * "status window". Many browsers and applet viewers
     * provide such a window, where the application can inform users of
     * its current state.
     *
     * <p>
     * 请求在"状态窗口"中显示参数字符串。许多浏览器和小应用程序查看器提供这样的窗口,其中应用可以通知用户其当前状态。
     * 
     * 
     * @param   msg   a string to display in the status window.
     */
    public void showStatus(String msg) {
        getAppletContext().showStatus(msg);
    }

    /**
     * Returns an <code>Image</code> object that can then be painted on
     * the screen. The <code>url</code> that is passed as an argument
     * must specify an absolute URL.
     * <p>
     * This method always returns immediately, whether or not the image
     * exists. When this applet attempts to draw the image on the screen,
     * the data will be loaded. The graphics primitives that draw the
     * image will incrementally paint on the screen.
     *
     * <p>
     *  返回一个<code> Image </code>对象,然后可以在屏幕上绘制。作为参数传递的<code> url </code>必须指定绝对URL。
     * <p>
     *  此方法始终立即返回,无论图像是否存在。当这个小程序试图在屏幕上绘制图像时,数据将被加载。绘制图像的图形基元将在屏幕上渐进地绘制。
     * 
     * 
     * @param   url   an absolute URL giving the location of the image.
     * @return  the image at the specified URL.
     * @see     java.awt.Image
     */
    public Image getImage(URL url) {
        return getAppletContext().getImage(url);
    }

    /**
     * Returns an <code>Image</code> object that can then be painted on
     * the screen. The <code>url</code> argument must specify an absolute
     * URL. The <code>name</code> argument is a specifier that is
     * relative to the <code>url</code> argument.
     * <p>
     * This method always returns immediately, whether or not the image
     * exists. When this applet attempts to draw the image on the screen,
     * the data will be loaded. The graphics primitives that draw the
     * image will incrementally paint on the screen.
     *
     * <p>
     *  返回一个<code> Image </code>对象,然后可以在屏幕上绘制。 <code> url </code>参数必须指定绝对URL。
     *  <code> name </code>参数是一个相对于<code> url </code>参数的说明符。
     * <p>
     *  此方法始终立即返回,无论图像是否存在。当这个小程序试图在屏幕上绘制图像时,数据将被加载。绘制图像的图形基元将在屏幕上渐进地绘制。
     * 
     * 
     * @param   url    an absolute URL giving the base location of the image.
     * @param   name   the location of the image, relative to the
     *                 <code>url</code> argument.
     * @return  the image at the specified URL.
     * @see     java.awt.Image
     */
    public Image getImage(URL url, String name) {
        try {
            return getImage(new URL(url, name));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Get an audio clip from the given URL.
     *
     * <p>
     *  从给定的URL获取音频剪辑。
     * 
     * 
     * @param url points to the audio clip
     * @return the audio clip at the specified URL.
     *
     * @since       1.2
     */
    public final static AudioClip newAudioClip(URL url) {
        return new sun.applet.AppletAudioClip(url);
    }

    /**
     * Returns the <code>AudioClip</code> object specified by the
     * <code>URL</code> argument.
     * <p>
     * This method always returns immediately, whether or not the audio
     * clip exists. When this applet attempts to play the audio clip, the
     * data will be loaded.
     *
     * <p>
     *  返回由<code> URL </code>参数指定的<code> AudioClip </code>对象。
     * <p>
     *  无论音频剪辑是否存在,此方法始终立即返回。当此小程序尝试播放音频剪辑时,数据将被加载。
     * 
     * 
     * @param   url  an absolute URL giving the location of the audio clip.
     * @return  the audio clip at the specified URL.
     * @see     java.applet.AudioClip
     */
    public AudioClip getAudioClip(URL url) {
        return getAppletContext().getAudioClip(url);
    }

    /**
     * Returns the <code>AudioClip</code> object specified by the
     * <code>URL</code> and <code>name</code> arguments.
     * <p>
     * This method always returns immediately, whether or not the audio
     * clip exists. When this applet attempts to play the audio clip, the
     * data will be loaded.
     *
     * <p>
     *  返回由<code> URL </code>和<code> name </code>参数指定的<code> AudioClip </code>对象。
     * <p>
     * 无论音频剪辑是否存在,此方法始终立即返回。当此小程序尝试播放音频剪辑时,数据将被加载。
     * 
     * 
     * @param   url    an absolute URL giving the base location of the
     *                 audio clip.
     * @param   name   the location of the audio clip, relative to the
     *                 <code>url</code> argument.
     * @return  the audio clip at the specified URL.
     * @see     java.applet.AudioClip
     */
    public AudioClip getAudioClip(URL url, String name) {
        try {
            return getAudioClip(new URL(url, name));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Returns information about this applet. An applet should override
     * this method to return a <code>String</code> containing information
     * about the author, version, and copyright of the applet.
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class returns <code>null</code>.
     *
     * <p>
     *  返回有关此applet的信息。小程序应该重写此方法以返回包含关于小程序的作者,版本和版权的信息的<code> String </code>。
     * <p>
     *  由<code> Applet </code>类提供的此方法的实现返回<code> null </code>。
     * 
     * 
     * @return  a string containing information about the author, version, and
     *          copyright of the applet.
     */
    public String getAppletInfo() {
        return null;
    }

    /**
     * Gets the locale of the applet. It allows the applet
     * to maintain its own locale separated from the locale
     * of the browser or appletviewer.
     *
     * <p>
     *  获取小程序的区域设置。它允许小程序保持它自己的与浏览器或appletviewer的语言环境分离的语言环境。
     * 
     * 
     * @return  the locale of the applet; if no locale has
     *          been set, the default locale is returned.
     * @since   JDK1.1
     */
    public Locale getLocale() {
      Locale locale = super.getLocale();
      if (locale == null) {
        return Locale.getDefault();
      }
      return locale;
    }

    /**
     * Returns information about the parameters that are understood by
     * this applet. An applet should override this method to return an
     * array of <code>Strings</code> describing these parameters.
     * <p>
     * Each element of the array should be a set of three
     * <code>Strings</code> containing the name, the type, and a
     * description. For example:
     * <blockquote><pre>
     * String pinfo[][] = {
     *   {"fps",    "1-10",    "frames per second"},
     *   {"repeat", "boolean", "repeat image loop"},
     *   {"imgs",   "url",     "images directory"}
     * };
     * </pre></blockquote>
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class returns <code>null</code>.
     *
     * <p>
     *  返回有关此applet理解的参数的信息。一个applet应该覆盖这个方法来返回描述这些参数的<code> Strings </code>数组。
     * <p>
     *  数组的每个元素应该是包含名称,类型和描述的三个<code> Strings </code>的集合。
     * 例如：<blockquote> <pre> String pinfo [] [] = {{"fps","1-10","frames per second"},{"repeat","boolean","repeat image loop"}
     *  ,{"imgs","url","images directory"}}; </pre> </blockquote>。
     *  数组的每个元素应该是包含名称,类型和描述的三个<code> Strings </code>的集合。
     * <p>
     *  由<code> Applet </code>类提供的此方法的实现返回<code> null </code>。
     * 
     * 
     * @return  an array describing the parameters this applet looks for.
     */
    public String[][] getParameterInfo() {
        return null;
    }

    /**
     * Plays the audio clip at the specified absolute URL. Nothing
     * happens if the audio clip cannot be found.
     *
     * <p>
     *  以指定的绝对网址播放音频剪辑。如果找不到音频剪辑,则不会发生任何事情。
     * 
     * 
     * @param   url   an absolute URL giving the location of the audio clip.
     */
    public void play(URL url) {
        AudioClip clip = getAudioClip(url);
        if (clip != null) {
            clip.play();
        }
    }

    /**
     * Plays the audio clip given the URL and a specifier that is
     * relative to it. Nothing happens if the audio clip cannot be found.
     *
     * <p>
     *  播放音频剪辑给定的URL和一个说明符是相对于它。如果找不到音频剪辑,则不会发生任何事情。
     * 
     * 
     * @param   url    an absolute URL giving the base location of the
     *                 audio clip.
     * @param   name   the location of the audio clip, relative to the
     *                 <code>url</code> argument.
     */
    public void play(URL url, String name) {
        AudioClip clip = getAudioClip(url, name);
        if (clip != null) {
            clip.play();
        }
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it has been loaded into the system. It is always
     * called before the first time that the <code>start</code> method is
     * called.
     * <p>
     * A subclass of <code>Applet</code> should override this method if
     * it has initialization to perform. For example, an applet with
     * threads would use the <code>init</code> method to create the
     * threads and the <code>destroy</code> method to kill them.
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * <p>
     * 由浏览器或小程序查看器调用以通知此小程序它已被加载到系统中。它总是在第一次调用<code> start </code>方法之前调用。
     * <p>
     *  <code> Applet </code>的子类应该重写这个方法,如果它有初始化执行。
     * 例如,具有线程的applet将使用<code> init </code>方法创建线程和<code> destroy </code>方法来杀死它们。
     * <p>
     *  由<code> Applet </code>类提供的这个方法的实现什么也不做。
     * 
     * 
     * @see     java.applet.Applet#destroy()
     * @see     java.applet.Applet#start()
     * @see     java.applet.Applet#stop()
     */
    public void init() {
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it should start its execution. It is called after
     * the <code>init</code> method and each time the applet is revisited
     * in a Web page.
     * <p>
     * A subclass of <code>Applet</code> should override this method if
     * it has any operation that it wants to perform each time the Web
     * page containing it is visited. For example, an applet with
     * animation might want to use the <code>start</code> method to
     * resume animation, and the <code>stop</code> method to suspend the
     * animation.
     * <p>
     * Note: some methods, such as <code>getLocationOnScreen</code>, can only
     * provide meaningful results if the applet is showing.  Because
     * <code>isShowing</code> returns <code>false</code> when the applet's
     * <code>start</code> is first called, methods requiring
     * <code>isShowing</code> to return <code>true</code> should be called from
     * a <code>ComponentListener</code>.
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * <p>
     *  由浏览器或小应用程序查看器调用以通知此小程序它应该开始执行。它在<code> init </code>方法之后调用,每次在Web页面中重新访问applet时。
     * <p>
     *  <code> Applet </code>的子类应该重写这个方法,如果它有任何操作,它想要执行每次包含它的网页被访问。
     * 例如,具有动画的小程序可能想使用<code> start </code>方法来恢复动画,而使用<code> stop </code>方法来暂停动画。
     * <p>
     *  注意：一些方法,如<code> getLocationOnScreen </code>,只能提供有意义的结果,如果applet显示。
     * 因为当第一次调用小程序的<code> start </code>时,<code> isShowing </code>返回<code> false </code>,需要<code> isShowing </code>
     * 的方法返回<code> true < / code>应该从<code> ComponentListener </code>中调用。
     *  注意：一些方法,如<code> getLocationOnScreen </code>,只能提供有意义的结果,如果applet显示。
     * <p>
     * 由<code> Applet </code>类提供的这个方法的实现什么也不做。
     * 
     * 
     * @see     java.applet.Applet#destroy()
     * @see     java.applet.Applet#init()
     * @see     java.applet.Applet#stop()
     * @see     java.awt.Component#isShowing()
     * @see     java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    public void start() {
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it should stop its execution. It is called when
     * the Web page that contains this applet has been replaced by
     * another page, and also just before the applet is to be destroyed.
     * <p>
     * A subclass of <code>Applet</code> should override this method if
     * it has any operation that it wants to perform each time the Web
     * page containing it is no longer visible. For example, an applet
     * with animation might want to use the <code>start</code> method to
     * resume animation, and the <code>stop</code> method to suspend the
     * animation.
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * <p>
     *  由浏览器或小程序查看器调用以通知此小程序它应停止其执行。当包含这个小程序的网页被另一个页面替换,并且也在小程序被销毁之前被调用。
     * <p>
     *  <code> Applet </code>的子类应该重写这个方法,如果它有任何操作,它想要执行每次包含它的网页不再可见。
     * 例如,具有动画的小程序可能想使用<code> start </code>方法来恢复动画,而使用<code> stop </code>方法来暂停动画。
     * <p>
     *  由<code> Applet </code>类提供的这个方法的实现什么也不做。
     * 
     * 
     * @see     java.applet.Applet#destroy()
     * @see     java.applet.Applet#init()
     */
    public void stop() {
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it is being reclaimed and that it should destroy
     * any resources that it has allocated. The <code>stop</code> method
     * will always be called before <code>destroy</code>.
     * <p>
     * A subclass of <code>Applet</code> should override this method if
     * it has any operation that it wants to perform before it is
     * destroyed. For example, an applet with threads would use the
     * <code>init</code> method to create the threads and the
     * <code>destroy</code> method to kill them.
     * <p>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * <p>
     *  由浏览器或小程序查看器调用以通知此小程序它正在被回收,并且它应该销毁它已经分配的任何资源。 <code> stop </code>方法将始终在<code> destroy </code>之前调用。
     * <p>
     *  <code> Applet </code>的子类应该覆盖此方法,如果它有任何操作,它在它被销毁之前执行。
     * 例如,具有线程的applet将使用<code> init </code>方法创建线程和<code> destroy </code>方法来杀死它们。
     * <p>
     *  由<code> Applet </code>类提供的这个方法的实现什么也不做。
     * 
     * 
     * @see     java.applet.Applet#init()
     * @see     java.applet.Applet#start()
     * @see     java.applet.Applet#stop()
     */
    public void destroy() {
    }

    //
    // Accessibility support
    //

    AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this Applet.
     * For applets, the AccessibleContext takes the form of an
     * AccessibleApplet.
     * A new AccessibleApplet instance is created if necessary.
     *
     * <p>
     * 获取与此Applet关联的AccessibleContext。对于applet,AccessibleContext采用AccessibleApplet的形式。
     * 如果需要,将创建一个新的AccessibleApplet实例。
     * 
     * 
     * @return an AccessibleApplet that serves as the
     *         AccessibleContext of this Applet
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleApplet();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Applet</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to applet user-interface elements.
     * <p>
     *  此类实现<code> Applet </code>类的辅助功能支持。它提供了适用于applet用户界面元素的Java可访问性API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleApplet extends AccessibleAWTPanel {

        private static final long serialVersionUID = 8127374778187708896L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.FRAME;
        }

        /**
         * Get the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            states.add(AccessibleState.ACTIVE);
            return states;
        }

    }
}
