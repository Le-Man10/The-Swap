package com.example.The_Swap.Model;
import jakarta.validation.constraints.NotNull;



import java.util.List;

public class Group {
    @NotNull(message = "Please enter group number")
    private int groupNo;
    @NotNull(message = "Please enter client name")
    private String clientName;
    @NotNull(message = "Please enter client email")
    private String clientEmail;
    @NotNull(message = "student details are missing")
    private List<Student> partners;

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public List<Student> getPartners() {
        return partners;
    }

    public void setPartners(List<Student> partners) {
        this.partners = partners;
    }

    public Group(int groupNo, String clientName, String clientEmail, List<Student> partners) {
        this.groupNo = groupNo;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.partners = partners;
    }
}
