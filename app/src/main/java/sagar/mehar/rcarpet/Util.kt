package sagar.mehar.rcarpet

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


/**
 * Created by Mountain on 21-12-2017.
 */

object Util {
    @Throws(IOException::class)
    fun zip(files: Array<String>, zipFileName: String){
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFileName))).use { out ->
            val data = ByteArray(64)
            for (file in files) {
                FileInputStream(file).use { fi ->
                    BufferedInputStream(fi).use { origin ->
                        val entry = ZipEntry(file)
                        out.putNextEntry(entry)
                        while (true) {
                            val readBytes = origin.read(data)
                            if (readBytes == -1) {
                                break
                            }
                            out.write(data, 0, readBytes)
                        }
                    }
                }
            }
        }
    }


}
