create table users_sso (
    id bigserial primary key,
    user_id bigint,
    acc_id varchar(255),
    provider varchar(255),
    foreign key (user_id) references users(id) on delete cascade
);