INSERT INTO user_role (role)
VALUES ('employee'),
('finance_manager');

INSERT INTO users (email, first_name, last_name, password, username, role_id)
VALUES ('jshim@email.com', 'Jiwon', 'Shim', 'jiwon1234', 'jshim', 2),
('mkim@email.com', 'Minah', 'Kim', 'minah1234', 'mkim', 1);

INSERT INTO type (type)
VALUES ('lodging'),
('travel'),
('food'),
('other');

INSERT INTO status (status)
VALUES ('pending'),
('approved'),
('denied');

INSERT INTO reimbursements (amount, description, receipt, time_resolved, time_submitted, author_id, resolver_id, status_id, type_id)
VALUES
(1700.98, 'Relocation Assistance', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', null, '05/05/2022', 2, null, 1, 4);