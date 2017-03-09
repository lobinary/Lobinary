/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import java.util.Vector;

/**
 * Class SetOfIntegerSyntax is an abstract base class providing the common
 * implementation of all attributes whose value is a set of nonnegative
 * integers. This includes attributes whose value is a single range of integers
 * and attributes whose value is a set of ranges of integers.
 * <P>
 * You can construct an instance of SetOfIntegerSyntax by giving it in "string
 * form." The string consists of zero or more comma-separated integer groups.
 * Each integer group consists of either one integer, two integers separated by
 * a hyphen (<CODE>-</CODE>), or two integers separated by a colon
 * (<CODE>:</CODE>). Each integer consists of one or more decimal digits
 * (<CODE>0</CODE> through <CODE>9</CODE>). Whitespace characters cannot
 * appear within an integer but are otherwise ignored. For example:
 * <CODE>""</CODE>, <CODE>"1"</CODE>, <CODE>"5-10"</CODE>, <CODE>"1:2,
 * 4"</CODE>.
 * <P>
 * You can also construct an instance of SetOfIntegerSyntax by giving it in
 * "array form." Array form consists of an array of zero or more integer groups
 * where each integer group is a length-1 or length-2 array of
 * <CODE>int</CODE>s; for example, <CODE>int[0][]</CODE>,
 * <CODE>int[][]{{1}}</CODE>, <CODE>int[][]{{5,10}}</CODE>,
 * <CODE>int[][]{{1,2},{4}}</CODE>.
 * <P>
 * In both string form and array form, each successive integer group gives a
 * range of integers to be included in the set. The first integer in each group
 * gives the lower bound of the range; the second integer in each group gives
 * the upper bound of the range; if there is only one integer in the group, the
 * upper bound is the same as the lower bound. If the upper bound is less than
 * the lower bound, it denotes a null range (no values). If the upper bound is
 * equal to the lower bound, it denotes a range consisting of a single value. If
 * the upper bound is greater than the lower bound, it denotes a range
 * consisting of more than one value. The ranges may appear in any order and are
 * allowed to overlap. The union of all the ranges gives the set's contents.
 * Once a SetOfIntegerSyntax instance is constructed, its value is immutable.
 * <P>
 * The SetOfIntegerSyntax object's value is actually stored in "<I>canonical</I>
 * array form." This is the same as array form, except there are no null ranges;
 * the members of the set are represented in as few ranges as possible (i.e.,
 * overlapping ranges are coalesced); the ranges appear in ascending order; and
 * each range is always represented as a length-two array of <CODE>int</CODE>s
 * in the form {lower bound, upper bound}. An empty set is represented as a
 * zero-length array.
 * <P>
 * Class SetOfIntegerSyntax has operations to return the set's members in
 * canonical array form, to test whether a given integer is a member of the
 * set, and to iterate through the members of the set.
 * <P>
 *
 * <p>
 *  类SetOfIntegerSyntax是一个抽象基类,提供其值为一组非负整数的所有属性的公共实现。这包括其值为单个整数范围的属性和其值为一组整数范围的属性。
 * <P>
 *  您可以通过以"字符串形式"来构造SetOfIntegerSyntax的实例。该字符串由零个或多个逗号分隔的整数组组成。
 * 每个整数组由一个整数,由连字符(<CODE>  -  </CODE>)分隔的两个整数或由冒号(<CODE>：</CODE>)分隔的两个整数组成。
 * 每个整数由一个或多个十进制数字(<CODE> 0 </CODE>至<CODE> 9 </CODE>)组成。空格字符不能出现在整数内,否则会被忽略。
 * 例如：<CODE>""</CODE>,<CODE>"1"</CODE>,<CODE>"5-10"</CODE>,<CODE>"1：2,4" >。
 * <P>
 *  你也可以通过以"数组形式"来构造SetOfIntegerSyntax的实例。
 * 数组形式由零个或多个整数组组成,其中每个整数组是<CODE> int </CODE>的长度为1或长度为2的数组;例如,<CODE> int [0] [] </CODE>,<CODE> int [] []
 *  {{1}} </CODE>,<CODE> int [] [] {{5,10}} </CODE>,<CODE> int [] [] {{1,2},{4}} </CODE>。
 *  你也可以通过以"数组形式"来构造SetOfIntegerSyntax的实例。
 * <P>
 * 在字符串形式和数组形式中,每个连续的整数组给出要包括在集合中的整数范围。每组中的第一个整数给出范围的下限;每组中的第二个整数给出了范围的上限;如果组中只有一个整数,则上限与下限相同。
 * 如果上限小于下限,则其表示空范围(无值)。如果上限等于下限,则它表示由单个值组成的范围。如果上限大于下限,则表示由多于一个值组成的范围。范围可以以任何顺序出现并且允许重叠。
 * 所有范围的并集给出集合的内容。一旦构造了SetOfIntegerSyntax实例,其值是不可变的。
 * <P>
 *  SetOfIntegerSyntax对象的值实际上存储在"<I>规范</I>数组形式"中。
 * 这与数组形式相同,除了没有空值范围;该集合的成员在尽可能少的范围内表示(即,重叠范围合并);范围以升序显示;并且每个范围总是表示为{下限,上界}形式的<CODE> int </CODE>的长度二数组。
 * 空集表示为零长度数组。
 * <P>
 * SetOfIntegerSyntax类具有返回规范数组形式的集合成员的操作,以测试给定整数是否是集合的成员,并遍历集合的成员。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public abstract class SetOfIntegerSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = 3666874174847632203L;

    /**
     * This set's members in canonical array form.
     * <p>
     *  这个集合的成员是规范数组形式。
     * 
     * 
     * @serial
     */
    private int[][] members;


    /**
     * Construct a new set-of-integer attribute with the given members in
     * string form.
     *
     * <p>
     *  使用字符串形式的给定成员构造一个新的整数集合属性。
     * 
     * 
     * @param  members  Set members in string form. If null, an empty set is
     *                     constructed.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>members</CODE> does not
     *    obey  the proper syntax.
     */
    protected SetOfIntegerSyntax(String members) {
        this.members = parse (members);
    }

    /**
     * Parse the given string, returning canonical array form.
     * <p>
     *  解析给定的字符串,返回规范数组形式。
     * 
     */
    private static int[][] parse(String members) {
        // Create vector to hold int[] elements, each element being one range
        // parsed out of members.
        Vector theRanges = new Vector();

        // Run state machine over members.
        int n = (members == null ? 0 : members.length());
        int i = 0;
        int state = 0;
        int lb = 0;
        int ub = 0;
        char c;
        int digit;
        while (i < n) {
            c = members.charAt(i ++);
            switch (state) {

            case 0: // Before first integer in first group
                if (Character.isWhitespace(c)) {
                    state = 0;
                }
                else if ((digit = Character.digit(c, 10)) != -1) {
                    lb = digit;
                    state = 1;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 1: // In first integer in a group
                if (Character.isWhitespace(c)){
                        state = 2;
                } else if ((digit = Character.digit(c, 10)) != -1) {
                    lb = 10 * lb + digit;
                    state = 1;
                } else if (c == '-' || c == ':') {
                    state = 3;
                } else if (c == ',') {
                    accumulate (theRanges, lb, lb);
                    state = 6;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 2: // After first integer in a group
                if (Character.isWhitespace(c)) {
                    state = 2;
                }
                else if (c == '-' || c == ':') {
                    state = 3;
                }
                else if (c == ',') {
                    accumulate(theRanges, lb, lb);
                    state = 6;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 3: // Before second integer in a group
                if (Character.isWhitespace(c)) {
                    state = 3;
                } else if ((digit = Character.digit(c, 10)) != -1) {
                    ub = digit;
                    state = 4;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 4: // In second integer in a group
                if (Character.isWhitespace(c)) {
                    state = 5;
                } else if ((digit = Character.digit(c, 10)) != -1) {
                    ub = 10 * ub + digit;
                    state = 4;
                } else if (c == ',') {
                    accumulate(theRanges, lb, ub);
                    state = 6;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 5: // After second integer in a group
                if (Character.isWhitespace(c)) {
                    state = 5;
                } else if (c == ',') {
                    accumulate(theRanges, lb, ub);
                    state = 6;
                } else {
                    throw new IllegalArgumentException();
                }
                break;

            case 6: // Before first integer in second or later group
                if (Character.isWhitespace(c)) {
                    state = 6;
                } else if ((digit = Character.digit(c, 10)) != -1) {
                    lb = digit;
                    state = 1;
                } else {
                    throw new IllegalArgumentException();
                }
                break;
            }
        }

        // Finish off the state machine.
        switch (state) {
        case 0: // Before first integer in first group
            break;
        case 1: // In first integer in a group
        case 2: // After first integer in a group
            accumulate(theRanges, lb, lb);
            break;
        case 4: // In second integer in a group
        case 5: // After second integer in a group
            accumulate(theRanges, lb, ub);
            break;
        case 3: // Before second integer in a group
        case 6: // Before first integer in second or later group
            throw new IllegalArgumentException();
        }

        // Return canonical array form.
        return canonicalArrayForm (theRanges);
    }

    /**
     * Accumulate the given range (lb .. ub) into the canonical array form
     * into the given vector of int[] objects.
     * <p>
     *  将规范数组形式的给定范围(lb .. ub)累加到int []对象的给定向量中。
     * 
     */
    private static void accumulate(Vector ranges, int lb,int ub) {
        // Make sure range is non-null.
        if (lb <= ub) {
            // Stick range at the back of the vector.
            ranges.add(new int[] {lb, ub});

            // Work towards the front of the vector to integrate the new range
            // with the existing ranges.
            for (int j = ranges.size()-2; j >= 0; -- j) {
            // Get lower and upper bounds of the two ranges being compared.
                int[] rangea = (int[]) ranges.elementAt (j);
                int lba = rangea[0];
                int uba = rangea[1];
                int[] rangeb = (int[]) ranges.elementAt (j+1);
                int lbb = rangeb[0];
                int ubb = rangeb[1];

                /* If the two ranges overlap or are adjacent, coalesce them.
                 * The two ranges overlap if the larger lower bound is less
                 * than or equal to the smaller upper bound. The two ranges
                 * are adjacent if the larger lower bound is one greater
                 * than the smaller upper bound.
                 * <p>
                 *  如果较大的下限小于或等于较小的上限,则两个范围重叠。如果较大的下边界比较小的上边界大一个,则两个范围是相邻的。
                 * 
                 */
                if (Math.max(lba, lbb) - Math.min(uba, ubb) <= 1) {
                    // The coalesced range is from the smaller lower bound to
                    // the larger upper bound.
                    ranges.setElementAt(new int[]
                                           {Math.min(lba, lbb),
                                                Math.max(uba, ubb)}, j);
                    ranges.remove (j+1);
                } else if (lba > lbb) {

                    /* If the two ranges don't overlap and aren't adjacent but
                     * are out of order, swap them.
                     * <p>
                     *  是无序的,交换他们。
                     * 
                     */
                    ranges.setElementAt (rangeb, j);
                    ranges.setElementAt (rangea, j+1);
                } else {
                /* If the two ranges don't overlap and aren't adjacent and
                 * aren't out of order, we're done early.
                 * <p>
                 *  不是故障,我们提前完成。
                 * 
                 */
                    break;
                }
            }
        }
    }

    /**
     * Convert the given vector of int[] objects to canonical array form.
     * <p>
     *  将给定的int []对象向量转换为规范数组形式。
     * 
     */
    private static int[][] canonicalArrayForm(Vector ranges) {
        return (int[][]) ranges.toArray (new int[ranges.size()][]);
    }

    /**
     * Construct a new set-of-integer attribute with the given members in
     * array form.
     *
     * <p>
     *  使用数组形式的给定成员构造一个新的整数集合属性。
     * 
     * 
     * @param  members  Set members in array form. If null, an empty set is
     *                     constructed.
     *
     * @exception  NullPointerException
     *     (Unchecked exception) Thrown if any element of
     *     <CODE>members</CODE> is null.
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if any element of
     *     <CODE>members</CODE> is not a length-one or length-two array or if
     *     any non-null range in <CODE>members</CODE> has a lower bound less
     *     than zero.
     */
    protected SetOfIntegerSyntax(int[][] members) {
        this.members = parse (members);
    }

    /**
     * Parse the given array form, returning canonical array form.
     * <p>
     *  解析给定的数组形式,返回规范数组形式。
     * 
     */
    private static int[][] parse(int[][] members) {
        // Create vector to hold int[] elements, each element being one range
        // parsed out of members.
        Vector ranges = new Vector();

        // Process all integer groups in members.
        int n = (members == null ? 0 : members.length);
        for (int i = 0; i < n; ++ i) {
            // Get lower and upper bounds of the range.
            int lb, ub;
            if (members[i].length == 1) {
                lb = ub = members[i][0];
            } else if (members[i].length == 2) {
                lb = members[i][0];
                ub = members[i][1];
            } else {
                throw new IllegalArgumentException();
            }

            // Verify valid bounds.
            if (lb <= ub && lb < 0) {
                throw new IllegalArgumentException();
            }

            // Accumulate the range.
            accumulate(ranges, lb, ub);
        }

                // Return canonical array form.
                return canonicalArrayForm (ranges);
                }

    /**
     * Construct a new set-of-integer attribute containing a single integer.
     *
     * <p>
     *  构造一个包含单个整数的新的整数集合属性。
     * 
     * 
     * @param  member  Set member.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>member</CODE> is less than
     *     zero.
     */
    protected SetOfIntegerSyntax(int member) {
        if (member < 0) {
            throw new IllegalArgumentException();
        }
        members = new int[][] {{member, member}};
    }

    /**
     * Construct a new set-of-integer attribute containing a single range of
     * integers. If the lower bound is greater than the upper bound (a null
     * range), an empty set is constructed.
     *
     * <p>
     *  构造一个包含单个整数范围的新的整数集合属性。如果下限大于上限(空范围),则构造空集合。
     * 
     * 
     * @param  lowerBound  Lower bound of the range.
     * @param  upperBound  Upper bound of the range.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if the range is non-null and
     *     <CODE>lowerBound</CODE> is less than zero.
     */
    protected SetOfIntegerSyntax(int lowerBound, int upperBound) {
        if (lowerBound <= upperBound && lowerBound < 0) {
            throw new IllegalArgumentException();
        }
        members = lowerBound <=upperBound ?
            new int[][] {{lowerBound, upperBound}} :
            new int[0][];
    }


    /**
     * Obtain this set-of-integer attribute's members in canonical array form.
     * The returned array is "safe;" the client may alter it without affecting
     * this set-of-integer attribute.
     *
     * <p>
     *  以规范数组形式获取此整数集合属性的成员。返回的数组是"safe";客户端可以更改它而不影响此整数集合属性。
     * 
     * 
     * @return  This set-of-integer attribute's members in canonical array form.
     */
    public int[][] getMembers() {
        int n = members.length;
        int[][] result = new int[n][];
        for (int i = 0; i < n; ++ i) {
            result[i] = new int[] {members[i][0], members[i][1]};
        }
        return result;
    }

    /**
     * Determine if this set-of-integer attribute contains the given value.
     *
     * <p>
     *  确定此整数集合属性是否包含给定值。
     * 
     * 
     * @param  x  Integer value.
     *
     * @return  True if this set-of-integer attribute contains the value
     *          <CODE>x</CODE>, false otherwise.
     */
    public boolean contains(int x) {
        // Do a linear search to find the range that contains x, if any.
        int n = members.length;
        for (int i = 0; i < n; ++ i) {
            if (x < members[i][0]) {
                return false;
            } else if (x <= members[i][1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if this set-of-integer attribute contains the given integer
     * attribute's value.
     *
     * <p>
     * 确定此整数集合属性是否包含给定的整数属性的值。
     * 
     * 
     * @param  attribute  Integer attribute.
     *
     * @return  True if this set-of-integer attribute contains
     *          <CODE>theAttribute</CODE>'s value, false otherwise.
     */
    public boolean contains(IntegerSyntax attribute) {
        return contains (attribute.getValue());
    }

    /**
     * Determine the smallest integer in this set-of-integer attribute that is
     * greater than the given value. If there are no integers in this
     * set-of-integer attribute greater than the given value, <CODE>-1</CODE> is
     * returned. (Since a set-of-integer attribute can only contain nonnegative
     * values, <CODE>-1</CODE> will never appear in the set.) You can use the
     * <CODE>next()</CODE> method to iterate through the integer values in a
     * set-of-integer attribute in ascending order, like this:
     * <PRE>
     *     SetOfIntegerSyntax attribute = . . .;
     *     int i = -1;
     *     while ((i = attribute.next (i)) != -1)
     *         {
     *         foo (i);
     *         }
     * </PRE>
     *
     * <p>
     *  确定此整数集合属性中大于给定值的最小整数。如果此整数集合属性中没有大于给定值的整数,则返回<CODE> -1 </CODE>。
     *  (由于整数集合属性只能包含非负值,所以<CODE> -1 </CODE>将不会出现在集合中。)您可以使用<CODE> next()</CODE>整数值集合中的整数值按升序排序,如下所示：。
     * <PRE>
     *  SetOfIntegerSyntax attribute =。 。 。 int i = -1; while((i = attribute.next(i))！=  -  1){foo(i); }}
     * </PRE>
     * 
     * 
     * @param  x  Integer value.
     *
     * @return  The smallest integer in this set-of-integer attribute that is
     *          greater than <CODE>x</CODE>, or <CODE>-1</CODE> if no integer in
     *          this set-of-integer attribute is greater than <CODE>x</CODE>.
     */
    public int next(int x) {
        // Do a linear search to find the range that contains x, if any.
        int n = members.length;
        for (int i = 0; i < n; ++ i) {
            if (x < members[i][0]) {
                return members[i][0];
            } else if (x < members[i][1]) {
                return x + 1;
            }
        }
        return -1;
    }

    /**
     * Returns whether this set-of-integer attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class SetOfIntegerSyntax.
     * <LI>
     * This set-of-integer attribute's members and <CODE>object</CODE>'s
     * members are the same.
     * </OL>
     *
     * <p>
     *  返回此整数集合属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是SetOfIntegerSyntax类的实例。
     * <LI>
     *  这个整数集合属性的成员和<CODE>对象</CODE>的成员是相同的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this
     *          set-of-integer attribute, false otherwise.
     */
    public boolean equals(Object object) {
        if (object != null && object instanceof SetOfIntegerSyntax) {
            int[][] myMembers = this.members;
            int[][] otherMembers = ((SetOfIntegerSyntax) object).members;
            int m = myMembers.length;
            int n = otherMembers.length;
            if (m == n) {
                for (int i = 0; i < m; ++ i) {
                    if (myMembers[i][0] != otherMembers[i][0] ||
                        myMembers[i][1] != otherMembers[i][1]) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns a hash code value for this set-of-integer attribute. The hash
     * code is the sum of the lower and upper bounds of the ranges in the
     * canonical array form, or 0 for an empty set.
     * <p>
     */
    public int hashCode() {
        int result = 0;
        int n = members.length;
        for (int i = 0; i < n; ++ i) {
            result += members[i][0] + members[i][1];
        }
        return result;
    }

    /**
     * Returns a string value corresponding to this set-of-integer attribute.
     * The string value is a zero-length string if this set is empty. Otherwise,
     * the string value is a comma-separated list of the ranges in the canonical
     * array form, where each range is represented as <CODE>"<I>i</I>"</CODE> if
     * the lower bound equals the upper bound or
     * <CODE>"<I>i</I>-<I>j</I>"</CODE> otherwise.
     * <p>
     *  返回此整数集合属性的哈希码值。哈希码是规范数组形式范围的下限和上限的总和,对于空集,为0。
     * 
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        int n = members.length;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                result.append (',');
            }
            result.append (members[i][0]);
            if (members[i][0] != members[i][1]) {
                result.append ('-');
                result.append (members[i][1]);
            }
        }
        return result.toString();
    }

}
