package com.example.kursach2

import com.example.kursach2.AppDatabase
import com.example.kursach2.DiscountCard
import com.example.kursach2.DiscountCardDao
import android.app.Application
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiscountCardAdapter
    private lateinit var discountCardDao: DiscountCardDao
    private lateinit var discountCards: List<DiscountCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()

        discountCardDao = db.discountCardDao()

        recyclerView = findViewById(R.id.recyclerView)



        adapter = DiscountCardAdapter(emptyList(), this::onDeleteButtonClick)
        recyclerView.adapter = adapter

        val addCardButton: Button = findViewById(R.id.addCardButton)
        addCardButton.setOnClickListener {
            showAddCardDialog()
        }
        loadData()
    }

    private fun showAddCardDialog() {
        val dialog = CardInputDialog(this) { cardNumber, cardHolderName, expirationDate ->
            lifecycleScope.launch {
                val newCard = DiscountCard(cardNumber = cardNumber, cardHolderName =  cardHolderName, expirationDate =  expirationDate)
                discountCardDao.insertCard(newCard)

                withContext(Dispatchers.Main) {
                    loadData()
                }
            }
        }
        dialog.show()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val cards = withContext(Dispatchers.IO) {
                discountCardDao.getAllCards()
            }
            withContext(Dispatchers.Main) {
                adapter.setData(cards)
            }
        }
    }

    private fun onDeleteButtonClick(position: Int) {
        val cardToDelete = adapter.getItemAtPosition(position)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                discountCardDao.deleteCard(cardToDelete)
            }
            withContext(Dispatchers.Main) {
                adapter.removeItem(position, discountCardDao.getAllCards())
            }
        }
    }
}
