ALTER TABLE eg_location ALTER COLUMN active TYPE boolean USING CASE WHEN active = 0 THEN FALSE WHEN active = 1 THEN TRUE ELSE FALSE END;
