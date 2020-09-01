package com.example.firestorecrud;

import com.google.firebase.firestore.Exclude;

public class Note {
    private String documentId;
    private String email;
    private String password;
    public Note() {
        //public no-arg constructor needed
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public Note(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
