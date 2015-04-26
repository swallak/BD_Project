package dao.jdbc;

public class DataBaseConstant {

	/* Table Joueur */
	protected final static String PLAYER_TABLE_NAME = "Joueur";
	protected final static String PLAYER_PSEUDO = "pseudo";
	protected final static String PLAYER_FIRSTNAME = "prenom";
	protected final static String PLAYER_LASTNAME = "nom";
	protected final static String PLAYER_MAIL = "mail";
	protected final static String PLAYER_BIRTHDAY = "dateNaissance";
	protected final static String PLAYER_ADDRESS_NUMBER = "adrNumero";
	protected final static String PLAYER_ADDRESS_STREET = "adrRue";
	protected final static String PLAYER_ADDRESS_POSTAL_CODE = "adrCodePostal";
	protected final static String PLAYER_ADDRESS_CITY = "adrVille";

	/* Table JoueursParPartie */
	protected final static String PLAYER_ON_MATCH_TABLE_NAME = "JoueursParPartie";
	protected final static String PLAYER_ON_MATCH_MATCH_ID = "idPartie";
	protected final static String PLAYER_ON_MATCH_PLAYER_PSEUDO = "pseudo";

	/* Table Partie */
	protected final static String MATCH_TABLE_NAME = "Partie";
	protected final static String MATCH_START_DATE = "dateDemarage";
	protected final static String MATCH_ID = "idPartie";

	/* Table Action */
	protected final static String ACTION_TABLE_NAME = "Action";
	protected final static String ACTION_MATCH_ID = "idPartie";
	protected final static String ACTION_BOAT_ID = "idBateau";
	protected final static String ACTION_TURN = "tour";
	protected final static String ACTION_NUM_ACTION = "numAction";

	/* Table ActionDeplacement */
	protected final static String ACTION_MOVE_TABLE_NAME = "ActionDeplacement";
	protected final static String ACTION_MOVE_MATCH_ID = "idPartie";
	protected final static String ACTION_MOVE_TURN = "turn";
	protected final static String ACTION_MOVE_NUM_ACTION = "numAction";
	protected final static String ACTION_MOVE_TYPE = "typeDeplacement";

	/* Table ActionTir */
	protected final static String ACTION_SHOT_TABLE_NAME = "ActionTir";
	protected final static String ACTION_SHOT_MATCH_ID = "idPartie";
	protected final static String ACTION_SHOT_TURN = "tour";
	protected final static String ACTION_SHOT_NUM_ACTION = "numAction";
	protected final static String ACTION_SHOT_TARGET_X = "cibleTirX";
	protected final static String ACTION_SHOT_TARGET_Y = "cibleTirY";

	/* Table Bateau */
	protected final static String BOAT_TABLE_NAME = "bateau";
	protected final static String BOAT_MATCH_ID = "idPartie";
	protected final static String BOAT_ID = "idBateau";
	protected final static String BOAT_OWNER = "pseudo";
	protected final static String BOAT_SIZE = "taille";
	protected final static String BOAT_POS_X = "pivotX";
	protected final static String BOAT_POS_Y = "pivotY";
	protected final static String BOAT_ORIENTATION = "orientation";
	protected final static String BOAT_HP = "pointVie";
}
