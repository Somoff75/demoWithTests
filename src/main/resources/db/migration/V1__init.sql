create table public.users
(
    id          serial
        primary key,
    country     varchar(255),
    email       varchar(255),
    is_deleted  boolean,
    name        varchar(255),
    passport_id integer
        references passports,
    gender      varchar
);
create table public.passports
(
    id            serial
        primary key,
    birth_date    date,
    first_name    varchar(255),
    is_free       boolean,
    second_name   varchar(255),
    serial_number uuid
);