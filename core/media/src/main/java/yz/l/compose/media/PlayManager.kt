package yz.l.compose.media

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

/**
 * desc:
 * created by liyuzheng on 2026/5/10 0:37
 */
class PlayManager(context: Context) {
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var mediaController: MediaController? = null

    // 暴露给 UI 的状态
    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val _currentMediaItem = MutableLiveData<MediaItem?>()
    val currentMediaItem: LiveData<MediaItem?> = _currentMediaItem

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, PlaybackService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

        controllerFuture?.addListener({
            // 1. 获取 Controller 实例
            mediaController = controllerFuture?.get()

            // 2. 绑定内部监听器同步状态
            mediaController?.addListener(object : Player.Listener {
                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    super.onMediaMetadataChanged(mediaMetadata)
                    Log.v("PlayManager", "mediaMetadata ${mediaMetadata.title} ${mediaMetadata.extras?.getString("a")}")
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    Log.v("PlayManager", "onPlaybackStateChanged $playbackState")
                }

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    Log.v("PlayManager", "onIsLoadingChanged $isLoading")
                }

                override fun onIsPlayingChanged(playing: Boolean) {
                    _isPlaying.postValue(playing)
                    Log.v("PlayManager", "onIsPlayingChanged $playing")
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    _currentMediaItem.postValue(mediaItem)
                    Log.v("PlayManager", "onMediaItemTransition ${mediaItem?.mediaMetadata?.title}")

                }
            })
        }, MoreExecutors.directExecutor())
    }

    // --- 播放控制封装 ---

    fun playPause() {
        mediaController?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun seekToNext() {
        mediaController?.seekToNext()
    }

    fun setMediaList(items: List<MediaItem>) {
        mediaController?.setMediaItems(items)
        mediaController?.prepare()
    }

    fun release() {
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        mediaController = null
    }

    companion object {
        @Volatile
        private var instance: PlayManager? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: PlayManager(context.applicationContext).also { instance = it }
        }
    }
}