package com.example.projectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarioList;

    public UsuarioAdapter(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listausers, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarioList.get(position);
        holder.imageViewUsuario.setImageResource(usuario.getImagen());
        holder.textViewNombreUsuario.setText(usuario.getNombre());
        holder.buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes manejar la acción del botón Agregar
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioList != null ? usuarioList.size() : 0;
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonAgregar;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonAgregar = itemView.findViewById(R.id.buttonAgregar);
        }
    }
}
