/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
ALTER TABLE sch_proteus.requisitos
	ADD tipo varchar(10)

ALTER TABLE sch_proteus.reglas
	ADD tipo varchar(10)
GO


update sch_proteus.requisitos set tipo = 'M';
update sch_proteus.reglas set tipo = 'M';


ALTER TABLE sch_proteus.requisitos ALTER COLUMN tipo varchar(10) NOT NULL
ALTER TABLE sch_proteus.reglas ALTER COLUMN tipo varchar(10) NOT NULL
GO
