/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <code>RowFilter</code> is used to filter out entries from the
 * model so that they are not shown in the view.  For example, a
 * <code>RowFilter</code> associated with a <code>JTable</code> might
 * only allow rows that contain a column with a specific string. The
 * meaning of <em>entry</em> depends on the component type.
 * For example, when a filter is
 * associated with a <code>JTable</code>, an entry corresponds to a
 * row; when associated with a <code>JTree</code>, an entry corresponds
 * to a node.
 * <p>
 * Subclasses must override the <code>include</code> method to
 * indicate whether the entry should be shown in the
 * view.  The <code>Entry</code> argument can be used to obtain the values in
 * each of the columns in that entry.  The following example shows an
 * <code>include</code> method that allows only entries containing one or
 * more values starting with the string "a":
 * <pre>
 * RowFilter&lt;Object,Object&gt; startsWithAFilter = new RowFilter&lt;Object,Object&gt;() {
 *   public boolean include(Entry&lt;? extends Object, ? extends Object&gt; entry) {
 *     for (int i = entry.getValueCount() - 1; i &gt;= 0; i--) {
 *       if (entry.getStringValue(i).startsWith("a")) {
 *         // The value starts with "a", include it
 *         return true;
 *       }
 *     }
 *     // None of the columns start with "a"; return false so that this
 *     // entry is not shown
 *     return false;
 *   }
 * };
 * </pre>
 * <code>RowFilter</code> has two formal type parameters that allow
 * you to create a <code>RowFilter</code> for a specific model. For
 * example, the following assumes a specific model that is wrapping
 * objects of type <code>Person</code>.  Only <code>Person</code>s
 * with an age over 20 will be shown:
 * <pre>
 * RowFilter&lt;PersonModel,Integer&gt; ageFilter = new RowFilter&lt;PersonModel,Integer&gt;() {
 *   public boolean include(Entry&lt;? extends PersonModel, ? extends Integer&gt; entry) {
 *     PersonModel personModel = entry.getModel();
 *     Person person = personModel.getPerson(entry.getIdentifier());
 *     if (person.getAge() &gt; 20) {
 *       // Returning true indicates this row should be shown.
 *       return true;
 *     }
 *     // Age is &lt;= 20, don't show it.
 *     return false;
 *   }
 * };
 * PersonModel model = createPersonModel();
 * TableRowSorter&lt;PersonModel&gt; sorter = new TableRowSorter&lt;PersonModel&gt;(model);
 * sorter.setRowFilter(ageFilter);
 * </pre>
 *
 * <p>
 *  <code> RowFilter </code>用于过滤掉模型中的条目,以使它们不会显示在视图中。
 * 例如,与<code> JTable </code>关联的<code> RowFilter </code>可能只允许包含具有特定字符串的列的行。 <em>条目</em>的含义取决于组件类型。
 * 例如,当过滤器与<code> JTable </code>相关联时,条目对应于行;当与<code> JTree </code>相关联时,条目对应于节点。
 * <p>
 *  子类必须覆盖<code> include </code>方法以指示该条目是否应显示在视图中。 <code> Entry </code>参数可用于获取该条目中每个列中的值。
 * 以下示例显示了一个<code> include </code>方法,该方法只允许包含以字符串"a"开头的一个或多个值的条目：。
 * <pre>
 *  RowFilter&lt; Object,Object&gt; beginWithAFilter = new RowFilter&lt; Object,Object&gt;(){public boolean include(Entry&lt ;? extends Object,?extends Object&gt; entry){for(int i = entry.getValueCount() -  1; i&gt; = 0; i- - ){if(entry.getStringValue(i).startsWith("a")){//该值以"a"开始,包括return true; }
 * } //没有列以"a"开头;返回false,因此这个//条目未显示return false; }};。
 * </pre>
 * <code> RowFilter </code>有两个形式类型参数,您可以为特定模型创建<code> RowFilter </code>。
 * 例如,下面假设一个特定的模型,它包装类型<code> Person </code>的对象。只会显示年龄超过20岁的<code> Person </code>：。
 * <pre>
 *  RowFilter&lt; PersonModel,Integer&gt; ageFilter = new RowFilter&lt; PersonModel,Integer&gt;(){public boolean include(Entry&lt ;? extends PersonModel,?extends Integer&gt; entry){PersonModel personModel = entry.getModel Person person = personModel.getPerson(entry.getIdentifier()); if(person.getAge()&gt; 20){//返回true表示应该显示此行。
 *  return true; } //年龄是<= 20,不显示它。
 *  return false; }}; PersonModel model = createPersonModel(); TableRowSorter&lt; PersonModel&gt; sorter
 *  = new TableRowSorter&lt; PersonModel&gt;(model); sorter.setRowFilter(ageFilter);。
 *  return true; } //年龄是<= 20,不显示它。
 * </pre>
 * 
 * 
 * @param <M> the type of the model; for example <code>PersonModel</code>
 * @param <I> the type of the identifier; when using
 *            <code>TableRowSorter</code> this will be <code>Integer</code>
 * @see javax.swing.table.TableRowSorter
 * @since 1.6
 */
public abstract class RowFilter<M,I> {
    /**
     * Enumeration of the possible comparison values supported by
     * some of the default <code>RowFilter</code>s.
     *
     * <p>
     *  枚举一些默认的<code> RowFilter </code>支持的可能的比较值。
     * 
     * 
     * @see RowFilter
     * @since 1.6
     */
    public enum ComparisonType {
        /**
         * Indicates that entries with a value before the supplied
         * value should be included.
         * <p>
         *  表示应包含提供的值之前的值的条目。
         * 
         */
        BEFORE,

        /**
         * Indicates that entries with a value after the supplied
         * value should be included.
         * <p>
         *  表示应包括具有提供的值之后的值的条目。
         * 
         */
        AFTER,

        /**
         * Indicates that entries with a value equal to the supplied
         * value should be included.
         * <p>
         *  表示应包括值等于提供的值的条目。
         * 
         */
        EQUAL,

        /**
         * Indicates that entries with a value not equal to the supplied
         * value should be included.
         * <p>
         *  表示应包括值不等于提供的值的条目。
         * 
         */
        NOT_EQUAL
    }

    /**
     * Throws an IllegalArgumentException if any of the values in
     * columns are {@literal <} 0.
     * <p>
     *  如果列中的任何值为{@literal <} 0,则抛出IllegalArgumentException。
     * 
     */
    private static void checkIndices(int[] columns) {
        for (int i = columns.length - 1; i >= 0; i--) {
            if (columns[i] < 0) {
                throw new IllegalArgumentException("Index must be >= 0");
            }
        }
    }

    /**
     * Returns a <code>RowFilter</code> that uses a regular
     * expression to determine which entries to include.  Only entries
     * with at least one matching value are included.  For
     * example, the following creates a <code>RowFilter</code> that
     * includes entries with at least one value starting with
     * "a":
     * <pre>
     *   RowFilter.regexFilter("^a");
     * </pre>
     * <p>
     * The returned filter uses {@link java.util.regex.Matcher#find}
     * to test for inclusion.  To test for exact matches use the
     * characters '^' and '$' to match the beginning and end of the
     * string respectively.  For example, "^foo$" includes only rows whose
     * string is exactly "foo" and not, for example, "food".  See
     * {@link java.util.regex.Pattern} for a complete description of
     * the supported regular-expression constructs.
     *
     * <p>
     * 返回一个<code> RowFilter </code>,它使用正则表达式来确定要包括哪些条目。只包括具有至少一个匹配值的条目。
     * 例如,以下创建一个<code> RowFilter </code>,其中包含至少一个以"a"开头的值的条目：。
     * <pre>
     *  RowFilter.regexFilter("^ a");
     * </pre>
     * <p>
     *  返回的过滤器使用{@link java.util.regex.Matcher#find}测试包含。要测试完全匹配,请使用字符"^"和"$"分别匹配字符串的开头和结尾。
     * 例如,"^ foo $"仅包含其字符串正好为"foo"的行,而不包括例如"food"。有关受支持的正则表达式构造的完整描述,请参阅{@link java.util.regex.Pattern}。
     * 
     * 
     * @param regex the regular expression to filter on
     * @param indices the indices of the values to check.  If not supplied all
     *               values are evaluated
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @throws NullPointerException if <code>regex</code> is
     *         <code>null</code>
     * @throws IllegalArgumentException if any of the <code>indices</code>
     *         are &lt; 0
     * @throws PatternSyntaxException if <code>regex</code> is
     *         not a valid regular expression.
     * @see java.util.regex.Pattern
     */
    public static <M,I> RowFilter<M,I> regexFilter(String regex,
                                                       int... indices) {
        return (RowFilter<M,I>)new RegexFilter(Pattern.compile(regex),
                                               indices);
    }

    /**
     * Returns a <code>RowFilter</code> that includes entries that
     * have at least one <code>Date</code> value meeting the specified
     * criteria.  For example, the following <code>RowFilter</code> includes
     * only entries with at least one date value after the current date:
     * <pre>
     *   RowFilter.dateFilter(ComparisonType.AFTER, new Date());
     * </pre>
     *
     * <p>
     *  返回<code> RowFilter </code>,其中包含至少一个符合指定条件的<code> Date </code>值的条目。
     * 例如,以下<code> RowFilter </code>仅包含在当前日期后至少有一个日期值的条目：。
     * <pre>
     *  RowFilter.dateFilter(ComparisonType.AFTER,new Date());
     * </pre>
     * 
     * 
     * @param type the type of comparison to perform
     * @param date the date to compare against
     * @param indices the indices of the values to check.  If not supplied all
     *               values are evaluated
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @throws NullPointerException if <code>date</code> is
     *          <code>null</code>
     * @throws IllegalArgumentException if any of the <code>indices</code>
     *         are &lt; 0 or <code>type</code> is
     *         <code>null</code>
     * @see java.util.Calendar
     * @see java.util.Date
     */
    public static <M,I> RowFilter<M,I> dateFilter(ComparisonType type,
                                            Date date, int... indices) {
        return (RowFilter<M,I>)new DateFilter(type, date.getTime(), indices);
    }

    /**
     * Returns a <code>RowFilter</code> that includes entries that
     * have at least one <code>Number</code> value meeting the
     * specified criteria.  For example, the following
     * filter will only include entries with at
     * least one number value equal to 10:
     * <pre>
     *   RowFilter.numberFilter(ComparisonType.EQUAL, 10);
     * </pre>
     *
     * <p>
     *  返回<code> RowFilter </code>,其中包含至少有一个符合指定条件的<code> Number </code>值的条目。例如,以下过滤器将仅包含至少一个数值等于10的条目：
     * <pre>
     *  RowFilter.numberFilter(ComparisonType.EQUAL,10);
     * </pre>
     * 
     * 
     * @param type the type of comparison to perform
     * @param indices the indices of the values to check.  If not supplied all
     *               values are evaluated
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @throws IllegalArgumentException if any of the <code>indices</code>
     *         are &lt; 0, <code>type</code> is <code>null</code>
     *         or <code>number</code> is <code>null</code>
     */
    public static <M,I> RowFilter<M,I> numberFilter(ComparisonType type,
                                            Number number, int... indices) {
        return (RowFilter<M,I>)new NumberFilter(type, number, indices);
    }

    /**
     * Returns a <code>RowFilter</code> that includes entries if any
     * of the supplied filters includes the entry.
     * <p>
     * The following example creates a <code>RowFilter</code> that will
     * include any entries containing the string "foo" or the string
     * "bar":
     * <pre>
     *   List&lt;RowFilter&lt;Object,Object&gt;&gt; filters = new ArrayList&lt;RowFilter&lt;Object,Object&gt;&gt;(2);
     *   filters.add(RowFilter.regexFilter("foo"));
     *   filters.add(RowFilter.regexFilter("bar"));
     *   RowFilter&lt;Object,Object&gt; fooBarFilter = RowFilter.orFilter(filters);
     * </pre>
     *
     * <p>
     *  如果任何提供的过滤器包含条目,则返回包含条目的<code> RowFilter </code>。
     * <p>
     * 以下示例创建一个<code> RowFilter </code>,它将包含任何包含字符串"foo"或字符串"bar"的条目：
     * <pre>
     *  List&lt; RowFilter&lt; Object,Object&gt;&gt; filters = new ArrayList&lt; RowFilter&lt; Object,Object
     * &gt;&gt;(2); filters.add(RowFilter.regexFilter("foo")); filters.add(RowFilter.regexFilter("bar")); Ro
     * wFilter&lt; Object,Object&gt; fooBarFilter = RowFilter.orFilter(filters);。
     * </pre>
     * 
     * 
     * @param filters the <code>RowFilter</code>s to test
     * @throws IllegalArgumentException if any of the filters
     *         are <code>null</code>
     * @throws NullPointerException if <code>filters</code> is null
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @see java.util.Arrays#asList
     */
    public static <M,I> RowFilter<M,I> orFilter(
            Iterable<? extends RowFilter<? super M, ? super I>> filters) {
        return new OrFilter<M,I>(filters);
    }

    /**
     * Returns a <code>RowFilter</code> that includes entries if all
     * of the supplied filters include the entry.
     * <p>
     * The following example creates a <code>RowFilter</code> that will
     * include any entries containing the string "foo" and the string
     * "bar":
     * <pre>
     *   List&lt;RowFilter&lt;Object,Object&gt;&gt; filters = new ArrayList&lt;RowFilter&lt;Object,Object&gt;&gt;(2);
     *   filters.add(RowFilter.regexFilter("foo"));
     *   filters.add(RowFilter.regexFilter("bar"));
     *   RowFilter&lt;Object,Object&gt; fooBarFilter = RowFilter.andFilter(filters);
     * </pre>
     *
     * <p>
     *  如果所有提供的过滤器都包含条目,则返回包含条目的<code> RowFilter </code>。
     * <p>
     *  以下示例创建一个<code> RowFilter </code>,它将包含任何包含字符串"foo"和字符串"bar"的条目：
     * <pre>
     *  List&lt; RowFilter&lt; Object,Object&gt;&gt; filters = new ArrayList&lt; RowFilter&lt; Object,Object
     * &gt;&gt;(2); filters.add(RowFilter.regexFilter("foo")); filters.add(RowFilter.regexFilter("bar")); Ro
     * wFilter&lt; Object,Object&gt; fooBarFilter = RowFilter.andFilter(filters);。
     * </pre>
     * 
     * 
     * @param filters the <code>RowFilter</code>s to test
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @throws IllegalArgumentException if any of the filters
     *         are <code>null</code>
     * @throws NullPointerException if <code>filters</code> is null
     * @see java.util.Arrays#asList
     */
    public static <M,I> RowFilter<M,I> andFilter(
            Iterable<? extends RowFilter<? super M, ? super I>> filters) {
        return new AndFilter<M,I>(filters);
    }

    /**
     * Returns a <code>RowFilter</code> that includes entries if the
     * supplied filter does not include the entry.
     *
     * <p>
     *  如果提供的过滤器不包含条目,则返回包含条目的<code> RowFilter </code>。
     * 
     * 
     * @param filter the <code>RowFilter</code> to negate
     * @return a <code>RowFilter</code> implementing the specified criteria
     * @throws IllegalArgumentException if <code>filter</code> is
     *         <code>null</code>
     */
    public static <M,I> RowFilter<M,I> notFilter(RowFilter<M,I> filter) {
        return new NotFilter<M,I>(filter);
    }

    /**
     * Returns true if the specified entry should be shown;
     * returns false if the entry should be hidden.
     * <p>
     * The <code>entry</code> argument is valid only for the duration of
     * the invocation.  Using <code>entry</code> after the call returns
     * results in undefined behavior.
     *
     * <p>
     *  如果应显示指定的条目,则返回true;如果条目应该被隐藏,则返回false。
     * <p>
     *  <code>条目</code>参数仅在调用的持续时间有效。在调用返回后使用<code>条目</code>会导致未定义的行为。
     * 
     * 
     * @param entry a non-<code>null</code> object that wraps the underlying
     *              object from the model
     * @return true if the entry should be shown
     */
    public abstract boolean include(Entry<? extends M, ? extends I> entry);

    //
    // WARNING:
    // Because of the method signature of dateFilter/numberFilter/regexFilter
    // we can NEVER add a method to RowFilter that returns M,I. If we were
    // to do so it would be possible to get a ClassCastException during normal
    // usage.
    //

    /**
     * An <code>Entry</code> object is passed to instances of
     * <code>RowFilter</code>, allowing the filter to get the value of the
     * entry's data, and thus to determine whether the entry should be shown.
     * An <code>Entry</code> object contains information about the model
     * as well as methods for getting the underlying values from the model.
     *
     * <p>
     * 将<code> Entry </code>对象传递给<code> RowFilter </code>的实例,允许过滤器获取条目数据的值,从而确定是否应显示该条目。
     *  <code> Entry </code>对象包含有关模型的信息以及从模型中获取基础值的方法。
     * 
     * 
     * @param <M> the type of the model; for example <code>PersonModel</code>
     * @param <I> the type of the identifier; when using
     *            <code>TableRowSorter</code> this will be <code>Integer</code>
     * @see javax.swing.RowFilter
     * @see javax.swing.DefaultRowSorter#setRowFilter(javax.swing.RowFilter)
     * @since 1.6
     */
    public static abstract class Entry<M, I> {
        /**
         * Creates an <code>Entry</code>.
         * <p>
         *  创建<code>条目</code>。
         * 
         */
        public Entry() {
        }

        /**
         * Returns the underlying model.
         *
         * <p>
         *  返回底层模型。
         * 
         * 
         * @return the model containing the data that this entry represents
         */
        public abstract M getModel();

        /**
         * Returns the number of values in the entry.  For
         * example, when used with a table this corresponds to the
         * number of columns.
         *
         * <p>
         *  返回条目中的值的数量。例如,当与表一起使用时,这对应于列数。
         * 
         * 
         * @return number of values in the object being filtered
         */
        public abstract int getValueCount();

        /**
         * Returns the value at the specified index.  This may return
         * <code>null</code>.  When used with a table, index
         * corresponds to the column number in the model.
         *
         * <p>
         *  返回指定索引处的值。这可能返回<code> null </code>。与表一起使用时,索引对应于模型中的列号。
         * 
         * 
         * @param index the index of the value to get
         * @return value at the specified index
         * @throws IndexOutOfBoundsException if index &lt; 0 or
         *         &gt;= getValueCount
         */
        public abstract Object getValue(int index);

        /**
         * Returns the string value at the specified index.  If
         * filtering is being done based on <code>String</code> values
         * this method is preferred to that of <code>getValue</code>
         * as <code>getValue(index).toString()</code> may return a
         * different result than <code>getStringValue(index)</code>.
         * <p>
         * This implementation calls <code>getValue(index).toString()</code>
         * after checking for <code>null</code>.  Subclasses that provide
         * different string conversion should override this method if
         * necessary.
         *
         * <p>
         *  返回指定索引处的字符串值。
         * 如果基于<code> String </code>值进行过滤,则该方法优先于<code> getValue </code>的<code> getValue(index).toString()</code>
         * 不同于<code> getStringValue(index)</code>的结果。
         *  返回指定索引处的字符串值。
         * <p>
         * 
         * @param index the index of the value to get
         * @return {@code non-null} string at the specified index
         * @throws IndexOutOfBoundsException if index &lt; 0 ||
         *         &gt;= getValueCount
         */
        public String getStringValue(int index) {
            Object value = getValue(index);
            return (value == null) ? "" : value.toString();
        }

        /**
         * Returns the identifer (in the model) of the entry.
         * For a table this corresponds to the index of the row in the model,
         * expressed as an <code>Integer</code>.
         *
         * <p>
         *  这个实现在检查<code> null </code>之后调用<code> getValue(index).toString()</code>。提供不同字符串转换的子类必须覆盖此方法。
         * 
         * 
         * @return a model-based (not view-based) identifier for
         *         this entry
         */
        public abstract I getIdentifier();
    }


    private static abstract class GeneralFilter extends RowFilter<Object,Object> {
        private int[] columns;

        GeneralFilter(int[] columns) {
            checkIndices(columns);
            this.columns = columns;
        }

        public boolean include(Entry<? extends Object,? extends Object> value){
            int count = value.getValueCount();
            if (columns.length > 0) {
                for (int i = columns.length - 1; i >= 0; i--) {
                    int index = columns[i];
                    if (index < count) {
                        if (include(value, index)) {
                            return true;
                        }
                    }
                }
            }
            else {
                while (--count >= 0) {
                    if (include(value, count)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected abstract boolean include(
              Entry<? extends Object,? extends Object> value, int index);
    }


    private static class RegexFilter extends GeneralFilter {
        private Matcher matcher;

        RegexFilter(Pattern regex, int[] columns) {
            super(columns);
            if (regex == null) {
                throw new IllegalArgumentException("Pattern must be non-null");
            }
            matcher = regex.matcher("");
        }

        protected boolean include(
                Entry<? extends Object,? extends Object> value, int index) {
            matcher.reset(value.getStringValue(index));
            return matcher.find();
        }
    }


    private static class DateFilter extends GeneralFilter {
        private long date;
        private ComparisonType type;

        DateFilter(ComparisonType type, long date, int[] columns) {
            super(columns);
            if (type == null) {
                throw new IllegalArgumentException("type must be non-null");
            }
            this.type = type;
            this.date = date;
        }

        protected boolean include(
                Entry<? extends Object,? extends Object> value, int index) {
            Object v = value.getValue(index);

            if (v instanceof Date) {
                long vDate = ((Date)v).getTime();
                switch(type) {
                case BEFORE:
                    return (vDate < date);
                case AFTER:
                    return (vDate > date);
                case EQUAL:
                    return (vDate == date);
                case NOT_EQUAL:
                    return (vDate != date);
                default:
                    break;
                }
            }
            return false;
        }
    }




    private static class NumberFilter extends GeneralFilter {
        private boolean isComparable;
        private Number number;
        private ComparisonType type;

        NumberFilter(ComparisonType type, Number number, int[] columns) {
            super(columns);
            if (type == null || number == null) {
                throw new IllegalArgumentException(
                    "type and number must be non-null");
            }
            this.type = type;
            this.number = number;
            isComparable = (number instanceof Comparable);
        }

        @SuppressWarnings("unchecked")
        protected boolean include(
                Entry<? extends Object,? extends Object> value, int index) {
            Object v = value.getValue(index);

            if (v instanceof Number) {
                boolean compared = true;
                int compareResult;
                Class vClass = v.getClass();
                if (number.getClass() == vClass && isComparable) {
                    compareResult = ((Comparable)number).compareTo(v);
                }
                else {
                    compareResult = longCompare((Number)v);
                }
                switch(type) {
                case BEFORE:
                    return (compareResult > 0);
                case AFTER:
                    return (compareResult < 0);
                case EQUAL:
                    return (compareResult == 0);
                case NOT_EQUAL:
                    return (compareResult != 0);
                default:
                    break;
                }
            }
            return false;
        }

        private int longCompare(Number o) {
            long diff = number.longValue() - o.longValue();

            if (diff < 0) {
                return -1;
            }
            else if (diff > 0) {
                return 1;
            }
            return 0;
        }
    }


    private static class OrFilter<M,I> extends RowFilter<M,I> {
        List<RowFilter<? super M,? super I>> filters;

        OrFilter(Iterable<? extends RowFilter<? super M, ? super I>> filters) {
            this.filters = new ArrayList<RowFilter<? super M,? super I>>();
            for (RowFilter<? super M, ? super I> filter : filters) {
                if (filter == null) {
                    throw new IllegalArgumentException(
                        "Filter must be non-null");
                }
                this.filters.add(filter);
            }
        }

        public boolean include(Entry<? extends M, ? extends I> value) {
            for (RowFilter<? super M,? super I> filter : filters) {
                if (filter.include(value)) {
                    return true;
                }
            }
            return false;
        }
    }


    private static class AndFilter<M,I> extends OrFilter<M,I> {
        AndFilter(Iterable<? extends RowFilter<? super M,? super I>> filters) {
            super(filters);
        }

        public boolean include(Entry<? extends M, ? extends I> value) {
            for (RowFilter<? super M,? super I> filter : filters) {
                if (!filter.include(value)) {
                    return false;
                }
            }
            return true;
        }
    }


    private static class NotFilter<M,I> extends RowFilter<M,I> {
        private RowFilter<M,I> filter;

        NotFilter(RowFilter<M,I> filter) {
            if (filter == null) {
                throw new IllegalArgumentException(
                    "filter must be non-null");
            }
            this.filter = filter;
        }

        public boolean include(Entry<? extends M, ? extends I> value) {
            return !filter.include(value);
        }
    }
}
