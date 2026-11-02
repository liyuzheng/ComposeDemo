package yz.l.compose.media

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import okhttp3.OkHttpClient
import okhttp3.internal.tls.OkHostnameVerifier

/**
 * desc:
 * created by liyuzheng on 2026/5/9 22:04
 */
class PlaybackService : MediaSessionService() {
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "demo_session_notification_channel_id"
    }

    private var mediaSession: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        initializeSessionAndPlayer()
        setListener(MediaSessionServiceListener())
    }

    override fun onGetSession(p0: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    @OptIn(UnstableApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        pauseAllPlayersAndStopSelf()
    }

    @OptIn(UnstableApi::class)
    private fun initializeSessionAndPlayer() {
        val player = buildPlayer()
        mediaSession =
            MediaSession.Builder(this, player)
                .setCustomLayout(listOf(SessionCallback.favoriteButton))
                .setCallback(
                    createSessionCallback()
                ).build()
    }

    private fun buildPlayer(): Player {
        val exoPlayer =
            ExoPlayer.Builder(this)
                .setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true)
                .setMediaSourceFactory(
                    DefaultMediaSourceFactory(this).setDataSourceFactory(
                        OkHttpDataSource.Factory(
                            OkHttpClient.Builder()
                                .hostnameVerifier { hostname, session ->
                                    // 允许 streaming.jamendo.com 通过验证
                                    if (hostname == "streaming.jamendo.com") true
                                    else OkHostnameVerifier.verify(hostname, session)
                                }
                                .build())))
                .build()
        exoPlayer.addAnalyticsListener(EventLogger())
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                mediaSession?.let { session ->
                    // 核心：一变状态就逼迫系统重新走上面的 onUpdateNotification 流程
                    onUpdateNotification(session, isPlaying)
                }
            }
        })
        return exoPlayer
    }

    private fun getBackStackedActivity(): PendingIntent? = null

    private fun createSessionCallback(): MediaSession.Callback {
        return SessionCallback()
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        // 1. 正常交由父类绘制通知栏
        super.onUpdateNotification(session, startInForegroundRequired)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1001 // Media3 默认音乐通知的固定 ID

        // 2. 找到 Media3 刚刚创建出来发给系统的那个通知
        manager.activeNotifications.find { it.id == notificationId }?.let { sbn ->
            val currentNotification = sbn.notification

            if (mediaSession?.player?.isPlaying == true) {
                // 🔥 如果正在播放：强制追加 ONGOING 旗标，锁死通知栏不允许划掉
                currentNotification.flags =
                    currentNotification.flags or Notification.FLAG_ONGOING_EVENT
            } else {
                // ⏸ 如果暂停了：清除 ONGOING 旗标，解锁通知栏，允许用户手动划掉
                currentNotification.flags =
                    currentNotification.flags and Notification.FLAG_ONGOING_EVENT.inv()
            }

            // 3. 将修改完旗标的通知重新应用给系统
            manager.notify(notificationId, currentNotification)
        }
    }

    @OptIn(UnstableApi::class) // MediaSessionService.Listener
    private inner class MediaSessionServiceListener : Listener {

        /**
         * This method is only required to be implemented on Android 12 or above when an attempt is made
         * by a media controller to resume playback when the {@link MediaSessionService} is in the
         * background.
         */
        override fun onForegroundServiceStartNotAllowedException() {
            if (
                Build.VERSION.SDK_INT >= 33 &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                // Notification permission is required but not granted
                return
            }
            val notificationManagerCompat = NotificationManagerCompat.from(this@PlaybackService)
            ensureNotificationChannel(notificationManagerCompat)
            val builder =
                NotificationCompat.Builder(this@PlaybackService, CHANNEL_ID)
                    .setSmallIcon(androidx.media3.session.R.drawable.media3_notification_small_icon)
                    .setContentTitle("Playback cannot be resumed")
                    .setStyle(
                        NotificationCompat.BigTextStyle().bigText(
                            "Press on the play button on the media notification if it\n" +
                                    "    is still present, otherwise please open the app to start the playback and re-connect the session\n" +
                                    "    to the controller"
                        )
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .also { builder ->
                        getBackStackedActivity()?.let {
                            builder.setContentIntent(
                                it
                            )
                        }
                    }
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun ensureNotificationChannel(notificationManagerCompat: NotificationManagerCompat) {
        if (
            Build.VERSION.SDK_INT < 26 ||
            notificationManagerCompat.getNotificationChannel(CHANNEL_ID) != null
        ) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "Playback cannot be resumed",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManagerCompat.createNotificationChannel(channel)
    }
}