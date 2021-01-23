/*
 * Turbal Denis
 * https://github.com/EnterCapchaCode
 */

create table role(
    id SERIAL PRIMARY KEY,
    role_name character varying(20)
);

create table employee(
    id SERIAL PRIMARY KEY,
    name character varying(20),
    surname character varying(20),
    login character varying(30),
    role_id int not null
);

create table post(
    id SERIAL PRIMARY KEY,
    title character varying(30),
    description character varying(100),
    text character varying(2000),
    creation_date date,
    modification_date date,
    negative_rating bigint,
    positive_rating bigint,
    employee_id int not null,
    tag_id int
);

create table comment(
    id SERIAL PRIMARY KEY,
    comment_text character varying(200),
    employee_id int not null,
    post_id int not null
);

create table tag(
    id SERIAL PRIMARY KEY,
    name character varying(20)
);

alter table employee add constraint employee_role_fk foreign key (role_id) references role (id);

alter table post add constraint  post_employee_fk foreign key (employee_id) references employee (id);
alter table post add constraint  post_tag_fk foreign key (tag_id) references tag (id);

alter table comment add constraint comment_employee_fk foreign key (employee_id) references employee (id);
alter table comment add constraint comment_post_fk foreign key (post_id) references post (id);
