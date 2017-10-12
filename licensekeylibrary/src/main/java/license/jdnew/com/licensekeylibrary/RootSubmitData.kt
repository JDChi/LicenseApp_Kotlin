package license.jdnew.com.licensekeylibrary

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class RootSubmitData{

    /**
     * AES密钥，用于解密出客户端数据
     */
    var encrptAESKey: String? = null
    /**
     * 被AES加密过的数据
     */
    var encryptAESClientData: String? = null
    /**
     * 签名后的数据
     */
    var signData: String? = null
    /**
     * RSA签名公钥
     */
    var rsaSignPublicKey: String? = null
}