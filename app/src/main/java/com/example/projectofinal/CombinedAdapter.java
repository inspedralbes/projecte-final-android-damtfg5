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

public class CombinedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NOTIFICATION_VIEW_TYPE = 0;
    private static final int INVITATION_VIEW_TYPE = 1;

    private Context context;
    private List<Usuario> notificationItems;
    private List<TeamData> invitationList;

    public CombinedAdapter(Context context, List<Usuario> notificationItems, List<TeamData> invitationList) {
        this.context = context;
        this.notificationItems = notificationItems;
        this.invitationList = invitationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == NOTIFICATION_VIEW_TYPE) {
            View notificationView = inflater.inflate(R.layout.item_listanotis, parent, false);
            return new NotificationViewHolder(notificationView);
        } else if (viewType == INVITATION_VIEW_TYPE) {
            View invitationView = inflater.inflate(R.layout.item_listanotis2, parent, false);
            return new InvitationViewHolder(invitationView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder) {
            ((NotificationViewHolder) holder).bind(notificationItems.get(position));
        } else if (holder instanceof InvitationViewHolder) {
            ((InvitationViewHolder) holder).bind(invitationList.get(position - notificationItems.size()));
        }
    }

    @Override
    public int getItemCount() {
        return notificationItems.size() + invitationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < notificationItems.size() ? NOTIFICATION_VIEW_TYPE : INVITATION_VIEW_TYPE;
    }

    private class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView notificationText;
        private ImageView imageViewUsuario;
        private Button buttonConfirm;
        private Button buttonDelete;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.textViewNombreUsuario);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        void bind(Usuario item) {
            notificationText.setText(item.getFirstname()+" "+item.getSurname());
        }
    }

    private class InvitationViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewUsuario;
        private TextView invitationText;
        private Button buttonConfirm;
        private Button buttonDelete;

        InvitationViewHolder(@NonNull View itemView) {
            super(itemView);
            invitationText = itemView.findViewById(R.id.textViewIT);
            imageViewUsuario = itemView.findViewById(R.id.imageViewTeam);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        void bind(TeamData item) {
            invitationText.setText("Invitation for team " + item.getTeamName());
        }
    }
}

