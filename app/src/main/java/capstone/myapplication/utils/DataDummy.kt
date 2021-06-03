package capstone.myapplication.utils

import capstone.myapplication.R
import capstone.myapplication.data.DataEntity
import capstone.myapplication.data.DataPhoto

object DataDummy {
    fun generateDummyPest(): List<DataEntity>{

        val result = ArrayList<DataEntity>()

        val photo1 = ArrayList<DataPhoto>()
        photo1.add(DataPhoto(R.drawable.images1))
        photo1.add(DataPhoto(R.drawable.images2))

        val photo2 = ArrayList<DataPhoto>()
        photo2.add(DataPhoto(R.drawable.images3))
        photo2.add(DataPhoto(R.drawable.images4))

        result.add(
            DataEntity(
            "Pest1",
            "65%",
            photo1
        )
        )

        result.add(
            DataEntity(
            "Pest2",
            "65%",
            photo2
        )
        )

        return result
    }
}