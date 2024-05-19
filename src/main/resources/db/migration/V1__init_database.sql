CREATE TABLE IF NOT EXISTS tbl_user(
    id_user bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name varchar(15) NOT NULL,
    last_name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    phone varchar(20) NOT NULL,
    password varchar,
    is_active boolean NOT NULL,
    role_user(20) varchar NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id_user),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT phone_unique UNIQUE (phone),
    CONSTRAINT role_check CHECK (role_user in ('ROLE_USER','ROLE_ADMIN'))
);

CREATE TABLE IF NOT EXISTS tbl_training (
    id_training bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    training_type varchar(8),
    is_done boolean,
    created_at timestamp,
    updated_at timestamp,
    id_user bigint,
    PRIMARY KEY (id_training),
    CONSTRAINT training_unique UNIQUE (training),
    FOREIGN KEY (id_user) REFERENCES tbl_user (id_user)
);

CREATE TABLE IF NOT EXISTS tbl_training_time (
    id_training_time bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    start_time timestamp,
    final_time timestamp,
    training_date date,
    id_training bigint,
    PRIMARY KEY (id_training_time),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training)
)

CREATE TABLE IF NOT EXISTS tbl_muscle_group (
    id_muscle_group bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_group varchar(50),
    PRIMARY KEY (id_muscle_group)
);

CREATE TABLE IF NOT EXISTS tbl_musclegroup_training (
    id_muscle_group bigint,
    id_training bigint
);

CREATE TABLE IF NOT EXISTS tbl_exercise_name (
    id_exercise_name bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    exercise_name varchar(30) NOT NULL,
    CONSTRAINT exercise_name_unique UNIQUE (exercise_name),
    PRIMARY KEY (id_exercise_name)
);

CREATE TABLE IF NOT EXISTS tbl_heating (
    id_heating bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    series smallint,
    repetitions varchar(10),
    initial_load smallint,
    final_load smallint,
    method_exercise varchar(30),
    id_training bigint,
    id_exercise_name bigint,
    PRIMARY KEY (id_heating),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training),
    FOREIGN KEY (id_exercise_name) REFERENCES tbl_exercise_name (id_exercise_name)
);

CREATE TABLE IF NOT EXISTS tbl_exercise (
    id_exercise bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    series smallint,
    repetitions varchar(10),
    initial_load smallint,
    final_load smallint,
    method_exercise varchar(30),
    id_training bigint,
    id_exercise_name bigint,
    PRIMARY KEY (id_exercise),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training),
    FOREIGN KEY (id_exercise_name) REFERENCES tbl_exercise_name (id_exercise_name)
);