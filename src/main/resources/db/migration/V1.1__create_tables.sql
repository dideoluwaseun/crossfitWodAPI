
CREATE TABLE IF NOT EXISTS wod
(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(255),
    mode varchar(255),
    equipment varchar(255),
    exercises varchar(255),
    created_at date,
    updated_at date,
    trainer_tips varchar(255),
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS roles
(
   id int NOT NULL GENERATED ALWAYS AS IDENTITY,
   name varchar(255),
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(255),
    gender varchar(255),
    dob date,
    email varchar(255),
    username varchar(255),
    password varchar(255),
    roles_id integer REFERENCES roles (id),
    created_at date,
    updated_at date,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS workout_records
(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY ,
    workout_records varchar(255),
    members_id integer REFERENCES members (id),
    wod_id integer REFERENCES wod (id),
    created_at date,
    PRIMARY KEY (id)

);

CREATE TABLE IF NOT EXISTS members_roles
(
  id int NOT NULL GENERATED ALWAYS AS IDENTITY,
  members_id integer REFERENCES members (id),
  roles_id integer REFERENCES roles (id),
  PRIMARY KEY (id)
)






