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

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * A set of <code>Object</code>s with pairwise orderings between them.
 * The <code>iterator</code> method provides the elements in
 * topologically sorted order.  Elements participating in a cycle
 * are not returned.
 *
 * Unlike the <code>SortedSet</code> and <code>SortedMap</code>
 * interfaces, which require their elements to implement the
 * <code>Comparable</code> interface, this class receives ordering
 * information via its <code>setOrdering</code> and
 * <code>unsetPreference</code> methods.  This difference is due to
 * the fact that the relevant ordering between elements is unlikely to
 * be inherent in the elements themselves; rather, it is set
 * dynamically accoring to application policy.  For example, in a
 * service provider registry situation, an application might allow the
 * user to set a preference order for service provider objects
 * supplied by a trusted vendor over those supplied by another.
 *
 * <p>
 *  一组<code> Object </code>,在它们之间具有成对排序。 <code> iterator </code>方法以拓扑排序的顺序提供元素。不返回参与循环的元素。
 * 
 *  与需要它们的元素实现<code> Comparable </code>接口的<code> SortedSet </code>和<code> SortedMap </code>接口不同,此类通过其<code>
 *  setOrdering < / code>和<code> unsetPreference </code>方法。
 * 这种差异是由于元件之间的相关排序不可能是元件本身固有的;而是根据应用策略动态设置。例如,在服务提供商注册表情况下,应用可以允许用户针对由另一方提供的服务提供商对象提供的服务提供商对象设置优先顺序。
 * 
 */
class PartiallyOrderedSet extends AbstractSet {

    // The topological sort (roughly) follows the algorithm described in
    // Horowitz and Sahni, _Fundamentals of Data Structures_ (1976),
    // p. 315.

    // Maps Objects to DigraphNodes that contain them
    private Map poNodes = new HashMap();

    // The set of Objects
    private Set nodes = poNodes.keySet();

    /**
     * Constructs a <code>PartiallyOrderedSet</code>.
     * <p>
     *  构造一个<code> PartiallyOrderedSet </code>。
     * 
     */
    public PartiallyOrderedSet() {}

    public int size() {
        return nodes.size();
    }

    public boolean contains(Object o) {
        return nodes.contains(o);
    }

    /**
     * Returns an iterator over the elements contained in this
     * collection, with an ordering that respects the orderings set
     * by the <code>setOrdering</code> method.
     * <p>
     *  返回此集合中包含的元素的迭代器,其顺序遵循<code> setOrdering </code>方法设置的顺序。
     * 
     */
    public Iterator iterator() {
        return new PartialOrderIterator(poNodes.values().iterator());
    }

    /**
     * Adds an <code>Object</code> to this
     * <code>PartiallyOrderedSet</code>.
     * <p>
     *  向此<code> PartiallyOrderedSet </code>中添加<code> Object </code>。
     * 
     */
    public boolean add(Object o) {
        if (nodes.contains(o)) {
            return false;
        }

        DigraphNode node = new DigraphNode(o);
        poNodes.put(o, node);
        return true;
    }

    /**
     * Removes an <code>Object</code> from this
     * <code>PartiallyOrderedSet</code>.
     * <p>
     *  从此<code> PartiallyOrderedSet </code>中删除<code> Object </code>。
     * 
     */
    public boolean remove(Object o) {
        DigraphNode node = (DigraphNode)poNodes.get(o);
        if (node == null) {
            return false;
        }

        poNodes.remove(o);
        node.dispose();
        return true;
    }

    public void clear() {
        poNodes.clear();
    }

    /**
     * Sets an ordering between two nodes.  When an iterator is
     * requested, the first node will appear earlier in the
     * sequence than the second node.  If a prior ordering existed
     * between the nodes in the opposite order, it is removed.
     *
     * <p>
     * 设置两个节点之间的顺序。当请求迭代器时,第一个节点将出现在序列中比第二个节点更早。如果先前的顺序在相反顺序的节点之间存在,则它被去除。
     * 
     * 
     * @return <code>true</code> if no prior ordering existed
     * between the nodes, <code>false</code>otherwise.
     */
    public boolean setOrdering(Object first, Object second) {
        DigraphNode firstPONode =
            (DigraphNode)poNodes.get(first);
        DigraphNode secondPONode =
            (DigraphNode)poNodes.get(second);

        secondPONode.removeEdge(firstPONode);
        return firstPONode.addEdge(secondPONode);
    }

    /**
     * Removes any ordering between two nodes.
     *
     * <p>
     *  删除两个节点之间的任何顺序。
     * 
     * 
     * @return true if a prior prefence existed between the nodes.
     */
    public boolean unsetOrdering(Object first, Object second) {
        DigraphNode firstPONode =
            (DigraphNode)poNodes.get(first);
        DigraphNode secondPONode =
            (DigraphNode)poNodes.get(second);

        return firstPONode.removeEdge(secondPONode) ||
            secondPONode.removeEdge(firstPONode);
    }

    /**
     * Returns <code>true</code> if an ordering exists between two
     * nodes.
     * <p>
     *  如果两个节点之间存在顺序,则返回<code> true </code>。
     */
    public boolean hasOrdering(Object preferred, Object other) {
        DigraphNode preferredPONode =
            (DigraphNode)poNodes.get(preferred);
        DigraphNode otherPONode =
            (DigraphNode)poNodes.get(other);

        return preferredPONode.hasEdge(otherPONode);
    }
}

class PartialOrderIterator implements Iterator {

    LinkedList zeroList = new LinkedList();
    Map inDegrees = new HashMap(); // DigraphNode -> Integer

    public PartialOrderIterator(Iterator iter) {
        // Initialize scratch in-degree values, zero list
        while (iter.hasNext()) {
            DigraphNode node = (DigraphNode)iter.next();
            int inDegree = node.getInDegree();
            inDegrees.put(node, new Integer(inDegree));

            // Add nodes with zero in-degree to the zero list
            if (inDegree == 0) {
                zeroList.add(node);
            }
        }
    }

    public boolean hasNext() {
        return !zeroList.isEmpty();
    }

    public Object next() {
        DigraphNode first = (DigraphNode)zeroList.removeFirst();

        // For each out node of the output node, decrement its in-degree
        Iterator outNodes = first.getOutNodes();
        while (outNodes.hasNext()) {
            DigraphNode node = (DigraphNode)outNodes.next();
            int inDegree = ((Integer)inDegrees.get(node)).intValue() - 1;
            inDegrees.put(node, new Integer(inDegree));

            // If the in-degree has fallen to 0, place the node on the list
            if (inDegree == 0) {
                zeroList.add(node);
            }
        }

        return first.getData();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
