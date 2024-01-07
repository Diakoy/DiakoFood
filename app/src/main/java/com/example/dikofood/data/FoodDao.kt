package com.example.dikofood.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {
    @Insert
    fun addFood(food: FoodData)

    @Insert
    fun insertAllFoods(food: List<FoodData>)

    @Update
    fun updateFood(food: FoodData)

    @Delete
    fun deleteoneFood(foodData: FoodData)

    @Query("DELETE FROM food_table")
    fun deleteAllFoods()

    @Query("SELECT * FROM food_table")
    fun showAllFoods(): List<FoodData>

    @Query("SELECT * FROM food_table WHERE txt_title LIKE '%' || :search ||'%'")
    fun searchFood(search: String): List<FoodData>



}