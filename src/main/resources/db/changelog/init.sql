create table countries
(
    id bigserial not null
        constraint countries_pkey
            primary key,
    code varchar(255)
        constraint uk_992aa0625w4xw3qpu169m22u2
            unique,
    name varchar(255)
        constraint uk_1pyiwrqimi3hnl3vtgsypj5r
            unique
);

alter table countries owner to postgres;

create table airlines
(
    id bigserial not null
        constraint airlines_pkey
            primary key,
    call_sign varchar(255)
        constraint uk_15lv2puv7wcatupg9qwiw3h7g
            unique,
    code varchar(255),
    name varchar(255),
    country_name varchar(255)
        constraint fkrl8nevcnsi5vx3rm0vt749ct4
            references countries (name)
);

alter table airlines owner to postgres;

create table cities
(
    id bigserial not null
        constraint cities_pkey
            primary key,
    code varchar(255)
        constraint uk_43h9bt0mhgqb2eqqtttsccwl3
            unique,
    name varchar(255),
    country_code varchar(255)
        constraint fkkn4qtp6nk6w1elw18hc9bwfqk
            references countries (code)
);

alter table cities owner to postgres;

create table airports
(
    id bigserial not null
        constraint airports_pkey
            primary key,
    code varchar(255)
        constraint uk_nkoleinn8d0j08de5edmyeo5l
            unique,
    name varchar(255),
    city_code varchar(255)
        constraint fktnlfeq37m01het88ymku2mq2h
            references cities (code)
);

alter table airports owner to postgres;

create table currencies
(
    id bigserial not null
        constraint currencies_pkey
            primary key,
    code varchar(255)
        constraint uk_k1e8reg2ps27ttnx92r0bo1ky
            unique,
    name varchar(255)
);

alter table currencies owner to postgres;

create table providers
(
    id bigserial not null
        constraint providers_pkey
            primary key,
    name varchar(255)
        constraint uk_fuh4835foq2trqy6ur286u3s0
            unique
);

alter table providers owner to postgres;

create table search_criteria
(
    id bigserial not null
        constraint search_criteria_pkey
            primary key,
    depart_date bigint,
    return_date bigint,
    adults integer,
    cabin_class integer,
    children integer,
    ticket_type integer,
    currency_code bigint
        constraint fkihbtbtagpsorw7vmyfltoh2ht
            references currencies,
    from_airport_code bigint
        constraint fkdfopl3qxmp6kfqihghftltd9f
            references airports,
    from_city_name bigint
        constraint fkhy9asfpbot5vgf73ho70m4jl
            references cities,
    from_country_name bigint
        constraint fks7km5vpnmd5doxcmqoa2yfpkh
            references countries,
    to_airport_code bigint
        constraint fk5iduimg5290ybr7fidhoahy6s
            references airports,
    to_city_name bigint
        constraint fk9ytgp4ep8v6qa7s8fvwk847f3
            references cities,
    to_country_name bigint
        constraint fksmc17vsxldbqtaofxhdcq2e41
            references countries
);

alter table search_criteria owner to postgres;

create table tickets
(
    id bigserial not null
        constraint tickets_pkey
            primary key,
    booking_token varchar(255),
    price double precision,
    provider_name varchar(255)
        constraint fke8xnv0mxuubsqllku1vucj83h
            references providers (name)
);

alter table tickets owner to postgres;

create table routes
(
    id bigserial not null
        constraint routes_pkey
            primary key,
    duration bigint,
    from_city_code varchar(255)
        constraint fka35h1slcgwmjc2hwkcau2nh9m
            references cities (code),
    ticket_id bigint
        constraint fkrm7opw4a91158bhai5rmljhtb
            references tickets,
    to_city_code varchar(255)
        constraint fk1gn63aoot5vj7sua141r31kig
            references cities (code)
);

alter table routes owner to postgres;

create table flights
(
    id bigserial not null
        constraint flights_pkey
            primary key,
    arrival_time bigint,
    cabin_class integer,
    depart_time bigint,
    duration bigint,
    flight_number varchar(255),
    airline_name varchar(255)
        constraint fk5uu2nsggkft5x7tnbq5lvyc7a
            references airlines (call_sign),
    arrival_airport_code varchar(255)
        constraint fk4ohqpxpkw2strmju9gdy16515
            references airports (code),
    arrival_city_code varchar(255)
        constraint fkkwpr888auomgxycvu25vm1tjq
            references cities (code),
    depart_airport_code varchar(255)
        constraint fk2l9nus27g0x2jvvivqwrhvlgi
            references airports (code),
    depart_city_code varchar(255)
        constraint fkleq3jt6pcng6hjpt96xnojrx0
            references cities (code),
    route_id bigint
        constraint fkggm6k4h1glfes1nsg0wesanvy
            references routes
);

alter table flights owner to postgres;

create table users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    encoded_password varchar(255),
    name varchar(255)
        constraint uk_3g1j96g94xpk3lpxl2qbl985x
            unique,
    role varchar(255)
);

alter table users owner to postgres;

