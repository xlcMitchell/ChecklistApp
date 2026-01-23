package com.example.beforeyougo.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.beforeyougo.data.ChecklistEntity;
import com.example.beforeyougo.data.ChecklistItemEntity;
import com.example.beforeyougo.data.ChecklistRepository;

public class ChecklistDetailViewModel extends AndroidViewModel {

    private final ChecklistRepository repo;

    public ChecklistDetailViewModel(@NonNull Application application) {
        super(application);
        repo = new ChecklistRepository(application);
    }

    public LiveData<ChecklistEntity> observeChecklist(long checklistId) {
        return repo.observeChecklist(checklistId);
    }

    public LiveData<List<ChecklistItemEntity>> observeItems(long checklistId) {
        return repo.observeItems(checklistId);
    }

    public void toggleItem(long itemId) {
        repo.toggleItem(itemId);
    }

    public void resetChecklist(long checklistId) {
        repo.resetChecklist(checklistId);
    }

    public void touch(long checklistId) {
        repo.touchChecklist(checklistId);
    }
}
