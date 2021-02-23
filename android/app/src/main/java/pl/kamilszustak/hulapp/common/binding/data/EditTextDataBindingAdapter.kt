package pl.kamilszustak.hulapp.common.binding.data

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import pl.kamilszustak.hulapp.R

object EditTextDataBindingAdapter {
    private const val DELAYED_TEXT_ATTRIBUTE: String = "delayedText"
    private const val DELAYED_TEXT_ATTRIBUTE_CHANGED: String = "delayedTextAttrChanged"

    private const val BEFORE_TEXT_CHANGED_ATTRIBUTE: String = "android:beforeTextChanged"
    private const val ON_TEXT_CHANGED_ATTRIBUTE: String = "android:onTextChanged"
    private const val AFTER_TEXT_CHANGED_ATTRIBUTE: String = "android:afterTextChanged"

    @BindingAdapter(DELAYED_TEXT_ATTRIBUTE)
    @JvmStatic
    fun EditText.setDelayedText(text: CharSequence?) {
        this.setText(text)
        this.text?.let {
            this.setSelection(it.length)
        }
    }

    @InverseBindingAdapter(
        attribute = DELAYED_TEXT_ATTRIBUTE,
        event = DELAYED_TEXT_ATTRIBUTE_CHANGED
    )
    @JvmStatic
    fun EditText.getDelayedText(): String =
        this.text.toString()

    @BindingAdapter(
        value = [BEFORE_TEXT_CHANGED_ATTRIBUTE, ON_TEXT_CHANGED_ATTRIBUTE, AFTER_TEXT_CHANGED_ATTRIBUTE, DELAYED_TEXT_ATTRIBUTE_CHANGED],
        requireAll = false
    )
    @JvmStatic
    fun EditText.setDelayedTextWatcher(
        beforeTextChanged: TextViewBindingAdapter.BeforeTextChanged?,
        onTextChanged: TextViewBindingAdapter.OnTextChanged?,
        afterTextChanged: TextViewBindingAdapter.AfterTextChanged?,
        inverseBindingListener: InverseBindingListener?
    ) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            inverseBindingListener?.onChange()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (beforeTextChanged != null) {
                    beforeTextChanged(s, start, count, after)
                }
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                handler.removeCallbacks(runnable)

                if (onTextChanged != null) {
                    onTextChanged(s, start, before, count)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                handler.postDelayed(runnable, 700)

                if (afterTextChanged != null) {
                    afterTextChanged(s)
                }
            }
        }

        val oldListener = ListenerUtil.trackListener(this, textWatcher, R.id.textWatcher)
        if (oldListener != null) {
            this.removeTextChangedListener(oldListener)
        }

        this.addTextChangedListener(textWatcher)
    }
}