package com.openclassrooms.realestatemanager.view.itemDetail

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo

class ItemDetailRecyclerViewAdapter(private var listPhoto: List<Photo>) : RecyclerView.Adapter<ItemDetailRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemDetailRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = listPhoto[position]
        holder.bind(item)
    }

    override fun getItemCount() = listPhoto.size

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view.rootView) {
        private var image: ImageView = view.findViewById(R.id.photo_image_iv)
        private var description: TextView = view.findViewById(R.id.photo_description_txt)
        fun bind(photo : Photo){
            image.setImageURI(Uri.parse(photo.uri))
            description.text = photo.descriptionPhoto
        }
    }

}
