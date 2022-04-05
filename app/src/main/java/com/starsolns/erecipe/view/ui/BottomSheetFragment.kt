package com.starsolns.erecipe.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.starsolns.erecipe.R
import com.starsolns.erecipe.databinding.FragmentBottomSheetBinding
import com.starsolns.erecipe.util.Constants
import com.starsolns.erecipe.viewmodel.SharedViewModel
import java.util.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    private var mealType = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietType = Constants.DEFAULT_DIET_TYPE
    private var dietTypeId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)


        sharedViewModel.readMealDietType.asLiveData().observe(viewLifecycleOwner){value->
            mealType = value.selectedMealType
            dietType = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedMealId ->
            val chip = group.findViewById<Chip>(checkedMealId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealType = selectedMealType
            mealTypeId = checkedMealId
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedDietId ->
            val chip = group.findViewById<Chip>(checkedDietId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietType = selectedDietType
            dietTypeId = checkedDietId
        }

        binding.applyFilterButton.setOnClickListener {
            sharedViewModel.saveMealDietSelection(mealType, mealTypeId, dietType, dietTypeId)
        }



        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if(chipId != 0 ){
            try{
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e: Exception){

            }
        }
    }
}