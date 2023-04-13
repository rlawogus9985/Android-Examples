package com.example.lovetest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lovetest.R
import com.example.lovetest.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ResultFragment : Fragment() {

    var option = - 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        option = arguments?.getInt("index") ?: -1

        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    lateinit var navController: NavController
    lateinit var binding: FragmentResultBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setResult(option)
        binding.btnHome.setOnClickListener{
            navController.navigate(R.id.action_resultFragment_to_mainFragment)
        }
    }

    fun setResult(option: Int){
        when (option) {
            1 -> {
                binding.tvMain.text = "You are a QUITTER!"
                binding.tvSub.text = "You can let the person easily"
            }
            2 -> {
                binding.tvMain.text = "You should focus on yourself!"
                binding.tvSub.text = "You become really clingy to your ex"
            }
            3 -> {
                binding.tvMain.text = "You should take it easy"
                binding.tvSub.text = "You can do crazy things no matter what it takes"
            }
            4 -> {
                binding.tvMain.text = "You are pretty mature"
                binding.tvSub.text = "You can easily accept the break-up"
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}