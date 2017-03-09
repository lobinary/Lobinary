/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * File: ./org/omg/CORBA/DefinitionKind.java
 * From: ./ir.idl
 * Date: Fri Aug 28 16:03:31 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * <p>
 *  文件：./org/omg/CORBA/DefinitionKind.java从：./ir.idl日期：Fri Aug 28 16:03:31由By：idltojava Java IDL 1.2 Aug
 *  11 1998 02:00:18。
 * 
 */

package org.omg.CORBA;

/**
* The class that provides the constants used to identify the type of an
* Interface Repository object.  This class contains two kinds of constants,
* those that are an <code>int</code> and those that are an instance of the class
* <code>DefinitionKind</code>.  This class provides the method
* <code>from_int</code>, which given one
* of the <code>int</code> constants, creates the corresponding
* <code>DefinitionKind</code> instance.  It also provides the method
* <code>value</code>, which returns the <code>int</code> constant that
* is the value for a <code>DefinitionKind</code> instance.
*
* <p>
*  提供用于标识Interface Repository对象类型的常量的类。
* 这个类包含两种常量,即<code> int </code>和那些<code> DefinitionKind </code>类的实例。
* 这个类提供了方法<code> from_int </code>,它给出了<code> int </code>常量之一,创建了相应的<code> DefinitionKind </code>实例。
* 它还提供方法<code> value </code>,它返回<code> int </code>常量,它是<code> DefinitionKind </code>实例的值。
* 
* 
* @see IRObject
*/

public class DefinitionKind implements org.omg.CORBA.portable.IDLEntity {

/**
 * The constant that indicates that an Interface Repository object
 * does not have a definition kind.
 * <p>
 *  指示Interface Repository对象没有定义种类的常量。
 * 
 */
        public static final int _dk_none = 0,

/**
 * The constant that indicates that the type of an Interface Repository object
 * may be any type.
 * <p>
 *  指示Interface Repository对象的类型可以是任何类型的常量。
 * 
 */
        _dk_all = 1,

/**
 * The constant that indicates that an Interface Repository object is an
 * attribute.
 * <p>
 *  指示Interface Repository对象是属性的常量。
 * 
 */
        _dk_Attribute = 2,

/**
 * The constant that indicates that an Interface Repository object is a
 * constant.
 * <p>
 *  指示Interface Repository对象是常量的常量。
 * 
 */
        _dk_Constant = 3,

/**
 * The constant that indicates that an Interface Repository object is an
 * exception.
 * <p>
 *  指示Interface Repository对象是异常的常量。
 * 
 */

        _dk_Exception = 4,

/**
 * The constant that indicates that an Interface Repository object is an
 * interface.
 * <p>
 *  指示Interface Repository对象是接口的常量。
 * 
 */

        _dk_Interface = 5,

/**
 * The constant that indicates that an Interface Repository object is a
 * module.
 * <p>
 *  指示Interface Repository对象是模块的常量。
 * 
 */

        _dk_Module = 6,

/**
 * The constant that indicates that an Interface Repository object is an
 * operation.
 * <p>
 * 指示Interface Repository对象是操作的常量。
 * 
 */

        _dk_Operation = 7,

/**
 * The constant that indicates that an Interface Repository object is a
 * Typedef.
 * <p>
 *  指示Interface Repository对象是Typedef的常量。
 * 
 */

        _dk_Typedef = 8,

/**
 * The constant that indicates that an Interface Repository object is an
 * Alias.
 * <p>
 *  指示Interface Repository对象是别名的常量。
 * 
 */

        _dk_Alias = 9,

/**
 * The constant that indicates that an Interface Repository object is a
 * Struct.
 * <p>
 *  指示Interface Repository对象是Struct的常量。
 * 
 */

        _dk_Struct = 10,

/**
 * The constant that indicates that an Interface Repository object is a
 * Union.
 * <p>
 *  指示Interface Repository对象是联合的常量。
 * 
 */

        _dk_Union = 11,

/**
 * The constant that indicates that an Interface Repository object is an
 * Enum.
 * <p>
 *  指示Interface Repository对象是枚举的常量。
 * 
 */

        _dk_Enum = 12,

/**
 * The constant that indicates that an Interface Repository object is a
 * Primitive.
 * <p>
 *  指示Interface Repository对象是基元的常量。
 * 
 */

        _dk_Primitive = 13,

/**
 * The constant that indicates that an Interface Repository object is a
 * String.
 * <p>
 *  指示Interface Repository对象是String的常量。
 * 
 */

        _dk_String = 14,

/**
 * The constant that indicates that an Interface Repository object is a
 * Sequence.
 * <p>
 *  指示Interface Repository对象是序列的常量。
 * 
 */

        _dk_Sequence = 15,

/**
 * The constant that indicates that an Interface Repository object is an
 * Array.
 * <p>
 *  指示Interface Repository对象是数组的常量。
 * 
 */

        _dk_Array = 16,

/**
 * The constant that indicates that an Interface Repository object is a
 * Repository.
 * <p>
 *  指示Interface Repository对象是存储库的常量。
 * 
 */

        _dk_Repository = 17,

/**
 * The constant that indicates that an Interface Repository object is a
 * Wstring.
 * <p>
 *  指示Interface Repository对象是Wstring的常量。
 * 
 */

        _dk_Wstring = 18,

/**
 * The constant that indicates that an Interface Repository object is of type
 * Fixed.
 * <p>
 *  指示Interface Repository对象类型为Fixed的常量。
 * 
 */

        _dk_Fixed = 19,

/**
 * The constant that indicates that an Interface Repository object is a
 * Value.
 * <p>
 *  指示Interface Repository对象是值的常量。
 * 
 */

        _dk_Value = 20,

/**
 * The constant that indicates that an Interface Repository object is a
 * ValueBox.
 * <p>
 *  指示Interface Repository对象是ValueBox的常量。
 * 
 */

        _dk_ValueBox = 21,

/**
 * The constant that indicates that an Interface Repository object is a
 * ValueMember.
 * <p>
 *  指示Interface Repository对象是ValueMember的常量。
 * 
 */

        _dk_ValueMember = 22,

/**
 * The constant that indicates that an Interface Repository object is of type
 * Native.
 * <p>
 *  指示Interface Repository对象的类型为Native的常量。
 * 
 */

        _dk_Native = 23,

/**
 * The constant that indicates that an Interface Repository object
 * is representing an abstract interface.
 * <p>
 *  指示Interface Repository对象表示抽象接口的常量。
 * 
 */
        _dk_AbstractInterface = 24;

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object has no definition kind.
 * <p>
 * <code> DefinitionKind </code>的静态实例表示Interface Repository对象没有定义种类。
 * 
 */

    public static final DefinitionKind dk_none = new DefinitionKind(_dk_none);

     /**
         * The wildcard <code>DefinitionKind</code> constant, useful
         * in all occasions where any
     * <code>DefinitionKind</code> is appropriate. The Container's
         * <code>contents</code> method
     * makes use of this constant to return all contained definitions of any kind.
     * <p>
     *  通配符<code> DefinitionKind </code>常量,在任何<code> DefinitionKind </code>适用的所有场合都有用。
     * 容器的<code> contents </code>方法利用这个常量返回所有包含的任何类型的定义。
     * 
         */

    public static final DefinitionKind dk_all = new DefinitionKind(_dk_all);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Attribute.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是属性。
 * 
 */

    public static final DefinitionKind dk_Attribute = new DefinitionKind(_dk_Attribute);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a constant.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是常量。
 * 
 */

    public static final DefinitionKind dk_Constant = new DefinitionKind(_dk_Constant);


/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Exception.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个异常。
 * 
 */

    public static final DefinitionKind dk_Exception = new DefinitionKind(_dk_Exception);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Interface.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个接口。
 * 
 */

    public static final DefinitionKind dk_Interface = new DefinitionKind(_dk_Interface);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Module.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是一个模块。
 * 
 */

    public static final DefinitionKind dk_Module = new DefinitionKind(_dk_Module);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Operation.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个操作。
 * 
 */

    public static final DefinitionKind dk_Operation = new DefinitionKind(_dk_Operation);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Typedef.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是Typedef。
 * 
 */

    public static final DefinitionKind dk_Typedef = new DefinitionKind(_dk_Typedef);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Alias.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是别名。
 * 
 */

    public static final DefinitionKind dk_Alias = new DefinitionKind(_dk_Alias);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Struct.
 * <p>
 *  <code> DefinitionKind </code>的静态实例,表示Interface Repository对象是一个Struct。
 * 
 */

    public static final DefinitionKind dk_Struct = new DefinitionKind(_dk_Struct);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Union.
 * <p>
 * <code> DefinitionKind </code>的静态实例,表示Interface Repository对象是联盟。
 * 
 */

    public static final DefinitionKind dk_Union = new DefinitionKind(_dk_Union);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Enum.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是枚举。
 * 
 */

    public static final DefinitionKind dk_Enum = new DefinitionKind(_dk_Enum);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Primitive.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是基元。
 * 
 */

    public static final DefinitionKind dk_Primitive = new DefinitionKind(_dk_Primitive);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a String.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是String。
 * 
 */

    public static final DefinitionKind dk_String = new DefinitionKind(_dk_String);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Sequence.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个序列。
 * 
 */

    public static final DefinitionKind dk_Sequence = new DefinitionKind(_dk_Sequence);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is an Array.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个数组。
 * 
 */

    public static final DefinitionKind dk_Array = new DefinitionKind(_dk_Array);


/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Repository.
 * <p>
 *  <code> DefinitionKind </code>的静态实例指示Interface Repository对象是存储库。
 * 
 */

    public static final DefinitionKind dk_Repository = new DefinitionKind(_dk_Repository);


/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Wstring.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是Wstring。
 * 
 */

    public static final DefinitionKind dk_Wstring = new DefinitionKind(_dk_Wstring);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Fixed value.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个Fixed值。
 * 
 */

    public static final DefinitionKind dk_Fixed = new DefinitionKind(_dk_Fixed);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Value.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是一个值。
 * 
 */

    public static final DefinitionKind dk_Value = new DefinitionKind(_dk_Value);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a ValueBox.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是ValueBox。
 * 
 */

    public static final DefinitionKind dk_ValueBox = new DefinitionKind(_dk_ValueBox);

/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a ValueMember.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是ValueMember。
 * 
 */

    public static final DefinitionKind dk_ValueMember = new DefinitionKind(_dk_ValueMember);


/**
 * The static instance of <code>DefinitionKind</code> indicating that an
 * Interface Repository object is a Native value.
 * <p>
 *  <code> DefinitionKind </code>的静态实例表示Interface Repository对象是Native值。
 * 
 */

    public static final DefinitionKind dk_Native = new DefinitionKind(_dk_Native);


/**
* The static instance of <code>DefinitionKind</code> indicating that an
* Interface Repository object represents an abstract interface.
* <p>
* <code> DefinitionKind </code>的静态实例表示Interface Repository对象表示抽象接口。
* 
*/
    public static final DefinitionKind dk_AbstractInterface = new DefinitionKind(_dk_AbstractInterface);


     /**
     * Returns the <code>int</code> constant identifying the type of an IR object.
     * <p>
     *  返回标识IR对象类型的<code> int </code>常量。
     * 
     * 
     * @return the <code>int</code> constant from the class
         * <code>DefinitionKind</code> that is the value of this
         * <code>DefinitionKind</code> instance
     */

    public int value() {
        return _value;
    }


     /**
     * Creates a <code>DefinitionKind</code> instance corresponding to the given code
.
     * <p>
     *  创建与给定代码对应的<code> DefinitionKind </code>实例。
     * 
     * 
     * @param i one of the <code>int</code> constants from the class
         * <code>DefinitionKind</code>
         * @return the <code>DefinitionKind</code> instance corresponding
         *         to the given code
         * @throws org.omg.CORBA.BAD_PARAM if the given parameter is not
 one
         *         of the <code>int</code> constants from the class
         *         <code>DefinitionKind</code>
     */

    public static DefinitionKind from_int(int i) {
        switch (i) {
        case _dk_none:
            return dk_none;
        case _dk_all:
            return dk_all;
        case _dk_Attribute:
            return dk_Attribute;
        case _dk_Constant:
            return dk_Constant;
        case _dk_Exception:
            return dk_Exception;
        case _dk_Interface:
            return dk_Interface;
        case _dk_Module:
            return dk_Module;
        case _dk_Operation:
            return dk_Operation;
        case _dk_Typedef:
            return dk_Typedef;
        case _dk_Alias:
            return dk_Alias;
        case _dk_Struct:
            return dk_Struct;
        case _dk_Union:
            return dk_Union;
        case _dk_Enum:
            return dk_Enum;
        case _dk_Primitive:
            return dk_Primitive;
        case _dk_String:
            return dk_String;
        case _dk_Sequence:
            return dk_Sequence;
        case _dk_Array:
            return dk_Array;
        case _dk_Repository:
            return dk_Repository;
        case _dk_Wstring:
            return dk_Wstring;
        case _dk_Fixed:
            return dk_Fixed;
        case _dk_Value:
            return dk_Value;
        case _dk_ValueBox:
            return dk_ValueBox;
        case _dk_ValueMember:
            return dk_ValueMember;
        case _dk_Native:
            return dk_Native;
        default:
            throw new org.omg.CORBA.BAD_PARAM();
        }
    }

   /**
    * Constructs a <code>DefinitionKind</code> object with its <code>_value</code>
    * field initialized with the given value.
    * <p>
    *  构造一个<code> DefinitionKind </code>对象,其<code> _value </code>字段使用给定值初始化。
    * 
    * 
    * @param _value one of the <code>int</code> constants defined in the
    *                   class <code>DefinitionKind</code>
    */

    protected DefinitionKind(int _value){
        this._value = _value;
    }

     /**
      * The field that holds a value for a <code>DefinitionKind</code> object.
      * <p>
      *  保存<code> DefinitionKind </code>对象的值的字段。
      * 
      * @serial
      */

    private int _value;
}
