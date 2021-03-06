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
