package com.example.bokamarkadur.POJO;

// A list of available subjects users can categorize their uploaded books to.
public enum Subjects {
    /*
     * Each entity class represents a table in the database.
     * This entity lists the Subjects available for books.
     */
    BIO("BIOLOGY"),
    BUS("BUSINESSADMINISTRATION"),
    CIL("CIVILENGINEERING"),
    COE("COMPUTATIONALENGINEERING"),
    COS("COMPUTERSCIENCE"),
    CHE("CHEMISTRY"),
    DEN("DENTISTRY"),
    EDU("EDUCATION"),
    ECO("ECONOMICS"),
    ENUM10("ENGLISH"),
    ENUM11("FRENCH"),
    ENUM12("GEOGRAPHY"),
    ENUM13("GEOLOGY"),
    ENUM14("GERMAN"),
    ENUM15("HEALTHSCIENCE"),
    ENUM16("LAW"),
    ENUM17("MEDICALSCIENCES"),
    ENUM18("NURSING"),
    ENUM19("PHYSICS"),
    ENUM20("PSYCHOLOGY"),
    ENUM21("SOFTWAREENGINEERING"),
    ENUM22("SPANISH");

    private String strSubject;

    private Subjects(String aSubject){
        strSubject = aSubject;
    }

    @Override public String toString(){
        return strSubject;
    }
}