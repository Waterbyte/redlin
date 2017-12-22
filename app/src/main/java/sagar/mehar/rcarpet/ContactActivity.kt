package sagar.mehar.rcarpet

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import com.opencsv.CSVWriter
import rx.Observer
import sagar.mehar.rcarpet.ContactWork.Contact
import sagar.mehar.rcarpet.ContactWork.RxContacts
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

class ContactActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var contactStart: Button 
    internal lateinit var relativeLayout: RelativeLayout
    internal var entries: MutableList<Array<String>> = ArrayList()
    private val snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        relativeLayout = findViewById(R.id.relativeLayout) as RelativeLayout
        contactStart = findViewById(R.id.contactStart) as Button
        contactStart.setOnClickListener(this)
    }

    fun askCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), CONTACT_PERMISSION)  //request camera permission if not given
            else {
                startScanning()
            }
        } else {
            startScanning()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CONTACT_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScanning()
                } else {
                    Toast.makeText(this@ContactActivity, "Dear, We need Contact Access!", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun startScanning() {
        RxContacts.fetch(this@ContactActivity)
                .subscribe(object : Observer<Contact> {
                    override fun onCompleted() {
                        try {
                            writeCSV()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, e.message.toString())
                    }

                    override fun onNext(contact: Contact) {
                        val contactNums = contact.phoneNumbers!!
                        Log.e("Entries",contactNums.toString())
                        for (contactNum in contactNums) {
                            val data = arrayOf<String>(contact.displayName!!, contactNum)
                            entries.add(data)
                        }
                    }
                })
    }

    @Throws(IOException::class)
    private fun writeCSV() {
        val fileName = "contacts.csv"
        val file = File(this.filesDir.toString() + "/" + fileName)
        val csvWriter = CSVWriter(FileWriter(file),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)
        run {
            val headerRecord = arrayOf("Name", "Phone")
            csvWriter.writeNext(headerRecord)
            csvWriter.writeAll(entries)
        }
        csvWriter.close()
        val filePath = arrayOf(file.path)

        try {
            Util.zip(filePath, file.parent + "/" + "Contacts1.zip")
            val snackbar = Snackbar
                    .make(relativeLayout, "Contact Data zipped", Snackbar.LENGTH_LONG)

            snackbar.show()
        } catch (e: Exception) {
                e.printStackTrace();
        }

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.contactStart -> askCameraPermission()
        }
    }

    companion object {
        private val CONTACT_PERMISSION = 4324
        private val TAG = "ContactActivity"
    }


}

