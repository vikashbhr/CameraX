package com.kaagaz.mycamera.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaagaz.mycamera.databinding.ListItemAlbumBinding
import com.kaagaz.mycamera.databinding.ListItemCameraImageBinding
import com.kaagaz.mycamera.model.AlbumModel
import com.kaagaz.mycamera.model.ImageModel

class AlbumAdapter(private val onAlbumClickListener: OnAlbumClickListener) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private var albumList = emptyList<AlbumModel>()

    fun setAlbum(albumList: List<AlbumModel>) {
        this.albumList = albumList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        Glide.with(holder.itemView.context).load(album.photos?.get(0)?.imageUrl)
            .into(holder.binding.xThumbnail)
        holder.binding.xAlbumName.text = album.albumName
        holder.binding.xAlbumDate.text = album.date

        holder.binding.root.setOnClickListener {
            onAlbumClickListener.onClick(album)
        }

    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    interface OnAlbumClickListener{
        fun onClick(albumModel: AlbumModel)
    }
}