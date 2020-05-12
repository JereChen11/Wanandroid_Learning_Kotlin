package com.jere.wanandroid_learning_kotlin.view.login

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() , View.OnClickListener{

    private var isLogin: Boolean = true
    private var isPasswordEyeOff = true
    private var isRePasswordEyeOff = true

    override fun bindLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView(view: View?) {
        loginOrRegisterBtn.setOnClickListener(this)
        goRegisterTv.setOnClickListener(this)
        passwordEyeIv.setOnClickListener(this)
        repasswordEyeIv.setOnClickListener(this)

    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.loginOrRegisterBtn -> {
                if (isLogin) {

                } else {

                }
            }
            R.id.goRegisterTv -> {
                if (isLogin) {
                    logInOrRegisterTv.text = getText(R.string.register_cn)
                    loginOrRegisterBtn.text = getText(R.string.register_cn)
                    goRegisterTv.text = getText(R.string.login_cn)
                    rePasswordContainerCl.visibility = View.VISIBLE
                    isLogin = false
                } else {
                    logInOrRegisterTv.text = getText(R.string.login_cn)
                    loginOrRegisterBtn.text = getText(R.string.login_cn)
                    goRegisterTv.text = getText(R.string.register_cn)
                    rePasswordContainerCl.visibility = View.GONE
                    isLogin = true
                }
            }
            R.id.passwordEyeIv -> {
                if (isPasswordEyeOff) {
                    passwordEyeIv.setImageResource(R.drawable.vector_drawable_eye)
                    passwordEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    isPasswordEyeOff = false
                } else {
                    passwordEyeIv.setImageResource(R.drawable.vector_drawable_eyeoff)
                    passwordEt.inputType = InputType.TYPE_CLASS_TEXT
                    passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                    isPasswordEyeOff = true
                }

            }
            R.id.repasswordEyeIv -> {
                if (isRePasswordEyeOff) {
                    repasswordEyeIv.setImageResource(R.drawable.vector_drawable_eye)
                    rePasswordEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    isRePasswordEyeOff = false
                } else {
                    repasswordEyeIv.setImageResource(R.drawable.vector_drawable_eyeoff)
                    rePasswordEt.inputType = InputType.TYPE_CLASS_TEXT
                    rePasswordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                    isRePasswordEyeOff = true
                }
            }
        }
    }
}
