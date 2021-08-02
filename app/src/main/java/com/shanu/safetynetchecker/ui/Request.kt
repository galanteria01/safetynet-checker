package com.shanu.safetynetchecker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.safetynet.SafetyNet
import com.shanu.safetynetchecker.R
import com.shanu.safetynetchecker.databinding.FragmentRequestBinding
import com.shanu.safetynetchecker.util.API_KEY
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.SecureRandom
import java.util.*

class Request : Fragment() {
    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    private val mRandom: Random = SecureRandom()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStatus.setOnClickListener {
            checkGoogleApi()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkGoogleApi() {
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(requireContext(), 13000000) ==
            ConnectionResult.SUCCESS
        ) {
            sendSafetynetRequest()
        } else {
            Toast.makeText(context,"Update your Google Play Services",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSafetynetRequest() {

        // Generating the nonce
        var noonceData = "Safety Net Data: " + System.currentTimeMillis()
        val nonce = getRequestNonce(noonceData)

        // Sending the request
        SafetyNet.getClient(activity).attest(nonce!!, API_KEY)
            .addOnSuccessListener {
                Log.d("data", it.jwsResult!!)
                print(it.jwsResult)
                findNavController().navigate(R.id.action_request_fragment_to_result_fragment)
            }
            .addOnFailureListener{
                if(it is ApiException) {
                    val apiException = it as ApiException
                    Log.d("data",it.message.toString() )

                }else {
                    Log.d("data", it.message.toString())
                }
            }
    }

    private fun getRequestNonce(data: String): ByteArray? {
        val byteStream = ByteArrayOutputStream()
        val bytes = ByteArray(24)
        mRandom.nextBytes(bytes)
        try {
            byteStream.write(bytes)
            byteStream.write(data.toByteArray())
        } catch (e: IOException) {
            return null
        }
        return byteStream.toByteArray()
    }
}