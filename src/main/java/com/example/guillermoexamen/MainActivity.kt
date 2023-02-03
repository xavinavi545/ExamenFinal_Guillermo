package com.example.guillermoexamen

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterAPI {
    @POST("/register")
    fun register(@Body item: ClipData.Item): Call<ResponseBody>
}
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        val usernameEditText = findViewById<EditText>(R.id.editTextTextPersonName2)
        val submitButton = findViewById<Button>(R.id.button)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()



            val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:8000")
                .build()

            val registerAPI = retrofit.create(RegisterAPI::class.java)

            submitButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val username = usernameEditText.text.toString()

                val item = ClipData.Item(name = name, username = username)
                val call = registerAPI.register(item)

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Registro agregado!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error al agregar el registro", Toast.LENGTH_SHORT).show()
                    }
                })
            }


            Toast.makeText(this, "Registro agregado!", Toast.LENGTH_SHORT).show()
        }
    }
}