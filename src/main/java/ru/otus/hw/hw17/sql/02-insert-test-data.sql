SET search_path TO testing, public;

-- Дополнительные тестовые данные
INSERT INTO tests (title, description, duration_minutes, passing_score, created_by)
VALUES ('Тест по основам программирования', 'Базовые концепции программирования', 25, 60.0, 'teacher1'),
       ('Тест по базам данных', 'Углубленный тест по СУБД', 40, 80.0, 'teacher2'),
       ('Тест по веб-технологиям', 'HTML, CSS, JavaScript основы', 35, 65.0, 'teacher1');

-- Вопросы для теста по программированию
INSERT INTO questions (test_id, question_text, question_type, points, order_number)
VALUES (3, 'Что такое переменная в программировании?', 'single_choice', 1.0, 1),
       (3, 'Какие из перечисленных являются типами данных?', 'multiple_choice', 2.0, 2),
       (3, 'Что такое цикл?', 'single_choice', 1.5, 3);

-- Варианты ответов для вопросов программирования
INSERT INTO answer_options (question_id, option_text, is_correct, order_number)
VALUES
-- Вопрос: Что такое переменная?
(4, 'Именованная область памяти для хранения данных', TRUE, 1),
(4, 'Функция для выполнения операций', FALSE, 2),
(4, 'Условие в программе', FALSE, 3),
(4, 'Комментарий в коде', FALSE, 4),

-- Вопрос: Типы данных
(5, 'Integer', TRUE, 1),
(5, 'String', TRUE, 2),
(5, 'Boolean', TRUE, 3),
(5, 'Function', FALSE, 4),

-- Вопрос: Что такое цикл?
(6, 'Повторяющееся выполнение блока кода', TRUE, 1),
(6, 'Условная конструкция', FALSE, 2),
(6, 'Объявление переменной', FALSE, 3);

-- Вопросы для теста по базам данных
INSERT INTO questions (test_id, question_text, question_type, points, order_number)
VALUES (4, 'Что означает ACID в контексте баз данных?', 'single_choice', 2.0, 1),
       (4, 'Какие команды относятся к DML?', 'multiple_choice', 3.0, 2);

-- Варианты ответов для БД
INSERT INTO answer_options (question_id, option_text, is_correct, order_number)
VALUES
-- ACID
(7, 'Atomicity, Consistency, Isolation, Durability', TRUE, 1),
(7, 'Access, Control, Index, Database', FALSE, 2),
(7, 'Add, Create, Insert, Delete', FALSE, 3),

-- DML команды
(8, 'SELECT', TRUE, 1),
(8, 'INSERT', TRUE, 2),
(8, 'UPDATE', TRUE, 3),
(8, 'CREATE', FALSE, 4),
(8, 'ALTER', FALSE, 5);

-- Симуляция прохождения тестов
INSERT INTO test_attempts (test_id, user_name, attempt_number)
VALUES (1, 'student1', 1),
       (1, 'student2', 1),
       (3, 'student1', 1),
       (4, 'student2', 1);

-- Ответы пользователей
INSERT INTO user_answers (attempt_id, question_id, option_id)
VALUES
-- student1 проходит тест по SQL
(1, 1, 1), -- Правильный ответ
(1, 2, 1), -- Частично правильный
(1, 2, 3), -- Еще один правильный

-- student2 проходит тест по SQL
(2, 1, 1), -- Правильный ответ
(2, 2, 2), -- Неправильный ответ

-- student1 проходит тест по программированию
(3, 4, 1), -- Правильный ответ
(3, 5, 1), -- Правильный ответ
(3, 5, 2), -- Правильный ответ
(3, 6, 1), -- Правильный ответ

-- student2 проходит тест по БД
(4, 7, 1), -- Правильный ответ
(4, 8, 1), -- Правильный ответ
(4, 8, 2);
-- Правильный ответ

-- Подсчет результатов
SELECT calculate_test_result(1);
SELECT calculate_test_result(2);
SELECT calculate_test_result(3);
SELECT calculate_test_result(4);