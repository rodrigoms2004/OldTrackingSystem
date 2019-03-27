# Database VMSYS

# criando a base de dados
create database db_vmsys;
connect db_vmsys;

# criando usuário do banco de dados
grant all privileges on db_vmsys.* to 'vmsys'@'localhost' identified by 'vmsyspass';
flush privileges;

# dando permissão de acesso remoto
# liberar acesso remoto: editar arquivo vim /etc/my.conf e adicionar linha "bind-address = *"
grant all privileges on db_vmsys.* to 'vmsys'@'%' identified by 'vmsyspass';
flush privileges;
exit;

# Logando com o usuário da base de dados
mysql -u vmsys -pvmsyspass

# criando a tabela 
connect db_vmsys;
create table monitor(id integer auto_increment, latitude DOUBLE PRECISION, longitude DOUBLE PRECISION, timestp TIMESTAMP, RSSI INTEGER, primary key(id));

# inserindo dados
insert into monitor(latitude, longitude, timestp, RSSI) values (43.4567, 23.0978, now(), 21);

select * from monitor;

