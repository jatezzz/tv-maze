package com.jatezzz.tvmaze.biometric

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.jatezzz.tvmaze.R

class BiometricAccessPopUp(private val activity: Activity) : CustomPopup(activity) {

  lateinit var acceptAction: () -> Unit

  lateinit var cancelAction: () -> Unit

  lateinit var doNotAskAgainAction: () -> Unit

  override fun setupPopup(view: View): PopupWindow {
    val inflater: LayoutInflater =
      activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val popupContainer = view.findViewById<ViewGroup>(R.id.popup_container)
    val popupView = inflater.inflate(R.layout.popup_biometric_access, popupContainer)
    val popupWindow = PopupWindow(
      popupView,
      LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT
    )
    popupWindow.isOutsideTouchable = false
    popupView.findViewById<Button>(R.id.accept_button).setOnClickListener {
      acceptAction()
    }
    popupView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
      cancelAction()
    }
    popupView.findViewById<Button>(R.id.do_not_ask_again_button).setOnClickListener {
      doNotAskAgainAction()
    }
    return popupWindow
  }

  override fun showDialog() = setupAndPlacePopupWindow()

}
