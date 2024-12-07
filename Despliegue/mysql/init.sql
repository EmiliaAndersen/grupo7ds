GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
ALTER USER 'root'@'%' IDENTIFIED BY '1234';
FLUSH PRIVILEGES;

-- habilito que le pueda consultar de cualquier lado
SET GLOBAL bind_address = '0.0.0.0';
