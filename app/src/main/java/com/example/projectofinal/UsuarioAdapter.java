package com.example.projectofinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarioList;

    public UsuarioAdapter(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public void setUsuario(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
        notifyDataSetChanged();
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
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            // Cargar la imagen desde la URL utilizando Picasso
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            // Si el campo profilePic no contiene una URL válida, puedes cargar una imagen de placeholder o dejar el ImageView vacío
            holder.imageViewUsuario.setImageResource(R.drawable.perfil); // Cambia placeholder_image por el ID de tu imagen de placeholder
        }
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        holder.buttonSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes manejar la acción del botón Seguir
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
        Button buttonSeguir;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonSeguir = itemView.findViewById(R.id.buttonSeguir);
        }
    }
}
