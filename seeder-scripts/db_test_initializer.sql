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


INSERT INTO public.department (id, description, wording)
VALUES (1, 'Accounting', 'Accounting');
INSERT INTO public.department (id, description, wording)
VALUES (2, 'Administration', 'Administration');
INSERT INTO public.department (id, description, wording)
VALUES (3, 'General', 'General');
INSERT INTO public.department (id, description, wording)
VALUES (4, 'Technical Support', 'Technical Support');


INSERT INTO public.employee_card (birth_date, department_id, id, area, first_name, last_name,
                                  marital_status, position)
VALUES ('1986-03-09 02:00:00.000000', 1, 1, 'Sth mana sou', 'Losif3', 'Sagient3',
        'eeeeeeeeeeeeeeeeeeeeeeeee', 'dev');

INSERT INTO public.users (role, credentials_expiry_date, employee_card_id, id, email, language,
                          password)
VALUES (2, '2024-05-03 02:39:53.385595', 1, 1, 'lostrif3@gmail.com', 'en-US',
        '$2a$10$S.qfflR7b4M8ye7URolRj.RzHTsvuKlTMzGE04/hXfRgYA.nzvj5G');



-- UPDATE public.users
-- SET employee_card_id = 1
-- WHERE id = 1;


INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                 id, user_id, comment)
VALUES (4, '2024-01-17', '2024-01-14', 1, null, 1, 3, 1, 'Testadeia');
INSERT INTO public.transactions (days, end_date, start_date, status, approved_by_id, definition_id,
                                 id, user_id, comment)
VALUES (5, '2024-01-18', '2024-01-14', 1, null, 2, 4, 1, 'Testadeia 2: H epistrofh');
