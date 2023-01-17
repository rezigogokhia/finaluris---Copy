package com.example.finaluris

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.changepassword.*
import kotlinx.android.synthetic.main.changepassword.editTextPassword
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.android.synthetic.main.fragment_fragment1.view.*
import kotlinx.android.synthetic.main.fragment_fragment2.view.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.passwordreset.*
import kotlinx.android.synthetic.main.passwordreset.editTextEmail
import kotlinx.android.synthetic.main.registration.*

class main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        textView.text = FirebaseAuth.getInstance().currentUser?.uid

        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, loginactivity::class.java))
            finish()
        }

        buttonGotoChangePassword.setOnClickListener {
            val intent = Intent(this, changepasswordactivity::class.java)
            startActivity(intent)
        }

    }
}

class passwordresetactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.passwordreset)

        buttonSend.setOnClickListener {

            val email = editTextEmail.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check email!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}

class changepasswordactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.changepassword)

        val btn = findViewById<Button>(R.id.btnTest)
        btn.setOnClickListener{
            val dialogbinding = layoutInflater.inflate(R.layout.custom_dialog,null)

            val myDialog = Dialog(this)
            myDialog.setContentView(dialogbinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

        }

        buttonChangePassword.setOnClickListener {

            val newPassword = editTextPassword.text.toString()

            if (newPassword.length < 6) {
                Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .currentUser?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

}

class registrationactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        buttonRegister.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, loginactivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }


        }

    }
}

class loginactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, main::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        buttonLogin.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, main::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        buttonGotoRegister.setOnClickListener {

            val intent = Intent(this, registrationactivity::class.java)
            startActivity(intent)

        }

        buttonGotoResetPassword.setOnClickListener {

            val intent = Intent(this, passwordresetactivity::class.java)
            startActivity(intent)
        }


    }
}

class fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        view.buttonFYP.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateToSecondFragment)}


        return view


    }


}

class fragment2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        view.buttonHome.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateToFirstFragment)}


        return view
    }


}

