/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Version.java,v 1.1.2.1 2005/08/01 02:11:19 jeffsuttor Exp $
 * <p>
 *  $ Id：Version.java,v 1.1.2.1 2005/08/01 02:11:19 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal;

/**
 * Administrative class to keep track of the version number of
 * the Xalan release.
 * <P>This class implements the upcoming standard of having
 * org.apache.project-name.Version.getVersion() be a standard way
 * to get version information.  This class will replace the older
 * com.sun.org.apache.xalan.internal.processor.Version class.</P>
 * <P>See also: com/sun/org/apache/xalan/internal/res/XSLTInfo.properties for
 * information about the version of the XSLT spec we support.</P>
 * @xsl.usage general
 * <p>
 *  用于跟踪Xalan版本的版本号的管理类。 <P>这个类实现了将org.apache.project-name.Version.getVersion()作为获取版本信息的标准方法的即将到来的标准。
 * 此类将替换旧的com.sun.org.apache.xalan.internal.processor.Version类。
 * </P> <P>另请参阅：com / sun / org / apache / xalan / internal / res / XSLTInfo.properties有关我们支持的XSLT规范版本的信
 * 息。
 * 此类将替换旧的com.sun.org.apache.xalan.internal.processor.Version类。</P> @ xsl.usage general。
 * 
 */
public class Version
{

  /**
   * Get the basic version string for the current Xalan release.
   * Version String formatted like
   * <CODE>"<B>Xalan</B> <B>Java</B> v.r[.dd| <B>D</B>nn]"</CODE>.
   *
   * Futurework: have this read version info from jar manifest.
   *
   * <p>
   *  获取当前Xalan版本的基本版本字符串。版本字符串格式为<CODE>"<B> Xalan </B> <B> Java </B> v.r [.dd | <B> D </B> nn]"</CODE>。
   * 
   *  未来工作：有这个从jar清单读取版本信息。
   * 
   * 
   * @return String denoting our current version
   */
  public static String getVersion()
  {
     return getProduct()+" "+getImplementationLanguage()+" "
           +getMajorVersionNum()+"."+getReleaseVersionNum()+"."
           +( (getDevelopmentVersionNum() > 0) ?
               ("D"+getDevelopmentVersionNum()) : (""+getMaintenanceVersionNum()));
  }

  /**
   * Print the processor version to the command line.
   *
   * <p>
   * 将处理器版本打印到命令行。
   * 
   * 
   * @param argv command line arguments, unused.
   */
  public static void _main(String argv[])
  {
    System.out.println(getVersion());
  }

  /**
   * Name of product: Xalan.
   * <p>
   *  产品名称：Xalan。
   * 
   */
  public static String getProduct()
  {
    return "Xalan";
  }

  /**
   * Implementation Language: Java.
   * <p>
   *  实现语言：Java。
   * 
   */
  public static String getImplementationLanguage()
  {
    return "Java";
  }


  /**
   * Major version number.
   * Version number. This changes only when there is a
   *          significant, externally apparent enhancement from
   *          the previous release. 'n' represents the n'th
   *          version.
   *
   *          Clients should carefully consider the implications
   *          of new versions as external interfaces and behaviour
   *          may have changed.
   * <p>
   *  主版本号。版本号。只有当前一版本具有显着的外部明显增强时,此更改才会更改。 'n'表示第n个版本。
   * 
   *  客户端应该仔细考虑新版本的影响,因为外部接口和行为可能已更改。
   * 
   */
  public static int getMajorVersionNum()
  {
    return 2;

  }

  /**
   * Release Number.
   * Release number. This changes when:
   *            -  a new set of functionality is to be added, eg,
   *               implementation of a new W3C specification.
   *            -  API or behaviour change.
   *            -  its designated as a reference release.
   * <p>
   *  版本号。版本号。这在以下情况下改变： - 要添加新的功能集,例如,实施新的W3C规范。 -  API或行为更改。 - 其被指定为参考释放。
   * 
   */
  public static int getReleaseVersionNum()
  {
    return 7;
  }

  /**
   * Maintenance Drop Number.
   * Optional identifier used to designate maintenance
   *          drop applied to a specific release and contains
   *          fixes for defects reported. It maintains compatibility
   *          with the release and contains no API changes.
   *          When missing, it designates the final and complete
   *          development drop for a release.
   * <p>
   *  维护液滴数。用于指定应用于特定版本的维护水滴的可选标识符,包含报告的缺陷的修复程序。它保持与发行版的兼容性,并且不包含API更改。当缺失时,它指定发布的最终和完整的开发下降。
   * 
   */
  public static int getMaintenanceVersionNum()
  {
    return 0;
  }

  /**
   * Development Drop Number.
   * Optional identifier designates development drop of
   *          a specific release. D01 is the first development drop
   *          of a new release.
   *
   *          Development drops are works in progress towards a
   *          compeleted, final release. A specific development drop
   *          may not completely implement all aspects of a new
   *          feature, which may take several development drops to
   *          complete. At the point of the final drop for the
   *          release, the D suffix will be omitted.
   *
   *          Each 'D' drops can contain functional enhancements as
   *          well as defect fixes. 'D' drops may not be as stable as
   *          the final releases.
   * <p>
   *  发展水滴数。可选标识符指定特定发行版的开发。 D01是新版本的第一个开发下拉。
   * 
   *  发展落地正在朝着最终发布的方向发展。具体的开发下降可能不能完全实现新特征的所有方面,这可能需要几个开发下降来完成。在发布的最后一个点,D后缀将被省略。
   */
  public static int getDevelopmentVersionNum()
  {
    try {
        if ((new String("")).length() == 0)
          return 0;
        else
          return Integer.parseInt("");
    } catch (NumberFormatException nfe) {
           return 0;
    }
  }
}
