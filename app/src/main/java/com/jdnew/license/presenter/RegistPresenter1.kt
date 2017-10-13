package com.jdnew.license.presenter

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.text.TextUtils
import com.jdnew.license.view.IRegistView1
import license.jdnew.com.licensekeylibrary.GenLicenseKeyUtil
import license.jdnew.com.serverapp.aidl.ILicense

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */

class RegistPresenter1(val mContext : Activity , val mIRegistView1: IRegistView1){


    val genLicenseKeyUtil = GenLicenseKeyUtil(mContext)
    var rsaPublicKey : ByteArray ?= null

    fun bindService(){
        val intent = Intent()
        intent.action = "license.jdnew.com.serverapp.LicenseService"
        intent.`package` = "license.jdnew.com.serverapp"
        mContext.bindService(intent , mServiceConnection , Service.BIND_AUTO_CREATE)
    }

    var mILicense : ILicense ?= null
    var mServiceConnection = object : ServiceConnection{
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mILicense = ILicense.Stub.asInterface(service)
        }

    }

    fun genLicenseKey(name: String) {
       if(TextUtils.isEmpty(name)){
           mIRegistView1.getLicenseKeyFailed("请输入用户名")
           return
       }

        mIRegistView1.showLoading("正在获取证书")
        val rootLicenseData = mILicense?.returnLicense(genLicenseKeyUtil.getDataToGenLicense(name))
        mIRegistView1.getLicenseKeySuccess(rootLicenseData?.license)
        rsaPublicKey = rootLicenseData?.rsaPublicKey


    }

    fun submitData(licenseKey: String) {
        if (TextUtils.isEmpty(licenseKey)) {
            mIRegistView1.checkLicenseKeyFailed("请输入证书")
            return
        }

        val submitData = genLicenseKeyUtil.getSubmitData(licenseKey , rsaPublicKey)


            val result = mILicense?.returnCheckResult(submitData)
            if (result.equals("true")) {
                mIRegistView1.checkLicenseKeySuccess("验证成功")
            }else {
                mIRegistView1.checkLicenseKeyFailed("验证失败")
            }



    }
}
