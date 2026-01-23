package com.example.beforeyougo.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.beforeyougo.data.ChecklistRepository;
import com.example.beforeyougo.data.ChecklistSummary;

public class HomeViewModel extends AndroidViewModel {

    private final ChecklistRepository repo;
    private final LiveData<List<ChecklistSummary>> checklists;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repo = new ChecklistRepository(application);
        checklists = repo.observeUserChecklists();

        // Remove later; just so you see something on first run
        repo.createSampleChecklistIfEmpty();
    }

    public LiveData<List<ChecklistSummary>> getChecklists() {
        return checklists;
    }

    public void deleteChecklist(long checklistId) {
        repo.deleteChecklist(checklistId);
    }

    public void createChecklist(String name, ChecklistRepository.IdCallback callback) {
        repo.createChecklist(name, callback);
    }

}
