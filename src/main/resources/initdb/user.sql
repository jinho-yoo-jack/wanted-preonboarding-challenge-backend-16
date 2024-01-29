CREATE USER 'wanted'@'%' IDENTIFIED WITH mysql_native_password BY 'backend';
GRANT ALL PRIVILEGES ON *.* TO 'wanted'@'%';
FLUSH PRIVILEGES;
