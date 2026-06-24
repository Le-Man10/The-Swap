package com.example.The_Swap.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class Student {
    @NotNull(message = "Please enter student number")
    @Size(min = 9,max = 9,message = "student number must have 9 characters")
    private String studentNo;
    @NotNull(message = "Please enter student surname")
    private String surname;
    @NotNull(message = "Please enter student full name")
    private String fullName;
    @Max(value = 100, message = "Attendance rate must be less that 100")
    @Min(value = 0,message = "Attendance rate must be more that 0")
    @NotNull(message = "Please enter attendance rate")
    private int attendanceRate;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
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

    public Student(String studentNo, String surname, String fullName, int attendanceRate) {
        this.studentNo = studentNo;
        this.surname = surname;
        this.fullName = fullName;
        this.attendanceRate = attendanceRate;
    }
}
