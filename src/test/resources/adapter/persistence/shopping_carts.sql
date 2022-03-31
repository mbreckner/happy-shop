INSERT INTO shopping_cart
            (id, country_code, created_date)
VALUES      ('cart1', 'CHE', '2022-03-09 10:15:30Z');

INSERT INTO cart_item
            (id, shopping_cart_id, cart_item_id, barcode, quantity, unit_price, description)
VALUES      ('cart1::item1', 'cart1', 'item1', 'barcode1', 3.00, 1.00, 'banana');

INSERT INTO cart_item
            (id, shopping_cart_id, cart_item_id, barcode, quantity, unit_price, description)
VALUES      ('cart1::item2', 'cart1', 'item2', 'barcode2', 3.00, 5.00, 'cheese');