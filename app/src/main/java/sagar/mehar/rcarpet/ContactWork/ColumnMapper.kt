package sagar.mehar.rcarpet.ContactWork

import android.database.Cursor

/**
 * Created by Mountain on 21-12-2017.
 */

internal object ColumnMapper {


    fun mapDisplayName(cursor: Cursor, contact: Contact, columnIndex: Int) {
        val displayName = cursor.getString(columnIndex)
        if (displayName != null && !displayName.isEmpty()) {
            contact.displayName = displayName
        }
    }


    fun mapPhoneNumber(cursor: Cursor, contact: Contact, columnIndex: Int) {
        var phoneNumber: String? = cursor.getString(columnIndex)
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Remove all whitespaces
            phoneNumber = phoneNumber.replace("\\s+".toRegex(), "")
            contact.phoneNumbers.add(phoneNumber)
        }
    }


}// Utility class -> No instances allowed

