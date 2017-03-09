/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.datatransfer;

import java.awt.EventQueue;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import java.io.IOException;

import sun.awt.EventListenerAggregate;

/**
 * A class that implements a mechanism to transfer data using
 * cut/copy/paste operations.
 * <p>
 * {@link FlavorListener}s may be registered on an instance of the
 * Clipboard class to be notified about changes to the set of
 * {@link DataFlavor}s available on this clipboard (see
 * {@link #addFlavorListener}).
 *
 * <p>
 *  实现使用剪切/复制/粘贴操作传输数据的机制的类。
 * <p>
 *  {@link FlavorListener}可以在Clipboard类的实例上注册,以通知有关此剪贴板上可用的{@link DataFlavor}集合的更改(请参阅{@link #addFlavorListener}
 * )。
 * 
 * 
 * @see java.awt.Toolkit#getSystemClipboard
 * @see java.awt.Toolkit#getSystemSelection
 *
 * @author      Amy Fowler
 * @author      Alexander Gerasimov
 */
public class Clipboard {

    String name;

    protected ClipboardOwner owner;
    protected Transferable contents;

    /**
     * An aggregate of flavor listeners registered on this local clipboard.
     *
     * <p>
     *  在本地剪贴板上注册的风味侦听器的聚合。
     * 
     * 
     * @since 1.5
     */
    private EventListenerAggregate flavorListeners;

    /**
     * A set of <code>DataFlavor</code>s that is available on
     * this local clipboard. It is used for tracking changes
     * of <code>DataFlavor</code>s available on this clipboard.
     *
     * <p>
     *  在本地剪贴板上提供的一组<code> DataFlavor </code>。它用于跟踪此剪贴板上可用的<code> DataFlavor </code>的更改。
     * 
     * 
     * @since 1.5
     */
    private Set<DataFlavor> currentDataFlavors;

    /**
     * Creates a clipboard object.
     *
     * <p>
     *  创建剪贴板对象。
     * 
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     */
    public Clipboard(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this clipboard object.
     *
     * <p>
     *  返回此剪贴板对象的名称。
     * 
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the current contents of the clipboard to the specified
     * transferable object and registers the specified clipboard owner
     * as the owner of the new contents.
     * <p>
     * If there is an existing owner different from the argument
     * <code>owner</code>, that owner is notified that it no longer
     * holds ownership of the clipboard contents via an invocation
     * of <code>ClipboardOwner.lostOwnership()</code> on that owner.
     * An implementation of <code>setContents()</code> is free not
     * to invoke <code>lostOwnership()</code> directly from this method.
     * For example, <code>lostOwnership()</code> may be invoked later on
     * a different thread. The same applies to <code>FlavorListener</code>s
     * registered on this clipboard.
     * <p>
     * The method throws <code>IllegalStateException</code> if the clipboard
     * is currently unavailable. For example, on some platforms, the system
     * clipboard is unavailable while it is accessed by another application.
     *
     * <p>
     *  将剪贴板的当前内容设置为指定的可传输对象,并将指定的剪贴板所有者注册为新内容的所有者。
     * <p>
     *  如果存在与<code> owner </code>参数不同的现有所有者,则通知该所有者它不再通过调用<code> ClipboardOwner.lostOwnership()</code>来拥有剪贴板
     * 内容的所有权那个老板。
     *  <code> setContents()</code>的实现可以不直接从这个方法调用<code> lostOwnership()</code>。
     * 例如,<code> lostOwnership()</code>可以稍后在不同的线程上调用。这同样适用于在此剪贴板上注册的<code> FlavorListener </code>。
     * <p>
     * 如果剪贴板当前不可用,该方法会抛出<code> IllegalStateException </code>。例如,在某些平台上,系统剪贴板在由另一个应用程序访问时不可用。
     * 
     * 
     * @param contents the transferable object representing the
     *                 clipboard content
     * @param owner the object which owns the clipboard content
     * @throws IllegalStateException if the clipboard is currently unavailable
     * @see java.awt.Toolkit#getSystemClipboard
     */
    public synchronized void setContents(Transferable contents, ClipboardOwner owner) {
        final ClipboardOwner oldOwner = this.owner;
        final Transferable oldContents = this.contents;

        this.owner = owner;
        this.contents = contents;

        if (oldOwner != null && oldOwner != owner) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    oldOwner.lostOwnership(Clipboard.this, oldContents);
                }
            });
        }
        fireFlavorsChanged();
    }

    /**
     * Returns a transferable object representing the current contents
     * of the clipboard.  If the clipboard currently has no contents,
     * it returns <code>null</code>. The parameter Object requestor is
     * not currently used.  The method throws
     * <code>IllegalStateException</code> if the clipboard is currently
     * unavailable.  For example, on some platforms, the system clipboard is
     * unavailable while it is accessed by another application.
     *
     * <p>
     *  返回表示剪贴板当前内容的可传输对象。如果剪贴板当前没有内容,则返回<code> null </code>。参数Object请求器当前未使用。
     * 如果剪贴板当前不可用,该方法会抛出<code> IllegalStateException </code>。例如,在某些平台上,系统剪贴板在由另一个应用程序访问时不可用。
     * 
     * 
     * @param requestor the object requesting the clip data  (not used)
     * @return the current transferable object on the clipboard
     * @throws IllegalStateException if the clipboard is currently unavailable
     * @see java.awt.Toolkit#getSystemClipboard
     */
    public synchronized Transferable getContents(Object requestor) {
        return contents;
    }


    /**
     * Returns an array of <code>DataFlavor</code>s in which the current
     * contents of this clipboard can be provided. If there are no
     * <code>DataFlavor</code>s available, this method returns a zero-length
     * array.
     *
     * <p>
     *  返回一个<code> DataFlavor </code>数组,其中可以提供此剪贴板的当前内容。如果没有可用的<code> DataFlavor </code>,此方法返回零长度数组。
     * 
     * 
     * @return an array of <code>DataFlavor</code>s in which the current
     *         contents of this clipboard can be provided
     *
     * @throws IllegalStateException if this clipboard is currently unavailable
     *
     * @since 1.5
     */
    public DataFlavor[] getAvailableDataFlavors() {
        Transferable cntnts = getContents(null);
        if (cntnts == null) {
            return new DataFlavor[0];
        }
        return cntnts.getTransferDataFlavors();
    }

    /**
     * Returns whether or not the current contents of this clipboard can be
     * provided in the specified <code>DataFlavor</code>.
     *
     * <p>
     *  返回是否可以在指定的<code> DataFlavor </code>中提供此剪贴板的当前内容。
     * 
     * 
     * @param flavor the requested <code>DataFlavor</code> for the contents
     *
     * @return <code>true</code> if the current contents of this clipboard
     *         can be provided in the specified <code>DataFlavor</code>;
     *         <code>false</code> otherwise
     *
     * @throws NullPointerException if <code>flavor</code> is <code>null</code>
     * @throws IllegalStateException if this clipboard is currently unavailable
     *
     * @since 1.5
     */
    public boolean isDataFlavorAvailable(DataFlavor flavor) {
        if (flavor == null) {
            throw new NullPointerException("flavor");
        }

        Transferable cntnts = getContents(null);
        if (cntnts == null) {
            return false;
        }
        return cntnts.isDataFlavorSupported(flavor);
    }

    /**
     * Returns an object representing the current contents of this clipboard
     * in the specified <code>DataFlavor</code>.
     * The class of the object returned is defined by the representation
     * class of <code>flavor</code>.
     *
     * <p>
     *  在指定的<code> DataFlavor </code>中返回表示此剪贴板的当前内容的对象。返回的对象的类由<code> flavor </code>的表示类定义。
     * 
     * 
     * @param flavor the requested <code>DataFlavor</code> for the contents
     *
     * @return an object representing the current contents of this clipboard
     *         in the specified <code>DataFlavor</code>
     *
     * @throws NullPointerException if <code>flavor</code> is <code>null</code>
     * @throws IllegalStateException if this clipboard is currently unavailable
     * @throws UnsupportedFlavorException if the requested <code>DataFlavor</code>
     *         is not available
     * @throws IOException if the data in the requested <code>DataFlavor</code>
     *         can not be retrieved
     *
     * @see DataFlavor#getRepresentationClass
     *
     * @since 1.5
     */
    public Object getData(DataFlavor flavor)
        throws UnsupportedFlavorException, IOException {
        if (flavor == null) {
            throw new NullPointerException("flavor");
        }

        Transferable cntnts = getContents(null);
        if (cntnts == null) {
            throw new UnsupportedFlavorException(flavor);
        }
        return cntnts.getTransferData(flavor);
    }


    /**
     * Registers the specified <code>FlavorListener</code> to receive
     * <code>FlavorEvent</code>s from this clipboard.
     * If <code>listener</code> is <code>null</code>, no exception
     * is thrown and no action is performed.
     *
     * <p>
     *  注册指定的<code> FlavorListener </code>以从剪贴板接收<code> FlavorEvent </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不执行任何操作。
     * 
     * 
     * @param listener the listener to be added
     *
     * @see #removeFlavorListener
     * @see #getFlavorListeners
     * @see FlavorListener
     * @see FlavorEvent
     * @since 1.5
     */
    public synchronized void addFlavorListener(FlavorListener listener) {
        if (listener == null) {
            return;
        }
        if (flavorListeners == null) {
            currentDataFlavors = getAvailableDataFlavorSet();
            flavorListeners = new EventListenerAggregate(FlavorListener.class);
        }
        flavorListeners.add(listener);
    }

    /**
     * Removes the specified <code>FlavorListener</code> so that it no longer
     * receives <code>FlavorEvent</code>s from this <code>Clipboard</code>.
     * This method performs no function, nor does it throw an exception, if
     * the listener specified by the argument was not previously added to this
     * <code>Clipboard</code>.
     * If <code>listener</code> is <code>null</code>, no exception
     * is thrown and no action is performed.
     *
     * <p>
     * 删除指定的<code> FlavorListener </code>,使其不再从<code> Clipboard </code>接收<code> FlavorEvent </code>。
     * 如果参数指定的侦听器以前未添加到此<code> Clipboard </code>中,此方法不执行任何函数,也不会抛出异常。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不执行任何操作。
     * 
     * 
     * @param listener the listener to be removed
     *
     * @see #addFlavorListener
     * @see #getFlavorListeners
     * @see FlavorListener
     * @see FlavorEvent
     * @since 1.5
     */
    public synchronized void removeFlavorListener(FlavorListener listener) {
        if (listener == null || flavorListeners == null) {
            return;
        }
        flavorListeners.remove(listener);
    }

    /**
     * Returns an array of all the <code>FlavorListener</code>s currently
     * registered on this <code>Clipboard</code>.
     *
     * <p>
     *  返回当前在此<code>剪贴板</code>上注册的所有<code> FlavorListener </code>数组。
     * 
     * 
     * @return all of this clipboard's <code>FlavorListener</code>s or an empty
     *         array if no listeners are currently registered
     * @see #addFlavorListener
     * @see #removeFlavorListener
     * @see FlavorListener
     * @see FlavorEvent
     * @since 1.5
     */
    public synchronized FlavorListener[] getFlavorListeners() {
        return flavorListeners == null ? new FlavorListener[0] :
                (FlavorListener[])flavorListeners.getListenersCopy();
    }

    /**
     * Checks change of the <code>DataFlavor</code>s and, if necessary,
     * notifies all listeners that have registered interest for notification
     * on <code>FlavorEvent</code>s.
     *
     * <p>
     *  检查<code> DataFlavor </code>的更改,并在必要时通知所有已在<code> FlavorEvent </code>上注册了通知的收件人。
     * 
     * 
     * @since 1.5
     */
    private void fireFlavorsChanged() {
        if (flavorListeners == null) {
            return;
        }
        Set<DataFlavor> prevDataFlavors = currentDataFlavors;
        currentDataFlavors = getAvailableDataFlavorSet();
        if (prevDataFlavors.equals(currentDataFlavors)) {
            return;
        }
        FlavorListener[] flavorListenerArray =
                (FlavorListener[])flavorListeners.getListenersInternal();
        for (int i = 0; i < flavorListenerArray.length; i++) {
            final FlavorListener listener = flavorListenerArray[i];
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    listener.flavorsChanged(new FlavorEvent(Clipboard.this));
                }
            });
        }
    }

    /**
     * Returns a set of <code>DataFlavor</code>s currently available
     * on this clipboard.
     *
     * <p>
     *  返回此剪贴板上当前可用的一组<code> DataFlavor </code>。
     * 
     * @return a set of <code>DataFlavor</code>s currently available
     *         on this clipboard
     *
     * @since 1.5
     */
    private Set<DataFlavor> getAvailableDataFlavorSet() {
        Set<DataFlavor> set = new HashSet<>();
        Transferable contents = getContents(null);
        if (contents != null) {
            DataFlavor[] flavors = contents.getTransferDataFlavors();
            if (flavors != null) {
                set.addAll(Arrays.asList(flavors));
            }
        }
        return set;
    }
}
