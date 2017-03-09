/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.security.Permission;
import sun.net.www.ParseUtil;

/**
 * A URL Connection to a Java ARchive (JAR) file or an entry in a JAR
 * file.
 *
 * <p>The syntax of a JAR URL is:
 *
 * <pre>
 * jar:&lt;url&gt;!/{entry}
 * </pre>
 *
 * <p>for example:
 *
 * <p>{@code jar:http://www.foo.com/bar/baz.jar!/COM/foo/Quux.class}
 *
 * <p>Jar URLs should be used to refer to a JAR file or entries in
 * a JAR file. The example above is a JAR URL which refers to a JAR
 * entry. If the entry name is omitted, the URL refers to the whole
 * JAR file:
 *
 * {@code jar:http://www.foo.com/bar/baz.jar!/}
 *
 * <p>Users should cast the generic URLConnection to a
 * JarURLConnection when they know that the URL they created is a JAR
 * URL, and they need JAR-specific functionality. For example:
 *
 * <pre>
 * URL url = new URL("jar:file:/home/duke/duke.jar!/");
 * JarURLConnection jarConnection = (JarURLConnection)url.openConnection();
 * Manifest manifest = jarConnection.getManifest();
 * </pre>
 *
 * <p>JarURLConnection instances can only be used to read from JAR files.
 * It is not possible to get a {@link java.io.OutputStream} to modify or write
 * to the underlying JAR file using this class.
 * <p>Examples:
 *
 * <dl>
 *
 * <dt>A Jar entry
 * <dd>{@code jar:http://www.foo.com/bar/baz.jar!/COM/foo/Quux.class}
 *
 * <dt>A Jar file
 * <dd>{@code jar:http://www.foo.com/bar/baz.jar!/}
 *
 * <dt>A Jar directory
 * <dd>{@code jar:http://www.foo.com/bar/baz.jar!/COM/foo/}
 *
 * </dl>
 *
 * <p>{@code !/} is referred to as the <em>separator</em>.
 *
 * <p>When constructing a JAR url via {@code new URL(context, spec)},
 * the following rules apply:
 *
 * <ul>
 *
 * <li>if there is no context URL and the specification passed to the
 * URL constructor doesn't contain a separator, the URL is considered
 * to refer to a JarFile.
 *
 * <li>if there is a context URL, the context URL is assumed to refer
 * to a JAR file or a Jar directory.
 *
 * <li>if the specification begins with a '/', the Jar directory is
 * ignored, and the spec is considered to be at the root of the Jar
 * file.
 *
 * <p>Examples:
 *
 * <dl>
 *
 * <dt>context: <b>jar:http://www.foo.com/bar/jar.jar!/</b>,
 * spec:<b>baz/entry.txt</b>
 *
 * <dd>url:<b>jar:http://www.foo.com/bar/jar.jar!/baz/entry.txt</b>
 *
 * <dt>context: <b>jar:http://www.foo.com/bar/jar.jar!/baz</b>,
 * spec:<b>entry.txt</b>
 *
 * <dd>url:<b>jar:http://www.foo.com/bar/jar.jar!/baz/entry.txt</b>
 *
 * <dt>context: <b>jar:http://www.foo.com/bar/jar.jar!/baz</b>,
 * spec:<b>/entry.txt</b>
 *
 * <dd>url:<b>jar:http://www.foo.com/bar/jar.jar!/entry.txt</b>
 *
 * </dl>
 *
 * </ul>
 *
 * <p>
 *  URL与Java ARchive(JAR)文件或JAR文件中的条目的连接。
 * 
 *  <p> JAR网址的语法如下：
 * 
 * <pre>
 *  jar：&lt; url&gt;！/ {entry}
 * </pre>
 * 
 *  <p>例如：
 * 
 *  <p> {@ code jar：http：//www.foo.com/bar/baz.jar！/COM/foo/Quux.class}
 * 
 *  <p> Jar URL应用于引用JAR文件或JAR文件中的条目。上面的示例是一个JAR URL,它引用了一个JAR条目。如果省略条目名称,则URL引用整个JAR文件：
 * 
 *  {@code jar：http：//www.foo.com/bar/baz.jar！/}
 * 
 *  <p>当用户知道他们创建的URL是JAR URL时,应该将通用URLConnection转换为JarURLConnection,并且它们需要JAR特定的功能。例如：
 * 
 * <pre>
 *  URL url = new URL("jar：file：/home/duke/duke.jar！/"); JarURLConnection jarConnection =(JarURLConnecti
 * on)url.openConnection();清单manifest = jarConnection.getManifest();。
 * </pre>
 * 
 *  <p> JarURLConnection实例只能用于从JAR文件读取。不可能使用此类来获取{@link java.io.OutputStream}来修改或写入底层JAR文件。 <p>示例：
 * 
 * <dl>
 * 
 *  <dt> Jar条目<dd> {@ code jar：http：//www.foo.com/bar/baz.jar！/COM/foo/Quux.class}
 * 
 *  <dt> Jar文件<dd> {@ code jar：http：//www.foo.com/bar/baz.jar！/}
 * 
 *  <dt> Jar目录<dd> {@ code jar：http：//www.foo.com/bar/baz.jar！/ COM / foo /}
 * 
 * </dl>
 * 
 *  <p> {@ code！/}称为<em>分隔符</em>。
 * 
 * <p>通过{@code new URL(context,spec)}构造JAR网址时,应遵循以下规则：
 * 
 * <ul>
 * 
 *  <li>如果没有上下文网址,且传递给网址构造函数的规范不包含分隔符,则该网址被认为是指JarFile。
 * 
 *  <li>如果有上下文网址,则假定上下文网址指向JAR文件或Jar目录。
 * 
 *  <li>如果规范以"/"开头,则会忽略Jar目录,并且该规范被视为位于Jar文件的根目录。
 * 
 *  <p>示例：
 * 
 * <dl>
 * 
 *  <dt>上下文：<b> jar：http：//www.foo.com/bar/jar.jar！/ </b>,spec：<b> baz / entry.txt </b>
 * 
 * @see java.net.URL
 * @see java.net.URLConnection
 *
 * @see java.util.jar.JarFile
 * @see java.util.jar.JarInputStream
 * @see java.util.jar.Manifest
 * @see java.util.zip.ZipEntry
 *
 * @author Benjamin Renaud
 * @since 1.2
 */
public abstract class JarURLConnection extends URLConnection {

    private URL jarFileURL;
    private String entryName;

    /**
     * The connection to the JAR file URL, if the connection has been
     * initiated. This should be set by connect.
     * <p>
     * 
     *  <dd> url：<b> jar：http：//www.foo.com/bar/jar.jar！/baz/entry.txt </b>
     * 
     *  <dt>上下文：<b> jar：http：//www.foo.com/bar/jar.jar！/ baz </b>,spec：<b> entry.txt </b>
     * 
     *  <dd> url：<b> jar：http：//www.foo.com/bar/jar.jar！/baz/entry.txt </b>
     * 
     *  <dt>上下文：<b> jar：http：//www.foo.com/bar/jar.jar！/ baz </b>,spec：<b> /entry.txt </b>
     * 
     *  <dd> url：<b> jar：http：//www.foo.com/bar/jar.jar！/entry.txt </b>
     * 
     * </dl>
     * 
     * </ul>
     * 
     */
    protected URLConnection jarFileURLConnection;

    /**
     * Creates the new JarURLConnection to the specified URL.
     * <p>
     *  与JAR文件URL的连接(如果连接已启动)。这应该由connect设置。
     * 
     * 
     * @param url the URL
     * @throws MalformedURLException if no legal protocol
     * could be found in a specification string or the
     * string could not be parsed.
     */

    protected JarURLConnection(URL url) throws MalformedURLException {
        super(url);
        parseSpecs(url);
    }

    /* get the specs for a given url out of the cache, and compute and
     * cache them if they're not there.
     * <p>
     *  创建指向指定URL的新JarURLConnection。
     * 
     */
    private void parseSpecs(URL url) throws MalformedURLException {
        String spec = url.getFile();

        int separator = spec.indexOf("!/");
        /*
         * REMIND: we don't handle nested JAR URLs
         * <p>
         *  如果他们不在那里缓存他们。
         * 
         */
        if (separator == -1) {
            throw new MalformedURLException("no !/ found in url spec:" + spec);
        }

        jarFileURL = new URL(spec.substring(0, separator++));
        entryName = null;

        /* if ! is the last letter of the innerURL, entryName is null */
        if (++separator != spec.length()) {
            entryName = spec.substring(separator, spec.length());
            entryName = ParseUtil.decode (entryName);
        }
    }

    /**
     * Returns the URL for the Jar file for this connection.
     *
     * <p>
     *  REMIND：我们不处理嵌套的JAR URL
     * 
     * 
     * @return the URL for the Jar file for this connection.
     */
    public URL getJarFileURL() {
        return jarFileURL;
    }

    /**
     * Return the entry name for this connection. This method
     * returns null if the JAR file URL corresponding to this
     * connection points to a JAR file and not a JAR file entry.
     *
     * <p>
     *  返回此连接的Jar文件的URL。
     * 
     * 
     * @return the entry name for this connection, if any.
     */
    public String getEntryName() {
        return entryName;
    }

    /**
     * Return the JAR file for this connection.
     *
     * <p>
     *  返回此连接的条目名称。如果与此连接对应的JAR文件URL指向JAR文件而不是JAR文件条目,则此方法返回null。
     * 
     * 
     * @return the JAR file for this connection. If the connection is
     * a connection to an entry of a JAR file, the JAR file object is
     * returned
     *
     * @exception IOException if an IOException occurs while trying to
     * connect to the JAR file for this connection.
     *
     * @see #connect
     */
    public abstract JarFile getJarFile() throws IOException;

    /**
     * Returns the Manifest for this connection, or null if none.
     *
     * <p>
     *  返回此连接的JAR文件。
     * 
     * 
     * @return the manifest object corresponding to the JAR file object
     * for this connection.
     *
     * @exception IOException if getting the JAR file for this
     * connection causes an IOException to be thrown.
     *
     * @see #getJarFile
     */
    public Manifest getManifest() throws IOException {
        return getJarFile().getManifest();
    }

    /**
     * Return the JAR entry object for this connection, if any. This
     * method returns null if the JAR file URL corresponding to this
     * connection points to a JAR file and not a JAR file entry.
     *
     * <p>
     * 返回此连接的清单,如果没有,则返回null。
     * 
     * 
     * @return the JAR entry object for this connection, or null if
     * the JAR URL for this connection points to a JAR file.
     *
     * @exception IOException if getting the JAR file for this
     * connection causes an IOException to be thrown.
     *
     * @see #getJarFile
     * @see #getJarEntry
     */
    public JarEntry getJarEntry() throws IOException {
        return getJarFile().getJarEntry(entryName);
    }

    /**
     * Return the Attributes object for this connection if the URL
     * for it points to a JAR file entry, null otherwise.
     *
     * <p>
     *  返回此连接的JAR条目对象(如果有)。如果与此连接对应的JAR文件URL指向JAR文件而不是JAR文件条目,则此方法返回null。
     * 
     * 
     * @return the Attributes object for this connection if the URL
     * for it points to a JAR file entry, null otherwise.
     *
     * @exception IOException if getting the JAR entry causes an
     * IOException to be thrown.
     *
     * @see #getJarEntry
     */
    public Attributes getAttributes() throws IOException {
        JarEntry e = getJarEntry();
        return e != null ? e.getAttributes() : null;
    }

    /**
     * Returns the main Attributes for the JAR file for this
     * connection.
     *
     * <p>
     *  如果此连接的URL指向JAR文件条目,则返回Attributes对象,否则返回null。
     * 
     * 
     * @return the main Attributes for the JAR file for this
     * connection.
     *
     * @exception IOException if getting the manifest causes an
     * IOException to be thrown.
     *
     * @see #getJarFile
     * @see #getManifest
     */
    public Attributes getMainAttributes() throws IOException {
        Manifest man = getManifest();
        return man != null ? man.getMainAttributes() : null;
    }

    /**
     * Return the Certificate object for this connection if the URL
     * for it points to a JAR file entry, null otherwise. This method
     * can only be called once
     * the connection has been completely verified by reading
     * from the input stream until the end of the stream has been
     * reached. Otherwise, this method will return {@code null}
     *
     * <p>
     *  返回此连接的JAR文件的主要属性。
     * 
     * 
     * @return the Certificate object for this connection if the URL
     * for it points to a JAR file entry, null otherwise.
     *
     * @exception IOException if getting the JAR entry causes an
     * IOException to be thrown.
     *
     * @see #getJarEntry
     */
    public java.security.cert.Certificate[] getCertificates()
         throws IOException
    {
        JarEntry e = getJarEntry();
        return e != null ? e.getCertificates() : null;
    }
}
