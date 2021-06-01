package com.example.firstretrofit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstretrofit.databinding.FragmentMainBinding
import com.example.firstretrofit.model.MainViewModel


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var adapter: RecyclerAdapter? = null
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentMainBinding.inflate(inflater, container, false)
            init()
        }
        return binding!!.root
    }

    private fun init() {
        mainViewModel.init()
        observes()

        binding!!.refresh.setOnRefreshListener {
            adapter!!.clearCountries()
            mainViewModel.init()
        }

        adapter = RecyclerAdapter {
            findNavController().navigate(
                R.id.action_mainFragment_to_flagFragment,
                bundleOf("uri" to it)
            )
        }
        binding!!.rcCountries.adapter = adapter
        binding!!.rcCountries.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    private fun observes(){
        mainViewModel._loadingLiveData.observe(viewLifecycleOwner,{
            binding!!.refresh.isRefreshing = it
        })

        mainViewModel._countriesLiveData.observe(viewLifecycleOwner,{
            adapter!!.setCountries(it)
        })
    }
}