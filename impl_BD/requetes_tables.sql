--ACESS

--Get the nom of all the players

SELECT nom, prenom from Joueur

--Get all the boats in a match

SELECT idBateau from Bateau where idPartie = idPartie --(desired)

--Get the name of a determined boat in a determined match

SELECT nom, prenom from Bateau NATURAL JOIN Joueur where idBateau = x and idPartie = y

--Get all the action of a match

SELECT idAction from Action natural join ActionDeplacement natural join ActionTir where idPartie = x

--Get the actions of a turn

SELECT idAction from Action natural join ActionDeplacement natural join ActionTir where idPartie = x and tour = y

-- Find the winner of a match

SELECT pseudo from Vainqueurs WHERE idPartie = x

-- Find all the matches played by a player

SELECT idPartie from JoueursParPartie

-- Find the two players of a match

SELECT pseudo from JoueursParPartie where IdPartie = x


-- INSERTION

-- Create new match :

    First step :
        INSERT INTO Partie (idPartie, dateDemarage) values (?,?);
    Second step :
        INSERT INTO JoueursPartie (pseudo, idPartie) values (?,?);
    Third step :
        INSERT INTO JoueursPartie (pseudo, idPartie) values (?,?);

-- Create new boat

		INSERT INTO Bateau (idBateau, idPartie, pseudo, taille, pivotX, pivotY, orientation, pointVie) VALUES (?, ?, ?, ?, ?, ?, ?, ?);

-- Create new player

		INSERT INTO Joueur(pseudo, nom, prenom, mail, dateNaissance, adrRue, adrNumero, adrCodePostal, adrVille) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);

--TRANSACTIONS

-- Move the boat

		UPDATE Bateau SET pivotY = (oldPosition + 1) -- fait dans l'aplication
		WHERE idBateau = ?;

-- Boat being hit
		UPDATE Bateau SET pointVie = (oldPointVie - 1) -- fait dans l'aplication
		WHERE idBateau = ?;


--QUERIES DONE INSIDE THE CODE JAVA (DAO)

--ActionDAO

--createActionDeplacement
  INSERT INTO ActionDeplacement (idPartie, tour, numAction,
  typeDeplacement) values ( ?, ?, ?, ?, ?)

--createActionTir
  INSERT INTO ActionTir (idPartie, tour, numAction, cibleTirX,
  cibleTirY ) values ( ?, ?, ?, ?, ?)

--createActionCommun
  INSERT INTO Action (idPartie, idBateau, tour, numAction) values( ?,
  ?, ?, ?)

--isTurnExist
  SELECT MAX(a.tour) FROM Action a WHERE a.idPartie = ?

--getMoveAction
  SELECT a.numAction, a.bateau, aMove.typeDeplacement FROM Action a,
  ActionDeplacement aMove WHERE Action.idPartie =
  ActionDeplacement.idPartie AND Action.tour = ActionDeplacement.tour
  AND Action.numAction = ActionDeplacement.numAction AND Action.tour =
  ? AND Action.idPartie = ?

--getShotActions
  SELECT a.numAction, a.bateau, aShot.cibleTir_X, aShot.cibleTir_Y FROM
	Action a, ActionTir aShot WHERE Action.idPartie = aShot.idPartie AND
	Action.tour = aShot.tour AND Action.numAction = aShot.numAction AND
	Action.tour = ? AND Action.idPartie = ?

--BoatDAO

--findBoatMap
  SELECT * FROM Bateau WHERE idPartie = ? AND pseudo = ?

--createBoatList
  INSERT INTO Bateau (idBateau, idPartie, pseudo, taille, pivotX,
	pivotY, orientation, pointVie) VALUES (?, ?, ?, ?, ?, ?, ?, ?)

--updateBoat
  UPDATE Bateau SET pivotX = ?, pivotY = ?, hp = ?, orientation = ?
  WHERE idBateau = ? AND idPartie = ? AND pseudo = ?

-- MatchDAO
				--Get all observable matches
        SELECT Partie.idPartie, Date, joueur1.pseudo as pseudo1, joueur2.pseudo as pseudo2
        FROM JoueursPartie joueur1, JoueursPartie joueur2, Partie
        WHERE joueur1.pseudo !> userPseudo
        AND joueur2.pseudo !> userPseudo
        AND joueur1.idPartie = Partie.idPartie
        AND joueur2.idPartie = Partie.idPartie
        AND joueur1.numJoueur = 1
        AND joueur2.numJoueur = 2
        AND NOT EXISTS (SELECT * FROM Vainqueurs v  
                        WHERE v.idPartie = Partie.idPartie) 

        --Partie with winner
        SELECT Partie.idPartie, Partie.Date, joueur1.pseudo as pseudo1,
        joueur2.pseudo as pseudo2, Vainqueurs.pseudo
        FROM JoueursPartie joueur1, JoueursPartie joueur2, Vainqueurs, Partie
        WHERE joueur1.idPartie = Partie.idPartie
        AND joueur2.idPartie = Partie.idPartie
        AND Vainqueurs.idPartie = Partie.idPartie
        AND joueur1.numJoueur = 1
        AND joueur2.numJoueur = 2
        
-- Get all playable matches header.

        SELECT Partie.idPartie, Partie.dateDemarrage, joueur1.pseudo as pseudo1,
        joueur2.pseudo as pseudo2
        FROM JoueursPartie joueur1, JoueursPartie joueur2, Partie
        WHERE joueur1.idPartie = Partie.idPartie
        AND joueur2.idPartie = Partie.idPartie and
        AND joueur1.numJoueur = 1
        AND joueur2.numJoueur = 2
        AND (joueur1.pseudo = ? OR joueur2.pseudo = ?)
        AND NOT EXISTS (SELECT * FROM Vainqueurs 
                        WHERE Vainqueurs.idPartie = Partie.idPartie)


-- OpponentDAO

--Select all avaibles opponents

				SELECT pseudo FROM JoueurParPartie WHERE pseudo != ? HAVING
			 	COUNT(*) = (SELECT MIN(COUNT*) FROM JoueurParPartie WHERE pseudo
			  != ? GROUP BY pseudo) GROUP BY pseudo UNION SELECT pseudo FROM
			  Joueur WHERE NOT EXISTS(SELECT * FROM JoueurParPartie jp WHERE
			  jp.pseudo = pseudo)


