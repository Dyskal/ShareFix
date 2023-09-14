package me.dyskal.sharefix

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast

class ShareActivity : Activity() {
    private companion object {
        // Sources : https://github.com/everettsouthwick/embetter/blob/main/src/constants/platforms.js
        private val links = mapOf(
            "twitter.com" to "fxtwitter.com",
            "x.com" to "fixupx.com",
            "instagram.com" to "ddinstagram.com",
            "www.instagram.com" to "ddinstagram.com",
            "tiktok.com" to "tiktxk.com",
            "vm.tiktok.com" to "tiktxk.com",
            "reddit.com" to "rxddit.com",
            "threads.net" to "vxthreads.net",
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()

        if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val uri = Uri.parse(intent.getStringExtra(Intent.EXTRA_TEXT))
            if (!links.containsKey(uri.authority)) return
            val newUri = uri.buildUpon().authority(links[uri.authority]).clearQuery().build()

            val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clip.setPrimaryClip(ClipData.newPlainText("ShareFix", newUri.toString()))
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                Toast.makeText(applicationContext, "ShareFix copied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
