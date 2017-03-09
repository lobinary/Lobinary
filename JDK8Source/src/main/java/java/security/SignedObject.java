/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.io.*;

/**
 * <p> SignedObject is a class for the purpose of creating authentic
 * runtime objects whose integrity cannot be compromised without being
 * detected.
 *
 * <p> More specifically, a SignedObject contains another Serializable
 * object, the (to-be-)signed object and its signature.
 *
 * <p> The signed object is a "deep copy" (in serialized form) of an
 * original object.  Once the copy is made, further manipulation of
 * the original object has no side effect on the copy.
 *
 * <p> The underlying signing algorithm is designated by the Signature
 * object passed to the constructor and the {@code verify} method.
 * A typical usage for signing is the following:
 *
 * <pre>{@code
 * Signature signingEngine = Signature.getInstance(algorithm,
 *                                                 provider);
 * SignedObject so = new SignedObject(myobject, signingKey,
 *                                    signingEngine);
 * }</pre>
 *
 * <p> A typical usage for verification is the following (having
 * received SignedObject {@code so}):
 *
 * <pre>{@code
 * Signature verificationEngine =
 *     Signature.getInstance(algorithm, provider);
 * if (so.verify(publickey, verificationEngine))
 *     try {
 *         Object myobj = so.getObject();
 *     } catch (java.lang.ClassNotFoundException e) {};
 * }</pre>
 *
 * <p> Several points are worth noting.  First, there is no need to
 * initialize the signing or verification engine, as it will be
 * re-initialized inside the constructor and the {@code verify}
 * method. Secondly, for verification to succeed, the specified
 * public key must be the public key corresponding to the private key
 * used to generate the SignedObject.
 *
 * <p> More importantly, for flexibility reasons, the
 * constructor and {@code verify} method allow for
 * customized signature engines, which can implement signature
 * algorithms that are not installed formally as part of a crypto
 * provider.  However, it is crucial that the programmer writing the
 * verifier code be aware what {@code Signature} engine is being
 * used, as its own implementation of the {@code verify} method
 * is invoked to verify a signature.  In other words, a malicious
 * {@code Signature} may choose to always return true on
 * verification in an attempt to bypass a security check.
 *
 * <p> The signature algorithm can be, among others, the NIST standard
 * DSA, using DSA and SHA-1.  The algorithm is specified using the
 * same convention as that for signatures. The DSA algorithm using the
 * SHA-1 message digest algorithm can be specified, for example, as
 * "SHA/DSA" or "SHA-1/DSA" (they are equivalent).  In the case of
 * RSA, there are multiple choices for the message digest algorithm,
 * so the signing algorithm could be specified as, for example,
 * "MD2/RSA", "MD5/RSA" or "SHA-1/RSA".  The algorithm name must be
 * specified, as there is no default.
 *
 * <p> The name of the Cryptography Package Provider is designated
 * also by the Signature parameter to the constructor and the
 * {@code verify} method.  If the provider is not
 * specified, the default provider is used.  Each installation can
 * be configured to use a particular provider as default.
 *
 * <p> Potential applications of SignedObject include:
 * <ul>
 * <li> It can be used
 * internally to any Java runtime as an unforgeable authorization
 * token -- one that can be passed around without the fear that the
 * token can be maliciously modified without being detected.
 * <li> It
 * can be used to sign and serialize data/object for storage outside
 * the Java runtime (e.g., storing critical access control data on
 * disk).
 * <li> Nested SignedObjects can be used to construct a logical
 * sequence of signatures, resembling a chain of authorization and
 * delegation.
 * </ul>
 *
 * <p>
 *  <p> SignedObject是一个类,用于创建真正的运行时对象,其完整性不会被检测到而被破坏。
 * 
 *  <p>更具体地说,SignedObject包含另一个Serializable对象,(to-be)签名对象及其签名。
 * 
 *  <p>签名对象是原始对象的"深层副本"(以序列化形式)。一旦复制,进一步操作原始对象对副本没有副作用。
 * 
 *  <p>底层签名算法由传递给构造函数和{@code verify}方法的Signature对象指定。签名的典型用法如下：
 * 
 *  <pre> {@ code Signature signingEngine = Signature.getInstance(algorithm,provider); SignedObject so = new SignedObject(myobject,signingKey,signingEngine); }
 *  </pre>。
 * 
 *  <p>验证的典型用法如下(已收到SignedObject {@code so})：
 * 
 *  <pre> {@ code Signature verificationEngine = Signature.getInstance(algorithm,provider); if(so.verify(publickey,verificationEngine))try {Object myobj = so.getObject(); }
 *  catch(java.lang.ClassNotFoundException e){}; } </pre>。
 * 
 * <p>有几点值得注意。首先,不需要初始化签名或验证引擎,因为它将在构造函数和{@code verify}方法内重新初始化。
 * 其次,为了验证成功,指定的公钥必须是与用于生成SignedObject的私钥对应的公钥。
 * 
 *  更重要的是,出于灵活性原因,构造函数和{@code verify}方法允许定制签名引擎,它可以实现未正式作为加密提供程序的一部分安装的签名算法。
 * 然而,编写验证器代码的程序员必须知道正在使用什么{@code Signature}引擎,因为它自己的{@code verify}方法的实现被调用来验证签名。
 * 换句话说,恶意的{@code签名}可能会选择始终在验证时返回true,以尝试绕过安全检查。
 * 
 *  <p>签名算法可以是使用DSA和SHA-1的NIST标准DSA等等。使用与签名相同的约定来指定算法。
 * 使用SHA-1消息摘要算法的DSA算法可以被规定为例如"SHA / DSA"或"SHA-1 / DSA"(它们是等价的)。
 * 在RSA的情况下,消息摘要算法有多种选择,因此签名算法可以被规定为例如"MD2 / RSA","MD5 / RSA"或"SHA-1 / RSA"。必须指定算法名称,因为没有默认值。
 * 
 * <p>加密包提供程序的名称也由构造函数和{@code verify}方法的Signature参数指定。如果未指定提供程序,则使用缺省提供程序。每个安装可以配置为使用特定的提供程序作为默认。
 * 
 *  <p> SignedObject的潜在应用程序包括：
 * 
 * @see Signature
 *
 * @author Li Gong
 */

public final class SignedObject implements Serializable {

    private static final long serialVersionUID = 720502720485447167L;

    /*
     * The original content is "deep copied" in its serialized format
     * and stored in a byte array.  The signature field is also in the
     * form of byte array.
     * <p>
     * <ul>
     *  <li>它可以在任何Java运行时内部用作不可伪造的授权令牌 - 可以通过传递,而不必担心令牌可能被恶意修改而不被检测。
     *  <li>它可用于在Java运行时之外对数据/对象进行签名和序列化存储(例如,在磁盘上存储关键访问控制数据)。 <li>嵌套SignedObject可用于构建逻辑的签名序列,类似于授权和委派链。
     * </ul>
     * 
     */

    private byte[] content;
    private byte[] signature;
    private String thealgorithm;

    /**
     * Constructs a SignedObject from any Serializable object.
     * The given object is signed with the given signing key, using the
     * designated signature engine.
     *
     * <p>
     *  原始内容以其串行化格式"深度复制"并存储在字节数组中。签名字段也是字节数组的形式。
     * 
     * 
     * @param object the object to be signed.
     * @param signingKey the private key for signing.
     * @param signingEngine the signature signing engine.
     *
     * @exception IOException if an error occurs during serialization
     * @exception InvalidKeyException if the key is invalid.
     * @exception SignatureException if signing fails.
     */
    public SignedObject(Serializable object, PrivateKey signingKey,
                        Signature signingEngine)
        throws IOException, InvalidKeyException, SignatureException {
            // creating a stream pipe-line, from a to b
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutput a = new ObjectOutputStream(b);

            // write and flush the object content to byte array
            a.writeObject(object);
            a.flush();
            a.close();
            this.content = b.toByteArray();
            b.close();

            // now sign the encapsulated object
            this.sign(signingKey, signingEngine);
    }

    /**
     * Retrieves the encapsulated object.
     * The encapsulated object is de-serialized before it is returned.
     *
     * <p>
     *  从任何可序列化对象构造一个SignedObject。使用指定的签名引擎,使用给定的签名密钥对给定对象签名。
     * 
     * 
     * @return the encapsulated object.
     *
     * @exception IOException if an error occurs during de-serialization
     * @exception ClassNotFoundException if an error occurs during
     * de-serialization
     */
    public Object getObject()
        throws IOException, ClassNotFoundException
    {
        // creating a stream pipe-line, from b to a
        ByteArrayInputStream b = new ByteArrayInputStream(this.content);
        ObjectInput a = new ObjectInputStream(b);
        Object obj = a.readObject();
        b.close();
        a.close();
        return obj;
    }

    /**
     * Retrieves the signature on the signed object, in the form of a
     * byte array.
     *
     * <p>
     *  检索已封装的对象。被封装的对象在被返回之前被去序列化。
     * 
     * 
     * @return the signature. Returns a new array each time this
     * method is called.
     */
    public byte[] getSignature() {
        return this.signature.clone();
    }

    /**
     * Retrieves the name of the signature algorithm.
     *
     * <p>
     *  以字节数组的形式检索签名对象上的签名。
     * 
     * 
     * @return the signature algorithm name.
     */
    public String getAlgorithm() {
        return this.thealgorithm;
    }

    /**
     * Verifies that the signature in this SignedObject is the valid
     * signature for the object stored inside, with the given
     * verification key, using the designated verification engine.
     *
     * <p>
     *  检索签名算法的名称。
     * 
     * 
     * @param verificationKey the public key for verification.
     * @param verificationEngine the signature verification engine.
     *
     * @exception SignatureException if signature verification failed.
     * @exception InvalidKeyException if the verification key is invalid.
     *
     * @return {@code true} if the signature
     * is valid, {@code false} otherwise
     */
    public boolean verify(PublicKey verificationKey,
                          Signature verificationEngine)
         throws InvalidKeyException, SignatureException {
             verificationEngine.initVerify(verificationKey);
             verificationEngine.update(this.content.clone());
             return verificationEngine.verify(this.signature.clone());
    }

    /*
     * Signs the encapsulated object with the given signing key, using the
     * designated signature engine.
     *
     * <p>
     * 使用指定的验证引擎验证此SignedObject中的签名是否为使用给定验证密钥存储在其中的对象的有效签名。
     * 
     * 
     * @param signingKey the private key for signing.
     * @param signingEngine the signature signing engine.
     *
     * @exception InvalidKeyException if the key is invalid.
     * @exception SignatureException if signing fails.
     */
    private void sign(PrivateKey signingKey, Signature signingEngine)
        throws InvalidKeyException, SignatureException {
            // initialize the signing engine
            signingEngine.initSign(signingKey);
            signingEngine.update(this.content.clone());
            this.signature = signingEngine.sign().clone();
            this.thealgorithm = signingEngine.getAlgorithm();
    }

    /**
     * readObject is called to restore the state of the SignedObject from
     * a stream.
     * <p>
     *  使用指定的签名引擎使用给定的签名密钥签署封装的对象。
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
            java.io.ObjectInputStream.GetField fields = s.readFields();
            content = ((byte[])fields.get("content", null)).clone();
            signature = ((byte[])fields.get("signature", null)).clone();
            thealgorithm = (String)fields.get("thealgorithm", null);
    }
}
