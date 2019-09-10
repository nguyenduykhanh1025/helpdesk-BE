create database db_helpdesk;
use db_helpdesk;
create table categories (
    id int primary key not null auto_increment,
    name nvarchar(200)
);
create table skills (
    id int primary key not null auto_increment,
    name nvarchar(200),
    id_categories int,
    constraint foreign key(id_categories) references categories(id)
);
create table problems_type(
    id int primary key not null auto_increment,
    name nvarchar(200)
);
create table days_off_type(
    id int primary key not null auto_increment,
    name nvarchar(200)
);
create table status(
    id int not null primary key,
    name nvarchar(50)
);

create table roles(
	id int primary key auto_increment,
    name varchar(50)
);

create table users (
    id int  primary key not null auto_increment,
    email varchar(200),
    password varchar(200),
    firstName nvarchar(70),
    lastName nvarchar(70),
    age int,
    birthday date,
    sex bool, -- 0 : women 1: men
    address text,
    starting_day date
);

create table user_role(
	user_id int ,
    role_id int,
    constraint PK_user_role primary key (user_id,role_id),
    constraint FK_user foreign key (user_id) references users(id),
    constraint FK_role foreign key (role_id) references roles(id)
);

create table user_skill (
    id_user int not null,
    id_skill int not null,
    CONSTRAINT PK_skill_of_user PRIMARY KEY (id_user,id_skill),
    constraint foreign key(id_user) references users(id),
    constraint foreign key(id_skill) references skills(id)
);
create table days_off(
    id int primary key not null auto_increment,
    id_days_off_type int,
    id_user int,
    id_status int,
    day_start_off datetime,
    number_of_day_off float,
    create_at date,
    description text,
    constraint foreign key (id_days_off_type) references days_off_type(id),
    constraint foreign key (id_user) references users(id),
    constraint foreign key (id_status) references status(id)
);
create table problems(
    id int not null primary key auto_increment,
    id_problem int,
    id_user int,
    id_status int,
    day_request date,
    create_at date,
    description text,
    constraint foreign key (id_problem) references problems_type(id),
    constraint foreign key (id_user) references users(id),
    constraint foreign key (id_status) references status(id)
);
