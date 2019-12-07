package pl.kamilszustak.hulapp.common.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import timber.log.Timber

object DataBindingAdapter {

    @JvmStatic
    @InverseBindingAdapter(
        attribute = "text",
        event = "textAttrChanged"
    )
    fun EditText.getText(): String {
        return this.text.toString()
    }

    @JvmStatic
    @BindingAdapter("text")
    fun EditText.setText(text: CharSequence?) {
        Timber.i(text.toString())
        this.setText(text)
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun EditText.setTextChangedListener(listener: InverseBindingListener) {
//        this.textChangedListener {
//            this.afterTextChanged {
//                listener.onChange()
//            }
//        }

        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
                Timber.i("on changed")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}