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
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: LocaleUtility.java,v 1.2.4.1 2005/09/15 08:15:47 suresh_emailid Exp $
 * <p>
 */

package com.sun.org.apache.xml.internal.utils;

import java.util.Locale;

/**
/* <p>
/*  $ Id：LocaleUtility.java,v 1.2.4.1 2005/09/15 08:15:47 suresh_emailid Exp $
/* 
/* 
 * @author Igor Hersht, igorh@ca.ibm.com
 */
public class LocaleUtility {
    /**
     * IETF RFC 1766 tag separator
     * <p>
     */
    public final static char IETF_SEPARATOR = '-';
    public final static String EMPTY_STRING = "";


 public static Locale langToLocale(String lang) {
       if((lang == null) || lang.equals(EMPTY_STRING)){ // not specified => getDefault
            return Locale.getDefault();
       }
        String language = EMPTY_STRING;
        String country =  EMPTY_STRING;
        String variant =  EMPTY_STRING;

        int i1 = lang.indexOf(IETF_SEPARATOR);
        if (i1 < 0) {
            language = lang;
        } else {
            language = lang.substring(0, i1);
            ++i1;
            int i2 = lang.indexOf(IETF_SEPARATOR, i1);
            if (i2 < 0) {
                country = lang.substring(i1);
            } else {
                country = lang.substring(i1, i2);
                variant = lang.substring(i2+1);
            }
        }

        if(language.length() == 2){
           language = language.toLowerCase();
        }else {
          language = EMPTY_STRING;
        }

        if(country.length() == 2){
           country = country.toUpperCase();
        }else {
          country = EMPTY_STRING;
        }

        if((variant.length() > 0) &&
        ((language.length() == 2) ||(country.length() == 2))){
           variant = variant.toUpperCase();
        }else{
            variant = EMPTY_STRING;
        }

        return new Locale(language, country, variant );
    }



 }
