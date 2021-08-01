create table categoria (
	id bigint not null auto_increment,
    titulo varchar(30) not null,
    cor varchar(50) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE video
ADD COLUMN categoria_id BIGINT NOT NULL AFTER url,
ADD INDEX fk_video_categoria_id_idx (categoria_id ASC) VISIBLE;

ALTER TABLE aluraflixdb.video
ADD CONSTRAINT fk_video_categoria_id
  FOREIGN KEY (categoria_id)
  REFERENCES categoria (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;