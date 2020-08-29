CREATE TABLE persons(
	id int not null auto_increment,
    name varchar(100) not null,
    birth date not null,
    genre tinyint not null,
    document int not null,
    PRIMARY KEY(id)
);

CREATE TABLE general_publics(
	id int not null auto_increment,
    volunteers tinyint not null default 0,
    person_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(person_id) REFERENCES persons(id)
);

CREATE TABLE researchers(
	id int not null auto_increment,
    institution varchar(100) not null,
    person_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(person_id) REFERENCES persons(id)
);

CREATE TABLE stages(
	id int not null auto_increment,
    name varchar(50) not null
);

INSERT INTO stages VALUES('Inicial'), ('Testes'), ('Aplica��o em massa');

CREATE TABLE vaccines(
	id int not null auto_increment,
    country varchar(100) not null,
    stage_id int not null,
    researcher_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(stage_id) REFERENCES stages(id),
    FOREIGN KEY(researcher_id) REFERENCES researchers(id)
);

CREATE TABLE vaccine_persons(
	id int not null auto_increment,
    evaluation tinyint not null,
    vaccine_id int not null,
    person_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(vaccine_id) REFERENCES vaccines(id),
    FOREIGN KEY(person_id) REFERENCES persons(id)
);