package com.example.projectofinal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<DayItem> dayList;
    private int selectedItem = RecyclerView.NO_POSITION;
    private OnItemClickListener listener;

    public DayItem getItem(int position) {
        return dayList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DayAdapter(List<DayItem> dayList) {
        this.dayList = dayList;
        if (!dayList.isEmpty()) {
            selectedItem = 0;
        }
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DayItem dayItem = dayList.get(position);
        holder.dayOfWeekTextView.setText(dayItem.getDayOfWeek());
        holder.dayOfMonthTextView.setText(dayItem.getDayOfMonth());
        holder.monthTextView.setText(dayItem.getMonth());

        // Cambiar el color del texto de los TextViews para el elemento seleccionado
        if (position == selectedItem) {
            int orangeColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.naranja1);
            holder.dayOfWeekTextView.setTextColor(orangeColor);
            holder.dayOfMonthTextView.setBackgroundResource(R.drawable.rounded_orange_background);
            holder.dayOfMonthTextView.setTextColor(Color.WHITE);
            holder.monthTextView.setTextColor(orangeColor);
        } else {
            holder.dayOfWeekTextView.setTextColor(Color.BLACK);
            holder.dayOfMonthTextView.setBackgroundResource(0);
            holder.dayOfMonthTextView.setTextColor(Color.BLACK);
            holder.monthTextView.setTextColor(Color.BLACK);
        }

        // Manejar el clic en el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedItem); // Restablecer el color del elemento anterior
                }
                selectedItem = position; // Actualizar la posición seleccionada
                notifyItemChanged(selectedItem); // Actualizar el color del nuevo elemento
                if (listener != null) {
                    listener.onItemClick(position); // Notificar al listener
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        public TextView dayOfWeekTextView;
        public TextView dayOfMonthTextView;
        public TextView monthTextView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfWeekTextView = itemView.findViewById(R.id.textViewDayOfWeek);
            dayOfMonthTextView = itemView.findViewById(R.id.textViewDayOfMonth);
            monthTextView = itemView.findViewById(R.id.textViewMonth);
        }
    }
}

