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

            // 1️⃣ Weekend Trip
            long weekendId = dao.insertChecklist(
                    new ChecklistEntity("Weekend Trip", false)
            );
            dao.insertItems(makeItems(weekendId,
                    "Clothes",
                    "Toiletries",
                    "Toothbrush",
                    "Phone charger",
                    "Wallet",
                    "Keys",
                    "Sunglasses",
                    "Medication"
            ));

            // 2️⃣ Camping Trip
            long campingId = dao.insertChecklist(
                    new ChecklistEntity("Camping Trip", false)
            );
            dao.insertItems(makeItems(campingId,
                    "Tent",
                    "Pegs",
                    "Sleeping bag",
                    "Pillow",
                    "Torch / headlamp",
                    "Power bank",
                    "Cooking gear",
                    "Food",
                    "Water",
                    "Warm clothes",
                    "Rain jacket",
                    "First aid kit",
                    "Sunscreen",
                    "Insect repellent"
            ));

            // 3️⃣ Flight (Carry-on)
            long flightId = dao.insertChecklist(
                    new ChecklistEntity("Flight (Carry-on)", false)
            );
            dao.insertItems(makeItems(flightId,
                    "Passport",
                    "Boarding pass",
                    "Wallet",
                    "Phone",
                    "Phone charger",
                    "Headphones",
                    "Power bank",
                    "Travel pillow",
                    "Snacks",
                    "Hand sanitiser",
                    "Reusable water bottle",
                    "Jacket / hoodie"
            ));

            // 4️⃣ Gym Bag
            long gymId = dao.insertChecklist(
                    new ChecklistEntity("Gym Bag", false)
            );
            dao.insertItems(makeItems(gymId,
                    "Gym clothes",
                    "Training shoes",
                    "Towel",
                    "Water bottle",
                    "Headphones",
                    "Deodorant",
                    "Lock",
                    "Change of clothes",
                    "Protein / snack"
            ));

            // 5️⃣ Work / School Bag
            long workId = dao.insertChecklist(
                    new ChecklistEntity("Work / School Bag", false)
            );
            dao.insertItems(makeItems(workId,
                    "Laptop",
                    "Laptop charger",
                    "Notebook",
                    "Pens",
                    "Lunch",
                    "Water bottle",
                    "Headphones",
                    "Phone charger",
                    "ID / access card"
            ));
        });
    }

    private List<ChecklistItemEntity> makeItems(long checklistId, String... names) {
        List<ChecklistItemEntity> items = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            items.add(new ChecklistItemEntity(checklistId, names[i], i));
        }
        return items;
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
