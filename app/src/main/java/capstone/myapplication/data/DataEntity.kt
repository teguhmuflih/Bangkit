package capstone.myapplication.data

data class DataEntity (
    var name: String?,
    var percentage: String?,
    var dataPhoto: List<DataPhoto>
)

data class DataPhoto(
    var photo: Int?
)