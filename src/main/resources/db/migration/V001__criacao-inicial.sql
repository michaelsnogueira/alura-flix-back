create table video (
	id bigint not null auto_increment,
    titulo varchar(30) not null,
    descricao varchar(50) not null,
    url varchar(100) not null,

    primary key (id)
) engine=InnoDB default charset=utf8