package com.charmflex.sportgether.sdk.core

sealed interface UIErrorType {
    object None : UIErrorType
    sealed interface SnackBarError : UIErrorType
    sealed interface BottomSheetError : UIErrorType
    sealed interface DialogError : UIErrorType
    sealed interface TextFieldError : UIErrorType

    object AuthenticationError : SnackBarError
    object RegistrationError : SnackBarError
    object RegisteredAccountError : SnackBarError
    object GenericError : SnackBarError

    object UsernameIsUsedError : TextFieldError
    object InvalidUsernameError : TextFieldError
    object EmailIsUsedError : TextFieldError
    object InvalidEmailAddressError : TextFieldError
    object InvalidPasswordError : TextFieldError
    object InvalidConfirmPassword : TextFieldError

}


