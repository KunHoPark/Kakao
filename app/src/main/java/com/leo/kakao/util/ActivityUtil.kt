package com.leo.kakao.util

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.leo.kakao.R
import com.leo.kakao.ui.main.MainActivity

/**
 * Activity간 화면 이동을 위해 사용 한다.
 * @author LeoPark
 **/
object ActivityUtil {

    fun startMainActivity(activity: Activity, fragment: Fragment?=null) {
        Intent(activity, MainActivity::class.java).run {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            fragment?.let {
                it.startActivity(this)
            } ?: activity.startActivity(this)
            activity.overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left
            )
        }
    }

}