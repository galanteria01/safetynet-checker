package com.shanu.safetynetchecker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.shanu.safetynetchecker.databinding.FragmentResultBinding
import com.shanu.safetynetchecker.model.SafetynetResultModel

class Result : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val args: ResultArgs by navArgs()

    private lateinit var data:SafetynetResultModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.evaluationText.text = data.evaluationType
        binding.basicIntegrityText.text = data.basicIntegrity
        binding.profileMatchText.text = data.profileMatch

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        data = args.data
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}