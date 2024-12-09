import {connect
} from '../database/connection.js';
import mssql from 'mssql'
let pool = null

//Endpoints de PERSONS 

async function getPersons() {
   try {
      pool = await connect()
      const result = await pool.request().query('SELECT TOP 100 * FROM Person.Person ORDER BY BusinessEntityID DESC')
      return result.recordset
   }catch(err) {
      console.log('Error al obtener productos: ', err)
      throw new Error('Error al obtener productos')
   } finally {
      if (pool) {
         pool.close()
      }
   }
}

async function getProducts() {
   
   try {
      pool = await connect()
      const result = await pool.request().query('SELECT * FROM Production.Product')
      return result.recordset
   } catch (err) {
      throw new Error('Error al obtener productos')
   }
}

async function getPersonId(id) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('id', mssql.Int, id)
         .query('SELECT * FROM Person.Person WHERE BusinessEntityID = @id')
      return result.recordset
   } catch (err) {
      throw new Error('Error al obtener persona')
   }
}

async function createPerson(person) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('FirstName', mssql.NVarChar, person.FirstName)
         .input('LastName', mssql.NVarChar, person.LastName)
         .input('PersonType', mssql.NVarChar, person.PersonType)
         .query(`INSERT INTO Person.BusinessEntity (rowguid, ModifiedDate) OUTPUT INSERTED.BusinessEntityID VALUES (NEWID(), GETDATE())`)
         .then(result => {
         const businessEntityId = result.recordset[0].BusinessEntityID;
         return pool.request()
            .input('BusinessEntityID', mssql.Int, businessEntityId)
            .input('FirstName', mssql.NVarChar, person.FirstName)
            .input('LastName', mssql.NVarChar, person.LastName)
            .input('PersonType', mssql.NVarChar, person.PersonType)
            .query(`INSERT INTO Person.Person (BusinessEntityID, PersonType, FirstName, LastName, rowguid, ModifiedDate) VALUES (@BusinessEntityID, @PersonType, @FirstName, @LastName, NEWID(), GETDATE())`);
         });
      
      return result.recordset

   } catch (err) {
      throw new Error('Error al crear nueva persona model'+ err)
   }
}

async function updatePerson(person,id) {
   try {
      if (!person || !id) {
         throw new Error('Los parámetros person e id son obligatorios');
      }
      
     pool = await connect();
     const result = await pool.request()
        .input('BusinessEntityID', mssql.Int, id)
        .input('FirstName', mssql.NVarChar, person.FirstName)
        .input('LastName', mssql.NVarChar, person.LastName)
        .input('PersonType', mssql.NVarChar, person.PersonType)
        .query(`
           UPDATE Person.Person 
           SET FirstName = @FirstName, LastName = @LastName, PersonType = @PersonType 
           WHERE BusinessEntityID = @BusinessEntityID;
        `)
      
      return result.recordset
   } catch (err) {
      console.error('Error en updatePerson:', err.message);
      throw new Error('Error al actualizar persona');
   } finally {
      if (pool) {
         pool.close()
      }
   }
}


async function deletePerson(id) {
   try {
      pool = await connect();
      const result = await pool.request()
    .input('BusinessEntityID', mssql.Int, id)
    .query(`
        BEGIN TRANSACTION;

        DELETE FROM Person.Password WHERE BusinessEntityID = @BusinessEntityID;

        DELETE FROM Person.EmailAddress WHERE BusinessEntityID = @BusinessEntityID;

        DELETE so
        FROM Sales.SalesOrderHeader so
        INNER JOIN Sales.Customer sc ON so.CustomerID = sc.CustomerID
        WHERE PersonID = @BusinessEntityID;

        DELETE FROM Sales.Customer WHERE PersonID = @BusinessEntityID;

        DELETE pc
        FROM Sales.PersonCreditCard pc
        INNER JOIN Sales.CreditCard cc ON pc.CreditCardID = cc.CreditCardID
        WHERE BusinessEntityID = @BusinessEntityID;

        DELETE FROM Sales.PersonCreditCard WHERE BusinessEntityID = @BusinessEntityID;

        DELETE bea
        FROM Person.BusinessEntityAddress bea
        INNER JOIN Person.BusinessEntity be ON bea.BusinessEntityID = be.BusinessEntityID
        WHERE be.BusinessEntityID = @BusinessEntityID;

        DELETE pf
        FROM Person.PersonPhone pf
        INNER JOIN Person.Person pp ON pf.BusinessEntityID = pp.BusinessEntityID
        WHERE pp.BusinessEntityID = @BusinessEntityID;

        DELETE FROM Person.Person WHERE BusinessEntityID = @BusinessEntityID;

        DELETE FROM Person.BusinessEntity WHERE BusinessEntityID = @BusinessEntityID;

        COMMIT TRANSACTION;
    `);
      return result.recordset
   } catch (err) {
      console.error('Error en deletePerson:', err.message);
      throw new Error('Error al eliminar persona');
   } finally {
      if (pool) {
         pool.close();
      }
   }
}

//Enpoints de Store

async function getStores() {
   try {
      pool = await connect()
      const result = await pool.request().query('SELECT TOP 100 * FROM Sales.Store ORDER BY BusinessEntityID DESC')
      return result.recordset
   } catch (err) {
      console.error('Error en getStores:', err.message)
      throw new Error('Error al obtener tiendas')
   }
}

async function updateStore(store, id) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .input('Name', mssql.NVarChar, store.Name)
         .input('SalesPersonID', mssql.Int, store.SalesPersonID)
         .query(`
            UPDATE Sales.Store
            SET Name = @Name, SalesPersonID = @SalesPersonID
            WHERE BusinessEntityID = @BusinessEntityID;
         `)
      return result.recordset
   } catch (err) {
      console.error('Error en updateStore:', err.message)
      throw new Error('Error al actualizar tienda')
   }
}

async function getStoreId(id) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .query('SELECT * FROM Sales.Store WHERE BusinessEntityID = @BusinessEntityID')
      return result.recordset
   } catch (err) {
      console.error('Error en getStoreId:', err.message)
      throw new Error('Error al obtener tienda')
   }
}

async function createStore(store) {
   try {
      pool = await connect()
      const result = await pool.request()
         .query(`INSERT INTO Person.BusinessEntity (rowguid, ModifiedDate) OUTPUT INSERTED.BusinessEntityID VALUES (NEWID(), GETDATE())`)
         .then(result => {
            const businessEntityId = result.recordset[0].BusinessEntityID;
            return pool.request()
               .input('BusinessEntityID', mssql.Int, businessEntityId)
               .input('Name', mssql.NVarChar, store.Name)
               .input('SalesPersonID', mssql.Int, store.SalesPersonID)
               .query(`
                  INSERT INTO Sales.Store (BusinessEntityID, Name, SalesPersonID, rowguid, ModifiedDate)
                  VALUES (@BusinessEntityID, @Name, @SalesPersonID, NEWID(), GETDATE());
               `);
         });
      return result.recordset
   } catch (err) {
      console.error('Error en createStore:', err.message)
      throw new Error('Error al crear tienda')
   }
}

async function deleteStore(id) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .query(`BEGIN TRANSACTION

DELETE saor FROM Sales.SalesOrderHeader saor
    INNER JOIN Sales.Customer sacu
        ON saor.CustomerID = sacu.CustomerID WHERE StoreID = @BusinessEntityID;

DELETE sacu FROM Sales.Customer sacu
    INNER JOIN Sales.Store ss
        on sacu.StoreID = ss.BusinessEntityID
            WHERE SS.BusinessEntityID = @BusinessEntityID;

DELETE FROM Sales.Store WHERE BusinessEntityID = @BusinessEntityID;

COMMIT TRANSACTION`)
   } catch (err) {
      console.error('Error en deleteStore:', err.message)
      throw new Error('Error al eliminar tienda en model')
   }
}

//Endpoints Vendor

async function getVendors() {
   try {
      const pool = await connect()
      const result = await pool.request().query('SELECT * FROM Purchasing.Vendor ORDER BY BusinessEntityID DESC') 
      return result.recordset
   } catch (err) {
      throw new Error('Error al obtener vendedores')
   }
}

async function getVendorId(id) {
   try {
       pool = await connect()
      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .query('SELECT * FROM Purchasing.Vendor WHERE BusinessEntityID = @BusinessEntityID')
      return result.recordset
   } catch (err) {
      throw new Error('Error al obtener vendedor')
   }
}

async function updateVendor(vendor, id) {
   try {
      const pool = await connect()

      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .input('AccountNumber', mssql.NVarChar, vendor.AccountNumber)
         .input('Name', mssql.NVarChar, vendor.Name)
         .input('CreditRating', mssql.Int, vendor.CreditRating)
         .input('PreferredVendorStatus', mssql.Bit, vendor.PreferredVendorStatus)
         .input('ActiveFlag', mssql.Bit, vendor.ActiveFlag)
         .input('PurchasingWebServiceURL', mssql.NVarChar, vendor.PurchasingWebServiceURL)
         .query(`
            UPDATE Purchasing.Vendor
            SET AccountNumber = @AccountNumber, Name = @Name, CreditRating = @CreditRating, PreferredVendorStatus = @PreferredVendorStatus, ActiveFlag = @ActiveFlag, PurchasingWebServiceURL = @PurchasingWebServiceURL
            WHERE BusinessEntityID = @BusinessEntityID;
         `)
      return result.recordset
   } catch (err) {
      throw new Error('Error al actualizar vendedor')  
   }
}  

async function createVendor(vendor) {

   console.log(vendor)
   try {
      pool = await connect()
      const result = await pool.request()
         .query(`INSERT INTO Person.BusinessEntity (rowguid, ModifiedDate) OUTPUT INSERTED.BusinessEntityID VALUES (NEWID(), GETDATE())`)
         .then(result => {
            const businessEntityId = result.recordset[0].BusinessEntityID;
            return pool.request()
               .input('BusinessEntityID', mssql.Int, businessEntityId)
               .input('AccountNumber', mssql.NVarChar, vendor.AccountNumber)
               .input('Name', mssql.NVarChar, vendor.Name)
               .input('CreditRating', mssql.Int, vendor.CreditRating)
               .input('PreferredVendorStatus', mssql.Bit, vendor.PreferredVendorStatus)
               .input('ActiveFlag', mssql.Bit, vendor.ActiveFlag)
               .query(`
                  INSERT INTO Purchasing.Vendor (BusinessEntityID, AccountNumber, Name, CreditRating, PreferredVendorStatus, ActiveFlag, ModifiedDate)
                  VALUES (@BusinessEntityID, @AccountNumber, @Name, @CreditRating, @PreferredVendorStatus, @ActiveFlag, GETDATE());
               `);
         });
      return result.recordset
   } catch (err) {
      console.log('Error al crear vendedor: ', err)
      throw new Error('Error al crear vendedor')
   }
}

async function deleteVendor(id) {
   try {
      pool = await connect()
      const result = await pool.request()
         .input('BusinessEntityID', mssql.Int, id)
         .query(`BEGIN TRANSACTION;

-- Deshabilitar el trigger
DISABLE TRIGGER dVendor ON Purchasing.Vendor;

-- Eliminar la fila
DELETE pod FROM Purchasing.PurchaseOrderDetail pod 
INNER JOIN Purchasing.PurchaseOrderHeader poh ON pod.PurchaseOrderID = poh.PurchaseOrderID
INNER JOIN Purchasing.Vendor pv ON poh.VendorID = pv.BusinessEntityID 
WHERE pv.BusinessEntityID = @BusinessEntityID;

DELETE poh FROM Purchasing.PurchaseOrderHeader poh 
INNER JOIN Purchasing.Vendor pv ON pv.BusinessEntityID = poh.VendorID
WHERE pv.BusinessEntityID = @BusinessEntityID;

DELETE pv FROM Purchasing.ProductVendor pv 
INNER JOIN Purchasing.Vendor ON pv.BusinessEntityID = Vendor.BusinessEntityID 
WHERE pv.BusinessEntityID = @BusinessEntityID;

DELETE FROM Purchasing.Vendor WHERE BusinessEntityID = @BusinessEntityID;

-- Habilitar el trigger nuevamente
ENABLE TRIGGER dVendor ON Purchasing.Vendor;

COMMIT TRANSACTION;
         `)
      return result.recordset
   } catch (err) {
      throw new Error('Error al eliminar vendedor')
   } finally {
      if (pool) {
         pool.close();
      }
   }
}

export default {
   getPersons,
   getProducts,
   getPersonId,
   createPerson,
   updatePerson,
   deletePerson,
   getStores,
   updateStore,
   getStoreId,
   createStore,
   deleteStore,
   getVendors,
   getVendorId,
   updateVendor,
   createVendor,
   deleteVendor

}