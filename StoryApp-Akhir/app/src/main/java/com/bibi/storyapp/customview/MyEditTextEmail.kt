package com.bibi.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bibi.storyapp.R

class MyEditTextEmail : AppCompatEditText {
    var isValid = false

    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = context.getString(R.string.email)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        compoundDrawablePadding = 10
    }

    private fun init() {
        val emailImage = ContextCompat.getDrawable(context, R.drawable.ic_email) as Drawable
        setButtonDrawables(startOfTheText = emailImage)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { validate() }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null) {
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    fun validate() {
        val email = text
        if (email.isNullOrEmpty()) {
            isValid = false
            error = context.getString(R.string.required_field)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            error = context.getString(R.string.invalid_email)
        } else {
            isValid = true
        }
    }
}