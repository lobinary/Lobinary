/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import java.util.Hashtable;
import java.util.Enumeration;

import com.sun.org.apache.xerces.internal.xni.Augmentations;

/**
 * This class provides an implementation for Augmentations interface.
 * Augmentations interface defines a hashtable of additional data that could
 * be passed along the document pipeline. The information can contain extra
 * arguments or infoset augmentations, for example PSVI. This additional
 * information is identified by a String key.
 * <p>
 *
 * <p>
 *  这个类提供了一个用于Augmentations接口的实现。扩充接口定义了可以沿着文档管道传递的附加数据的散列表。该信息可以包含额外的参数或信息集扩展,例如PSVI。此附加信息由String键标识。
 * <p>
 * 
 * 
 * @author Elena Litani, IBM
 */
public class AugmentationsImpl implements Augmentations{

    private AugmentationsItemsContainer fAugmentationsContainer =
                                        new SmallContainer();

    /**
     * Add additional information identified by a key to the Augmentations structure.
     *
     * <p>
     *  将通过键标识的其他信息添加到Augmentations结构。
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     * @param item   Additional information
     *
     * @return the previous value of the specified key in the Augmentations strucutre,
     *         or <code>null</code> if it did not have one.
     */
    public Object putItem (String key, Object item){
        Object oldValue = fAugmentationsContainer.putItem(key, item);

        if (oldValue == null && fAugmentationsContainer.isFull()) {
            fAugmentationsContainer = fAugmentationsContainer.expand();
        }

        return oldValue;
    }


    /**
     * Get information identified by a key from the Augmentations structure
     *
     * <p>
     *  从Augmentations结构获取由键标识的信息
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     *
     * @return the value to which the key is mapped in the Augmentations structure;
     *         <code>null</code> if the key is not mapped to any value.
     */
    public Object getItem(String key){
        return fAugmentationsContainer.getItem(key);
    }


    /**
     * Remove additional info from the Augmentations structure
     *
     * <p>
     *  从增强结构中删除其他信息
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     */
    public Object removeItem (String key){
        return fAugmentationsContainer.removeItem(key);
    }

    /**
     * Returns an enumeration of the keys in the Augmentations structure
     *
     * <p>
     *  返回Augmentations结构中键的枚举
     * 
     */
    public Enumeration keys (){
        return fAugmentationsContainer.keys();
    }

    /**
     * Remove all objects from the Augmentations structure.
     * <p>
     *  从Augmentations结构中删除所有对象。
     */
    public void removeAllItems() {
        fAugmentationsContainer.clear();
    }

    public String toString() {
        return fAugmentationsContainer.toString();
    }

    abstract class AugmentationsItemsContainer {
        abstract public Object putItem(Object key, Object item);
        abstract public Object getItem(Object key);
        abstract public Object removeItem(Object key);
        abstract public Enumeration keys();
        abstract public void clear();
        abstract public boolean isFull();
        abstract public AugmentationsItemsContainer expand();
    }

    class SmallContainer extends AugmentationsItemsContainer {
        final static int SIZE_LIMIT = 10;
        final Object[] fAugmentations = new Object[SIZE_LIMIT*2];
        int fNumEntries = 0;

        public Enumeration keys() {
            return new SmallContainerKeyEnumeration();
        }

        public Object getItem(Object key) {
            for (int i = 0; i < fNumEntries*2; i = i + 2) {
                if (fAugmentations[i].equals(key)) {
                    return fAugmentations[i+1];
                }
            }

            return null;
        }

        public Object putItem(Object key, Object item) {
            for (int i = 0; i < fNumEntries*2; i = i + 2) {
                if (fAugmentations[i].equals(key)) {
                    Object oldValue = fAugmentations[i+1];
                    fAugmentations[i+1] = item;

                    return oldValue;
                }
            }

            fAugmentations[fNumEntries*2] = key;
            fAugmentations[fNumEntries*2+1] = item;
            fNumEntries++;

            return null;
        }


        public Object removeItem(Object key) {
            for (int i = 0; i < fNumEntries*2; i = i + 2) {
                if (fAugmentations[i].equals(key)) {
                    Object oldValue = fAugmentations[i+1];

                    for (int j = i; j < fNumEntries*2 - 2; j = j + 2) {
                        fAugmentations[j] = fAugmentations[j+2];
                        fAugmentations[j+1] = fAugmentations[j+3];
                    }

                    fAugmentations[fNumEntries*2-2] = null;
                    fAugmentations[fNumEntries*2-1] = null;
                    fNumEntries--;

                    return oldValue;
                }
            }

            return null;
        }

        public void clear() {
            for (int i = 0; i < fNumEntries*2; i = i + 2) {
                fAugmentations[i] = null;
                fAugmentations[i+1] = null;
            }

            fNumEntries = 0;
        }

        public boolean isFull() {
            return (fNumEntries == SIZE_LIMIT);
        }

        public AugmentationsItemsContainer expand() {
            LargeContainer expandedContainer = new LargeContainer();

            for (int i = 0; i < fNumEntries*2; i = i + 2) {
                expandedContainer.putItem(fAugmentations[i],
                                          fAugmentations[i+1]);
            }

            return expandedContainer;
        }

        public String toString() {
            StringBuffer buff = new StringBuffer();
            buff.append("SmallContainer - fNumEntries == " + fNumEntries);

            for (int i = 0; i < SIZE_LIMIT*2; i=i+2) {
                buff.append("\nfAugmentations[");
                buff.append(i);
                buff.append("] == ");
                buff.append(fAugmentations[i]);
                buff.append("; fAugmentations[");
                buff.append(i+1);
                buff.append("] == ");
                buff.append(fAugmentations[i+1]);
            }

            return buff.toString();
        }

        class SmallContainerKeyEnumeration implements Enumeration {
            Object [] enumArray = new Object[fNumEntries];
            int next = 0;

            SmallContainerKeyEnumeration() {
                for (int i = 0; i < fNumEntries; i++) {
                    enumArray[i] = fAugmentations[i*2];
                }
            }

            public boolean hasMoreElements() {
                return next < enumArray.length;
            }

            public Object nextElement() {
                if (next >= enumArray.length) {
                    throw new java.util.NoSuchElementException();
                }

                Object nextVal = enumArray[next];
                enumArray[next] = null;
                next++;

                return nextVal;
            }
        }
    }

    class LargeContainer extends AugmentationsItemsContainer {
        final Hashtable fAugmentations = new Hashtable();

        public Object getItem(Object key) {
            return fAugmentations.get(key);
        }

        public Object putItem(Object key, Object item) {
            return fAugmentations.put(key, item);
        }

        public Object removeItem(Object key) {
            return fAugmentations.remove(key);
        }

        public Enumeration keys() {
            return fAugmentations.keys();
        }

        public void clear() {
            fAugmentations.clear();
        }

        public boolean isFull() {
            return false;
        }

        public AugmentationsItemsContainer expand() {
            return this;
        }

        public String toString() {
            StringBuffer buff = new StringBuffer();
            buff.append("LargeContainer");
            Enumeration keys = fAugmentations.keys();

            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                buff.append("\nkey == ");
                buff.append(key);
                buff.append("; value == ");
                buff.append(fAugmentations.get(key));
            }

            return buff.toString();
        }
    }
}
