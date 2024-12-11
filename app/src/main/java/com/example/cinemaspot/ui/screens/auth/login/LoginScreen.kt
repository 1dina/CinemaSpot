package com.example.cinemaspot.ui.screens.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cinemaspot.MainActivity
import com.example.cinemaspot.R
import com.example.cinemaspot.ui.screens.auth.AuthViewModel
import com.example.cinemaspot.ui.theme.Blue
import com.example.cinemaspot.ui.theme.Poppins
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(authViewModel: AuthViewModel, context: Context) {

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading by authViewModel.isLoading.observeAsState(false)
    val error by authViewModel.error.observeAsState()
    val usernameError by authViewModel.usernameError.observeAsState()
    val passwordError by authViewModel.passwordError.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val passwordVisible = remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_anim))
    val screenWidth = LocalConfiguration.current.screenWidthDp

    // Calculate 25% of screen width for the Lottie animation size
    val animationSize = screenWidth.dp * 0.5f

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Box(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(color = Color(0xFF242A32))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(animationSize),
                iterations = LottieConstants.IterateForever
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomOutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = "Username",
                leadingIcon = painterResource(id = R.drawable.ic_user),
                isError = error != null || usernameError != null || passwordError != null,
                modifier = Modifier.focusRequester(usernameFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                supportingText = usernameError
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = "Password",
                leadingIcon = painterResource(R.drawable.ic_lock),
                isError = error != null || usernameError != null || passwordError != null,
                modifier = Modifier.focusRequester(passwordFocusRequester),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val icon = if (passwordVisible.value) {
                        R.drawable.ic_eye
                    } else {
                        R.drawable.ic_eye_closed
                    }
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                supportingText = passwordError
            )
            Spacer(modifier = Modifier.height(34.dp))
            Button(
                onClick = {
                    authViewModel.loginUser(username.value, password.value) {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(2.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        "Login",
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        color = Color.White
                    )
                }
            }
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Haven’t made an account?",
                    modifier = Modifier.padding(end = 4.dp),
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = Color.White
                )
                Text(
                    "Sign Up",
                    modifier = Modifier.clickable {
                        val signUpUrl =
                            "https://www.themoviedb.org/signup" // الرابط الصحيح لصفحة التسجيل
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(signUpUrl))
                        context.startActivity(intent)
                    },
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = Blue
                )
            }

        }

    }
}