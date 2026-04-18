package com.example.lab9;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.lab9.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

public class ListEtudiant extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView countText;
    private static final String loadUrl = "http://10.72.148.95/projet/ws/loadEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        recyclerView = findViewById(R.id.recyclerView);
        countText = findViewById(R.id.countText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chargerEtudiants();
    }

    private void chargerEtudiants() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, loadUrl,
                response -> {
                    Type type = new TypeToken<List<Etudiant>>(){}.getType();
                    List<Etudiant> etudiants = new Gson().fromJson(response, type);

                    countText.setText(etudiants.size() + " étudiant(s) trouvé(s)");
                    recyclerView.setAdapter(new EtudiantAdapter(etudiants));
                },
                error -> countText.setText("❌ Erreur de chargement")) ;

        queue.add(request);
    }
}