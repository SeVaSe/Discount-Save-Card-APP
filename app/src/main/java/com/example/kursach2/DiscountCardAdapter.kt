package com.example.kursach2

import kotlin.random.Random
import android.graphics.Color
import com.example.kursach2.DiscountCard
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class DiscountCardAdapter(
    private var cards: List<DiscountCard>,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<DiscountCardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val cardHolderNameTextView: TextView = itemView.findViewById(R.id.cardHolderNameTextView)
        val expirationDateTextView: TextView = itemView.findViewById(R.id.expirationDateTextView)
        val bankNameTextView: TextView = itemView.findViewById(R.id.bankNameTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_discount_card, parent, false)
        return CardViewHolder(view)
    }

// Остальной ваш код

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]

        val initialColors = intArrayOf(
            generateGradientForCard(card.cardNumber),
            generateGradientForCard(card.cardHolderName),
            generateGradientForCard(card.expirationDate),
             // Используйте поле bankName
        )

        val shape = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, initialColors)
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = 70f // Радиус закругления углов

        val hoverColor = initialColors[0] // Цвет для наведения (первый цвет градиента)

        holder.itemView.background = shape // Устанавливаем исходный фон

        val defaultCornerRadius = shape.cornerRadius // Сохраняем радиус закругления

        var isTouching = false // Флаг для отслеживания начала касания

        holder.itemView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isTouching = true
                    shape.colors = intArrayOf(hoverColor, hoverColor, hoverColor)
                    shape.cornerRadius = defaultCornerRadius
                    view.background = shape
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isTouching = false // Устанавливаем isTouching в false при завершении касания
                    shape.colors = initialColors
                    view.background = shape
                }
            }
            true
        }

        // Остальной код
        holder.cardNumberTextView.text = "${card.cardNumber}"
        holder.cardHolderNameTextView.text = "${card.cardHolderName}"
        holder.expirationDateTextView.text = "${card.expirationDate}"

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener.invoke(position)
        }
    }

    // Функция для генерации случайного градиента цвета на основе уникального идентификатора
    private fun generateGradientForCard(cardIdentifier: String): Int {
        val color = (cardIdentifier.hashCode() and 0x00FFFFFF) or 0xFF000000.toInt() // Генерация цвета
        return color
    }




    override fun getItemCount(): Int {
        return cards.size
    }

    fun setData(newCards: List<DiscountCard>) {
        cards = newCards
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): DiscountCard {
        return cards[position]
    }

    fun removeItem(position: Int, newCards: List<DiscountCard>) {
        cards = newCards
        notifyItemRemoved(position)
    }
}