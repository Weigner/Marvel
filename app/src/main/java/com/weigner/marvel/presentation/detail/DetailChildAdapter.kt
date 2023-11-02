package com.weigner.marvel.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.weigner.marvel.R
import com.weigner.marvel.databinding.ItemChildDetailBinding
import com.weigner.marvel.framework.imageLoader.ImageLoader

class DetailChildAdapter(
    private val detailChildList: List<DetailChildVE>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<DetailChildAdapter.DetailChildViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailChildViewHolder {
        return DetailChildViewHolder.create(parent, imageLoader)
    }

    override fun getItemCount(): Int {
        return detailChildList.size
    }

    override fun onBindViewHolder(holder: DetailChildViewHolder, position: Int) {
        holder.bind(detailChildList[position])
    }

    class DetailChildViewHolder(
        itemBinding: ItemChildDetailBinding,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val imageCategory: ImageView = itemBinding.imageItemCategory
        fun bind(detailChildVE: DetailChildVE) {
            imageLoader.load(imageCategory, detailChildVE.imageUrl, R.drawable.ic_img_loading_error)
        }

        companion object {
            fun create(parent: ViewGroup, imageLoader: ImageLoader) :DetailChildViewHolder{
                val itemBinding = ItemChildDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return DetailChildViewHolder(itemBinding, imageLoader)
            }
        }
    }
}