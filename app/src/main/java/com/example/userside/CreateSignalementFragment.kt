package com.example.userside

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.userside.databinding.FragmentCreateSignalementBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class CreateSignalementFragment : Fragment() {

    private lateinit var binding: FragmentCreateSignalementBinding
    private lateinit var image : ImageView
    private lateinit var video: VideoView
    private lateinit var btn_upload_camera : ImageView
    private lateinit var btn_upload_gallery : ImageView
    private lateinit var btn_upload_video : ImageView
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncher1: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncher2: ActivityResultLauncher<Intent>
    lateinit var imageBitmap: Bitmap
    lateinit var image_body: MultipartBody.Part
    lateinit var alertBody: MultipartBody.Part
    var filetype =""
    val requestCode = 400
    var intent_video: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateSignalementBinding.inflate(inflater, container, false)
        val view = binding.root
        image = binding.uploadedImage
        btn_upload_camera = binding.uploadCamera
        btn_upload_gallery = binding.uploadGallery
        btn_upload_video = binding.uploadVideo
        video = binding.videoView


        // code to uplaod the image from the camera
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result.data
            if (result.resultCode == AppCompatActivity.RESULT_OK && intent != null)
            {
                imageBitmap = intent.extras?.get("data") as Bitmap
                image.setImageBitmap(imageBitmap)
                image.visibility = View.VISIBLE
                video.visibility = View.INVISIBLE
                filetype = "image"
            }
        }
        // code to upload the image from the gallery
        activityResultLauncher1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result.data
            if (result.resultCode == AppCompatActivity.RESULT_OK && intent != null) {
                val selectedImageUri = intent.getData()
                imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedImageUri!!)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImageUri)
                }
                image.setImageBitmap(imageBitmap)
                image.visibility = View.VISIBLE
                video.visibility = View.INVISIBLE
                filetype = "image"
            }
        }


        // by clicking this button we get the image from the camera
        btn_upload_camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)  {
                openCameraIntent()
            }
            else {
                checkPermission()
            }
        }
        // by clicking this button we get the image from the gallery
        btn_upload_gallery.setOnClickListener {
            imageChooser()
        }

        // Code to upload the video from the gallery
        /*activityResultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result?.data
            if (result.resultCode == AppCompatActivity.RESULT_OK && intent != null) {
                val selectedVideoUri = intent.data
                if (selectedVideoUri != null) {
                    intent_video = selectedVideoUri
                }
                video.setVideoURI(selectedVideoUri)
                video.requestFocus()
                video.start()
                video.visibility = View.VISIBLE
                image.visibility = View.INVISIBLE
                filetype = "video"
            }
        }*/

        activityResultLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val intent = result.data
                if (result.resultCode == RESULT_OK && intent != null) {
                    val selectedVideoUri = intent.getData()
                    intent_video = selectedVideoUri
                    video.setVideoURI(selectedVideoUri)
                    video.requestFocus()
                    video.start()
                    video.visibility = View.VISIBLE
                    /*val user = User("test", "test")
                    val userBody = MultipartBody.Part.createFormData("data", Gson().toJson(user))*/

                    filetype = "video"

                }
            }

        // Code to upload the video from the camera
        btn_upload_video.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)  {
                openVideoCameraIntent()
            }
            else {
                checkPermission()
            }
        }
        binding.buttonEnvoyer.setOnClickListener {

            when (filetype) {
                "video" -> {
                    //Toast.makeText(requireContext(), "Mazal matkhadmtch", Toast.LENGTH_SHORT).show()
                    /*
                    val videoFile =File(intent_video?.path)
                    println("fffffff "+ videoFile)
                    val videoRequestBody = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
                    println("fffffff "+ videoFile.name)
                    println("fffffff "+ videoFile.path)

                    val vFile = MultipartBody.Part.createFormData("video", videoFile.name, videoRequestBody)
                    println("fffffff "+ vFile)*/*/

                /*
                    uploadMedia(vFile)*/
                    val telephone = binding.editTextTextPhone.text.toString()
                    val userlocation = binding.editTextTextUserLocation.text.toString()
                    val alertlocation = binding.editTextTextAlertLocation.text.toString()
                    val data: HashMap<String, String> = HashMap()
                    data.put("phone",telephone)
                    data.put("userlocation",userlocation)
                    data.put("alertlocation",alertlocation)

                    val path = FileHelper.getRealPathFromURI(requireContext(),intent_video)
                    val file = File(path)
                    val reqFile = RequestBody.create("*/*".toMediaTypeOrNull(), file)
                    val image = MultipartBody.Part.createFormData("video", file.name, reqFile)
                    alertBody =  MultipartBody.Part.createFormData("data", Gson().toJson(data))
                    binding.progressBar.visibility = View.VISIBLE
                    uploadVideo(image,alertBody)
                }
                "image" -> {
                    val telephone = binding.editTextTextPhone.text.toString()
                    val userlocation = binding.editTextTextUserLocation.text.toString()
                    val alertlocation = binding.editTextTextAlertLocation.text.toString()
                    val data: HashMap<String, String> = HashMap()
                    data.put("phone",telephone)
                    data.put("userlocation",userlocation)
                    data.put("alertlocation",alertlocation)

                    val filesDir = requireContext().getFilesDir()
                    val file = File(filesDir, "image" + ".png")
                    val bos = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
                    val bitmapdata = bos.toByteArray()
                    val fos = FileOutputStream(file)
                    fos.write(bitmapdata)
                    fos.flush()
                    fos.close()
                    val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    image_body = MultipartBody.Part.createFormData("image", file.getName(), reqFile)
                    alertBody =  MultipartBody.Part.createFormData("data", Gson().toJson(data))
                    binding.progressBar.visibility = View.VISIBLE
                    uploadMedia(image_body,alertBody)
                }
                "" -> {
                    Toast.makeText(requireContext(), "Pas d'image ou video insere", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun uploadVideo(image: MultipartBody.Part, alertBody: MultipartBody.Part) {
        binding.buttonEnvoyer.isEnabled = false
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.uploadVideo(image,alertBody)
            withContext(Dispatchers.Main) {
                binding.buttonEnvoyer.isEnabled = true
                binding.progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    Toast.makeText(requireActivity(),"Signalement est effectuee, merci pour votre collaboration",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
                }


            }


        }
    }

    private fun uploadMedia(imageBody: MultipartBody.Part, alertBody: MultipartBody.Part) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =  RetrofitService.endpoint.uploadMedia(imageBody,alertBody)
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.INVISIBLE
                if(response.isSuccessful) {
                    Toast.makeText(requireActivity(),"Signalement est effectuee, merci pour votre collaboration",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Take a picture launching camera 1
    fun openCameraIntent() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(pictureIntent)
    }
    // Request permission
    private fun checkPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(requireActivity(),perms, requestCode)
    }

    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        if (permsRequestCode==requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCameraIntent()
        }
    }

    // Function to choose a pic from the gallery
    fun imageChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        activityResultLauncher1.launch(intent)
    }
    // function to start recording a video from the camera
    fun openVideoCameraIntent() {
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        activityResultLauncher2.launch(videoIntent)
    }
}