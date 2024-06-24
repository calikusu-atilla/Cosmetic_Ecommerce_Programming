package com.example.cosmeticecommerceprogramming.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cosmeticecommerceprogramming.Model.ItemsModel
import com.example.cosmeticecommerceprogramming.databinding.ViewholderRecommendBinding

class RecommendationAdapter(val items:MutableList<ItemsModel>): RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    private var context:Context?=null
    class ViewHolder(val binding: ViewholderRecommendBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val binding=ViewholderRecommendBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleTxt.text=items[position].title
        holder.binding.priceTxt.text="$"+items[position].price.toString()
        holder.binding.ratingTxt.text=items[position].rating.toString()

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(RequestOptions.centerCropTransform())
            .into(holder.binding.pic)
    }
}