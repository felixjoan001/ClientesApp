import sql from 'mssql';

const sqlConfig = {
   server: 'localhost',
   database: 'AdventureWorks2019',
   user: 'sa',
   password: 'molina98',
   port: 1433,
   options: {
      encrypt: true,
      
      trustServerCertificate:true
   }
}
let pool
async function connect() {
   try {
      pool = await sql.connect(sqlConfig);
      console.log('Connected to SQL Server');

      return pool;
   }catch(err) {
      console.log(err);
   }
}


export {
   sqlConfig,
   connect
}