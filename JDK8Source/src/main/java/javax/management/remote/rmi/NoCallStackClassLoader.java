/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.remote.rmi;

import java.security.ProtectionDomain;

/**
    <p>A class loader that only knows how to define a limited number
    of classes, and load a limited number of other classes through
    delegation to another loader.  It is used to get around a problem
    with Serialization, in particular as used by RMI (including
    RMI/IIOP).  The JMX Remote API defines exactly what class loader
    must be used to deserialize arguments on the server, and return
    values on the client.  We communicate this class loader to RMI by
    setting it as the context class loader.  RMI uses the context
    class loader to load classes as it deserializes, which is what we
    want.  However, before consulting the context class loader, it
    looks up the call stack for a class with a non-null class loader,
    and uses that if it finds one.  So, in the standalone version of
    javax.management.remote, if the class you're looking for is known
    to the loader of jmxremote.jar (typically the system class loader)
    then that loader will load it.  This contradicts the class-loading
    semantics required.

    <p>We get around the problem by ensuring that the search up the
    call stack will find a non-null class loader that doesn't load any
    classes of interest, namely this one.  So even though this loader
    is indeed consulted during deserialization, it never finds the
    class being deserialized.  RMI then proceeds to use the context
    class loader, as we require.

    <p>This loader is constructed with the name and byte-code of one
    or more classes that it defines, and a class-loader to which it
    will delegate certain other classes required by that byte-code.
    We construct the byte-code somewhat painstakingly, by compiling
    the Java code directly, converting into a string, copying that
    string into the class that needs this loader, and using the
    stringToBytes method to convert it into the byte array.  We
    compile with -g:none because there's not much point in having
    line-number information and the like in these directly-encoded
    classes.

    <p>The referencedClassNames should contain the names of all
    classes that are referenced by the classes defined by this loader.
    It is not necessary to include standard J2SE classes, however.
    Here, a class is referenced if it is the superclass or a
    superinterface of a defined class, or if it is the type of a
    field, parameter, or return value.  A class is not referenced if
    it only appears in the throws clause of a method or constructor.
    Of course, referencedClassNames should not contain any classes
    that the user might want to deserialize, because the whole point
    of this loader is that it does not find such classes.
/* <p>
/*  <p>类加载器只知道如何定义有限数量的类,并通过委派加载有限数量的其他类到另一个加载器。它用于解决序列化的问题,特别是RMI(包括RMI / IIOP)使用的问题。
/*  JMX远程API准确地定义必须使用哪个类加载器来反序列化服务器上​​的参数,并在客户端上返回值。我们通过将其设置为上下文类加载器来将此类加载器传递给RMI。
/*  RMI使用上下文类加载器来加载类,因为它反序列化,这是我们想要的。然而,在咨询上下文类加载器之前,它查找具有非空类加载器的类的调用栈,并且如果它找到一个类加载器,则使用它。
/* 因此,在javax.management.remote的独立版本中,如果你正在寻找的类是jmxremote.jar的加载器(通常是系统类加载器)已知的,那么加载器将加载它。这与所需的类加载语义相矛盾。
/* 
/*  <p>我们通过确保在调用堆栈中搜索找到一个不加载任何感兴趣的类的非空类加载器,即这一个,解决了这个问题。因此,即使这个加载器在反序列化期间确实被咨询,它从来没有发现类被反序列化。
/*  RMI然后继续使用上下文类加载器,如我们所要求的。
/* 
/* <p>这个加载程序是用它定义的一个或多个类的名字和字节码构成的,还有一个类加载器,它将委托该字节码所需的某些其他类。
/* 我们通过直接编译Java代码,转换为字符串,将该字符串复制到需要该加载器的类中,并使用stringToBytes方法将其转换为字节数组,从而构建字节码。
/* 我们使用-g：none编译,因为在这些直接编码的类中有行号信息等没有多少意义。
/* 
/*  <p> referencedClassNames应包含由此加载器定义的类引用的所有类的名称。然而,没有必要包括标准的J2SE类。
/* 这里,如果类是定义类的超类或超级接口,或者如果它是字段,参数或返回值的类型,则引用该类。如果类只出现在方法或构造函数的throws子句中,则不会引用该类。
/* 当然,referencedClassNames不应该包含用户可能想要反序列化的任何类,因为这个加载器的整个点是它没有找到这样的类。
/* 
*/

class NoCallStackClassLoader extends ClassLoader {
    /** Simplified constructor when this loader only defines one class.  */
    public NoCallStackClassLoader(String className,
                                  byte[] byteCode,
                                  String[] referencedClassNames,
                                  ClassLoader referencedClassLoader,
                                  ProtectionDomain protectionDomain) {
        this(new String[] {className}, new byte[][] {byteCode},
             referencedClassNames, referencedClassLoader, protectionDomain);
    }

    public NoCallStackClassLoader(String[] classNames,
                                  byte[][] byteCodes,
                                  String[] referencedClassNames,
                                  ClassLoader referencedClassLoader,
                                  ProtectionDomain protectionDomain) {
        super(null);

        /* Validation. */
        if (classNames == null || classNames.length == 0
            || byteCodes == null || classNames.length != byteCodes.length
            || referencedClassNames == null || protectionDomain == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < classNames.length; i++) {
            if (classNames[i] == null || byteCodes[i] == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < referencedClassNames.length; i++) {
            if (referencedClassNames[i] == null)
                throw new IllegalArgumentException();
        }

        this.classNames = classNames;
        this.byteCodes = byteCodes;
        this.referencedClassNames = referencedClassNames;
        this.referencedClassLoader = referencedClassLoader;
        this.protectionDomain = protectionDomain;
    }

    /* This method is called at most once per name.  Define the name
     * if it is one of the classes whose byte code we have, or
     * delegate the load if it is one of the referenced classes.
     * <p>
     *  如果它是我们有其字节码的类之一,或委托加载,如果它是引用类之一。
     * 
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Note: classNames is guaranteed by the constructor to be non-null.
        for (int i = 0; i < classNames.length; i++) {
            if (name.equals(classNames[i])) {
                return defineClass(classNames[i], byteCodes[i], 0,
                                   byteCodes[i].length, protectionDomain);
            }
        }

        /* If the referencedClassLoader is null, it is the bootstrap
         * class loader, and there's no point in delegating to it
         * because it's already our parent class loader.
         * <p>
         *  类加载器,并且没有任何意义,因为它已经是我们的父类加载器。
         * 
         */
        if (referencedClassLoader != null) {
            for (int i = 0; i < referencedClassNames.length; i++) {
                if (name.equals(referencedClassNames[i]))
                    return referencedClassLoader.loadClass(name);
            }
        }

        throw new ClassNotFoundException(name);
    }

    private final String[] classNames;
    private final byte[][] byteCodes;
    private final String[] referencedClassNames;
    private final ClassLoader referencedClassLoader;
    private final ProtectionDomain protectionDomain;

    /**
     * <p>Construct a <code>byte[]</code> using the characters of the
     * given <code>String</code>.  Only the low-order byte of each
     * character is used.  This method is useful to reduce the
     * footprint of classes that include big byte arrays (e.g. the
     * byte code of other classes), because a string takes up much
     * less space in a class file than the byte code to initialize a
     * <code>byte[]</code> with the same number of bytes.</p>
     *
     * <p>We use just one byte per character even though characters
     * contain two bytes.  The resultant output length is much the
     * same: using one byte per character is shorter because it has
     * more characters in the optimal 1-127 range but longer because
     * it has more zero bytes (which are frequent, and are encoded as
     * two bytes in classfile UTF-8).  But one byte per character has
     * two key advantages: (1) you can see the string constants, which
     * is reassuring, (2) you don't need to know whether the class
     * file length is odd.</p>
     *
     * <p>This method differs from {@link String#getBytes()} in that
     * it does not use any encoding.  So it is guaranteed that each
     * byte of the result is numerically identical (mod 256) to the
     * corresponding character of the input.
     * <p>
     * <p>使用给定的<code> String </code>字符构造<code> byte [] </code>。只使用每个字符的低位字节。
     * 此方法对于减少包含大字节数组(例如其他类的字节代码)的类的占用空间非常有用,因为字符串在类文件中占用的空间远小于字节代码来初始化<code> byte [] </code>以相同的字节数。</p>。
     * 
     *  <p>我们每个字符只使用一个字节,即使字符包含两个字节。
     * 结果输出长度是相同的：每个字符使用一个字节更短,因为它有更多的字符在最佳1-127范围,但更长,因为它有更多的零字节(这是频繁的,并且被编码为两个字节在类文件UTF -8)。
     * 但是每个字符一个字节有两个主要优点：(1)你可以看到字符串常量,这是令人放心,(2)你不需要知道类文件长度是否为奇数。</p>。
     * 
     *  <p>此方法与{@link String#getBytes()}不同,它不使用任何编码。因此,保证结果的每个字节在数字上与输入的相应字符相同(mod 256)。
     * 
     */
    public static byte[] stringToBytes(String s) {
        final int slen = s.length();
        byte[] bytes = new byte[slen];
        for (int i = 0; i < slen; i++)
            bytes[i] = (byte) s.charAt(i);
        return bytes;
    }
}

/*

You can use the following Emacs function to convert class files into
strings to be used by the stringToBytes method above.  Select the
whole (defun...) with the mouse and type M-x eval-region, or save it
to a file and do M-x load-file.  Then visit the *.class file and do
M-x class-string.

;; class-string.el
;; visit the *.class file with emacs, then invoke this function

(defun class-string ()
  "Construct a Java string whose bytes are the same as the current
buffer.  The resultant string is put in a buffer called *string*,
possibly with a numeric suffix like <2>.  From there it can be
insert-buffer'd into a Java program."
  (interactive)
  (let* ((s (buffer-string))
         (slen (length s))
         (i 0)
         (buf (generate-new-buffer "*string*")))
    (set-buffer buf)
    (insert "\"")
    (while (< i slen)
      (if (> (current-column) 61)
          (insert "\"+\n\""))
      (let ((c (aref s i)))
        (insert (cond
                 ((> c 126) (format "\\%o" c))
                 ((= c ?\") "\\\"")
                 ((= c ?\\) "\\\\")
                 ((< c 33)
                  (let ((nextc (if (< (1+ i) slen)
                                   (aref s (1+ i))
                                 ?\0)))
                    (cond
                     ((and (<= nextc ?7) (>= nextc ?0))
                      (format "\\%03o" c))
                     (t
                      (format "\\%o" c)))))
                 (t c))))
      (setq i (1+ i)))
    (insert "\"")
    (switch-to-buffer buf)))

Alternatively, the following class reads a class file and outputs a string
that can be used by the stringToBytes method above.

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BytesToString {

    public static void main(String[] args) throws IOException {
        File f = new File(args[0]);
        int len = (int)f.length();
        byte[] classBytes = new byte[len];

        FileInputStream in = new FileInputStream(args[0]);
        try {
            int pos = 0;
            for (;;) {
                int n = in.read(classBytes, pos, (len-pos));
                if (n < 0)
                    throw new RuntimeException("class file changed??");
                pos += n;
                if (pos >= n)
                    break;
            }
        } finally {
            in.close();
        }

        int pos = 0;
        boolean lastWasOctal = false;
        for (int i=0; i<len; i++) {
            int value = classBytes[i];
            if (value < 0)
                value += 256;
            String s = null;
            if (value == '\\')
                s = "\\\\";
            else if (value == '\"')
                s = "\\\"";
            else {
                if ((value >= 32 && value < 127) && ((!lastWasOctal ||
                    (value < '0' || value > '7')))) {
                    s = Character.toString((char)value);
                }
            }
            if (s == null) {
                s = "\\" + Integer.toString(value, 8);
                lastWasOctal = true;
            } else {
                lastWasOctal = false;
            }
            if (pos > 61) {
                System.out.print("\"");
                if (i<len)
                    System.out.print("+");
                System.out.println();
                pos = 0;
            }
            if (pos == 0)
                System.out.print("                \"");
            System.out.print(s);
            pos += s.length();
        }
        System.out.println("\"");
    }
}

         (buf (generate-new-buffer "* <p>
         (buf (generate-new-buffer "*  您可以使用以下Emacs函数将类文件转换为stringToBytes方法要使用的字符串。
         (buf (generate-new-buffer "* 用鼠标选择整个(defun ...)并键入M-x eval-region,或将其保存到文件并执行M-x加载文件。然后访问* .class文件并做M-x类字符串。
         (buf (generate-new-buffer "* 
         (buf (generate-new-buffer "* ;; class-string.el ;;使用emacs访问* .class文件,然后调用此函数
         (buf (generate-new-buffer "* 
         (buf (generate-new-buffer "*  (defun class-string()"构造一个字符串与当前缓冲区相同的Java字符串,将结果字符串放入一个名为* string *的缓冲区中,可能带有<2>这样的数字后缀, insert-buf
         (buf (generate-new-buffer "* fer'd into a Java program。
         (buf (generate-new-buffer "* "(交互)(let *((s(buffer-string))(slen(length s))(i 0)(buf(generate-new-buffer"* string * ))(set-buffer 
         (buf (generate-new-buffer "* buf)(插入"\"")(while(<i slen)(if(>(current-column)61)(insert"\"+ \ n \""))let (c(aref si)))(insert(cond
         (buf (generate-new-buffer "* ((&gt; c 126)(format"\\％o"c))((= c?\")"\\\" \)"\\\\")((<c 33)(let((nextc(if(<(1+ i slen)(aref s(1+ i))?\ 0)和(<= nextc?7)(>
         (buf (generate-new-buffer "*  = nextc?0))(格式"\\％03o"c) setq i(1+ i)))(插入"\""(切换到缓冲器buf)))。
         (buf (generate-new-buffer "* 
         (buf (generate-new-buffer "*  或者,以下类读取类文件并输出可由上面的stringToBytes方法使用的字符串。
         (buf (generate-new-buffer "* 
*/
