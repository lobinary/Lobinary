/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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


package javax.naming.directory;

/**
  * This class encapsulates
  * factors that determine scope of search and what gets returned
  * as a result of the search.
  *<p>
  * A SearchControls instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single SearchControls instance should lock the object.
  *
  * <p>
  *  此类封装了确定搜索范围的因素以及作为搜索结果返回的内容。
  * p>
  *  SearchControls实例未与并发多线程访问同步。尝试访问和修改单个SearchControls实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class SearchControls implements java.io.Serializable {
    /**
     * Search the named object.
     *<p>
     * The NamingEnumeration that results from search()
     * using OBJECT_SCOPE will contain one or zero element.
     * The enumeration contains one element if the named object satisfies
     * the search filter specified in search().
     * The element will have as its name the empty string because the names
     * of elements in the NamingEnumeration are relative to the
     * target context--in this case, the target context is the named object.
     * It contains zero element if the named object does not satisfy
     * the search filter specified in search().
     * <p>
     * The value of this constant is <tt>0</tt>.
     * <p>
     *  搜索命名对象。
     * p>
     *  使用OBJECT_SCOPE从search()得到的NamingEnumeration将包含一个或零个元素。如果命名对象满足search()中指定的搜索过滤器,则枚举包含一个元素。
     * 该元素将具有作为其名称的空字符串,因为NamingEnumeration中的元素的名称是相对于目标上下文的 - 在这种情况下,目标上下文是命名对象。
     * 如果命名对象不满足search()中指定的搜索过滤器,则它包含零元素。
     * <p>
     *  此常数的值为<tt> 0 </tt>。
     * 
     */
    public final static int OBJECT_SCOPE = 0;

    /**
     * Search one level of the named context.
     *<p>
     * The NamingEnumeration that results from search()
     * using ONELEVEL_SCOPE contains elements with
     * objects in the named context that satisfy
     * the search filter specified in search().
     * The names of elements in the NamingEnumeration are atomic names
     * relative to the named context.
     * <p>
     * The value of this constant is <tt>1</tt>.
     * <p>
     *  搜索命名上下文的一个级别。
     * p>
     *  使用ONELEVEL_SCOPE从search()得到的NamingEnumeration包含具有指定上下文中满足search()中指定的搜索过滤器的对象的元素。
     *  NamingEnumeration中的元素名称是相对于命名上下文的原子名称。
     * <p>
     *  此常数的值为<tt> 1 </tt>。
     * 
     */
    public final static int ONELEVEL_SCOPE = 1;
    /**
     * Search the entire subtree rooted at the named object.
     *<p>
     * If the named object is not a DirContext, search only the object.
     * If the named object is a DirContext, search the subtree
     * rooted at the named object, including the named object itself.
     *<p>
     * The search will not cross naming system boundaries.
     *<p>
     * The NamingEnumeration that results from search()
     * using SUBTREE_SCOPE contains elements of objects
     * from the subtree (including the named context)
     * that satisfy the search filter specified in search().
     * The names of elements in the NamingEnumeration are either
     * relative to the named context or is a URL string.
     * If the named context satisfies the search filter, it is
     * included in the enumeration with the empty string as
     * its name.
     * <p>
     * The value of this constant is <tt>2</tt>.
     * <p>
     *  搜索以命名对象为根的整个子树。
     * p>
     * 如果命名对象不是DirContext,则只搜索对象。如果命名对象是DirContext,则搜索以命名对象为根的子树,包括命名对象本身。
     * p>
     *  搜索不会跨越命名系统边界。
     * p>
     *  使用SUBTREE_SCOPE从search()得到的NamingEnumeration包含满足search()中指定的搜索过滤器的子树(包括命名上下文)对象的元素。
     *  NamingEnumeration中的元素名称或者是相对于命名的上下文,或者是一个URL字符串。如果命名上下文满足搜索过滤器,则它将以空字符串作为其名称包含在枚举中。
     * <p>
     *  该常数的值为<tt> 2 </tt>。
     * 
     */
    public final static int SUBTREE_SCOPE = 2;

    /**
     * Contains the scope with which to apply the search. One of
     * <tt>ONELEVEL_SCOPE</tt>, <tt>OBJECT_SCOPE</tt>, or
     * <tt>SUBTREE_SCOPE</tt>.
     * <p>
     *  包含要应用搜索的范围。 <tt> ONELEVEL_SCOPE </tt>,<tt> OBJECT_SCOPE </tt>或<tt> SUBTREE_SCOPE </tt>中的一个。
     * 
     * 
     * @serial
     */
    private int searchScope;

    /**
     * Contains the milliseconds to wait before returning
     * from search.
     * <p>
     *  包含从搜索返回之前等待的毫秒数。
     * 
     * 
     * @serial
     */
    private int timeLimit;

    /**
     * Indicates whether JNDI links are dereferenced during
     * search.
     * <p>
     *  指示是否在搜索期间取消引用JNDI链接。
     * 
     * 
     * @serial
     */
    private boolean derefLink;

    /**
     *  Indicates whether object is returned in <tt>SearchResult</tt>.
     * <p>
     *  指示是否在<tt> SearchResult </tt>中返回对象。
     * 
     * 
     * @serial
     */
    private boolean returnObj;

    /**
     * Contains the maximum number of SearchResults to return.
     * <p>
     *  包含要返回的最大SearchResults数。
     * 
     * 
     * @serial
     */
    private long countLimit;

    /**
     *  Contains the list of attributes to be returned in
     * <tt>SearchResult</tt> for each matching entry of search. <tt>null</tt>
     * indicates that all attributes are to be returned.
     * <p>
     *  包含要在<tt> SearchResult </tt>中为每个匹配的搜索条目返回的属性列表。 <tt> null </tt>表示要返回所有属性。
     * 
     * 
     * @serial
     */
    private String[] attributesToReturn;

    /**
     * Constructs a search constraints using defaults.
     *<p>
     * The defaults are:
     * <ul>
     * <li>search one level
     * <li>no maximum return limit for search results
     * <li>no time limit for search
     * <li>return all attributes associated with objects that satisfy
     *   the search filter.
     * <li>do not return named object  (return only name and class)
     * <li>do not dereference links during search
     *</ul>
     * <p>
     *  使用默认值构造搜索约束。
     * p>
     *  默认值为：
     * <ul>
     * <li>搜索一级<li>搜索结果没有最大返回限制<li>没有搜索时间限制<li>返回与满足搜索过滤器的对象相关联的所有属性。
     *  <li>不返回命名对象(仅返回名称和类)<li>不要在搜索期间取消引用链接。
     * /ul>
     */
    public SearchControls() {
        searchScope = ONELEVEL_SCOPE;
        timeLimit = 0; // no limit
        countLimit = 0; // no limit
        derefLink = false;
        returnObj = false;
        attributesToReturn = null; // return all
    }

    /**
     * Constructs a search constraints using arguments.
     * <p>
     *  使用参数构造搜索约束。
     * 
     * 
     * @param scope     The search scope.  One of:
     *                  OBJECT_SCOPE, ONELEVEL_SCOPE, SUBTREE_SCOPE.
     * @param timelim   The number of milliseconds to wait before returning.
     *                  If 0, wait indefinitely.
     * @param deref     If true, dereference links during search.
     * @param countlim  The maximum number of entries to return.  If 0, return
     *                  all entries that satisfy filter.
     * @param retobj    If true, return the object bound to the name of the
     *                  entry; if false, do not return object.
     * @param attrs     The identifiers of the attributes to return along with
     *                  the entry.  If null, return all attributes. If empty
     *                  return no attributes.
     */
    public SearchControls(int scope,
                             long countlim,
                             int timelim,
                             String[] attrs,
                             boolean retobj,
                             boolean deref) {
        searchScope = scope;
        timeLimit = timelim; // no limit
        derefLink = deref;
        returnObj = retobj;
        countLimit = countlim; // no limit
        attributesToReturn = attrs; // return all
    }

    /**
     * Retrieves the search scope of these SearchControls.
     *<p>
     * One of OBJECT_SCOPE, ONELEVEL_SCOPE, SUBTREE_SCOPE.
     *
     * <p>
     *  检索这些SearchControl的搜索范围。
     * p>
     *  OBJECT_SCOPE,ONELEVEL_SCOPE,SUBTREE_SCOPE之一。
     * 
     * 
     * @return The search scope of this SearchControls.
     * @see #setSearchScope
     */
    public int getSearchScope() {
        return searchScope;
    }

    /**
     * Retrieves the time limit of these SearchControls in milliseconds.
     *<p>
     * If the value is 0, this means to wait indefinitely.
     * <p>
     *  以毫秒检索这些SearchControl的时间限制。
     * p>
     *  如果值为0,这意味着无限期地等待。
     * 
     * 
     * @return The time limit of these SearchControls in milliseconds.
     * @see #setTimeLimit
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Determines whether links will be dereferenced during the search.
     *
     * <p>
     *  确定是否在搜索期间取消引用链接。
     * 
     * 
     * @return true if links will be dereferenced; false otherwise.
     * @see #setDerefLinkFlag
     */
    public boolean getDerefLinkFlag() {
        return derefLink;
    }

    /**
     * Determines whether objects will be returned as part of the result.
     *
     * <p>
     *  确定对象是否将作为结果的一部分返回。
     * 
     * 
     * @return true if objects will be returned; false otherwise.
     * @see #setReturningObjFlag
     */
    public boolean getReturningObjFlag() {
        return returnObj;
    }

    /**
     * Retrieves the maximum number of entries that will be returned
     * as a result of the search.
     *<p>
     * 0 indicates that all entries will be returned.
     * <p>
     *  检索由于搜索将返回的条目的最大数量。
     * p>
     *  0表示将返回所有条目。
     * 
     * 
     * @return The maximum number of entries that will be returned.
     * @see #setCountLimit
     */
    public long getCountLimit() {
        return countLimit;
    }

    /**
     * Retrieves the attributes that will be returned as part of the search.
     *<p>
     * A value of null indicates that all attributes will be returned.
     * An empty array indicates that no attributes are to be returned.
     *
     * <p>
     *  检索将作为搜索的一部分返回的属性。
     * p>
     *  值为null表示将返回所有属性。空数组表示不返回任何属性。
     * 
     * 
     * @return An array of attribute ids identifying the attributes that
     * will be returned. Can be null.
     * @see #setReturningAttributes
     */
    public String[] getReturningAttributes() {
        return attributesToReturn;
    }

    /**
     * Sets the search scope to one of:
     * OBJECT_SCOPE, ONELEVEL_SCOPE, SUBTREE_SCOPE.
     * <p>
     *  将搜索范围设置为以下之一：OBJECT_SCOPE,ONELEVEL_SCOPE,SUBTREE_SCOPE。
     * 
     * 
     * @param scope     The search scope of this SearchControls.
     * @see #getSearchScope
     */
    public void setSearchScope(int scope) {
        searchScope = scope;
    }

    /**
     * Sets the time limit of these SearchControls in milliseconds.
     *<p>
     * If the value is 0, this means to wait indefinitely.
     * <p>
     *  以毫秒为单位设置这些SearchControl的时间限制。
     * p>
     *  如果值为0,这意味着无限期地等待。
     * 
     * 
     * @param ms        The time limit of these SearchControls in milliseconds.
     * @see #getTimeLimit
     */
    public void setTimeLimit(int ms) {
        timeLimit = ms;
    }

    /**
     * Enables/disables link dereferencing during the search.
     *
     * <p>
     *  在搜索期间启用/禁用链接取消引用。
     * 
     * 
     * @param on        if true links will be dereferenced; if false, not followed.
     * @see #getDerefLinkFlag
     */
    public void setDerefLinkFlag(boolean on) {
        derefLink = on;
    }

    /**
     * Enables/disables returning objects returned as part of the result.
     *<p>
     * If disabled, only the name and class of the object is returned.
     * If enabled, the object will be returned.
     *
     * <p>
     *  启用/禁用返回作为结果一部分的对象。
     * p>
     *  如果禁用,则仅返回对象的名称和类。如果启用,将返回对象。
     * 
     * 
     * @param on        if true, objects will be returned; if false,
     *                  objects will not be returned.
     * @see #getReturningObjFlag
     */
    public void setReturningObjFlag(boolean on) {
        returnObj = on;
    }

    /**
     * Sets the maximum number of entries to be returned
     * as a result of the search.
     *<p>
     * 0 indicates no limit:  all entries will be returned.
     *
     * <p>
     * 设置作为搜索结果返回的条目的最大数目。
     * p>
     *  0表示无限制：将返回所有条目。
     * 
     * 
     * @param limit The maximum number of entries that will be returned.
     * @see #getCountLimit
     */
    public void setCountLimit(long limit) {
        countLimit = limit;
    }

    /**
     * Specifies the attributes that will be returned as part of the search.
     *<p>
     * null indicates that all attributes will be returned.
     * An empty array indicates no attributes are returned.
     *
     * <p>
     *  指定将作为搜索的一部分返回的属性。
     * p>
     *  null表示将返回所有属性。空数组表示不返回任何属性。
     * 
     * 
     * @param attrs An array of attribute ids identifying the attributes that
     *              will be returned. Can be null.
     * @see #getReturningAttributes
     */
    public void setReturningAttributes(String[] attrs) {
        attributesToReturn = attrs;
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability.
     * <p>
     */
    private static final long serialVersionUID = -2480540967773454797L;
}
