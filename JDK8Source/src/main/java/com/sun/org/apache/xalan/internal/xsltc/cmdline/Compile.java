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
 * 
 */
/*
 * $Id: Compile.java,v 1.2.4.1 2005/08/31 11:24:13 pvedula Exp $
 * <p>
 *  $ Id：Compile.java,v 1.2.4.1 2005/08/31 11:24:13 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.cmdline;

import com.sun.org.apache.xalan.internal.utils.FeatureManager;
import java.io.File;
import java.net.URL;
import java.util.Vector;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author G. Todd Miller
 * @author Morten Jorgensen
 */
public final class Compile {

    // Versioning numbers  for the compiler -v option output
    private static int VERSION_MAJOR = 1;
    private static int VERSION_MINOR = 4;
    private static int VERSION_DELTA = 0;



    // This variable should be set to false to prevent any methods in this
    // class from calling System.exit(). As this is a command-line tool,
    // calling System.exit() is normally OK, but we also want to allow for
    // this class being used in other ways as well.
    private static boolean _allowExit = true;


    public static void printUsage() {
      System.err.println("XSLTC version " +
              VERSION_MAJOR + "." + VERSION_MINOR +
              ((VERSION_DELTA > 0) ? ("." + VERSION_DELTA) : ("")) + "\n" +
              new ErrorMsg(ErrorMsg.COMPILE_USAGE_STR));
        if (_allowExit) System.exit(-1);
    }

    /**
     * This method implements the command line compiler. See the USAGE_STRING
     * constant for a description. It may make sense to move the command-line
     * handling to a separate package (ie. make one xsltc.cmdline.Compiler
     * class that contains this main() method and one xsltc.cmdline.Transform
     * class that contains the DefaultRun stuff).
     * <p>
     *  此方法实现了命令行编译器。有关说明,请参阅USAGE_STRING常数。
     * 将命令行处理移动到单独的包(即,make一个包含此main()方法的xsltc.cmdline.Compiler类和包含DefaultRun东西的一个xsltc.cmdline.Transform类)是
     * 有意义的。
     */
    public static void main(String[] args) {
        try {
            boolean inputIsURL = false;
            boolean useStdIn = false;
            boolean classNameSet = false;
            final GetOpt getopt = new GetOpt(args, "o:d:j:p:uxhsinv");
            if (args.length < 1) printUsage();

            final XSLTC xsltc = new XSLTC(true, new FeatureManager());
            xsltc.init();

            int c;
            while ((c = getopt.getNextOption()) != -1) {
                switch(c) {
                case 'i':
                    useStdIn = true;
                    break;
                case 'o':
                    xsltc.setClassName(getopt.getOptionArg());
                    classNameSet = true;
                    break;
                case 'd':
                    xsltc.setDestDirectory(getopt.getOptionArg());
                    break;
                case 'p':
                    xsltc.setPackageName(getopt.getOptionArg());
                    break;
                case 'j':
                    xsltc.setJarFileName(getopt.getOptionArg());
                    break;
                case 'x':
                    xsltc.setDebug(true);
                    break;
                case 'u':
                    inputIsURL = true;
                    break;
                case 's':
                    _allowExit = false;
                    break;
                case 'n':
                    xsltc.setTemplateInlining(true);    // used to be 'false'
                    break;
                case 'v':
                    // fall through to case h
                case 'h':
                default:
                    printUsage();
                    break;
                }
            }

            boolean compileOK;

            if (useStdIn) {
                if (!classNameSet) {
                    System.err.println(new ErrorMsg(ErrorMsg.COMPILE_STDIN_ERR));
                    if (_allowExit) System.exit(-1);
                }
                compileOK = xsltc.compile(System.in, xsltc.getClassName());
            }
            else {
                // Generate a vector containg URLs for all stylesheets specified
                final String[] stylesheetNames = getopt.getCmdArgs();
                final Vector   stylesheetVector = new Vector();
                for (int i = 0; i < stylesheetNames.length; i++) {
                    final String name = stylesheetNames[i];
                    URL url;
                    if (inputIsURL)
                        url = new URL(name);
                    else
                        url = (new File(name)).toURI().toURL();
                    stylesheetVector.addElement(url);
                }
                compileOK = xsltc.compile(stylesheetVector);
            }

            // Compile the stylesheet and output class/jar file(s)
            if (compileOK) {
                xsltc.printWarnings();
                if (xsltc.getJarFileName() != null) xsltc.outputToJar();
                if (_allowExit) System.exit(0);
            }
            else {
                xsltc.printWarnings();
                xsltc.printErrors();
                if (_allowExit) System.exit(-1);
            }
        }
        catch (GetOptsException ex) {
            System.err.println(ex);
            printUsage(); // exits with code '-1'
        }
        catch (Exception e) {
            e.printStackTrace();
            if (_allowExit) System.exit(-1);
        }
    }

}
