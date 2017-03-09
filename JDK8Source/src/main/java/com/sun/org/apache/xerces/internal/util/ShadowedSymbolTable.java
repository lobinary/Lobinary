/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
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
 * Shadowed symbol table.
 *
 * The table has a reference to the main symbol table and is
 * not allowed to add new symbols to the main symbol table.
 * New symbols are added to the shadow symbol table and are local
 * to the component using this table.
 *
 * <p>
 *  阴影的符号表。
 * 
 *  该表具有对主符号表的引用,并且不允许向主符号表添加新符号。新符号将添加到阴影符号表中,并且使用此表对组件是本地的。
 * 
 * 
 * @author Andy Clark IBM
 */

public final class ShadowedSymbolTable
extends SymbolTable {

    //
    // Data
    //

    /** Main symbol table. */
    protected SymbolTable fSymbolTable;

    //
    // Constructors
    //

    /** Constructs a shadow of the specified symbol table. */
    public ShadowedSymbolTable(SymbolTable symbolTable) {
        fSymbolTable = symbolTable;
    } // <init>(SymbolTable)

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

        if (fSymbolTable.containsSymbol(symbol)) {
            return fSymbolTable.addSymbol(symbol);
        }
        return super.addSymbol(symbol);

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

        if (fSymbolTable.containsSymbol(buffer, offset, length)) {
            return fSymbolTable.addSymbol(buffer, offset, length);
        }
        return super.addSymbol(buffer, offset, length);

    } // addSymbol(char[],int,int):String

    /**
     * Returns a hashcode value for the specified symbol. The value
     * returned by this method must be identical to the value returned
     * by the <code>hash(char[],int,int)</code> method when called
     * with the character array that comprises the symbol string.
     *
     * <p>
     * 返回指定符号的哈希码值。当使用包含符号字符串的字符数组调用时,此方法返回的值必须与<code> hash(char [],int,int)</code>方法返回的值相同。
     * 
     * 
     * @param symbol The symbol to hash.
     */
    public int hash(String symbol) {
        return fSymbolTable.hash(symbol);
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
     * @param buffer The character buffer containing the symbol.
     * @param offset The offset into the character buffer of the start
     *               of the symbol.
     * @param length The length of the symbol.
     */
    public int hash(char[] buffer, int offset, int length) {
        return fSymbolTable.hash(buffer, offset, length);
    } // hash(char[],int,int):int

} // class ShadowedSymbolTable
