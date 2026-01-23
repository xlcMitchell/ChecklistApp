package com.example.beforeyougo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChecklistDao {

    @Query("SELECT c.*, " +
            "COUNT(i.id) AS totalCount, " +
            "COALESCE(SUM(CASE WHEN i.isChecked = 1 THEN 1 ELSE 0 END), 0) AS checkedCount " +
            "FROM checklists c " +
            "LEFT JOIN checklist_items i ON i.checklistId = c.id " +
            "WHERE c.isTemplate = 0 " +
            "GROUP BY c.id " +
            "ORDER BY COALESCE(c.lastUsedAt, c.createdAt) DESC")
    LiveData<List<ChecklistSummary>> observeUserChecklists();

    @Query("SELECT COUNT(*) FROM checklists WHERE isTemplate = 0")
    int getUserChecklistCount();

    @Query("SELECT * FROM checklists WHERE id = :id")
    LiveData<ChecklistEntity> observeChecklist(long id);

    @Query("SELECT * FROM checklist_items WHERE checklistId = :checklistId ORDER BY sortOrder ASC, id ASC")
    LiveData<List<ChecklistItemEntity>> observeItems(long checklistId);

    @Insert
    long insertChecklist(ChecklistEntity checklist);

    @Insert
    void insertItems(List<ChecklistItemEntity> items);

    @Insert
    long insertItem(ChecklistItemEntity item);

    @Update
    void updateChecklist(ChecklistEntity checklist);

    @Update
    void updateItem(ChecklistItemEntity item);

    @Query("UPDATE checklists SET lastUsedAt = :time, updatedAt = :time WHERE id = :checklistId")
    void touchChecklist(long checklistId, long time);

    @Query("UPDATE checklist_items SET isChecked = NOT isChecked, updatedAt = :time WHERE id = :itemId")
    void toggleItem(long itemId, long time);

    @Query("UPDATE checklist_items SET isChecked = 0, updatedAt = :time WHERE checklistId = :checklistId")
    void resetChecklist(long checklistId, long time);

    @Query("DELETE FROM checklists WHERE id = :checklistId")
    void deleteChecklist(long checklistId);
}
