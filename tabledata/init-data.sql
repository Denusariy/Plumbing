drop table if exists house;
drop table if exists plumber;

create table plumber
(
    id           serial
        primary key,
    name         varchar(255),
    bank_account bigint
);

alter table plumber
    owner to postgres;

INSERT INTO public.plumber (id, name, bank_account) VALUES (4, 'Alex', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (5, 'Pavel', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (6, 'Alisher', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (3, 'Алишер', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (2, 'Игорь', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (7, 'Tony Kark', 0);
INSERT INTO public.plumber (id, name, bank_account) VALUES (1, 'Петя', 0);

create table if not exists house
(
    id         serial
        primary key,
    address    varchar(255),
    plumber_id integer
        constraint fk2s7cpaubcanlcd3hvhxpndjep
            references plumber,
    budget     bigint
);

alter table house
    owner to postgres;

INSERT INTO public.house (id, address, plumber_id, budget) VALUES (6, 'UAE, Dubai, Burdj Khalifa', 2, 1000000000);
INSERT INTO public.house (id, address, plumber_id, budget) VALUES (2, 'USA, NY, Wall Street 13', 2, 1000000000);
INSERT INTO public.house (id, address, plumber_id, budget) VALUES (3, 'Moscow, Krasnopresnenskaya 12/1', 3, 1000000000);
INSERT INTO public.house (id, address, plumber_id, budget) VALUES (4, 'Uzbekistan, Tashkent, Fidokor 30', 3, 1000000000);
INSERT INTO public.house (id, address, plumber_id, budget) VALUES (5, 'England, London, Baker Street 31', 3, 1000000000);
INSERT INTO public.house (id, address, plumber_id, budget) VALUES (1, 'Москва, ул.Ленина 81', 1, 996478700);
