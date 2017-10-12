package license.jdnew.com.licensekeylibrary

import android.content.Context
import org.json.JSONObject
import org.spongycastle.util.encoders.Hex

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class GenLicenseKeyUtil(val mContext: Context) {


    val getDeviceInfoUtil = GetDeviceInfoUtil(mContext)
    val mRootData = RootData()
    val codeUtil = CodeUtil()
    val rsaUtil = RSAUtil()
    val shaUtil = SHAUtil()
    val aesUtil = AESUtil()

    init {
        //1.初始化密钥和公钥
        rsaUtil.initPrivateAndPublicKey()
    }

    /**
     * 获取要提交给服务端生成证书的数据
     * @param input
     * @return
     */
    fun getDataToGenLicense(input: String): String {


        mRootData.userName = input

        mRootData.uuid = (getDeviceInfoUtil.getUUID());
        mRootData.applicationId = (getDeviceInfoUtil.getAppPackageName());

        return mRootData.userName + mRootData.uuid + mRootData.applicationId
    }

    /**
     * 获取要提交给服务端校验的数据
     *
     * @return
     */
    fun getSubmitData(licenseKey: String, RSAPublicKey: ByteArray): String {

        //获取要提交的客户端数据
        var clientData = genClientData(licenseKey);
        //由于数据过长，无法进行RSA加密，所以使用AES加密客户端数据
        var aesEncryptClientByte = aesUtil.encryptData(Hex.encode(clientData.toByteArray()), aesUtil.getAESSecretKey())
        //获取AES的加密密钥
        var aesKey = aesUtil.getAESSecretKey()
        //用服务端发过来的RSA公钥对AES的密钥进行加密
        var encryptAESKey = rsaUtil.encryptWithPublicKey(aesKey, RSAPublicKey)
        //使用RSA对数据进行签名
        var signData = rsaUtil.signWithPrivateKey(clientData.toByteArray(), rsaUtil.getPrivateKey())
        //获取RSA的公钥
        var rsaSignPublicKey = rsaUtil.getPublicKey()


        //对aes加密后客户端数据，aes密钥，rsa公钥三者打包成json返回
        var submitData: String = genSubmitData(aesEncryptClientByte, encryptAESKey, signData, rsaSignPublicKey)


        return submitData;

    }


    /**
     * 转换成json，提交给服务端的数据
     * @param clientData 被AES加密过的数据
     * @param encryptAESKey AES密钥，用于解密出客户端数据
     * @param signData 签名后的数据
     *@param rsaSignPublicKey RSA签名公钥
     * @return
     */
    fun genSubmitData(clientData: ByteArray, encryptAESKey: ByteArray, signData: ByteArray, rsaSignPublicKey: ByteArray?): String {
        var rootSubmitData = RootSubmitData()
        rootSubmitData.encryptAESClientData = String(Hex.encode(clientData))
        rootSubmitData.encrptAESKey = String(Hex.encode(encryptAESKey))
        rootSubmitData.signData = String(Hex.encode(signData))
        rootSubmitData.rsaSignPublicKey = String(Hex.encode(rsaSignPublicKey))


        var jsonObject = JSONObject()

        jsonObject.put("encryptAESClientData", rootSubmitData.encryptAESClientData)
        jsonObject.put("encrptAESKey", rootSubmitData.encrptAESKey);
        jsonObject.put("signData", rootSubmitData.signData)
        jsonObject.put("rsaSignPublicKey", rootSubmitData.rsaSignPublicKey)

        return jsonObject.toString()
    }

    /**
     * 生成客户端数据，即在用户名，uuid，applicaitonId的基础上
     * 加入证书
     *
     * @return
     */
    fun genClientData(licenseKey: String): String {


        mRootData.licenseKey = licenseKey

        var jsonObject = JSONObject()

        jsonObject.put("userName", mRootData.userName);
        jsonObject.put("uuid", mRootData.uuid)
        jsonObject.put("applicationId", mRootData.applicationId)
        jsonObject.put("licenseKey", mRootData.licenseKey)

        return jsonObject.toString()
    }


}