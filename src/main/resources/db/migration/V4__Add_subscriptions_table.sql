create table user_subscriptions (
    channel_id int8 not null references app_user,
    subscriber_id int8 not null references app_user,
    primary key (channel_id, subscriber_id)
);