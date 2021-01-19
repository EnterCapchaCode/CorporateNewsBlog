/*
 * Turbal Denis
 * https://github.com/EnterCapchaCode
 */

insert into role (role_name) values ('admin');
insert into role (role_name) values ('creator');
insert into role (role_name) values ('reader');

insert into employee (name, surname, login, role_id) values ('Denis', 'Turbal', 'dturball@gamail.com', 1);
insert into employee (name, surname, login, role_id) values ('Pavel', 'Kravchenko', 'pavelk@gamail.com', 2);
insert into employee (name, surname, login, role_id) values ('Uladzimir', 'Redzko', 'uredzko@gamail.com', 3);


insert into post (title, description, text, creation_date, negative_rating, positive_rating, employee_id)
    values ('test', 'test test', 'test test test', current_date, 3, 15, 2);

insert into comment (comment_text, employee_id, post_id) values ('test', 3, 1);
insert into comment (comment_text, employee_id, post_id) values ('test', 2, 1);

insert into tag (name) values ('programming');
insert into tag (name) values ('officenews');

insert into post_tag (post_id, tag_id) values (1, 1);
insert into post_tag (post_id, tag_id) values (1, 2);
