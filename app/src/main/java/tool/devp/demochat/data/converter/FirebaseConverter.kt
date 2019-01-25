package tool.devp.demochat.data.converter

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseConverter<T> {
    fun deserialize(dataSnapshot: DocumentSnapshot): T

    fun deserializes(query: QuerySnapshot) : List<T>

    /**
     * Data listener
     */
    fun deserialize(query: DocumentChange) : T
}