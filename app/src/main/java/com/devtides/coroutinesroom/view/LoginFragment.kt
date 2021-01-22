package com.devtides.coroutinesroom.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener { onLogin(it) }
        gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(this, Observer { isComplete ->
            if (isComplete) {
                val action = LoginFragmentDirections.actionGoToMain()
                Navigation.findNavController(loginBtn).navigate(action)
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                error, Snackbar.LENGTH_SHORT
            ).show()

        })
    }

    private fun onLogin(v: View) {
        if (loginUsername.text.isNullOrBlank() || loginPassword.text.isNullOrBlank()) {
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                "Fields empty",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            viewModel.login(loginUsername.text.toString(), loginPassword.text.toString())
        }

    }

    private fun onGotoSignup(v: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
