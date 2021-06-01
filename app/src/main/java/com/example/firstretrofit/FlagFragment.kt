package com.example.firstretrofit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.firstretrofit.databinding.FragmentFlagBinding

class FlagFragment : Fragment() {

    private var binding: FragmentFlagBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentFlagBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    private fun init(){
        binding!!.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        var uri = arguments?.get("uri") as String
        val imageLoader = ImageLoader.Builder(requireContext())
            .componentRegistry {
                add(SvgDecoder(requireContext()))
            }
            .build()

        val request = ImageRequest.Builder(requireContext())
            .data(uri)
            .crossfade(true)
            .target(binding!!.ivFlag)
            .build()

        imageLoader.enqueue(request)
    }
}