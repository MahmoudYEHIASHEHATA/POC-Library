package com.cassbana.antifraud.workers.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*
import com.fingerprintjs.android.fingerprint.Configuration
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import com.fingerprintjs.android.fingerprint.signal_providers.StabilityLevel

@ExperimentalCoroutinesApi
object RSUniqueIDGenerator {

    private const val TAG = "UniqueIDGenerator"
    private const val UUID_DIRECTORY_NAME = "Cassbana"
    private const val UUID_FILE_NAME = "Key"
    private const val UUID_FILE_EXTENSION = ".jpg"
    private const val UUID_FILE_NAME_WITH_EXTENSION = "$UUID_FILE_NAME$UUID_FILE_EXTENSION"
    private lateinit var uuid: String
    private lateinit var fingerPrintID: String

    fun getUUID(context: Context): String {
        if (RSUniqueIDGenerator::uuid.isInitialized)
            return uuid

        uuid = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            getUUIDAboveQ(context)
        else
            getUUIDBelowQ()

        return uuid
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range", "Recycle")
    private fun getUUIDAboveQ(context: Context): String {
        val contentUri = MediaStore.Files.getContentUri("external")
        val selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?"
        val selectionArgs = arrayOf(Environment.DIRECTORY_DOCUMENTS + "/$UUID_DIRECTORY_NAME/")
        val cursor: Cursor = context.contentResolver.query(
            contentUri,
            null,
            selection,
            selectionArgs,
            null
        )!!
        if (cursor.count == 0) {
            return writeUuidAboveQ(context)
        } else {
            var uri: Uri? = null
            while (cursor.moveToNext()) {
                val fileName: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                if (fileName == UUID_FILE_NAME_WITH_EXTENSION) {
                    val id: Long =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                    uri = ContentUris.withAppendedId(contentUri, id)
                    break
                }
            }
            return if (uri == null) {
                writeUuidAboveQ(context)
            } else {
                try {
                    readUUIDAboveQ(context, uri)
                } catch (e: IOException) {
                    Timber.tag(TAG).d(e.toString())
                    ""
                }
            }
        }
    }

    private fun writeUuidAboveQ(context: Context): String {
        return try {
            val values = ContentValues()
            values.put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                UUID_FILE_NAME
            ) //file name
            values.put(
                MediaStore.MediaColumns.MIME_TYPE,
                "image/jpeg"
            ) //file extension, will automatically add to file
            values.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS + "/$UUID_DIRECTORY_NAME/"
            ) //end "/" is not mandatory
            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                values
            ) //important!
            val outputStream: OutputStream? = context.contentResolver.openOutputStream(uri!!)
            val uuid = generateUUID()
            outputStream?.write(uuid.toByteArray())
            outputStream?.close()
            uuid
        } catch (e: IOException) {
            Timber.tag(TAG).d(e.toString())
            e.toString()
        }
    }

    private fun readUUIDAboveQ(context: Context, uri: Uri): String {
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        val size: Int = inputStream.available()
        val bytes = ByteArray(size)
        inputStream.read(bytes)
        inputStream.close()
        return String(bytes, StandardCharsets.UTF_8)
    }

    @Synchronized
    private fun getUUIDBelowQ(): String {
        val dir = File(
            Environment.getExternalStorageDirectory().path + "/$UUID_DIRECTORY_NAME/"
        )
        dir.mkdirs()
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val uuidFile = File(dir, UUID_FILE_NAME)
        return try {
            if (!uuidFile.exists())
                writeUuidFile(uuidFile)
            else
                readUuidFile(uuidFile)
        } catch (e: Exception) {
            Timber.tag(TAG).d(e.toString())
            ""
        }
    }

    @Throws(IOException::class)
    private fun readUuidFile(uuidFile: File): String {
        val f = RandomAccessFile(uuidFile, "rw")
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeUuidFile(uuidFile: File): String {
        val out = FileOutputStream(uuidFile)
        val uuid = generateUUID()
        out.write(uuid.toByteArray())
        out.close()
        return uuid
    }

    private fun generateUUID(): String = UUID.randomUUID().toString()

    fun getFingerPrinterIdFlow(context: Context): Flow<String> {
        val fingerPrinter = FingerprinterFactory
            .getInstance(
                context,
                Configuration(version = 3)
            )

        return callbackFlow {
            fingerPrinter.getFingerprint(StabilityLevel.STABLE) { fingerprintResult ->
                trySend(fingerprintResult.fingerprint)
                close()
            }
            awaitClose {
            }
        }
    }

    suspend fun getFingerPrintID(context: Context): String {
        return if (RSUniqueIDGenerator::fingerPrintID.isInitialized)
            fingerPrintID
        else {
            fingerPrintID = getFingerPrinterIdFlow(context).first()
            fingerPrintID
        }
    }

}
