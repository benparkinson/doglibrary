# drop database doggos;

create database doggos;

use doggos;

create table Breeds
(
BreedId int not null auto_increment primary key,
BreedName varchar(100) not null
);

create table Dogs
(dogid int not null auto_increment primary key,
name varchar(100) not null,
breedid int not null,
foreign key fk_breed(breedid)
references Breeds(BreedId)
);

create table users
(userid int not null auto_increment primary key,
username varchar(100) not null unique,
firstname varchar(100) not null,
surname varchar(100) not null,
favouritebreedid int not null,
passwordhash varchar(500) not null,
foreign key fk_favebreed(favouritebreedid)
references breeds(breedid)
);

create table withdrawals
(dogid int not null,
userid int not null,
withdrawnfrom datetime not null,
withdrawnto datetime null,
foreign key fk_withdrawndog(dogid)
references dogs(dogid),
foreign key fk_withdrawinguser(userid)
references users(userid)
);

create table faves
(userid int not null,
dogid int not null,
favefrom datetime not null,
faveto datetime null,
foreign key fk_favedog(dogid)
references dogs(dogid),
foreign key fk_faveuser(userid)
references users(userid)
);

create procedure getAllDogs()
select d.dogid, d.name, b.breedname
from dogs d
inner join
breeds b
on d.breedid = b.breedid;

DELIMITER //
create procedure getAllUsers()
begin
select u.userid, u.username, u.firstname, u.surname, b.breedname, u.passwordhash
from users u
inner join
breeds b
on u.breedid = b.breedid;
end //
DELIMITER ;

create procedure getCurrentWithdrawals()
select w.dogid, w.userid, w.withdrawnfrom, w.withdrawnto from withdrawals w
where w.withdrawnto is null;

create procedure getFavesForUser(param_userid int)
select dogid
from faves
where userid = param_userid;

DELIMITER //
create procedure addUser(param_username varchar(100), param_firstname varchar(100), param_surname varchar(100), param_favourite_breed varchar(100), param_passwordhash varchar(500), out param_newuserid int)
begin
declare fave_breed_id int;
set fave_breed_id = (select breedid from breeds where breedname = param_favourite_breed);
if (fave_breed_id is NULL) then
    insert into breeds (breedName) values (param_favourite_breed); 
    set fave_breed_id = (select last_insert_id());
end if;
insert into users (username, firstname, surname, favouritebreedid, passwordhash) values (param_username, param_firstname, param_surname, fave_breed_id, param_passwordhash);
set param_newuserid = (select last_insert_id());
end //
DELIMITER ;

DELIMITER //
create procedure addDog(param_name varchar(100), param_breed varchar(100), out param_newdogid int)
begin
declare breed_id int;
set breed_id = (select breedid from breeds where breedname = param_breed);
if (breed_id is NULL) then
    insert into breeds (breedName) values (param_breed); 
    set breed_id = (select last_insert_id());
end if;
insert into dogs (name, breedid) values (param_name, breed_id);
set param_newdogid = (select last_insert_id());
end //
DELIMITER ;

DELIMITER //
create procedure withdrawDog(param_userid int, param_dogid int)
begin
insert into withdrawals (dogid, userid, withdrawnfrom, withdrawnto) values (param_dogid, param_userid, now(), null);
end //
DELIMITER ;

DELIMITER //
create procedure returnDog(param_userid int)
begin
update withdrawals set withdrawnto = now() where userid = param_userid;
end //
DELIMITER ;

DELIMITER //
create procedure favouriteDog(param_userid int, param_dogid int)
begin
insert into faves (userid, dogid, favefrom, faveto) values (param_userid, param_dogid, now(), null);
end //
DELIMITER ;

DELIMITER //
create procedure unFavouriteDog(param_userid int, param_dogid int)
begin
update faves set faveto = now() where userid = param_userid and dogid = param_dogid;
end //
DELIMITER ;