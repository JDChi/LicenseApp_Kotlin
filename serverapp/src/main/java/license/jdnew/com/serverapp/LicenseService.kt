package license.jdnew.com.serverapp

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import license.jdnew.com.licensekeylibrary.*
import license.jdnew.com.serverapp.aidl.ILicense
import license.jdnew.com.serverapp.aidl.RootLicenseData
import org.spongycastle.util.encoders.Hex

/**
 * description
 * Created by JD
 * on 2017/10/13.
 */
class LicenseService : Service() {


    val shaUtil = SHAUtil()
    val codeUtil = CodeUtil()
    val rsaUtil = RSAUtil()
    val aesUtil = AESUtil()


    override fun onCreate() {
        super.onCreate()
    }

    val iLicenseBinder = object : ILicense.Stub() {
        override fun returnLicense(licenseData: String?): license.jdnew.com.serverapp.aidl.RootLicenseData {
            val sha1Data = shaUtil.encodeSHA1(licenseData?.toByteArray())
            val licenseKey = codeUtil.hexData(sha1Data)
            //获取RSA的公钥
            val rsaPublicKey = rsaUtil.getPublicKey()
            val rootLicenseData = RootLicenseData(licenseKey, rsaPublicKey)

            return rootLicenseData
        }

        override fun returnCheckResult(submitData: String?): String {
            val gson = Gson()
            val rootSubmitData = gson.fromJson(submitData, RootSubmitData::class.java)
            val aesKey = Hex.decode(rootSubmitData.encrptAESKey)
            val encryptClientData = Hex.decode(rootSubmitData.encryptAESClientData)
            val signData = Hex.decode(rootSubmitData.signData)
            val rsaSignPublicKey = Hex.decode(rootSubmitData.rsaSignPublicKey)


            //用RSA私钥解密出AES密钥
            val aesKeyByte = rsaUtil.decryptWithPrivateKey(aesKey, rsaUtil.getPrivateKey())
            //用AES密钥解密出客户端数据
            val decryptClientData = aesUtil.decryptData(encryptClientData, aesKeyByte)
            val result = String(Hex.decode(decryptClientData))
            Log.d(TAG, result)

            if (isLicenseEqual(result) && isSignCorrect(result, signData, rsaSignPublicKey)) {
                return "true"
            } else {
                return "false"
            }
        }

    }

    /**
     * 签名校验结果
     *
     * @param result
     * @param signData
     * @param rsaSignPublicKey
     * @return
     */
   fun isSignCorrect (result : String,  signData : ByteArray,  rsaSignPublicKey : ByteArray) : Boolean{
        return rsaUtil.verify(result.toByteArray(), rsaSignPublicKey, signData);
    }

    /**
     * 校验客户端传过来的证书和服务端生成的证书是否一致
     *
     * @param result
     * @return
     */
    fun isLicenseEqual(result : String) : Boolean {
        val gson = Gson()
        val rootData = gson.fromJson(result, RootData::class.java)
        val data = rootData.userName + rootData.uuid + rootData.applicationId
        val newLicense = codeUtil.hexData(
                shaUtil.encodeSHA1(data?.toByteArray())
                )
        //判断由username，uuid和applicationId拼接并哈希后的值与客户端传过来的证书是否一致
        if (newLicense.equals(rootData.licenseKey)) {
            Log.d(TAG, "校验成功")
            return true

        } else {
            Log.d(TAG, "校验失败")
            return false
        }
    }


    override fun onBind(p0: Intent?): IBinder {
        return iLicenseBinder
    }

}