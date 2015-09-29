/**PROCEDIMIENTOS DE TOMA DE INVENTARIO**/

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_obtenerID'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
DROP PROCEDURE [dbo].[stp_obtenerID]
GO

/****** Object:  StoredProcedure [dbo].[stp_obtenerID]    Script Date: 9/4/2015 1:48:34 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_obtenerID] --stp_obtenerID '000091'
@codigoP varchar(10),
@bodega varchar(2),
@estanteria varchar(50),
@seccion varchar(50)
as

select ind.codigo_producto, ind.estanteria_ID, ind.seccion_ID, ind.unidad_medida from in_tomafisica_detDis as ind
INNER JOIN in_estanteria as e on e.descripcion = @estanteria
INNER JOIN in_seccionestanteria as s on s.descripcion = @seccion

WHERE ind.codigo_producto = @codigoP and e.Estanteria_Id = ind.estanteria_ID and s.Seccion_Id = ind.seccion_ID
GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_UDinActualizaProducto2'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto2]    Script Date: 9/4/2015 1:51:01 PM ******/
DROP PROCEDURE [dbo].[stp_UDinActualizaProducto2]
GO

/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto2]    Script Date: 9/4/2015 1:51:01 PM ******/
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

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_UDinActualizaProducto'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto]    Script Date: 9/4/2015 1:53:30 PM ******/
DROP PROCEDURE [dbo].[stp_UDinActualizaProducto]
GO

/****** Object:  StoredProcedure [dbo].[stp_UDinActualizaProducto]    Script Date: 9/4/2015 1:53:30 PM ******/
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




/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_ProductoAutoCompletacion'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_ProductoAutoCompletacion]    Script Date: 9/4/2015 1:58:38 PM ******/
DROP PROCEDURE [dbo].[stp_ProductoAutoCompletacion]
GO

/****** Object:  StoredProcedure [dbo].[stp_ProductoAutoCompletacion]    Script Date: 9/4/2015 1:58:38 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_ProductoAutoCompletacion]

@codigoP varchar(10),
@seccionID int,
@bodegaID int,
@estanteriaID int
AS

DECLARE @codigoC varchar(10)
select @codigoC =  isnull(dbo.replicacionCero_productos(@codigoP),@codigoP)

SELECT 
	  inp.codigo_producto
	, inp.descripcion_larga
	, inu.descripcion
	, inp.unidad_inventario
	, ind.conteo1
	, ind.conteo2
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu ON inp.unidad_inventario=inu.unidad_medida
 LEFT JOIN in_tomafisica_detDis AS ind ON inp.codigo_producto=ind.codigo_producto and
                                          ind.codigo_bodega = @bodegaID AND
										  ind.seccion_ID = @seccionID AND 
				                          ind.estanteria_ID = @estanteriaID
WHERE 
    inp.codigo_producto = @codigoC 

GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_CoincidenciasProductoCodigo'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoCodigo]    Script Date: 9/4/2015 2:00:50 PM ******/
DROP PROCEDURE [dbo].[stp_CoincidenciasProductoCodigo]
GO

/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoCodigo]    Script Date: 9/4/2015 2:00:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[stp_CoincidenciasProductoCodigo] --stp_CoincidenciasProductoCodigo '9','03',

	@codigoP char(10),
	@codigoB char(2),
	@codigoE int,
	@codigoS int

AS
DECLARE @codigoC varchar(10)
select @codigoC =  isnull(dbo.replicacionCero_productos(@codigoP),@codigoP)
SELECT 
	  inp.codigo_producto
	, inp.descripcion_larga
	, inu.descripcion
	, inp.unidad_inventario
	, ind.conteo1
	, ind.conteo2
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu ON inp.unidad_inventario=inu.unidad_medida
 LEFT JOIN in_tomafisica_detDis AS ind ON inp.codigo_producto=ind.codigo_producto and
                                          ind.codigo_bodega = @codigoB AND
										  ind.seccion_ID = @codigoS AND 
				                          ind.estanteria_ID = @codigoE
WHERE 
    inp.codigo_producto = @codigoC 	   
   --AND Activo = 1

GO

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_CoincidenciasProductoDescripcion'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoDescripcion]    Script Date: 9/4/2015 2:02:47 PM ******/
DROP PROCEDURE [dbo].[stp_CoincidenciasProductoDescripcion]
GO

/****** Object:  StoredProcedure [dbo].[stp_CoincidenciasProductoDescripcion]    Script Date: 9/4/2015 2:02:47 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[stp_CoincidenciasProductoDescripcion]

	@descripcion varchar(250),
	@codigoB char(2),
	@codigoE int,
	@codigoS int

AS

SELECT 
	  inp.codigo_producto
	, inp.descripcion_larga
	, inu.descripcion
	, inp.unidad_inventario
	, ind.conteo1
	, ind.conteo2
FROM
 in_productos AS inp
 INNER JOIN in_unidades AS inu ON inp.unidad_inventario=inu.unidad_medida
 LEFT JOIN in_tomafisica_detDis AS ind ON inp.codigo_producto=ind.codigo_producto and
                                          ind.codigo_bodega = @codigoB AND
										  ind.seccion_ID = @codigoS AND 
				                          ind.estanteria_ID = @codigoE

WHERE 
	    inp.descripcion_larga like '%'+ @descripcion +'%'
	--AND Activo = 1


GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_buscaseccion'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_buscaseccion]    Script Date: 9/4/2015 2:04:06 PM ******/
DROP PROCEDURE [dbo].[stp_buscaseccion]
GO

/****** Object:  StoredProcedure [dbo].[stp_buscaseccion]    Script Date: 9/4/2015 2:04:06 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[stp_buscaseccion]
@estanteriaID int = null
AS 

SELECT Seccion_Id, descripcion FROM in_seccionestanteria

--where Seccion_Id = @estanteriaID
--stp_buscaseccion
GO

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_obtenerDigitos'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_obtenerDigitos]    Script Date: 9/4/2015 2:07:31 PM ******/
DROP PROCEDURE [dbo].[stp_obtenerDigitos]
GO

/****** Object:  StoredProcedure [dbo].[stp_obtenerDigitos]    Script Date: 9/4/2015 2:07:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE  PROCEDURE [dbo].[stp_obtenerDigitos]
@codigoP varchar(50)

AS

select dbo.replicacionCero_productos(@codigoP) As Codigo


GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_CantidadProductoActual'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_CantidadProductoActual]    Script Date: 9/4/2015 2:09:03 PM ******/
DROP PROCEDURE [dbo].[stp_CantidadProductoActual]
GO

/****** Object:  StoredProcedure [dbo].[stp_CantidadProductoActual]    Script Date: 9/4/2015 2:09:03 PM ******/
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

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_buscaEstanteria'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_buscaEstanteria]    Script Date: 9/4/2015 2:10:14 PM ******/
DROP PROCEDURE [dbo].[stp_buscaEstanteria]
GO

/****** Object:  StoredProcedure [dbo].[stp_buscaEstanteria]    Script Date: 9/4/2015 2:10:14 PM ******/
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

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_ConsultaProductos'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductos]    Script Date: 9/4/2015 2:11:17 PM ******/
DROP PROCEDURE [dbo].[stp_ConsultaProductos]
GO

/****** Object:  StoredProcedure [dbo].[stp_ConsultaProductos]    Script Date: 9/4/2015 2:11:17 PM ******/
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

/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_Seleccionartomas'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_Seleccionartomas]    Script Date: 9/4/2015 2:13:00 PM ******/
DROP PROCEDURE [dbo].[stp_Seleccionartomas]
GO

/****** Object:  StoredProcedure [dbo].[stp_Seleccionartomas]    Script Date: 9/4/2015 2:13:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


create PROCEDURE [dbo].[stp_Seleccionartomas]

AS

SELECT no_toma, observaciones, estado_toma FROM in_tomafisica_enc WHERE estado_toma=3   OR estado_toma=2

GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = '_OpcionPermisos'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[_OpcionPermisos]    Script Date: 9/4/2015 2:14:01 PM ******/
DROP PROCEDURE [dbo].[_OpcionPermisos]
GO

/****** Object:  StoredProcedure [dbo].[_OpcionPermisos]    Script Date: 9/4/2015 2:14:01 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE Procedure [dbo].[_OpcionPermisos] --_OpcionPermisos 'inventario',1
 @NombrePantalla varchar(50),
 @UserId int
As

set nocount on 

Select isnull(Agregar,0) Agregar, isnull(Modificar,0) Modificar, 
       isnull(eliminar,0) eliminar, isnull(ejecutar,0) ejecutar 
   from _opcionesgroups
  where opcionid = (Select OpcionId 
                      from _opciones
					  where nombreobjeto = @NombrePantalla)
		and UserGroupId in (Select UserGroupID
		                      from _UserGroups 
		                      where userid = @userid)

Return

GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'stp_cargarProductosDiferencia'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[stp_cargarProductosDiferencia]    Script Date: 9/4/2015 2:15:13 PM ******/
DROP PROCEDURE [dbo].[stp_cargarProductosDiferencia]
GO

/****** Object:  StoredProcedure [dbo].[stp_cargarProductosDiferencia]    Script Date: 9/4/2015 2:15:13 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[stp_cargarProductosDiferencia] --stp_cargarProductosDiferencia '000045','08'

@codProd varchar(50),
@bodegaID char(2)

AS

DECLARE
@codigoC varchar(50)

select @codigoC =  dbo.replicacionCero_productos(@codProd)

SELECT ind.codigo_producto, inp.descripcion_larga, ind.estanteria_ID, e.descripcion Estanteria, ind.seccion_ID, s.descripcion Seccion, ind.codigo_bodega, ind.conteo2, inu.descripcion 
FROM in_tomafisica_detDis AS ind 
INNER JOIN in_productos AS inp on ind.codigo_producto = inp.codigo_producto
INNER JOIN in_unidades AS inu on inu.unidad_medida = ind.unidad_medida
INNER JOIN in_estanteria as e on ind.estanteria_ID = e.Estanteria_Id
INNER JOIN in_seccionestanteria as s on ind.seccion_ID = s.Seccion_Id


WHERE ind.codigo_producto = @codigoC AND ind.codigo_bodega = @bodegaID

ORDER BY 
ind.codigo_producto


GO


/*****PROCEDIMIENTO DE OBTENER ID DE PRODUCTO*******/
IF EXISTS (SELECT sys.objects.name FROM sys.objects INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id WHERE sys.objects.name = 'looginSelectUser'  and sys.schemas.name = 'dbo'  and sys.objects.type = 'P')
/****** Object:  StoredProcedure [dbo].[looginSelectUser]    Script Date: 9/4/2015 2:16:26 PM ******/
DROP PROCEDURE [dbo].[looginSelectUser]
GO

/****** Object:  StoredProcedure [dbo].[looginSelectUser]    Script Date: 9/4/2015 2:16:26 PM ******/
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
