package com.example.wishlist

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var items = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemURL = findViewById<EditText>(R.id.itemURL)
        val itemName = findViewById<EditText>(R.id.itemName)
        val itemPrice = findViewById<EditText>(R.id.itemPrice)
        val submitButton = findViewById<Button>(R.id.submit)

        val itemsRv = findViewById<RecyclerView>(R.id.itemRv)
        var adapter: ItemAdapter

        submitButton.setOnClickListener {
            this.currentFocus?.let { it1 -> this.hideKeyboard(it1) }
            if (itemURL.text.isEmpty() || itemName.text.isEmpty() || itemPrice.text.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields to add an item!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            (items as MutableList<Item>).add(Item(itemName.text.toString(), itemURL.text.toString(), itemPrice.text.toString()))
            adapter = ItemAdapter(items)
            itemsRv.adapter = adapter
            itemsRv.layoutManager = LinearLayoutManager(this)
            itemName.text.clear()
            itemURL.text.clear()
            itemPrice.text.clear()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}