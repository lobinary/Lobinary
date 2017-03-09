/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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

package com.sun.org.apache.xerces.internal.jaxp.validation;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLSchemaDescription;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;

/**
 * <p>This grammar pool is a memory sensitive cache. The grammars
 * stored in the pool are softly reachable and may be cleared by
 * the garbage collector in response to memory demand. Equality
 * of <code>XMLSchemaDescription</code>s is determined using both
 * the target namespace for the schema and schema location.</p>
 *
 * <p>
 *  <p>此语法池是一个内存敏感缓存。存储在池中的语法是可轻易到达的,并且可以由垃圾收集器响应于存储器需求来清除。
 * 使用模式和模式位置的目标命名空间确定<code> XMLSchemaDescription </code>的平等性。</p>。
 * 
 * 
 * @author Michael Glavassevich, IBM
 */
final class SoftReferenceGrammarPool implements XMLGrammarPool {

    //
    // Constants
    //

    /** Default size. */
    protected static final int TABLE_SIZE = 11;

    /** Zero length grammar array. */
    protected static final Grammar [] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar [0];

    //
    // Data
    //

    /** Grammars. */
    protected Entry [] fGrammars = null;

    /** Flag indicating whether this pool is locked */
    protected boolean fPoolIsLocked;

    /** The number of grammars in the pool */
    protected int fGrammarCount = 0;

    /** Reference queue for cleared grammar references */
    protected final ReferenceQueue fReferenceQueue = new ReferenceQueue();

    //
    // Constructors
    //

    /** Constructs a grammar pool with a default number of buckets. */
    public SoftReferenceGrammarPool() {
        fGrammars = new Entry[TABLE_SIZE];
        fPoolIsLocked = false;
    } // <init>()

    /** Constructs a grammar pool with a specified number of buckets. */
    public SoftReferenceGrammarPool(int initialCapacity) {
        fGrammars = new Entry[initialCapacity];
        fPoolIsLocked = false;
    }

    //
    // XMLGrammarPool methods
    //

    /* <p> Retrieve the initial known set of grammars. This method is
     * called by a validator before the validation starts. The application
     * can provide an initial set of grammars available to the current
     * validation attempt. </p>
     *
     * <p>
     *  在验证开始之前由验证器调用。应用程序可以提供可用于当前验证尝试的语法的初始集合。 </p>
     * 
     * 
     * @param grammarType The type of the grammar, from the
     *                    <code>com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription</code>
     *                    interface.
     * @return            The set of grammars the validator may put in its "bucket"
     */
    public Grammar [] retrieveInitialGrammarSet (String grammarType) {
        synchronized (fGrammars) {
            clean();
            // Return no grammars. This allows the garbage collector to sift
            // out grammars which are not in use when memory demand is high.
            // It also allows the pool to return the "right" schema grammar
            // based on schema locations.
            return ZERO_LENGTH_GRAMMAR_ARRAY;
        }
    } // retrieveInitialGrammarSet (String): Grammar[]

    /* <p> Return the final set of grammars that the validator ended up
     * with. This method is called after the validation finishes. The
     * application may then choose to cache some of the returned grammars.</p>
     * <p>In this implementation, we make our choice based on whether this object
     * is "locked"--that is, whether the application has instructed
     * us not to accept any new grammars.</p>
     *
     * <p>
     *  与。此方法在验证完成后调用。然后应用程序可以选择缓存一些返回的语法。</p> <p>在这个实现中,我们根据这个对象是否被"锁定"来做出选择 - 也就是说,应用程序是否指示我们不要接受任何新语法。
     * </p>。
     * 
     * 
     * @param grammarType The type of the grammars being returned;
     * @param grammars    An array containing the set of grammars being
     *                    returned; order is not significant.
     */
    public void cacheGrammars(String grammarType, Grammar[] grammars) {
        if (!fPoolIsLocked) {
            for (int i = 0; i < grammars.length; ++i) {
                putGrammar(grammars[i]);
            }
        }
    } // cacheGrammars(String, Grammar[]);

    /* <p> This method requests that the application retrieve a grammar
     * corresponding to the given GrammarIdentifier from its cache.
     * If it cannot do so it must return null; the parser will then
     * call the EntityResolver. </p>
     * <strong>An application must not call its EntityResolver itself
     * from this method; this may result in infinite recursions.</strong>
     *
     * This implementation chooses to use the root element name to identify a DTD grammar
     * and the target namespace to identify a Schema grammar.
     *
     * <p>
     * 对应于来自其缓存的给定语法标识符。如果它不能这样做,它必须返回null;解析器将调用EntityResolver。
     *  </p> <strong>应用程序不能从此方法调用其EntityResolver本身;这可能会导致无限递归。</strong>。
     * 
     *  此实现选择使用根元素名称来标识DTD语法,并使用目标命名空间来标识模式语法。
     * 
     * 
     * @param desc The description of the Grammar being requested.
     * @return     The Grammar corresponding to this description or null if
     *             no such Grammar is known.
     */
    public Grammar retrieveGrammar(XMLGrammarDescription desc) {
        return getGrammar(desc);
    } // retrieveGrammar(XMLGrammarDescription):  Grammar

    //
    // Public methods
    //

    /**
     * Puts the specified grammar into the grammar pool and associates it to
     * its root element name or its target namespace.
     *
     * <p>
     *  将指定的语法放入语法池,并将其关联到其根元素名称或其目标命名空间。
     * 
     * 
     * @param grammar The Grammar.
     */
    public void putGrammar(Grammar grammar) {
        if (!fPoolIsLocked) {
            synchronized (fGrammars) {
                clean();
                XMLGrammarDescription desc = grammar.getGrammarDescription();
                int hash = hashCode(desc);
                int index = (hash & 0x7FFFFFFF) % fGrammars.length;
                for (Entry entry = fGrammars[index]; entry != null; entry = entry.next) {
                    if (entry.hash == hash && equals(entry.desc, desc)) {
                        if (entry.grammar.get() != grammar) {
                            entry.grammar = new SoftGrammarReference(entry, grammar, fReferenceQueue);
                        }
                        return;
                    }
                }
                // create a new entry
                Entry entry = new Entry(hash, index, desc, grammar, fGrammars[index], fReferenceQueue);
                fGrammars[index] = entry;
                fGrammarCount++;
            }
        }
    } // putGrammar(Grammar)

    /**
     * Returns the grammar associated to the specified grammar description.
     * Currently, the root element name is used as the key for DTD grammars
     * and the target namespace  is used as the key for Schema grammars.
     *
     * <p>
     *  返回与指定的语法描述关联的语法。目前,根元素名称用作DTD语法的键,目标命名空间用作模式语法的键。
     * 
     * 
     * @param desc The Grammar Description.
     */
    public Grammar getGrammar(XMLGrammarDescription desc) {
        synchronized (fGrammars) {
            clean();
            int hash = hashCode(desc);
            int index = (hash & 0x7FFFFFFF) % fGrammars.length;
            for (Entry entry = fGrammars[index]; entry != null; entry = entry.next) {
                Grammar tempGrammar = (Grammar) entry.grammar.get();
                /** If the soft reference has been cleared, remove this entry from the pool. */
                if (tempGrammar == null) {
                    removeEntry(entry);
                }
                else if ((entry.hash == hash) && equals(entry.desc, desc)) {
                    return tempGrammar;
                }
            }
            return null;
        }
    } // getGrammar(XMLGrammarDescription):Grammar

    /**
     * Removes the grammar associated to the specified grammar description from the
     * grammar pool and returns the removed grammar. Currently, the root element name
     * is used as the key for DTD grammars and the target namespace  is used
     * as the key for Schema grammars.
     *
     * <p>
     *  从语法池中删除与指定的语法描述相关联的语法,并返回已删除的语法。目前,根元素名称用作DTD语法的键,目标命名空间用作模式语法的键。
     * 
     * 
     * @param desc The Grammar Description.
     * @return     The removed grammar.
     */
    public Grammar removeGrammar(XMLGrammarDescription desc) {
        synchronized (fGrammars) {
            clean();
            int hash = hashCode(desc);
            int index = (hash & 0x7FFFFFFF) % fGrammars.length;
            for (Entry entry = fGrammars[index]; entry != null; entry = entry.next) {
                if ((entry.hash == hash) && equals(entry.desc, desc)) {
                    return removeEntry(entry);
                }
            }
            return null;
        }
    } // removeGrammar(XMLGrammarDescription):Grammar

    /**
     * Returns true if the grammar pool contains a grammar associated
     * to the specified grammar description. Currently, the root element name
     * is used as the key for DTD grammars and the target namespace  is used
     * as the key for Schema grammars.
     *
     * <p>
     *  如果语法池包含与指定的语法描述关联的语法,则返回true。目前,根元素名称用作DTD语法的键,目标命名空间用作模式语法的键。
     * 
     * 
     * @param desc The Grammar Description.
     */
    public boolean containsGrammar(XMLGrammarDescription desc) {
        synchronized (fGrammars) {
            clean();
            int hash = hashCode(desc);
            int index = (hash & 0x7FFFFFFF) % fGrammars.length;
            for (Entry entry = fGrammars[index]; entry != null ; entry = entry.next) {
                Grammar tempGrammar = (Grammar) entry.grammar.get();
                /** If the soft reference has been cleared, remove this entry from the pool. */
                if (tempGrammar == null) {
                    removeEntry(entry);
                }
                else if ((entry.hash == hash) && equals(entry.desc, desc)) {
                    return true;
                }
            }
            return false;
        }
    } // containsGrammar(XMLGrammarDescription):boolean

    /* <p> Sets this grammar pool to a "locked" state--i.e.,
     * no new grammars will be added until it is "unlocked".
     * <p>
     *  没有新的语法将被添加,直到它"解锁"。
     * 
     */
    public void lockPool() {
        fPoolIsLocked = true;
    } // lockPool()

    /* <p> Sets this grammar pool to an "unlocked" state--i.e.,
     * new grammars will be added when putGrammar or cacheGrammars
     * are called.
     * <p>
     *  当调用putGrammar或cacheGrammars时,将添加新的语法。
     * 
     */
    public void unlockPool() {
        fPoolIsLocked = false;
    } // unlockPool()

    /*
     * <p>This method clears the pool-i.e., removes references
     * to all the grammars in it.</p>
     * <p>
     *  <p>此方法清除池,即删除对其中所有语法的引用。</p>
     * 
     */
    public void clear() {
        for (int i=0; i<fGrammars.length; i++) {
            if(fGrammars[i] != null) {
                fGrammars[i].clear();
                fGrammars[i] = null;
            }
        }
        fGrammarCount = 0;
    } // clear()

    /**
     * This method checks whether two grammars are the same. Currently, we compare
     * the root element names for DTD grammars and the target namespaces for Schema grammars.
     * The application can override this behaviour and add its own logic.
     *
     * <p>
     * 此方法检查两个语法是否相同。目前,我们比较DTD语法的根元素名称和模式语法的目标命名空间。应用程序可以覆盖此行为并添加自己的逻辑。
     * 
     * 
     * @param desc1 The grammar description
     * @param desc2 The grammar description of the grammar to be compared to
     * @return      True if the grammars are equal, otherwise false
     */
    public boolean equals(XMLGrammarDescription desc1, XMLGrammarDescription desc2) {
        if (desc1 instanceof XMLSchemaDescription) {
            if (!(desc2 instanceof XMLSchemaDescription)) {
                return false;
            }
            final XMLSchemaDescription sd1 = (XMLSchemaDescription) desc1;
            final XMLSchemaDescription sd2 = (XMLSchemaDescription) desc2;
            final String targetNamespace = sd1.getTargetNamespace();
            if (targetNamespace != null) {
                if (!targetNamespace.equals(sd2.getTargetNamespace())) {
                    return false;
                }
            }
            else if (sd2.getTargetNamespace() != null) {
                return false;
            }
            // The JAXP 1.3 spec says that the implementation can assume that
            // if two schema location hints are the same they always resolve
            // to the same document. In the default grammar pool implementation
            // we only look at the target namespaces. Here we also compare
            // location hints.
            final String expandedSystemId = sd1.getExpandedSystemId();
            if (expandedSystemId != null) {
                if (!expandedSystemId.equals(sd2.getExpandedSystemId())) {
                    return false;
                }
            }
            else if (sd2.getExpandedSystemId() != null) {
                return false;
            }
            return true;
        }
        return desc1.equals(desc2);
    }

    /**
     * Returns the hash code value for the given grammar description.
     *
     * <p>
     *  返回给定语法描述的哈希码值。
     * 
     * 
     * @param desc The grammar description
     * @return     The hash code value
     */
    public int hashCode(XMLGrammarDescription desc) {
        if (desc instanceof XMLSchemaDescription) {
            final XMLSchemaDescription sd = (XMLSchemaDescription) desc;
            final String targetNamespace = sd.getTargetNamespace();
            final String expandedSystemId = sd.getExpandedSystemId();
            int hash = (targetNamespace != null) ? targetNamespace.hashCode() : 0;
            hash ^= (expandedSystemId != null) ? expandedSystemId.hashCode() : 0;
            return hash;
        }
        return desc.hashCode();
    }

    /**
     * Removes the given entry from the pool
     *
     * <p>
     *  从池中删除给定的条目
     * 
     * 
     * @param entry the entry to remove
     * @return The grammar attached to this entry
     */
    private Grammar removeEntry(Entry entry) {
        if (entry.prev != null) {
            entry.prev.next = entry.next;
        }
        else {
            fGrammars[entry.bucket] = entry.next;
        }
        if (entry.next != null) {
            entry.next.prev = entry.prev;
        }
        --fGrammarCount;
        entry.grammar.entry = null;
        return (Grammar) entry.grammar.get();
    }

    /**
     * Removes stale entries from the pool.
     * <p>
     *  从池中删除失效的条目。
     * 
     */
    private void clean() {
        Reference ref = fReferenceQueue.poll();
        while (ref != null) {
            Entry entry = ((SoftGrammarReference) ref).entry;
            if (entry != null) {
                removeEntry(entry);
            }
            ref = fReferenceQueue.poll();
        }
    }

    /**
     * This class is a grammar pool entry. Each entry acts as a node
     * in a doubly linked list.
     * <p>
     *  这个类是一个语法池条目。每个条目充当双向链表中的节点。
     * 
     */
    static final class Entry {

        public int hash;
        public int bucket;
        public Entry prev;
        public Entry next;
        public XMLGrammarDescription desc;
        public SoftGrammarReference grammar;

        protected Entry(int hash, int bucket, XMLGrammarDescription desc, Grammar grammar, Entry next, ReferenceQueue queue) {
            this.hash = hash;
            this.bucket = bucket;
            this.prev = null;
            this.next = next;
            if (next != null) {
                next.prev = this;
            }
            this.desc = desc;
            this.grammar = new SoftGrammarReference(this, grammar, queue);
        }

        // clear this entry; useful to promote garbage collection
        // since reduces reference count of objects to be destroyed
        protected void clear () {
            desc = null;
            grammar = null;
            if(next != null) {
                next.clear();
                next = null;
            }
        } // clear()

    } // class Entry

    /**
     * This class stores a soft reference to a grammar object. It keeps a reference
     * to its associated entry, so that it can be easily removed from the pool.
     * <p>
     *  这个类存储对语法对象的软引用。它保持对其相关条目的引用,以便它可以容易地从池中移除。
     */
    static final class SoftGrammarReference extends SoftReference {

        public Entry entry;

        protected SoftGrammarReference(Entry entry, Grammar grammar, ReferenceQueue queue) {
            super(grammar, queue);
            this.entry = entry;
        }

    } // class SoftGrammarReference

} // class SoftReferenceGrammarPool
