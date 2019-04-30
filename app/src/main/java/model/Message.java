package model;

import java.io.Serializable;

public class Message implements Serializable {

    String idConv = "";
    String id_emmeteur = "";
    String id_receveur = "";
    String message = "";
    String date = "";

    public String getIdConv() {
        return idConv;
    }

    public void setIdConv(String idConv) {
        this.idConv = idConv;
    }

    public String getId_emmeteur() {
        return id_emmeteur;
    }

    public void setId_emmeteur(String id_emmeteur) {
        this.id_emmeteur = id_emmeteur;
    }

    public String getId_receveur() {
        return id_receveur;
    }

    public void setId_receveur(String id_receveur) {
        this.id_receveur = id_receveur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
