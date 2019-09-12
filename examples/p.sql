/multiline on
/abolish
/output off
CREATE TABLE t1(a INTEGER, b INTEGER, c INTEGER, d INTEGER,  PRIMARY KEY(a));

CREATE TABLE t2(a INTEGER, b INTEGER, c INTEGER, d INTEGER,  PRIMARY KEY(a), FOREIGN KEY(b) REFERENCES t1(a));

CREATE TABLE t3(a INTEGER, b INTEGER, c INTEGER, d INTEGER,  PRIMARY KEY(a), FOREIGN KEY(b) REFERENCES t2(a));

CREATE TABLE t4(a INTEGER, b INTEGER, c INTEGER, d INTEGER,  PRIMARY KEY(a), FOREIGN KEY(b) REFERENCES t3(a));

INSERT INTO t1(a,b,c,d) VALUES (1,10,2,8);

INSERT INTO t1(a,b,c,d) VALUES (2,9,4,6);

INSERT INTO t1(a,b,c,d) VALUES (3,8,7,8);

INSERT INTO t1(a,b,c,d) VALUES (4,7,3,8);

INSERT INTO t1(a,b,c,d) VALUES (5,6,8,4);

INSERT INTO t1(a,b,c,d) VALUES (6,5,8,8);

INSERT INTO t1(a,b,c,d) VALUES (7,4,1,7);

INSERT INTO t1(a,b,c,d) VALUES (8,3,2,9);

INSERT INTO t1(a,b,c,d) VALUES (9,2,5,1);

INSERT INTO t1(a,b,c,d) VALUES (10,1,0,3);

INSERT INTO t2(a,b,c,d) VALUES (1,10,7,0);

INSERT INTO t2(a,b,c,d) VALUES (2,9,9,0);

INSERT INTO t2(a,b,c,d) VALUES (3,8,4,8);

INSERT INTO t2(a,b,c,d) VALUES (4,7,1,2);

INSERT INTO t2(a,b,c,d) VALUES (5,6,2,8);

INSERT INTO t2(a,b,c,d) VALUES (6,5,4,2);

INSERT INTO t2(a,b,c,d) VALUES (7,4,5,6);

INSERT INTO t2(a,b,c,d) VALUES (8,3,4,8);

INSERT INTO t2(a,b,c,d) VALUES (9,2,6,4);

INSERT INTO t2(a,b,c,d) VALUES (10,1,3,7);

INSERT INTO t3(a,b,c,d) VALUES (1,10,8,2);

INSERT INTO t3(a,b,c,d) VALUES (2,9,3,5);

INSERT INTO t3(a,b,c,d) VALUES (3,8,4,0);

INSERT INTO t3(a,b,c,d) VALUES (4,7,3,0);

INSERT INTO t3(a,b,c,d) VALUES (5,6,8,3);

INSERT INTO t3(a,b,c,d) VALUES (6,5,4,5);

INSERT INTO t3(a,b,c,d) VALUES (7,4,6,2);

INSERT INTO t3(a,b,c,d) VALUES (8,3,1,5);

INSERT INTO t3(a,b,c,d) VALUES (9,2,1,4);

INSERT INTO t3(a,b,c,d) VALUES (10,1,8,3);

INSERT INTO t4(a,b,c,d) VALUES (1,10,8,0);

INSERT INTO t4(a,b,c,d) VALUES (2,9,6,2);

INSERT INTO t4(a,b,c,d) VALUES (3,8,7,7);

INSERT INTO t4(a,b,c,d) VALUES (4,7,1,6);

INSERT INTO t4(a,b,c,d) VALUES (5,6,8,3);

INSERT INTO t4(a,b,c,d) VALUES (6,5,0,9);

INSERT INTO t4(a,b,c,d) VALUES (7,4,8,1);

INSERT INTO t4(a,b,c,d) VALUES (8,3,1,0);

INSERT INTO t4(a,b,c,d) VALUES (9,2,5,5);

INSERT INTO t4(a,b,c,d) VALUES (10,1,4,6);

CREATE VIEW v11(a,b,c,d) AS SELECT ALL t3.a,t3.d,t1.b,t1.d FROM t1, t3 WHERE (t3.a = t1.b AND (t3.d >= 4 OR t1.d >= 8));

CREATE VIEW v12(a,b,c,d) AS ( SELECT ALL t1.a,t1.c,t1.d,t1.d FROM t1 WHERE t1.d > 3 ) UNION ( SELECT DISTINCT t4.b,t4.a,t4.d,t4.a FROM t4 WHERE t4.a = 2 );

CREATE VIEW v13(a,b,c,d) AS ( SELECT ALL t2.a,t2.c,t2.b,t2.a FROM t2 WHERE t2.b < 9 ) UNION ALL ( SELECT ALL t1.b,t1.b,t1.d,t1.c FROM t1 WHERE t1.c >= 1 );

CREATE VIEW v14(a,b,c,d) AS ( SELECT DISTINCT t1.a,t1.c,t1.c,t1.a FROM t1 WHERE t1.c <= 5 ) EXCEPT ( SELECT DISTINCT t1.c,t1.c,t1.d,t1.b FROM t1 WHERE t1.d = 1 );

CREATE VIEW v15(a,b,c,d) AS SELECT ALL t3.b,t3.b,t3.d,t3.b FROM t3, t3;

CREATE VIEW v16(a,b,c,d) AS ( SELECT DISTINCT t4.a,t4.b,t4.b,t4.d FROM t4 WHERE t4.c < 2 ) UNION ALL ( SELECT ALL t4.a,t4.d,t4.d,t4.c FROM t4 WHERE t4.a < 7 );

CREATE VIEW v17(a,b,c,d) AS ( SELECT DISTINCT t3.a,t3.a,t3.a,t3.b FROM t3 ) UNION ALL ( SELECT ALL t3.b,t3.b,t3.a,t3.b FROM t3 WHERE t3.c <= 6 );

CREATE VIEW v18(a,b,c,d) AS ( SELECT DISTINCT t1.c,t1.d,t1.c,t1.b FROM t1 WHERE t1.b < 2 ) EXCEPT ( SELECT ALL t2.c,t2.c,t2.d,t2.d FROM t2 WHERE t2.a > 7 );

CREATE VIEW v19(a,b,c,d) AS SELECT DISTINCT t4.b,t4.d,t4.a,t4.c FROM t4 WHERE t4.c >= 7;

CREATE VIEW v20(a,b,c,d) AS ( SELECT ALL t3.d,t3.b,t3.c,t3.b FROM t3 WHERE t3.b >= 9 ) EXCEPT ( SELECT DISTINCT t4.b,t4.b,t4.d,t4.d FROM t4 WHERE t4.c < 6 );

CREATE VIEW v10(a,b,c,d) AS ( SELECT ALL v15.c,v15.d,v15.c,v15.c FROM v15 WHERE v15.b = 1 ) UNION ( SELECT DISTINCT t4.a,t4.a,t4.b,t4.b FROM t4 WHERE t4.b <= 0 );

CREATE VIEW v5(a,b,c,d) AS ( SELECT ALL v15.a,v15.c,v15.a,v15.d FROM v15 WHERE v15.b = 4 ) UNION ( SELECT ALL v15.b,v15.a,v15.a,v15.b FROM v15 WHERE v15.d > 3 );

CREATE VIEW v6(a,b,c,d) AS ( SELECT ALL t4.a,t4.a,t4.a,t4.c FROM t4 WHERE t4.c = 3 ) UNION ( SELECT ALL v13.c,v13.c,v13.c,v13.a FROM v13 WHERE v13.c >= 6 );

CREATE VIEW v7(a,b,c,d) AS SELECT ALL v16.d,v16.a,v19.a,v19.b FROM v16, v17, v19;

CREATE VIEW v8(a,b,c,d) AS ( SELECT DISTINCT t1.a,t1.c,t1.a,t1.d FROM t1 WHERE t1.c > 4 ) UNION ALL ( SELECT ALL v18.a,v18.a,v18.b,v18.b FROM v18 WHERE v18.c > 5 );

CREATE VIEW v9(a,b,c,d) AS SELECT ALL v14.d,v20.d,v20.c,v14.b FROM v14, v20 WHERE (v20.a = v14.b AND (v20.b >= 4 OR v14.c <= 9));

CREATE VIEW v2(a,b,c,d) AS ( SELECT DISTINCT v8.a,v8.a,v8.d,v8.d FROM v8 WHERE v8.c = 6 ) UNION ALL ( SELECT DISTINCT v7.d,v7.d,v7.a,v7.a FROM v7 WHERE v7.a < 9 );

CREATE VIEW v3(a,b,c,d) AS SELECT DISTINCT v9.b,v9.a,v12.d,v12.b FROM v12, v9;

CREATE VIEW v4(a,b,c,d) AS ( SELECT ALL v11.a,v10.a,v11.a,v11.c FROM v10, v11 WHERE (v11.a = v10.b AND v11.c > 4) ) UNION ( SELECT DISTINCT v5.a,v5.c,v5.b,v5.d FROM v5 WHERE v5.d >= 8 );

CREATE VIEW v1(a,b,c,d) AS ( SELECT DISTINCT v4.a,v3.a,v4.b,v4.d FROM v3, v4 WHERE (v4.a = v3.b AND (v4.a >= 6 OR v3.a <= 6)) ) UNION ALL ( SELECT ALL v2.c,v2.c,v2.c,v2.b FROM v2 WHERE v2.a >= 3 );

/output on
