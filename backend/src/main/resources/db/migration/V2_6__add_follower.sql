create table "user_followers" (
    id bigserial primary key,
    follower_id bigint,
    following_id bigint,
    foreign key (follower_id) references users(id),
    foreign key (following_id) references users(id),
    unique (follower_id, following_id)
)