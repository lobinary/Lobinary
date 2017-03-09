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

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.ColorModel;
import java.net.URL;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * This interface corresponds to an applet's environment: the
 * document containing the applet and the other applets in the same
 * document.
 * <p>
 * The methods in this interface can be used by an applet to obtain
 * information about its environment.
 *
 * <p>
 *  此接口对应于小程序的环境：包含小程序的文档和在同一文档中的其他小程序。
 * <p>
 *  此接口中的方法可由小程序使用以获取有关其环境的信息。
 * 
 * 
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public interface AppletContext {
    /**
     * Creates an audio clip.
     *
     * <p>
     *  创建音频剪辑。
     * 
     * 
     * @param   url   an absolute URL giving the location of the audio clip.
     * @return  the audio clip at the specified URL.
     */
    AudioClip getAudioClip(URL url);

    /**
     * Returns an <code>Image</code> object that can then be painted on
     * the screen. The <code>url</code> argument that is
     * passed as an argument must specify an absolute URL.
     * <p>
     * This method always returns immediately, whether or not the image
     * exists. When the applet attempts to draw the image on the screen,
     * the data will be loaded. The graphics primitives that draw the
     * image will incrementally paint on the screen.
     *
     * <p>
     *  返回一个<code> Image </code>对象,然后可以在屏幕上绘制。作为参数传递的<code> url </code>参数必须指定绝对URL。
     * <p>
     *  此方法始终立即返回,无论图像是否存在。当小程序试图在屏幕上绘制图像时,数据将被加载。绘制图像的图形基元将在屏幕上渐进地绘制。
     * 
     * 
     * @param   url   an absolute URL giving the location of the image.
     * @return  the image at the specified URL.
     * @see     java.awt.Image
     */
    Image getImage(URL url);

    /**
     * Finds and returns the applet in the document represented by this
     * applet context with the given name. The name can be set in the
     * HTML tag by setting the <code>name</code> attribute.
     *
     * <p>
     *  在由此给定名称的小程序上下文表示的文档中查找并返回该小程序。可以通过设置<code> name </code>属性在HTML标记中设置名称。
     * 
     * 
     * @param   name   an applet name.
     * @return  the applet with the given name, or <code>null</code> if
     *          not found.
     */
    Applet getApplet(String name);

    /**
     * Finds all the applets in the document represented by this applet
     * context.
     *
     * <p>
     *  查找由此小程序上下文表示的文档中的所有小程序。
     * 
     * 
     * @return  an enumeration of all applets in the document represented by
     *          this applet context.
     */
    Enumeration<Applet> getApplets();

    /**
     * Requests that the browser or applet viewer show the Web page
     * indicated by the <code>url</code> argument. The browser or
     * applet viewer determines which window or frame to display the
     * Web page. This method may be ignored by applet contexts that
     * are not browsers.
     *
     * <p>
     *  请求浏览器或小程序查看器显示由<code> url </code>参数指示的网页。浏览器或小程序查看器确定显示网页的窗口或框架。此方法可能被不是浏览器的applet上下文忽略。
     * 
     * 
     * @param   url   an absolute URL giving the location of the document.
     */
    void showDocument(URL url);

    /**
     * Requests that the browser or applet viewer show the Web page
     * indicated by the <code>url</code> argument. The
     * <code>target</code> argument indicates in which HTML frame the
     * document is to be displayed.
     * The target argument is interpreted as follows:
     *
     * <center><table border="3" summary="Target arguments and their descriptions">
     * <tr><th>Target Argument</th><th>Description</th></tr>
     * <tr><td><code>"_self"</code>  <td>Show in the window and frame that
     *                                   contain the applet.</tr>
     * <tr><td><code>"_parent"</code><td>Show in the applet's parent frame. If
     *                                   the applet's frame has no parent frame,
     *                                   acts the same as "_self".</tr>
     * <tr><td><code>"_top"</code>   <td>Show in the top-level frame of the applet's
     *                                   window. If the applet's frame is the
     *                                   top-level frame, acts the same as "_self".</tr>
     * <tr><td><code>"_blank"</code> <td>Show in a new, unnamed
     *                                   top-level window.</tr>
     * <tr><td><i>name</i><td>Show in the frame or window named <i>name</i>. If
     *                        a target named <i>name</i> does not already exist, a
     *                        new top-level window with the specified name is created,
     *                        and the document is shown there.</tr>
     * </table> </center>
     * <p>
     * An applet viewer or browser is free to ignore <code>showDocument</code>.
     *
     * <p>
     * 请求浏览器或小程序查看器显示由<code> url </code>参数指示的网页。 <code> target </code>参数指示要在哪个HTML框架中显示文档。目标参数解释如下：
     * 
     *  <center> <table border ="3"summary ="目标参数及其说明"> <tr> <th>目标参数</th> <th>描述</th> </tr> <tr> <td> <code>
     * "_ self"</code> <td>在包含小程序的窗口和框架中显示</tr> <tr> <td> <code> applet的父框架。
     * 如果小程序的框架没有父框架,其行为与"_self"相同。</tr> <tr> <td> <code>"_ top"</code> <td>在小程序的顶层框架中显示窗口。
     * 如果小程序的框架是顶层框架,其行为与"_self"相同。</tr> <tr> <td> <code>"_ blank"</code> </t> </b>在名为<i>名称</i>的框架或窗口中显示。
     * 如果名为<i> name </i>的目标不存在,则会创建一个具有指定名称的新顶级窗口,并在其中显示该文档。</tr> </table> </center>。
     * <p>
     *  小程序查看器或浏览器可以忽略<code> showDocument </code>。
     * 
     * 
     * @param   url   an absolute URL giving the location of the document.
     * @param   target   a <code>String</code> indicating where to display
     *                   the page.
     */
    public void showDocument(URL url, String target);

    /**
     * Requests that the argument string be displayed in the
     * "status window". Many browsers and applet viewers
     * provide such a window, where the application can inform users of
     * its current state.
     *
     * <p>
     *  请求在"状态窗口"中显示参数字符串。许多浏览器和小应用程序查看器提供这样的窗口,其中应用可以通知用户其当前状态。
     * 
     * 
     * @param   status   a string to display in the status window.
     */
    void showStatus(String status);

    /**
     * Associates the specified stream with the specified key in this
     * applet context. If the applet context previously contained a mapping
     * for this key, the old value is replaced.
     * <p>
     * For security reasons, mapping of streams and keys exists for each
     * codebase. In other words, applet from one codebase cannot access
     * the streams created by an applet from a different codebase
     * <p>
     * <p>
     * 将指定的流与此小程序上下文中的指定键相关联。如果小应用程序上下文先前包含此键的映射,则替换旧值。
     * <p>
     *  出于安全原因,对于每个代码库存在流和键的映射。换句话说,来自一个代码库的applet不能访问由来自不同代码库的applet创建的流
     * <p>
     * 
     * @param key key with which the specified value is to be associated.
     * @param stream stream to be associated with the specified key. If this
     *               parameter is <code>null</code>, the specified key is removed
     *               in this applet context.
     * @throws IOException if the stream size exceeds a certain
     *         size limit. Size limit is decided by the implementor of this
     *         interface.
     * @since 1.4
     */
    public void setStream(String key, InputStream stream)throws IOException;

    /**
     * Returns the stream to which specified key is associated within this
     * applet context. Returns <tt>null</tt> if the applet context contains
     * no stream for this key.
     * <p>
     * For security reasons, mapping of streams and keys exists for each
     * codebase. In other words, applet from one codebase cannot access
     * the streams created by an applet from a different codebase
     * <p>
     * <p>
     *  返回在此小程序上下文中与指定键相关联的流。如果小程序上下文不包含此键的流,则返回<tt> null </tt>。
     * <p>
     *  出于安全原因,对于每个代码库存在流和键的映射。换句话说,来自一个代码库的applet不能访问由来自不同代码库的applet创建的流
     * <p>
     * 
     * @return the stream to which this applet context maps the key
     * @param key key whose associated stream is to be returned.
     * @since 1.4
     */
    public InputStream getStream(String key);

    /**
     * Finds all the keys of the streams in this applet context.
     * <p>
     * For security reasons, mapping of streams and keys exists for each
     * codebase. In other words, applet from one codebase cannot access
     * the streams created by an applet from a different codebase
     * <p>
     * <p>
     *  在这个applet上下文中查找流的所有键。
     * <p>
     * 
     * @return  an Iterator of all the names of the streams in this applet
     *          context.
     * @since 1.4
     */
    public Iterator<String> getStreamKeys();
}
