package uk.ac.aber.dcs.cs31620.phrasepad

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

public class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}