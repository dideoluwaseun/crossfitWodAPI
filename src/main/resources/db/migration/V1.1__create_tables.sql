
CREATE TABLE IF NOT EXISTS wod
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    mode varchar(255),
    equipment varchar(255),
    exercises varchar(255),
    created_at datetime,
    updated_at datetime,
    trainer_tips varchar(255),
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS roles
(
   id int NOT NULL AUTO_INCREMENT,
   name varchar(255),
   PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS members
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    gender varchar(255),
    dob datetime,
    email varchar(255),
    username varchar(255),
    password varchar(255),
    roles_id integer REFERENCES roles (id),
    created_at datetime,
    updated_at datetime,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS workout_records
(
    id int NOT NULL AUTO_INCREMENT,
    workout_records varchar(255),
    members_id integer REFERENCES members (id),
    wod_id integer REFERENCES wod (id),
    created_at datetime,
    PRIMARY KEY (`id`)

);

CREATE TABLE IF NOT EXISTS members_roles
(
  id int NOT NULL AUTO_INCREMENT,
  members_id integer REFERENCES members (id),
  roles_id integer REFERENCES roles (id),
  PRIMARY KEY (`id`)
)






