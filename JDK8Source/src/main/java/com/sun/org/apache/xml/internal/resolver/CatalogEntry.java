/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// CatalogEntry.java - Represents Catalog entries

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

package com.sun.org.apache.xml.internal.resolver;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Represents a Catalog entry.
 *
 * <p>Instances of this class represent individual entries
 * in a Catalog.</p>
 *
 * <p>Each catalog entry has a unique name and is associated with
 * an arbitrary number of arguments (all strings). For example, the
 * TR9401 catalog entry "PUBLIC" has two arguments, a public identifier
 * and a system identifier. Each entry has a unique numeric type,
 * assigned automatically when the entry type is created.</p>
 *
 * <p>The number and type of catalog entries is maintained
 * <em>statically</em>. Catalog classes, or their subclasses, can add
 * new entry types, but all Catalog objects share the same global pool
 * of types.</p>
 *
 * <p>Initially there are no valid entries.</p>
 *
 * <p>
 *  表示商品。
 * 
 *  <p>此类别的实例表示目录中的各个条目。</p>
 * 
 *  <p>每个商品都有一个唯一的名称,并与任意数量的参数(所有字符串)相关联。例如,TR9401商品"PUBLIC"有两个参数,一个公共标识符和一个系统标识符。
 * 每个条目都有唯一的数字类型,在创建条目类型时自动分配。</p>。
 * 
 *  <p>商品的数量和类型会静态维护</em>。目录类或其子类可以添加新的条目类型,但所有Catalog对象共享相同的全局类型池。</p>
 * 
 *  <p>最初没有有效的项目。</p>
 * 
 * 
 * @see Catalog
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class CatalogEntry {
  /** The nextEntry is the ordinal number of the next entry type. */
  protected static int nextEntry = 0;

  /**
   * The entryTypes vector maps catalog entry names
   * (e.g., 'BASE' or 'SYSTEM') to their type (1, 2, etc.).
   * Names are case sensitive.
   * <p>
   * entryTypes向量将商品名称(例如,"BASE"或"SYSTEM")映射到其类型(1,2等)。名称区分大小写。
   * 
   */
  protected static Hashtable entryTypes = new Hashtable();

  /** The entryTypes vector maps catalog entry types to the
  /* <p>
  /* 
      number of arguments they're required to have. */
  protected static Vector entryArgs = new Vector();

  /**
   * Adds a new catalog entry type.
   *
   * <p>
   *  添加新的商品类型。
   * 
   * 
   * @param name The name of the catalog entry type. This must be
   * unique among all types and is case-sensitive. (Adding a duplicate
   * name effectively replaces the old type with the new type.)
   * @param numArgs The number of arguments that this entry type
   * is required to have. There is no provision for variable numbers
   * of arguments.
   * @return The type for the new entry.
   */
  public static int addEntryType(String name, int numArgs) {
    entryTypes.put(name, new Integer(nextEntry));
    entryArgs.add(nextEntry, new Integer(numArgs));
    nextEntry++;

    return nextEntry-1;
  }

  /**
   * Lookup an entry type
   *
   * <p>
   *  查找条目类型
   * 
   * 
   * @param name The name of the catalog entry type.
   * @return The type of the catalog entry with the specified name.
   * @throws InvalidCatalogEntryTypeException if no entry has the
   * specified name.
   */
  public static int getEntryType(String name)
    throws CatalogException {
    if (!entryTypes.containsKey(name)) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }

    Integer iType = (Integer) entryTypes.get(name);

    if (iType == null) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }

    return iType.intValue();
  }

  /**
   * Find out how many arguments an entry is required to have.
   *
   * <p>
   *  了解一个条目需要有多少个参数。
   * 
   * 
   * @param name The name of the catalog entry type.
   * @return The number of arguments that entry type is required to have.
   * @throws InvalidCatalogEntryTypeException if no entry has the
   * specified name.
   */
  public static int getEntryArgCount(String name)
    throws CatalogException {
    return getEntryArgCount(getEntryType(name));
  }

  /**
   * Find out how many arguments an entry is required to have.
   *
   * <p>
   *  了解一个条目需要有多少个参数。
   * 
   * 
   * @param type A valid catalog entry type.
   * @return The number of arguments that entry type is required to have.
   * @throws InvalidCatalogEntryTypeException if the type is invalid.
   */
  public static int getEntryArgCount(int type)
    throws CatalogException {
    try {
      Integer iArgs = (Integer) entryArgs.get(type);
      return iArgs.intValue();
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }
  }

  /** The entry type of this entry */
  protected int entryType = 0;

  /** The arguments associated with this entry */
  protected Vector args = null;

  /**
   * Null constructor; something for subclasses to call.
   * <p>
   *  空构造函数;东西为子类调用。
   * 
   */
  public CatalogEntry() {}

  /**
   * Construct a catalog entry of the specified type.
   *
   * <p>
   *  构造指定类型的商品。
   * 
   * 
   * @param name The name of the entry type
   * @param args A String Vector of arguments
   * @throws InvalidCatalogEntryTypeException if no such entry type
   * exists.
   * @throws InvalidCatalogEntryException if the wrong number of arguments
   * is passed.
   */
  public CatalogEntry(String name, Vector args)
    throws CatalogException {
    Integer iType = (Integer) entryTypes.get(name);

    if (iType == null) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }

    int type = iType.intValue();

    try {
      Integer iArgs = (Integer) entryArgs.get(type);
      if (iArgs.intValue() != args.size()) {
        throw new CatalogException(CatalogException.INVALID_ENTRY);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }

    entryType = type;
    this.args = args;
  }

  /**
   * Construct a catalog entry of the specified type.
   *
   * <p>
   *  构造指定类型的商品。
   * 
   * 
   * @param type The entry type
   * @param args A String Vector of arguments
   * @throws InvalidCatalogEntryTypeException if no such entry type
   * exists.
   * @throws InvalidCatalogEntryException if the wrong number of arguments
   * is passed.
   */
  public CatalogEntry(int type, Vector args)
    throws CatalogException {
    try {
      Integer iArgs = (Integer) entryArgs.get(type);
      if (iArgs.intValue() != args.size()) {
        throw new CatalogException(CatalogException.INVALID_ENTRY);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new CatalogException(CatalogException.INVALID_ENTRY_TYPE);
    }

    entryType = type;
    this.args = args;
  }

  /**
   * Get the entry type.
   *
   * <p>
   *  获取条目类型。
   * 
   * 
   * @return The entry type of the CatalogEntry
   */
  public int getEntryType() {
    return entryType;
  }

  /**
   * Get an entry argument.
   *
   * <p>
   *  获取输入参数。
   * 
   * 
   * @param argNum The argument number (arguments are numbered from 0).
   * @return The specified argument or null if an invalid argNum is
   * provided.
   */
  public String getEntryArg(int argNum) {
    try {
      String arg = (String) args.get(argNum);
      return arg;
    } catch (ArrayIndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * Set an entry argument.
   *
   * <p>Catalogs sometimes need to adjust the catlog entry parameters,
   * for example to make a relative URI absolute with respect to the
   * current base URI. But in general, this function should only be
   * called shortly after object creation to do some sort of cleanup.
   * Catalog entries should not mutate over time.</p>
   *
   * <p>
   *  设置条目参数。
   * 
   * 
   * @param argNum The argument number (arguments are numbered from 0).
   * @throws ArrayIndexOutOfBoundsException if an invalid argument
   * number is provided.
   */
  public void setEntryArg(int argNum, String newspec)
    throws ArrayIndexOutOfBoundsException {
    args.set(argNum, newspec);
  }
}
