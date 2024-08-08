# Firebase
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepclassmembers class com.google.firebase.auth.FirebaseAuth {
    *;
}
-keepclassmembers class com.google.firebase.firestore.FirebaseFirestore {
    *;
}
-keepclassmembers class com.google.firebase.firestore.QuerySnapshot {
    *;
}
-keepclassmembers class com.google.firebase.firestore.DocumentSnapshot {
    *;
}
-keepclassmembers class com.google.firebase.firestore.WriteBatch {
    *;
}
-keepclassmembers class com.google.firebase.firestore.Transaction {
    *;
}
-keepclassmembers class com.google.firebase.firestore.DocumentReference {
    *;
}
-keepclassmembers class com.google.firebase.firestore.QueryDocumentSnapshot {
    *;
}
-keepclassmembers class com.google.firebase.firestore.FieldValue {
    *;
}
