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

import java.net.URL;

/**
 * When an applet is first created, an applet stub is attached to it
 * using the applet's <code>setStub</code> method. This stub
 * serves as the interface between the applet and the browser
 * environment or applet viewer environment in which the application
 * is running.
 *
 * <p>
 *  当第一次创建applet时,使用applet的<code> setStub </code>方法将applet存根连接到它。此存根用作小程序和运行应用程序的浏览器环境或小应用程序查看器环境之间的接口。
 * 
 * 
 * @author      Arthur van Hoff
 * @see         java.applet.Applet#setStub(java.applet.AppletStub)
 * @since       JDK1.0
 */
public interface AppletStub {
    /**
     * Determines if the applet is active. An applet is active just
     * before its <code>start</code> method is called. It becomes
     * inactive just before its <code>stop</code> method is called.
     *
     * <p>
     *  确定小应用程序是否处于活动状态。一个applet在调用<code> start </code>方法之前是活动的。它在调用<code> stop </code>方法之前变为无效。
     * 
     * 
     * @return  <code>true</code> if the applet is active;
     *          <code>false</code> otherwise.
     */
    boolean isActive();


    /**
     * Gets the URL of the document in which the applet is embedded.
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
     *  获取嵌入了小程序的文档的URL。
     * 例如,假设文档中包含一个小程序：<blockquote> <pre> http://www.oracle.com/technetwork/java/index.html </pre> </blockquote>
     * 文档基础是：<blockquote > <pre> http://www.oracle.com/technetwork/java/index.html </pre> </blockquote>。
     *  获取嵌入了小程序的文档的URL。
     * 
     * 
     * @return  the {@link java.net.URL} of the document that contains the
     *          applet.
     * @see     java.applet.AppletStub#getCodeBase()
     */
    URL getDocumentBase();

    /**
     * Gets the base URL. This is the URL of the directory which contains the applet.
     *
     * <p>
     *  获取基本URL。这是包含小程序的目录的URL。
     * 
     * 
     * @return  the base {@link java.net.URL} of
     *          the directory which contains the applet.
     * @see     java.applet.AppletStub#getDocumentBase()
     */
    URL getCodeBase();

    /**
     * Returns the value of the named parameter in the HTML tag. For
     * example, if an applet is specified as
     * <blockquote><pre>
     * &lt;applet code="Clock" width=50 height=50&gt;
     * &lt;param name=Color value="blue"&gt;
     * &lt;/applet&gt;
     * </pre></blockquote>
     * <p>
     * then a call to <code>getParameter("Color")</code> returns the
     * value <code>"blue"</code>.
     *
     * <p>
     *  返回HTML标记中的命名参数的值。
     * 例如,如果小应用程序被指定为<blockquote> <pre>&lt; applet code ="Clock"width = 50 height = 50&gt; &lt; param name =
     *  Color value ="blue"&gt; &lt; / applet&gt; </pre> </blockquote>。
     *  返回HTML标记中的命名参数的值。
     * <p>
     *  那么调用<code> getParameter("Color")</code>会返回值<code>"blue"</code>。
     * 
     * 
     * @param   name   a parameter name.
     * @return  the value of the named parameter,
     * or <tt>null</tt> if not set.
     */
    String getParameter(String name);

    /**
     * Returns the applet's context.
     *
     * <p>
     * 
     * @return  the applet's context.
     */
    AppletContext getAppletContext();

    /**
     * Called when the applet wants to be resized.
     *
     * <p>
     *  返回小程序的上下文。
     * 
     * 
     * @param   width    the new requested width for the applet.
     * @param   height   the new requested height for the applet.
     */
    void appletResize(int width, int height);
}
