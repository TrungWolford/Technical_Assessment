-- Insert 5 lead records
INSERT INTO leads (full_name, email, phone_number, vehicle_of_interest, source, notes, dealership_id, status, created_at, updated_at)
VALUES 
  ('Nguyen Van A', 'nguyenvana@example.com', '0901234567', 'Toyota Camry', 'Website', 'Customer is interested in a sedan', 'DEALER-001', 'NEW', NOW(), NOW()),
  ('Tran Thi B', 'tranthib@example.com', '0902345678', 'Honda Accord', 'Facebook', 'Requested a test drive', 'DEALER-001', 'CONTACTED', NOW(), NOW()),
  ('Pham Van C', 'phamvanc@example.com', '0903456789', 'Mazda CX-5', 'Google', 'Customer requested pricing information', 'DEALER-001', 'QUALIFIED', NOW(), NOW()),
  ('Le Thi D', 'lethid@example.com', '0904567890', 'Hyundai Tucson', 'Referral', 'Customer is ready to buy', 'DEALER-001', 'WON', NOW(), NOW()),
  ('Hoang Van E', 'hoangvane@example.com', '0905678901', 'Ford Ranger', 'Direct', 'Not interested at the moment', 'DEALER-001', 'LOST', NOW(), NOW());

-- Insert 25 lead activity records (5 for each lead)
-- Lead ID 1
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (1, 'CALL', 'Called the customer to introduce the new vehicle', 'sales_01', NOW() - INTERVAL '5 days', NOW()),
  (1, 'EMAIL', 'Sent an email with the recommended vehicle list', 'sales_01', NOW() - INTERVAL '4 days', NOW()),
  (1, 'NOTE', 'Customer is interested in the promotional price', 'sales_01', NOW() - INTERVAL '3 days', NOW()),
  (1, 'SMS', 'Sent a reminder message about the vehicle demo', 'sales_01', NOW() - INTERVAL '2 days', NOW()),
  (1, 'MEETING', 'Scheduled a meeting at the showroom on Friday', 'sales_01', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 2
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (2, 'EMAIL', 'Sent detailed information about the Honda Accord', 'sales_02', NOW() - INTERVAL '4 days', NOW()),
  (2, 'CALL', 'Called to confirm the test drive appointment', 'sales_02', NOW() - INTERVAL '3 days', NOW()),
  (2, 'MEETING', 'Customer visited the showroom for a test drive', 'sales_02', NOW() - INTERVAL '2 days', NOW()),
  (2, 'NOTE', 'Customer is satisfied with the car and is considering it', 'sales_02', NOW() - INTERVAL '1 days', NOW()),
  (2, 'SMS', 'Sent a special price offer to the customer', 'sales_02', NOW(), NOW());

-- Lead ID 3
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (3, 'CALL', 'Called to provide a detailed quotation', 'sales_03', NOW() - INTERVAL '6 days', NOW()),
  (3, 'EMAIL', 'Sent the quotation by email', 'sales_03', NOW() - INTERVAL '5 days', NOW()),
  (3, 'NOTE', 'Customer requested warranty information', 'sales_03', NOW() - INTERVAL '3 days', NOW()),
  (3, 'MEETING', 'Customer came to view the car in person', 'sales_03', NOW() - INTERVAL '2 days', NOW()),
  (3, 'CALL', 'Continued negotiating the price', 'sales_03', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 4
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (4, 'EMAIL', 'Sent the sales contract', 'sales_04', NOW() - INTERVAL '5 days', NOW()),
  (4, 'CALL', 'Confirmed the contract details with the customer', 'sales_04', NOW() - INTERVAL '4 days', NOW()),
  (4, 'MEETING', 'Customer signed the contract at the showroom', 'sales_04', NOW() - INTERVAL '3 days', NOW()),
  (4, 'NOTE', 'Completed the paperwork and delivered the car successfully', 'sales_04', NOW() - INTERVAL '2 days', NOW()),
  (4, 'SMS', 'Sent a thank-you message and warranty information', 'sales_04', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 5
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (5, 'CALL', 'Called to understand the customer needs', 'sales_05', NOW() - INTERVAL '10 days', NOW()),
  (5, 'EMAIL', 'Sent product information', 'sales_05', NOW() - INTERVAL '8 days', NOW()),
  (5, 'SMS', 'Sent a special promotion', 'sales_05', NOW() - INTERVAL '6 days', NOW()),
  (5, 'NOTE', 'Customer is not interested and could not be reached', 'sales_05', NOW() - INTERVAL '5 days', NOW()),
  (5, 'CALL', 'Tried calling again, but the customer declined', 'sales_05', NOW() - INTERVAL '3 days', NOW());

-- Indexes
CREATE INDEX IF NOT EXISTS idx_leads_dealership_id ON leads (dealership_id);
CREATE INDEX IF NOT EXISTS idx_leads_status ON leads (status);
CREATE INDEX IF NOT EXISTS idx_leads_created_at_desc ON leads (created_at DESC);
CREATE INDEX IF NOT EXISTS idx_lead_activities_lead_id ON lead_activities (lead_id);
