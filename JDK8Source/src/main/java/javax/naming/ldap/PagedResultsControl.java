/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import com.sun.jndi.ldap.Ber;
import com.sun.jndi.ldap.BerEncoder;

/**
 * Requests that the results of a search operation be returned by the LDAP
 * server in batches of a specified size.
 * The requestor controls the rate at which batches are returned by the rate
 * at which it invokes search operations.
 * <p>
 * The following code sample shows how the class may be used:
 * <pre>{@code
 *
 *     // Open an LDAP association
 *     LdapContext ctx = new InitialLdapContext();
 *
 *     // Activate paged results
 *     int pageSize = 20; // 20 entries per page
 *     byte[] cookie = null;
 *     int total;
 *     ctx.setRequestControls(new Control[]{
 *         new PagedResultsControl(pageSize, Control.CRITICAL) });
 *
 *     do {
 *         // Perform the search
 *         NamingEnumeration results =
 *             ctx.search("", "(objectclass=*)", new SearchControls());
 *
 *         // Iterate over a batch of search results
 *         while (results != null && results.hasMore()) {
 *             // Display an entry
 *             SearchResult entry = (SearchResult)results.next();
 *             System.out.println(entry.getName());
 *             System.out.println(entry.getAttributes());
 *
 *             // Handle the entry's response controls (if any)
 *             if (entry instanceof HasControls) {
 *                 // ((HasControls)entry).getControls();
 *             }
 *         }
 *         // Examine the paged results control response
 *         Control[] controls = ctx.getResponseControls();
 *         if (controls != null) {
 *             for (int i = 0; i < controls.length; i++) {
 *                 if (controls[i] instanceof PagedResultsResponseControl) {
 *                     PagedResultsResponseControl prrc =
 *                         (PagedResultsResponseControl)controls[i];
 *                     total = prrc.getResultSize();
 *                     cookie = prrc.getCookie();
 *                 } else {
 *                     // Handle other response controls (if any)
 *                 }
 *             }
 *         }
 *
 *         // Re-activate paged results
 *         ctx.setRequestControls(new Control[]{
 *             new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
 *     } while (cookie != null);
 *
 *     // Close the LDAP association
 *     ctx.close();
 *     ...
 *
 * } </pre>
 * <p>
 * This class implements the LDAPv3 Control for paged-results as defined in
 * <a href="http://www.ietf.org/rfc/rfc2696.txt">RFC 2696</a>.
 *
 * The control's value has the following ASN.1 definition:
 * <pre>{@code
 *
 *     realSearchControlValue ::= SEQUENCE {
 *         size      INTEGER (0..maxInt),
 *                           -- requested page size from client
 *                           -- result set size estimate from server
 *         cookie    OCTET STRING
 *     }
 *
 * }</pre>
 *
 * <p>
 *  请求LDAP服务器以指定大小的批次返回搜索操作的结果。请求者通过调用搜索操作的速率来控制返回批次的速率。
 * <p>
 *  以下代码示例显示如何使用类：<pre> {@ code
 * 
 *  //打开LDAP关联LdapContext ctx = new InitialLdapContext();
 * 
 *  //激活分页结果int pageSize = 20; //每页20个条目byte [] cookie = null; int total; ctx.setRequestControls(new Con
 * trol [] {new PagedResultsControl(pageSize,Control.CRITICAL)});。
 * 
 *  do {//执行搜索NamingEnumeration results = ctx.search("","(objectclass = *)",new SearchControls());
 * 
 *  //迭代一批搜索结果while(results！= null && results.hasMore()){//显示一个条目SearchResult entry =(SearchResult)results.next(); System.out.println(entry.getName()); System.out.println(entry.getAttributes());。
 * 
 * //处理条目的响应控制(如果有)if(条目instanceof HasControls){//((HasControls)条目).getControls(); }} //检查分页结果控件响应Contro
 * l [] controls = ctx.getResponseControls(); if(controls！= null){for(int i = 0; i <controls.length; i ++){if(controls [i] instanceof PagedResultsResponseControl){PagedResultsResponseControl prrc =(PagedResultsResponseControl)controls [i]; total = prrc.getResultSize(); cookie = prrc.getCookie(); } else {//处理其他响应控件(如果有)}}}。
 * 
 *  //重新激活分页结果ctx.setRequestControls(new Control [] {new PagedResultsControl(pageSize,cookie,Control.CRITICAL)}
 * ); } while(cookie！= null);。
 * 
 *  //关闭LDAP关联ctx.close(); ... ...
 * 
 *  } </pre>
 * <p>
 * 
 * @since 1.5
 * @see PagedResultsResponseControl
 * @author Vincent Ryan
 */
final public class PagedResultsControl extends BasicControl {

    /**
     * The paged-results control's assigned object identifier
     * is 1.2.840.113556.1.4.319.
     * <p>
     *  此类实现了<a href="http://www.ietf.org/rfc/rfc2696.txt"> RFC 2696 </a>中定义的分页结果的LDAPv3控件。
     * 
     *  控件的值具有以下ASN.1定义：<pre> {@ code
     * 
     *  realSearchControlValue :: = SEQUENCE {size INTEGER(0..maxInt), - 从客户端请求的页面大小 - 从服务器cookie估计的结果集大小OCTET STRING}
     * 。
     * 
     *  } </pre>
     * 
     */
    public static final String OID = "1.2.840.113556.1.4.319";

    private static final byte[] EMPTY_COOKIE = new byte[0];

    private static final long serialVersionUID = 6684806685736844298L;

    /**
     * Constructs a control to set the number of entries to be returned per
     * page of results.
     *
     * <p>
     *  分页结果控制的分配的对象标识符是1.2.840.113556.1.4.319。
     * 
     * 
     * @param   pageSize        The number of entries to return in a page.
     * @param   criticality     If true then the server must honor the control
     *                          and return search results as indicated by
     *                          pageSize or refuse to perform the search.
     *                          If false, then the server need not honor the
     *                          control.
     * @exception IOException   If an error was encountered while encoding the
     *                          supplied arguments into a control.
     */
    public PagedResultsControl(int pageSize, boolean criticality)
            throws IOException {

        super(OID, criticality, null);
        value = setEncodedValue(pageSize, EMPTY_COOKIE);
    }

    /**
     * Constructs a control to set the number of entries to be returned per
     * page of results. The cookie is provided by the server and may be
     * obtained from the paged-results response control.
     * <p>
     * A sequence of paged-results can be abandoned by setting the pageSize
     * to zero and setting the cookie to the last cookie received from the
     * server.
     *
     * <p>
     *  构造一个控件以设置每页结果返回的条目数。
     * 
     * 
     * @param   pageSize        The number of entries to return in a page.
     * @param   cookie          A possibly null server-generated cookie.
     * @param   criticality     If true then the server must honor the control
     *                          and return search results as indicated by
     *                          pageSize or refuse to perform the search.
     *                          If false, then the server need not honor the
     *                          control.
     * @exception IOException   If an error was encountered while encoding the
     *                          supplied arguments into a control.
     */
    public PagedResultsControl(int pageSize, byte[] cookie,
        boolean criticality) throws IOException {

        super(OID, criticality, null);
        if (cookie == null) {
            cookie = EMPTY_COOKIE;
        }
        value = setEncodedValue(pageSize, cookie);
    }

    /*
     * Encodes the paged-results control's value using ASN.1 BER.
     * The result includes the BER tag and length for the control's value but
     * does not include the control's object identifier and criticality setting.
     *
     * <p>
     *  构造一个控件以设置每页结果返回的条目数。 cookie由服务器提供,并且可以从分页结果响应控件获得。
     * <p>
     * 通过将pageSize设置为零并将cookie设置为从服务器接收的最后一个cookie,可以放弃分页结果序列。
     * 
     * 
     * @param   pageSize        The number of entries to return in a page.
     * @param   cookie          A non-null server-generated cookie.
     * @return A possibly null byte array representing the ASN.1 BER encoded
     *         value of the LDAP paged-results control.
     * @exception IOException If a BER encoding error occurs.
     */
    private byte[] setEncodedValue(int pageSize, byte[] cookie)
        throws IOException {

        // build the ASN.1 encoding
        BerEncoder ber = new BerEncoder(10 + cookie.length);

        ber.beginSeq(Ber.ASN_SEQUENCE | Ber.ASN_CONSTRUCTOR);
            ber.encodeInt(pageSize);
            ber.encodeOctetString(cookie, Ber.ASN_OCTET_STR);
        ber.endSeq();

        return ber.getTrimmedBuf();
    }
}
