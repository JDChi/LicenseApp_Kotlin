package license.jdnew.com.licensekeylibrary

import org.spongycastle.util.encoders.Hex

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */

class CodeUtil{

    fun hexData(data : ByteArray) : String{
        return String(Hex.encode(data)).toUpperCase()
    }
}