package com.example.cosmeticecommerceprogramming.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.cosmeticecommerceprogramming.Model.SliderModel
import com.example.cosmeticecommerceprogramming.R

class SliderAdapter(
    private var sliderItems: List<SliderModel>?,  //Slider öğelerinin tutulduğu liste null olarak tanımlandı
    private val viewPager2: ViewPager2            //Slider'ın gösterileceği ViewPager bileşeni

):RecyclerView.Adapter<SliderAdapter.SliderViewholder>(){
    private lateinit var context:Context  // Context nesnesi
    private val runnable = Runnable {     // ViewPager2 için otomatik geçiş işlevi
        sliderItems=sliderItems
        notifyDataSetChanged() //çağrısını yaparak slider öğelerinin güncellenmesini sağlar.
    }
    class SliderViewholder(itemView: View):RecyclerView.ViewHolder(itemView) {   // SliderViewHolder sınıfı, RecyclerView.ViewHolder sınıfından kalıtım alır
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage (sliderModel: SliderModel,context: Context){ //Slider öğesinin görüntüsünü ayarlayan yöntem
            Glide.with(context)
                .load(sliderModel.url) // yüklencek resmin Url'si
                .into(imageView) // Resmin yükleneceği ImageView bileşeni
        }

    }

    // onCreateViewHolder: Yeni bir SliderViewHolder oluşturur ve ilgili görünümü bağlar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapter.SliderViewholder {
        context= parent.context
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item_container,parent,false)
        return SliderViewholder(view)
    }

    // onBindViewHolder: Slider öğesini belirli bir pozisyonda bağlar
    override fun onBindViewHolder(holder: SliderAdapter.SliderViewholder, position: Int) {
      sliderItems?.get(position)?.let { sliderModel ->
            holder.setImage(sliderModel,context)
        }
        if (position == sliderItems?.lastIndex ?:0){
            viewPager2.post(runnable)
        }
    }

    // Son slider öğesine geldiğinde ViewPager2'nin otomatik geçişini sağlar
    override fun getItemCount(): Int {
        return sliderItems?.size ?: 0
    }
}