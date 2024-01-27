import android.util.Patterns
import android.webkit.URLUtil
import androidx.annotation.Keep
import com.example.myprofile.R
import com.example.myprofile.utils.EmailValidator
import java.util.regex.Matcher
import java.util.regex.Pattern


class Validations private constructor() {

    @Keep
    data class ValidationResponse(val valid: Boolean, val msgId: Int)


    companion object {
        private var validations: Validations? = null
        fun getInstance(): Validations {
            if (validations == null) validations = Validations()
            return validations!!
        }
    }

    fun checkNameValidation(name: String?): ValidationResponse {
        return if (name!!.isEmpty()) {
            ValidationResponse(false, R.string.please_enter_name)
        } else {
            ValidationResponse(true, 0)
        }
    }

    fun checkEmailValidation(email: String?): ValidationResponse {
        return if (email!!.isEmpty() ) {
            ValidationResponse(false, R.string.please_enter_email)
        } else if (!EmailValidator.isValidEmail(email.toString())) {
                ValidationResponse(false, R.string.please_enter_valid_email)
        } else {
                ValidationResponse(true, 0)
        }
    }

    fun checkPasswordValidation(password: String?): ValidationResponse {
        return if (password!!.isNotEmpty() && password.length < 8 && !isValidPassword(password)) {
            ValidationResponse(false, R.string.please_enter_valid_password)
        } else if (password.isEmpty()) {
            ValidationResponse(false, R.string.please_enter_password)
        } else {
            ValidationResponse(true, 0)
        }
    }

    fun checkWebsiteValidation(website: String?): ValidationResponse {
        return if (website!!.isNotEmpty() && !URLUtil.isValidUrl(website)) {
            ValidationResponse(false, R.string.please_enter_valid_url)
        } else {
            ValidationResponse(true, 0)
        }
    }

    fun enterData(): ValidationResponse {
        return ValidationResponse(false,R.string.please_enter_data)

    }
    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password!!)
        return matcher.matches()
    }
}
