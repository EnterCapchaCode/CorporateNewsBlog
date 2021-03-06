/*
 * Turbal Denis
 * https://github.com/EnterCapchaCode
 */

insert into role (role_name) values ('admin');
insert into role (role_name) values ('creator');
insert into role (role_name) values ('reader');

insert into employee (name, surname, login, role_id) values ('Denis', 'Turbal', 'dturball@gamail.com', 1);
insert into employee (name, surname, login, role_id) values ('Vlad', 'Martinkov', 'vmartinkov@gamail.com', 2);
insert into employee (name, surname, login, role_id) values ('Uladzimir', 'Redzko', 'uredzko@gamail.com', 3);

insert into tag (id, name) values (1, 'programming');
insert into tag (id, name) values (2, 'officenews');

insert into post (title, description, text, creation_date, negative_rating, positive_rating, employee_id, tag_id)
    values ('test', 'test test', 'test test test', current_date, 3, 15, 2, 1);

insert into comment (comment_text, employee_id, post_id) values ('test', 3, 1);
insert into comment (comment_text, employee_id, post_id) values ('test', 2, 1);
