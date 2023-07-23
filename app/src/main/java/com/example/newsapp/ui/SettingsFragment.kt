package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import kotlinx.coroutines.NonDisposableHandle.parent


class SettingsFragment : Fragment() {
    var items = arrayOf("English", "Arabic")
    lateinit var auto_complete_txt: AutoCompleteTextView
    lateinit var adapterItems: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
auto_complete_txt=requireView().findViewById(R.id.auto_complete_txt)
        adapterItems =ArrayAdapter<String>(requireContext(), R.layout.list_item, items)
        auto_complete_txt.setAdapter(adapterItems)
        auto_complete_txt.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val item: String = adapterView.getItemAtPosition(i).toString()
                Toast.makeText(requireContext().applicationContext,"Item: "+ item,Toast.LENGTH_SHORT).show()
            }

        }
    }
