-- =========================
-- 1. DATABASE
-- =========================
--CREATE DATABASE IF NOT EXISTS shopping_cart_localization;
USE shopping_cart_localization;

-- =========================
-- 2. TABLES
-- =========================

-- Languages
CREATE TABLE languages (
                           code VARCHAR(5) PRIMARY KEY,
                           name VARCHAR(50) NOT NULL,
                           is_default BOOLEAN DEFAULT FALSE
);

-- Keys
CREATE TABLE localization_keys (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   `key` VARCHAR(100) NOT NULL UNIQUE
);

-- Translations
CREATE TABLE localization_values (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     key_id INT NOT NULL,
                                     language_code VARCHAR(5) NOT NULL,
                                     value TEXT NOT NULL,

                                     FOREIGN KEY (key_id) REFERENCES localization_keys(id) ON DELETE CASCADE,
                                     FOREIGN KEY (language_code) REFERENCES languages(code),

                                     UNIQUE KEY unique_translation (key_id, language_code)
);

-- =========================
-- 3. SEED: LANGUAGES
-- =========================
INSERT INTO languages (code, name, is_default) VALUES
                                                   ('en', 'English', TRUE),
                                                   ('fi', 'Finnish', FALSE),
                                                   ('sv', 'Swedish', FALSE),
                                                   ('ja', 'Japanese', FALSE),
                                                   ('ar', 'Arabic', FALSE);

-- =========================
-- 4. SEED: KEYS
-- =========================
INSERT INTO localization_keys (`key`) VALUES
                                          ('app.title'),
                                          ('welcome'),
                                          ('prompt.num.items'),
                                          ('prompt.price'),
                                          ('prompt.quantity'),
                                          ('item.prompt'),
                                          ('item.added'),
                                          ('total.cost'),
                                          ('items.count'),
                                          ('error.invalid.input'),
                                          ('error.invalid.number'),
                                          ('error.positive.number'),
                                          ('select.language'),
                                          ('btn.generate.items'),
                                          ('btn.calculate.total');

-- =========================
-- 5. SEED: TRANSLATIONS
-- =========================

-- ENGLISH
INSERT INTO localization_values (key_id, language_code, value)
SELECT id, 'en',
       CASE `key`
           WHEN 'app.title' THEN 'Shopping Cart'
           WHEN 'welcome' THEN 'Welcome to Shopping Cart!'
           WHEN 'prompt.num.items' THEN 'Enter the number of items to purchase:'
           WHEN 'prompt.price' THEN 'Enter the price for item:'
           WHEN 'prompt.quantity' THEN 'Enter the quantity for item:'
           WHEN 'item.prompt' THEN 'Item'
           WHEN 'item.added' THEN 'Added item total:'
           WHEN 'total.cost' THEN 'Total cost:'
           WHEN 'items.count' THEN 'Total items:'
           WHEN 'error.invalid.input' THEN 'Invalid input'
           WHEN 'error.invalid.number' THEN 'Invalid number format. Please enter a valid number.'
           WHEN 'error.positive.number' THEN 'Please enter a positive number.'
           WHEN 'select.language' THEN 'Select Language:'
           WHEN 'btn.generate.items' THEN 'Generate Items'
           WHEN 'btn.calculate.total' THEN 'Calculate Total'
           END
FROM localization_keys;

-- FINNISH
INSERT INTO localization_values (key_id, language_code, value)
SELECT id, 'fi',
       CASE `key`
           WHEN 'app.title' THEN 'Ostoskori'
           WHEN 'welcome' THEN 'Tervetuloa ostoskoriin!'
           WHEN 'prompt.num.items' THEN 'Syötä ostettavien tuotteiden määrä:'
           WHEN 'prompt.price' THEN 'Syötä tuotteen hinta:'
           WHEN 'prompt.quantity' THEN 'Syötä tuotteen määrä:'
           WHEN 'item.prompt' THEN 'Tuote'
           WHEN 'item.added' THEN 'Lisätty tuotteen yhteishinta:'
           WHEN 'total.cost' THEN 'Kokonaishinta:'
           WHEN 'items.count' THEN 'Tuotteiden yhteismäärä:'
           WHEN 'error.invalid.input' THEN 'Virheellinen syöte'
           WHEN 'error.invalid.number' THEN 'Virheellinen numeromuoto. Anna kelvollinen numero.'
           WHEN 'error.positive.number' THEN 'Anna positiivinen luku.'
           WHEN 'select.language' THEN 'Valitse kieli:'
           WHEN 'btn.generate.items' THEN 'Luo tuotteet'
           WHEN 'btn.calculate.total' THEN 'Laske yhteensä'
           END
FROM localization_keys;

-- SWEDISH
INSERT INTO localization_values (key_id, language_code, value)
SELECT id, 'sv',
       CASE `key`
           WHEN 'app.title' THEN 'Kundvagn'
           WHEN 'welcome' THEN 'Välkommen till kundvagnen!'
           WHEN 'prompt.num.items' THEN 'Ange antal artiklar att köpa:'
           WHEN 'prompt.price' THEN 'Ange priset för artikeln:'
           WHEN 'prompt.quantity' THEN 'Ange antalet för artikeln:'
           WHEN 'item.prompt' THEN 'Artikel'
           WHEN 'item.added' THEN 'Tillagd artikelsumma:'
           WHEN 'total.cost' THEN 'Totalkostnad:'
           WHEN 'items.count' THEN 'Totalt antal artiklar:'
           WHEN 'error.invalid.input' THEN 'Ogiltig inmatning'
           WHEN 'error.invalid.number' THEN 'Ogiltigt nummerformat. Ange ett giltigt nummer.'
           WHEN 'error.positive.number' THEN 'Ange ett positivt tal.'
           WHEN 'select.language' THEN 'Välj språk:'
           WHEN 'btn.generate.items' THEN 'Generera artiklar'
           WHEN 'btn.calculate.total' THEN 'Beräkna totalt'
           END
FROM localization_keys;

-- JAPANESE
INSERT INTO localization_values (key_id, language_code, value)
SELECT id, 'ja',
       CASE `key`
           WHEN 'app.title' THEN 'ショッピングカート'
           WHEN 'welcome' THEN 'ショッピングカートへようこそ！'
           WHEN 'prompt.num.items' THEN '購入するアイテムの数を入力してください：'
           WHEN 'prompt.price' THEN 'アイテムの価格を入力してください：'
           WHEN 'prompt.quantity' THEN 'アイテムの数量を入力してください：'
           WHEN 'item.prompt' THEN 'アイテム'
           WHEN 'item.added' THEN '追加されたアイテムの合計：'
           WHEN 'total.cost' THEN '合計金額：'
           WHEN 'items.count' THEN 'アイテム合計数：'
           WHEN 'error.invalid.input' THEN '無効な入力'
           WHEN 'error.invalid.number' THEN '無効な数値形式です。有効な数値を入力してください。'
           WHEN 'error.positive.number' THEN '正の数を入力してください。'
           WHEN 'select.language' THEN '言語を選択：'
           WHEN 'btn.generate.items' THEN 'アイテムを生成'
           WHEN 'btn.calculate.total' THEN '合計を計算'
           END
FROM localization_keys;

-- ARABIC
INSERT INTO localization_values (key_id, language_code, value)
SELECT id, 'ar',
       CASE `key`
           WHEN 'app.title' THEN 'عربة التسوق'
           WHEN 'welcome' THEN 'مرحباً بك في عربة التسوق!'
           WHEN 'prompt.num.items' THEN 'أدخل عدد العناصر للشراء:'
           WHEN 'prompt.price' THEN 'أدخل سعر العنصر:'
           WHEN 'prompt.quantity' THEN 'أدخل كمية العنصر:'
           WHEN 'item.prompt' THEN 'عنصر'
           WHEN 'item.added' THEN 'إجمالي العنصر المضاف:'
           WHEN 'total.cost' THEN 'التكلفة الإجمالية:'
           WHEN 'items.count' THEN 'إجمالي العناصر:'
           WHEN 'error.invalid.input' THEN 'إدخال غير صالح'
           WHEN 'error.invalid.number' THEN 'تنسيق رقم غير صالح. يرجى إدخال رقم صحيح.'
           WHEN 'error.positive.number' THEN 'يرجى إدخال رقم موجب.'
           WHEN 'select.language' THEN 'اختر اللغة:'
           WHEN 'btn.generate.items' THEN 'إنشاء العناصر'
           WHEN 'btn.calculate.total' THEN 'احسب المجموع'
           END
FROM localization_keys;