/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2001-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2001-2003 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 */
package com.sun.org.apache.xerces.internal.xinclude;

import java.util.Stack;
import com.sun.org.apache.xerces.internal.xinclude.XPointerSchema;

public class XPointerFramework{

        /*
                Todo's by next integration.
                While constructing schema names and uris use a dynamic datastructure.
        /* <p>
        /*  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
        /* 
        /* 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
        /* 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
        /* 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
        /* 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
        /*  ================================================== ==================。
         */

    XPointerSchema [] fXPointerSchema;
    String [] fSchemaPointerName;
    String [] fSchemaPointerURI;
    String fSchemaPointer;
    String fCurrentSchemaPointer;
    Stack fSchemaNotAvailable;
    int fCountSchemaName = 0;
    int schemaLength = 0;
    XPointerSchema fDefaultXPointerSchema;

    public XPointerFramework(){
        this(null);
    }

    public XPointerFramework(XPointerSchema [] xpointerschema){
        fXPointerSchema = xpointerschema;
        fSchemaNotAvailable = new Stack();
    }

    public void reset(){
        fXPointerSchema = null;
        fXPointerSchema = null;
        fCountSchemaName = 0;
        schemaLength = 0;
        fSchemaPointerName = null;
        fSchemaPointerURI = null;
        fDefaultXPointerSchema = null;
        fCurrentSchemaPointer = null;
    }

    public void setXPointerSchema(XPointerSchema [] xpointerschema){
        fXPointerSchema = xpointerschema;
    }

    public void setSchemaPointer(String schemaPointer){
        fSchemaPointer = schemaPointer;
    }

    public XPointerSchema getNextXPointerSchema(){
        int  i=fCountSchemaName;
        if(fSchemaPointerName == null){
            getSchemaNames();
        }
        if(fDefaultXPointerSchema == null){
            getDefaultSchema();
        }
        if(fDefaultXPointerSchema.getXpointerSchemaName().equalsIgnoreCase(fSchemaPointerName[i])){
            fDefaultXPointerSchema.reset();
            fDefaultXPointerSchema.setXPointerSchemaPointer(fSchemaPointerURI[i]);
            fCountSchemaName = ++i;
            return  getDefaultSchema();
        }
        if(fXPointerSchema == null){
            fCountSchemaName = ++i;
            return null;
        }

        int fschemalength = fXPointerSchema.length;

        for(;fSchemaPointerName[i] != null; i++){
            for(int j=0; j<fschemalength; j++ ){
                if(fSchemaPointerName[i].equalsIgnoreCase(fXPointerSchema[j].getXpointerSchemaName())){
                    fXPointerSchema[j].setXPointerSchemaPointer(fSchemaPointerURI[i]);
                    fCountSchemaName = ++i;
                    return fXPointerSchema[j];
                }
            }

            if(fSchemaNotAvailable == null)
            fSchemaNotAvailable = new Stack();

            fSchemaNotAvailable.push(fSchemaPointerName[i]);
        }
        return null;
    }

    public XPointerSchema getDefaultSchema(){
        if(fDefaultXPointerSchema == null)
            fDefaultXPointerSchema = new XPointerElementHandler();
        return fDefaultXPointerSchema;
    }

    public void getSchemaNames(){
        int count =0;
        int index =0, lastindex =0;
        int schemapointerindex  =0, schemapointerURIindex=0;
        char c;
        int length = fSchemaPointer.length();
        fSchemaPointerName = new String [5];
        fSchemaPointerURI = new String [5];

        index = fSchemaPointer.indexOf('(');
        if( index <= 0)
            return;

        fSchemaPointerName[schemapointerindex++] = fSchemaPointer.substring(0, index++).trim();
        lastindex = index;
        String tempURI = null;
        count++;

        while(index < length){
            c = fSchemaPointer.charAt(index);
            if(c == '(')
                count++;
            if(c == ')')
                count--;
            if(count==0 ){
                tempURI = fSchemaPointer.substring(lastindex, index).trim();
                fSchemaPointerURI[schemapointerURIindex++] = getEscapedURI(tempURI);
                lastindex = index;
                if((index = fSchemaPointer.indexOf('(', lastindex)) != -1){
                    fSchemaPointerName[schemapointerindex++] = fSchemaPointer.substring(lastindex+1, index).trim();
                    count++;
                    lastindex = index+1;
                }
                else{
                    index = lastindex;
                }
            }
            index++;
        }
        schemaLength = schemapointerURIindex -1;
    }

    public String   getEscapedURI(String URI){
        return URI;
    }

    public int getSchemaCount(){
        return schemaLength;
    }

    public int getCurrentPointer(){
        return fCountSchemaName;
    }

}//XPointerFramwork
