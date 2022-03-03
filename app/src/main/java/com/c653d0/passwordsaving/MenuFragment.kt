package com.c653d0.passwordsaving

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.c653d0.passwordsaving.tool.EncryptInterface
import com.c653d0.passwordsaving.tool.FileOperate
import com.c653d0.passwordsaving.tool.RsaEncrypt
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MenuFragment : Fragment() {

    private var currentKey:TextView? = null
    private var addButton:Button? = null
    private var loadButton:Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_menu, container, false)

        currentKey = view.findViewById(R.id.currentKeyText)
        addButton = view.findViewById(R.id.generateButton)
        loadButton = view.findViewById(R.id.loadButton)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addButton?.setOnClickListener {
            //生成密钥对
            val encode:EncryptInterface = RsaEncrypt()
            encode.generateKey()
            //写入文件
            generateKey(requireContext(),encode.getEncryptionKey(),encode.getDecryptionKey())
        }
    }

    private fun generateKey(context: Context, publicKey:String,privateKey:String){
        val fileOperate = FileOperate()
        fileOperate.writeToFile("key_rsa.pub",publicKey,context)
        fileOperate.writeToFile("key_rsa",privateKey,context)
    }

}