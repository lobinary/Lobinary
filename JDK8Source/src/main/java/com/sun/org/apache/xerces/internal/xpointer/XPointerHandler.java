/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2005 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
package com.sun.org.apache.xerces.internal.xpointer;

import java.util.Hashtable;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.XMLChar;
import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler;
import com.sun.org.apache.xerces.internal.xinclude.XIncludeNamespaceSupport;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;

/**
 * <p>
 * This is a pipeline component which extends the XIncludeHandler to perform
 * XPointer specific processing specified in the W3C XPointerFramework and
 * element() Scheme Recommendations.
 * </p>
 *
 * <p>
 * This component analyzes each event in the pipeline, looking for an element
 * that matches a PointerPart in the parent XInclude element's xpointer attribute
 * value.  If the match succeeds, all children are passed by this component.
 * </p>
 *
 * <p>
 * See the <a href="http://www.w3.org/TR/xptr-framework//">XPointer Framework Recommendation</a> for
 * more information on the XPointer Framework and ShortHand Pointers.
 * See the <a href="http://www.w3.org/TR/xptr-element/">XPointer element() Scheme Recommendation</a> for
 * more information on the XPointer element() Scheme.
 * </p>
 *
 * @xerces.internal
 *
 * <p>
 * <p>
 * 这是一个管道组件,它扩展了XIncludeHandler以执行在W3C XPointerFramework和element()方案中指定的XPointer特定处理
 * </p>
 * 
 * <p>
 *  此组件分析管道中的每个事件,查找与父XInclude元素的xpointer属性值中的PointerPart匹配的元素如果匹配成功,则所有子代都由此组件传递
 * </p>
 * 
 * <p>
 *  有关XPointer Framework和ShortHand指针的更多信息,请参见<a href=\"http://wwww3org/TR/xptr-framework//\"> XPointer框架
 * 建议</a>。
 * 请参阅<a href ="http：//关于XPointer元素()方案的详细信息,请参见"wwww3org / TR / xptr-element /"> XPointer元素。
 * </p>
 * 
 *  @xercesinternal
 * 
 */
public final class XPointerHandler extends XIncludeHandler implements
        XPointerProcessor {

    // Fields
    // A Vector of XPointerParts
    protected Vector fXPointerParts = null;

    // The current XPointerPart
    protected XPointerPart fXPointerPart = null;

    // Has the fXPointerPart resolved successfully
    protected boolean fFoundMatchingPtrPart = false;

    // The XPointer Error reporter
    protected XMLErrorReporter fXPointerErrorReporter;

    // The XPointer Error Handler
    protected XMLErrorHandler fErrorHandler;

    // XPointerFramework symbol table
    protected SymbolTable fSymbolTable = null;

    // Supported schemes
    private final String ELEMENT_SCHEME_NAME = "element";

   // Has the XPointer resolved the subresource
    protected boolean fIsXPointerResolved = false;

    // Fixup xml:base and xml:lang attributes
    protected boolean fFixupBase = false;
    protected boolean fFixupLang = false;

    // ************************************************************************
    // Constructors
    // ************************************************************************

    /**
     *
     * <p>
     */
    public XPointerHandler() {
        super();

        fXPointerParts = new Vector();
        fSymbolTable = new SymbolTable();
    }

    public XPointerHandler(SymbolTable symbolTable,
            XMLErrorHandler errorHandler, XMLErrorReporter errorReporter) {
        super();

        fXPointerParts = new Vector();
        fSymbolTable = symbolTable;
        fErrorHandler = errorHandler;
        fXPointerErrorReporter = errorReporter;
        //fErrorReporter = errorReporter; // The XInclude ErrorReporter
    }

    // ************************************************************************
    //  Implementation of the XPointerProcessor interface.
    // ************************************************************************

    /**
     * Parses the XPointer framework expression and delegates scheme specific parsing.
     *
     * <p>
     * 解析XPointer框架表达式并委派方案特定的解析
     * 
     * 
     * @see com.sun.org.apache.xerces.internal.xpointer.XPointerProcessor#parseXPointer(java.lang.String)
     */
    public void parseXPointer(String xpointer) throws XNIException {

        // Initialize
        init();

        // tokens
        final Tokens tokens = new Tokens(fSymbolTable);

        // scanner
        Scanner scanner = new Scanner(fSymbolTable) {
            protected void addToken(Tokens tokens, int token)
                    throws XNIException {
                if (token == Tokens.XPTRTOKEN_OPEN_PAREN
                        || token == Tokens.XPTRTOKEN_CLOSE_PAREN
                        || token == Tokens.XPTRTOKEN_SCHEMENAME
                        || token == Tokens.XPTRTOKEN_SCHEMEDATA
                        || token == Tokens.XPTRTOKEN_SHORTHAND) {
                    super.addToken(tokens, token);
                    return;
                }
                reportError("InvalidXPointerToken", new Object[] { tokens
                        .getTokenString(token) });
            }
        };

        // scan the XPointer expression
        int length = xpointer.length();
        boolean success = scanner.scanExpr(fSymbolTable, tokens, xpointer, 0,
                length);

        if (!success)
            reportError("InvalidXPointerExpression", new Object[] { xpointer });

        while (tokens.hasMore()) {
            int token = tokens.nextToken();

            switch (token) {
            case Tokens.XPTRTOKEN_SHORTHAND: {

                // The shortHand name
                token = tokens.nextToken();
                String shortHandPointerName = tokens.getTokenString(token);

                if (shortHandPointerName == null) {
                    reportError("InvalidXPointerExpression",
                            new Object[] { xpointer });
                }

                XPointerPart shortHandPointer = new ShortHandPointer(
                        fSymbolTable);
                shortHandPointer.setSchemeName(shortHandPointerName);
                fXPointerParts.add(shortHandPointer);
                break;
            }
            case Tokens.XPTRTOKEN_SCHEMENAME: {

                // Retreive the local name and prefix to form the scheme name
                token = tokens.nextToken();
                String prefix = tokens.getTokenString(token);
                token = tokens.nextToken();
                String localName = tokens.getTokenString(token);

                String schemeName = prefix + localName;

                // The next character should be an open parenthesis
                int openParenCount = 0;
                int closeParenCount = 0;

                token = tokens.nextToken();
                String openParen = tokens.getTokenString(token);
                if (openParen != "XPTRTOKEN_OPEN_PAREN") {

                    // can not have more than one ShortHand Pointer
                    if (token == Tokens.XPTRTOKEN_SHORTHAND) {
                        reportError("MultipleShortHandPointers",
                                new Object[] { xpointer });
                    } else {
                        reportError("InvalidXPointerExpression",
                                new Object[] { xpointer });
                    }
                }
                openParenCount++;

                // followed by zero or more ( and  the schemeData
                String schemeData = null;
                while (tokens.hasMore()) {
                    token = tokens.nextToken();
                    schemeData = tokens.getTokenString(token);
                    if (schemeData != "XPTRTOKEN_OPEN_PAREN") {
                        break;
                    }
                    openParenCount++;
                }
                token = tokens.nextToken();
                schemeData = tokens.getTokenString(token);

                // followed by the same number of )
                token = tokens.nextToken();
                String closeParen = tokens.getTokenString(token);
                if (closeParen != "XPTRTOKEN_CLOSE_PAREN") {
                    reportError("SchemeDataNotFollowedByCloseParenthesis",
                            new Object[] { xpointer });
                }
                closeParenCount++;

                while (tokens.hasMore()) {
                    if (tokens.getTokenString(tokens.peekToken()) != "XPTRTOKEN_OPEN_PAREN") {
                        break;
                    }
                    closeParenCount++;
                }

                // check if the number of open parenthesis are equal to the number of close parenthesis
                if (openParenCount != closeParenCount) {
                    reportError("UnbalancedParenthesisInXPointerExpression",
                            new Object[] { xpointer,
                                    new Integer(openParenCount),
                                    new Integer(closeParenCount) });
                }

                // Perform scheme specific parsing of the pointer part
                if (schemeName.equals(ELEMENT_SCHEME_NAME)) {
                    XPointerPart elementSchemePointer = new ElementSchemePointer(
                            fSymbolTable, fErrorReporter);
                    elementSchemePointer.setSchemeName(schemeName);
                    elementSchemePointer.setSchemeData(schemeData);

                    // If an exception occurs while parsing the element() scheme expression
                    // ignore it and move on to the next pointer part
                    try {
                        elementSchemePointer.parseXPointer(schemeData);
                        fXPointerParts.add(elementSchemePointer);
                    } catch (XNIException e) {
                        // Re-throw the XPointer element() scheme syntax error.
                        throw new XNIException (e);
                    }

                } else {
                    // ????
                    reportWarning("SchemeUnsupported",
                            new Object[] { schemeName });
                }

                break;
            }
            default:
                reportError("InvalidXPointerExpression",
                        new Object[] { xpointer });
            }
        }

    }

    /**
     *
     * <p>
     * 
     * @see com.sun.org.apache.xerces.internal.xpointer.XPointerProcessor#resolveXPointer(com.sun.org.apache.xerces.internal.xni.QName, com.sun.org.apache.xerces.internal.xni.XMLAttributes, com.sun.org.apache.xerces.internal.xni.Augmentations, int event)
     */
    public boolean resolveXPointer(QName element, XMLAttributes attributes,
            Augmentations augs, int event) throws XNIException {
        boolean resolved = false;

        // The result of the first pointer part whose evaluation identifies
        // one or more subresources is reported by the XPointer processor as the
        // result of the pointer as a whole, and evaluation stops.
        // In our implementation, typically the first xpointer scheme that
        // matches an element is the document is considered.
        // If the pointer part resolved then use it, else search for the fragment
        // using next pointer part from lef-right.
        if (!fFoundMatchingPtrPart) {

            // for each element, attempt to resolve it against each pointer part
            // in the XPointer expression until a matching element is found.
            for (int i = 0; i < fXPointerParts.size(); i++) {

                fXPointerPart = (XPointerPart) fXPointerParts.get(i);

                if (fXPointerPart.resolveXPointer(element, attributes, augs,
                        event)) {
                    fFoundMatchingPtrPart = true;
                    resolved = true;
                }
            }
        } else {
            if (fXPointerPart.resolveXPointer(element, attributes, augs, event)) {
                resolved = true;
            }
        }

        if (!fIsXPointerResolved) {
            fIsXPointerResolved = resolved;
        }

        return resolved;
    }

    /**
     * Returns true if the Node fragment is resolved.
     *
     * <p>
     *  如果节点片段已解决,则返回true
     * 
     * 
     * @see com.sun.org.apache.xerces.internal.xpointer.XPointerProcessor#isFragmentResolved()
     */
    public boolean isFragmentResolved() throws XNIException {
        boolean resolved = (fXPointerPart != null) ? fXPointerPart.isFragmentResolved()
                : false;

        if (!fIsXPointerResolved) {
            fIsXPointerResolved = resolved;
        }

        return resolved;
    }

    /**
     * Returns true if the XPointer expression resolves to a non-element child
     * of the current resource fragment.
     *
     * <p>
     *  如果XPointer表达式解析为当前资源片段的非元素子元素,则返回true
     * 
     * 
     * @see com.sun.org.apache.xerces.internal.xpointer.XPointerPart#isChildFragmentResolved()
     *
     */
    public boolean isChildFragmentResolved() throws XNIException {
        boolean resolved = (fXPointerPart != null) ? fXPointerPart
                                .isChildFragmentResolved() : false;
                return resolved;
    }

    /**
     * Returns true if the XPointer successfully found a sub-resource .
     *
     * <p>
     *  如果XPointer成功找到子资源,则返回true
     * 
     * 
     * @see com.sun.org.apache.xerces.internal.xpointer.XPointerProcessor#isFragmentResolved()
     */
    public boolean isXPointerResolved() throws XNIException {
        return fIsXPointerResolved;
    }

    /**
     * Returns the pointer part used to resolve the document fragment.
     *
     * <p>
     *  返回用于解析文档片段的指针部分
     * 
     * 
     * @return String - The pointer part used to resolve the document fragment.
     */
    public XPointerPart getXPointerPart() {
        return fXPointerPart;
    }

    /**
     * Reports XPointer Errors
     *
     * <p>
     *  报告XPointer错误
     * 
     */
    private void reportError(String key, Object[] arguments)
            throws XNIException {
        /*
        fXPointerErrorReporter.reportError(
                XPointerMessageFormatter.XPOINTER_DOMAIN, key, arguments,
                XMLErrorReporter.SEVERITY_ERROR);
        /* <p>
        /*  fXPointerErrorReporterreportError(XPointerMessageFormatterXPOINTER_DOMAIN,key,arguments,XMLErrorRepo
        /* rterSEVERITY_ERROR);。
        /* 
        */
        throw new XNIException((fErrorReporter
                                .getMessageFormatter(XPointerMessageFormatter.XPOINTER_DOMAIN))
                                .formatMessage(fErrorReporter.getLocale(), key, arguments));
    }

    /**
     * Reports XPointer Warnings
     *
     * <p>
     *  报告XPointer警告
     * 
     */
    private void reportWarning(String key, Object[] arguments)
            throws XNIException {
        fXPointerErrorReporter.reportError(
                XPointerMessageFormatter.XPOINTER_DOMAIN, key, arguments,
                XMLErrorReporter.SEVERITY_WARNING);
    }

    /**
     * Initializes error handling objects
     *
     * <p>
     *  初始化错误处理对象
     * 
     */
    protected void initErrorReporter() {
        if (fXPointerErrorReporter == null) {
            fXPointerErrorReporter = new XMLErrorReporter();
        }
        if (fErrorHandler == null) {
            fErrorHandler = new XPointerErrorHandler();
        }
        /*
         fXPointerErrorReporter.setProperty(Constants.XERCES_PROPERTY_PREFIX
         + Constants.ERROR_HANDLER_PROPERTY, fErrorHandler);
        /* <p>
        /*  fXPointerErrorReportersetProperty(ConstantsXERCES_PROPERTY_PREFIX + ConstantsERROR_HANDLER_PROPERTY,
        /* fErrorHandler);。
        /* 
         */
        fXPointerErrorReporter.putMessageFormatter(
                XPointerMessageFormatter.XPOINTER_DOMAIN,
                new XPointerMessageFormatter());
    }

    /**
     * Initializes the XPointer Processor;
     * <p>
     *  初始化XPointer处理器;
     * 
     */
    protected void init() {
        fXPointerParts.clear();
        fXPointerPart = null;
        fFoundMatchingPtrPart = false;
        fIsXPointerResolved = false;
        //fFixupBase = false;
        //fFixupLang = false;

        initErrorReporter();
    }

    /**
     * Returns a Vector of XPointerPart objects
     *
     * <p>
     *  返回XPointerPart对象的向量
     * 
     * 
     * @return A Vector of XPointerPart objects.
     */
    public Vector getPointerParts() {
        return fXPointerParts;
    }

    /**
     * List of XPointer Framework tokens.
     *
     * @xerces.internal
     *
     * <p>
     * XPointer Framework令牌列表
     * 
     *  @xercesinternal
     * 
     */
    private final class Tokens {

        /**
         * XPointer Framework tokens
         * [1] Pointer     ::= Shorthand | SchemeBased
         * [2] Shorthand   ::= NCName
         * [3] SchemeBased ::= PointerPart (S? PointerPart)*
         * [4] PointerPart ::= SchemeName '(' SchemeData ')'
         * [5] SchemeName  ::= QName
         * [6] SchemeData  ::= EscapedData*
         * [7] EscapedData ::= NormalChar | '^(' | '^)' | '^^' | '(' SchemeData ')'
         * [8] NormalChar  ::= UnicodeChar - [()^]
         * [9] UnicodeChar ::= [#x0-#x10FFFF]
         *
         * <p>
         *  XPointer Framework Token [1] Pointer :: = Shorthand | SchemeBased [2] Shorthand :: = NCName [3] Sche
         * meBased :: = PointerPart(S?PointerPart)* [4] PointerPart :: = SchemeName'('SchemeData')'SchemeName ::
         *  = QName [6] :: = EscapedData * [7] EscapedData :: = NormalChar | '^('|'^)'| '^^'| '('SchemeData')'[8
         * ] NormalChar :: = UnicodeChar  -  [()^] [9] UnicodeChar :: = [#x0-#x10FFFF]。
         * 
         */
        private static final int XPTRTOKEN_OPEN_PAREN = 0,
                XPTRTOKEN_CLOSE_PAREN = 1, XPTRTOKEN_SHORTHAND = 2,
                XPTRTOKEN_SCHEMENAME = 3, XPTRTOKEN_SCHEMEDATA = 4;

        // Token names
        private final String[] fgTokenNames = { "XPTRTOKEN_OPEN_PAREN",
                "XPTRTOKEN_CLOSE_PAREN", "XPTRTOKEN_SHORTHAND",
                "XPTRTOKEN_SCHEMENAME", "XPTRTOKEN_SCHEMEDATA" };

        // Token count
        private static final int INITIAL_TOKEN_COUNT = 1 << 8;

        private int[] fTokens = new int[INITIAL_TOKEN_COUNT];

        private int fTokenCount = 0;

        // Current token position
        private int fCurrentTokenIndex;

        private SymbolTable fSymbolTable;

        private Hashtable fTokenNames = new Hashtable();

        /**
         * Constructor
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param symbolTable SymbolTable
         */
        private Tokens(SymbolTable symbolTable) {
            fSymbolTable = symbolTable;

            fTokenNames.put(new Integer(XPTRTOKEN_OPEN_PAREN),
                    "XPTRTOKEN_OPEN_PAREN");
            fTokenNames.put(new Integer(XPTRTOKEN_CLOSE_PAREN),
                    "XPTRTOKEN_CLOSE_PAREN");
            fTokenNames.put(new Integer(XPTRTOKEN_SHORTHAND),
                    "XPTRTOKEN_SHORTHAND");
            fTokenNames.put(new Integer(XPTRTOKEN_SCHEMENAME),
                    "XPTRTOKEN_SCHEMENAME");
            fTokenNames.put(new Integer(XPTRTOKEN_SCHEMEDATA),
                    "XPTRTOKEN_SCHEMEDATA");
        }

        /**
         * Returns the token String
         * <p>
         *  返回标记String
         * 
         * 
         * @param token The index of the token
         * @return String The token string
         */
        private String getTokenString(int token) {
            return (String) fTokenNames.get(new Integer(token));
        }

        /**
         * Add the specified string as a token
         *
         * <p>
         *  将指定的字符串添加为令牌
         * 
         * 
         * @param token The token string
         */
        private void addToken(String tokenStr) {
            Integer tokenInt = (Integer) fTokenNames.get(tokenStr);
            if (tokenInt == null) {
                tokenInt = new Integer(fTokenNames.size());
                fTokenNames.put(tokenInt, tokenStr);
            }
            addToken(tokenInt.intValue());
        }

        /**
         * Add the specified int token
         *
         * <p>
         *  添加指定的int令牌
         * 
         * 
         * @param token The int specifying the token
         */
        private void addToken(int token) {
            try {
                fTokens[fTokenCount] = token;
            } catch (ArrayIndexOutOfBoundsException ex) {
                int[] oldList = fTokens;
                fTokens = new int[fTokenCount << 1];
                System.arraycopy(oldList, 0, fTokens, 0, fTokenCount);
                fTokens[fTokenCount] = token;
            }
            fTokenCount++;
        }

        /**
         * Resets the current position to the head of the token list.
         * <p>
         *  将当前位置重置为令牌列表的头
         * 
         */
        private void rewind() {
            fCurrentTokenIndex = 0;
        }

        /**
         * Returns true if the {@link #getNextToken()} method
         * returns a valid token.
         * <p>
         *  如果{@link #getNextToken()}方法返回有效的令牌,则返回true
         * 
         */
        private boolean hasMore() {
            return fCurrentTokenIndex < fTokenCount;
        }

        /**
         * Obtains the token at the current position, then advance
         * the current position by one.
         *
         * throws If there's no such next token, this method throws
         * <tt>new XNIException("XPointerProcessingError");</tt>.
         * <p>
         *  在当前位置获取令牌,然后将当前位置前进一个
         * 
         * throws如果没有这样的下一个标记,这个方法会抛出<tt> new XNIException("XPointerProcessingError"); </tt>
         * 
         */
        private int nextToken() throws XNIException {
            if (fCurrentTokenIndex == fTokenCount) {
                reportError("XPointerProcessingError", null);
            }
            return fTokens[fCurrentTokenIndex++];
        }

        /**
         * Obtains the token at the current position, without advancing
         * the current position.
         *
         * If there's no such next token, this method throws
         * <tt>new XNIException("XPointerProcessingError");</tt>.
         * <p>
         *  在当前位置获取令牌,而不推进当前位置
         * 
         *  如果没有这样的下一个标记,这个方法会抛出<tt> new XNIException("XPointerProcessingError"); </tt>
         * 
         */
        private int peekToken() throws XNIException {
            if (fCurrentTokenIndex == fTokenCount) {
                reportError("XPointerProcessingError", null);
            }
            return fTokens[fCurrentTokenIndex];
        }

        /**
         * Obtains the token at the current position as a String.
         *
         * If there's no current token or if the current token
         * is not a string token, this method throws
         * If there's no such next token, this method throws
         * <tt>new XNIException("XPointerProcessingError");</tt>.
         * <p>
         *  在当前位置获取一个字符串的令牌
         * 
         *  如果没有当前令牌或者当前令牌不是字符串令牌,则此方法抛出如果没有这样的下一个令牌,此方法将抛出<tt> new XNIException("XPointerProcessingError"); </tt>
         * 。
         * 
         */
        private String nextTokenAsString() throws XNIException {
            String tokenStrint = getTokenString(nextToken());
            if (tokenStrint == null) {
                reportError("XPointerProcessingError", null);
            }
            return tokenStrint;
        }
    }

    /**
     *
     * The XPointer expression scanner.  Scans the XPointer framework expression.
     *
     * @xerces.internal
     *
     * <p>
     *  XPointer表达式扫描器扫描XPointer框架表达式
     * 
     *  @xercesinternal
     * 
     */
    private class Scanner {

        /**
         * 7-bit ASCII subset
         *
         *  0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
         *  0,  0,  0,  0,  0,  0,  0,  0,  0, HT, LF,  0,  0, CR,  0,  0,  // 0
         *  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  // 1
         * SP,  !,  ",  #,  $,  %,  &,  ',  (,  ),  *,  +,  ,,  -,  .,  /,  // 2
         *  0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  :,  ;,  <,  =,  >,  ?,  // 3
         *  @,  A,  B,  C,  D,  E,  F,  G,  H,  I,  J,  K,  L,  M,  N,  O,  // 4
         *  P,  Q,  R,  S,  T,  U,  V,  W,  X,  Y,  Z,  [,  \,  ],  ^,  _,  // 5
         *  `,  a,  b,  c,  d,  e,  f,  g,  h,  i,  j,  k,  l,  m,  n,  o,  // 6
         *  p,  q,  r,  s,  t,  u,  v,  w,  x,  y,  z,  {,  |,  },  ~, DEL  // 7
         * <p>
         *  7位ASCII子集
         * 
         * 0 1 2 3 4 5 6 7 8 9 ABCDEF 0,0,0,0,0,0,0,0,0,HT,LF,0,0,CR,0,0,0 // 0,0,0, 0,0,0,0,0,0,0,0,1,0,0,0,0,0
         * ,0, ,*,+,,,  - ,,/,// 2 0,1,2,3,4,5,6,7,8,9, A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W a,b,c,d,e,
         * f,g,h,i,j,k,l,m,n, o,// 6 p,q,r,s,t,u,v,w,x,y,z,{,。
         * 
         */
        private static final byte CHARTYPE_INVALID = 0, // invalid XML character
                CHARTYPE_OTHER = 1, // not special - one of "#%&;?\`{}~" or DEL
                CHARTYPE_WHITESPACE = 2, // one of "\t\n\r " (0x09, 0x0A, 0x0D, 0x20)
                CHARTYPE_CARRET = 3, // ^
                CHARTYPE_OPEN_PAREN = 4, // '(' (0x28)
                CHARTYPE_CLOSE_PAREN = 5, // ')' (0x29)
                CHARTYPE_MINUS = 6, // '-' (0x2D)
                CHARTYPE_PERIOD = 7, // '.' (0x2E)
                CHARTYPE_SLASH = 8, // '/' (0x2F)
                CHARTYPE_DIGIT = 9, // '0'-'9' (0x30 to 0x39)
                CHARTYPE_COLON = 10, // ':' (0x3A)
                CHARTYPE_EQUAL = 11, // '=' (0x3D)
                CHARTYPE_LETTER = 12, // 'A'-'Z' or 'a'-'z' (0x41 to 0x5A and 0x61 to 0x7A)
                CHARTYPE_UNDERSCORE = 13, // '_' (0x5F)
                CHARTYPE_NONASCII = 14; // Non-ASCII Unicode codepoint (>= 0x80)

        private final byte[] fASCIICharMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2,
                0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                2, 1, 1, 1, 1, 1, 1, 1, 4, 5, 1, 1, 1, 6, 7, 8, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 10, 1, 1, 11, 1, 1, 1, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 1, 1, 1, 3, 13, 1, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 1, 1, 1, 1, 1 };

        //
        // Data
        //
        /** Symbol table. */
        private SymbolTable fSymbolTable;

        /**
         * Constructs an XPointer Framework expression scanner.
         *
         * <p>
         *  构造XPointer Framework表达式扫描程序
         * 
         * 
         * @param symbolTable SymbolTable
         */
        private Scanner(SymbolTable symbolTable) {
            // save pool and tokens
            fSymbolTable = symbolTable;

        } // <init>(SymbolTable)

        /**
         * Scans the XPointer Expression
         *
         * <p>
         *  扫描XPointer表达式
         * 
         */
        private boolean scanExpr(SymbolTable symbolTable, Tokens tokens,
                String data, int currentOffset, int endOffset)
                throws XNIException {

            int ch;
            int openParen = 0;
            int closeParen = 0;
            int nameOffset, dataOffset;
            boolean isQName = false;
            String name = null;
            String prefix = null;
            String schemeData = null;
            StringBuffer schemeDataBuff = new StringBuffer();

            while (true) {

                if (currentOffset == endOffset) {
                    break;
                }
                ch = data.charAt(currentOffset);

                //
                while (ch == ' ' || ch == 0x0A || ch == 0x09 || ch == 0x0D) {
                    if (++currentOffset == endOffset) {
                        break;
                    }
                    ch = data.charAt(currentOffset);
                }
                if (currentOffset == endOffset) {
                    break;
                }

                //
                // [1]    Pointer      ::=    Shorthand | SchemeBased
                // [2]    Shorthand    ::=    NCName
                // [3]    SchemeBased  ::=    PointerPart (S? PointerPart)*
                // [4]    PointerPart  ::=    SchemeName '(' SchemeData ')'
                // [5]    SchemeName   ::=    QName
                // [6]    SchemeData   ::=    EscapedData*
                // [7]    EscapedData  ::=    NormalChar | '^(' | '^)' | '^^' | '(' SchemeData ')'
                // [8]    NormalChar   ::=    UnicodeChar - [()^]
                // [9]    UnicodeChar  ::=    [#x0-#x10FFFF]
                // [?]    QName        ::=    (NCName ':')? NCName
                // [?]    NCName       ::=    (Letter | '_') (NCNameChar)*
                // [?]    NCNameChar   ::=    Letter | Digit | '.' | '-' | '_'  (ascii subset of 'NCNameChar')
                // [?]    Letter       ::=    [A-Za-z]                              (ascii subset of 'Letter')
                // [?]    Digit        ::=    [0-9]                                  (ascii subset of 'Digit')
                //
                byte chartype = (ch >= 0x80) ? CHARTYPE_NONASCII
                        : fASCIICharMap[ch];

                switch (chartype) {

                case CHARTYPE_OPEN_PAREN: // '('
                    addToken(tokens, Tokens.XPTRTOKEN_OPEN_PAREN);
                    openParen++;
                    ++currentOffset;
                    break;

                case CHARTYPE_CLOSE_PAREN: // ')'
                    addToken(tokens, Tokens.XPTRTOKEN_CLOSE_PAREN);
                    closeParen++;
                    ++currentOffset;
                    break;

                case CHARTYPE_CARRET:
                case CHARTYPE_COLON:
                case CHARTYPE_DIGIT:
                case CHARTYPE_EQUAL:
                case CHARTYPE_LETTER:
                case CHARTYPE_MINUS:
                case CHARTYPE_NONASCII:
                case CHARTYPE_OTHER:
                case CHARTYPE_PERIOD:
                case CHARTYPE_SLASH:
                case CHARTYPE_UNDERSCORE:
                case CHARTYPE_WHITESPACE:
                    // Scanning SchemeName | Shorthand
                    if (openParen == 0) {
                        nameOffset = currentOffset;
                        currentOffset = scanNCName(data, endOffset,
                                currentOffset);

                        if (currentOffset == nameOffset) {
                            reportError("InvalidShortHandPointer",
                                    new Object[] { data });
                            return false;
                        }

                        if (currentOffset < endOffset) {
                            ch = data.charAt(currentOffset);
                        } else {
                            ch = -1;
                        }

                        name = symbolTable.addSymbol(data.substring(nameOffset,
                                currentOffset));
                        prefix = XMLSymbols.EMPTY_STRING;

                        // The name is a QName => a SchemeName
                        if (ch == ':') {
                            if (++currentOffset == endOffset) {
                                return false;
                            }

                            ch = data.charAt(currentOffset);
                            prefix = name;
                            nameOffset = currentOffset;
                            currentOffset = scanNCName(data, endOffset,
                                    currentOffset);

                            if (currentOffset == nameOffset) {
                                return false;
                            }

                            if (currentOffset < endOffset) {
                                ch = data.charAt(currentOffset);
                            } else {
                                ch = -1;
                            }

                            isQName = true;
                            name = symbolTable.addSymbol(data.substring(
                                    nameOffset, currentOffset));
                        }

                        // REVISIT:
                        if (currentOffset != endOffset) {
                            addToken(tokens, Tokens.XPTRTOKEN_SCHEMENAME);
                            tokens.addToken(prefix);
                            tokens.addToken(name);
                            isQName = false;
                        } else if (currentOffset == endOffset) {
                            // NCName => Shorthand
                            addToken(tokens, Tokens.XPTRTOKEN_SHORTHAND);
                            tokens.addToken(name);
                            isQName = false;
                        }

                        // reset open/close paren for the next pointer part
                        closeParen = 0;

                        break;

                    } else if (openParen > 0 && closeParen == 0 && name != null) {
                        // Scanning SchemeData
                        dataOffset = currentOffset;
                        currentOffset = scanData(data, schemeDataBuff,
                                endOffset, currentOffset);

                        if (currentOffset == dataOffset) {
                            reportError("InvalidSchemeDataInXPointer",
                                    new Object[] { data });
                            return false;
                        }

                        if (currentOffset < endOffset) {
                            ch = data.charAt(currentOffset);
                        } else {
                            ch = -1;
                        }

                        schemeData = symbolTable.addSymbol(schemeDataBuff
                                .toString());
                        addToken(tokens, Tokens.XPTRTOKEN_SCHEMEDATA);
                        tokens.addToken(schemeData);

                        // reset open/close paren for the next pointer part
                        openParen = 0;
                        schemeDataBuff.delete(0, schemeDataBuff.length());

                    } else {
                        // ex. schemeName()
                        // Should we throw an exception with a more suitable message instead??
                        return false;
                    }
                }
            } // end while
            return true;
        }

        /**
         * Scans a NCName.
         * From Namespaces in XML
         * [5] NCName ::= (Letter | '_') (NCNameChar)*
         * [6] NCNameChar ::= Letter | Digit | '.' | '-' | '_' | CombiningChar | Extender
         *
         * <p>
         * 从NC中的命名空间扫描NCName [5] NCName :: =(Letter |'_')(NCNameChar)* [6] NCNameChar :: = Letter |数字| ''| ' - '
         * | '_'|组合Char |扩展器。
         * 
         * 
         * @param data A String containing the XPointer expression
         * @param endOffset The int XPointer expression length
         * @param currentOffset An int representing the current position of the XPointer expression pointer
         */
        private int scanNCName(String data, int endOffset, int currentOffset) {
            int ch = data.charAt(currentOffset);
            if (ch >= 0x80) {
                if (!XMLChar.isNameStart(ch)) {
                    return currentOffset;
                }
            } else {
                byte chartype = fASCIICharMap[ch];
                if (chartype != CHARTYPE_LETTER
                        && chartype != CHARTYPE_UNDERSCORE) {
                    return currentOffset;
                }
            }

            //while (currentOffset++ < endOffset) {
            while (++currentOffset < endOffset) {
                ch = data.charAt(currentOffset);
                if (ch >= 0x80) {
                    if (!XMLChar.isName(ch)) {
                        break;
                    }
                } else {
                    byte chartype = fASCIICharMap[ch];
                    if (chartype != CHARTYPE_LETTER
                            && chartype != CHARTYPE_DIGIT
                            && chartype != CHARTYPE_PERIOD
                            && chartype != CHARTYPE_MINUS
                            && chartype != CHARTYPE_UNDERSCORE) {
                        break;
                    }
                }
            }
            return currentOffset;
        }

        /**
         * Scans the SchemeData.
         * [6]    SchemeData   ::=    EscapedData*
         * [7]    EscapedData  ::=    NormalChar | '^(' | '^)' | '^^' | '(' SchemeData ')'
         * [8]    NormalChar   ::=    UnicodeChar - [()^]
         * [9]    UnicodeChar  ::=    [#x0-#x10FFFF]
         *
         * <p>
         *  扫描SchemeData [6] SchemeData :: = EscapedData * [7] EscapedData :: = NormalChar | '^('|'^)'| '^^'| '(
         * 'SchemeData')'[8] NormalChar :: = UnicodeChar  -  [()^] [9] UnicodeChar :: = [#x0-#x10FFFF]。
         * 
         */
        private int scanData(String data, StringBuffer schemeData,
                int endOffset, int currentOffset) {
            while (true) {

                if (currentOffset == endOffset) {
                    break;
                }

                int ch = data.charAt(currentOffset);
                byte chartype = (ch >= 0x80) ? CHARTYPE_NONASCII
                        : fASCIICharMap[ch];

                if (chartype == CHARTYPE_OPEN_PAREN) {
                    schemeData.append(ch);
                    //schemeData.append(Tokens.XPTRTOKEN_OPEN_PAREN);
                    currentOffset = scanData(data, schemeData, endOffset,
                            ++currentOffset);
                    if (currentOffset == endOffset) {
                        return currentOffset;
                    }

                    ch = data.charAt(currentOffset);
                    chartype = (ch >= 0x80) ? CHARTYPE_NONASCII
                            : fASCIICharMap[ch];

                    if (chartype != CHARTYPE_CLOSE_PAREN) {
                        return endOffset;
                    }
                    schemeData.append((char) ch);
                    ++currentOffset;//

                } else if (chartype == CHARTYPE_CLOSE_PAREN) {
                    return currentOffset;

                } else  if (chartype == CHARTYPE_CARRET) {
                    ch = data.charAt(++currentOffset);
                    chartype = (ch >= 0x80) ? CHARTYPE_NONASCII
                            : fASCIICharMap[ch];

                    if (chartype != CHARTYPE_CARRET
                            && chartype != CHARTYPE_OPEN_PAREN
                            && chartype != CHARTYPE_CLOSE_PAREN) {
                        break;
                    }
                    schemeData.append((char) ch);
                    ++currentOffset;

                } else {
                    schemeData.append((char) ch);
                    ++currentOffset;//
                }
            }

            return currentOffset;
        }

        //
        // Protected methods
        //

        /**
         * This method adds the specified token to the token list. By
         * default, this method allows all tokens. However, subclasses
         * of the XPathExprScanner can override this method in order
         * to disallow certain tokens from being used in the scanned
         * XPath expression. This is a convenient way of allowing only
         * a subset of XPath.
         * <p>
         *  此方法将指定的标记添加到标记列表默认情况下,此方法允许所有标记。
         * 但是,XPathExprScanner的子类可以覆盖此方法,以禁止在扫描的XPath表达式中使用某些标记这是一种方便的方法, XPath的子集。
         * 
         */
        protected void addToken(Tokens tokens, int token) throws XNIException {
            tokens.addToken(token);
        } // addToken(int)

    } // class Scanner

    // ************************************************************************
    //  Overridden XMLDocumentHandler methods
    // ************************************************************************
    /**
     * If the comment is a child of a matched element, then pass else return.
     *
     * <p>
     *  如果注释是匹配元素的子元素,则传递else返回
     * 
     * 
     * @param text   The text in the comment.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by application to signal an error.
     */
    public void comment(XMLString text, Augmentations augs) throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.comment(text, augs);
    }

    /**
     * A processing instruction. Processing instructions consist of a
     * target name and, optionally, text data. The data is only meaningful
     * to the application.
     * <p>
     * Typically, a processing instruction's data will contain a series
     * of pseudo-attributes. These pseudo-attributes follow the form of
     * element attributes but are <strong>not</strong> parsed or presented
     * to the application as anything other than text. The application is
     * responsible for parsing the data.
     *
     * <p>
     * 处理指令处理指令由目标名称和可选的文本数据组成。数据仅对应用程序有意义
     * <p>
     *  通常,处理指令的数据将包含一系列伪属性这些伪属性遵循元素属性的形式,但是<strong>不</strong>作为除文本之外的任何东西解析或呈现给应用程序应用程序负责解析数据
     * 
     * 
     * @param target The target.
     * @param data   The data or null if none specified.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void processingInstruction(String target, XMLString data,
            Augmentations augs) throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.processingInstruction(target, data, augs);
    }

    /**
     * The start of an element.
     *
     * <p>
     *  元素的开始
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs       Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void startElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {
        if (!resolveXPointer(element, attributes, augs,
                XPointerPart.EVENT_ELEMENT_START)) {

            // xml:base and xml:lang processing
                if (fFixupBase) {
                processXMLBaseAttributes(attributes);
                }
            if (fFixupLang) {
                processXMLLangAttributes(attributes);
            }

            // set the context invalid if the element till an element from the result infoset is included
            fNamespaceContext.setContextInvalid();

            return;
        }
        super.startElement(element, attributes, augs);
    }

    /**
     * An empty element.
     *
     * <p>
     *  空元素
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs       Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void emptyElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {
        if (!resolveXPointer(element, attributes, augs,
                XPointerPart.EVENT_ELEMENT_EMPTY)) {
            // xml:base and xml:lang processing
                if (fFixupBase) {
                processXMLBaseAttributes(attributes);
                }
            if (fFixupLang) {
                processXMLLangAttributes(attributes);
            }
            // no need to restore restoreBaseURI() for xml:base and xml:lang processing

            // set the context invalid if the element till an element from the result infoset is included
            fNamespaceContext.setContextInvalid();
            return;
        }
        super.emptyElement(element, attributes, augs);
    }

    /**
     * Character content.
     *
     * <p>
     *  字符内容
     * 
     * 
     * @param text   The content.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void characters(XMLString text, Augmentations augs)
            throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.characters(text, augs);
    }

    /**
     * Ignorable whitespace. For this method to be called, the document
     * source must have some way of determining that the text containing
     * only whitespace characters should be considered ignorable. For
     * example, the validator can determine if a length of whitespace
     * characters in the document are ignorable based on the element
     * content model.
     *
     * <p>
     * 可忽略的空格对于要调用的此方法,文档源必须具有某种方式来确定只包含空格字符的文本应该被视为可忽略。例如,验证器可以确定文档中的空白字符的长度是否可基于元素内容模型
     * 
     * 
     * @param text   The ignorable whitespace.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void ignorableWhitespace(XMLString text, Augmentations augs)
            throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.ignorableWhitespace(text, augs);
    }

    /**
     * The end of an element.
     *
     * <p>
     *  元素的结尾
     * 
     * 
     * @param element The name of the element.
     * @param augs    Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void endElement(QName element, Augmentations augs)
            throws XNIException {
        if (!resolveXPointer(element, null, augs,
                XPointerPart.EVENT_ELEMENT_END)) {

            // no need to restore restoreBaseURI() for xml:base and xml:lang processing
            return;
        }
        super.endElement(element, augs);
    }

    /**
     * The start of a CDATA section.
     *
     * <p>
     *  CDATA节的开始
     * 
     * 
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void startCDATA(Augmentations augs) throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.startCDATA(augs);
    }

    /**
     * The end of a CDATA section.
     *
     * <p>
     *  CDATA段的结尾
     * 
     * 
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void endCDATA(Augmentations augs) throws XNIException {
        if (!isChildFragmentResolved()) {
            return;
        }
        super.endCDATA(augs);
    }

    // ************************************************************************
    // Overridden XMLComponent methods
    // ************************************************************************
    /**
     * <p>
     * Sets the value of a property. This method is called by the component
     * manager any time after reset when a property changes value.
     * </p>
     * <strong>Note:</strong> Components should silently ignore properties
     * that do not affect the operation of the component.
     *
     * <p>
     * <p>
     *  设置属性的值当属性更改值时,此方法由组件管理器在复位后任何时间调用
     * </p>
     * 
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     *
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                  In general, components should
     *                                  only throw this exception if
     *                                  it is <strong>really</strong>
     *                                  a critical error.
     */
    public void setProperty(String propertyId, Object value)
            throws XMLConfigurationException {

        // Error reporter
        if (propertyId == Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY) {
            if (value != null) {
                fXPointerErrorReporter = (XMLErrorReporter) value;
            } else {
                fXPointerErrorReporter = null;
            }
        }

        // Error handler
        if (propertyId == Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_HANDLER_PROPERTY) {
            if (value != null) {
                fErrorHandler = (XMLErrorHandler) value;
            } else {
                fErrorHandler = null;
            }
        }

        // xml:lang
        if (propertyId == Constants.XERCES_FEATURE_PREFIX
                + Constants.XINCLUDE_FIXUP_LANGUAGE_FEATURE) {
            if (value != null) {
                fFixupLang = ((Boolean)value).booleanValue();
            } else {
                fFixupLang = false;
            }
        }

        // xml:base
        if (propertyId == Constants.XERCES_FEATURE_PREFIX
                + Constants.XINCLUDE_FIXUP_BASE_URIS_FEATURE) {
            if (value != null) {
                fFixupBase = ((Boolean)value).booleanValue();
            } else {
                fFixupBase = false;
            }
        }

        //
        if (propertyId == Constants.XERCES_PROPERTY_PREFIX
                + Constants.NAMESPACE_CONTEXT_PROPERTY) {
            fNamespaceContext = (XIncludeNamespaceSupport) value;
        }

        super.setProperty(propertyId, value);
    }

}
