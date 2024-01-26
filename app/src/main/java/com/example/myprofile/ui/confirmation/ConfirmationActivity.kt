package com.example.myprofile.ui.confirmation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.myprofile.R
import com.example.myprofile.databinding.ActivityConfirmationBinding
import com.example.myprofile.ui.BaseActivity
import com.example.myprofile.ui.confirmation.viewModel.ConfirmationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class ConfirmationActivity  : BaseActivity() {

    lateinit var binding: ActivityConfirmationBinding
    val viewModel: ConfirmationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)
        binding.viewModel = viewModel
        setSpanText()
        setProfileImage()
    }

    private fun setSpanText() {
        val content = SpannableString(intent.getStringExtra("url"))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.tvUrl.text = content
        Linkify.addLinks(binding.tvUrl, Linkify.WEB_URLS);
        if (intent.getStringExtra("url")!!.isNotEmpty()){
            binding.tvUrl.visibility = View.VISIBLE
        }
    }
    private fun setProfileImage() {
        if (intent.getBooleanExtra("isData", false)) {
            val byteArray = intent.getByteArrayExtra("profile")
            if (byteArray!!.isNotEmpty()) {
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                binding.ivProfile.setImageBitmap(bmp)
            }
        }else{
            binding.cvImage.visibility = View.GONE
        }
    }

    override fun onCreateView() {
        val name = intent.getStringExtra("name")
        val upperString: String =
            name!!.substring(0, 1).uppercase(Locale.getDefault()) + name.substring(1)
                .lowercase(Locale.getDefault())

        viewModel.header.set("Hello, $upperString!")
        viewModel.name.set(upperString.toString())
        viewModel.email.set(intent.getStringExtra("email"))


    }
}