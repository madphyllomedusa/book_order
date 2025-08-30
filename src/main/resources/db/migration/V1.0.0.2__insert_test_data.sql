INSERT INTO books (uuid, title, author, isbn)
VALUES
    (gen_random_uuid(), 'Война и мир', 'Лев Толстой', '9785389064965'),
    (gen_random_uuid(), 'Преступление и наказание', 'Фёдор Достоевский', '9785699152027'),
    (gen_random_uuid(), 'Мастер и Маргарита', 'Михаил Булгаков', '9785171183667');

INSERT INTO clients (uuid, last_name, first_name, middle_name, birth_date)
VALUES
    (gen_random_uuid(), 'Иванов', 'Иван', 'Иванович', '1990-05-01'),
    (gen_random_uuid(), 'Петров', 'Пётр', 'Сергеевич', '1985-03-12'),
    (gen_random_uuid(), 'Сидорова', 'Анна', 'Александровна', '1995-07-20');

INSERT INTO readings (uuid, client_uuid, book_uuid, taken_at)
SELECT gen_random_uuid(), c.uuid, b.uuid, now()
FROM clients c, books b
WHERE c.last_name = 'Иванов' AND b.title = 'Война и мир'
UNION ALL
SELECT gen_random_uuid(), c.uuid, b.uuid, now() - interval '5 days'
FROM clients c, books b
WHERE c.last_name = 'Петров' AND b.title = 'Преступление и наказание';
