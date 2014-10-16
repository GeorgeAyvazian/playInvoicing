INSERT INTO tax_rates (id, description, amount) VALUES
  (1, 'GST', 10),
  (2, 'No Tax', 0),
  (3, 'Postage', 20),
  (4, 'Sales Tax', 10),
  (5, 'VAT', 10);

INSERT INTO products (id, description, unitprice, tax_id) VALUES
  (1, 'Accomodation', 1255, 1),
  (2, 'Accounting Services', 140, 1),
  (3, 'Hourly Charge', 150, 1);

INSERT INTO invoices (id, number) VALUES
  (1, '1'),
  (2, '2');


INSERT INTO line_items (id, product_id, quantity, amount, invoice_id) VALUES (1, 1, 2, 10, 1);

