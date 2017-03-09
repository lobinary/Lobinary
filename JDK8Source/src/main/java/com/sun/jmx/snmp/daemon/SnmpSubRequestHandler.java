/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp.daemon;



// java import
//
import java.util.logging.Level;
import java.util.Vector;

// jmx imports
//
import static com.sun.jmx.defaults.JmxProperties.SNMP_ADAPTOR_LOGGER;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpVarBind;
import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpEngine;

// SNMP Runtime import
//
import com.sun.jmx.snmp.agent.SnmpMibAgent;
import com.sun.jmx.snmp.agent.SnmpMibRequest;
import com.sun.jmx.snmp.ThreadContext;
import com.sun.jmx.snmp.internal.SnmpIncomingRequest;

class SnmpSubRequestHandler implements SnmpDefinitions, Runnable {

    protected SnmpIncomingRequest incRequest = null;
    protected SnmpEngine engine = null;
    /**
     * V3 enabled Adaptor. Each Oid is added using updateRequest method.
     * <p>
     *  V3启用适配器。每个Oid使用updateRequest方法添加。
     * 
     */
    protected SnmpSubRequestHandler(SnmpEngine engine,
                                    SnmpIncomingRequest incRequest,
                                    SnmpMibAgent agent,
                                    SnmpPdu req) {
        this(agent, req);
        init(engine, incRequest);
    }

    /**
     * V3 enabled Adaptor.
     * <p>
     *  V3启用适配器。
     * 
     */
    protected SnmpSubRequestHandler(SnmpEngine engine,
                                    SnmpIncomingRequest incRequest,
                                    SnmpMibAgent agent,
                                    SnmpPdu req,
                                    boolean nouse) {
        this(agent, req, nouse);
        init(engine, incRequest);
    }
    /**
     * SNMP V1/V2 . To be called with updateRequest.
     * <p>
     *  SNMP V1 / V2。使用updateRequest调用。
     * 
     */
    protected SnmpSubRequestHandler(SnmpMibAgent agent, SnmpPdu req) {
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                "constructor", "creating instance for request " + String.valueOf(req.requestId));
        }

        version= req.version;
        type= req.type;
        this.agent= agent;

        // We get a ref on the pdu in order to pass it to SnmpMibRequest.
        reqPdu = req;

        //Pre-allocate room for storing varbindlist and translation table.
        //
        int length= req.varBindList.length;
        translation= new int[length];
        varBind= new NonSyncVector<SnmpVarBind>(length);
    }

    /**
     * SNMP V1/V2 The constructor initialize the subrequest with the whole varbind list contained
     * in the original request.
     * <p>
     *  SNMP V1 / V2构造函数初始化包含在原始请求中的整个varbind列表的子请求。
     * 
     */
    @SuppressWarnings("unchecked")  // cast to NonSyncVector<SnmpVarBind>
    protected SnmpSubRequestHandler(SnmpMibAgent agent,
                                    SnmpPdu req,
                                    boolean nouse) {
        this(agent,req);

        // The translation table is easy in this case ...
        //
        int max= translation.length;
        SnmpVarBind[] list= req.varBindList;
        for(int i=0; i < max; i++) {
            translation[i]= i;
            ((NonSyncVector<SnmpVarBind>)varBind).addNonSyncElement(list[i]);
        }
    }

    SnmpMibRequest createMibRequest(Vector<SnmpVarBind> vblist,
                                    int protocolVersion,
                                    Object userData) {

        // This is an optimization:
        //    The SnmpMibRequest created in the check() phase is
        //    reused in the set() phase.
        //
        if (type == pduSetRequestPdu && mibRequest != null)
            return mibRequest;

        //This is a request comming from an SnmpV3AdaptorServer.
        //Full power.
        SnmpMibRequest result = null;
        if(incRequest != null) {
            result = SnmpMibAgent.newMibRequest(engine,
                                                reqPdu,
                                                vblist,
                                                protocolVersion,
                                                userData,
                                                incRequest.getPrincipal(),
                                                incRequest.getSecurityLevel(),
                                                incRequest.getSecurityModel(),
                                                incRequest.getContextName(),
                                                incRequest.getAccessContext());
        } else {
            result = SnmpMibAgent.newMibRequest(reqPdu,
                                                vblist,
                                                protocolVersion,
                                                userData);
        }
        // If we're doing the check() phase, we store the SnmpMibRequest
        // so that we can reuse it in the set() phase.
        //
        if (type == pduWalkRequest)
            mibRequest = result;

        return result;
    }

    void setUserData(Object userData) {
        data = userData;
    }

    public void run() {

        try {
            final ThreadContext oldContext =
                ThreadContext.push("SnmpUserData",data);
            try {
                switch(type) {
                case pduGetRequestPdu:
                    // Invoke a get operation
                    //
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                            "run", "[" + Thread.currentThread() +
                              "]:get operation on " + agent.getMibName());
                    }

                    agent.get(createMibRequest(varBind,version,data));
                    break;

                case pduGetNextRequestPdu:
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                            "run", "[" + Thread.currentThread() +
                              "]:getNext operation on " + agent.getMibName());
                    }
                    //#ifdef DEBUG
                    agent.getNext(createMibRequest(varBind,version,data));
                    break;

                case pduSetRequestPdu:
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                            "run", "[" + Thread.currentThread() +
                            "]:set operation on " + agent.getMibName());
                    }
                    agent.set(createMibRequest(varBind,version,data));
                    break;

                case pduWalkRequest:
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                            "run", "[" + Thread.currentThread() +
                            "]:check operation on " + agent.getMibName());
                    }
                    agent.check(createMibRequest(varBind,version,data));
                    break;

                default:
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(),
                            "run", "[" + Thread.currentThread() +
                              "]:unknown operation (" +  type + ") on " +
                              agent.getMibName());
                    }
                    errorStatus= snmpRspGenErr;
                    errorIndex= 1;
                    break;

                }// end of switch

            } finally {
                ThreadContext.restore(oldContext);
            }
        } catch(SnmpStatusException x) {
            errorStatus = x.getStatus() ;
            errorIndex=  x.getErrorIndex();
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(),
                    "run", "[" + Thread.currentThread() +
                      "]:an Snmp error occurred during the operation", x);
            }
        }
        catch(Exception x) {
            errorStatus = SnmpDefinitions.snmpRspGenErr ;
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(),
                    "run", "[" + Thread.currentThread() +
                      "]:a generic error occurred during the operation", x);
            }
        }
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSubRequestHandler.class.getName(),
                "run", "[" + Thread.currentThread() + "]:operation completed");
        }
    }

    // -------------------------------------------------------------
    //
    // This function does a best-effort to map global error status
    // to SNMP v1 valid global error status.
    //
    // An SnmpStatusException can contain either:
    // <li> v2 local error codes (that should be stored in the varbind)</li>
    // <li> v2 global error codes </li>
    // <li> v1 global error codes </li>
    //
    // v2 local error codes (noSuchInstance, noSuchObject) are
    // transformed in a global v1 snmpRspNoSuchName error.
    //
    // v2 global error codes are transformed in the following way:
    //
    //    If the request was a GET/GETNEXT then either
    //         snmpRspNoSuchName or snmpRspGenErr is returned.
    //
    //    Otherwise:
    //      snmpRspNoAccess, snmpRspInconsistentName
    //               => snmpRspNoSuchName
    //      snmpRspAuthorizationError, snmpRspNotWritable, snmpRspNoCreation
    //               => snmpRspReadOnly  (snmpRspNoSuchName for GET/GETNEXT)
    //      snmpRspWrong*
    //               => snmpRspBadValue  (snmpRspNoSuchName for GET/GETNEXT)
    //      snmpRspResourceUnavailable, snmpRspRspCommitFailed,
    //      snmpRspUndoFailed
    //                  => snmpRspGenErr
    //
    // -------------------------------------------------------------
    //
    static final int mapErrorStatusToV1(int errorStatus, int reqPduType) {
        // Map v2 codes onto v1 codes
        //
        if (errorStatus == SnmpDefinitions.snmpRspNoError)
            return SnmpDefinitions.snmpRspNoError;

        if (errorStatus == SnmpDefinitions.snmpRspGenErr)
            return SnmpDefinitions.snmpRspGenErr;

        if (errorStatus == SnmpDefinitions.snmpRspNoSuchName)
            return SnmpDefinitions.snmpRspNoSuchName;

        if ((errorStatus == SnmpStatusException.noSuchInstance) ||
            (errorStatus == SnmpStatusException.noSuchObject)   ||
            (errorStatus == SnmpDefinitions.snmpRspNoAccess)    ||
            (errorStatus == SnmpDefinitions.snmpRspInconsistentName) ||
            (errorStatus == SnmpDefinitions.snmpRspAuthorizationError)){

            return SnmpDefinitions.snmpRspNoSuchName;

        } else if ((errorStatus ==
                    SnmpDefinitions.snmpRspAuthorizationError)         ||
                   (errorStatus == SnmpDefinitions.snmpRspNotWritable)) {

            if (reqPduType == SnmpDefinitions.pduWalkRequest)
                return SnmpDefinitions.snmpRspReadOnly;
            else
                return SnmpDefinitions.snmpRspNoSuchName;

        } else if ((errorStatus == SnmpDefinitions.snmpRspNoCreation)) {

                return SnmpDefinitions.snmpRspNoSuchName;

        } else if ((errorStatus == SnmpDefinitions.snmpRspWrongType)      ||
                   (errorStatus == SnmpDefinitions.snmpRspWrongLength)    ||
                   (errorStatus == SnmpDefinitions.snmpRspWrongEncoding)  ||
                   (errorStatus == SnmpDefinitions.snmpRspWrongValue)     ||
                   (errorStatus == SnmpDefinitions.snmpRspWrongLength)    ||
                   (errorStatus ==
                    SnmpDefinitions.snmpRspInconsistentValue)) {

            if ((reqPduType == SnmpDefinitions.pduSetRequestPdu) ||
                (reqPduType == SnmpDefinitions.pduWalkRequest))
                return SnmpDefinitions.snmpRspBadValue;
            else
                return SnmpDefinitions.snmpRspNoSuchName;

        } else if ((errorStatus ==
                    SnmpDefinitions.snmpRspResourceUnavailable) ||
                   (errorStatus ==
                    SnmpDefinitions.snmpRspCommitFailed)        ||
                   (errorStatus == SnmpDefinitions.snmpRspUndoFailed)) {

            return SnmpDefinitions.snmpRspGenErr;

        }

        // At this point we should have a V1 error code
        //
        if (errorStatus == SnmpDefinitions.snmpRspTooBig)
            return SnmpDefinitions.snmpRspTooBig;

        if( (errorStatus == SnmpDefinitions.snmpRspBadValue) ||
            (errorStatus == SnmpDefinitions.snmpRspReadOnly)) {
            if ((reqPduType == SnmpDefinitions.pduSetRequestPdu) ||
                (reqPduType == SnmpDefinitions.pduWalkRequest))
                return errorStatus;
            else
                return SnmpDefinitions.snmpRspNoSuchName;
        }

        // We have a snmpRspGenErr, or something which is not defined
        // in RFC1905 => return a snmpRspGenErr
        //
        return SnmpDefinitions.snmpRspGenErr;

    }

    // -------------------------------------------------------------
    //
    // This function does a best-effort to map global error status
    // to SNMP v2 valid global error status.
    //
    // An SnmpStatusException can contain either:
    // <li> v2 local error codes (that should be stored in the varbind)</li>
    // <li> v2 global error codes </li>
    // <li> v1 global error codes </li>
    //
    // v2 local error codes (noSuchInstance, noSuchObject)
    // should not raise this level: they should have been stored in the
    // varbind earlier. If they, do there is nothing much we can do except
    // to transform them into:
    // <li> a global snmpRspGenErr (if the request is a GET/GETNEXT) </li>
    // <li> a global snmpRspNoSuchName otherwise. </li>
    //
    // v2 global error codes are transformed in the following way:
    //
    //    If the request was a GET/GETNEXT then snmpRspGenErr is returned.
    //    (snmpRspGenErr is the only global error that is expected to be
    //     raised by a GET/GETNEXT request).
    //
    //    Otherwise the v2 code itself is returned
    //
    // v1 global error codes are transformed in the following way:
    //
    //      snmpRspNoSuchName
    //               => snmpRspNoAccess  (snmpRspGenErr for GET/GETNEXT)
    //      snmpRspReadOnly
    //               => snmpRspNotWritable (snmpRspGenErr for GET/GETNEXT)
    //      snmpRspBadValue
    //               => snmpRspWrongValue  (snmpRspGenErr for GET/GETNEXT)
    //
    // -------------------------------------------------------------
    //
    static final int mapErrorStatusToV2(int errorStatus, int reqPduType) {
        // Map v1 codes onto v2 codes
        //
        if (errorStatus == SnmpDefinitions.snmpRspNoError)
            return SnmpDefinitions.snmpRspNoError;

        if (errorStatus == SnmpDefinitions.snmpRspGenErr)
            return SnmpDefinitions.snmpRspGenErr;

        if (errorStatus == SnmpDefinitions.snmpRspTooBig)
            return SnmpDefinitions.snmpRspTooBig;

        // For get / getNext / getBulk the only global error
        // (PDU-level) possible is genErr.
        //
        if ((reqPduType != SnmpDefinitions.pduSetRequestPdu) &&
            (reqPduType != SnmpDefinitions.pduWalkRequest)) {
            if(errorStatus == SnmpDefinitions.snmpRspAuthorizationError)
                return errorStatus;
            else
                return SnmpDefinitions.snmpRspGenErr;
        }

        // Map to noSuchName
        //      if ((errorStatus == SnmpDefinitions.snmpRspNoSuchName) ||
        //   (errorStatus == SnmpStatusException.noSuchInstance) ||
        //  (errorStatus == SnmpStatusException.noSuchObject))
        //  return SnmpDefinitions.snmpRspNoSuchName;

        // SnmpStatusException.noSuchInstance and
        // SnmpStatusException.noSuchObject can't happen...

        if (errorStatus == SnmpDefinitions.snmpRspNoSuchName)
            return SnmpDefinitions.snmpRspNoAccess;

        // Map to notWritable
        if (errorStatus == SnmpDefinitions.snmpRspReadOnly)
                return SnmpDefinitions.snmpRspNotWritable;

        // Map to wrongValue
        if (errorStatus == SnmpDefinitions.snmpRspBadValue)
            return SnmpDefinitions.snmpRspWrongValue;

        // Other valid V2 codes
        if ((errorStatus == SnmpDefinitions.snmpRspNoAccess) ||
            (errorStatus == SnmpDefinitions.snmpRspInconsistentName) ||
            (errorStatus == SnmpDefinitions.snmpRspAuthorizationError) ||
            (errorStatus == SnmpDefinitions.snmpRspNotWritable) ||
            (errorStatus == SnmpDefinitions.snmpRspNoCreation) ||
            (errorStatus == SnmpDefinitions.snmpRspWrongType) ||
            (errorStatus == SnmpDefinitions.snmpRspWrongLength) ||
            (errorStatus == SnmpDefinitions.snmpRspWrongEncoding) ||
            (errorStatus == SnmpDefinitions.snmpRspWrongValue) ||
            (errorStatus == SnmpDefinitions.snmpRspWrongLength) ||
            (errorStatus == SnmpDefinitions.snmpRspInconsistentValue) ||
            (errorStatus == SnmpDefinitions.snmpRspResourceUnavailable) ||
            (errorStatus == SnmpDefinitions.snmpRspCommitFailed) ||
            (errorStatus == SnmpDefinitions.snmpRspUndoFailed))
            return errorStatus;

        // Ivalid V2 code => genErr
        return SnmpDefinitions.snmpRspGenErr;
    }

    static final int mapErrorStatus(int errorStatus,
                                    int protocolVersion,
                                    int reqPduType) {
        if (errorStatus == SnmpDefinitions.snmpRspNoError)
            return SnmpDefinitions.snmpRspNoError;

        // Too bad, an error occurs ... we need to translate it ...
        //
        if (protocolVersion == SnmpDefinitions.snmpVersionOne)
            return mapErrorStatusToV1(errorStatus,reqPduType);
        if (protocolVersion == SnmpDefinitions.snmpVersionTwo ||
            protocolVersion == SnmpDefinitions.snmpVersionThree)
            return mapErrorStatusToV2(errorStatus,reqPduType);

        return SnmpDefinitions.snmpRspGenErr;
    }

    /**
     * The method returns the error status of the operation.
     * The method takes into account the protocol version.
     * <p>
     *  该方法返回操作的错误状态。该方法考虑协议版本。
     * 
     */
    protected int getErrorStatus() {
        if (errorStatus == snmpRspNoError)
            return snmpRspNoError;

        return mapErrorStatus(errorStatus,version,type);
    }

    /**
     * The method returns the error index as a position in the var bind list.
     * The value returned by the method corresponds to the index in the original
     * var bind list as received by the SNMP protocol adaptor.
     * <p>
     *  该方法返回错误索引作为var绑定列表中的位置。方法返回的值对应于SNMP协议适配器接收的原始var绑定列表中的索引。
     * 
     */
    protected int getErrorIndex() {
        if  (errorStatus == snmpRspNoError)
            return -1;

        // An error occurs. We need to be carefull because the index
        // we are getting is a valid SNMP index (so range starts at 1).
        // FIX ME: Shall we double-check the range here ?
        // The response is : YES :
        if ((errorIndex == 0) || (errorIndex == -1))
            errorIndex = 1;

        return translation[errorIndex -1];
    }

    /**
     * The method updates the varbind list of the subrequest.
     * <p>
     *  该方法更新子请求的varbind列表。
     * 
     */
    protected  void updateRequest(SnmpVarBind var, int pos) {
        int size= varBind.size();
        translation[size]= pos;
        varBind.addElement(var);
    }

    /**
     * The method updates a given var bind list with the result of a
     * previsouly invoked operation.
     * Prior to calling the method, one must make sure that the operation was
     * successful. As such the method getErrorIndex or getErrorStatus should be
     * called.
     * <p>
     *  该方法使用预先调用的操作的结果更新给定的var绑定列表。在调用方法之前,必须确保操作成功。因此应该调用getErrorIndex或getErrorStatus方法。
     * 
     */
    protected void updateResult(SnmpVarBind[] result) {

        if (result == null) return;
        final int max=varBind.size();
        final int len=result.length;
        for(int i= 0; i< max ; i++) {
            // bugId 4641694: must check position in order to avoid
            //       ArrayIndexOutOfBoundException
            final int pos=translation[i];
            if (pos < len) {
                result[pos] =
                    (SnmpVarBind)((NonSyncVector)varBind).elementAtNonSync(i);
            } else {
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSubRequestHandler.class.getName(),
                        "updateResult","Position `"+pos+"' is out of bound...");
                }
            }
        }
    }

    private void init(SnmpEngine engine,
                      SnmpIncomingRequest incRequest) {
        this.incRequest = incRequest;
        this.engine = engine;
    }

    // PRIVATE VARIABLES
    //------------------

    /**
     * Store the protocol version to handle
     * <p>
     *  存储要处理的协议版本
     * 
     */
    protected int version= snmpVersionOne;

    /**
     * Store the operation type. Remember if the type is Walk, it means
     * that we have to invoke the check method ...
     * <p>
     *  存储操作类型。记住如果类型是Walk,这意味着我们必须调用check方法...
     * 
     */
    protected int type= 0;

    /**
     * Agent directly handled by the sub-request handler.
     * <p>
     *  代理由子请求处理程序直接处理。
     * 
     */
    protected SnmpMibAgent agent;

    /**
     * Error status.
     * <p>
     *  错误状态。
     * 
     */
    protected int errorStatus= snmpRspNoError;

    /**
     * Index of error.
     * A value of -1 means no error.
     * <p>
     *  错误索引。值为-1表示没有错误。
     * 
     */
    protected int errorIndex= -1;

    /**
     * The varbind list specific to the current sub request.
     * The vector must contain object of type SnmpVarBind.
     * <p>
     *  特定于当前子请求的varbind列表。向量必须包含SnmpVarBind类型的对象。
     * 
     */
    protected Vector<SnmpVarBind> varBind;

    /**
     * The array giving the index translation between the content of
     * <VAR>varBind</VAR> and the varbind list as specified in the request.
     * <p>
     * 该数组给出在请求中指定的<VAR> varBind </VAR>的内容和varbind列表之间的索引转换。
     * 
     */
    protected int[] translation;

    /**
     * Contextual object allocated by the SnmpUserDataFactory.
     * <p>
     *  由SnmpUserDataFactory分配的上下文对象。
     * 
     * 
     **/
    protected Object data;

    /**
     * The SnmpMibRequest that will be passed to the agent.
     *
     * <p>
     *  将传递给代理的SnmpMibRequest。
     * 
     * 
     **/
    private   SnmpMibRequest mibRequest = null;

    /**
     * The SnmpPdu that will be passed to the request.
     *
     * <p>
     *  将被传递到请求的SnmpPdu。
     * 
     **/
    private   SnmpPdu reqPdu = null;

    // All the methods of the Vector class are synchronized.
    // Synchronization is a very expensive operation. In our case it is not always
    // required...
    //
    @SuppressWarnings("serial")  // we never serialize this
    class NonSyncVector<E> extends Vector<E> {

        public NonSyncVector(int size) {
            super(size);
        }

        final void addNonSyncElement(E obj) {
            ensureCapacity(elementCount + 1);
            elementData[elementCount++] = obj;
        }

        @SuppressWarnings("unchecked")  // cast to E
        final E elementAtNonSync(int index) {
            return (E) elementData[index];
        }
    };
}
