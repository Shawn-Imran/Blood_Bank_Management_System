CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;

-- create aan enum for user type
CREATE TYPE user_type AS ENUM ('admin', 'donor',  'receiver');

CREATE TYPE blood_group AS ENUM ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-');

-- create a table for user
create table "users"(
                       id int primary key GENERATED ALWAYS AS IDENTITY,
                       username varchar(255) not null,
                       password varchar(255) not null,
                       email varchar(255) not null,
                       phone varchar(255) not null,
                       user_type user_type not null default 'donor',
                       blood_group blood_group not null,
                       create_time timestamp not null default current_timestamp,
                       update_time timestamp not null default current_timestamp
);

-- Create a function to update the update_time column
CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger to call the function on update
CREATE TRIGGER update_user_timestamp
    BEFORE UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();


-- create a table for blood store details like how much blood is available
create table "blood_store"(
                       id int primary key GENERATED ALWAYS AS IDENTITY,
                       blood_group blood_group not null unique,
                       quantity int not null,
                       create_time timestamp not null default current_timestamp,
                       update_time timestamp not null default current_timestamp
);

-- make blood_group column indexed
CREATE INDEX blood_group_index ON blood_store(blood_group);