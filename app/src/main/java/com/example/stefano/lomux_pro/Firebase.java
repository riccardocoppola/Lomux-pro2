package com.example.stefano.lomux_pro;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Firebase{

    public static class Firestore {

        public static boolean addObjet(Object value, FirestoreListenerAddObject listener) {
            if (Utility.isInternetAvailable()) {
                FirebaseFirestore.getInstance().collection(value.getClass().getSimpleName())
                        .add(value)
                        .addOnSuccessListener(listener)
                        .addOnFailureListener(listener);
                return true;
            }
            else{
                listener.onFailure(new Utility.InternetException("Nessuna connessione internet"));
            } return false;
        }

        public static void readCollection(Class objectClass, Firebase.FirestoreListenerGetObject listener){
            FirebaseFirestore.getInstance().collection(objectClass.getSimpleName())
                    .get()
                    .addOnCompleteListener(listener);
        }

    }
    public interface FirestoreListenerAddObject extends OnSuccessListener<DocumentReference>,OnFailureListener {}
    public interface FirestoreListenerGetObject extends OnCompleteListener<QuerySnapshot> {}
}
