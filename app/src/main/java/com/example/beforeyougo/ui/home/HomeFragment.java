package com.example.beforeyougo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.beforeyougo.R;
import com.example.beforeyougo.data.ChecklistSummary;
import com.example.beforeyougo.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel vm;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(HomeViewModel.class);

        ChecklistSummaryAdapter adapter = new ChecklistSummaryAdapter(
                new ChecklistSummaryAdapter.OnClick(){
                    @Override
                    public void onClick(ChecklistSummary summary) {
                        Bundle args = new Bundle();
                        args.putLong("checklistId", summary.checklist.id);
                        Navigation.findNavController(view)
                                .navigate(R.id.action_home_to_detail, args);
                    }

                    @Override
                    public void onDelete(ChecklistSummary summary) {
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Delete checklist?")
                                .setMessage("This will delete the checklist and all its items.")
                                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                                .setPositiveButton("Delete", (d, w) ->
                                        vm.deleteChecklist(summary.checklist.id)
                                )
                                .show();
                    }
                }
        );

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);

        vm.getChecklists().observe(getViewLifecycleOwner(), adapter::submitList);


       //onclick lisenter to add new checklist
        binding.fabAdd.setOnClickListener(v -> {
            android.widget.EditText input = new android.widget.EditText(requireContext());
            input.setHint("Checklist name");

            new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                    .setTitle("New checklist")
                    .setView(input)
                    .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                    .setPositiveButton("Create", (d, w) -> {
                        String name = input.getText().toString().trim();
                        if (name.isEmpty()) name = "New Checklist";

                        vm.createChecklist(name, newId -> {
                            Bundle args = new Bundle();
                            args.putLong("checklistId", newId);
                            Navigation.findNavController(view)
                                    .navigate(R.id.action_home_to_detail, args);
                        });
                    })
                    .show();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
