package com.example.myprofile.ui.signup.viewModel
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprofile.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {

    private val validations = Validations.getInstance()
    var name: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var website: ObservableField<String> = ObservableField("")
    val validationErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    val navLiveData: MutableLiveData<Constants.SignupNavigationEnum> = MutableLiveData()

    fun onSubmit(view: View) {

        val nameValidationResponse = validations.checkNameValidation(name.get())
        val emailValidationResponse = validations.checkEmailValidation(email.get())
        val passwordValidationResponse = validations.checkPasswordValidation(password.get())
        val urlValidationResponse = validations.checkWebsiteValidation(website.get())
        if (!nameValidationResponse.valid && !emailValidationResponse.valid && !passwordValidationResponse.valid && !urlValidationResponse.valid){
            validationErrorLiveData.postValue(validations.enterData().msgId)
        }else if (!nameValidationResponse.valid ){
            validationErrorLiveData.postValue(nameValidationResponse.msgId)
        }else if (!emailValidationResponse.valid ){
            validationErrorLiveData.postValue(emailValidationResponse.msgId)
        }else if (!passwordValidationResponse.valid ){
            validationErrorLiveData.postValue(passwordValidationResponse.msgId)
        }else{
            navLiveData.postValue(Constants.SignupNavigationEnum.NavSubmit)
        }
    }
    fun addImage(view: View) {
        navLiveData.postValue(Constants.SignupNavigationEnum.NavAddImage)
    }
}