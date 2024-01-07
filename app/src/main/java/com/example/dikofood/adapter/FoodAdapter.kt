package com.example.dikofood.adapter

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dikofood.data.FoodData
import com.example.dikofood.databinding.FoodItemViewBinding

lateinit var binding: FoodItemViewBinding

class FoodAdapter(val data: ArrayList<FoodData>, val foodEvent: FoodEvent) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bindView(foodData: FoodData) {
            Glide
                .with(itemView.context)
                .load(foodData.img_url)
                .into(binding.imageView)

            binding.txtTitle.text = foodData.txt_title
            binding.txtPrice.text = foodData.txt_price + " $"
            binding.txtLocation.text = foodData.txt_location
            binding.ratingNum.text = foodData.rating_number
            binding.ratingBar.rating = foodData.rating_bar
            itemView.setOnClickListener {
                foodEvent.onFoodClicked(data[adapterPosition], adapterPosition)
            }
            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)

                true
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        binding = FoodItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return FoodViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return data.count()

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.bindView(data[position])
    }

    fun addNewFood(newFood: FoodData) {
        data.add(0 , newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: FoodData, oldPosition: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(updatedFood: FoodData, position: Int) {
        data.set(position, updatedFood)
        notifyItemChanged(position)

    }

    fun setData(newList: ArrayList<FoodData>) {

        // set new data to list :
        data.clear()
        data.addAll(newList)

        notifyDataSetChanged()
    }

    interface FoodEvent {
        //create functions
        // create a val in classes arguments
        //call (fill) interface in adapter
        // implement interface in Activity

        fun onFoodClicked(updatedFood: FoodData, position: Int)
        fun onFoodLongClicked(food: FoodData, position: Int)

    }

}