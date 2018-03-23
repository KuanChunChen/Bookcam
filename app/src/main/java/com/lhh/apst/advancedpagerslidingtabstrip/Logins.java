package com.lhh.apst.advancedpagerslidingtabstrip;

import java.io.Serializable;

public class Logins implements Serializable {
    private int id;
    private String nick;
    private String name;
    private String password;

    public Logins(String name) {
        this(0, name);
    }

    public Logins(int id, String name) {
        this.id = id;
        this.name=name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

   /* public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

   /* public void setPassword(String password) {
        this.password = password;}

    public String getPassword() {
        return password;
    }*/


}
