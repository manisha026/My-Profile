package com.example.myprofile.ui.signup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myprofile.R
import com.example.myprofile.databinding.ActivitySignUpBinding
import com.example.myprofile.ui.BaseActivity
import com.example.myprofile.ui.confirmation.ConfirmationActivity
import com.example.myprofile.ui.signup.viewModel.SignUpViewModel
import com.example.myprofile.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    val viewModel: SignUpViewModel by viewModels()
    private val CAMERA_REQUEST = 101
    private lateinit var bitmapImage: Bitmap
    private var isImageUpload: Boolean = false
    private lateinit var byteArray: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel
    }

    override fun onCreateView() {
        observer()
    }

    private fun observer() {

        viewModel.navLiveData.observe(this) {
            when (it!!) {
                Constants.SignupNavigationEnum.NavAddImage -> {
                    openCamera()
                }

                Constants.SignupNavigationEnum.NavSubmit -> {
                    if (isImageUpload) {
                        val stream = ByteArrayOutputStream()
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        byteArray = stream.toByteArray()
                    }
                    val intent = Intent(this@SignUpActivity, ConfirmationActivity::class.java)
                        .putExtra("name", viewModel.name.get())
                        .putExtra("email", viewModel.email.get())
                        .putExtra("url", viewModel.website.get())
                    if (isImageUpload) {
                        intent.putExtra("profile", byteArray)
                        intent.putExtra("isData", true)
                    } else {
                        intent.putExtra("isData", false)

                    }
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                    clearData()
                }
            }
        }
        viewModel.validationErrorLiveData.observe(this) {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearData() {
        val handler = Handler()
        handler.postDelayed( { // Do something after 5s = 5000ms
            viewModel.name.set("")
            viewModel.email.set("")
            viewModel.website.set("")
            viewModel.password.set("")
            isImageUpload = false
            binding.ivProfile.visibility = View.GONE
            binding.etFirstName.isFocusable = false
            binding.etEmail.isFocusable = false
            binding.etPassword.isFocusable = false
            binding.etWebsite.isFocusable = false
        }, 1000)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmapImage = data!!.extras!!["data"] as Bitmap
            binding.ivProfile.setImageBitmap(bitmapImage)
            isImageUpload = true
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 101)

        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Camera Permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}