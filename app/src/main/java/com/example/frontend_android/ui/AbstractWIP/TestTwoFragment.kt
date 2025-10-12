//package com.example.frontend_android.ui.AbstractWIP
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.example.frontend_android.R
//import com.google.android.material.button.MaterialButton
//import com.google.android.material.textview.MaterialTextView
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.launch
//import kotlin.getValue
//
//@AndroidEntryPoint
//class TestTwoFragment : Fragment(R.layout.test_one_two) {
//
//    private val vm: TestTwoViewModel by viewModels()
//
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val one  = view.findViewById<MaterialButton>(R.id.project_btn)
//        val two = view.findViewById<MaterialButton>(R.id.message_btn)
//
//        val get = view.findViewById<MaterialButton>(R.id.get)
//
//        val name_header = view.findViewById<MaterialTextView>(R.id.name_header)
//
//
//        name_header.text = "Fragment two"
//
//        one.setOnClickListener {
//            val action = TestTwoFragmentDirections.twoToOne()
//            findNavController().navigate(action)
//        }
//
//
//
//        get.setOnClickListener {
//            vm.getAll()
//        }
//
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            vm.datalist.collect { list ->
//                Log.d("Chatview", "Got projects: $list")
//            }
//        }
//
//    }
//}
