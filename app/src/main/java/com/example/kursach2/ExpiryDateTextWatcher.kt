package com.example.kursach2

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

class ExpiryDateTextWatcher(private val editText: EditText) : TextWatcher {

    private val slashIndex = 2
    private val maxLength = 5
    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val minMonth = 1
    private val maxMonth = 12
    private val minYear = 23
    private val maxYear = 99

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (s.length == slashIndex + 1 && s[s.length - 1] != '/') {
                editText.removeTextChangedListener(this)
                editText.setText(s.insert(slashIndex, "/"))
                editText.setSelection(editText.text.length)
                editText.addTextChangedListener(this)
            }

            if (s.length > maxLength) {
                editText.removeTextChangedListener(this)
                editText.setText(s.subSequence(0, maxLength))
                editText.setSelection(maxLength)
                editText.addTextChangedListener(this)
            }

            if (s.length == 5) {
                val monthString = s.substring(0, 2)
                val yearString = s.substring(3)

                val month = monthString.toIntOrNull()
                val year = yearString.toIntOrNull()

                if (month == null || year == null || month !in minMonth..maxMonth || year !in minYear..maxYear) {
                    // Если введенная дата некорректна, игнорируем изменения
                    editText.setText("")
                    return
                }
            }

            // Если введенные символы не цифры в нужных местах, игнорируем их
            if (!s.matches(Regex("^[0-9/]*$"))) {
                editText.setText(s.filter { it.isDigit() || it == '/' })
                editText.setSelection(editText.text.length)
            }
        }
    }
}

// Вспомогательное расширение для вставки символа в строку по заданной позиции
fun CharSequence.insert(index: Int, text: CharSequence): CharSequence {
    val builder = StringBuilder(this)
    builder.insert(index, text)
    return builder.toString()
}

