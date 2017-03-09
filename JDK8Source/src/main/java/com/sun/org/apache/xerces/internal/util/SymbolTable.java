/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

/**
 * This class is a symbol table implementation that guarantees that
 * strings used as identifiers are unique references. Multiple calls
 * to <code>addSymbol</code> will always return the same string
 * reference.
 * <p>
 * The symbol table performs the same task as <code>String.intern()</code>
 * with the following differences:
 * <ul>
 *  <li>
 *   A new string object does not need to be created in order to
 *   retrieve a unique reference. Symbols can be added by using
 *   a series of characters in a character array.
 *  </li>
 *  <li>
 *   Users of the symbol table can provide their own symbol hashing
 *   implementation. For example, a simple string hashing algorithm
 *   may fail to produce a balanced set of hashcodes for symbols
 *   that are <em>mostly</em> unique. Strings with similar leading
 *   characters are especially prone to this poor hashing behavior.
 *  </li>
 * </ul>
 *
 * <p>
 *  这个类是一个符号表实现,它保证用作标识符的字符串是唯一的引用。对<code> addSymbol </code>的多次调用将始终返回相同的字符串引用。
 * <p>
 *  符号表执行与<code> String.intern()</code>相同的任务,但具有以下区别：
 * <ul>
 * <li>
 *  不需要创建新的字符串对象以便检索唯一引用。可以通过使用字符数组中的一系列字符来添加符号。
 * </li>
 * <li>
 *  符号表的用户可以提供自己的符号散列实现。例如,简单的字符串散列算法可能不能产生用于大多数</em>唯一的符号的散列码的平衡集合。具有类似前导字符的字符串特别容易出现这种差的哈希行为。
 * </li>
 * </ul>
 * 
 * 
 * @see SymbolHash
 *
 * @author Andy Clark
 *
 */
public class SymbolTable {

    //
    // Constants
    //

    /** Default table size. */
    protected static final int TABLE_SIZE = 173;


    /** Buckets. */
    protected Entry[] fBuckets = null;

    // actual table size
    protected int fTableSize;

    //
    // Constructors
    //

    /** Constructs a symbol table with a default number of buckets. */
    public SymbolTable() {
        this(TABLE_SIZE);
    }

    /** Constructs a symbol table with a specified number of buckets. */
    public SymbolTable(int tableSize) {
        fTableSize = tableSize;
        fBuckets = new Entry[fTableSize];
    }

    //
    // Public methods
    //

    /**
     * Adds the specified symbol to the symbol table and returns a
     * reference to the unique symbol. If the symbol already exists,
     * the previous symbol reference is returned instead, in order
     * guarantee that symbol references remain unique.
     *
     * <p>
     * 将指定的符号添加到符号表,并返回对唯一符号的引用。如果符号已经存在,则返回先前的符号引用,以保证符号引用保持唯一。
     * 
     * 
     * @param symbol The new symbol.
     */
    public String addSymbol(String symbol) {

        // search for identical symbol
        final int hash = hash(symbol);
        final int bucket = hash % fTableSize;
        final int length = symbol.length();
        OUTER: for (Entry entry = fBuckets[bucket]; entry != null; entry = entry.next) {
            if (length == entry.characters.length && hash == entry.hashCode) {
                if(symbol.regionMatches(0,entry.symbol,0,length)){
                    return entry.symbol;
                }
                else{
                    continue OUTER;
                }
                /**
                for (int i = 0; i < length; i++) {
                    if (symbol.charAt(i) != entry.characters[i]) {
                        continue OUTER;
                    }
                }
                symbolAsArray = entry.characters;
                return entry.symbol;
                /* <p>
                /*  for(int i = 0; i <length; i ++){if(symbol.charAt(i)！= entry.characters [i]){continue OUTER; }} symbolAsArray = entry.characters; return entry.symbol;。
                /* 
                 */
            }
        }

        // create new entry
        Entry entry = new Entry(symbol, fBuckets[bucket]);
        entry.hashCode = hash;
        fBuckets[bucket] = entry;
        return entry.symbol;

    } // addSymbol(String):String

    /**
     * Adds the specified symbol to the symbol table and returns a
     * reference to the unique symbol. If the symbol already exists,
     * the previous symbol reference is returned instead, in order
     * guarantee that symbol references remain unique.
     *
     * <p>
     *  将指定的符号添加到符号表,并返回对唯一符号的引用。如果符号已经存在,则返回先前的符号引用,以保证符号引用保持唯一。
     * 
     * 
     * @param buffer The buffer containing the new symbol.
     * @param offset The offset into the buffer of the new symbol.
     * @param length The length of the new symbol in the buffer.
     */
    public String addSymbol(char[] buffer, int offset, int length) {
        // search for identical symbol
        int hash = hash(buffer, offset, length);
        int bucket = hash % fTableSize;
        OUTER: for (Entry entry = fBuckets[bucket]; entry != null; entry = entry.next) {
            if (length == entry.characters.length && hash ==entry.hashCode) {
                for (int i = 0; i < length; i++) {
                    if (buffer[offset + i] != entry.characters[i]) {
                        continue OUTER;
                    }
                }
                return entry.symbol;
            }
        }

        // add new entry
        Entry entry = new Entry(buffer, offset, length, fBuckets[bucket]);
        fBuckets[bucket] = entry;
        entry.hashCode = hash;
        return entry.symbol;

    } // addSymbol(char[],int,int):String

    /**
     * Returns a hashcode value for the specified symbol. The value
     * returned by this method must be identical to the value returned
     * by the <code>hash(char[],int,int)</code> method when called
     * with the character array that comprises the symbol string.
     *
     * <p>
     *  返回指定符号的哈希码值。此方法返回的值必须与使用包含符号字符串的字符数组调用时<code> hash(char [],int,int)</code>方法返回的值相同。
     * 
     * 
     * @param symbol The symbol to hash.
     */
    public int hash(String symbol) {

        int code = 0;
        int length = symbol.length();
        for (int i = 0; i < length; i++) {
            code = code * 37 + symbol.charAt(i);
        }
        return code & 0x7FFFFFFF;

    } // hash(String):int

    /**
     * Returns a hashcode value for the specified symbol information.
     * The value returned by this method must be identical to the value
     * returned by the <code>hash(String)</code> method when called
     * with the string object created from the symbol information.
     *
     * <p>
     *  返回指定符号信息的哈希码值。当使用从符号信息创建的字符串对象调用时,此方法返回的值必须与<code> hash(String)</code>方法返回的值相同。
     * 
     * 
     * @param buffer The character buffer containing the symbol.
     * @param offset The offset into the character buffer of the start
     *               of the symbol.
     * @param length The length of the symbol.
     */
    public int hash(char[] buffer, int offset, int length) {

        int code = 0;
        for (int i = 0; i < length; i++) {
            code = code * 37 + buffer[offset + i];
        }
        return code & 0x7FFFFFFF;

    } // hash(char[],int,int):int

    /**
     * Returns true if the symbol table already contains the specified
     * symbol.
     *
     * <p>
     *  如果符号表已包含指定的符号,则返回true。
     * 
     * 
     * @param symbol The symbol to look for.
     */
    public boolean containsSymbol(String symbol) {

        // search for identical symbol
        int hash = hash(symbol);
        int bucket = hash % fTableSize;
        int length = symbol.length();
        OUTER: for (Entry entry = fBuckets[bucket]; entry != null; entry = entry.next) {
            if (length == entry.characters.length && hash == entry.hashCode) {
                if(symbol.regionMatches(0,entry.symbol,0,length)){
                    return true;
                }
                else {
                    continue OUTER;
                }
                /**
                for (int i = 0; i < length; i++) {
                    if (symbol.charAt(i) != entry.characters[i]) {
                        continue OUTER;
                    }
                }
                 return true;
                /* <p>
                /*  for(int i = 0; i <length; i ++){if(symbol.charAt(i)！= entry.characters [i]){continue OUTER; }} return true;。
                /* 
                 */
            }
        }

        return false;

    } // containsSymbol(String):boolean

    /**
     * Returns true if the symbol table already contains the specified
     * symbol.
     *
     * <p>
     *  如果符号表已包含指定的符号,则返回true。
     * 
     * 
     * @param buffer The buffer containing the symbol to look for.
     * @param offset The offset into the buffer.
     * @param length The length of the symbol in the buffer.
     */
    public boolean containsSymbol(char[] buffer, int offset, int length) {

        // search for identical symbol
        int hash = hash(buffer, offset, length) ;
        int bucket = hash % fTableSize;
        OUTER: for (Entry entry = fBuckets[bucket]; entry != null; entry = entry.next) {
            if (length == entry.characters.length && hash == entry.hashCode) {
                for (int i = 0; i < length; i++) {
                    if (buffer[offset + i] != entry.characters[i]) {
                        continue OUTER;
                    }
                }
                return true;
            }
        }

        return false;

    } // containsSymbol(char[],int,int):boolean


    //
    // Classes
    //

    /**
     * This class is a symbol table entry. Each entry acts as a node
     * in a linked list.
     * <p>
     *  此类是一个符号表条目。每个条目充当链接列表中的节点。
     * 
     */
    protected static final class Entry {

        //
        // Data
        //

        /** Symbol. */
        public String symbol;
        int hashCode = 0;

        /**
         * Symbol characters. This information is duplicated here for
         * comparison performance.
         * <p>
         * 符号字符。此信息在此处用于比较性能。
         * 
         */
        public char[] characters;

        /** The next entry. */
        public Entry next;

        //
        // Constructors
        //

        /**
         * Constructs a new entry from the specified symbol and next entry
         * reference.
         * <p>
         *  根据指定的符号和下一个条目引用构造一个新条目。
         * 
         */
        public Entry(String symbol, Entry next) {
            this.symbol = symbol.intern();
            characters = new char[symbol.length()];
            symbol.getChars(0, characters.length, characters, 0);
            this.next = next;
        }

        /**
         * Constructs a new entry from the specified symbol information and
         * next entry reference.
         * <p>
         *  根据指定的符号信息和下一个条目引用构造新条目。
         */
        public Entry(char[] ch, int offset, int length, Entry next) {
            characters = new char[length];
            System.arraycopy(ch, offset, characters, 0, length);
            symbol = new String(characters).intern();
            this.next = next;
        }

    } // class Entry

} // class SymbolTable
