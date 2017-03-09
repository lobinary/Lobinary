/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil;

import org.omg.CORBA.ORB;
import java.io.Serializable;
import java.net.MalformedURLException;

/**
 * Methods on specific instances of RepositoryId.  Hides
 * versioning of our RepositoryId class.
 * <p>
 *  RepositoryId的特定实例的方法。隐藏我们的RepositoryId类的版本。
 */
public interface RepositoryIdInterface
{
    Class getClassFromType() throws ClassNotFoundException;

    Class getClassFromType(String codebaseURL)
        throws ClassNotFoundException, MalformedURLException;

    Class getClassFromType(Class expectedType,
                           String codebaseURL)
        throws ClassNotFoundException, MalformedURLException;

    String getClassName();
}
