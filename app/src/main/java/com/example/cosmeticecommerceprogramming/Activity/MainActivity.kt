package com.example.cosmeticecommerceprogramming.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.cosmeticecommerceprogramming.Adapter.CategoryAdapter
import com.example.cosmeticecommerceprogramming.Adapter.RecommendationAdapter
import com.example.cosmeticecommerceprogramming.Adapter.SliderAdapter
import com.example.cosmeticecommerceprogramming.Model.SliderModel
import com.example.cosmeticecommerceprogramming.ViewModel.MainViewModel
import com.example.cosmeticecommerceprogramming.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewModel=MainViewModel() // ViewModel nesnesi oluşturuluyor
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initBanners() // Banner/slider bileşenini başlatan fonksiyon
        initCategory()  // Kategori bileşenini başlatan fonksiyon
        initRecommended() // Recommended bileşenini başlatan fonksiyon
        initBottomMenu()


    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity,CartActivity::class.java))
        }
    }

    private fun initBanners() {
       binding.progressBarBanner.visibility = View.VISIBLE    // Banner yüklenirken progress bar'ı göster

        viewModel.banner.observe(this, Observer {
            banners(it)  // LiveData'dan gelen bannner listesini işleyen fonksiyon
            binding.progressBarBanner.visibility=View.GONE  // Banner yükleme tamamlandığında progress bar'ı gizle
        })
        viewModel.loadBanner()  //Banner verilerini yükleme işlemi
    }

    private fun banners(it: List<SliderModel>?) {
        // SliderAdapter kullanarak ViewPager2'ye slider öğelerini bağla
        binding.viewPagerSlider.adapter = SliderAdapter(it,binding.viewPagerSlider)

        // ViewPager2 için görüntüleme ayarları
        binding.viewPagerSlider.clipToPadding=false
        binding.viewPagerSlider.clipChildren=false
        binding.viewPagerSlider.offscreenPageLimit=3
        binding.viewPagerSlider.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        // ViewPager2 için kenar boşlukları ve dönüşüm efektleri ayarlanıyor
        val compositePageTransformer=CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPagerSlider.setPageTransformer(compositePageTransformer)
        // Slider öğesi varsa nokta göstergesi ve görünürlüğü ayarla
        if (it != null) {
            if (it.size>1){
                binding.springDotsIndicator.visibility=View.VISIBLE
                binding.springDotsIndicator.attachTo(binding.viewPagerSlider)
            }
        }
    }

    private fun initCategory(){
        binding.progressBarCategory.visibility =View.VISIBLE     // Kategori yüklenirken progress bar'ı göster
        viewModel.category.observe(this, Observer {
            // Kategori RecyclerView'yi düzenleme ve adapter ile bağlama işlemleri
            binding.viewCatogory.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.viewCatogory.adapter=CategoryAdapter(it)
            binding.progressBarCategory.visibility=View.GONE  // Kategori yükleme tamamlandığında progress bar'ı gizle
        })
        viewModel.loadCategory() // Kategori verilerini yükleme işlemi
    }


    private fun initRecommended() {
        binding.progressBarRecommendation.visibility=View.VISIBLE

        viewModel.recommend.observe(this, Observer {
            binding.viewRecommendation.layoutManager = GridLayoutManager(this@MainActivity,2)
            binding.viewRecommendation.adapter=RecommendationAdapter(it)
            binding.progressBarRecommendation.visibility=View.GONE
    })
        viewModel.loadRecommended()
}

}