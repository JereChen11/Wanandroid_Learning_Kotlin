package com.jere.wanandroid_learning_kotlin.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    /**
     * 是否禁止旋转屏幕
     */
    private var isAllowScreenRotate = false

    /**
     * 当前Activity渲染的视图View
     */
    private var mContextView: View? = null

    /**
     * 日志输出标志
     */
    protected val TAG = this.javaClass.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null)

        //hide time, battery status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //hide app title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mContextView)

        //set can rotate screen
//        if (!isAllowScreenRotate) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }

        initView(mContextView)
        doBusiness(this)
    }

    /**
     * [绑定布局] * * @return
     */
    abstract fun bindLayout(): Int

    /**
     * [初始化控件] *
     *
     * @param view
     */
    abstract fun initView(view: View?)

    /**
     * [业务操作]
     *
     * @param mContext
     */
    abstract fun doBusiness(mContext: Context?)

    /**
     * [页面跳转]
     * * @param clz
     */
    fun startActivity(targetClass: Class<*>?) {
        startActivity(Intent(this@BaseActivity, targetClass))
    }

    /**
     * [携带数据的页面跳转]
     */
    fun startActivity(targetClass: Class<*>?, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, targetClass!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     * * @param tartgetClass
     *
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(
        tartgetClass: Class<*>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(this, tartgetClass!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    protected fun showToast(msgContent: String?) {
        Toast.makeText(this, msgContent, Toast.LENGTH_SHORT).show()
    }

    /**
     * 隐藏软件盘
     */
    fun hideSoftInput() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null && imm != null) {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    /**
     * 显示软键盘
     */
    fun showInputMethod() {
        if (currentFocus != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.showSoftInput(
                currentFocus,
                InputMethodManager.SHOW_IMPLICIT
            )
        }
    }

    /**
     * [是否允许屏幕旋转] *
     *
     * @param isAllowScreenRoate
     */
    fun setScreenRoate(isAllowScreenRoate: Boolean) {
        isAllowScreenRotate = isAllowScreenRoate
    }
}