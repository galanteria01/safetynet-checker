package com.shanu.safetynetchecker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SafetynetResultModel(
    val basicIntegrity: String,
    val evaluationType: String,
    val profileMatch: String
): Parcelable