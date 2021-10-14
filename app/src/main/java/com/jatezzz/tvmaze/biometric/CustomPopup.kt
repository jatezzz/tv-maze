package com.jatezzz.tvmaze.biometric

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

const val DEFAULT_POPUP_ELEVATION = 10.0F
const val DEFAULT_DIM_AMOUNT = 0.4f

abstract class CustomPopup(private val activity: Activity) {

    private lateinit var popupWindow: PopupWindow

    open fun showDialog() {
        setupAndPlacePopupWindow()
        setupWindowFlags(popupWindow)
    }

    @SuppressLint("ClickableViewAccessibility")
    protected fun setupAndPlacePopupWindow() {
        val rootView = activity.window.decorView
        popupWindow = setupPopup(rootView)
        popupWindow.isOutsideTouchable = false
        popupWindow.setTouchInterceptor { view, motionEvent ->
            if (motionEvent.x < 0 || motionEvent.x > view.width) {
                true
            } else motionEvent.y < 0 || motionEvent.y > view.height
        }
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0)
    }

    abstract fun setupPopup(view: View): PopupWindow

    private fun setupWindowFlags(popupWindow: PopupWindow) {
        val container: View = if (popupWindow.contentView.parent is View) {
            popupWindow.contentView.parent as View
        } else {
            popupWindow.contentView
        }
        val windowManager = activity.windowManager
        val layoutParams = container.layoutParams as WindowManager.LayoutParams
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = DEFAULT_DIM_AMOUNT
        windowManager.updateViewLayout(container, layoutParams)
    }

    fun isShowing() = popupWindow.isShowing

    fun dismiss() = popupWindow.dismiss()

}
