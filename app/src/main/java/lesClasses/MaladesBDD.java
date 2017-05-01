package lesClasses;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//classe passerelle entre la base de données et l'application
public class MaladesBDD {

		private static final int VERSION_BDD = 1; //version de la base de données
		private static final String NOM_BDD = "bddMalades.db"; //nom de la base de données
		private static final String TABLE_MALADES = "table_malades"; //nom de la table

		private SQLiteDatabase bdd;
		private MaBaseSQLite maBaseSQLite;

		public MaladesBDD(Context context){
			//On instancie l'objet de la classe permettant la gestion de la BDD
			maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
		}

		public void open(){
			//on ouvre la BDD en lecture et ecriture
			bdd = maBaseSQLite.getWritableDatabase();
		}

		public void close(){
			//on ferme l'acces à la BDD
			bdd.close();
		}

		public long ajoutMalade(Malade unMalade){
			//Creation d'un ContentValues (fonctionne comme une HashMap)
			ContentValues valeurs = new ContentValues();
			//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
			valeurs.put("NOM", unMalade.getNom());
			valeurs.put("PRENOM", unMalade.getPrenom());
			valeurs.put("GLYCEMIE", unMalade.getGlycemie());
			//on insère l'objet dans la BDD via le ContentValues
			return bdd.insert(TABLE_MALADES, null, valeurs);
		}

		public void viderTable()
		{
			//Suppression de toutes les lignes de la table
			bdd.delete(TABLE_MALADES, null, null);
		}


		//methode renvoyant le nombre de malades dans la table table_malades
		public int nombreMalades()
		{
			//récupère dans un curseur tous les enregistrements de la table table_malades
			Cursor c = bdd.rawQuery("select * from TABLE_MALADES", null);
			//compte le nombre d'enregistrements
			int nombre = c.getCount();
			c.close();
			return nombre;
		}

		//methode renvoyant une liste d'objets de type Malade
		public ArrayList<Malade> getTousLesMalades()
		{
			ArrayList<Malade> lesMalades = new ArrayList<>();
			int nombre = this.nombreMalades();
			Cursor leCurseur = bdd.rawQuery("SELECT * FROM TABLE_MALADES", null);
			leCurseur.moveToFirst();
			for (int i = 0; i < nombre; i++ )
			{
				//on instancie un malade grace aux informations contenues dans le curseur
				Malade leMalade = new Malade(leCurseur.getString(1), leCurseur.getString(2),
						Double.parseDouble(leCurseur.getString(3)));
				lesMalades.add(leMalade);
				leCurseur.moveToNext();
			}
			leCurseur.close();
			return lesMalades;
		}

		public ArrayList<String> getToutesLesinfos()
		{
			//A écrire
			//Renvoie les infos sur les malades
			ArrayList<String> toutesLesInfos = new ArrayList<>();
			ArrayList<Malade> lesMalades = this.getTousLesMalades();
			int nombre = this.nombreMalades();
			for (int i = 0; i < nombre; i++)
			{
				String leNom = lesMalades.get(i).getNom();
				String lePrenom = lesMalades.get(i).getPrenom();
				String laGlycemie = String.valueOf(lesMalades.get(i).getGlycemie());
				String lesInfos = leNom + " " + lePrenom + " | " + laGlycemie;
				toutesLesInfos.add(lesInfos);
			}
			return toutesLesInfos;
		}

	}
