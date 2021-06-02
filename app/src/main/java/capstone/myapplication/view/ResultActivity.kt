package capstone.myapplication.view

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import capstone.myapplication.databinding.ActivityResultBinding
import capstone.myapplication.databinding.ItemResultBinding
import capstone.myapplication.tflite.Classifier
import java.lang.Exception
import java.sql.Connection

class ResultActivity : AppCompatActivity() {

    private val MODEL_PATH = "PlantModel.tflite"
    private val LABEL_PATH = "labels.txt"
    private val INPUT_SIZE = 224

    private lateinit var classifier: Classifier
    private lateinit var binding: ActivityResultBinding
    private lateinit var itemBinding: ItemResultBinding

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        itemBinding = binding.itemResult
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarResult)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        classifier = Classifier(assets, MODEL_PATH, LABEL_PATH, INPUT_SIZE)

        val dataIntent = Uri.parse(intent.getStringExtra(EXTRA_DETAIL))
        val bitmapPhoto = MediaStore.Images.Media.getBitmap(this.contentResolver, dataIntent)

        itemBinding.viewResult.setImageBitmap(bitmapPhoto)

        val result = classifier.recognizeImage(bitmapPhoto)

        val titleResult = result.get(0).title

        runOnUiThread {
            itemBinding.textViewResult.text = titleResult
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}