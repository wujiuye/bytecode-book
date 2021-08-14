package com.wujiuye.asmbytecode.book.third.model;

public class User {

    private String name;
    private Integer age;
    private String e_mail;
    private String phone;

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public User setE_mail(String e_mail) {
        this.e_mail = e_mail;
        return this;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPhone() {
        return phone;
    }

}
