package license.jdnew.com.licensekeylibrary


import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class AESTest{

    @Test
    fun aesTest(){
        var aesUtil : AESUtil = AESUtil()
        var originData : String = "hello world"
        var encryptData = aesUtil.encryptData(originData.toByteArray(), aesUtil.getAESSecretKey())
        var decryptData = aesUtil.decryptData(encryptData , aesUtil.getAESSecretKey())

        assertEquals(originData , String(decryptData))
    }
}