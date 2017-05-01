package lesClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//classe permettant la creation de la base de données
public class MaBaseSQLite extends SQLiteOpenHelper
{
	//nom de la table
	private static final String TABLE_MALADES= "table_malades";
	//nom des colonnes
	private static final String COL_ID = "ID";
	private static final String COL_NOM = "NOM";
	private static final String COL_PRENOM = "PRENOM";
	private static final String COL_GLYCEMIE = "GLYCEMIE";
	//requete de creation
	private static final String CREATE_TABLE_MALADES = "CREATE TABLE "
	+ TABLE_MALADES + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ COL_NOM + " TEXT NOT NULL, "
	+ COL_PRENOM + " TEXT NOT NULL, "
	+ COL_GLYCEMIE + " TEXT NOT NULL);";

	//Le constructeur
	public MaBaseSQLite(Context context, String nomBDD, CursorFactory factory, int version) {
		super(context, nomBDD, factory, version);
	}

	//creation de la table
	@Override
	public void onCreate(SQLiteDatabase db) {
		//Creation des tables
		db.execSQL(CREATE_TABLE_MALADES);
	}

	//suppression, creation de la table lors du changement de version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE table_malades;" );
		this.onCreate(db);
	}
}
