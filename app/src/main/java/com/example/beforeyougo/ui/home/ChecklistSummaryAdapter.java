package com.example.beforeyougo.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.beforeyougo.data.ChecklistSummary;
import com.example.beforeyougo.databinding.RowChecklistSummaryBinding;

public class ChecklistSummaryAdapter extends RecyclerView.Adapter<ChecklistSummaryAdapter.VH> {

    /**
     * Callback interface for user actions on a checklist row.
     *
     * The adapter is responsible only for displaying data and detecting UI events.
     * It does NOT perform navigation, show dialogs, or modify the database.
     *
     * These callbacks allow the adapter to notify the owning Fragment when a user
     * interacts with a checklist (e.g. open or delete), so the Fragment can decide
     * what action to take.
     */
    public interface OnClick {
        void onClick(ChecklistSummary summary);
        void onDelete(ChecklistSummary summary);
    }

    private final OnClick onClick;
    private final List<ChecklistSummary> items = new ArrayList<>();

    public ChecklistSummaryAdapter(OnClick onClick) {
        this.onClick = onClick;
    }

    public void submitList(List<ChecklistSummary> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChecklistSummaryBinding b = RowChecklistSummaryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ChecklistSummary s = items.get(position);
        holder.b.title.setText(s.checklist.name);
        holder.b.subtitle.setText(s.checkedCount + " / " + s.totalCount + " checked");
        holder.b.getRoot().setOnClickListener(v -> onClick.onClick(s));
        holder.b.btnDelete.setOnClickListener(v -> onClick.onDelete(s));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final RowChecklistSummaryBinding b;
        VH(RowChecklistSummaryBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}
