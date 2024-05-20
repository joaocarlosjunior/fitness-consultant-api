CREATE TABLE IF NOT EXISTS tbl_user (
    id_user BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR,
    is_active BOOLEAN NOT NULL,
    role_user VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id_user),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT phone_unique UNIQUE (phone),
    CONSTRAINT role_check CHECK (role_user in ('ROLE_USER','ROLE_ADMIN'))
);

CREATE TABLE IF NOT EXISTS tbl_training (
    id_training BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    training_type VARCHAR(8),
    is_done BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    id_user BIGINT,
    PRIMARY KEY (id_training),
    CONSTRAINT training_type_unique UNIQUE (training_type),
    FOREIGN KEY (id_user) REFERENCES tbl_user (id_user)
);

CREATE TABLE IF NOT EXISTS tbl_training_time (
    id_training_time BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    start_time TIMESTAMP,
    final_time TIMESTAMP,
    training_date date,
    id_training BIGINT,
    PRIMARY KEY (id_training_time),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training)
);

CREATE TABLE IF NOT EXISTS tbl_muscle_group (
    id_muscle_group BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_group VARCHAR(50),
    PRIMARY KEY (id_muscle_group)
);

CREATE TABLE IF NOT EXISTS tbl_musclegroup_training (
    id_muscle_group BIGINT,
    id_training BIGINT
);

CREATE TABLE IF NOT EXISTS tbl_exercise_name (
    id_exercise_name BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    exercise_name VARCHAR(30) NOT NULL,
    CONSTRAINT exercise_name_unique UNIQUE (exercise_name),
    PRIMARY KEY (id_exercise_name)
);

CREATE TABLE IF NOT EXISTS tbl_heating (
    id_heating BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    series SMALLINT,
    repetitions VARCHAR(10),
    initial_load SMALLINT,
    final_load SMALLINT,
    method_exercise VARCHAR(30),
    id_training BIGINT,
    id_exercise_name BIGINT,
    PRIMARY KEY (id_heating),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training),
    FOREIGN KEY (id_exercise_name) REFERENCES tbl_exercise_name (id_exercise_name)
);

CREATE TABLE IF NOT EXISTS tbl_exercise (
    id_exercise BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    series SMALLINT,
    repetitions VARCHAR(10),
    initial_load SMALLINT,
    final_load SMALLINT,
    method_exercise VARCHAR(30),
    id_training BIGINT,
    id_exercise_name BIGINT,
    PRIMARY KEY (id_exercise),
    FOREIGN KEY (id_training) REFERENCES tbl_training (id_training),
    FOREIGN KEY (id_exercise_name) REFERENCES tbl_exercise_name (id_exercise_name)
);