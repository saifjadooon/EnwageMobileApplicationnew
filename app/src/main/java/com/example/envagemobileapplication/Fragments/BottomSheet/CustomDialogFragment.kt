package com.example.envagemobileapplication.Fragments.BottomSheet

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.Constants


class CustomDialogFragment : DialogFragment() {
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.select_camera_gallery)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


        var camerabutton: ConstraintLayout = dialog.findViewById(R.id.cv_takePhoto)
        var gallerybutton: ConstraintLayout = dialog.findViewById(R.id.cv_gallery)
        var tv_gallery: TextView = dialog.findViewById(R.id.tv_gallery)
        var tv_camera: TextView = dialog.findViewById(R.id.tv_camera)
        camerabutton.setOnClickListener {
            openCamera()
            gallerybutton.setBackgroundResource(R.drawable.searchbg)
            gallerybutton.setBackgroundColor(resources.getColor(R.color.white))
            tv_gallery.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)
            tv_gallery.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                null,
                null
            )
            camerabutton.setBackgroundColor(resources.getColor(R.color.app_main_color))
            tv_camera.setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                tv_camera.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }


        }

        gallerybutton.setOnClickListener {

            openGallery()
            camerabutton.setBackgroundResource(R.drawable.searchbg)
            camerabutton.setBackgroundColor(resources.getColor(R.color.white))
            tv_camera.setTextColor(resources.getColor(R.color.black))
            val drawableLeft: Drawable? =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_camera)
            tv_camera.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)


            gallerybutton.setBackgroundColor(resources.getColor(R.color.app_main_color))
            tv_gallery.setTextColor(resources.getColor(R.color.white))
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_select_from_gallery)

            // Set the color you want for the SVG drawable
            val color = ContextCompat.getColor(requireContext(), R.color.white)

            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, color)
                tv_gallery.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable,
                    null,
                    null,
                    null
                )
            }
        }

        return dialog
    }

    private fun openGallery() {
        Constants.ReqCode = "1"
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        Constants.ReqCode = "2"
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

}