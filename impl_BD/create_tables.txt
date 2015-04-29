--Commandes en une ligne
CREATE TABLE Bateau(idBateau INT,idPartie INT,pseudo VARCHAR(255),taille INT,pivotX INT,pivotY INT,orientation VARCHAR(255),pointVie INT,CONSTRAINT Bateau_pk PRIMARY KEY (idBateau, idPartie),CONSTRAINT Partie_Bat_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie), CONSTRAINT Joueur_Bat_fk FOREIGN KEY(pseudo) REFERENCES Joueur(pseudo), CONSTRAINT Bateu_chk CHECK ((orientation='nord' OR orientation='sud' OR orientation='est' OR orientation='ouest') AND pointVie>=0 AND pivotX>=0 AND pivotX<10 AND pivotY>=0 AND pivotY<10 AND (taille=2 OR taille=3) AND (((Orientation = 'nord') and (pivotY + taille < 10)) or ((orientation= 'sud') and (pivotY-taille >= 0)) or ((orientation='est') and (pivotX+taille < 10)) or ((orientation='ouest') and (pivotX-taille >= 0)))));

CREATE TABLE Joueur (pseudo VARCHAR(255),nom VARCHAR(255),prenom VARCHAR(255),mail VARCHAR(255),dateNaissance DATE,adrRue VARCHAR(255),adrNumero INT,adrCodePostal INT,adrVille VARCHAR(255),CONSTRAINT Joueur_pk PRIMARY KEY (pseudo),CONSTRAINT Joueur_chk CHECK(dateNaissance > '01-JAN-1900' AND adrNumero > 0 AND adrCodePostal > 0));

CREATE TABLE Partie (idPartie INT,dateDemarrage DATE,CONSTRAINT Partie_pk PRIMARY KEY (idPartie),CONSTRAINT Partie_chk CHECK(dateDemarrage > '01-JAN-2015'));

CREATE TABLE Action(idPartie INT,idBateau INT,pseudo VARCHAR(255),tour INT,numAction INT,CONSTRAINT Action_pk PRIMARY KEY (idPartie, tour, numAction),CONSTRAINT Bateau_Act_fk FOREIGN KEY (idBateau, idPartie) REFERENCES Bateau(idBateau, idPartie),CONSTRAINT Joueur_Act_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo),CONSTRAINT Action_chk CHECK (tour > 0 and numAction > 0));

CREATE TABLE ActionDeplacement(idPartie INT,tour INT,numAction INT,typeDeplacement VARCHAR(255),CONSTRAINT ActionDepl_pk PRIMARY KEY (tour,idPartie, numAction),CONSTRAINT ActionDepl_fk FOREIGN KEY (tour, idPartie, numAction) REFERENCES Action(tour, idPartie, numAction),CONSTRAINT ActionDepl_chk CHECK ((typeDeplacement='droite' OR typeDeplacement='gauche' OR typeDeplacement='avancer' OR typeDeplacement='reculer') AND (tour > 0 and numAction > 0)));

CREATE TABLE ActionTir(idPartie INT,tour INT,numAction INT,cibleTirX INT,cibleTirY INT,CONSTRAINT ActionTir_pk PRIMARY KEY (tour,idPartie, numAction),CONSTRAINT ActionTir_fk FOREIGN KEY (tour, idPartie, numAction) REFERENCES Action(tour, idPartie, numAction),CONSTRAINT ActionTir_chk CHECK (cibleTirX>=0 AND cibleTirX<10 AND cibleTirY>=0 AND cibleTirY<10 AND tour > 0 AND numAction > 0));

CREATE TABLE Vainqueurs(idPartie INT,pseudo VARCHAR(255),CONSTRAINT Vainqueurs_pk PRIMARY KEY (idPartie),CONSTRAINT Vainqueur_Partie_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie),CONSTRAINT Vainqueur_Joueur_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo));

CREATE TABLE JoueursParPartie(pseudo VARCHAR(255),idPartie INT,numJoueur INT,CONSTRAINT JoueursParPartie_pk PRIMARY KEY (idPartie, pseudo), CONSTRAINT NbJoueur_chk CHECK(numJoueur = 1 OR numJoueur = 2),CONSTRAINT JoueursDePartie_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo),CONSTRAINT PartieJoue_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie));

--Commandes plus ligibles
CREATE TABLE Bateau(
  idBateau INT,
  idPartie INT,
  pseudo VARCHAR(255),
  taille INT,
  pivotX INT,
  pivotY INT,
  orientation VARCHAR(255),
  pointVie INT,
  CONSTRAINT Bateau_pk PRIMARY KEY (idBateau, idPartie),
  CONSTRAINT Partie_Bat_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie), 
  CONSTRAINT Joueur_Bat_fk FOREIGN KEY(pseudo) REFERENCES Joueur(pseudo), 
  CONSTRAINT Bateu_chk CHECK (
                                (orientation='nord' OR orientation='sud' OR orientation='est' OR orientation='ouest') AND
                                pointVie>=0 AND pivotX>=0 AND pivotX<10 AND pivotY>=0 AND pivotY<10 AND (taille=2 OR taille=3) AND
                                (
                                  ((Orientation = 'nord') AND (pivotY + taille < 10)) OR 
                                  ((orientation= 'sud') AND (pivotY-taille >= 0)) OR
                                  ((orientation='est') AND (pivotX+taille < 10)) OR 
                                  ((orientation='ouest') AND (pivotX-taille >= 0))
                                )
                              )
  );


CREATE TABLE Joueur (
  pseudo VARCHAR(255),
  nom VARCHAR(255),
  prenom VARCHAR(255),
  mail VARCHAR(255),
  dateNaissance DATE,
  adrRue VARCHAR(255),
  adrNumero INT,
  adrCodePostal INT,
  adrVille VARCHAR(255),
  CONSTRAINT Joueur_pk PRIMARY KEY (pseudo),
  CONSTRAINT Joueur_chk CHECK(dateNaissance > '01-JAN-1900' AND adrNumero > 0 AND adrCodePostal > 0)
  );


CREATE TABLE Partie (
  idPartie INT,
  dateDemarrage DATE,
  CONSTRAINT Partie_pk PRIMARY KEY (idPartie),
  CONSTRAINT Partie_chk CHECK(dateDemarrage > '01-JAN-2015')
  );


CREATE TABLE Action(
  idPartie INT,
  idBateau INT,
  pseudo VARCHAR(255),
  tour INT,
  numAction INT,
  CONSTRAINT Action_pk PRIMARY KEY (idPartie, tour, numAction),
  CONSTRAINT Bateau_Act_fk FOREIGN KEY (idBateau, idPartie) REFERENCES Bateau(idBateau, idPartie),
  CONSTRAINT Joueur_Act_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo),
	CONSTRAINT Action_chk CHECK (tour > 0 and numAction > 0)
  );

CREATE TABLE ActionDeplacement(
  idPartie INT,
  tour INT,
  numAction INT,
  typeDeplacement VARCHAR(255),
  CONSTRAINT ActionDepl_pk PRIMARY KEY (tour,idPartie, numAction),
  CONSTRAINT ActionDepl_fk FOREIGN KEY (tour, idPartie, numAction) REFERENCES Action(tour, idPartie, numAction),
  CONSTRAINT ActionDepl_chk CHECK ((typeDeplacement='droite' OR typeDeplacement='gauche' OR typeDeplacement='avancer' OR typeDeplacement='reculer') AND (tour > 0 and numAction > 0))
  );

CREATE TABLE ActionTir(
  idPartie INT,
  tour INT,
  numAction INT,
  cibleTirX INT,
  cibleTirY INT,
  CONSTRAINT ActionTir_pk PRIMARY KEY (tour,idPartie, numAction),
  CONSTRAINT ActionTir_fk FOREIGN KEY (tour, idPartie, numAction) REFERENCES Action(tour, idPartie, numAction),
  CONSTRAINT ActionTir_chk CHECK (cibleTirX>=0 AND cibleTirX<10 AND cibleTirY>=0 AND cibleTirY<10 AND tour > 0 AND numAction > 0)
  );

CREATE TABLE Vainqueurs(
  idPartie INT,
  pseudo VARCHAR(255),
  CONSTRAINT Vainqueurs_pk PRIMARY KEY (idPartie),
  CONSTRAINT Vainqueur_Partie_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie),
  CONSTRAINT Vainqueur_Joueur_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo)
  );

CREATE TABLE JoueursParPartie(
  pseudo VARCHAR(255),
  idPartie INT,
  numJoueur INT,
  CONSTRAINT JoueursParPartie_pk PRIMARY KEY (idPartie, pseudo), 
  CONSTRAINT NbJoueur_chk CHECK(numJoueur = 1 OR numJoueur = 2),
  CONSTRAINT JoueursDePartie_fk FOREIGN KEY (pseudo) REFERENCES Joueur(pseudo),
  CONSTRAINT PartieJoue_fk FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)
  );

