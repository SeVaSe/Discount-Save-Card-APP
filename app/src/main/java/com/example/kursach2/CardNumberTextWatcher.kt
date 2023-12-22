package com.example.kursach2

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CardNumberTextWatcher(private val editText: EditText) : TextWatcher {

    private val spaceInterval = 4 // Интервал для пробелов
    private val maxCardLength = 19 // Максимальная длина карты вместе с пробелами

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            val originalText = s.toString().replace(" ", "")
            val formattedText = buildString {
                originalText.chunked(spaceInterval).forEachIndexed { index, chunk ->
                    append(chunk)
                    if (index != originalText.chunked(spaceInterval).size - 1) {
                        append(" ")
                    }
                }
            }

            if (formattedText != s.toString()) {
                editText.removeTextChangedListener(this)
                editText.setText(formattedText)
                editText.setSelection(formattedText.length)
                editText.addTextChangedListener(this)
            }

            // Убедимся, что количество символов не превышает максимальной длины
            if (s.length >= maxCardLength) {
                editText.removeTextChangedListener(this)
                editText.setText(s.subSequence(0, maxCardLength))
                editText.setSelection(maxCardLength)
                editText.addTextChangedListener(this)
            }
        }
    }
}

