package com.mtndont.smartpokewalker.util

import android.content.Context

infix fun Context.getRawIdentifier(name: String): Int? {
    val id = this.resources.getIdentifier(
        name,
        "raw",
        this.packageName
    )
    if (id == 0) return null
    return id
}