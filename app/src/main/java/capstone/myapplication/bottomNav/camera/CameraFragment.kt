package capstone.myapplication.bottomNav.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import capstone.myapplication.R
import capstone.myapplication.ResultActivity
import capstone.myapplication.databinding.FragmentCameraBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.io.File

class CameraFragment : Fragment() {

    private lateinit var cameraBinding: FragmentCameraBinding
    //private lateinit var cameraViewModel: CameraViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraBinding = FragmentCameraBinding.bind(view)
        //cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)

        cameraBinding.animPlant.visibility = View.GONE
        cameraBinding.btAnalyze.visibility = View.INVISIBLE

        //cameraBinding.animPlant.visibility = View.VISIBLE
        //cameraBinding.previewPlant.visibility = View.VISIBLE

        cameraBinding.btCamera.setOnClickListener {
            //openCamera()
            ImagePicker.with(this).cameraOnly().start()
        }

        cameraBinding.btGallery.setOnClickListener {
            ImagePicker.with(this)
                    .galleryOnly()
                    .galleryMimeTypes(arrayOf("image/*"))
                    .maxResultSize(400,400)
                    .start()
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (activity?.let { it1 -> checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) } == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImageGallery();
                }
            }else{
                chooseImageGallery();
            }*/
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){
            val dataPhoto = data?.data
            cameraBinding.previewPlant.setImageURI(dataPhoto)
            cameraBinding.btAnalyze.visibility = View.VISIBLE

            cameraBinding.btAnalyze.setOnClickListener {
                val dataCamera = Intent(activity, ResultActivity::class.java)
                dataCamera.putExtra(ResultActivity.EXTRA_DETAIL, dataPhoto.toString())
                startActivity(dataCamera)
            }
        }
        /*if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val dataCamera = data.extras?.get("data") as Bitmap
            cameraBinding.previewPlant.setImageBitmap(dataCamera)

            cameraBinding.previewPlant.visibility = View.VISIBLE
            cameraBinding.btAnalyze.visibility = View.VISIBLE
            //Convert to byte array
            val stream = ByteArrayOutputStream()
            dataCamera.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()

            cameraBinding.btAnalyze.setOnClickListener {
                val dataCamera = Intent(activity, ResultActivity::class.java)
                dataCamera.putExtra(ResultActivity.EXTRA_DETAIL, byteArray)
                startActivity(dataCamera)
            }
        }
        else if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            val dataGallery = data?.data
            val dataBitmap = MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), dataGallery)
            cameraBinding.previewPlant.setImageBitmap(dataBitmap)
            cameraBinding.previewPlant.visibility = View.VISIBLE
            cameraBinding.btAnalyze.visibility = View.VISIBLE

            cameraBinding.btAnalyze.setOnClickListener {
                val dataCamera = Intent(activity, ResultActivity::class.java)
                dataCamera.putExtra(ResultActivity.EXTRA_DETAIL, dataBitmap)
                startActivity(dataCamera)
            }
        }
            //cameraViewModel.setImage(data)

            /*cameraViewModel.image.observe(viewLifecycleOwner, { photo ->

                cameraBinding.previewPlant.setImageBitmap(photo)
                cameraBinding.previewPlant.visibility = View.VISIBLE
                cameraBinding.btAnalyze.visibility = View.VISIBLE
                //Convert to byte array
                //Convert to byte array
                val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                cameraBinding.btAnalyze.setOnClickListener {
                    val dataCamera = Intent(activity, ResultActivity::class.java)
                    dataCamera.putExtra(ResultActivity.EXTRA_DETAIL, byteArray)
                    startActivity(dataCamera)
                }
                //data.extras?.get("data") as Bitmap
            })*/
            //cameraBinding.previewPlant.visibility = View.VISIBLE

*/
    }
}