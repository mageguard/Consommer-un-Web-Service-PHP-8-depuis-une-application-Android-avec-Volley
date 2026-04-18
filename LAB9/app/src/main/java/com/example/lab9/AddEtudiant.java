package com.example.lab9;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.lab9.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    private EditText nom, prenom;
    private Spinner ville;
    private RadioButton m;
    private Button add;
    private TextView errorText;
    private RequestQueue requestQueue;

    private static final String insertUrl = "http://10.72.148.95/projet/ws/createEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        m = findViewById(R.id.m);
        add = findViewById(R.id.add);
        errorText = findViewById(R.id.errorText);

        requestQueue = Volley.newRequestQueue(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            if (valider()) envoyerEtudiant();
        }
    }

    // ✅ Validation des champs
    private boolean valider() {
        errorText.setVisibility(View.GONE);

        if (nom.getText().toString().trim().isEmpty()) {
            errorText.setText("⚠️ Le nom est obligatoire !");
            errorText.setVisibility(View.VISIBLE);
            nom.requestFocus();
            return false;
        }
        if (prenom.getText().toString().trim().isEmpty()) {
            errorText.setText("⚠️ Le prénom est obligatoire !");
            errorText.setVisibility(View.VISIBLE);
            prenom.requestFocus();
            return false;
        }
        if (nom.getText().toString().trim().length() < 2) {
            errorText.setText("⚠️ Le nom doit contenir au moins 2 caractères !");
            errorText.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void envoyerEtudiant() {
        // Désactiver le bouton pendant l'envoi
        add.setEnabled(false);
        add.setText("Envoi en cours...");

        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                response -> {
                    add.setEnabled(true);
                    add.setText("✅  Ajouter l'étudiant");

                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);

                    Toast.makeText(this,
                            "✅ Étudiant ajouté avec succès ! (" + etudiants.size() + " au total)",
                            Toast.LENGTH_LONG).show();

                    // Vider les champs après ajout
                    nom.setText("");
                    prenom.setText("");
                },
                error -> {
                    add.setEnabled(true);
                    add.setText("✅  Ajouter l'étudiant");
                    errorText.setText("❌ Erreur réseau. Vérifiez XAMPP !");
                    errorText.setVisibility(View.VISIBLE);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom", nom.getText().toString().trim());
                params.put("prenom", prenom.getText().toString().trim());
                params.put("ville", ville.getSelectedItem().toString());
                params.put("sexe", m.isChecked() ? "homme" : "femme");
                return params;
            }
        };

        requestQueue.add(request);
    }
}