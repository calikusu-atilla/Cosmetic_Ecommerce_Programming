package com.example.cosmeticecommerceprogramming.Model

import android.os.Parcel
import android.os.Parcelable

data class ItemsModel(
    var title: String = "",                               //ürün adı
    var description: String = "",                         //Ürün açıklaması
    var picUrl:ArrayList<String> = ArrayList(),           //Ürüne ait resimlerin URL'lerinin bir listesi
    var size:ArrayList<String> = ArrayList(),             //Ürünün boyutlarının bir listesi
    var price: Double = 0.0,                              //Ürünün fiyatı
    var rating: Double = 0.0,                             //Ürün puanı
    var numberInCart: Int = 0,

    ):Parcelable {    //Parcelable, verileri bir Intent ile diğer bir Activity'ye taşımak için kullanılan bir arayüzdür.
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readDouble(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(picUrl)
        parcel.writeStringList(size)
        parcel.writeDouble(price)
        parcel.writeDouble(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}
