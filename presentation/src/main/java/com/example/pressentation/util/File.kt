package com.example.pressentation.ui.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.webkit.MimeTypeMap
import java.io.*




private fun getFilePathFromUri(context: Context, uri: Uri): String =
    if (uri.path?.contains("file://") == true) {
        uri.path!!
    } else {
        var file= getFileFromContentUri(context, uri)
        file.path
    }

@SuppressLint("SuspiciousIndentation")
private fun getFileFromContentUri(context: Context, contentUri: Uri): File {

    val fileExtension = getFileExtension(context, contentUri) ?: ""
    var fileName = "temp_file.$fileExtension"
    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()

    var oStream: FileOutputStream? = null
    var inputStream: InputStream? = null

    try {
        oStream = FileOutputStream(tempFile)
        inputStream = context.contentResolver.openInputStream(contentUri)

        inputStream?.let { copy(inputStream, oStream) }

        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // Close streams
        inputStream?.close()
        oStream?.close()
    }
    return tempFile
}

private fun getFileExtension(context: Context, uri: Uri): String? =
    if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
    } else {
        uri.path?.let { MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString()) }
    }

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buffer = ByteArray(8192)
    var length: Int
    while (source.read(buffer).also { length = it } > 0) {
        target.write(buffer, 0, length)
    }

}

fun getAvailableInternalMemorySize(): Long {
    val path = Environment.getDataDirectory()
    val stat = StatFs(path.path)
    val blockSize: Long = stat.blockSizeLong
    val availableBlocks: Long = stat.availableBlocksLong
    return availableBlocks * blockSize
}

@SuppressLint("Recycle")
fun getFileSize(context: Context, uri: Uri): Long {
    val fileDescriptor: AssetFileDescriptor =
        context.contentResolver.openAssetFileDescriptor(uri, "r")!!
    return fileDescriptor.length
}

fun getRealPathFromBitmapImage(context: Context, bitmap: Bitmap): String {

    val name="temp_file.jpg"
    val file = File(context.cacheDir, name)
    try {
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file.path

}


 fun getRealPath(context: Context, uri: Uri):String{
    if(getFileSize(context,uri) < getAvailableInternalMemorySize())
        return  getFilePathFromUri(context,uri)
    else
        return ""
}

