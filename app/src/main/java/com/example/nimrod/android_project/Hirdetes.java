package com.example.nimrod.android_project;

import java.util.ArrayList;

/**
 * Created by Nimrod on 12/12/2017.
 */

public class Hirdetes {

    public String cim;
    public String author;
    public String rovidLeiras;
    public String hosszuLeiras;
    public ArrayList<String> imageRef;
    public String kep;

    public Hirdetes(){


    }

    public Hirdetes(String author,String cim, String rovidLeiras, String hosszuLeiras,String kep){

        this.author = author;
        this.cim = cim;
        this.rovidLeiras = rovidLeiras;
        this.hosszuLeiras = hosszuLeiras;
        this.kep = kep;
     //   imageRef = new ArrayList<String>();

    }

    public void AddImageRef(String image){
        imageRef.add(image);

    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRovidLeiras(String rovidLeiras) {
        this.rovidLeiras = rovidLeiras;
    }

    public void setHosszuLeiras(String hosszuLeiras) {
        this.hosszuLeiras = hosszuLeiras;
    }

    public void setKep(String kep) {
        this.kep = kep;
    }

    public String getCim() {
        return cim;
    }

    public String getAuthor() {
        return author;
    }

    public String getRovidLeiras() {
        return rovidLeiras;
    }

    public String getHosszuLeiras() {
        return hosszuLeiras;
    }

    public String getKep() {
        return kep;
    }
}
