package com.openclassrooms.realestatemanager.view.itemCreation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo

class ItemListCreationRecyclerViewAdapter(
        private val listPhoto: List<Photo>,
        private val onDeletePhoto: (photo: Photo) -> Unit,
        private val onRenamePhoto: (photo: Photo) -> Unit,
) :
        RecyclerView.Adapter<ItemListCreationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo_edit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listPhoto[position]
        holder.bind(item, onDeletePhoto, onRenamePhoto)
    }

    override fun getItemCount() =  listPhoto.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var image: ImageView = view.findViewById(R.id.photo_edit_image_iv)
        private val buttonEdit: ImageView = view.findViewById(R.id.photo_edit_name_iv)
        private val buttonDelete: ImageView = view.findViewById(R.id.photo_edit_delete_iv)
        private var description: TextView = view.findViewById(R.id.photo_edit_description_txt)
        fun bind(photo: Photo, onDeletePhoto: (photo: Photo) -> Unit, onDescriptionPhoto: (photo: Photo) -> Unit){
            image.setImageURI(Uri.parse(photo.uri))
            description.text = photo.descriptionPhoto
            buttonEdit.setOnClickListener {
                onDescriptionPhoto(photo)
            }
            buttonDelete.setOnClickListener {
                onDeletePhoto(photo)
            }
        }
    }
}
