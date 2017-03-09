/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.spi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A node in a directed graph.  In addition to an arbitrary
 * <code>Object</code> containing user data associated with the node,
 * each node maintains a <code>Set</code>s of nodes which are pointed
 * to by the current node (available from <code>getOutNodes</code>).
 * The in-degree of the node (that is, number of nodes that point to
 * the current node) may be queried.
 *
 * <p>
 *  有向图中的节点。
 * 除了包含与节点相关联的用户数据的任意<code> Object </code>,每个节点维护当前节点所指向的节点的<code> Set </code>(可从<code> getOutNodes </code>
 * )。
 *  有向图中的节点。可以查询节点的内部度(即,指向当前节点的节点的数量)。
 * 
 */
class DigraphNode implements Cloneable, Serializable {

    /** The data associated with this node. */
    protected Object data;

    /**
     * A <code>Set</code> of neighboring nodes pointed to by this
     * node.
     * <p>
     *  设置此节点所指向的相邻节点的<code>设置</code>。
     * 
     */
    protected Set outNodes = new HashSet();

    /** The in-degree of the node. */
    protected int inDegree = 0;

    /**
     * A <code>Set</code> of neighboring nodes that point to this
     * node.
     * <p>
     *  指向此节点的相邻节点的<code>设置</code>。
     * 
     */
    private Set inNodes = new HashSet();

    public DigraphNode(Object data) {
        this.data = data;
    }

    /** Returns the <code>Object</code> referenced by this node. */
    public Object getData() {
        return data;
    }

    /**
     * Returns an <code>Iterator</code> containing the nodes pointed
     * to by this node.
     * <p>
     *  返回包含此节点所指向的节点的<code>迭代器</code>。
     * 
     */
    public Iterator getOutNodes() {
        return outNodes.iterator();
    }

    /**
     * Adds a directed edge to the graph.  The outNodes list of this
     * node is updated and the in-degree of the other node is incremented.
     *
     * <p>
     *  向图形添加有向边。更新此节点的outNodes列表,并增加其他节点的in-degree。
     * 
     * 
     * @param node a <code>DigraphNode</code>.
     *
     * @return <code>true</code> if the node was not previously the
     * target of an edge.
     */
    public boolean addEdge(DigraphNode node) {
        if (outNodes.contains(node)) {
            return false;
        }

        outNodes.add(node);
        node.inNodes.add(this);
        node.incrementInDegree();
        return true;
    }

    /**
     * Returns <code>true</code> if an edge exists between this node
     * and the given node.
     *
     * <p>
     *  如果此节点与给定节点之间存在边,则返回<code> true </code>。
     * 
     * 
     * @param node a <code>DigraphNode</code>.
     *
     * @return <code>true</code> if the node is the target of an edge.
     */
    public boolean hasEdge(DigraphNode node) {
        return outNodes.contains(node);
    }

    /**
     * Removes a directed edge from the graph.  The outNodes list of this
     * node is updated and the in-degree of the other node is decremented.
     *
     * <p>
     *  从图形中删除有向边。更新此节点的outNodes列表,并减少其他节点的in-degree。
     * 
     * 
     * @return <code>true</code> if the node was previously the target
     * of an edge.
     */
    public boolean removeEdge(DigraphNode node) {
        if (!outNodes.contains(node)) {
            return false;
        }

        outNodes.remove(node);
        node.inNodes.remove(this);
        node.decrementInDegree();
        return true;
    }

    /**
     * Removes this node from the graph, updating neighboring nodes
     * appropriately.
     * <p>
     *  从图中删除此节点,适当地更新邻居节点。
     */
    public void dispose() {
        Object[] inNodesArray = inNodes.toArray();
        for(int i=0; i<inNodesArray.length; i++) {
            DigraphNode node = (DigraphNode) inNodesArray[i];
            node.removeEdge(this);
        }

        Object[] outNodesArray = outNodes.toArray();
        for(int i=0; i<outNodesArray.length; i++) {
            DigraphNode node = (DigraphNode) outNodesArray[i];
            removeEdge(node);
        }
    }

    /** Returns the in-degree of this node. */
    public int getInDegree() {
        return inDegree;
    }

    /** Increments the in-degree of this node. */
    private void incrementInDegree() {
        ++inDegree;
    }

    /** Decrements the in-degree of this node. */
    private void decrementInDegree() {
        --inDegree;
    }
}
