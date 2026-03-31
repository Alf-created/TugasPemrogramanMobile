package com.example.nyawit

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tlName = findViewById<TextInputLayout>(R.id.tlName)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val tlEmail = findViewById<TextInputLayout>(R.id.tlEmail)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val tlBirth = findViewById<TextInputLayout>(R.id.tlBirth)
        val etBirth = findViewById<TextInputEditText>(R.id.etBirth)
        val tlPass = findViewById<TextInputLayout>(R.id.tlPass)
        val etPass = findViewById<TextInputEditText>(R.id.etPass)
        val tlConfirm = findViewById<TextInputLayout>(R.id.tlConfirm)
        val etConfirm = findViewById<TextInputEditText>(R.id.etConfirm)
        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)
        val tvGenderError = findViewById<TextView>(R.id.tvGenderError)
        val cbAgree = findViewById<CheckBox>(R.id.cbAgree)
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        val genders = arrayOf("Pilih Gender", "Laki-laki", "Perempuan")
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genders) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (v is TextView) {
                    v.setTextColor(Color.BLACK)
                }
                return v
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getDropDownView(position, convertView, parent)
                if (v is TextView) {
                    v.setTextColor(Color.BLACK)
                }
                return v
            }
        }
        spinnerGender.adapter = adapter

        etBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                val dateStr = String.format("%02d/%02d/%d", day, month + 1, year)
                etBirth.setText(dateStr)
                tlBirth.error = null
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnCreate.setOnClickListener {
            if (validateInputs(tlName, etName, tlEmail, etEmail, tlBirth, etBirth, tlPass, etPass, tlConfirm, etConfirm, spinnerGender, tvGenderError, cbAgree)) {
                Toast.makeText(this, "Akun Berhasil Dibuat!", Toast.LENGTH_LONG).show()
            }
        }

        tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInputs(
        tlName: TextInputLayout, etName: TextInputEditText,
        tlEmail: TextInputLayout, etEmail: TextInputEditText,
        tlBirth: TextInputLayout, etBirth: TextInputEditText,
        tlPass: TextInputLayout, etPass: TextInputEditText,
        tlConfirm: TextInputLayout, etConfirm: TextInputEditText,
        spinnerGender: Spinner,
        tvGenderError: TextView,
        cbAgree: CheckBox
    ): Boolean {
        var isValid = true

        if (etName.text.toString().trim().isEmpty()) {
            tlName.error = "Nama tidak boleh kosong"
            isValid = false
        } else { tlName.error = null }

        if (etEmail.text.toString().trim().isEmpty()) {
            tlEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else { tlEmail.error = null }

        if (etBirth.text.toString().isEmpty()) {
            tlBirth.error = "Pilih tanggal lahir"
            isValid = false
        } else { tlBirth.error = null }

        if (spinnerGender.selectedItemPosition == 0) {
            tvGenderError.visibility = View.VISIBLE
            isValid = false
        } else {
            tvGenderError.visibility = View.GONE
        }

        if (etPass.text.toString().length < 6) {
            tlPass.error = "Minimal 6 karakter"
            isValid = false
        } else { tlPass.error = null }

        if (etConfirm.text.toString() != etPass.text.toString()) {
            tlConfirm.error = "Password tidak cocok"
            isValid = false
        } else { tlConfirm.error = null }

        if (!cbAgree.isChecked) {
            Toast.makeText(this, "Setujui Syarat & Ketentuan", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}
