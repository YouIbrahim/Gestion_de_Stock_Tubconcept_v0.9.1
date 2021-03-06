package com.example.gestiondestocktubconcept.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestiondestocktubconcept.R;
import com.example.gestiondestocktubconcept.modele.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class liste_produits extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    List<Profil> liste_produits;
    MyRecyclerViewAdapter adapter;

    TextView txt_categorie_produit ;
    TextView txt_reference_produit ;
    TextView txt_nom_produit ;
    TextView txt_prix_produit ;
    TextView txt_quantite_produit ;
    TextView txt_description_produit ;

    EditText value_categorie;
    EditText value_reference;
    EditText value_nom;
    EditText value_prix;
    EditText value_quantite;
    EditText value_description ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);


        value_categorie =  findViewById(R.id.txt_input_categorie);
        value_reference =  findViewById(R.id.txt_input_reference);
        value_nom = findViewById(R.id.txt_input_nom);
        value_prix =  findViewById(R.id.txt_input_prix);
        value_quantite =  findViewById(R.id.txt_input_quantite);
        value_description = findViewById(R.id.txt_input_description);


        // Données pour remplir le RecyclerView :
        liste_produits = new ArrayList<>();
        liste_produits.add(new Profil("voiture","RF745963-65","twingo",3000.,1,"c une joli voiture"));
        //this.getListeProduit().add(new Profil("voiture","RF745963-65","twingo",3000.,1,"c une joli voitssssrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssure"));

        // set up le RecyclerView:
        RecyclerView recyclerView = findViewById(R.id.rv_produits);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new MyRecyclerViewAdapter(this, liste_produits);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    public void onButtonClick(View view){
        Double value_prix_double = Double.parseDouble(value_prix.getText().toString());
        Integer value_quantite_int = Integer.parseInt(value_quantite.getText().toString());
        ajout_un_item(value_categorie.getText().toString(),value_reference.getText().toString(),value_nom.getText().toString(),value_prix_double,value_quantite_int,value_description.getText().toString());
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }

    private void ajout_un_item(String categorie,String reference,String nom,Double prix,Integer quantite,String description ) {
        Profil item = new Profil(categorie, reference, nom, prix, quantite, description);
        int insertIndex = 1;
        liste_produits.add(insertIndex, item);
        adapter.notifyItemInserted(insertIndex);
    }

   // private void ajout_des_items(){
   //     ArrayList<String> items = new ArrayList<>();
   //     items.add("Pig");
   //     items.add("Chicken");
   //     items.add("Dog");
   //     int insertIndex = 2;
   //     liste_produits.addAll(insertIndex, items);
   //     adapter.notifyItemRangeInserted(insertIndex, items.size());
   // }

    public void export (View view){


            /*  ++Creation des données++  */
            StringBuilder data = new StringBuilder();

            data.append("Catégorie,Référence,Nom,Prix,Quantités,Description");
            for (int i = 0; i < 6; i++) {
                data.append("\n" + "test");
            }

            /*  --Creation des données--  */


            try {
                /*  ++Sauvegarde des données dans l'appareil++ */

                FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                /*  --Sauvegarde des données dans l'appareil-- */


                /*  ++Export des données++  */

                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "data.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.gestiondestocktubconcept.FileProvider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));

                /*  --Export des données--  */
            } catch (Exception e) {
                e.printStackTrace();
            }


        }



}
