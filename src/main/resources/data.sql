INSERT INTO public.department (id, description, wording)
VALUES (1, 'Accounting', 'Accounting');
INSERT INTO public.department (id, description, wording)
VALUES (2, 'Administration', 'Administration');
INSERT INTO public.department (id, description, wording)
VALUES (3, 'General', 'General');
INSERT INTO public.department (id, description, wording)
VALUES (4, 'Technical Support', 'Technical Support');

--
--        ---- USER: Lostrif
--INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
--                                          marital_status, position)
--VALUES ('1986-03-09 02:00:00.000000', 1, 1, 'athens', 'Losif3', 'Sagient3',
--               'eeeeeeeeeeeeeeeeeeeeeeeee', 'dev');
--INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
--                                                         marital_status, position)
--VALUES ('1986-03-09 02:00:00.000000', 2, 2, 'athens', 'man2', 'man',
--                              'eeeeeeeeeeeeeeeeeeeeeeeee', 'dev');
--
--INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
--                                                       password)
--VALUES (0, '2024-05-03 02:39:53.385595', 2, 2, 'man@gmail.com', 'en-US',
--                                      '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');
--INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
--                         password)
--VALUES (0, '2024-05-03 02:39:53.385595', 1, 1, 'iosifsag2@gmail.com', 'en-US',
--        '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');
--
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (1, 'Regular Day Off', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (2, 'Maternity Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (3, 'Birth Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (4, 'IVF Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (5, 'OAED Maternity Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (6, 'Nursing Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (7, 'Adoption Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (8, 'Paternity Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (9, 'Single Parent Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (10, 'School Performance Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (11, 'Dependent Member Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (12, 'Marriage Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (13, 'Blood Donation', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (14, 'Grief Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (15, 'Student Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (16, 'Election Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (17, 'Court Attendance Leave', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (18, 'Strike', null);
INSERT INTO public.days_off_definition (id, description, wording)
VALUES (19, 'Union Leave', null);
--
--INSERT INTO public.days_off (available,total,definition_id,user_id)
--values(20,20,1,1);
--INSERT INTO public.days_off (available,total,definition_id,user_id)
--values(14,20,2,1);
--INSERT INTO public.days_off (available,total,definition_id,user_id)
--values(0,20,3,1);
--
----
create view days_off_with_definitions AS
Select o.*, d.*
From days_off o
Left join days_off_definition d
on o.definition_id = d.id;
----
--
----
----INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
----                          password)
----VALUES (2, '2024-05-03 02:39:53.385595', 1, 1, 'lostrif3@gmail.com', 'en-US',
----        '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');
----
------ USER: Ionis
----INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
----                                  marital_status, position)
----VALUES ('1986-03-09 02:00:00.000000', 1, 2, 'Ston patera sou', 'Ionis', 'Ypallhlopoulos',
----        'xoiros', 'dev');
----
--
--
----
----
----INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
----                                 id, user_id, comment)
----VALUES (4, '2024-01-17', '2024-01-14', 1, null, 1, 3, 1, 'Testadeia');
----INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
----                                 id, user_id, comment)
----VALUES (5, '2024-01-18', '2024-01-14', 1, null, 2, 4, 1, 'Testadeia 2: H epistrofh');
--
--
--select * from transactions;
--INSERT INTO public.transactions
--    (id, days, end_date, start_date, status, approved_by_id, definition_id, user_id, comment)
--VALUES
--    -- Records for user 1
--    (1000, 4, '2024-01-17', '2024-01-14', 1, NULL, 1, 1, 'Transaction for User 1'),
--    (1001, 3, '2024-02-10', '2024-02-08', 1, NULL, 2, 1, 'Transaction for User 1'),
--    (1002, 5, '2024-03-20', '2024-03-15', 1, NULL, 1, 1, 'Transaction for User 1'),
--    (1003, 2, '2024-04-05', '2024-04-03', 1, NULL, 2, 1, 'Transaction for User 1'),
--    (1004, 7, '2024-05-30', '2024-05-23', 1, NULL, 1, 1, 'Transaction for User 1'),
--    (1005, 1, '2024-06-01', '2024-06-01', 1, NULL, 3, 1, 'Transaction for User 1'),
--    (1006, 4, '2024-07-14', '2024-07-10', 1, NULL, 2, 1, 'Transaction for User 1'),
--    (1007, 3, '2024-08-09', '2024-08-07', 1, NULL, 1, 1, 'Transaction for User 1'),
--    (1008, 6, '2024-09-18', '2024-09-13', 1, NULL, 3, 1, 'Transaction for User 1'),
--    (1009, 2, '2024-10-04', '2024-10-03', 1, NULL, 2, 1, 'Transaction for User 1'),
--
--    -- Records for user 2
--    (1010, 3, '2024-01-15', '2024-01-12', 1, NULL, 1, 2, 'Transaction for User 2'),
--    (1011, 5, '2024-02-25', '2024-02-20', 1, NULL, 2, 2, 'Transaction for User 2'),
--    (1012, 4, '2024-03-12', '2024-03-09', 1, NULL, 1, 2, 'Transaction for User 2'),
--    (1013, 2, '2024-04-04', '2024-04-02', 1, NULL, 3, 2, 'Transaction for User 2'),
--    (1014, 7, '2024-05-15', '2024-05-08', 1, NULL, 2, 2, 'Transaction for User 2'),
--    (1015, 1, '2024-06-01', '2024-06-01', 1, NULL, 1, 2, 'Transaction for User 2'),
--    (1016, 3, '2024-07-07', '2024-07-05', 1, NULL, 3, 2, 'Transaction for User 2'),
--    (1017, 6, '2024-08-18', '2024-08-13', 1, NULL, 2, 2, 'Transaction for User 2'),
--    (1018, 4, '2024-09-10', '2024-09-06', 1, NULL, 1, 2, 'Transaction for User 2'),
--    (1019, 2, '2024-10-03', '2024-10-01', 1, NULL, 3, 2, 'Transaction for User 2');
