package license.jdnew.com.licensekeylibrary

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class SHATest{

    @Test
    fun shaTest(){
        val shaUtil = SHAUtil()
        val codeUtil = CodeUtil()
        val originData = "abc"
        assertEquals(codeUtil.hexData(shaUtil.encodeSHA1(originData.toByteArray())) , codeUtil.hexData(shaUtil.encodeSHA1(originData.toByteArray())))
    }
}