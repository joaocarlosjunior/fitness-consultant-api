INSERT INTO tbl_muscle_group(name_group)
VALUES ('Nome grupo muscular');

INSERT INTO tbl_exercise_name(exercise_name, id_muscle_group)
VALUES ('Nome do exercicio', 1);

INSERT INTO tbl_user(first_name, last_name, email, phone, password,  role, created_at, updated_at,
                     is_enabled)
VALUES ('Nome usuario', 'Sobrenome usuario', 'email_usuario@email.com', '77999999999', '12345', 'ROLE_USER',
        CURRENT_DATE, CURRENT_DATE, true);

INSERT INTO tbl_periodization(name, number_weeks, start_date, id_user, created_at, updated_at)
VALUES ('Nome Periodizacao', 4, CURRENT_DATE, 1, CURRENT_DATE, CURRENT_DATE);

INSERT INTO tbl_training(training_type, training_name, is_done, created_at, updated_at, id_periodization)
VALUES ('A', 'Nome do treino', false, CURRENT_DATE, CURRENT_DATE, 1);

/*Exercicios*/
INSERT INTO tbl_exercise(series, repetitions, initial_load, final_load, method_exercise, id_training,
                         id_exercise_name)
VALUES (4, 'Repeticoes', 20, 30, 'Metodo', 1, 1);
INSERT INTO tbl_exercise(series, repetitions, initial_load, final_load, method_exercise, id_training,
                         id_exercise_name)
VALUES (4, 'Repeticoes', 20, 30, 'Metodo', 1, 1);
INSERT INTO tbl_exercise(series, repetitions, initial_load, final_load, method_exercise, id_training,
                         id_exercise_name)
VALUES (4, 'Repeticoes', 20, 30, 'Metodo', 1, 1);
INSERT INTO tbl_exercise(series, repetitions, initial_load, final_load, method_exercise, id_training,
                         id_exercise_name)
VALUES (4, 'Repeticoes', 20, 30, 'Metodo', 1, 1);
