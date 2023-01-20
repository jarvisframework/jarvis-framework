/***************************************************************************************************************************************************
 *   字段添加或修改注释
***************************************************************************************************************************************************/
if exists (select 1 from sysobjects where id = object_id('comment_column'))
begin
drop procedure comment_column;
end
go
create procedure comment_column (@table_name nvarchar(100), @column_name nvarchar(100), @comment nvarchar(255))
    as
begin
    declare @commentid int;

    if @comment is not null
begin
select @commentid=a.id from syscolumns a
                                join sysobjects d on a.id=d.id
                                left join sys.extended_properties g on a.id=g.major_id and a.colid=g.minor_id
where d.name=@table_name and a.name=@column_name and g.[value] is not null

    if @commentid is null
begin
exec sp_addextendedproperty N'MS_Description', @comment, N'user', N'dbo', N'table', @table_name, N'column', @column_name
end
else
begin
exec sp_updateextendedproperty N'MS_Description', @comment, N'user', N'dbo', N'table', @table_name, N'column', @column_name
end
end

end;
go

/***************************************************************************************************************************************************
 *   修改字段
***************************************************************************************************************************************************/
if exists (select 1 from sysobjects where id = object_id('modify_column'))
begin
drop procedure modify_column;
end
go
create procedure modify_column (@table_name nvarchar(100), @column_name nvarchar(100), @data_type nvarchar(32), @default_value nvarchar(100), @comment nvarchar(255))
    as
begin
    declare @tid       int;
    declare @cid       int;
    declare @sql       nvarchar(2000);
    declare @constraint_name nvarchar(100);
    declare @commentid int;

select @tid=object_id(@table_name);

if @tid is null
begin
        return;
end;

select @cid = column_id from sys.columns sc where sc.object_id=@tid and sc.name=@column_name;

begin
select @constraint_name=name from sys.default_constraints
where type='D' and parent_object_id=@tid and parent_column_id=@cid;
if @constraint_name is not null
begin
            set @sql = N'alter table ' + @table_name + N' drop constraint ' + @constraint_name;
exec sp_executesql @sql;
end;

        set @sql = N'alter table ' + @table_name + N' alter column ' + @column_name + N' ' + @data_type;
end;

exec sp_executesql @sql;

    if @default_value is not null
begin
        set @sql = N'alter table ' + @table_name + N' add constraint DF_' + @table_name + N'_' + @column_name + N' default N''' + @default_value + ''' for ' + @column_name;
exec sp_executesql @sql;
end;

    if @comment is not null
        exec comment_column @table_name, @column_name, @comment
end

end;
go

/***************************************************************************************************************************************************
 *   删除字段
***************************************************************************************************************************************************/
if exists (select 1 from sysobjects where id = object_id('drop_column'))
begin
drop procedure drop_column;
end
go
create procedure drop_column (@table_name nvarchar(100), @column_name nvarchar(100))
    as
begin
    declare @tid       int;
    declare @cid       int;
    declare @sql       nvarchar(2000);
    declare @constraint_name nvarchar(100);

select @tid=object_id(@table_name);

if @tid is null
begin
        return;
end;

select @cid = column_id from sys.columns sc where sc.object_id=@tid and sc.name=@column_name;

begin
select @constraint_name=name from sys.default_constraints
where type='D' and parent_object_id=@tid and parent_column_id=@cid;
if @constraint_name is not null
begin
            set @sql = N'alter table ' + @table_name + N' drop constraint ' + @constraint_name;
exec sp_executesql @sql;
end;

        set @sql = N'alter table ' + @table_name + N' drop column ' + @column_name;
end;

exec sp_executesql @sql;

end;
go
