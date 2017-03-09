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
package javax.swing.text;

import java.util.Vector;
import java.io.Serializable;
import javax.swing.undo.*;
import javax.swing.SwingUtilities;

/**
 * An implementation of the AbstractDocument.Content interface that is
 * a brute force implementation that is useful for relatively small
 * documents and/or debugging.  It manages the character content
 * as a simple character array.  It is also quite inefficient.
 * <p>
 * It is generally recommended that the gap buffer or piece table
 * implementations be used instead.  This buffer does not scale up
 * to large sizes.
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
 *  AbstractDocument.Content接口的实现,它是一个强力实现,对于相对较小的文档和/或调试非常有用。它将字符内容作为一个简单的字符数组来管理。这也是相当低效率。
 * <p>
 *  通常建议使用间隙缓冲区或片表实现。此缓冲区不会扩展到大型。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Timothy Prinzing
 */
public final class StringContent implements AbstractDocument.Content, Serializable {

    /**
     * Creates a new StringContent object.  Initial size defaults to 10.
     * <p>
     *  创建一个新的StringContent对象。初始大小默认为10。
     * 
     */
    public StringContent() {
        this(10);
    }

    /**
     * Creates a new StringContent object, with the initial
     * size specified.  If the length is &lt; 1, a size of 1 is used.
     *
     * <p>
     *  创建一个新的StringContent对象,指定初始大小。如果长度< 1,使用1的大小。
     * 
     * 
     * @param initialLength the initial size
     */
    public StringContent(int initialLength) {
        if (initialLength < 1) {
            initialLength = 1;
        }
        data = new char[initialLength];
        data[0] = '\n';
        count = 1;
    }

    /**
     * Returns the length of the content.
     *
     * <p>
     *  返回内容的长度。
     * 
     * 
     * @return the length &gt;= 1
     * @see AbstractDocument.Content#length
     */
    public int length() {
        return count;
    }

    /**
     * Inserts a string into the content.
     *
     * <p>
     *  在内容中插入字符串。
     * 
     * 
     * @param where the starting position &gt;= 0 &amp;&amp; &lt; length()
     * @param str the non-null string to insert
     * @return an UndoableEdit object for undoing
     * @exception BadLocationException if the specified position is invalid
     * @see AbstractDocument.Content#insertString
     */
    public UndoableEdit insertString(int where, String str) throws BadLocationException {
        if (where >= count || where < 0) {
            throw new BadLocationException("Invalid location", count);
        }
        char[] chars = str.toCharArray();
        replace(where, 0, chars, 0, chars.length);
        if (marks != null) {
            updateMarksForInsert(where, str.length());
        }
        return new InsertUndo(where, str.length());
    }

    /**
     * Removes part of the content.  where + nitems must be &lt; length().
     *
     * <p>
     *  删除部分内容。其中+长度()。
     * 
     * 
     * @param where the starting position &gt;= 0
     * @param nitems the number of characters to remove &gt;= 0
     * @return an UndoableEdit object for undoing
     * @exception BadLocationException if the specified position is invalid
     * @see AbstractDocument.Content#remove
     */
    public UndoableEdit remove(int where, int nitems) throws BadLocationException {
        if (where + nitems >= count) {
            throw new BadLocationException("Invalid range", count);
        }
        String removedString = getString(where, nitems);
        UndoableEdit edit = new RemoveUndo(where, removedString);
        replace(where, nitems, empty, 0, 0);
        if (marks != null) {
            updateMarksForRemove(where, nitems);
        }
        return edit;

    }

    /**
     * Retrieves a portion of the content.  where + len must be &lt;= length().
     *
     * <p>
     *  检索内容的一部分。其中+ len必须<= length()。
     * 
     * 
     * @param where the starting position &gt;= 0
     * @param len the length to retrieve &gt;= 0
     * @return a string representing the content; may be empty
     * @exception BadLocationException if the specified position is invalid
     * @see AbstractDocument.Content#getString
     */
    public String getString(int where, int len) throws BadLocationException {
        if (where + len > count) {
            throw new BadLocationException("Invalid range", count);
        }
        return new String(data, where, len);
    }

    /**
     * Retrieves a portion of the content.  where + len must be &lt;= length()
     *
     * <p>
     *  检索内容的一部分。其中+ len必须<= length()
     * 
     * 
     * @param where the starting position &gt;= 0
     * @param len the number of characters to retrieve &gt;= 0
     * @param chars the Segment object to return the characters in
     * @exception BadLocationException if the specified position is invalid
     * @see AbstractDocument.Content#getChars
     */
    public void getChars(int where, int len, Segment chars) throws BadLocationException {
        if (where + len > count) {
            throw new BadLocationException("Invalid location", count);
        }
        chars.array = data;
        chars.offset = where;
        chars.count = len;
    }

    /**
     * Creates a position within the content that will
     * track change as the content is mutated.
     *
     * <p>
     * 在内容中创建一个位置,以便在内容发生改变时跟踪更改。
     * 
     * 
     * @param offset the offset to create a position for &gt;= 0
     * @return the position
     * @exception BadLocationException if the specified position is invalid
     */
    public Position createPosition(int offset) throws BadLocationException {
        // some small documents won't have any sticky positions
        // at all, so the buffer is created lazily.
        if (marks == null) {
            marks = new Vector<PosRec>();
        }
        return new StickyPosition(offset);
    }

    // --- local methods ---------------------------------------

    /**
     * Replaces some of the characters in the array
     * <p>
     *  替换数组中的一些字符
     * 
     * 
     * @param offset  offset into the array to start the replace
     * @param length  number of characters to remove
     * @param replArray replacement array
     * @param replOffset offset into the replacement array
     * @param replLength number of character to use from the
     *   replacement array.
     */
    void replace(int offset, int length,
                 char[] replArray, int replOffset, int replLength) {
        int delta = replLength - length;
        int src = offset + length;
        int nmove = count - src;
        int dest = src + delta;
        if ((count + delta) >= data.length) {
            // need to grow the array
            int newLength = Math.max(2*data.length, count + delta);
            char[] newData = new char[newLength];
            System.arraycopy(data, 0, newData, 0, offset);
            System.arraycopy(replArray, replOffset, newData, offset, replLength);
            System.arraycopy(data, src, newData, dest, nmove);
            data = newData;
        } else {
            // patch the existing array
            System.arraycopy(data, src, data, dest, nmove);
            System.arraycopy(replArray, replOffset, data, offset, replLength);
        }
        count = count + delta;
    }

    void resize(int ncount) {
        char[] ndata = new char[ncount];
        System.arraycopy(data, 0, ndata, 0, Math.min(ncount, count));
        data = ndata;
    }

    synchronized void updateMarksForInsert(int offset, int length) {
        if (offset == 0) {
            // zero is a special case where we update only
            // marks after it.
            offset = 1;
        }
        int n = marks.size();
        for (int i = 0; i < n; i++) {
            PosRec mark = marks.elementAt(i);
            if (mark.unused) {
                // this record is no longer used, get rid of it
                marks.removeElementAt(i);
                i -= 1;
                n -= 1;
            } else if (mark.offset >= offset) {
                mark.offset += length;
            }
        }
    }

    synchronized void updateMarksForRemove(int offset, int length) {
        int n = marks.size();
        for (int i = 0; i < n; i++) {
            PosRec mark = marks.elementAt(i);
            if (mark.unused) {
                // this record is no longer used, get rid of it
                marks.removeElementAt(i);
                i -= 1;
                n -= 1;
            } else if (mark.offset >= (offset + length)) {
                mark.offset -= length;
            } else if (mark.offset >= offset) {
                mark.offset = offset;
            }
        }
    }

    /**
     * Returns a Vector containing instances of UndoPosRef for the
     * Positions in the range
     * <code>offset</code> to <code>offset</code> + <code>length</code>.
     * If <code>v</code> is not null the matching Positions are placed in
     * there. The vector with the resulting Positions are returned.
     * <p>
     * This is meant for internal usage, and is generally not of interest
     * to subclasses.
     *
     * <p>
     *  返回包含范围<code> offset </code>到<code> offset </code> + <code> length </code>中的位置的UndoPosRef实例的向量。
     * 如果<code> v </code>不为空,则匹配的位置被放置在其中。返回具有所得到的位置的向量。
     * <p>
     *  这是为了内部使用,并且通常不是子类感兴趣。
     * 
     * 
     * @param v the Vector to use, with a new one created on null
     * @param offset the starting offset &gt;= 0
     * @param length the length &gt;= 0
     * @return the set of instances
     */
    protected Vector getPositionsInRange(Vector v, int offset,
                                                      int length) {
        int n = marks.size();
        int end = offset + length;
        Vector placeIn = (v == null) ? new Vector() : v;
        for (int i = 0; i < n; i++) {
            PosRec mark = marks.elementAt(i);
            if (mark.unused) {
                // this record is no longer used, get rid of it
                marks.removeElementAt(i);
                i -= 1;
                n -= 1;
            } else if(mark.offset >= offset && mark.offset <= end)
                placeIn.addElement(new UndoPosRef(mark));
        }
        return placeIn;
    }

    /**
     * Resets the location for all the UndoPosRef instances
     * in <code>positions</code>.
     * <p>
     * This is meant for internal usage, and is generally not of interest
     * to subclasses.
     *
     * <p>
     *  重置<code>位置</code>中所有UndoPosRef实例的位置。
     * <p>
     *  这是为了内部使用,并且通常不是子类感兴趣。
     * 
     * 
     * @param positions the positions of the instances
     */
    protected void updateUndoPositions(Vector positions) {
        for(int counter = positions.size() - 1; counter >= 0; counter--) {
            UndoPosRef ref = (UndoPosRef)positions.elementAt(counter);
            // Check if the Position is still valid.
            if(ref.rec.unused) {
                positions.removeElementAt(counter);
            }
            else
                ref.resetLocation();
        }
    }

    private static final char[] empty = new char[0];
    private char[] data;
    private int count;
    transient Vector<PosRec> marks;

    /**
     * holds the data for a mark... separately from
     * the real mark so that the real mark can be
     * collected if there are no more references to
     * it.... the update table holds only a reference
     * to this grungy thing.
     * <p>
     *  保持标记的数据...与真正的标记分开,使得如果没有更多的引用则可以收集真正的标记....更新表仅保持对该球衣事物的引用。
     * 
     */
    final class PosRec {

        PosRec(int offset) {
            this.offset = offset;
        }

        int offset;
        boolean unused;
    }

    /**
     * This really wants to be a weak reference but
     * in 1.1 we don't have a 100% pure solution for
     * this... so this class trys to hack a solution
     * to causing the marks to be collected.
     * <p>
     *  这真的想成为一个弱的参考,但在1.1我们没有一个100％的纯解决方案为此...所以这个类trys来破解一个解决方案,导致标记被收集。
     * 
     */
    final class StickyPosition implements Position {

        StickyPosition(int offset) {
            rec = new PosRec(offset);
            marks.addElement(rec);
        }

        public int getOffset() {
            return rec.offset;
        }

        protected void finalize() throws Throwable {
            // schedule the record to be removed later
            // on another thread.
            rec.unused = true;
        }

        public String toString() {
            return Integer.toString(getOffset());
        }

        PosRec rec;
    }

    /**
     * Used to hold a reference to a Position that is being reset as the
     * result of removing from the content.
     * <p>
     *  用于保存对从内容中删除的结果重置的位置的引用。
     * 
     */
    final class UndoPosRef {
        UndoPosRef(PosRec rec) {
            this.rec = rec;
            this.undoLocation = rec.offset;
        }

        /**
         * Resets the location of the Position to the offset when the
         * receiver was instantiated.
         * <p>
         *  将接收器实例化时,将Position的位置重置为偏移。
         * 
         */
        protected void resetLocation() {
            rec.offset = undoLocation;
        }

        /** Location to reset to when resetLocatino is invoked. */
        protected int undoLocation;
        /** Position to reset offset. */
        protected PosRec rec;
    }

    /**
     * UnoableEdit created for inserts.
     * <p>
     *  为插入创建UnoableEdit。
     * 
     */
    class InsertUndo extends AbstractUndoableEdit {
        protected InsertUndo(int offset, int length) {
            super();
            this.offset = offset;
            this.length = length;
        }

        public void undo() throws CannotUndoException {
            super.undo();
            try {
                synchronized(StringContent.this) {
                    // Get the Positions in the range being removed.
                    if(marks != null)
                        posRefs = getPositionsInRange(null, offset, length);
                    string = getString(offset, length);
                    remove(offset, length);
                }
            } catch (BadLocationException bl) {
              throw new CannotUndoException();
            }
        }

        public void redo() throws CannotRedoException {
            super.redo();
            try {
                synchronized(StringContent.this) {
                    insertString(offset, string);
                    string = null;
                    // Update the Positions that were in the range removed.
                    if(posRefs != null) {
                        updateUndoPositions(posRefs);
                        posRefs = null;
                    }
              }
            } catch (BadLocationException bl) {
              throw new CannotRedoException();
            }
        }

        // Where the string goes.
        protected int offset;
        // Length of the string.
        protected int length;
        // The string that was inserted. To cut down on space needed this
        // will only be valid after an undo.
        protected String string;
        // An array of instances of UndoPosRef for the Positions in the
        // range that was removed, valid after undo.
        protected Vector posRefs;
    }


    /**
     * UndoableEdit created for removes.
     * <p>
     *  为删除创建了UndoableEdit。
     */
    class RemoveUndo extends AbstractUndoableEdit {
        protected RemoveUndo(int offset, String string) {
            super();
            this.offset = offset;
            this.string = string;
            this.length = string.length();
            if(marks != null)
                posRefs = getPositionsInRange(null, offset, length);
        }

        public void undo() throws CannotUndoException {
            super.undo();
            try {
                synchronized(StringContent.this) {
                    insertString(offset, string);
                    // Update the Positions that were in the range removed.
                    if(posRefs != null) {
                        updateUndoPositions(posRefs);
                        posRefs = null;
                    }
                    string = null;
                }
            } catch (BadLocationException bl) {
              throw new CannotUndoException();
            }
        }

        public void redo() throws CannotRedoException {
            super.redo();
            try {
                synchronized(StringContent.this) {
                    string = getString(offset, length);
                    // Get the Positions in the range being removed.
                    if(marks != null)
                        posRefs = getPositionsInRange(null, offset, length);
                    remove(offset, length);
                }
            } catch (BadLocationException bl) {
              throw new CannotRedoException();
            }
        }

        // Where the string goes.
        protected int offset;
        // Length of the string.
        protected int length;
        // The string that was inserted. This will be null after an undo.
        protected String string;
        // An array of instances of UndoPosRef for the Positions in the
        // range that was removed, valid before undo.
        protected Vector posRefs;
    }
}
