/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

import java.util.Iterator;
import java.util.Vector;

/**
 * A container for <code>MimeHeader</code> objects, which represent
 * the MIME headers present in a MIME part of a message.
 *
 * <p>This class is used primarily when an application wants to
 * retrieve specific attachments based on certain MIME headers and
 * values. This class will most likely be used by implementations of
 * <code>AttachmentPart</code> and other MIME dependent parts of the SAAJ
 * API.
 * <p>
 *  一个用于<code> MimeHeader </code>对象的容器,它表示消息的MIME部分中存在的MIME头。
 * 
 *  <p>此类主要用于应用程序基于某些MIME标头和值检索特定附件时。这个类最有可能被SAAJ API的<code> AttachmentPart </code>和其他MIME依赖部分的实现使用。
 * 
 * 
 * @see SOAPMessage#getAttachments
 * @see AttachmentPart
 */
public class MimeHeaders {
    private Vector headers;

   /**
    * Constructs a default <code>MimeHeaders</code> object initialized with
    * an empty <code>Vector</code> object.
    * <p>
    *  构造一个用空的<code> Vector </code>对象初始化的默认<code> MimeHeaders </code>对象。
    * 
    */
    public MimeHeaders() {
        headers = new Vector();
    }

    /**
     * Returns all of the values for the specified header as an array of
     * <code>String</code> objects.
     *
     * <p>
     *  将指定标头的所有值作为<code> String </code>对象的数组返回。
     * 
     * 
     * @param   name the name of the header for which values will be returned
     * @return a <code>String</code> array with all of the values for the
     *         specified header
     * @see #setHeader
     */
    public String[] getHeader(String name) {
        Vector values = new Vector();

        for(int i = 0; i < headers.size(); i++) {
            MimeHeader hdr = (MimeHeader) headers.elementAt(i);
            if (hdr.getName().equalsIgnoreCase(name)
                && hdr.getValue() != null)
                values.addElement(hdr.getValue());
        }

        if (values.size() == 0)
            return null;

        String r[] = new String[values.size()];
        values.copyInto(r);
        return r;
    }

    /**
     * Replaces the current value of the first header entry whose name matches
     * the given name with the given value, adding a new header if no existing header
     * name matches. This method also removes all matching headers after the first one.
     * <P>
     * Note that RFC822 headers can contain only US-ASCII characters.
     *
     * <p>
     *  将名称与给定名称匹配的第一个报头条目的当前值替换为给定值,如果没有现有报头名称匹配,则添加新报头。此方法还会删除第一个之后的所有匹配标头。
     * <P>
     *  请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name a <code>String</code> with the name of the header for
     *          which to search
     * @param   value a <code>String</code> with the value that will replace the
     *          current value of the specified header
     *
     * @exception IllegalArgumentException if there was a problem in the
     * mime header name or the value being set
     * @see #getHeader
     */
    public void setHeader(String name, String value)
    {
        boolean found = false;

        if ((name == null) || name.equals(""))
            throw new IllegalArgumentException("Illegal MimeHeader name");

        for(int i = 0; i < headers.size(); i++) {
            MimeHeader hdr = (MimeHeader) headers.elementAt(i);
            if (hdr.getName().equalsIgnoreCase(name)) {
                if (!found) {
                    headers.setElementAt(new MimeHeader(hdr.getName(),
                                                        value), i);
                    found = true;
                }
                else
                    headers.removeElementAt(i--);
            }
        }

        if (!found)
            addHeader(name, value);
    }

    /**
     * Adds a <code>MimeHeader</code> object with the specified name and value
     * to this <code>MimeHeaders</code> object's list of headers.
     * <P>
     * Note that RFC822 headers can contain only US-ASCII characters.
     *
     * <p>
     *  向此<code> MimeHeaders </code>对象的标头列表中添加具有指定名称和值的<code> MimeHeader </code>对象。
     * <P>
     *  请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name a <code>String</code> with the name of the header to
     *          be added
     * @param   value a <code>String</code> with the value of the header to
     *          be added
     *
     * @exception IllegalArgumentException if there was a problem in the
     * mime header name or value being added
     */
    public void addHeader(String name, String value)
    {
        if ((name == null) || name.equals(""))
            throw new IllegalArgumentException("Illegal MimeHeader name");

        int pos = headers.size();

        for(int i = pos - 1 ; i >= 0; i--) {
            MimeHeader hdr = (MimeHeader) headers.elementAt(i);
            if (hdr.getName().equalsIgnoreCase(name)) {
                headers.insertElementAt(new MimeHeader(name, value),
                                        i+1);
                return;
            }
        }
        headers.addElement(new MimeHeader(name, value));
    }

    /**
     * Remove all <code>MimeHeader</code> objects whose name matches the
     * given name.
     *
     * <p>
     *  删除名称与给定名称匹配的所有<code> MimeHeader </code>对象。
     * 
     * 
     * @param   name a <code>String</code> with the name of the header for
     *          which to search
     */
    public void removeHeader(String name) {
        for(int i = 0; i < headers.size(); i++) {
            MimeHeader hdr = (MimeHeader) headers.elementAt(i);
            if (hdr.getName().equalsIgnoreCase(name))
                headers.removeElementAt(i--);
        }
    }

    /**
     * Removes all the header entries from this <code>MimeHeaders</code> object.
     * <p>
     *  从<code> MimeHeaders </code>对象中删除所有头条目。
     * 
     */
    public void removeAllHeaders() {
        headers.removeAllElements();
    }


    /**
     * Returns all the <code>MimeHeader</code>s in this <code>MimeHeaders</code> object.
     *
     * <p>
     *  返回此<code> MimeHeaders </code>对象中的所有<code> MimeHeader </code>。
     * 
     * 
     * @return  an <code>Iterator</code> object over this <code>MimeHeaders</code>
     *          object's list of <code>MimeHeader</code> objects
     */
    public Iterator getAllHeaders() {
        return headers.iterator();
    }

    class MatchingIterator implements Iterator {
        private boolean match;
        private Iterator iterator;
        private String[] names;
        private Object nextHeader;

        MatchingIterator(String[] names, boolean match) {
            this.match = match;
            this.names = names;
            this.iterator = headers.iterator();
        }

        private Object nextMatch() {
        next:
            while (iterator.hasNext()) {
                MimeHeader hdr = (MimeHeader) iterator.next();

                if (names == null)
                    return match ? null : hdr;

                for(int i = 0; i < names.length; i++)
                    if (hdr.getName().equalsIgnoreCase(names[i]))
                        if (match)
                            return hdr;
                        else
                            continue next;
                if (!match)
                    return hdr;
            }
            return null;
        }


        public boolean hasNext() {
            if (nextHeader == null)
                nextHeader = nextMatch();
            return nextHeader != null;
        }

        public Object next() {
            // hasNext should've prefetched the header for us,
            // return it.
            if (nextHeader != null) {
                Object ret = nextHeader;
                nextHeader = null;
                return ret;
            }
            if (hasNext())
                return nextHeader;
            return null;
        }

        public void remove() {
            iterator.remove();
        }
    }


    /**
     * Returns all the <code>MimeHeader</code> objects whose name matches
     * a name in the given array of names.
     *
     * <p>
     * 返回名称与给定名称数组中的名称匹配的所有<code> MimeHeader </code>对象。
     * 
     * 
     * @param names an array of <code>String</code> objects with the names
     *         for which to search
     * @return  an <code>Iterator</code> object over the <code>MimeHeader</code>
     *          objects whose name matches one of the names in the given list
     */
    public Iterator getMatchingHeaders(String[] names) {
        return new MatchingIterator(names, true);
    }

    /**
     * Returns all of the <code>MimeHeader</code> objects whose name does not
     * match a name in the given array of names.
     *
     * <p>
     *  返回其名称与给定名称数组中的名称不匹配的所有<code> MimeHeader </code>对象。
     * 
     * @param names an array of <code>String</code> objects with the names
     *         for which to search
     * @return  an <code>Iterator</code> object over the <code>MimeHeader</code>
     *          objects whose name does not match one of the names in the given list
     */
    public Iterator getNonMatchingHeaders(String[] names) {
        return new MatchingIterator(names, false);
    }
}
