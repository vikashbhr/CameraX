package com.kaagaz.mycamera.view.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaagaz.mycamera.Constants
import com.kaagaz.mycamera.R
import com.kaagaz.mycamera.databinding.FragmentHomeBinding
import com.kaagaz.mycamera.model.AlbumModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val adapter: AlbumAdapter

    init {
        adapter = AlbumAdapter(onAlbumClickListener())
    }

    private fun onAlbumClickListener() = object : AlbumAdapter.OnAlbumClickListener {
        override fun onClick(albumModel: AlbumModel) {
            val bundle = Bundle()
            bundle.putInt("id", albumModel.id)
            findNavController().navigate(R.id.action_homeFragment_to_galleryFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allPermissionGranted()) {
            Toast.makeText(requireContext(), "We have permissions", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.xOpenCameraButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }

        binding.xRecyclerView.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.hasFixedSize()
            it.adapter = adapter
        }

        viewModel.albumList?.observe(viewLifecycleOwner) {
            adapter.setAlbum(it)
            Log.d("Images", it.toString())
        }

    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }


}