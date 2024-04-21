drop database if exists ooad;
create database ooad;
use ooad;

CREATE TABLE Users(
    uid BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (uid)
);

CREATE TABLE user_groups (
    gid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    adminid BIGINT NOT NULL, 
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    gdesc VARCHAR(255),
    maxlimit int NOT NULL,
    FOREIGN KEY (adminid) REFERENCES users(uid)
);

CREATE TABLE discussions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gid BIGINT NOT NULL,
    userid bigint NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    message TEXT NOT NULL,
    FOREIGN KEY (gid) REFERENCES user_groups(gid),
    FOREIGN KEY (userid) REFERENCES users(uid)
);

CREATE TABLE file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userid BIGINT,
    name VARCHAR(255),
    parentfolder VARCHAR(255),
    type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userid) REFERENCES users(uid)
);

ALTER TABLE file ADD INDEX idx_name (name);
ALTER TABLE file ADD FOREIGN KEY (parentfolder) REFERENCES file(name);

CREATE TABLE file_content (
    id BIGINT PRIMARY KEY,
    code TEXT,
    language VARCHAR(255),
    lastmodified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES file(id) ON DELETE CASCADE
);

CREATE TABLE group_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userid BIGINT NOT NULL,
    gid BIGINT NOT NULL,
    joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastseen TIMESTAMP,
    FOREIGN KEY (gid) REFERENCES user_groups(gid),
    FOREIGN KEY (userid) REFERENCES users(uid)
);

CREATE TABLE securityquestions (
    userid BIGINT PRIMARY KEY,
    q1 VARCHAR(255),
    q2 VARCHAR(255),
    a1 VARCHAR(255),
    a2 VARCHAR(255),
    FOREIGN KEY (userid) REFERENCES users(uid)
);