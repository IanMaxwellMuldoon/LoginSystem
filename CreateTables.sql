use `userdatabase`; 

DROP TABLE IF EXISTS `Tags`;
DROP TABLE IF EXISTS `Hobbies`;
DROP TABLE IF EXISTS `Comments`;
DROP TABLE IF EXISTS `Follow`;
DROP TABLE IF EXISTS `Blog`;


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
PRIMARY KEY (`id`),
FOREIGN KEY (`usersID`)
REFERENCES users(id)
);

CREATE TABLE `Follow`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`FollowedUserid` int(15) NOT NULL,
`FollowingUserid` int(15) NOT NULL,
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
PRIMARY KEY(`id`),
FOREIGN KEY (`usersID`)
REFERENCES users(id)
);

CREATE TABLE `Comments`(
`id` int(15) NOT NULL AUTO_INCREMENT,
`usersID` int(15) NOT NULL,
`Description` varchar(30) NOT NULL,
`Sentiment` boolean NOT NULL,
`blogID` int(15) NOT NULL,
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
PRIMARY KEY (`id`),
FOREIGN KEY (`blogID`)
REFERENCES blog(id)
);

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








