package com.demo.demosignup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class SignUpViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SignUpViewModel()
        viewModel.result.observeForever(observer)
    }

    @Test
    fun signUp_withValidInput_shouldReturnSuccess() {
        viewModel.signUpUser("validEmail@gmail.com","validUsername", "validPassword","validWebPath","validImagePath")
        Mockito.verify(observer).onChanged(true)
    }

    @Test
    fun signUp_WithInvalidEmail_shouldReturnSuccess() {
        viewModel.signUpUser("validEmail","validUsername", "validPassword","validWebPath","validImagePath")
        Mockito.verify(observer).onChanged(false)
    }

    @Test
    fun signUp_withEmptyInput_shouldReturnFailure() {
        viewModel.signUpUser("","", "","","")
        Mockito.verify(observer).onChanged(false)
    }
}