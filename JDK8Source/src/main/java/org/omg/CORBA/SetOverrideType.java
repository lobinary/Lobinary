/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2010, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * The mapping of a CORBA <code>enum</code> tagging
 * <code>SET_OVERRIDE</code> and <code>ADD_OVERRIDE</code>, which
 * indicate whether policies should replace the
 * existing policies of an <code>Object</code> or be added to them.
 * <P>
 * The method {@link org.omg.CORBA.Object#_set_policy_override} takes
 * either <code>SetOverrideType.SET_OVERRIDE</code> or
 * <code>SetOverrideType.ADD_OVERRIDE</code> as its second argument.
 * The method <code>_set_policy_override</code>
 * creates a new <code>Object</code> initialized with the
 * <code>Policy</code> objects supplied as the first argument.  If the
 * second argument is <code>ADD_OVERRIDE</code>, the new policies
 * are added to those of the <code>Object</code> instance that is
 * calling the <code>_set_policy_override</code> method.  If
 * <code>SET_OVERRIDE</code> is given instead, the existing policies
 * are replaced with the given ones.
 *
 * <p>
 *  CORBA <code>枚举</code>标记<code> SET_OVERRIDE </code>和<code> ADD_OVERRIDE </code>的映射,指示策略是否应替换<code> Ob
 * ject </code >或添加到它们。
 * <P>
 *  方法{@link org.omg.CORBA.Object#_set_policy_override}采用<code> SetOverrideType.SET_OVERRIDE </code>或<code>
 *  SetOverrideType.ADD_OVERRIDE </code>作为其第二个参数。
 * 方法<code> _set_policy_override </code>创建一个新的<code> Object </code>,它以作为第一个参数提供的<code> Policy </code>对象初
 * 始化。
 * 如果第二个参数是<code> ADD_OVERRIDE </code>,则会将新策略添加到调用<code> _set_policy_override </code>方法的<code> Object </code>
 * 实例中。
 * 如果给出了<code> SET_OVERRIDE </code>,则现有策略将替换为给定的策略。
 * 
 * 
 * @author OMG
 * @since   JDK1.2
 */

public class SetOverrideType implements org.omg.CORBA.portable.IDLEntity {

    /**
     * The <code>int</code> constant for the enum value SET_OVERRIDE.
     * <p>
     *  枚举值SET_OVERRIDE的<code> int </code>常量。
     * 
     */
    public static final int _SET_OVERRIDE = 0;

    /**
     * The <code>int</code> constant for the enum value ADD_OVERRIDE.
     * <p>
     *  枚举值ADD_OVERRIDE的<code> int </code>常量。
     * 
     */
    public static final int _ADD_OVERRIDE = 1;

    /**
     * The <code>SetOverrideType</code> constant for the enum value SET_OVERRIDE.
     * <p>
     *  枚举值SET_OVERRIDE的<code> SetOverrideType </code>常量。
     * 
     */
    public static final SetOverrideType SET_OVERRIDE = new SetOverrideType(_SET_OVERRIDE);

    /**
     * The <code>SetOverrideType</code> constant for the enum value ADD_OVERRIDE.
     * <p>
     *  枚举值ADD_OVERRIDE的<code> SetOverrideType </code>常量。
     * 
     */
    public static final SetOverrideType ADD_OVERRIDE = new SetOverrideType(_ADD_OVERRIDE);

    /**
     * Retrieves the value of this <code>SetOverrideType</code> instance.
     *
     * <p>
     *  检索此<code> SetOverrideType </code>实例的值。
     * 
     * 
     * @return  the <code>int</code> for this <code>SetOverrideType</code> instance.
     */
    public int value() {
        return _value;
    }

    /**
     * Converts the given <code>int</code> to the corresponding
     * <code>SetOverrideType</code> instance.
     *
     * <p>
     *  将给定的<code> int </code>转换为相应的<code> SetOverrideType </code>实例。
     * 
     * 
     * @param  i the <code>int</code> to convert; must be either
     *         <code>SetOverrideType._SET_OVERRIDE</code> or
     *         <code>SetOverrideType._ADD_OVERRIDE</code>
     * @return  the <code>SetOverrideType</code> instance whose value
     *       matches the given <code>int</code>
     * @exception  BAD_PARAM  if the given <code>int</code> does not
     *       match the value of
     *       any <code>SetOverrideType</code> instance
     */
    public static SetOverrideType from_int(int i)
    {
        switch (i) {
        case _SET_OVERRIDE:
            return SET_OVERRIDE;
        case _ADD_OVERRIDE:
            return ADD_OVERRIDE;
        default:
            throw new org.omg.CORBA.BAD_PARAM();
        }
    }

    /**
     * Constructs a <code>SetOverrideType</code> instance from an
     * <code>int</code>.
     * <p>
     * 从<code> int </code>构造<code> SetOverrideType </code>实例。
     * 
     * 
     * @param _value must be either <code>SET_OVERRIDE</code> or
     *        <code>ADD_OVERRIDE</code>
     */
    protected SetOverrideType(int _value){
        this._value = _value;
    }

    /**
     * The field containing the value for this <code>SetOverrideType</code>
     * object.
     *
     * <p>
     *  包含此<code> SetOverrideType </code>对象的值的字段。
     */
    private int _value;
}
