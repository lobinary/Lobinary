/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2000-2002 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款,最初是基于软件版权(c)1999,国际商业机器公司,http://www.apache.org。
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.xni.NamespaceContext;

/**
 * Namespace support for XML document handlers. This class doesn't
 * perform any error checking and assumes that all strings passed
 * as arguments to methods are unique symbols. The SymbolTable class
 * can be used for this purpose.
 *
 * <p>
 *  命名空间支持XML文档处理程序。此类不执行任何错误检查,并假定作为参数传递给方法的所有字符串都是唯一符号。 SymbolTable类可以用于此目的。
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public class NamespaceSupport implements NamespaceContext {

    //
    // Data
    //

    /**
     * Namespace binding information. This array is composed of a
     * series of tuples containing the namespace binding information:
     * &lt;prefix, uri&gt;. The default size can be set to anything
     * as long as it is a power of 2 greater than 1.
     *
     * <p>
     * 命名空间绑定信息。此数组由一系列包含命名空间绑定信息的元组组成：&lt; prefix,uri&gt ;.默认大小可以设置为任何值,只要它是大于1的2的幂。
     * 
     * 
     * @see #fNamespaceSize
     * @see #fContext
     */
    protected String[] fNamespace = new String[16 * 2];

    /** The top of the namespace information array. */
    protected int fNamespaceSize;

    // NOTE: The constructor depends on the initial context size
    //       being at least 1. -Ac

    /**
     * Context indexes. This array contains indexes into the namespace
     * information array. The index at the current context is the start
     * index of declared namespace bindings and runs to the size of the
     * namespace information array.
     *
     * <p>
     *  上下文索引。此数组包含命名空间信息数组中的索引。当前上下文的索引是已声明的命名空间绑定的开始索引,并运行到命名空间信息数组的大小。
     * 
     * 
     * @see #fNamespaceSize
     */
    protected int[] fContext = new int[8];

    /** The current context. */
    protected int fCurrentContext;

    protected String[] fPrefixes = new String[16];

    //
    // Constructors
    //

    /** Default constructor. */
    public NamespaceSupport() {
    } // <init>()

    /**
     * Constructs a namespace context object and initializes it with
     * the prefixes declared in the specified context.
     * <p>
     *  构造命名空间上下文对象,并使用在指定上下文中声明的前缀来初始化它。
     * 
     */
    public NamespaceSupport(NamespaceContext context) {
        pushContext();
        // copy declaration in the context
        Enumeration prefixes = context.getAllPrefixes();
        while (prefixes.hasMoreElements()){
            String prefix = (String)prefixes.nextElement();
            String uri = context.getURI(prefix);
            declarePrefix(prefix, uri);
        }
    } // <init>(NamespaceContext)


    //
    // Public methods
    //

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#reset()
     */
    public void reset() {

        // reset namespace and context info
        fNamespaceSize = 0;
        fCurrentContext = 0;


        // bind "xml" prefix to the XML uri
        fNamespace[fNamespaceSize++] = XMLSymbols.PREFIX_XML;
        fNamespace[fNamespaceSize++] = NamespaceContext.XML_URI;
        // bind "xmlns" prefix to the XMLNS uri
        fNamespace[fNamespaceSize++] = XMLSymbols.PREFIX_XMLNS;
        fNamespace[fNamespaceSize++] = NamespaceContext.XMLNS_URI;

        fContext[fCurrentContext] = fNamespaceSize;
        //++fCurrentContext;

    } // reset(SymbolTable)


    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#pushContext()
     */
    public void pushContext() {

        // extend the array, if necessary
        if (fCurrentContext + 1 == fContext.length) {
            int[] contextarray = new int[fContext.length * 2];
            System.arraycopy(fContext, 0, contextarray, 0, fContext.length);
            fContext = contextarray;
        }

        // push context
        fContext[++fCurrentContext] = fNamespaceSize;
        //System.out.println("calling push context, current context = " + fCurrentContext);
    } // pushContext()


    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#popContext()
     */
    public void popContext() {
        fNamespaceSize = fContext[fCurrentContext--];
        //System.out.println("Calling popContext, fCurrentContext = " + fCurrentContext);
    } // popContext()

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#declarePrefix(String, String)
     */
    public boolean declarePrefix(String prefix, String uri) {
        // ignore "xml" and "xmlns" prefixes
        if (prefix == XMLSymbols.PREFIX_XML || prefix == XMLSymbols.PREFIX_XMLNS) {
            return false;
        }

        // see if prefix already exists in current context
        for (int i = fNamespaceSize; i > fContext[fCurrentContext]; i -= 2) {
            if (fNamespace[i - 2] == prefix) {
                // REVISIT: [Q] Should the new binding override the
                //          previously declared binding or should it
                //          it be ignored? -Ac
                // NOTE:    The SAX2 "NamespaceSupport" helper allows
                //          re-bindings with the new binding overwriting
                //          the previous binding. -Ac
                fNamespace[i - 1] = uri;
                return true;
            }
        }

        // resize array, if needed
        if (fNamespaceSize == fNamespace.length) {
            String[] namespacearray = new String[fNamespaceSize * 2];
            System.arraycopy(fNamespace, 0, namespacearray, 0, fNamespaceSize);
            fNamespace = namespacearray;
        }

        // bind prefix to uri in current context
        fNamespace[fNamespaceSize++] = prefix;
        fNamespace[fNamespaceSize++] = uri;

        return true;

    } // declarePrefix(String,String):boolean

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#getURI(String)
     */
    public String getURI(String prefix) {

        // find prefix in current context
        for (int i = fNamespaceSize; i > 0; i -= 2) {
            if (fNamespace[i - 2] == prefix) {
                return fNamespace[i - 1];
            }
        }

        // prefix not found
        return null;

    } // getURI(String):String


    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#getPrefix(String)
     */
    public String getPrefix(String uri) {

        // find uri in current context
        for (int i = fNamespaceSize; i > 0; i -= 2) {
            if (fNamespace[i - 1] == uri) {
                if (getURI(fNamespace[i - 2]) == uri)
                    return fNamespace[i - 2];
            }
        }

        // uri not found
        return null;

    } // getPrefix(String):String

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#getDeclaredPrefixCount()
     */
    public int getDeclaredPrefixCount() {
        return (fNamespaceSize - fContext[fCurrentContext]) / 2;
    } // getDeclaredPrefixCount():int

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#getDeclaredPrefixAt(int)
     */
    public String getDeclaredPrefixAt(int index) {
        return fNamespace[fContext[fCurrentContext] + index * 2];
    } // getDeclaredPrefixAt(int):String

    public Iterator getPrefixes(){
        int count = 0;
        if (fPrefixes.length < (fNamespace.length/2)) {
            // resize prefix array
            String[] prefixes = new String[fNamespaceSize];
            fPrefixes = prefixes;
        }
        String prefix = null;
        boolean unique = true;
        for (int i = 2; i < (fNamespaceSize-2); i += 2) {
            prefix = fNamespace[i + 2];
            for (int k=0;k<count;k++){
                if (fPrefixes[k]==prefix){
                    unique = false;
                    break;
                }
            }
            if (unique){
                fPrefixes[count++] = prefix;
            }
            unique = true;
        }
        return new IteratorPrefixes(fPrefixes, count);
    }//getPrefixes
    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xni.NamespaceContext#getAllPrefixes()
     */
    public Enumeration getAllPrefixes() {
        int count = 0;
        if (fPrefixes.length < (fNamespace.length/2)) {
            // resize prefix array
            String[] prefixes = new String[fNamespaceSize];
            fPrefixes = prefixes;
        }
        String prefix = null;
        boolean unique = true;
        for (int i = 2; i < (fNamespaceSize-2); i += 2) {
            prefix = fNamespace[i + 2];
            for (int k=0;k<count;k++){
                if (fPrefixes[k]==prefix){
                    unique = false;
                    break;
                }
            }
            if (unique){
                fPrefixes[count++] = prefix;
            }
            unique = true;
        }
        return new Prefixes(fPrefixes, count);
    }

    public  Vector getPrefixes(String uri){
        int count = 0;
        String prefix = null;
        boolean unique = true;
        Vector prefixList = new Vector();
        for (int i = fNamespaceSize; i >0 ; i -= 2) {
            if(fNamespace[i-1] == uri){
                if(!prefixList.contains(fNamespace[i-2]))
                    prefixList.add(fNamespace[i-2]);
            }
        }
        return prefixList;
    }

    /*
     * non-NamespaceContext methods
     * <p>
     *  非NamespaceContext方法
     * 
     */

    /**
     * Checks whether a binding or unbinding for
     * the given prefix exists in the context.
     *
     * <p>
     *  检查上下文中是否存在给定前缀的绑定或取消绑定。
     * 
     * 
     * @param prefix The prefix to look up.
     *
     * @return true if the given prefix exists in the context
     */
    public boolean containsPrefix(String prefix) {

        // find prefix in context
        for (int i = fNamespaceSize; i > 0; i -= 2) {
            if (fNamespace[i - 2] == prefix) {
                return true;
            }
        }

        // prefix not found
        return false;
    }

    /**
     * Checks whether a binding or unbinding for
     * the given prefix exists in the current context.
     *
     * <p>
     *  检查当前上下文中是否存在给定前缀的绑定或取消绑定。
     * 
     * 
     * @param prefix The prefix to look up.
     *
     * @return true if the given prefix exists in the current context
     */
    public boolean containsPrefixInCurrentContext(String prefix) {

        // find prefix in current context
        for (int i = fContext[fCurrentContext]; i < fNamespaceSize; i += 2) {
            if (fNamespace[i] == prefix) {
                return true;
            }
        }

        // prefix not found
        return false;
    }

    protected final class IteratorPrefixes implements Iterator  {
        private String[] prefixes;
        private int counter = 0;
        private int size = 0;

        /**
         * Constructor for Prefixes.
         * <p>
         *  前缀的构造函数。
         * 
         */
        public IteratorPrefixes(String [] prefixes, int size) {
            this.prefixes = prefixes;
            this.size = size;
        }

        /**
        /* <p>
        /* 
         * @see java.util.Enumeration#hasMoreElements()
         */
        public boolean hasNext() {
            return (counter < size);
        }

        /**
        /* <p>
        /* 
         * @see java.util.Enumeration#nextElement()
         */
        public Object next() {
            if (counter< size){
                return fPrefixes[counter++];
            }
            throw new NoSuchElementException("Illegal access to Namespace prefixes enumeration.");
        }

        public String toString(){
            StringBuffer buf = new StringBuffer();
            for (int i=0;i<size;i++){
                buf.append(prefixes[i]);
                buf.append(" ");
            }

            return buf.toString();
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }


    protected final class Prefixes implements Enumeration {
        private String[] prefixes;
        private int counter = 0;
        private int size = 0;

        /**
         * Constructor for Prefixes.
         * <p>
         *  前缀的构造函数。
         * 
         */
        public Prefixes(String [] prefixes, int size) {
            this.prefixes = prefixes;
            this.size = size;
        }

        /**
        /* <p>
        /* 
         * @see java.util.Enumeration#hasMoreElements()
         */
        public boolean hasMoreElements() {
            return (counter< size);
        }

        /**
        /* <p>
        /* 
         * @see java.util.Enumeration#nextElement()
         */
        public Object nextElement() {
            if (counter< size){
                return fPrefixes[counter++];
            }
            throw new NoSuchElementException("Illegal access to Namespace prefixes enumeration.");
        }

        public String toString(){
            StringBuffer buf = new StringBuffer();
            for (int i=0;i<size;i++){
                buf.append(prefixes[i]);
                buf.append(" ");
            }

            return buf.toString();
        }


    }

} // class NamespaceSupport
