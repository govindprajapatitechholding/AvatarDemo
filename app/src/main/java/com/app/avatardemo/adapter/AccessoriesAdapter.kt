package com.app.avatardemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.avatardemo.R
import com.app.avatardemo.model.AccessoriesItem
import com.app.avatardemo.model.CategoryItem

class AccessoriesAdapter(var mList: ArrayList<AccessoriesItem>,val listener: OnAccessoriesClickListener) :
    RecyclerView.Adapter<AccessoriesAdapter.ViewHolder>() {

    var mSelectedItem: AccessoriesItem? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_accessories, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(item.accessoriesResource)
        if (mSelectedItem?.equals(item) == true) {
            holder.imageView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.imageView.context,
                    R.color.grey
                )
            )
        } else {
            holder.imageView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.imageView.context,
                    R.color.white
                )
            )
        }
        holder.imageView.setOnClickListener {
            listener.onAccessoriesItemClick(item, position)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(newList: ArrayList<AccessoriesItem>) {
        mList = newList
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    interface OnAccessoriesClickListener {
        fun onAccessoriesItemClick(item: AccessoriesItem, position: Int)
    }
}