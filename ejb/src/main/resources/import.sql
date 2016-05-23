--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into Registrant(id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212')
--insert into client(name) values ('Isaac Veron')
--insert into provider(name) values ('INCA')
--insert into product(precioUnitario, nameProduct, cantidad) values (1000,'Lavandina',50)

INSERT INTO provider(name) VALUES ('Proveedor 1');
INSERT INTO provider(name) VALUES ('Proveedor 2');
INSERT INTO provider(name) VALUES ('Proveedor 3');
INSERT INTO provider(name) VALUES ('Proveedor 4');
INSERT INTO provider(name) VALUES ('Proveedor 5');
INSERT INTO provider(name) VALUES ('Proveedor 6');
INSERT INTO provider(name) VALUES ('Proveedor 7');
INSERT INTO provider(name) VALUES ('Proveedor 8');
INSERT INTO provider(name) VALUES ('Proveedor 9');

INSERT INTO client(name) VALUES ('Cliente 1');
INSERT INTO client(name) VALUES ('Cliente 2');
INSERT INTO client(name) VALUES ('Cliente 3');
INSERT INTO client(name) VALUES ('Cliente 4');
INSERT INTO client(name) VALUES ('Cliente 5');
INSERT INTO client(name) VALUES ('Cliente 6');
INSERT INTO client(name) VALUES ('Cliente 7');
INSERT INTO client(name) VALUES ('Cliente 8');
INSERT INTO client(name) VALUES ('Cliente 9');

INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (100,'Producto 1',3000);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (200,'Producto 2', 12000);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (160,'Producto 3', 12000);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (80,'Producto 4', 12000);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (410,'Producto 5', 100);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (400,'Producto 6', 100);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (500,'Producto 7', 100);
INSERT INTO product(cantidad, nameproduct, preciounitario) VALUES (300,'Producto 8', 100);

INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Juan 1', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Juan 1', 'pass', 'venta', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Jose 1', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Jose 1', 'pass', 'venta', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Marcos 1', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Marcos 1', 'pass', 'venta', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Juan 2', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Juan 2', 'pass', 'venta', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Jose 2', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Jose 2', 'pass', 'venta', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Marcos 2', 'pass', 'compra', '');
INSERT INTO usuario(name, pass, rol, access_token) VALUES ('Marcos 2', 'pass', 'venta', '');

INSERT INTO compra(fecha, provider_id) VALUES ('2016-05-05',1);
INSERT INTO compra(fecha, provider_id) VALUES ('2016-05-06',2);
INSERT INTO compra(fecha, provider_id) VALUES ('2016-05-14',1);

INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (10,1,4);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (12,1,5);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (20,1,1);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (10,2,4);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (12,2,5);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (20,2,1);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (10,3,4);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (12,3,5);
INSERT INTO compra_det(cantidad, compra_id, product_id) VALUES (20,3,1);

--INSERT INTO compra(fecha, provider_id) VALUES (NOW(),2);

