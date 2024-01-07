package com.example.dikofood.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikofood.adapter.FoodAdapter
import com.example.dikofood.data.FoodDao
import com.example.dikofood.data.FoodData
import com.example.dikofood.data.MyDatabase
import com.example.dikofood.databinding.ActivityMainBinding
import com.example.dikofood.databinding.AddfoodDialogBinding
import com.example.dikofood.databinding.DialogRemoveAllFoodBinding
import com.example.dikofood.databinding.DialogRemoveFoodBinding
import com.example.dikofood.databinding.DialogUpdateFoodBinding
import java.util.Random

const val BASE_URL_IMG = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {
    lateinit var binding: ActivityMainBinding
    lateinit var foodList: ArrayList<FoodData>
    lateinit var myadapter: FoodAdapter
    lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = MyDatabase.getDatabase(this).foodDao
        val sharedprefrences = getSharedPreferences("DikoFood", Context.MODE_PRIVATE)
        if (sharedprefrences.getBoolean("first_run", true)) {
            firstRun()
            sharedprefrences.edit().putBoolean("first_run", false).apply()
        }
        showData()
        clearAllFoods()
        addfood()
        searchFunction()


    }

    private fun clearAllFoods() {
        binding.btnClearAll.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogRemoveAllFoodBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogBinding.btnNo.setOnClickListener { dialog.dismiss() }
            dialogBinding.btnYes.setOnClickListener {
                dialog.dismiss()
                foodDao.deleteAllFoods()
                showData()
            }


        }
    }

    private fun showData() {
        val foodData = foodDao.showAllFoods()
        myadapter =
            FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myadapter
        binding.recyclerMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun firstRun() {
        foodList = arrayListOf(
            FoodData(
                txt_title = "Hamburger",
                txt_price = "15",
                txt_location = "Isfahan, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                rating_number = "20",
                rating_bar = 4.5f
            ),
            FoodData(
                txt_title = "Grilled fish",
                txt_price = "20",
                txt_location = " Tehran, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                rating_number = "10",
                rating_bar = 4f
            ),
            FoodData(
                txt_title = "Lasania",
                txt_price = "40",
                txt_location = "Isfahan, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                rating_number = "30",
                rating_bar = 2f
            ),
            FoodData(
                txt_title = "pizza",
                txt_price = "10",
                txt_location = "Zahedan, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                rating_number = "80",
                rating_bar = 1.5f
            ),
            FoodData(
                txt_title = "Sushi",
                txt_price = "20",
                txt_location = "Mashhad, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                rating_number = "200",
                rating_bar = 3f
            ),
            FoodData(
                txt_title = "Roasted Fish",
                txt_price = "40",
                txt_location = "Jolfa, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                rating_number = "50",
                rating_bar = 3.5f
            ),
            FoodData(
                txt_title = "Fried chicken",
                txt_price = "70",
                txt_location = "NewYork, USA",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                rating_number = "70",
                rating_bar = 2.5f
            ),
            FoodData(
                txt_title = "Vegetable salad",
                txt_price = "12",
                txt_location = "Berlin, Germany",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                rating_number = "40",
                rating_bar = 4.5f
            ),
            FoodData(
                txt_title = "Grilled chicken",
                txt_price = "10",
                txt_location = "Beijing, China",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                rating_number = "15",
                rating_bar = 5f
            ),
            FoodData(
                txt_title = "Baryooni",
                txt_price = "16",
                txt_location = "Ilam, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                rating_number = "28",
                rating_bar = 4.5f
            ),
            FoodData(
                txt_title = "Ghorme Sabzi",
                txt_price = "11.5",
                txt_location = "Karaj, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                rating_number = "27",
                rating_bar = 5f
            ),
            FoodData(
                txt_title = "Rice",
                txt_price = "12.5",
                txt_location = "Shiraz, Iran",
                img_url = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                rating_number = "35",
                rating_bar = 2.5f
            ),
        )
        foodDao.insertAllFoods(foodList)
    }

    private fun searchFunction() {
        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

                // filter data   'h'
                val searchedData = foodDao.searchFood(editTextInput.toString())
                myadapter.setData(ArrayList(searchedData))


            }else{
                val data = foodDao.showAllFoods()
                myadapter.setData(ArrayList(data))



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
                    val random_url = (1..12).random()

                    val url_pic = BASE_URL_IMG + random_url.toString() + ".jpg"
                    val new_food = FoodData(
                        txt_title = food_name,
                        txt_price = food_price,
                        txt_location = location,
                        img_url = url_pic,
                        rating_number = rating_number.toString(),
                        rating_bar = rating_star
                    )
                    myadapter.addNewFood(new_food)
                    foodDao.addFood(new_food)
                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)


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
                val url_pic = BASE_URL_IMG + random_url.toString() + ".jpg"
                val newfood_updated = FoodData(
                    id = updatedFood.id,
                    txt_title = food_name,
                    txt_price = food_price,
                    txt_location = location,
                    img_url = url_pic,
                    rating_number = rating_number.toString(),
                    rating_bar = rating_star
                )

                myadapter.updateFood(newfood_updated, position)
                foodDao.updateFood(newfood_updated)
                dialog.dismiss()
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
            foodDao.deleteoneFood(food)
        }
    }


}