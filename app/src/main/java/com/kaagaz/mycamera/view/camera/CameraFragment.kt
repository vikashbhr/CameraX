package com.kaagaz.mycamera.view.camera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaagaz.mycamera.Constants
import com.kaagaz.mycamera.R
import com.kaagaz.mycamera.databinding.FragmentCameraBinding
import com.kaagaz.mycamera.model.AlbumModel
import com.kaagaz.mycamera.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val viewModel: CameraViewModel by viewModels()
    private lateinit var binding: FragmentCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDir: File
    private lateinit var cameraExecutor: ExecutorService
    private var adapter: CameraImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outputDir = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        adapter = CameraImageAdapter()
        startCamera()
        binding.xTakePhoto.setOnClickListener { takePhoto() }
        binding.xAddToAlbum.setOnClickListener {
            if (viewModel.tempMutableImageList.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Click some photo to proceed", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            insertToAlbum()
        }

        binding.xImageList.also {
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            it.hasFixedSize()
            it.layoutManager = layoutManager
            it.adapter = adapter
        }
        viewModel.tempMutableImageList.observe(viewLifecycleOwner) { images ->
            adapter?.setImage(images)
        }

    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun startCamera() {
        val cameraProvider = ProcessCameraProvider.getInstance(requireContext())
        cameraProvider.addListener({
            val cameraP = cameraProvider.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.xPreviewView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraP.unbindAll()
                cameraP.bindToLifecycle(
                    requireActivity(), cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.d(Constants.TAG, "Start Camera failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val fileName = SimpleDateFormat(
            Constants.FILE_NAME_FORMAT,
            Locale.getDefault()
        ).format(System.currentTimeMillis()) + ".jpg"
        val photoFile = File(outputDir, fileName)
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUrl = Uri.fromFile(photoFile)
                    viewModel.saveImage(ImageModel(0, fileName, savedUrl.toString()))
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(Constants.TAG, "onError: $exception", exception)
                }
            })
    }

    private fun insertToAlbum() {
        val albumName = SimpleDateFormat(
            Constants.FILE_NAME_FORMAT,
            Locale.getDefault()
        ).format(System.currentTimeMillis())

        val date = SimpleDateFormat(
            Constants.DATE_FORMAT,
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        viewModel.saveAlbum(AlbumModel(0, albumName, date, viewModel.tempMutableImageList.value))
        findNavController().navigate(R.id.action_cameraFragment_to_homeFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}