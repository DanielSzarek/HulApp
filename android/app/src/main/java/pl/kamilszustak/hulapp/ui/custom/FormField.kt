package pl.kamilszustak.hulapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_form_field.view.*
import pl.kamilszustak.hulapp.R

class FormField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_form_field, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FormField,
            0,
            0
        ).apply {
            try {
                inputLayout.apply {
                    this.startIconDrawable = getDrawable(R.styleable.FormField_startIconDrawable)
                    this.isErrorEnabled = getBoolean(R.styleable.FormField_errorEnabled, false)
                    this.error = getString(R.styleable.FormField_errorText)
                }

                editText.apply {
                    this.inputType = getInt(R.styleable.FormField_inputType, 0)
                    this.maxLines = getInt(R.styleable.FormField_maxLines, 1)
                    this.setText(getString(R.styleable.FormField_text))
                    this.hint = getString(R.styleable.FormField_hint)
                }
            } finally {
                recycle()
            }
        }
    }
}