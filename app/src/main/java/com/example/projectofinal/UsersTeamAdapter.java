package com.example.projectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersTeamAdapter extends RecyclerView.Adapter<UsersTeamAdapter.ViewHolder> {
    private Context context;
    private List<Usuario> userList;

    public UsersTeamAdapter(Context context, List<Usuario> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listausersteam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario user = userList.get(position);
        holder.textViewNombreUsuario.setText(user.getSurname());
        holder.textViewPosicionUsuario.setText(user.getPosition());
        if (user.getProfilePic() != null && !user.getProfilePic().isEmpty()) {
            Picasso.get().load(user.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        TextView textViewPosicionUsuario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            textViewPosicionUsuario = itemView.findViewById(R.id.textViewPosicionUsuario);
        }
    }
}
