## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE sfg_recipedb_dev_uder;
CREATE DATABASE sfg_recipedb_prod_user;

#Create database service accounts
CREATE USER 'sfg_recipedb_dev_uder'@'localhost' IDENTIFIED BY 'guru';
CREATE USER 'sfg_recipedb_prod_user'@'localhost' IDENTIFIED BY 'guru';
CREATE USER 'sfg_recipedb_dev_uder'@'%' IDENTIFIED BY 'guru';
CREATE USER 'sfg_recipedb_prod_user'@'%' IDENTIFIED BY 'guru';

#Database grants
GRANT SELECT ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'localhost';
GRANT INSERT ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'localhost';
GRANT DELETE ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'localhost';
GRANT UPDATE ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'localhost';
GRANT SELECT ON sfg_prod.* to 'sfg_recipedb_prod_user'@'localhost';
GRANT INSERT ON sfg_prod.* to 'sfg_recipedb_prod_user'@'localhost';
GRANT DELETE ON sfg_prod.* to 'sfg_recipedb_prod_user'@'localhost';
GRANT UPDATE ON sfg_prod.* to 'sfg_recipedb_prod_user'@'localhost';
GRANT SELECT ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'%';
GRANT INSERT ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'%';
GRANT DELETE ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'%';
GRANT UPDATE ON sfg_dev.* to 'sfg_recipedb_dev_uder'@'%';
GRANT SELECT ON sfg_prod.* to 'sfg_recipedb_prod_user'@'%';
GRANT INSERT ON sfg_prod.* to 'sfg_recipedb_prod_user'@'%';
GRANT DELETE ON sfg_prod.* to 'sfg_recipedb_prod_user'@'%';
GRANT UPDATE ON sfg_prod.* to 'sfg_recipedb_prod_user'@'%';