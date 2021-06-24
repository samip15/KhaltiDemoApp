package com.example.khaltidemointegration

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.khalti.checkout.helper.Config
import com.khalti.checkout.helper.KhaltiCheckOut
import com.khalti.checkout.helper.OnCheckOutListener
import com.khalti.checkout.helper.PaymentPreference
import com.khalti.utils.Constant
import com.khalti.widget.KhaltiButton


class MainActivity : AppCompatActivity() {
    lateinit var khaltiBtn: KhaltiButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        khaltiBtn = findViewById(R.id.khalti_button)
        val map: MutableMap<String, Any> = HashMap()
        map["merchant_extra"] = "This is extra data"

        val builder: Config.Builder = Config.Builder(
            Constant.pub,
            "Product ID",
            "Main",
            1100L,
            object : OnCheckOutListener {
                override fun onError(
                    action: String,
                    errorMap: Map<String, String>
                ) {
                    Log.i(action, errorMap.toString())
                }

                override fun onSuccess(data: Map<String, Any>) {
                    Log.i("success", data.toString())
                }
            })
            .paymentPreferences(object : ArrayList<PaymentPreference?>() {
                init {
                    add(PaymentPreference.KHALTI)
                    add(PaymentPreference.EBANKING)
                    add(PaymentPreference.MOBILE_BANKING)
                    add(PaymentPreference.CONNECT_IPS)
                    add(PaymentPreference.SCT)
                }
            })
            .additionalData(map)
            .productUrl("http://example.com/product")
            .mobile("9800000000")
        val config: Config = builder.build()
        val khaltiCheckOut = KhaltiCheckOut(this, config)
        khaltiBtn.setOnClickListener{
            khaltiCheckOut.show();
        }
    }
}