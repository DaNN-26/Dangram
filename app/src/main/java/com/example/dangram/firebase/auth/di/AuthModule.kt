package com.example.dangram.firebase.auth.di

import com.example.dangram.firebase.auth.signIn.data.SignInRepositoryImpl
import com.example.dangram.firebase.auth.signUp.data.SignUpRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideFirebase() =
        FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideSignUpRepository(firebaseAuth: FirebaseAuth) =
        SignUpRepositoryImpl(firebaseAuth)

    @Singleton
    @Provides
    fun provideSignInRepository(firebaseAuth: FirebaseAuth) =
        SignInRepositoryImpl(firebaseAuth)
}
