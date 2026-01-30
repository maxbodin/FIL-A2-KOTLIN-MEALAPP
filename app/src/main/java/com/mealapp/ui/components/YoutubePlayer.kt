package com.mealapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayer(youtubeUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val videoId = remember(youtubeUrl) { extractVideoId(youtubeUrl) }

    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
        }
    }

    var player by remember { mutableStateOf<YouTubePlayer?>(null) }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(youTubePlayerView)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
            youTubePlayerView.release()
        }
    }

    LaunchedEffect(player, videoId) {
        if (player != null && videoId != null) {
            Log.d("YoutubeDebug", "Cueing Video ID: $videoId")
            player?.cueVideo(videoId, 0f)
        }
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        factory = {
            youTubePlayerView.apply {
                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        player = youTubePlayer
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                        Log.e("YoutubeDebug", "Player Error: $error")
                    }
                })
            }
        }
    )
}

fun extractVideoId(url: String): String? {
    val cleanUrl = url.replace("\\", "").trim()
    val pattern =
        "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*".toRegex()
    val extracted = pattern.find(cleanUrl)?.value
    return if (extracted != null && extracted.length == 11) extracted else null
}