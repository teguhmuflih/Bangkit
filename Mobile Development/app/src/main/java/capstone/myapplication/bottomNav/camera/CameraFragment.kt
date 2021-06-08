package capstone.myapplication.bottomNav.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import capstone.myapplication.R
import capstone.myapplication.view.ResultActivity
import capstone.myapplication.databinding.FragmentCameraBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class CameraFragment : Fragment() {

    private lateinit var cameraBinding: FragmentCameraBinding

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

        cameraBinding.animPlant.visibility = View.GONE
        cameraBinding.btAnalyze.visibility = View.INVISIBLE

        cameraBinding.btCamera.setOnClickListener {
            ImagePicker.with(this).cameraOnly().start()
        }

        cameraBinding.btGallery.setOnClickListener {
            ImagePicker.with(this)
                    .galleryOnly()
                    .galleryMimeTypes(arrayOf("image/*"))
                    .maxResultSize(400,400)
                    .start()
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
    }
}