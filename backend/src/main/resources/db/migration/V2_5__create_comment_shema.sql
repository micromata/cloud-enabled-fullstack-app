create table "post_comments" (
    id bigserial primary key,
    user_id bigint,
    post_id bigint,
    content text,
    published_at timestamp,
    foreign key (user_id) references users(id),
    foreign key (post_id) references posts(id)
)