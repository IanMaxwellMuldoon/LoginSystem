use `userdatabase`; 

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Blog`;
DROP TABLE IF EXISTS `Follow`;
DROP TABLE IF EXISTS `Hobbies`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `Tags`;
DROP TABLE IF EXISTS `Comments`;
SET FOREIGN_KEY_CHECKS = 1;



CREATE TABLE users (
id int (15) NOT NULL AUTO_INCREMENT,
username VARCHAR (30) NOT NULL,
password VARCHAR (30) NOT NULL,
firstname VARCHAR (30) NOT NULL,
lastname VARCHAR (30) NOT NULL,
email VARCHAR (30) NOT NULL,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE `Hobbies`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`usersID` int(15) NOT NULL,
`hiking` boolean ,
`swimming` boolean,
`calligraphy` boolean,
`bowling` boolean,
`movie` boolean,
`cooking` boolean,
`dancing` boolean,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
FOREIGN KEY (`usersID`)
REFERENCES users(id)
);

CREATE TABLE `Follow`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`FollowedUserid` int(15) NOT NULL,
`FollowingUserid` int(15) NOT NULL,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
FOREIGN KEY(`FollowedUserid`)
REFERENCES users(id),
FOREIGN KEY(`FollowingUserid`)
REFERENCES users(id)
);

CREATE TABLE `Blog`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`Subject` varchar(30) NOT NULL,
`Description` varchar(30) NOT NULL,
`usersID` int(15) NOT NULL,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(`id`),
FOREIGN KEY (`usersID`)
REFERENCES users(id)
);

CREATE TABLE `Comments`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`usersID` int(15) NOT NULL,
`Description` varchar(30) NOT NULL,
`Sentiment` boolean,
`blogID` int(15) NOT NULL,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
FOREIGN KEY (`usersID`)
REFERENCES users(id),
FOREIGN KEY (`blogID`)
REFERENCES blog(id)
);

CREATE TABLE `Tags`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`blogID` int(15) NOT NULL,
`Title` varchar(30) NOT NULL,
createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
FOREIGN KEY (`blogID`)
REFERENCES blog(id)
);

--InsertData
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('IanMuld','pass','Ian','Muldoon','ian.muldoon@gmail.com');
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('ColinShep','password','Colin','Shepard','colin.shepard@gmail.com');
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('NikkoO','pass123','Nikko','Orillo','n.Orillo@gmail.com');
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('JacobSilly','password','Jacob','Sickagoose','J.sick@gmail.com');
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('Kenz','pass','Makenzie','McCormick','mccormick@gmail.com');
INSERT INTO users(username,password,firstname,lastname,email)
VALUES('JDoe','password123','John','Doe','JDoe@gmail.com');

-- Insert data into hobbies
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(1,true,true,true,false,false,false,true);
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(3,false,false,false,true,true,true,false);
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(2,true,false,true,false,true,false,false);
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(5,false,true,true,true,true,false,false);
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(4,true,true,false,false,true,true,false);
INSERT INTO Hobbies(usersID,hiking,swimming,calligraphy,bowling,movie,cooking,dancing)
VALUES(6,false,false,true,false,true,false,true);

-- Insert data into Follow
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(1,2);
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(1,3);
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(2,4);
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(4,5);
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(2,1);
INSERT INTO Follow(FollowedUserid, FollowingUserid)
VALUES(5,3);

-- Insert data into Blog
INSERT INTO Blog(Subject, Description, usersID)
VALUES('Animals','I like animals',1);
INSERT INTO Blog(Subject, Description, usersID)
VALUES('Frogs','I like frogs',2);
INSERT INTO Blog(Subject, Description, usersID)
VALUES('Bikes','I like bikes',3);
INSERT INTO Blog(Subject, Description, usersID)
VALUES('Games','I like games',4);
INSERT INTO Blog(Subject, Description, usersID)
VALUES('Exercise','I like exercise',5);

-- Insert data into Comments
INSERT INTO Comments(usersID,Description,Sentiment,Blogid)
Values(1,'wow i liked it',true,2);
INSERT INTO Comments(usersID,Description,Sentiment,Blogid)
Values(2,'wow i disliked it',false,4);
INSERT INTO Comments(usersID,Description,Sentiment,Blogid)
Values(4,'thats very true',true,5);
INSERT INTO Comments(usersID,Description,Sentiment,Blogid)
Values(3,'wow i liked it',true,2);
INSERT INTO Comments(usersID,Description,Sentiment,Blogid)
Values(5,'wow i hated it',false,3);

-- Insert Data into Tags
INSERT INTO Tags(blogID,Title)
Values(1,'Animals');
INSERT INTO Tags(blogID,Title)
Values(2,'Animals');
INSERT INTO Tags(blogID,Title)
Values(2,'Frogs');
INSERT INTO Tags(blogID,Title)
Values(3,'Exercise');
INSERT INTO Tags(blogID,Title)
Values(4,'VideoGames');
INSERT INTO Tags(blogID,Title)
Values(5,'Workout');










