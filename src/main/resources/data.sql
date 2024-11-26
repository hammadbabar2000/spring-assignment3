INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('fahad.jan@sheridancollege.ca', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('frank@sheridancollege.ca', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('admin@sheridancollege.ca', '$2a$10$gnyPemF6mcQXbWz1TOTlTufqLdzVrkOjSjWkRrDJSPk7Nq7rVp9aO', 1);


INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');
 
INSERT INTO sec_role (roleName)
VALUES ('ROLE_GUEST');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_ADMIN');
 

 
INSERT INTO user_role (userId, roleId)
VALUES (1, 1);
 
INSERT INTO user_role (userId, roleId)
VALUES (2, 2);

INSERT INTO user_role (userId, roleId)
VALUES (3, 3);

INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS)
VALUES ('The Way Of Kings', 'Brandon Sanderson', 'Tor Books', 10, 'Finished');

INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS)
VALUES ('The Final Empire', 'Brandon Sanderson', 'Tor Books', 10, 'Finished');

INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS)
VALUES ('The Hero of Ages', 'Brandon Sanderson', 'Tor Books', 10, 'Finished');

INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS)
VALUES ('The Well of Ascension', 'Brandon Sanderson', 'Tor Books', 10, 'Finished');

INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS)
VALUES ('Oathbringer', 'Brandon Sanderson', 'Tor Books', 10, 'Finished');