package sagar.mehar.rcarpet.ContactWork

import java.util.*

/**
 * Created by Mountain on 21-12-2017.
 */

class Contact
/**
 * Creates a new contact with the specified id.
 *
 * @param id The id of the contact.
 */
internal constructor(
        /**
         * The unique id of this contact.
         */
        val id: Long) {


    /**
     * The display name of this contact.
     */
    var displayName: String? = null


    //    /**
    //     * The email addresses of this contact.
    //     */
    //    public Set<String> emails = new HashSet<>();

    /**
     * The phone numbers of this contact.
     */
    var phoneNumbers: MutableSet<String> = HashSet()

    override fun hashCode(): Int {
        return (id xor id.ushr(32)).toInt()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val contact = o as Contact?
        return id == contact!!.id
    }
}
