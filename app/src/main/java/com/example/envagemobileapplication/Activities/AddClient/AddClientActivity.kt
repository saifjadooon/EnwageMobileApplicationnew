package com.example.envagemobileapplication.Activities.AddClient

import BaseActivity
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.envagemobileapplication.BuildConfig
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientResponse.AddClientResponse
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.CircleTransformation
import com.example.envagemobileapplication.Utils.Constants.Companion.context
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.databinding.ActivityAddClientBinding
import com.ezshifa.aihealthcare.network.ApiUtils
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import jp.wasabeef.richeditor.RichEditor
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddClientActivity : BaseActivity() {
    private var imageadded: Boolean = false
    private var globacamerauri: Uri? = null
    private var descriptiontextforbackpress: String? = ""
    lateinit var description: MultipartBody.Part
    lateinit var loader: Loader
    lateinit var bodyofpf: MultipartBody.Part
    private val GALLERY_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    lateinit var binding: ActivityAddClientBinding
    lateinit var currentPhotoPath: String
    private var mEditor: RichEditor? = null
    var descriptiontext: String = ""
    lateinit var imagefilforapi: File
    private val MULTIPLE_PERMISSIONS = 10
    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO

    )


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityAddClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)
        setUpRtf()
        var uri: Uri = Uri.parse("http://stackoverflow.com")
        val imageFile: File? = uriToFile(contentResolver, uri!!)
        val requestBody =
            imageFile?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        bodyofpf =
            MultipartBody.Part.createFormData(
                "profilePicture",
                "imageFile.name",

                )
        //binding.etLegalName.filters = arrayOf(filter)

        clickListeners()


    }

    private fun clickListeners() {

        KeyboardVisibilityEvent.setEventListener(this, object : KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                if (isOpen) {

                    binding.ivCancel.visibility = View.GONE
                    binding.btnAddClient.visibility = View.GONE
                    // Keyboard is open, handle UI changes here (e.g., hide or adjust UI elements)
                } else {
                    binding.ivCancel.visibility = View.VISIBLE
                    binding.btnAddClient.visibility = View.VISIBLE

                    // Keyboard is closed, handle UI changes here (e.g., show or adjust UI elements)
                }
            }
        })
        binding.scrollview.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Check if the touch event is inside the target view
                val location = IntArray(2)
                binding.cvCameragallery.getLocationOnScreen(location)
                val targetRect = Rect(
                    location[0],
                    location[1],
                    location[0] + binding.cvCameragallery.width,
                    location[1] + binding.cvCameragallery.height
                )

                if (!targetRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    // Touch event is outside the target view, so hide it
                    binding.cvCameragallery.visibility = View.GONE
                    return@setOnTouchListener true // Consume the touch event
                }
            }
            false // Don't consume the touch event
        }

        binding.btnAddClient.setOnClickListener {

            var clientName = binding.etLegalName.text


            if (clientName!!.isNotEmpty()) {

                AddClientApi()
            } else {
                binding.clientLegalName.setError("Name can't be emtpy");
                binding.clientLegalName.setErrorIconDrawable(null)// Set the error message
                binding.clientLegalName.setErrorTextAppearance(R.style.ErrorText);

            }

        }

        binding.ivCamera.setOnClickListener {
            if (checkPermissions()) {
                binding.cvCameragallery.visibility = View.VISIBLE
            }
        }

        binding.ivCross.setOnClickListener {
            if (!binding.etLegalName.text.isNullOrBlank() ||
                /* !binding.etClientDescription.text.isNullOrBlank() ||*/
                !binding.etClientWebsite.text.isNullOrBlank() || descriptiontextforbackpress != "" || descriptiontextforbackpress != "" || imageadded
            ) {
                showDialog()
            } else {
                finish()
            }


        }

        binding.cvTakePhoto.setOnClickListener {
            if (checkPermissions()) {
                openCamera()
                binding.cvGallery.setBackgroundResource(R.drawable.searchbg)
                binding.cvGallery.setBackgroundColor(resources.getColor(R.color.white))
                binding.tvGallery.setTextColor(resources.getColor(R.color.black))
                val drawableLeft: Drawable? =
                    ContextCompat.getDrawable(this, R.drawable.ic_select_from_gallery)
                binding.tvGallery.setCompoundDrawablesWithIntrinsicBounds(
                    drawableLeft,
                    null,
                    null,
                    null
                )
                binding.cvTakePhoto.setBackgroundResource(R.drawable.btn_black_radius)
                binding.tvCamera.setTextColor(resources.getColor(R.color.white))
                val drawable = ContextCompat.getDrawable(this, R.drawable.ic_select_camera)

                // Set the color you want for the SVG drawable
                val color = ContextCompat.getColor(this, R.color.white)

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
        }

        binding.etClientDescription!!.setOnTextChangeListener { text ->

            descriptiontextforbackpress = text
            if (text.contains("<")) {
                descriptiontext = text
            } else {
                descriptiontext = "<p>" + text + "</p>"
            }


        }


        binding.cvGallery.setOnClickListener {
            if (checkPermissions()) {
                openGallery()
                binding.cvTakePhoto.setBackgroundResource(R.drawable.searchbg)
                binding.cvTakePhoto.setBackgroundColor(resources.getColor(R.color.white))
                binding.tvCamera.setTextColor(resources.getColor(R.color.black))
                val drawableLeft: Drawable? =
                    ContextCompat.getDrawable(this, R.drawable.ic_select_camera)
                binding.tvCamera.setCompoundDrawablesWithIntrinsicBounds(
                    drawableLeft,
                    null,
                    null,
                    null
                )
                binding.cvGallery.setBackgroundResource(R.drawable.btn_black_radius)

                binding.tvGallery.setTextColor(resources.getColor(R.color.white))
                val drawable =
                    ContextCompat.getDrawable(this, R.drawable.ic_select_from_gallery)

                // Set the color you want for the SVG drawable
                val color = ContextCompat.getColor(this, R.color.white)

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

        binding.ivCancel.setOnClickListener {
            if (!binding.etLegalName.text.isNullOrBlank() ||
                /* !binding.etClientDescription.text.isNullOrBlank() ||*/
                !binding.etClientWebsite.text.isNullOrBlank() || descriptiontextforbackpress != "" || descriptiontextforbackpress != "" || imageadded
            ) {
                showDialog()
            } else {
                finish()
            }
        }

        val hint = "Client Legal Name *"
        val formattedHint = formatHintWithRedAsterisk(hint)
        binding.clientLegalName.hint = formattedHint

        binding.etClientWebsite.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is not used in this example.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is not used in this example.
            }

            override fun afterTextChanged(editable: Editable?) {
                val enteredText = editable.toString()

                if (enteredText.isEmpty()) {
                    // Clear any previous error when the text is empty
                    binding.clientWebsite.error = null
                } else if (isValidUrl(enteredText)) {
                    // URL is valid, you can update UI or perform other actions
                    binding.clientWebsite.error = null // Clear any previous error
                } else {

                    binding.clientWebsite.setError("Enter valid url");
                    binding.clientWebsite.setErrorIconDrawable(null)// Set the error message
                    binding.clientWebsite.setErrorTextAppearance(R.style.ErrorText);
                    // URL is not valid, display an error message
                    /*binding.etClientWebsite.error = "Invalid URL"*/
                }

            }
        })

        binding.etLegalName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {

                binding.clientLegalName.error = null

            }
        })


    }

    /*  private fun isValidUrl(url: String): Boolean {
          val pattern: Pattern = Patterns.WEB_URL
          val matcher = pattern.matcher(url)
          return matcher.matches()
      }*/


    private fun isValidUrl(url: String): Boolean {
        val customPattern =
            "^(https?://(www\\.)?|www\\.)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/.*)?$"
        return url.matches(customPattern.toRegex())
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("Discard Changes")
        alertDialogBuilder.setMessage("Are you sure you want to discard changes?")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()
            finish()// Dismiss the dialog
        }

        // Add negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Handle the Cancel button click (if needed)
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun showNoPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("App does not have access to your Camera and Gallery. Please enable Camera and Gallery permissions from the settings and select Always.")

        // Add positive button and its click listener
        alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            // Handle the OK button click (if needed)
            dialog.dismiss()

        }


        // Create and show the alert dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun AddClientApi() {
        var profilePic = bodyofpf
        var tokenmanager: TokenManager = TokenManager(this)
        var token = tokenmanager.getAccessToken()

        var name = binding.etLegalName.text.toString()
        var partname = MultipartBody.Part.createFormData(
            "name", name
        )
        var website = binding.etClientWebsite.text.toString()
        var partwebstie = MultipartBody.Part.createFormData(
            "websiteUrl", website
        )
        //  var description = binding.etClientDescription.text.toString()


        if (!descriptiontext.isNullOrBlank()) {
            val htmlContent = descriptiontext
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "Description",
                    "description.html",
                    descriptionBody
                )
            description = descriptionPart
        }
        else {

            val htmlContent = "<p></p>"
            val mediaType = "text/html".toMediaTypeOrNull()
            val descriptionBody = RequestBody.create(mediaType, htmlContent)
            val descriptionPart =
                MultipartBody.Part.createFormData(
                    "description",
                    "description.html",
                    descriptionBody
                )
            description = descriptionPart
        }


        if (name.isNullOrEmpty()) {
            binding.clientLegalName.setError("Name can't be emtpy");
            binding.clientLegalName.setErrorIconDrawable(null)// Set the error message
            binding.clientLegalName.setErrorTextAppearance(R.style.ErrorText);

        }
        try {
            loader.show()

            ApiUtils.getAPIService(this).AddClientData(
                token.toString(), partname, partwebstie, description, profilePic
            )
                .enqueue(object : Callback<AddClientResponse> {
                    override fun onResponse(
                        call: Call<AddClientResponse>,
                        response: Response<AddClientResponse>
                    ) {
                        loader.hide()
                        if (response.body() != null) {
                            if (response?.body()!!.data.alreadyExist.equals(true)) {
                                loader.hide()

                                Snackbar.make(
                                    binding.root,
                                    "Client already exists.",
                                    Snackbar.LENGTH_LONG
                                ).apply {

                                    setTextColor(
                                        ContextCompat.getColor(
                                            this@AddClientActivity,
                                            R.color.white
                                        )
                                    )
                                    setBackgroundTint(
                                        ContextCompat.getColor(
                                            this@AddClientActivity,
                                            R.color.orange
                                        )
                                    )
                                    show() // Show the Snackbar
                                }
                            } else {

                                val toast = Toast.makeText(
                                    this@AddClientActivity,
                                    "Client Added successfully",
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                                finish()
                            }
                        } else {
                            Log.i("errormsg", response.message())
                        }
                    }

                    override fun onFailure(call: Call<AddClientResponse>, t: Throwable) {
                        loader.hide()
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            loader.hide()
            Log.i("exceptionddsfdsfds", ex.toString())
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
                } else {
                    //    checkPermissions()
                    showNoPermissionDialog()
                    //  Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    binding.cvCameragallery.visibility = View.GONE
                    val selectedImageUri: Uri? = data?.data
                    val contentResolver = applicationContext.contentResolver
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val fileSize = inputStream?.available() ?: 0

                    if (fileSize > 5 * 1024 * 1024) {
                        imageadded = true// 5 MB in bytes
                        // Show an error message
                        Toast.makeText(
                            applicationContext,
                            "Please upload an image in defined limit (5 MB).",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val uri: Uri? =
                            selectedImageUri

                        val imageFile: File? = uriToFile(contentResolver, uri!!)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!
                        Picasso.get().load(imageFile)
                            .placeholder(R.drawable.upload_pic_bg)
                            .transform(CircleTransformation()).into(binding.imageView10)
                        imageadded = true
                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profilePicture",
                                imageFile.name,
                                requestBody
                            )

                    }

                }

                CAMERA_REQUEST_CODE -> {

                    try {
                        val uri = Uri.parse(currentPhotoPath)
                        CropImage.activity(uri)
                            .setRequestedSize(512, 512)
                            .start(this)

                        val imageFile: File? = File(currentPhotoPath)
                        Log.i("imagefile", imageFile.toString())
                        imagefilforapi = imageFile!!

                        Picasso.get().load(uri)
                            .placeholder(R.drawable.upload_pic_bg)
                            .transform(CircleTransformation()).into(binding.imageView10)
                        imageadded = true

                        val requestBody =
                            imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profilePicture",
                                imageFile.name,
                                requestBody
                            )
                        binding.cvCameragallery.visibility = View.GONE
                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    }


                }

                CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == Activity.RESULT_OK) {
                        val resultUri = result.uri
                        val file = resultUri.path
                        // mChatListner.sendImageMessage(file!!)

                        Glide.with(this)
                            .load(resultUri)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(binding.imageView10)

                        val imagePath = File(file)


                        imageadded = true

                        imagefilforapi = imagePath

                        val requestBody =
                            imagefilforapi
                                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        bodyofpf =
                            MultipartBody.Part.createFormData(
                                "profilePicture",
                                imagefilforapi.name,
                                requestBody
                            )

                        imageadded = true
                        binding.cvCameragallery.visibility = View.GONE


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        result.error.printStackTrace()
                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        if (!binding.etLegalName.text.isNullOrBlank() ||
            /* !binding.etClientDescription.text.isNullOrBlank() ||*/
            !binding.etClientWebsite.text.isNullOrBlank() || descriptiontextforbackpress != "" || imageadded
        ) {
            showDialog()
        } else {
            finish()
        }
    }

    private fun checkPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= 33) {
            permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        }

        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }

        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/jpg", "image/png")
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

    }

    private fun openCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = getImageFile()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            ) else Uri.fromFile(file)
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            openCameraActivityResultLauncher.launch(pictureIntent)
        } else {
            startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
        }
    }

    fun uriToFile(contentResolver: ContentResolver, uri: Uri): File? {
        try {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val inputStream = contentResolver.openInputStream(uri)
                val file = File(context!!.cacheDir, displayName)

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                    return file
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getImageFile(): File {
        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
        val storageDir: File = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            this.filesDir
        } else {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "Camera"
            )
        }
        val file = File.createTempFile(
            imageFileName, ".jpg", storageDir
        )
        currentPhotoPath = "file:" + file.absolutePath
        currentPhotoPath = "file:" + file.absolutePath
        return file
    }

    var openCameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val uri = Uri.parse(currentPhotoPath)
                    val resultUri = uri
                    val file = resultUri.path
                    imageadded = true
                    // mChatListner.sendImageMessage(file!!)
                    val imagePath = File(file)


                    var imagebytearray = getStreamByteFromImage(imagePath)
                    val tempImageFile = createTempImageFile(imagebytearray!!)
                    // Compress the image using BitmapFactory
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 2 // Adjust the sample size as needed
                    val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath, options)

                    val compressedImageFile = File(this.externalCacheDir, "compressed_image.jpg")
                    val outputStream = FileOutputStream(compressedImageFile)

                    // Compress the Bitmap to a file
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)


                    Glide.with(this)
                        .load(resultUri)
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.imageView10)


                    imagefilforapi = tempImageFile

                    val requestBody =
                        imagefilforapi
                            .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    bodyofpf =
                        MultipartBody.Part.createFormData(
                            "profilePicture",
                            imagefilforapi.name,
                            requestBody
                        )

                    binding.cvCameragallery.visibility = View.GONE
                    /*   CropImage.activity(uri)
                           .setRequestedSize(512, 512)
                           .start(this@AddClientActivity)*/
                } catch (e: Exception) {

                }

            }
        } catch (e: Exception) {

        }
    }


    fun formatHintWithRedAsterisk(hint: String): CharSequence {
        val spannable = SpannableStringBuilder(hint)
        val indexOfAsterisk = hint.indexOf('*')

        if (indexOfAsterisk >= 0) {
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                indexOfAsterisk,
                indexOfAsterisk + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannable
    }

    private fun setUpRtf() {
        mEditor = findViewById<View>(R.id.et_client_description) as RichEditor
        mEditor!!.setEditorHeight(110)
        mEditor!!.setEditorFontSize(14)
        mEditor!!.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(0, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor!!.setPlaceholder("Description")
        //mEditor.setInputEnabled(false);
        //  mPreview = findViewById<View>(R.id.preview) as TextView
        //   mEditor!!.setOnTextChangeListener { text -> mPreview!!.text = text }
        findViewById<View>(R.id.action_undo).setOnClickListener { mEditor!!.undo() }
        findViewById<View>(R.id.action_redo).setOnClickListener { mEditor!!.redo() }
        findViewById<View>(R.id.action_bold).setOnClickListener { mEditor!!.setBold() }
        findViewById<View>(R.id.action_italic).setOnClickListener { mEditor!!.setItalic() }

        findViewById<View>(R.id.action_strikethrough).setOnClickListener { mEditor!!.setStrikeThrough() }
        findViewById<View>(R.id.action_underline).setOnClickListener { mEditor!!.setUnderline() }

        findViewById<View>(R.id.action_txt_color).setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor!!.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })
        findViewById<View>(R.id.action_bg_color).setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor!!.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        findViewById<View>(R.id.action_align_left).setOnClickListener { mEditor!!.setAlignLeft() }
        findViewById<View>(R.id.action_align_center).setOnClickListener { mEditor!!.setAlignCenter() }
        findViewById<View>(R.id.action_align_right).setOnClickListener { mEditor!!.setAlignRight() }

        findViewById<View>(R.id.action_insert_bullets).setOnClickListener { mEditor!!.setBullets() }
        findViewById<View>(R.id.action_insert_numbers).setOnClickListener { mEditor!!.setNumbers() }

        findViewById<View>(R.id.action_insert_link).setOnClickListener {
            mEditor!!.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
    }

    //this is the byte stream that I upload.
    fun getStreamByteFromImage(imageFile: File): ByteArray? {
        var photoBitmap = BitmapFactory.decodeFile(imageFile.path)
        val stream = ByteArrayOutputStream()
        val imageRotation = getImageRotation(imageFile)
        if (imageRotation != 0) photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation)
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }


    private fun getImageRotation(imageFile: File): Int {
        var exif: ExifInterface? = null
        var exifRotation = 0
        try {
            exif = ExifInterface(imageFile.path)
            exifRotation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return if (exif == null) 0 else exifToDegrees(exifRotation)
    }

    private fun exifToDegrees(rotation: Int): Int {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90) return 90 else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) return 180 else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) return 270
        return 0
    }

    private fun getBitmapRotatedByDegree(bitmap: Bitmap, rotationDegree: Int): Bitmap {
        val matrix = Matrix()
        matrix.preRotate(rotationDegree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun createTempImageFile(imageByteArray: ByteArray): File {
        val tempFile = File.createTempFile("temp_image", ".jpg", this.cacheDir)
        try {
            val fileOutputStream = FileOutputStream(tempFile)
            fileOutputStream.write(imageByteArray)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile
    }
}