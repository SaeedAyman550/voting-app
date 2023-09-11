package com.example.pressentation.ui.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.utils.Resourse
import com.example.pressentation.R
import com.example.pressentation.databinding.FragmentSetTimeBinding
import com.example.pressentation.sharedViewModel.GetTimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetTime : Fragment(R.layout.fragment_set_time) {

    private val adminViewModel: AdminViewModel by viewModels()
    private val getTimeViewModel: GetTimeViewModel by viewModels()

    lateinit var binding:FragmentSetTimeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentSetTimeBinding.bind(view)


            binding.setTimeSaveButton.setOnClickListener {

                val hours=getStringFromEditText(binding.setTimeEditText)
                if(checkNotEmpty(hours))
                adminViewModel.setTime(hours.toInt())
                else
                    Toast.makeText(requireContext(),"number of hours is empty",Toast.LENGTH_LONG).show()
            }

        lifecycleScope.launchWhenCreated {
            getTimeViewModel.get_time_state_flow.collect{
                when(it){
                    is Resourse.Loading-> binding.setTimeProgressPar.visibility=View.VISIBLE

                    is Resourse.Success-> {
                        binding.setTimeProgressPar.visibility=View.GONE
                        updateView(it.data!!.end_at)
                    }
                    is Resourse.Error-> {
                        binding.setTimeProgressPar.visibility=View.GONE
                        Log.d("saeed","${it.message} error ")
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated{
            adminViewModel.set_time_state_flow.collect {

                when(it){
                    is Resourse.Loading-> binding.setTimeProgressPar.visibility=View.VISIBLE

                    is Resourse.Success-> {
                        binding.setTimeProgressPar.visibility=View.GONE
                        Toast.makeText(requireContext(),"sucess set time",Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    is Resourse.Error-> {
                        binding.setTimeProgressPar.visibility=View.GONE
                        Log.d("saeed","${it.message} error ")
                    }


                }

            }
        }

    }

    private fun updateView(date:String){
        if (date.isNotEmpty()){
            binding.setTimeTextNormal.text=
                "${getString(R.string.set_time_time_to_update_voting_time_before_time)} ${date} ${getString(R.string.set_time_time_to_update_voting_time_after_time)}"
        }
    }

    private fun checkNotEmpty(number_of_hour:String):Boolean= number_of_hour.isNotEmpty()

    private fun getStringFromEditText(edit_text: EditText):String=edit_text.text.toString()
}