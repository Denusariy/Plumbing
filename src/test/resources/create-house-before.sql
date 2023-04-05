insert into plumbing_db_test.public.plumber(id, name) values (1, 'Mario');
insert into plumbing_db_test.public.plumber(id, name) values (2, 'Luigi');
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (1, 'Privet Drive, 4', null);
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (2, 'Diagon Alley, 18a', 1);
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (3, '3rd Stroiteley st., 25, 12, Moscow', 1);
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (4, '3rd Stroiteley st., 25, 12, St.Petersburg', 1);
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (5, 'P.Sherman, 42 Wallaby Way, Sydney', 1);
insert into plumbing_db_test.public.house(id, address, plumber_id)
    values (6, 'Rubinstein st., 13, St.Petersburg', 1);

alter sequence plumbing_db_test.public.house_id_seq restart with 10;

