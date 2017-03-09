/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
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
 * Synchronized symbol table.
 *
 * This class moved into the util package since it's needed by multiple
 * other classes (CachingParserPool, XMLGrammarCachingConfiguration).
 *
 * <p>
 *  同步符号表。
 * 
 *  这个类移动到util包中,因为它需要多个其他类(CachingParserPool,XMLGrammarCachingConfiguration)。
 * 
 * 
 * @author Andy Clark, IBM
 */

public final class SynchronizedSymbolTable
    extends SymbolTable {

    //
    // Data
    //

    /** Main symbol table. */
    protected SymbolTable fSymbolTable;

    //
    // Constructors
    //

    /** Constructs a synchronized symbol table. */
    public SynchronizedSymbolTable(SymbolTable symbolTable) {
        fSymbolTable = symbolTable;
    } // <init>(SymbolTable)

    // construct synchronized symbol table of default size
    public SynchronizedSymbolTable() {
        fSymbolTable = new SymbolTable();
    } // init()

    // construct synchronized symbol table of given size
    public SynchronizedSymbolTable(int size) {
        fSymbolTable = new SymbolTable(size);
    } // init(int)

    //
    // SymbolTable methods
    //

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
     * @param symbol The new symbol.
     */
    public String addSymbol(String symbol) {

        synchronized (fSymbolTable) {
            return fSymbolTable.addSymbol(symbol);
        }

    } // addSymbol(String)

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

        synchronized (fSymbolTable) {
            return fSymbolTable.addSymbol(buffer, offset, length);
        }

    } // addSymbol(char[],int,int):String

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

        synchronized (fSymbolTable) {
            return fSymbolTable.containsSymbol(symbol);
        }

    } // containsSymbol(String):boolean

    /**
     * Returns true if the symbol table already contains the specified
     * symbol.
     *
     * <p>
     *  如果符号表已包含指定的符号,则返回true。
     * 
     * @param buffer The buffer containing the symbol to look for.
     * @param offset The offset into the buffer.
     * @param length The length of the symbol in the buffer.
     */
    public boolean containsSymbol(char[] buffer, int offset, int length) {

        synchronized (fSymbolTable) {
            return fSymbolTable.containsSymbol(buffer, offset, length);
        }

    } // containsSymbol(char[],int,int):boolean

} // class SynchronizedSymbolTable
