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

package com.sun.org.apache.xerces.internal.parsers;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.util.XMLGrammarPoolImpl;

import com.sun.org.apache.xerces.internal.util.ShadowedSymbolTable;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * A parser pool that enables caching of grammars. The caching parser
 * pool is constructed with a specific symbol table and grammar pool
 * that has already been populated with the grammars used by the
 * application.
 * <p>
 * Once the caching parser pool is constructed, specific parser
 * instances are created by calling the appropriate factory method
 * on the parser pool.
 * <p>
 * <strong>Note:</strong> There is a performance penalty for using
 * a caching parser pool due to thread safety. Access to the symbol
 * table and grammar pool must be synchronized to ensure the safe
 * operation of the symbol table and grammar pool.
 * <p>
 * <strong>Note:</strong> If performance is critical, then another
 * mechanism needs to be used instead of the caching parser pool.
 * One approach would be to create parser instances that do not
 * share these structures. Instead, each instance would get its
 * own copy to use while parsing. This avoids the synchronization
 * overhead at the expense of more memory and the time required
 * to copy the structures for each new parser instance. And even
 * when a parser instance is re-used, there is a potential for a
 * memory leak due to new symbols being added to the symbol table
 * over time. In other words, always take caution to make sure
 * that your application is thread-safe and avoids leaking memory.
 *
 * <p>
 *  启用语法缓存的解析器池。缓存解析器池由特定的符号表和语法池构成,该池已经被应用程序使用的语法填充。
 * <p>
 *  一旦构建缓存解析器池,就可以通过在解析器池上调用相应的工厂方法来创建特定的解析器实例。
 * <p>
 *  <strong>请注意</strong>：由于线程安全,使用缓存解析器池会导致性能下降。对符号表和语法池的访问必须同步,以确保符号表和语法池的安全操作。
 * <p>
 * <strong>注意：</strong>如果性能至关重要,则需要使用另一种机制,而不是缓存解析器池。一种方法是创建不共享这些结构的解析器实例。相反,每个实例将获得自己的副本,以供解析时使用。
 * 这避免了以牺牲更多内存和为每个新的解析器实例复制结构所需的时间为代价的同步开销。并且即使当重新使用解析器实例时,由于随着时间的推移新符号被添加到符号表,因此存在潜在的存储器泄漏。
 * 换句话说,总是要小心,以确保您的应用程序是线程安全的,并避免泄漏的内存。
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public class CachingParserPool {

    //
    // Constants
    //

    /** Default shadow symbol table (false). */
    public static final boolean DEFAULT_SHADOW_SYMBOL_TABLE = false;

    /** Default shadow grammar pool (false). */
    public static final boolean DEFAULT_SHADOW_GRAMMAR_POOL = false;

    //
    // Data
    //

    /**
     * Symbol table. The symbol table that the caching parser pool is
     * constructed with is automatically wrapped in a synchronized
     * version for thread-safety.
     * <p>
     *  符号表。构建缓存解析器池的符号表将自动包装在线程安全的同步版本中。
     * 
     */
    protected SymbolTable fSynchronizedSymbolTable;

    /**
     * Grammar pool. The grammar pool that the caching parser pool is
     * constructed with is automatically wrapped in a synchronized
     * version for thread-safety.
     * <p>
     *  语法池。构建缓存解析器池的语法池将自动包装在线程安全的同步版本中。
     * 
     */
    protected XMLGrammarPool fSynchronizedGrammarPool;

    /**
     * Shadow the symbol table for new parser instances. If true,
     * new parser instances use shadow copies of the main symbol
     * table and are not allowed to add new symbols to the main
     * symbol table. New symbols are added to the shadow symbol
     * table and are local to the parser instance.
     * <p>
     *  为新的解析器实例隐藏符号表。如果为true,则新的解析器实例使用主符号表的卷影副本,并且不允许向主符号表添加新的符号。新的符号被添加到阴影符号表中,并且对于解析器实例是本地的。
     * 
     */
    protected boolean fShadowSymbolTable = DEFAULT_SHADOW_SYMBOL_TABLE;

    /**
     * Shadow the grammar pool for new parser instances. If true,
     * new parser instances use shadow copies of the main grammar
     * pool and are not allowed to add new grammars to the main
     * grammar pool. New grammars are added to the shadow grammar
     * pool and are local to the parser instance.
     * <p>
     * 隐藏新解析器实例的语法池。如果为true,则新的解析器实例使用主文法池的卷影副本,并且不允许向主文法池添加新的语法。新的语法被添加到影子语法池,并且对于语法分析器实例是本地的。
     * 
     */
    protected boolean fShadowGrammarPool = DEFAULT_SHADOW_GRAMMAR_POOL;

    //
    // Constructors
    //

    /** Default constructor. */
    public CachingParserPool() {
        this(new SymbolTable(), new XMLGrammarPoolImpl());
    } // <init>()

    /**
     * Constructs a caching parser pool with the specified symbol table
     * and grammar pool.
     *
     * <p>
     *  构造具有指定符号表和语法池的缓存解析器池。
     * 
     * 
     * @param symbolTable The symbol table.
     * @param grammarPool The grammar pool.
     */
    public CachingParserPool(SymbolTable symbolTable, XMLGrammarPool grammarPool) {
        fSynchronizedSymbolTable = new SynchronizedSymbolTable(symbolTable);
        fSynchronizedGrammarPool = new SynchronizedGrammarPool(grammarPool);
    } // <init>(SymbolTable,XMLGrammarPool)

    //
    // Public methods
    //

    /** Returns the symbol table. */
    public SymbolTable getSymbolTable() {
        return fSynchronizedSymbolTable;
    } // getSymbolTable():SymbolTable

    /** Returns the grammar pool. */
    public XMLGrammarPool getXMLGrammarPool() {
        return fSynchronizedGrammarPool;
    } // getXMLGrammarPool():XMLGrammarPool

    // setters and getters

    /**
     * Sets whether new parser instance receive shadow copies of the
     * main symbol table.
     *
     * <p>
     *  设置新的解析器实例是否接收主符号表的卷影副本。
     * 
     * 
     * @param shadow If true, new parser instances use shadow copies
     *               of the main symbol table and are not allowed to
     *               add new symbols to the main symbol table. New
     *               symbols are added to the shadow symbol table and
     *               are local to the parser instance. If false, new
     *               parser instances are allowed to add new symbols
     *               to the main symbol table.
     */
    public void setShadowSymbolTable(boolean shadow) {
        fShadowSymbolTable = shadow;
    } // setShadowSymbolTable(boolean)

    // factory methods

    /** Creates a new DOM parser. */
    public DOMParser createDOMParser() {
        SymbolTable symbolTable = fShadowSymbolTable
                                ? new ShadowedSymbolTable(fSynchronizedSymbolTable)
                                : fSynchronizedSymbolTable;
        XMLGrammarPool grammarPool = fShadowGrammarPool
                                ? new ShadowedGrammarPool(fSynchronizedGrammarPool)
                                : fSynchronizedGrammarPool;
        return new DOMParser(symbolTable, grammarPool);
    } // createDOMParser():DOMParser

    /** Creates a new SAX parser. */
    public SAXParser createSAXParser() {
        SymbolTable symbolTable = fShadowSymbolTable
                                ? new ShadowedSymbolTable(fSynchronizedSymbolTable)
                                : fSynchronizedSymbolTable;
        XMLGrammarPool grammarPool = fShadowGrammarPool
                                ? new ShadowedGrammarPool(fSynchronizedGrammarPool)
                                : fSynchronizedGrammarPool;
        return new SAXParser(symbolTable, grammarPool);
    } // createSAXParser():SAXParser

    //
    // Classes
    //

    /**
     * Synchronized grammar pool.
     *
     * <p>
     *  同步语法池。
     * 
     * 
     * @author Andy Clark, IBM
     */
    public static final class SynchronizedGrammarPool
        implements XMLGrammarPool {

        //
        // Data
        //

        /** Main grammar pool. */
        private XMLGrammarPool fGrammarPool;

        //
        // Constructors
        //

        /** Constructs a synchronized grammar pool. */
        public SynchronizedGrammarPool(XMLGrammarPool grammarPool) {
            fGrammarPool = grammarPool;
        } // <init>(XMLGrammarPool)

        //
        // GrammarPool methods
        //

        // retrieve the initial set of grammars for the validator
        // to work with.
        // REVISIT:  does this need to be synchronized since it's just reading?
        // @param grammarType type of the grammars to be retrieved.
        // @return the initial grammar set the validator may place in its "bucket"
        public Grammar [] retrieveInitialGrammarSet(String grammarType ) {
            synchronized (fGrammarPool) {
                return fGrammarPool.retrieveInitialGrammarSet(grammarType);
            }
        } // retrieveInitialGrammarSet(String):  Grammar[]

        // retrieve a particular grammar.
        // REVISIT:  does this need to be synchronized since it's just reading?
        // @param gDesc description of the grammar to be retrieved
        // @return Grammar corresponding to gDesc, or null if none exists.
        public Grammar retrieveGrammar(XMLGrammarDescription gDesc) {
            synchronized (fGrammarPool) {
                return fGrammarPool.retrieveGrammar(gDesc);
            }
        } // retrieveGrammar(XMLGrammarDesc):  Grammar

        // give the grammarPool the option of caching these grammars.
        // This certainly must be synchronized.
        // @param grammarType The type of the grammars to be cached.
        // @param grammars the Grammars that may be cached (unordered, Grammars previously
        //  given to the validator may be included).
        public void cacheGrammars(String grammarType, Grammar[] grammars) {
            synchronized (fGrammarPool) {
                fGrammarPool.cacheGrammars(grammarType, grammars);
            }
        } // cacheGrammars(String, Grammar[]);

        /** lock the grammar pool */
        public void lockPool() {
            synchronized (fGrammarPool) {
                fGrammarPool.lockPool();
            }
        } // lockPool()

        /** clear the grammar pool */
        public void clear() {
            synchronized (fGrammarPool) {
                fGrammarPool.clear();
            }
        } // lockPool()

        /** unlock the grammar pool */
        public void unlockPool() {
            synchronized (fGrammarPool) {
                fGrammarPool.unlockPool();
            }
        } // unlockPool()

        /***
         * Methods corresponding to original (pre Xerces2.0.0final)
         * grammarPool have been commented out.
         * <p>
         *  对应于原始(pre Xerces2.0.0 final)语法Pool的方法已被注释掉。
         * 
         */
        /**
         * Puts the specified grammar into the grammar pool.
         *
         * <p>
         *  将指定的语法放入语法池。
         * 
         * 
         * @param key Key to associate with grammar.
         * @param grammar Grammar object.
         */
        /******
        public void putGrammar(String key, Grammar grammar) {
            synchronized (fGrammarPool) {
                fGrammarPool.putGrammar(key, grammar);
            }
        } // putGrammar(String,Grammar)
        /* <p>
        /*  public void putGrammar(String key,Grammar grammar){synchronized(fGrammarPool){fGrammarPool.putGrammar(key,grammar); }
        /* } // putGrammar(String,Grammar)。
        /* 
        /* 
        *******/

        /**
         * Returns the grammar associated to the specified key.
         *
         * <p>
         *  返回与指定键相关联的语法。
         * 
         * 
         * @param key The key of the grammar.
         */
        /**********
        public Grammar getGrammar(String key) {
            synchronized (fGrammarPool) {
                return fGrammarPool.getGrammar(key);
            }
        } // getGrammar(String):Grammar
        /* <p>
        /*  公共语法getGrammar(String key){synchronized(fGrammarPool){return fGrammarPool.getGrammar(key); }} // get
        /* Grammar(String)：Grammar。
        /* 
        /* 
        ***********/

        /**
         * Removes the grammar associated to the specified key from the
         * grammar pool and returns the removed grammar.
         *
         * <p>
         *  从语法池中删除与指定键相关联的语法,并返回删除的语法。
         * 
         * 
         * @param key The key of the grammar.
         */
        /**********
        public Grammar removeGrammar(String key) {
            synchronized (fGrammarPool) {
                return fGrammarPool.removeGrammar(key);
            }
        } // removeGrammar(String):Grammar
        /* <p>
        /*  公共语法removeGrammar(String key){synchronized(fGrammarPool){return fGrammarPool.removeGrammar(key); }} 
        /* // removeGrammar(String)：Grammar。
        /* 
        /* 
        ******/

        /**
         * Returns true if the grammar pool contains a grammar associated
         * to the specified key.
         *
         * <p>
         *  如果语法池包含与指定键相关联的语法,则返回true。
         * 
         * 
         * @param key The key of the grammar.
         */
        /**********
        public boolean containsGrammar(String key) {
            synchronized (fGrammarPool) {
                return fGrammarPool.containsGrammar(key);
            }
        } // containsGrammar(String):boolean
        /* <p>
        /*  public boolean containsGrammar(String key){synchronized(fGrammarPool){return fGrammarPool.containsGrammar(key); }
        /* } // containsGrammar(String)：boolean。
        /* 
        /* 
        ********/

    } // class SynchronizedGrammarPool

    /**
     * Shadowed grammar pool.
     * This class is predicated on the existence of a concrete implementation;
     * so using our own doesn't seem to bad an idea.
     *
     * <p>
     * 阴影语法池。这个类基于一个具体实现的存在;所以使用我们自己似乎并不坏的想法。
     * 
     * 
     * @author Andy Clark, IBM
     * @author Neil Graham, IBM
     */
    public static final class ShadowedGrammarPool
        extends XMLGrammarPoolImpl {

        //
        // Data
        //

        /** Main grammar pool. */
        private XMLGrammarPool fGrammarPool;

        //
        // Constructors
        //

        /** Constructs a shadowed grammar pool. */
        public ShadowedGrammarPool(XMLGrammarPool grammarPool) {
            fGrammarPool = grammarPool;
        } // <init>(GrammarPool)

        //
        // GrammarPool methods
        //

        /**
         * Retrieve the initial set of grammars for the validator to work with.
         * REVISIT:  does this need to be synchronized since it's just reading?
         *
         * <p>
         *  检索验证器使用的初始语法集。 REVISIT：这需要同步,因为它只是阅读?
         * 
         * 
         * @param grammarType Type of the grammars to be retrieved.
         * @return            The initial grammar set the validator may place in its "bucket"
         */
        public Grammar [] retrieveInitialGrammarSet(String grammarType ) {
            Grammar [] grammars = super.retrieveInitialGrammarSet(grammarType);
            if (grammars != null) return grammars;
            return fGrammarPool.retrieveInitialGrammarSet(grammarType);
        } // retrieveInitialGrammarSet(String):  Grammar[]

        /**
         * Retrieve a particular grammar.
         * REVISIT:  does this need to be synchronized since it's just reading?
         *
         * <p>
         *  检索特定语法。 REVISIT：这需要同步,因为它只是阅读?
         * 
         * 
         * @param gDesc Description of the grammar to be retrieved
         * @return      Grammar corresponding to gDesc, or null if none exists.
         */
        public Grammar retrieveGrammar(XMLGrammarDescription gDesc) {
            Grammar g = super.retrieveGrammar(gDesc);
            if(g != null) return g;
            return fGrammarPool.retrieveGrammar(gDesc);
        } // retrieveGrammar(XMLGrammarDesc):  Grammar

        /**
         * Give the grammarPool the option of caching these grammars.
         * This certainly must be synchronized.
         *
         * <p>
         *  为grammarPool提供缓存这些语法的选项。这当然必须同步。
         * 
         * 
         * @param grammarType The type of the grammars to be cached.
         * @param grammars    The Grammars that may be cached (unordered, Grammars previously
         *                    given to the validator may be included).
         */
        public void cacheGrammars(String grammarType, Grammar[] grammars) {
           // better give both grammars a shot...
           super.cacheGrammars(grammarType, grammars);
           fGrammarPool.cacheGrammars(grammarType, grammars);
        } // cacheGrammars(grammarType, Grammar[]);

        /**
         * Returns the grammar associated to the specified description.
         *
         * <p>
         *  返回与指定描述关联的语法。
         * 
         * 
         * @param desc The description of the grammar.
         */
        public Grammar getGrammar(XMLGrammarDescription desc) {

            if (super.containsGrammar(desc)) {
                return super.getGrammar(desc);
            }
            return null;

        } // getGrammar(XMLGrammarDescription):Grammar

        /**
         * Returns true if the grammar pool contains a grammar associated
         * to the specified description.
         *
         * <p>
         *  如果语法池包含与指定的描述关联的语法,则返回true。
         * 
         * @param desc The description of the grammar.
         */
        public boolean containsGrammar(XMLGrammarDescription desc) {
            return super.containsGrammar(desc);
        } // containsGrammar(XMLGrammarDescription):boolean

    } // class ShadowedGrammarPool

} // class CachingParserPool
