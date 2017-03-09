/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd;

import java.awt.Component;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.awt.dnd.peer.DropTargetContextPeer;

import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.List;


/**
 * A <code>DropTargetContext</code> is created
 * whenever the logical cursor associated
 * with a Drag and Drop operation coincides with the visible geometry of
 * a <code>Component</code> associated with a <code>DropTarget</code>.
 * The <code>DropTargetContext</code> provides
 * the mechanism for a potential receiver
 * of a drop operation to both provide the end user with the appropriate
 * drag under feedback, but also to effect the subsequent data transfer
 * if appropriate.
 *
 * <p>
 *  只要与拖放操作相关联的逻辑光标与与<code> DropTarget </code>相关联的<code> Component </code>的可见几何重合,就会创建<code> DropTargetC
 * ontext </code>。
 *  <code> DropTargetContext </code>为放置操作的潜在接收器提供了用于在反馈下向最终用户提供适当的拖拽的机制,而且如果适当的话还实现后续数据传输。
 * 
 * 
 * @since 1.2
 */

public class DropTargetContext implements Serializable {

    private static final long serialVersionUID = -634158968993743371L;

    /**
     * Construct a <code>DropTargetContext</code>
     * given a specified <code>DropTarget</code>.
     * <P>
     * <p>
     *  构造一个指定<code> DropTarget </code>的<code> DropTargetContext </code>。
     * <P>
     * 
     * @param dt the DropTarget to associate with
     */

    DropTargetContext(DropTarget dt) {
        super();

        dropTarget = dt;
    }

    /**
     * This method returns the <code>DropTarget</code> associated with this
     * <code>DropTargetContext</code>.
     * <P>
     * <p>
     *  此方法返回与此<code> DropTargetContext </code>关联的<code> DropTarget </code>。
     * <P>
     * 
     * @return the <code>DropTarget</code> associated with this <code>DropTargetContext</code>
     */

    public DropTarget getDropTarget() { return dropTarget; }

    /**
     * This method returns the <code>Component</code> associated with
     * this <code>DropTargetContext</code>.
     * <P>
     * <p>
     *  此方法返回与此<code> DropTargetContext </code>关联的<code> Component </code>。
     * <P>
     * 
     * @return the Component associated with this Context
     */

    public Component getComponent() { return dropTarget.getComponent(); }

    /**
     * Called when associated with the <code>DropTargetContextPeer</code>.
     * <P>
     * <p>
     *  当与<code> DropTargetContextPeer </code>关联时调用。
     * <P>
     * 
     * @param dtcp the <code>DropTargetContextPeer</code>
     */

    public void addNotify(DropTargetContextPeer dtcp) {
        dropTargetContextPeer = dtcp;
    }

    /**
     * Called when disassociated with the <code>DropTargetContextPeer</code>.
     * <p>
     *  当与<code> DropTargetContextPeer </code>取消关联时调用。
     * 
     */

    public void removeNotify() {
        dropTargetContextPeer = null;
        transferable          = null;
    }

    /**
     * This method sets the current actions acceptable to
     * this <code>DropTarget</code>.
     * <P>
     * <p>
     *  此方法设置此<code> DropTarget </code>可接受的当前操作。
     * <P>
     * 
     * @param actions an <code>int</code> representing the supported action(s)
     */

    protected void setTargetActions(int actions) {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            synchronized (peer) {
                peer.setTargetActions(actions);
                getDropTarget().doSetDefaultActions(actions);
            }
        } else {
            getDropTarget().doSetDefaultActions(actions);
        }
    }

    /**
     * This method returns an <code>int</code> representing the
     * current actions this <code>DropTarget</code> will accept.
     * <P>
     * <p>
     *  此方法返回代表此<code> DropTarget </code>将接受的当前操作的<code> int </code>。
     * <P>
     * 
     * @return the current actions acceptable to this <code>DropTarget</code>
     */

    protected int getTargetActions() {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        return ((peer != null)
                        ? peer.getTargetActions()
                        : dropTarget.getDefaultActions()
        );
    }

    /**
     * This method signals that the drop is completed and
     * if it was successful or not.
     * <P>
     * <p>
     *  该方法表示丢弃完成并且如果它成功或不成功。
     * <P>
     * 
     * @param success true for success, false if not
     * <P>
     * @throws InvalidDnDOperationException if a drop is not outstanding/extant
     */

    public void dropComplete(boolean success) throws InvalidDnDOperationException{
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            peer.dropComplete(success);
        }
    }

    /**
     * accept the Drag.
     * <P>
     * <p>
     *  接受拖动。
     * <P>
     * 
     * @param dragOperation the supported action(s)
     */

    protected void acceptDrag(int dragOperation) {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            peer.acceptDrag(dragOperation);
        }
    }

    /**
     * reject the Drag.
     * <p>
     *  拒绝拖动。
     * 
     */

    protected void rejectDrag() {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            peer.rejectDrag();
        }
    }

    /**
     * called to signal that the drop is acceptable
     * using the specified operation.
     * must be called during DropTargetListener.drop method invocation.
     * <P>
     * <p>
     *  调用以使用指定的操作来通知可以接受下降。必须在DropTargetListener.drop方法调用期间调用。
     * <P>
     * 
     * @param dropOperation the supported action(s)
     */

    protected void acceptDrop(int dropOperation) {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            peer.acceptDrop(dropOperation);
        }
    }

    /**
     * called to signal that the drop is unacceptable.
     * must be called during DropTargetListener.drop method invocation.
     * <p>
     * 被称为表示该下降是不可接受的。必须在DropTargetListener.drop方法调用期间调用。
     * 
     */

    protected void rejectDrop() {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer != null) {
            peer.rejectDrop();
        }
    }

    /**
     * get the available DataFlavors of the
     * <code>Transferable</code> operand of this operation.
     * <P>
     * <p>
     *  获取此操作的<code> Transferable </code>操作数的可用DataFlavors。
     * <P>
     * 
     * @return a <code>DataFlavor[]</code> containing the
     * supported <code>DataFlavor</code>s of the
     * <code>Transferable</code> operand.
     */

    protected DataFlavor[] getCurrentDataFlavors() {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        return peer != null ? peer.getTransferDataFlavors() : new DataFlavor[0];
    }

    /**
     * This method returns a the currently available DataFlavors
     * of the <code>Transferable</code> operand
     * as a <code>java.util.List</code>.
     * <P>
     * <p>
     *  此方法将<code> Transferable </code>操作数的当前可用的DataFlavors作为<code> java.util.List </code>返回。
     * <P>
     * 
     * @return the currently available
     * DataFlavors as a <code>java.util.List</code>
     */

    protected List<DataFlavor> getCurrentDataFlavorsAsList() {
        return Arrays.asList(getCurrentDataFlavors());
    }

    /**
     * This method returns a <code>boolean</code>
     * indicating if the given <code>DataFlavor</code> is
     * supported by this <code>DropTargetContext</code>.
     * <P>
     * <p>
     *  此方法返回<code> boolean </code>,指示此<code> DropTargetContext </code>是否支持给定的<code> DataFlavor </code>。
     * <P>
     * 
     * @param df the <code>DataFlavor</code>
     * <P>
     * @return if the <code>DataFlavor</code> specified is supported
     */

    protected boolean isDataFlavorSupported(DataFlavor df) {
        return getCurrentDataFlavorsAsList().contains(df);
    }

    /**
     * get the Transferable (proxy) operand of this operation
     * <P>
     * <p>
     *  获取此操作的可传递(代理)操作数
     * <P>
     * 
     * @throws InvalidDnDOperationException if a drag is not outstanding/extant
     * <P>
     * @return the <code>Transferable</code>
     */

    protected Transferable getTransferable() throws InvalidDnDOperationException {
        DropTargetContextPeer peer = getDropTargetContextPeer();
        if (peer == null) {
            throw new InvalidDnDOperationException();
        } else {
            if (transferable == null) {
                Transferable t = peer.getTransferable();
                boolean isLocal = peer.isTransferableJVMLocal();
                synchronized (this) {
                    if (transferable == null) {
                        transferable = createTransferableProxy(t, isLocal);
                    }
                }
            }

            return transferable;
        }
    }

    /**
     * Get the <code>DropTargetContextPeer</code>
     * <P>
     * <p>
     *  获取<code> DropTargetContextPeer </code>
     * <P>
     * 
     * @return the platform peer
     */

    DropTargetContextPeer getDropTargetContextPeer() {
        return dropTargetContextPeer;
    }

    /**
     * Creates a TransferableProxy to proxy for the specified
     * Transferable.
     *
     * <p>
     *  为指定的Transferable创建一个TransferableProxy代理。
     * 
     * 
     * @param t the <tt>Transferable</tt> to be proxied
     * @param local <tt>true</tt> if <tt>t</tt> represents
     *        the result of a local drag-n-drop operation.
     * @return the new <tt>TransferableProxy</tt> instance.
     */
    protected Transferable createTransferableProxy(Transferable t, boolean local) {
        return new TransferableProxy(t, local);
    }

/****************************************************************************/


    /**
     * <code>TransferableProxy</code> is a helper inner class that implements
     * <code>Transferable</code> interface and serves as a proxy for another
     * <code>Transferable</code> object which represents data transfer for
     * a particular drag-n-drop operation.
     * <p>
     * The proxy forwards all requests to the encapsulated transferable
     * and automatically performs additional conversion on the data
     * returned by the encapsulated transferable in case of local transfer.
     * <p>
     *  <code> TransferableProxy </code>是一个帮助器内部类,实现<code> Transferable </code>接口,并用作另一个<code> Transferable 
     * </code>对象的代理,代表特定drag-n滴操作。
     * <p>
     *  代理将所有请求转发到封装的可转移对象,并在本地传递的情况下自动对由封装的可转移对象返回的数据执行附加转换。
     * 
     */

    protected class TransferableProxy implements Transferable {

        /**
         * Constructs a <code>TransferableProxy</code> given
         * a specified <code>Transferable</code> object representing
         * data transfer for a particular drag-n-drop operation and
         * a <code>boolean</code> which indicates whether the
         * drag-n-drop operation is local (within the same JVM).
         * <p>
         * <p>
         *  给定表示用于特定拖放操作的数据传输的指定的<code>可传输</code>对象和<code>布尔</code>构造<code> TransferableProxy </code> n-drop操作是本
         * 地的(在同一个JVM内)。
         * <p>
         * 
         * @param t the <code>Transferable</code> object
         * @param local <code>true</code>, if <code>t</code> represents
         *        the result of local drag-n-drop operation
         */
        TransferableProxy(Transferable t, boolean local) {
            proxy = new sun.awt.datatransfer.TransferableProxy(t, local);
            transferable = t;
            isLocal      = local;
        }

        /**
         * Returns an array of DataFlavor objects indicating the flavors
         * the data can be provided in by the encapsulated transferable.
         * <p>
         * <p>
         *  返回一个DataFlavor对象的数组,指示数据可以由封装的可转换对象提供的风格。
         * <p>
         * 
         * @return an array of data flavors in which the data can be
         *         provided by the encapsulated transferable
         */
        public DataFlavor[] getTransferDataFlavors() {
            return proxy.getTransferDataFlavors();
        }

        /**
         * Returns whether or not the specified data flavor is supported by
         * the encapsulated transferable.
         * <p>
         * 返回封装的可转移对象是否支持指定的数据flavor。
         * 
         * 
         * @param flavor the requested flavor for the data
         * @return <code>true</code> if the data flavor is supported,
         *         <code>false</code> otherwise
         */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return proxy.isDataFlavorSupported(flavor);
        }

        /**
         * Returns an object which represents the data provided by
         * the encapsulated transferable for the requested data flavor.
         * <p>
         * In case of local transfer a serialized copy of the object
         * returned by the encapsulated transferable is provided when
         * the data is requested in application/x-java-serialized-object
         * data flavor.
         *
         * <p>
         *  返回一个对象,该对象表示由请求的数据风格的封装的可转换对象提供的数据。
         * <p>
         *  在本地传输的情况下,当在application / x-java-序列化对象数据风格中请求数据时,提供由封装的可转换对象返回的对象的序列化副本。
         * 
         * 
         * @param df the requested flavor for the data
         * @throws IOException if the data is no longer available
         *              in the requested flavor.
         * @throws UnsupportedFlavorException if the requested data flavor is
         *              not supported.
         */
        public Object getTransferData(DataFlavor df)
            throws UnsupportedFlavorException, IOException
        {
            return proxy.getTransferData(df);
        }

        /*
         * fields
         * <p>
         *  字段
         * 
         */

        // We don't need to worry about client code changing the values of
        // these variables. Since TransferableProxy is a protected class, only
        // subclasses of DropTargetContext can access it. And DropTargetContext
        // cannot be subclassed by client code because it does not have a
        // public constructor.

        /**
         * The encapsulated <code>Transferable</code> object.
         * <p>
         *  封装的<code> Transferable </code>对象。
         * 
         */
        protected Transferable  transferable;

        /**
         * A <code>boolean</code> indicating if the encapsulated
         * <code>Transferable</code> object represents the result
         * of local drag-n-drop operation (within the same JVM).
         * <p>
         *  指示所封装的<code> Transferable </code>对象是否表示本地拖放操作(在同一JVM内)的结果的<code> boolean </code>。
         * 
         */
        protected boolean       isLocal;

        private sun.awt.datatransfer.TransferableProxy proxy;
    }

/****************************************************************************/

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * The DropTarget associated with this DropTargetContext.
     *
     * <p>
     *  与此DropTargetContext相关联的DropTarget。
     * 
     * @serial
     */
    private DropTarget dropTarget;

    private transient DropTargetContextPeer dropTargetContextPeer;

    private transient Transferable transferable;
}
