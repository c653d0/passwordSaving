package com.c653d0.passwordsaving

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.c653d0.passwordsaving.database.PasswordInfo
import com.c653d0.passwordsaving.tool.EncryptInterface
import com.c653d0.passwordsaving.tool.FileOperate
import com.c653d0.passwordsaving.tool.RsaEncrypt
import java.io.File

class DetailInfoFragment : Fragment() {
    private var editContentLabel:EditText? = null
    private var editContentId:EditText? = null
    private var editContentPassword:EditText? = null
    private var editContentDescription:EditText? = null
    private var detailPageCancelButton: Button? = null
    private var detailPageSaveButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_detail_info, container, false)

        editContentLabel = view.findViewById(R.id.edit_content_label)
        editContentId = view.findViewById(R.id.edit_content_id)
        editContentPassword = view.findViewById(R.id.edit_content_password)
        editContentDescription = view.findViewById(R.id.edit_content_description)
        detailPageCancelButton = view.findViewById(R.id.detail_page_cancel_button)
        detailPageSaveButton = view.findViewById(R.id.detail_page_save_button)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //创建viewModel
        val viewModel:PasswordViewModel by viewModels {
            PasswordViewModelFactory(requireActivity().application)
        }

        //设置点击事件
        detailPageSaveButton?.setOnClickListener {
            val label:String = editContentLabel?.text.toString()
            val uid:String = editContentId?.text.toString()
            val passwordText:String = editContentPassword?.text.toString()
            val description:String = editContentDescription?.text.toString()


            if("" == label || "" == uid || "" == passwordText){
                Toast.makeText(requireContext(),requireContext().getString(R.string.missing_content_prompt).toString(),Toast.LENGTH_SHORT).show()
            }else{
                if(check(requireContext(),"key_rsa.pub")){
                    //将密码进行加密
                    val fileOperate = FileOperate()
                    val publicKey = fileOperate.readFromFile(requireContext(),"key_rsa.pub")
                    val encode:EncryptInterface = RsaEncrypt()
                    val encodePassword = encode.encrypt(passwordText,publicKey)
                    //存放数据到数据库
                    val password:PasswordInfo = PasswordInfo(0,label,uid,encodePassword,description)
                    viewModel.insert(password)
                    Toast.makeText(requireContext(),requireContext().getString(R.string.add_successfully),Toast.LENGTH_SHORT).show()
                    it.findNavController().navigateUp()
                }else{
                    Toast.makeText(requireContext(),"no",Toast.LENGTH_SHORT).show()
                }
            }
        }

        detailPageCancelButton?.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun check(context: Context, fileName: String):Boolean{
        val file = File(context.getExternalFilesDir("KEY")!!.path + "/$fileName")
        return file.exists()
    }
}