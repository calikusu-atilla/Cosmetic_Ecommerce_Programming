package com.example.cosmeticecommerceprogramming.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cosmeticecommerceprogramming.Model.CategoryModel
import com.example.cosmeticecommerceprogramming.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.jvm.internal.Ref

class MainViewModel():ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance() // Firebese veritabanı oluşturuldu

    private val _banner = MutableLiveData<List<SliderModel>>()   //banner verileri tutulmak için mutablelivedata oluşturuldu -> mutablelivedata: gözlemlenerek veri değişikliklerine tepki veri
    private val _category = MutableLiveData<MutableList<CategoryModel>> ()

    val banner : MutableLiveData<List<SliderModel>> = _banner
    val category : LiveData<MutableList<CategoryModel>> = _category

    fun loadCategory() {
        val Ref = firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (child in snapshot.children){
                    val data = child.getValue(CategoryModel::class.java)
                    if (data!=null) {
                        list.add(data)
                    }
                }
                _category.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        )
    }

    fun loadBanner() { // Banner verilerini yükleyen bir fonksiyon oluşturuldu

        val Ref = firebaseDatabase.getReference("Banner") //FireBasedeki Banner referensına ulaşılıyor
        Ref.addValueEventListener(object : ValueEventListener{ //firebase verilerinde değişik olduğunda veri çekmek için dinleyici oluşuruldu.

            override fun onDataChange(snapshot: DataSnapshot) { //veri başarılırsa çağırılan fonksiyon

                val list = mutableListOf<SliderModel>()  // SliderModel nesnelerini tutmak için boş bir liste oluşturuldu
                for (child in snapshot.children){  // her bir nesne tek tek gezilir
                    val data = child.getValue(SliderModel::class.java) // her nesneyi tek tek SliderModel nesnesine dönüştürür

                    if (data!=null) {  // eğer liste boş değilse listeye eklenir
                        list.add(data!!)
                    }
                }
                _banner.value = list // _banner listesi yeni liste ile güncellenir
            }

            override fun onCancelled(error: DatabaseError) { // veri çekilirken hata oluşursa çağrılır ve hata durumu log.e kaydolur

            }

        })
    }


}