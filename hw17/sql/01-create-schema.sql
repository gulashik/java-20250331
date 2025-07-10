-- PostgreSQL DDL скрипты

-- Создание схемы
CREATE SCHEMA IF NOT EXISTS testing;
SET search_path TO testing, public;

-- Таблица для хранения тестов
CREATE TABLE tests
(
    test_id          SERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    duration_minutes INTEGER CHECK (duration_minutes > 0),
    max_attempts     INTEGER   DEFAULT 3 CHECK (max_attempts > 0),
    passing_score    DECIMAL(5, 2) CHECK (passing_score >= 0 AND passing_score <= 100),
    is_active        BOOLEAN   DEFAULT TRUE,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by       VARCHAR(100)
);

-- Таблица для хранения вопросов
CREATE TABLE questions
(
    question_id   SERIAL PRIMARY KEY,
    test_id       INTEGER NOT NULL,
    question_text TEXT    NOT NULL,
    question_type VARCHAR(20)   DEFAULT 'single_choice' CHECK (question_type IN ('single_choice', 'multiple_choice')),
    points        DECIMAL(5, 2) DEFAULT 1.0 CHECK (points > 0),
    order_number  INTEGER NOT NULL,
    is_active     BOOLEAN       DEFAULT TRUE,
    created_at    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (test_id) REFERENCES tests (test_id) ON DELETE CASCADE,
    UNIQUE (test_id, order_number)
);

-- Таблица для хранения вариантов ответов
CREATE TABLE answer_options
(
    option_id    SERIAL PRIMARY KEY,
    question_id  INTEGER NOT NULL,
    option_text  TEXT    NOT NULL,
    is_correct   BOOLEAN   DEFAULT FALSE,
    order_number INTEGER NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (question_id) REFERENCES questions (question_id) ON DELETE CASCADE,
    UNIQUE (question_id, order_number)
);

-- Таблица для хранения результатов прохождения тестов
CREATE TABLE test_attempts
(
    attempt_id     SERIAL PRIMARY KEY,
    test_id        INTEGER      NOT NULL,
    user_name      VARCHAR(100) NOT NULL,
    start_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time       TIMESTAMP,
    score          DECIMAL(5, 2),
    max_score      DECIMAL(5, 2),
    percentage     DECIMAL(5, 2),
    is_completed   BOOLEAN   DEFAULT FALSE,
    attempt_number INTEGER   DEFAULT 1,

    FOREIGN KEY (test_id) REFERENCES tests (test_id) ON DELETE CASCADE
);

-- Таблица для хранения ответов пользователей
CREATE TABLE user_answers
(
    answer_id   SERIAL PRIMARY KEY,
    attempt_id  INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    option_id   INTEGER NOT NULL,
    answered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (attempt_id) REFERENCES test_attempts (attempt_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions (question_id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES answer_options (option_id) ON DELETE CASCADE,
    UNIQUE (attempt_id, question_id, option_id)
);

-- Создание индексов для оптимизации запросов
CREATE INDEX idx_questions_test_id ON questions (test_id);
CREATE INDEX idx_questions_active ON questions (is_active);
CREATE INDEX idx_answer_options_question_id ON answer_options (question_id);
CREATE INDEX idx_answer_options_correct ON answer_options (is_correct);
CREATE INDEX idx_test_attempts_test_id ON test_attempts (test_id);
CREATE INDEX idx_test_attempts_user ON test_attempts (user_name);
CREATE INDEX idx_user_answers_attempt_id ON user_answers (attempt_id);
CREATE INDEX idx_user_answers_question_id ON user_answers (question_id);

-- Создание триггера для автоматического обновления updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_tests_updated_at
    BEFORE UPDATE
    ON tests
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Функция для вычисления результата теста
CREATE OR REPLACE FUNCTION calculate_test_result(p_attempt_id INTEGER)
    RETURNS VOID AS
$$
DECLARE
    v_score      DECIMAL(5, 2) := 0;
    v_max_score  DECIMAL(5, 2) := 0;
    v_percentage DECIMAL(5, 2);
BEGIN
    -- Вычисление набранных баллов
    SELECT COALESCE(SUM(
                            CASE
                                WHEN ao.is_correct THEN q.points
                                ELSE 0
                                END
                    ), 0)
    INTO v_score
    FROM user_answers ua
             JOIN answer_options ao ON ua.option_id = ao.option_id
             JOIN questions q ON ua.question_id = q.question_id
    WHERE ua.attempt_id = p_attempt_id;

    -- Вычисление максимально возможных баллов
    SELECT COALESCE(SUM(q.points), 0)
    INTO v_max_score
    FROM questions q
             JOIN test_attempts ta ON q.test_id = ta.test_id
    WHERE ta.attempt_id = p_attempt_id
      AND q.is_active = TRUE;

    -- Вычисление процентов
    IF v_max_score > 0 THEN
        v_percentage := (v_score / v_max_score) * 100;
    ELSE
        v_percentage := 0;
    END IF;

    -- Обновление результата
    UPDATE test_attempts
    SET score        = v_score,
        max_score    = v_max_score,
        percentage   = v_percentage,
        end_time     = CURRENT_TIMESTAMP,
        is_completed = TRUE
    WHERE attempt_id = p_attempt_id;
END;
$$ LANGUAGE plpgsql;

-- Добавление комментариев к таблицам
COMMENT ON TABLE tests IS 'Таблица для хранения тестов';
COMMENT ON TABLE questions IS 'Таблица для хранения вопросов тестов';
COMMENT ON TABLE answer_options IS 'Таблица для хранения вариантов ответов на вопросы';
COMMENT ON TABLE test_attempts IS 'Таблица для хранения результатов прохождения тестов';
COMMENT ON TABLE user_answers IS 'Таблица для хранения ответов пользователей';

-- Добавление тестовых данных для проверки работоспособности
INSERT INTO tests (title, description, duration_minutes, passing_score, created_by)
VALUES ('Тест по основам SQL', 'Базовый тест для проверки знаний SQL', 30, 70.0, 'admin'),
       ('Тест по PostgreSQL', 'Тест по специфическим функциям PostgreSQL', 45, 75.0, 'admin');

INSERT INTO questions (test_id, question_text, question_type, points, order_number)
VALUES (1, 'Что означает аббревиатура SQL?', 'single_choice', 2.0, 1),
       (1, 'Какие из следующих команд относятся к DDL?', 'multiple_choice', 3.0, 2),
       (2, 'Какой тип данных в PostgreSQL используется для хранения JSON?', 'single_choice', 2.5, 1);

INSERT INTO answer_options (question_id, option_text, is_correct, order_number)
VALUES
-- Вопрос 1
(1, 'Structured Query Language', TRUE, 1),
(1, 'Simple Query Language', FALSE, 2),
(1, 'Standard Query Language', FALSE, 3),
(1, 'System Query Language', FALSE, 4),
-- Вопрос 2
(2, 'CREATE', TRUE, 1),
(2, 'SELECT', FALSE, 2),
(2, 'DROP', TRUE, 3),
(2, 'INSERT', FALSE, 4),
(2, 'ALTER', TRUE, 5),
-- Вопрос 3
(3, 'JSON', TRUE, 1),
(3, 'TEXT', FALSE, 2),
(3, 'VARCHAR', FALSE, 3),
(3, 'BLOB', FALSE, 4);