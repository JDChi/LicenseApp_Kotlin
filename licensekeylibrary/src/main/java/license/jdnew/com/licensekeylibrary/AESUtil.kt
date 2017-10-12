package license.jdnew.com.licensekeylibrary

import java.security.Security
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

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
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITEM , "SC")
        keyGenerator.init(128)
        mSecretKey = keyGenerator.generateKey()
    }


}