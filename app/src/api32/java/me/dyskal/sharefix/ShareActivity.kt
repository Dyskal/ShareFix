package me.dyskal.sharefix

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import java.util.Map.of

class ShareActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        val uri = Uri.parse(intent.getStringExtra(Intent.EXTRA_TEXT))
        val links = of(
            "twitter.com", "fixupx.com",
            "x.com", "fixupx.com",
            "instagram.com", "uuinstagram.com",
            "www.instagram.com", "uuinstagram.com",
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
        finish()
    }
}
