package license.jdnew.com.licensekeylibrary

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class RSATest{


    @Test
    fun rsaTest() {
        var rsaUtil = RSAUtil()
        var originData = "abc"

        //私钥加密
        var encryptData = rsaUtil.encryptWithPrivateKey(originData.toByteArray(), rsaUtil.getPrivateKey());
        //公钥解密
        var decryptData = rsaUtil.decryptWithPublicKey(encryptData, rsaUtil.getPublicKey());

        assertEquals(originData, String(decryptData));
    }
}