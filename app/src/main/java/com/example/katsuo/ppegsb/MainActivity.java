package com.example.katsuo.ppegsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lesClasses.Malade;
import lesClasses.MaladesBDD;
import lesClasses.MapProtocoles;
import lesClasses.Protocole;

public class MainActivity extends AppCompatActivity {

    //declaration des objets nécessaires
    private EditText editNom, editPrenom, editGlycemie;
    private ListView listMalades;
    private Button btnAjouterMalade, btnAfficherMalades, btnAfficherInsuline;
    private MaladesBDD maladesBDD;
    private TextView txtInsuline, txtNom, txtPrenom;
    private RadioButton radio_protocole1;
    private RadioButton radio_protocole2;
    private MapProtocoles lesProtocoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permet d'afficher l'icone de l'application dans la bar d'action
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        //lien entre les views de l'interface et les objets du code
        this.editNom = (EditText) findViewById(R.id.editNom);
        this.editPrenom = (EditText) findViewById(R.id.editPrenom);
        this.editGlycemie = (EditText) findViewById(R.id.editGlycemie);
        this.btnAfficherMalades = (Button) findViewById(R.id.btnAfficherMalades);
        this.btnAjouterMalade = (Button) findViewById(R.id.btnAjouterMalade);
        this.btnAfficherInsuline = (Button) findViewById(R.id.btnAfficherInsuline);
        this.listMalades = (ListView) findViewById(R.id.listMalades);
        this.txtInsuline = (TextView) findViewById(R.id.txtInsuline);
        this.txtNom = (TextView) findViewById(R.id.txtNom);
        this.txtPrenom = (TextView) findViewById(R.id.txtPrenom);
        this.radio_protocole1 = (RadioButton) findViewById(R.id.radio_protocole1);
        this.radio_protocole2 = (RadioButton) findViewById(R.id.radio_protocole2);

        //Création de la passerelle entre la base de données et l'application
        maladesBDD = new MaladesBDD(this);

        lesProtocoles = new MapProtocoles();
        lesProtocoles.initialiser();

        //Ecouteurs d'évenements pour les clics sur les boutons
        this.btnAjouterMalade.setOnClickListener(clickListenerBtnAjouterMalade);
        this.btnAfficherMalades.setOnClickListener(clickListenerBtnAfficherMalades);
        this.btnAfficherInsuline.setOnClickListener(clickListenerBtnAfficherInsuline);

        this.listMalades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MaladeActivity.class);
                String item = ((TextView)view).getText().toString();
                intent.putExtra("selected-item", item);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //lorsque l'application est lancée, on ouvre l'accès à la base de données
    @Override
    protected void onResume()
    {
        super.onResume();
        maladesBDD.open();
    }

    //lorsque l'application est mise en pause, on ferme l'accès à la base de données
    @Override
    protected void onPause()
    {
        super.onPause();
        maladesBDD.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_vider:
                maladesBDD.viderTable();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //code effectué lorsqu'on clique sur le bouton "Ajouter"
    private View.OnClickListener clickListenerBtnAjouterMalade = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            if (editNom.getText().toString().isEmpty() || editGlycemie.getText().toString().isEmpty()
                    || editPrenom.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Veuillez saisir toutes les informations !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String nom = MainActivity.this.editNom.getText().toString();
                String prenom = MainActivity.this.editPrenom.getText().toString();
                double glycemie = Double.parseDouble(MainActivity.this.editGlycemie.getText().toString());
                if (glycemie < 1)
                {
                    Toast.makeText(MainActivity.this, "La glycémie ne peut pas être inférieure à 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Malade leMalade = new Malade(nom, prenom, glycemie);
                    ArrayList<Malade> lesMalades = maladesBDD.getTousLesMalades();
                    int nbMalades = maladesBDD.nombreMalades();
                    boolean existe = false;
                    for (int i = 0; i < nbMalades; i++)
                    {
                        Malade unMalade = lesMalades.get(i);
                        if (nom.equals(unMalade.getNom()) && prenom.equals(unMalade.getPrenom()))
                        {
                            Toast.makeText(MainActivity.this, "Ce malade existe déjà !", Toast.LENGTH_SHORT).show();
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        maladesBDD.ajoutMalade(leMalade);
                        Toast.makeText(MainActivity.this, "Le malade a bien été ajouté.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    //code effectue lorsqu'on clique sur le bouton "Afficher les malades"
    private View.OnClickListener clickListenerBtnAfficherMalades = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            int nombreMalades = maladesBDD.nombreMalades();
            if (nombreMalades == 0)
                Toast.makeText(MainActivity.this, "Il n'y a aucun malade à signaler.", Toast.LENGTH_SHORT).show();
            else
            {
                ArrayList<String> lesInfos = maladesBDD.getToutesLesinfos();
                //Adapter pour faire le lien entre la liste du programme et la liste de l'interface
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, lesInfos);
                listMalades.setAdapter(adapter);
            }
        }
    };

    //code effectué lorsqu'on clique sur le bouton "Afficher l'insuline"
    private View.OnClickListener clickListenerBtnAfficherInsuline = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

            txtPrenom.setVisibility(View.INVISIBLE);
            txtNom.setVisibility(View.INVISIBLE);
            editPrenom.setVisibility(View.INVISIBLE);
            editNom.setVisibility(View.INVISIBLE);
            txtInsuline.setVisibility(View.INVISIBLE);

            //si le champs de saisie de la glycemie est vide, on affiche un message d'erreur
            if (editGlycemie.getText().toString().isEmpty())
            {
                Toast.makeText(MainActivity.this, "Veuillez saisir la glycemie !", Toast.LENGTH_SHORT).show();
            }

            else
            {
                double glycemie = Double.parseDouble(MainActivity.this.editGlycemie.getText().toString());
                int insuline = 0;

                //si la glycemie est inférieure, on affiche un message d'erreur
                if (glycemie < 1)
                {
                    Toast.makeText(MainActivity.this, "La glycémie ne peut pas être inférieure à 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //si le protocole 1 est choisi
                    if (MainActivity.this.radio_protocole1.isChecked())
                    {
                        Protocole leProtocole = lesProtocoles.getProtocole(1);
                        insuline = leProtocole.insuline(glycemie);
                    }
                    //sinon si le protocole 2 est choisi
                    else if (MainActivity.this.radio_protocole2.isChecked())
                    {
                        Protocole leProtocole = lesProtocoles.getProtocole(2);
                        insuline = leProtocole.insuline(glycemie);
                    }

                    //si la glycemie est inférieure a 2, on affiche l'insuline
                    if (glycemie < 2)
                    {
                        btnAjouterMalade.setVisibility(View.INVISIBLE);
                        txtInsuline.setText(String.format("Insuline : %d", insuline));
                        txtInsuline.setVisibility(View.VISIBLE);
                    }
                    //si la glycemie est supérieure à 2, on affiche l'insuline, un message et les éléments pour
                    // ajouter un malade
                    else if (glycemie >= 2)
                    {
                        Toast.makeText(MainActivity.this, "Le patient doit être ajouté à la liste des malades à signaler",
                            Toast.LENGTH_LONG).show();
                        btnAjouterMalade.setVisibility(View.VISIBLE);
                        editNom.setVisibility(View.VISIBLE);
                        editPrenom.setVisibility(View.VISIBLE);
                        txtPrenom.setVisibility(View.VISIBLE);
                        txtNom.setVisibility(View.VISIBLE);
                        txtInsuline.setText(String.format("Insuline : %d", insuline));
                        txtInsuline.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };


}
