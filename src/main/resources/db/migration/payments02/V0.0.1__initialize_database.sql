create table if not exists payments (
    payment_id serial primary key,
    account_from varchar(50) not null,
    account_to varchar(50) not null,
    amount decimal(12, 2) not null
);