
package com.example.architecture.model.enty;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    private int uid;
    private String name;

    // Getters and Setters - required for Room
    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }
}