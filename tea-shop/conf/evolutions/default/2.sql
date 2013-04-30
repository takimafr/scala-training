# --- Sample dataset

# --- !Ups

insert into user (login,password,name) values ('steph','steph', 'Stephane');

insert into supplier (id,name,country,uri) values (1,'Stash','USA','http://stashtea.com');
insert into supplier (id,name,country,uri) values (2,'Mariage Frères','France','http://mariagefreres.com');
insert into supplier (id,name,country,uri) values (3,'Postcard Teas','England','http://postcardteas.com');
insert into supplier (id,name,country,uri) values (4,'Silk Road Teas','USA','http://silkroadteas.com');
insert into supplier (id,name,country,uri) values (5,'TeaGschwndner','Germany','http://shop.tgtea.com');
insert into supplier (id,name,country,uri) values (6,'Seattle Teacup','USA','http://seattleteacup.com');
insert into supplier (id,name,country,uri) values (7,'Le Palais Des Thés','France','http://www.palaisdesthes.com');

insert into tea (id,name,color,size,currency,price,supplier_id) values (1,'Darjeeling Estate Golden Tipped','Black','100g','$','15.00',1);
insert into tea (id,name,color,size,currency,price,supplier_id) values (2,'Irish Breakfast','Black','100g','$','7.50',1);
insert into tea (id,name,color,size,currency,price,supplier_id) values (3,'China Keemun','Black','100g','$','7.50',1);
insert into tea (id,name,color,size,currency,price,supplier_id) values (4,'Moroccan Mint Green Tea','Green','100g','$','7.50',1);
insert into tea (id,name,color,size,currency,price,supplier_id) values (5,'White Tea from beyond the Skies','White','100g','€','105.00',2);
insert into tea (id,name,color,size,currency,price,supplier_id) values (6,'Blue Himalaya','Oolong','100g','€','28',2);
insert into tea (id,name,color,size,currency,price,supplier_id) values (7,'Golden Jamguri SFTGFOP1','Black','100g','€','60',2);
insert into tea (id,name,color,size,currency,price,supplier_id) values (8,'Gianfranco''s Earl Grey','Black','50g','£','6.45',3);
insert into tea (id,name,color,size,currency,price,supplier_id) values (9,'Master Matsumoto''s Supernatural Green','Green','50g','£','11.95',3);
insert into tea (id,name,color,size,currency,price,supplier_id) values (10,'2012 DARJEELING HILTON DJ1 S.F.T.P.G.F.O.P.1','Black','100g','€','56.00',4);

# --- !Downs

delete from tea;
delete from supplier;
