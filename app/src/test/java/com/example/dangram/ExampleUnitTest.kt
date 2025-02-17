package com.example.dangram

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val firebaseAuth: FirebaseAuth = Firebase.auth

    @Test
    fun TestIncorrectInputAuth() = runBlocking {
        val testEmail = "dan@gmail.com"
        val invalidPassword = "super"

        try {
            firebaseAuth.signInWithEmailAndPassword(testEmail, invalidPassword).await()
            fail("Авторизация с неверным паролем должна вызывать ошибку")
        } catch (e: Exception) {
            assertNotNull("Ожидаемое исключение должно быть получено", e)
        }
    }

    @Test
    fun TestExistUserRegistration() = runBlocking {
        val testEmail = "dan@gmail.com"
        val testPassword = "super26"

        try {
            firebaseAuth.createUserWithEmailAndPassword(testEmail, testPassword).await()
            fail("Регистрация с уже существующим пользователем должна вызывать ошибку")
        } catch (e: Exception) {
            assertNotNull("Ожидаемое исключение должно быть получено", e)
        }
    }
}