package com.example.beforeyougo.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.example.beforeyougo.databinding.FragmentChecklistDetailBinding;

public class ChecklistDetailFragment extends Fragment {

    private FragmentChecklistDetailBinding binding;
    private ChecklistDetailViewModel vm;
    private long checklistId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChecklistDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ChecklistDetailViewModel.class);
        checklistId = requireArguments().getLong("checklistId");

        ChecklistItemAdapter adapter = new ChecklistItemAdapter(item -> vm.toggleItem(item.id));

        binding.recyclerItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerItems.setAdapter(adapter);

        vm.observeChecklist(checklistId).observe(getViewLifecycleOwner(), checklist -> {
            if (checklist != null) {
                binding.title.setText(checklist.name);
                vm.touch(checklistId);
            }
        });

        vm.observeItems(checklistId).observe(getViewLifecycleOwner(), adapter::submitList);

        binding.btnReset.setOnClickListener(v -> new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Reset checklist?")
                .setMessage("Clear all ticks so it’s ready for next time.")
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .setPositiveButton("Reset", (d, w) -> vm.resetChecklist(checklistId))
                .show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
