package com.example.The_Swap.Model;

import jakarta.validation.constraints.NotNull;

public class Student {
    @NotNull(message = "Please enter student number")
    private long studentNo;
    @NotNull(message = "Please enter student surname")
    private String surname;
    @NotNull(message = "Please enter student full name")
    private String fullName;
    @NotNull(message = "Please enter attendance rate")
    private int attendanceRate;

    public long getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(long studentNo) {
        this.studentNo = studentNo;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(int attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public Student(long studentNo, String surname, String fullName, int attendanceRate) {
        this.studentNo = studentNo;
        this.surname = surname;
        this.fullName = fullName;
        this.attendanceRate = attendanceRate;
    }
}
