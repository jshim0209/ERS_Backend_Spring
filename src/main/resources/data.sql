INSERT INTO user_role (role)
VALUES ('employee'),
('finance_manager');

INSERT INTO users (email, first_name, last_name, password, username, role_id)
VALUES ('jshim@email.com', 'Jiwon', 'Shim', 'jiwon1234', 'jshim', 2),
('jvonortas@email.com', 'Jason', 'Vonortas', 'jason1234', 'jvonortas', 1),
('jcho@email.com', 'Joe', 'Cho', 'joe1234', 'jcho', 1),
('achoi@email.com', 'Andy', 'Choi', 'andy1234', 'achoi', 2),
('mkim@email.com', 'Minah', 'Kim', 'minah1234', 'mkim', 1);

INSERT INTO type (type)
VALUES ('lodging'),
('travel'),
('food'),
('other');

INSERT INTO status (status)
VALUES ('pending'),
('approved'),
('rejected');

INSERT INTO reimbursements (amount, description, receipt, time_resolved, time_submitted, author_id, resolver_id, status_id, type_id)
VALUES
(1700.98, 'Relocation Assistance', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', null, '05/05/2022', 2, null, 1, 4),
(234.98, 'Hotel in New York', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', '05/10/2022', '05/05/2022', 5, 1, 2, 1),
(145.98, 'Certification', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', '05/09/2022', '05/05/2022', 2, 4, 2, 4),
(136.98, 'Train ticket to DC', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', null, '05/05/2022', 3, null, 1, 2),
(654.98, 'Dinner with clients', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', '05/12/2022', '05/05/2022', 5, 1, 2, 3),
(43.98, 'Lunch Money', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', '05/11/2022', '05/05/2022', 3, 4, 3, 3),
(56.98, 'Programming books', 'https://storage.googleapis.com/ers-images/receipt-3.jpg', null, '05/05/2022', 2, null, 1, 4);