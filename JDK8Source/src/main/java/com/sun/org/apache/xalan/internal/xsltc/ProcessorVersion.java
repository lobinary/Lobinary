/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 */
/*
 * $Id: ProcessorVersion.java,v 1.2.4.1 2005/08/31 10:30:36 pvedula Exp $
 * <p>
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc;


/**
 * Admin class that assigns a version number to the XSLTC software.
 * The version number is made up from three fields as in:
 * MAJOR.MINOR[.DELTA]. Fields are incremented based on the following:
 * DELTA field: changes for each bug fix, developer fixing the bug should
 *              increment this field.
 * MINOR field: API changes or a milestone culminating from several
 *              bug fixes. DELTA field goes to zero and MINOR is
 *              incremented such as: {1.0,1.0.1,1.0.2,1.0.3,...1.0.18,1.1}
 * MAJOR field: milestone culminating in fundamental API changes or
 *              architectural changes.  MINOR field goes to zero
 *              and MAJOR is incremented such as: {...,1.1.14,1.2,2.0}
 * Stability of a release follows: X.0 > X.X > X.X.X
 * <p>
 *  $ Id：ProcessorVersion.java,v 1.2.4.1 2005/08/31 10:30:36 pvedula Exp $
 * 
 * 
 * @author G. Todd Miller
 */
public class ProcessorVersion {
    private static int MAJOR = 1;
    private static int MINOR = 0;
    private static int DELTA = 0;

    public static void main(String[] args) {
        System.out.println("XSLTC version " + MAJOR + "." + MINOR +
            ((DELTA > 0) ? ("."+DELTA) : ("")));
    }
}
