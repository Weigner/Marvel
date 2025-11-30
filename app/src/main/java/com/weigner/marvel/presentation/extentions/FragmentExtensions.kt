package com.weigner.marvel.presentation.extentions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(@StringRes textResId: Int) {
    Toast.makeText(requireContext(), textResId, Toast.LENGTH_SHORT).show()
}