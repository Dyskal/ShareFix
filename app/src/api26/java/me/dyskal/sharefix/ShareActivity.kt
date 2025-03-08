package me.dyskal.sharefix

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import java.util.Map.of

class ShareActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        val uri = Uri.parse(intent.getStringExtra(Intent.EXTRA_TEXT))
        val links = of(
            "twitter.com", "fixupx.com",
            "x.com", "fixupx.com",
            "instagram.com", "ddinstagram.com",
            "www.instagram.com", "ddinstagram.com",
            "tiktok.com", "vxtiktok.com",
            "vm.tiktok.com", "vxtiktok.com",
            "reddit.com", "rxddit.com",
            "threads.net", "vxthreads.net",
        )
        val authority = links.getOrDefault(uri.authority, uri.authority)
        val clip = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clip.setPrimaryClip(
            ClipData.newPlainText(
                "ShareFix",
                uri.buildUpon().authority(authority).clearQuery().toString()
            )
        )
        // Toast if SDK <= 32, else system does it
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(this, "ShareFix copied", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}