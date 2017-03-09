/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

/**
 * A sort key and its associated sort parameters.
 * This class implements a sort key which is used by the LDAPv3
 * Control for server-side sorting of search results as defined in
 * <a href="http://www.ietf.org/rfc/rfc2891.txt">RFC 2891</a>.
 *
 * <p>
 *  排序键及其关联的排序参数。
 * 此类实现了一个排序键,LDAPv3控件使用该键排序搜索结果的服务器端排序,如<a href="http://www.ietf.org/rfc/rfc2891.txt"> RFC 2891 </a>。
 * 
 * 
 * @since 1.5
 * @see SortControl
 * @author Vincent Ryan
 */
public class SortKey {

    /*
     * The ID of the attribute to sort by.
     * <p>
     *  要排序的属性的ID。
     * 
     */
    private String attrID;

    /*
     * The sort order. Ascending order, by default.
     * <p>
     *  排序顺序。升序排序,默认值。
     * 
     */
    private boolean reverseOrder = false;

    /*
     * The ID of the matching rule to use for ordering attribute values.
     * <p>
     *  用于排序属性值的匹配规则的ID。
     * 
     */
    private String matchingRuleID = null;

    /**
     * Creates the default sort key for an attribute. Entries will be sorted
     * according to the specified attribute in ascending order using the
     * ordering matching rule defined for use with that attribute.
     *
     * <p>
     *  为属性创建默认排序键。将使用为该属性使用定义的排序匹配规则按照升序对指定的属性排序条目。
     * 
     * 
     * @param   attrID  The non-null ID of the attribute to be used as a sort
     *          key.
     */
    public SortKey(String attrID) {
        this.attrID = attrID;
    }

    /**
     * Creates a sort key for an attribute. Entries will be sorted according to
     * the specified attribute in the specified sort order and using the
     * specified matching rule, if supplied.
     *
     * <p>
     *  为属性创建排序键。将根据指定的排序顺序中指定的属性并使用指定的匹配规则(如果提供)对条目进行排序。
     * 
     * 
     * @param   attrID          The non-null ID of the attribute to be used as
     *                          a sort key.
     * @param   ascendingOrder  If true then entries are arranged in ascending
     *                          order. Otherwise there are arranged in
     *                          descending order.
     * @param   matchingRuleID  The possibly null ID of the matching rule to
     *                          use to order the attribute values. If not
     *                          specified then the ordering matching rule
     *                          defined for the sort key attribute is used.
     */
    public SortKey(String attrID, boolean ascendingOrder,
                    String matchingRuleID) {

        this.attrID = attrID;
        reverseOrder = (! ascendingOrder);
        this.matchingRuleID = matchingRuleID;
    }

    /**
     * Retrieves the attribute ID of the sort key.
     *
     * <p>
     *  检索排序键的属性ID。
     * 
     * 
     * @return    The non-null Attribute ID of the sort key.
     */
    public String getAttributeID() {
        return attrID;
    }

    /**
     * Determines the sort order.
     *
     * <p>
     *  确定排序顺序。
     * 
     * 
     * @return    true if the sort order is ascending, false if descending.
     */
    public boolean isAscending() {
        return (! reverseOrder);
    }

    /**
     * Retrieves the matching rule ID used to order the attribute values.
     *
     * <p>
     *  检索用于对属性值进行排序的匹配规则ID。
     * 
     * @return    The possibly null matching rule ID. If null then the
     *            ordering matching rule defined for the sort key attribute
     *            is used.
     */
    public String getMatchingRuleID() {
        return matchingRuleID;
    }
}
