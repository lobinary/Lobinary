/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import javax.management.* ;



/**
 * This class is used for storing a pair (name, object) where name is
 * an object name and object is a reference to the object.
 *
 * <p>
 *  此类用于存储对(名称,对象),其中name是对象名称,object是对对象的引用。
 * 
 * 
 * @since 1.5
 */
public class NamedObject  {


    /**
     * Object name.
     * <p>
     *  对象名称。
     * 
     */
    private final ObjectName name;

    /**
     * Object reference.
     * <p>
     *  对象引用。
     * 
     */
    private final DynamicMBean object;


    /**
     * Allows a named object to be created.
     *
     * <p>
     *  允许创建命名对象。
     * 
     * 
     *@param objectName The object name of the object.
     *@param object A reference to the object.
     */
    public NamedObject(ObjectName objectName, DynamicMBean object)  {
        if (objectName.isPattern()) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Invalid name->"+ objectName.toString()));
        }
        this.name= objectName;
        this.object= object;
    }

    /**
     * Allows a named object to be created.
     *
     * <p>
     *  允许创建命名对象。
     * 
     * 
     *@param objectName The string representation of the object name of the object.
     *@param object A reference to the object.
     *
     *@exception MalformedObjectNameException The string passed does not have the format of a valid ObjectName
     */
    public NamedObject(String objectName, DynamicMBean object) throws MalformedObjectNameException{
        ObjectName objName= new ObjectName(objectName);
        if (objName.isPattern()) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Invalid name->"+ objName.toString()));
        }
        this.name= objName;
        this.object= object;
    }

    /**
     * Compares the current object name with another object name.
     *
     * <p>
     *  将当前对象名称与另一个对象名称进行比较。
     * 
     * 
     * @param object  The Named Object that the current object name is to be
     *        compared with.
     *
     * @return  True if the two named objects are equal, otherwise false.
     */
    public boolean equals(Object object)  {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof NamedObject)) return false;
        NamedObject no = (NamedObject) object;
        return name.equals(no.getName());
    }


    /**
     * Returns a hash code for this named object.
     *
     * <p>
     *  返回此命名对象的哈希码。
     * 
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Get the object name.
     * <p>
     *  获取对象名称。
     * 
     */
    public ObjectName getName()  {
        return name;
    }

    /**
     * Get the object
     * <p>
     *  获取对象
     */
    public DynamicMBean getObject()  {
        return object;
   }

 }
