package com.willyramad.lmovie.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.willyramad.lmovie.R
import com.willyramad.lmovie.databinding.FragmentProfilBinding
import com.willyramad.lmovie.image.writeBitmapToFile
import com.willyramad.lmovie.viewmodel.BlurViewModel
import com.willyramad.lmovie.viewmodel.BlurViewModelFactory
import com.willyramad.lmovie.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ProfilFragment : Fragment() {
    lateinit var binding : FragmentProfilBinding
    lateinit var Email : String
    lateinit var Nama : String
    lateinit var User : String
    lateinit var userViewModel: UserViewModel
    private val blurViewM : BlurViewModel by viewModels { BlurViewModelFactory(requireActivity().application)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        simpanData()

        binding.btnIndo.setOnClickListener {
            setBahasa("id")
        }
        binding.btning.setOnClickListener {
            setBahasa("en")
        }

        binding.btnUpdate.setOnClickListener {
            editData()
            binding.btnUpdate.setOnClickListener {
                val namaB = binding.namaProfil.text.toString()
                val emailB =  binding.emailProfil.text.toString()
                val userB = binding.uProfil.text.toString()
                userViewModel.editData(namaB,emailB,userB, password = String(), "true")
            }
            simpanData()
        }
        binding.ivImage.setOnClickListener {
            checkingPermissions()
        }
        binding.btnKeluar.setOnClickListener {
            userViewModel.editSplash("false")
            Navigation.findNavController(view).navigate(R.id.action_profilFragment_to_homeFragment)
            Toast.makeText(requireActivity(), "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show()
        }
    }
    fun simpanData(){
        userViewModel.dataUser.observe(requireActivity()){
            Email = it.email
            Nama =  it.nama
            User =  it.username
        }
        binding.namaProfil.setText(Nama)
        binding.emailProfil.setText(Email)
        binding.uProfil.setText(User)
    }
    fun editData(){
        binding.namaProfil.isEnabled = true
        binding.emailProfil.isEnabled = true
        binding.uProfil.isEnabled = true
    }

    //handling profile picture
    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                97,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }


    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireActivity())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }


    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    //    camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            blurViewM.imageUri(result!!)
            saveProfile(result)
            val image = BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator +"blur-filter-output-%s"+ File.separator +"blur-filter-output-%s.png")
            if(image!=null){
                binding.ivImage.setImageBitmap(image)
            }else {
                Toast.makeText(requireActivity(), "Foto kosong", Toast.LENGTH_SHORT).show()
            }
        }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivImage.setImageBitmap(bitmap)
    }

    private fun saveProfile(uri : Uri){
        val img = BitmapFactory.decodeStream(requireActivity().applicationContext.contentResolver
            .openInputStream(Uri.parse(uri.toString())))
        blurViewM.applyBlur()
        writeBitmapToFile(requireActivity(), img)
        saveNormalProfileImage(requireActivity(), img)
    }

    fun saveNormalProfileImage(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, "blur-filter-output-%s")
        if (!outputDir.exists()) {
            outputDir.mkdirs() // should succeed
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }
    fun setBahasa(lang : String){
        val bahasa = Locale(lang)
        val res = resources
        val cons = res.configuration
        cons.locale= bahasa
        res.updateConfiguration(cons,res.displayMetrics)
        startActivity(Intent(requireActivity(),FavoriteFragment::class.java))
        requireActivity().finish()
    }
}