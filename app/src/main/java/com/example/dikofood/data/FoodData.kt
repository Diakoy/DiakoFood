package com.example.dikofood.data

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("food_table")

data class FoodData(
    @PrimaryKey(true)
    val id: Int? = null,
    val txt_title: String,
    val txt_price: String,
    val txt_location: String,
    val img_url: String,
    val rating_number: String,
    val rating_bar: Float

)
