insert into users(username, password, role) values ('admin', 'admin', 'ROLE_ADMIN');
insert into users(username, password, role) values ('user', 'user', 'ROLE_USER');

insert into cafe(CAFE_NAME, CITY_NAME, ADDRESS, CAFE_EMAIL, CAFE_NUMBER,OPEN_AT,CLOSE_AT ) values ('Amora','Leipzig', 'Osttstr.10', 'amora_pizza@gmx.de','+491325674378','10am','12pm');
insert into cafe(CAFE_NAME, CITY_NAME, ADDRESS, CAFE_EMAIL, CAFE_NUMBER,OPEN_AT,CLOSE_AT ) values ('Caesar','Leipzig', 'Täubchenweg 76', 'caesar_pizza@gmx.de','+491325004378','9am','10pm');
insert into cafe(CAFE_NAME, CITY_NAME, ADDRESS, CAFE_EMAIL, CAFE_NUMBER,OPEN_AT,CLOSE_AT ) values ('Star','Leipzig', 'Gutenbergplatz 1', 'star_pizza@gmx.de','+491985678878','11am','12pm');
insert into cafe(CAFE_NAME, CITY_NAME, ADDRESS, CAFE_EMAIL, CAFE_NUMBER,OPEN_AT,CLOSE_AT ) values ('Napoletana','Leipzig', 'Heinrichstr.1a', 'napoletana_pizza@gmx.de','+491325698578','11am','11pm');

insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Margarita', 'S', 'Cheese', 6.0 ,1);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Napoletana', 'S', 'Cheese', 6,1);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Vegetarina', 'S', 'Cheese', 6,1);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Pampe', 'S', 'Cheese', 6,1);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Pepperoni', 'S', 'Cheese', 6,2);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('American Hot', 'S', 'Cheese', 6,3);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Verona', 'S', 'Cheese', 6,3);
insert into pizza (PIZZA_NAME, PIZZA_SIZE, KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Capriciosa', 'S', 'Cheese', 6,2);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Hawaii', 'S', 'Cheese', 6,1);
insert into pizza (PIZZA_NAME, PIZZA_SIZE,KEY_INGREDIENTS, PIZZA_PRICE, cafe_id) values ('Al Tonno', 'M', 'Onion', 9,4);

