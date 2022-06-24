package com.kaagaz.mycamera.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kaagaz.mycamera.Constants
import com.kaagaz.mycamera.databinding.FragmentGalleryBinding
import com.kaagaz.mycamera.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding
    private val adapter: GalleryImageAdapter = GalleryImageAdapter(onImageClickListener())

    private fun onImageClickListener() = object : GalleryImageAdapter.OnGalleryImageClickListener {
        override fun onClick(imageModel: ImageModel) {
            ImagePreviewDialogFragment(imageModel).show(
                parentFragmentManager,
                Constants.TAG
            )
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.albumId = arguments?.getInt("id")
        binding.xRecyclerView.also {
            val layoutManager = GridLayoutManager(requireContext(), 3)
            it.layoutManager = layoutManager
            it.hasFixedSize()
            it.adapter = adapter
        }

        viewModel.getAlbum(viewModel.albumId ?: 0)?.observe(viewLifecycleOwner) { album ->
            album?.photos?.let { adapter.setImage(it) }
        }
    }

}