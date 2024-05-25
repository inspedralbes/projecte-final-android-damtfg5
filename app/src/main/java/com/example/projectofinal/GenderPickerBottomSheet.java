package com.example.projectofinal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GenderPickerBottomSheet extends BottomSheetDialogFragment {

    private GenderPickerListener listener;

    public interface GenderPickerListener {
        void onGenderSelected(String gender);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenderPickerListener) {
            listener = (GenderPickerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GenderPickerListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_gender_picker, container, false);

        view.findViewById(R.id.textViewMale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGenderSelected("Masculino");
                dismiss();
            }
        });

        view.findViewById(R.id.textViewFemale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGenderSelected("Femenino");
                dismiss();
            }
        });

        view.findViewById(R.id.textViewOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGenderSelected("Otros");
                dismiss();
            }
        });

        view.findViewById(R.id.textViewPreferNotToSay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGenderSelected("Prefiero no decirlo");
                dismiss();
            }
        });

        return view;
    }
}
