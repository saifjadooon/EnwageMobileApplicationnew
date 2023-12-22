package com.example.envagemobileapplication.Fragments.BottomSheet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.databinding.SelectCameraGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetSelectCameraGallery : BottomSheetDialogFragment() {

    lateinit var binding: SelectCameraGalleryBinding
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SelectCameraGalleryBinding.inflate(inflater, container, false)

        if (checkPermissions()) {
            clickListeners()
        } else {
            requestPermissions()
        }
        return binding.root

    }

    private fun clickListeners() {
        binding.cvTakePhoto.setOnClickListener {
            openCamera()
            binding.cvGallery.setBackgroundResource(R.drawable.searchbg)
            binding.cvGallery.setBackgroundColor(resources.getColor(R.color.white))
            binding.tvGallery.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
            binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                null,
                null
            )
            binding.cvTakePhoto.setBackgroundColor(resources.getColor(R.color.app_main_color))
            binding.tvCamera.setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }


        }

        binding.cvGallery.setOnClickListener {

            openGallery()
            binding.cvTakePhoto.setBackgroundResource(R.drawable.searchbg)
            binding.cvTakePhoto.setBackgroundColor(resources.getColor(R.color.white))
            binding.tvCamera.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)
            binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)


            binding.cvGallery.setBackgroundColor(resources.getColor(R.color.app_main_color))
            binding.tvGallery.setTextColor(resources.getColor(R.color.white))
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val storagePermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        return cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val cameraPermission = Manifest.permission.CAMERA
        val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                cameraPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(cameraPermission)
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                storagePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(storagePermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                CAMERA_REQUEST_CODE
            )
        } else {
            clickListeners()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                clickListeners()
            } else {

                // Handle the case where permissions are not granted
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    // Handle the selected image from the gallery
                    val selectedImageUri: Uri? = data?.data
                    // Use the selectedImageUri as needed
                }
                CAMERA_REQUEST_CODE -> {
                    // Handle the captured image from the camera
                    // The image is usually available in the data parameter of the intent.
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    // Use the imageBitmap as needed
                }
            }
        }
    }


    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }


}