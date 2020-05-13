package com.jere.wanandroid_learning_kotlin.view.login

import android.content.Context
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jere.wanandroid_learning_kotlin.R
import com.jere.wanandroid_learning_kotlin.utils.BaseActivity
import com.jere.wanandroid_learning_kotlin.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var isLogin: Boolean = true
    private var isPasswordEyeOff = true
    private var isRePasswordEyeOff = true
    private lateinit var loginVm: LoginViewModel

    override fun bindLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView(view: View?) {
        loginOrRegisterBtn.setOnClickListener(this)
        goRegisterTv.setOnClickListener(this)
        passwordEyeIv.setOnClickListener(this)
        repasswordEyeIv.setOnClickListener(this)

        loginVm = ViewModelProvider(this)[LoginViewModel::class.java]
        loginVm.isLoginLd.observe(this, Observer {
            if (it) {
                finish()
            } else {
                showToast(getString(R.string.pls_input_right_username_password_cn))
            }
        })

    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginOrRegisterBtn -> {
                if (isLogin) {
                    if (TextUtils.isEmpty(userNameEt.text.toString())
                        || TextUtils.isEmpty(passwordEt.text.toString())
                    ) {
                        showToast(getString(R.string.pls_input_right_username_password_cn))
                        return
                    }
                    loginVm.setIsLoginLd(userNameEt.text.toString(), passwordEt.text.toString())
                } else {
                    if (TextUtils.isEmpty(userNameEt.text.toString())
                        || TextUtils.isEmpty(passwordEt.text.toString())
                        || TextUtils.isEmpty(rePasswordEt.text.toString())
                    ) {
                        showToast(getString(R.string.pls_input_right_username_password_cn))
                        return
                    } else if (rePasswordEt.text != passwordEt.text) {
                        showToast(getString(R.string.pls_check_password))
                        return
                    }

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
                    passwordEt.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
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
                    rePasswordEt.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
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
