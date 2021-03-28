alter table post
drop column description;

alter table post
alter column title type character varying(60);