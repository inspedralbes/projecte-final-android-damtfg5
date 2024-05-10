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

import com.squareup.picasso.Picasso;

import java.util.List;

public class AddPeopleAdapter extends RecyclerView.Adapter<AddPeopleAdapter.ViewHolder> {

    private List<Usuario> usuarioList;
    private Context context;

    public AddPeopleAdapter(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    public void setUsuario(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listainviteteam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = usuarioList.get(position);
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }
        holder.buttonInvitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic en el botón Invitar
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonInvitar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonInvitar = itemView.findViewById(R.id.buttonSeguir);
        }
    }
}

