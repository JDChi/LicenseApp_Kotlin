package com.jdnew.license.presenter

import android.app.Activity
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import com.jdnew.license.common.Constant
import com.jdnew.license.view.IRegistView

/**
 * description
 * Created by JD
 * on 2017/10/11.
 */

public class RegistPresenter(private var mContext: Activity, private var iRegistView: IRegistView) {

    private val TAG = "RegistPresenter"

    init {

    }

    fun genLicenseKey(name: String) {
        if (TextUtils.isEmpty(name)) {
            iRegistView.checkLicenseKeyFailed("请输入用户名")
            return
        }
        iRegistView.showLoading("正在获取证书")
        callServiceToGetLicense(name);

    }

    private fun callServiceToGetLicense(name: String) {
        val message = Message()
        val bundle = Bundle()

        message.what = Constant.MSG_GET_LICENSE


    }


    fun submitData(key: String) {

    }


}
