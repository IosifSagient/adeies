INSERT INTO public.department (id, description, wording)
VALUES (1, 'Accounting', 'Accounting');
INSERT INTO public.department (id, description, wording)
VALUES (2, 'Administration', 'Administration');
INSERT INTO public.department (id, description, wording)
VALUES (3, 'General', 'General');
INSERT INTO public.department (id, description, wording)
VALUES (4, 'Technical Support', 'Technical Support');


        ---- USER: Lostrif
INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
                                          marital_status, position)
VALUES ('1986-03-09 02:00:00.000000', 1, 1, 'athens', 'Losif3', 'Sagient3',
               'eeeeeeeeeeeeeeeeeeeeeeeee', 'dev');
INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
                                                         marital_status, position)
VALUES ('1986-03-09 02:00:00.000000', 2, 2, 'athens', 'man2', 'man',
                              'eeeeeeeeeeeeeeeeeeeeeeeee', 'dev');

INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
                                                       password)
VALUES (0, '2024-05-03 02:39:53.385595', 2, 2, 'man@gmail.com', 'en-US',
                                      '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');
INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
                         password)
VALUES (0, '2024-05-03 02:39:53.385595', 1, 1, 'iosifsag2@gmail.com', 'en-US',
        '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');

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
INSERT INTO public.days_off (available,total,definition_id,user_id)
values(20,20,1,1);
INSERT INTO public.days_off (available,total,definition_id,user_id)
values(14,20,2,1);
INSERT INTO public.days_off (available,total,definition_id,user_id)
values(0,20,3,1);
--
--
create view days_off_with_definitions AS
Select o.*, d.*
From days_off o
Left join days_off_definition d
on o.definition_id = d.id;
--

--
--INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
--                          password)
--VALUES (2, '2024-05-03 02:39:53.385595', 1, 1, 'lostrif3@gmail.com', 'en-US',
--        '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');
--
---- USER: Ionis
--INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
--                                  marital_status, position)
--VALUES ('1986-03-09 02:00:00.000000', 1, 2, 'Ston patera sou', 'Ionis', 'Ypallhlopoulos',
--        'xoiros', 'dev');
--


--
--
--INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
--                                 id, user_id, comment)
--VALUES (4, '2024-01-17', '2024-01-14', 1, null, 1, 3, 1, 'Testadeia');
--INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
--                                 id, user_id, comment)
--VALUES (5, '2024-01-18', '2024-01-14', 1, null, 2, 4, 1, 'Testadeia 2: H epistrofh');


select * from transactions;
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (4, '2024-01-17', '2024-01-14', 1, null, 1, 1, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (6, '2024-01-17', '2024-01-14', 1, null, 1, 2, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (7, '2024-01-17', '2024-01-14', 1, null, 1, 3, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (8, '2024-01-17', '2024-01-14', 1, null, 1, 4, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (9, '2024-01-17', '2024-01-14', 1, null, 1, 5, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (10, '2024-01-17', '2024-01-14', 1, null, 1, 6, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (11, '2024-01-17', '2024-01-14', 1, null, 1, 7, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (12, '2024-01-17', '2024-01-14', 1, null, 1, 8, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (13, '2024-01-17', '2024-01-14', 1, null, 1, 9, 1, 'Testadeia');

INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (14, '2024-01-17', '2024-01-14', 1, null, 1, 10, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (15, '2024-01-17', '2024-01-14', 1, null, 1, 11, 1, 'Testadeia');

INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (16, '2024-01-17', '2024-01-14', 1, null, 1, 12, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                id, user_id, comment)
VALUES (17, '2024-01-17', '2024-01-14', 1, null, 1, 13, 1, 'Testadeia');