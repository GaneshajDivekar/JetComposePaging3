package jetcompose.paging3

import android.provider.Contacts
import androidx.paging.PagingSource
import com.app.model.main.PhotoListModel
import jetcompose.paging3.Constants.Companion.flickrPhotosSearch
import jetcompose.paging3.Constants.Companion.format
import jetcompose.paging3.Constants.Companion.kittenSearch
import jetcompose.paging3.Constants.Companion.noJsonCallback
import jetcompose.paging3.Constants.Companion.perPage
import net.simplifiedcoding.data.MyApi

class PassengersDataSource(
    private val api: MyApi
) : PagingSource<Int, PhotoListModel>() {
    private val initialPageIndex: Int = 0
    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, PhotoListModel> {
        return try {
            val position = params.key ?: initialPageIndex
            val response = api.getPassengersData(
                flickrPhotosSearch, "9a95c68a9c6ec61104cd3967dcbb8bd3", kittenSearch, position,
                perPage, format, noJsonCallback).await()

            val items = response.body()?.photos
            LoadResult.Page(
                data = items!!.photo!!,
                prevKey = if (position == initialPageIndex) null else position - 1,//if (position > 0) position - 1 else null,
                nextKey = if (items.photo!!.isEmpty()) null else position + 1
                //if (position < response.photos.is!!) position + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
