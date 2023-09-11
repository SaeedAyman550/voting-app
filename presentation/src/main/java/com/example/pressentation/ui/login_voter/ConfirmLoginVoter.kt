package com.example.pressentation.ui.login_voter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentConfirmLoginVoterBinding
import com.example.domain.utils.ConstantFragmentsDeepLinkUri
import com.example.domain.utils.Resourse
import com.example.pressentation.sharedViewModel.TokenManagerViewModel
import com.example.pressentation.ui.utils.getRealPathFromBitmapImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmLoginVoter : Fragment(R.layout.fragment_confirm_login_voter) {
    private val token_view_model: TokenManagerViewModel by viewModels()
    private val view_model: VoterAuthViewModel by viewModels()
    private val args: ConfirmLoginVoterArgs by navArgs()
    private var Permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private var selectedBitmap: Bitmap? = null


    lateinit var binding: FragmentConfirmLoginVoterBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmLoginVoterBinding.bind(view)

        binding.confirmLoginUserCaptureButton.setOnClickListener {
            pickImage()
        }
        binding.confirmLoginUserConfirmButton.setOnClickListener {

            if (selectedBitmap != null && args.id.isNotEmpty()) {
                val realPath = getRealPathFromBitmapImage(requireContext(), selectedBitmap!!)
                view_model.loginVoter(args.id.toLong(), realPath)
            } else
                Toast.makeText(requireContext(), "id or image not valid", Toast.LENGTH_LONG).show()
        }

        lifecycleScope.launchWhenCreated {
            view_model.login_voter_state_flow.collect {

                when (it) {
                    is Resourse.Loading ->
                        binding.confirmLoginVoterProgressPar.visibility = View.VISIBLE

                    is Resourse.Success -> {
                        binding.confirmLoginVoterProgressPar.visibility = View.GONE

                        handleSaveToken(it.data!!.accessToken) {
                            findNavController().popBackStack()
                            findNavController().popBackStack()
                            val request = NavDeepLinkRequest
                                .Builder
                                .fromUri(ConstantFragmentsDeepLinkUri.Candidates.toUri()).build()
                            findNavController().navigate(request)
                        }

                    }
                    is Resourse.Error -> {
                        binding.confirmLoginVoterProgressPar.visibility = View.GONE
                        Log.d("saeed", "${it.message} error ")

                    }
                }
            }
        }
    }


    private fun pickImage() {
        if (checkPermissionsIsGrandted(requireContext(), Permissions)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(intent)
        } else
            permissionReqLauncher.launch(Permissions)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == AppCompatActivity.RESULT_OK) {


                if (it.data != null) {
                    binding.confirmLoginUserTextStatusUploadImage.setText(R.string.confirm_login_user_text_sucess_upload_image)
                    binding.confirmLoginUserTextStatusUploadImage.visibility = View.VISIBLE
                    selectedBitmap = it.data!!.extras!!.get("data") as Bitmap


                } else
                    Toast.makeText(
                        requireContext(),
                        "data of pick image is null",
                        Toast.LENGTH_LONG
                    ).show()

            }
        }

    private fun checkPermissionsIsGrandted(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private var permissionReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

            val granted = it.entries.all { it.value }
            if (granted) {
                pickImage()
            } else {
                Toast.makeText(requireContext(), "permission not granted", Toast.LENGTH_LONG).show()


            }
        }


    private suspend fun handleSaveToken(token: String, sucess_handle: () -> Unit) {

        val resourse = token_view_model.setVoterToken(token)
        when (resourse) {
            is Resourse.Loading -> {}
            is Resourse.Success -> sucess_handle()
            is Resourse.Error -> {
                Log.d("saeed", "${resourse.message} error ")
            }
        }
    }


}