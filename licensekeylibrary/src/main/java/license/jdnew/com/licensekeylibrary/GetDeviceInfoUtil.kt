package license.jdnew.com.licensekeylibrary

import android.content.Context
import android.content.pm.PackageManager
import org.spongycastle.util.encoders.Hex
import java.util.*
import java.util.regex.Pattern

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class GetDeviceInfoUtil(val mContext : Context){

    /**
     * 获取唯一标识码（每次安装app都是不一样的值）
     * 如：736519d5-4f7e-42fd-a6b0-66b74dff4657
     *
     * @return 返回过滤掉“-”的标识码
     * 736519d54f7e42fda6b066b74dff4657
     */
    fun getUUID() : String{
        val regularExpression = "-"
        val pattern = Pattern.compile(regularExpression)
        val matcher = pattern.matcher(UUID.randomUUID().toString())
        return matcher.replaceAll("").trim()
    }

    /**
     * 获取应用包名
     * @return
     */
   fun getAppPackageName() : String{
        return mContext.packageName
    }

    /**
     * 获取应用签名
     * @return
     */
    fun getSign() : String? {
        val pm = mContext.packageManager
        val apps = pm
                .getInstalledPackages(PackageManager.GET_SIGNATURES);
        val iter = apps.iterator();

        while (iter.hasNext()) {
            val info = iter.next();
            val packageName = info.packageName;
            //按包名 取签名
            if (packageName.equals(getAppPackageName())) {
                val signByte = info.signatures[0].toByteArray();
                return String( Hex.encode(signByte));
            }
        }
        return null
    }


}