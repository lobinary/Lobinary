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

import java.io.FilterInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The Manifest class is used to maintain Manifest entry names and their
 * associated Attributes. There are main Manifest Attributes as well as
 * per-entry Attributes. For information on the Manifest format, please
 * see the
 * <a href="../../../../technotes/guides/jar/jar.html">
 * Manifest format specification</a>.
 *
 * <p>
 *  清单类用于维护清单条目名称及其关联的属性。有主要清单属性以及每条目属性。有关清单格式的信息,请参阅
 * <a href="../../../../technotes/guides/jar/jar.html">
 *  清单格式规范</a>。
 * 
 * 
 * @author  David Connelly
 * @see     Attributes
 * @since   1.2
 */
public class Manifest implements Cloneable {
    // manifest main attributes
    private Attributes attr = new Attributes();

    // manifest entries
    private Map<String, Attributes> entries = new HashMap<>();

    /**
     * Constructs a new, empty Manifest.
     * <p>
     *  构造一个新的,空的清单。
     * 
     */
    public Manifest() {
    }

    /**
     * Constructs a new Manifest from the specified input stream.
     *
     * <p>
     *  从指定的输入流构造新的清单。
     * 
     * 
     * @param is the input stream containing manifest data
     * @throws IOException if an I/O error has occurred
     */
    public Manifest(InputStream is) throws IOException {
        read(is);
    }

    /**
     * Constructs a new Manifest that is a copy of the specified Manifest.
     *
     * <p>
     *  构造一个新的清单,它是指定清单的副本。
     * 
     * 
     * @param man the Manifest to copy
     */
    public Manifest(Manifest man) {
        attr.putAll(man.getMainAttributes());
        entries.putAll(man.getEntries());
    }

    /**
     * Returns the main Attributes for the Manifest.
     * <p>
     *  返回清单的主要属性。
     * 
     * 
     * @return the main Attributes for the Manifest
     */
    public Attributes getMainAttributes() {
        return attr;
    }

    /**
     * Returns a Map of the entries contained in this Manifest. Each entry
     * is represented by a String name (key) and associated Attributes (value).
     * The Map permits the {@code null} key, but no entry with a null key is
     * created by {@link #read}, nor is such an entry written by using {@link
     * #write}.
     *
     * <p>
     *  返回此清单中包含的条目的映射。每个条目由字符串名称(键)和关联的属性(值)表示。
     *  Map允许{@code null}键,但是没有带有空键的条目是由{@link #read}创建的,也不是使用{@link #write}编写的条目。
     * 
     * 
     * @return a Map of the entries contained in this Manifest
     */
    public Map<String,Attributes> getEntries() {
        return entries;
    }

    /**
     * Returns the Attributes for the specified entry name.
     * This method is defined as:
     * <pre>
     *      return (Attributes)getEntries().get(name)
     * </pre>
     * Though {@code null} is a valid {@code name}, when
     * {@code getAttributes(null)} is invoked on a {@code Manifest}
     * obtained from a jar file, {@code null} will be returned.  While jar
     * files themselves do not allow {@code null}-named attributes, it is
     * possible to invoke {@link #getEntries} on a {@code Manifest}, and
     * on that result, invoke {@code put} with a null key and an
     * arbitrary value.  Subsequent invocations of
     * {@code getAttributes(null)} will return the just-{@code put}
     * value.
     * <p>
     * Note that this method does not return the manifest's main attributes;
     * see {@link #getMainAttributes}.
     *
     * <p>
     *  返回指定条目名称的属性。该方法定义为：
     * <pre>
     *  return(Attributes)getEntries()。get(name)
     * </pre>
     * 尽管{@code null}是有效的{@code name},但是当从jar文件获取的{@code Manifest}上调用{@code getAttributes(null)}时,将返回{@code null}
     * 。
     * 虽然jar文件本身不允许{@code null}命名的属性,可以在{@code Manifest}上调用{@link #getEntries},然后在该结果上使用空键调用{@code put}和任意值。
     * 随后调用{@code getAttributes(null)}将返回just  -  {@ code put}值。
     * <p>
     *  请注意,此方法不返回清单的主要属性;请参阅{@link #getMainAttributes}。
     * 
     * 
     * @param name entry name
     * @return the Attributes for the specified entry name
     */
    public Attributes getAttributes(String name) {
        return getEntries().get(name);
    }

    /**
     * Clears the main Attributes as well as the entries in this Manifest.
     * <p>
     *  清除主要属性以及此清单中的条目。
     * 
     */
    public void clear() {
        attr.clear();
        entries.clear();
    }

    /**
     * Writes the Manifest to the specified OutputStream.
     * Attributes.Name.MANIFEST_VERSION must be set in
     * MainAttributes prior to invoking this method.
     *
     * <p>
     *  将清单写入指定的OutputStream。 Attributes.Name.MANIFEST_VERSION必须在调用此方法之前在MainAttributes中设置。
     * 
     * 
     * @param out the output stream
     * @exception IOException if an I/O error has occurred
     * @see #getMainAttributes
     */
    public void write(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        // Write out the main attributes for the manifest
        attr.writeMain(dos);
        // Now write out the pre-entry attributes
        Iterator<Map.Entry<String, Attributes>> it = entries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Attributes> e = it.next();
            StringBuffer buffer = new StringBuffer("Name: ");
            String value = e.getKey();
            if (value != null) {
                byte[] vb = value.getBytes("UTF8");
                value = new String(vb, 0, 0, vb.length);
            }
            buffer.append(value);
            buffer.append("\r\n");
            make72Safe(buffer);
            dos.writeBytes(buffer.toString());
            e.getValue().write(dos);
        }
        dos.flush();
    }

    /**
     * Adds line breaks to enforce a maximum 72 bytes per line.
     * <p>
     *  添加换行符以强制每行最多72个字节。
     * 
     */
    static void make72Safe(StringBuffer line) {
        int length = line.length();
        if (length > 72) {
            int index = 70;
            while (index < length - 2) {
                line.insert(index, "\r\n ");
                index += 72;
                length += 3;
            }
        }
        return;
    }

    /**
     * Reads the Manifest from the specified InputStream. The entry
     * names and attributes read will be merged in with the current
     * manifest entries.
     *
     * <p>
     *  从指定的InputStream读取清单。读取的条目名称和属性将与当前清单条目合并。
     * 
     * 
     * @param is the input stream
     * @exception IOException if an I/O error has occurred
     */
    public void read(InputStream is) throws IOException {
        // Buffered input stream for reading manifest data
        FastInputStream fis = new FastInputStream(is);
        // Line buffer
        byte[] lbuf = new byte[512];
        // Read the main attributes for the manifest
        attr.read(fis, lbuf);
        // Total number of entries, attributes read
        int ecount = 0, acount = 0;
        // Average size of entry attributes
        int asize = 2;
        // Now parse the manifest entries
        int len;
        String name = null;
        boolean skipEmptyLines = true;
        byte[] lastline = null;

        while ((len = fis.readLine(lbuf)) != -1) {
            if (lbuf[--len] != '\n') {
                throw new IOException("manifest line too long");
            }
            if (len > 0 && lbuf[len-1] == '\r') {
                --len;
            }
            if (len == 0 && skipEmptyLines) {
                continue;
            }
            skipEmptyLines = false;

            if (name == null) {
                name = parseName(lbuf, len);
                if (name == null) {
                    throw new IOException("invalid manifest format");
                }
                if (fis.peek() == ' ') {
                    // name is wrapped
                    lastline = new byte[len - 6];
                    System.arraycopy(lbuf, 6, lastline, 0, len - 6);
                    continue;
                }
            } else {
                // continuation line
                byte[] buf = new byte[lastline.length + len - 1];
                System.arraycopy(lastline, 0, buf, 0, lastline.length);
                System.arraycopy(lbuf, 1, buf, lastline.length, len - 1);
                if (fis.peek() == ' ') {
                    // name is wrapped
                    lastline = buf;
                    continue;
                }
                name = new String(buf, 0, buf.length, "UTF8");
                lastline = null;
            }
            Attributes attr = getAttributes(name);
            if (attr == null) {
                attr = new Attributes(asize);
                entries.put(name, attr);
            }
            attr.read(fis, lbuf);
            ecount++;
            acount += attr.size();
            //XXX: Fix for when the average is 0. When it is 0,
            // you get an Attributes object with an initial
            // capacity of 0, which tickles a bug in HashMap.
            asize = Math.max(2, acount / ecount);

            name = null;
            skipEmptyLines = true;
        }
    }

    private String parseName(byte[] lbuf, int len) {
        if (toLower(lbuf[0]) == 'n' && toLower(lbuf[1]) == 'a' &&
            toLower(lbuf[2]) == 'm' && toLower(lbuf[3]) == 'e' &&
            lbuf[4] == ':' && lbuf[5] == ' ') {
            try {
                return new String(lbuf, 6, len - 6, "UTF8");
            }
            catch (Exception e) {
            }
        }
        return null;
    }

    private int toLower(int c) {
        return (c >= 'A' && c <= 'Z') ? 'a' + (c - 'A') : c;
    }

    /**
     * Returns true if the specified Object is also a Manifest and has
     * the same main Attributes and entries.
     *
     * <p>
     *  如果指定的对象也是清单并且具有相同的主属性和条目,则返回true。
     * 
     * 
     * @param o the object to be compared
     * @return true if the specified Object is also a Manifest and has
     * the same main Attributes and entries
     */
    public boolean equals(Object o) {
        if (o instanceof Manifest) {
            Manifest m = (Manifest)o;
            return attr.equals(m.getMainAttributes()) &&
                   entries.equals(m.getEntries());
        } else {
            return false;
        }
    }

    /**
     * Returns the hash code for this Manifest.
     * <p>
     *  返回此清单的哈希码。
     * 
     */
    public int hashCode() {
        return attr.hashCode() + entries.hashCode();
    }

    /**
     * Returns a shallow copy of this Manifest.  The shallow copy is
     * implemented as follows:
     * <pre>
     *     public Object clone() { return new Manifest(this); }
     * </pre>
     * <p>
     *  返回此清单的浅副本。浅拷贝实现如下：
     * <pre>
     *  public Object clone(){return new Manifest(this); }}
     * </pre>
     * 
     * @return a shallow copy of this Manifest
     */
    public Object clone() {
        return new Manifest(this);
    }

    /*
     * A fast buffered input stream for parsing manifest files.
     * <p>
     *  用于解析清单文件的快速缓冲输入流。
     * 
     */
    static class FastInputStream extends FilterInputStream {
        private byte buf[];
        private int count = 0;
        private int pos = 0;

        FastInputStream(InputStream in) {
            this(in, 8192);
        }

        FastInputStream(InputStream in, int size) {
            super(in);
            buf = new byte[size];
        }

        public int read() throws IOException {
            if (pos >= count) {
                fill();
                if (pos >= count) {
                    return -1;
                }
            }
            return Byte.toUnsignedInt(buf[pos++]);
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int avail = count - pos;
            if (avail <= 0) {
                if (len >= buf.length) {
                    return in.read(b, off, len);
                }
                fill();
                avail = count - pos;
                if (avail <= 0) {
                    return -1;
                }
            }
            if (len > avail) {
                len = avail;
            }
            System.arraycopy(buf, pos, b, off, len);
            pos += len;
            return len;
        }

        /*
         * Reads 'len' bytes from the input stream, or until an end-of-line
         * is reached. Returns the number of bytes read.
         * <p>
         *  从输入流读取"len"字节,或直到到达行尾。返回读取的字节数。
         */
        public int readLine(byte[] b, int off, int len) throws IOException {
            byte[] tbuf = this.buf;
            int total = 0;
            while (total < len) {
                int avail = count - pos;
                if (avail <= 0) {
                    fill();
                    avail = count - pos;
                    if (avail <= 0) {
                        return -1;
                    }
                }
                int n = len - total;
                if (n > avail) {
                    n = avail;
                }
                int tpos = pos;
                int maxpos = tpos + n;
                while (tpos < maxpos && tbuf[tpos++] != '\n') ;
                n = tpos - pos;
                System.arraycopy(tbuf, pos, b, off, n);
                off += n;
                total += n;
                pos = tpos;
                if (tbuf[tpos-1] == '\n') {
                    break;
                }
            }
            return total;
        }

        public byte peek() throws IOException {
            if (pos == count)
                fill();
            if (pos == count)
                return -1; // nothing left in buffer
            return buf[pos];
        }

        public int readLine(byte[] b) throws IOException {
            return readLine(b, 0, b.length);
        }

        public long skip(long n) throws IOException {
            if (n <= 0) {
                return 0;
            }
            long avail = count - pos;
            if (avail <= 0) {
                return in.skip(n);
            }
            if (n > avail) {
                n = avail;
            }
            pos += n;
            return n;
        }

        public int available() throws IOException {
            return (count - pos) + in.available();
        }

        public void close() throws IOException {
            if (in != null) {
                in.close();
                in = null;
                buf = null;
            }
        }

        private void fill() throws IOException {
            count = pos = 0;
            int n = in.read(buf, 0, buf.length);
            if (n > 0) {
                count = n;
            }
        }
    }
}
