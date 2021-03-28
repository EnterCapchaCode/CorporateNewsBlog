/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

alter table employee add column google_id character varying;
alter table employee add column role int;
alter table employee drop constraint employee_role_fk;
alter table employee drop column role_id;

drop table role;