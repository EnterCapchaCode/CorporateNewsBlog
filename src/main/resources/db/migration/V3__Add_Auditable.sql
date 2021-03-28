/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

alter table comment add column version bigint default 0;
alter table comment add column created timestamp not null default now();
alter table comment add column modified timestamp;

alter table employee add column version bigint default 0;
alter table employee add column created timestamp not null default now();
alter table employee add column modified timestamp;

alter table post add column version bigint default 0;
alter table post add column created timestamp not null default now();
alter table post add column modified timestamp;

alter table role add column version bigint default 0;
alter table role add column created timestamp not null default now();
alter table role add column modified timestamp;

alter table tag add column version bigint default 0;
alter table tag add column created timestamp not null default now();
alter table tag add column modified timestamp;
