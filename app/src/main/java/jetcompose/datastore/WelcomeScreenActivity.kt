package jetcompose.datastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import jetcompose.paging3.R
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeScreenActivity : AppCompatActivity() {
    protected lateinit var userPref: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        userPref = UserPref(applicationContext)
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            userPref.authToken.asLiveData().observe(this, Observer{
                System.out.println("value=" + it)

            })
        }

    }
}