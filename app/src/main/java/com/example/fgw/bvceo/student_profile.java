package com.example.fgw.bvceo;

public class student_profile {

    String name,Branch,Shift,image;

    public student_profile(String name, String branch, String shift) {
        this.name = name;
        Branch = branch;
        Shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
