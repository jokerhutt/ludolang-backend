create table course
(
    id        int          not null
        primary key,
    image_src varchar(255) null,
    title     varchar(255) null
);


create table monthly_challenge_definition
(
    id            int auto_increment
        primary key,
    code          varchar(255)         null,
    target        int                  not null,
    reward_points int                  not null,
    active        tinyint(1) default 1 not null,
    constraint code
        unique (code)
);


create table quest_definition
(
    id            int auto_increment
        primary key,
    code          varchar(255)         null,
    target        int                  not null,
    reward_points int                  not null,
    active        tinyint(1) default 1 not null,
    constraint code
        unique (code)
);


create table users
(
    id                int auto_increment
        primary key,
    username          varchar(255)                           null,
    first_name        varchar(255)                           null,
    last_name         varchar(255)                           null,
    pfp_source        varchar(255)                           null,
    points            int          default 0                 not null,
    created_at        timestamp    default CURRENT_TIMESTAMP not null,
    last_submission   timestamp                              null,
    streak_length     int          default 0                 not null,
    current_course_id int                                    null,
    email             varchar(255) default 'test@gmail.com'  not null,
    constraint id_UNIQUE
        unique (id),
    constraint username_UNIQUE
        unique (username),
    constraint fk_users_current_course
        foreign key (current_course_id) references course (id)
            on delete set null
);

create index ix_users_points_desc
    on users (points desc, id asc);


create table sections
(
    id          int auto_increment
        primary key,
    course_id   int          not null,
    order_index int          not null,
    title       varchar(255) null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_sections_course
        foreign key (course_id) references course (id)
            on delete cascade
);

create table ludoUnits
(
    id             int auto_increment
        primary key,
    title          varchar(255) null,
    description    varchar(255) null,
    section_id     int          not null,
    order_index    int          not null,
    course_id      int          not null,
    created_at     datetime(6)  null,
    animation_path varchar(255) null,
    color          varchar(255) null,
    constraint uq_course_order
        unique (course_id, order_index),
    constraint fk_units_course
        foreign key (course_id) references course (id)
            on delete cascade
);

create table lessons
(
    id          int auto_increment
        primary key,
    title       varchar(255)                 null,
    unit_id     int                          not null,
    order_index int                          not null,
    type        varchar(20) default 'Lesson' not null,
    lesson_type varchar(255)                 null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_lessons_unit
        foreign key (unit_id) references ludoUnits (id)
            on delete cascade
);

create table exercises
(
    id          int auto_increment
        primary key,
    lesson_id   int          not null,
    type        varchar(255) null,
    prompt      varchar(255) null,
    order_index int          not null,
    constraint id
        unique (id),
    constraint exercises_ibfk_1
        foreign key (lesson_id) references lessons (id)
            on delete cascade
);

create index lesson_id
    on exercises (lesson_id);

create table exercise_options
(
    id           int auto_increment
        primary key,
    content      varchar(255) null,
    exercise_id  int          not null,
    image_url    varchar(255) null,
    is_correct   tinyint(1)   not null,
    answer_order int          null
);


create table exercise_attempts
(
    id           int auto_increment
        primary key,
    exercise_id  int                                       not null,
    option_id    int                                       not null,
    score        int                                       null,
    submitted_at timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    user_id      int                                       not null,
    is_checked   tinyint(1)   default 0                    not null
);

create table exercise_attempt_option
(
    id         int auto_increment
        primary key,
    attempt_id int not null,
    option_id  int not null,
    position   int not null,
    constraint exercise_attempt_option_ibfk_1
        foreign key (attempt_id) references exercise_attempts (id)
            on delete cascade,
    constraint exercise_attempt_option_ibfk_2
        foreign key (option_id) references exercise_options (id)
);

create index attempt_id
    on exercise_attempt_option (attempt_id);

create index option_id
    on exercise_attempt_option (option_id);


create table lesson_completions
(
    user_id      int                                 not null,
    course_id    int                                 not null,
    lesson_id    int                                 not null,
    score        int                                 not null,
    completed_at timestamp default CURRENT_TIMESTAMP not null,
    primary key (user_id, lesson_id),
    constraint lesson_completions_ibfk_1
        foreign key (user_id) references users (id),
    constraint lesson_completions_ibfk_2
        foreign key (lesson_id) references lessons (id)
);

create index lesson_id
    on lesson_completions (lesson_id);


create table user_course_progress
(
    id                int auto_increment
        primary key,
    user_id           int                                  not null,
    course_id         int                                  not null,
    current_lesson_id int                                  not null,
    updated_at        timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_complete       tinyint(1) default 0                 not null,
    constraint user_course_progress_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint user_course_progress_ibfk_2
        foreign key (course_id) references course (id)
            on delete cascade,
    constraint user_course_progress_ibfk_3
        foreign key (current_lesson_id) references lessons (id)
);

create index current_lesson_id
    on user_course_progress (current_lesson_id);

create index user_id
    on user_course_progress (user_id);


create table follows
(
    id          int auto_increment
        primary key,
    follower_id int                                 not null,
    followed_id int                                 not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    constraint uq_follows
        unique (follower_id, followed_id),
    constraint fk_follows_followed
        foreign key (followed_id) references users (id)
            on delete cascade,
    constraint fk_follows_follower
        foreign key (follower_id) references users (id)
            on delete cascade,
    constraint chk_no_self_follow
        check (`follower_id` <> `followed_id`)
);

create index idx_follows_followed
    on follows (followed_id);

create index idx_follows_follower
    on follows (follower_id);




create table path_icons
(
    unit_id int          not null
        primary key,
    icon    varchar(255) not null,
    constraint fk_unit
        foreign key (unit_id) references ludoUnits (id)
            on delete cascade
);

create table user_daily_quest
(
    user_id        int                  not null,
    quest_def_id   int                  not null,
    date           date                 not null,
    progress       int        default 0 not null,
    completed_at   timestamp            null,
    reward_claimed tinyint(1) default 0 not null,
    primary key (user_id, quest_def_id, date),
    constraint user_daily_quest_ibfk_1
        foreign key (quest_def_id) references quest_definition (id),
    constraint user_daily_quest_ibfk_2
        foreign key (user_id) references users (id)
);

create index quest_def_id
    on user_daily_quest (quest_def_id);

CREATE TABLE `user_monthly_challenge` (
                                          `user_id` int NOT NULL,
                                          `challenge_def_id` int NOT NULL,
                                          `year` int NOT NULL,
                                          `month` int NOT NULL,
                                          `progress` int NOT NULL DEFAULT '0',
                                          `completed_at` timestamp NULL DEFAULT NULL,
                                          `reward_claimed` tinyint(1) NOT NULL DEFAULT '0',
                                          PRIMARY KEY (`user_id`,`challenge_def_id`,`year`,`month`),
                                          KEY `challenge_def_id` (`challenge_def_id`),
                                          CONSTRAINT `user_monthly_challenge_ibfk_1` FOREIGN KEY (`challenge_def_id`) REFERENCES `monthly_challenge_definition` (`id`),
                                          CONSTRAINT `user_monthly_challenge_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO duo_db.course (id, image_src, title) VALUES (1, 'https://d35aaqx5ub95lt.cloudfront.net/images/borderlessFlags/7488bd7cd28b768ec2469847a5bc831e.svg', 'FRENCH');
INSERT INTO duo_db.course (id, image_src, title) VALUES (2, 'https://d35aaqx5ub95lt.cloudfront.net/images/borderlessFlags/097f1c20a4f421aa606367cd33893083.svg', 'GERMAN');
INSERT INTO duo_db.course (id, image_src, title) VALUES (3, 'https://d35aaqx5ub95lt.cloudfront.net/images/borderlessFlags/40a9ce3dfafe484bced34cdc124a59e4.svg', 'SPANISH');
