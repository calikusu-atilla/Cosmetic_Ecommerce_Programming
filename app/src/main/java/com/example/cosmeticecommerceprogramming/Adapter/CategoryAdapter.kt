package com.example.cosmeticecommerceprogramming.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cosmeticecommerceprogramming.Model.CategoryModel
import com.example.cosmeticecommerceprogramming.databinding.ViewholderCategoryBinding
import com.google.firebase.database.core.Context

class CategoryAdapter(val items:MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.Viewholder>() {
    private lateinit var context: android.content.Context
    inner class Viewholder( val binding: ViewholderCategoryBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, position: Int) {
        val items = items[position]
        holder.binding.titleTxt.text=items.title

        Glide.with(holder.itemView.context)
            .load(items.picUrl)
            .into(holder.binding.categoryPic)
    }

    override fun getItemCount(): Int = items.size
    }
