--
-- Base de données :  project
--

-- --------------------------------------------------------

DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS releaseProject;
DROP TABLE IF EXISTS sprint;
DROP TABLE IF EXISTS task;

-- --------------------------------------------------------

--
-- Structure de la table contact
--

CREATE TABLE contact (
  idcontact int(11) NOT NULL,
  login varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  admin BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table project
--

CREATE TABLE project (
  idproject int(11) NOT NULL,
  nom varchar(45) NOT NULL,
  description varchar(255) DEFAULT NULL,
  contacts varchar(255) DEFAULT NULL,
  startDate datetime DEFAULT NULL,
  endDate datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table release
--

CREATE TABLE releaseProject (
  idrelease int(11) NOT NULL,
  idproject int(11) NOT NULL,
  daterelease datetime DEFAULT NULL,
  version varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table sprint
--

CREATE TABLE sprint (
  idsprint int(11) NOT NULL,
  idrelease int(11) NOT NULL,
  description varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table task
--

CREATE TABLE task (
  idtask int(11) NOT NULL,
  idsprint int(11) NOT NULL,
  contacts varchar(255) DEFAULT NULL,
  dateStart datetime DEFAULT NULL,
  dateEnd datetime DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  status int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables exportées
--

--
-- Index pour la table contact
--
ALTER TABLE contact
  ADD PRIMARY KEY (idcontact),
  ADD UNIQUE KEY idcontact_UNIQUE (idcontact),
  ADD UNIQUE KEY login_UNIQUE (login);

--
-- Index pour la table project
--
ALTER TABLE project
  ADD PRIMARY KEY (idproject),
  ADD UNIQUE KEY idproject_UNIQUE (idproject),
  ADD UNIQUE KEY nom_UNIQUE (nom);

--
-- Index pour la table release
--
ALTER TABLE releaseProject
  ADD PRIMARY KEY (idrelease),
  ADD UNIQUE KEY idrelease_UNIQUE (idrelease);

--
-- Index pour la table sprint
--
ALTER TABLE sprint
  ADD PRIMARY KEY (idsprint),
  ADD UNIQUE KEY idsprint_UNIQUE (idsprint);

--
-- Index pour la table task
--
ALTER TABLE task
  ADD PRIMARY KEY (idtask),
  ADD UNIQUE KEY idtask_UNIQUE (idtask);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table contact
--
ALTER TABLE contact
  MODIFY idcontact int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table project
--
ALTER TABLE project
  MODIFY idproject int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table release
--
ALTER TABLE releaseProject
  MODIFY idrelease int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table task
--
ALTER TABLE task
  MODIFY idtask int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table sprint
--
ALTER TABLE sprint
  MODIFY idsprint int(11) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table release
--
ALTER TABLE releaseProject
  ADD CONSTRAINT idprojectfk FOREIGN KEY (idproject) REFERENCES project (idproject) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table sprint
--
ALTER TABLE sprint
  ADD CONSTRAINT idreleasefk FOREIGN KEY (idrelease) REFERENCES releaseProject (idrelease) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table task
--
ALTER TABLE task
  ADD CONSTRAINT idsprintfk FOREIGN KEY (idsprint) REFERENCES sprint (idsprint) ON DELETE CASCADE ON UPDATE CASCADE;
