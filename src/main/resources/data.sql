
-- 插入供应商数据

INSERT INTO supplier (code, name, bank_account, partnership_case, attribute, mode, hotel, status, contact, position, telephone, salesman, contract_status, deal_date, start_date, end_date, remark)
VALUES
    ('S001', 'Supplier A', '123456789', 'Case A', 'Attribute A', 'Mode A', 'Hotel A', 'Active', 'John Doe', 'Manager', '123-456-7890', 'Sales A', 'Signed', '2024-01-01', '2024-01-15', '2025-01-15', 'Remark A'),
    ('S002', 'Supplier B', '987654321', 'Case B', 'Attribute B', 'Mode B', 'Hotel B', 'Active', 'Jane Smith', 'Assistant', '987-654-3210', 'Sales B', 'Pending', '2024-02-01', '2024-02-15', '2025-02-15', 'Remark B'),
    ('S003', 'Supplier C', '112233445', 'Case C', 'Attribute C', 'Mode C', 'Hotel C', 'Inactive', 'Alice Johnson', 'Coordinator', '112-233-4455', 'Sales C', 'Expired', '2024-03-01', '2024-03-15', '2025-03-15', 'Remark C'),
    ('S004', 'Supplier D', '556677889', 'Case D', 'Attribute D', 'Mode D', 'Hotel D', 'Active', 'Bob Brown', 'Director', '556-677-8899', 'Sales D', 'Signed', '2024-04-01', '2024-04-15', '2025-04-15', 'Remark D');
