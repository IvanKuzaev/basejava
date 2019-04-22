create table resume
(
  uuid char(36) not null
    constraint resume_pkey
    primary key,
  full_name text not null
)
;

alter table resume owner to postgres
;

create table contact
(
  id serial not null
    constraint contact_pkey
    primary key,
  resume_uuid char(36) not null
    constraint contact_resume_uuid_fk
    references resume (uuid)
    on delete cascade,
  type text not null,
  value text not null
)
;

alter table contact owner to postgres
;

create unique index contact_uuid_type_index
  on contact (resume_uuid, type)
;

create table section
(
  id serial not null
    constraint section_pkey
    primary key,
  resume_uuid char(36) not null
    constraint section_resume_uuid_fk
    references resume (uuid)
    on delete cascade,
  type text not null,
  value text not null
)
;

alter table section owner to postgres
;

create unique index section_uuid_type_index
  on section (resume_uuid, type)
;

create table organization
(
  id serial not null
    constraint organization_pkey
    primary key,
  title text not null,
  description text,
  weblink text
)
;

create unique index organization_title_index
  on organization (title)
;

alter table organization owner to postgres
;

create table biography
(
  id serial not null
    constraint biography_pkey
    primary key,
  resume_uuid char(36) not null
    constraint biography_resume_uuid_fk
    references resume (uuid)
    on delete cascade,
  type text not null,
  organization_id serial not null
    constraint biography_organization_id_fk
    references organization (id)
    on delete cascade,
  start_date date not null,
  end_date date not null,
  title text not null,
  description text
)
;

alter table biography owner to postgres
;

--create unique index biography_uuid_type_index
--  on biography (resume_uuid, type)
--;

