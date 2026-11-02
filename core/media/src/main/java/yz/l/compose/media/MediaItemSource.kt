package yz.l.compose.media

import androidx.media3.common.MediaItem

/**
 * desc:
 * created by liyuzheng on 2026/5/10 0:04
 */
object MediaItemSource {
    val currentMediaItemTree = mutableListOf<MediaItem>()
    private var dataSource: DataSource? = null
    fun setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
    }

    fun insert(item: MediaItem) {
        if (dataSource == null) {
            throw RuntimeException("dataSource is not impl!")
        }
        dataSource?.insert(item)
    }

    fun insertAll(items: List<MediaItem>) {
        if (dataSource == null) {
            throw RuntimeException("insertAll is not impl!")
        }
        dataSource?.insertAll(items)
    }

    fun getItemById(id: String): MediaItem? {
        if (dataSource == null) {
            throw RuntimeException("getItemById is not impl!")
        }
        return dataSource?.getItemById(id)
    }

    interface DataSource {
        fun insert(item: MediaItem)
        fun insertAll(items: List<MediaItem>)
        fun getItemById(id: String): MediaItem?
    }
}