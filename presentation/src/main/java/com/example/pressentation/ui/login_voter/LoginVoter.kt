package com.example.pressentation.ui.login_voter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.domain.utils.*
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentLoginVoterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginVoter : Fragment(R.layout.fragment_login_voter) {

    lateinit var binding:FragmentLoginVoterBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentLoginVoterBinding.bind(view)

        binding.loginUserButton.setOnClickListener {

            val id=getStringFromEditText(binding.loginUserEditText)
            if (checkValidationId(id)){
                val request = NavDeepLinkRequest.Builder
                    .fromUri("${ConstantFragmentsDeepLinkUri.ConfirmLoginVoter}/${id}".toUri()).build()
                findNavController().navigate(request)
            }
            else
                Toast.makeText(requireContext(),"id is not valid",Toast.LENGTH_LONG).show()

        }

    }



    private fun checkValidationId(id:String):Boolean= id.isNotEmpty()&&id.length==14&&id.toLongOrNull()!=null

    private fun getStringFromEditText(edit_text: EditText):String=edit_text.text.toString()







}