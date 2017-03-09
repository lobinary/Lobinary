/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Debug.java - Print debug messages

/*
 * Copyright 2001-2004 The Apache Software Foundation or its licensors,
 * as applicable.
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
 *  版权所有2001-2004 Apache软件基金会或其许可方(如适用)。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xml.internal.resolver.helpers;

/**
 * Static debugging/messaging class for Catalogs.
 *
 * <p>This class defines a set of static methods that can be called
 * to produce debugging messages. Messages have an associated "debug
 * level" and messages below the current setting are not displayed.</p>
 *
 * <p>
 *  目录的静态调试/消息类。
 * 
 *  <p>此类定义了一组可以调用以生成调试消息的静态方法。消息具有关联的"调试级别",并且不显示低于当前设置的消息。</p>
 * 
 * 
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class Debug {
  /** The internal debug level. */
  protected int debug = 0;

  /** Constructor */
  public Debug() {
    // nop
  }

  /** Set the debug level for future messages. */
  public void setDebug(int newDebug) {
    debug = newDebug;
  }

  /** Get the current debug level. */
  public int getDebug() {
    return debug;
  }

  /**
   * Print debug message (if the debug level is high enough).
   *
   * <p>Prints "the message"</p>
   *
   * <p>
   *  打印调试消息(如果调试级别足够高)。
   * 
   *  <p>打印"信息</p>
   * 
   * 
   * @param level The debug level of this message. This message
   * will only be
   * displayed if the current debug level is at least equal to this
   * value.
   * @param message The text of the message.
   */
  public void message(int level, String message) {
    if (debug >= level) {
      System.out.println(message);
    }
  }

  /**
   * Print debug message (if the debug level is high enough).
   *
   * <p>Prints "the message: spec"</p>
   *
   * <p>
   *  打印调试消息(如果调试级别足够高)。
   * 
   *  <p>打印"消息：spec"</p>
   * 
   * 
   * @param level The debug level of this message. This message
   * will only be
   * displayed if the current debug level is at least equal to this
   * value.
   * @param message The text of the message.
   * @param spec An argument to the message.
   */
  public void message(int level, String message, String spec) {
    if (debug >= level) {
      System.out.println(message + ": " + spec);
    }
  }

  /**
   * Print debug message (if the debug level is high enough).
   *
   * <p>Prints "the message: spec1" and "spec2" indented on the next line.</p>
   *
   * <p>
   *  打印调试消息(如果调试级别足够高)。
   * 
   * 
   * @param level The debug level of this message. This message
   * will only be
   * displayed if the current debug level is at least equal to this
   * value.
   * @param message The text of the message.
   * @param spec1 An argument to the message.
   * @param spec2 Another argument to the message.
   */
  public void message(int level, String message,
                             String spec1, String spec2) {
    if (debug >= level) {
      System.out.println(message + ": " + spec1);
      System.out.println("\t" + spec2);
    }
  }
}
