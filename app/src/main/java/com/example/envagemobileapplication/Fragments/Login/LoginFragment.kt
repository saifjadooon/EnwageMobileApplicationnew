package com.example.envagemobileapplication.Fragments.Login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.envagemobileapplication.Activities.MainActivity
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.SharedPreferences.SSOSharedPreferences
import com.example.envagemobileapplication.Utils.Constants
import com.example.envagemobileapplication.Utils.Loader
import com.example.envagemobileapplication.Utils.SharedPrefs
import com.example.envagemobileapplication.ViewModels.SharedLoginViewModel
import com.example.envagemobileapplication.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    lateinit var loader: Loader // Pass the context of your activity or fragment
    lateinit var binding: FragmentLoginBinding
    val sharedViewModel: SharedLoginViewModel by activityViewModels()
    lateinit var tokenManager: TokenManager
    lateinit var companyPrefs: SSOSharedPreferences

    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        if (dstart == 0 && end > start && source[start] == ' ') {
            // Block leading space
            return@InputFilter ""
        }
        null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader = Loader(requireContext())

        tokenManager = TokenManager(requireContext())
        companyPrefs = SSOSharedPreferences(requireContext())
        binding.buttonNext.setOnClickListener {
            /* val username = "jamesMicheal"
            val password = "Login@786"*/
            /*    val username = binding.etUsername.text.toString().trimEnd()
                val password = binding.etPassword.text.toString().trimEnd()*/

            /*   val username = "upshiftuser"
               val password = "123qweASD&"*/
/*
            val username = "upshiftuser"
            val password = "Test@Zxc756"*/

            val username = "raja@mailinator.com"
            val password = "Login@786"

            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                if (username.isNullOrEmpty()) {
                    binding.textinputUsername.setError("Email Address can't be empty") // Set the error message
                    binding.textinputUsername.setErrorTextAppearance(R.style.ErrorText)
                    binding.textinputUsername.setErrorIconDrawable(null)// Set the error message


                    /*   binding.etUsername.setError("")
                       binding.etUsername.requestFocus()*/
                }
                if (password.isNullOrEmpty()) {
                    binding.textinputPassword.setError("Password can't be empty");
                    binding.textinputPassword.setErrorIconDrawable(null)// Set the error message
                    binding.textinputPassword.setErrorTextAppearance(R.style.ErrorText);

                }

            } else {

                loader.show()
                sharedViewModel.loginUser(username, password, tokenManager)
            }

        }
        sharedViewModel.LDloginUser.observe(viewLifecycleOwner) { data ->
            if (data.equals("UserLoggedinSuccesfully")) {


                val domain = Constants.domain
                sharedViewModel.getCompanybyDomainName(domain, companyPrefs)
                /*val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)*/

            } else {

                loader.hide()
                // loadingManager.hideLoading()

                val rootView = binding.content

                val message = "Invalid credentials"
                val duration = Snackbar.LENGTH_SHORT

                val snackbar = Snackbar.make(rootView, message, duration)
                snackbar.setActionTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                snackbar.show()
            }

        }

        sharedViewModel.LDcompanyDomainName.observe(viewLifecycleOwner) { data ->
            var token = tokenManager.getAccessToken()
            sharedViewModel.getRights(requireContext(), token)
            if (data.response != null) {


                var token = tokenManager.getAccessToken()
                sharedViewModel.getRights(requireContext(), token)


            } else {

                /* val toast = Toast.makeText(
                     requireContext(),
                     "Login Failed",
                     Toast.LENGTH_LONG
                 )

                 toast.show()*/

                loader.hide()

            }
        }

        sharedViewModel.LDgetRights.observe(viewLifecycleOwner) { data ->
            if (data != null) {

                //    loadingManager.hideLoading()

                SharedPrefs.setFirstTimeLaunch(requireContext(), "isFirstTime", false)
                SharedPrefs.setUserLogin(requireContext(), "isUserLogin", true)
                val intent = Intent(requireContext(), MainActivity::class.java)
                //  val intent = Intent(requireContext(), ComposeMessageActivity::class.java)
                // val intent = Intent(requireContext(), HiredDetailsActivity::class.java)
                // val intent = Intent(requireContext(), JobsSummaryActivity::class.java)
                //    val intent = Intent(requireContext(), SendOfferLetterActivity::class.java)
                activity?.finishAffinity()
                startActivity(intent)

            } else {


                /*    val toast = Toast.makeText(
                        requireContext(),
                        "Login Failed",
                        Toast.LENGTH_LONG
                    )

                    toast.show()
    */
            }

        }
        binding.textinputPassword.setEndIconOnClickListener {
            // Toggle password visibility
            if (binding.etPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                // Hide password
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.textinputPassword.endIconDrawable =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_eyeoff)
            } else {
                // Show password
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.textinputPassword.endIconDrawable =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_eyeon)
            }
            // Move the cursor to the end of the text
            binding.etPassword.setSelection(binding.etPassword.text!!.length)
        }
        setupClearErrorOnTextChange(binding.textinputPassword, binding.etPassword)
        setupClearErrorOnTextChange(binding.textinputUsername, binding.etUsername)

        binding.etUsername.filters = arrayOf(filter)
        binding.etPassword.filters = arrayOf(filter)
    }

    private fun setupClearErrorOnTextChange(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText
    ) {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Remove the error message when the user starts typing
                textInputLayout.error = null
            }

            override fun afterTextChanged(editable: Editable) {
                // Not needed for this implementation
            }
        })
    }


}