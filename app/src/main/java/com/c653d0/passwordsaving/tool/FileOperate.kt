package com.c653d0.passwordsaving.tool

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.RandomAccessFile
import java.lang.Exception

class FileOperate {
    fun writeToFile(fileName:String, text:String,context:Context){
        makeFileDirectory(context.getExternalFilesDir("")!!.path)
        val file = makeFile(context.getExternalFilesDir("KEY")!!.path + "/$fileName")

        //清空文件内容
        FileWriter(file).let {
            it.write("")
            it.flush()
            it.close()
        }

        //写入内容
        RandomAccessFile(file, "rwd").let {
            it.write(text.toByteArray())
            it.close()
        }
    }

    fun readFromFile(context : Context , fileName:String):String{
        val file:File = File(context.getExternalFilesDir("KEY"),fileName)
        var res = ""

        try {
            res = file.readText()
        }catch (e:FileNotFoundException){
            Log.d("FileOperateErr", "readFromFile: FileNotFound")
        }

        return res
    }


    //生成文件
    private fun makeFile(filePath: String): File {
        var file: File? = null
        try {
            file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }
        } catch (e: Exception) {
            Log.i("makeFileError", "makeFile: $e")
        }
        return file!!
    }

    //生成文件夹
    private fun makeFileDirectory(dirPath: String) {
        var file: File? = null
        try {
            file = File(dirPath)
            if (!file.exists()) {
                file.mkdir()
            }
        } catch (e: Exception) {
            Log.i("makeFileDirectoryError", "makeFileDirectory: $e")
        }
    }
}