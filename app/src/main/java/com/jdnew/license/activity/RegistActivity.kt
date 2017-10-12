package com.jdnew.license.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jdnew.license.R
import com.jdnew.license.presenter.RegistPresenter
import com.jdnew.license.view.IRegistView
import kotlinx.android.synthetic.main.activity_regist.*

/**
 * description
 * Created by JD
 * on 2017/10/11.
 */
class RegistActivity : AppCompatActivity(), IRegistView, View.OnClickListener {






    var registPresenter = RegistPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        initView()
        setListener()
    }

    private fun initView() {

    }

    override fun getLicenseKeyFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getLicenseKeySuccess(key: String) {

    }

    override fun checkLicenseKeySuccess(msg: String) {


    }

    override fun checkLicenseKeyFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(msg: String) {

    }

    fun setListener(){
        bt_check.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.bt_check->
                   registPresenter.genLicenseKey(et_input_name.text.toString().trim())
           R.id.bt_gen->
                   registPresenter.submitData(et_key.text.toString().trim())
        }
    }


}