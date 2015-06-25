/****** Object:  StoredProcedure [dbo].[setLog]    Script Date: 6/24/2015 7:49:33 PM ******/
DROP PROCEDURE [dbo].[setLog]
GO

/****** Object:  StoredProcedure [dbo].[setLog]    Script Date: 6/24/2015 7:49:33 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[setLog] 
       @log bit
	  ,@UserID int
AS 

SET NOCOUNT ON

BEGIN 

UPDATE
	_Users set login = @log
WHERE
	UserID = @UserID

END

GO
-- SIGUIENTE ---

/****** Object:  StoredProcedure [dbo].[stp_verficarProductoUsuario]    Script Date: 6/24/2015 8:01:56 PM ******/
DROP PROCEDURE [dbo].[stp_verficarProductoUsuario]
GO

/****** Object:  StoredProcedure [dbo].[stp_verficarProductoUsuario]    Script Date: 6/24/2015 8:01:56 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_verficarProductoUsuario]
@codigoP varchar(50),
@userId int
AS
SELECT * from in_tomafisica_detDis where usuarioId=@userId and codigo_producto=@codigoP
GO

-- SIGUIENTE --
/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto]    Script Date: 6/24/2015 8:02:56 PM ******/
DROP PROCEDURE [dbo].[stp_UDinActualizaProducto]
GO

/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto]    Script Date: 6/24/2015 8:02:56 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE procedure [dbo].[stp_UDinActualizaProducto]
   @no_toma int,
   @codigo_bodega char(2),
   @codigo_producto char(35),
   @unidad_medida char(2),
   @cantidad decimal(22,4),
   @error varchar(100),
   @seccion_Id int = null ,
   @estanteria_id int = null,
   @usuarioId int = null
as
--------------------------------------
-- autor : rsao
-- fecha : 15-06-2015
-- Cambio : Se agregaron valores default nulos para compatabiliad con la version anterior 
--------------------------------------
--------------------------------------
-- autor : rsao
-- fecha : 02-06-2015
-- Cambio : Se agregaron datos adicionales (estanteria,ubicacion,usuario) 
--------------------------------------
--------------------------------------
-- autor : lsao
-- fecha : 01-06-2006
-- Cambio de existencias 
--------------------------------------
-- autor : lsao
-- fecha : 22-05-2006
-- Toma fisica de inventario, inserta productos que no existen o actualiza la cantidad 
---------------------------------------
Set nocount on


declare @codigo_marca varchar(10),
        @codigo_familia varchar(10), 
        @terminal int

		
   
		

if not exists ( select 1 from in_bodegas where codigo_bodega = @codigo_bodega )
begin
   raiserror ('Bodega  no existe - stp_UDinActualizaProducto' ,16,1,5000)
   return
end


if not exists ( select 1 from in_productos where codigo_producto = @codigo_producto )
begin
   raiserror ('Producto no existe - stp_UDinActualizaProducto' ,16,1,5000)
   return
end


if not exists ( select 1 from in_unidades_medida where codigo_producto = @codigo_producto
                           and unidad_medida = @unidad_medida )
begin
   raiserror ('Unidad Medida no existe - stp_UDinActualizaProducto' ,16,1,5000)
   return
end


Begin Tran
If (@seccion_Id is null and @estanteria_id is null)
Begin 
		if exists ( select 1 from in_tomafisica_det 
				   where no_toma = @no_toma
					 and ltrim(rtrim(codigo_bodega)) = ltrim(rtrim(@codigo_bodega))
					 and ltrim(rtrim(codigo_producto)) = ltrim(rtrim(@codigo_producto))
					 )
		begin
		   update in_tomafisica_det
			 set conteo1 = @cantidad
		   where no_toma = @no_toma
			 and codigo_bodega = @codigo_bodega
			 and codigo_producto = @codigo_producto
			  

		   if @@error <> 0
		   begin
			  raiserror ( 'Cantidad no pudo ser actualizada - stp_UDinActualizaProducto' ,16,1,5000)
			  rollback work
			  return
		   end
		end
		else
		begin
		   select @codigo_marca = codigo_marca,
				  @codigo_familia = codigo_familia
		   from in_productos
		   where codigo_producto = @codigo_producto

   
		   insert into in_tomafisica_det ( 
			 no_toma,
			 codigo_bodega,
			 codigo_producto,
			 unidad_medida,
			 conteo1,
			 observaciones,
			 codigo_marca,
			 codigo_familia,
			 seccion_Id,
			 estanteria_id,
			 usuarioId,
			 muestras )
			values ( @no_toma,
					 @codigo_bodega,
					 @codigo_producto,
					 @unidad_medida,
					 @cantidad,
					 'Insertado manualmente ',
					 @codigo_marca ,
					 @codigo_familia,
					 @seccion_Id ,
					 @estanteria_id ,
					 @usuarioId,
					 0 )

			if @@error <> 0
			begin
			   raiserror ('No pudo ser insertado el producto - stp_UDinAcualizaProducto ' ,16,1,5000)
			   rollback work
			   return
			end
		end

End

if not (@seccion_Id is null and @estanteria_id is null)
begin
	if exists ( select 1 from in_tomafisica_detdis
			   where no_toma = @no_toma
				 and ltrim(rtrim(codigo_bodega)) = ltrim(rtrim(@codigo_bodega))
				 and ltrim(rtrim(codigo_producto)) = ltrim(rtrim(@codigo_producto))
				 and seccion_id = @seccion_id
				 and estanteria_id = @estanteria_id)
	begin
	   update in_tomafisica_detdis
		 set conteo1 = @cantidad
	   where no_toma = @no_toma
		 and codigo_bodega = @codigo_bodega
		 and codigo_producto = @codigo_producto
		 and seccion_id = @seccion_id
		 and estanteria_id = @estanteria_id 

	   if @@error <> 0
	   begin
		  raiserror ( 'Cantidad no pudo ser actualizada - stp_UDinActualizaProducto' ,16,1,5000)
		  rollback work
		  return
	   end
	end
	else
	begin
	   select @codigo_marca = codigo_marca,
			  @codigo_familia = codigo_familia
	   from in_productos
	   where codigo_producto = @codigo_producto

   
	   insert into in_tomafisica_detdis ( 
		 no_toma,
		 codigo_bodega,
		 codigo_producto,
		 unidad_medida,
		 conteo1,
		 observaciones,
		 codigo_marca,
		 codigo_familia,
		 seccion_Id,
		 estanteria_id,
		 usuarioId,
		 muestras )
		values ( @no_toma,
				 @codigo_bodega,
				 @codigo_producto,
				 @unidad_medida,
				 @cantidad,
				 'Insertado manualmente ',
				 @codigo_marca ,
				 @codigo_familia,
				 @seccion_Id ,
				 @estanteria_id ,
				 @usuarioId,
				 0 )

		if @@error <> 0
		begin
		   raiserror ('No pudo ser insertado el producto - stp_UDinAcualizaProducto ' ,16,1,5000)
		   rollback work
		   return
		end
	end

end

Commit Tran

select 1 dato

GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto2]    Script Date: 6/24/2015 8:04:40 PM ******/
DROP PROCEDURE [dbo].[stp_UDinActualizaProducto2]
GO

/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto2]    Script Date: 6/24/2015 8:04:40 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE procedure [dbo].[stp_UDinActualizaProducto2]
   @no_toma int,
   @codigo_bodega char(2),
   @codigo_producto char(35),
   @unidad_medida char(2),
   @cantidad decimal(22,4),
   @error varchar(100),
   @seccion_Id int = null,
   @estanteria_id int = null,
   @usuarioId int = null
as
--------------------------------------
-- autor : rsao
-- fecha : 02-06-2015
-- Cambio : Se agregaron datos adicionales (estanteria,ubicacion,usuario) 
--------------------------------------
--------------------------------------
-- autor : lsao
-- fecha : 26-05-2006
-- Toma fisica de inventario, inserta productos que no existen o actualiza la cantidad en el conteo 2
---------------------------------------
Set nocount on

declare @codigo_marca varchar(10),
        @codigo_familia varchar(10)

if not exists ( select 1 from in_bodegas where codigo_bodega = @codigo_bodega )
begin
   raiserror ('Bodega  no existe - stp_UDinActualizaProducto2' ,16,1,5000)
   return
end


if not exists ( select 1 from in_productos where codigo_producto = @codigo_producto )
begin
   raiserror ('Producto no existe - stp_UDinActualizaProducto2' ,16,1,5000)
   return
end


if not exists ( select 1 from in_unidades_medida where codigo_producto = @codigo_producto
                           and unidad_medida = @unidad_medida )
begin
   raiserror ('Unidad Medida no existe - stp_UDinActualizaProducto2' ,16,1,5000)
   return
end


Begin Tran
If ( @seccion_Id is null and @estanteria_id is null)
Begin 
		if exists ( select 1 from in_tomafisica_det 
				   where no_toma = @no_toma
					 and codigo_bodega = @codigo_bodega
					 and codigo_producto = @codigo_producto
					 )  
		begin
		   update in_tomafisica_det
			 set conteo2 = @cantidad
		   where no_toma = @no_toma
			 and codigo_bodega = @codigo_bodega
			 and codigo_producto = @codigo_producto
			
		   if @@error <> 0
		   begin
			  raiserror ( 'Cantidad no pudo ser actualizada - stp_UDinActualizaProducto2' ,16,1,5000)
			  rollback work
			  return
		   end
		end
		else
		begin
		   select @codigo_marca = codigo_marca,
				  @codigo_familia = codigo_familia
		   from in_productos
		   where codigo_producto = @codigo_producto

   
		   insert into in_tomafisica_det ( 
			 no_toma,
			 codigo_bodega,
			 codigo_producto,
			 unidad_medida,
			 conteo1,
			 conteo2,
			 observaciones,
			 codigo_marca,
			 codigo_familia ,
			 seccion_Id,
			 estanteria_id,
			 usuarioId,
			 muestras )
			values ( @no_toma,
					 @codigo_bodega,
					 @codigo_producto,
					 @unidad_medida,
					 0.00,
					 @cantidad,
					 'Insertado manualmente ',
					 @codigo_marca ,
					 @codigo_familia ,
					 @seccion_Id,
					 @estanteria_id,
					 @usuarioId,
					 0 )

			if @@error <> 0
			begin
			   raiserror ('No pudo ser insertado el producto - stp_UDinAcualizaProducto2 ' ,16,1,5000)
			   rollback work
			   return
			end
		end

End

If (@seccion_Id is not null and @estanteria_id is not null)
Begin

	if exists ( select 1 from in_tomafisica_detdis
			   where no_toma = @no_toma
				 and codigo_bodega = @codigo_bodega
				 and codigo_producto = @codigo_producto
				 and seccion_id = @seccion_id
				 and estanteria_id = @estanteria_id)
	begin
	   update in_tomafisica_detdis
		 set conteo2 = @cantidad
	   where no_toma = @no_toma
		 and codigo_bodega = @codigo_bodega
		 and codigo_producto = @codigo_producto
		 and seccion_id = @seccion_id
		 and estanteria_id = @estanteria_id 

	   if @@error <> 0
	   begin
		  raiserror ( 'Cantidad no pudo ser actualizada - stp_UDinActualizaProducto2' ,16,1,5000)
		  rollback work
		  return
	   end
	end
	else
	begin
	   select @codigo_marca = codigo_marca,
			  @codigo_familia = codigo_familia
	   from in_productos
	   where codigo_producto = @codigo_producto

   
	   insert into in_tomafisica_detdis ( 
		 no_toma,
		 codigo_bodega,
		 codigo_producto,
		 unidad_medida,
		 conteo1,
		 conteo2,
		 observaciones,
		 codigo_marca,
		 codigo_familia ,
		 seccion_Id,
		 estanteria_id,
		 usuarioId,
		 muestras )
		values ( @no_toma,
				 @codigo_bodega,
				 @codigo_producto,
				 @unidad_medida,
				 0.00,
				 @cantidad,
				 'Insertado manualmente ',
				 @codigo_marca ,
				 @codigo_familia ,
				 @seccion_Id,
				 @estanteria_id,
				 @usuarioId,
				 0 )

		if @@error <> 0
		begin
		   raiserror ('No pudo ser insertado el producto - stp_UDinAcualizaProducto2 ' ,16,1,5000)
		   rollback work
		   return
		end
	end
end

Commit Tran

select 1 dato

GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_obtenerDigitos]    Script Date: 6/24/2015 8:06:34 PM ******/
DROP PROCEDURE [dbo].[stp_obtenerDigitos]
GO

/****** Object:  StoredProcedure [dbo].[stp_obtenerDigitos]    Script Date: 6/24/2015 8:06:34 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE  PROCEDURE [dbo].[stp_obtenerDigitos]
@codigoP varchar(50)

AS

select dbo.replicacionCero_productos(@codigoP) As Codigo

GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_ProductoAutoCompletacion]    Script Date: 6/24/2015 8:07:49 PM ******/
DROP PROCEDURE [dbo].[stp_ProductoAutoCompletacion]
GO

/****** Object:  StoredProcedure [dbo].[stp_ProductoAutoCompletacion]    Script Date: 6/24/2015 8:07:49 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[stp_ProductoAutoCompletacion]

@codigoP varchar(50)
AS

SELECT 
	  inp.codigo_producto
	, inp.descripcion_larga
	, inu.descripcion
	, inp.unidad_inventario
	, ind.estanteria_ID
	, ind.seccion_ID
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu ON inp.unidad_inventario=inu.unidad_medida
 INNER JOIN in_tomafisica_det AS ind ON ind.codigo_producto = inp.codigo_producto
WHERE 
    inp.codigo_producto = @codigoP
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoCodigo]    Script Date: 6/24/2015 8:11:20 PM ******/
DROP PROCEDURE [dbo].[stp_CoincidenciasProductoCodigo]
GO

/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoCodigo]    Script Date: 6/24/2015 8:11:20 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[stp_CoincidenciasProductoCodigo]

	@codigo_producto char(10)

AS

SELECT 
   inp.codigo_producto
  ,inp.descripcion_larga
  ,inu.descripcion
  ,inp.unidad_inventario
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu
 ON inp.unidad_inventario=inu.unidad_medida
WHERE 
	   inp.codigo_producto = @codigo_producto
   --AND Activo = 1
GO

-- SIGUIENTE --
/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoDescripcion]    Script Date: 6/24/2015 8:12:10 PM ******/
DROP PROCEDURE [dbo].[stp_CoincidenciasProductoDescripcion]
GO

/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoDescripcion]    Script Date: 6/24/2015 8:12:10 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[stp_CoincidenciasProductoDescripcion]

	@descripcion varchar(250)

AS

SELECT 
    inp.codigo_producto
  , inp.descripcion_larga
  , inu.descripcion
  , inp.unidad_inventario
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu
 ON inp.unidad_inventario=inu.unidad_medida
WHERE 
	    inp.descripcion_larga like '%'+ @descripcion +'%'
	--AND Activo = 1

GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_CantidadProductoActual]    Script Date: 6/24/2015 8:13:54 PM ******/
DROP PROCEDURE [dbo].[stp_CantidadProductoActual]
GO

/****** Object:  StoredProcedure [dbo].[stp_CantidadProductoActual]    Script Date: 6/24/2015 8:13:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_CantidadProductoActual]
@codigoP varchar(50),
@seccionID int,
@estanteriaID int,
@bodegaID int
AS
DECLARE 
@codigoC varchar(50)

select @codigoC =  dbo.replicacionCero_productos(@codigoP)

SELECT
	 conteo1,conteo2
	from
	 in_tomafisica_detDis
	where 
		 codigo_producto = @codigoC
	 and seccion_ID = @seccionID 
	 and estanteria_ID = @estanteriaID
	 and codigo_bodega = @bodegaID
	 
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_buscaEstanteria]    Script Date: 6/24/2015 8:17:57 PM ******/
DROP PROCEDURE [dbo].[stp_buscaEstanteria]
GO

/****** Object:  StoredProcedure [dbo].[stp_buscaEstanteria]    Script Date: 6/24/2015 8:17:57 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[stp_buscaEstanteria] 

AS 

SELECT Estanteria_Id , descripcion 
FROM 
in_estanteria
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_buscaseccion]    Script Date: 6/24/2015 8:19:22 PM ******/
DROP PROCEDURE [dbo].[stp_buscaseccion]
GO

/****** Object:  StoredProcedure [dbo].[stp_buscaseccion]    Script Date: 6/24/2015 8:19:22 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_buscaseccion]

AS 

SELECT Seccion_Id, descripcion FROM in_seccionestanteria
GO 

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductos]    Script Date: 6/24/2015 8:21:08 PM ******/
DROP PROCEDURE [dbo].[stp_ConsultaProductos]
GO

/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductos]    Script Date: 6/24/2015 8:21:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_ConsultaProductos]   
@usuarioId int ,
@noToma int
AS

SELECT 
	t.no_toma
  ,t.codigo_producto
  ,p.descripcion_larga
  ,t.codigo_bodega
  ,t.conteo1
  ,t.conteo2
  ,t.seccion_ID
  ,t.estanteria_ID
  ,u.descripcion unidad
  ,e.descripcion estanteria
  ,s.descripcion seccion
  ,b.descripcion bodega
FROM 
 in_tomafisica_detDis as t
 INNER JOIN in_productos as p on t.codigo_producto = p.codigo_producto
 INNER JOIN in_bodegas as b on t.codigo_bodega = b.codigo_bodega
 INNER JOIN in_unidades as u on t.unidad_medida = u.unidad_medida
 INNER JOIN in_estanteria as e on t.estanteria_ID = e.Estanteria_Id
 INNER JOIN in_seccionestanteria as s on t.seccion_ID = s.Seccion_Id
WHERE
 t.usuarioId = @usuarioId
 AND t.no_toma = @noToma

 ORDER BY
	t.codigo_producto
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductosAuditor]    Script Date: 6/24/2015 8:21:53 PM ******/
DROP PROCEDURE [dbo].[stp_ConsultaProductosAuditor]
GO

/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductosAuditor]    Script Date: 6/24/2015 8:21:53 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[stp_ConsultaProductosAuditor] --stp_ConsultaProductosAuditor   1,608

@noToma int
AS

SELECT 
	 t.no_toma
	,t.codigo_producto
	,p.descripcion_larga
	,t.codigo_bodega
	,t.conteo1
	,t.conteo2
	,t.seccion_ID
	,t.estanteria_ID
	,u.descripcion unidad
	,e.descripcion estanteria
	,s.descripcion seccion
	,b.descripcion bodega
	,us.UserName
	,t.fecha_toma
FROM 
			in_tomafisica_detDis as t
 INNER JOIN in_productos as p on t.codigo_producto = p.codigo_producto
 INNER JOIN in_bodegas as b on t.codigo_bodega = b.codigo_bodega
 INNER JOIN in_unidades as u on t.unidad_medida = u.unidad_medida
 INNER JOIN in_estanteria as e on t.estanteria_ID = e.Estanteria_Id
 INNER JOIN in_seccionestanteria as s on t.seccion_ID = s.Seccion_Id
 INNER JOIN _Users as us on t.usuarioId = us.UserID
WHERE
 ----t.usuarioId = @usuarioId
 t.no_toma = @noToma
 
 ORDER BY
	t.codigo_producto
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_Seleccionartomas]    Script Date: 6/24/2015 8:23:37 PM ******/
DROP PROCEDURE [dbo].[stp_Seleccionartomas]
GO

/****** Object:  StoredProcedure [dbo].[stp_Seleccionartomas]    Script Date: 6/24/2015 8:23:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[stp_Seleccionartomas]

AS

SELECT no_toma, observaciones, estado_toma FROM in_tomafisica_enc WHERE estado_toma=3   OR estado_toma=2
GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[stp_cargarProductosDiferencia]    Script Date: 6/24/2015 8:26:15 PM ******/
DROP PROCEDURE [dbo].[stp_cargarProductosDiferencia]
GO

/****** Object:  StoredProcedure [dbo].[stp_cargarProductosDiferencia]    Script Date: 6/24/2015 8:26:15 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_cargarProductosDiferencia] --stp_cargarProductosDiferencia '000783','03'

@codProd varchar(50),
@bodegaID char(2)

AS

SELECT ind.codigo_producto, inp.descripcion_larga, ind.estanteria_ID, ind.seccion_ID, ind.codigo_bodega, ind.conteo2, inu.descripcion 
FROM in_tomafisica_detDis AS ind 
INNER JOIN in_productos AS inp on ind.codigo_producto = inp.codigo_producto
INNER JOIN in_unidades AS inu on inu.unidad_medida = ind.unidad_medida
WHERE ind.codigo_producto = @codProd AND ind.codigo_bodega = @bodegaID

ORDER BY 
ind.codigo_producto
GO

-- SIGUIENTE -- 

/****** Object:  StoredProcedure [dbo].[looginSelectUser]    Script Date: 6/24/2015 8:29:20 PM ******/
DROP PROCEDURE [dbo].[looginSelectUser]
GO

/****** Object:  StoredProcedure [dbo].[looginSelectUser]    Script Date: 6/24/2015 8:29:20 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[looginSelectUser] 
      @usuario nvarchar(50)
AS 
BEGIN 

SELECT 
	 [UserID]
	,[UserName]
	,[Password]
	,[estado_empleado]
	,[fecha_exprira]
	,[caduca_clave]
	,[Bloqueado]
	,[Intentos]
	,[login]
FROM 
	_Users 
WHERE 
	UserName = @usuario

END 

GO

-- SIGUIENTE --

/****** Object:  StoredProcedure [dbo].[Stp_UDintomatabular]    Script Date: 6/24/2015 8:32:00 PM ******/
DROP PROCEDURE [dbo].[Stp_UDintomatabular]
GO

/****** Object:  StoredProcedure [dbo].[Stp_UDintomatabular]    Script Date: 6/24/2015 8:32:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE Procedure [dbo].[Stp_UDintomatabular]

@toma1 as integer,
@toma2 as integer

as
-- Cambio: Rsao
-- Fecha: 14/10/2010
-- Cambios: Se agrego case para el costo para no dejar el costo en Cero,
--           1. costo_unitario, 2.ultimo_costo 3.costo estandar
-- Cambio hecho por lsao
-- Fecha : 15-01-2007
-- Estandares 

set nocount on

Select codigo_producto, Max(anomes) Anomes
into #Costos
from in_existencias_mensuales
group by codigo_producto


select 	a.codigo_bodega,
	a.codigo_producto,
	a.codigo_familia,
	a.codigo_marca,
	--b.ultimo_costo_local as costo,
	isnull(a.teorico,0)  as teorico,
	
	isnull(a.reservado,0) as reservado,
	isnull(a.teorico,0) as existencias,
	Case when a.conteo2 is not null then a.conteo2 else a.conteo1 end conteo,
	--Case when a.conteo2 is not null then (a.teorico+ a.reservado) - a.conteo2 else (a.teorico + a.reservado) - a.conteo1 end diferencia,
	Case when a.conteo2 is not null then (a.teorico)-a.conteo2 else (a.teorico)-a.conteo1 end diferencia,
	f.anomes
	--isnull(a.teorico,0)+isnull(a.reservado,0)-a.ultimo_conteo as diferencia
into #Tabular
from 	in_tomafisica_det a,
	#costos f
where	a.no_toma between @toma1 and @toma2 and
	a.codigo_producto *= f.codigo_producto
order by a.codigo_producto


select  a.codigo_bodega,
	a.codigo_producto,
	a.codigo_familia,
	a.codigo_marca,
	a.teorico,
	a.reservado,
	a.existencias,
	a.conteo,
	a.diferencia,
	case				-- Agregado Rsao 14/10/2010
	     when b.costo_unitario is not null and b.costo_unitario <> 0 then b.costo_unitario
         when b.costo_ultimo is not null and b.costo_ultimo <> 0	  then b.costo_ultimo
         when b.costo_estandar is not null and  b.costo_estandar <> 0 then b.costo_estandar 
         else 0
	end as cestandar ,
    a.diferencia * b.costo_unitario as ctotal
into #tabular2
from #tabular a, in_existencias_mensuales b
where a.codigo_producto *= b.codigo_producto and
	a.anomes *= b.anomes


select a.codigo_bodega +' - '+ b.descripcion as bodega,
	ltrim(rtrim(a.codigo_producto)) + ' - ' + c.descripcion as producto,
	d.descripcion as familia,
	e.descripcion as marca,
	c.ultimo_costo_local as costo,
	a.teorico, a.reservado, a.existencias, a.conteo, a.diferencia,
             case when isnull(a.cestandar,0) = 0 then  case			-- Agregado Rsao 14/10/2010
															when isnull(c.ultimo_costo_local,0) = 0 then  c.costo_estandar
													        else c.ultimo_costo_local
													   end 
                  else cestandar 
              end cestandar,
             case when isnull(a.cestandar,0) = 0 then  case         -- Agregado Rsao 14/10/2010
													        when isnull(c.ultimo_costo_local * a.diferencia,0) = 0 then c.costo_estandar * a.diferencia   
													        else c.ultimo_costo_local * a.diferencia
													   end
                  else cestandar * a.diferencia 
              end ctotal
from #tabular2 a, in_bodegas b, in_productos c, in_familias d, in_marcas e
where diferencia <> 0 and
	--a.existencias<>0 and -- Reporte SAT
	a.codigo_bodega = b.codigo_bodega and
	a.codigo_producto = c.codigo_producto and
	a.codigo_familia = d.codigo_familia and
	a.codigo_marca = e.codigo_marca
GO

-- SIGUIENTE -- 
/****** Object:  StoredProcedure [dbo].[stp_udtf_consultabodega]    Script Date: 6/24/2015 8:33:15 PM ******/
DROP PROCEDURE [dbo].[stp_udtf_consultabodega]
GO

/****** Object:  StoredProcedure [dbo].[stp_udtf_consultabodega]    Script Date: 6/24/2015 8:33:15 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

---------------------------------------------------------------------------------------
-- Autor: Raul Sao
-- Fecha: 15/06/2015
-- Observaciones:  Busqueda de bodega por numero de toma, verificando la bodega activa
--------------------------------------------------------------------------------------
create PROCEDURE [dbo].[stp_udtf_consultabodega]
  @no_toma int
AS 
BEGIN 

Set nocount on

SELECT distinct
     b.codigo_bodega ,
     b.descripcion
FROM 
     in_bodegas b
	 inner join in_tomafisica_det t on b.codigo_bodega = t.codigo_bodega
where b.Activo = 1
      and t.no_toma = @no_toma

END


GO
