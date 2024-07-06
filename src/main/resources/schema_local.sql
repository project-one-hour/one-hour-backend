drop table if exists auth_provider;
drop table if exists member_interest;
drop table if exists bungae;
drop table if exists credit;
drop table if exists member;

create table member
(
    member_id                bigint                             not null auto_increment,
    nickname                 varchar(10),
    gender                   enum ('MALE','FEMALE'),
    birthday                 date,
    mbti                     enum ('ISTJ','ISTP','ISFJ','ISFP','INTJ','INTP','INFJ','INFP','ESTJ','ESTP','ESFJ','ESFP','ENTJ','ENTP','ENFJ','ENFP'),
    sign_up_status           enum ('AUTHENTICATED','SIGNED_UP') not null,
    authority                enum ('ADMIN','GUEST','MEMBER')    not null,
    marketing_info_status    enum ('ALLOW','NOT_ALLOW'),
    first_profile_image_url  varchar(255),
    second_profile_image_url varchar(255),
    third_profile_image_url  varchar(255),
    created_at               datetime(6),
    created_by               bigint,
    updated_at               datetime(6),
    updated_by               bigint,
    deleted_at               datetime(6),
    primary key (member_id)
) engine = InnoDB;

create table auth_provider
(
    auth_provider_id bigint                 not null auto_increment,
    email            varchar(255),
    provider_id      varchar(255)           not null,
    provider         enum ('KAKAO','APPLE') not null,
    member_id        bigint,
    created_at       datetime(6),
    created_by       bigint,
    updated_at       datetime(6),
    updated_by       bigint,
    deleted_at       datetime(6),
    primary key (auth_provider_id)
) engine = InnoDB;

create table member_interest
(
    member_interest_id bigint                                                                                                                                                                                                                                                                               not null auto_increment,
    member_id          bigint,
    interest           enum ('FOOD_TOUR','COFFEE','DESSERT','WINE_BAR','TRIP','DRIVE','TAKE_WALK','PET','STUDYING','READING','TENNIS','RUNNING','GOLF','HEALTH','FUTSAL','CYCLE','YOGA','SPORT_CLIMBING','WATCHING_SPORTS','POPUP_STORE','EXHIBITION','CONCERT','MUSEUM','MOVIE','MUSICAL','PICNIC','GAME') not null,
    created_at         datetime(6),
    created_by         bigint,
    updated_at         datetime(6),
    updated_by         bigint,
    deleted_at         datetime(6),
    primary key (member_interest_id)
) engine = InnoDB;

create table bungae
(
    bungae_id           bigint                                                                                                                                                             not null auto_increment,
    bungae_type         enum ('MEET','CHAT')                                                                                                                                               not null,
    gender_filter_type  enum ('NONE','FEMALE','MALE')                                                                                                                                      not null,
    start_age           integer                                                                                                                                                            not null,
    end_age             integer                                                                                                                                                            not null,
    level_lower_bound   enum ('LEVEL0','LEVEL1','LEVEL2','LEVEL3')                                                                                                                         not null,
    location            point                                                                                                                                                              not null,
    location_name       text,
    category            enum ('FOOD_TOUR','CAFE_TOUR','SIMPLE_DRINK','TRIP','TAKE_WALK','PET','CAFE_STUDY','SPORTS','CULTURAL_LIFE','GAME','SIMPLE_TALK','HOBBY_SHARING','ADVICE_TALKING') not null,
    max_participants    integer                                                                                                                                                            not null,
    start_delay_minutes integer                                                                                                                                                            not null,
    title               varchar(40)                                                                                                                                                        not null,
    description         text,
    cover_image_url     varchar(255),
    host_id             bigint                                                                                                                                                             not null,

    primary key (bungae_id)
) engine = InnoDB;

create table credit
(
    credit_id    bigint not null auto_increment,
    credit_count integer,
    member_id    bigint not null,
    created_at   datetime(6),
    created_by   bigint,
    updated_at   datetime(6),
    updated_by   bigint,
    deleted_at   datetime(6),
    primary key (credit_id)
) engine = InnoDB;

alter table auth_provider
    add constraint uk_provider_id unique (provider_id);

alter table member
    add constraint uk_nickname unique (nickname);

alter table auth_provider
    add constraint fk_auth_provider_from_member_member_id
        foreign key (member_id)
            references member (member_id);

alter table bungae
    add constraint fk_bungae_from_member_member_id
        foreign key (host_id)
            references member (member_id);

alter table credit
    add constraint fk_credit_from_member_member_id
        foreign key (member_id)
            references member (member_id);

alter table member_interest
    add constraint fk_member_interest_from_member_member_id
        foreign key (member_id)
            references member (member_id);
