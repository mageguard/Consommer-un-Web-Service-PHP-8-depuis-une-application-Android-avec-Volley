package com.example.lab9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab9.beans.Etudiant;
import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {

    private final List<Etudiant> etudiants;

    public EtudiantAdapter(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_etudiant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant e = etudiants.get(position);

        // ✅ Vérification null pour nom et prénom
        String nom = e.getNom() != null ? e.getNom() : "";
        String prenom = e.getPrenom() != null ? e.getPrenom() : "";
        String ville = e.getVille() != null ? e.getVille() : "";
        String sexe = e.getSexe() != null ? e.getSexe() : "";

        holder.nomPrenom.setText(nom + " " + prenom);
        holder.villeText.setText("📍 " + ville);

        // ✅ Vérification null pour sexe avant .equals()
        if (sexe.equals("homme")) {
            holder.sexeBadge.setText("♂ Homme");
            holder.sexeBadge.setBackgroundColor(0xFF3F51B5);
        } else {
            holder.sexeBadge.setText("♀ Femme");
            holder.sexeBadge.setBackgroundColor(0xFFE91E63);
        }

        // ✅ Vérification null pour avatar
        if (!nom.isEmpty()) {
            holder.avatar.setText(nom.substring(0, 1).toUpperCase());
        } else {
            holder.avatar.setText("?");
        }
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomPrenom, villeText, sexeBadge, avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomPrenom = itemView.findViewById(R.id.nomPrenom);
            villeText = itemView.findViewById(R.id.villeText);
            sexeBadge = itemView.findViewById(R.id.sexeBadge);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}