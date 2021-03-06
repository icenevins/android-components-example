package com.example.components.architecture.nvice.ui.user.details

import android.graphics.Color
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import com.example.components.architecture.nvice.BaseActivity
import com.example.components.architecture.nvice.R


class UserDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_user_details)
        if (savedInstanceState == null) {
            val userId = intent?.getIntExtra("userId", 0)
            initView(userId)
            iniTransition()
        }
    }

    private fun initView(userId: Int?) {
        supportFragmentManager.beginTransaction().replace(R.id.container, UserDetailsFragment.newInstance(userId)).commit()
    }

    private fun iniTransition() {
        val enterTransitionSet = TransitionSet()

        val fade = Fade()
                .excludeTarget(android.R.id.navigationBarBackground, true)
                .addTarget(android.R.id.statusBarBackground)
                .addTarget(R.id.vBackground)
                .addTarget(R.id.ivUserCover)
                .setDuration(150)


        val slide = Slide(Gravity.TOP)
                .excludeTarget(android.R.id.statusBarBackground, true)
                .excludeTarget(android.R.id.navigationBarBackground, true)
                .addTarget(R.id.appBar)

        val explode = Explode()
                .excludeTarget(android.R.id.statusBarBackground, true)
                .excludeTarget(android.R.id.navigationBarBackground, true)
                .excludeTarget(R.id.ivUserCover, true)
                .excludeTarget(R.id.vBackground, true)
                .excludeTarget(R.id.appBar, true)

        enterTransitionSet.addTransition(fade)
        enterTransitionSet.addTransition(slide)
        enterTransitionSet.addTransition(explode)

        window.enterTransition = enterTransitionSet
    }
}
