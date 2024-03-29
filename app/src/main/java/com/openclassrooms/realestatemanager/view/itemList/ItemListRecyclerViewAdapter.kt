package com.openclassrooms.realestatemanager.view.itemList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailActivity
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailFragment


class ItemListRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                  private val values: List<RealEstateComplete>,
                                  private val twoPane: Boolean,
                                  private val inEuro: Boolean) :
        RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as RealEstateComplete
        if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ItemDetailFragment.ARG_ITEM_ID, item.realEstate.id)
                    putBoolean("edit", twoPane)
                }
            }
            parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
        } else {
            val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ITEM_ID, item.realEstate.id)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.image.setImageURI(Uri.parse(item.photos[0].uri))
        holder.type.text = item.realEstate.type
        holder.address.text = item.realEstate.address
        if(inEuro){
            "${Utils.convertDollarToEuro(item.realEstate.price)} €".also { holder.price.text = it }
        }else{
            "${item.realEstate.price} $".also { holder.price.text = it }
        }
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_iv)
        val type: TextView = view.findViewById(R.id.item_list_type_txt)
        val address: TextView = view.findViewById(R.id.item_list_address_txt)
        val price: TextView = view.findViewById(R.id.item_list_price_txt)
    }
}