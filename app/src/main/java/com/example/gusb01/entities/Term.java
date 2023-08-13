package com.example.gusb01.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private double termNumber;

    public Term(int termID, String termName, double termNumber) {
        this.termID = termID;
        this.termName = termName;
        this.termNumber = termNumber;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public double getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(double termNumber) {
        this.termNumber = termNumber;
    }

    @Override
    public String toString() {
        return "Associated Term:" + termName;
        /*return "Term{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                ", termNumber=" + termNumber +
                '}';*/
    }
}
