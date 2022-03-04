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

        var isEdit:Boolean = false
        var dataIn:PasswordInfo? = null

        arguments?.get("data").apply{
            if(this!=null){
                dataIn = this as PasswordInfo
                isEdit = true

                //将密码进行解密
                val fileOperate = FileOperate()
                val privateKey = fileOperate.readFromFile(requireContext(), "key_rsa")
                val encode: EncryptInterface = RsaEncrypt()
                val decryptPassword = encode.decrypt(dataIn!!.password, privateKey)

                //将数据填充到对应的文本框内
                editContentLabel?.setText(dataIn!!.label)
                editContentId?.setText(dataIn!!.uid)
                editContentPassword?.setText(decryptPassword)
                editContentDescription?.setText(dataIn!!.description)

                //更改保存按钮上的文字
                detailPageSaveButton?.setText(R.string.update_button)
            }
        }

        //设置保存按钮点击事件
        detailPageSaveButton?.setOnClickListener {

            //将文本框内的数据整合到data对象中
            val data = saveData(dataIn?.id?:0)

            //判断进行更新还是插入操作
            data?.apply {
                if(isEdit){
                    viewModel.update(this)
                }else{
                    viewModel.insert(this)
                }
                //操作结束返回上一层界面
                it.findNavController().navigateUp()
            }
        }

        //取消按钮点击事件
        detailPageCancelButton?.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    //检查文件是否存在
    private fun check(context: Context, fileName: String):Boolean{
        val file = File(context.getExternalFilesDir("KEY")!!.path + "/$fileName")
        return file.exists()
    }

    //将数据保存到一个对象中
    private fun saveData(id:Int):PasswordInfo?{
        val label:String = editContentLabel?.text.toString()
        val uid:String = editContentId?.text.toString()
        val passwordText:String = editContentPassword?.text.toString()
        val description:String = editContentDescription?.text.toString()

        //判断数据是否为空，若为空提醒用户补充数据
        if("" == label || "" == uid || "" == passwordText){
            Toast.makeText(requireContext(),requireContext().getString(R.string.missing_content_prompt).toString(),Toast.LENGTH_SHORT).show()
        }else{
            //判断数据规范，若密码长度超过限制，不进行保存并提醒
            if(passwordText.length>20){
                Toast.makeText(requireContext(),requireContext().getText(R.string.exceeds_limit),Toast.LENGTH_SHORT).show()
                return null
            }

            //对密钥文件进行检查，若存在进行加密存储，若不存在提醒当前没有密钥
            if(check(requireContext(),"key_rsa.pub")) {
                //将密码进行加密
                val fileOperate = FileOperate()
                val publicKey = fileOperate.readFromFile(requireContext(), "key_rsa.pub")
                val encode: EncryptInterface = RsaEncrypt()
                val encodePassword = encode.encrypt(passwordText, publicKey)
                //返回加密后的数据对象
                return PasswordInfo(id, label, uid, encodePassword, description)
            }else{
                Toast.makeText(requireContext(),"NoKey",Toast.LENGTH_SHORT).show()
            }
        }

        return null
    }
}