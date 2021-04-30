package com.project.moon

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stream.*

class StreamActivity : AppCompatActivity() {

    private var videoUrl: String = "https://www.youtube.com/watch?v=qwn7bRSH54U&list=RDnvdcaMrK2Bc&index=7"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)

        startBtn.setOnClickListener {
            try {
                if (!videoView.isPlaying) {
                    val uri = Uri.parse(videoUrl)
                    videoView.setVideoURI(uri)
                } else {
                    videoView.pause()
                }
            } catch (ex: Exception) {
                ex.printStackTrace();
            }
            videoView.requestFocus()
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                videoView.start()
            }
        }
    }
}