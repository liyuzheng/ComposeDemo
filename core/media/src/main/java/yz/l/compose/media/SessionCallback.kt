package yz.l.compose.media

import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.ListenableFuture

/**
 * desc:
 * created by liyuzheng on 2026/5/9 23:31
 */
class SessionCallback : MediaSession.Callback {
    companion object {
        private const val CUSTOM_COMMAND_FAVORITE = "ACTION_FAVORITE"
        val favoriteCommand = SessionCommand(CUSTOM_COMMAND_FAVORITE, Bundle.EMPTY)
        val favoriteButton =
            CommandButton.Builder(CommandButton.ICON_HEART_UNFILLED)
                .setSessionCommand(favoriteCommand)
                .setDisplayName("收藏")
                .build()
    }


    @OptIn(UnstableApi::class)
    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        val sessionCommands = MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
        val playerCommands = MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
        sessionCommands.add(favoriteCommand)
        if (controller.packageName == "yz.l.compose.demo  ") {
            Log.v("SessionCallback", "onConnect current package")
            return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                .setAvailablePlayerCommands(playerCommands.build())
                .setAvailableSessionCommands(sessionCommands.build())
                .build()
        }
        playerCommands.remove(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM)

        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
            .setAvailableSessionCommands(sessionCommands.build())
            .setAvailablePlayerCommands(playerCommands.build())
            .build()
    }

    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        super.onPostConnect(session, controller)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        Log.v(
            "onCustomCommand",
            "customCommand ${customCommand.customAction} ${
                session.player.currentMediaItem?.mediaMetadata?.extras?.getString("a")
            }"
        )
        return super.onCustomCommand(session, controller, customCommand, args)
    }

    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>
    ): ListenableFuture<List<MediaItem>> {
        return super.onAddMediaItems(mediaSession, controller, mediaItems)
    }

    @OptIn(UnstableApi::class)
    override fun onSetMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>,
        startIndex: Int,
        startPositionMs: Long
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        return super.onSetMediaItems(
            mediaSession,
            controller,
            mediaItems,
            startIndex,
            startPositionMs
        )
    }

}