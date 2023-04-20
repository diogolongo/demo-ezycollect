
create table contact
(
    id bigint auto_increment,
    first_name         varchar(100) not null,
    last_name         varchar(100) not null,
    phone_number varchar(20)  not null,
    constraint primary_key
        primary key (id)
);

