package com.wanandroid.kotlin.ui.me

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaeger.library.StatusBarUtil
import com.wanandroid.kotlin.R
import com.wanandroid.kotlin.utils.BaseActivity
import com.wanandroid.kotlin.utils.SpSettings
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_personal_info.*
import java.io.File
import java.io.IOException

class PersonalInfoActivity : BaseActivity(), View.OnClickListener {

    private val TAKE_PHOTO_REQUEST_CODE = 1
    private val FROM_ALBUM_REQUEST_CODE = 2
    private var imageUri: Uri? = null
    private lateinit var selectedPictureDialog: AlertDialog

    override fun bindLayout(): Int {
        return R.layout.activity_personal_info
    }

    override fun initView(view: View?) {

        initSetAvatarDialog()

        usernameContainerCl.setOnClickListener(this)
        avatarContainerCl.setOnClickListener(this)
    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.dark_gray), 0)
    }

    override fun onResume() {
        super.onResume()
        initUsernameAndAvatar()
    }

    private fun initUsernameAndAvatar() {
        usernameContentTv.text = SpSettings.getUsername() ?: "username"

        val requestOptions = RequestOptions.circleCropTransform();
        if (SpSettings.getAvatarUriString().isNullOrBlank()) {
            Glide.with(this)
                .load(R.drawable.default_profile)
                .apply(requestOptions)
                .into(avatarPictureIv)
        } else {
            Glide.with(this)
                .load(SpSettings.getAvatarUriString())
                .apply(requestOptions)
                .into(avatarPictureIv)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.usernameContainerCl -> {
                showSetUsernameDialog()
            }
            R.id.avatarContainerCl -> {
                PermissionX.init(this).permissions(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                        deniedList,
                        getString(R.string.granted_permission),
                        getString(R.string.confirm_cn),
                        getString(R.string.cancel_cn)
                    )
                }
                    .request { allGranted, _, deniedList ->
                        if (allGranted) {
                            selectedPictureDialog.show()
                            changeDialogSizeAndPosition()
                        } else {
                            Toast.makeText(
                                this@PersonalInfoActivity,
                                "这些权限被拒绝: $deniedList",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
            R.id.cameraTv -> {
                cameraIntent()
                selectedPictureDialog.dismiss()
            }
            R.id.albumTv -> {
                albumIntent()
                selectedPictureDialog.dismiss()
            }
            R.id.cancelTv -> {
                selectedPictureDialog.dismiss()
            }

        }
    }

    private fun showSetUsernameDialog() {
        val setUserNameEt = EditText(this)
        setUserNameEt.setText(usernameContentTv.text ?: "username")
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(55, 0, 55, 0)
        setUserNameEt.layoutParams = lp
        val linearLayout = LinearLayout(this)
        linearLayout.addView(setUserNameEt)
        val alertDialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.set_user_name_dialog_title_cn))
            .setView(linearLayout)
            .setPositiveButton(
                getString(R.string.complete_cn)
            ) { dialog, _ ->
                if (!TextUtils.isEmpty(setUserNameEt.text.toString())) {
                    val newUsername = setUserNameEt.text.toString()
                    SpSettings.setUsername(newUsername)
                    usernameContentTv.text = newUsername
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.pls_input_right_username),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun initSetAvatarDialog() {
        val builder = AlertDialog.Builder(this, R.style.dialogTheme)
        val view: View = layoutInflater.inflate(R.layout.select_picture_dialog_custom_view, null)
        builder.setView(view)
        selectedPictureDialog = builder.create()

        val cameraTv = view.findViewById<TextView>(R.id.cameraTv)
        val albumTv = view.findViewById<TextView>(R.id.albumTv)
        val cancelTv = view.findViewById<TextView>(R.id.cancelTv)
        cameraTv.setOnClickListener(this)
        albumTv.setOnClickListener(this)
        cancelTv.setOnClickListener(this)
    }

    private fun changeDialogSizeAndPosition() {
        val window = selectedPictureDialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun cameraIntent() {
        val outputImage = File(externalCacheDir, "output_image.jpg")
        if (outputImage.exists()) {
            outputImage.delete()
        }
        try {
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                this,
                "com.jere.wanandroid_learning_kotlin.fileprovider",
                outputImage
            )
        } else {
            Uri.fromFile(outputImage)
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE)
    }

    private fun albumIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, FROM_ALBUM_REQUEST_CODE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                imageUri?.let { setAvatar(it) }
                SpSettings.setAvatarUriString(imageUri.toString())
            }
            FROM_ALBUM_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK && data != null) {
                val uri = data.data
                uri?.let { setAvatar(it) }
                SpSettings.setAvatarUriString(uri.toString())
            }
            else -> {
            }
        }
    }

    private fun setAvatar(avatarUri: Uri) {
        val requestOptions = RequestOptions.circleCropTransform()
        Glide.with(this).load(avatarUri).apply(requestOptions)
            .into(avatarPictureIv)
    }
}