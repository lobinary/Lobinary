/***** Lobxxx Translate Finished ******/
/*
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
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * When associating an object to a key on a node using
 * <code>Node.setUserData()</code> the application can provide a handler
 * that gets called when the node the object is associated to is being
 * cloned, imported, or renamed. This can be used by the application to
 * implement various behaviors regarding the data it associates to the DOM
 * nodes. This interface defines that handler.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  当使用<code> Node.setUserData()</code>将对象关联到节点上的键时,应用程序可以提供一个处理程序,当与该对象相关联的节点正在被克隆,导入或重命名时,该处理程序将被调用。
 * 这可以由应用程序用于实现关于其关联到DOM节点的数据的各种行为。此接口定义处理程序。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 * 
 * @since DOM Level 3
 */
public interface UserDataHandler {
    // OperationType
    /**
     * The node is cloned, using <code>Node.cloneNode()</code>.
     * <p>
     *  使用<code> Node.cloneNode()</code>克隆该节点。
     * 
     */
    public static final short NODE_CLONED               = 1;
    /**
     * The node is imported, using <code>Document.importNode()</code>.
     * <p>
     *  使用<code> Document.importNode()</code>导入节点。
     * 
     */
    public static final short NODE_IMPORTED             = 2;
    /**
     * The node is deleted.
     * <p ><b>Note:</b> This may not be supported or may not be reliable in
     * certain environments, such as Java, where the implementation has no
     * real control over when objects are actually deleted.
     * <p>
     *  节点被删除。 <p> <b>注意：</b>这可能不受支持,或者在某些环境(如Java)中可能不可靠,其中实施对实际删除对象时没有实际控制。
     * 
     */
    public static final short NODE_DELETED              = 3;
    /**
     * The node is renamed, using <code>Document.renameNode()</code>.
     * <p>
     *  使用<code> Document.renameNode()</code>重命名该节点。
     * 
     */
    public static final short NODE_RENAMED              = 4;
    /**
     * The node is adopted, using <code>Document.adoptNode()</code>.
     * <p>
     * 采用节点,使用<code> Document.adoptNode()</code>。
     * 
     */
    public static final short NODE_ADOPTED              = 5;

    /**
     * This method is called whenever the node for which this handler is
     * registered is imported or cloned.
     * <br> DOM applications must not raise exceptions in a
     * <code>UserDataHandler</code>. The effect of throwing exceptions from
     * the handler is DOM implementation dependent.
     * <p>
     *  每当导入或克隆此处理程序注册的节点时,将调用此方法。 <br> DOM应用程序不得在<code> UserDataHandler </code>中引发异常。从处理程序抛出异常的效果是DOM实现依赖。
     * 
     * @param operation Specifies the type of operation that is being
     *   performed on the node.
     * @param key Specifies the key for which this handler is being called.
     * @param data Specifies the data for which this handler is being called.
     * @param src Specifies the node being cloned, adopted, imported, or
     *   renamed. This is <code>null</code> when the node is being deleted.
     * @param dst Specifies the node newly created if any, or
     *   <code>null</code>.
     */
    public void handle(short operation,
                       String key,
                       Object data,
                       Node src,
                       Node dst);

}
