package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Your holder should contain a member variable for any view that will be set as you render
        // a row
        val itemNameTextView : TextView
        val itemURLTextView : TextView
        val itemPriceTextView : TextView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each sub-view
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            itemNameTextView = itemView.findViewById(R.id.nameTv)
            itemURLTextView = itemView.findViewById(R.id.urlTv)
            itemPriceTextView = itemView.findViewById(R.id.priceTv)



            itemURLTextView.setOnClickListener{
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itemURLTextView.text.toString()))
                    ContextCompat.startActivity(it.context, browserIntent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(it.context, "Invalid URL for " + itemPriceTextView.text.toString(), Toast.LENGTH_LONG).show()
                }
            }

            itemView.setOnLongClickListener {
                deleteItem(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    fun deleteItem(index: Int) {
        (items as MutableList<Item>).removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Populate data into the item through the holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = items[position]
        // Set item views based on views and data model
        holder.itemNameTextView.text = item.itemName
        holder.itemURLTextView.text = item.itemURL
        holder.itemPriceTextView.text = item.itemPrice
    }
}