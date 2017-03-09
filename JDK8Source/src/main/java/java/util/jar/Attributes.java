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

package java.util.jar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.AbstractSet;
import java.util.Iterator;
import sun.util.logging.PlatformLogger;
import java.util.Comparator;
import sun.misc.ASCIICaseInsensitiveComparator;

/**
 * The Attributes class maps Manifest attribute names to associated string
 * values. Valid attribute names are case-insensitive, are restricted to
 * the ASCII characters in the set [0-9a-zA-Z_-], and cannot exceed 70
 * characters in length. Attribute values can contain any characters and
 * will be UTF8-encoded when written to the output stream.  See the
 * <a href="../../../../technotes/guides/jar/jar.html">JAR File Specification</a>
 * for more information about valid attribute names and values.
 *
 * <p>
 *  Attributes类将Manifest属性名称映射到关联的字符串值。有效的属性名称不区分大小写,仅限于集合[0-9a-zA-Z_-]中的ASCII字符,并且长度不能超过70个字符。
 * 属性值可以包含任何字符,并且在写入输出流时将进行UTF8编码。
 * 有关有效的属性名称和值的详细信息,请参见<a href="../../../../technotes/guides/jar/jar.html"> JAR文件规范</a>。
 * 
 * 
 * @author  David Connelly
 * @see     Manifest
 * @since   1.2
 */
public class Attributes implements Map<Object,Object>, Cloneable {
    /**
     * The attribute name-value mappings.
     * <p>
     *  属性名称 - 值映射。
     * 
     */
    protected Map<Object,Object> map;

    /**
     * Constructs a new, empty Attributes object with default size.
     * <p>
     *  构造一个具有默认大小的新的空的Attributes对象。
     * 
     */
    public Attributes() {
        this(11);
    }

    /**
     * Constructs a new, empty Attributes object with the specified
     * initial size.
     *
     * <p>
     *  构造具有指定初始大小的新的空的Attributes对象。
     * 
     * 
     * @param size the initial number of attributes
     */
    public Attributes(int size) {
        map = new HashMap<>(size);
    }

    /**
     * Constructs a new Attributes object with the same attribute name-value
     * mappings as in the specified Attributes.
     *
     * <p>
     *  构造具有与指定属性中相同的属性名称 - 值映射的新Attributes对象。
     * 
     * 
     * @param attr the specified Attributes
     */
    public Attributes(Attributes attr) {
        map = new HashMap<>(attr);
    }


    /**
     * Returns the value of the specified attribute name, or null if the
     * attribute name was not found.
     *
     * <p>
     *  返回指定属性名称的值,如果未找到属性名称,则返回null。
     * 
     * 
     * @param name the attribute name
     * @return the value of the specified attribute name, or null if
     *         not found.
     */
    public Object get(Object name) {
        return map.get(name);
    }

    /**
     * Returns the value of the specified attribute name, specified as
     * a string, or null if the attribute was not found. The attribute
     * name is case-insensitive.
     * <p>
     * This method is defined as:
     * <pre>
     *      return (String)get(new Attributes.Name((String)name));
     * </pre>
     *
     * <p>
     *  返回指定为字符串的指定属性名称的值,如果未找到该属性,则返回null。属性名称不区分大小写。
     * <p>
     *  该方法定义为：
     * <pre>
     *  return(String)get(new Attributes.Name((String)name));
     * </pre>
     * 
     * 
     * @param name the attribute name as a string
     * @return the String value of the specified attribute name, or null if
     *         not found.
     * @throws IllegalArgumentException if the attribute name is invalid
     */
    public String getValue(String name) {
        return (String)get(new Attributes.Name(name));
    }

    /**
     * Returns the value of the specified Attributes.Name, or null if the
     * attribute was not found.
     * <p>
     * This method is defined as:
     * <pre>
     *     return (String)get(name);
     * </pre>
     *
     * <p>
     *  返回指定Attributes.Name的值,如果未找到该属性,则返回null。
     * <p>
     *  该方法定义为：
     * <pre>
     *  return(String)get(name);
     * </pre>
     * 
     * 
     * @param name the Attributes.Name object
     * @return the String value of the specified Attribute.Name, or null if
     *         not found.
     */
    public String getValue(Name name) {
        return (String)get(name);
    }

    /**
     * Associates the specified value with the specified attribute name
     * (key) in this Map. If the Map previously contained a mapping for
     * the attribute name, the old value is replaced.
     *
     * <p>
     * 将指定的值与此映射中指定的属性名称(键)相关联。如果Map先前包含属性名称的映射,则替换旧值。
     * 
     * 
     * @param name the attribute name
     * @param value the attribute value
     * @return the previous value of the attribute, or null if none
     * @exception ClassCastException if the name is not a Attributes.Name
     *            or the value is not a String
     */
    public Object put(Object name, Object value) {
        return map.put((Attributes.Name)name, (String)value);
    }

    /**
     * Associates the specified value with the specified attribute name,
     * specified as a String. The attributes name is case-insensitive.
     * If the Map previously contained a mapping for the attribute name,
     * the old value is replaced.
     * <p>
     * This method is defined as:
     * <pre>
     *      return (String)put(new Attributes.Name(name), value);
     * </pre>
     *
     * <p>
     *  将指定的值与指定的属性名称相关联,指定为字符串。属性名称不区分大小写。如果Map先前包含属性名称的映射,则替换旧值。
     * <p>
     *  该方法定义为：
     * <pre>
     *  return(String)put(new Attributes.Name(name),value);
     * </pre>
     * 
     * 
     * @param name the attribute name as a string
     * @param value the attribute value
     * @return the previous value of the attribute, or null if none
     * @exception IllegalArgumentException if the attribute name is invalid
     */
    public String putValue(String name, String value) {
        return (String)put(new Name(name), value);
    }

    /**
     * Removes the attribute with the specified name (key) from this Map.
     * Returns the previous attribute value, or null if none.
     *
     * <p>
     *  从此映射中删除具有指定名称(键)的属性。返回上一个属性值,如果没有,则返回null。
     * 
     * 
     * @param name attribute name
     * @return the previous value of the attribute, or null if none
     */
    public Object remove(Object name) {
        return map.remove(name);
    }

    /**
     * Returns true if this Map maps one or more attribute names (keys)
     * to the specified value.
     *
     * <p>
     *  如果此映射将一个或多个属性名称(键)映射到指定的值,则返回true。
     * 
     * 
     * @param value the attribute value
     * @return true if this Map maps one or more attribute names to
     *         the specified value
     */
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /**
     * Returns true if this Map contains the specified attribute name (key).
     *
     * <p>
     *  如果此地图包含指定的属性名称(键),则返回true。
     * 
     * 
     * @param name the attribute name
     * @return true if this Map contains the specified attribute name
     */
    public boolean containsKey(Object name) {
        return map.containsKey(name);
    }

    /**
     * Copies all of the attribute name-value mappings from the specified
     * Attributes to this Map. Duplicate mappings will be replaced.
     *
     * <p>
     *  将所有属性名称 - 值映射从指定的属性复制到此映射。将替换重复的映射。
     * 
     * 
     * @param attr the Attributes to be stored in this map
     * @exception ClassCastException if attr is not an Attributes
     */
    public void putAll(Map<?,?> attr) {
        // ## javac bug?
        if (!Attributes.class.isInstance(attr))
            throw new ClassCastException();
        for (Map.Entry<?,?> me : (attr).entrySet())
            put(me.getKey(), me.getValue());
    }

    /**
     * Removes all attributes from this Map.
     * <p>
     *  从此地图中删除所有属性。
     * 
     */
    public void clear() {
        map.clear();
    }

    /**
     * Returns the number of attributes in this Map.
     * <p>
     *  返回此地图中的属性数。
     * 
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns true if this Map contains no attributes.
     * <p>
     *  如果此地图不包含任何属性,则返回true。
     * 
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns a Set view of the attribute names (keys) contained in this Map.
     * <p>
     *  返回此映射中包含的属性名称(键)的集合视图。
     * 
     */
    public Set<Object> keySet() {
        return map.keySet();
    }

    /**
     * Returns a Collection view of the attribute values contained in this Map.
     * <p>
     *  返回此映射中包含的属性值的集合视图。
     * 
     */
    public Collection<Object> values() {
        return map.values();
    }

    /**
     * Returns a Collection view of the attribute name-value mappings
     * contained in this Map.
     * <p>
     *  返回此映射中包含的属性名称 - 值映射的集合视图。
     * 
     */
    public Set<Map.Entry<Object,Object>> entrySet() {
        return map.entrySet();
    }

    /**
     * Compares the specified Attributes object with this Map for equality.
     * Returns true if the given object is also an instance of Attributes
     * and the two Attributes objects represent the same mappings.
     *
     * <p>
     *  将指定的Attributes对象与此Map比较以确保相等。如果给定对象也是属性的实例,并且两个Attributes对象表示相同的映射,则返回true。
     * 
     * 
     * @param o the Object to be compared
     * @return true if the specified Object is equal to this Map
     */
    public boolean equals(Object o) {
        return map.equals(o);
    }

    /**
     * Returns the hash code value for this Map.
     * <p>
     * 返回此Map的哈希码值。
     * 
     */
    public int hashCode() {
        return map.hashCode();
    }

    /**
     * Returns a copy of the Attributes, implemented as follows:
     * <pre>
     *     public Object clone() { return new Attributes(this); }
     * </pre>
     * Since the attribute names and values are themselves immutable,
     * the Attributes returned can be safely modified without affecting
     * the original.
     * <p>
     *  返回属性的副本,实现如下：
     * <pre>
     *  public Object clone(){return new Attributes(this); }}
     * </pre>
     *  由于属性名称和值本身是不可变的,所以返回的属性可以安全地修改而不影响原始属性。
     * 
     */
    public Object clone() {
        return new Attributes(this);
    }

    /*
     * Writes the current attributes to the specified data output stream.
     * XXX Need to handle UTF8 values and break up lines longer than 72 bytes
     * <p>
     *  将当前属性写入指定的数据输出流。 XXX需要处理UTF8值并拆分超过72个字节的行
     * 
     */
     void write(DataOutputStream os) throws IOException {
        Iterator<Map.Entry<Object, Object>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> e = it.next();
            StringBuffer buffer = new StringBuffer(
                                        ((Name)e.getKey()).toString());
            buffer.append(": ");

            String value = (String)e.getValue();
            if (value != null) {
                byte[] vb = value.getBytes("UTF8");
                value = new String(vb, 0, 0, vb.length);
            }
            buffer.append(value);

            buffer.append("\r\n");
            Manifest.make72Safe(buffer);
            os.writeBytes(buffer.toString());
        }
        os.writeBytes("\r\n");
    }

    /*
     * Writes the current attributes to the specified data output stream,
     * make sure to write out the MANIFEST_VERSION or SIGNATURE_VERSION
     * attributes first.
     *
     * XXX Need to handle UTF8 values and break up lines longer than 72 bytes
     * <p>
     *  将当前属性写入指定的数据输出流,请务必先写出MANIFEST_VERSION或SIGNATURE_VERSION属性。
     * 
     *  XXX需要处理UTF8值并拆分超过72个字节的行
     * 
     */
    void writeMain(DataOutputStream out) throws IOException
    {
        // write out the *-Version header first, if it exists
        String vername = Name.MANIFEST_VERSION.toString();
        String version = getValue(vername);
        if (version == null) {
            vername = Name.SIGNATURE_VERSION.toString();
            version = getValue(vername);
        }

        if (version != null) {
            out.writeBytes(vername+": "+version+"\r\n");
        }

        // write out all attributes except for the version
        // we wrote out earlier
        Iterator<Map.Entry<Object, Object>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> e = it.next();
            String name = ((Name)e.getKey()).toString();
            if ((version != null) && ! (name.equalsIgnoreCase(vername))) {

                StringBuffer buffer = new StringBuffer(name);
                buffer.append(": ");

                String value = (String)e.getValue();
                if (value != null) {
                    byte[] vb = value.getBytes("UTF8");
                    value = new String(vb, 0, 0, vb.length);
                }
                buffer.append(value);

                buffer.append("\r\n");
                Manifest.make72Safe(buffer);
                out.writeBytes(buffer.toString());
            }
        }
        out.writeBytes("\r\n");
    }

    /*
     * Reads attributes from the specified input stream.
     * XXX Need to handle UTF8 values.
     * <p>
     *  从指定的输入流读取属性。 XXX需要处理UTF8值。
     * 
     */
    void read(Manifest.FastInputStream is, byte[] lbuf) throws IOException {
        String name = null, value = null;
        byte[] lastline = null;

        int len;
        while ((len = is.readLine(lbuf)) != -1) {
            boolean lineContinued = false;
            if (lbuf[--len] != '\n') {
                throw new IOException("line too long");
            }
            if (len > 0 && lbuf[len-1] == '\r') {
                --len;
            }
            if (len == 0) {
                break;
            }
            int i = 0;
            if (lbuf[0] == ' ') {
                // continuation of previous line
                if (name == null) {
                    throw new IOException("misplaced continuation line");
                }
                lineContinued = true;
                byte[] buf = new byte[lastline.length + len - 1];
                System.arraycopy(lastline, 0, buf, 0, lastline.length);
                System.arraycopy(lbuf, 1, buf, lastline.length, len - 1);
                if (is.peek() == ' ') {
                    lastline = buf;
                    continue;
                }
                value = new String(buf, 0, buf.length, "UTF8");
                lastline = null;
            } else {
                while (lbuf[i++] != ':') {
                    if (i >= len) {
                        throw new IOException("invalid header field");
                    }
                }
                if (lbuf[i++] != ' ') {
                    throw new IOException("invalid header field");
                }
                name = new String(lbuf, 0, 0, i - 2);
                if (is.peek() == ' ') {
                    lastline = new byte[len - i];
                    System.arraycopy(lbuf, i, lastline, 0, len - i);
                    continue;
                }
                value = new String(lbuf, i, len - i, "UTF8");
            }
            try {
                if ((putValue(name, value) != null) && (!lineContinued)) {
                    PlatformLogger.getLogger("java.util.jar").warning(
                                     "Duplicate name in Manifest: " + name
                                     + ".\n"
                                     + "Ensure that the manifest does not "
                                     + "have duplicate entries, and\n"
                                     + "that blank lines separate "
                                     + "individual sections in both your\n"
                                     + "manifest and in the META-INF/MANIFEST.MF "
                                     + "entry in the jar file.");
                }
            } catch (IllegalArgumentException e) {
                throw new IOException("invalid header field name: " + name);
            }
        }
    }

    /**
     * The Attributes.Name class represents an attribute name stored in
     * this Map. Valid attribute names are case-insensitive, are restricted
     * to the ASCII characters in the set [0-9a-zA-Z_-], and cannot exceed
     * 70 characters in length. Attribute values can contain any characters
     * and will be UTF8-encoded when written to the output stream.  See the
     * <a href="../../../../technotes/guides/jar/jar.html">JAR File Specification</a>
     * for more information about valid attribute names and values.
     * <p>
     *  Attributes.Name类表示存储在此Map中的属性名称。有效的属性名称不区分大小写,仅限于集合[0-9a-zA-Z_-]中的ASCII字符,并且长度不能超过70个字符。
     * 属性值可以包含任何字符,并且在写入输出流时将进行UTF8编码。
     * 有关有效的属性名称和值的详细信息,请参见<a href="../../../../technotes/guides/jar/jar.html"> JAR文件规范</a>。
     * 
     */
    public static class Name {
        private String name;
        private int hashCode = -1;

        /**
         * Constructs a new attribute name using the given string name.
         *
         * <p>
         *  使用给定的字符串名称构造新的属性名称。
         * 
         * 
         * @param name the attribute string name
         * @exception IllegalArgumentException if the attribute name was
         *            invalid
         * @exception NullPointerException if the attribute name was null
         */
        public Name(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            if (!isValid(name)) {
                throw new IllegalArgumentException(name);
            }
            this.name = name.intern();
        }

        private static boolean isValid(String name) {
            int len = name.length();
            if (len > 70 || len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                if (!isValid(name.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isValid(char c) {
            return isAlpha(c) || isDigit(c) || c == '_' || c == '-';
        }

        private static boolean isAlpha(char c) {
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        /**
         * Compares this attribute name to another for equality.
         * <p>
         *  将此属性名称与另一个属性名称相等比较。
         * 
         * 
         * @param o the object to compare
         * @return true if this attribute name is equal to the
         *         specified attribute object
         */
        public boolean equals(Object o) {
            if (o instanceof Name) {
                Comparator<String> c = ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER;
                return c.compare(name, ((Name)o).name) == 0;
            } else {
                return false;
            }
        }

        /**
         * Computes the hash value for this attribute name.
         * <p>
         *  计算此属性名称的哈希值。
         * 
         */
        public int hashCode() {
            if (hashCode == -1) {
                hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(name);
            }
            return hashCode;
        }

        /**
         * Returns the attribute name as a String.
         * <p>
         *  以String形式返回属性名称。
         * 
         */
        public String toString() {
            return name;
        }

        /**
         * <code>Name</code> object for <code>Manifest-Version</code>
         * manifest attribute. This attribute indicates the version number
         * of the manifest standard to which a JAR file's manifest conforms.
         * <p>
         * <code> Manifest-Version </code>清单属性的<code> Name </code>对象。此属性指示JAR文件清单符合的清单标准的版本号。
         * 
         * 
         * @see <a href="../../../../technotes/guides/jar/jar.html#JAR_Manifest">
         *      Manifest and Signature Specification</a>
         */
        public static final Name MANIFEST_VERSION = new Name("Manifest-Version");

        /**
         * <code>Name</code> object for <code>Signature-Version</code>
         * manifest attribute used when signing JAR files.
         * <p>
         *  对签名JAR文件时使用的<code>签名版本</code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/jar/jar.html#JAR_Manifest">
         *      Manifest and Signature Specification</a>
         */
        public static final Name SIGNATURE_VERSION = new Name("Signature-Version");

        /**
         * <code>Name</code> object for <code>Content-Type</code>
         * manifest attribute.
         * <p>
         *  <code> Content-Type </code>清单属性的<code> Name </code>对象。
         * 
         */
        public static final Name CONTENT_TYPE = new Name("Content-Type");

        /**
         * <code>Name</code> object for <code>Class-Path</code>
         * manifest attribute. Bundled extensions can use this attribute
         * to find other JAR files containing needed classes.
         * <p>
         *  <code>类路径</code>清单属性的<code> Name </code>对象。捆绑扩展可以使用此属性来查找包含所需类的其他JAR文件。
         * 
         * 
         * @see <a href="../../../../technotes/guides/jar/jar.html#classpath">
         *      JAR file specification</a>
         */
        public static final Name CLASS_PATH = new Name("Class-Path");

        /**
         * <code>Name</code> object for <code>Main-Class</code> manifest
         * attribute used for launching applications packaged in JAR files.
         * The <code>Main-Class</code> attribute is used in conjunction
         * with the <code>-jar</code> command-line option of the
         * <tt>java</tt> application launcher.
         * <p>
         *  用于启动JAR文件中打包的应用程序的<code> Main-Class </code>清单属性的<code> Name </code>对象。
         *  <code> Main-Class </code>属性与<tt> java </tt>应用程序启动器的<code> -jar </code>命令行选项结合使用。
         * 
         */
        public static final Name MAIN_CLASS = new Name("Main-Class");

        /**
         * <code>Name</code> object for <code>Sealed</code> manifest attribute
         * used for sealing.
         * <p>
         *  用于密封的<code>密封</code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/jar/jar.html#sealing">
         *      Package Sealing</a>
         */
        public static final Name SEALED = new Name("Sealed");

       /**
         * <code>Name</code> object for <code>Extension-List</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * <p>
         *  用于声明对已安装扩展的依赖性的<code> Extension-List </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        public static final Name EXTENSION_LIST = new Name("Extension-List");

        /**
         * <code>Name</code> object for <code>Extension-Name</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * <p>
         *  用于声明对已安装扩展的依赖性的<code> Extension-Name </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        public static final Name EXTENSION_NAME = new Name("Extension-Name");

        /**
         * <code>Name</code> object for <code>Extension-Name</code> manifest attribute
         * used for declaring dependencies on installed extensions.
         * <p>
         *  用于声明对已安装扩展的依赖性的<code> Extension-Name </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @deprecated Extension mechanism will be removed in a future release.
         *             Use class path instead.
         * @see <a href="../../../../technotes/guides/extensions/spec.html#dependency">
         *      Installed extension dependency</a>
         */
        @Deprecated
        public static final Name EXTENSION_INSTALLATION = new Name("Extension-Installation");

        /**
         * <code>Name</code> object for <code>Implementation-Title</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于包版本化的<code> Implementation-Title </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_TITLE = new Name("Implementation-Title");

        /**
         * <code>Name</code> object for <code>Implementation-Version</code>
         * manifest attribute used for package versioning.
         * <p>
         * 用于包版本化的<code>实现版本</code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_VERSION = new Name("Implementation-Version");

        /**
         * <code>Name</code> object for <code>Implementation-Vendor</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于包版本化的<code> Implementation-Vendor </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");

        /**
         * <code>Name</code> object for <code>Implementation-Vendor-Id</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于包版本化的<code> Implementation-Vendor-Id </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @deprecated Extension mechanism will be removed in a future release.
         *             Use class path instead.
         * @see <a href="../../../../technotes/guides/extensions/versioning.html#applet">
         *      Optional Package Versioning</a>
         */
        @Deprecated
        public static final Name IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");

       /**
         * <code>Name</code> object for <code>Implementation-URL</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于包版本化的<code> Implementation-URL </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @deprecated Extension mechanism will be removed in a future release.
         *             Use class path instead.
         * @see <a href="../../../../technotes/guides/extensions/versioning.html#applet">
         *      Optional Package Versioning</a>
         */
        @Deprecated
        public static final Name IMPLEMENTATION_URL = new Name("Implementation-URL");

        /**
         * <code>Name</code> object for <code>Specification-Title</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于包版本化的<code> Specification-Title </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_TITLE = new Name("Specification-Title");

        /**
         * <code>Name</code> object for <code>Specification-Version</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于软件包版本控制的<code> Specification-Version </code>清单属性的<code> Name </code>对象。
         * 
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_VERSION = new Name("Specification-Version");

        /**
         * <code>Name</code> object for <code>Specification-Vendor</code>
         * manifest attribute used for package versioning.
         * <p>
         *  用于软件包版本控制的<code> Specification-Vendor </code>清单属性的<code> Name </code>对象。
         * 
         * @see <a href="../../../../technotes/guides/versioning/spec/versioning2.html#wp90779">
         *      Java Product Versioning Specification</a>
         */
        public static final Name SPECIFICATION_VENDOR = new Name("Specification-Vendor");
    }
}
