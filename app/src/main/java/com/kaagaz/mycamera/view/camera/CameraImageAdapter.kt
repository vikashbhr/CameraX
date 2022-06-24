package com.kaagaz.mycamera.view.camera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaagaz.mycamera.databinding.ListItemCameraImageBinding
import com.kaagaz.mycamera.model.ImageModel

class CameraImageAdapter() :
    RecyclerView.Adapter<CameraImageAdapter.ViewHolder>() {

    private var imageList = emptyList<ImageModel>()

    fun setImage(imageList: List<ImageModel>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemCameraImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemCameraImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageList[position].imageUrl)
            .into(holder.binding.xImage)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}