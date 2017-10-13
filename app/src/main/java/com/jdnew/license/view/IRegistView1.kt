package com.jdnew.license.view

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
interface IRegistView1{

    /**
     * 获取证书失败
     * @param msg
     */
    fun getLicenseKeyFailed(msg: String)

    /**
     * 获取证书成功
     * @param key
     */
    fun getLicenseKeySuccess(key: String?)

    /**
     * 证书校验成功
     * @param msg
     */
    fun checkLicenseKeySuccess(msg: String)

    /**
     * 证书校验失败
     * @param msg
     */
    fun checkLicenseKeyFailed(msg: String)

    /**
     * 加载中
     * @param msg
     */
    fun showLoading(msg: String)
}