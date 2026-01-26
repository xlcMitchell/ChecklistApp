package com.example.beforeyougo.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.beforeyougo.data.ChecklistItemEntity;
import com.example.beforeyougo.databinding.RowChecklistItemBinding;

public class ChecklistItemAdapter extends RecyclerView.Adapter<ChecklistItemAdapter.VH> {

    public interface OnItemClick {
        void onItemClick(ChecklistItemEntity item);
        void onItemDelete(ChecklistItemEntity item);
    }

    private final OnItemClick onItemClick;
    private final List<ChecklistItemEntity> items = new ArrayList<>();

    public ChecklistItemAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void submitList(List<ChecklistItemEntity> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChecklistItemBinding b = RowChecklistItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ChecklistItemEntity item = items.get(position);

        holder.b.checkbox.setOnCheckedChangeListener(null);
        holder.b.checkbox.setText(item.text);
        holder.b.checkbox.setChecked(item.isChecked);

        holder.b.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> onItemClick.onItemClick(item));
        holder.b.btnDelete.setOnClickListener(v -> onItemClick.onItemDelete(item));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final RowChecklistItemBinding b;
        VH(RowChecklistItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}
