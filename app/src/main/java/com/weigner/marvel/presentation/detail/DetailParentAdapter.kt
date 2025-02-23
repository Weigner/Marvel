package com.weigner.marvel.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weigner.marvel.databinding.ItemParentDetailBinding
import com.weigner.marvel.framework.imageLoader.ImageLoader

class DetailParentAdapter(
    private val detailParentList: List<DetailParentVE>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<DetailParentAdapter.DetailParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailParentViewHolder {
        return DetailParentViewHolder.create(parent, imageLoader)
    }

    override fun getItemCount(): Int {
        return detailParentList.size
    }

    override fun onBindViewHolder(holder: DetailParentViewHolder, position: Int) {
        holder.bind(detailParentList[position])
    }

    class DetailParentViewHolder(
        itemBinding: ItemParentDetailBinding,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textItemCategory: TextView = itemBinding.textItemCategory
        private val recyclerChildDetail: RecyclerView = itemBinding.recyclerChildDetail
        fun bind(detailParentVE: DetailParentVE) {
            textItemCategory.text = itemView.context.getString(detailParentVE.categoryStringResId)
            recyclerChildDetail.run {
                setHasFixedSize(true)
                adapter = DetailChildAdapter(detailParentVE.detailChildList, imageLoader)
            }
        }

        companion object {
            fun create(parent: ViewGroup, imageLoader: ImageLoader): DetailParentViewHolder {
                val itemBinding = ItemParentDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return DetailParentViewHolder(itemBinding, imageLoader)
            }
        }
    }
}