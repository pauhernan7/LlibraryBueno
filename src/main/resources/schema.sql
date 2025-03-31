CREATE TABLE IF NOT EXISTS books (
    id_llibre INT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    editorial VARCHAR(255),
    data_publicacio DATE,
    tematica VARCHAR(255),
    ISBN VARCHAR(20) UNIQUE NOT NULL  
);