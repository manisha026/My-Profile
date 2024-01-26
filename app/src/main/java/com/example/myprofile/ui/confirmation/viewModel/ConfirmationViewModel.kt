package com.example.myprofile.ui.confirmation.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
) : ViewModel() {

    var header: ObservableField<String> = ObservableField("")
    var name: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")


}