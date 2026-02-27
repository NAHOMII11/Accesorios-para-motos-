const { Client } = require('pg');

const client = new Client({
  host: 'localhost', 
  user: 'reto',
  password: 'reto',
  database: 'authdb',
  port: 5432,
});

async function configurarBaseDeDatos() {
  try {
    await client.connect();
 

    // 1. Crear tabla ROLES
    await client.query(`
      CREATE TABLE IF NOT EXISTS roles (
        id SERIAL PRIMARY KEY,
        name VARCHAR(50) UNIQUE NOT NULL,
        description TEXT
      );
    `);

    // 2. Crear tabla USERS
    await client.query(`
      CREATE TABLE IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        is_active BOOLEAN DEFAULT TRUE
      );
    `);

    // 3. Crear tabla TOKENS (Relacionada con Users)
    await client.query(`
      CREATE TABLE IF NOT EXISTS tokens (
        id SERIAL PRIMARY KEY,
        user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
        token TEXT NOT NULL,
        expires TIMESTAMP NOT NULL,
        revoked BOOLEAN DEFAULT FALSE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      );
    `);

    // 4. Crear tabla USER_ROL (Relación Muchos a Muchos entre Users y Roles)
    await client.query(`
      CREATE TABLE IF NOT EXISTS user_rol (
        user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
        rol_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
        PRIMARY KEY (user_id, rol_id)
      );
    `);


  } finally {
    await client.end();
  }
}

configurarBaseDeDatos();