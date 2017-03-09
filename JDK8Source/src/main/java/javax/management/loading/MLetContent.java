/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.loading;


// java import

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the contents of the <CODE>MLET</CODE> tag.
 * It can be consulted by a subclass of {@link MLet} that overrides
 * the {@link MLet#check MLet.check} method.
 *
 * <p>
 *  此类表示<CODE> MLET </CODE>标记的内容。可以通过覆盖{@link MLet#check MLet.check}方法的{@link MLet}子类来查询它。
 * 
 * 
 * @since 1.6
 */
public class MLetContent {


    /**
     * A map of the attributes of the <CODE>MLET</CODE> tag
     * and their values.
     * <p>
     *  标签的属性及其值的映射。<CODE> MLET </CODE>
     * 
     */
    private Map<String,String> attributes;

    /**
     * An ordered list of the TYPE attributes that appeared in nested
     * &lt;PARAM&gt; tags.
     * <p>
     *  嵌套&lt; PARAM&gt;中出现的TYPE属性的有序列表。标签。
     * 
     */
    private List<String> types;

    /**
     * An ordered list of the VALUE attributes that appeared in nested
     * &lt;PARAM&gt; tags.
     * <p>
     *  嵌套&lt; PARAM&gt;中出现的VALUE属性的有序列表。标签。
     * 
     */
    private List<String> values;

    /**
     * The MLet text file's base URL.
     * <p>
     *  MLet文本文件的基本URL。
     * 
     */
    private URL documentURL;
    /**
     * The base URL.
     * <p>
     *  基本URL。
     * 
     */
    private URL baseURL;


    /**
     * Creates an <CODE>MLet</CODE> instance initialized with attributes read
     * from an <CODE>MLET</CODE> tag in an MLet text file.
     *
     * <p>
     *  创建使用从MLet文本文件中的<CODE> MLET </CODE>标签读取的属性初始化的<CODE> MLet </CODE>实例。
     * 
     * 
     * @param url The URL of the MLet text file containing the
     * <CODE>MLET</CODE> tag.
     * @param attributes A map of the attributes of the <CODE>MLET</CODE> tag.
     * The keys in this map are the attribute names in lowercase, for
     * example <code>codebase</code>.  The values are the associated attribute
     * values.
     * @param types A list of the TYPE attributes that appeared in nested
     * &lt;PARAM&gt; tags.
     * @param values A list of the VALUE attributes that appeared in nested
     * &lt;PARAM&gt; tags.
     */
    public MLetContent(URL url, Map<String,String> attributes,
                       List<String> types, List<String> values) {
        this.documentURL = url;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.types = Collections.unmodifiableList(types);
        this.values = Collections.unmodifiableList(values);

        // Initialize baseURL
        //
        String att = getParameter("codebase");
        if (att != null) {
            if (!att.endsWith("/")) {
                att += "/";
            }
            try {
                baseURL = new URL(documentURL, att);
            } catch (MalformedURLException e) {
                // OK : Move to next block as baseURL could not be initialized.
            }
        }
        if (baseURL == null) {
            String file = documentURL.getFile();
            int i = file.lastIndexOf('/');
            if (i >= 0 && i < file.length() - 1) {
                try {
                    baseURL = new URL(documentURL, file.substring(0, i + 1));
                } catch (MalformedURLException e) {
                    // OK : Move to next block as baseURL could not be initialized.
                }
            }
        }
        if (baseURL == null)
            baseURL = documentURL;

    }

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the attributes of the <CODE>MLET</CODE> tag.  The keys in
     * the returned map are the attribute names in lowercase, for
     * example <code>codebase</code>.  The values are the associated
     * attribute values.
     * <p>
     *  获取<CODE> MLET </CODE>标记的属性。返回的映射中的键是小写的属性名称,例如<code> codebase </code>。这些值是关联的属性值。
     * 
     * 
     * @return A map of the attributes of the <CODE>MLET</CODE> tag
     * and their values.
     */
    public Map<String,String> getAttributes() {
        return attributes;
    }

    /**
     * Gets the MLet text file's base URL.
     * <p>
     *  获取MLet文本文件的基本URL。
     * 
     * 
     * @return The MLet text file's base URL.
     */
    public URL getDocumentBase() {
        return documentURL;
    }

    /**
     * Gets the code base URL.
     * <p>
     *  获取代码库URL。
     * 
     * 
     * @return The code base URL.
     */
    public URL getCodeBase() {
        return baseURL;
    }

    /**
     * Gets the list of <CODE>.jar</CODE> files specified by the <CODE>ARCHIVE</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     * <p>
     *  获取由<CODE> MLET </CODE>标签的<CODE> ARCHIVE </CODE>属性指定的<CODE> .jar </CODE>文件的列表。
     * 
     * 
     * @return A comma-separated list of <CODE>.jar</CODE> file names.
     */
    public String getJarFiles() {
        return getParameter("archive");
    }

    /**
     * Gets the value of the <CODE>CODE</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     * <p>
     *  获取<CODE> MLET </CODE>标签的<CODE> CODE </CODE>属性的值。
     * 
     * 
     * @return The value of the <CODE>CODE</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     */
    public String getCode() {
        return getParameter("code");
    }

    /**
     * Gets the value of the <CODE>OBJECT</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     * <p>
     *  获取<CODE> MLET </CODE>标签的<CODE> OBJECT </CODE>属性的值。
     * 
     * 
     * @return The value of the <CODE>OBJECT</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     */
    public String getSerializedObject() {
        return getParameter("object");
    }

    /**
     * Gets the value of the <CODE>NAME</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     * <p>
     *  获取<CODE> MLET </CODE>标签的<CODE> NAME </CODE>属性的值。
     * 
     * 
     * @return The value of the <CODE>NAME</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     */
    public String getName() {
        return getParameter("name");
    }


    /**
     * Gets the value of the <CODE>VERSION</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     * <p>
     *  获取<CODE> MLET </CODE>标签的<CODE> VERSION </CODE>属性的值。
     * 
     * 
     * @return The value of the <CODE>VERSION</CODE>
     * attribute of the <CODE>MLET</CODE> tag.
     */
    public String getVersion() {
        return getParameter("version");
    }

    /**
     * Gets the list of values of the <code>TYPE</code> attribute in
     * each nested &lt;PARAM&gt; tag within the <code>MLET</code>
     * tag.
     * <p>
     * 获取每个嵌套&lt; PARAM&gt;中的<code> TYPE </code>属性的值列表。标签内的<code> MLET </code>标签。
     * 
     * 
     * @return the list of types.
     */
    public List<String> getParameterTypes() {
        return types;
    }

    /**
     * Gets the list of values of the <code>VALUE</code> attribute in
     * each nested &lt;PARAM&gt; tag within the <code>MLET</code>
     * tag.
     * <p>
     *  获取每个嵌套&lt; PARAM&gt;中的<code> VALUE </code>属性的值列表标记内的<code> MLET </code>标记。
     * 
     * 
     * @return the list of values.
     */
    public List<String> getParameterValues() {
        return values;
    }

    /**
     * Gets the value of the specified
     * attribute of the <CODE>MLET</CODE> tag.
     *
     * <p>
     *  获取<CODE> MLET </CODE>标记的指定属性的值。
     * 
     * @param name A string representing the name of the attribute.
     * @return The value of the specified
     * attribute of the <CODE>MLET</CODE> tag.
     */
    private String getParameter(String name) {
        return attributes.get(name.toLowerCase());
    }

}
