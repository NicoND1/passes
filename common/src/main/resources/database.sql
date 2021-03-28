create table passes_users
(
    uuid               varchar(36)   not null,
    pass_id            int           not null,
    level              int           null,
    exp                double        null,
    collectable_levels varchar(4369) null,
    primary key (uuid, pass_id)
);
