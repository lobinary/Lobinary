/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.java_cup.internal.runtime;

/**
 * Defines the Symbol class, which is used to represent all terminals
 * and nonterminals while parsing.  The lexer should pass CUP Symbols
 * and CUP returns a Symbol.
 *
 * <p>
 *  定义Symbol类,用于在解析时表示所有终端和非终结符。词法分析器应通过CUP符号并且CUP返回一个符号。
 * 
 * 
 * @author  Frank Flannery
 */

/* ****************************************************************
  Class Symbol
  what the parser expects to receive from the lexer.
  the token is identified as follows:
  sym:    the symbol type
  parse_state: the parse state.
  value:  is the lexical value of type Object
  left :  is the left position in the original input file
  right:  is the right position in the original input file
/* <p>
/*  类符号解析器期望从词法分析器接收的内容。令牌标识如下：sym：符号类型parse_state：解析状态。
/*  value：是Object类型的词法值left：是原始输入文件中的左侧位置right：是原始输入文件中的右侧位置。
/* 
/* 
******************************************************************/

public class Symbol {

/*******************************
  Constructor for l,r values
/* <p>
/*  l,r值的构造方法
/* 
/* 
 *******************************/

  public Symbol(int id, int l, int r, Object o) {
    this(id);
    left = l;
    right = r;
    value = o;
  }

/*******************************
  Constructor for no l,r values
/* <p>
/*  无l,r值的构造函数
/* 
/* 
********************************/

  public Symbol(int id, Object o) {
    this(id);
    left = -1;
    right = -1;
    value = o;
  }

/*****************************
  Constructor for no value
/* <p>
/*  无价值的构造函数
/* 
/* 
  ***************************/

  public Symbol(int sym_num, int l, int r) {
    sym = sym_num;
    left = l;
    right = r;
    value = null;
  }

/***********************************
  Constructor for no value or l,r
/* <p>
/*  没有值的构造函数或l,r
/* 
/* 
***********************************/

  public Symbol(int sym_num) {
    this(sym_num, -1);
    left = -1;
    right = -1;
    value = null;
  }

/***********************************
  Constructor to give a start state
/* <p>
/*  构造器给出一个开始状态
/* 
/* 
***********************************/
  public Symbol(int sym_num, int state)
    {
      sym = sym_num;
      parse_state = state;
    }

/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The symbol number of the terminal or non terminal being represented */
  public int sym;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The parse state to be recorded on the parse stack with this symbol.
   *  This field is for the convenience of the parser and shouldn't be
   *  modified except by the parser.
   * <p>
   *  此字段是为了方便解析器,不应该被解析器修改。
   * 
   */
  public int parse_state;
  /** This allows us to catch some errors caused by scanners recycling
  /* <p>
  /* 
   *  symbols.  For the use of the parser only. [CSA, 23-Jul-1999] */
  boolean used_by_parser = false;

/*******************************
  The data passed to parser
/* <p>
/*  数据传递给解析器
/* 
/* 
 *******************************/

  public int left, right;
  public Object value;

  /*****************************
    Printing this token out. (Override for pretty-print).
  /* <p>
  /*  打印此标记。 (覆盖美丽的打印)。
  /* 
    ****************************/
  public String toString() { return "#"+sym; }
}
