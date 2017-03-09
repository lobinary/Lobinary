/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


/**
 * Represents values that can be passed as arguments to
 * relational expressions. Strings, numbers, attributes are valid values
 * and should be represented by implementations of <CODE>ValueExp</CODE>.
 *
 * <p>
 *  表示可以作为参数传递到关系表达式的值。字符串,数字,属性是有效值,应由<CODE> ValueExp </CODE>的实现来表示。
 * 
 * 
 * @since 1.5
 */
/*
  We considered generifying this interface as ValueExp<T>, where T is
  the Java type that this expression generates.  This allows some additional
  checking in the various methods of the Query class, but in practice
  not much.  Typically you have something like
  Query.lt(Query.attr("A"), Query.value(5)).  We can arrange for Query.value
  to have type ValueExp<Integer> (or maybe ValueExp<Long> or ValueExp<Number>)
  but for Query.attr we can't do better than ValueExp<?> or plain ValueExp.
  So even though we could define Query.lt as:
  QueryExp <T> lt(ValueExp<T> v1, ValueExp<T> v2)
  and thus prevent comparing a
  number against a string, in practice the first ValueExp will almost always
  be a Query.attr so this check serves no purpose.  You would have to
  write Query.<Number>attr("A"), for example, which would be awful.  And,
  if you wrote Query.<Integer>attr("A") you would then discover that you
  couldn't compare it against Query.value(5) if the latter is defined as
  ValueExp<Number>, or against Query.value(5L) if it is defined as
  ValueExp<Integer>.

  Worse, for Query.in we would like to define:
  QueryExp <T> in(ValueExp<T> val, ValueExp<T>[] valueList)
  but this is unusable because you cannot write
  "new ValueExp<Integer>[] {...}" (the compiler forbids it).

  The few mistakes you might catch with this generification certainly
  wouldn't justify the hassle of modifying user code to get the checks
  to be made and the "unchecked" warnings that would arise if it
  wasn't so modified.

  We could reconsider this if the Query methods were augmented, for example
  with:
  AttributeValueExp<Number> numberAttr(String name);
  AttributeValueExp<String> stringAttr(String name);
  AttributeValueExp<Boolean> booleanAttr(String name);
  QueryExp <T> in(ValueExp<T> val, Set<ValueExp<T>> valueSet).
  But it's not really clear what numberAttr should do if it finds that the
  attribute is not in fact a Number.
/* <p>
/*  我们考虑将此接口生成为ValueExp <T>,其中T是此表达式生成的Java类型。这允许在Query类的各种方法中进行一些额外的检查,但实际上并不多。
/* 通常你有类似Query.lt(Query.attr("A"),Query.value(5))。
/* 我们可以安排Query.value具有类型ValueExp <Integer>(或者ValueExp <Long>或ValueExp <Number>),但对于Query.attr,我们不能做得比Val
/* ueExp <?>或纯ValueExp更好。
/* 通常你有类似Query.lt(Query.attr("A"),Query.value(5))。
/* 因此,即使我们可以定义Query.lt为：QueryExp <T> lt(ValueExp <T> v1,ValueExp <T> v2),因此阻止比较一个数字和字符串,实际上第一个ValueExp几乎
/* 总是一个Query .attr,所以这个检查没有目的。
/* 通常你有类似Query.lt(Query.attr("A"),Query.value(5))。你必须写Query。<Number> attr("A"),例如,这将是可怕的。如果你写了Query。
/* <Integer> attr("A"),你会发现,如果后者被定义为ValueExp <Number>,或者Query.value(5)不能与Query.value (5L),如果它被定义为ValueEx
/* p <Integer>。
/* 通常你有类似Query.lt(Query.attr("A"),Query.value(5))。你必须写Query。<Number> attr("A"),例如,这将是可怕的。如果你写了Query。
/* 
/* 更糟糕的是,对于Query.in,我们想定义：QueryExp <T> in(ValueExp <T> val,ValueExp <T> [] valueList),但是这是不可用的,因为你不能写"Ne
/* w ValueExp <Integer> [] {。
/*  ..}"(编译器禁止它)。
 */
public interface ValueExp extends java.io.Serializable {

    /**
     * Applies the ValueExp on a MBean.
     *
     * <p>
     * 
     *  你可能会抓住这个生成的几个错误肯定不会证明修改用户代码以获得检查和如果没有这样修改会出现"未经检查的"警告的麻烦。
     * 
     *  如果Query方法被扩充,我们可以重新考虑这一点,例如：AttributeValueExp <Number> numberAttr(String name); AttributeValueExp <String>
     *  stringAttr(String name); AttributeValueExp <Boolean> booleanAttr(String name); QueryExp <T> in(Value
     * Exp <T> val,Set <ValueExp <T >> valueSet)。
     * 但是它不是很清楚什么numberAttr应该做,如果它发现属性实际上不是一个数字。
     * 
     * @param name The name of the MBean on which the ValueExp will be applied.
     *
     * @return  The <CODE>ValueExp</CODE>.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException
     * @exception InvalidApplicationException
     */
    public ValueExp apply(ObjectName name)
            throws BadStringOperationException, BadBinaryOpValueExpException,
                   BadAttributeValueExpException, InvalidApplicationException;

    /**
     * Sets the MBean server on which the query is to be performed.
     *
     * <p>
     * 
     * 
     * @param s The MBean server on which the query is to be performed.
     *
     * @deprecated This method is not needed because a
     * <code>ValueExp</code> can access the MBean server in which it
     * is being evaluated by using {@link QueryEval#getMBeanServer()}.
     */
    @Deprecated
    public  void setMBeanServer(MBeanServer s) ;
}
