package com.kaagaz.mycamera.view.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.kaagaz.mycamera.R
import com.kaagaz.mycamera.databinding.FragmentImagePreviewDialogBinding
import com.kaagaz.mycamera.model.ImageModel

class ImagePreviewDialogFragment(private val imageModel: ImageModel) : DialogFragment() {
    private lateinit var binding: FragmentImagePreviewDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePreviewDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.xClose.setOnClickListener { dismiss() }
        binding.xImageName.text = imageModel.imageName
        Glide.with(requireContext()).load(imageModel.imageUrl).into(binding.xImage)
    }
}