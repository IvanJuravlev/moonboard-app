
DROP TABLE IF EXISTS problem CASCADE;
DROP TABLE IF EXISTS problem_hooks CASCADE;
DROP TABLE IF EXISTS grades CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS grades(
   grade_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
   grade    VARCHAR (20)
);


CREATE TABLE IF NOT EXISTS problem(
    problem_id     BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    creator_id     BIGINT                                              NOT NULL,
    name           VARCHAR(100)                                        NOT NULL,
    description    VARCHAR(255)                                        NOT NULL,
    grade          BIGINT                                              NOT NULL,
    rating         INTEGER,
    video_url      VARCHAR(255),
    climbs         INTEGER,
    average_grade  VARCHAR(20),
    published_date TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT FK_PROBLEM_ON_GRADE FOREIGN KEY (grade) REFERENCES grades (grade_id)

);

CREATE TABLE IF NOT EXISTS problem_hooks(
    problem_id           BIGINT NOT NULL,
    problem_number_field VARCHAR(20)                                   NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES problem(problem_id)

CREATE TABLE IF NOT EXISTS user_type
(
    type_id      BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name    VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id      BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name    VARCHAR(60) NOT NULL,
    email   VARCHAR(60) NOT NULL,
    city    VARCHAR(90),
    rate    BIGINT
);




