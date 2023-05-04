CREATE OR REPLACE FUNCTION transfer_data_to_top()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO top (id, country, email, is_deleted, name, passport_id, gender)
VALUES (NEW.id, NEW.country, NEW.email, NEW.is_deleted, NEW.name, NEW.passport_id, NEW.gender);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER insert_data_to_top
    AFTER INSERT ON users
    FOR EACH ROW
    EXECUTE FUNCTION transfer_data_to_top();