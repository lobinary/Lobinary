/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation.adapters;



/**
 * {@link XmlAdapter} to handle <tt>xs:normalizedString</tt>.
 *
 * <p>
 * Replaces any tab, CR, and LF by a whitespace character ' ',
 * as specified in <a href="http://www.w3.org/TR/xmlschema-2/#rf-whiteSpace">the whitespace facet 'replace'</a>
 *
 * <p>
 *  {@link XmlAdapter}以处理<tt> xs：normalizedString </tt>。
 * 
 * <p>
 *  将任何制表符,CR和LF替换为空格字符"",如<a href="http://www.w3.org/TR/xmlschema-2/#rf-whiteSpace"> whitespace facet'r
 * eplace中所指定'</a>。
 * 
 * 
 * @author Kohsuke Kawaguchi, Martin Grebac
 * @since JAXB 2.0
 */
public final class NormalizedStringAdapter extends XmlAdapter<String,String> {
    /**
     * Replace any tab, CR, and LF by a whitespace character ' ',
     * as specified in <a href="http://www.w3.org/TR/xmlschema-2/#rf-whiteSpace">the whitespace facet 'replace'</a>
     * <p>
     *  将任何制表符,CR和LF替换为空格字符"",如<a href="http://www.w3.org/TR/xmlschema-2/#rf-whiteSpace"> whitespace facet'r
     * eplace中所指定'</a>。
     * 
     */
    public String unmarshal(String text) {
        if(text==null)      return null;    // be defensive

        int i=text.length()-1;

        // look for the first whitespace char.
        while( i>=0 && !isWhiteSpaceExceptSpace(text.charAt(i)) )
            i--;

        if( i<0 )
            // no such whitespace. replace(text)==text.
            return text;

        // we now know that we need to modify the text.
        // allocate a char array to do it.
        char[] buf = text.toCharArray();

        buf[i--] = ' ';
        for( ; i>=0; i-- )
            if( isWhiteSpaceExceptSpace(buf[i]))
                buf[i] = ' ';

        return new String(buf);
    }

    /**
     * No-op.
     *
     * Just return the same string given as the parameter.
     * <p>
     *  无操作。
     * 
     *  只返回与参数给定的相同的字符串。
     * 
     */
        public String marshal(String s) {
            return s;
        }


    /**
     * Returns true if the specified char is a white space character
     * but not 0x20.
     * <p>
     */
    protected static boolean isWhiteSpaceExceptSpace(char ch) {
        // most of the characters are non-control characters.
        // so check that first to quickly return false for most of the cases.
        if( ch>=0x20 )   return false;

        // other than we have to do four comparisons.
        return ch == 0x9 || ch == 0xA || ch == 0xD;
    }
}
