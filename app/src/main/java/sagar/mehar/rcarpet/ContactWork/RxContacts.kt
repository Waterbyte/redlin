package sagar.mehar.rcarpet.ContactWork

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.LongSparseArray

import io.reactivex.annotations.NonNull
import rx.Observable
import rx.Subscriber

import sagar.mehar.rcarpet.ContactWork.ColumnMapper.mapDisplayName
import sagar.mehar.rcarpet.ContactWork.ColumnMapper.mapPhoneNumber

/**
 * Created by Mountain on 21-12-2017.
 */

class RxContacts(private val context: Context) {

    private val resolver: ContentResolver

    init {
        resolver = context.contentResolver
    }

    private fun fetch(subscriber: Subscriber<in Contact>) {
        val contacts = LongSparseArray<Contact>()
        // Create a new cursor and go to the first position
        val cursor = createCursor()
        cursor!!.moveToFirst()
        // Get the column indexes
        val idxId = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
        val idxMimetype = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
        val idxData1 = cursor.getColumnIndex(ContactsContract.Data.DATA1)
        val idxDisplayNamePrimary = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
        // Map the columns to the fields of the contact
        while (!cursor.isAfterLast) {
            // Get the id and the contact for this id. The contact may be a null.
            val id = cursor.getLong(idxId)
            var contact: Contact? = contacts.get(id,
                    null)
            if (contact == null) {
                // Create a new contact
                contact = Contact(id)
                // Add the contact to the collection
                mapDisplayName(cursor, contact, idxDisplayNamePrimary)
                mapPhoneNumber(cursor, contact, idxData1)
                contacts.put(id, contact)

            } else {
                val mimetype = cursor.getString(idxMimetype)
                when (mimetype) {
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        mapPhoneNumber(cursor, contact, idxData1)
                    }
                }
            }
            cursor.moveToNext()
        }
        // Close the cursor
        cursor.close()
        // Emit the contacts
        for (i in 0 until contacts.size()) {
            subscriber.onNext(contacts.valueAt(i))
        }
        subscriber.onCompleted()
    }


    private fun createCursor(): Cursor? {
        return resolver.query(
                ContactsContract.Data.CONTENT_URI,
                PROJECTION, null, null,
                ContactsContract.Data.CONTACT_ID
        )
    }

    companion object {

        private val PROJECTION = arrayOf(ContactsContract.Data.CONTACT_ID, ContactsContract.Data.DISPLAY_NAME_PRIMARY, ContactsContract.Data.STARRED, ContactsContract.Data.PHOTO_URI, ContactsContract.Data.PHOTO_THUMBNAIL_URI, ContactsContract.Data.DATA1, ContactsContract.Data.MIMETYPE, ContactsContract.Data.IN_VISIBLE_GROUP)

        fun fetch(@NonNull context: Context): Observable<Contact> {
            return Observable.create { subscriber -> RxContacts(context).fetch(subscriber) }
        }
    }

}
