package com.m2f.sheddtest.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m2f.sheddtest.presentation.main.BaseActivity
import com.m2f.sheddtest.presentation.main.SheddApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection


/*
* this extension function basically listens for activity/fragment creation and injects depenedencies with AndroidInjector*/
internal fun SheddApplication.initInjection() {

    DaggerApplicationComponent.builder()
            .context(this.applicationContext)
            .build()
            .inject(this)
    this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            if (activity is BaseActivity) {
                AndroidInjection.inject(activity)
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentCreated(fm: FragmentManager?, f: Fragment, savedInstanceState: Bundle?) {
                                if (f is Injectable) {
                                    AndroidSupportInjection.inject(f)
                                }
                            }
                        }, true)
            }
        }

    })
}