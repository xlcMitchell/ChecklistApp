package com.example.beforeyougo.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "checklists")
public class ChecklistEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public boolean isTemplate;

    public long createdAt;
    public long updatedAt;
    public Long lastUsedAt; // nullable

    public ChecklistEntity(String name, boolean isTemplate) {
        this.name = name;
        this.isTemplate = isTemplate;
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.lastUsedAt = null;
    }
}
