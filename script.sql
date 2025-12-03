-- Script de carga inicial para H2: divisiones, profesores y programas educativos
-- Limpieza opcional de tablas (ajusta según tu necesidad)
-- DELETE FROM programas_educativos;
-- DELETE FROM profesores;
-- DELETE FROM divisiones;

-- Divisiones
INSERT INTO divisiones (id, nombre, activo) VALUES (1, 'División de Ingenierías', TRUE);
INSERT INTO divisiones (id, nombre, activo) VALUES (2, 'División de Ciencias Económicas', TRUE);
INSERT INTO divisiones (id, nombre, activo) VALUES (3, 'División de Ciencias Sociales', FALSE);

-- Profesores (sin división asignada inicialmente)
-- INSERT INTO profesores (id, nombre, apellidos, email, activo, division_id) VALUES (1, 'Ana', 'García López', 'ana.garcia@uni.edu', TRUE, NULL);
-- INSERT INTO profesores (id, nombre, apellidos, email, activo, division_id) VALUES (2, 'Carlos', 'Pérez Ramos', 'carlos.perez@uni.edu', TRUE, NULL);
-- INSERT INTO profesores (id, nombre, apellidos, email, activo, division_id) VALUES (3, 'María', 'Torres Díaz', 'maria.torres@uni.edu', FALSE, NULL);

-- Programas educativos (asociados a divisiones)
INSERT INTO programas_educativos (id, programa, activo, division_id) VALUES (1, 'Ingeniería en Sistemas Computacionales', TRUE, 1);
INSERT INTO programas_educativos (id, programa, activo, division_id) VALUES (2, 'Ingeniería Industrial', TRUE, 1);
INSERT INTO programas_educativos (id, programa, activo, division_id) VALUES (3, 'Licenciatura en Administración', TRUE, 2);
INSERT INTO programas_educativos (id, programa, activo, division_id) VALUES (4, 'Licenciatura en Contaduría', TRUE, 2);
INSERT INTO programas_educativos (id, programa, activo, division_id) VALUES (5, 'Sociología', FALSE, 3);

-- Ejemplos de asignaciones de división a profesor (opcional)
-- UPDATE profesores SET division_id = 1 WHERE id = 1; -- Ana a Ingenierías
-- UPDATE profesores SET division_id = 2 WHERE id = 2; -- Carlos a Económicas
