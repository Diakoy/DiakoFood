package com.example.dikofood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikofood.adapter.FoodAdapter
import com.example.dikofood.data.FoodData
import com.example.dikofood.databinding.ActivityMainBinding
import com.example.dikofood.databinding.AddfoodDialogBinding
import com.example.dikofood.databinding.DialogRemoveFoodBinding
import com.example.dikofood.databinding.DialogUpdateFoodBinding
import java.util.Random

lateinit var binding: ActivityMainBinding
lateinit var foodList: ArrayList<FoodData>
lateinit var myadapter: FoodAdapter

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //first designed the ui and defined colors and shapes
        // 2 setting recycler view and designing the item view
        // 3 setting recyclerview adapter

        foodList = arrayListOf(
            FoodData(
                "Hamburger",
                "15",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                "20",
                4.5f
            ),
            FoodData(
                "Grilled fish",
                "20",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                "10",
                4f
            ),
            FoodData(
                "Lasania",
                "40",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                "30",
                2f
            ),
            FoodData(
                "pizza",
                "10",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                "80",
                1.5f
            ),
            FoodData(
                "Sushi",
                "20",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                "200",
                3f
            ),
            FoodData(
                "Roasted Fish",
                "40",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                "50",
                3.5f
            ),
            FoodData(
                "Fried chicken",
                "70",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                "70",
                2.5f
            ),
            FoodData(
                "Vegetable salad",
                "12",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                "40",
                4.5f
            ),
            FoodData(
                "Grilled chicken",
                "10",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                "15",
                5f
            ),
            FoodData(
                "Baryooni",
                "16",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                "28",
                4.5f
            ),
            FoodData(
                "Ghorme Sabzi",
                "11.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                "27",
                5f
            ),
            FoodData(
                "Rice",
                "12.5",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                "35",
                2.5f
            ),
        )
        myadapter =
            FoodAdapter(foodList.clone() as ArrayList<FoodData>, this)
        binding.recyclerMain.adapter = myadapter
        binding.recyclerMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        addfood()
        searchFunction()

    }

    private fun searchFunction() {
        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

                // filter data   'h'
                val cloneList = foodList.clone() as ArrayList<FoodData>
                val filteredList = cloneList.filter { foodItem ->
                    foodItem.txt_title.contains(editTextInput)
                }

                myadapter.setData(ArrayList(filteredList))


            } else {

                // show all data :
                myadapter.setData(foodList.clone() as ArrayList<FoodData>)

            }


        }


    }

    private fun addfood() {
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogbinding = AddfoodDialogBinding.inflate(layoutInflater)
            dialog.setView(dialogbinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogbinding.btnSubmit.setOnClickListener {

                if (dialogbinding.edtFoodLocation.length() > 0
                    && dialogbinding.edtFoodName.length() > 0
                    && dialogbinding.edtFoodPrice.length() > 0
                ) {

                    val food_name = dialogbinding.edtFoodName.text.toString()
                    val food_price = dialogbinding.edtFoodPrice.text.toString()
                    val location = dialogbinding.edtFoodLocation.text.toString()
                    val rating_number: Int = (1..150).random()
                    val min = 0F
                    val max = 5F
                    val rating_star: Float = min + Random().nextFloat() * (max - min)
                    val random_url = (0..1).random()
                    val url_pic = foodList[random_url].img_url
                    val new_food = FoodData(
                        food_name,
                        food_price,
                        location,
                        url_pic,
                        rating_number.toString(),
                        rating_star
                    )
                    myadapter.addNewFood(new_food)
                    dialog.dismiss()


                } else {
                    Toast.makeText(this, "Fill the boxes correctly", Toast.LENGTH_SHORT).show()
                }


            }


        }
    }

    override fun onFoodClicked(updatedFood: FoodData, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogbinding = DialogUpdateFoodBinding.inflate(layoutInflater)
        dialog.setView(dialogbinding.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogbinding.edtFoodLocation.setText(updatedFood.txt_location)
        dialogbinding.edtFoodName.setText(updatedFood.txt_title)
        dialogbinding.edtFoodPrice.setText(updatedFood.txt_price)

        dialogbinding.btnCancelUpdate.setOnClickListener { dialog.dismiss() }
        dialogbinding.btnSubmitUpdate.setOnClickListener {

            if (dialogbinding.edtFoodLocation.length() > 0
                && dialogbinding.edtFoodName.length() > 0
                && dialogbinding.edtFoodPrice.length() > 0
            ) {
                val food_name = dialogbinding.edtFoodName.text.toString()
                val food_price = dialogbinding.edtFoodPrice.text.toString()
                val location = dialogbinding.edtFoodLocation.text.toString()
                val rating_number: Int = (1..150).random()
                val min = 0F
                val max = 5F
                val rating_star: Float = min + Random().nextFloat() * (max - min)
                val random_url = (0..1).random()
                val url_pic = foodList[random_url].img_url
                val newfood_updated = FoodData(
                    food_name,
                    food_price,
                    location,
                    url_pic,
                    rating_number.toString(),
                    rating_star
                )

                myadapter.updateFood(newfood_updated, position)
            } else {
                Toast.makeText(this, "Enter Correctly", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onFoodLongClicked(food: FoodData, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogRemoveFoodBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogBinding.btnNo.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnYes.setOnClickListener {
            dialog.dismiss()
            myadapter.removeFood(food, position)
        }
    }


}