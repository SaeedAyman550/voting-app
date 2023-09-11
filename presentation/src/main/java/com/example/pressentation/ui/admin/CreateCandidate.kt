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
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.models.Agenda
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.adapter.CreateCandidateAgentaAdapter
import com.example.pressentation.databinding.FragmentCreateCandidateBinding
import com.example.pressentation.ui.utils.getRealPath
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateCandidate : Fragment(R.layout.fragment_create_candidate) {

    lateinit var binding:FragmentCreateCandidateBinding
    @Inject
    lateinit var adapter:CreateCandidateAgentaAdapter
    private  val  view_model: AdminViewModel by viewModels()
    private var Permissions  =  arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private  var selected_file: Uri?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentCreateCandidateBinding.bind(view)

        initRecyView()

        binding.createCandidateEditCircleImage.setOnClickListener{
            pick()
        }

        binding.createCandidateAddAgendasText.setOnClickListener {
             adapter.addItemCandidate()
        }

        binding.createCandidateSaveButton.setOnClickListener {

           handleCreateCandidate {
               val spinnerData=getAllStringFromSpinner()
               val editTextData=getAllStringFromEditText()
               val politicalParty=getSelectedRadioButton()
               val list=adapter.getCandidateAgendaList()
               val birthDay="${spinnerData.first}-${spinnerData.second}-${spinnerData.third}"

               val real_path = getRealPath(requireContext(), selected_file!!)

                   view_model.createCandidate(
                       editTextData.first,
                       editTextData.second,
                       birthDay,
                       politicalParty,
                       list,
                       real_path
                   )


           }
        }


        lifecycleScope.launchWhenCreated {
        view_model.create_candidate_state_flow.collect {
            when (it) {
                is Resourse.Loading ->
                    binding.createCandidateProgressPar.visibility = View.VISIBLE

                is Resourse.Success -> {
                    binding.createCandidateProgressPar.visibility = View.GONE
                    findNavController().popBackStack()
                }
                is Resourse.Error -> {
                        Log.d("saeed", "${it.message} error ")
                        binding.createCandidateProgressPar.visibility = View.GONE
                }


            }
        }}



    }
    private fun getEmptyItemAgenda():List <Agenda> {

        val l:MutableList<Agenda> = mutableListOf()
        for (i in 0..10){
           l.add( Agenda())
        }
        return l

    }
    private fun initRecyView(){
        var list=getEmptyItemAgenda()

        val l= mutableListOf<Agenda>()
        l.add(Agenda())
        adapter.submitList(l)
        binding.createCandidatesRecy.adapter=adapter

    }

    private fun getStringFromSpinner(spinner: Spinner):String=spinner.selectedItem.toString()
    private fun getAllStringFromSpinner():Triple<String,String,String>{
        val year=getStringFromSpinner(binding.createCandidateSpinnerYear)
        val month=getStringFromSpinner(binding.createCandidateSpinerMonth)
        val day=getStringFromSpinner(binding.createCandidateSpinerDay)
        return Triple(day,month,year)
    }

    private fun getStringFromEditText(edit_text: EditText):String=edit_text.text.toString()
    private fun getAllStringFromEditText():Pair<String,String>{
        val name=getStringFromEditText(binding.createCandidateEditTextName)
        val code=getStringFromEditText(binding.createCandidateEditTextCode)
        return Pair(name,code)
    }

    private fun getStringFromRadioButton(radioButton: RadioButton):String=radioButton.text.toString()
    private fun getSelectedRadioButton():String{
        val democrat=getStringFromRadioButton(binding.createCandidateRadioButtonDemocrat)
        val republican=getStringFromRadioButton(binding.createCandidateRadioButtonRepublican)
        if (binding.createCandidateRadioButtonDemocrat.isChecked)
            return democrat
        else if (binding.createCandidateRadioButtonRepublican.isChecked)
            return republican

        return ""
    }


    private fun checkSpinner(year:String,month:String,day:String):Boolean= year != "year"&& month!="Month"&& day!="Day"
    private fun checkEditText( name:String, code:String):Boolean= name.isNotEmpty()&&code.isNotEmpty()
    private fun checkItemListNotEmpty(list:List<Agenda>):Boolean {
        list.map {
            if (it.abstraction.isEmpty()||it.summary.isEmpty())
            return false
        }
            return true
    }
    private fun checkRadioButton(radio:String):Boolean=radio.isNotEmpty()



    private fun handleCreateCandidate(handle:()->Unit){

        val spinner_data=getAllStringFromSpinner()
        val edit_text_data=getAllStringFromEditText()

        if (selected_file!=null)
            if (checkEditText(edit_text_data.first,edit_text_data.second))
                if (checkSpinner(spinner_data.first,spinner_data.second,spinner_data.third))
                    if (checkRadioButton(getSelectedRadioButton()))
                        if (checkItemListNotEmpty(adapter.getCandidateAgendaList()))
                         handle()
                    else
                        Toast.makeText(requireContext(),"agendas not valid",Toast.LENGTH_LONG).show()
                else
                        Toast.makeText(requireContext(),"select on radio button",Toast.LENGTH_LONG).show()
             else
                    Toast.makeText(requireContext(),"choose date from spinner",Toast.LENGTH_LONG).show()
        else
                Toast.makeText(requireContext()," name or code not valid",Toast.LENGTH_LONG).show()
    else
            Toast.makeText(requireContext(),"pick profile image",Toast.LENGTH_LONG).show()
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
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == AppCompatActivity.RESULT_OK) {

                if (it.data!=null) {
                    selected_file = it.data!!.data!!
                    binding.createCandidateCircleImageProfile.setImageURI(selected_file)
                }
                else
                    Toast.makeText(requireContext(), "data of pick image is null", Toast.LENGTH_LONG).show()

            }
        }
    private var permissionReqLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val granted = it.entries.all { it.value }
            if (granted) {
                selectImage()
            } else {
                Toast.makeText(requireContext(),"permission not granted", Toast.LENGTH_LONG).show()
            }
        }
}