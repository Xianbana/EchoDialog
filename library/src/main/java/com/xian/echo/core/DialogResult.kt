package com.xian.echo.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface DialogResult : Parcelable {
    @Parcelize
    object Positive : DialogResult
    
    @Parcelize
    object Negative : DialogResult
    
    @Parcelize
    object Cancelled : DialogResult
    
    @Parcelize
    data class Selection(val indices: List<Int>) : DialogResult
    
    @Parcelize
    data class Input(val text: String) : DialogResult
    
    @Parcelize
    data class LinkClick(val url: String, val text: String) : DialogResult
}


