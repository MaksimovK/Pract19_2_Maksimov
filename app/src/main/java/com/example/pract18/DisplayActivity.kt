package com.example.listoftraffictickets

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import com.example.pract18.R

class DisplayActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var deleteButton: Button
    private lateinit var numberEditText: EditText
    private lateinit var typeEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        listView = findViewById(R.id.list_view)
        deleteButton = findViewById(R.id.delete)
        numberEditText = findViewById(R.id.number_edit_text)
        typeEditText = findViewById(R.id.type_edit_text)

        val saveButton: Button = findViewById(R.id.add)
        saveButton.setOnClickListener {
            if (numberEditText.text.toString().isEmpty() || typeEditText.text.toString().isEmpty()){
                val i = Toast.makeText(this, "Введите номер и тип билета.", Toast.LENGTH_SHORT)
                i.setGravity(Gravity.TOP, 0, 160)
                i.show()
                return@setOnClickListener
            }
            val number = numberEditText.text.toString()
            val type = typeEditText.text.toString()
            val ticket = Ticket(number, type)
            val tickets = GsonHelper.loadTickets(this).toMutableList()
            if(tickets.any{it.number.contains(numberEditText.text)}) {
                val i = Toast.makeText(this, "Билет с таким номером уже есть", Toast.LENGTH_SHORT)
                i.setGravity(Gravity.TOP, 0, 160)
                i.show()
                return@setOnClickListener
            }

            tickets.add(ticket)
            GsonHelper.saveTickets(this, tickets)

            val i = Toast.makeText(this, "Билет № $number добавлен.", Toast.LENGTH_SHORT)
            i.setGravity(Gravity.TOP, 0, 160)
            i.show()

            numberEditText.text = null
            typeEditText.text = null

            updateList()
        }

        val tickets = GsonHelper.loadTickets(this)
        val ticketData = tickets.map { "${it.number} - ${it.type}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ticketData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        listView.adapter = adapter

        deleteButton.setOnClickListener {
            GsonHelper.clearTickets(this)
            adapter.clear()
            adapter.notifyDataSetChanged()
            updateList()
        }

        val imageViewBackToMain: ImageView = findViewById(R.id.imageViewBackToMain)
        imageViewBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateList() {
        val tickets = GsonHelper.loadTickets(this)
        val ticketData = tickets.map { "${it.number} - ${it.type}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ticketData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        listView.adapter = adapter
    }
    private fun getAdapter(): ArrayAdapter<String> {
        val tickets = GsonHelper.loadTickets(this)
        val ticketData = tickets.map { "${it.number} - ${it.type}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ticketData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}