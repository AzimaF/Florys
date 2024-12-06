package com.example.florys.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.florys.R
import com.example.florys.databinding.FragmentChatBinding

class ChatFragment : Fragment () {
    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_chat, container, false)
        val ChatViewModel =
            ViewModelProvider(this)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root


    }
}

