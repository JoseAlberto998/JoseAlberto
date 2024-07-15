
CREATE TABLE mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    especie VARCHAR(255) NOT NULL,
    raza VARCHAR(255) NOT NULL,
    edad VARCHAR(255) NOT NULL,
    codigo_identificacion VARCHAR(255) NOT NULL,
    dni_responsable VARCHAR(255) NOT NULL,
    inactive BOOLEAN
);

CREATE TABLE ingreso (
    id_ingreso INT AUTO_INCREMENT PRIMARY KEY,
    fecha_alta DATE NOT NULL,
    fecha_finalizacion DATE,
    estado VARCHAR(255),
    id_mascota INT,
    dni_persona VARCHAR(255),
    FOREIGN KEY (id_mascota) REFERENCES mascota(id_mascota)
);
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);