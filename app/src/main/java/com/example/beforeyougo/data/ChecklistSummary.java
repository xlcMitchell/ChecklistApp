package com.example.beforeyougo.data;

import androidx.room.Embedded;

public class ChecklistSummary {
    @Embedded
    public ChecklistEntity checklist;

    public int totalCount;
    public int checkedCount;
}
