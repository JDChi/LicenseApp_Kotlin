package license.jdnew.com.licensekeylibrary

import java.security.MessageDigest
import java.security.Security

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class SHAUtil{

    companion object{
        init {
            //从位置1开始，添加新的提供者
            Security.insertProviderAt(org.spongycastle.jce.provider.BouncyCastleProvider(), 1)
        }
    }


    fun encodeSHA1(data: ByteArray) : ByteArray{
        var dataByte : ByteArray
        var newDataByte : ByteArray

        var messageDigest = MessageDigest.getInstance("SHA");
        dataByte = messageDigest.digest(data);
        //摘要后截取后10位返回
        newDataByte =  ByteArray(10)
        System.arraycopy(dataByte, 9, newDataByte, 0, 10);

        return newDataByte
    }

}