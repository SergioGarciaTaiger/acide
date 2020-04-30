/abolish
create view v5(a) as select 0;
create view v4(a) as select 0;
create view v3(a) as select * from v4 natural join v5;
create view v2(a) as select 0;
create view v1(a) as select * from v2 natural join v3;
