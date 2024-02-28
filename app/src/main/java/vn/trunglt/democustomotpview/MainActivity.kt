package vn.trunglt.democustomotpview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val otpView = findViewById<CustomOtpView>(R.id.customOtpView)
        val btnClear = findViewById<Button>(R.id.btn_clear)
        btnClear.setOnClickListener {
            otpView.clear()
        }
    }
}