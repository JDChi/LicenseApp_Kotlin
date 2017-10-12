package license.jdnew.com.licensekeylibrary

import java.security.Key
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * description
 * Created by JD
 * on 2017/10/11.
 */


class AESUtil {

    val KEY_ALGORITEM = "AES"
    val CIPHER_ALGORITEM = "AES/ECB/PKCS7Padding"
    var mSecretKey : SecretKey? =null

    companion object{
         init {
            //从位置1开始，添加新的提供者
            Security.insertProviderAt(org.spongycastle.jce.provider.BouncyCastleProvider(), 1)
         }
    }

    init {
        var keyGenerator = KeyGenerator.getInstance(KEY_ALGORITEM , "SC")
        keyGenerator.init(128)
        mSecretKey = keyGenerator.generateKey()
    }

    /**
     * aes加密数据
     * @param data 要加密的数据
     * @param keyByte 二进制密钥
     * @return
     */
    fun encryptData(data: ByteArray , keyByte: ByteArray) : ByteArray{

        var encryptResult : ByteArray
        var key = genKey(keyByte)
        var cipher = Cipher.getInstance(CIPHER_ALGORITEM , "SC")

        cipher.init(Cipher.ENCRYPT_MODE , key)
        encryptResult = cipher.doFinal(data)

        return encryptResult

    }

    /**
     * aes解密数据
     * @param data 要解密的数据
     * @param keyByte 二进制密钥
     * @return
     */
    fun decryptData(data: ByteArray , keyByte: ByteArray) : ByteArray{

        var encryptResult : ByteArray
        var key = genKey(keyByte)
        var cipher = Cipher.getInstance(CIPHER_ALGORITEM , "SC")

        cipher.init(Cipher.DECRYPT_MODE , key)
        encryptResult = cipher.doFinal(data)

        return encryptResult

    }

    fun getAESSecretKey() : ByteArray{
        return mSecretKey!!.encoded
    }

    fun genKey(keyByte: ByteArray): Key {
        var secretKey = SecretKeySpec(keyByte , KEY_ALGORITEM)

        return secretKey
    }


}