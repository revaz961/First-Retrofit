package com.example.firstretrofit

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstretrofit.databinding.FragmentMainBinding
import com.example.firstretrofit.model.CountryModel
import com.example.firstretrofit.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private var adapter: RecyclerAdapter? = null
    private var countries = mutableListOf<CountryModel>()

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
        adapter = RecyclerAdapter(countries) {
            findNavController().navigate(
                R.id.action_mainFragment_to_flagFragment,
                bundleOf("uri" to countries[it].flag)
            )
        }
        binding!!.rcCountries.adapter = adapter
        binding!!.rcCountries.layoutManager = GridLayoutManager(requireContext(), 1)
        binding!!.btnDoownload.setOnClickListener {
            binding!!.btnDoownload.visibility = View.GONE
            binding!!.pbProgress.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                getCountries()
                CoroutineScope(Dispatchers.Main).launch {
                    binding!!.rcCountries.adapter!!.notifyDataSetChanged()
                    binding!!.pbProgress.visibility = View.GONE
                }
            }
        }
    }

    private suspend fun getCountries() {
        var result = RetrofitService.retrofitService().getCountry()
        if (result.isSuccessful) {
            countries.addAll(result.body()!!)
        } else {

        }
    }
}