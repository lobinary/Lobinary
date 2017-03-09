/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

/**
 * <P>The class that defines the constants that are used to identify generic
 * SQL types, called JDBC types.
 * <p>
 * This class is never instantiated.
 * <p>
 *  <P>定义用于标识通用SQL类型(称为JDBC类型)的常量的类。
 * <p>
 *  这个类永远不会实例化。
 * 
 */
public class Types {

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BIT</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> BIT </code>。
 * 
 */
        public final static int BIT             =  -7;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TINYINT</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> TINYINT </code>。
 * 
 */
        public final static int TINYINT         =  -6;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>SMALLINT</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> SMALLINT </code>。
 * 
 */
        public final static int SMALLINT        =   5;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>INTEGER</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> INTEGER </code>。
 * 
 */
        public final static int INTEGER         =   4;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BIGINT</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> BIGINT </code>。
 * 
 */
        public final static int BIGINT          =  -5;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>FLOAT</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> FLOAT </code>。
 * 
 */
        public final static int FLOAT           =   6;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>REAL</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> REAL </code>。
 * 
 */
        public final static int REAL            =   7;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DOUBLE</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> DOUBLE </code>。
 * 
 */
        public final static int DOUBLE          =   8;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>NUMERIC</code>.
 * <p>
 * <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> NUMERIC </code>。
 * 
 */
        public final static int NUMERIC         =   2;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DECIMAL</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> DECIMAL </code>。
 * 
 */
        public final static int DECIMAL         =   3;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>CHAR</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> CHAR </code>。
 * 
 */
        public final static int CHAR            =   1;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>VARCHAR</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> VARCHAR </code>。
 * 
 */
        public final static int VARCHAR         =  12;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>LONGVARCHAR</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> LONGVARCHAR </code>。
 * 
 */
        public final static int LONGVARCHAR     =  -1;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DATE</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> DATE </code>。
 * 
 */
        public final static int DATE            =  91;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TIME</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> TIME </code>。
 * 
 */
        public final static int TIME            =  92;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TIMESTAMP</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> TIMESTAMP </code>。
 * 
 */
        public final static int TIMESTAMP       =  93;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BINARY</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> BINARY </code>。
 * 
 */
        public final static int BINARY          =  -2;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>VARBINARY</code>.
 * <p>
 *  <P> Java编程语言中的常量,有时称为类型代码,标识通用SQL类型<code> VARBINARY </code>。
 * 
 */
        public final static int VARBINARY       =  -3;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>LONGVARBINARY</code>.
 * <p>
 * <P> Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> LONGVARBINARY </code>。
 * 
 */
        public final static int LONGVARBINARY   =  -4;

/**
 * <P>The constant in the Java programming language
 * that identifies the generic SQL value
 * <code>NULL</code>.
 * <p>
 *  <P> Java编程语言中标识通用SQL值<code> NULL </code>的常量。
 * 
 */
        public final static int NULL            =   0;

    /**
     * The constant in the Java programming language that indicates
     * that the SQL type is database-specific and
     * gets mapped to a Java object that can be accessed via
     * the methods <code>getObject</code> and <code>setObject</code>.
     * <p>
     *  Java编程语言中的常量,指示SQL类型是数据库特定的,并且映射到可以通过方法<code> getObject </code>和<code> setObject </code>访问的Java对象。
     * 
     */
        public final static int OTHER           = 1111;



    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>JAVA_OBJECT</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> JAVA_OBJECT </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int JAVA_OBJECT         = 2000;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>DISTINCT</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> DISTINCT </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int DISTINCT            = 2001;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>STRUCT</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> STRUCT </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int STRUCT              = 2002;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>ARRAY</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> ARRAY </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int ARRAY               = 2003;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>BLOB</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> BLOB </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int BLOB                = 2004;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>CLOB</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> CLOB </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int CLOB                = 2005;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>REF</code>.
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> REF </code>。
     * 
     * 
     * @since 1.2
     */
        public final static int REF                 = 2006;

    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>DATALINK</code>.
     *
     * <p>
     * Java编程语言中的常量,通常称为类型代码,标识通用SQL类型<code> DATALINK </code>。
     * 
     * 
     * @since 1.4
     */
    public final static int DATALINK = 70;

    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>BOOLEAN</code>.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> BOOLEAN </code>。
     * 
     * 
     * @since 1.4
     */
    public final static int BOOLEAN = 16;

    //------------------------- JDBC 4.0 -----------------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>ROWID</code>
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> ROWID </code>
     * 
     * 
     * @since 1.6
     *
     */
    public final static int ROWID = -8;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCHAR</code>
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> NCHAR </code>
     * 
     * 
     * @since 1.6
     */
    public static final int NCHAR = -15;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NVARCHAR</code>.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> NVARCHAR </code>。
     * 
     * 
     * @since 1.6
     */
    public static final int NVARCHAR = -9;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>LONGNVARCHAR</code>.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> LONGNVARCHAR </code>。
     * 
     * 
     * @since 1.6
     */
    public static final int LONGNVARCHAR = -16;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCLOB</code>.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> NCLOB </code>。
     * 
     * 
     * @since 1.6
     */
    public static final int NCLOB = 2011;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>XML</code>.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型<code> XML </code>。
     * 
     * 
     * @since 1.6
     */
    public static final int SQLXML = 2009;

    //--------------------------JDBC 4.2 -----------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code REF CURSOR}.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型{@code REF CURSOR}。
     * 
     * 
     * @since 1.8
     */
    public static final int REF_CURSOR = 2012;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIME WITH TIMEZONE}.
     *
     * <p>
     *  Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型{@code TIME WITH TIMEZONE}。
     * 
     * 
     * @since 1.8
     */
    public static final int TIME_WITH_TIMEZONE = 2013;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIMESTAMP WITH TIMEZONE}.
     *
     * <p>
     * Java编程语言中的常量,有时称为类型代码,用于标识通用SQL类型{@code TIMESTAMP WITH TIMEZONE}。
     * 
     * @since 1.8
     */
    public static final int TIMESTAMP_WITH_TIMEZONE = 2014;

    // Prevent instantiation
    private Types() {}
}
