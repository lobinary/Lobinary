/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.org.apache.xerces.internal.util;

//java imports
import java.util.Iterator ;
import java.util.NoSuchElementException;

//xerces imports
import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl ;

/**
 *
 * <p>
 * 
 * @author  Neeraj Bajaj, Sun Microsystems
 */

/**
 * Its better to extend the functionality of existing XMLAttributesImpl and also make it of type Iterator.
 * We can directly  give an object of type iterator from StartElement event. We should also have
 * Attribute object of type javax.xml.stream.Attribute internally. It would avoid the need of creating
 * new javax.xml.stream.Attribute object at the later stage.
 *
 * Should we change XMLAttributes interface to implement Iteraotr ? I think its better avoid touching XNI as
 * much as possible. - NB.
 * <p>
 *  它更好地扩展现有XMLAttributesImpl的功能,并使其成为类型Iterator。我们可以直接从StartElement事件给一个类型迭代器的对象。
 * 我们还应该在内部具有类型为javax.xml.stream.Attribute的Attribute对象。这将避免在后期创建新的javax.xml.stream.Attribute对象的需要。
 * 
 *  我们应该改变XMLAttributes接口来实现Iteraotr吗?我认为它更好地避免触及XNI尽可能多。 -  NB。
 * 
 */

public class XMLAttributesIteratorImpl extends XMLAttributesImpl implements Iterator {

    //pointer to current position.
    protected int fCurrent = 0 ;

    protected XMLAttributesImpl.Attribute fLastReturnedItem ;

    /** Creates a new instance of XMLAttributesIteratorImpl */
    public XMLAttributesIteratorImpl() {
    }

    public boolean hasNext() {
        return fCurrent < getLength() ? true : false ;
    }//hasNext()

    public Object next() {
        if(hasNext()){
            // should this be of type javax.xml.stream.Attribute ?
            return fLastReturnedItem = fAttributes[fCurrent++] ;
        }
        else{
            throw new NoSuchElementException() ;
        }
    }//next

    public void remove() {
        //make sure that only last returned item can be removed.
        if(fLastReturnedItem == fAttributes[fCurrent - 1]){
            //remove the attribute at current index and lower the current position by 1.
            removeAttributeAt(fCurrent--) ;
        }
        else {
            //either the next method has been called yet, or the remove method has already been called
            //after the last call to the next method.
            throw new IllegalStateException();
        }
    }//remove

    public void removeAllAttributes() {
        super.removeAllAttributes() ;
        fCurrent = 0 ;
    }
    /** xxx: should we be doing this way ? Attribute event defines so many functions which doesn't make any sense
     *for Attribute.
     *
     * <p>
     *  或属性。
     * 
     */
    /*
    class AttributeImpl extends com.sun.org.apache.xerces.internal.util.XMLAttributesImpl.Attribute implements javax.xml.stream.events.Attribute{

    }
    /* <p>
    /*  class AttributeImpl extends com.sun.org.apache.xerces.internal.util.XMLAttributesImpl.Attribute impl
    /* ements javax.xml.stream.events.Attribute {。
    /* 
     */

} //XMLAttributesIteratorImpl
