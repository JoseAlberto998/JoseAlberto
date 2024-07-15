INSERT INTO mascota (especie, raza, edad, codigo_identificacion, dni_responsable, inactive) 
VALUES ('Perro', 'salchicha', '5', 'ID123456', '12345678A', false);

INSERT INTO mascota (especie, raza, edad, codigo_identificacion, dni_responsable, inactive) 
VALUES ('Gato', 'egipcio', '3', 'ID654321', '87654321B', false);

INSERT INTO ingreso (fecha_alta, fecha_finalizacion, estado, id_mascota, dni_persona) 
VALUES ('2024-07-26', NULL, 'ALTA', 1, '12345678A');

INSERT INTO usuario(id_usuario, nombre, contrasena) VALUES (1, 'admin', '$2a$10$AoWXw290Ttmg/GT.xnKLduBmUA0ztdw15Vk9INvV4jSKIzpt8y/7C');

