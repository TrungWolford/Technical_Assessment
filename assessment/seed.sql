-- Insert 5 Lead records
INSERT INTO leads (full_name, email, phone_number, vehicle_of_interest, source, notes, dealership_id, status, created_at, updated_at)
VALUES 
  ('Nguyễn Văn A', 'nguyenvana@example.com', '0901234567', 'Toyota Camry', 'Website', 'Khách hàng quan tâm xe sedan', 'DEALER-001', 'NEW', NOW(), NOW()),
  ('Trần Thị B', 'tranthib@example.com', '0902345678', 'Honda Accord', 'Facebook', 'Yêu cầu test drive', 'DEALER-001', 'CONTACTED', NOW(), NOW()),
  ('Phạm Văn C', 'phamvanc@example.com', '0903456789', 'Mazda CX-5', 'Google', 'Khách hàng yêu cầu giá', 'DEALER-001', 'QUALIFIED', NOW(), NOW()),
  ('Lê Thị D', 'lethid@example.com', '0904567890', 'Hyundai Tucson', 'Referral', 'Khách hàng sẵn sàng mua', 'DEALER-001', 'WON', NOW(), NOW()),
  ('Hoàng Văn E', 'hoangvane@example.com', '0905678901', 'Ford Ranger', 'Direct', 'Không quan tâm tại thời điểm', 'DEALER-001', 'LOST', NOW(), NOW());

-- Insert 25 LeadActivity records (5 for each Lead)
-- Lead ID 1
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (1, 'CALL', 'Gọi khách hàng để giới thiệu xe mới', 'sales_01', NOW() - INTERVAL '5 days', NOW()),
  (1, 'EMAIL', 'Gửi email danh sách xe được đề xuất', 'sales_01', NOW() - INTERVAL '4 days', NOW()),
  (1, 'NOTE', 'Khách hàng quan tâm về giá ưu đãi', 'sales_01', NOW() - INTERVAL '3 days', NOW()),
  (1, 'SMS', 'Gửi tin nhắn nhắc nhở về demo xe', 'sales_01', NOW() - INTERVAL '2 days', NOW()),
  (1, 'MEETING', 'Hẹn gặp tại showroom vào thứ 6', 'sales_01', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 2
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (2, 'EMAIL', 'Gửi thông tin chi tiết về Honda Accord', 'sales_02', NOW() - INTERVAL '4 days', NOW()),
  (2, 'CALL', 'Gọi xác nhận cuộc hẹn test drive', 'sales_02', NOW() - INTERVAL '3 days', NOW()),
  (2, 'MEETING', 'Khách hàng đến showroom test drive', 'sales_02', NOW() - INTERVAL '2 days', NOW()),
  (2, 'NOTE', 'Khách hàng hài lòng với xe, đang cân nhắc', 'sales_02', NOW() - INTERVAL '1 days', NOW()),
  (2, 'SMS', 'Gửi offer giá đặc biệt cho khách', 'sales_02', NOW(), NOW());

-- Lead ID 3
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (3, 'CALL', 'Gọi cung cấp báo giá chi tiết', 'sales_03', NOW() - INTERVAL '6 days', NOW()),
  (3, 'EMAIL', 'Gửi báo giá qua email', 'sales_03', NOW() - INTERVAL '5 days', NOW()),
  (3, 'NOTE', 'Khách hàng yêu cầu kiểm tra bảo hành', 'sales_03', NOW() - INTERVAL '3 days', NOW()),
  (3, 'MEETING', 'Khách hàng đến xem xe trực tiếp', 'sales_03', NOW() - INTERVAL '2 days', NOW()),
  (3, 'CALL', 'Tiếp tục thương thuyết giá', 'sales_03', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 4
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (4, 'EMAIL', 'Gửi hợp đồng mua bán', 'sales_04', NOW() - INTERVAL '5 days', NOW()),
  (4, 'CALL', 'Xác nhận chi tiết hợp đồng với khách', 'sales_04', NOW() - INTERVAL '4 days', NOW()),
  (4, 'MEETING', 'Khách hàng ký hợp đồng tại showroom', 'sales_04', NOW() - INTERVAL '3 days', NOW()),
  (4, 'NOTE', 'Hoàn tất thủ tục, giao xe thành công', 'sales_04', NOW() - INTERVAL '2 days', NOW()),
  (4, 'SMS', 'Gửi lời cảm ơn và thông tin bảo hành', 'sales_04', NOW() - INTERVAL '1 days', NOW());

-- Lead ID 5
INSERT INTO lead_activities (lead_id, activity_type, description, created_by, occurred_at, created_at)
VALUES 
  (5, 'CALL', 'Gọi tìm hiểu nhu cầu của khách', 'sales_05', NOW() - INTERVAL '10 days', NOW()),
  (5, 'EMAIL', 'Gửi thông tin sản phẩm', 'sales_05', NOW() - INTERVAL '8 days', NOW()),
  (5, 'SMS', 'Gửi khuyến mãi đặc biệt', 'sales_05', NOW() - INTERVAL '6 days', NOW()),
  (5, 'NOTE', 'Khách hàng không hứng thú, không liên hệ được', 'sales_05', NOW() - INTERVAL '5 days', NOW()),
  (5, 'CALL', 'Thử gọi lại một lần, khách từ chối', 'sales_05', NOW() - INTERVAL '3 days', NOW());

-- Indexes
CREATE INDEX IF NOT EXISTS idx_leads_dealership_id ON leads (dealership_id);
CREATE INDEX IF NOT EXISTS idx_leads_status ON leads (status);
CREATE INDEX IF NOT EXISTS idx_leads_created_at_desc ON leads (created_at DESC);
CREATE INDEX IF NOT EXISTS idx_lead_activities_lead_id ON lead_activities (lead_id);
