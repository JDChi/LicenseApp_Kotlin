package license.jdnew.com.licensekeylibrary

import org.spongycastle.jce.provider.BouncyCastleProvider
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class RSAUtil {

    companion object {

        init {
            //从位置1开始，添加新的提供者
            Security.insertProviderAt(org.spongycastle.jce.provider.BouncyCastleProvider(), 1)
        }
    }


    val mKeyMap : HashMap<String , Key> = HashMap()
    val PUBLIC_KEY = "publicKey"
    val PRIVATE_KEY = "privateKey"
    val KEY_ALGORITHM = "RSA"
    val SIGN_ALGORITHM = "SHA1withRSA"

    init {
        initPrivateAndPublicKey()

    }



    /**
     * RSA私钥加密数据
     *
     * @param data           要加密的数据
     * @param privateKeyByte 二进制的私钥
     * @return 私钥加密后的数据
     */
    fun encryptWithPrivateKey(data: ByteArray, privateKeyByte: ByteArray?): ByteArray {
        var dataBytes: ByteArray

        var pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKeyByte)
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        var privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)

        var cipher = Cipher.getInstance("RSA", BouncyCastleProvider())
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)
        dataBytes = cipher.doFinal(data)

        return dataBytes

    }

    /**
     * RSA公钥加密数据
     *
     * @param data          要加密的数据
     * @param publicKeyByte 二进制的公钥
     * @return 公钥加密后的数据
     */
    fun encryptWithPublicKey(data: ByteArray, publicKeyByte: ByteArray): ByteArray {

        var dataBytes: ByteArray

        var x509EncodedKeySpec = X509EncodedKeySpec(publicKeyByte)
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        var publicKey = keyFactory.generatePublic(x509EncodedKeySpec)

        var cipher = Cipher.getInstance("RSA", BouncyCastleProvider())
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        dataBytes = cipher.doFinal(data)

        return dataBytes
    }

    /**
     * RSA解密数据
     *
     * @param data          要解密的数据
     * @param publicKeyByte 二进制公钥
     * @return 解密后的数据
     */
    fun decryptWithPublicKey(data: ByteArray, publicKeyByte: ByteArray?): ByteArray {
        var dataBytes: ByteArray

        var x509EncodedKeySpec = X509EncodedKeySpec(publicKeyByte)
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        var publicKey = keyFactory.generatePublic(x509EncodedKeySpec)

        var cipher = Cipher.getInstance(keyFactory.getAlgorithm(), BouncyCastleProvider())
        cipher.init(Cipher.DECRYPT_MODE, publicKey)
        dataBytes = cipher.doFinal(data)

        return dataBytes

    }


    /**
     * RSA解密数据
     *
     * @param data           要解密的数据
     * @param privateKeyByte 二进制私钥
     * @return 解密后的数据
     */
    fun decryptWithPrivateKey(data: ByteArray, privateKeyByte: ByteArray): ByteArray {
        var dataBytes: ByteArray

        var pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKeyByte)
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        var privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)

        var cipher = Cipher.getInstance(keyFactory.getAlgorithm(), BouncyCastleProvider())
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        dataBytes = cipher.doFinal(data)

        return dataBytes
    }

    fun initPrivateAndPublicKey() : HashMap<String, Key>? {

        var keyPairGenerator = KeyPairGenerator.getInstance("RSA", "SC")
        keyPairGenerator.initialize(2048)
        //生成一个密钥对
        var keyPair = keyPairGenerator.generateKeyPair()
        var publicKey = keyPair.public
        var privateKey = keyPair.private

        mKeyMap?.put(PUBLIC_KEY, publicKey)
        mKeyMap?.put(PRIVATE_KEY, privateKey)

        return mKeyMap
    }

   fun getPrivateKey() : ByteArray?{
       return mKeyMap?.get(PRIVATE_KEY)?.encoded
   }

    fun getPublicKey() : ByteArray?{
        return mKeyMap?.get(PUBLIC_KEY)?.encoded
    }


    /**
     * 用私钥签名
     *
     * @param data           要签名的数据
     * @param privateKeyByte 私钥
     * @return 已签名的数据
     */
    fun signWithPrivateKey(data: ByteArray , privateKeyByte: ByteArray?) : ByteArray{

        var result : ByteArray
        var pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKeyByte)
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        var privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)
        var signature = Signature.getInstance(SIGN_ALGORITHM)
        signature.initSign(privateKey)
        signature.update(data)
        result = signature.sign()

        return result
    }

    fun verify(data: ByteArray , publicKeyByte: ByteArray , sign: ByteArray) : Boolean{

        var x509EncodedKeySpec = X509EncodedKeySpec(publicKeyByte);
        var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        var publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        var signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }





}