create table users
(
    id       int primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table cards
(
    id          int primary key,
    card_number varchar(16) not null unique,
    bank_name   varchar(50) not null,
    expire_date varchar(4)  not null,
    cvv2        varchar(4)  not null,
    balance     double precision,
    user_id     int references users (id)
);

create table transactions
(
    id                      int primary key,
    source_card_number      varchar(16)    not null references cards (card_number),
    destination_card_number varchar(16)    not null,
    amount                  decimal(10, 2) not null,
    fee         decimal(10, 2),
    timestamp   timestamp      not null,
    type        varchar(50)    not null,
    status      varchar(50)    not null
);