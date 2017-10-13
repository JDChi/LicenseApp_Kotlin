package com.jdnew.license.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jdnew.license.R
import com.jdnew.license.presenter.RegistPresenter1
import com.jdnew.license.view.IRegistView1
import kotlinx.android.synthetic.main.activity_regist.*

/**
 * description
 * Created by JD
 * on 2017/10/12.
 */
class RegistActivity1 : AppCompatActivity() , IRegistView1, View.OnClickListener {


    var registPresenter = RegistPresenter1(this, this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        setListener()
        registPresenter.bindService()
    }

    private fun setListener() {

        bt_gen.setOnClickListener(this)
        bt_check.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.bt_check->registPresenter.submitData(et_key.text.toString().trim())
           R.id.bt_gen->registPresenter.genLicenseKey(et_input_name.text.toString().trim())
       }

    }

    override fun getLicenseKeyFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        dismissProgressDialog()
    }

    override fun getLicenseKeySuccess(key: String?) {
//        dismissProgressDialog()
        et_key.setText(key)
    }

    override fun checkLicenseKeySuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, RegistSuccessActivity::class.java))
        finish()
    }

    override fun checkLicenseKeyFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        dismissProgressDialog()
    }

    override fun showLoading(msg: String) {
//       showProgressDialog(msg)
    }

//    var progressDialog = ProgressDialog(this)

//    fun showProgressDialog(msg : String){
//        progressDialog.setMessage(msg)
//        progressDialog.show()
//    }
//
//    fun dismissProgressDialog(){
//        if( progressDialog.isShowing){
//            progressDialog.dismiss()
//        }
//    }

}
