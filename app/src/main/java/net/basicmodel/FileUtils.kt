package net.basicmodel

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


object FileUtils {
    private val SEPARATOR = File.separator

    /**
     * 复制res/raw中的文件到指定目录
     * @param context 上下文
     * @param id 资源ID
     * @param fileName 文件名
     * @param storagePath 目标文件夹的路径
     */
    fun copyFilesFromRaw(context: Context, id: Int, fileName: String, storagePath: String) {
        val inputStream: InputStream = context.resources.openRawResource(id)
        val file = File(storagePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        val path = storagePath + SEPARATOR + fileName
        if (!File(path).exists()) {
            readInputStream(path, inputStream)
        }
    }

    private fun readInputStream(storagePath: String, inputStream: InputStream) {
        val file = File(storagePath)
        try {
            if (!file.exists()) {
                val fos = FileOutputStream(file)
                val buffer = ByteArray(inputStream.available())
                var lenght = 0
                while ((inputStream.read(buffer).also { lenght = it }) != -1) {
                    fos.write(buffer, 0, lenght)
                }
                fos.flush()
                fos.close()
                inputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}