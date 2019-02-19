package tool.devp.demochat.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import tool.devp.demochat.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setActions()
    }

    private fun setActions() {
        tvFace.setOnClickListener {
            openPage()
        }
        tvGit.setOnClickListener { }
        tvStoreAndroid.setOnClickListener {
            openStore()
        }
    }

    private fun openPage() {
        var link = "https://www.facebook.com/Android-Learning-Share-1948571468789880/"
        try {
            var app = packageManager.getPackageInfo("com.facebook.katana", 0)
            if (app.applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                link = "fb://page/1948571468789880"
            }

        } catch (ignored: Exception) {
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun openGitPage() {

    }

    private fun openStore() {
        var intentMarketAll = Intent("android.intent.action.VIEW")
        intentMarketAll.data = Uri.parse("https://play.google.com/store/apps/developer?id=Red Android Deverloper")
        startActivity(intentMarketAll)
    }
}