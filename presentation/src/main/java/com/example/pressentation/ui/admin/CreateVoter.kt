package com.example.pressentation.ui.admin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentCreateVoterBinding
import com.example.pressentation.ui.utils.getRealPath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateVoter : Fragment(R.layout.fragment_create_voter) {

    private val view_model: AdminViewModel by viewModels()
    private var Permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private var selected_file: Uri? = null

    lateinit var binding: FragmentCreateVoterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateVoterBinding.bind(view)

        binding.createVoterEditCircleImage.setOnClickListener {
            pick()
        }

        binding.createVoterSaveButton.setOnClickListener {

            handleCreateVoter {
                val spinnerData = getAllStringFromSpinner()
                val editTextData = getAllStringFromEditText()
                val realPath = getRealPath(requireContext(), selected_file!!)
                val birthDay = "${spinnerData.first}-${spinnerData.second}-${spinnerData.third}"

                view_model.createVoter(
                    editTextData.first.toLong(), editTextData.second, birthDay,
                    editTextData.third, realPath
                )

            }
        }

        lifecycleScope.launchWhenCreated {
            view_model.create_voter_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.createVoterProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.createVoterProgressPar.visibility = View.GONE
                        findNavController().popBackStack()
                    }
                    is Resourse.Error -> {
                        if (it.message == "init mutable state")
                        else {
                            Log.d("saeed", "${it.message} error ")
                            binding.createVoterProgressPar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
    private fun getStringFromEditText(edit_text: EditText): String = edit_text.text.toString()
    private fun getAllStringFromEditText(): Triple<String, String, String> {
        val id = getStringFromEditText(binding.createVoterEditTextId)
        val name = getStringFromEditText(binding.createVoterEditTextName)
        val city = getStringFromEditText(binding.createVoterEditTextCity)
        return Triple(id, name, city)
    }

    private fun getStringFromSpinner(spinner: Spinner): String = spinner.selectedItem.toString()
    private fun getAllStringFromSpinner(): Triple<String, String, String> {
        val year = getStringFromSpinner(binding.createVoterSpinnerYear)
        val month = getStringFromSpinner(binding.createVoterSpinerMonth)
        val day = getStringFromSpinner(binding.createVoterSpinerDay)
        return Triple(day, month, year)
    }

    private fun checkSpinner(year: String, month: String, day: String): Boolean =
        year != "year" && month != "Month" && day != "Day"

    private fun checkEditText(id: String, name: String, city: String): Boolean =
        id.isNotEmpty() && name.isNotEmpty() && city.isNotEmpty() && id.length == 14

    private fun handleCreateVoter(handle: () -> Unit) {

        val spinner_data = getAllStringFromSpinner()
        val edit_text_data = getAllStringFromEditText()

        if (selected_file != null)
            if (checkEditText(edit_text_data.first, edit_text_data.second, edit_text_data.third))
                if (checkSpinner(spinner_data.first, spinner_data.second, spinner_data.third))
                    handle()
                else
                    Toast.makeText(requireContext(), "choose date from spinner", Toast.LENGTH_LONG)
                        .show()
            else
                Toast.makeText(requireContext(), "id or name or city not valid", Toast.LENGTH_LONG)
                    .show()
        else
            Toast.makeText(requireContext(), "pick face id image", Toast.LENGTH_LONG).show()
    }


    fun pick() {
        if (checkPermissionsIsGrandted(requireContext(), Permissions)) {
            selectImage()
        } else {
            permissionReqLauncher.launch(Permissions)
        }


    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        resultLauncher.launch(intent)
    }

    private fun checkPermissionsIsGrandted(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                if (it.data != null) {
                    selected_file = it.data!!.data!!
                    binding.createVoterCircleImageProfile.setImageURI(selected_file)
                } else
                    Toast.makeText(
                        requireContext(),
                        "data of pick image is null",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }


    private var permissionReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val granted = it.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
                Toast.makeText(requireContext(), "permission not granted", Toast.LENGTH_LONG).show()
            }
        }


}