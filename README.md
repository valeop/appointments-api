<div align="center"><img src="https://spring.io/img/spring-2.svg" width=350px alt="Spring Framework Logo" title="Spring Framework"></div>

# RESTful Appointments API
This API is created to create, update or delete appointments, manage the personal and the services of a Health Center based on the users' roles and their permissions

## Features
- User register and login
- JWT authentication
- access and control according with role (PATIENT, DOCTOR, ADMIN)
- CRUD operations
- Try endpoints with Postman

## Testing (In progress)
- API tests with Karate
- Performance tests with Gatling

## Deploy (In progress)
- Render
- Docker

## Tech Stack
- Java
- Spring Boot
- PostgreSQL powered by Neon Serverless
- Postman
- JWT
- Render
- Docker
- Karate
- Gatling

## Steps to run it
This session will be just to interact with the system locally (at least for now). So, please Follow the next steps:

### 1. Create the SQL DB
<img width="1181" height="521" alt="appointments-api" src="https://github.com/user-attachments/assets/7f8414f8-6236-4bf9-82f7-0bdd5e9a5ef4" />

This entity-relation diagram shows how the system will work with tables connected to each other. Below you'll find the specific scripts used on Postgres to create tables, fields and dummies registers for you to practice:

**Script to create tables:**
```
-- 1) genders
CREATE TABLE IF NOT EXISTS genders(
  gender_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  gender_name VARCHAR(20) UNIQUE NOT NULL
);

-- 2) blood_types
CREATE TABLE IF NOT EXISTS blood_types(
  blood_type_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  blood_type_name VARCHAR(20) UNIQUE NOT NULL
);

-- 3) persons
CREATE TABLE IF NOT EXISTS persons(
  person_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  gender_id INT NOT NULL,
  blood_type_id INT NOT NULL,
  identity_card VARCHAR(20) UNIQUE NOT NULL,
  firstname VARCHAR(20) NOT NULL,
  lastname VARCHAR(20) NOT NULL,
  birthdate DATE NOT NULL,
  CONSTRAINT fk_gender FOREIGN KEY (gender_id) REFERENCES genders(gender_id) ON DELETE CASCADE,
  CONSTRAINT fk_blood_type FOREIGN KEY (blood_type_id) REFERENCES blood_types(blood_type_id) ON DELETE CASCADE
);

-- 4) service_types
CREATE TABLE IF NOT EXISTS service_types(
  service_type_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  service_type_name VARCHAR(20) UNIQUE NOT NULL
);

-- 5) services
CREATE TABLE IF NOT EXISTS services(
  service_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  service_type_id INT NOT NULL,
  service_name VARCHAR(40) UNIQUE NOT NULL,
  CONSTRAINT fk_service_type FOREIGN KEY (service_type_id) REFERENCES service_types(service_type_id) ON DELETE CASCADE
);

-- 6) roles
CREATE TABLE IF NOT EXISTS roles(
  role_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  role_name VARCHAR(20) UNIQUE NOT NULL
);

-- 7) users
CREATE TABLE IF NOT EXISTS users(
  user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  person_id INT NOT NULL,
  role_id INT NOT NULL,
  email VARCHAR(40) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE,
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- 8) doctors
CREATE TABLE IF NOT EXISTS doctors(
  doctor_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INT NOT NULL,
  person_id INT NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE
);

-- 9) doctor_services
CREATE TABLE IF NOT EXISTS doctor_services(
  doctor_service_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  doctor_id INT NOT NULL,
  service_id INT NOT NULL,
  CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
  CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES services(service_id) ON DELETE CASCADE
);

-- 10) appointments
CREATE TABLE IF NOT EXISTS appointments(
  appointment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INT NOT NULL,
  doctor_service_id INT NOT NULL,
  appointment_datetime TIMESTAMP NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  CONSTRAINT fk_doctor_service FOREIGN KEY (doctor_service_id) REFERENCES doctor_services(doctor_service_id) ON DELETE CASCADE
);
```
**Script to insert data into each table:**
```
-- 1) genders
INSERT INTO genders(gender_name)
  VALUES ('Female'),
  ('Male'),
  ('Non-binary'),
  ('Prefer not to say');

-- 2) blood_types
INSERT INTO blood_types(blood_type_name)
  VALUES ('A+'),
  ('O+'),
  ('B+'),
  ('AB+'),
  ('A-'),
  ('O-'),
  ('B-'),
  ('AB-');

-- 3) persons
INSERT INTO persons(gender_id, blood_type_id, identity_card, firstname, lastname, birthdate)
  VALUES (1, 1, '729301', 'Cate', 'Morgan', '1970-05-11'), 
  (2, 2, '693548', 'Robert', 'Jakefield', '1987-09-20'),
  (1, 3, '774929', 'Lauren', 'Dorian', '1995-11-17'), 
  (2, 4, '110822', 'Jacobo', 'Serre', '1975-01-09'), 
  (1, 5, '993863', 'Julie', 'Bellec', '2001-06-25'),
  (1, 6, '882731', 'Elena', 'Villalobos', '1992-03-14'),
  (2, 1, '445902', 'Marco', 'Polo', '1985-07-22'),
  (3, 2, '339184', 'Alex', 'Vance', '1998-11-30'),
  (1, 5, '220938', 'Sofia', 'Ricci', '2000-01-05'),
  (2, 8, '556127', 'Julian', 'Casablancas', '1978-08-28'),
  (4, 7, '771029', 'Sam', 'Altman', '1982-04-22'),
  (1, 3, '112233', 'Clara', 'Oswald', '1994-06-13'),
  (2, 4, '998877', 'Arthur', 'Morgan', '1863-04-15'),
  (3, 6, '664422', 'Robin', 'Buckley', '2002-12-01'),
  (1, 1, '554433', 'Maria', 'Curie', '1967-11-07'),
  (2, 2, '887766', 'Victor', 'Frankenstein', '1988-10-31'),
  (3, 6, '002933', 'Alex', 'Scherbatsky', '1978-01-22'), 
  (2, 7, '740585', 'Camilo', 'Reyes', '2005-04-16'), 
  (2, 8, '960155', 'Paul', 'McConell', '1999-12-24'), 
  (4, 1, '665478', 'Kai', 'Zinman', '2026-01-12'), 
  (1, 2, '229867', 'Elliet', 'Peterson', '1959-02-24');

-- 4) service_types
INSERT INTO service_types(service_type_name)
  VALUES ('General'),
  ('Dental'),
  ('Ophthalmology'),
  ('Specialist'),
  ('Laboratory'),
  ('Diagnostic radiology');

-- 5) services
INSERT INTO services(service_type_id, service_name)
  VALUES (1, 'General practitioner'),
  (2, 'Dental consultation'),
  (3, 'Ophthalmology consultation'),
  (4, 'Dermatology consultation'), 
  (4, 'Nutritional counseling'), 
  (4, 'Pediatric consultation'),
  (4, 'Cardiology consultation'),
  (4, 'Neurology consultation'),
  (4, 'Psychiatry consultation'),
  (4, 'Psychology consultation'),
  (4, 'Endocrinology consultation'),
  (4, 'Neumology consultation'),
  (4, 'Rheumatology consultation'),
  (4, 'Infectology consultation'),
  (5, 'Sample collection and analysis'),
  (6, 'Radiology consultation');

-- 6) roles
INSERT INTO roles(role_name)
  VALUES ('PATIENT'),
  ('DOCTOR'),
  ('ADMIN');

-- 7) users
CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO users(person_id, role_id, email, password_hash) 
  VALUES (1, 2, 'cate.morgan@hospital.com', crypt('password', gen_salt('bf'))),
  (2, 2, 'robert.jakefield@hospital.com', crypt('password', gen_salt('bf'))),
  (3, 2, 'lauren.dorian@hospital.com', crypt('password', gen_salt('bf'))),
  (4, 2, 'jacobo.serre@hospital.com', crypt('password', gen_salt('bf'))),
  (5, 2, 'julie.bellec@hospital.com', crypt('password', gen_salt('bf'))),
  (6, 2, 'elena.villalobos@hospital.com', crypt('password', gen_salt('bf'))),
  (7, 2, 'marco.polo@hospital.com', crypt('password', gen_salt('bf'))),
  (8, 2, 'alex.vance@hospital.com', crypt('password', gen_salt('bf'))),
  (9, 2, 'sofia.ricci@hospital.com', crypt('password', gen_salt('bf'))),
  (10, 2, 'julian.casablancas@hospital.com', crypt('password', gen_salt('bf'))),
  (11, 2, 'sam.altman@hospital.com', crypt('password', gen_salt('bf'))),
  (12, 2, 'clara.oswald@hospital.com', crypt('password', gen_salt('bf'))),
  (13, 2, 'arthur.morgan@hospital.com', crypt('password', gen_salt('bf'))),
  (14, 2, 'robin.buckley@hospital.com', crypt('password', gen_salt('bf'))),
  (15, 2, 'maria.curie@hospital.com', crypt('password', gen_salt('bf'))),
  (16, 2, 'victor.frankenstein@hospital.com', crypt('password', gen_salt('bf'))),
  (17, 1, 'alex.scherbatsky@patientmail.com', crypt('password', gen_salt('bf'))),
  (18, 1, 'camilo.reyes@patientmail.com', crypt('password', gen_salt('bf'))),
  (19, 1, 'paul.mcconell@patientmail.com', crypt('password', gen_salt('bf'))),
  (20, 1, 'kai.zinman@patientmail.com', crypt('password', gen_salt('bf'))),
  (21, 1, 'elliet.peterson@patientmail.com', crypt('password', gen_salt('bf')));

-- 8) doctors
INSERT INTO doctors(user_id, person_id) 
  VALUES (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8),
  (9, 9),
  (10, 10),
  (11, 11),
  (12, 12),
  (13, 13),
  (14, 14),
  (15, 15),
  (16, 16);

-- 9) doctor_services
INSERT INTO doctor_services(doctor_id, service_id) 
  VALUES (1, 16),
  (2, 4),
  (3, 14),
  (4, 2),
  (5, 15),
  (6, 10),
  (7, 3),
  (8, 6),
  (9, 12),
  (10, 5),
  (11, 1),
  (12, 8),
  (13, 13),
  (14, 7),
  (15, 11),
  (16, 9);
```
**Script to delete tables (necessary cases only):**
```
DROP TABLE IF EXISTS
  genders,
  blood_types,
  persons, 
  roles,
  service_types,
  services, 
  doctors,
  users,
  doctor_services, 
  appointments 
  CASCADE;
```
Once you have created the Postgres DB with Neon Serverless or locally. Follow the next step.
### 2. Clone this repository and sync the pom.xml file in your IDE of preference
In your path:
```
git clone https://github.com/valeop/appointments-api.git
```
Open your IDE and sync!

### 3. Set up your environment variables
Prepare your IDE to work with .env files or just set them up locally in your IDE settings. There's a **`.env.example`** file that shows you the variables needed for this project to run

### 4. Run the project and test the endpoints with **Postman**
Follow this link to access to Postman API endpoinds tests. You'll find some simple descriptions for each endpoint there:
[Postman-Appointments-API-endpoints-link](https://www.postman.com/lunar-module-cosmologist-74614115/workspace/personal-projects/folder/33250817-3cde0ecb-5020-4eb4-b058-e4430f3621d5?action=share&creator=33250817)

#### Roles permissions (In progress)

### 5. Try the project deployed (In progress)
