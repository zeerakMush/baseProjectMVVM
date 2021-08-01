package com.zk.base_project

import android.app.Application
import com.github.tamir7.contacts.Contacts
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application() {

    companion object{
        lateinit var context: Application
    }


    override fun onCreate() {
        super.onCreate()
        Contacts.initialize(this);
        context = this
    }

}