/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.undo;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * <P>StateEdit is a general edit for objects that change state.
 * Objects being edited must conform to the StateEditable interface.</P>
 *
 * <P>This edit class works by asking an object to store it's state in
 * Hashtables before and after editing occurs.  Upon undo or redo the
 * object is told to restore it's state from these Hashtables.</P>
 *
 * A state edit is used as follows:
 * <PRE>
 *      // Create the edit during the "before" state of the object
 *      StateEdit newEdit = new StateEdit(myObject);
 *      // Modify the object
 *      myObject.someStateModifyingMethod();
 *      // "end" the edit when you are done modifying the object
 *      newEdit.end();
 * </PRE>
 *
 * <P><EM>Note that when a StateEdit ends, it removes redundant state from
 * the Hashtables - A state Hashtable is not guaranteed to contain all
 * keys/values placed into it when the state is stored!</EM></P>
 *
 * <p>
 *  <P> StateEdit是对改变状态的对象的一般编辑。正在编辑的对象必须符合StateEditable接口。</P>
 * 
 *  <P>此编辑类通过请求对象在编辑之前和之后将其状态存储在Hashtables中来工作。撤消或重做后,将通知对象从这些Hashtables恢复其状态。</P>
 * 
 *  状态编辑使用如下：
 * <PRE>
 *  //在对象的"之前"状态创建编辑StateEdit newEdit = new StateEdit(myObject); //修改对象myObject.someStateModifyingMethod
 * (); //"end"编辑完成后修改对象newEdit.end();。
 * </PRE>
 * 
 *  注意,当StateEdit结束时,它从Hashtables中删除冗余状态 - 当状态被存储时,状态Hashtable不能保证包含所有的键/值！</EM> </P>
 * 
 * 
 * @see StateEditable
 *
 * @author Ray Ryan
 */

public class StateEdit
        extends AbstractUndoableEdit {

    protected static final String RCSID = "$Id: StateEdit.java,v 1.6 1997/10/01 20:05:51 sandipc Exp $";

    //
    // Attributes
    //

    /**
     * The object being edited
     * <p>
     *  正在编辑的对象
     * 
     */
    protected StateEditable object;

    /**
     * The state information prior to the edit
     * <p>
     *  编辑前的状态信息
     * 
     */
    protected Hashtable<Object,Object> preState;

    /**
     * The state information after the edit
     * <p>
     *  编辑后的状态信息
     * 
     */
    protected Hashtable<Object,Object> postState;

    /**
     * The undo/redo presentation name
     * <p>
     *  撤消/重做演示文稿名称
     * 
     */
    protected String undoRedoName;

    //
    // Constructors
    //

    /**
     * Create and return a new StateEdit.
     *
     * <p>
     *  创建并返回一个新的StateEdit。
     * 
     * 
     * @param anObject The object to watch for changing state
     *
     * @see StateEdit
     */
    public StateEdit(StateEditable anObject) {
        super();
        init (anObject,null);
    }

    /**
     * Create and return a new StateEdit with a presentation name.
     *
     * <p>
     *  创建并返回一个带有演示文稿名称的新StateEdit。
     * 
     * 
     * @param anObject The object to watch for changing state
     * @param name The presentation name to be used for this edit
     *
     * @see StateEdit
     */
    public StateEdit(StateEditable anObject, String name) {
        super();
        init (anObject,name);
    }

    protected void init (StateEditable anObject, String name) {
        this.object = anObject;
        this.preState = new Hashtable<Object, Object>(11);
        this.object.storeState(this.preState);
        this.postState = null;
        this.undoRedoName = name;
    }


    //
    // Operation
    //


    /**
     * Gets the post-edit state of the StateEditable object and
     * ends the edit.
     * <p>
     *  获取StateEditable对象的编辑后状态并结束编辑。
     * 
     */
    public void end() {
        this.postState = new Hashtable<Object, Object>(11);
        this.object.storeState(this.postState);
        this.removeRedundantState();
    }

    /**
     * Tells the edited object to apply the state prior to the edit
     * <p>
     *  告诉编辑对象在编辑之前应用状态
     * 
     */
    public void undo() {
        super.undo();
        this.object.restoreState(preState);
    }

    /**
     * Tells the edited object to apply the state after the edit
     * <p>
     *  告诉编辑对象在编辑后应用状态
     * 
     */
    public void redo() {
        super.redo();
        this.object.restoreState(postState);
    }

    /**
     * Gets the presentation name for this edit
     * <p>
     *  获取此编辑的演示文稿名称
     * 
     */
    public String getPresentationName() {
        return this.undoRedoName;
    }


    //
    // Internal support
    //

    /**
     * Remove redundant key/values in state hashtables.
     * <p>
     *  删除状态哈希表中的冗余键/值。
     */
    protected void removeRedundantState() {
        Vector<Object> uselessKeys = new Vector<Object>();
        Enumeration myKeys = preState.keys();

        // Locate redundant state
        while (myKeys.hasMoreElements()) {
            Object myKey = myKeys.nextElement();
            if (postState.containsKey(myKey) &&
                postState.get(myKey).equals(preState.get(myKey))) {
                uselessKeys.addElement(myKey);
            }
        }

        // Remove redundant state
        for (int i = uselessKeys.size()-1; i >= 0; i--) {
            Object myKey = uselessKeys.elementAt(i);
            preState.remove(myKey);
            postState.remove(myKey);
        }
    }

} // End of class StateEdit
