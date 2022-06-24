package com.kaagaz.mycamera.view.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaagaz.mycamera.databinding.ListItemCameraImageBinding
import com.kaagaz.mycamera.databinding.ListItemGalaryImageBinding
import com.kaagaz.mycamera.model.ImageModel

class GalleryImageAdapter(private val onGalleryImageClickListener: OnGalleryImageClickListener) :
    RecyclerView.Adapter<GalleryImageAdapter.ViewHolder>() {

    private var imageList = emptyList<ImageModel>()

    fun setImage(imageList: List<ImageModel>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemGalaryImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemGalaryImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageList[position].imageUrl)
            .into(holder.binding.xImage)

        holder.binding.xImage.setOnClickListener {
            onGalleryImageClickListener.onClick(imageList[position])
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    interface OnGalleryImageClickListener{
        fun onClick(imageModel: ImageModel)
    }
}