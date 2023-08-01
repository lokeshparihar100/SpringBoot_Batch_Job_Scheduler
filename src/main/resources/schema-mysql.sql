CREATE TABLE datasource1.person_name (
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

INSERT INTO datasource1.person_name (first_name, last_name) VALUES
    ('John', 'Doe'),
    ('Alice', 'Smith'),
    ('Bob', 'Johnson');

CREATE TABLE datasource1.person_details (
    address VARCHAR(50),
    mobile_number int8(20)
);

INSERT INTO datasource1.person_details (address, mobile_number) VALUES
    ('Bangalore', 9836472834),
    ('Delhi', 7610000266),
    ('Pune', 7012373922);
 

CREATE TABLE datasource1.person_job (
    job_title VARCHAR(50),
    company VARCHAR(50),
    team VARCHAR(20)
);

INSERT INTO datasource1.person_job (job_title, company, team) VALUES
    ('System', 'TCS','Manager'),
    ('Coder', 'APP','Test'),
    ('QA', 'GOO','Dev');