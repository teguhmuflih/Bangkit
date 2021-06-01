package capstone.myapplication.view

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.myapplication.R
import capstone.myapplication.data.DataDummy
import capstone.myapplication.data.DataEntity
import capstone.myapplication.databinding.ActivityResultBinding
import capstone.myapplication.databinding.ItemResultBinding
import capstone.myapplication.tflite.Classifier

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

    //var photoResult: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_result)

        binding = ActivityResultBinding.inflate(layoutInflater)
        itemBinding = binding.itemResult
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarResult)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        classifier = Classifier(assets, MODEL_PATH, LABEL_PATH, INPUT_SIZE)

        //val data = DataDummy.generateDummyPest()
        //val dataintent = intent.getByteArrayExtra(EXTRA_DETAIL)
        val dataIntent = Uri.parse(intent.getStringExtra(EXTRA_DETAIL))
        //val bmp: Bitmap = BitmapFactory.decodeByteArray(dataintent, 0, dataintent!!.size)

        //val degrees = 90f //rotation degree

        //val matrix = Matrix()
        //matrix.setRotate(degrees)
        //val bOutput = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)

        //binding.viewResult.setImageBitmap(bmp)
        //binding.viewResult.setImageURI(dataIntent)
        val bitmapPhoto = MediaStore.Images.Media.getBitmap(this.contentResolver, dataIntent)

        itemBinding.viewResult.setImageBitmap(bitmapPhoto)

        val result = classifier.recognizeImage(bitmapPhoto)

        runOnUiThread {
            itemBinding.textViewResult.text = result.get(0).title
        }

        //binding.viewResult.setImageBitmap(bitmapPhoto)
        //setData(data)
    }

    /*private fun setData(data: List<DataEntity>) {
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        val photoResult = findViewById<RecyclerView>(R.id.rv_result)
        //val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        //photoResult.layoutManager = LinearLayoutManager(this)
        mainResultAdapter  = MainResult(this, data)
        photoResult.adapter = mainResultAdapter
    }*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}