/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util;

/**
 * {@code StringJoiner} is used to construct a sequence of characters separated
 * by a delimiter and optionally starting with a supplied prefix
 * and ending with a supplied suffix.
 * <p>
 * Prior to adding something to the {@code StringJoiner}, its
 * {@code sj.toString()} method will, by default, return {@code prefix + suffix}.
 * However, if the {@code setEmptyValue} method is called, the {@code emptyValue}
 * supplied will be returned instead. This can be used, for example, when
 * creating a string using set notation to indicate an empty set, i.e.
 * <code>"{}"</code>, where the {@code prefix} is <code>"{"</code>, the
 * {@code suffix} is <code>"}"</code> and nothing has been added to the
 * {@code StringJoiner}.
 *
 * @apiNote
 * <p>The String {@code "[George:Sally:Fred]"} may be constructed as follows:
 *
 * <pre> {@code
 * StringJoiner sj = new StringJoiner(":", "[", "]");
 * sj.add("George").add("Sally").add("Fred");
 * String desiredString = sj.toString();
 * }</pre>
 * <p>
 * A {@code StringJoiner} may be employed to create formatted output from a
 * {@link java.util.stream.Stream} using
 * {@link java.util.stream.Collectors#joining(CharSequence)}. For example:
 *
 * <pre> {@code
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
 * String commaSeparatedNumbers = numbers.stream()
 *     .map(i -> i.toString())
 *     .collect(Collectors.joining(", "));
 * }</pre>
 *
 * <p>
 *  {@code StringJoiner}用于构造由定界符分隔的字符序列,并且可选地从提供的前缀开始并以提供的后缀结尾。
 * <p>
 *  在向{@code StringJoiner}添加内容之前,其{@code sj.toString()}方法将默认返回{@code prefix + suffix}。
 * 但是,如果调用{@code setEmptyValue}方法,将返回提供的{@code emptyValue}。
 * 例如,当使用set notation创建一个字符串以指示一个空集合,即<code>"{}"</code>时,可以使用这种方法,其中{@code prefix}是<code>"{"< code>,{@code suffix}
 * 是<code>"}"</code>,并且没有向{@code StringJoiner}中添加任何内容。
 * 但是,如果调用{@code setEmptyValue}方法,将返回提供的{@code emptyValue}。
 * 
 *  @apiNote <p>字符串{@code"[George：Sally：Fred]"}可以构造如下：
 * 
 *  <pre> {@code StringJoiner sj = new StringJoiner("：","[","]"); sj.add("George")。add("Sally")。
 * add("Fred"); String desiredString = sj.toString(); } </pre>。
 * <p>
 *  可以使用{@code StringJoiner}来使用{@link java.util.stream.Collectors#joining(CharSequence)}从{@link java.util.stream.Stream}
 * 创建格式化的输出。
 * 例如：。
 * 
 *  <pre> {@code List <Integer> numbers = Arrays.asList(1,2,3,4); String commaSeparatedNumbers = numbers.stream().map(i  - > i.toString()).collect(Collectors.joining(",")); }
 *  </pre>。
 * 
 * 
 * @see java.util.stream.Collectors#joining(CharSequence)
 * @see java.util.stream.Collectors#joining(CharSequence, CharSequence, CharSequence)
 * @since  1.8
*/
public final class StringJoiner {
    private final String prefix;
    private final String delimiter;
    private final String suffix;

    /*
     * StringBuilder value -- at any time, the characters constructed from the
     * prefix, the added element separated by the delimiter, but without the
     * suffix, so that we can more easily add elements without having to jigger
     * the suffix each time.
     * <p>
     * StringBuilder值 - 在任何时候,从前缀构造的字符,添加的元素由分隔符分隔,但没有后缀,以便我们可以更容易地添加元素,而不必每次都跳过后缀。
     * 
     */
    private StringBuilder value;

    /*
     * By default, the string consisting of prefix+suffix, returned by
     * toString(), or properties of value, when no elements have yet been added,
     * i.e. when it is empty.  This may be overridden by the user to be some
     * other value including the empty String.
     * <p>
     *  默认情况下,当没有元素被添加时,即当它为空时,由前缀+后缀,由toString()返回的字符串或值的属性。这可能被用户覆盖为包括空字符串的某个其他值。
     * 
     */
    private String emptyValue;

    /**
     * Constructs a {@code StringJoiner} with no characters in it, with no
     * {@code prefix} or {@code suffix}, and a copy of the supplied
     * {@code delimiter}.
     * If no characters are added to the {@code StringJoiner} and methods
     * accessing the value of it are invoked, it will not return a
     * {@code prefix} or {@code suffix} (or properties thereof) in the result,
     * unless {@code setEmptyValue} has first been called.
     *
     * <p>
     *  构造一个不带任何字符的{@code StringJoiner},不带任何{@code prefix}或{@code suffix},以及提供的{@code delimiter}副本。
     * 如果没有向{@code StringJoiner}中添加字符,并且访问它的值的方法被调用,它将不会在结果中返回{@code prefix}或{@code suffix}(或其属性),除非{@代码setEmptyValue}
     * 已被第一次调用。
     *  构造一个不带任何字符的{@code StringJoiner},不带任何{@code prefix}或{@code suffix},以及提供的{@code delimiter}副本。
     * 
     * 
     * @param  delimiter the sequence of characters to be used between each
     *         element added to the {@code StringJoiner} value
     * @throws NullPointerException if {@code delimiter} is {@code null}
     */
    public StringJoiner(CharSequence delimiter) {
        this(delimiter, "", "");
    }

    /**
     * Constructs a {@code StringJoiner} with no characters in it using copies
     * of the supplied {@code prefix}, {@code delimiter} and {@code suffix}.
     * If no characters are added to the {@code StringJoiner} and methods
     * accessing the string value of it are invoked, it will return the
     * {@code prefix + suffix} (or properties thereof) in the result, unless
     * {@code setEmptyValue} has first been called.
     *
     * <p>
     *  使用提供的{@code prefix},{@code delimiter}和{@code suffix}的副本构造一个不带任何字符的{@code StringJoiner}。
     * 如果没有向{@code StringJoiner}添加字符,并且访问它的字符串值的方法被调用,它将返回结果中的{@code prefix + suffix}(或其属性),除非{@code setEmptyValue}
     * 第一次被叫。
     *  使用提供的{@code prefix},{@code delimiter}和{@code suffix}的副本构造一个不带任何字符的{@code StringJoiner}。
     * 
     * 
     * @param  delimiter the sequence of characters to be used between each
     *         element added to the {@code StringJoiner}
     * @param  prefix the sequence of characters to be used at the beginning
     * @param  suffix the sequence of characters to be used at the end
     * @throws NullPointerException if {@code prefix}, {@code delimiter}, or
     *         {@code suffix} is {@code null}
     */
    public StringJoiner(CharSequence delimiter,
                        CharSequence prefix,
                        CharSequence suffix) {
        Objects.requireNonNull(prefix, "The prefix must not be null");
        Objects.requireNonNull(delimiter, "The delimiter must not be null");
        Objects.requireNonNull(suffix, "The suffix must not be null");
        // make defensive copies of arguments
        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
    }

    /**
     * Sets the sequence of characters to be used when determining the string
     * representation of this {@code StringJoiner} and no elements have been
     * added yet, that is, when it is empty.  A copy of the {@code emptyValue}
     * parameter is made for this purpose. Note that once an add method has been
     * called, the {@code StringJoiner} is no longer considered empty, even if
     * the element(s) added correspond to the empty {@code String}.
     *
     * <p>
     * 设置确定此{@code StringJoiner}的字符串表示形式时使用的字符序列,并且尚未添加任何元素,即其为空时。为此目的创建{@code emptyValue}参数的副本。
     * 注意,一旦调用了add方法,{@code StringJoiner}不再被认为是空的,即使添加的元素对应于空的{@code String}。
     * 
     * 
     * @param  emptyValue the characters to return as the value of an empty
     *         {@code StringJoiner}
     * @return this {@code StringJoiner} itself so the calls may be chained
     * @throws NullPointerException when the {@code emptyValue} parameter is
     *         {@code null}
     */
    public StringJoiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = Objects.requireNonNull(emptyValue,
            "The empty value must not be null").toString();
        return this;
    }

    /**
     * Returns the current value, consisting of the {@code prefix}, the values
     * added so far separated by the {@code delimiter}, and the {@code suffix},
     * unless no elements have been added in which case, the
     * {@code prefix + suffix} or the {@code emptyValue} characters are returned
     *
     * <p>
     *  返回由{@code prefix},到目前为止由{@code delimiter}和{@code suffix}分隔的值组成的当前值,除非在这种情况下没有添加元素,{@code前缀+后缀}或{@code emptyValue}
     * 字符。
     * 
     * 
     * @return the string representation of this {@code StringJoiner}
     */
    @Override
    public String toString() {
        if (value == null) {
            return emptyValue;
        } else {
            if (suffix.equals("")) {
                return value.toString();
            } else {
                int initialLength = value.length();
                String result = value.append(suffix).toString();
                // reset value to pre-append initialLength
                value.setLength(initialLength);
                return result;
            }
        }
    }

    /**
     * Adds a copy of the given {@code CharSequence} value as the next
     * element of the {@code StringJoiner} value. If {@code newElement} is
     * {@code null}, then {@code "null"} is added.
     *
     * <p>
     *  将给定的{@code CharSequence}值的副本添加为{@code StringJoiner}值的下一个元素。
     * 如果{@code newElement}是{@code null},则会添加{@code"null"}。
     * 
     * 
     * @param  newElement The element to add
     * @return a reference to this {@code StringJoiner}
     */
    public StringJoiner add(CharSequence newElement) {
        prepareBuilder().append(newElement);
        return this;
    }

    /**
     * Adds the contents of the given {@code StringJoiner} without prefix and
     * suffix as the next element if it is non-empty. If the given {@code
     * StringJoiner} is empty, the call has no effect.
     *
     * <p>A {@code StringJoiner} is empty if {@link #add(CharSequence) add()}
     * has never been called, and if {@code merge()} has never been called
     * with a non-empty {@code StringJoiner} argument.
     *
     * <p>If the other {@code StringJoiner} is using a different delimiter,
     * then elements from the other {@code StringJoiner} are concatenated with
     * that delimiter and the result is appended to this {@code StringJoiner}
     * as a single element.
     *
     * <p>
     *  将不带前缀和后缀的给定{@code StringJoiner}的内容作为下一个元素(如果它是非空的)添加。如果给定的{@code StringJoiner}为空,则调用不起作用。
     * 
     *  <p>如果{@link #add(CharSequence)add()}从未被调用,并且{@code merge()}从未被非空{@code}调用,则{@code StringJoiner} Stri
     * ngJoiner}参数。
     * 
     *  <p>如果其他{@code StringJoiner}使用不同的分隔符,则来自其他{@code StringJoiner}的元素将与该分隔符连接,并将结果作为单个元素附加到此{@code StringJoiner}
     * 。
     * 
     * @param other The {@code StringJoiner} whose contents should be merged
     *              into this one
     * @throws NullPointerException if the other {@code StringJoiner} is null
     * @return This {@code StringJoiner}
     */
    public StringJoiner merge(StringJoiner other) {
        Objects.requireNonNull(other);
        if (other.value != null) {
            final int length = other.value.length();
            // lock the length so that we can seize the data to be appended
            // before initiate copying to avoid interference, especially when
            // merge 'this'
            StringBuilder builder = prepareBuilder();
            builder.append(other.value, other.prefix.length(), length);
        }
        return this;
    }

    private StringBuilder prepareBuilder() {
        if (value != null) {
            value.append(delimiter);
        } else {
            value = new StringBuilder().append(prefix);
        }
        return value;
    }

    /**
     * Returns the length of the {@code String} representation
     * of this {@code StringJoiner}. Note that if
     * no add methods have been called, then the length of the {@code String}
     * representation (either {@code prefix + suffix} or {@code emptyValue})
     * will be returned. The value should be equivalent to
     * {@code toString().length()}.
     *
     * <p>
     * 
     * 
     * @return the length of the current value of {@code StringJoiner}
     */
    public int length() {
        // Remember that we never actually append the suffix unless we return
        // the full (present) value or some sub-string or length of it, so that
        // we can add on more if we need to.
        return (value != null ? value.length() + suffix.length() :
                emptyValue.length());
    }
}
