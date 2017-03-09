/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 */

package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xpath.internal.XPathContext;
/*
 *
 * <p>
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 * @author igorh
 *
 * Simple wrapper to DTM and XPathContext objects.
 * Used in XRTreeFrag for caching references to the objects.
 */
 public final class DTMXRTreeFrag {
  private DTM m_dtm;
  private int m_dtmIdentity = DTM.NULL;
  private XPathContext m_xctxt;

  public DTMXRTreeFrag(int dtmIdentity, XPathContext xctxt){
      m_xctxt = xctxt;
      m_dtmIdentity = dtmIdentity;
      m_dtm = xctxt.getDTM(dtmIdentity);
    }

  public final void destruct(){
    m_dtm = null;
    m_xctxt = null;
 }

final  DTM getDTM(){return m_dtm;}
public final  int getDTMIdentity(){return m_dtmIdentity;}
final  XPathContext getXPathContext(){return m_xctxt;}

public final int hashCode() { return m_dtmIdentity; }
public final boolean equals(Object obj) {
   if (obj instanceof DTMXRTreeFrag) {
       return (m_dtmIdentity == ((DTMXRTreeFrag)obj).getDTMIdentity());
   }
   return false;
 }

}
