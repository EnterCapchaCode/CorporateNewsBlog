/*
 * Turbal Denis
 * https://github.com/EnterCapchaCode
 */

create table likes(
    post_id int not null,
    employee_id int not null
);

create table dislikes(
    post_id int not null,
    employee_id int not null
);

alter table likes add constraint post_likes_fk foreign key (post_id) references post (id);
alter table likes add constraint employee_likes_fk foreign key (employee_id) references employee (id);

alter table dislikes add constraint post_dislikes_fk foreign key (post_id) references post (id);
alter table dislikes add constraint employee_dislikes_fk foreign key (employee_id) references employee (id);

alter table dislikes add column version bigint default 0;
alter table dislikes add column created timestamp not null default now();
alter table dislikes add column modified timestamp;

alter table likes add column version bigint default 0;
alter table likes add column created timestamp not null default now();
alter table likes add column modified timestamp;