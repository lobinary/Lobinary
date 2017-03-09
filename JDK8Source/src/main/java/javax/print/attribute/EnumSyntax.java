/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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


package javax.print.attribute;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Class EnumSyntax is an abstract base class providing the common
 * implementation of all "type safe enumeration" objects. An enumeration class
 * (which extends class EnumSyntax) provides a group of enumeration values
 * (objects) that are singleton instances of the enumeration class; for example:
 * <PRE>
 *     public class Bach extends EnumSyntax {
 *         public static final Bach JOHANN_SEBASTIAN     = new Bach(0);
 *         public static final Bach WILHELM_FRIEDEMANN   = new Bach(1);
 *         public static final Bach CARL_PHILIP_EMMANUEL = new Bach(2);
 *         public static final Bach JOHANN_CHRISTIAN     = new Bach(3);
 *         public static final Bach P_D_Q                = new Bach(4);
 *
 *         private static final String[] stringTable = {
 *             "Johann Sebastian Bach",
 *              "Wilhelm Friedemann Bach",
 *              "Carl Philip Emmanuel Bach",
 *              "Johann Christian Bach",
 *              "P.D.Q. Bach"
 *         };
 *
 *         protected String[] getStringTable() {
 *             return stringTable;
 *         }
 *
 *         private static final Bach[] enumValueTable = {
 *             JOHANN_SEBASTIAN,
 *              WILHELM_FRIEDEMANN,
 *              CARL_PHILIP_EMMANUEL,
 *              JOHANN_CHRISTIAN,
 *              P_D_Q
 *         };
 *
 *         protected EnumSyntax[] getEnumValueTable() {
 *             return enumValueTable;
 *         }
 *     }
 * </PRE>
 * You can then write code that uses the <CODE>==</CODE> and <CODE>!=</CODE>
 * operators to test enumeration values; for example:
 * <PRE>
 *     Bach theComposer;
 *     . . .
 *     if (theComposer == Bach.JOHANN_SEBASTIAN) {
 *         System.out.println ("The greatest composer of all time!");
 *     }
 * </PRE>
 * The <CODE>equals()</CODE> method for an enumeration class just does a test
 * for identical objects (<CODE>==</CODE>).
 * <P>
 * You can convert an enumeration value to a string by calling {@link
 * #toString() toString()}. The string is obtained from a table
 * supplied by the enumeration class.
 * <P>
 * Under the hood, an enumeration value is just an integer, a different integer
 * for each enumeration value within an enumeration class. You can get an
 * enumeration value's integer value by calling {@link #getValue()
 * getValue()}. An enumeration value's integer value is established
 * when it is constructed (see {@link #EnumSyntax(int)
 * EnumSyntax(int)}). Since the constructor is protected, the only
 * possible enumeration values are the singleton objects declared in the
 * enumeration class; additional enumeration values cannot be created at run
 * time.
 * <P>
 * You can define a subclass of an enumeration class that extends it with
 * additional enumeration values. The subclass's enumeration values' integer
 * values need not be distinct from the superclass's enumeration values' integer
 * values; the <CODE>==</CODE>, <CODE>!=</CODE>, <CODE>equals()</CODE>, and
 * <CODE>toString()</CODE> methods will still work properly even if the subclass
 * uses some of the same integer values as the superclass. However, the
 * application in which the enumeration class and subclass are used may need to
 * have distinct integer values in the superclass and subclass.
 * <P>
 *
 * <p>
 *  类EnumSyntax是一个抽象基类,提供所有"类型安全枚举"对象的常见实现。枚举类(扩展类EnumSyntax)提供了一组枚举值(对象),这些枚举值是枚举类的单例实例;例如：
 * <PRE>
 *  public class Bach extends EnumSyntax {public static final Bach JOHANN_SEBASTIAN = new Bach(0); public static final Bach WILHELM_FRIEDEMANN = new Bach(1); public static final Bach CARL_PHILIP_EMMANUEL = new Bach(2); public static final Bach JOHANN_CHRISTIAN = new Bach(3); public static final Bach P_D_Q = new Bach(4);。
 * 
 *  private static final String [] stringTable = {"Johann Sebastian Bach","Wilhelm Friedemann Bach","Carl Philip Emmanuel Bach","Johann Christian Bach","P.D.Q. Bach"}
 * ;。
 * 
 *  protected String [] getStringTable(){return stringTable; }}
 * 
 *  private static final Bach [] enumValueTable = {JOHANN_SEBASTIAN,WILHELM_FRIEDEMANN,CARL_PHILIP_EMMANUEL,JOHANN_CHRISTIAN,P_D_Q}
 * ;。
 * 
 *  protected EnumSyntax [] getEnumValueTable(){return enumValueTable; }}
 * </PRE>
 *  然后,您可以编写使用<CODE> == </CODE>和<CODE>！= </CODE>运算符来测试枚举值的代码;例如：
 * <PRE>
 *  Bach theComposer; 。 。 。
 *  if(theComposer == Bach.JOHANN_SEBASTIAN){System.out.println("所有时间最伟大的作曲家！ }}。
 * </PRE>
 * 枚举类的<CODE> equals()</CODE>方法只是测试相同的对象(<CODE> == </CODE>)。
 * <P>
 *  您可以通过调用{@link #toString()toString()}将枚举值转换为字符串。该字符串从枚举类提供的表中获取。
 * <P>
 *  在引擎盖下,枚举值只是一个整数,枚举类中的每个枚举值的不同的整数。您可以通过调用{@link #getValue()getValue()}获取枚举值的整数值。
 * 枚举值的整数值在构造时建立(参见{@link #EnumSyntax(int)EnumSyntax(int)})。
 * 由于构造函数是受保护的,所以唯一可能的枚举值是在枚举类中声明的单例对象;无法在运行时创建附加枚举值。
 * <P>
 *  您可以定义枚举类的子类,使用附加的枚举值扩展它。
 * 子类的枚举值的整数值不需要与超类的枚举值的整数值不同; <CODE> == </CODE>,<CODE>！= </CODE>,<CODE> equals()</CODE>和<CODE> toString
 * ()</CODE>子类使用一些与超类相同的整数值。
 *  您可以定义枚举类的子类,使用附加的枚举值扩展它。然而,其中使用枚举类和子类的应用可能需要在超类和子类中具有不同的整数值。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public abstract class EnumSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = -2739521845085831642L;

    /**
     * This enumeration value's integer value.
     * <p>
     *  此枚举值的整数值。
     * 
     * 
     * @serial
     */
    private int value;

    /**
     * Construct a new enumeration value with the given integer value.
     *
     * <p>
     * 使用给定的整数值构造新的枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected EnumSyntax(int value) {
        this.value = value;
    }

    /**
     * Returns this enumeration value's integer value.
     * <p>
     *  返回此枚举值的整数值。
     * 
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a clone of this enumeration value, which to preserve the
     * semantics of enumeration values is the same object as this enumeration
     * value.
     * <p>
     *  返回此枚举值的克隆,以保留枚举值的语义与此枚举值相同的对象。
     * 
     */
    public Object clone() {
        return this;
    }

    /**
     * Returns a hash code value for this enumeration value. The hash code is
     * just this enumeration value's integer value.
     * <p>
     *  返回此枚举值的哈希码值。哈希码只是这个枚举值的整数值。
     * 
     */
    public int hashCode() {
        return value;
    }

    /**
     * Returns a string value corresponding to this enumeration value.
     * <p>
     *  返回与此枚举值对应的字符串值。
     * 
     */
    public String toString() {

        String[] theTable = getStringTable();
        int theIndex = value - getOffset();
        return
            theTable != null && theIndex >= 0 && theIndex < theTable.length ?
            theTable[theIndex] :
            Integer.toString (value);
    }

    /**
     * During object input, convert this deserialized enumeration instance to
     * the proper enumeration value defined in the enumeration attribute class.
     *
     * <p>
     *  在对象输入期间,将此反序列化枚举实例转换为枚举属性类中定义的正确枚举值。
     * 
     * 
     * @return  The enumeration singleton value stored at index
     *          <I>i</I>-<I>L</I> in the enumeration value table returned by
     *          {@link #getEnumValueTable() getEnumValueTable()},
     *          where <I>i</I> is this enumeration value's integer value and
     *          <I>L</I> is the value returned by {@link #getOffset()
     *          getOffset()}.
     *
     * @throws ObjectStreamException if the stream can't be deserialised
     * @throws  InvalidObjectException
     *     Thrown if the enumeration value table is null, this enumeration
     *     value's integer value does not correspond to an element in the
     *     enumeration value table, or the corresponding element in the
     *     enumeration value table is null. (Note: {@link
     *     java.io.InvalidObjectException InvalidObjectException} is a subclass
     *     of {@link java.io.ObjectStreamException ObjectStreamException}, which
     *     <CODE>readResolve()</CODE> is declared to throw.)
     */
    protected Object readResolve() throws ObjectStreamException {

        EnumSyntax[] theTable = getEnumValueTable();

        if (theTable == null) {
            throw new InvalidObjectException(
                                "Null enumeration value table for class " +
                                getClass());
        }

        int theOffset = getOffset();
        int theIndex = value - theOffset;

        if (0 > theIndex || theIndex >= theTable.length) {
            throw new InvalidObjectException
                ("Integer value = " +  value + " not in valid range " +
                 theOffset + ".." + (theOffset + theTable.length - 1) +
                 "for class " + getClass());
        }

        EnumSyntax result = theTable[theIndex];
        if (result == null) {
            throw new InvalidObjectException
                ("No enumeration value for integer value = " +
                 value + "for class " + getClass());
        }
        return result;
    }

    // Hidden operations to be implemented in a subclass.

    /**
     * Returns the string table for this enumeration value's enumeration class.
     * The enumeration class's integer values are assumed to lie in the range
     * <I>L</I>..<I>L</I>+<I>N</I>-1, where <I>L</I> is the value returned by
     * {@link #getOffset() getOffset()} and <I>N</I> is the length
     * of the string table. The element in the string table at index
     * <I>i</I>-<I>L</I> is the value returned by {@link #toString()
     * toString()} for the enumeration value whose integer value
     * is <I>i</I>. If an integer within the above range is not used by any
     * enumeration value, leave the corresponding table element null.
     * <P>
     * The default implementation returns null. If the enumeration class (a
     * subclass of class EnumSyntax) does not override this method to return a
     * non-null string table, and the subclass does not override the {@link
     * #toString() toString()} method, the base class {@link
     * #toString() toString()} method will return just a string
     * representation of this enumeration value's integer value.
     * <p>
     *  返回此枚举值的枚举类的字符串表。
     * 枚举类的整数值被假定为处于范围<I> L <L> L <N> N + N -1中,其中<I> L </I> >是{@link #getOffset()getOffset()}返回的值,<I> N </I>
     * 是字符串表的长度。
     *  返回此枚举值的枚举类的字符串表。
     * 索引<I> i </I>  -  <I> L </I>处的字符串表中的元素是由{@link #toString()toString()}返回的值,对于整数值< I> i </I>。
     * 如果任何枚举值不使用上述范围内的整数,请将相应的表元素保留为空。
     * <P>
     * 默认实现返回null。
     * 如果枚举类(EnumSyntax类的子类)不覆盖此方法以返回非空字符串表,并且子类不覆盖{@link #toString()toString()}方法,则基类{@ link #toString()toString()}
     * 方法将仅返回此枚举值的整数值的字符串表示形式。
     * 默认实现返回null。
     * 
     * 
     * @return the string table
     */
    protected String[] getStringTable() {
        return null;
    }

    /**
     * Returns the enumeration value table for this enumeration value's
     * enumeration class. The enumeration class's integer values are assumed to
     * lie in the range <I>L</I>..<I>L</I>+<I>N</I>-1, where <I>L</I> is the
     * value returned by {@link #getOffset() getOffset()} and
     * <I>N</I> is the length of the enumeration value table. The element in the
     * enumeration value table at index <I>i</I>-<I>L</I> is the enumeration
     * value object whose integer value is <I>i</I>; the {@link #readResolve()
     * readResolve()} method needs this to preserve singleton
     * semantics during deserialization of an enumeration instance. If an
     * integer within the above range is not used by any enumeration value,
     * leave the corresponding table element null.
     * <P>
     * The default implementation returns null. If the enumeration class (a
     * subclass of class EnumSyntax) does not override this method to return
     * a non-null enumeration value table, and the subclass does not override
     * the {@link #readResolve() readResolve()} method, the base
     * class {@link #readResolve() readResolve()} method will throw
     * an exception whenever an enumeration instance is deserialized from an
     * object input stream.
     * <p>
     *  返回此枚举值的枚举类的枚举值表。
     * 枚举类的整数值被假定为处于范围<I> L <L> L <N> N + N -1中,其中<I> L </I> >是{@link #getOffset()getOffset()}返回的值,<I> N </I>
     * 是枚举值表的长度。
     *  返回此枚举值的枚举类的枚举值表。
     * 索引<i> i < - > I <L>下的枚举值表中的元素是整数值为<I> i </I>的枚举值对象; {@link #readResolve()readResolve()}方法需要这个在枚举实例的反序
     * 列化期间保留单例语义。
     *  返回此枚举值的枚举类的枚举值表。如果任何枚举值不使用上述范围内的整数,请将相应的表元素保留为空。
     * <P>
     * 默认实现返回null。
     * 
     * @return the value table
     */
    protected EnumSyntax[] getEnumValueTable() {
        return null;
    }

    /**
     * Returns the lowest integer value used by this enumeration value's
     * enumeration class.
     * <P>
     * The default implementation returns 0. If the enumeration class (a
     * subclass of class EnumSyntax) uses integer values starting at other than
     * 0, override this method in the subclass.
     * <p>
     * 如果枚举类(EnumSyntax类的子类)不覆盖此方法以返回非空枚举值表,并且子类不覆盖{@link #readResolve()readResolve()}方法,则基类{ @link #readResolve()readResolve()}
     * 方法将在枚举实例从对象输入流反序列化时抛出异常。
     * 默认实现返回null。
     * 
     * 
     * @return the offset of the lowest enumeration value.
     */
    protected int getOffset() {
        return 0;
    }

}
