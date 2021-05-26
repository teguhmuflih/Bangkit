package capstone.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.myapplication.adapter.MainResult
import capstone.myapplication.data.DataDummy
import capstone.myapplication.data.DataEntity
import capstone.myapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    //var photoResult: RecyclerView? = null
    var mainResultAdapter: MainResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_result)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = DataDummy.generateDummyPest()
        //val dataintent = intent.getByteArrayExtra(EXTRA_DETAIL)
        val dataIntent = Uri.parse(intent.getStringExtra(EXTRA_DETAIL))
        //val bmp: Bitmap = BitmapFactory.decodeByteArray(dataintent, 0, dataintent!!.size)

        //val degrees = 90f //rotation degree

        //val matrix = Matrix()
        //matrix.setRotate(degrees)
        //val bOutput = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)

        //binding.viewResult.setImageBitmap(bmp)
        binding.viewResult.setImageURI(dataIntent)
        setData(data)
    }

    private fun setData(data: List<DataEntity>) {
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        val photoResult = findViewById<RecyclerView>(R.id.rv_result)
        //val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        //photoResult.layoutManager = LinearLayoutManager(this)
        mainResultAdapter  = MainResult(this, data)
        photoResult.adapter = mainResultAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}