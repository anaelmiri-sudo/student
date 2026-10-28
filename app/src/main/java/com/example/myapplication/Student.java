package com.example.myapplication;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String studentClass;
    private String major;
    private boolean notifications;

    public Student(String firstName, String lastName, String email, String studentClass, String major, boolean notifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentClass = studentClass;
        this.major = major;
        this.notifications = notifications;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getStudentClass() { return studentClass; }
    public String getMajor() { return major; }
    public boolean isNotifications() { return notifications; }

    @Override
    public String toString() {
        return "שם פרטי: " + firstName +
                "\nשם משפחה: " + lastName +
                "\nאימייל: " + email +
                "\nכיתה: " + studentClass +
                "\nמגמה: " + major +
                "\nקבלת התראות: " + (notifications ? "כן" : "לא");
    }
}