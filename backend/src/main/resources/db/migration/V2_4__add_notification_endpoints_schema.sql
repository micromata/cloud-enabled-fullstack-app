create table user_notification_endpoints (
    id bigserial primary key,
    user_id bigint unique,
    endpoint text,
    key text,
    auth text,
    foreign key (user_id) references users(id)
)