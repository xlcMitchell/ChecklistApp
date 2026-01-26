package com.example.beforeyougo.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class ChecklistRepository {

    private final ChecklistDao dao;

    public ChecklistRepository(Context context) {
        dao = AppDatabase.getInstance(context).checklistDao();
    }

    public LiveData<List<ChecklistSummary>> observeUserChecklists() {
        return dao.observeUserChecklists();
    }

    public LiveData<ChecklistEntity> observeChecklist(long id) {
        return dao.observeChecklist(id);
    }

    public LiveData<List<ChecklistItemEntity>> observeItems(long checklistId) {
        return dao.observeItems(checklistId);
    }

    // Optional starter checklist so the app isn't empty on first run.
    // Remove whenever you add templates / create screen.
    public void createSampleChecklistIfEmpty() {
        AppExecutors.getInstance().db().execute(() -> {
            int count = dao.getUserChecklistCount();
            if (count > 0) return; // already has data, don’t seed again
            long checklistId = dao.insertChecklist(new ChecklistEntity("Weekend Trip", false));
            List<ChecklistItemEntity> items = new ArrayList<>();
            items.add(new ChecklistItemEntity(checklistId, "Phone charger", 0));
            items.add(new ChecklistItemEntity(checklistId, "Toothbrush", 1));
            items.add(new ChecklistItemEntity(checklistId, "Wallet / keys", 2));
            items.add(new ChecklistItemEntity(checklistId, "Jacket", 3));
            dao.insertItems(items);
        });
    }

    public void toggleItem(long itemId) {
        AppExecutors.getInstance().db().execute(() ->
                dao.toggleItem(itemId, System.currentTimeMillis())
        );
    }

    public void resetChecklist(long checklistId) {
        AppExecutors.getInstance().db().execute(() -> {
            long now = System.currentTimeMillis();
            dao.resetChecklist(checklistId, now);
            dao.touchChecklist(checklistId, now);
        });
    }

    public void touchChecklist(long checklistId) {
        AppExecutors.getInstance().db().execute(() ->
                dao.touchChecklist(checklistId, System.currentTimeMillis())
        );
    }

    public void deleteChecklist(long id) {
        AppExecutors.getInstance().db().execute(() ->
                dao.deleteChecklist(id)
        );
    }

    public interface IdCallback {
        void onId(long id);
    }

    public void createChecklist(String name, IdCallback callback) {
        AppExecutors.getInstance().db().execute(() -> {
            //creates a new checklist entity object to be inserted into database
            long id = dao.insertChecklist(new ChecklistEntity(name, false));
            // callback back on main thread:
            // gets called back to main thread because UI updates need to take place
            //starting a new fragment
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> callback.onId(id));
        });
    }

    //Add new checklists item method..

    public void addItem(long checklistId, String text, int sortOrder) {
        AppExecutors.getInstance().db().execute(() -> {
            dao.insertItem(new ChecklistItemEntity(checklistId, text, sortOrder));
            dao.touchChecklist(checklistId, System.currentTimeMillis()); // optional
        });
    }

    public void deleteItem(long itemId){
        AppExecutors.getInstance().db().execute(()->{
               dao.deleteItem(itemId);
        });
    }


}
