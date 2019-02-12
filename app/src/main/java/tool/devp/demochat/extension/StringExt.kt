package tool.devp.demochat.extension

import android.util.Patterns

val String.isEmail: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()