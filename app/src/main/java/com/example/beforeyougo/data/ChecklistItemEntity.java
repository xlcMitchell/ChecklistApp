package com.example.beforeyougo.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "checklist_items",
        foreignKeys = @ForeignKey(
                entity = ChecklistEntity.class,
                parentColumns = "id",
                childColumns = "checklistId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("checklistId")}
)
public class ChecklistItemEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long checklistId;
    public String text;
    public boolean isChecked;
    public int sortOrder;

    public long createdAt;
    public long updatedAt;

    public ChecklistItemEntity(long checklistId, String text, int sortOrder) {
        this.checklistId = checklistId;
        this.text = text;
        this.sortOrder = sortOrder;
        this.isChecked = false;
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
