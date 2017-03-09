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

package java.util.prefs;

import java.util.Map;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.io.ByteArrayOutputStream;
import sun.util.logging.PlatformLogger;

/**
 * Windows registry based implementation of  <tt>Preferences</tt>.
 * <tt>Preferences</tt>' <tt>systemRoot</tt> and <tt>userRoot</tt> are stored in
 * <tt>HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Prefs</tt> and
 * <tt>HKEY_CURRENT_USER\Software\JavaSoft\Prefs</tt> correspondingly.
 *
 * <p>
 *  基于Windows注册表的<tt>首选项</tt>实现。
 *  <tt>首选项</tt>'<tt> systemRoot </tt>和<tt> userRoot </tt>存储在<tt> HKEY_LOCAL_MACHINE \ SOFTWARE \ JavaSo
 * ft \ Prefs </tt>和<tt> HKEY_CURRENT_USER \ Software \ JavaSoft \ Prefs </tt>。
 *  基于Windows注册表的<tt>首选项</tt>实现。
 * 
 * 
 * @author  Konstantin Kladko
 * @see Preferences
 * @see PreferencesFactory
 * @since 1.4
 */

class WindowsPreferences extends AbstractPreferences{

    /**
     * Logger for error messages
     * <p>
     *  记录错误消息
     * 
     */
    private static PlatformLogger logger;

    /**
     * Windows registry path to <tt>Preferences</tt>'s root nodes.
     * <p>
     *  Windows注册表路径到<tt>首选项</tt>的根节点。
     * 
     */
    private static final byte[] WINDOWS_ROOT_PATH
                               = stringToByteArray("Software\\JavaSoft\\Prefs");

    /**
     * Windows handles to <tt>HKEY_CURRENT_USER</tt> and
     * <tt>HKEY_LOCAL_MACHINE</tt> hives.
     * <p>
     *  Windows处理<tt> HKEY_CURRENT_USER </tt>和<tt> HKEY_LOCAL_MACHINE </tt>蜂巢。
     * 
     */
    private static final int HKEY_CURRENT_USER = 0x80000001;
    private static final int HKEY_LOCAL_MACHINE = 0x80000002;

    /**
     * Mount point for <tt>Preferences</tt>'  user root.
     * <p>
     *  <tt>首选项</tt>的用户根的安装点。
     * 
     */
    private static final int USER_ROOT_NATIVE_HANDLE = HKEY_CURRENT_USER;

    /**
     * Mount point for <tt>Preferences</tt>'  system root.
     * <p>
     *  <tt>首选项</tt>"系统根目录的安装点。
     * 
     */
    private static final int SYSTEM_ROOT_NATIVE_HANDLE = HKEY_LOCAL_MACHINE;

    /**
     * Maximum byte-encoded path length for Windows native functions,
     * ending <tt>null</tt> character not included.
     * <p>
     *  Windows本机函数的最大字节编码路径长度,不包括结束<tt> null </tt>字符。
     * 
     */
    private static final int MAX_WINDOWS_PATH_LENGTH = 256;

    /**
     * User root node.
     * <p>
     *  用户根节点。
     * 
     */
    static final Preferences userRoot =
         new WindowsPreferences(USER_ROOT_NATIVE_HANDLE, WINDOWS_ROOT_PATH);

    /**
     * System root node.
     * <p>
     *  系统根节点。
     * 
     */
    static final Preferences systemRoot =
        new WindowsPreferences(SYSTEM_ROOT_NATIVE_HANDLE, WINDOWS_ROOT_PATH);

    /*  Windows error codes. */
    private static final int ERROR_SUCCESS = 0;
    private static final int ERROR_FILE_NOT_FOUND = 2;
    private static final int ERROR_ACCESS_DENIED = 5;

    /* Constants used to interpret returns of native functions    */
    private static final int NATIVE_HANDLE = 0;
    private static final int ERROR_CODE = 1;
    private static final int SUBKEYS_NUMBER = 0;
    private static final int VALUES_NUMBER = 2;
    private static final int MAX_KEY_LENGTH = 3;
    private static final int MAX_VALUE_NAME_LENGTH = 4;
    private static final int DISPOSITION = 2;
    private static final int REG_CREATED_NEW_KEY = 1;
    private static final int REG_OPENED_EXISTING_KEY = 2;
    private static final int NULL_NATIVE_HANDLE = 0;

    /* Windows security masks */
    private static final int DELETE = 0x10000;
    private static final int KEY_QUERY_VALUE = 1;
    private static final int KEY_SET_VALUE = 2;
    private static final int KEY_CREATE_SUB_KEY = 4;
    private static final int KEY_ENUMERATE_SUB_KEYS = 8;
    private static final int KEY_READ = 0x20019;
    private static final int KEY_WRITE = 0x20006;
    private static final int KEY_ALL_ACCESS = 0xf003f;

    /**
     * Initial time between registry access attempts, in ms. The time is doubled
     * after each failing attempt (except the first).
     * <p>
     *  注册表访问尝试之间的初始时间(以毫秒为单位)。每次失败尝试后,时间加倍(除了第一次)。
     * 
     */
    private static int INIT_SLEEP_TIME = 50;

    /**
     * Maximum number of registry access attempts.
     * <p>
     *  最大注册表访问尝试次数。
     * 
     */
    private static int MAX_ATTEMPTS = 5;

    /**
     * BackingStore availability flag.
     * <p>
     *  BackingStore可用性标志。
     * 
     */
    private boolean isBackingStoreAvailable = true;

    /**
     * Java wrapper for Windows registry API RegOpenKey()
     * <p>
     *  Windows注册表API的Java包装器RegOpenKey()
     * 
     */
    private static native int[] WindowsRegOpenKey(int hKey, byte[] subKey,
                                                         int securityMask);
    /**
     * Retries RegOpenKey() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegOpenKey()MAX_ATTEMPTS次。
     * 
     */
    private static int[] WindowsRegOpenKey1(int hKey, byte[] subKey,
                                                      int securityMask) {
        int[] result = WindowsRegOpenKey(hKey, subKey, securityMask);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
            return result;
        } else if (result[ERROR_CODE] == ERROR_FILE_NOT_FOUND) {
            logger().warning("Trying to recreate Windows registry node " +
            byteArrayToString(subKey) + " at root 0x" +
            Integer.toHexString(hKey) + ".");
            // Try recreation
            int handle = WindowsRegCreateKeyEx(hKey, subKey)[NATIVE_HANDLE];
            WindowsRegCloseKey(handle);
            return WindowsRegOpenKey(hKey, subKey, securityMask);
        } else if (result[ERROR_CODE] != ERROR_ACCESS_DENIED) {
            long sleepTime = INIT_SLEEP_TIME;
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                return result;
            }
            sleepTime *= 2;
            result = WindowsRegOpenKey(hKey, subKey, securityMask);
            if (result[ERROR_CODE] == ERROR_SUCCESS) {
                return result;
            }
            }
        }
        return result;
    }

     /**
     * Java wrapper for Windows registry API RegCloseKey()
     * <p>
     *  Windows注册表API的Java包装RegCloseKey()
     * 
     */
    private static native int WindowsRegCloseKey(int hKey);

    /**
     * Java wrapper for Windows registry API RegCreateKeyEx()
     * <p>
     *  Windows注册表API的Java包装器RegCreateKeyEx()
     * 
     */
    private static native int[] WindowsRegCreateKeyEx(int hKey, byte[] subKey);

    /**
     * Retries RegCreateKeyEx() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegCreateKeyEx()MAX_ATTEMPTS次。
     * 
     */
    private static int[] WindowsRegCreateKeyEx1(int hKey, byte[] subKey) {
        int[] result = WindowsRegCreateKeyEx(hKey, subKey);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegCreateKeyEx(hKey, subKey);
                if (result[ERROR_CODE] == ERROR_SUCCESS) {
                return result;
                }
            }
        }
        return result;
    }
    /**
     * Java wrapper for Windows registry API RegDeleteKey()
     * <p>
     *  Windows注册表API的Java包装器RegDeleteKey()
     * 
     */
    private static native int WindowsRegDeleteKey(int hKey, byte[] subKey);

    /**
     * Java wrapper for Windows registry API RegFlushKey()
     * <p>
     *  Windows注册表API的Java包装器RegFlushKey()
     * 
     */
    private static native int WindowsRegFlushKey(int hKey);

    /**
     * Retries RegFlushKey() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegFlushKey()MAX_ATTEMPTS次。
     * 
     */
    private static int WindowsRegFlushKey1(int hKey) {
        int result = WindowsRegFlushKey(hKey);
        if (result == ERROR_SUCCESS) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegFlushKey(hKey);
                if (result == ERROR_SUCCESS) {
                return result;
                }
            }
        }
        return result;
    }

    /**
     * Java wrapper for Windows registry API RegQueryValueEx()
     * <p>
     * Windows注册表API的Java包装器RegQueryValueEx()
     * 
     */
    private static native byte[] WindowsRegQueryValueEx(int hKey,
                                                              byte[] valueName);
    /**
     * Java wrapper for Windows registry API RegSetValueEx()
     * <p>
     *  Windows注册表API的Wrapper RegSetValueEx()
     * 
     */
    private static native int WindowsRegSetValueEx(int hKey, byte[] valueName,
                                                         byte[] value);
    /**
     * Retries RegSetValueEx() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegSetValueEx()MAX_ATTEMPTS次。
     * 
     */
    private static int WindowsRegSetValueEx1(int hKey, byte[] valueName,
                                                         byte[] value) {
        int result = WindowsRegSetValueEx(hKey, valueName, value);
        if (result == ERROR_SUCCESS) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegSetValueEx(hKey, valueName, value);
                if (result == ERROR_SUCCESS) {
                return result;
                }
            }
        }
        return result;
    }

    /**
     * Java wrapper for Windows registry API RegDeleteValue()
     * <p>
     *  Windows注册表API的Java包装器RegDeleteValue()
     * 
     */
    private static native int WindowsRegDeleteValue(int hKey, byte[] valueName);

    /**
     * Java wrapper for Windows registry API RegQueryInfoKey()
     * <p>
     *  Windows注册表API的Java包装器RegQueryInfoKey()
     * 
     */
    private static native int[] WindowsRegQueryInfoKey(int hKey);

    /**
     * Retries RegQueryInfoKey() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegQueryInfoKey()MAX_ATTEMPTS次。
     * 
     */
    private static int[] WindowsRegQueryInfoKey1(int hKey) {
        int[] result = WindowsRegQueryInfoKey(hKey);
        if (result[ERROR_CODE] == ERROR_SUCCESS) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegQueryInfoKey(hKey);
                if (result[ERROR_CODE] == ERROR_SUCCESS) {
                return result;
                }
            }
        }
        return result;
    }

    /**
     * Java wrapper for Windows registry API RegEnumKeyEx()
     * <p>
     *  Windows注册表API的Java包装器RegEnumKeyEx()
     * 
     */
    private static native byte[] WindowsRegEnumKeyEx(int hKey, int subKeyIndex,
                                      int maxKeyLength);

    /**
     * Retries RegEnumKeyEx() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegEnumKeyEx()MAX_ATTEMPTS次。
     * 
     */
    private static byte[] WindowsRegEnumKeyEx1(int hKey, int subKeyIndex,
                                      int maxKeyLength) {
        byte[] result = WindowsRegEnumKeyEx(hKey, subKeyIndex, maxKeyLength);
        if (result != null) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegEnumKeyEx(hKey, subKeyIndex, maxKeyLength);
                if (result != null) {
                return result;
                }
            }
        }
        return result;
    }

    /**
     * Java wrapper for Windows registry API RegEnumValue()
     * <p>
     *  Windows注册表API的Java包装器RegEnumValue()
     * 
     */
    private static native byte[] WindowsRegEnumValue(int hKey, int valueIndex,
                                      int maxValueNameLength);
    /**
     * Retries RegEnumValueEx() MAX_ATTEMPTS times before giving up.
     * <p>
     *  在放弃之前重试RegEnumValueEx()MAX_ATTEMPTS次。
     * 
     */
    private static byte[] WindowsRegEnumValue1(int hKey, int valueIndex,
                                      int maxValueNameLength) {
        byte[] result = WindowsRegEnumValue(hKey, valueIndex,
                                                            maxValueNameLength);
        if (result != null) {
                return result;
            } else {
                long sleepTime = INIT_SLEEP_TIME;
                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    return result;
                }
                sleepTime *= 2;
                result = WindowsRegEnumValue(hKey, valueIndex,
                                                            maxValueNameLength);
                if (result != null) {
                return result;
                }
            }
        }
        return result;
    }

    /**
     * Constructs a <tt>WindowsPreferences</tt> node, creating underlying
     * Windows registry node and all its Windows parents, if they are not yet
     * created.
     * Logs a warning message, if Windows Registry is unavailable.
     * <p>
     *  构造一个<tt> WindowsPreferences </tt>节点,创建基础Windows注册表节点及其所有Windows父项(如果尚未创建)。如果Windows注册表不可用,记录一条警告消息。
     * 
     */
    private WindowsPreferences(WindowsPreferences parent, String name) {
        super(parent, name);
        int parentNativeHandle = parent.openKey(KEY_CREATE_SUB_KEY, KEY_READ);
        if (parentNativeHandle == NULL_NATIVE_HANDLE) {
            // if here, openKey failed and logged
            isBackingStoreAvailable = false;
            return;
        }
        int[] result =
               WindowsRegCreateKeyEx1(parentNativeHandle, toWindowsName(name));
        if (result[ERROR_CODE] != ERROR_SUCCESS) {
            logger().warning("Could not create windows registry "
            + "node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegCreateKeyEx(...) returned error code " +
            result[ERROR_CODE] + ".");
            isBackingStoreAvailable = false;
            return;
        }
        newNode = (result[DISPOSITION] == REG_CREATED_NEW_KEY);
        closeKey(parentNativeHandle);
        closeKey(result[NATIVE_HANDLE]);
    }

    /**
     * Constructs a root node creating the underlying
     * Windows registry node and all of its parents, if they have not yet been
     * created.
     * Logs a warning message, if Windows Registry is unavailable.
     * <p>
     *  构造根节点,创建基础Windows注册表节点及其所有父项(如果尚未创建)。如果Windows注册表不可用,记录一条警告消息。
     * 
     * 
     * @param rootNativeHandle Native handle to one of Windows top level keys.
     * @param rootDirectory Path to root directory, as a byte-encoded string.
     */
    private  WindowsPreferences(int rootNativeHandle, byte[] rootDirectory) {
        super(null,"");
        int[] result =
                WindowsRegCreateKeyEx1(rootNativeHandle, rootDirectory);
        if (result[ERROR_CODE] != ERROR_SUCCESS) {
            logger().warning("Could not open/create prefs root node " +
            byteArrayToString(windowsAbsolutePath()) + " at root 0x" +
            Integer.toHexString(rootNativeHandle()) +
            ". Windows RegCreateKeyEx(...) returned error code " +
            result[ERROR_CODE] + ".");
            isBackingStoreAvailable = false;
            return;
        }
        // Check if a new node
        newNode = (result[DISPOSITION] == REG_CREATED_NEW_KEY);
        closeKey(result[NATIVE_HANDLE]);
    }

    /**
     * Returns Windows absolute path of the current node as a byte array.
     * Java "/" separator is transformed into Windows "\".
     * <p>
     *  以字节数组形式返回当前节点的Windows绝对路径。 Java"/"分隔符转换为Windows"\"。
     * 
     * 
     * @see Preferences#absolutePath()
     */
    private byte[] windowsAbsolutePath() {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        bstream.write(WINDOWS_ROOT_PATH, 0, WINDOWS_ROOT_PATH.length-1);
        StringTokenizer tokenizer = new StringTokenizer(absolutePath(),"/");
        while (tokenizer.hasMoreTokens()) {
            bstream.write((byte)'\\');
            String nextName = tokenizer.nextToken();
            byte[] windowsNextName = toWindowsName(nextName);
            bstream.write(windowsNextName, 0, windowsNextName.length-1);
        }
        bstream.write(0);
        return bstream.toByteArray();
    }

    /**
     * Opens current node's underlying Windows registry key using a
     * given security mask.
     * <p>
     *  使用给定的安全掩码打开当前节点的基础Windows注册表项。
     * 
     * 
     * @param securityMask Windows security mask.
     * @return Windows registry key's handle.
     * @see #openKey(byte[], int)
     * @see #openKey(int, byte[], int)
     * @see #closeKey(int)
     */
    private int openKey(int securityMask) {
        return openKey(securityMask, securityMask);
    }

    /**
     * Opens current node's underlying Windows registry key using a
     * given security mask.
     * <p>
     *  使用给定的安全掩码打开当前节点的基础Windows注册表项。
     * 
     * 
     * @param mask1 Preferred Windows security mask.
     * @param mask2 Alternate Windows security mask.
     * @return Windows registry key's handle.
     * @see #openKey(byte[], int)
     * @see #openKey(int, byte[], int)
     * @see #closeKey(int)
     */
    private int openKey(int mask1, int mask2) {
        return openKey(windowsAbsolutePath(), mask1,  mask2);
    }

     /**
     * Opens Windows registry key at a given absolute path using a given
     * security mask.
     * <p>
     *  使用给定的安全掩码在给定的绝对路径打开Windows注册表项。
     * 
     * 
     * @param windowsAbsolutePath Windows absolute path of the
     *        key as a byte-encoded string.
     * @param mask1 Preferred Windows security mask.
     * @param mask2 Alternate Windows security mask.
     * @return Windows registry key's handle.
     * @see #openKey(int)
     * @see #openKey(int, byte[],int)
     * @see #closeKey(int)
     */
    private int openKey(byte[] windowsAbsolutePath, int mask1, int mask2) {
        /*  Check if key's path is short enough be opened at once
        /* <p>
        /* 
            otherwise use a path-splitting procedure */
        if (windowsAbsolutePath.length <= MAX_WINDOWS_PATH_LENGTH + 1) {
             int[] result = WindowsRegOpenKey1(rootNativeHandle(),
                                               windowsAbsolutePath, mask1);
             if (result[ERROR_CODE] == ERROR_ACCESS_DENIED && mask2 != mask1)
                 result = WindowsRegOpenKey1(rootNativeHandle(),
                                             windowsAbsolutePath, mask2);

             if (result[ERROR_CODE] != ERROR_SUCCESS) {
                logger().warning("Could not open windows "
                + "registry node " + byteArrayToString(windowsAbsolutePath()) +
                " at root 0x" + Integer.toHexString(rootNativeHandle()) +
                ". Windows RegOpenKey(...) returned error code " +
                result[ERROR_CODE] + ".");
                result[NATIVE_HANDLE] = NULL_NATIVE_HANDLE;
                if (result[ERROR_CODE] == ERROR_ACCESS_DENIED) {
                    throw new SecurityException("Could not open windows "
                + "registry node " + byteArrayToString(windowsAbsolutePath()) +
                " at root 0x" + Integer.toHexString(rootNativeHandle()) +
                ": Access denied");
                }
             }
             return result[NATIVE_HANDLE];
        } else {
            return openKey(rootNativeHandle(), windowsAbsolutePath, mask1, mask2);
        }
    }

     /**
     * Opens Windows registry key at a given relative path
     * with respect to a given Windows registry key.
     * <p>
     *  在给定的Windows注册表项的给定相对路径上打开Windows注册表项。
     * 
     * 
     * @param windowsAbsolutePath Windows relative path of the
     *        key as a byte-encoded string.
     * @param nativeHandle handle to the base Windows key.
     * @param mask1 Preferred Windows security mask.
     * @param mask2 Alternate Windows security mask.
     * @return Windows registry key's handle.
     * @see #openKey(int)
     * @see #openKey(byte[],int)
     * @see #closeKey(int)
     */
    private int openKey(int nativeHandle, byte[] windowsRelativePath,
                        int mask1, int mask2) {
    /* If the path is short enough open at once. Otherwise split the path */
        if (windowsRelativePath.length <= MAX_WINDOWS_PATH_LENGTH + 1 ) {
             int[] result = WindowsRegOpenKey1(nativeHandle,
                                               windowsRelativePath, mask1);
             if (result[ERROR_CODE] == ERROR_ACCESS_DENIED && mask2 != mask1)
                 result = WindowsRegOpenKey1(nativeHandle,
                                             windowsRelativePath, mask2);

             if (result[ERROR_CODE] != ERROR_SUCCESS) {
                logger().warning("Could not open windows "
                + "registry node " + byteArrayToString(windowsAbsolutePath()) +
                " at root 0x" + Integer.toHexString(nativeHandle) +
                ". Windows RegOpenKey(...) returned error code " +
                result[ERROR_CODE] + ".");
                result[NATIVE_HANDLE] = NULL_NATIVE_HANDLE;
             }
             return result[NATIVE_HANDLE];
        } else {
            int separatorPosition = -1;
            // Be greedy - open the longest possible path
            for (int i = MAX_WINDOWS_PATH_LENGTH; i > 0; i--) {
                if (windowsRelativePath[i] == ((byte)'\\')) {
                    separatorPosition = i;
                    break;
                }
            }
            // Split the path and do the recursion
            byte[] nextRelativeRoot = new byte[separatorPosition+1];
            System.arraycopy(windowsRelativePath, 0, nextRelativeRoot,0,
                                                      separatorPosition);
            nextRelativeRoot[separatorPosition] = 0;
            byte[] nextRelativePath = new byte[windowsRelativePath.length -
                                      separatorPosition - 1];
            System.arraycopy(windowsRelativePath, separatorPosition+1,
                             nextRelativePath, 0, nextRelativePath.length);
            int nextNativeHandle = openKey(nativeHandle, nextRelativeRoot,
                                           mask1, mask2);
            if (nextNativeHandle == NULL_NATIVE_HANDLE) {
                return NULL_NATIVE_HANDLE;
            }
            int result = openKey(nextNativeHandle, nextRelativePath,
                                 mask1,mask2);
            closeKey(nextNativeHandle);
            return result;
        }
    }

     /**
     * Closes Windows registry key.
     * Logs a warning if Windows registry is unavailable.
     * <p>
     * 关闭Windows注册表项。如果Windows注册表不可用,请记录警告。
     * 
     * 
     * @param key's Windows registry handle.
     * @see #openKey(int)
     * @see #openKey(byte[],int)
     * @see #openKey(int, byte[],int)
    */
    private void closeKey(int nativeHandle) {
        int result = WindowsRegCloseKey(nativeHandle);
        if (result != ERROR_SUCCESS) {
            logger().warning("Could not close windows "
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegCloseKey(...) returned error code " + result + ".");
        }
    }

     /**
     * Implements <tt>AbstractPreferences</tt> <tt>putSpi()</tt> method.
     * Puts name-value pair into the underlying Windows registry node.
     * Logs a warning, if Windows registry is unavailable.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> putSpi()</tt>方法。将名称值对放入基础Windows注册表节点。如果Windows注册表不可用,请记录警告。
     * 
     * 
     * @see #getSpi(String)
     */
    protected void putSpi(String javaName, String value) {
    int nativeHandle = openKey(KEY_SET_VALUE);
    if (nativeHandle == NULL_NATIVE_HANDLE) {
        isBackingStoreAvailable = false;
        return;
    }
    int result =  WindowsRegSetValueEx1(nativeHandle,
                          toWindowsName(javaName), toWindowsValueString(value));
    if (result != ERROR_SUCCESS) {
        logger().warning("Could not assign value to key " +
        byteArrayToString(toWindowsName(javaName))+ " at Windows registry node "
       + byteArrayToString(windowsAbsolutePath()) + " at root 0x"
       + Integer.toHexString(rootNativeHandle()) +
       ". Windows RegSetValueEx(...) returned error code " + result + ".");
        isBackingStoreAvailable = false;
        }
    closeKey(nativeHandle);
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>getSpi()</tt> method.
     * Gets a string value from the underlying Windows registry node.
     * Logs a warning, if Windows registry is unavailable.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> getSpi()</tt>方法。从底层Windows注册表节点获取字符串值。如果Windows注册表不可用,请记录警告。
     * 
     * 
     * @see #putSpi(String, String)
     */
    protected String getSpi(String javaName) {
    int nativeHandle = openKey(KEY_QUERY_VALUE);
    if (nativeHandle == NULL_NATIVE_HANDLE) {
        return null;
    }
    Object resultObject =  WindowsRegQueryValueEx(nativeHandle,
                                                  toWindowsName(javaName));
    if (resultObject == null) {
        closeKey(nativeHandle);
        return null;
    }
    closeKey(nativeHandle);
    return toJavaValueString((byte[]) resultObject);
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>removeSpi()</tt> method.
     * Deletes a string name-value pair from the underlying Windows registry
     * node, if this value still exists.
     * Logs a warning, if Windows registry is unavailable or key has already
     * been deleted.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> removeSpi()</tt>方法。从基础Windows注册表节点删除字符串名称/值对,如果此值仍然存在。
     * 记录警告,如果Windows注册表不可用或密钥已被删除。
     * 
     */
    protected void removeSpi(String key) {
        int nativeHandle = openKey(KEY_SET_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
        return;
        }
        int result =
            WindowsRegDeleteValue(nativeHandle, toWindowsName(key));
        if (result != ERROR_SUCCESS && result != ERROR_FILE_NOT_FOUND) {
            logger().warning("Could not delete windows registry "
            + "value " + byteArrayToString(windowsAbsolutePath())+ "\\" +
            toWindowsName(key) + " at root 0x" +
            Integer.toHexString(rootNativeHandle()) +
            ". Windows RegDeleteValue(...) returned error code " +
            result + ".");
            isBackingStoreAvailable = false;
        }
        closeKey(nativeHandle);
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>keysSpi()</tt> method.
     * Gets value names from the underlying Windows registry node.
     * Throws a BackingStoreException and logs a warning, if
     * Windows registry is unavailable.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> keysSpi()</tt>方法。从底层Windows注册表节点获取值名称。
     * 如果Windows注册表不可用,则抛出BackingStoreException并记录警告。
     * 
     */
    protected String[] keysSpi() throws BackingStoreException{
        // Find out the number of values
        int nativeHandle = openKey(KEY_QUERY_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            throw new BackingStoreException("Could not open windows"
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) + ".");
        }
        int[] result =  WindowsRegQueryInfoKey1(nativeHandle);
        if (result[ERROR_CODE] != ERROR_SUCCESS) {
            String info = "Could not query windows"
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegQueryInfoKeyEx(...) returned error code " +
            result[ERROR_CODE] + ".";
            logger().warning(info);
            throw new BackingStoreException(info);
        }
        int maxValueNameLength = result[MAX_VALUE_NAME_LENGTH];
        int valuesNumber = result[VALUES_NUMBER];
        if (valuesNumber == 0) {
            closeKey(nativeHandle);
            return new String[0];
       }
       // Get the values
       String[] valueNames = new String[valuesNumber];
       for (int i = 0; i < valuesNumber; i++) {
            byte[] windowsName = WindowsRegEnumValue1(nativeHandle, i,
                                                        maxValueNameLength+1);
            if (windowsName == null) {
                String info =
                "Could not enumerate value #" + i + "  of windows node " +
                byteArrayToString(windowsAbsolutePath()) + " at root 0x" +
                Integer.toHexString(rootNativeHandle()) + ".";
                logger().warning(info);
                throw new BackingStoreException(info);
            }
            valueNames[i] = toJavaName(windowsName);
        }
        closeKey(nativeHandle);
        return valueNames;
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>childrenNamesSpi()</tt> method.
     * Calls Windows registry to retrive children of this node.
     * Throws a BackingStoreException and logs a warning message,
     * if Windows registry is not available.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> childrenNamesSpi()</tt>方法。调用Windows注册表以检索此节点的子代。
     * 如果Windows注册表不可用,则抛出BackingStoreException并记录一条警告消息。
     * 
     */
    protected String[] childrenNamesSpi() throws BackingStoreException {
        // Open key
        int nativeHandle = openKey(KEY_ENUMERATE_SUB_KEYS| KEY_QUERY_VALUE);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            throw new BackingStoreException("Could not open windows"
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) + ".");
        }
        // Get number of children
        int[] result =  WindowsRegQueryInfoKey1(nativeHandle);
        if (result[ERROR_CODE] != ERROR_SUCCESS) {
            String info = "Could not query windows"
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegQueryInfoKeyEx(...) returned error code " +
            result[ERROR_CODE] + ".";
            logger().warning(info);
            throw new BackingStoreException(info);
        }
        int maxKeyLength = result[MAX_KEY_LENGTH];
        int subKeysNumber = result[SUBKEYS_NUMBER];
        if (subKeysNumber == 0) {
            closeKey(nativeHandle);
            return new String[0];
        }
        String[] subkeys = new String[subKeysNumber];
        String[] children = new String[subKeysNumber];
        // Get children
        for (int i = 0; i < subKeysNumber; i++) {
            byte[] windowsName = WindowsRegEnumKeyEx1(nativeHandle, i,
                                                                maxKeyLength+1);
            if (windowsName == null) {
                String info =
                "Could not enumerate key #" + i + "  of windows node " +
                byteArrayToString(windowsAbsolutePath()) + " at root 0x" +
                Integer.toHexString(rootNativeHandle()) + ". ";
                logger().warning(info);
                throw new BackingStoreException(info);
            }
            String javaName = toJavaName(windowsName);
            children[i] = javaName;
        }
        closeKey(nativeHandle);
        return children;
    }

    /**
     * Implements <tt>Preferences</tt> <tt>flush()</tt> method.
     * Flushes Windows registry changes to disk.
     * Throws a BackingStoreException and logs a warning message if Windows
     * registry is not available.
     * <p>
     *  实现<tt>首选项</tt> <tt> flush()</tt>方法。将Windows注册表更改刷新到磁盘。
     * 如果Windows注册表不可用,则抛出BackingStoreException并记录一条警告消息。
     * 
     */
    public void flush() throws BackingStoreException{

        if (isRemoved()) {
            parent.flush();
            return;
        }
        if (!isBackingStoreAvailable) {
            throw new BackingStoreException(
                                       "flush(): Backing store not available.");
        }
        int nativeHandle = openKey(KEY_READ);
        if (nativeHandle == NULL_NATIVE_HANDLE) {
            throw new BackingStoreException("Could not open windows"
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) + ".");
        }
        int result = WindowsRegFlushKey1(nativeHandle);
        if (result != ERROR_SUCCESS) {
            String info = "Could not flush windows "
            + "registry node " + byteArrayToString(windowsAbsolutePath())
            + " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegFlushKey(...) returned error code " + result + ".";
            logger().warning(info);
            throw new BackingStoreException(info);
        }
        closeKey(nativeHandle);
    }


    /**
     * Implements <tt>Preferences</tt> <tt>sync()</tt> method.
     * Flushes Windows registry changes to disk. Equivalent to flush().
     * <p>
     *  实现<tt>首选项</tt> <tt> sync()</tt>方法。将Windows注册表更改刷新到磁盘。相当于flush()。
     * 
     * 
     * @see flush()
     */
    public void sync() throws BackingStoreException{
        if (isRemoved())
            throw new IllegalStateException("Node has been removed");
        flush();
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>childSpi()</tt> method.
     * Constructs a child node with a
     * given name and creates its underlying Windows registry node,
     * if it does not exist.
     * Logs a warning message, if Windows Registry is unavailable.
     * <p>
     * 实现<tt> AbstractPreferences </tt> <tt> childSpi()</tt>方法。构造具有给定名称的子节点,并创建其基础Windows注册表节点(如果它不存在)。
     * 如果Windows注册表不可用,记录一条警告消息。
     * 
     */
    protected AbstractPreferences childSpi(String name) {
            return new WindowsPreferences(this, name);
    }

    /**
     * Implements <tt>AbstractPreferences</tt> <tt>removeNodeSpi()</tt> method.
     * Deletes underlying Windows registry node.
     * Throws a BackingStoreException and logs a warning, if Windows registry
     * is not available.
     * <p>
     *  实现<tt> AbstractPreferences </tt> <tt> removeNodeSpi()</tt>方法。删除基础Windows注册表节点。
     * 如果Windows注册表不可用,则抛出BackingStoreException并记录警告。
     * 
     */
    public void removeNodeSpi() throws BackingStoreException {
        int parentNativeHandle =
                         ((WindowsPreferences)parent()).openKey(DELETE);
        if (parentNativeHandle == NULL_NATIVE_HANDLE) {
            throw new BackingStoreException("Could not open parent windows"
            + "registry node of " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) + ".");
        }
        int result =
                WindowsRegDeleteKey(parentNativeHandle, toWindowsName(name()));
        if (result != ERROR_SUCCESS) {
            String info = "Could not delete windows "
            + "registry node " + byteArrayToString(windowsAbsolutePath()) +
            " at root 0x" + Integer.toHexString(rootNativeHandle()) +
            ". Windows RegDeleteKeyEx(...) returned error code " +
            result + ".";
            logger().warning(info);
            throw new BackingStoreException(info);
        }
        closeKey(parentNativeHandle);
    }

    /**
     * Converts value's or node's name from its byte array representation to
     * java string. Two encodings, simple and altBase64 are used. See
     * {@link #toWindowsName(String) toWindowsName()} for a detailed
     * description of encoding conventions.
     * <p>
     *  将值或节点的名称从其字节数组表示形式转换为java字符串。使用两种编码,简单和altBase64。
     * 有关编码约定的详细说明,请参阅{@link #toWindowsName(String)toWindowsName()}。
     * 
     * 
     * @param windowsNameArray Null-terminated byte array.
     */
    private static String toJavaName(byte[] windowsNameArray) {
        String windowsName = byteArrayToString(windowsNameArray);
        // check if Alt64
        if ((windowsName.length()>1) &&
                                   (windowsName.substring(0,2).equals("/!"))) {
            return toJavaAlt64Name(windowsName);
        }
        StringBuffer javaName = new StringBuffer();
        char ch;
        // Decode from simple encoding
        for (int i = 0; i < windowsName.length(); i++){
            if ((ch = windowsName.charAt(i)) == '/') {
                char next = ' ';
                if ((windowsName.length() > i + 1) &&
                   ((next = windowsName.charAt(i+1)) >= 'A') && (next <= 'Z')) {
                ch = next;
                i++;
                } else  if ((windowsName.length() > i + 1) && (next == '/')) {
                ch = '\\';
                i++;
                }
            } else if (ch == '\\') {
                ch = '/';
            }
            javaName.append(ch);
        }
        return javaName.toString();
    }

    /**
     * Converts value's or node's name from its Windows representation to java
     * string, using altBase64 encoding. See
     * {@link #toWindowsName(String) toWindowsName()} for a detailed
     * description of encoding conventions.
     * <p>
     *  使用altBase64编码将值或节点的名称从其Windows表示形式转换为java字符串。
     * 有关编码约定的详细说明,请参阅{@link #toWindowsName(String)toWindowsName()}。
     * 
     */

    private static String toJavaAlt64Name(String windowsName) {
        byte[] byteBuffer =
                          Base64.altBase64ToByteArray(windowsName.substring(2));
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < byteBuffer.length; i++) {
            int firstbyte = (byteBuffer[i++] & 0xff);
            int secondbyte =  (byteBuffer[i] & 0xff);
            result.append((char)((firstbyte << 8) + secondbyte));
        }
        return result.toString();
    }

    /**
     * Converts value's or node's name to its Windows representation
     * as a byte-encoded string.
     * Two encodings, simple and altBase64 are used.
     * <p>
     * <i>Simple</i> encoding is used, if java string does not contain
     * any characters less, than 0x0020, or greater, than 0x007f.
     * Simple encoding adds "/" character to capital letters, i.e.
     * "A" is encoded as "/A". Character '\' is encoded as '//',
     * '/' is encoded as '\'.
     * The constructed string is converted to byte array by truncating the
     * highest byte and adding the terminating <tt>null</tt> character.
     * <p>
     * <i>altBase64</i>  encoding is used, if java string does contain at least
     * one character less, than 0x0020, or greater, than 0x007f.
     * This encoding is marked by setting first two bytes of the
     * Windows string to '/!'. The java name is then encoded using
     * byteArrayToAltBase64() method from
     * Base64 class.
     * <p>
     *  将值或节点的名称转换为其Windows表示形式作为字节编码的字符串。使用两种编码,简单和altBase64。
     * <p>
     *  <i>使用简单</i>编码,如果java字符串不包含任何字符小于0x0020或更大,而不是0x007f。简单编码将"/"字符添加到大写字母,即"A"被编码为"/ A"。
     * 字符'\'编码为'//','/'编码为'\'。通过截断最高字节并添加终止<tt> null </tt>字符,将构造的字符串转换为字节数组。
     * <p>
     * 使用<i> altBase64 </i>编码,如果java字符串至少包含一个字符,小于0x0020,或大于0x007f。此编码通过将Windows字符串的前两个字节设置为"/！"来标记。
     * 然后使用来自Base64类的byteArrayToAltBase64()方法对java名称进行编码。
     * 
     */
    private static byte[] toWindowsName(String javaName) {
        StringBuffer windowsName = new StringBuffer();
        for (int i = 0; i < javaName.length(); i++) {
            char ch =javaName.charAt(i);
            if ((ch < 0x0020)||(ch > 0x007f)) {
                // If a non-trivial character encountered, use altBase64
                return toWindowsAlt64Name(javaName);
            }
            if (ch == '\\') {
                windowsName.append("//");
            } else if (ch == '/') {
                windowsName.append('\\');
            } else if ((ch >= 'A') && (ch <='Z')) {
                windowsName.append("/" + ch);
            } else {
                windowsName.append(ch);
            }
        }
        return stringToByteArray(windowsName.toString());
    }

    /**
     * Converts value's or node's name to its Windows representation
     * as a byte-encoded string, using altBase64 encoding. See
     * {@link #toWindowsName(String) toWindowsName()} for a detailed
     * description of encoding conventions.
     * <p>
     *  使用altBase64编码将值或节点的名称转换为其Windows表示形式作为字节编码的字符串。
     * 有关编码约定的详细说明,请参阅{@link #toWindowsName(String)toWindowsName()}。
     * 
     */
    private static byte[] toWindowsAlt64Name(String javaName) {
        byte[] javaNameArray = new byte[2*javaName.length()];
        // Convert to byte pairs
        int counter = 0;
        for (int i = 0; i < javaName.length();i++) {
                int ch = javaName.charAt(i);
                javaNameArray[counter++] = (byte)(ch >>> 8);
                javaNameArray[counter++] = (byte)ch;
        }

        return stringToByteArray(
                           "/!" + Base64.byteArrayToAltBase64(javaNameArray));
    }

    /**
     * Converts value string from its Windows representation
     * to java string.  See
     * {@link #toWindowsValueString(String) toWindowsValueString()} for the
     * description of the encoding algorithm.
     * <p>
     *  将值字符串从其Windows表示形式转换为java字符串。
     * 有关编码算法的说明,请参阅{@link #toWindowsValueString(String)toWindowsValueString()}。
     * 
     */
     private static String toJavaValueString(byte[] windowsNameArray) {
        // Use modified native2ascii algorithm
        String windowsName = byteArrayToString(windowsNameArray);
        StringBuffer javaName = new StringBuffer();
        char ch;
        for (int i = 0; i < windowsName.length(); i++){
            if ((ch = windowsName.charAt(i)) == '/') {
                char next = ' ';

                if (windowsName.length() > i + 1 &&
                                    (next = windowsName.charAt(i + 1)) == 'u') {
                    if (windowsName.length() < i + 6){
                        break;
                    } else {
                        ch = (char)Integer.parseInt
                                      (windowsName.substring(i + 2, i + 6), 16);
                        i += 5;
                    }
                } else
                if ((windowsName.length() > i + 1) &&
                          ((windowsName.charAt(i+1)) >= 'A') && (next <= 'Z')) {
                ch = next;
                i++;
                } else  if ((windowsName.length() > i + 1) &&
                                               (next == '/')) {
                ch = '\\';
                i++;
                }
            } else if (ch == '\\') {
                ch = '/';
            }
            javaName.append(ch);
        }
        return javaName.toString();
    }

    /**
     * Converts value string to it Windows representation.
     * as a byte-encoded string.
     * Encoding algorithm adds "/" character to capital letters, i.e.
     * "A" is encoded as "/A". Character '\' is encoded as '//',
     * '/' is encoded as  '\'.
     * Then encoding scheme similar to jdk's native2ascii converter is used
     * to convert java string to a byte array of ASCII characters.
     * <p>
     *  将值字符串转换为Windows表示形式。作为字节编码的字符串。编码算法将"/"字符添加到大写字母,即"A"被编码为"/ A"。字符'\'编码为'//','/'编码为'\'。
     * 然后编码方案类似于jdk的native2ascii转换器用于将java字符串转换为ASCII字符的字节数组。
     * 
     */
    private static byte[] toWindowsValueString(String javaName) {
        StringBuffer windowsName = new StringBuffer();
        for (int i = 0; i < javaName.length(); i++) {
            char ch =javaName.charAt(i);
            if ((ch < 0x0020)||(ch > 0x007f)){
                // write \udddd
                windowsName.append("/u");
                String hex = Integer.toHexString(javaName.charAt(i));
                StringBuffer hex4 = new StringBuffer(hex);
                hex4.reverse();
                int len = 4 - hex4.length();
                for (int j = 0; j < len; j++){
                    hex4.append('0');
                }
                for (int j = 0; j < 4; j++){
                    windowsName.append(hex4.charAt(3 - j));
                }
            } else if (ch == '\\') {
                windowsName.append("//");
            } else if (ch == '/') {
                windowsName.append('\\');
            } else if ((ch >= 'A') && (ch <='Z')) {
                windowsName.append("/" + ch);
            } else {
                windowsName.append(ch);
            }
        }
        return stringToByteArray(windowsName.toString());
    }

    /**
     * Returns native handle for the top Windows node for this node.
     * <p>
     *  返回此节点的顶级Windows节点的本地句柄。
     * 
     */
    private int rootNativeHandle() {
        return (isUserNode()? USER_ROOT_NATIVE_HANDLE :
                              SYSTEM_ROOT_NATIVE_HANDLE);
    }

    /**
     * Returns this java string as a null-terminated byte array
     * <p>
     *  返回这个java字符串作为一个以null终止的字节数组
     * 
     */
    private static byte[] stringToByteArray(String str) {
        byte[] result = new byte[str.length()+1];
        for (int i = 0; i < str.length(); i++) {
            result[i] = (byte) str.charAt(i);
        }
        result[str.length()] = 0;
        return result;
    }

    /**
     * Converts a null-terminated byte array to java string
     * <p>
     *  将以null结尾的字节数组转换为java字符串
     * 
     */
    private static String byteArrayToString(byte[] array) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < array.length - 1; i++) {
            result.append((char)array[i]);
        }
        return result.toString();
    }

   /**
    * Empty, never used implementation  of AbstractPreferences.flushSpi().
    * <p>
    *  空,从未使用AbstractPreferences.flushSpi()的实现。
    * 
    */
    protected void flushSpi() throws BackingStoreException {
        // assert false;
    }

   /**
    * Empty, never used implementation  of AbstractPreferences.flushSpi().
    * <p>
    *  空,从未使用AbstractPreferences.flushSpi()的实现。
    */
    protected void syncSpi() throws BackingStoreException {
        // assert false;
    }

    private static synchronized PlatformLogger logger() {
        if (logger == null) {
            logger = PlatformLogger.getLogger("java.util.prefs");
        }
        return logger;
    }
}
