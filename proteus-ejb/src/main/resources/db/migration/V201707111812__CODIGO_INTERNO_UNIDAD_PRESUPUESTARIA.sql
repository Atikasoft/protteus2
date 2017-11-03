/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
ALTER TABLE sch_proteus.unidades_presupuestarias
	ADD codigo_interno varchar(30) NULL
GO


update up set up.codigo_interno = (
select c.num from (
select up2.id, ('UP_' + CONVERT(VARCHAR(12), ROW_NUMBER() OVER (ORDER BY up2.id) )) as num from sch_proteus.unidades_presupuestarias up2
) as c where c.id = up.id )
from sch_proteus.unidades_presupuestarias up;


ALTER TABLE sch_proteus.unidades_presupuestarias ALTER COLUMN codigo_interno varchar(30) NOT NULL
GO
