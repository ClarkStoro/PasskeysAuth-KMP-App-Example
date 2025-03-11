package com.clarkstoro.passkeyauth_kmp_app_example

import android.app.Application
import com.clarkstoro.passkeyauth_kmp_app_example.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class PasskeyAuthApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initializeKoin {
            androidContext(this@PasskeyAuthApplication)
        }
    }
}