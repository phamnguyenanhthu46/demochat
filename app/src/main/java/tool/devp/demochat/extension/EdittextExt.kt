package tool.devp.demochat.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addOnTextChangedListener(l: (s: CharSequence, start: Int, end: Int, count: Int) -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            l(p0, p1, p2, p3)
        }
    }.apply {
        addTextChangedListener(this)
    }
}