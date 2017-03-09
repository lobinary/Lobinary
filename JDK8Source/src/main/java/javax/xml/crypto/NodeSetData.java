/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: NodeSetData.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：NodeSetData.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.util.Iterator;

/**
 * An abstract representation of a <code>Data</code> type containing a
 * node-set. The type (class) and ordering of the nodes contained in the set
 * are not defined by this class; instead that behavior should be
 * defined by <code>NodeSetData</code> subclasses.
 *
 * <p>
 *  包含节点集的<code> Data </code>类型的抽象表示。包含在集合中的节点的类型(类)和排序不由该类定义;而是该行为应该由<code> NodeSetData </code>子类定义。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public interface NodeSetData extends Data {

    /**
     * Returns a read-only iterator over the nodes contained in this
     * <code>NodeSetData</code> in
     * <a href="http://www.w3.org/TR/1999/REC-xpath-19991116#dt-document-order">
     * document order</a>. Attempts to modify the returned iterator
     * via the <code>remove</code> method throw
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  在<code> NodeSetData </code>中包含的节点上返回只读迭代器
     * <a href="http://www.w3.org/TR/1999/REC-xpath-19991116#dt-document-order">
     * 
     * @return an <code>Iterator</code> over the nodes in this
     *    <code>NodeSetData</code> in document order
     */
    @SuppressWarnings("rawtypes")
    Iterator iterator();
}
