package com.wenbin.pojo;

public class User {
    private String username;
    private String password;
    private String sex;
    private String introduction;

    private Integer age;

    public User() {
    }

    public User(String username, String password, String sex, String introduction, Integer age) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.introduction = introduction;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
