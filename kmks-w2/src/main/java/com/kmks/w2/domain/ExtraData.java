package com.kmks.w2.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode
public class ExtraData {

    private String row1;
    private String row2;
    private String row3;
    private String row4;

    public String getRow1() {
        if (row1 == null){
            return " ";
        }
        return row1;
    }

    public void setRow1(String row1) {
        if (row1 == null){
            this.row1 = "";
            return;
        }
        this.row1 = row1;
    }

    public String getRow2() {
        if (row2 == null){
            return " ";
        }
        return row2;
    }

    public void setRow2(String row2) {
        if (row2 == null){
            this.row2 = "";
            return;
        }
        this.row2 = row2;
    }

    public String getRow3() {
        if (row3 == null){
            return " ";
        }
        return row3;
    }

    public void setRow3(String row3) {
        if (row3 == null){
            this.row3 = "";
            return;
        }
        this.row3 = row3;
    }

    public String getRow4() {
        if (row4 == null){
            return " ";
        }
        return row4;
    }

    public void setRow4(String row4) {
        if (row4 == null){
            this.row4 = "";
            return;
        }
        this.row4 = row4;
    }

    @Override
    public String toString() {
        return "ExtraData{" +
                "row1='" + row1 + '\'' +
                ", row2='" + row2 + '\'' +
                ", row3='" + row3 + '\'' +
                ", row4='" + row4 + '\'' +
                '}';
    }
}
