package com.example.firstretrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.firstretrofit.databinding.CountryLayoutBinding
import com.example.firstretrofit.model.CountryModel

class RecyclerAdapter(
    private var click: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    private var countries = mutableListOf<CountryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding =
            CountryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = countries.size

    inner class MyViewHolder(private var binding: CountryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvName.text = countries[adapterPosition].name
            binding.tvNativeName.text = countries[adapterPosition].nativeName
            binding.tvRegion.text = countries[adapterPosition].region
            binding.tvSubRegion.text = countries[adapterPosition].subregion
            binding.tvLanguage.text =
                countries[adapterPosition].languages?.fold("") { acc, language ->
                    "$acc, ${language.name}"
                }?.drop(1)

            binding.ivFlag.setOnClickListener {
                click(countries[adapterPosition].flag!!)
            }

            val imageLoader = ImageLoader.Builder(binding.ivFlag.context)
                .componentRegistry {
                    add(SvgDecoder(binding.ivFlag.context))
                }
                .build()

            val request = ImageRequest.Builder(binding.ivFlag.context)
                .data(countries[adapterPosition].flag)
                .crossfade(true)
                .target(binding.ivFlag)
                .build()

            imageLoader.enqueue(request)
        }
    }

    fun setCountries(items:List<CountryModel>){
        countries.addAll(items)
        notifyDataSetChanged()
    }

    fun clearCountries(){
        countries.clear()
        notifyDataSetChanged()
    }
}