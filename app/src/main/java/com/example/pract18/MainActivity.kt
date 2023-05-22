package com.example.listoftraffictickets

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.listoftraffictickets.DisplayActivity
import com.example.pract18.R

class MainActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var enterButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginEditText = findViewById(R.id.login)
        passwordEditText = findViewById(R.id.password)
        registerButton = findViewById(R.id.save)
        enterButton = findViewById(R.id.enter)
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        val login = sharedPreferences.getString("login", null)
        val password = sharedPreferences.getString("password", null)

        loginEditText.setText(login)
        passwordEditText.setText(password)

        registerButton.setOnClickListener {
            if (loginEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()){
                val i = Toast.makeText(this, "Введите логин и пароль.", Toast.LENGTH_SHORT)
                i.setGravity(Gravity.TOP, 0, 160)
                i.show()
                return@setOnClickListener
            }

            val loginEd = loginEditText.text.toString()
            val passwordEd = passwordEditText.text.toString()

            val ed = sharedPreferences.edit()
            ed.putString("login", loginEd)
            ed.putString("password", passwordEd)
            ed.apply()

            val i = Toast.makeText(this, "Вы зарегестрировались.", Toast.LENGTH_SHORT)
            i.setGravity(Gravity.TOP, 0, 160)
            i.show()
        }

        enterButton.setOnClickListener {
            if (loginEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()){
                val i = Toast.makeText(this, "Введите логин и пароль.", Toast.LENGTH_SHORT)
                i.setGravity(Gravity.TOP, 0, 160)
                i.show()
                return@setOnClickListener
            } else if (loginEditText.text.toString() != login || passwordEditText.text.toString() != password){
                val i = Toast.makeText(this, "Пароль или логин неверны.", Toast.LENGTH_SHORT)
                i.setGravity(Gravity.TOP, 0, 160)
                i.show()
                return@setOnClickListener
            }
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }
    }
}