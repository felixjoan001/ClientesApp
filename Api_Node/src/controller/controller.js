import service from '../service/service.js'

async function getPersons(req, res) {
   try {
      const persons = await service.getPersons()
      res.json(persons)
   } catch (err) {
      res.status(500).send('Error al obtener personas')
   }
}

async function getPersonId(req, res) {
   try {
      const id = req.params.id
      const person = await service.getPersonId(id)
      res.json(person)
   } catch (err) {
      res.status(500).send('Error al obtener persona')
   }
}

async function createPerson(req, res) {
   try {
      const person = req.body
      const id = await service.createPerson(person)
      res.status(201).json({BusinessEntityID: id });
   } catch (err) {
      res.status(500).send('Error al crear nueva persona controller'+ err)
   }
}

async function updatePerson(req, res) {
   try {
      const person = req.body
      const id = req.params.id
      await service.updatePerson(person,id)
      res.send('Persona actualizada correctamente')
   } catch (err) {
      res.status(500).send('Error al actualizar persona controller')
   }
}

async function deletePerson(req, res) {
   try {
      const id = req.params.id
      await service.deletePerson(id)
      res.status(200).send('Person eliminada correctamente')
   } catch (err) {
      res.status(500).send('Error al eliminar persona controller')
   }
}
/* Store */

async function getStores(req, res) {
   try {
      const stores = await service.getStores()
      res.json(stores)
   } catch (err) {
      res.status(500).send('Error al obtener tiendas')
   }
}

async function updateStore(req, res) {
   try {
      const store = req.body
      const id = req.params.id
      await service.updateStore(store,id)
      res.send('Tienda actualizada correctamente')
   } catch (err) {
      res.status(500).send('Error al actualizar tienda controller')
   }
}

async function getStoreId(req, res) {
   try {
      const id = req.params.id
      const store = await service.getStoreId(id)
      res.json(store)
   } catch (err) {
      res.status(500).send('Error al obtener tienda')
   }
}

async function createStore(req, res) {
   try {
      const store = req.body
      console.log( store); 
      const id = await service.createStore(store)
      res.status(201).json({BusinessEntityID: id });
   } catch (err) {
      res.status(500).send('Error al crear nueva tienda controller'+ err)
   }
}

async function deleteStore(req, res) {
   try {
      const id = req.params.id
      await service.deleteStore(id)
      res.status(200).send('Tienda eliminada correctamente')
   } catch (err) {
      res.status(500).send('Error al eliminar tienda controller')
   }
}

/* END POINTS VENDOR */
async function getVendors(req, res) {
   try {
      const vendors = await service.getVendors()
      res.json(vendors)
   } catch (err) {
      res.status(500).send('Error al obtener vendedores')
   }
}

async function getVendorId(req, res) {
   try {
      const id = req.params.id
      const vendor = await service.getVendorId(id)
      res.json(vendor)
   } catch (err) {
      res.status(500).send('Error al obtener el vendor')
   }
}

async function updateVendor(req, res) {
   try {
      const vendor = req.body
      const id = req.params.id
   
      await service.updateVendor(vendor,id)
      res.send('Vendor actualizado correctamente')
   }catch (err) {
      res.status(500).send('Error al actualizar vendor controller')
   }
}

async function createVendor(req, res) {
   try {
      const vendor = req.body
      const id = await service.createVendor(vendor)
      res.status(201).json({BusinessEntityID: id });
   } catch (err) {
      res.status(500).send('Error al crear nuevo vendor controller')
   }
}

async function deleteVendor(req, res){
   try {
      const id = req.params.id
      console.log(id);
      await service.deleteVendor(id)
      res.status(200).send('Vendor eliminado correctamente')
   } catch (err) {
      res.status(500).send('Error al eliminar vendor controller')
   }
}

export default {
   getPersons,
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