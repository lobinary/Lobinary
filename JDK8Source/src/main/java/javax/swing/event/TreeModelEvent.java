/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

import java.util.EventObject;
import javax.swing.tree.TreePath;


/**
 * Encapsulates information describing changes to a tree model, and
 * used to notify tree model listeners of the change.
 * For more information and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html">How to Write a Tree Model Listener</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  封装描述树模型更改的信息,并用于通知树模型侦听器的更改。
 * 有关详细信息和示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html">
 * 如何编写树模型侦听器</a>, em> Java教程。
 *  封装描述树模型更改的信息,并用于通知树模型侦听器的更改。</em>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
public class TreeModelEvent extends EventObject {
    /** Path to the parent of the nodes that have changed. */
    protected TreePath  path;
    /** Indices identifying the position of where the children were. */
    protected int[]     childIndices;
    /** Children that have been removed. */
    protected Object[]  children;

    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * an array of Objects. All of the modified objects are siblings which are
     * direct descendents (not grandchildren) of the specified parent.
     * The positions at which the inserts, deletes, or changes occurred are
     * specified by an array of <code>int</code>. The indexes in that array
     * must be in order, from lowest to highest.
     * <p>
     * For changes, the indexes in the model correspond exactly to the indexes
     * of items currently displayed in the UI. As a result, it is not really
     * critical if the indexes are not in their exact order. But after multiple
     * inserts or deletes, the items currently in the UI no longer correspond
     * to the items in the model. It is therefore critical to specify the
     * indexes properly for inserts and deletes.
     * <p>
     * For inserts, the indexes represent the <i>final</i> state of the tree,
     * after the inserts have occurred. Since the indexes must be specified in
     * order, the most natural processing methodology is to do the inserts
     * starting at the lowest index and working towards the highest. Accumulate
     * a Vector of <code>Integer</code> objects that specify the
     * insert-locations as you go, then convert the Vector to an
     * array of <code>int</code> to create the event. When the postition-index
     * equals zero, the node is inserted at the beginning of the list. When the
     * position index equals the size of the list, the node is "inserted" at
     * (appended to) the end of the list.
     * <p>
     * For deletes, the indexes represent the <i>initial</i> state of the tree,
     * before the deletes have occurred. Since the indexes must be specified in
     * order, the most natural processing methodology is to use a delete-counter.
     * Start by initializing the counter to zero and start work through the
     * list from lowest to highest. Every time you do a delete, add the current
     * value of the delete-counter to the index-position where the delete occurred,
     * and append the result to a Vector of delete-locations, using
     * <code>addElement()</code>. Then increment the delete-counter. The index
     * positions stored in the Vector therefore reflect the effects of all previous
     * deletes, so they represent each object's position in the initial tree.
     * (You could also start at the highest index and working back towards the
     * lowest, accumulating a Vector of delete-locations as you go using the
     * <code>insertElementAt(Integer, 0)</code>.) However you produce the Vector
     * of initial-positions, you then need to convert the Vector of <code>Integer</code>
     * objects to an array of <code>int</code> to create the event.
     * <p>
     * <b>Notes:</b><ul style="list-style-type:none">
     * <li>Like the <code>insertNodeInto</code> method in the
     *    <code>DefaultTreeModel</code> class, <code>insertElementAt</code>
     *    appends to the <code>Vector</code> when the index matches the size
     *    of the vector. So you can use <code>insertElementAt(Integer, 0)</code>
     *    even when the vector is empty.</li>
     * <li>To create a node changed event for the root node, specify the parent
     *     and the child indices as <code>null</code>.</li>
     * </ul>
     *
     * <p>
     *  用于在节点已更改,插入或删除时创建事件,将修改项目的父级的路径标识为对象数组。所有修改的对象都是指定父对象的直接后代(不是孙子)的兄弟节点。
     * 发生插入,删除或更改的位置由<code> int </code>的数组指定。该数组中的索引必须按顺序,从最低到最高。
     * <p>
     * 对于更改,模型中的索引完全对应于UI中当前显示的项目的索引。因此,如果索引不是按照它们的确切顺序,这并不是很关键。但是在多次插入或删除后,UI中当前的项目不再对应于模型中的项目。
     * 因此,为插入和删除正确指定索引至关重要。
     * <p>
     *  对于插入,在插入发生后,索引表示树的<i>最终</i>状态。由于索引必须按顺序指定,最自然的处理方法是从最低索引开始执行插入,并向最高索引执行。
     * 收集指定插入位置的<code> Integer </code>对象的向量,然后将向量转换为<code> int </code>的数组以创建事件。
     * 当postition-index等于零时,节点将插入到列表的开头。当位置索引等于列表的大小时,节点被"插入"在列表的末尾(附加到)。
     * <p>
     * 对于删除,索引表示在发生删除之前的树的<i>初始</i>状态。由于索引必须按顺序指定,最自然的处理方法是使用删除计数器。首先将计数器初始化为零,然后通过列表从最低到最高开始工作。
     * 每次执行删除时,将删除计数器的当前值添加到发生删除的索引位置,并使用<code> addElement()</code>将结果附加到delete-locations的向量。然后增加删除计数器。
     * 因此,存储在向量中的索引位置反映了所有先前删除的效果,因此它们表示每个对象在初始树中的位置。
     *  (你也可以从最高索引开始,向最低索引返回,使用<code> insertElementAt(Integer,0)</code>累积一个删除位置的向量。
     * )然而,位置,然后需要将<code> Integer </code>对象的向量转换为<code> int </code>的数组来创建事件。
     * <p>
     * <b>注意：</b> <ul style ="list-style-type：none"> <li>与<code> DefaultTreeModel </code>类中的<code> insertNod
     * eInto </code> code> insertElementAt </code>追加到<code> Vector </code>当索引匹配向量的大小。
     * 因此,即使向量为空,也可以使用<code> insertElementAt(Integer,0)</code>。
     * </li> <li>要为根节点创建节点更改事件,请将父索引和子索引指定为<code> null </code>。</li>。
     * </ul>
     * 
     * 
     * @param source the Object responsible for generating the event (typically
     *               the creator of the event object passes <code>this</code>
     *               for its value)
     * @param path   an array of Object identifying the path to the
     *               parent of the modified item(s), where the first element
     *               of the array is the Object stored at the root node and
     *               the last element is the Object stored at the parent node
     * @param childIndices an array of <code>int</code> that specifies the
     *               index values of the removed items. The indices must be
     *               in sorted order, from lowest to highest
     * @param children an array of Object containing the inserted, removed, or
     *                 changed objects
     * @see TreePath
     */
    public TreeModelEvent(Object source, Object[] path, int[] childIndices,
                          Object[] children)
    {
        this(source, (path == null) ? null : new TreePath(path), childIndices, children);
    }

    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * a TreePath object. For more information on how to specify the indexes
     * and objects, see
     * <code>TreeModelEvent(Object,Object[],int[],Object[])</code>.
     *
     * <p>
     *  用于在已更改,插入或删除节点时创建事件,将修改项目的父级的路径标识为TreePath对象。
     * 有关如何指定索引和对象的更多信息,请参阅<code> TreeModelEvent(Object,Object [],int [],Object [])</code>。
     * 
     * 
     * @param source the Object responsible for generating the event (typically
     *               the creator of the event object passes <code>this</code>
     *               for its value)
     * @param path   a TreePath object that identifies the path to the
     *               parent of the modified item(s)
     * @param childIndices an array of <code>int</code> that specifies the
     *               index values of the modified items
     * @param children an array of Object containing the inserted, removed, or
     *                 changed objects
     *
     * @see #TreeModelEvent(Object,Object[],int[],Object[])
     */
    public TreeModelEvent(Object source, TreePath path, int[] childIndices,
                          Object[] children)
    {
        super(source);
        this.path = path;
        this.childIndices = childIndices;
        this.children = children;
    }

    /**
     * Used to create an event when the node structure has changed in some way,
     * identifying the path to the root of a modified subtree as an array of
     * Objects. A structure change event might involve nodes swapping position,
     * for example, or it might encapsulate multiple inserts and deletes in the
     * subtree stemming from the node, where the changes may have taken place at
     * different levels of the subtree.
     * <blockquote>
     *   <b>Note:</b><br>
     *   JTree collapses all nodes under the specified node, so that only its
     *   immediate children are visible.
     * </blockquote>
     *
     * <p>
     *  用于在节点结构以某种方式更改时创建事件,将经修改的子树的根路径标识为对象数组。
     * 结构改变事件可能涉及节点交换位置,例如,或者它可以封装源自节点的子树中的多个插入和删除,其中改变可能发生在子树的不同级别。
     * <blockquote>
     *  <b>注意：</b> <br> JTree会折叠指定节点下的所有节点,以便只有其直接子项可见。
     * </blockquote>
     * 
     * 
     * @param source the Object responsible for generating the event (typically
     *               the creator of the event object passes <code>this</code>
     *               for its value)
     * @param path   an array of Object identifying the path to the root of the
     *               modified subtree, where the first element of the array is
     *               the object stored at the root node and the last element
     *               is the object stored at the changed node
     * @see TreePath
     */
    public TreeModelEvent(Object source, Object[] path)
    {
        this(source, (path == null) ? null : new TreePath(path));
    }

    /**
     * Used to create an event when the node structure has changed in some way,
     * identifying the path to the root of the modified subtree as a TreePath
     * object. For more information on this event specification, see
     * <code>TreeModelEvent(Object,Object[])</code>.
     *
     * <p>
     * 用于在节点结构以某种方式更改时创建事件,将修改的子树的根路径标识为TreePath对象。
     * 有关此事件规范的更多信息,请参阅<code> TreeModelEvent(Object,Object [])</code>。
     * 
     * 
     * @param source the Object responsible for generating the event (typically
     *               the creator of the event object passes <code>this</code>
     *               for its value)
     * @param path   a TreePath object that identifies the path to the
     *               change. In the DefaultTreeModel,
     *               this object contains an array of user-data objects,
     *               but a subclass of TreePath could use some totally
     *               different mechanism -- for example, a node ID number
     *
     * @see #TreeModelEvent(Object,Object[])
     */
    public TreeModelEvent(Object source, TreePath path)
    {
        super(source);
        this.path = path;
        this.childIndices = new int[0];
    }

    /**
     * For all events, except treeStructureChanged,
     * returns the parent of the changed nodes.
     * For treeStructureChanged events, returns the ancestor of the
     * structure that has changed. This and
     * <code>getChildIndices</code> are used to get a list of the effected
     * nodes.
     * <p>
     * The one exception to this is a treeNodesChanged event that is to
     * identify the root, in which case this will return the root
     * and <code>getChildIndices</code> will return null.
     *
     * <p>
     *  对于所有事件,除了treeStructureChanged,返回更改的节点的父级。对于treeStructureChanged事件,返回已更改的结构的祖先。
     * 这和<code> getChildIndices </code>用于获取受影响节点的列表。
     * <p>
     *  一个例外是一个用于标识根的treeNodesChanged事件,在这种情况下,这将返回根,并且<code> getChildIndices </code>将返回null。
     * 
     * 
     * @return the TreePath used in identifying the changed nodes.
     * @see TreePath#getLastPathComponent
     */
    public TreePath getTreePath() { return path; }

    /**
     * Convenience method to get the array of objects from the TreePath
     * instance that this event wraps.
     *
     * <p>
     *  方便方法从此事件包装的TreePath实例获取对象数组。
     * 
     * 
     * @return an array of Objects, where the first Object is the one
     *         stored at the root and the last object is the one
     *         stored at the node identified by the path
     */
    public Object[] getPath() {
        if(path != null)
            return path.getPath();
        return null;
    }

    /**
     * Returns the objects that are children of the node identified by
     * <code>getPath</code> at the locations specified by
     * <code>getChildIndices</code>. If this is a removal event the
     * returned objects are no longer children of the parent node.
     *
     * <p>
     *  返回由<code> getChildIndices </code>指定的位置处由<code> getPath </code>标识的节点的子节点的对象。
     * 如果这是一个删除事件,返回的对象不再是父节点的子节点。
     * 
     * 
     * @return an array of Object containing the children specified by
     *         the event
     * @see #getPath
     * @see #getChildIndices
     */
    public Object[] getChildren() {
        if(children != null) {
            int            cCount = children.length;
            Object[]       retChildren = new Object[cCount];

            System.arraycopy(children, 0, retChildren, 0, cCount);
            return retChildren;
        }
        return null;
    }

    /**
     * Returns the values of the child indexes. If this is a removal event
     * the indexes point to locations in the initial list where items
     * were removed. If it is an insert, the indices point to locations
     * in the final list where the items were added. For node changes,
     * the indices point to the locations of the modified nodes.
     *
     * <p>
     *  返回子索引的值。如果这是除去事件,则索引指向初始列表中已除去项的位置。如果是插入,则索引指向添加项目的最终列表中的位置。对于节点更改,索引指向修改节点的位置。
     * 
     * 
     * @return an array of <code>int</code> containing index locations for
     *         the children specified by the event
     */
    public int[] getChildIndices() {
        if(childIndices != null) {
            int            cCount = childIndices.length;
            int[]          retArray = new int[cCount];

            System.arraycopy(childIndices, 0, retArray, 0, cCount);
            return retArray;
        }
        return null;
    }

    /**
     * Returns a string that displays and identifies this object's
     * properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * @return a String representation of this object
     */
    public String toString() {
        StringBuffer   retBuffer = new StringBuffer();

        retBuffer.append(getClass().getName() + " " +
                         Integer.toString(hashCode()));
        if(path != null)
            retBuffer.append(" path " + path);
        if(childIndices != null) {
            retBuffer.append(" indices [ ");
            for(int counter = 0; counter < childIndices.length; counter++)
                retBuffer.append(Integer.toString(childIndices[counter])+ " ");
            retBuffer.append("]");
        }
        if(children != null) {
            retBuffer.append(" children [ ");
            for(int counter = 0; counter < children.length; counter++)
                retBuffer.append(children[counter] + " ");
            retBuffer.append("]");
        }
        return retBuffer.toString();
    }
}
