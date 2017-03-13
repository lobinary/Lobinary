/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <p>Constructs query object constraints.</p>
 *
 * <p>The MBean Server can be queried for MBeans that meet a particular
 * condition, using its {@link MBeanServer#queryNames queryNames} or
 * {@link MBeanServer#queryMBeans queryMBeans} method.  The {@link QueryExp}
 * parameter to the method can be any implementation of the interface
 * {@code QueryExp}, but it is usually best to obtain the {@code QueryExp}
 * value by calling the static methods in this class.  This is particularly
 * true when querying a remote MBean Server: a custom implementation of the
 * {@code QueryExp} interface might not be present in the remote MBean Server,
 * but the methods in this class return only standard classes that are
 * part of the JMX implementation.</p>
 *
 * <p>As an example, suppose you wanted to find all MBeans where the {@code
 * Enabled} attribute is {@code true} and the {@code Owner} attribute is {@code
 * "Duke"}. Here is how you could construct the appropriate {@code QueryExp} by
 * chaining together method calls:</p>
 *
 * <pre>
 * QueryExp query =
 *     Query.and(Query.eq(Query.attr("Enabled"), Query.value(true)),
 *               Query.eq(Query.attr("Owner"), Query.value("Duke")));
 * </pre>
 *
 * <p>
 *  <p>构造查询对象约束</p>
 * 
 * <p>可以使用其{@link MBeanServer#queryNames queryNames}或{@link MBeanServer#queryMBeans queryMBeans}方法查询满足特定
 * 条件的MBean的MBean服务器。
 * 该方法的{@link QueryExp}参数可以是任何实现接口{@code QueryExp},但通常最好通过调用此类中的静态方法来获得{@code QueryExp}值这在查询远程MBean Serv
 * er时尤其如此：{@代码QueryExp}接口可能不存在于远程MBean服务器中,但此类中的方法仅返回作为JMX实现的一部分的标准类</p>。
 * 
 * <p>例如,假设您想要找到{@code Enabled}属性为{@code true}且{@code Owner}属性为{@code"Duke"}的所有MBean。
 * 下面是您可以如何构建通过将方法调用链接在一起的适当{@code QueryExp}：</p>。
 * 
 * <pre>
 *  QueryExp query = Queryand(Queryeq(Queryattr("Enabled"),Queryvalue(true)),Queryeq(Queryattr("Owner"),
 * Queryvalue("Duke")));。
 * </pre>
 * 
 * 
 * @since 1.5
 */
 public class Query extends Object   {


     /**
      * A code representing the {@link Query#gt} query.  This is chiefly
      * of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#gt}查询的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int GT  = 0;

     /**
      * A code representing the {@link Query#lt} query.  This is chiefly
      * of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#lt}查询的代码这是查询序列化形式的主要关注点
      * 
      */
     public static final int LT  = 1;

     /**
      * A code representing the {@link Query#geq} query.  This is chiefly
      * of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#geq}查询的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int GE  = 2;

     /**
      * A code representing the {@link Query#leq} query.  This is chiefly
      * of interest for the serialized form of queries.
      * <p>
      * 代表{@link Query#leq}查询的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int LE  = 3;

     /**
      * A code representing the {@link Query#eq} query.  This is chiefly
      * of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#eq}查询的代码这是查询序列化形式的主要关注点
      * 
      */
     public static final int EQ  = 4;


     /**
      * A code representing the {@link Query#plus} expression.  This
      * is chiefly of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#plus}表达式的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int PLUS  = 0;

     /**
      * A code representing the {@link Query#minus} expression.  This
      * is chiefly of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#minus}表达式的代码这是查询序列化形式的主要关注点
      * 
      */
     public static final int MINUS = 1;

     /**
      * A code representing the {@link Query#times} expression.  This
      * is chiefly of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#times}表达式的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int TIMES = 2;

     /**
      * A code representing the {@link Query#div} expression.  This is
      * chiefly of interest for the serialized form of queries.
      * <p>
      *  代表{@link Query#div}表达式的代码这是查询的序列化形式的主要关注点
      * 
      */
     public static final int DIV   = 3;


     /**
      * Basic constructor.
      * <p>
      *  基本构造函数
      * 
      */
     public Query() {
     }


     /**
      * Returns a query expression that is the conjunction of two other query
      * expressions.
      *
      * <p>
      * 返回一个查询表达式,它是两个其他查询表达式的联合
      * 
      * 
      * @param q1 A query expression.
      * @param q2 Another query expression.
      *
      * @return  The conjunction of the two arguments.  The returned object
      * will be serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.AndQueryExp">
      * javax.management.AndQueryExp</a>.
      */
     public static QueryExp and(QueryExp q1, QueryExp q2)  {
         return new AndQueryExp(q1, q2);
     }

     /**
      * Returns a query expression that is the disjunction of two other query
      * expressions.
      *
      * <p>
      *  返回一个查询表达式,它是两个其他查询表达式的离散点
      * 
      * 
      * @param q1 A query expression.
      * @param q2 Another query expression.
      *
      * @return  The disjunction of the two arguments.  The returned object
      * will be serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.OrQueryExp">
      * javax.management.OrQueryExp</a>.
      */
     public static QueryExp or(QueryExp q1, QueryExp q2)  {
         return new OrQueryExp(q1, q2);
     }

     /**
      * Returns a query expression that represents a "greater than" constraint on
      * two values.
      *
      * <p>
      *  返回对两个值表示"大于"约束的查询表达式
      * 
      * 
      * @param v1 A value expression.
      * @param v2 Another value expression.
      *
      * @return A "greater than" constraint on the arguments.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryRelQueryExp">
      * javax.management.BinaryRelQueryExp</a> with a {@code relOp} equal
      * to {@link #GT}.
      */
     public static QueryExp gt(ValueExp v1, ValueExp v2)  {
         return new BinaryRelQueryExp(GT, v1, v2);
     }

     /**
      * Returns a query expression that represents a "greater than or equal
      * to" constraint on two values.
      *
      * <p>
      *  返回对两个值表示"大于或等于"约束的查询表达式
      * 
      * 
      * @param v1 A value expression.
      * @param v2 Another value expression.
      *
      * @return A "greater than or equal to" constraint on the
      * arguments.  The returned object will be serialized as an
      * instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryRelQueryExp">
      * javax.management.BinaryRelQueryExp</a> with a {@code relOp} equal
      * to {@link #GE}.
      */
     public static QueryExp geq(ValueExp v1, ValueExp v2)  {
         return new BinaryRelQueryExp(GE, v1, v2);
     }

     /**
      * Returns a query expression that represents a "less than or equal to"
      * constraint on two values.
      *
      * <p>
      *  返回在两个值上表示"小于或等于"约束的查询表达式
      * 
      * 
      * @param v1 A value expression.
      * @param v2 Another value expression.
      *
      * @return A "less than or equal to" constraint on the arguments.
      * The returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryRelQueryExp">
      * javax.management.BinaryRelQueryExp</a> with a {@code relOp} equal
      * to {@link #LE}.
      */
     public static QueryExp leq(ValueExp v1, ValueExp v2)  {
         return new BinaryRelQueryExp(LE, v1, v2);
     }

     /**
      * Returns a query expression that represents a "less than" constraint on
      * two values.
      *
      * <p>
      *  返回一个查询表达式,该表达式对两个值表示"小于"的约束
      * 
      * 
      * @param v1 A value expression.
      * @param v2 Another value expression.
      *
      * @return A "less than" constraint on the arguments.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryRelQueryExp">
      * javax.management.BinaryRelQueryExp</a> with a {@code relOp} equal
      * to {@link #LT}.
      */
     public static QueryExp lt(ValueExp v1, ValueExp v2)  {
         return new BinaryRelQueryExp(LT, v1, v2);
     }

     /**
      * Returns a query expression that represents an equality constraint on
      * two values.
      *
      * <p>
      *  返回表示两个值上的等式约束的查询表达式
      * 
      * 
      * @param v1 A value expression.
      * @param v2 Another value expression.
      *
      * @return A "equal to" constraint on the arguments.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryRelQueryExp">
      * javax.management.BinaryRelQueryExp</a> with a {@code relOp} equal
      * to {@link #EQ}.
      */
     public static QueryExp eq(ValueExp v1, ValueExp v2)  {
         return new BinaryRelQueryExp(EQ, v1, v2);
     }

     /**
      * Returns a query expression that represents the constraint that one
      * value is between two other values.
      *
      * <p>
      *  返回一个查询表达式,表示一个值在两个其他值之间的约束
      * 
      * 
      * @param v1 A value expression that is "between" v2 and v3.
      * @param v2 Value expression that represents a boundary of the constraint.
      * @param v3 Value expression that represents a boundary of the constraint.
      *
      * @return The constraint that v1 lies between v2 and v3.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BetweenQueryExp">
      * javax.management.BetweenQueryExp</a>.
      */
     public static QueryExp between(ValueExp v1, ValueExp v2, ValueExp v3) {
         return new BetweenQueryExp(v1, v2, v3);
     }

     /**
      * Returns a query expression that represents a matching constraint on
      * a string argument. The matching syntax is consistent with file globbing:
      * supports "<code>?</code>", "<code>*</code>", "<code>[</code>",
      * each of which may be escaped with "<code>\</code>";
      * character classes may use "<code>!</code>" for negation and
      * "<code>-</code>" for range.
      * (<code>*</code> for any character sequence,
      * <code>?</code> for a single arbitrary character,
      * <code>[...]</code> for a character sequence).
      * For example: <code>a*b?c</code> would match a string starting
      * with the character <code>a</code>, followed
      * by any number of characters, followed by a <code>b</code>,
      * any single character, and a <code>c</code>.
      *
      * <p>
      * 返回一个查询表达式,表示匹配的字符串参数的约束匹配的语法与文件globbing一致：支持"<code>?</code>","<code> * </code>","<code> </code>",每个都可
      * 以用"<code> \\ </code>"转义;字符类可以对于任何字符序列使用"<code>！</code>"作为否定和使用"<code>  -  </code>"(<code> * </code> >
      * 用于单个任意字符,<code> [] </code>用于字符序列)例如：<code> a * b?c </code>将匹配以字符开头的字符<代码>,后跟任意数量的字符,后跟一个<code> b </code>
      * ,任何单个字符和<code> c </code>。
      * 
      * 
      * @param a An attribute expression
      * @param s A string value expression representing a matching constraint
      *
      * @return A query expression that represents the matching
      * constraint on the string argument.  The returned object will
      * be serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.MatchQueryExp">
      * javax.management.MatchQueryExp</a>.
      */
     public static QueryExp match(AttributeValueExp a, StringValueExp s)  {
         return new MatchQueryExp(a, s);
     }

     /**
      * <p>Returns a new attribute expression.  See {@link AttributeValueExp}
      * for a detailed description of the semantics of the expression.</p>
      *
      * <p>Evaluating this expression for a given
      * <code>objectName</code> includes performing {@link
      * MBeanServer#getAttribute MBeanServer.getAttribute(objectName,
      * name)}.</p>
      *
      * <p>
      * <p>返回新的属性表达式有关表达式语义的详细说明,请参阅{@link AttributeValueExp}。</p>
      * 
      *  <p>为给定的<code> objectName </code>评估此表达式包括执行{@link MBeanServer#getAttribute MBeanServergetAttribute(objectName,name)}
      *  </p>。
      * 
      * 
      * @param name The name of the attribute.
      *
      * @return An attribute expression for the attribute named {@code name}.
      */
     public static AttributeValueExp attr(String name)  {
         return new AttributeValueExp(name);
     }

     /**
      * <p>Returns a new qualified attribute expression.</p>
      *
      * <p>Evaluating this expression for a given
      * <code>objectName</code> includes performing {@link
      * MBeanServer#getObjectInstance
      * MBeanServer.getObjectInstance(objectName)} and {@link
      * MBeanServer#getAttribute MBeanServer.getAttribute(objectName,
      * name)}.</p>
      *
      * <p>
      *  <p>返回新的限定属性表达式</p>
      * 
      *  <p>为给定的<code> objectName </code>评估此表达式包括执行{@link MBeanServer#getObjectInstance MBeanServergetObjectInstance(objectName)}
      * 和{@link MBeanServer#getAttribute MBeanServergetAttribute(objectName,name)} </p>。
      * 
      * 
      * @param className The name of the class possessing the attribute.
      * @param name The name of the attribute.
      *
      * @return An attribute expression for the attribute named name.
      * The returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.QualifiedAttributeValueExp">
      * javax.management.QualifiedAttributeValueExp</a>.
      */
     public static AttributeValueExp attr(String className, String name)  {
         return new QualifiedAttributeValueExp(className, name);
     }

     /**
      * <p>Returns a new class attribute expression which can be used in any
      * Query call that expects a ValueExp.</p>
      *
      * <p>Evaluating this expression for a given
      * <code>objectName</code> includes performing {@link
      * MBeanServer#getObjectInstance
      * MBeanServer.getObjectInstance(objectName)}.</p>
      *
      * <p>
      *  <p>返回一个新的类属性表达式,可以在任何期望ValueExp的查询调用中使用</p>
      * 
      * <p>为给定的<code> objectName </code>评估此表达式包括执行{@link MBeanServer#getObjectInstance MBeanServergetObjectInstance(objectName)}
      *  </p>。
      * 
      * 
      * @return A class attribute expression.  The returned object
      * will be serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.ClassAttributeValueExp">
      * javax.management.ClassAttributeValueExp</a>.
      */
     public static AttributeValueExp classattr()  {
         return new ClassAttributeValueExp();
     }

     /**
      * Returns a constraint that is the negation of its argument.
      *
      * <p>
      *  返回一个约束,该约束是其参数的否定
      * 
      * 
      * @param queryExp The constraint to negate.
      *
      * @return A negated constraint.  The returned object will be
      * serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.NotQueryExp">
      * javax.management.NotQueryExp</a>.
      */
     public static QueryExp not(QueryExp queryExp)  {
         return new NotQueryExp(queryExp);
     }

     /**
      * Returns an expression constraining a value to be one of an explicit list.
      *
      * <p>
      *  返回一个将值限制为显式列表之一的表达式
      * 
      * 
      * @param val A value to be constrained.
      * @param valueList An array of ValueExps.
      *
      * @return A QueryExp that represents the constraint.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.InQueryExp">
      * javax.management.InQueryExp</a>.
      */
     public static QueryExp in(ValueExp val, ValueExp valueList[])  {
         return new InQueryExp(val, valueList);
     }

     /**
      * Returns a new string expression.
      *
      * <p>
      *  返回一个新的字符串表达式
      * 
      * 
      * @param val The string value.
      *
      * @return  A ValueExp object containing the string argument.
      */
     public static StringValueExp value(String val)  {
         return new StringValueExp(val);
     }

     /**
      * Returns a numeric value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      *  返回一个数值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val An instance of Number.
      *
      * @return A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.NumericValueExp">
      * javax.management.NumericValueExp</a>.
      */
     public static ValueExp value(Number val)  {
         return new NumericValueExp(val);
     }

     /**
      * Returns a numeric value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      *  返回一个数值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val An int value.
      *
      * @return A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.NumericValueExp">
      * javax.management.NumericValueExp</a>.
      */
     public static ValueExp value(int val)  {
         return new NumericValueExp((long) val);
     }

     /**
      * Returns a numeric value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      *  返回一个数值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val A long value.
      *
      * @return A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.NumericValueExp">
      * javax.management.NumericValueExp</a>.
      */
     public static ValueExp value(long val)  {
         return new NumericValueExp(val);
     }

     /**
      * Returns a numeric value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      *  返回一个数值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val A float value.
      *
      * @return A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.NumericValueExp">
      * javax.management.NumericValueExp</a>.
      */
     public static ValueExp value(float val)  {
         return new NumericValueExp((double) val);
     }

     /**
      * Returns a numeric value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      * 返回一个数值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val A double value.
      *
      * @return  A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.NumericValueExp">
      * javax.management.NumericValueExp</a>.
      */
     public static ValueExp value(double val)  {
         return new NumericValueExp(val);
     }

     /**
      * Returns a boolean value expression that can be used in any Query call
      * that expects a ValueExp.
      *
      * <p>
      *  返回一个布尔值表达式,可以在任何期望ValueExp的Query调用中使用
      * 
      * 
      * @param val A boolean value.
      *
      * @return A ValueExp object containing the argument.  The
      * returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BooleanValueExp">
      * javax.management.BooleanValueExp</a>.
      */
     public static ValueExp value(boolean val)  {
         return new BooleanValueExp(val);
     }

     /**
      * Returns a binary expression representing the sum of two numeric values,
      * or the concatenation of two string values.
      *
      * <p>
      *  返回表示两个数字值之和的二进制表达式,或两个字符串值的并置
      * 
      * 
      * @param value1 The first '+' operand.
      * @param value2 The second '+' operand.
      *
      * @return A ValueExp representing the sum or concatenation of
      * the two arguments.  The returned object will be serialized as
      * an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryOpValueExp">
      * javax.management.BinaryOpValueExp</a> with an {@code op} equal to
      * {@link #PLUS}.
      */
     public static ValueExp plus(ValueExp value1, ValueExp value2) {
         return new BinaryOpValueExp(PLUS, value1, value2);
     }

     /**
      * Returns a binary expression representing the product of two numeric values.
      *
      *
      * <p>
      *  返回表示两个数值的乘积的二进制表达式
      * 
      * 
      * @param value1 The first '*' operand.
      * @param value2 The second '*' operand.
      *
      * @return A ValueExp representing the product.  The returned
      * object will be serialized as an instance of the non-public
      * class
      * <a href="../../serialized-form.html#javax.management.BinaryOpValueExp">
      * javax.management.BinaryOpValueExp</a> with an {@code op} equal to
      * {@link #TIMES}.
      */
     public static ValueExp times(ValueExp value1,ValueExp value2) {
         return new BinaryOpValueExp(TIMES, value1, value2);
     }

     /**
      * Returns a binary expression representing the difference between two numeric
      * values.
      *
      * <p>
      *  返回表示两个数值之间的差值的二进制表达式
      * 
      * 
      * @param value1 The first '-' operand.
      * @param value2 The second '-' operand.
      *
      * @return A ValueExp representing the difference between two
      * arguments.  The returned object will be serialized as an
      * instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryOpValueExp">
      * javax.management.BinaryOpValueExp</a> with an {@code op} equal to
      * {@link #MINUS}.
      */
     public static ValueExp minus(ValueExp value1, ValueExp value2) {
         return new BinaryOpValueExp(MINUS, value1, value2);
     }

     /**
      * Returns a binary expression representing the quotient of two numeric
      * values.
      *
      * <p>
      *  返回表示两个数值的商的二进制表达式
      * 
      * 
      * @param value1 The first '/' operand.
      * @param value2 The second '/' operand.
      *
      * @return A ValueExp representing the quotient of two arguments.
      * The returned object will be serialized as an instance of the
      * non-public class
      * <a href="../../serialized-form.html#javax.management.BinaryOpValueExp">
      * javax.management.BinaryOpValueExp</a> with an {@code op} equal to
      * {@link #DIV}.
      */
     public static ValueExp div(ValueExp value1, ValueExp value2) {
         return new BinaryOpValueExp(DIV, value1, value2);
     }

     /**
      * Returns a query expression that represents a matching constraint on
      * a string argument. The value must start with the given literal string
      * value.
      *
      * <p>
      *  返回表示对字符串参数的匹配约束的查询表达式该值必须以给定的字符串值开头
      * 
      * 
      * @param a An attribute expression.
      * @param s A string value expression representing the beginning of the
      * string value.
      *
      * @return The constraint that a matches s.  The returned object
      * will be serialized as an instance of the non-public class
      *
      * <a href="../../serialized-form.html#javax.management.MatchQueryExp">
      * javax.management.MatchQueryExp</a>.
      */
     public static QueryExp initialSubString(AttributeValueExp a, StringValueExp s)  {
         return new MatchQueryExp(a,
             new StringValueExp(escapeString(s.getValue()) + "*"));
     }

     /**
      * Returns a query expression that represents a matching constraint on
      * a string argument. The value must contain the given literal string
      * value.
      *
      * <p>
      * 返回表示对字符串参数的匹配约束的查询表达式该值必须包含给定的文字字符串值
      * 
      * 
      * @param a An attribute expression.
      * @param s A string value expression representing the substring.
      *
      * @return The constraint that a matches s.  The returned object
      * will be serialized as an instance of the non-public class
      *
      * <a href="../../serialized-form.html#javax.management.MatchQueryExp">
      * javax.management.MatchQueryExp</a>.
      */
     public static QueryExp anySubString(AttributeValueExp a, StringValueExp s) {
         return new MatchQueryExp(a,
             new StringValueExp("*" + escapeString(s.getValue()) + "*"));
     }

     /**
      * Returns a query expression that represents a matching constraint on
      * a string argument. The value must end with the given literal string
      * value.
      *
      * <p>
      *  返回表示对字符串参数的匹配约束的查询表达式该值必须以给定的字符串值结尾
      * 
      * 
      * @param a An attribute expression.
      * @param s A string value expression representing the end of the string
      * value.
      *
      * @return The constraint that a matches s.  The returned object
      * will be serialized as an instance of the non-public class
      *
      * <a href="../../serialized-form.html#javax.management.MatchQueryExp">
      * javax.management.MatchQueryExp</a>.
      */
     public static QueryExp finalSubString(AttributeValueExp a, StringValueExp s) {
         return new MatchQueryExp(a,
             new StringValueExp("*" + escapeString(s.getValue())));
     }

     /**
      * Returns a query expression that represents an inheritance constraint
      * on an MBean class.
      * <p>Example: to find MBeans that are instances of
      * {@link NotificationBroadcaster}, use
      * {@code Query.isInstanceOf(Query.value(NotificationBroadcaster.class.getName()))}.
      * </p>
      * <p>Evaluating this expression for a given
      * <code>objectName</code> includes performing {@link
      * MBeanServer#isInstanceOf MBeanServer.isInstanceOf(objectName,
      * ((StringValueExp)classNameValue.apply(objectName)).getValue()}.</p>
      *
      * <p>
      *  返回一个表示MBean类继承约束的查询表达式示例：要查找作为{@link NotificationBroadcaster}实例的MBean,请使用{@code QueryisInstanceOf(Queryvalue(NotificationBroadcasterclassgetName()))}
      * }。
      * </p>
      *  <p>为给定的<code> objectName </code>评估此表达式包括执行{@link MBeanServer#isInstanceOf MBeanServerisInstanceOf(objectName,((StringValueExp)classNameValueapply(objectName))getValue()}
      *  </p>。
      * 
      * @param classNameValue The {@link StringValueExp} returning the name
      *        of the class of which selected MBeans should be instances.
      * @return a query expression that represents an inheritance
      * constraint on an MBean class.  The returned object will be
      * serialized as an instance of the non-public class
      * <a href="../../serialized-form.html#javax.management.InstanceOfQueryExp">
      * javax.management.InstanceOfQueryExp</a>.
      * @since 1.6
      */
     public static QueryExp isInstanceOf(StringValueExp classNameValue) {
        return new InstanceOfQueryExp(classNameValue);
     }

     /**
      * Utility method to escape strings used with
      * Query.{initial|any|final}SubString() methods.
      * <p>
      * 
      */
     private static String escapeString(String s) {
         if (s == null)
             return null;
         s = s.replace("\\", "\\\\");
         s = s.replace("*", "\\*");
         s = s.replace("?", "\\?");
         s = s.replace("[", "\\[");
         return s;
     }
 }
