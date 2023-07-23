package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.CategoriesAdapter
import com.example.newsapp.R
import com.example.newsapp.model.Category

class CategoriesFragment : Fragment() {
lateinit var categoriesRecyclerView: RecyclerView

val categories= listOf<Category>(
    Category(id="sports",title="Sports", backgroundColor = R.color.red,
    imageId = R.drawable.sports),
    Category(id="general",title="general", backgroundColor = R.color.blue,
        imageId = R.drawable.politics),
    Category(id="health",title= "health", backgroundColor = R.color.pink,
        imageId = R.drawable.health),
    Category(id="business",title="business", backgroundColor = R.color.brown_orange,
        imageId = R.drawable.bussines),
    Category(id="entertainment",title="entertainment", backgroundColor = R.color.baby_blue,
        imageId = R.drawable.environment
    ),
    Category(id="science",title="science", backgroundColor = R.color.yellow,
        imageId = R.drawable.science),

)
lateinit var adapter:CategoriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
 fun initViews(){
      categoriesRecyclerView=requireView().findViewById(R.id.categories_recycler_view)
     adapter= CategoriesAdapter(categories)
     adapter.onItemClicked=object:CategoriesAdapter.OnItemClicked{
         override fun onCategoryClicked(pos: Int, item: Category) {
             onCategoryClicked?.onCategory(item)
         }

     }

     categoriesRecyclerView.adapter=adapter
  }
    var onCategoryClicked:OnCategoryClicked?=null
    interface OnCategoryClicked{
        fun onCategory(category: Category)
    }
    }
