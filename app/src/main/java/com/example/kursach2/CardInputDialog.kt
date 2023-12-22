package com.example.kursach2

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialog
import java.util.regex.Pattern

class CardInputDialog(context: Context, private val onAddClickListener: (String, String, String) -> Unit) : AppCompatDialog(context) {

    private lateinit var cardNumberEditText: EditText
    private lateinit var cardHolderNameEditText: EditText
    private lateinit var expirationDateEditText: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_card)

        cardNumberEditText = findViewById(R.id.cardNumberEditText)!!
        cardNumberEditText.inputType = InputType.TYPE_CLASS_NUMBER
        cardNumberEditText.addTextChangedListener(CardNumberTextWatcher(cardNumberEditText))!!

        cardHolderNameEditText = findViewById(R.id.cardHolderNameEditText)!!
        cardHolderNameEditText.filters = arrayOf(LatinInputFilter())

        expirationDateEditText = findViewById(R.id.expirationDateEditText)!!
        expirationDateEditText.addTextChangedListener(ExpiryDateTextWatcher(expirationDateEditText))!!

        addButton = findViewById(R.id.addButton)!!

        addButton.setOnClickListener {
            val cardNumber = cardNumberEditText.text.toString()
            val cardHolderName = cardHolderNameEditText.text.toString()
            val expirationDate = expirationDateEditText.text.toString()

            if (cardNumber.length == 19 && expirationDate.length == 5 &&
                cardHolderName.isNotEmpty() && cardNumber.isNotEmpty() && expirationDate.isNotEmpty()) {
                onAddClickListener.invoke(cardNumber, cardHolderName, expirationDate)
                dismiss()
            }
        }
    }
}

// InputFilter для латинских символов
class LatinInputFilter : InputFilter {
    private val pattern = Pattern.compile("[a-zA-Z]*") // Регулярное выражение для латинских символов

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher = pattern.matcher(source)
        return if (!matcher.matches()) "" else null
    }
}
