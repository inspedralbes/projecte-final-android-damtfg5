package com.example.projectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.UsuarioViewHolder> {
    private Context context;
    private List<Usuario> usuarios;

    public NotificacionesAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listanotis, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        holder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción cuando se hace clic en el botón Confirmar
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción cuando se hace clic en el botón Eliminar
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonConfirm;
        Button buttonDelete;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}

