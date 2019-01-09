package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

public class StepsFragment extends Fragment {

    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.steps_fragment, container, false);
        final TextView instructions = v.findViewById(R.id.instructions);
        final TextView stepTitle = v.findViewById(R.id.step_name);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getCurrentStep().observe(this, new Observer<Steps>() {
            @Override
            public void onChanged(@Nullable Steps step) {
                instructions.setText(step.getDescription());
                stepTitle.setText(step.getShortDescription());
            }
        });
        return v;
    }
    //If the screen orientation changes, set the trigger in the ViewModel to show the Details
    //fragment
    @Override
    public void onDetach() {
        super.onDetach();
        viewModel.select("Details");
    }
}
