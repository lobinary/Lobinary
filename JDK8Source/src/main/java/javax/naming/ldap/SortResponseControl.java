/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
import javax.naming.*;
import javax.naming.directory.*;
import com.sun.jndi.ldap.Ber;
import com.sun.jndi.ldap.BerDecoder;
import com.sun.jndi.ldap.LdapCtx;

/**
 * Indicates whether the requested sort of search results was successful or not.
 * When the result code indicates success then the results have been sorted as
 * requested. Otherwise the sort was unsuccessful and additional details
 * regarding the cause of the error may have been provided by the server.
 * <p>
 * The code sample in {@link SortControl} shows how this class may be used.
 * <p>
 * This class implements the LDAPv3 Response Control for server-side sorting
 * as defined in
 * <a href="http://www.ietf.org/rfc/rfc2891.txt">RFC 2891</a>.
 *
 * The control's value has the following ASN.1 definition:
 * <pre>
 *
 *     SortResult ::= SEQUENCE {
 *        sortResult  ENUMERATED {
 *            success                   (0), -- results are sorted
 *            operationsError           (1), -- server internal failure
 *            timeLimitExceeded         (3), -- timelimit reached before
 *                                           -- sorting was completed
 *            strongAuthRequired        (8), -- refused to return sorted
 *                                           -- results via insecure
 *                                           -- protocol
 *            adminLimitExceeded       (11), -- too many matching entries
 *                                           -- for the server to sort
 *            noSuchAttribute          (16), -- unrecognized attribute
 *                                           -- type in sort key
 *            inappropriateMatching    (18), -- unrecognized or inappro-
 *                                           -- priate matching rule in
 *                                           -- sort key
 *            insufficientAccessRights (50), -- refused to return sorted
 *                                           -- results to this client
 *            busy                     (51), -- too busy to process
 *            unwillingToPerform       (53), -- unable to sort
 *            other                    (80)
 *            },
 *      attributeType [0] AttributeType OPTIONAL }
 *
 * </pre>
 *
 * <p>
 *  指示所请求的搜索结果排序是否成功。当结果代码指示成功时,结果已按请求排序。否则,排序不成功,并且有关错误原因的其他详细信息可能已由服务器提供。
 * <p>
 *  {@link SortControl}中的代码示例显示了如何使用此类。
 * <p>
 *  此类实现了<a href="http://www.ietf.org/rfc/rfc2891.txt"> RFC 2891 </a>中定义的服务器端排序的LDAPv3响应控件。
 * 
 *  控件的值具有以下ASN.1定义：
 * <pre>
 * 
 * SortResult :: = SEQUENCE {sortResult ENUMERATED {success(0), - 结果排序操作错误(1), - 服务器内部失败timeLimitExceeded(3), -  timelimit达到之前 - 排序完成strongAuthRequired - 拒绝返回sorted  - 通过不安全的结果 - 协议adminLimitExceeded(11), - 太多匹配条目 - 服务器排序noSuchAttribute(16), - 无法识别的属性 - 类型在sort key不匹配, - 无法识别或不适当的匹配规则 - 排序键insufficientAccessRights(50), - 拒绝返回排序结果给此客户端忙(51), - 太忙,无法处理unwillingToPerform(53) - 无法排序other(80)}
 * ,attributeType [0] AttributeType OPTIONAL}。
 * 
 * </pre>
 * 
 * 
 * @since 1.5
 * @see SortControl
 * @author Vincent Ryan
 */
final public class SortResponseControl extends BasicControl {

    /**
     * The server-side sort response control's assigned object identifier
     * is 1.2.840.113556.1.4.474.
     * <p>
     *  服务器端排序响应控件的分配的对象标识符为1.2.840.113556.1.4.474。
     * 
     */
    public static final String OID = "1.2.840.113556.1.4.474";

    private static final long serialVersionUID = 5142939176006310877L;

    /**
     * The sort result code.
     *
     * <p>
     *  排序结果代码。
     * 
     * 
     * @serial
     */
    private int resultCode = 0;

    /**
     * The ID of the attribute that caused the sort to fail.
     *
     * <p>
     *  导致排序失败的属性的ID。
     * 
     * 
     * @serial
     */
    private String badAttrId = null;

    /**
     * Constructs a control to indicate the outcome of a sort request.
     *
     * <p>
     *  构造一个控件以指示排序请求的结果。
     * 
     * 
     * @param   id              The control's object identifier string.
     * @param   criticality     The control's criticality.
     * @param   value           The control's ASN.1 BER encoded value.
     *                          It is not cloned - any changes to value
     *                          will affect the contents of the control.
     * @exception               IOException if an error is encountered
     *                          while decoding the control's value.
     */
    public SortResponseControl(String id, boolean criticality, byte[] value)
        throws IOException {

        super(id, criticality, value);

        // decode value
        BerDecoder ber = new BerDecoder(value, 0, value.length);

        ber.parseSeq(null);
        resultCode = ber.parseEnumeration();
        if ((ber.bytesLeft() > 0) && (ber.peekByte() == Ber.ASN_CONTEXT)) {
            badAttrId = ber.parseStringWithTag(Ber.ASN_CONTEXT, true, null);
        }
    }

    /**
     * Determines if the search results have been successfully sorted.
     * If an error occurred during sorting a NamingException is thrown.
     *
     * <p>
     *  确定搜索结果是否已成功排序。如果在排序期间发生错误,则抛出NamingException。
     * 
     * 
     * @return    true if the search results have been sorted.
     */
    public boolean isSorted() {
        return (resultCode == 0); // a result code of zero indicates success
    }

    /**
     * Retrieves the LDAP result code of the sort operation.
     *
     * <p>
     *  检索排序操作的LDAP结果代码。
     * 
     * 
     * @return    The result code. A zero value indicates success.
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Retrieves the ID of the attribute that caused the sort to fail.
     * Returns null if no ID was returned by the server.
     *
     * <p>
     *  检索导致排序失败的属性的ID。如果服务器未返回任何ID,则返回null。
     * 
     * 
     * @return The possibly null ID of the bad attribute.
     */
    public String getAttributeID() {
        return badAttrId;
    }

    /**
     * Retrieves the NamingException appropriate for the result code.
     *
     * <p>
     *  检索适用于结果代码的NamingException。
     * 
     * @return A NamingException or null if the result code indicates
     *         success.
     */
    public NamingException getException() {

        return LdapCtx.mapErrorCode(resultCode, null);
    }
}
