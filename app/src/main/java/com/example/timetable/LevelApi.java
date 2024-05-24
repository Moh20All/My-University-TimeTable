package com.example.timetable;

public class LevelApi {
    private int id_niv_spec;
    private int idspecialty;
    private int idniveau;
    private int Actif;


    public LevelApi(int id_niv_spec, int idspecialty, int idniveau, int actif) {
        this.id_niv_spec = id_niv_spec;
        this.idspecialty = idspecialty;
        this.idniveau = idniveau;
        Actif = actif;
    }

    public int getId_niv_spec() {
        return id_niv_spec;
    }

    public int getIdspecialty() {
        return idspecialty;
    }

    public int getIdniveau() {
        return idniveau;
    }

    public int getActif() {
        return Actif;
    }
}
