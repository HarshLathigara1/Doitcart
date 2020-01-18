package lathigara.harsh.doitcart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject
import java.util.*


class PaymentActivity : AppCompatActivity() {
    var url = "https://doitcart.000webhostapp.com/Paytm/generateChecksum.php"
    var callBackUrl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
    var price:String = ""
    var amount:Double? = null

    var orderId = UUID.randomUUID().toString().substring(0, 28)
    var custId = "78910"
    var mid = "WYqTYG04731238682595"

    var CHECKSUMHASH = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
      //  Checkout.preload(this)

        val title = intent.getStringExtra("result_title")
        val Image = intent.getStringExtra("result_Image")
        val overVieww = intent.getStringExtra("txt_overView")
        val voteaverage = intent.getStringExtra("txt_voteaverage")
        val originallanguage = intent.getStringExtra("txt_originallanguage")
         price = intent.getStringExtra("txt_price")
        amount = price.toDouble()




        movieName.text = title
        overView.text  = overVieww
        languages.text = originallanguage


        Glide.with(this).load(Image).into(imageView2)
        moviePrice.text = "${price} Rs"
        progressBar2.visibility = View.GONE
        btnPay.setOnClickListener {
            progressBar2.visibility = View.VISIBLE
            val requestQueue = Volley.newRequestQueue(this@PaymentActivity)

            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    Toast.makeText(this@PaymentActivity, "done", Toast.LENGTH_LONG).show()
                    try {
                        val jsonObject = JSONObject(response)
                        if (jsonObject.has("CHECKSUMHASH")) {
                            CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH")
                            val paytmPGService =
                                PaytmPGService.getStagingService("https://securegw-stage.paytm.in/order/process")
                            val paramMap =
                                HashMap<String, String>()
                            paramMap["MID"] = mid
                            paramMap["ORDER_ID"] = orderId
                            paramMap["CUST_ID"] = custId
                            paramMap["CHANNEL_ID"] = "WAP"
                            paramMap["TXN_AMOUNT"] = "${amount}"
                            paramMap["WEBSITE"] = "WEBSTAGING"
                            paramMap["INDUSTRY_TYPE_ID"] = "Retail"
                            paramMap["CHECKSUMHASH"] = CHECKSUMHASH
                            paramMap["CALLBACK_URL"] = callBackUrl
                            val paytmOrder = PaytmOrder(paramMap)
                            paytmPGService.initialize(paytmOrder, null)
                            paytmPGService.startPaymentTransaction(
                                this@PaymentActivity,
                                true,
                                true,
                                object :
                                    PaytmPaymentTransactionCallback {
                                    override fun onTransactionResponse(inResponse: Bundle) {
                                        Toast.makeText(
                                            this@PaymentActivity,
                                            inResponse.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()

                                        val intent = Intent(this@PaymentActivity,FinalActivity::class.java)


                                        startActivity(intent)
                                        finish()
                                    }

                                    override fun networkNotAvailable() {
                                        Toast.makeText(
                                            this@PaymentActivity,
                                            "error",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    override fun clientAuthenticationFailed(inErrorMessage: String) {
                                        Toast.makeText(
                                            this@PaymentActivity,
                                            " Auth error",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    override fun someUIErrorOccurred(inErrorMessage: String) {}
                                    override fun onErrorLoadingWebPage(
                                        iniErrorCode: Int,
                                        inErrorMessage: String,
                                        inFailingUrl: String
                                    ) {
                                    }

                                    override fun onBackPressedCancelTransaction() {
                                        progressBar2.visibility = View.GONE
                                    }
                                    override fun onTransactionCancel(
                                        inErrorMessage: String,
                                        inResponse: Bundle
                                    ) {
                                        progressBar2.visibility = View.GONE
                                    }
                                })
                        }
                    } catch (e: Exception) {
                    }
                },
                Response.ErrorListener { }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val paramMap: MutableMap<String, String> =
                        HashMap()
                    paramMap["MID"] = mid
                    paramMap["ORDER_ID"] = orderId
                    paramMap["CUST_ID"] = custId
                    paramMap["CHANNEL_ID"] = "WAP"
                    paramMap["TXN_AMOUNT"] = "100.12"
                    paramMap["WEBSITE"] = "WEBSTAGING"
                    paramMap["INDUSTRY_TYPE_ID"] = "Retail"
                    paramMap["CALLBACK_URL"] = callBackUrl
                    return paramMap
                }
            }
            requestQueue.add(stringRequest)




        }
    }

  /*  override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Donee",Toast.LENGTH_LONG).show()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,p1.toString(),Toast.LENGTH_LONG).show()
        Log.d("this","${p1.toString()}")

    }*/




}
